package com.citydeals.ui;

import com.citydeals.CityDealsApplication;
import com.citydeals.Comment;
import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.R.layout;
import com.citydeals.R.menu;
import com.citydeals.User;
import com.citydeals.service.CommentService;
import com.citydeals.service.UserService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class New_comment extends Activity {
	protected Deal deal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_comment);
			
		Bundle extras = getIntent().getExtras();
		deal = (Deal) extras.getSerializable("deal");
		
//		EditText comm = (EditText) findViewById(R.id.NEWcomment);
//		RatingBar rating = (RatingBar) findViewById(R.id.NEWrating);
		Button send = (Button) findViewById(R.id.send);
		
		send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		EditText comm = (EditText) findViewById(R.id.NEWcomment);
        		RatingBar rating = (RatingBar) findViewById(R.id.NEWrating);
        		comm.setImeOptions(EditorInfo.IME_ACTION_DONE);
        		 
            	CityDealsApplication application = (CityDealsApplication) getApplication();
            	String mail = application.getUser();
            	//String pw = application.getPassword();
            	User user = UserService.getUserByEmail(mail);
            	
        		Comment comment = new Comment();
        		comment.setComment(comm.getText().toString());
        		comment.setRating(Math.round(rating.getRating()));
        		comment.setDeal(deal);
        		comment.setUser(user);
        		
        		CommentService.setComment(comment);  
        		finish();        		
            }});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_comment, menu);
		return true;
	}

}
