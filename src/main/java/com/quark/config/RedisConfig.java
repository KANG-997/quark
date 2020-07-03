package com.quark.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.crypto.KeyGenerator;
import java.io.Serializable;
import java.time.Duration;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

     @Value("${spring.redis.host}")
     private String host;
     @Value("${spring.redis.port}")
     private int port;
     @Value("${spring.redis.timeout}")
     private int timeout;
     @Value("${spring.redis.jedis.maxIdle}")
     private int maxIdle;
     @Value("${spring.redis.jedis.maxWaitMills}")
     private long maxWaitMills;
     @Value("${spring.redis.database:0}")
     private int database;

     @Bean
     public JedisPool redisPoolFactory(){
         JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
         jedisPoolConfig.setMaxIdle(maxIdle);
         jedisPoolConfig.setMaxWaitMillis(maxWaitMills);
         return new JedisPool(jedisPoolConfig,host,port,timeout,null,database);
     }

     @Bean
     public JedisConnectionFactory jedisConnectionFactory(){
         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
         redisStandaloneConfiguration.setHostName(host);
         redisStandaloneConfiguration.setPort(port);
         redisStandaloneConfiguration.setPassword(RedisPassword.none());
         redisStandaloneConfiguration.setDatabase(database);

         JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();;
         jedisClientConfigurationBuilder.connectTimeout(Duration.ofMinutes(timeout));
         jedisClientConfigurationBuilder.usePooling();
         return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfigurationBuilder.build());
     }

     @Bean(name = "redisTemplate")
     @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
         RedisTemplate<Object,Object> template = new RedisTemplate<>();
         JacksonRedisSerializer<Object> objectJacksonRedisSerializer = new JacksonRedisSerializer<>(Object.class);
         template.setValueSerializer(objectJacksonRedisSerializer);
         template.setHashValueSerializer(objectJacksonRedisSerializer);
         template.setKeySerializer(new StringRedisSerializer());
         template.setHashValueSerializer(new StringRedisSerializer());
         template.setConnectionFactory(redisConnectionFactory);
         return template;
     }

     @Bean
     public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
         RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);
         return redisCacheManagerBuilder.build();
     }

     @Bean
     @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
         StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
         stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
         return stringRedisTemplate;
     }

     @Bean
    public RedisTemplate<String, Serializable> limitRedisTemplate(RedisConnectionFactory redisConnectionFactory){
         RedisTemplate<String, Serializable> template = new RedisTemplate<>();
         template.setKeySerializer(new StringRedisSerializer());
         template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
         template.setConnectionFactory(redisConnectionFactory);
         return template;
     }


}
