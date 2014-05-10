package com.example.lyricsapplication;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewLyricsActivity extends Activity implements OnClickListener {

	
	private Button bPlay;
	private Button bPause;
	private Button bHome;
	
	private TextView edName;
	private TextView edLyrics;
	private String songPath;
	
	private boolean somInciado = false; 
	
	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_lyrics);
		
		bPlay = (Button) findViewById(R.id.bPlay);
		bPlay.setOnClickListener(this);
		bPause = (Button) findViewById(R.id.bPause);
		bPause.setOnClickListener(this);
		bHome = (Button) findViewById(R.id.bHome);
		bHome.setOnClickListener(this);

		edName = (TextView) findViewById(R.id.textView1);
		edLyrics = (TextView) findViewById(R.id.textView2);
 
		String nome = getIntent().getStringExtra("nome");
		String letra = getIntent().getStringExtra("letra");
		songPath = getIntent().getStringExtra("songPath");

		edName.setText(nome);
		edLyrics.setText(letra);

	}

	public void voltar() {
		this.stopSong();
		Intent intent = new Intent(this, LyricsListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}
	
	@Override
	public void onClick(View v) {
		if (v == bPlay) {
			this.playSong();
		} else if (v == bPause) {
			this.pauseSong();
		} else {
			this.voltar();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		this.voltar();
		super.onBackPressed();
	}
	
    private void playSong() {     
    	if (songPath == null || songPath.isEmpty()) {
    		Toast.makeText(this, "Edite esta música realizando Upload de Mp3!", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	if (somInciado) {
    		if (!mediaPlayer.isPlaying()) {
    			mediaPlayer.start();
    		}
    	} else {
    		try {
	            mediaPlayer.reset();
	            mediaPlayer.setDataSource(songPath);
	            mediaPlayer.prepare();
	            mediaPlayer.start();
	            somInciado = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
    	}
    }
    
    private void pauseSong() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
    }

    private void stopSong() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
    }
}
