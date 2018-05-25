package com.locallibrary.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
public class Author {

	@Id
	private String _id;
	private String first_name;
	private String family_name;
	private LocalDate date_of_birth;
	private LocalDate date_of_death;
	@Transient
	private String url;
	@Transient
	private String date_of_birth_formatted;
	@Transient
	private String date_of_death_formatted;
	@Transient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	public Author() {
	}


	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public LocalDate getDate_of_death() {
		return date_of_death;
	}

	public void setDate_of_death(LocalDate date_of_death) {
		this.date_of_death = date_of_death;
	}

	public String getUrl() {
		return "/catalog/author/" + this._id.toString();
	}

	public String getDate_of_birth_formatted() {
		return date_of_birth == null ? "" : date_of_birth.format(formatter);
	}

	public String getDate_of_death_formatted() {
		return date_of_death == null ? "" : date_of_death.format(formatter);
	}


	public String get_id() {
		return _id;
	}


	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return this.family_name + ", " + this.first_name;
	}
}
