package com.shiny;

import java.text.NumberFormat;

public class NumberUtil {
    public static String getPercentValue( double similarity) {
        NumberFormat fmt = NumberFormat.getPercentInstance();
        fmt.setMaximumFractionDigits(0);//最多两位百分小数，如25.23%
        return fmt.format(similarity);
    }
}
