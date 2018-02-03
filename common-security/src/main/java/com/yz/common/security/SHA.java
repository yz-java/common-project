package com.yz.common.security;

import java.security.MessageDigest;

/**
 * Created by yangzhao on 17/7/20.
 */
public class SHA {

    public String Sign(String content, String privateKey) throws Exception {
        byte[] bt = content.getBytes();
        String encName = "SHA-256";
        MessageDigest md = MessageDigest.getInstance(encName);
        md.update(bt);
        String strDes = bytes2Hex(md.digest());
        return strDes;
    }

    private String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
