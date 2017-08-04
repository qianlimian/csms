<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="s-row-fluid s-toolbar">
    <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
    <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
    <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
</div>
<form class="s-row-fluid">
    <div class="display-none">
        <input type="text" id="edit_caseId" data-bind="value:caseRecord.caseId"/>
        <input type="text" id="edit_id" data-bind="value:caseRecord.id"/>
    </div>

    <div class="s-row-fluid">
        <div class="s-fieldset">
            <label>案件信息</label>
        </div>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案件编号</label>
        <input class="s-span15" type="text" id="edit_caseCode" data-bind="value:caseRecord.caseCode" disabled/>
        <a id="btnDoShow" class="k-button s-span3"><span class="fa fa-search"></span></a>

        <label class="s-span9">警情编号</label>
        <input class="s-span18" type="text" id="edit_alarmCode" data-bind="value:caseRecord.alarmCode" disabled/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">嫌疑人姓名</label>
        <input class="s-span18" type="text" id="edit_suspect" data-bind="value:caseRecord.suspect" disabled/>
        <label class="s-span9">简要案情</label>
        <input class="s-span18" type="text" id="edit_caseSummary" data-bind="value:caseRecord.caseSummary" disabled/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">案发时间</label>
        <input class="s-span18" type="text" id="edit_occurDate" data-bind="value:caseRecord.occurDate" disabled/>
        <label class="s-span9">受案时间</label>
        <input class="s-span18" type="text" id="edit_acceptDate" data-bind="value:caseRecord.acceptDate" disabled/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">立案时间</label>
        <input class="s-span18" type="text" id="edit_registerDate" data-bind="value:caseRecord.registerDate" disabled/>
    </div>

    <div class="s-row-fluid">
        <div class="s-fieldset">
            <label>办案信息</label>
        </div>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9 s-required">案件名称</label>
        <input class="s-span18" type="text" id="edit_caseName" data-bind="value:caseRecord.caseName"/>
        <label class="s-span9 s-required">案件类型</label>
        <input class="s-span18" type="text" id="edit_caseType" data-bind="value:caseRecord.caseType"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9 s-required">主办单位</label>
        <input class="s-span18" type="text" id="edit_masterUnitId" data-bind="value:caseRecord.masterUnitId"/>

        <label class="s-span9 s-required">主办人</label>
        <input class="s-span18" type="text" id="edit_masterPoliceId" data-bind="value:caseRecord.masterPoliceId"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9 s-required">协办单位</label>
        <input class="s-span18" type="text" id="edit_slaveUnitId" data-bind="value:caseRecord.slaveUnitId"/>

        <label class="s-span9 s-required">协办人</label>
        <input class="s-span18" type="text" id="edit_slavePoliceId" data-bind="value:caseRecord.slavePoliceId"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">办案时间</label>
        <input class="s-span18" type="text" id="edit_startDate" data-bind="value:caseRecord.startDate"/>
        <label class="s-span9">结案时间</label>
        <input class="s-span18" type="text" id="edit_closeDate" data-bind="value:caseRecord.closeDate"/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">办理状态</label>
        <input class="s-span18" type="text" id="edit_caseHandle" data-bind="value:caseRecord.caseHandle" disabled/>
    </div>
</form>

<div id="ctnSelectWrap"></div>