<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnUserIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button"><span class="fa fa-remove"></span>删除</a>
		<a id="btnDoResetPwd" class="k-button">重置密码</a>
	</script>

	<div id="mainGrid"></div>
</div>
<div id="ctnUserEditWrap"></div>

<script src="${ctx}/views/smart/frame/user/index.js"></script>
</body>   