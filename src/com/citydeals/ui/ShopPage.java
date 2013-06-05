package com.citydeals.ui;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.Shop;
import com.citydeals.service.LocationService;
import com.citydeals.util.ImageDownloadHelper;
import com.citydeals.util.StringUtil;

public class ShopPage extends Activity {
	private static Handler handler;
	private static Bitmap downloadMap;
	private Deal d;
	private Shop s;
	
	TextView textViewShop;
	TextView textViewStreet;
	TextView textViewPostalCode;
	TextView textViewCity;
	TextView textViewTel;
	TextView textViewWeb;
	TextView textViewOpeningHours;
	TextView textViewDistance;

	@SuppressWarnings("unused")
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_page);
		textViewShop = (TextView) findViewById(R.id.textViewShop);
		textViewStreet = (TextView) findViewById(R.id.textViewStreet);
		textViewPostalCode = (TextView) findViewById(R.id.TextViewPostalCode);
		textViewCity = (TextView) findViewById(R.id.TextViewCity);
		textViewTel = (TextView) findViewById(R.id.textViewTelephone);
		textViewWeb = (TextView) findViewById(R.id.textViewWebSite);
		textViewOpeningHours = (TextView) findViewById(R.id.textViewOpeningHours);
		textViewDistance = (TextView) findViewById(R.id.textViewDistance);
		
		
		final ImageView imageViewMap = (ImageView) findViewById(R.id.imageViewMap);

		Bundle extras = getIntent().getExtras();
		d = (Deal) extras.getSerializable("deal");
		s = d.getShop();

		loadStaticMap(s.getLat().toString(), s.getLog().toString());

		Float distance = d.getDistance();
		textViewDistance.setText(LocationService.formattedDistance(distance));
		
		textViewShop.setText(s.getName());
		textViewStreet.setText(s.getStreet());
		textViewPostalCode.setText(s.getPostalcode());
		textViewCity.setText(s.getCity());
		
		if(s.getTelephone() != null){
			String tel = s.getTelephone();
			SpannableString spanTel = new SpannableString(tel);
			spanTel.setSpan(new UnderlineSpan(), 0, tel.length() , 0); 
			spanTel.setSpan(new ForegroundColorSpan(Color.BLUE), 0, tel.length() , 0); 
			textViewTel.setText(spanTel);
			textViewTel.setVisibility(View.VISIBLE);	
		}
		else{
			textViewTel.setVisibility(View.GONE);
		}
		
		if(s.getWebsite() != null){
			textViewWeb.setText(s.getWebsite());
			textViewWeb.setVisibility(View.VISIBLE);			
		}
		else{
			textViewWeb.setVisibility(View.GONE);
		}
		
		if(s.getOpeninghours() != null){
			
			String[] splittetLine = StringUtil.splitLine(s.getOpeninghours());
			String hours = "";
			for(int i=0; i < splittetLine.length; i++){
				hours = hours + splittetLine[i] + "\n";
			}
			
			textViewOpeningHours.setText(hours);
			textViewOpeningHours.setVisibility(View.VISIBLE);			
		}
		

		String discount = d.getDiscount().replace("%", "pct.");
		discount = discount.replace("€", "EUR");
		discount = discount.replace("$", "DOLLAR");
		discount = discount.replaceAll("\\s", "");
		String qr = s.getName().replaceAll("\\s", "") + "_" + discount;

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (downloadMap != null) {
					imageViewMap.setImageBitmap(downloadMap);
				}

			}

		};
		
	}

	private void loadStaticMap(final String lat, final String lng) {

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					String url = "http://maps.google.com/maps/api/staticmap?center="
							+ lat
							+ ","
							+ lng
							+ "&zoom=15&size=440x220"
							+ "&markers=color:blue%7C"
							+ lat
							+ ","
							+ lng
							+ "&sensor=false";
					downloadMap = ImageDownloadHelper.downloadBitmap(url);
					// Updates the user interface
					handler.sendEmptyMessage(0);
				} catch (IOException e) {

				}

			};
		};
		thread.start();
	}

	public void showBarcode(final View view) {
		Intent i = new Intent(this, BarcodeTab.class);
		d.setImage(null);
		i.putExtra("deal", d);
		startActivity(i);
	}

	public void showMap(final View view) {
		double lat = s.getLat();
		double lng = s.getLog();
		Intent i = new Intent(this, MapTest.class);
		i.putExtra("dest_lat", lat);
		i.putExtra("dest_lng", lng);
		startActivity(i);
	}
	
	public void call (final View view){
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:+"+textViewTel.getText().toString().replaceAll("\\s", "").trim()));
		startActivity(callIntent);
	}
		
}
