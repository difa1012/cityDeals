package com.citydeals;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	@SerializedName("comment_id")
	private int id;	
	private Deal deal;	
	private User user;	
	private String comment;
	private int rating;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Deal getDeal() {
		return deal;
	}
	public void setDeal(Deal deal) {
		this.deal = deal;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}	
}
