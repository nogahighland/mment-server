package com.noga.highland.mment;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Maps;


public class ArtworkSearcher {

	private static final Logger LOGGER = LoggerFactory.getLogger(ArtworkSearcher.class);
	private static final String API_ROOT = "https://itunes.apple.com/search/";

	private String httpMethod = HttpMethods.GET;
	private Map<String, Object> params = Maps.newHashMap();
	private HttpHeaders headers = new HttpHeaders();

	public <T> T search(Class<T> clazz) {
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) throws IOException {
				request.setRequestMethod(httpMethod);
				request.setHeaders(headers);
				request.setConnectTimeout(10000);
				request.setReadTimeout(100000);
				request.setParser(new JacksonFactory().createJsonObjectParser());
			}
		});
		GenericUrl url = new GenericUrl(API_ROOT);
		url.putAll(params);
		LOGGER.debug("URL\n{}", url);
		try {
			HttpRequest req = requestFactory.buildGetRequest(url);
			long start = System.currentTimeMillis();
			HttpResponse res = req.execute();
			LOGGER.debug("end {}", System.currentTimeMillis() - start);
			T result = res.parseAs(clazz);
			return result;
		} catch(IOException e) {
			LOGGER.error("URL\n{}", url, e);
			return null;
		}
	}

	public ArtworkSearcher param(String key, Object value) {
		params.put(key, value);
		return this;
	}
}
