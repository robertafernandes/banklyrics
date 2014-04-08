package com.example.lyricsapplication.bd;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.UserDictionary.Words;

import com.example.lyricsapplication.entity.Artista;
import com.example.lyricsapplication.entity.Musica;

public class MusicaDataBase implements IMusicaDataBase{ 
	
	private static final MusicaDataBase instance = new MusicaDataBase();
	
	private SQLiteDatabase db;
	 
	public MusicaDataBase() {
		// Do nothing 
	} 
	
	/**
	 * Get UserDataBase instance
	 * 
	 * @param ctx Context
	 * @return The UserDataBase instance.
	 */
	public static IMusicaDataBase getInstance(Context ctx) {
		if (instance.db == null || !instance.db.isOpen()) {
			instance.db = new DbHelper(ctx).getWritableDatabase();
		}
		return instance;
	}
	
	@Override
	public long insert(Musica musica) {
		long retValue = -1;

		ContentValues cv = new ContentValues();
		cv.put(DbHelper.DATABASE_NAME_FIELD, musica.getName()); 
		cv.put(DbHelper.DATABASE_LETRA_FIELD, musica.getLyrics()); 
		
		try {
			db.beginTransaction();
			retValue = db.insert(DbHelper.TBL_MUSICA, null, cv);
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
			db.delete(DbHelper.TBL_MUSICA, DbHelper.DATABASE_ID_FIELD + "=?",  
					new String[] { String.valueOf(id) });
			db.setTransactionSuccessful();
		} finally { 
			db.endTransaction();
		}
		
	} 
	
	public List<Musica> getList(String songName) {
		
		List<Musica> list = new ArrayList<Musica>();
		String[] columns = new String[] { DbHelper.DATABASE_ID_FIELD, DbHelper.DATABASE_ID_FIELD,
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_LETRA_FIELD}; 
		
		String where = DbHelper.DATABASE_NAME_FIELD + " LIKE '%"   
        + songName +"%'";  

		Cursor c = db.query(DbHelper.TBL_MUSICA, columns, where, null, null, null, DbHelper.DATABASE_NAME_FIELD);
        
        c.moveToFirst();
        
        while (!c.isAfterLast()) {
			Musica Musica = fillMusica(c);
			list.add(Musica);
			c.moveToNext(); 
		}	
		
		return list;
	}
	
	@Override
	public List<Musica> getList() {
		List<Musica> list = new ArrayList<Musica>(); 
		
		String[] columns = new String[] { DbHelper.DATABASE_ID_FIELD,
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_LETRA_FIELD}; 
		
//		String where = DbHelper.DATABASE_DATE_FIELD + " >= '"   
//        + Util.getFirstDayMonth() +"' and " + DbHelper.DATABASE_DATE_FIELD + " <= '"   
//        + Util.getLastDayMonth() +"'"; 
		
//		Log.d("elfinha", "data 1 : " + Util.getFirstDayMonth() + " data 2: " + Util.getLastDayMonth());
		
		Cursor c = db.query(DbHelper.TBL_MUSICA, columns, null, null , null, null, null); 
		
		c.moveToFirst();
		while (!c.isAfterLast()) {
			Musica Musica = fillMusica(c);
			list.add(Musica);
			c.moveToNext(); 
		}
		return list;
	}
	 
	public String[] getArrayList() {
		List<Musica> Musicas = getList();
		String[] result = new String[Musicas.size()];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Musicas.get(i).getName(); 
		}
		return result;
	}

	private Musica fillMusica(Cursor c) {      
		int id = c.getInt(c.getColumnIndex(DbHelper.DATABASE_ID_FIELD));
		String name = c.getString(c.getColumnIndex(DbHelper.DATABASE_NAME_FIELD)); 
		String letra = c.getString(c.getColumnIndex(DbHelper.DATABASE_LETRA_FIELD));
		
		//FIXME receber Artista
		Artista artista = null;
		return new Musica(id, name, letra, artista);  
	}

	@Override
	public void update(int id, String nome, String letra, Artista artista) { 
		
		db.beginTransaction();
		try {
			ContentValues valores = new ContentValues();
			valores.put(DbHelper.DATABASE_NAME_FIELD, nome); 
			valores.put(DbHelper.DATABASE_LETRA_FIELD, letra);
			
			db.update(DbHelper.TBL_MUSICA, valores, DbHelper.DATABASE_ID_FIELD + "=?", new String[] { String.valueOf(id) });
			db.setTransactionSuccessful();
		} finally { 
			db.endTransaction();
		}
		
	}
}
