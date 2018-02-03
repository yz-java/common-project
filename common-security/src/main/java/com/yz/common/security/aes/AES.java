package com.yz.common.security.aes;

/**
 * @author yangzhao
 * @Description
 * @Date create by 15:47 18/2/3
 */
public interface AES {

    public String encrypt(String data) throws Exception;

    public String decrypt(String data) throws Exception;
}
