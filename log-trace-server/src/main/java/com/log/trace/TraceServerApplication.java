package com.log.trace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @descriptions:
 * @author: xucl
 * @version: 1.0
 */
@SpringBootApplication
public class TraceServerApplication {

    public static void main(String[] args){
        SpringApplication.run(TraceServerApplication.class,args);
    }
}
