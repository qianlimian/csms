<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>  
<div id="ctnBdmHandlingAreaEdit">
    <%-- formToolbar --%>
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span6 s-required">编码</label>
      		<input class="s-span12" type="text" id="edit_code" data-bind="value:bdmHandlingArea.code" />

			<label class="s-span6 s-required">名称</label>
			<input class="s-span12" type="text" id="edit_name" data-bind="value:bdmHandlingArea.name" />

			<label class="s-span6 s-required">公安机关</label>
			<input class="s-span9" type="hidden" id="edit_policeStationId" data-bind="value:bdmHandlingArea.policeStationId" />
			<input class="s-span9" type="text" id="edit_policeStationName" data-bind="value:bdmHandlingArea.policeStationName" readonly/>
			<a class="k-button s-span3" id="btnDoPick"><span class="fa fa-search"></span></a>
        </div>

		<div class="s-row-fluid">
			<label class="s-span6">备注</label>
			<input class="s-span30" type="text" id="edit_note" data-bind="value:bdmHandlingArea.note" />
		</div>
  	</form>

    <%-- gridToolbar --%>
    <script id="cabin_template" type="text/x-kendo-template">
        <a class="k-button" id="cabinBtnDoNewSub" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
        <a class="k-button" id="cabinBtnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
    </script>

    <script id="strap_template" type="text/x-kendo-template">
        <a class="k-button" id="strapBtnDoNewSub" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
        <a class="k-button" id="strapBtnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
    </script>

    <%-- tabStripGrid --%>
	<div class="s-row-fluid" id="tabstripEdit">
		<ul>
			<li class="k-state-active">物品柜</li>
			<li>手环</li>
		</ul>
		<div>
			<div id="cabinSubGridEdit"></div>
		</div>
		<div>
			<div id="strapSubGridEdit"></div>
		</div>
	</div>
</div>

<script src="${ctx}/views/pages/bdmHandlingArea/edit.js"></script>
</body>   