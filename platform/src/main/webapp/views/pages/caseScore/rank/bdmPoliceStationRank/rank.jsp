<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseScoreRank">	
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoShow" class="k-button" smart-ability="edit"><span class="fa fa-search"></span>详情</a>
	</script>
	<div id="rankMainGrid"></div>
</div>
<div id="ctnScoreDetailWrap"></div>

<script src="${ctx}/views/pages/caseScore/rank/bdmPoliceStationRank/rank.js"></script>
</body>   