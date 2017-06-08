<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div id="ctnCaseScore">
    <div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
    </div>

    <form class="s-row-fluid">
        <div class="s-row-fluid">
            <input type="hidden" id="score_id" value="${score.id}"/>
            <input type="hidden" id="score_caseRecordId" value="${score.caseRecordId}"/>

            <label class="s-span15">案件名称</label>
            <input class="s-span30" type="text" id="score_caseName" value="${score.caseName}" readonly/>
        </div>

        <div class="s-row-fluid">
            <label class="s-span15">分数</label>
            <input class="s-span30" type="text" id="score_totalScore" value="${score.totalScore}" readonly/>
        </div>

        <fieldset>
            <legend>评分项</legend>

            <c:forEach items="${score.itemDtos}" var="item">
            <div class='s-row-fluid'>
                <input type="hidden" name="item_id" value="${item.id}"/>
                <input type="hidden" name="item_evalId" value="${item.evalId}"/>

                <label class="s-span15">${item.standard}</label>
                <input class="s-span30" type="text" name="item_score" value="${item.score}" <c:if test="${item.evalType == '客观'}">readonly</c:if> />
            </div>
            </c:forEach>
        </fieldset>
    </form>
</div>

<script src="${ctx}/views/pages/caseScore/score.js"></script>
</body>
