package com.example.lyricsapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lyricsapplication.bd.ArtistaDataBase;
import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.entity.Artista;

public class ArtistRegistryActivity extends Activity implements OnClickListener {

	private DataBase<Artista> database;
	private Button bCancel;
	private Button bSave;
	private ImageView imViewArtist;
	private EditText edName;
		
	private String imageArtist;
	
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_registry);

		database = ArtistaDataBase.getInstance(this);
	
		bSave = (Button) findViewById(R.id.bSave);
		bSave.setOnClickListener(this);

		bCancel = (Button) findViewById(R.id.bCancel);
		bCancel.setOnClickListener(this);

		edName = (EditText) findViewById(R.id.edName);
		 
		imViewArtist = (ImageView) findViewById(R.id.imViewArtist);
		imViewArtist.setOnClickListener(this);
		
		id = getIntent().getIntExtra("id", 0); 
		String nome = getIntent().getStringExtra("nome");
		imageArtist = getIntent().getStringExtra("imagem");
		
		edName.setText(nome);
        Bitmap bmp = BitmapFactory.decodeFile(imageArtist);
        imViewArtist.setImageBitmap(bmp); 
	}
	
	private int RESULT_LOAD_IMAGE = 7;

	@Override
	public void onClick(View v) {
		if (v == imViewArtist) {			
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);			
			startActivityForResult(i, RESULT_LOAD_IMAGE);			
		} else if (v == bSave){
			if ("".equals(edName.getText().toString()) ) {
				Toast.makeText(this, "Você precisa especificar um nome!", Toast.LENGTH_SHORT).show();
			} else {
				String name = edName.getText().toString().trim();
				if (id == 0) {
					database.insert(new Artista(name, imageArtist));  
					Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
				} else {
					Artista artista = new Artista(id, name, imageArtist);
					database.update(artista);
					Toast.makeText(this, "Alterado com sucesso!", Toast.LENGTH_SHORT).show();
				}				
				startNewListIntent(); 
			}
		} else {
			startNewListIntent();
		} 
	}
	
	private void startNewListIntent() {
		this.finish();
		Intent intent = new Intent(this, ArtistsListActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    	startActivity(intent);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imageArtist = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bmp = BitmapFactory.decodeFile(imageArtist);
            imViewArtist.setImageBitmap(bmp);
        }
    }		

}
