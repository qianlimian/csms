<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCaseRecordIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
		<a id="btnDoHandle" class="k-button" smart-ability="handle"><span class="fa fa-user"></span>开始办案</a>
		<a id="btnDoFinish" class="k-button" smart-ability="handle"><span class="fa fa-check-circle"></span>结束办案</a>
        <a id="btnDoMark" class="k-button" smart-ability="mark"><span class="fa fa-commenting-o"></span>评价打分</a>
        <a id="btnDoUpload" class="k-button" smart-ability="edit"><span class="fa fa-upload"></span>视频上传</a>
	</script>	
	<div id="mainGrid"></div>
</div>
<div id="ctnCaseRecordEditWrap"></div>
<div id="ctnCaseScoreEditWrap"></div>
<div id="ctnCaseMediaUploadWrap"></div>
<div id="ctnCaseRegisterWrap"></div>
<script src="${ctx}/views/pages/caseRecord/index.js"></script>

<select id="police_stations_select" class="display-none">
	<c:forEach items="${policeStations}" var="ps">
		<option value="${ps.id}">${ps.name}</option>
	</c:forEach>
</select>

<select id="polices_select" class="display-none">
	<c:forEach items="${polices}" var="p">
		<option value="${p.id}">${p.userName}</option>
	</c:forEach>
</select>
</body>   