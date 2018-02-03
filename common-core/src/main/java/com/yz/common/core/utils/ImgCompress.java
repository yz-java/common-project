package com.yz.common.core.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片压缩
 * 
 * @author yangzhao at 2015年11月30日
 *
 */
public class ImgCompress {

	private static final Logger logger=LogManager.getLogger(ImgCompress.class);

	private Image img;
	private int width;
	private int height;
	private String imgUrl;

	/**
	 * 构造函数
	 */
	public ImgCompress(File file, String imgUrl) throws IOException {
		// File file = new File(path);// 读入文件
		img = ImageIO.read(file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
		this.imgUrl = imgUrl;
	}

	/**
	 * 按照宽度还是高度进行压缩
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 */
	public void resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w);
		} else {
			resizeByHeight(h);
		}
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 */
	public void resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 */
	public void resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h);
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 */
	public boolean resize(int w, int h) {
		boolean flag = false;
		// SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File saveFile = new File(imgUrl);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(saveFile);
			// 可以正常实现bmp、png、gif转jpg
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image); // JPEG编码
			out.close();
			flag = true;
		} catch (Exception e) {
			logger.error("压缩图片失败！ ----", e);
		}
		return flag;
	}

	public static void main(String[] args) throws Exception {

		File file = new File("D:\\banner4.png");
		ImgCompress imgCom = new ImgCompress(file, "d:\\123.png");
		imgCom.resizeFix(1280, 720);

	}
}
