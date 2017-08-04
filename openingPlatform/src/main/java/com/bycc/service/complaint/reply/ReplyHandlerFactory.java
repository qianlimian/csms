package com.bycc.service.complaint.reply;

import com.bycc.enumitem.ReplyType;
import org.smartframework.platform.exception.BusinessException;

/**
 * Description: 回复处理器工厂
 * User: yumingzhe
 * Time: 2017-7-11 14:08
 */
public class ReplyHandlerFactory {

	public static ReplyHandler getHandler(ReplyType replyType) throws BusinessException {
		switch (replyType) {
			case EMAIL:
				return new EmailReplyHandler();
			case TEL:
				return null;
			case TALK:
				return null;
			case SMS:
				return null;
			default:
				throw new BusinessException("不支持的类型！");
		}
	}
}
