package org.smartframework.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * @description 生成验证码工具类
 * @author liuguangyin
 * @date 2014年5月12日 下午2:04:11
 */
public final class CaptchaUtil {
	private static final String[] chars = { "0", "1", "2", "3", "4", "5", 
			"6","7", "8", "9", "A","a", "B","b",
			"C","c", "D","d", "E","e", "F","f",
			"G","g", "H","h", "I","i", "J","j",
			"K","k", "L","l", "M","m", "N","n", 
			"O","o", "P","p", "Q","q", "R","r", 
			"T","t", "U","u", "V","v", "W","w", 
			"X","x", "Y","y", "Z","z"};
	private static final int SIZE = 4;//显示字符个数
	private static final int LINES = 5;//干扰线数量
	private static final int WIDTH = 80;//图片宽度
	private static final int HEIGHT = 30;//图片高度
	private static final int FONT_SIZE = 20;//字体大小
	
	public static Map<String,BufferedImage> createImage() {
		StringBuffer sb = new StringBuffer();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphic = image.getGraphics();
		graphic.setColor(Color.LIGHT_GRAY);
		graphic.fillRect(0, 0, WIDTH, HEIGHT);
		Random ran = new Random();
		//画随机字符
		for(int i=1;i<=SIZE;i++){
			int r = ran.nextInt(chars.length);
			graphic.setColor(getRandomColor());
			graphic.setFont(new Font(null,Font.BOLD+Font.ITALIC,FONT_SIZE));
			graphic.drawString(chars[r],(i-1)*WIDTH/SIZE , 22);
			sb.append(chars[r]);//将字符保存，存入Session
		}
		//画干扰线
		for(int i=1;i<=LINES;i++){
			graphic.setColor(getRandomColor());
			graphic.drawLine(ran.nextInt(WIDTH), ran.nextInt(HEIGHT), ran.nextInt(WIDTH),ran.nextInt(HEIGHT));
		}
		Map<String,BufferedImage> map = new HashMap<String,BufferedImage>();
		map.put(sb.toString(), image);
		return map;
	}
	
	public static InputStream change(BufferedImage image) throws Exception{
		//将image图片压缩成JPEG
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		JPEGImageEncoder encode = JPEGCodec.createJPEGEncoder(bos);
//		encode.encode(image);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(image,  "jpeg" , bos); 
		
		//将bos中存储的JPEG格式图片字节取出
		byte[] bytes = bos.toByteArray();
		return new ByteArrayInputStream(bytes);
	}
	
	private static Color getRandomColor(){
		Random ran = new Random();
		Color color = new Color(ran.nextInt(256),ran.nextInt(256),ran.nextInt(256));
		return color;
	}
}
