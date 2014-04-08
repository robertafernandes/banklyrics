package com.example.lyricsapplication;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

		View linha = LayoutInflater.from(contexto).inflate(
				R.layout.item_spinner_artista, null);

		TextView txtView = (TextView) linha.findViewById(R.id.textName);

		String image = artista.getImage();
		if (image != null && !image.isEmpty()) {
			ImageView visualizar = (ImageView) linha 
					.findViewById(R.id.imageView1);
			Bitmap bmp = BitmapFactory.decodeFile(image); 
			visualizar.setImageBitmap( getResizedBitmap(bmp, 50, 50));  
		}

		txtView.setText(artista.getName());

		return linha; 
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		 
		int width = bm.getWidth();
		int height = bm.getHeight();
		 
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		 
		// create a matrix for the manipulation		 
		Matrix matrix = new Matrix();

		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		 
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		 
		return resizedBitmap;  
		 
		}


}
