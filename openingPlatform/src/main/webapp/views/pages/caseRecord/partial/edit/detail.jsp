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
        <input class="s-span18" type="text" id="edit_caseCode" data-bind="value:caseRecord.caseCode" disabled/>
        <label class="s-span9">案件状态</label>
        <input class="s-span18" type="text" id="edit_caseStatus" data-bind="value:caseRecord.caseStatus" disabled/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">嫌疑人姓名</label>
        <input class="s-span18" type="text" id="edit_suspect" data-bind="value:caseRecord.suspect" disabled/>
        <label class="s-span9">简要案情</label>
        <input class="s-span18" type="text" id="edit_caseSummary" data-bind="value:caseRecord.caseSummary" disabled/>
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">受案时间</label>
        <input class="s-span18" type="text" id="edit_acceptDate" data-bind="value:caseRecord.acceptDate" disabled/>

        <label class="s-span9">立案时间</label>
        <input class="s-span18" type="text" id="edit_registerDate" data-bind="value:caseRecord.registerDate" disabled/>
    </div>
   
    <div class="s-row-fluid">
        <div class="s-fieldset">
            <label>办案信息</label>
        </div>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">案件名称</label>
        <input class="s-span18" type="text" id="edit_caseName" data-bind="value:caseRecord.caseName"/>
        <label class="s-span9">案件类型</label>
        <input class="s-span18" type="text" id="edit_caseType" data-bind="value:caseRecord.caseType"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">主办单位</label>
        <input class="s-span18" type="text" id="edit_masterUnit" data-bind="value:caseRecord.masterUnit"/>

        <label class="s-span9">主办人</label>
        <input class="s-span18" type="text" id="edit_masterPoliceName" data-bind="value:caseRecord.masterPoliceName"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">协办单位</label>
        <input class="s-span18" type="text" id="edit_slaveUnit" data-bind="value:caseRecord.slaveUnit"/>
        <label class="s-span9">协办人</label>
        <input class="s-span18" type="text" id="edit_slavePoliceName" data-bind="value:caseRecord.slavePoliceName"/>
    </div>

    <div class="s-row-fluid">
        <label class="s-span9">办理状态</label>
        <input class="s-span18" type="text" id="edit_caseHandle" data-bind="value:caseRecord.caseHandle" disabled/>    
         <label class="s-span9">公开状态</label>
        <input class="s-span18" type="text" id="edit_open" data-bind="value:caseRecord.open" disabled/>        
    </div>
    <div class="s-row-fluid">
        <label class="s-span9">办案时间</label>
        <input class="s-span18" type="text" id="edit_startDate" data-bind="value:caseRecord.startDate"/>
        <label class="s-span9">结案时间</label>
        <input class="s-span18" type="text" id="edit_closeDate" data-bind="value:caseRecord.closeDate"/>
    </div>
</form>

<div id="ctnSelectWrap"></div>