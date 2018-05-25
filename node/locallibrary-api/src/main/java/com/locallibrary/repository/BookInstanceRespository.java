package com.locallibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.locallibrary.model.BookInstance;

public interface BookInstanceRespository extends MongoRepository<BookInstance, String>{
	
}
