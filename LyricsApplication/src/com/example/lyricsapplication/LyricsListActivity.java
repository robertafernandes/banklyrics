package com.example.lyricsapplication;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Musica;

public class LyricsListActivity extends Activity {

	private List<Musica> musicas;
	private DataBase<Musica> database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musicas);

		database = MusicaDataBase.getInstance(this);
		musicas = database.getList();
		
		LinearLayout parent = (LinearLayout) findViewById(R.id.musicasll); 
		parent.removeAllViews();
		
		for (Musica musica : musicas) {
			LinearLayout child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_musica, null); 
			
			final int id = musica.getId(); 
			final String letra = musica.getLyrics();
			final TextView name = (TextView) child.findViewById(R.id.textView1); 
			name.setText(musica.getName()); 
			
			name.setOnClickListener(new OnClickListener() { 
				@Override 
				public void onClick(View v) {
					Intent intent = new Intent(LyricsListActivity.this, ViewLyricsActivity.class);  
					intent.putExtra("nome", (String) name.getText());
					intent.putExtra("letra", letra);   
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			    	startActivity(intent);
				}
			});	 
			
			ImageView editar = (ImageView) child.findViewById(R.id.imageView2);
			editar.setOnClickListener(new OnClickListener() {
				@Override 
				public void onClick(View v) {
					Intent intent = new Intent(LyricsListActivity.this, EditLyricsActivity.class); 
					intent.putExtra("id", (Integer) id);  
					intent.putExtra("nome", (String) name.getText());
					intent.putExtra("letra", letra);  
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			    	startActivity(intent);
				}
			});
			 
			ImageView excluir = (ImageView) child.findViewById(R.id.imageView3);  
			excluir.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					database.remove(id);  
					Intent intent = new Intent(LyricsListActivity.this, LyricsListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);  
					Toast.makeText(LyricsListActivity.this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();  
				}
			});  
			
			parent.addView(child); 
		}
		
	} 

	public void voltar(View view) {
		Intent intent = new Intent(this, LyricsMainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	} 
	
	public void inserir(View view) {
		Intent intent = new Intent(this, LyricsRegistryActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent); 
	} 

}
