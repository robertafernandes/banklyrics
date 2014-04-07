package com.example.lyricsapplication.bd;

import java.util.List;

public interface DataBase<T> {
	
	public long insert(T object);

	public void remove(int id);
	
	//FIXME definir forma genérica
	//public void update(int id, Object ... args);
	
	public List<T> getList(); 
	
	public List<T> getList(String data1);

}
