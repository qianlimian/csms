<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnCourseEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span10 s-required">课程名</label>
      		<input class="s-span15" type="text" id="edit_courseName" data-bind="value:course.courseName" />

			<label class="s-span10 s-required">年级</label>
			<input class="s-span15" type="text" id="edit_gradeId" data-bind="value:course.gradeId" />
      	</div>

		<div class="s-row-fluid">
			<label class="s-span10 s-required">教师</label>
			<input class="s-span15" type="text" id="edit_teacherId" data-bind="value:course.teacherId" />
		</div>
  	</form>

	<script id="template" type="text/x-kendo-template">
		<a class="k-button" id="btnDoNewSub" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a class="k-button" id="btnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
	</script>

	<div class="s-row-fluid" id="subGridEdit"></div>
</div>

<script src="${ctx}/views/smart/demo/course/edit.js"></script>
</body>   