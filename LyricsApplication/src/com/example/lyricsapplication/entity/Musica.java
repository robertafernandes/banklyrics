package com.example.lyricsapplication.entity;

public class Musica {

	private int id; 
	private String name;
	private String lyrics;    
	
	public Musica(String name, String lyrics) {
		super();  
		this.name = name; 
		this.lyrics = lyrics;
	} 
	
	public Musica(int id, String name, String lyrics) {
		super(); 
		this.id = id;
		this.name = name; 
		this.lyrics = lyrics;
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
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}
	
	
}
