package com.example.lyricsapplication.bd;

import com.example.lyricsapplication.entity.Artista;

public interface IArtistaDataBase extends DataBase<Artista> {

	public void update(int id, String nome, byte image[]); 
}
