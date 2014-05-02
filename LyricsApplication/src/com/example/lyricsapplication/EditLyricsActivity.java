package com.example.lyricsapplication;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	private LinearLayout linLayoutUploadSong;
			
	private int id;
	private String songPath;
	
	private int RESULT_LOAD_AUDIO = 13;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_registry);

		databaseMusica = MusicaDataBase.getInstance(this);
		databaseArtista = ArtistaDataBase.getInstance(this);
		
		id = getIntent().getIntExtra("id", 0); 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");
		songPath = getIntent().getStringExtra("songPath");
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
        
        linLayoutUploadSong = (LinearLayout) findViewById(R.id.linLayoutUploadSong);
        linLayoutUploadSong.setOnClickListener(this);

	}

	public void onClick(View v) {
		Log.d("elfinha", "dentro do onclick");
		if (v == linLayoutUploadSong) {
			//Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);			
			
			//startActivityForResult(Intent.createChooser(i, "Selecione a Musica"), RESULT_LOAD_AUDIO);
			startActivityForResult(i, RESULT_LOAD_AUDIO);
			
//			Intent tmpIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
//		    startActivityForResult(tmpIntent, 0);
		} else if (v == save) {

			String name = edName.getText().toString();
			String letra = edLyrics.getText().toString();
			int position = spinnerArtist.getSelectedItemPosition();
			Artista artista = (Artista) artistaItemSpinnerAdapter.getItem(position);
			Musica musica = new Musica(id, name, letra, songPath, artista);
			databaseMusica.update(musica);
			
			Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
			startNewListIntent();
		} else {
			startNewListIntent();	
		}
	}
	
	private void startNewListIntent() {
		this.finish();		
    	Log.d("list", "Listando Letras");
		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);		
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_AUDIO && resultCode == RESULT_OK && null != data) {
            Uri selectedSong = data.getData();
            Log.d("song",selectedSong.toString());
//            songPath = selectedSong.getPath();
//            Log.d("song",songPath);
            
            
            /////////////////////////////
            String[] filePathColumn = { MediaStore.Audio.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedSong,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            songPath = cursor.getString(columnIndex);
            cursor.close();
            Log.d("song",songPath);
//            Bitmap bmp = BitmapFactory.decodeFile(imageArtist);
//            imViewArtist.setImageBitmap(bmp);            
            
        }
    }	
}