package com.yz.common.core.utils;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.util.GraphicsRenderingHints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * PDF工具类
 *
 * @auther yangzhao
 * create by 17/10/10
 */
public class PDFUtil {

    /**
     * pdf转png
     *
     * @param file        pdf文件
     * @param pngFilePath 生成png文件的路径
     * @param pageNum 转换页码（0代表所有页）
     * @return 返回png文件名
     * @throws Exception
     */
    public static String[] pdfToPng(String file, String pngFilePath, int pageNum) throws Exception {

        File pdf = new File(file);

        // set up the PDF reading
        RandomAccessFile raf = new RandomAccessFile(pdf, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
                channel.size());
        PDFFile pdfFile = new PDFFile(buf);

        String[]fileNameArray;

        File target = new File(pngFilePath);
        if (!target.exists()){
            target.mkdirs();
        }

        if (pageNum==0){
            // print出该pdf文档的页数
            pageNum = pdfFile.getNumPages();

            fileNameArray = new String[pageNum];

            for (int i=0;i<pageNum;i++){
                // 设置将第pageNum生成png图片
                PDFPage page = pdfFile.getPage(i);
                // create and configure a graphics object
                int width = (int) page.getBBox().getWidth();
                int height = (int) page.getBBox().getHeight();
                BufferedImage img = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // do the actual drawing
                PDFRenderer renderer = new PDFRenderer(page, g2, new Rectangle(0, 0,
                        width, height), null, Color.WHITE);
                page.waitForFinish();
                renderer.run();
                g2.dispose();
                String fileName = UUIDUtil.getUUID() + ".png";
                ImageIO.write(img, "png", new File(pngFilePath + fileName));
                img.flush();
                fileNameArray[i]=fileName;
            }
        }else{
            fileNameArray = new String[1];
            // 设置将第pageNum生成png图片
            PDFPage page = pdfFile.getPage(pageNum);
            // create and configure a graphics object
            int width = (int) page.getBBox().getWidth();
            int height = (int) page.getBBox().getHeight();
            BufferedImage img = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            // do the actual drawing
            PDFRenderer renderer = new PDFRenderer(page, g2, new Rectangle(0, 0,
                    width, height), null, Color.WHITE);
            page.waitForFinish();
            renderer.run();
            g2.dispose();
            String fileName = UUIDUtil.getUUID() + ".png";
            ImageIO.write(img, "png", new File(pngFilePath + fileName));
            img.flush();
            fileNameArray[0]=fileName;
        }

        return fileNameArray;
    }

    /**
     * ICEPDF转png
     *
     * @param file        pdf文件
     * @param pngFilePath 生成png文件的路径
     * @param pageNum 转换页码（0代表所有页）
     * @return 返回png文件名
     * @throws Exception
     */
    public static String[] icePdfToPng(String file, String pngFilePath, int pageNum) throws Exception {
        Document document = new Document();
        document.setFile(file);
        float scale = 2.5f;//缩放比例
        float rotation = 0f;//旋转角度

        String[]fileNameArray;

        File target = new File(pngFilePath);
        if (!target.exists()){
            target.mkdirs();
        }
        if (pageNum==0){
            pageNum = document.getNumberOfPages();
            fileNameArray = new String[pageNum];
            for (int i = 0; i < pageNum; i++) {
                BufferedImage image = (BufferedImage)
                        document.getPageImage(i, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
                RenderedImage rendImage = image;
                String imgName = UUIDUtil.getUUID() + ".png";
                ImageIO.write(rendImage, "png", new File(pngFilePath + imgName));
                image.flush();
                fileNameArray[i]=imgName;
            }
        }else {
            fileNameArray = new String[1];
            BufferedImage image = (BufferedImage)
                    document.getPageImage(pageNum, GraphicsRenderingHints.SCREEN, org.icepdf.core.pobjects.Page.BOUNDARY_CROPBOX, rotation, scale);
            RenderedImage rendImage = image;
            String imgName = UUIDUtil.getUUID() + ".png";
            ImageIO.write(rendImage, "png", new File(pngFilePath + imgName));
            image.flush();
            fileNameArray[0]=imgName;
        }
        document.dispose();
        return fileNameArray;
    }

}
