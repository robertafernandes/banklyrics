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
import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Artista;
import com.example.lyricsapplication.entity.Musica;

public class EditLyricsActivity extends Activity implements OnClickListener {

	private DataBase<Musica> databaseMusica;
	private DataBase<Artista> databaseArtista;
	private Button save;
	private Button cancel; 
	private EditText edName;
	private EditText edLyrics;
	private Spinner spinnerArtist;
	private ArtistaItemSpinnerAdapter artistaItemSpinnerAdapter;
		
	int id;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_registry);

		databaseMusica = MusicaDataBase.getInstance(this);
		databaseArtista = ArtistaDataBase.getInstance(this);
		
		id = getIntent().getIntExtra("id", 0); 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");
		int idArtista = getIntent().getIntExtra("idArtista",0);		
		Artista artista = new Artista("", ""); 
		if (idArtista != 0) {
			artista = databaseArtista.getUnique(idArtista);
		}

		// Total value
		edName = (EditText) findViewById(R.id.edName);
		edLyrics = (EditText) findViewById(R.id.edLyrics);

		edName.setText(nome);
		edLyrics.setText(letra);
//		edName.invalidate();
//		edLyrics.invalidate(); 

		save = (Button) findViewById(R.id.bSave);
		save.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.bCancel);
		cancel.setOnClickListener(this); 
		
		spinnerArtist = (Spinner)findViewById(R.id.spinArtist);
		List<Artista> artistas = databaseArtista.getList();
		artistaItemSpinnerAdapter = new ArtistaItemSpinnerAdapter(artistas, this);
        spinnerArtist.setAdapter(artistaItemSpinnerAdapter);
        int position = artistaItemSpinnerAdapter.getPosition(artista);        
        spinnerArtist.setSelection(position);
        //artistas.
        
        /*
        spinnerArtist.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			//	artistaItemSpinnerAdapter.getItem(position);
				spinnerArtist.getSelectedItem();
				
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub				
			}
       }); 
       */
	}

	public void onClick(View v) {
		Log.d("elfinha", "dentro do onclick");
		if (v == save) {

			String name = edName.getText().toString();
			String letra = edLyrics.getText().toString();
			int position = spinnerArtist.getSelectedItemPosition();
			Artista artista = (Artista) artistaItemSpinnerAdapter.getItem(position);
			Musica musica = new Musica(id, name, letra, artista);
			databaseMusica.update(musica);
		}

		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
		Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();   
	}
}