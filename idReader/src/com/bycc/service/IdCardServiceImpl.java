package com.bycc.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bycc.domain.IDCard;
import com.bycc.utils.IDCardParser;
import com.sun.jna.Library;
import com.sun.jna.Native;

public class IdCardServiceImpl implements IdCardService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	// USB1端口号
	private static final int PORT_USB1 = 1001;

	// 找卡成功状态码
	private static final int FIND_CARD_SUCCESS = 0x9f;

	// 打开端口成功状态码
	private static final int OPEN_PORT_SUCCESS = 0x90;

	// 关闭端口成功状态码
	private static final int CLOSE_PORT_SUCCESS = 0x90;

	// 选卡成功状态码
	private static final int SELECT_CARD_SUCCESS = 0x90;

	// 读卡成功状态码
	private static final int READ_CARD_SUCCESS = 0x90;

	// 照片解码成功状态码
	private static final int PHOTO_DECODE_SUCCESS = 1;

	// 身份证照片临时名称
	private static final String PHOTO_TMP_NAME = "photo.wlt";

	// 身份证照片解码后的名称
	private static final String PHOTO_NAME = "photo.bmp";

	@Override
	public IDCard read() throws Exception {
		// 加载动态库
		System.load(this.getClass().getClassLoader().getResource("sdtapi.dll").getPath());
		System.load(this.getClass().getClassLoader().getResource("WltRS.dll").getPath());

		// 打开端口
		int code = SDTAPI.SYNC_INSTANCE.SDT_OpenPort(PORT_USB1);
		if (code != OPEN_PORT_SUCCESS) {
			logger.debug("打开端口失败，错误码：{}", code);
			SDTAPI.SYNC_INSTANCE.SDT_ClosePort(PORT_USB1);
			throw new Exception("打开端口失败，错误码:" + code);
		}
		logger.debug("打开端口成功");

		// 找卡
		byte[] msg = new byte[8];
		code = SDTAPI.SYNC_INSTANCE.SDT_StartFindIDCard(PORT_USB1, msg, 0);
		if (code != FIND_CARD_SUCCESS) {
			logger.debug("找卡失败，错误码：{}", code);
			SDTAPI.SYNC_INSTANCE.SDT_ClosePort(PORT_USB1);
			throw new Exception("找卡失败，错误码:" + code);
		}
		logger.debug("找卡成功");

		// 选卡
		code = SDTAPI.SYNC_INSTANCE.SDT_SelectIDCard(PORT_USB1, new byte[8], 0);
		if (code != SELECT_CARD_SUCCESS) {
			logger.debug("选卡失败，错误码：{}", code);
			SDTAPI.SYNC_INSTANCE.SDT_ClosePort(PORT_USB1);
			throw new Exception("选卡失败，错误码:" + code);
		}
		logger.debug("选卡成功");

		// 读卡
		char[] content = new char[256];
		byte[] photoBytes = new byte[1024];
		code = SDTAPI.SYNC_INSTANCE.SDT_ReadBaseMsg(PORT_USB1, content, new int[256], photoBytes, new int[1024], 0);
		if (code != READ_CARD_SUCCESS) {
			logger.debug("读卡失败，错误码：{}", code);
			SDTAPI.SYNC_INSTANCE.SDT_ClosePort(PORT_USB1);
			throw new Exception("读卡失败，错误码:" + code);
		}
		logger.debug("读卡成功");

		// 关闭端口
		code = SDTAPI.SYNC_INSTANCE.SDT_ClosePort(PORT_USB1);
		if (code != CLOSE_PORT_SUCCESS) {
			logger.debug("关闭端口失败，错误码：{}", code);
			throw new Exception("关闭端口失败，错误码:" + code);
		}
		logger.debug("关闭端口成功");

		// 获取程序根目录
		String baseDir = System.getProperty("user.dir");
		// 创建临时目录
		String tmpDir = baseDir + "\\tmp";
		if (!Files.exists(new File(tmpDir).toPath())) {
			logger.debug("创建临时目录：{}", tmpDir);
			Files.createDirectory(new File(tmpDir).toPath());
		}

		// 删除旧的照片信息
		File tmpPhoto = new File(tmpDir + "\\" + PHOTO_TMP_NAME);
		if (tmpPhoto.exists()) {
			if (!tmpPhoto.delete()) {
				logger.error("无法删除照片文件{}", tmpDir + "\\" + PHOTO_TMP_NAME);
			}
		}

		// 保存照片信息
		FileOutputStream fStream = new FileOutputStream(tmpPhoto);
		BufferedOutputStream bos = new BufferedOutputStream(fStream);
		bos.write(photoBytes);
		bos.close();

		// 解析身份证信息
		IDCard idCard = IDCardParser.parseIdCard(new String(content));
		logger.debug("解析基本信息：{}", idCard);

		// 解析照片
		code = WltRS.SYNC_INSTANCE.GetBmp(tmpDir + "\\" + PHOTO_TMP_NAME, 2);
		if (code != PHOTO_DECODE_SUCCESS) {
			logger.debug("照片解码失败，错误码：{}", code);
			throw new Exception("照片解码失败，错误码:" + code);
		}
		logger.debug("照片解码成功");
		File photo = new File(tmpDir + "\\" + PHOTO_NAME);
		logger.debug("base64编码照片: {}", Base64.getEncoder().encodeToString(Files.readAllBytes(photo.toPath())));
		idCard.setPhoto(Base64.getEncoder().encodeToString(Files.readAllBytes(photo.toPath())));
		return idCard;
	}

	// 读卡API
	public interface SDTAPI extends Library {
		// 打开端口
		int SDT_OpenPort(int port);

		// 关闭端口
		int SDT_ClosePort(int port);

		// 找卡
		int SDT_StartFindIDCard(int port, byte[] pucManaInfo, int ifOpen);

		// 选卡
		int SDT_SelectIDCard(int port, byte[] pucManaMsg, int ifOpen);

		// 读卡
		int SDT_ReadBaseMsg(int port, char[] content, int[] contentLength, byte[] photo, int[] photoLength, int ifOpen);

		SDTAPI INSTANCE = Native.loadLibrary("sdtapi", SDTAPI.class);

		SDTAPI SYNC_INSTANCE = (SDTAPI) Native.synchronizedLibrary(INSTANCE);
	}

	// 身份证照片转换API
	public interface WltRS extends Library {
		// 照片解码
		// 参数1：wlt文件路径，参数2：接口，1-com口，2-usb
		int GetBmp(String Wlt_File, int intf);

		WltRS INSTANCE = Native.loadLibrary("WltRS", WltRS.class);

		WltRS SYNC_INSTANCE = (WltRS) Native.synchronizedLibrary(INSTANCE);
	}
}
