package com.shiny;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.util.Collections;

public class test {
    public static void main(String[] args) {
        String json = " {\"pageSize\":50,\"startTime\":\"2022-11-01T00:00:00\",\"endTime\":\"2022-11-09T23:59:59\",\"pageNum\":1}";
        JSONObject josn = (JSONObject) JSONObject.parse(json);
        System.out.println(josn.getTimestamp("startTime"));
        System.out.println(josn.getTimestamp("endTime"));
        Timestamp startTime = josn.getTimestamp("startTime");

        Timestamp timestamp2 = Timestamp.valueOf("2022-11-12 09:59:01");
        System.out.println(timestamp2);

        JSONObject requestBody = new JSONObject();

        requestBody.put("start_time", startTime);
        requestBody.put("timestamp2", timestamp2);
        System.out.println(requestBody);

        System.out.println(requestBody.getTimestamp("start_time"));
        System.out.println(requestBody.getTimestamp("timestamp2"));


        String test = "{\"start_time\":1667232000000,\"num\":1000}";
        JSONObject testJson = (JSONObject) JSONObject.parse(test);
        System.out.println(testJson.getTimestamp("start_time"));
    }
}
