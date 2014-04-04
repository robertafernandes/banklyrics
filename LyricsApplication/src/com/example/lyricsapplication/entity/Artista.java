package com.example.lyricsapplication.entity;

public class Artista {

	private int id; 
	private String name;
	private byte[] image;    
	
	public Artista(String name, byte[] image) {
		this(0, name, image);
	} 
	
	public Artista(int id, String name, byte[] image) {
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	
	
	
}
