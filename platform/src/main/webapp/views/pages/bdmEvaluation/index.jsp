<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div class="s-row-fluid">
    <div class="s-pct20 e-panel">
        <div class="e-header"><span>评分项</span></div>
        <div class="e-toolbar">
            <a id="btnDoNewItem" smart-ability="edit" title="新增"><span class="fa fa-plus"></span></a>
            <a id="btnDoEditItem" smart-ability="edit" title="编辑"><span class="fa fa-edit"></span></a>
            <a id="btnDoDeleteItem" smart-ability="delete" title="删除"><span class="fa fa-remove"></span></a>
        </div>
        <div class="e-content">
            <div id="rootItem"></div>
        </div>
    </div>
    <div class="s-pct80 e-panel">
        <div class="e-header"><span>评分细则</span></div>
        <div class="e-toolbar" style="text-align: left;padding-left: 5px;">
            <a id="btnDoNewSub" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
            <a id="btnDoEditSub" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
            <a id="btnDoDeleteSub" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
        </div>
        <div class="e-content" style="text-align: left;">
            <div id="detailItems"></div>
        </div>
    </div>
</div>

<div id="ctnSelectWrap"></div>

<script src="${ctx}/views/pages/bdmEvaluation/index.js"></script>
<style>
    .e-header,
    .e-toolbar {
        line-height: 30px;
        text-align: center;
        padding-left: 10px;
        border: 1px solid #ccc;
    }
    .e-toolbar a:not(.k-button) {
        padding: 0 5px;
    }
    .e-toolbar a.k-button {
        vertical-align: baseline;
    }
    .e-content {
        text-align: center;
        border: 1px solid #ccc;
        min-height: 400px;
    }
</style>
</body>