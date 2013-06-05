package com.citydeals.ui;

import java.io.IOException;

import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.Shop;
import com.citydeals.util.ImageDownloadHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class BarcodeQR extends Activity {

	private static Handler handler;
	private static Bitmap downloadQR;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.barcode_qr_page);
		final ImageView imageViewQR = (ImageView) findViewById(R.id.imageViewBarcode);


		Bundle extras = getIntent().getExtras();
		Deal d = (Deal) extras.getSerializable("deal");

		Shop s = d.getShop();

		String discount = d.getDiscount().replace("%", "pct.");
		discount = discount.replace("€", "EUR");
		discount = discount.replace("$", "DOLLAR");
		discount = discount.replaceAll("\\s", "");
		String qr = s.getName().replaceAll("\\s", "") + "_" + discount;

		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(downloadQR != null){
				imageViewQR.setImageBitmap(downloadQR);
				}
			}

		};
		loadQRCode(qr);
	}
	
	
	private void loadQRCode(final String content) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					String url = "http://chart.apis.google.com/chart?cht=qr&chs=300x300&chl="
							+ content;
					downloadQR = ImageDownloadHelper.downloadBitmap(url);
					// Updates the user interface
					handler.sendEmptyMessage(0);
				} catch (IOException e) {

				}

			};
		};
		thread.start();
	}
	
}
