<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnBdmHandlingAreaIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
	</script>

	<div id="mainGrid"></div>

	<div id="tabstrip">
		<ul>
			<li class="k-state-active">物品柜</li>
			<li>手环</li>
		</ul>
		<div>
			<div id="cabinSubGrid"></div>
		</div>
		<div>
			<div id="strapSubGrid"></div>
		</div>
	</div>
</div>
<div id="ctnBdmHandlingAreaEditWrap"></div>

<div id="ctnPsSelectWrap"></div>

<script src="${ctx}/views/pages/bdmHandlingArea/index.js"></script>

</body>