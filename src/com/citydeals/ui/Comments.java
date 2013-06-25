package com.citydeals.ui;

import java.util.ArrayList;

import com.citydeals.Comment;
import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.service.CommentService;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Comments extends ListActivity {
	private ArrayList<Comment> comments = null;
	private Deal deal = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		deal = (Deal) extras.getSerializable("deal");
		int id = deal.getId();	
		
//		try {
//			comments = CommentService.getCommentsByDeal(id);
//		} catch(RuntimeException ex) {
//			String e = ex.getMessage();
//		}
//		
//		if (comments == null) {
//			Toast.makeText(this, "Keine Kommentare vorhanden", Toast.LENGTH_LONG).show();
//			comments = new ArrayList<Comment>();
//		}	
//		
//		CommentArrayAdapter adapter = new CommentArrayAdapter(this, comments); 
//		setListAdapter(adapter);
	}
	
	
	  @Override
	protected void onResume() {
			int id = deal.getId();	
			
			try {
				comments = CommentService.getCommentsByDeal(id);
			} catch(RuntimeException ex) {
				String e = ex.getMessage();
			}
			
			if (comments == null) {
				Toast.makeText(this, "Keine Kommentare vorhanden", Toast.LENGTH_LONG).show();
				comments = new ArrayList<Comment>();
			}	
			
			CommentArrayAdapter adapter = new CommentArrayAdapter(this, comments); 
			setListAdapter(adapter);
			super.onResume();
	}

	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    Comment item = (Comment) getListAdapter().getItem(position);
	    Intent i = new Intent(this, SingleComment.class);
	    i.putExtra("comment", item.getComment());
	    startActivity(i);
	  }	
}
