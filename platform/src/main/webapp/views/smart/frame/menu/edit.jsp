<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnMenuEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form>
      	<div class="s-row-fluid">
      		<label class="s-span15">编码</label>
      		<input class="s-span30" required="required" type="text" data-bind="value:menu.code" />
      	</div>

		<div class="s-row-fluid">
			<label class="s-span15">名称</label>
			<input class="s-span30" required="required" type="text" data-bind="value:menu.name" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span15">插件</label>
			<input class="s-span30" required="required" type="text" data-bind="value:menu.plugin" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span15">类型</label>
			<input class="s-span30" required="required" type="text" data-bind="value:menu.type" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span15">URL</label>
			<input class="s-span30" required="required" type="text" data-bind="value:menu.url" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span15">父菜单</label>
			<input class="s-span30" required="required" type="text" data-bind="value:menu.parentId" />
		</div>

      	<div class="s-row-fluid">
      		<label class="s-span15">描述</label>
      		<input class="s-span30" type="text" data-bind="value:menu.desc" />
      	</div>

		<div class="s-row-fluid">
			<label class="s-span15">排序</label>
			<input class="s-span30" type="text" data-bind="value:menu.displayOrder" />
		</div>
  	</form>
</div>

<script src="${ctx}/views/smart/frame/menu/edit.js"></script>
</body>   