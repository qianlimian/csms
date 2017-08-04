<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div id="ctnLawEdit">
	<div class="s-row-fluid s-toolbar">
		<a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
		<a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
		<a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>
	<form class="s-row-fluid">
		<div class="s-row-fluid">
			<label class="s-span15 s-required">标题</label>
			<input class="s-span30" type="text" id="edit_title" data-bind="value:law.title"/>
		</div>

		<div class="s-row-fluid">
			<label class="s-span15 s-required">内容</label>
			<textarea style="height: 550px;width: 700px;" class="s-span30" id="edit_content" data-bind="value:law.content"></textarea>
		</div>
	</form>
</div>
<link rel="stylesheet" href="${ctx}/views/smart/assets/kindeditor/themes/default/default.css"/>
<script type="text/javascript" src="${ctx}/views/smart/assets/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/views/smart/assets/kindeditor/lang/zh-CN.js"></script>
<script>
	// 初始化富文本编辑器
	KindEditor.ready(function (K) {
		window.editor = K.create('#edit_content');
	});
</script>
<script src="${ctx}/views/pages/law/edit.js"></script>
</body>