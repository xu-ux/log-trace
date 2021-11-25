package com.log.trace.service.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @descriptions:
 * @author: xucl
 * @version: 1.0
 */
@Slf4j
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Value("${server.port}")
    private int port;

    @Value("${server.servlet.context-path}")
    private String path;

    @Value("${spring.application.name}")
    private String name;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[{}] 项目启动完成，访问地址：http://localhost:{}{}",name,port,path);
    }
}
