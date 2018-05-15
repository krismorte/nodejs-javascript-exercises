package com.locallibrary.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.locallibrary.model.Genre;

public interface GenreRespository extends MongoRepository<Genre, String>{

	public Optional<Genre> findByName(String name);
	
}
