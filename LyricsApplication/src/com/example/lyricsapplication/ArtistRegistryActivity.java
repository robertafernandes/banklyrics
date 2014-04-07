package com.example.lyricsapplication;

import java.io.ByteArrayOutputStream;

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
	private Button bImage;
	private Button bCancel;
	private Button bSave;
	private EditText edName;
	
	private ImageView imViewArtist;
	
	private byte picture[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_registry);

		database = ArtistaDataBase.getInstance(this);

		bImage = (Button) findViewById(R.id.bImage);
		bImage.setOnClickListener(this);
		
		bSave = (Button) findViewById(R.id.bSave);
		bSave.setOnClickListener(this);

		bCancel = (Button) findViewById(R.id.bCancel);
		bCancel.setOnClickListener(this);

		edName = (EditText) findViewById(R.id.edName);
		
		imViewArtist = (ImageView) findViewById(R.id.imViewArtist);
 
	}
	
	private int RESULT_LOAD_IMAGE = 7;

	@Override
	public void onClick(View v) {
		if (v == bImage) {			
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);			
			startActivityForResult(i, RESULT_LOAD_IMAGE);			
		} else if (v == bSave){
			if ("".equals(edName.getText().toString()) ) {
				Toast.makeText(this, "Você precisa especificar um nome!", Toast.LENGTH_SHORT).show();
			} else {
				String name = edName.getText().toString().trim();
				database.insert(new Artista(name, picture));  
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

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            //byte picture[] = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bmp = BitmapFactory.decodeFile(picturePath);                      
            
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            picture = stream.toByteArray();
                          
            //FIXME desnecessario apenas para teste
            Bitmap theImage = BitmapFactory.decodeByteArray(picture, 0, picture.length); 
            
            imViewArtist.setImageBitmap(theImage);
        }
    }		

}
