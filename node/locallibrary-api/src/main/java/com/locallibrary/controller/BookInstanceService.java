package com.locallibrary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locallibrary.model.BookInstance;
import com.locallibrary.model.Genre;
import com.locallibrary.repository.BookInstanceRespository;

@Service
public class BookInstanceService {

	@Autowired
	private BookInstanceRespository repository;

	public List<BookInstance> listAll() {
		return repository.findAll();
	}

	public Optional<BookInstance> getBookInstance(String id) {
		return repository.findById(id);
	}

	/*public Optional<Genre> getGenreByName(String name) {
		return repository.findByName(name);
	}
*/
	public void addBookInstance(BookInstance bookInstance) {
		repository.save(bookInstance);
	}

	public void updateBookInstance(BookInstance bookInstance) {
		repository.save(bookInstance);
	}

	public void deleteBookInstance(String id) {
		repository.deleteById(id);
	}

}
