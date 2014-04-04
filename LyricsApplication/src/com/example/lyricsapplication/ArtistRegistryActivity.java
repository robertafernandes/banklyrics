package com.example.lyricsapplication;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lyricsapplication.bd.DataBase;
import com.example.lyricsapplication.bd.MusicaDataBase;
import com.example.lyricsapplication.entity.Musica;

public class ArtistRegistryActivity extends Activity implements OnClickListener {

	private DataBase<Musica> database;
	private Button bImage;
	private Button bCancel;
	private Button bSave;
	private EditText edName;
	
	private ImageView imViewArtist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_artist_registry);

		database = MusicaDataBase.getInstance(this);

		bImage = (Button) findViewById(R.id.bImage);
		bImage.setOnClickListener(this);
		
		bSave = (Button) findViewById(R.id.bSave);
		bSave.setOnClickListener(this);

		bCancel = (Button) findViewById(R.id.bCancel);
		bCancel.setOnClickListener(this);

		edName = (EditText) findViewById(R.id.edName);
		
		imViewArtist = (ImageView) findViewById(R.id.imViewArtist);
 
	}

	@Override
	public void onClick(View v) {
		if (v == bImage) {
			callGallery();
		} else if (v == bSave){
			if ("".equals(edName.getText().toString()) ) {
				Toast.makeText(this, "Você precisa especificar um nome e valor!", Toast.LENGTH_SHORT).show(); 
				
			} else {
				String name = edName.getText().toString().trim();
				//String letra = edLyrics.getText().toString().trim(); 
				//database.insert(new Musica(name, letra));  
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

	
	public void callGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 0);
		intent.putExtra("aspectY", 0);
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
//		startActivityForResult(
//		Intent.createChooser(intent, "Complete action using"),
//		PICK_FROM_GALLERY);

		startActivityForResult(intent, 1);
	}
	
	
//	public void callGallery() {
//		Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
//		getParent().startActivityForResult(intent, 1);
//	}
	//
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("onActivityResult", "resultCode "+resultCode);
		if (resultCode != RESULT_OK)
			return;

		Bundle extras2 = data.getExtras();

		if (extras2 != null) {
			Bitmap yourImage = extras2.getParcelable("data");
			// convert bitmap to byte
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte imageInByte[] = stream.toByteArray();
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
			imViewArtist.setImageBitmap(bitmap);
			
			Log.e("output before conversion", imageInByte.toString());
			// Inserting Contacts
			Log.d("Insert: ", "Inserting ..");
//			db.addContact(new Contact("Android", imageInByte));
//			Intent i = new Intent(SQLiteDemoActivity.this,
//					SQLiteDemoActivity.class);
//			startActivity(i);
			
//			finish();
		}
	}
		

}
