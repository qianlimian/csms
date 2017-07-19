<%--
  Created by IntelliJ IDEA.
  User: wanghaidong
  Date: 2017/7/12
  Time: 9:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="ctnCaseOpeningOption">
    <div class="s-row-fluid s-toolbar">
        <%--<a class="k-button" id="btnDoFind"><i class="fa fa-plus"></i>查询</a>--%>
        <a class="k-button" id="btnDoExport"><i class="fa fa-save"></i>导出</a>
    </div>
    <form class="s-row-fluid">
        <div class="s-row-fluid">
            <label class="s-span6">办理状态</label>
            <input class="s-span12" type="text" id="handle_status" />
        </div>
        <div class="s-row-fluid">
            <label class="s-span6 ">主办单位</label>
            <input class="s-span12" type="text" id="master_unit"/>
            <%--<label class="s-span6">主办人</label>
            <input class="s-span12" type="text" id="master_police"/>--%>
        </div>
        <div class="s-row-fluid">
            <label class="s-span6 ">受案时间范围</label>
            <input class="s-span8" type="text" id="accept_data_start"/>
            <input class="s-span8" type="text" id="accept_data_end"/>
        </div>
        <div class="s-row-fluid">
            <label class="s-span6">结案时间范围</label>
            <input class="s-span8" type="text" id="close_data_start"/>
            <input class="s-span8" type="text" id="close_data_end"/>
        </div>
    </form>

</div>
<script src="${ctx}/views/pages/caseOpening/option.js"></script>
<select id="policeStation_select" class="display-none">
    <c:forEach items="${policeStations}" var="policeStation">
        <option value="${policeStation.id}">${policeStation.name}</option>
    </c:forEach>
</select>
</body>
</html>
