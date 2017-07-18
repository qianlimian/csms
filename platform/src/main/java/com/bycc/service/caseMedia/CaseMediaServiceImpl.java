/**
 * 
 */
package com.bycc.service.caseMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.manager.entity.User;
import org.smartframework.utils.helper.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.bycc.entity.BdmPoliceStation;
import com.bycc.entity.BdmRoom;
import com.bycc.entity.BdmVideoCategory;
import com.bycc.entity.CaseMedia;
import com.bycc.entity.CasePeople;
import com.bycc.entity.CaseRecord;
import com.bycc.service.permission.PermissionService;
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
	@Autowired
	PermissionService permissionSer;
	
	private Logger logger = LoggerFactory.getLogger(CaseMediaServiceImpl.class);

	private static boolean TEST = Boolean.valueOf(FilePropertyUtil.getValueByKey("file.test"));
	private static String SRC_PATH = FilePropertyUtil.getValueByKey("file.srcPath");//市局Web服务器上挂载的目录
	private static String LOCAL_SHARE_DIR = FilePropertyUtil.getValueByKey("file.localShareDir");//派出所流媒体服务器视频的根目录
	private static String DEST_PATH = FilePropertyUtil.getValueByKey("file.destPath");
	private static String REMOTE_LOCAL = FilePropertyUtil.getValueByKey("file.remoteLocal");
	private static String REMOTE_SERVER_IP = FilePropertyUtil.getValueByKey("file.remoteIp");
	private static String REMOTE_SHARE_DIR = FilePropertyUtil.getValueByKey("file.remoteShareDir");// 市局流媒体共享目录

	/**
	 * 根据登录用户所在派出所的ID，去获取视频
	 * 
	 * 获取派出所流媒体服务器视频列表
	 */
	public List<MediaTreeListDto> getLocalMediaList(Integer caseRecordId, String path) {
		String rootPath = null;// 实际目录
		boolean test = TEST;
		String srcPath = SRC_PATH;// 挂载目录
		String localShareDir=LOCAL_SHARE_DIR;
		String ip = null;// 派出所流媒体IP
		String tmp = null;// 临时值
		
		
		BdmPoliceStation station=permissionSer.findPoliceStation();
		if(station==null){
			logger.error("当前登录用户不属于任何派出所!");
			return null;
		}
		Integer stationId=station.getId();//派出所ID
	
		if (test) {
			rootPath = FilePropertyUtil.getTestSrcPath();
		} else {
			rootPath = srcPath+"/"+stationId;// 映射到市局服务器中的目录
		}
		tmp = rootPath;
		if (rootPath == null) {
			logger.error("未设置派出所流媒体服务器目录");
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
			logger.error("不存在警局,当前用户ID：" + userId);
			return null;
		}
		if (path == null) {
			if (test) {
				rootPath = rootPath + "\\" + caseRecordId;
			} else {
				rootPath = rootPath + "/" + caseRecordId;
			}
		} else {
			rootPath = path;
		}
		File file = new File(rootPath);
	
		if (file.isFile()) {
			logger.error("该路径是文件！");
			return null;
		} else {
			List<MediaTreeListDto> dtos = new ArrayList<>();
			File[] fileLists = file.listFiles(); // 如果是目录，获取该目录下的内容集合
			if (fileLists == null) {
				logger.error("文件夹为空！"+rootPath);
				return null;
			}
			for (int i = 0; i < fileLists.length; i++) {
				MediaTreeListDto dto = MediaTreeListDto.toDto(fileLists[i]);
				dto.setCaseRecordId(caseRecordId);
				if (fileLists[i].isFile()) {
					String serverPath = fileLists[i].getPath().substring(tmp.length());
					if (test) {
						Integer peopleId = Integer.parseInt(serverPath.split("\\\\")[2]);
						dto.setCasePeopleId(peopleId);
						dto.setLabel(fileLists[i].getName());
						dto.setServerPath("videoSrc" + serverPath.replace("\\", "/"));
					} else {
						Integer peopleId = Integer.parseInt(serverPath.split("/")[2]);
						dto.setCasePeopleId(peopleId);
						dto.setLabel(fileLists[i].getName());
						// 派出所流媒体服务器地址
						String relativePath = serverPath.replace("\\", "/");
						dto.setServerPath("http://" + ip + localShareDir + relativePath);
					}
					String mediaName = fileLists[i].getName().split("\\.")[0];
					// 解析视频名称
					String[] mediaInfos = mediaName.split("_");
					if (mediaInfos.length == 3) {
						String code = mediaInfos[0];
						// 房间类别
						BdmRoom room = bdmRoomDao.findByCode(code);
						dto.setRoomCategory(room != null ? room.getName() : null);
						// 时间
						Date startDate = new Date(Long.parseLong(mediaInfos[1]));
						Date endDate = new Date(Long.parseLong(mediaInfos[2]));
						dto.setStartDate(DateHelper.formatDateToString(startDate, "yyyy-MM-dd HH:mm:ss"));
						dto.setEndDate(DateHelper.formatDateToString(endDate, "yyyy-MM-dd HH:mm:ss"));
						dtos.add(dto);
					}
				} else {
					Integer peopleId = Integer.parseInt(fileLists[i].getName());
					CasePeople casePeople = casePeopleDao.findOne(peopleId);
					String peopleName = casePeople != null ? casePeople.getName() : fileLists[i].getName();
					dto.setLabel(peopleName);
					dto.setCasePeopleId(peopleId);
					dtos.add(dto);
				}				
				
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
		Integer unitId = null;// 派出所ID
		Integer caseRecordId = null;// 案件ID
		Integer casePeopleId = null;// 涉案人员ID

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
				caseRecordId = media.getCaseRecordId();
				casePeopleId = media.getCasePeopleId();

				CaseRecord cr = crDao.findOne(caseRecordId);
				CasePeople casePeople = casePeopleDao.findOne(casePeopleId);

				if (cr != null && casePeople != null) {					
					if (cr.getMasterUnit() != null) {
						area = cr.getMasterUnit().getAreaType().key();
						unitId = cr.getMasterUnit().getId();
						if (area != null && unitId != null) {
							if (test) {
								destDir = destDir + "\\" + area + "\\" + unitId + "\\" + caseRecordId + "\\"
										+ casePeopleId;
							} else {
								destDir = destDir + "/" + area + "/" + unitId + "/" + caseRecordId + "/"
										+ casePeopleId;
							}
							boolean result = FileOperateUtil.copyFile(media.getMappingPath(), media.getLabel(),
									destDir, test);
							if (result) {
								BdmVideoCategory cg = null;
								destDir = destDir.substring(tmp.length());
								cg = bdmVideoCgDao.findByName(media.getMediaCategory());
								CaseMedia entity = caseMediaDao.findByCaseRecordIdAndTitle(cr.getId(),
										media.getLabel());
								Integer id = entity == null ? null : entity.getId();
								CaseMedia caseMedia = CaseMediaDto.toEntity(id, media.getLabel(), destDir, cg,
										casePeople, cr, media.getNote());
								caseMediaDao.save(caseMedia);
							}
						}
					} else {
						logger.error("办案记录未设置主办单位！");
					}
				} else {
					logger.error("上传的视频没有与之相关联办案记录或嫌疑人!");
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
		String destPath = null;// 目的路径
		String remoteLocal = REMOTE_LOCAL;// 本地文件存放文件夹
		String area = null;// 地区
		Integer unitId = null;// 派出所ID

		boolean test = TEST;
		if (test) {
			destPath = FilePropertyUtil.getTestDestPath();
		} else {
			destPath = DEST_PATH;
		}
		String tmp = destPath;

		CaseRecord cr = crDao.findOne(caseRecordId);
		if (cr != null) {			
			if (cr.getMasterUnit() != null) {
				area = cr.getMasterUnit().getAreaType().key();
				unitId = cr.getMasterUnit().getId();
			} else {
				logger.error("办案记录未设置主办单位！");
			}
			
			if (test) {
				destPath = destPath + "\\" + area + "\\" + unitId + "\\" + caseRecordId + "\\" + remoteLocal;
			} else {
				destPath = destPath + "/" + area + "/" + unitId + "/" + caseRecordId + "/" + remoteLocal;
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
		} else {
			logger.error("不存在ID=" + caseRecordId + ",的办案记录！");
		}
		return null;
	}

	/**
	 * 获取市局流媒体服务器视频列表
	 */
	public List<MediaTreeListDto> getRemoteMediaList(Integer caseRecordId, String path) {
		String destPath = null;
		boolean test = TEST;
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
				logger.error("未设置市局流媒体服务器目录");
				return null;
			}
			CaseRecord cr = crDao.findOne(caseRecordId);
			if (cr != null && cr.getMasterUnit() != null) {
				String area = cr.getMasterUnit().getAreaType().key();
				Integer unitId = cr.getMasterUnit().getId();
				if (test) {
					destPath = destPath + "\\" + area + "\\" + unitId + "\\" + caseRecordId;
				} else {
					destPath = destPath + "/" + area + "/" + unitId + "/" + caseRecordId;
				}

			} else {
				logger.error("不存在这样办案记录！");
				return null;
			}
		} else {
			destPath = path;
		}
		File destFile = new File(destPath);
		if (destFile.isFile()) {
			logger.error("非目录："+destPath);
			return null;
		} else {
			List<MediaTreeListDto> dtos = new ArrayList<>();
			File[] fileLists = destFile.listFiles(); // 如果是目录，获取该目录下的内容集合
			if (fileLists == null) {
				//logger.error("该目录下没有内容!");
				return null;
			}
			List<String> tmpNameFiles = new ArrayList<>();
			// 检测临时文件
			for (int i = 0; i < fileLists.length; i++) {
				File file = fileLists[i];
				if (file.getName().endsWith(".tmp")) {
					tmpNameFiles.add(file.getName());
				}
			}
			for (int i = 0; i < fileLists.length; i++) {
				File file = fileLists[i];
				String fileName = file.getName();
				boolean tmpFile = false;
				for (String tmpFileName : tmpNameFiles) {
					if (tmpFileName.contains(fileName)) {
						tmpFile = true;
						break;
					}
				}
				if (!tmpFile) {
					MediaTreeListDto dto = MediaTreeListDto.toDto(file);
					if(fileLists[i].isFile()){
						String serverPath = fileLists[i].getPath().substring(tmp.length());											
						if (test) {
							Integer peopleId = Integer.parseInt(serverPath.split("\\\\")[2]);
							dto.setCasePeopleId(peopleId);
							dto.setLabel(fileLists[i].getName());
							serverPath = "video" + serverPath.replace("\\", "/");
						} else {
							Integer peopleId = Integer.parseInt(serverPath.split("/")[2]);
							dto.setCasePeopleId(peopleId);
							dto.setLabel(fileLists[i].getName());
							serverPath = "http://" + remoteServerIp + remoteShareDir + serverPath;
						}
						dto.setServerPath(serverPath);
						CaseMedia entity = caseMediaDao.findByCaseRecordIdAndTitle(caseRecordId, fileLists[i].getName());
						
						if (entity != null) {
							dto.setMediaCategory(entity.getCategory() != null ? entity.getCategory().getName() : null);
							dto.setStartDate(DateHelper.formatDateToString(entity.getUpdateDate(), "yyyy-MM-dd HH:mm:ss"));
						}
						if (!destPath.contains(remoteLocal)) {
							String mediaName = fileLists[i].getName();
							String[] mediaInfos = mediaName.split("_");
							String code = mediaInfos[0];
							// 房间类别
							BdmRoom room = bdmRoomDao.findByCode(code);
							dto.setRoomCategory(room != null ? room.getName() : null);
						}
					}else{
						if(fileLists[i].getName().equals(remoteLocal)){
							dto.setLabel(remoteLocal);
						}else{
							Integer peopleId = Integer.parseInt(fileLists[i].getName());
							CasePeople casePeople = casePeopleDao.findOne(peopleId);
							String peopleName = casePeople != null ? casePeople.getName() : fileLists[i].getName();
							dto.setLabel(peopleName);
							dto.setCasePeopleId(peopleId);
						}
					
					}
					dtos.add(dto);
				}

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

	/**
	 * 音视频预览
	 */
	public CaseMediaDto previewMedia(Integer id) {
		boolean test = TEST;
		String remoteServerIp = REMOTE_SERVER_IP;// 市局流媒体地址
		String remoteShareDir = REMOTE_SHARE_DIR;// 市局流媒体共享目录

		CaseMedia entity = caseMediaDao.findOne(id);
		CaseMediaDto dto = CaseMediaDto.toDto(entity);

		if (test) {
			dto.setUrl("video" + entity.getUrl() + "/" + entity.getTitle());
		} else {
			dto.setUrl("http://" + remoteServerIp + remoteShareDir + entity.getUrl() + "/" + entity.getTitle());
		}
		return dto;
	}

	/**
	 * 音视频列表 过滤、视频分类、办案单位
	 */
	public List<CaseMediaDto> findByQueryBeanCondition(QueryBean qb, String filter, String category, String unit) {
		String remoteServerIp = REMOTE_SERVER_IP;// 市局流媒体地址
		String remoteShareDir = REMOTE_SHARE_DIR;// 市局流媒体共享目录
		boolean test = TEST;
		// 传参
		String[] array = new String[] {};
		array[0] = "(title=:filter or caseRecord.caseName = :filter or caseRecord.suspect =: filter)";
		array[1] = "category.name =: category";
		array[1] = "caseRecord.masterUnit.name =: unit or caseRecord.slaveUnit.name =: unit";
		// 赋值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("filter", filter);
		map.put("category", category);
		map.put("unit", unit);
		List<CaseMedia> entities = caseMediaDao.findByQueryBeanCondition(array, map, qb);
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
