<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnLawyerIndex">
	<script id="template" type="text/x-kendo-template">
		<%--<a id="btnAdvancedQuery" class="k-button"><span class="fa fa-search"></span>查询</a>--%>
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
		<a id="btnDoUpload" class="k-button" smart-ability="edit"><span class="fa fa-upload"></span>导入</a>
	</script>
	<div id="mainGrid"></div>
</div>
<div id="ctnUploadWrap"></div>
<div id="ctnLawyerEditWrap"></div>
<script src="${ctx}/views/pages/lawyer/index.js"></script>

</body>