package com.music.demo.util;

import com.baidu.aip.contentcensor.AipContentCensor;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class ShenHeUtils {// 内容审核类

    private static final String APP_ID = "19002475";
    private static final String API_KEY = "fIsL1wg3XaU6Xl5V2xcMKCw8";
    private static final String SECRET_KEY = "Np9KzZDbZlaSaCOh1eQSPgHniD9uCPDP";

    // 初始化一个AipContentCensor
    private static final AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);

    // 图像审核接口
    public static boolean shenHeTuXiang(MultipartFile file) {
        boolean result = false;
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);// 建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000);// 通过打开的连接传输数据的超时时间（单位：毫秒）
        try {
            // 调用百度的图像审核接口，获取图像审核的结果
            JSONObject response = client.imageCensorUserDefined(file.getBytes(), null);
            // 表达式成立表示图片未通过审核，返回false，否则返回true
            result = "不合规".equals(response.getString("conclusion")) ? false : true;
        } catch (IOException exception) {
            System.out.println("图像转字节数组失败");
        }
        return result;
    }

    // 文本审核接口
    public static boolean shenHeWenBen(String text) {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);// 建立连接的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000);// 通过打开的连接传输数据的超时时间（单位：毫秒）
        // 调用百度的文本审核接口，获取文本审核的结果
        JSONObject response = client.textCensorUserDefined(text);
        // 表达式成立表示文本未通过审核，返回false，否则返回true
        return "不合规".equals(response.getString("conclusion")) ? false : true;
    }

    // 防御简单XSS接口
    public static boolean solveSimpleXSS(String text) {
        // 如果传入的文本包含html的编码，则文本中的编码会被转码，再将转码后的文本与原文本匹配，相同则说明原文本不包含html的编码
        return StringEscapeUtils.escapeHtml4(text.trim()).equals(text.trim()) ? true : false;
    }

}
