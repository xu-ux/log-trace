package com.log.trace.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 17:44
 * @version: 1.0
 */
@Slf4j
@Configuration
public class RestHighLevelConfig {

    @Value("${es.host}")
    private String host;

    @Value("${es.port:9200}")
    private int port;

    @Value("${es.username}")
    private String username;

    @Value("${es.password}")
    private String password;

    @Bean
    public RestHighLevelClient elasticsearchClient(){
        try {
            HttpHost httpHost = new HttpHost(host, port, HttpHost.DEFAULT_SCHEME_NAME);
            RestClientBuilder builder=RestClient.builder(httpHost);
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
            RestHighLevelClient restClient = new RestHighLevelClient( builder);
            return restClient;
        } catch (Exception e) {
            log.error("初始化 RestHighLevelClient 异常",e);
            return null;
        }
    }
}
