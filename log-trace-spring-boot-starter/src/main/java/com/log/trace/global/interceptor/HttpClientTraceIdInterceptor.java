package com.log.trace.global.interceptor;

import com.log.trace.pojo.Constants;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * @descriptions: HttpClient添加 traceId拦截器
 * @author: xucl
 * @date: 2021/11/25 14:49
 * @version: 1.0
 */
public class HttpClientTraceIdInterceptor implements HttpRequestInterceptor {

    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        //当前线程调用中有traceId，则将该traceId进行透传
        if (traceId != null) {
            //添加请求体
            httpRequest.addHeader(Constants.TRACE_ID, traceId);
        }
    }
}
