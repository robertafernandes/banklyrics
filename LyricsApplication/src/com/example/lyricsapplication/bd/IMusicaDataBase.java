package com.example.lyricsapplication.bd;

import com.example.lyricsapplication.entity.Artista;
import com.example.lyricsapplication.entity.Musica;

public interface IMusicaDataBase extends DataBase<Musica> {

	public void update(int id, String nome, String letra, Artista artista); 
}
