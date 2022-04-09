package br.ce.anderson.cursobackend.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ce.anderson.cursobackend.model.Item;
import br.ce.anderson.cursobackend.services.itemService;
import br.ce.anderson.cursobackend.shared.ItemDTO;
import br.ce.anderson.cursobackend.view.model.ItemRequest;
import br.ce.anderson.cursobackend.view.model.ItemResponse;

@RestController
@RequestMapping("/api/itens")
public class ItemController {
    
    @Autowired
    private itemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> obterTodos(){
        List<ItemDTO> itens = itemService.obterTodos();

        ModelMapper mapper = new ModelMapper();

        List<ItemResponse> resposta = itens.stream()
        .map(itemDto -> mapper.map(itemDto, ItemResponse.class))
        .collect(Collectors.toList());

        return new ResponseEntity<>(resposta, HttpStatus.CREATED);

    }

    @PostMapping
    public ResponseEntity<ItemResponse> adicionar(@RequestBody ItemRequest itemReq){
        ModelMapper mapper = new ModelMapper();

        ItemDTO itemDto = mapper.map(itemReq, ItemDTO.class);

        itemDto = itemService.adicionar(itemDto);

        ItemResponse itemResp = mapper.map(itemDto, ItemResponse.class);

        return new ResponseEntity<>(itemResp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> obterPorId(@PathVariable Integer id){
        Optional<ItemDTO> itemDto = itemService.obterPorId(id);

        ItemResponse resposta = new ModelMapper().map(itemDto.get(), ItemResponse.class);
        
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
        itemService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> atualizar(@RequestBody ItemRequest itemReq, @PathVariable Integer id) throws Exception{
        ModelMapper mapper = new ModelMapper();
        
        ItemDTO itemDto = mapper.map(itemReq, ItemDTO.class);

        itemDto = itemService.atualizar(id, itemDto);

        ItemResponse itemResp = mapper.map(itemDto, ItemResponse.class);

        return new ResponseEntity<>(itemResp, HttpStatus.OK);
    }
    
}
