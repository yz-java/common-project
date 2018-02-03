package com.yz.common.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 图片处理
 * 服务器需要安装GraphicsMagick
 * @author yangzhao 2016年2月29日
 * 
 */
public class GraphicsMagick {
	private static final Logger logger=LogManager.getLogger(GraphicsMagick.class);

	/**
	 * 缩放图片
	 * @param width
	 * @param height
	 * @param srcPath
	 * @param newPath
	 * @param type 1=按像素缩放 2=按百分比
	 * @return
	 */
	public static boolean thumbnail(int width, int height, String srcPath, String newPath, int type) {
		String raw = "";
		if (type == 1) {
			raw = width + "x" + height ;
		} else {
			raw = width + "%x" + height + "%";
		}
		try {
			Runtime.getRuntime().exec("gm convert -resize  " + raw + "  -gravity Center -quality 99 " + srcPath + " " + newPath);
		} catch (Exception e) {
			logger.error("缩放图片失败！----", e);
			return false;
		}
		return true;
	}

	/**
	 * 裁剪图片
	 * @param width 剪裁后的宽度
	 * @param height 剪裁后的高度
	 * @param x	坐标X
	 * @param y 坐标Y
	 * @param srcPath
	 * @param newPath
	 * @return
	 */
	public static boolean cutImage(int width, int height, int x, int y, String srcPath, String newPath) {
		try {
			Runtime.getRuntime().exec("gm convert -crop " + width + "x" + height + "+" + x + "+" + y + srcPath + " " + newPath);
			return true;
		} catch (Exception e) {
			logger.error("裁剪图片失败！----", e);
		}
		return false;
	}

	/**
	 * 旋转图片
	 * @param degree 旋转角度
	 * @param srcPath
	 * @param newPath
	 * @return
	 */
	public static boolean rotateImage(int degree, String srcPath, String newPath) {
		try {
			Runtime.getRuntime().exec("gm convert -rotate " + degree + " " + srcPath + " " + newPath);
			return true;
		} catch (Exception e) {
			logger.error("旋转图片失败！----", e);
		}
		return false;
	}

	/**
	 * 把图片变为黑白颜色
	 * @param srcPath
	 * @param newPath
	 * @return
	 */
	public static boolean imageBlackAndWhite(String srcPath, String newPath) {
		try {
			Runtime.getRuntime().exec("gm convert -monochrome " + srcPath + " " + newPath);
			return true;
		} catch (Exception e) {
			logger.error("图片变为黑白颜色失败！----", e);
		}
		return false;
	}
}