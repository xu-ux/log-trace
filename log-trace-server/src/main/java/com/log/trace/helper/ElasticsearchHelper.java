package com.log.trace.helper;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 17:44
 * @version: 1.0
 */
@Component
public class ElasticsearchHelper {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 索引是否创建
     * @param indexName  索引名称
     * @return
     */
    public boolean exist(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }


    /**
     * 创建索引
     * @param indexName   索引名称
     * @param shards      索引主分片
     * @param replicas    索引主分片的副本数
     * @return
     */
    public boolean createIndex(String indexName,int shards,int replicas) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards",shards)
                .put("index.number_of_replicas",replicas));

        CreateIndexResponse createIndexResponse = restHighLevelClient.indices()
                .create(createIndexRequest, RequestOptions.DEFAULT);

        return createIndexResponse.isAcknowledged();
    }

    /**
     * 批量添加
     * @param indexName
     * @param data
     * @return
     */
    public <T> boolean  batchAddDocument(String indexName, List<T> data) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        data.stream().forEach(x->{
            bulkRequest.add(new IndexRequest(indexName).source(JSON.toJSONString(x), XContentType.JSON));
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }
}
