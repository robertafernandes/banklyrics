package com.example.lyricsapplication.bd;

import java.util.List;

public interface DataBase<T> {
	
	public long insert(T object);

	public void remove(int id);
	
	public void update(int id, String data1, String data2);
	
	public List<T> getList(); 
	
	public List<T> getList(String data1);

}
