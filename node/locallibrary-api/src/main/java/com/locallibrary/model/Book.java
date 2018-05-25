package com.locallibrary.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {

	@Id
	private String _id;
	private String title;
	private String author;
	private String summary;
	private String isbn;
	private List<String> genre;
    @Transient
	private String url;
    
    public Book() {	
	}


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<String> getGenre() {
		return genre;
	}
	
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
    
	public String getUrl() {
		return "/catalog/book/" + this._id.toString();
	}
	
}
