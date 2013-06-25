package com.citydeals.ui;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.citydeals.CityDealsApplication;
import com.citydeals.Deal;
import com.citydeals.R;
import com.citydeals.Shop;
import com.citydeals.service.DealService;
import com.citydeals.service.LocationService;
import com.citydeals.util.StringUtil;

public class Deals extends ListActivity {

	private ArrayList<Deal> deals = null;
	private CityDealsApplication application = null;
	private Deal topdeal = null;
	final Context context = this;

	private void init() {
		application = (CityDealsApplication) this.getApplication();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();

		// TODO absturz bei keinen deals
		try {
			deals = DealService.getDealsByCity(application.getCity());
		} catch (Exception ex) {
			Toast.makeText(this, "Keine Deals für "+ application.getCity() + " gefunden",
					Toast.LENGTH_LONG).show();
			Intent i = new Intent(this, StartPage.class);
			startActivity(i);
			return;
		}

		LocationService ls = new LocationService(this);

		ArrayList<Deal> noShow = new ArrayList<Deal>();

		for (Deal d : deals) {

			if (!showDeal(d)) {
				noShow.add(d);
			} else {
				Shop s = d.getShop();
				Location shopLocation = new Location("myProvider");
				shopLocation.setLatitude(s.getLat());
				shopLocation.setLongitude(s.getLog());

				d.setDistance(ls.calculateDistance(shopLocation));
			}
		}
		// remove unwanted deals
		for(Deal d : noShow)
		{
			deals.remove(d);
		}
		
		// TOPDEAL

		topdeal = deals.get(1);
		if (topdeal != null) {

			final Dialog dialog = new Dialog(context);
			dialog.setContentView(R.layout.topdeal_dialog);
			dialog.setTitle("Topdeal für " + application.getCity());

			// opaque background
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.alpha = 0.8f;
			dialog.getWindow().setAttributes(lp);

			// set the custom dialog components - text, image and button
			ImageView image = (ImageView) dialog.findViewById(R.id.imageViewTD);
			setImageByCategory(topdeal, image);
			TextView head = (TextView) dialog
					.findViewById(R.id.textViewTDHeadline);
			TextView subhead = (TextView) dialog
					.findViewById(R.id.textViewTDSubHeadline);
			String[] splitteHeadline = StringUtil.splitLine(topdeal
					.getHeadline());
			head.setText(splitteHeadline[0]);
			subhead.setText(splitteHeadline[1]);

			ImageButton dialogButton = (ImageButton) dialog
					.findViewById(R.id.imageButtonTD);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});

			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					dialog.dismiss();
					Intent intent = new Intent(Deals.this, DealDetailTab.class);
					Deal d = topdeal;
					d.setImage(null);
					intent.putExtra("deal", d);
					startActivity(intent);

				}
			});

			dialog.show();
		}

		// DEALS NACH ENTFERNUNG SORTIEREN
		Collections.sort(deals);

		setListAdapter(new DealsArrayAdapter(this, deals));
	}

	private void setImageByCategory(Deal d, ImageView i) {

		// get the image of the deal
		String imgBase64 = DealService.getImageByDeal(d.getId());
		if (imgBase64 != null) {
			Bitmap img = d.base64ToBitmap(imgBase64);
			d.setImage(img);
		}
		String category = d.getCategory().getCategory();

		if (d.getImage() != null) {
			i.setImageBitmap(d.getImage());
		} else if (category.equals("FoodDrink")) {
			i.setImageResource(R.drawable.food);
		} else if (category.equals("Shopping")) {

			i.setImageResource(R.drawable.shopping);
		} else if (category.equals("CarTransport")) {

			i.setImageResource(R.drawable.transport);
		} else if (category.equals("Service")) {

			i.setImageResource(R.drawable.service);
		} else if (category.equals("Recreation")) {

			i.setImageResource(R.drawable.recreation);
		}
	}

	private boolean showDeal(Deal d) {
		boolean show = true;

		String category = d.getCategory().getCategory();
		if (category.equals("FoodDrink")) {
			if (!application.showCategoryFoodDrink())
				show = false;
		} else if (category.equals("Shopping")) {
			if (!application.showCategoryShopping())
				show = false;
		} else if (category.equals("CarTransport")) {
			if (!application.showCategoryCarTransport())
				show = false;
		} else if (category.equals("Service")) {
			if (!application.showCategoryService())
				show = false;
		} else if (category.equals("Recreation")) {
			if (!application.showCategoryRecreation())
				show = false;
		}
		return show;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Intent i = new Intent(this, DealDetailTab.class);
		Deal d = deals.get(position);
		d.setImage(null);
		i.putExtra("deal", d);
		startActivity(i);
	}
}
