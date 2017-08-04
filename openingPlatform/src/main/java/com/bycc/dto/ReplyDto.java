package com.bycc.dto;

/**
 * Description:
 * User: yumingzhe
 * Time: 2017-7-11 13:32
 */
public class ReplyDto {
	private Integer complaintId;

	private String replyType;

	private String replyContent;

	public Integer getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}
}
