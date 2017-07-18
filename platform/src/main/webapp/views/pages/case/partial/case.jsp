<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<form class="s-row-fluid">

    <div class="s-row-fluid">
        <div class="s-fieldset">
            <label>案件信息</label>
        </div>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案件编号</label>
        <input class="s-span18" type="text" id="edit_caseCode" data-bind="value:case.caseCode"/>

        <label class="s-span9">警情编号</label>
        <input class="s-span18" type="text" id="edit_alarmCode" data-bind="value:case.alarmCode"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案件名称</label>
        <input class="s-span18" type="text" id="edit_caseName" data-bind="value:case.caseName"/>

        <label class="s-span9">嫌疑人姓名</label>
        <input class="s-span18" type="text" id="edit_suspect" data-bind="value:case.suspect"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案件类型</label>
        <input class="s-span18" type="text" id="edit_caseType" data-bind="value:case.caseType"/>

        <label class="s-span9">案件状态</label>
        <input class="s-span18" type="text" id="edit_caseStatus" data-bind="value:case.caseStatusName"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案发时间</label>
        <input class="s-span18" type="text" id="edit_occurDate" data-bind="value:case.occurDate"/>

        <label class="s-span9">风险等级</label>
        <input class="s-span18" type="text" id="edit_riskLevel" data-bind="value:case.riskLevel"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">受案时间</label>
        <input class="s-span18" type="text" id="edit_acceptDate" data-bind="value:case.acceptDate"/>

        <label class="s-span9">立案时间</label>
        <input class="s-span18" type="text" id="edit_registerDate" data-bind="value:case.registerDate"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">简要案情</label>
        <input class="s-span45" type="text" id="edit_caseSummary" data-bind="value:case.caseSummary"/>
    </div>

    <div class="s-row-fluid">
        <div class="s-fieldset">
            <label>办案信息</label>
        </div>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">主办单位</label>
        <input class="s-span18" type="text" id="edit_masterUnit" data-bind="value:case.masterUnit"/>

        <label class="s-span9">主办人</label>
        <input class="s-span18" type="text" id="edit_masterPoliceName" data-bind="value:case.masterPoliceName"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">协办单位</label>
        <input class="s-span18" type="text" id="edit_slaveUnit" data-bind="value:case.slaveUnit"/>

        <label class="s-span9">协办人</label>
        <input class="s-span18" type="text" id="edit_slavePoliceName" data-bind="value:case.slavePoliceName"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">办案时间</label>
        <input class="s-span18" type="text" id="edit_startDate" data-bind="value:case.startDate"/>

        <label class="s-span9">结案时间</label>
        <input class="s-span18" type="text" id="edit_closeDate" data-bind="value:case.closeDate"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">同步时间</label>
        <input class="s-span18" type="text" id="edit_insertDate" data-bind="value:case.insertDate"/>

        <label class="s-span9">更新时间</label>
        <input class="s-span18" type="text" id="edit_updateDate" data-bind="value:case.updateDate"/>
    </div>
</form>