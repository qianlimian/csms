<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>

<div id="ctnBdmEvaluationEdit">
	<div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
	</div>
	<form class="s-row-fluid">
		<div class="display-none" >
      		<input type="text" id="editEval_id" value="${item.id }"/>
      		<input type="text" id="editEval_parent" value="${item.parent }"/>
        </div>
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">评价项</label>
      		<input class="s-span30" type="text" id="editEval_standard" value="${item.standard }"/>
        </div>
        <div class="s-row-fluid">
      		<label class="s-span15 s-required">分数</label>
      		<input class="s-span30" type="number" id="editEval_score" value="${item.score }"/>
        </div>
        <div class="s-row-fluid">
      		<label class="s-span15 s-required">排序</label>
      		<input class="s-span30" type="number" id="editEval_displayOrder" value="${item.displayOrder }"/>
        </div>
    </form>
</div>

<script src="${ctx}/views/pages/bdmEvaluation/editItem.js"></script>
</body>