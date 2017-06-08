<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div class="s-row-fluid s-toolbar">
	<a class="k-button" id="btnDoUnbindStrap"><i class="fa fa-floppy-o"></i>保存</a>
</div>
<form class="s-row-fluid">

	<div class="s-row-fluid">
		<label class="s-span15">离开原因</label>
		<textarea class="s-span30" data-bind="value:casePeople.leaveReason"></textarea>
	</div>

	<div class="s-row-fluid">
		<label class="s-span15">随身物品处理情况</label>
		<div class="s-span30">

            <div class="s-span30">
                <label class="s-span24">全部返还</label>
                <input class="s-span12" type="radio" name="allBelongsReturn" data-bind="checked:casePeople.allBelongsReturn" value="true">
            </div>

            <div class="s-span30">
                <label class="s-span24">部分返还</label>
                <input class="s-span12" type="radio" name="allBelongsReturn" data-bind="checked:casePeople.allBelongsReturn" value="false">
            </div>
		</div>
	</div>

	<div class="s-row-fluid">
		<label class="s-span15">未返还物品情况记载</label>
		<textarea class="s-span30" data-bind="value:casePeople.note"></textarea>
	</div>
</form>
<script src="${ctx}/views/pages/caseRegister/partial/unbindStrap.js"></script>
</body>
