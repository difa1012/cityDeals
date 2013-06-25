package com.citydeals.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.StrictMode;

import com.citydeals.User;
import com.citydeals.util.HTTPReq;

public class UserService extends HTTPReq {
	//multiple results: 500 HTTP-Error (NonUniqueResultException) - catched
	//one result: JSON-Object
	//no result: 500 HTTP-Error (NoResultException) - catched
	public static User getUserByEmail(String email) {
		//quick and dirty fix to avoid async methods
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		try {
			RestTemplate rest = new RestTemplate();        
			final String url = BASE_URL + "/user/{email}";
			rest.getMessageConverters().add(new GsonHttpMessageConverter());
			return rest.getForObject(url,User.class, email);    
		} catch (Exception e) { //catch the HTTP-500 error and return null
			return null; //no or multiple users were found			
		}
    }
	
	//crashes but work
	public static boolean setUser(User user) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);		
	    
		RestTemplate rest = new RestTemplate(); 
		final String url = BASE_URL + "/user/create";
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		HttpEntity<User> requestEntity = new HttpEntity<User>(user, requestHeaders);

		rest.getMessageConverters().add(new GsonHttpMessageConverter());
		rest.getMessageConverters().add(new StringHttpMessageConverter());
		
		try {
			rest.exchange(url, HttpMethod.POST, requestEntity, String.class);
		} catch (Exception e) {		}
				
		User newUser = getUserByEmail(user.geteMail());
		if (newUser != null) {
			if (newUser.geteMail().equals(user.geteMail()))
				return true;
		}
		
		return false;
		}
	
	public static boolean checkUser(String mail, String pw) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);	
		
		boolean b = false;
		
		try {
			RestTemplate rest = new RestTemplate(); 
			
			final String url = BASE_URL + "/login/" + mail + "/" + pw;	
			rest.getMessageConverters().add(new StringHttpMessageConverter());
	        try {
	        	b = Boolean.parseBoolean(rest.getForObject(url, String.class));  
	        } catch (Exception e) {
	        	
	        } 
		} catch (Exception e) { 
			return false; 	
		}
		
		return b;	
	}
}
