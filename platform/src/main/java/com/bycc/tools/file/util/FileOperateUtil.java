/**
 * 
 */
package com.bycc.tools.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.commons.io.FileUtils;
import org.smartframework.utils.helper.LogHelper;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description 文件操作-单线程
 * @author gaoningbo
 * @date 2017年5月5日
 * 
 */
public class FileOperateUtil {

	private static String TMPFILEPRE = "~$";// 临时文件前缀
	private static String TMPFILELAST = ".tmp";// 临时文件后缀

	/**
	 * NFS拷贝文件到指定目录下
	 * 
	 */
	public static boolean copyFile(String srcPathName, String fileName, String destPath,boolean test) {
		long srcFileSize = 0;// 源文件大小

		// 判断源文件是否存在
		File srcFile = new File(srcPathName);
		if (!srcFile.exists()) {
			return false;
		} else if (!srcFile.isFile()) {
			return false;
		}
		srcFileSize = srcFile.length();

		// 判断文件路径是否存在
		File destFile = new File(destPath);
		if (destFile.exists()) {
			long position = checkTmpFile(destPath, fileName, srcFileSize);
			if (position > 0) {
				return keepGoingNFS(srcPathName, fileName, destPath, position,test);
			} else {
				// 删除临时文件
				deleteTmpFile(destPath, fileName);
			}
		} else {
			if (!destFile.mkdirs()) {
				return false;
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;
		int position = 0;
		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(new File(destPath, fileName));
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
				position++;
				if (position % 1000 == 0) {
					createTmpFile(destPath, fileName, srcFileSize + "-" + position);
				}
			}
			// 上传成功，删除临时文件
			deleteTmpFile(destPath, fileName);
			// 删除源文件
			if (in != null)
				in.close();
			srcFile.delete();			
			return true;
		} catch (FileNotFoundException e) {
			LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			return false;
		} catch (IOException e) {
			// 出现异常，创建临时文件
			createTmpFile(destPath, fileName, srcFileSize + "-" + position);
			LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			}
		}
	}

	/**
	 * NFS续传
	 */
	private static boolean keepGoingNFS(String srcPathName, String fileName, String destPath, long position,boolean test) {

		RandomAccessFile readFile = null;
		RandomAccessFile writeFile = null;
		long srcFileSize = 0;
		try {
			readFile = new RandomAccessFile(srcPathName, "rw");
			if(test){
				writeFile = new RandomAccessFile(destPath + "\\" + fileName, "rw");
			}else{
				writeFile = new RandomAccessFile(destPath + "/" + fileName, "rw");
			}
			readFile.seek(position);
			writeFile.seek(position);
			srcFileSize = readFile.length();
			// 数据缓冲区
			byte[] buf = new byte[1024];
			// 数据读写
			while (readFile.read(buf) != -1) {
				writeFile.write(buf);
				position++;
				if (position % 1000 == 0) {
					createTmpFile(destPath, fileName, srcFileSize + "-" + position);
				}
			}
			deleteTmpFile(destPath, fileName);
			// 删除源文件
			if (readFile != null)
				readFile.close();
			File srcFile = new File(srcPathName);
			srcFile.delete();
			return true;
		} catch (FileNotFoundException e) {
			LogHelper.log(FileOperateUtil.class, 1, e.getMessage() + ",文件丢失，正在重新上传！");
			deleteTmpFile(destPath, fileName);
			return copyFile(srcPathName, fileName, destPath,test);
		} catch (IOException e) {
			LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			return false;
		} finally {
			try {
				if (readFile != null)
					readFile.close();
				if (writeFile != null)
					writeFile.close();
			} catch (IOException e) {
				LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
				return false;
			}
		}
	}

	/**
	 * HTTP续传
	 * 
	 * @throws IOException
	 */
	public static String saveFileByHttp(String destPath, String contentRange, MultipartFile file) {
		long uploadedSize = 0;// 已上传大小
		long srcFileSize = 0;// 源文件大小
		long startPosition = 0;// 开始位置
		long endPosition = 0;// 结束位置
		// int fileLength = 102400;
		OutputStream fs = null;
		RandomAccessFile randomFile = null;
		String fileName = file.getOriginalFilename();
		if (!new File(destPath).exists()) {
			new File(destPath).mkdirs();
		}
		if (contentRange == null) {
			try {
				FileUtils.writeByteArrayToFile(new File(destPath, fileName), file.getBytes());
				contentRange=file.getSize()+"-"+file.getSize();
			} catch (IOException e) {
				LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			}
		} else {
			// bytes 10000-19999/1157632 将获取到的数据进行处理截取出开始跟结束位置
			if (contentRange != null && contentRange.length() > 0) {
				try {
					contentRange = contentRange.replace("bytes", "").trim();
					srcFileSize = Long
							.parseLong(contentRange.substring(contentRange.indexOf("/") + 1, contentRange.length()));
					contentRange = contentRange.substring(0, contentRange.indexOf("/"));
					String[] ranges = contentRange.split("-");
					startPosition = Long.parseLong(ranges[0]);
					endPosition = Long.parseLong(ranges[1]);
				} catch (Exception e) {
					LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
				}
			}

			// 判断所上传文件是否已经存在，若存在则返回存在文件的大小
			try {
				File tmpFile = new File(destPath, fileName);
				randomFile = new RandomAccessFile(tmpFile, "rw");
				if (tmpFile.exists()) {
					// 检测临时文件
					long position = checkTmpFile(destPath, fileName, srcFileSize);
					if (position > 0) {
						// 续传
						uploadedSize = tmpFile.length();
					} else {
						// 重传
						tmpFile.delete();
						deleteTmpFile(destPath, fileName);
						uploadedSize = 0;
					}
				} else {
					uploadedSize = 0;
				}
				// 判断所上传文件片段是否存在
				if (uploadedSize > endPosition) {
					endPosition = uploadedSize - 1;
					contentRange = endPosition + "-" + endPosition;
				} else if (uploadedSize <= startPosition) {
					randomFile.seek(uploadedSize);
					randomFile.write(file.getBytes());
				} /*
					 * else if (uploadedSize > startPosition && uploadedSize <
					 * endPosition) { byte[] nbytes = new byte[fileLength]; int
					 * nReadSize = 0; file.getInputStream().skip(uploadedSize);
					 * int end = (int) (endPosition - uploadedSize); nReadSize =
					 * file.getInputStream().read(nbytes, 0, end); fs = new
					 * FileOutputStream(saveFilePath, true); if (nReadSize > 0)
					 * { fs.write(nbytes, 0, nReadSize); nReadSize =
					 * file.getInputStream().read(nbytes, 0, end); } }
					 */
			} catch (Exception e) {
				LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
			} finally {
				try {
					if (fs != null) {
						fs.flush();
						fs.close();
						fs = null;
					}
					if (randomFile != null) {
						randomFile.close();
						randomFile = null;
					}
				} catch (Exception e) {
					LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
				}
			}
			// 临时文件
			String content = srcFileSize + "-" + endPosition;
			if (srcFileSize - endPosition == 1) {
				// 上传结束，删除临时文件
				deleteTmpFile(destPath, fileName);
			} else {
				createTmpFile(destPath, fileName, content);
			}
		}

		return contentRange;
	}

	/**
	 * 检测是否存在临时文件
	 */
	private static long checkTmpFile(String destPath, String fileName, long srcFileSize) {

		File file = new File(destPath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].getName().startsWith(TMPFILEPRE + fileName)
					&& tempFile[i].getName().endsWith(TMPFILELAST)) {
				// 存在临时文件，返回内容
				String content = tempFile[i].getName().substring((TMPFILEPRE + fileName).length(),
						tempFile[i].getName().length() - TMPFILELAST.length());
				if (content.length() > 0) {
					String[] contents = content.split("-");
					if (contents.length == 2) {
						long size = Long.parseLong(contents[0]);
						long position = Long.parseLong(contents[1]);
						if (srcFileSize == size) {
							// 文件未损坏,可继续上传
							return position;
						} else {
							// 文件损坏重新上传，删除临时文件和已上传的文件
							// deleteTmpFile(destPath,fileName);
						}
					} else {
						// 临时文件错误，删除临时文件和已上传的文件
						// deleteTmpFile(destPath,fileName);
					}
				} else {
					// 临时文件错误，删除临时文件和已上传的文件
				}
			}
		}
		return -1;
	}

	/**
	 * 删除临时文件
	 */
	private static void deleteTmpFile(String destPath, String fileName) {

		File file = new File(destPath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].getName().startsWith(TMPFILEPRE + fileName)
					&& tempFile[i].getName().endsWith(TMPFILELAST)) {
				tempFile[i].delete();
			}
		}
	}

	/**
	 * 创建临时文件
	 */
	private static boolean createTmpFile(String destPath, String fileName, String content) {
		File file = new File(destPath);
		File[] tempFile = file.listFiles();
		for (int i = 0; i < tempFile.length; i++) {
			if (tempFile[i].getName().startsWith(TMPFILEPRE + fileName)
					&& tempFile[i].getName().endsWith(TMPFILELAST)) {
				// 重命名临时文件
				return tempFile[i].renameTo(new File(destPath, TMPFILEPRE + fileName + content + TMPFILELAST));
			}
		}
		try {
			// 新建临时文件
			return new File(destPath, TMPFILEPRE + fileName + content + TMPFILELAST).createNewFile();
		} catch (IOException e) {
			LogHelper.log(FileOperateUtil.class, 1, e.getMessage());
		}
		return false;
	}
	/**
	 * 保存上传状态
	 * 
	 * /** 存储实时上传状态
	 */
	/*
	 * private static void saveUploadStatus(String key,Progress progress){
	 * System.out.println("readByte:"+progress.getBytesRead());
	 * MediaUploadStatus mediaUploadStatus=new MediaUploadStatus();
	 * List<MediaTreeListDto> dtos=mediaUploadStatus.get(key);
	 * for(MediaTreeListDto dto:dtos){
	 * if(dto.getPath()==progress.getFilePathName()){
	 * dto.setUploadStatus(progress); } } }
	 */
}
