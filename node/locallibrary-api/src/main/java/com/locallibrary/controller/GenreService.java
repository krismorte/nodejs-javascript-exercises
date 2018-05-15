package com.locallibrary.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locallibrary.model.Genre;
import com.locallibrary.repository.GenreRespository;

@Service
public class GenreService {

	@Autowired
	private GenreRespository repository;

	public List<Genre> listAll() {
		return repository.findAll();
	}

	public Optional<Genre> getGenre(String id) {
		return repository.findById(id);
	}

	public Optional<Genre> getGenreByName(String name) {
		return repository.findByName(name);
	}

	public void addGenre(Genre genre) {
		repository.save(genre);
	}

	public void updateGenre(Genre genre) {
		repository.save(genre);
	}

	public void deleteGenre(String id) {
		repository.deleteById(id);
	}

}
