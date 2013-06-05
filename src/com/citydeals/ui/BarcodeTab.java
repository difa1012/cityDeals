package com.citydeals.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.citydeals.R;

@SuppressWarnings("deprecation")
public class BarcodeTab extends TabActivity {

	TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.barcode_tab);

		// get variable from previous activity
		Bundle extras = getIntent().getExtras();

		tabHost = getTabHost();

		// Tab QR
		TabSpec qrPage = tabHost.newTabSpec("QR");
		qrPage.setIndicator("QR");
		Intent qrIntent = new Intent(this, BarcodeQR.class);
		qrIntent.putExtras(extras);
		qrPage.setContent(qrIntent);

		// Tab EAN
		TabSpec eanPage = tabHost.newTabSpec("Barcode");
		eanPage.setIndicator("Barcode");
		Intent eanIntent = new Intent(this, BarcodeEAN.class);
		eanIntent.putExtras(extras);
		eanPage.setContent(eanIntent);

		tabHost.addTab(qrPage);
		tabHost.addTab(eanPage);
	}
}
