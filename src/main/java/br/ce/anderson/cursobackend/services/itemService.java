package br.ce.anderson.cursobackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ce.anderson.cursobackend.model.Item;
import br.ce.anderson.cursobackend.model.exception.ResourceNotFoundException;
import br.ce.anderson.cursobackend.repository.ItemRepository;
import br.ce.anderson.cursobackend.shared.ItemDTO;

@Service
public class itemService {
    
    @Autowired
    private ItemRepository itemrepository;

    public List<ItemDTO> obterTodos(){
        //return itemrepository.obterTodos();
        //return itemrepository.findAll();

        //Obtendo List<Item> do banco
        List<Item> itens = itemrepository.findAll();

        //Convertendo a List<Item> em uma List<ItemDTO> e retornando essa
        return itens.stream().map(item -> new ModelMapper().map(item, ItemDTO.class)).collect(Collectors.toList());
    }

    public Optional<ItemDTO> obterPorId(Integer id){
        //return itemrepository.obterPorId(id);

        //Obtendo opional de Item do banco
        Optional<Item> item = itemrepository.findById(id);

        // Se o Item não existe então é lançado uma exceção
        if(item.isEmpty()){
            throw new ResourceNotFoundException("Item com o id: " + id + " não encontrado!");
        }

        //Convertendo o optional de Item em um ItemDTO
        ItemDTO dto = new ModelMapper().map(item.get(), ItemDTO.class);
        
        //Retornando um optional de ItemDTO
        return Optional.of(dto);
    }

    public ItemDTO adicionar(ItemDTO itemDto){
        //return itemrepository.adicionar(item);
        itemDto.setId(null);
        //Convertendo ItemDTO em um Item
        Item item = new ModelMapper().map(itemDto, Item.class);

        //Salvando o Item no banco
        item = itemrepository.save(item);

        itemDto.setId(item.getId());

        //Retornando o ItemDTO
        return itemDto;
    }

    public void deletar(Integer id){
        //itemrepository.deletar(id);
        //itemrepository.deleteById(id);

        Optional<ItemDTO> item = obterPorId(id);

        //Verificando se o Item existe
        if(item.isEmpty()){
            throw new ResourceNotFoundException("Produto não existe!");
        }
        //Deletando o Item
        itemrepository.deleteById(id);;
    }

    public ItemDTO atualizar(Integer id, ItemDTO itemDto) throws Exception{
        itemDto.setId(id);
        //return itemrepository.atualizar(item);
        //return itemrepository.

        //Obtendo o Item do banco
        Optional<Item> itemAux = itemrepository.findById(id);

        //Verificando se o Item existe
        if(itemAux.isEmpty()){
            throw new ResourceNotFoundException("Produto não existe");
        }

        //Convertendo o ItemDTO em Item
        Item item = new ModelMapper().map(itemDto, Item.class);

        //Atualizando o Item
        itemrepository.save(item);

        //Retornando o ItemDTO
        return itemDto;
    }
}
