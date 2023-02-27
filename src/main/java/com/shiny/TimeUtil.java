package com.shiny;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    // 把时间转换成标准格式字符串
    public static String convertLocalDateTimeToString(LocalDateTime nowTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMddHHmmyyyy.ss");
        return df.format(nowTime);
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.convertLocalDateTimeToString(LocalDateTime.now()));
    }
}
