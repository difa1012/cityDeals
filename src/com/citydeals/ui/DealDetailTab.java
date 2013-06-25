package com.citydeals.ui;



import com.citydeals.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class DealDetailTab extends TabActivity {

	TabHost tabHost;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dealdetail_tab);

		
		// get variable from previous activity
		Bundle extras = getIntent().getExtras();		
		
		tabHost = getTabHost();
		
		// Tab Deal
		TabSpec dealPage = tabHost.newTabSpec("Deal");
		dealPage.setIndicator("Deal");
		Intent dealIntent = new Intent(this, DealDetail.class);
		dealIntent.putExtras(extras);
		dealPage.setContent(dealIntent);
		

		// Tab Shop
		TabSpec shopPage = tabHost.newTabSpec("Shop");
		shopPage.setIndicator("Shop");
		Intent shopIntent = new Intent(this, ShopPage.class);
		shopIntent.putExtras(extras);
		shopPage.setContent(shopIntent);
		
		// Tab Comment
		TabSpec commentPage = tabHost.newTabSpec("Kommentare");
		commentPage.setIndicator("Meinung");
		Intent commentIntent = new Intent(this, Comments.class);
		commentIntent.putExtras(extras);
		commentPage.setContent(commentIntent);
		
		
		tabHost.addTab(dealPage);
		tabHost.addTab(shopPage);
		tabHost.addTab(commentPage);
		
	}

	
	
}
