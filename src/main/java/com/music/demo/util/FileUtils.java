package com.music.demo.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    // 图片路径
    public static final String IMAGE_PATH = "/static/image/";
    // 音频路径
    public static final String AUDIO_PATH = "/static/audio/";
    // 音频路径
    public static final String VOICE_PATH = "/static/voice/voice_cache.wav";

    // 上传文件
    public static String uploadFile(MultipartFile file, String flag, HttpServletRequest request) {
        String result = "unsuccessful";
        String newFileName = "";
        // 文件非空
        if (!file.isEmpty()) {
            try {
                // 初始化文件保存路径
                String filePath = "";
                // 取得文件的原始名称
                String originalFileName = file.getOriginalFilename();
                // 文件重命名
                newFileName = UUID.randomUUID() + "-" + originalFileName;
                // 取得上下文路径
                String contextPath = request.getSession().getServletContext().getRealPath("");
                // 根据文件类型指定文件保存路径
                if ("image".equals(flag)) {
                    filePath = contextPath + IMAGE_PATH;
                } else if ("audio".equals(flag)) {
                    filePath = contextPath + AUDIO_PATH;
                }
                // 创建目标文件对象
                File targetFile = new File(filePath + newFileName);
                // 上传文件
                file.transferTo(targetFile);
                // 把重命名后的文件名作为返回值
                result = newFileName;
                System.out.println("文件上传成功：" + newFileName);
            } catch (IOException exception) {
                System.out.println("文件上传失败：" + newFileName);
            }
        }
        return result;
    }

    // 删除文件
    public static void deleteFile(String oldFileName, String flag, HttpServletRequest request) {
        // 初始化文件保存路径
        String filePath = "";
        // 取得上下文路径
        String contextPath = request.getSession().getServletContext().getRealPath("");
        // 根据文件类型指定文件保存路径
        if ("image".equals(flag)) {
            filePath = contextPath + IMAGE_PATH;
        } else if ("audio".equals(flag)) {
            filePath = contextPath + AUDIO_PATH;
        }
        // 原文件非空
        if (!"".equals(oldFileName)) {
            // 创建待删除的文件对象
            File targetFile = new File(filePath + oldFileName);
            // 删除文件
            if (targetFile.exists() && targetFile.isFile()) {
                targetFile.delete();
                System.out.println("文件删除成功：" + oldFileName);
            } else {
                System.out.println("文件删除失败：" + oldFileName);
            }
        }
    }

}
