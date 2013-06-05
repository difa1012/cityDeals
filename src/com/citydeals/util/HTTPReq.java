package com.citydeals.util;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HTTPReq {
	protected static final String BASE_URL = "http://www.iwi.hs-karlsruhe.de/ebatc/eb09-cityDealsServer/rest";	
	
	protected static HttpEntity<?> getRequestEntity() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
        return new HttpEntity<Object>(requestHeaders);
      }
}
