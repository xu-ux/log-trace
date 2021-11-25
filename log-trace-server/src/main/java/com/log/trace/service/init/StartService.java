package com.log.trace.service.init;

import com.log.trace.pojo.Constants;
import com.log.trace.helper.ElasticsearchHelper;
import com.log.trace.helper.RedisHelper;
import com.log.trace.pojo.LogInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 17:41
 * @version: 1.0
 */
@Slf4j
@Service
public class StartService {

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private ElasticsearchHelper elasticsearchHelper;

    /**
     * 加载servlet时会加载一次
     */
    @PostConstruct
    public void start(){
        while (true){
            List<LogInfo> list = redisHelper.<LogInfo>rightPopRange(Constants.LOG_TRACE_QUEUE_KEY, 1000);
            try {
                if (CollectionUtils.isEmpty(list)) {
                    Thread.sleep(3000);
                    continue;
                }
                String indexName =  "log_trace_".concat(list.get(0).getAppName());
                if (!elasticsearchHelper.exist(indexName)){
                    log.info("TraceServer-创建索引 index:{}",indexName);
                    elasticsearchHelper.createIndex(indexName,1,0);
                }
                boolean b = elasticsearchHelper.batchAddDocument(indexName, list);
            } catch (Exception e) {
                log.error("TraceServer-缓存业务操作数据日志失败",e);
            }
        }

    }
}
