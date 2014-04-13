package com.example.lyricsapplication;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lyricsapplication.bd.ArtistaDataBase;
import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.entity.Artista;

public class ArtistsListActivity extends Activity {

	private List<Artista> artistas;
	private DataBase<Artista> database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artistas);

		database = ArtistaDataBase.getInstance(this);
		artistas = database.getList();
		
		LinearLayout parent = (LinearLayout) findViewById(R.id.artistsall); 
		parent.removeAllViews();
		
		for (Artista artista : artistas) {
			LinearLayout child = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_artista, null); 
			
			final int id = artista.getId();
			final String image = artista.getImage();
			final TextView name = (TextView) child.findViewById(R.id.textName); 
			name.setText(artista.getName()); 
						
			
			if (image != null && !image.isEmpty()) {
				ImageView visualizar = (ImageView) child.findViewById(R.id.imageView1);
				Bitmap bmp = BitmapFactory.decodeFile(image);
				//Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
				visualizar.setImageBitmap(bmp);
			}
			
			ImageView editar = (ImageView) child.findViewById(R.id.imageView2);
			editar.setOnClickListener(new OnClickListener() {
				@Override 
				public void onClick(View v) {
					//FIXME atualizar
					Intent intent = new Intent(ArtistsListActivity.this, ArtistRegistryActivity.class); 
					intent.putExtra("id", (Integer) id);  
					intent.putExtra("nome", (String) name.getText());
					intent.putExtra("imagem", image);  
			    	startActivity(intent);
				}
			});
			 
			ImageView excluir = (ImageView) child.findViewById(R.id.imageView3);  
			excluir.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					database.remove(id);  
					Intent intent = new Intent(ArtistsListActivity.this, ArtistsListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					startActivity(intent);  
					Toast.makeText(ArtistsListActivity.this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();  
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
		Intent intent = new Intent(this, ArtistRegistryActivity.class);
		startActivity(intent); 
	} 

}
