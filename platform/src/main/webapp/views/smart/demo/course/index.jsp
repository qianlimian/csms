<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCourseIndex">
	<script id="template" type="text/x-kendo-template">
		<%--<a id="btnAdvancedQuery" class="k-button"><span class="fa fa-search"></span>查询</a>--%>
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
	</script>

	<%--默认已经取消了查询窗口，有特殊需要的加--%>
	<%--<div id="ctnQueryWin" class="display-none">
		<div class="s-toolbar">
			<a id="btnDoQuery" class="k-button" ><i class="fa fa-search"></i>查询</a>
			<a id="btnDoReset" class="k-button" ><i class="fa fa-eraser"></i>重置</a>
		</div>
		<div class="s-query">
			<div class="s-row-fluid">
				<label class="s-span10">课程名</label>
				<input class="s-span15" type="text" name="courseName"/>

				<label class="s-span10">年级</label>
				<input class="s-span15" type="text" name="gradeId"/>
			</div>

			<div class="s-row-fluid">
				<label class="s-span10">教师</label>
				<input class="s-span15" type="text" name="teacherId"/>
			</div>
		</div>
	</div>--%>

	<div id="mainGrid"></div>

	<div id="subGrid"></div>
</div>
<div id="ctnCourseEditWrap"></div>

<script src="${ctx}/views/smart/demo/course/index.js"></script>

<%-- combo的列表项 --%>
<select id="teachers_select" class="display-none">
	<c:forEach items="${teachers}" var="teacher">
		<option value="${teacher.id}">${teacher.name}</option>
	</c:forEach>
</select>

<select id="students_select" class="display-none">
	<c:forEach items="${students}" var="student">
		<option value="${student.id}">${student.name}</option>
	</c:forEach>
</select>

</body>   