<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnGradeEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">班级</label>
      		<input class="s-span30" type="text" id="edit_gradeName" data-bind="value:grade.gradeName" />
      	</div>

      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">教师</label>
      		<input class="s-span30" type="text" id="edit_teacherId" data-bind="value:grade.teacherId" />
      	</div>
  	</form>
</div>

<script src="${ctx}/views/smart/demo/grade/edit.js"></script>
</body>