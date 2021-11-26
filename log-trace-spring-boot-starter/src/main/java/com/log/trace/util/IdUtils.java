package com.log.trace.util;

import com.fasterxml.uuid.Generators;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * @Author xucl
 * @Version 1.0
 * @Description id获取工具
 * @date 2021/6/3 14:24
 */
public class IdUtils {

    public static final String PATTERN_1 = "yyyyMMddHHmm";

    public static final String PATTERN_2 = "yyyyMMddHHmmss";

    public final static String PATTERN_3="yyyyMMddHHmmssSSSS";

    public static String getUUID(){
        return Generators.timeBasedGenerator().generate().toString();
    }

    public static String getSimpleUUID(){
        return Generators.timeBasedGenerator().generate().toString().replace("-", "");
    }

    public static String getDateWithServer(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN_1)).concat("_internal_service");
    }

    /**
     * 获取唯一id
     * @return 例如：2021061711501151228626
     */
    public static String getDateId(){
            int hashCodeV = UUID.randomUUID().toString().hashCode();
            hashCodeV = hashCodeV < 0 ? -hashCodeV : hashCodeV;
            // 0 代表前面补充0
            // d 代表参数为正数型
            return new SimpleDateFormat(PATTERN_1).format(new Date()) + String.format("%010d", hashCodeV);
    }

    public static String getSerialId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        hashCodeV = hashCodeV < 0 ? -hashCodeV : hashCodeV;
        return new SimpleDateFormat(PATTERN_2).format(new Date()) + String.format("%010d", hashCodeV);
    }

    public static String getSerialStrictId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        hashCodeV = hashCodeV < 0 ? -hashCodeV : hashCodeV;
        return new SimpleDateFormat(PATTERN_3).format(new Date()) + String.format("%010d", hashCodeV);
    }

}
