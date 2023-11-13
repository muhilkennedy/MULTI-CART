package com.base.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.base.util.Log;

/**
 * @author Muhil Kennedy
 *
 */
@Profile(value = { "prod", "dev" })
@Configuration
@EnableCaching
@ConditionalOnProperty(prefix = "spring.cache", value = "enabled", havingValue = "true")
public class RedisConfiguration {
	
	@Value("${spring.redis.port}")
	private int port;

	// Creating Connection with Redis
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory();
	}

	// multiple bean can be configured for specific entity types with diff serializer.
	@Bean(name = "genericCacheManager")
	public RedisTemplate<Long, Object> redisTemplate() {
		RedisTemplate<Long, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());
		redisTemplate.setDefaultSerializer(serializer);
		redisTemplate.setEnableDefaultSerializer(true);
		return redisTemplate;
	}
    
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		Log.base.info("----- Initializing REDIS Cache Manager -----");
		RedisCacheConfiguration config = RedisCacheConfiguration
				.defaultCacheConfig(Thread.currentThread().getContextClassLoader())
				.prefixCacheNameWith(this.getClass().getSimpleName() + ".")
				.entryTtl(Duration.ofHours(1))
				.disableCachingNullValues();
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
	}
	
//	@Bean("RedisKeyGenerator")
//    public KeyGenerator keyGenerator() {
//        return new RedisKeyGenerator();
//    }

}

