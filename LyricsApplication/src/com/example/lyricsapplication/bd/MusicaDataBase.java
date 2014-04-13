package com.example.lyricsapplication.bd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lyricsapplication.entity.Artista;
import com.example.lyricsapplication.entity.Musica;

public class MusicaDataBase implements DataBase<Musica>{ 
	
	private static final MusicaDataBase instance = new MusicaDataBase();
	private static DataBase<Artista> dataBaseArtista;
	
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
	public static DataBase<Musica> getInstance(Context ctx) {
		if (instance.db == null || !instance.db.isOpen()) {
			instance.db = new DbHelper(ctx).getWritableDatabase();
			dataBaseArtista = ArtistaDataBase.getInstance(ctx);
		}
		return instance;
	}
	
	@Override
	public long insert(Musica musica) {
		long retValue = -1;

		ContentValues cv = new ContentValues();
		cv.put(DbHelper.DATABASE_NAME_FIELD, musica.getName()); 
		cv.put(DbHelper.DATABASE_LETRA_FIELD, musica.getLyrics());		
		if (musica.getArtista() != null) {
			int idArtista = musica.getArtista().getId();
			cv.put(DbHelper.DATABASE_ID_ARTISTA_FIELD, idArtista);
		}
		
		
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
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_LETRA_FIELD,
				DbHelper.DATABASE_ID_ARTISTA_FIELD}; 
		
		String where = DbHelper.DATABASE_NAME_FIELD + " LIKE '%"   
        + songName +"%'";  

		Cursor c = db.query(DbHelper.TBL_MUSICA, columns, where, null, null, null, 
				DbHelper.DATABASE_NAME_FIELD + DbHelper.ORDER_ASC);
        
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
				DbHelper.DATABASE_NAME_FIELD, DbHelper.DATABASE_LETRA_FIELD,
				DbHelper.DATABASE_ID_ARTISTA_FIELD}; 
		
		Cursor c = db.query(DbHelper.TBL_MUSICA, columns, null, null , null, null, 
				DbHelper.DATABASE_NAME_FIELD + DbHelper.ORDER_ASC); 
		
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
		int idArtista = c.getInt(c.getColumnIndex(DbHelper.DATABASE_ID_ARTISTA_FIELD));

		Artista artista = null;
		if (idArtista != 0) {
			artista = dataBaseArtista.getUnique(idArtista);
		}
		
		return new Musica(id, name, letra, artista);  
	}

	@Override
	public void update(Musica musica) { 
		
		db.beginTransaction();
		try {
			ContentValues valores = new ContentValues();
			valores.put(DbHelper.DATABASE_NAME_FIELD, musica.getName()); 
			valores.put(DbHelper.DATABASE_LETRA_FIELD, musica.getLyrics());
			int idArtista = 0;
			if (musica.getArtista() != null) {
				idArtista = musica.getArtista().getId();
			}
			valores.put(DbHelper.DATABASE_ID_ARTISTA_FIELD, idArtista);
			
			db.update(DbHelper.TBL_MUSICA, valores, DbHelper.DATABASE_ID_FIELD + "=?", 
					new String[] { String.valueOf(musica.getId()) });
			db.setTransactionSuccessful();
		} finally { 
			db.endTransaction();
		}
		
	}

	@Override
	public Musica getUnique(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
