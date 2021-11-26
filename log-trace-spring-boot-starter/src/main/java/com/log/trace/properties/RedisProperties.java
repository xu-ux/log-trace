package com.log.trace.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 9:18
 * @version: 1.0
 */
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "trace.redis")
public class RedisProperties {

    private String host = "localhost";

    private int port = 6379;

    private String password;

    private int database = 15;

    private Pool pool;

    @Getter
    @Setter
    @ToString
    public static class Pool {

        /**
         * 最大空闲连接数
         */
        private int maxIdle = 4;

        /**
         * 最小空闲连接数
         */
        private int minIdle = 0;

        /**
         * 最大数据库连接数
         */
        private int maxActive = 4;

        /**
         * 连接池最大阻塞等待时间
         */
        private Duration maxWait = Duration.ofMillis(-1L);
    }

}
