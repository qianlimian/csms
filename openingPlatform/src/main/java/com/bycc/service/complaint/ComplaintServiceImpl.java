package com.bycc.service.complaint;

import com.bycc.common.ExcelUtil;
import com.bycc.dao.ComplaintDao;
import com.bycc.dto.ComplaintDto;
import com.bycc.dto.ReplyDto;
import com.bycc.entity.Complaint;
import com.bycc.enumitem.ReplyStatus;
import com.bycc.enumitem.ReplyType;
import com.bycc.service.complaint.reply.ReplyHandler;
import com.bycc.service.complaint.reply.ReplyHandlerFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartframework.common.kendo.QueryBean;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 投诉管理服务层实现类
 * User: yumingzhe
 * Time: 2017-7-10 13:57
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ComplaintDao complaintDao;
	
	@Override
	public List<ComplaintDto> query(QueryBean qb) {
		List<Complaint> complaints = complaintDao.findByQueryBean(qb);
		return complaints.stream().map(ComplaintDto::toDto).collect(Collectors.toList());
	}

	@Override
	public ComplaintDto findOne(Integer id) {
		return ComplaintDto.toDto(complaintDao.findOne(id));
	}

	@Override
	public void saveReply(ReplyDto replyDto) throws BusinessException {
		if (StringUtils.isEmpty(replyDto.getReplyContent())) {
			throw new BusinessException("回复内容不允许为空！");
		}
		// 更新记录
		Complaint complaint = complaintDao.findOne(replyDto.getComplaintId());
		complaint.setResult(replyDto.getReplyContent());
		complaint.setReplyType(ReplyType.getMatchByKey(replyDto.getReplyType()));
		complaint.setReplyStatus(ReplyStatus.REPLYED);
		complaintDao.save(complaint);

		// 处理回复
		ReplyHandler handler = ReplyHandlerFactory.getHandler(ReplyType.getMatchByKey(replyDto.getReplyType()));
		if (handler != null) {
			handler.handle(complaint);
		} else {
			logger.warn("未找到处理{}类型的回复处理器", replyDto.getReplyType());
		}
	}

	@Override
	public Workbook export(QueryBean queryBean) throws BusinessException {
		List<Complaint> complaints = complaintDao.findByQueryBean(queryBean);
		List<ComplaintDto> dtos = complaints.stream().map(ComplaintDto::toDto).collect(Collectors.toList());
		try {
			return ExcelUtil.export(dtos, ComplaintDto.class, "投诉记录");
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}

	/**
	 * 保存投诉记录
	 */
	@Override
	public String save(ComplaintDto dto, HttpServletRequest request){
		
		// 获取存放在session中的验证码
        String code = (String) request.getSession().getAttribute("verCode");
        // 获取页面提交的验证码
        String inputCode = request.getParameter("code");
        if(code != null && code.toLowerCase().equals(inputCode.toLowerCase())) { // 验证码不区分大小写
        	request.getSession().removeAttribute("verCode");
        	Complaint complaint = new Complaint();
        	complaint.setName(dto.getName());
        	complaint.setTel(dto.getTel());
        	complaint.setEmail(dto.getEmail());
    		complaint.setTitle(dto.getTitle());
    		complaint.setContent(dto.getContent());
    		complaint.setInsertDate(new Date());
    		complaint.setReplyStatus(ReplyStatus.UNREPLY);
    		complaintDao.save(complaint);
    		return "success";
        } else {
        	return "error";
        }
	}
}
