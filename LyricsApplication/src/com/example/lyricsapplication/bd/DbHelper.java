package com.example.lyricsapplication.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "dbMusicas";
	
	public static final String TBL_MUSICA = "Musica"; 
	
	public static final String DATABASE_ID_FIELD = "_id";

	public static final String DATABASE_NAME_FIELD = "nome";
 
	public static final String DATABASE_LETRA_FIELD = "letra";
	
	private static final int DBVERSION = 3;

	public DbHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	} 
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Musica ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "nome TEXT NOT NULL, letra TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
