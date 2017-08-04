<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<head>
	<!-- jquery-multiselect -->
	<link href="${ctx}/views/smart/assets/jquery-multiselect/css/multi-select.css" rel="stylesheet" >
	<script src="${ctx}/views/smart/assets/jquery-multiselect/js/multi-select.js"></script>
</head>

<body>
<div id="ctnGroupIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button"><span class="fa fa-remove"></span>删除</a>
	</script>

	<div class="s-row-fluid">
		<div class="s-pct50" id="mainGrid" ></div>
		<div class="s-pct50">
			<div id="tabstrip" style="border:none;">
				<ul>
					<li class="k-state-active">人员维护</li>
					<li>菜单配置</li>
					<li>功能权限</li>
					<li>角色配置</li>
					<div style="float: right;"><a id="btnDoSave" class="k-button"><i class="fa fa-save"></i>保存</a></div>
				</ul>

				<%--用户Tab--%>
				<div>
					<script id="tbUserGrid" type="text/x-kendo-template">
						<a id="btnDoNewUser" class="k-button"><span class="k-icon k-i-plus"></span>新增</a>
						<a id="btnDoDeleteUser" class="k-button"><span class="k-icon k-i-close"></span>删除</a>
					</script>

					<div id="userGrid"></div>
				</div>
				<%--菜单Tab--%>
				<div>
					<div id="menusTreeView"></div>
				</div>
				<%--权限Tab--%>
				<div>
					<div id="operatesTreeView"></div>
				</div>
				<%--角色Tab--%>
				<div>
					<div id="roleSelect">
						<select id="multiSelect" multiple='multiple'>
							<c:forEach items="${roles}" var="role">
								<option value="${role.name}">${role.desc}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="ctnGroupEditWrap"></div>

<div id="ctnUserSelectWrap"></div>

<script src="${ctx}/views/smart/frame/group/index.js"></script>

<div style="display:none;" id="smartMenus">${menus}</div>
<div style="display:none;" id="smartOperates">${operates}</div>
</body>