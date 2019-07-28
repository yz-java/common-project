package com.yz.common.core.file.dowload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * @author: yangzhao
 * @Date: 2019/7/28 22:27
 * @Description:
 */
public class FileDownloadManager {

    private static final Logger logger = LoggerFactory.getLogger(FileDownloadManager.class);

    /**
     * 获取远程文件大小
     */
    public static long getRemoteFileSize(String remoteFileUrl) throws IOException {
        long fileSize = 0;
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(remoteFileUrl).openConnection();
        httpConnection.setRequestMethod("HEAD");
        int responseCode = httpConnection.getResponseCode();
        if (responseCode >= 400) {
            logger.debug("Web服务器响应错误!");
            return 0;
        }
        String sHeader;
        for (int i = 1; ; i++) {
            sHeader = httpConnection.getHeaderFieldKey(i);
            if (sHeader != null && sHeader.equals("Content-Length")) {
                logger.info("文件大小ContentLength:"+ httpConnection.getContentLength());
                fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
                break;
            }

        }
        return fileSize;
    }

    /**
     * 同步下载文件
     * @param url
     * @param savePath
     * @throws Exception
     */
    public static void syncDownload(String url,String savePath) throws Exception {
        long remoteFileSize = getRemoteFileSize(url);
        FileDownload fileDownload = new FileDownload(remoteFileSize, url, savePath);
        Thread thread=new Thread(fileDownload);
        thread.start();
        FileDownload.countDownLatch.await();
    }

    /**
     * 同步下载文件（回调）
     * @param url
     * @param savePath
     * @param fileDownloadProgress
     * @throws Exception
     */
    public static void syncDownload(String url,String savePath,FileDownloadProgress fileDownloadProgress) throws Exception {
        long remoteFileSize = getRemoteFileSize(url);
        FileDownload fileDownload = new FileDownload(remoteFileSize, url, savePath,fileDownloadProgress);
        Thread thread=new Thread(fileDownload);
        thread.start();
        FileDownload.countDownLatch.await();
    }

    /**
     * 异步下载文件
     * @param url
     * @param savePath
     * @throws Exception
     */
    public static void asyncDownload(String url,String savePath) throws Exception {
        long remoteFileSize = getRemoteFileSize(url);
        FileDownload fileDownload = new FileDownload(remoteFileSize, url, savePath);
        Thread thread=new Thread(fileDownload);
        thread.start();
    }

    /**
     * 异步下载文件（回调）
     * @param url
     * @param savePath
     * @param fileDownloadProgress
     * @throws Exception
     */
    public static void asyncDownload(String url,String savePath,FileDownloadProgress fileDownloadProgress) throws Exception {
        long remoteFileSize = getRemoteFileSize(url);
        FileDownload fileDownload = new FileDownload(remoteFileSize, url, savePath,fileDownloadProgress);
        Thread thread=new Thread(fileDownload);
        thread.start();
    }

}
