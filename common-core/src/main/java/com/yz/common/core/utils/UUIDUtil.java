package com.yz.common.core.utils;

import java.util.UUID;

public class UUIDUtil {
	/**
	 * 获取字符串UUID（除掉"-"）
	 * @return
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}
}
