package com.citydeals;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	@SerializedName("category_id")
	private int id;
	@SerializedName("description")
	private String  category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

}
