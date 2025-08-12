package com.equity.equitytest.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss");

    public static String yyyyMMddHHmmss(LocalDateTime localDateTime){
        return dateTimeFormatter.format(localDateTime);
    }
}
