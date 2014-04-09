package com.example.lyricsapplication.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lyricsapplication.entity.Artista;

public class ArtistaDataBase implements DataBase<Artista>{ 
	
	private static final ArtistaDataBase instance = new ArtistaDataBase();
	
	private SQLiteDatabase db;
	 
	public ArtistaDataBase() {
		// Do nothing 
	} 
	
	/**
	 * Get UserDataBase instance
	 * 
	 * @param ctx Context
	 * @return The UserDataBase instance.
	 */
	public static DataBase<Artista> getInstance(Context ctx) {
		if (instance.db == null || !instance.db.isOpen()) {
			instance.db = new DbHelper(ctx).getWritableDatabase();
		}
		return instance;
	}
	
	@Override
	public long insert(Artista artista) {
		long retValue = -1;

		ContentValues cv = new ContentValues();
		cv.put(DbHelper.DATABASE_NAME_FIELD, artista.getName()); 
		cv.put(DbHelper.DATABASE_IMAGEM_FIELD, artista.getImage()); 
		
		try {
			db.beginTransaction();
			retValue = db.insert(DbHelper.TBL_ARTISTA, null, cv);
			if (retValue != -1) {
				db.setTransactionSuccessful();
			}
		} finally {
			db.endTransaction();
		}
		return retValue;
	}

	@Override 
	public void remove(int id) {
		db.beginTransaction();
		try {
			db.delete(DbHelper.TBL_ARTISTA, DbHelper.DATABASE_ID_FIELD + "=?",  
					new String[] { String.valueOf(id) });
			db.setTransactionSuccessful();
		} finally { 
			db.endTransaction();
		}
		
	} 
	
	public List<Artista> getList(String artistName) {
		
		List<Artista> list = new ArrayList<Artista>();
		String[] columns = new String[] { DbHelper.DATABASE_ID_FIELD, DbHelper.DATABASE_ID_FIELD,
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_IMAGEM_FIELD}; 
		
		String where = DbHelper.DATABASE_NAME_FIELD + " LIKE '%"   
        + artistName +"%'";  

		Cursor c = db.query(DbHelper.TBL_ARTISTA, columns, where, null, null, null, DbHelper.DATABASE_NAME_FIELD);
        
        c.moveToFirst();
        
        while (!c.isAfterLast()) {
			Artista artista = fillArtista(c);
			list.add(artista);
			c.moveToNext(); 
		}	
		
		return list;
	}
	
	
	public Artista getUnique(int id) {
				
		String[] columns = new String[] { DbHelper.DATABASE_ID_FIELD, DbHelper.DATABASE_ID_FIELD,
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_IMAGEM_FIELD}; 
		
		String where = DbHelper.DATABASE_ID_FIELD + " = ?";

		Cursor c = db.query(DbHelper.TBL_ARTISTA, columns, where, 
				new String[] { String.valueOf(id) }, null, null, null);
        
        c.moveToFirst();
        
        Artista artista = null;
        if (!c.isAfterLast()) {
			artista = fillArtista(c);			
			c.close(); 
		}	
		
		return artista;
	}
	
	
	@Override
	public List<Artista> getList() {
		List<Artista> list = new ArrayList<Artista>(); 
		
		String[] columns = new String[] { DbHelper.DATABASE_ID_FIELD, DbHelper.DATABASE_ID_FIELD,
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_IMAGEM_FIELD}; 
		
		Cursor c = db.query(DbHelper.TBL_ARTISTA, columns, null, null , null, null, DbHelper.DATABASE_NAME_FIELD); 
		
		c.moveToFirst();
		while (!c.isAfterLast()) {			
			Artista artista = fillArtista(c);
			list.add(artista);
			Log.d("artista-db", artista.getName());
			c.moveToNext(); 
		}
		c.close();
		return list;
	}
	 
	public String[] getArrayList() {
		List<Artista> Artistas = getList();
		String[] result = new String[Artistas.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Artistas.get(i).getName(); 
		}
		return result;
	}

	private Artista fillArtista(Cursor c) {    
		int id = c.getInt(c.getColumnIndex(DbHelper.DATABASE_ID_FIELD));
		String name = c.getString(c.getColumnIndex(DbHelper.DATABASE_NAME_FIELD));
		String image = c.getString(c.getColumnIndex(DbHelper.DATABASE_IMAGEM_FIELD));

		return new Artista(id, name, image);  
	}

	@Override
	public void update(Artista artista) { 
		
		db.beginTransaction();
		try {
			ContentValues valores = new ContentValues();
			valores.put(DbHelper.DATABASE_NAME_FIELD, artista.getName()); 
			valores.put(DbHelper.DATABASE_IMAGEM_FIELD, artista.getImage());
			
			db.update(DbHelper.TBL_ARTISTA, valores, DbHelper.DATABASE_ID_FIELD + "=?", 
					new String[] { String.valueOf(artista.getId()) });
			db.setTransactionSuccessful();
		} finally { 
			db.endTransaction();
		}
		
	}
}
