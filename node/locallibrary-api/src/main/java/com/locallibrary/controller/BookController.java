package com.locallibrary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.locallibrary.model.Author;
import com.locallibrary.model.Book;

@RestController
public class BookController {

	@Autowired
	private BookService service;

	@RequestMapping("/books")
	public List<Book> getAll() {
		return service.listAll();

	}

	@RequestMapping("/books/{id}")
	public Book getBook(@PathVariable String id) {
		return service.getBook(id).orElse(null);

	}

	@RequestMapping("/books/genre/{genreId}")
	public List<Book> getBookByGenre(@PathVariable String genreId) {
		return service.getBookByGenre(genreId);

	}

	/*
	 * @RequestMapping("/authors/name/{name}") public Genre
	 * getGenreByName(@PathVariable String name) { return
	 * service.getGenreByName(name).orElse(null);
	 * 
	 * }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/books")
	public void addBook(@RequestBody Book author) {
		/*
		 * if (service.getGenreByName(genre.getName()).isPresent()) { throw new
		 * ExistingObjectException(); }
		 */
		service.addBook(author);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/books/{id}")
	public void updateBook(@RequestBody Book author) {
		/*
		 * Optional<Author> genreDatabase=service.getGenreByName(genre.getName()); if
		 * (genreDatabase.isPresent()) {
		 * if(!genreDatabase.get().get_id().equals(genre.get_id())) { throw new
		 * ExistingObjectException(); } }
		 */
		service.updateBook(author);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/books/{id}")
	public void deleteBook(@PathVariable String id) {
		service.deleteBook(id);
	}

}
