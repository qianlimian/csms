<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnMenuIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnAdvancedQuery" class="k-button"><span class="fa fa-search"></span>查询</a>
		<%--<a id="btnDoNew" class="k-button"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button"><span class="fa fa-remove"></span>删除</a>--%>
	</script>

	<div id="ctnQueryWin" class="display-none">
		<div class="s-toolbar">
			<a id="btnDoQuery" class="k-button" ><i class="fa fa-search"></i>查询</a>
			<a id="btnDoReset" class="k-button" ><i class="fa fa-eraser"></i>重置</a>
		</div>
		<div class="s-query">
			<div class="s-row-fluid">
				<label class="s-span15">菜单</label>
				<input class="s-span30" type="text" name="name"/>
			</div>
		</div>
	</div>

	<div id="mainGrid"></div>
</div>
<div id="ctnMenuEditWrap"></div>

<script src="${ctx}/views/smart/frame/menu/index.js"></script>
</body>   