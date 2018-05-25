package com.locallibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locallibrary.model.Book;
import com.locallibrary.model.BookInstance;

@RestController
public class BookInstanceController {

	@Autowired
	private BookInstanceService service;

	@RequestMapping("/bookinstances")
	public List<BookInstance> getAll() {
		return service.listAll();

	}

	@RequestMapping("/bookinstances/{id}")
	public BookInstance getBookInstance(@PathVariable String id) {
		return service.getBookInstance(id).orElse(null);

	}

	/*@RequestMapping("/authors/name/{name}")
	public Genre getGenreByName(@PathVariable String name) {
		return service.getGenreByName(name).orElse(null);

	}*/

	@RequestMapping(method = RequestMethod.POST, value = "/bookinstances")
	public void addBookInstance(@RequestBody BookInstance author){
		/*if (service.getGenreByName(genre.getName()).isPresent()) {
			throw new ExistingObjectException();
		}*/
		service.addBookInstance(author);
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/bookinstances/{id}")
	public void updateBookInstance(@RequestBody BookInstance author) {
		/*Optional<Author> genreDatabase=service.getGenreByName(genre.getName());
		if (genreDatabase.isPresent()) {
			if(!genreDatabase.get().get_id().equals(genre.get_id())) {
				throw new ExistingObjectException();	
			}			
		}*/
		service.updateBookInstance(author);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/bookinstances/{id}")
	public void deleteBookInstance(@PathVariable String id) {
		service.deleteBookInstance(id);
	}

}
