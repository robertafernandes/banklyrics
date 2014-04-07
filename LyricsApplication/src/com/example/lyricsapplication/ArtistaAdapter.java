package com.example.lyricsapplication;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lyricsapplication.entity.Artista;

public class ArtistaAdapter extends BaseAdapter {
	
	private List<Artista> artistas;
	private Context contexto; 

	public ArtistaAdapter(List<Artista> artistas, Context contexto) { 
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
		
		View linha = LayoutInflater.from(contexto).inflate(R.layout.item_artista, null);
		 
//		ImageView imgview = (ImageView) linha.findViewById(R.id.imageView1); 
		TextView txtView = (TextView) linha.findViewById(R.id.textView1);  
		
//		ImageView imgview2 = (ImageView) linha.findViewById(R.id.imageView2);
//		ImageView imgview3 = (ImageView) linha.findViewById(R.id.imageView3);
		
//		imgview.setImageResource(musica.getFigura());
		txtView.setText(artista.getName());     
		  
		return linha; 
	}
}
