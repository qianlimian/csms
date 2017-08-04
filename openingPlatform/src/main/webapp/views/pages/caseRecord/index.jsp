<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseRecordIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoView" class="k-button"><span class="fa fa-file-text-o"></span>案件信息</a>
		<a id="btnDoOpen" class="k-button"><span class="fa fa-check-square-o"></span>选中公开</a>
		<a id="btnDoCancel" class="k-button"><span class="fa fa-remove"></span>取消公开</a>
		<a id="btnDoLawyer" class="k-button"><span class="fa fa-link"></span>关联律师</a>
		<a id="btnDoLaw" class="k-button"><span class="fa fa-link"></span>法律法规</a>
		<a id="btnDoUpload" class="k-button"><span class="fa fa-upload"></span>导入案件</a>
	</script>	
	<div id="mainGrid"></div>
</div>
<div id="ctnCaseRecordEditWrap"></div>
<div id="ctnLawWrap"></div>
<div id="ctnLawyerWrap"></div>
<div id="ctnUploadWrap"></div>
<script src="${ctx}/views/pages/caseRecord/index.js"></script>

</body>