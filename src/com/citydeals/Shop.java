package com.citydeals;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;
	@SerializedName("shop_id")
	private int id;
	private String name;
	private String street;
	private String postalcode;
	private String city;
	@SerializedName("latitude")
	private Double lat;
	@SerializedName("longitude")
	private Double log;
	private String telephone;
	private String description;
	private String website;
	private String openinghours;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLog() {
		return log;
	}
	public void setLog(Double log) {
		this.log = log;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getOpeninghours() {
		return openinghours;
	}
	public void setOpeninghours(String openinghours) {
		this.openinghours = openinghours;
	}

	
}
