package com.example.lyricsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Musica;

public class EditLyricsActivity extends Activity implements OnClickListener {

	private DataBase<Musica> database;
	private Button save;
	private Button cancel; 
	private EditText edName;
	private EditText edLyrics;
	
	int id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_registry);

		id = getIntent().getIntExtra("id", 0); 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");

		// Total value
		edName = (EditText) findViewById(R.id.editText1);
		edLyrics = (EditText) findViewById(R.id.editText2);

		edName.setText(nome);
		edLyrics.setText(letra);
//		edName.invalidate();
//		edLyrics.invalidate(); 

		database = MusicaDataBase.getInstance(this);

		save = (Button) findViewById(R.id.button2);
		save.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.button1);
		cancel.setOnClickListener(this); 

	}

	public void onClick(View v) {
		Log.d("elfinha", "dentro do onclick");
		if (v == save) {

			String name = edName.getText().toString();
			String letra = edLyrics.getText().toString();

			database.update(id, name, letra);
		}

		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
		Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();   
	}
}