package com.example.lyricsapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LyricsMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lyrics_main);
	}

	public void onClick(View view) {

		Intent it;

		switch (view.getId()) {
		case R.id.bRegisterArtist:
			it = new Intent(this, ArtistsListActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(it);
			break;
			
		case R.id.bRegisterLyric:
			it = new Intent(this, LyricsRegistryActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(it);
			break;

		case R.id.bFindLyric:
			it = new Intent(this, LyricsListActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(it);
			break;

		}

	}

}
