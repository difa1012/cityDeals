package com.citydeals.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "settings.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String CATEGORIES_TABLE= "categories";
	public static final String CATEGORIES_ID = "_id";
	public static final String CATEGORIES_NAME = "category";
	public static final String CATEGORIES_SHOW = "show";
	
	public static final String CITY_TABLE= "city";
	public static final String CITY_ID = "_id";
	public static final String CITY_NAME = "city";

	public static final String USER_TABLE = "user";
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "name";
	public static final String USER_PASSWORD = "password";	
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);	
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createCategoryTable = "CREATE TABLE " + CATEGORIES_TABLE + " (" + 
				CATEGORIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				CATEGORIES_NAME + " TEXT, " +
				CATEGORIES_SHOW + " INTEGER) ";
		
		String createCityTable = "CREATE TABLE " + CITY_TABLE + " (" + 
				CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				CITY_NAME + " TEXT ) ";
		
		String createUserTable = "CREATE TABLE " + USER_TABLE + " (" + 
				USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				USER_NAME + " TEXT, " +
				USER_PASSWORD + " TEXT) ";
		
		db.execSQL(createCategoryTable);
		db.execSQL(createCityTable);
		db.execSQL(createUserTable);
		
		insertCategories(db);
	}

	public void insertCategories(SQLiteDatabase db){
		ContentValues valuesFoodDrink = new ContentValues();
		valuesFoodDrink.put(DatabaseHelper.CATEGORIES_NAME, "categoryFoodDrink");
		valuesFoodDrink.put(DatabaseHelper.CATEGORIES_SHOW, "1");
		db.insert(DatabaseHelper.CATEGORIES_TABLE, null, valuesFoodDrink);
		
		ContentValues valuesService = new ContentValues();
		valuesService.put(DatabaseHelper.CATEGORIES_NAME, "categoryService");
		valuesService.put(DatabaseHelper.CATEGORIES_SHOW, "1");
		db.insert(DatabaseHelper.CATEGORIES_TABLE, null, valuesService);

		ContentValues valuesShopping = new ContentValues();
		valuesShopping.put(DatabaseHelper.CATEGORIES_NAME, "categoryShopping");
		valuesShopping.put(DatabaseHelper.CATEGORIES_SHOW, "1");
		db.insert(DatabaseHelper.CATEGORIES_TABLE, null, valuesShopping);
		
		ContentValues valuesCarTransport = new ContentValues();
		valuesCarTransport.put(DatabaseHelper.CATEGORIES_NAME, "categoryCarTransport");
		valuesCarTransport.put(DatabaseHelper.CATEGORIES_SHOW, "1");
		db.insert(DatabaseHelper.CATEGORIES_TABLE, null, valuesCarTransport);
		
		ContentValues valuesRecreation = new ContentValues();
		valuesRecreation.put(DatabaseHelper.CATEGORIES_NAME, "categoryRecreation");
		valuesRecreation.put(DatabaseHelper.CATEGORIES_SHOW, "1");
		db.insert(DatabaseHelper.CATEGORIES_TABLE, null, valuesRecreation);

	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " +CATEGORIES_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " +CITY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " +USER_TABLE);
		
		onCreate(db);
	}

}
