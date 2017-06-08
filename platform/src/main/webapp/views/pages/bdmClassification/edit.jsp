<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnBdmClassificationEdit">
    <div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
    </div>

    <form class="s-row-fluid">
        <div class="s-row-fluid">
            <label class="s-span15 s-required">案件类型</label>
            <input class="s-span30" type="text" id="edit_caseType" data-bind="value:bdmClassification.caseType" />
        </div>

        <div class="s-row-fluid">
            <label class="s-span15 s-required">关键词</label>
            <input class="s-span30" type="text" id="edit_keyWord" data-bind="value:bdmClassification.keyWord" />
        </div>

        <div class="s-row-fluid">
            <label class="s-span15 s-required">风险等级</label>
            <input class="s-span30" type="text" id="edit_riskLevel" data-bind="value:bdmClassification.riskLevel" />
        </div>

        <div class="s-row-fluid">
            <label class="s-span15">备注</label>
            <input class="s-span30" type="text" id="edit_note" data-bind="value:bdmClassification.note" />
        </div>

    </form>
</div>

<script src="${ctx}/views/pages/bdmClassification/edit.js"></script>
</body>