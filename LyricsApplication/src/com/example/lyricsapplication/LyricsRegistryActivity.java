package com.example.lyricsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Musica;

public class LyricsRegistryActivity extends Activity implements OnClickListener {

	private DataBase<Musica> database;
	private Button save;
	private Button cancel;
	private EditText edName;
	private EditText edLyrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_registry);

		database = MusicaDataBase.getInstance(this);

		save = (Button) findViewById(R.id.button2);
		save.setOnClickListener(this);

		cancel = (Button) findViewById(R.id.button1);
		cancel.setOnClickListener(this);

		edName = (EditText) findViewById(R.id.editText1); 
		edLyrics = (EditText) findViewById(R.id.editText2); 
	}

	@Override
	public void onClick(View v) {
		if (v == save){
			if ("".equals(edName.getText().toString()) || "".equals(edLyrics.getText().toString())) {
				Toast.makeText(this, "Você precisa especificar um nome e valor!", Toast.LENGTH_SHORT).show(); 
				
			} else {
				String name = edName.getText().toString().trim();
				String letra = edLyrics.getText().toString().trim(); 
				database.insert(new Musica(name, letra));  
				Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show(); 
				startNewIntent(); 
			}
		} else {
			startNewIntent();
		} 
	}
	
	private void startNewIntent() {
		Intent intent = new Intent(this, LyricsMainActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	startActivity(intent);
	}

}
