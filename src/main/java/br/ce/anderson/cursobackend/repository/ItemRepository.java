package br.ce.anderson.cursobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ce.anderson.cursobackend.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    
}
