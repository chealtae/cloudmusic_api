package com.music.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    // 转换时区格式
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseStringToDate(String str) {
        Date result = new Date();
        try {
            // 解析字符串，转成Date类型
            result = dateFormat.parse(str);
        } catch (ParseException exception) {
            System.out.println("String 转 Date 失败");
        }
        return result;
    }

    public static Date parseStringToDateTime(String str) {
        Date result = new Date();
        try {
            // 解析字符串，转成Date类型
            result = dateTimeFormat.parse(str);
        } catch (ParseException exception) {
            System.out.println("String 转 DateTime 失败");
        }
        return result;
    }

}
