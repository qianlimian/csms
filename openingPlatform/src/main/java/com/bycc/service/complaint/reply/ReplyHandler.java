package com.bycc.service.complaint.reply;

import com.bycc.entity.Complaint;
import org.smartframework.platform.exception.BusinessException;

/**
 * Description: 回复处理接口
 * User: yumingzhe
 * Time: 2017-7-11 14:06
 */
public interface ReplyHandler {
	void handle(Complaint complaint) throws BusinessException;
}
