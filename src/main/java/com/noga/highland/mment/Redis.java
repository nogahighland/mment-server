package com.noga.highland.mment;

import redis.clients.jedis.JedisPool;

public class Redis {
	
	private static final String HOST = "localhost";
	private static final JedisPool POOL = new JedisPool(HOST);
	

}
