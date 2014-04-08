package com.example.lyricsapplication;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lyricsapplication.bd.ArtistaDataBase;
import com.example.lyricsapplication.bd.IArtistaDataBase;
import com.example.lyricsapplication.bd.IMusicaDataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Artista;

public class EditLyricsActivity extends Activity implements OnClickListener {

	private IMusicaDataBase databaseMusica;
	private IArtistaDataBase databaseArtista;
	private Button save;
	private Button cancel; 
	private EditText edName;
	private EditText edLyrics;
	private Spinner spinnerArtist;
	
	int id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_registry);

		id = getIntent().getIntExtra("id", 0); 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");

		// Total value
		edName = (EditText) findViewById(R.id.edName);
		edLyrics = (EditText) findViewById(R.id.edLyrics);

		edName.setText(nome);
		edLyrics.setText(letra);
//		edName.invalidate();
//		edLyrics.invalidate(); 

		databaseMusica = MusicaDataBase.getInstance(this);
		databaseArtista = ArtistaDataBase.getInstance(this);

		save = (Button) findViewById(R.id.bSave);
		save.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.bCancel);
		cancel.setOnClickListener(this); 
		
		spinnerArtist = (Spinner)findViewById(R.id.spinArtist);
		List<Artista> artistas = databaseArtista.getList();
        spinnerArtist.setAdapter(new ArtistaItemSpinnerAdapter(artistas, this));
	}

	public void onClick(View v) {
		Log.d("elfinha", "dentro do onclick");
		if (v == save) {

			String name = edName.getText().toString();
			String letra = edLyrics.getText().toString();
			//FIXME obter Artista
			Artista artista = null;

			databaseMusica.update(id, name, letra, artista);
		}

		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
		Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();   
	}
}