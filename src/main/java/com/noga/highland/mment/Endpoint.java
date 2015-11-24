package com.noga.highland.mment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

@ServerEndpoint(value="/")
public class Endpoint {

	@OnMessage
	public void onMessage(String message) {
		JsonObject msg = (JsonObject) new JsonParser().parse(message);
		System.out.println(msg);

		Injector injector = Guice.createInjector(new Module() {
			@Override
			public void configure(Binder binder) {
			}
		});
		String artist = msg.get("artist").getAsString();
		//		String album = msg.get("album").getAsString();
		String title = msg.get("title").getAsString();

		List<GenericData> results = Arrays.asList("GB", "DE", "US", "JP", "AU", "NZ").parallelStream().map(cc -> {
			ArtworkSearcher searcher = injector.getInstance(ArtworkSearcher.class);
			searcher.param("media", "music");
			searcher.param("country", cc);
			searcher.param("term", Joiner.on(' ').join(Arrays.asList(artist, title)));

			GenericData result = searcher.search(GenericData.class);
			return result;
		}).collect(Collectors.toList());
		System.out.println(results);
	}
}
