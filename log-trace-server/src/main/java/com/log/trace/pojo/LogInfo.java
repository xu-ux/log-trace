package com.log.trace.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
}
