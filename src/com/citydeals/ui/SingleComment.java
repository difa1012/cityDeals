package com.citydeals.ui;

import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.R.layout;
import com.citydeals.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SingleComment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_comment);
		
		Bundle extras = getIntent().getExtras();
		String comment = extras.getString("comment");
		
		TextView c = (TextView) findViewById(R.id.singleComment);
		c.setText(comment);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_comment, menu);
		return true;
	}

}
