<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoView" class="k-button"><span class="fa fa-file-text-o"></span>案件信息</a>
		<a id="btnDoMark" class="k-button"><span class="fa fa-commenting-o"></span>评价打分</a>
	</script>

	<div id="mainGrid"></div>
</div>
<div id="ctnCaseEditWrap"></div>
<div id="ctnCaseScoreWrap"></div>

<script src="${ctx}/views/pages/caseScore/mark/index.js"></script>
</body>   