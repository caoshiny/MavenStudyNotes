package com.shiny;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private static final List<String> finalList = new ArrayList<>();

    public static void main(String[] args) {
        String a = "1111111";
        System.out.println(a);
        a = "222222";
        System.out.println(a);
        a.substring(1, 2);
        System.out.println(a);

        long l1 = 270;
        long l2 = 180;
        System.out.println(l1 / l2);

        finalList.add("111");
        finalList.add("222");
        finalList.remove("111");
        System.out.println(finalList);
    }
}
