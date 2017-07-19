<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnStudentEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">姓名</label>
      		<input class="s-span30" type="text" id="edit_name" data-bind="value:student.name" />
      	</div>

      	<div class="s-row-fluid">
      		<label class="s-span15">年龄</label>
      		<input class="s-span30" type="text" id="edit_age" data-bind="value:student.age" />
      	</div>

		<div class="s-row-fluid">
			<label class="s-span15">生日</label>
			<input class="s-span30" type="text" id="edit_birthday" data-bind="value:student.birthday" />
		</div>

	  	<div class="s-row-fluid">
      		<label class="s-span15">住址</label>
      		<input class="s-span30" type="text" id="edit_address" data-bind="value:student.address" />
      	</div>
  	</form>
</div>

<script src="${ctx}/views/smart/demo/student/edit.js"></script>
</body>