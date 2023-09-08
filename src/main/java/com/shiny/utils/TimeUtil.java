package com.shiny.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    // 把时间转换成标准格式字符串
    public static String convertLocalDateTimeToString(LocalDateTime nowTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMddHHmmyyyy.ss");
        return df.format(nowTime);
    }

    public static String formatLongTime(long mss) {
        String DateTimes = null;
        long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;

        DateTimes=String.format("%02d:", hours)+ String.format("%02d:", minutes) + String.format("%02d", seconds);
        return DateTimes;
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.convertLocalDateTimeToString(LocalDateTime.now()));
        System.out.println(TimeUtil.formatLongTime(3600));
    }
}
