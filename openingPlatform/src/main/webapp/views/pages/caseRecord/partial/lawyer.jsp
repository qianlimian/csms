<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnLawyerIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoSaveLawyer" class="k-button"><span class="fa fa-check-square-o"></span>关联</a>		
	</script>	
	<div>
		<input id="crId" type="hidden" name="id" value="${caseRecord.id }">
	</div>
	<div id="mainLawyerGrid"></div>
</div>
<div id="ctnLawyerEditWrap"></div>
<script src="${ctx}/views/pages/caseRecord/partial/lawyer.js"></script>

</body>