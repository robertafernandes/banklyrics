package com.example.lyricsapplication.entity;

public class Artista {

	private int id; 
	private String name;
	private String image;    
	
	public Artista(String name, String image) {
		this(0, name, image);
	} 
	
	public Artista(int id, String name, String image) {
		super(); 
		this.id = id;
		this.name = name; 
		this.image = image;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}
