<div align="center"><h1 styles="text-align: center;">Log Trace</h1></div>

<p align="center">分布式日志追踪和收集系统 🛠 | Distributed log tracking and collection system </p>

## 介绍😊

Log Trace分为客户端和服务端，客户端收集日志，服务端存储日志；客户端项目依赖logback等日志框架，依赖springboot环境

## 目录📚

```shell
|-- README.md
|-- log-trace-demo # 示例工程
|-- log-trace-server # 服务端
|-- log-trace-spring-boot-starter # 客户端
`-- pom.xml

```


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


## 使用方法📖

在需要集成的业务项目下

pom文件引入客户端
```xml
    <dependency>
        <groupId>com.log.trace</groupId>
        <artifactId>log-trace-spring-boot-starter</artifactId>
        <version>0.0.1</version>
    </dependency>
```

yaml文件增加log-trace使用的redis连接
>注意：此配置不影响spring-boot-data-redis的autoconfig
```yaml
## log-trace-client配置
trace:
  redis:
    host: # your redis ip
    port: # your redis port
    password:
    database: 15 # 默认15号库
    pool:
      #最小空闲连接数
      min-idle: 1
      #最大空闲连接数
      max-idle: 2
      #最大数据库连接数
      max-active: 2
      #连接池最大阻塞等待时间
      max-wait: 1000ms

```

服务端需要修改yaml文件的`spring.redis.`端口、IP等，保持和客户端的一致

详细请看`log-trace-demo`工程

