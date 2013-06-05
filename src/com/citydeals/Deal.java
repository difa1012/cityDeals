package com.citydeals;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

public class Deal implements Comparable<Deal>, Serializable {
	private static final long serialVersionUID = 1L;

	@SerializedName("deal_id")
	private int id;
	private String headline;
	private String description;
	private String discount;
	private Date validFrom;
	@SerializedName("validTo")
	private Date validUntil;
	private Bitmap image;
	private Category category;
	private Shop shop;
	private boolean isTopDeal;
	private boolean hasPicture;
	private int availability;
	private int claimeddeals;

	private Float distance;

	public Bitmap base64ToBitmap(String encodedImage) {
		try {
			byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
			return BitmapFactory.decodeByteArray(decodedString, 0,
					decodedString.length);
		} catch (Exception ex) {

			return null;

		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public boolean isTopDeal() {
		return isTopDeal;
	}

	public void setTopDeal(boolean isTopDeal) {
		this.isTopDeal = isTopDeal;
	}

	public boolean isHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}

	public int getClaimeddeals() {
		return claimeddeals;
	}

	public void setClaimeddeals(int claimeddeals) {
		this.claimeddeals = claimeddeals;
	}

	@Override
	public int compareTo(Deal d) {
		return this.getDistance().compareTo(d.getDistance());
	}
}
