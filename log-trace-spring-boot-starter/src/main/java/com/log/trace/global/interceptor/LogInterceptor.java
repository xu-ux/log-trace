package com.log.trace.global.interceptor;

import com.log.trace.pojo.Constants;
import com.log.trace.util.IdUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @descriptions: 将tractId存入mdc（保证其他日志也能获取traceId）
 * @author: xucl
 * @date: 2021/11/24 10:30
 * @version: 1.0
 */
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = IdUtils.getUUID();
        }
        MDC.put(Constants.TRACE_ID, traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //调用结束后删除
        MDC.remove(Constants.TRACE_ID);
    }
}
