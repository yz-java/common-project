package com.yz.common.core.file.dowload;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * @author: yangzhao
 * @Date: 2019/7/28 22:34
 * @Description:
 */
public class FileDownload implements Runnable {

    public static final CountDownLatch countDownLatch =new CountDownLatch(1);

    private long fileSize;

    private String fileUrl;

    private String fileSavePath;
    /**
     * 已下载文件大小
     */
    private long downloadFileSize=0;

    private FileDownloadProgress fileDownloadProgress;

    public FileDownload(long fileSize, String fileUrl, String fileSavePath){
        this.fileSize=fileSize;
        this.fileUrl=fileUrl;
        this.fileSavePath=fileSavePath;
    }

    public FileDownload(long fileSize, String fileUrl, String fileSavePath, FileDownloadProgress fileDownloadProgress){
        this.fileSize=fileSize;
        this.fileUrl=fileUrl;
        this.fileSavePath=fileSavePath;
        this.fileDownloadProgress=fileDownloadProgress;
    }

    @Override
    public void run() {
        HttpClient httpClient = HttpClients.createDefault();
        OutputStream out = null;
        InputStream in = null;
        Timer timer = new Timer();
        try {
            HttpGet httpGet = new HttpGet(this.fileUrl);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            in = entity.getContent();
            long length = entity.getContentLength();
            if (length <= 0) {
                System.out.println("下载文件不存在！");
                return;
            }

            File file = new File(this.fileSavePath);
            if(!file.exists()){
                file.createNewFile();
            }
            out = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if (fileDownloadProgress!=null){
                        //回调进度信息
                        fileDownloadProgress.progressCallBack(fileSize,downloadFileSize);
                    }
                }
            }, new Date(),2000);
            while ((readLength=in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                downloadFileSize+=bytes.length;
                out.write(bytes);
            }
            out.flush();
            if (fileDownloadProgress!=null){
                //回调进度信息
                fileDownloadProgress.finish(fileSavePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (countDownLatch!=null){
                countDownLatch.countDown();
            }
            timer.cancel();
        }
    }
}
