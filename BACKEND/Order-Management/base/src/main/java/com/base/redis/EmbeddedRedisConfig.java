package com.base.redis;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.base.util.Log;

import redis.embedded.RedisServer;

/**
 * @author Muhil Kennedy
 * ref: https://github.com/ozimov/embedded-redis for full impl
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.embedded.redis", value = "enabled", havingValue = "true")
public class EmbeddedRedisConfig {

	private RedisServer redisServer;

	@Value("${spring.redis.port}")
	private int redisPort;

	@PostConstruct
	public void startRedis() {
		Log.base.info("----- Initializing Embedded REDIS Server Started -----");
		this.redisServer = new RedisServer(redisPort);
		redisServer.start();
		Log.base.info("----- Embedded REDIS Server Started at port {} -----" , redisPort);
	}

	@PreDestroy
	public void stopRedis() {
		this.redisServer.stop();
		Log.base.info("----- Embedded REDIS Stopped -----");
	}
}
