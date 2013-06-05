package com.citydeals.data;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.citydeals.CityDealsApplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings({ "static-access", "unused" })
public class DBService {

	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	private static final String SELECT_CITY = "SELECT _id, city FROM city";

	public DBService(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getReadableDatabase();
	}

	public void closeDB() {
		db.close();
	}

	public String getStoredCity() {

		Cursor cursorCity = db.query(dbHelper.CITY_TABLE, new String[] { "city" }, null,
				null, null, null, null);
		// Cursor cursorCity = db.rawQuery(SELECT_CITY, null);
		cursorCity.moveToFirst();
		String city = null;
		if (cursorCity.getCount() > 0) {
			city = cursorCity.getString(0);
		}
		return city;
	}


	public void storeCity(String city) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.CITY_NAME, city);
		db.delete(dbHelper.CITY_TABLE, null, null);
		long insertId = db.insert(dbHelper.CITY_TABLE, null, values);

	}
	
	
	public void storeCredentials(String user, String password) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.USER_NAME, user);
		values.put(dbHelper.USER_PASSWORD, password);
		db.delete(dbHelper.USER_TABLE, null, null);
		long insertId = db.insert(dbHelper.USER_TABLE, null, values);

	}
	
	public String[] getStoredCredentials()
	{
		Cursor cursorCity = db.query(dbHelper.USER_TABLE, new String[] { dbHelper.USER_NAME, dbHelper.USER_PASSWORD }, null,
				null, null, null, null);

		cursorCity.moveToFirst();
		String user = null;
		String pass = null;
		if (cursorCity.getCount() > 0) {
			user = cursorCity.getString(0);
			pass = cursorCity.getString(1);
		}
		return new String[]{user, pass};
		
	}
	
	public void storeCategory(String category, Boolean show){
		ContentValues values = new ContentValues();
		values.put(dbHelper.CATEGORIES_SHOW, show);
		String whereClause = dbHelper.CATEGORIES_NAME + " ='"+category+"'";
		db.update(dbHelper.CATEGORIES_TABLE, values, whereClause, null);		
	}
	
	public Map <String ,Boolean>  getCategories()
	{
		Map <String ,Boolean> cat = new HashMap<String, Boolean>();
		Cursor cursorCity = db.query(dbHelper.CATEGORIES_TABLE, new String[] { "category, show" }, null,
				null, null, null, null);

		cursorCity.moveToFirst();
	    while (!cursorCity.isAfterLast()) {    	
	    	
	    	boolean show = true;
	    	if ( (int) cursorCity.getLong(1) != 1 )
	    	{
	    		show = false;
	    	}
	    	
	    	cat.put(cursorCity.getString(0), show);
	    	cursorCity.moveToNext();
	    }
	    
	    return cat;
	}
	
	public void insertCategories()
	{
		dbHelper.insertCategories(db);	
	}

}
