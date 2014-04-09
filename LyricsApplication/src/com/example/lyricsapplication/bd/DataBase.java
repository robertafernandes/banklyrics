package com.example.lyricsapplication.bd;

import java.util.List;

public interface DataBase<T> {
	
	public long insert(T object);

	public void remove(int id);
	
	public void update(T object);
	
	public T getUnique(int id);
	
	public List<T> getList(); 
	
	public List<T> getList(String data1);

}
