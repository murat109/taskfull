package com.murat.taskback.model;

import jakarta.persistence.*;

/* City entity class implementation.
 * Database table name: cities.
 * Columns: id (int), name (text), photo(text)
 * If photo column is to be used as a file, then change the type of photo to 
 * @Lob private byte[]   
 * Also in the frontend use file upload.
 */


@Entity
@Table(name = "cities")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "photo", length = 1000)
	private String photo;

	public City() {
		super();
	}

	public City(int id, String name, String photo) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
    
}
