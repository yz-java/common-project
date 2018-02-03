package com.yz.common.core.utils;

import java.nio.ByteBuffer;

/**
 * 数据包的发送与接收都需要进行异或
 * @author yangzhao 2015年10月17日
 */
public class EncryptUtil {
	/**
	 * 加密钥匙
	 */
	public static final int MAX_BUFFER = 1024;
	public static final byte BUFFER[] = new byte[MAX_BUFFER];

	/**
	 * 加密
	 * @param source 要加密的数据 
	 * @param key 加密密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] source, String key) {
		if (source == null) {
			return null;
		}
		int keyLength = key.length();
		int length = source.length;
		ByteBuffer ba = ByteBuffer.allocate(length);
		for (int i = 0; i < length; i++) {
			ba.put((byte) (source[i] ^ (byte) key.charAt(i % keyLength)));
		}
		return ba.array();
	}

	/**
	 * 解密
	 * @param source 要解密的数据
	 * @param offset 从第几位开始进行解密
	 * @param length 解密多少位
	 * @param key 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] source, int offset, int length, String key) {
		int keyLength = key.length();
		ByteBuffer ba = ByteBuffer.allocate(length);
		length = length + offset;
		for (int i = offset; i < length; i++) {
			ba.put((byte) (source[i] ^ (byte) key.charAt((i - offset) % keyLength)));
		}
		return ba.array();
	}

	/**
	 * 解密
	 * @param source 要解密的数据
	 * @param key 解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] source, String key) {
		if (source == null) {
			return null;
		}
		int offset = 0;
		int length = source.length;
		return decrypt(source, offset, length, key);
	}

}
