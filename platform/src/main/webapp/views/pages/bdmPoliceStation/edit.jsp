<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnBdmPoliceStationEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
		<div class="s-row-fluid">
			<label class="s-span6 s-required">编码</label>
			<input class="s-span12" type="text" id="edit_code" data-bind="value:bdmPoliceStation.code" />

			<label class="s-span6 s-required">名称</label>
			<input class="s-span12" type="text" id="edit_name" data-bind="value:bdmPoliceStation.name" />

			<label class="s-span6 s-required">区域</label>
			<input class="s-span12" type="text" id="edit_areaType" data-bind="value:bdmPoliceStation.areaType" />
		</div>

		<div class="s-row-fluid">
			<label class="s-span6 s-required">级别</label>
			<input class="s-span12" type="text" id="edit_policeStationType" data-bind="value:bdmPoliceStation.policeStationType" />
			<label class="s-span6">MSIP</label>
			<input class="s-span12" type="text" id="edit_ip" data-bind="value:bdmPoliceStation.ip" />
		</div>
		<div class="s-row-fluid">
			<label class="s-span6">地址</label>
			<input class="s-span30" type="text" id="edit_address" data-bind="value:bdmPoliceStation.address" />
		</div>
  	</form>

	<script id="template" type="text/x-kendo-template">
		<a class="k-button" id="btnDoNewSub" smart-ability="edit"><span class="fa fa-search"></span>选择</a>
		<a class="k-button" id="btnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
	</script>

	<div class="s-row-fluid" id="subGridEdit"></div>
</div>

<script src="${ctx}/views/pages/bdmPoliceStation/edit.js"></script>
</body>   