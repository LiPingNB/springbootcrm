package com.t251.springbootcrm.util;

import redis.clients.jedis.Jedis;

public class Rediscache {
	public static void main(String[] args) {
		//获得数据库连接
		/*Jedis jedis=new Jedis("127.0.0.1",6379);
		System.out.println("连接redis成功");
		jedis.set("k1", "数据保存");
		jedis.set("k2", "数据测试");
		jedis.set("k3", "数据验证");
		jedis.set("k4", "57848383");
		jedis.expire("k4", 10);//设置key有效期
		//释放连接
		jedis.close();*/
		//TestRedis.saveCode("18570277331", "666777", 20);
		
		System.out.println(Rediscache.get("18570277331"));
	}
	
	public static void saveCode(String key,String value){
		Jedis jedis=new Jedis("127.0.0.1",6379);
		jedis.set(key, value);
		jedis.expire(key, 3600);//设置key有效期
		//释放连接
		jedis.close();
	}
	
	public static String  get(String key){
		Jedis jedis=new Jedis("127.0.0.1",6379);
		String value=jedis.get(key);
		//释放连接
		jedis.close();
		return value;
	}

}
