package com.example.lyricsapplication;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lyricsapplication.entity.Musica;

public class MusicaAdapter extends BaseAdapter {
	
	private List<Musica> musicas;
	private Context contexto; 

	public MusicaAdapter(List<Musica> musicas, Context contexto) { 
		this.musicas = musicas;
		this.contexto = contexto;
	}

	@Override
	public int getCount() { 
		return musicas.size();
	}

	@Override
	public Object getItem(int position) {
		return musicas.get(position);
	}

	@Override
	public long getItemId(int position) { 
		return position;
	}
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Musica musica = musicas.get(position);  
		
		View linha = LayoutInflater.from(contexto).inflate(R.layout.item_musica, null);
		 
//		ImageView imgview = (ImageView) linha.findViewById(R.id.imageView1); 
		TextView txtView = (TextView) linha.findViewById(R.id.textView1);  
		
//		ImageView imgview2 = (ImageView) linha.findViewById(R.id.imageView2);
//		ImageView imgview3 = (ImageView) linha.findViewById(R.id.imageView3);
		
//		imgview.setImageResource(musica.getFigura());
		txtView.setText(musica.getName());     
		  
		return linha; 
	}

}
