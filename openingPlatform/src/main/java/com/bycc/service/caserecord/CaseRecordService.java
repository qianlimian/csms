/**
 * 
 */
package com.bycc.service.caserecord;

import java.util.List;

import org.smartframework.common.kendo.QueryBean;
import org.springframework.web.multipart.MultipartFile;

import com.bycc.dto.CaseRecordDto;
import com.bycc.dto.LawDto;
import com.bycc.dto.LawyerDto;

/**
 * @description
 * @author gaoningbo
 * @date 2017年4月14日
 * 
 */
public interface CaseRecordService {

	/**
	 * 删除案件记录
	 */
	void deleteCaseRecordById(String ids);

	/**
	 * 根据ID查找案件记录
	 */
	CaseRecordDto findById(Integer id);

	/**
	 * 根据查询条件
	 */
	List<CaseRecordDto> query(QueryBean qb);

	/**
	 * 设置为开放
	 */
	void doOpen(String id);

	/**
	 * 取消开放
	 */
	void doCancel(String id);

	/**
	 * 关联法律法规
	 */
	void doSaveLaw(Integer caseRecordId, String ids);

	/**
	 * 关联律师
	 */
	void doSaveLawyer(Integer caseRecordId, String ids);

	/**
	 * 解除关联法律法规
	 */
	void doCancelLaw(Integer caseRecordId, String ids);

	/**
	 * 解除关联律师
	 */
	void doCancelLawyer(Integer caseRecordId, String ids);
	/**
	 * 查询法律法规
	 */
	List<LawDto> queryLaw(Integer caseRecordId);
	/**
	 * 查询律师
	 */
	List<LawyerDto> queryLawyer(Integer caseRecordId);
	/**
	 * 导入
	 */
	int importExcel(MultipartFile file) throws Exception;
}
