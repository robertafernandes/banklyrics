package com.example.lyricsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewLyricsActivity extends Activity {

	private TextView edName;
	private TextView edLyrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_lyrics);

		// Total value
		edName = (TextView) findViewById(R.id.textView1);
		edLyrics = (TextView) findViewById(R.id.textView2);
 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");

		edName.setText(nome);
		edLyrics.setText(letra);

	}

	public void voltar(View view) {
		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

}
