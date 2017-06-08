<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>

<div id="ctnBdmEvaluationEdit">
	<div class="s-row-fluid s-toolbar">
       <!--  <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a> -->
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
	</div>

	<form class="s-row-fluid">
      	<div class="s-row-fluid">
      		<label class="s-span15 s-required">评价标准</label>
      		<input class="s-span30" type="text" id="edit_standard" data-bind="value:bdmEvaluation.standard" disabled/>
        </div>

        <div class="s-row-fluid">
			<label class="s-span15 s-required">加减分项</label>
			<input class="s-span30" type="text" id="edit_scoreType" data-bind="value:bdmEvaluation.scoreType" disabled/>
      	</div>

		<div class="s-row-fluid">
			<label class="s-span15 s-required">基础分值</label>
			<input class="s-span30" type="text" id="edit_score" data-bind="value:bdmEvaluation.score" />
		</div>

		<div class="s-row-fluid">
            <label class="s-span15 s-required">类别</label>
        	<input class="s-span30" type="text" id="edit_evalType" data-bind="value:bdmEvaluation.evalType" disabled/>
        </div>

        <div class="s-row-fluid">
            <label class="s-span15 ">备注信息</label>
        	<input class="s-span30" type="text" id="edit_note" data-bind="value:bdmEvaluation.note" />
        </div>
    </form>
</div>

<script src="${ctx}/views/pages/bdmEvaluation/edit.js"></script>
</body>