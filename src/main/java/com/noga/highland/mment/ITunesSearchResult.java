package com.noga.highland.mment;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

@EqualsAndHashCode(callSuper=false)
@Data
public class ITunesSearchResult extends GenericData {

	@Key("resultCount")
	private int count;
	@Key("results")
	private List<ITunesTrack> tracks;

}
