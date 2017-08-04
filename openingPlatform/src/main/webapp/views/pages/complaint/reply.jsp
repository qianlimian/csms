<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<style>
	html, body {
		height: auto !important;
	}

	form label {
		text-align: left;
	}

	form .toolbar {
		text-align: center;
		margin-top: 10px;
	}

	form .toolbar > a {
		width: 150px;
	}

	.k-grid .k-grid-content tr[role='row'] {
		height: 30px;
		line-height: 30px;
	}
</style>

<div>
	<div class="s-row-fluid">
		<div class="s-pct100">
			<fieldset>
				<legend>投诉信息</legend>

				<div class="s-row-fluid">
					<label class="s-span5">投诉人姓名</label>

					<div class="s-span15">
						<input type="text" name="name" value="${dto.name}" disabled>
					</div>

					<label class="s-span5">电话</label>

					<div class="s-span15">
						<input type="text" name="tel" value="${dto.tel}" disabled>
					</div>

					<label class="s-span5">邮箱</label>

					<div class="s-span15">
						<input type="text" name="email" value="${dto.email}" disabled>
					</div>
				</div>

				<div class="s-row-fluid">
					<label class="s-span5">标题</label>

					<div class="s-span15">
						<input type="text" name="title" value="${dto.title}" disabled>
					</div>
				</div>
				<div class="s-row-fluid">
					<label class="s-span5">投诉内容</label>

					<div>
						<textarea name="content" style="width: 100%;height: 100%;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;" disabled>${dto.title}</textarea>
					</div>
				</div>
			</fieldset>

			<form>
				<fieldset>
					<legend>我的回复</legend>
					<div class="s-row-fluid">
						<input type="hidden" id="complaintId" value="${dto.id}">
						<label class="s-span5">回复方式</label>
						<select id="replyType">
							<c:forEach items="${replyType}" var="type">
								<option value ='${type.key()}'>${type.value()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="s-row-fluid">
						<label class="s-span5">回复内容</label>
						<textarea id="replyContent" style="width: 100%;height: 100%;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;"></textarea>
					</div>
				</fieldset>
				<div class="toolbar">
					<a class="k-button" id="btnDoReply"><i class="fa fa-reply"></i>回复</a>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	$(function () {
		$('#btnDoReply').click(function () {
			var replyContent = $('#replyContent').val();

			if(!replyContent) {
				alert("请填写回复内容!");
				return;
			}

			smart.ajax({
				url: "complaints/reply.do",
				contentType:'application/json',
				async:true,
				data:JSON.stringify({"complaintId":$("#complaintId").val(),"replyType":$("#replyType").val(),"replyContent":replyContent}),
				method :'POST',
				success: function () {
					smart.Event('REPLY_SAVED_EVENT').fire();
				}
			});
		});
	});
</script>
</body>