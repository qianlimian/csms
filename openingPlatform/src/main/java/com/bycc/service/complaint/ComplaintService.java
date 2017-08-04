package com.bycc.service.complaint;

import com.bycc.dto.ComplaintDto;
import com.bycc.dto.ReplyDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 投诉service接口
 */
public interface ComplaintService {
	/**
	 * 查询
	 *
	 * @param qb 分页组件
	 */
	List<ComplaintDto> query(QueryBean qb);

	/**
	 * 按id查询
	 *
	 * @param id 投诉id
	 */
	ComplaintDto findOne(Integer id);

	/**
	 * 保存回复信息
	 */
	void saveReply(ReplyDto replyDto) throws BusinessException;

	/**
	 * 导出
	 */
	Workbook export(QueryBean queryBean) throws BusinessException;

	/**
	 * 保存投诉记录
	 */
	String save(ComplaintDto dto, HttpServletRequest request);
}
