package com.locallibrary.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locallibrary.model.Book;
import com.locallibrary.model.Genre;
import com.locallibrary.repository.BookRespository;

@Service
public class BookService {

	@Autowired
	private BookRespository repository;

	public List<Book> listAll() {
		return repository.findAll();
	}

	public Optional<Book> getBook(String id) {
		return repository.findById(id);
	}

	public List<Book> getBookByGenre(String genreId) {
		return repository.findByGenre(Arrays.asList(genreId));
	}

	public void addBook(Book book) {
		repository.save(book);
	}

	public void updateBook(Book book) {
		repository.save(book);
	}

	public void deleteBook(String id) {
		repository.deleteById(id);
	}

}

