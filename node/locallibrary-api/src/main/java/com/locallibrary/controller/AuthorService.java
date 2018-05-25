package com.locallibrary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locallibrary.model.Author;
import com.locallibrary.model.Genre;
import com.locallibrary.repository.AuthorRespository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRespository repository;

	public List<Author> listAll() {
		return repository.findAll();
	}

	public Optional<Author> getAuthor(String id) {
		return repository.findById(id);
	}

	/*public Optional<Genre> getGenreByName(String name) {
		return repository.findByName(name);
	}*/

	public void addAuthor(Author author) {
		repository.save(author);
	}

	public void updateAuthor(Author author) {
		repository.save(author);
	}

	public void deleteAuthor(String id) {
		repository.deleteById(id);
	}

}
