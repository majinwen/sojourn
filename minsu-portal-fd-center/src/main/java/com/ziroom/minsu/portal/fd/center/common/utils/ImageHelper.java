package com.ziroom.minsu.portal.fd.center.common.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.alibaba.dubbo.container.Main;

/**
 * 
 * <p>图片裁剪</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class ImageHelper {
	/*
	 * 根据尺寸图片居中裁剪
	 */
	 @SuppressWarnings("rawtypes")
	public static void cutCenterImage(String src,String dest,int w,int h) throws IOException{ 
		 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         Rectangle rect = new Rectangle((reader.getWidth(imageIndex)-w)/2, (reader.getHeight(imageIndex)-h)/2, w, h);  
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));           
  
	 }
	/*
	 * 图片裁剪二分之一
	 */
	 @SuppressWarnings("rawtypes")
	public static void cutHalfImage(String src,String dest) throws IOException{ 
		 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg"); 
         ImageReader reader = (ImageReader)iterator.next(); 
         InputStream in=new FileInputStream(src);
         ImageInputStream iis = ImageIO.createImageInputStream(in); 
         reader.setInput(iis, true); 
         ImageReadParam param = reader.getDefaultReadParam(); 
         int imageIndex = 0; 
         int width = reader.getWidth(imageIndex)/2; 
         int height = reader.getHeight(imageIndex)/2; 
         Rectangle rect = new Rectangle(width/2, height/2, width, height); 
         param.setSourceRegion(rect); 
         BufferedImage bi = reader.read(0,param);   
         ImageIO.write(bi, "jpg", new File(dest));   
	 }
	/*
	 * 图片裁剪通用接口
	 */

	 @SuppressWarnings("rawtypes")
	public static void cutImage(String src,String dest,int x,int y,int w,int h) throws IOException{   
         Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");   
         ImageReader reader = (ImageReader)iterator.next();   
         InputStream in=new FileInputStream(src);  
         ImageInputStream iis = ImageIO.createImageInputStream(in);   
         reader.setInput(iis, true);   
         ImageReadParam param = reader.getDefaultReadParam();   
         Rectangle rect = new Rectangle(x, y, w,h);    
         param.setSourceRegion(rect);   
         BufferedImage bi = reader.read(0,param);     
         ImageIO.write(bi, "jpg", new File(dest));             

	 }   
    /*
     * 图片缩放
     */
    @SuppressWarnings("static-access")
	public static void zoomImage(String src,String dest,int w,int h) throws Exception {
		double wr=0,hr=0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		BufferedImage bufImg = ImageIO.read(srcFile);
		Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
		wr=w*1.0/bufImg.getWidth();
		hr=h*1.0 / bufImg.getHeight();
		AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
		Itemp = ato.filter(bufImg, null);
		try {
			ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    //创建截取图
    public static void createPreviewImageofCut(InputStream input,ByteArrayOutputStream out,int x,int y,int w,int h) throws IOException { 
    	BufferedImage bis = ImageIO.read(input);
        //四个参数分别为图像起点坐标和宽高，即CropImageFilter(int x,int y,int width,int height)，详细情况请参考API
        ImageFilter cropFilter =new CropImageFilter(x,y,w,h);
        //获得截图图片
        Image croppedImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(bis.getSource(),cropFilter));
        //Image 转为  BufferedImage
        BufferedImage bufImg = new BufferedImage(croppedImage.getWidth(null), croppedImage.getHeight(null),BufferedImage.TYPE_INT_RGB);   
        bufImg .createGraphics().drawImage(croppedImage, 0, 0, null);  
        //输出为图片文件
        ImageIO.write(bufImg, "jpeg", out);        
    }
    
    
    /**
     * 
     * 旋转图片
     *
     * @author jixd
     * @created 2016年8月16日 下午9:52:16
     *
     * @param image
     * @param degree
     * @param bgcolor
     * @return
     * @throws IOException
     */
    public static void rotateImg(InputStream in,ByteArrayOutputStream out, int degree, Color bgcolor) throws IOException { 
    	BufferedImage image = ImageIO.read(in);
        int iw = image.getWidth();//原始图象的宽度 
        int ih = image.getHeight();//原始图象的高度 
        int w = 0; 
        int h = 0; 
        int x = 0; 
        int y = 0; 
        degree = degree % 360; 
        if (degree < 0) 
            degree = 360 + degree;//将角度转换到0-360度之间 
        double ang = Math.toRadians(degree);//将角度转为弧度 
        /**
         *确定旋转后的图象的高度和宽度
         */ 
        if (degree == 180 || degree == 0 || degree == 360) { 
            w = iw; 
            h = ih; 
        } else if (degree == 90 || degree == 270) { 
            w = ih; 
            h = iw; 
        } else { 
            double cosVal = Math.abs(Math.cos(ang));
            double sinVal = Math.abs(Math.sin(ang));
            w = (int) (sinVal*ih) + (int) (cosVal*iw);
            h = (int) (sinVal*iw) + (int) (cosVal*ih);
        } 
  
        x = (w / 2) - (iw / 2);//确定原点坐标 
        y = (h / 2) - (ih / 2); 
        BufferedImage rotatedImage = new BufferedImage(w, h, image.getType()); 
        Graphics2D gs = (Graphics2D)rotatedImage.getGraphics(); 
        if(bgcolor==null){ 
            rotatedImage = gs.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT); 
        }else{ 
            gs.setColor(bgcolor); 
            gs.fillRect(0, 0, w, h);//以给定颜色绘制旋转后图片的背景 
        } 
          
        AffineTransform at = new AffineTransform(); 
        at.rotate(ang, w / 2, h / 2);//旋转图象 
        at.translate(x, y); 
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC); 
        op.filter(image, rotatedImage); 
        image = rotatedImage; 
          
        ImageIO.write(image, "png", out); 
    } 
    
    public static void main(String[] args) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			rotateImg(new FileInputStream("E:\\4.jpg"), out, 40, null);
			DataOutputStream to=new DataOutputStream(new FileOutputStream(new File("E:\\5.jpg")));
			out.writeTo(to);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}