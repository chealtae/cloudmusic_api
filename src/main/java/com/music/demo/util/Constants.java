package com.music.demo.util;

public class Constants {
    public final static String USER_SESSION = "userSession";
    public final static String ADMIN_SESSION = "adminSession";
    public static final String SENDER_MAIL = "1576492562@qq.com";
    public static String CODE = "code";
    public static String MSG = "msg";
    public static String namePrefix="新用户";
    public static String getUrl() {
//        return "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator");
        return System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator");
    }

    public static String getUrl2() {
//        return "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "img" + System.getProperty("file.separator");
        return System.getProperty("user.dir") + System.getProperty("file.separator") + "audio" + System.getProperty("file.separator");
    }
}
