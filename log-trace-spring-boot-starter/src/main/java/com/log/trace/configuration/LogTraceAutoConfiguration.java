package com.log.trace.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.log.trace.helper.RedisHelper;
import com.log.trace.properties.TraceRedisProperties;
import com.log.trace.util.ConsoleColors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 9:21
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({TraceRedisProperties.class})
@ComponentScan(basePackages = {"com.log.trace"})
public class LogTraceAutoConfiguration {

    @Autowired
    private TraceRedisProperties traceRedisProperties;

    /**
     * 连接池属性配置
     * @return
     */
    @Bean(name = "traceJedisPoolConfig")
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(traceRedisProperties.getPool().getMaxIdle());
        poolConfig.setMaxTotal(traceRedisProperties.getPool().getMaxActive());
        poolConfig.setMaxWaitMillis(traceRedisProperties.getPool().getMaxWait().toMillis());
        poolConfig.setTestOnBorrow(true);
        return poolConfig;
    }

    /**
     * 单机版配置
     * @return
     */
    @Bean(name = "traceRedisStandaloneConfiguration")
    public RedisStandaloneConfiguration redisConfig(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(traceRedisProperties.getHost());
        configuration.setPassword(traceRedisProperties.getPassword());
        configuration.setDatabase(traceRedisProperties.getDatabase());
        configuration.setPort(traceRedisProperties.getPort());
        return configuration;
    }

    /**
     * 创建redis连接工厂
     */
    @Bean(name = "traceRedisConnectionFactory")
    public JedisConnectionFactory createJedisConnectionFactory(@Qualifier("traceRedisStandaloneConfiguration") RedisStandaloneConfiguration  redisStandaloneConfiguration,
                                                               @Qualifier("traceJedisPoolConfig")  JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration);
        factory.setPoolConfig(jedisPoolConfig);
        log.info(ConsoleColors.getDefault()+"初始化TraceRedis服务成功 host:{} port:{} database:{}",traceRedisProperties.getHost(),
                traceRedisProperties.getPort(),traceRedisProperties.getDatabase());
        return factory;
    }

    /**
     * JSON序列化
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "traceRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate1(@Qualifier("traceRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 使用Jackson2JsonRedisSerialize 替换默认的jdkSerializeable序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 初始化redisHelper
     * @param traceRedisTemplate
     * @return
     */
    @Bean
    public RedisHelper initRedisHelper(RedisTemplate<String, Object> traceRedisTemplate){
        RedisHelper utilsRedis = new RedisHelper(traceRedisTemplate);
        RedisHelper.setSelfBean(utilsRedis);
        return utilsRedis;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        System.out.println(ConsoleColors.YELLOW_BACKGROUND + "LOG TRACE CLIENT START"+ ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "\n" +
                        "██╗      ██████╗  ██████╗    ████████╗██████╗  █████╗  ██████╗███████╗\n" +
                        "██║     ██╔═══██╗██╔════╝    ╚══██╔══╝██╔══██╗██╔══██╗██╔════╝██╔════╝\n" +
                        "██║     ██║   ██║██║  ███╗█████╗██║   ██████╔╝███████║██║     █████╗  \n" +
                        "██║     ██║   ██║██║   ██║╚════╝██║   ██╔══██╗██╔══██║██║     ██╔══╝  \n" +
                        "███████╗╚██████╔╝╚██████╔╝      ██║   ██║  ██║██║  ██║╚██████╗███████╗\n" +
                        "╚══════╝ ╚═════╝  ╚═════╝       ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝\n" +
                "\n"+ ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Powered By xu-ux" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Link: https://github.com/xu-ux"+ ConsoleColors.RESET);
    }

}
