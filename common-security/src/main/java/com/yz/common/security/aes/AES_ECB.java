package com.yz.common.security.aes;

import com.yz.common.security.ISecurity;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

public class AES_ECB implements ISecurity {

    private String sKey = "B31F2A75FBF94099";

    private String ALGORITHM = "AES/ECB/PKCS7Padding";

    private byte[] raw = null;


    public AES_ECB(String sKey, String algorithm) {
        this.sKey = sKey;
        this.ALGORITHM = algorithm;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            raw = sKey.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public String Encrypt(String data) throws Exception {
        byte[] encrypted = null;
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        encrypted = cipher.doFinal(data.getBytes("utf-8"));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public String Decrypt(String data) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decode = Base64.getDecoder().decode(data.getBytes());//先用base64解密
        byte[] original = cipher.doFinal(decode);
        String originalString = new String(original, "utf-8");
        return originalString;
    }

    @Override
    public String Sign(String content, String privateKey) throws Exception {
        return null;
    }
}
