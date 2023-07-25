package com.shiny.utils;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

public class LanguageUtil {
    public static void main(String[] args) {
        System.out.println(ZhConverterUtil.toSimple("我有這個地方不安全"));
    }
}
