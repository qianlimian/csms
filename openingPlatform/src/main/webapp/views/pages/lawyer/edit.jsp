<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnLawyerEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">

		<div class="s-row-fluid">
			<label class="s-span10 s-required">律师姓名</label>
			<input class="s-span15" type="text" id="edit_name" data-bind="value:lawyer.name" />

			<label class="s-span10">专业领域</label>
			<input class="s-span15" type="text" id="edit_domain" data-bind="value:lawyer.domain" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span10">执业证号</label>
			<input class="s-span15" type="text" id="edit_registrationNum" data-bind="value:lawyer.registrationNum" />

			<label class="s-span10">所属事务所</label>
			<input class="s-span15" type="text" id="edit_lawyerOffice" data-bind="value:lawyer.lawyerOffice" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span10">联系电话</label>
			<input class="s-span15" type="text" id="edit_phone" data-bind="value:lawyer.phone" />

			<label class="s-span10">电子邮箱</label>
			<input class="s-span15" type="email" id="edit_email" data-bind="value:lawyer.email" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span10">从业时间</label>
			<input class="s-span15" type="text" id="edit_registerDate" data-bind="value:lawyer.registerDate" />

			<label class="s-span10">状态</label>
			<input class="s-span15" type="text" id="edit_status" data-bind="value:lawyer.status" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span10 ">律师简介 </label>
			<textarea style="height: 100px;" class="s-span40" id="edit_info" data-bind="value:lawyer.info" />
		</div>



  	</form>
</div>

<script src="${ctx}/views/pages/lawyer/edit.js"></script>
</body>