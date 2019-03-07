package com.ex.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ex.jpa.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{

}
