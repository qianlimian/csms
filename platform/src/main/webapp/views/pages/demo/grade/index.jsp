<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnGradeIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
	</script>

	<div id="mainGrid"></div>
</div>
<div id="ctnGradeEditWrap"></div>

<script src="${ctx}/views/pages/demo/grade/index.js"></script>


<%-- combo的列表项 --%>
<select id="teachers_select" class="display-none">
	<c:forEach items="${teachers}" var="teacher">
		<option value="${teacher.id}">${teacher.name}</option>
	</c:forEach>
</select>

</body>   