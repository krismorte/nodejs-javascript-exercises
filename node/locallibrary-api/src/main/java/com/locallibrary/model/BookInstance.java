package com.locallibrary.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookinstances")
public class BookInstance {

	@Id
	private String _id;
	private String book;
    private String imprint;
    private String status;
    private LocalDate due_back;
    @Transient
	private String url;
    @Transient
    private LocalDate due_back_formatted;
    @Transient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
    public BookInstance() {
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getImprint() {
		return imprint;
	}

	public void setImprint(String imprint) {
		this.imprint = imprint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDue_back() {
		return due_back;
	}

	public void setDue_back(LocalDate due_back) {
		this.due_back = due_back;
	}
    
	public String getUrl() {
		return "/catalog/bookinstance/" + this._id.toString();
	}
	
	public String getDue_back_formatted() {
		return due_back_formatted == null ? "" : due_back_formatted.format(formatter);
	}
}
