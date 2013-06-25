package com.citydeals.service;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.os.StrictMode;

import com.citydeals.Comment;
import com.citydeals.util.HTTPReq;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommentService extends HTTPReq {
	public static ArrayList<Comment> getCommentsByDeal(int id) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	
        RestTemplate rest = new RestTemplate();
        ArrayList<Comment> comments = null;
        
        final String url = BASE_URL + "/comment/{id}";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        rest.getMessageConverters().add(new GsonHttpMessageConverter(gson));

    	try {
    		ResponseEntity<CommentContainer> responseEntity =  rest.exchange(url, HttpMethod.GET, getRequestEntity(), CommentContainer.class, id);
    		comments = responseEntity.getBody().getComments();
    	}
    	catch(Exception e) { 
    		comments = new ArrayList<Comment>();
    		
    		CommentResponse dr = rest.getForObject(url, CommentResponse.class, id);
    		
    		comments.add(dr.getComment());    		
    	}
    		
    	return comments;
	}
	
	public static boolean setComment(Comment comment) {
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy); 
    	
    	//System.setProperty("http.keepAlive", "false");
    	
        RestTemplate rest = new RestTemplate();
        
        //rest.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        
        final String url = BASE_URL + "/comment/create";
        
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		HttpEntity<Comment> requestEntity = new HttpEntity<Comment>(comment, requestHeaders);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		rest.getMessageConverters().add(new GsonHttpMessageConverter(gson));
		rest.getMessageConverters().add(new StringHttpMessageConverter());
		
		try {
			rest.exchange(url, HttpMethod.POST, requestEntity, Comment.class);
		} catch (Exception e) {
			@SuppressWarnings("unused")
			int i = 0;
			i = 1;		
		}
		
		return false;
	}
	
	private static class CommentContainer {
    	private ArrayList<Comment> comment;
    	
    	public ArrayList<Comment> getComments() {
    		return comment;    		
    	} 	
    }
    private static class CommentResponse {
    	private Comment comment;
    	
    	public Comment getComment() {
    		return comment;
    	}
    }		
}