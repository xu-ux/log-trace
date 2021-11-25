<div align="center"><h1 styles="text-align: center;">Log Trace</h1></div>

<p align="center">分布式日志追踪和收集系统 🛠 | Distributed log tracking and collection system </p>

## 介绍😊

Log Trace分为客户端和服务端，客户端收集日志，服务端存储日志；项目依赖logback等日志框架



## 执行流程🎡

```sequence
Appender->Queue: Client：日志框架收集日志到RedisAppender，具体数据存到本地队列
Queue-->>Redis: Client：从本地队列批量取出数据，缓存到Redis队列
Redis->ElasticSearch: Server：取出数据，存至ES
```



目前仅适配logback的appender

```xml
    <appender name="LOG_TRACE" class="com.log.trace.appender.RedisAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
    </appender>
```

