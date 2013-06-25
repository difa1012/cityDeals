package com.citydeals.ui;

import java.util.ArrayList;
import java.util.List;

import com.citydeals.Comment;
import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.service.LocationService;
import com.citydeals.util.StringUtil;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentArrayAdapter extends ArrayAdapter<Comment> {
	private final ArrayList<Comment> comments;
	private final Context context;
	
	
	public CommentArrayAdapter(Context context, ArrayList<Comment> comments) {
		super(context, R.layout.comment_list_item, comments);
		this.context = context;
		this.comments = comments;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater
				.inflate(R.layout.comment_list_item, parent, false);
		CommentHolder holder = new CommentHolder();
		holder.name = (TextView) rowView.findViewById(R.id.comment_name);
		holder.value = (TextView) rowView.findViewById(R.id.comment_value);
		holder.rating = (RatingBar) rowView.findViewById(R.id.comment_rating);
		
		rowView.setTag(holder);

		Comment comment = comments.get(position);

		holder.name.setText(comment.getUser().geteMail());
		holder.value.setText(comment.getComment());
		holder.rating.setRating(comment.getRating());

		return rowView;
	}

    static class CommentHolder
    {
    	TextView name;
    	TextView value;
    	RatingBar rating;
    }
}
