<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnUserEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form>
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">姓名</label>
      		<input class="s-span30" type="text" data-bind="value:user.name" />
      	</div>

      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">登陆名</label>
      		<input class="s-span30" type="text" data-bind="value:user.loginName" />
      	</div>

	  	<div class="s-row-fluid">
      		<label class="s-span15 s-required">密码</label>
      		<input class="s-span30" type="password" data-bind="value:user.password" />
      	</div>
  	</form>
</div>

<script src="${ctx}/views/smart/frame/user/edit.js"></script>
</body>   