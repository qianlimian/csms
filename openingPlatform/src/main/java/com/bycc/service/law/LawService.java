/**
 *
 */
package com.bycc.service.law;

import com.bycc.dto.LawDto;
import org.smartframework.common.kendo.QueryBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author gaoningbo
 * @description
 * @date 2017年7月11日
 */
public interface LawService {
	/**
	 * 根据查询条件
	 */
	List<LawDto> query(QueryBean qb);


	/**
	 * 按id查找
	 */
	LawDto findById(Integer id);

	/**
	 * 查询所有
	 */
	List<LawDto> findAll();

	/**
	 * 保存
	 */
	LawDto save(LawDto dto) throws Exception;

	/**
	 * 删除
	 */
	void delete(String ids);

	/**
	 * 上传法律法规
	 */
	public int importExcel(MultipartFile file) throws Exception;

}
