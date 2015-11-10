package com.noga.highland.mment;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/")
public class Endpoint {
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println(message);
	}
}
