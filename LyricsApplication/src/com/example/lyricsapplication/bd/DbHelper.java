package com.example.lyricsapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "dbMusicas";
	
	public static final String TBL_ARTISTA = "Artista"; 
	
	public static final String TBL_MUSICA = "Musica"; 
	
	public static final String DATABASE_ID_FIELD = "_id";
	
	public static final String DATABASE_ID_ARTISTA_FIELD = "idArtista";

	public static final String DATABASE_NAME_FIELD = "nome";
 
	public static final String DATABASE_LETRA_FIELD = "letra";
	
	public static final String DATABASE_IMAGEM_FIELD = "imagem";
	
	public static final String DATABASE_ARQUIVO_AUDIO_FIELD = "arquivoAudio";
	
	public static final String ORDER_ASC = " asc";
	
	private static final int DBVERSION = 3;

	public DbHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	} 
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "+TBL_ARTISTA+" ( "+DATABASE_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DATABASE_NAME_FIELD+ " TEXT COLLATE NOCASE NOT NULL, "+ DATABASE_IMAGEM_FIELD +" TEXT)");
		
		
		db.execSQL("CREATE TABLE "+TBL_MUSICA+" ( "+DATABASE_ID_FIELD+" INTEGER PRIMARY KEY AUTOINCREMENT, "				
				+ DATABASE_NAME_FIELD+ " TEXT COLLATE NOCASE NOT NULL, "+ DATABASE_LETRA_FIELD +" TEXT NOT NULL, "
				+ DATABASE_ARQUIVO_AUDIO_FIELD + " TEXT," + DATABASE_ID_ARTISTA_FIELD + " INTEGER, "
				+ "FOREIGN KEY("+DATABASE_ID_ARTISTA_FIELD+") REFERENCES "+TBL_ARTISTA+"("+DATABASE_ID_FIELD+"))");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
