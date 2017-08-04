package com.bycc.service.complaint.reply;

import com.bycc.entity.Complaint;
import org.smartframework.platform.context.SpringContext;
import org.smartframework.platform.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Description:邮件回复处理器
 * User: yumingzhe
 * Time: 2017-7-11 14:06
 */
@Component
public class EmailReplyHandler implements ReplyHandler {

	@Override
	public void handle(Complaint complaint) throws BusinessException {
		// 读取邮件配置(properties/mail.properties)
		Properties mailConfig = (Properties) SpringContext.getBean("mailConfig");

		// 设置环境信息
		Session session = Session.getInstance(mailConfig);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		try {
			// 设置邮件主题
			msg.setSubject("关于 \"" + complaint.getTitle() + "\" 的回复");
			// 设置邮件内容
			msg.setText(complaint.getResult());
			// 设置发件人
			msg.setFrom(new InternetAddress(mailConfig.getProperty("mail.sender")));

			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(mailConfig.getProperty("mail.user"), mailConfig.getProperty("mail.password"));
			// 发送邮件
			transport.sendMessage(msg, new Address[]{new InternetAddress(complaint.getEmail())});
			// 关闭连接
			transport.close();
		} catch (AuthenticationFailedException e) {
			throw new BusinessException("邮件服务器验证失败，请确保用户名和密码配置正确!");
		} catch (SendFailedException e) {
			throw new BusinessException("邮件发送失败!原因：" + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}
}
