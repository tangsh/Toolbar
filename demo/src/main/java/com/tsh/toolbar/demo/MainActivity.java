package com.tsh.toolbar.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.tsh.toolbar.Toolbar;

public class MainActivity extends AppCompatActivity {

	Toolbar toolbar3;
	Toolbar toolbar4;
	Toolbar toolbar5;
	Toolbar toolbar6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
//
		setSupportActionBar(toolbar3);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
//
		toolbar4 = (Toolbar) findViewById(R.id.toolbar4);
		setSupportActionBar(toolbar4);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
//
		toolbar5 = (Toolbar) findViewById(R.id.toolbar5);
		setSupportActionBar(toolbar5);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//
		toolbar6 = (Toolbar) findViewById(R.id.toolbar6);
		setSupportActionBar(toolbar6);
		actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
}
