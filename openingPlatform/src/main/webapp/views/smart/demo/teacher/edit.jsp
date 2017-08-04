<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnTeacherEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">姓名</label>
      		<input class="s-span30" type="text" id="edit_name" data-bind="value:teacher.name" />
      	</div>

	  	<div class="s-row-fluid">
      		<label class="s-span15">性别</label>
      		<input class="s-span30" type="text" id="edit_gender" data-bind="value:teacher.gender" />
      	</div>
  	</form>
</div>

<script src="${ctx}/views/smart/demo/teacher/edit.js"></script>
</body>   