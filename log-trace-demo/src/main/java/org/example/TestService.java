package org.example;

import com.log.trace.LOG;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/25 10:30
 * @version: 1.0
 */
@Slf4j
@Service
public class TestService {

    @PostConstruct
    public void testInit() throws InterruptedException {
        while (true){
            String dateTimeStr = LocalDateTime.now().toString();
            LOG.info("业务操作日志信息 info:{} | Business operation log information info:{}",dateTimeStr,dateTimeStr);
            Thread.sleep(30000);
        }
    }
}
