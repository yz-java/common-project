package com.yz.common.core.file.dowload;

/**
 * @author: yangzhao
 * @Date: 2019/7/28 22:42
 * @Description:
 */
public interface FileDownloadProgress {
    /**
     * 进度回调
     * @param fileSize
     * @param currentFileSize
     */
    public void progressCallBack(long fileSize,long currentFileSize);

    /**
     * 完成下载
     * @param filePath
     */
    public void finish(String filePath);
}
