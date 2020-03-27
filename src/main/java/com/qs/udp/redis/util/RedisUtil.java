package com.qs.udp.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "129.28.159.43";

	// Redis的密码
	private static String PASSWORD = "123456";

	// Redis的端口号
	private static int PORT = 6379;

	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;

	private static int TIMEOUT = 1000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池（可作为分布式部署，支持主从redis数据库）
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 */
	public static synchronized Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 设置值设置相同的key，即可覆盖
	public static void setValue(String key, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
		} finally {
			jedis.close();
		}
	}

	// 通过key去取值
	public static String getValue(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.get(key);
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
	}

	// 通过key删除值
	public static long delValue(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(key);
		} finally {
			if (null != jedis) {
				jedis.close();
			}
		}
	}

}
