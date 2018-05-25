package com.locallibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.locallibrary.model.Author;

public interface AuthorRespository extends MongoRepository<Author, String>{

	
	
}
