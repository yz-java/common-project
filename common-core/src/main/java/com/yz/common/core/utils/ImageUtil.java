package com.yz.common.core.utils;


import java.io.File;

/**
 * @author: yangzhao
 * @Date: 2019/7/31 09:46
 * @Description:
 */
public class ImageUtil {

    /**
     * 图片转换成base64字符串
     * @param imgFile
     * @return
     */
    public String imageToBase64(File imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String contentToBase64 = FileUtils.fileContentToBase64(imgFile);
        String name = imgFile.getName();
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        String data = "data:image/" + suffix + ";base64," + contentToBase64;
        return data;
    }

    /**
     * 图片转换成base64字符串
     * @param imgFilePath 图片本地路径
     * @return
     */
    public String imageToBase64(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        File file = new File(imgFilePath);
        return imageToBase64(file);
    }
}
