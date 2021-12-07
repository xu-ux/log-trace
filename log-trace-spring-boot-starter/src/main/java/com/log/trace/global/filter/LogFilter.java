package com.log.trace.global.filter;

import com.log.trace.pojo.Constants;
import com.log.trace.util.IdUtils;
import com.log.trace.util.thread.TraceIdThreadLocal;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @descriptions: 从请求头中traceId获取，并转存至线程本地变量中(保证业务操作日志可以获取traceId）)
 * @author: xucl
 * @date: 2021/11/24 9:10
 * @version: 1.0
 */
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //如果有上层调用就用上层的ID
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String header = request.getHeader(Constants.TRACE_ID);
        TraceIdThreadLocal.add(StringUtils.isEmpty(header) ? IdUtils.uuid() : header);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        TraceIdThreadLocal.remove();
    }
}
