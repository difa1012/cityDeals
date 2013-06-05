package com.citydeals;

import android.app.Application;

public class CityDealsApplication extends Application {

    private String city;
    private String user;
	private String password;
	private boolean showCategoryFoodDrink;
    private boolean showCategoryService;
    private boolean showCategoryShopping;
    private boolean showCategoryCarTransport;  
    private boolean showCategoryRecreation;
    
    
    public boolean showCategoryFoodDrink() {
		return showCategoryFoodDrink;
	}

	public void setShowCategoryFoodDrink(boolean _showCategoryFoodDrink) {
		this.showCategoryFoodDrink = _showCategoryFoodDrink;
	}

	public boolean showCategoryService() {
		return showCategoryService;
	}

	public void setShowCategoryService(boolean _showCategoryService) {
		this.showCategoryService = _showCategoryService;
	}

	public boolean showCategoryShopping() {
		return showCategoryShopping;
	}

	public void setShowCategoryShopping(boolean _showCategoryShopping) {
		this.showCategoryShopping = _showCategoryShopping;
	}

	public boolean showCategoryCarTransport() {
		return showCategoryCarTransport;
	}

	public void setShowCategoryCarTransport(boolean _showCategoryCarTransport) {
		this.showCategoryCarTransport = _showCategoryCarTransport;
	}

	public boolean showCategoryRecreation() {
		return showCategoryRecreation;
	}

	public void setShowCategoryRecreation(boolean _showCategoryRecreation) {
		this.showCategoryRecreation = _showCategoryRecreation;
	}

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}