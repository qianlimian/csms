<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnCivilIndex">
    <script id="template" type="text/x-kendo-template">
        <a id="btnDoNew" class="k-button" smart-ability="edit" title="新增"><span class="fa fa-plus"></span>新增</a>
        <a id="btnDoEdit" class="k-button" smart-ability="edit" title="编辑"><span class="fa fa-edit"></span>编辑</a>
        <a id="btnDoDelete" class="k-button" smart-ability="delete" title="删除"><span class="fa fa-remove">删除</span></a>
        <a id="btnDoUpload" class="k-button" smart-ability="edit"><span class="fa fa-upload"></span>导入</a>    </script>
    <div id="mainGrid"></div>
</div>
<div id="ctnUploadWrap"></div>
<div id="ctnLawEditWrap"></div>
<script src="${ctx}/views/pages/law/index.js"></script>
</body>