<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoView" class="k-button" smart-ability="view"><span class="fa fa-file-text-o"></span>案件信息</a>
        <a id="btnDoHandle" class="k-button" smart-ability="handle"><span class="fa fa-user"></span>开始办案</a>
		<a id="btnDoFinish" class="k-button" smart-ability="handle"><span class="fa fa-check-circle"></span>结束办案</a>
	</script>

	<div id="mainGrid"></div>
</div>
<div id="ctnCaseEditWrap"></div>
<div id="ctnCaseRegisterWrap"></div>

<script src="${ctx}/views/pages/case/dispute/index.js"></script>
</body>   