package com.example.lyricsapplication.entity;

public class Musica {

	private int id; 
	private String name;
	private String lyrics;
	private String arquivoAudio;    
	private Artista artista;	
	
	public Musica(String name, String lyrics, String arquivoAudio, Artista artista) {
		this(0, name, lyrics, arquivoAudio, artista);
	} 
	
	public Musica(int id, String name, String lyrics, String arquivoAudio, Artista artista) {
		super(); 
		this.id = id;
		this.name = name; 
		this.lyrics = lyrics;
		this.arquivoAudio = arquivoAudio;
		this.artista = artista;
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

	public String getArquivoAudio() {
		return arquivoAudio;
	}
	public void setArquivoAudio(String arquivoAudio) {
		this.arquivoAudio = arquivoAudio;
	}	
	
	public Artista getArtista() {
		return artista;
	}
	public void setArtista(Artista artista) {
		this.artista = artista;
	}	
	
}
