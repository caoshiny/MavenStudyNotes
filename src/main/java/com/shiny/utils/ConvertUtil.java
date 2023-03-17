package com.shiny.utils;

import java.text.DecimalFormat;

public class ConvertUtil {

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    /**
     * @param second 秒
     * @description: 秒转换为时分秒 HH:mm:ss 格式 仅当小时数大于0时 展示HH
     * @return: {@link String}
     * @author: pzzhao
     * @date: 2022-05-08 13:55:17
     */
    public static String second2Time(Long second) {
        if (second == null || second < 0) {
            return "00:00";
        }

        long h = second / 3600;
        long m = (second % 3600) / 60;
        long s = second % 60;
        String str = "";
        if (h > 0) {
            str = (h < 10 ? ("0" + h) : h) + "时";
        }
        str += (m < 10 ? ("0" + m) : m) + "分";
        str += (s < 10 ? ("0" + s) : s) + "秒";
        return str;
    }
}
