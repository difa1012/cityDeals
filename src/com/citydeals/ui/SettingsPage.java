package com.citydeals.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.citydeals.CityDealsApplication;
import com.citydeals.R;
import com.citydeals.User;
import com.citydeals.data.DBService;
import com.citydeals.service.UserService;

public class SettingsPage extends Activity {
	
	private CityDealsApplication application = null;	
	
	private CheckBox checkBoxFoodDrink;
	private CheckBox checkBoxService;
	private CheckBox checkBoxShopping;
	private CheckBox checkBoxCarTransport;
	private CheckBox CheckBoxRecreation;
	
	
	private void init(){
		application = (CityDealsApplication) this.getApplication();		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.settings_page);
			
		this.init();
		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, StartPage.cities);
		EditText editUser = (EditText) findViewById(R.id.editTextUser);
		EditText editPassword = (EditText) findViewById(R.id.editTextPassword);
		
		textViewCity.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editUser.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
		textViewCity.setAdapter(adapter);
		
		textViewCity.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	textViewCity.setThreshold(1);
         	   return false;
            }
		});
		
		// get City from memory or phone db
		String s = ((CityDealsApplication) this.getApplication()).getCity();		
		if (s != null && s != "")
		{
			textViewCity.setText(s);
			textViewCity.setThreshold(1000);
		}
		

		
		String user = ((CityDealsApplication) this.getApplication()).getUser();
		String password = ((CityDealsApplication) this.getApplication()).getPassword();	
		
		//manage button visibility
		Button login = (Button) findViewById(R.id.login);
		Button logout = (Button) findViewById(R.id.logout);
		Button regist = (Button) findViewById(R.id.regist);
		
		if(!user.isEmpty() && !password.isEmpty()) {
			login.setVisibility(Button.INVISIBLE);
			logout.setVisibility(Button.VISIBLE);
			regist.setVisibility(Button.INVISIBLE);
			editUser.setText(user);
			editPassword.setText(password);
		} else {
			login.setVisibility(Button.VISIBLE);
			logout.setVisibility(Button.INVISIBLE);
			regist.setVisibility(Button.VISIBLE);
			editUser.setText("");
			editPassword.setText("");
		}
		
		login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		EditText editUser = (EditText) findViewById(R.id.editTextUser);
        		EditText editPassword = (EditText) findViewById(R.id.editTextPassword);
        		Button login = (Button) findViewById(R.id.login);
        		Button logout = (Button) findViewById(R.id.logout);
        		Button regist = (Button) findViewById(R.id.regist);
            	
        		String user = editUser.getText().toString();
        		String password = editPassword.getText().toString();
        		
				boolean success = UserService.checkUser(user, password);
        		
        		if (success) {
        			Toast.makeText(getApplicationContext(), "Login erfolgreich", Toast.LENGTH_LONG).show();
        			application.setUser(user);
        			application.setPassword(password);
        			DBService dbs = new DBService(getApplicationContext());
        			dbs.storeCredentials(user, password);     
        			login.setVisibility(Button.INVISIBLE);
        			logout.setVisibility(Button.VISIBLE);
        			regist.setVisibility(Button.INVISIBLE);        			
        		} else {
        			Toast.makeText(getApplicationContext(), "Login fehlgeschlagen", Toast.LENGTH_LONG).show(); 
        			login.setVisibility(Button.VISIBLE);
        			logout.setVisibility(Button.INVISIBLE);
        			regist.setVisibility(Button.VISIBLE);
        		}        		
            }
        });
		
		
		logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		EditText editUser = (EditText) findViewById(R.id.editTextUser);
        		EditText editPassword = (EditText) findViewById(R.id.editTextPassword);
        		Button login = (Button) findViewById(R.id.login);
        		Button logout = (Button) findViewById(R.id.logout);
        		Button regist = (Button) findViewById(R.id.regist);
            	       			
        		Toast.makeText(getApplicationContext(), "Logout erfolgreich", Toast.LENGTH_LONG).show();
        		application.setUser("");
        		application.setPassword("");
        		DBService dbs = new DBService(getApplicationContext());
        		dbs.storeCredentials("", "");
    			editUser.setText("");
    			editPassword.setText("");
    			login.setVisibility(Button.VISIBLE);
    			logout.setVisibility(Button.INVISIBLE);
    			regist.setVisibility(Button.VISIBLE);
            }
        });
		
		
		regist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		EditText editUser = (EditText) findViewById(R.id.editTextUser);
        		EditText editPassword = (EditText) findViewById(R.id.editTextPassword);
        		Button login = (Button) findViewById(R.id.login);
        		Button logout = (Button) findViewById(R.id.logout);
        		Button regist = (Button) findViewById(R.id.regist);
        		
        		String user = editUser.getText().toString();
        		String password = editPassword.getText().toString();
            	       			
        		User newUser = new User();
        		newUser.seteMail(user);
        		newUser.setPassword(password);
        		boolean success = UserService.setUser(newUser);
        		
        		if (success) {
        			Toast.makeText(getApplicationContext(), "Anmeldung erfolgreich", Toast.LENGTH_LONG).show();
        			application.setUser(user);
        			application.setPassword(password);
        			DBService dbs = new DBService(getApplicationContext());
        			dbs.storeCredentials(user, password);     
        			login.setVisibility(Button.INVISIBLE);
        			logout.setVisibility(Button.VISIBLE);
        			regist.setVisibility(Button.INVISIBLE);
        			
        		} else {
        			Toast.makeText(getApplicationContext(), "Anmeldung fehlgeschlagen", Toast.LENGTH_LONG).show(); 
        			login.setVisibility(Button.VISIBLE);
        			logout.setVisibility(Button.INVISIBLE);
        			regist.setVisibility(Button.VISIBLE);
        		}
            }
        });

		
//		if(user != null && user != "" && password != null && password != ""){
//			editUser.setText(user);
//			editPassword.setText(password);
//		}
		
		
		checkBoxFoodDrink = (CheckBox) findViewById(R.id.checkBoxFoodDrink);
		if (application.showCategoryFoodDrink()){
			checkBoxFoodDrink.setChecked(true);
		}
		checkBoxService = (CheckBox) findViewById(R.id.checkBoxService);
		if (application.showCategoryService()){
			checkBoxService.setChecked(true);
		}
		checkBoxShopping = (CheckBox) findViewById(R.id.checkBoxShopping);
		if (application.showCategoryShopping()){
			checkBoxShopping.setChecked(true);
		}
		checkBoxCarTransport = (CheckBox) findViewById(R.id.checkBoxCarTransport);
		if (application.showCategoryCarTransport()){
			checkBoxCarTransport.setChecked(true);
		}
		CheckBoxRecreation = (CheckBox) findViewById(R.id.CheckBoxRecreation);
		if (application.showCategoryRecreation()){
			CheckBoxRecreation.setChecked(true);
		}
		
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		final AutoCompleteTextView textViewCity = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		final EditText editUser = (EditText) findViewById(R.id.editTextUser);
		final EditText editPassword = (EditText) findViewById(R.id.editTextPassword);
		
		DBService dbs = new DBService(this);

		// store City
		String s = textViewCity.getText().toString();		
		if (s != null && s != "")
		{
			// store city in Memory and phone DB
			application.setCity(s);	
			dbs.storeCity(s);			
		}
	
		// store credentials
		
//		String user = editUser.getText().toString();
//		String password = editPassword.getText().toString();	
//		
//		if(user != null && user != "" && password != null && password != ""){
//			application.setUser(user);
//			application.setPassword(password);
//			dbs.storeCredentials(user, password);
//			
//		}
		
		// store checkBox values in Memory and phone DB
		dbs.storeCategory("categoryFoodDrink", checkBoxFoodDrink.isChecked());
		application.setShowCategoryFoodDrink(checkBoxFoodDrink.isChecked());
		dbs.storeCategory("categoryService", checkBoxService.isChecked());
		application.setShowCategoryService(checkBoxService.isChecked());
		dbs.storeCategory("categoryShopping", checkBoxShopping.isChecked());
		application.setShowCategoryShopping(checkBoxShopping.isChecked());
		dbs.storeCategory("categoryCarTransport", checkBoxCarTransport.isChecked());
		application.setShowCategoryCarTransport(checkBoxCarTransport.isChecked());
		dbs.storeCategory("categoryRecreation", CheckBoxRecreation.isChecked());
		application.setShowCategoryRecreation(CheckBoxRecreation.isChecked());

		dbs.closeDB();
		
	}
	
}
