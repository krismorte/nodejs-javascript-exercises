package com.locallibrary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locallibrary.exception.ExistingObjectException;
import com.locallibrary.model.Author;
import com.locallibrary.model.Genre;

@RestController
public class AuthorController {

	@Autowired
	private AuthorService service;

	@RequestMapping("/authors")
	public List<Author> getAll() {
		return service.listAll();

	}

	@RequestMapping("/authors/{id}")
	public Author getAuthor(@PathVariable String id) {
		return service.getAuthor(id).orElse(null);

	}

	/*@RequestMapping("/authors/name/{name}")
	public Genre getGenreByName(@PathVariable String name) {
		return service.getGenreByName(name).orElse(null);

	}*/

	@RequestMapping(method = RequestMethod.POST, value = "/authors")
	public void addAuthor(@RequestBody Author author){
		/*if (service.getGenreByName(genre.getName()).isPresent()) {
			throw new ExistingObjectException();
		}*/
		service.addAuthor(author);
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/authors/{id}")
	public void updateAuthor(@RequestBody Author author) {
		/*Optional<Author> genreDatabase=service.getGenreByName(genre.getName());
		if (genreDatabase.isPresent()) {
			if(!genreDatabase.get().get_id().equals(genre.get_id())) {
				throw new ExistingObjectException();	
			}			
		}*/
		service.updateAuthor(author);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/authors/{id}")
	public void deleteAuthor(@PathVariable String id) {
		service.deleteAuthor(id);
	}

}
