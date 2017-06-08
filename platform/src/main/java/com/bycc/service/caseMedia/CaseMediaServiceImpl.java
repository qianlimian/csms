/**
 * 
 */
package com.bycc.service.caseMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.smartframework.utils.helper.DateHelper;
import org.smartframework.utils.helper.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.bycc.dao.BdmPoliceDao;
import com.bycc.dao.BdmRoomDao;
import com.bycc.dao.BdmVideoCgDao;
import com.bycc.dao.CaseMediaDao;
import com.bycc.dao.CasePeopleDao;
import com.bycc.dao.CaseRecordDao;
import com.bycc.dto.caseMedia.CaseMediaDto;
import com.bycc.dto.caseMedia.MediaTreeListDto;
import com.bycc.entity.BdmPolice;
import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmVideoCategory;
import com.bycc.entity.CaseMedia;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CaseRecord;
import com.bycc.tools.file.util.FileOperateUtil;
import com.bycc.tools.file.util.FilePropertyUtil;

/**
 * @description
 * @author gaoningbo
 * @date 2017年5月3日
 * 
 */
@Service
public class CaseMediaServiceImpl implements CaseMediaService {

	@Autowired
	CaseRecordDao crDao;
	@Autowired
	CasePeopleDao cpDao;
	@Autowired
	CaseMediaDao caseMediaDao;
	@Autowired
	BdmPoliceDao bdmPoliceDao;
	@Autowired
	CasePeopleDao casePeopleDao;
	@Autowired
	BdmVideoCgDao bdmVideoCgDao;
	@Autowired
	BdmRoomDao bdmRoomDao;
	
	private Logger logger = LoggerFactory.getLogger(CaseMediaServiceImpl.class);
	
	private static boolean TEST = Boolean.valueOf(FilePropertyUtil.getValueByKey("file.test"));
	private static String SRC_PATH = FilePropertyUtil.getValueByKey("file.srcPath");
	private static String LOCAL_SHARE_DIR = FilePropertyUtil.getValueByKey("file.localShareDir");
	private static String DEST_PATH = FilePropertyUtil.getValueByKey("file.destPath");
	private static String REMOTE_LOCAL = FilePropertyUtil.getValueByKey("file.remoteLocal");
	private static String REMOTE_SERVER_IP = FilePropertyUtil.getValueByKey("file.remoteIp");
	private static String REMOTE_SHARE_DIR = FilePropertyUtil.getValueByKey("file.remoteShareDir");// 市局流媒体共享目录

	/**
	 * 获取派出所流媒体服务器视频列表
	 */
	public List<MediaTreeListDto> getLocalMediaList(Integer caseRecordId, String path) {
		String rootPath = null;
		boolean test = TEST;
		String localShareDir = LOCAL_SHARE_DIR;//挂载目录
		String ip = null;// 派出所流媒体IP
		String caseName = null;
		String tmp = null;//临时值
		
		if (test) {
			rootPath = FilePropertyUtil.getTestSrcPath();
		} else {
			rootPath = SRC_PATH;// 映射到市局服务器中的目录
		}
		tmp = rootPath;

		if (rootPath == null) {
			// logger.error("未设置派出所流媒体服务器目录");
			return null;
		}
		// 获取当前用户id
		Integer userId = User.getCurrentUser().getUserId();
		BdmPolice police = bdmPoliceDao.findByUserId(userId);
		if (police != null) {
			if (police.getPoliceStation() != null) {
				ip = police.getPoliceStation().getIp();
			}
		}
		if (police == null) {
			// logger.error("不存在警局,当前用户ID："+userId);
			return null;
		}
		CaseRecord cr = crDao.findOne(caseRecordId);
		caseName = cr.getCaseName();
		if (path == null) {
			if (test) {
				rootPath = rootPath + "\\" + caseName;
			} else {
				rootPath = rootPath + "/" + caseName;
			}
		} else {
			rootPath = path;
		}
		File file = new File(rootPath);
		if (file.isFile()) {
			// logger.error("该路径是文件！");
			return null;
		} else {
			List<MediaTreeListDto> dtos = new ArrayList<>();
			File[] fileLists = file.listFiles(); // 如果是目录，获取该目录下的内容集合
			if (fileLists == null) {
				// logger.error("文件夹为空！");
				return null;
			}
			for (int i = 0; i < fileLists.length; i++) {
				MediaTreeListDto dto = MediaTreeListDto.toDto(fileLists[i]);
				dto.setCaseRecordId(caseRecordId);
				if (fileLists[i].isFile()) {
					if (test) {
						dto.setServerPath("videoSrc" + fileLists[i].getPath().substring(tmp.length()).replace("\\", "/"));
					} else {
						// 派出所流媒体服务器地址
						String[] paths = fileLists[i].getPath().split(caseName);
						String relativePath = paths[1].replace("\\", "/");
						dto.setServerPath("http://" + ip + localShareDir + "/" + caseName + relativePath);
					}
					String mediaName = fileLists[i].getName().split("\\.")[0];
					// 解析视频名称
					String[] mediaInfos = mediaName.split("_");
					if (mediaInfos.length == 3) {
						String code = mediaInfos[0];						
						//房间类别
						BdmRoom room=bdmRoomDao.findByCode(code);
						dto.setRoomCategory(room!=null?room.getName():null);
						//时间
						Date startDate = new Date(Long.parseLong(mediaInfos[1]));
						Date endDate = new Date(Long.parseLong(mediaInfos[2]));
						dto.setStartDate(DateHelper.formatDateToString(startDate, "yyyy-MM-dd hh:mm:ss"));
						dto.setEndDate(DateHelper.formatDateToString(endDate, "yyyy-MM-dd hh:mm:ss"));
					}
				}
				dtos.add(dto);
			}
			return dtos;
		}
	}

	/**
	 * NFS拷贝文件
	 */
	public void copyFilesNFS(List<MediaTreeListDto> checkedMedias) {
		String destDir = null;
		String area = null;// 地区
		String unitName = null;// 派出所
		String caseName = null;// 案件
		
		boolean test = TEST;
		if (test) {
			destDir = FilePropertyUtil.getTestDestPath();
		} else {
			destDir = DEST_PATH;
		}
		String tmp = destDir;
		
		for (MediaTreeListDto media : checkedMedias) {
			if (media.getMappingPath() != null && media.getCaseRecordId() != null) {
				// 获得目录
				CaseRecord cr = crDao.findOne(media.getCaseRecordId());
				caseName = cr.getCaseName();
				String[] path = media.getMappingPath().split(cr.getCaseName());
				String[] casePeopleName = null;
				if (test) {
					casePeopleName = path[1].split("\\\\");
				} else {
					casePeopleName = path[1].split("/");
				}
				
				List<CasePeople> lists = casePeopleDao.findByCaseRecordId(cr.getId());
				CasePeople suspect = null;// 嫌疑人
				for (CasePeople casePeople : lists) {
					if (casePeople.getName().equals(casePeopleName[1])) {
						suspect = casePeople;
						break;
					}
				}
				if (cr != null) {
					// 获取当前用户id
					Integer userId = User.getCurrentUser().getUserId();
					BdmPolice police = bdmPoliceDao.findByUserId(userId);
					if (police != null) {
						if (police.getPoliceStation() != null) {
							area = police.getPoliceStation().getAreaType().value();
							unitName = police.getPoliceStation().getName();
							if (area != null && unitName != null) {								
								if (test) {
									destDir = destDir + "\\" + area + "\\" + unitName + "\\" + caseName + "\\"
											+ casePeopleName[1];
								} else {
									destDir = destDir + "/" + area + "/" + unitName + "/" + caseName + "/"
											+ casePeopleName[1];
								}
								boolean result = FileOperateUtil.copyFile(media.getMappingPath(), media.getLabel(),
										destDir, test);
								if (result) {
									BdmVideoCategory cg = null;
									//String categoryCode = media.getLabel().split("_")[0];									
									destDir = destDir.substring(tmp.length());
									cg = bdmVideoCgDao.findByName(media.getMediaCategory());
									CaseMedia entity = caseMediaDao.findByCaseRecordIdAndTitle(cr.getId(),
											media.getLabel());
									Integer id = entity == null ? null : entity.getId();
									CaseMedia caseMedia = CaseMediaDto.toEntity(id, media.getLabel(), destDir, cg,
											suspect, cr, media.getNote());
									caseMediaDao.save(caseMedia);
								}
							}
						}
					}
				}

			}
		}
	}

	/**
	 * HTTP断点上传
	 * 
	 * @throws IOException
	 */
	public String saveFiles(MultipartFile uploadedFile, String contentRange, Integer caseRecordId, String categoryName)
			throws IOException {
		String destPath = null;//目的路径
		String remoteLocal = REMOTE_LOCAL;// 本地文件存放文件夹
		String area = null;// 地区
		String unitName = null;// 派出所
		String caseName = null;// 案件
		
		boolean test = TEST;
		if (test) {
			destPath = FilePropertyUtil.getTestDestPath();
		} else {
			destPath = DEST_PATH;
		}
		String tmp = destPath;
		
		CaseRecord cr = crDao.findOne(caseRecordId);
		if (cr != null) {
			caseName = cr.getCaseName();
			Integer userId = User.getCurrentUser().getUserId();
			BdmPolice police = bdmPoliceDao.findByUserId(userId);
			if (police != null) {
				if (police.getPoliceStation() != null) {
					area = police.getPoliceStation().getAreaType().value();
					unitName = police.getPoliceStation().getName();
				}
			}
			if (test) {
				destPath = destPath + "\\" + area + "\\" + unitName + "\\" + caseName + "\\" + remoteLocal;
			} else {
				destPath = destPath + "/" + area + "/" + unitName + "/" + caseName + "/" + remoteLocal;
			}

			String range = FileOperateUtil.saveFileByHttp(destPath, contentRange, uploadedFile);
			if (range == null) {
				return null;
			} else {
				String[] ranges = range.split("-");
				// long startPosition = Long.parseLong(ranges[0]);
				long endPosition = Long.parseLong(ranges[1]);
				long srcFileSize = 0;
				if (contentRange == null) {
					srcFileSize = endPosition;
				} else {
					contentRange = contentRange.replace("bytes", "").trim();
					srcFileSize = Long
							.parseLong(contentRange.substring(contentRange.indexOf("/") + 1, contentRange.length()));
				}
				if (srcFileSize - endPosition > 1) {
					return range;
				} else {
					// 上传完成
					String fileName = uploadedFile.getOriginalFilename();
					destPath = destPath.substring(tmp.length());
					CaseMedia entity = caseMediaDao.findByCaseRecordIdAndTitle(caseRecordId, fileName);
					CaseMedia caseMedia = null;
					Integer id = entity == null ? null : entity.getId();
					caseMedia = CaseMediaDto.toEntity(id, fileName, destPath, bdmVideoCgDao.findByName(categoryName),
							null, cr, null);					
					caseMediaDao.save(caseMedia);
					return range;
				}
			}
		}
		return null;
	}

	/**
	 * 获取市局流媒体服务器视频列表
	 */
	public List<MediaTreeListDto> getRemoteMediaList(Integer caseRecordId, String path) {
		String destPath = null;
		boolean test =TEST;
		String remoteServerIp = REMOTE_SERVER_IP;// 市局流媒体地址
		String remoteShareDir = REMOTE_SHARE_DIR;// 市局流媒体共享目录
		String remoteLocal = REMOTE_LOCAL;// 本地上传视频所在目录
		
		if (test) {
			destPath = FilePropertyUtil.getTestDestPath();
		} else {
			destPath = DEST_PATH;
		}
		String tmp = destPath;

		if (path == null) {
			if (destPath == null) {
				// logger.info("未设置市局流媒体服务器目录");
				return null;
			}
			CaseRecord cr = crDao.findOne(caseRecordId);
			if (cr != null && cr.getMasterUnit() != null) {
				String caseName = cr.getCaseName();
				String area = cr.getMasterUnit().getAreaType().value();
				String unitName = cr.getMasterUnit().getName();
				if (test) {
					destPath = destPath + "\\" + area + "\\" + unitName + "\\" + caseName;
				} else {
					destPath = destPath + "/" + area + "/" + unitName + "/" + caseName;
				}

			} else {
				// logger.info("不存在这样办案记录！");
				return null;
			}
		} else {
			destPath = path;
		}
		File file = new File(destPath);
		if (file.isFile()) {
			return null;
		} else {
			List<MediaTreeListDto> dtos = new ArrayList<>();
			File[] fileLists = file.listFiles(); // 如果是目录，获取该目录下的内容集合
			if (fileLists == null) {
				return null;
			}
			for (int i = 0; i < fileLists.length; i++) {
				MediaTreeListDto dto = MediaTreeListDto.toDto(fileLists[i]);
				dto.setCaseRecordId(caseRecordId);
				// String
				// serverPath="http://"+serverIp+cityShareDir+fileLists[i].getPath().split(tmp)[1];
				// 测试
				String serverPath = null;
				if (test) {
					serverPath = "video" + fileLists[i].getPath().substring(tmp.length()).replace("\\", "/");
				} else {
					serverPath = "http://" + remoteServerIp + remoteShareDir + fileLists[i].getPath().substring(tmp.length());
				}
				dto.setServerPath(serverPath);
				CaseMedia entity = caseMediaDao.findByCaseRecordIdAndTitle(caseRecordId, fileLists[i].getName());
				if (entity != null) {
					dto.setMediaCategory(entity.getCategory() != null ? entity.getCategory().getName() : null);
					dto.setStartDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd hh:mm:ss"));
				}
				if (!destPath.contains(remoteLocal)) {
					String mediaName = fileLists[i].getName();
					String[] mediaInfos = mediaName.split("_");
					String code = mediaInfos[0];						
					//房间类别
					BdmRoom room=bdmRoomDao.findByCode(code);
					dto.setRoomCategory(room!=null?room.getName():null);
				}
				
				dtos.add(dto);
			}
			return dtos;
		}
	}

	/**
	 * 音视频列表
	 */
	public List<CaseMediaDto> query(QueryBean qb) {

		String remoteServerIp = REMOTE_SERVER_IP;// 市局流媒体地址
		String remoteShareDir = REMOTE_SHARE_DIR;// 市局流媒体共享目录
		boolean test = TEST;

		List<CaseMedia> entities = caseMediaDao.findByQueryBean(qb);
		if (entities == null) {
			return null;
		}
		List<CaseMediaDto> dtos = new ArrayList<>();
		for (CaseMedia entity : entities) {
			CaseMediaDto dto = CaseMediaDto.toDto(entity);
			if (test) {
				dto.setUrl("video" + entity.getUrl() + "/" + entity.getTitle());
			} else {
				dto.setUrl("http://" + remoteServerIp + remoteShareDir + entity.getUrl() + "/" + entity.getTitle());
			}

			dtos.add(dto);
		}
		return dtos;
	}

}
