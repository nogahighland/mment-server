package com.noga.highland.mment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

@EqualsAndHashCode(callSuper=false)
@Data
public class ITunesTrack extends GenericData {

	@Key("wrapperType")
	private String wrapperType;
	@Key("kind")
	private String kind;
	@Key("artistId")
	private int artistId;
	@Key("collectionId")
	private int collectionId;
	@Key("trackId")
	private int trackId;
	@Key("artistName")
	private String artistName;
	@Key("collectionName")
	private String collectionName;
	@Key("trackName")
	private String trackName;
	@Key("collectionCensoredName")
	private String collectionCensoredName;
	@Key("trackCensoredName")
	private String trackCensoredName;
	@Key("artistViewUrl")
	private String artistViewUrl;
	@Key("collectionViewUrl")
	private String collectionViewUrl;
	@Key("trackViewUrl")
	private String trackViewUrl;
	@Key("previewUrl")
	private String previewUrl;
	@Key("artworkUrl30")
	private String artworkUrl30;
	@Key("artworkUrl60")
	private String artworkUrl60;
	@Key("artworkUrl100")
	private String artworkUrl100;
	@Key("collectionPrice")
	private double collectionPrice;
	@Key("trackPrice")
	private double trackPrice;
	@Key("releaseDate")
	private String releaseDate;
	@Key("collectionExplicitness")
	private String collectionExplicitness;
	@Key("trackExplicitness")
	private String trackExplicitness;
	@Key("discCount")
	private int discCount;
	@Key("discNumber")
	private int discNumber;
	@Key("trackCount")
	private int trackCount;
	@Key("trackNumber")
	private int trackNumber;
	@Key("trackTimeMillis")
	private long trackTimeMillis;
	@Key("country")
	private String country;
	@Key("currency")
	private String currency;
	@Key("primaryGenreName")
	private String primaryGenreName;
	@Key("radioStationUrl")
	private String radioStationUrl;
	@Key("isStreamable")
	private boolean isStreamable;

}
