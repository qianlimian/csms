<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseScoreRank">
	<div id="mainGrid"></div>
	<div id="subGrid"></div>
</div>
<div id="ctnCaseScoreWrap"></div>

<script src="${ctx}/views/pages/caseScore/rank/caseRank/rank.js"></script>
</body>   