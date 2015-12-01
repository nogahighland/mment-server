package com.noga.highland.mment;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.api.client.util.Joiner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

@ServerEndpoint(value="/")
public class Endpoint {

	public static Predicate<ITunesTrack> distinctTrack() {
		ConcurrentLinkedQueue<ITunesTrack> set = new ConcurrentLinkedQueue<ITunesTrack>();
		return track -> {
			if (set.isEmpty()) {
				set.add(track);
				return true;
			}
			return !set.stream().anyMatch(t -> {
				if (track.getArtistId() == t.getArtistId()
						&& track.getCollectionId() == t.getCollectionId()
						&& track.getTrackId() == t.getTrackId()) {
					return true;
				} else {
					set.add(track);
					return false;
				}
			});
		};
	}

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
		String album = msg.get("album").getAsString();
		String title = msg.get("title").getAsString();

		List<ITunesSearchResult> results = Arrays.asList("GB", "DE", "US", "JP", "AU", "NZ").parallelStream().map(cc -> {
			ArtworkSearcher searcher = injector.getInstance(ArtworkSearcher.class);
			searcher.param("media", "music");
			searcher.param("country", cc);
			searcher.param("term", Joiner.on(' ').join(Arrays.asList(album, artist, title)));

			ITunesSearchResult result = searcher.search(ITunesSearchResult.class);
			return result;
		}).collect(Collectors.toList());

		List<ITunesTrack> tracks = results.stream()
				.filter(r -> r.getCount() > 0)
				.map(r -> r.getTracks())
				.flatMap(list -> list.stream())
				.filter(distinctTrack())
				.collect(Collectors.toList());

		//TODO best hitの抽出
		//TODO subscriberに合った形式に変換
		//TODO redisへ登録 → publish

		System.out.println(results);
	}

	@OnOpen
	public void onOpen(Session session) {
		//TODO redisをsubscribe
	}
}
