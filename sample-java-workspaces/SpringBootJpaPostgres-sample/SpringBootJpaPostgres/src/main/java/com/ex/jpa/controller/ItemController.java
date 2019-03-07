package com.ex.jpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.jpa.model.Item;
import com.ex.jpa.repository.ItemRepository;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ItemController {
	
	@Value("${herasoo.id}")
	private String herasoo_id;

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/items")
	public List<Item> getAllItems() {
		System.out.println("Get all Items...");
		System.out.println("# herasoo.id=" + herasoo_id);

		List<Item> list = new ArrayList<>();
		Iterable<Item> items = itemRepository.findAll();
		items.forEach(list::add);
		return list;
	}

	@PostMapping("/items/create")
	public Item createItem(@Valid @RequestBody Item item) {
		System.out.println("Create Item: " + item.getName() + "...");

		return itemRepository.save(item);
	}

	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItem(@PathVariable("id") Long id) {
		System.out.println("Get Item by id...");

		Optional<Item> itemData = itemRepository.findById(id);
		if (itemData.isPresent()) {
			return new ResponseEntity<>(itemData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/items/{id}")
	public ResponseEntity<Item> updateItem(@PathVariable("id") Long id, @RequestBody Item item) {
		System.out.println("Update Item with ID = " + id + "...");

		Optional<Item> itemData = itemRepository.findById(id);
		if (itemData.isPresent()) {
			Item savedItem = itemData.get();
			savedItem.setId(id);
			savedItem.setName(item.getName());
			savedItem.setPrice(item.getPrice());
					
			Item updatedItem = itemRepository.save(savedItem);
			
			return new ResponseEntity<>(updatedItem, HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable("id") Long id) {
		System.out.println("Delete Item with ID = " + id + "...");

		try {
			itemRepository.deleteById(id);
		} catch (Exception e) {
			return new ResponseEntity<>("Fail to delete!", HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>("Item has been deleted!", HttpStatus.OK);
	}
}
