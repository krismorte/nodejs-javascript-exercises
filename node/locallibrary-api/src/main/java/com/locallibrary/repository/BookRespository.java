package com.locallibrary.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.locallibrary.model.Book;

@Repository
public interface BookRespository extends MongoRepository<Book, String>{
	
	@Query("{ 'genre' : ?0 }")
	public List<Book> findByGenre(List genre);
	
}
