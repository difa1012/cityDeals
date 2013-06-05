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

public class BarcodeEAN extends Activity {

	private static Handler handler;
	private static Bitmap downloadQR;

	@SuppressWarnings("unused")
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.barcode_ean_page);
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
				if (downloadQR != null) {
					imageViewQR.setImageBitmap(downloadQR);
				}
			}

		};
		loadEANCode("123456789112");
	}

	private void loadEANCode(final String content) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					String url = "http://generator.onbarcode.com/linear.aspx?TYPE=15&DATA="
							+ content
							+ "&UOM=0&X=2&Y=200&LEFT-MARGIN=0&RIGHT-MARGIN=0&TOP-MARGIN=0&BOTTOM-MARGIN=0&RESOLUTION=72&ROTATE=0&BARCODE-WIDTH=0&BARCODE-HEIGHT=0&SHOW-TEXT=true&TEXT-FONT=Arial%7c9%7cRegular&TextMargin=6&FORMAT=gif&SUP-DATA=&SUP-HEIGHT=0.8&SUP-SPACE=15";
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
