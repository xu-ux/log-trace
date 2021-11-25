package com.log.trace.pojo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerContextVO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.log.trace.util.IdUtils;
import com.log.trace.util.thread.TraceIdThreadLocal;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @descriptions:
 * @author: xucl
 * @date: 2021/11/24 10:24
 * @version: 1.0
 */
@Data
@Accessors(chain = true)
public class LogInfo implements Serializable {

    //应用名称
    private String appName;

    //时间戳
    private Long timeStamp;

    //traceId 用于追踪
    private String traceId;

    //内容
    private String content;

    //日志级别
    private String logLevel;

    //类名
    private String className;

    //方法名
    private String method;

    //行号
    private Integer lineNumber;

    //时间
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;

    public static LogInfo getLogInfo(final ILoggingEvent event){
        long timeStamp = event.getTimeStamp();
        LoggerContextVO loggerContextVO = event.getLoggerContextVO();
        String traceId = TraceIdThreadLocal.get();
        StackTraceElement callerDatum = event.getCallerData()[1];
        // 需要配置 contextName属性
        LogInfo logInfo = new LogInfo().setAppName(loggerContextVO.getName())
                .setContent(event.getFormattedMessage())
                .setDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault()))
                .setTimeStamp(timeStamp)
                .setTraceId(Objects.isNull(traceId)? IdUtils.getDateWithServer() : traceId)
                .setClassName(callerDatum.getClassName())
                .setMethod(callerDatum.getMethodName())
                .setLineNumber(callerDatum.getLineNumber())
                .setLogLevel(event.getLevel().levelStr);

        return logInfo;
    }
}
