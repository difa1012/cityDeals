package com.citydeals.ui;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.citydeals.CityDealsApplication;
import com.citydeals.R;
import com.citydeals.data.DBService;
import com.citydeals.service.LocationService;

public class StartPage extends Activity {

	private CityDealsApplication application = null;

	private void init() {
		application = (CityDealsApplication) this.getApplication();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		extracted(savedInstanceState);
		setContentView(R.layout.start_page);
		this.init();
		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, cities);

		textViewCity.setAdapter(adapter);
		
		// enable DropDown if manual user input
		textViewCity.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				textViewCity.setThreshold(1);
				return false;
			}
		});

		// read city from phone DB
		if (((CityDealsApplication) this.getApplication()).getCity() == null) {
			// get stored city from phone DB
			DBService dbs = new DBService(this);
			putCategoriesInMemory(dbs.getCategories());
			String city = dbs.getStoredCity();
			dbs.closeDB();

			if (city != null && !city.equals("")) {
				textViewCity.setText(city);
				// disable DropDown
				textViewCity.setThreshold(1000);
				application.setCity(city);
			}
		}
		// read credentials from phoneDB
		if (application.getUser() == null) {
			DBService dbs = new DBService(this);
			String[] cred = dbs.getStoredCredentials();
			dbs.closeDB();

			if (cred[0] != null && cred[0] != "" && cred[1] != null && cred[1] != "") {
				application.setUser(cred[0]);
				application.setPassword(cred[1]);
			}
		}
	}

	private void extracted(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		String s = application.getCity();
		if (s != null && s != "") {
			textViewCity.setText(s);
			textViewCity.setThreshold(1000);
		}
		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public void settings(final View view) {
		startActivity(new Intent(this, SettingsPage.class));
	}

	public void deals(final View view) {
		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		String city = textViewCity.getText().toString();	

		
		if (city != null && !city.equals("")) {
			application.setCity(city);			
			Intent i = new Intent(this, Deals.class);
			startActivity(i);			
		}
		else{
			Toast.makeText(this, "Bitte eine Stadt eingeben",
					Toast.LENGTH_LONG).show();			
		}


	}

	public void location(final View view) {
		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

		LocationService ls = new LocationService(this);
		Address address = ls.getAdress(this);
		
		if (address != null && address.getLocality() != null) {
			String city = address.getLocality();

			if (city == null) {
				// because of a bug in Android GeoCoder address.getLocality()
				// often returns null
				// but in address.getAddressLine(1) the correct value appears
				String[] arr = address.getAddressLine(1).split(" ");
				if (arr.length > 1) {
					city = arr[1];
				}
			}
			textViewCity.setText(city);
			textViewCity.setThreshold(1000);
			application.setCity(city);

			// Position nur per Menü settings oder immer speichern ??
			// DBService dbs = new DBService(this);
			// dbs.storeCity(city);
			// dbs.closeDB();

		} else {
			Toast.makeText(this, "Adresse konnte nicht ermittelt werden",
					Toast.LENGTH_LONG).show();
		}

 	}

	private void putCategoriesInMemory(Map<String, Boolean> categories) {

		for (Map.Entry<String, Boolean> e : categories.entrySet()) {
			if (e.getKey().equals("categoryFoodDrink")) {
				application.setShowCategoryFoodDrink(e.getValue());
			} else if (e.getKey().equals("categoryService")) {
				application.setShowCategoryService(e.getValue());
			} else if (e.getKey().equals("categoryShopping")) {
				application.setShowCategoryShopping(e.getValue());
			} else if (e.getKey().equals("categoryCarTransport")) {
				application.setShowCategoryCarTransport(e.getValue());
			} else if (e.getKey().equals("categoryRecreation")) {
				application.setShowCategoryRecreation(e.getValue());
			}
		}
	}

	public final static String[] cities = { "Aachen", "Augsburg",
			"Bergisch Gladbach", "Berlin", "Bielefeld", "Bochum", "Bonn",
			"Bottrop", "Braunschweig", "Bremen", "Bremerhaven ", "Celle", "Chemnitz",
			"Cottbus", "Darmstadt", "Dortmund", "Dresden", "Duisburg",
			"Düsseldorf", "Erfurt", "Erlangen", "Essen", "Frankfurt am Main",
			"Freiburg im Breisgau", "Fürth", "Gelsenkirchen", "Göttingen",
			"Hagen", "Halle (Saale)", "Hamburg", "Hamm", "Hannover",
			"Heidelberg", "Heilbronn", "Herne", "Hildesheim", "Ingolstadt",
			"Jena", "Karlsruhe", "Kassel", "Kiel", "Koblenz", "Krefeld",
			"Köln", "Leipzig", "Leverkusen", "Ludwigshafen am Rhein", "Lübeck",
			"Magdeburg", "Mainz", "Mannheim", "Moers", "Mönchengladbach",
			"Mülheim an der Ruhr", "München", "Münster", "Neuss", "Nürnberg",
			"Oberhausen", "Offenbach am Main", "Oldenburg", "Osnabrück",
			"Paderborn", "Pforzheim", "Potsdam", "Recklinghausen",
			"Regensburg", "Remscheid", "Reutlingen", "Rostock", "Saarbrücken",
			"Salzgitter ", "Siegen", "Solingen", "Stuttgart", "Trier", "Ulm",
			"Wiesbaden", "Wolfsburg", "Wuppertal", "Würzburg" };
}
