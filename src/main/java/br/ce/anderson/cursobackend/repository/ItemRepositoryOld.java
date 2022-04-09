package br.ce.anderson.cursobackend.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.ce.anderson.cursobackend.model.Item;
import br.ce.anderson.cursobackend.model.exception.ResourceNotFoundException;

@Repository
public class ItemRepositoryOld {
    private List<Item> itens = new ArrayList<Item>();
    private Integer ultimoId = 0;

    public List<Item> obterTodos(){
        return itens;
    }

    public Item obterPorId(Integer id){
        for(int i = 0; i < itens.size(); i++ ){
            if(itens.get(i).getId() == id){
                return itens.get(i);
            }
        }
        return null;
    }

    public Item adicionar(Item item){
        ultimoId++;
        item.setId(ultimoId);
        itens.add(item);
        return item;
    }

    public void deletar(Integer id){
        for(int i = 0; i < itens.size(); i++ ){
            if(itens.get(i).getId() == id){
                itens.remove(itens.get(i));
            }
        }
    }

    public Item atualizar(Item item) throws Exception{
        Item prod = obterPorId(item.getId());
        if(prod == null){
            throw new ResourceNotFoundException("Produto não existe");
        }
        itens.remove(prod);
        itens.add(item);
        return item;
    }
}
