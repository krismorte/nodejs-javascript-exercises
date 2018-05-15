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
import com.locallibrary.model.Genre;

@RestController
public class GenreController {

	@Autowired
	private GenreService service;

	@RequestMapping("/genres")
	public List<Genre> getAllCourses() {
		return service.listAll();

	}

	@RequestMapping("/genres/{id}")
	public Genre getGenre(@PathVariable String id) {
		return service.getGenre(id).orElse(null);

	}

	@RequestMapping("/genres/name/{name}")
	public Genre getGenreByName(@PathVariable String name) {
		return service.getGenreByName(name).orElse(null);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/genres")
	public void addGenre(@RequestBody Genre genre){
		if (service.getGenreByName(genre.getName()).isPresent()) {
			throw new ExistingObjectException();
		}
		service.addGenre(genre);
	}
	

	@RequestMapping(method = RequestMethod.PUT, value = "/genres/{id}")
	public void updateGenre(@RequestBody Genre genre) {
		Optional<Genre> genreDatabase=service.getGenreByName(genre.getName());
		if (genreDatabase.isPresent()) {
			if(!genreDatabase.get().get_id().equals(genre.get_id())) {
				throw new ExistingObjectException();	
			}			
		}
		service.updateGenre(genre);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/genres/{id}")
	public void deleteGenre(@PathVariable String id) {
		service.deleteGenre(id);
	}

}
