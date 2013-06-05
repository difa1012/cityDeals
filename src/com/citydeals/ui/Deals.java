package com.citydeals.ui;

import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.citydeals.CityDealsApplication;
import com.citydeals.Deal;
import com.citydeals.Shop;
import com.citydeals.service.DealService;
import com.citydeals.service.LocationService;

public class Deals extends ListActivity {

	private ArrayList<Deal> deals = null;
	private CityDealsApplication application = null;

	private void init() {
		application = (CityDealsApplication) this.getApplication();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();

		
		try{
		deals = DealService.getDealsByCity(application.getCity());
		}
		catch(RuntimeException ex){
			Toast.makeText(this, "Feler beim abrufen der Deals",
					Toast.LENGTH_LONG).show();	
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Intent i = new Intent(this, StartPage.class);
			startActivity(i);
			
		}
		LocationService ls = new LocationService(this);

		for (Deal d : deals) {
			Shop s = d.getShop();
			Location shopLocation = new Location("myProvider");
			shopLocation.setLatitude(s.getLat());
			shopLocation.setLongitude(s.getLog());

			d.setDistance(ls.calculateDistance(shopLocation));
		}

		Collections.sort(deals);

		setListAdapter(new DealsArrayAdapter(this, deals));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent i = new Intent(this, DealDetailTab.class);
		i.putExtra("deal", deals.get(position));
		startActivity(i);
	}
}
