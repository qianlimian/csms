/**
 * 
 */
package com.bycc.service.caseMedia;


import java.io.IOException;
import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.web.multipart.MultipartFile;

import com.bycc.dto.caseMedia.CaseMediaDto;
import com.bycc.dto.caseMedia.MediaTreeListDto;

/**
 * @description
 * @author gaoningbo
 * @date 2017年5月3日
 * 
 */
public interface CaseMediaService {

	//获取音视频
	List<CaseMediaDto> query(QueryBean qb);
	//获取音视频
	List<CaseMediaDto> findByQueryBeanCondition(QueryBean qb,String filter,String category,String unit);
	//预览音视频
	CaseMediaDto previewMedia(Integer id);
	//获取派出所视频列表
	List<MediaTreeListDto> getLocalMediaList(Integer caseRecordId,String path);
	//获取市局视频列表
	List<MediaTreeListDto> getRemoteMediaList(Integer caseRecordId,String path);
	//拷贝NFS服务器上文件
	void copyFilesNFS(List<MediaTreeListDto> checkedMedias);
	//保存HTTP上传的文件
	String saveFiles(MultipartFile uploadedFile,String range,Integer caseRecordId,String category)throws IOException;	
	
	
	
}
