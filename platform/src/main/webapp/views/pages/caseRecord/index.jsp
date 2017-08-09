<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri='/smart.tld' prefix='smart'%>

<body>
<div id="ctnCaseRecordIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
		<a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
		<a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
		<a id="btnDoHandle" class="k-button" smart-ability="handle"><span class="fa fa-user"></span>开始办案</a>
		<a id="btnDoFinish" class="k-button" smart-ability="handle"><span class="fa fa-check-circle"></span>结束办案</a>
        <a id="btnDoUpload" class="k-button" smart-ability="edit"><span class="fa fa-upload"></span>视频上传</a>
		<a id="btnAdvancedQuery" class="k-button" smart-ability="edit"><span class="fa fa-upload"></span>案件导出</a>
	</script>	
	<div id="mainGrid"></div>
</div>

<%--默认已经取消了查询窗口，有特殊需要的加--%>
<div id="ctnQueryWin" class="display-none">
    <div class="s-toolbar">
        <a class="k-button" id="btnDoQuery"><i class="fa fa-search"></i>查询</a>
		<a class="k-button" id="btnDoExport"><i class="fa fa-save"></i>导出</a>
    </div>
    <div class="s-query">
		<%--<div class="s-row-fluid">--%>
			<%--<label class="s-span10">主办单位</label>--%>
			<%--<input class="s-span15" type="text" id="master_unit"/>--%>

			<%--<label class="s-span10">办理状态</label>--%>
			<%--<input class="s-span15" type="text" id="handle_status" />--%>
		<%--</div>--%>
		<div class="s-row-fluid">
			<label class="s-span10">警情编号</label>
			<input class="s-span15" type="text" id="alarm_code"/>

			<label class="s-span10">案件类型</label>
			<input class="s-span15" type="text" id="case_type" />
		</div>
		<div class="s-row-fluid">
			<label class="s-span10">受理单位</label>
			<input class="s-span15" type="text" id="accept_unit"/>

			<label class="s-span10">受理人</label>
			<input class="s-span15" type="text" id="accept_police" />
		</div>
		<div class="s-row-fluid">
			<label class="s-span10">案发时间起</label>
			<input class="s-span15" type="text" id="occur_data_start"/>
			<label class="s-span10">案发时间止</label>
			<input class="s-span15" type="text" id="occur_data_end"/>
		</div>
		<div class="s-row-fluid">
			<label class="s-span10">受案时间起</label>
			<input class="s-span15" type="text" id="accept_data_start"/>
			<label class="s-span10">受案时间止</label>
			<input class="s-span15" type="text" id="accept_data_end"/>
		</div>
		<div class="s-row-fluid">
			<label class="s-span10">结案时间起</label>
			<input class="s-span15" type="text" id="close_data_start"/>
			<label class="s-span10">结案时间止</label>
			<input class="s-span15" type="text" id="close_data_end"/>
		</div>
    </div>
</div>

<div id="ctnCaseRecordEditWrap"></div>
<div id="ctnCaseMediaUploadWrap"></div>
<div id="ctnCaseRegisterWrap"></div>
<div id="ctnExportWrap"></div>
<script src="${ctx}/views/pages/caseRecord/index.js"></script>

<smart:select uri="com.bycc.entity.BdmPoliceStation.findAllPoliceStations" clazz="display-none" id="police_stations_select" />

<select id="polices_select" class="display-none">
	<c:forEach items="${polices}" var="p">
		<option value="${p.id}">${p.userName}</option>
	</c:forEach>
</select>
</body>   