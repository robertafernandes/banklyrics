package com.example.lyricsapplication;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lyricsapplication.entity.Artista;

public class ArtistaItemSpinnerAdapter extends BaseAdapter {
	
	private List<Artista> artistas;
	private Context contexto; 

	public ArtistaItemSpinnerAdapter(List<Artista> artistas, Context contexto) { 
		this.artistas = artistas;
		this.contexto = contexto;
	}

	@Override
	public int getCount() { 
		return artistas.size();
	}

	@Override
	public Object getItem(int position) {
		return artistas.get(position);
	}

	@Override
	public long getItemId(int position) { 
		return position;
	}
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Artista artista = artistas.get(position);  
		
		View linha = LayoutInflater.from(contexto).inflate(R.layout.item_spinner_artista, null);

		TextView txtView = (TextView) linha.findViewById(R.id.textName);  
		
		String image = artista.getImage();
		if (image != null && !image.isEmpty()) {
			ImageView visualizar = (ImageView) linha.findViewById(R.id.imageView1);
			Bitmap bmp = BitmapFactory.decodeFile(image);
			visualizar.setImageBitmap(bmp);
		}
		
		txtView.setText(artista.getName());     
		  
		return linha; 
	}
}
