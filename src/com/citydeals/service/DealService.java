package com.citydeals.service;

import java.util.ArrayList;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.StrictMode;

import com.citydeals.Deal;
import com.citydeals.util.HTTPReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DealService extends HTTPReq {
	public static Deal getDealById(int id) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	
        RestTemplate rest = new RestTemplate();
        
        final String url = BASE_URL + "/deal/{id}";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        rest.getMessageConverters().add(new GsonHttpMessageConverter(gson));
        return rest.getForObject(url,Deal.class, id);    	
    }
    public static ArrayList<Deal> getDealsByCity(String city) {
    	RestTemplate rest = new RestTemplate();
    	ArrayList<Deal> deals = null;

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);    	
    	
    	final String url = BASE_URL + "/deal/bycity/{city}";
    	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    	rest.getMessageConverters().add(new GsonHttpMessageConverter(gson));

    	//try to get an JSON-Array (multiple deals)
    	try {
    		ResponseEntity<DealContainer> responseEntity =  rest.exchange(url, HttpMethod.GET, getRequestEntity(), DealContainer.class, city);
    		deals = responseEntity.getBody().getDeals();
    	}
    	catch(Exception e) { //one deal (JSON-Object) or no deal
    		deals = new ArrayList<Deal>();
    		
    		DealResponse dr = rest.getForObject(url, DealResponse.class, city);
    		
    		deals.add(dr.getDeal());    		
    	}
    		
    	return deals;
    }
    
    public static String getImageByDeal(int id) {
        RestTemplate rest = new RestTemplate();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);        
        
        final String url = BASE_URL + "/deal/image/{id}";
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        try {
        	return rest.getForObject(url,String.class, id);  
        } catch(Exception e) {
        	return null;        	
        }
    }
    
	public static double getRatingByDeal(int id) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	
    	double rating = 0;
    	
        RestTemplate rest = new RestTemplate();
        
        final String url = BASE_URL + "/rating/{id}";
        rest.getMessageConverters().add(new StringHttpMessageConverter());
        try {
        	rating = Double.parseDouble(rest.getForObject(url,String.class, id));  
        } catch (Exception e) {}
    	
        if (Double.isNaN(rating)) {
        	rating = 0;
        }
        
		return rating;
	}
	
	public static Deal getTopDeal(String city) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	
        RestTemplate rest = new RestTemplate();
        
        final String url = BASE_URL + "/topdeal/bycity/{city}";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        rest.getMessageConverters().add(new GsonHttpMessageConverter(gson));
        return rest.getForObject(url,Deal.class, city);  		
	}
    
    
    private static class DealContainer {
    	private ArrayList<Deal> deal;
    	
    	public ArrayList<Deal> getDeals() {
    		return deal;    		
    	} 	
    }
    
    private static class DealResponse {
    	private Deal deal;
    	
    	public Deal getDeal() {
    		return deal;
    	}
    }
}
