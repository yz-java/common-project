package com.yz.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.imageio.ImageIO;

public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final Logger logger= LoggerFactory.getLogger(FileUtils.class);

	/**
	 * 读取json文件转对象
	 * @param path	:项目根目录下的路径
	 * @param clazz
	 * @return
	 */
	public static <T> T readJsonFileToObject(String path, Class<T> clazz) {
		path = FileUtils.class.getResource(path).getFile().toString();
		File file = new File(path);
		try {
			String data = readFileToString(file);
			return com.alibaba.fastjson.JSON.parseObject(data,clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean delFile(String path) {
		try {
			File file = new File(path);
			file.delete();
			return true;
		} catch (Exception e) {
			logger.error("文件删除失败---- 路径：" + path);
		}
		return false;
	}
	/**
	 * NIO模式读取
	 * @param path
	 * @param model
	 * @return
	 */
	public static byte[] readFile(String path, String model) {
		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(path, model);
			FileChannel channel = file.getChannel();
			int size = (int) channel.size();
			ByteBuffer buffer = ByteBuffer.allocate(size);
			channel.read(buffer);
			byte[] array = buffer.array();
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * BIO模式读取
	 * @param path
	 * @param fileSize
	 * @return
	 */
	public static byte[] readFile(String path, int fileSize) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			byte[] data = new byte[fileSize];
			fis.read(data);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取图片的宽和高
	 * @param inputStream
	 * @return
     */
	public static String getImageProperty(InputStream inputStream){
		try {
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			logger.info("宽度:"+width+"----"+"高度:"+height);
			return width+","+height;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取图片的宽和高
	 * @param file
	 * @return
     */
	public static String getImageProperty(File file){
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			logger.info("宽度:"+width+"----"+"高度:"+height);
			return width+","+height;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改文件名称
	 * @param path
	 * @param newFileName
	 */
	public static void updateName(String path,String newFileName){
		File oldFile = new File(path);
		String parent = oldFile.getParent();
		File newFile = new File(parent+"/"+newFileName);
		oldFile.renameTo(newFile);
	}

	/**
	 * 递归读取文件所在路径
	 * @param filePath 路径
	 * @param fileName 文件名
	 * @return
	 */
	public static String recursionReadFile(String filePath,String fileName){
		File file = new File(filePath);
		File[] files = file.listFiles();
		for (File f:files){
			if (f.getName().equals(fileName)){
				return file.getAbsolutePath()+"/"+f.getName();
			}
		}

		for (File f:files){
			String readPath=filePath+"/"+f.getName();
			file = new File(readPath);
			if (file.isDirectory()){
				return recursionReadFile(readPath, fileName);
			}
		}

		return null;
	}
}
