<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<style>
    html, body {
        height: auto !important;
    }

	form label {
		text-align: left;
	}

	form textarea {
		height: 50px !important;
	}

	form .toolbar {
		text-align: center;
		margin-top: 10px;
	}

	form .toolbar > a {
		width: 150px;
	}

    .k-grid .k-grid-content tr[role='row']{
        height: 30px;
        line-height: 30px;
    }
</style>

<div id="ctnCaseRegisterIndex">
	<div class="s-row-fluid">
        <%--嫌疑人--%>
		<script id="template" type="text/x-kendo-template">
			<a id="btnDoNew" class="k-button"><span class="fa fa-plus"></span>新增</a>
			<a id="btnDoEdit" class="k-button"><span class="fa fa-edit"></span>编辑</a>
			<a id="btnBindStrap" class="k-button"><span class="fa fa-link"></span>绑定手环</a>
			<a id="btnUnbindStrap" class="k-button"><span class="fa fa-chain-broken"></span>解绑手环</a>
			<a id="btnDoPrint" class="k-button"><span class="fa fa-print"></span>打印登记表</a>
		</script>
        <div class="s-pct50">
            <%--<div id="mainGrid"></div>--%>
            <div id="peopleGrid"></div>
        </div>

        <%--人身检查记录和信息采集--%>
        <div class="s-pct50">
            <form id="inspectForm">
                <fieldset>
                    <legend>人身安全检查</legend>

                    <div class="s-row-fluid">
                        <label class="s-span60">自述症状：（既往病史、是否饮酒、是否患有传染性等疾病）</label>
                        <textarea class="s-span60" name="statement" data-bind="value:inspect.statement"></textarea>
                    </div>

                    <div class="s-row-fluid">
                        <label class="s-span60">检查情况：（体表是否有伤痕、是否饮酒以及全身检查情况）</label>
                        <textarea class="s-span60" name="inspection" data-bind="value:inspect.inspection"></textarea>
                    </div>
                </fieldset>

                <fieldset>
                    <legend>信息采集</legend>

                    <div class="s-row-fluid">
                        <label class="s-span12">信息采集</label>

                        <div class="s-span12">
                            <label class="s-span12">是</label>
                            <input class="s-span12" type="radio" name="infoCollect" data-bind="checked: inspect.collectOrNot" value="true">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">否</label>
                            <input class="s-span12" type="radio" name="infoCollect" data-bind="checked: inspect.collectOrNot" value="false">
                        </div>
                    </div>

                    <div class="s-row-fluid">
                        <label class="s-span12">信息入库</label>

                        <div class="s-span12">
                            <label class="s-span12">是</label>
                            <input class="s-span12" type="radio" data-bind="checked: inspect.storeOrNot" value="true">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">否</label>
                            <input class="s-span12" type="radio" data-bind="checked: inspect.storeOrNot" value="false">
                        </div>
                    </div>

                    <div class="s-row-fluid">
                        <label class="s-span12">采集项目</label>

                        <div class="s-span12">
                            <label class="s-span12">指纹</label>
                            <input class="s-span12" type="checkbox" value="ZW" data-bind="checked: inspect.collectItems">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">血液</label>
                            <input class="s-span12" type="checkbox" value="XY" data-bind="checked: inspect.collectItems">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">尿液</label>
                            <input class="s-span12" type="checkbox" value="NY" data-bind="checked: inspect.collectItems">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">其它</label>
                            <input class="s-span12" type="checkbox" value="QT" data-bind="checked: inspect.collectItems">
                        </div>
                    </div>

                    <div class="s-row-fluid">
                        <label class="s-span12">核对比查</label>

                        <div class="s-span12">
                            <label class="s-span12">是</label>
                            <input class="s-span12" type="radio" data-bind="checked: inspect.verifyOrNot" value="true">
                        </div>

                        <div class="s-span12">
                            <label class="s-span12">否</label>
                            <input class="s-span12" type="radio" data-bind="checked: inspect.verifyOrNot" value="false">
                        </div>
                    </div>
                </fieldset>

                <div class="toolbar">
                    <a class="k-button" id="btnDoSavePeopleInspect"><i class="fa fa-save"></i>保存</a>
                </div>
            </form>
        </div>

        <%--随身财物检查记录列表--%>
		<script id="subtemplate" type="text/x-kendo-template">
			<a class="k-button" id="btnDoAddBelong"><i class="fa fa-plus"></i>增行</a>
			<a class="k-button" id="btnDoSaveBelong"><i class="fa fa-floppy-o"></i>保存</a>
			<a class="k-button" id="btnDoReturnBelong"><i class="fa fa-recycle"></i>返还</a>
		</script>
        <div class="s-pct100">
            <%--<div id="subGrid"></div>--%>
            <div id="peopleBelongsGrid"></div>
        </div>
	</div>
</div>

<%--编辑页面--%>
<div id="ctnCaseRegisterEditWrap"></div>

<%--绑定手环页面--%>
<div id="ctnBindStrapWrap"></div>

<%--解绑手环页面--%>
<div id="ctnUnbindStrapWrap"></div>

<%--查看办案区登记表窗口--%>
<div id="ctnPreviewWrap"></div>

<%-- combo的列表项 --%>
<select id="cabinet_select" class="display-none">
	<c:forEach items="${cabinets}" var="cabinet">
		<option value="${cabinet.id}">${cabinet.code}</option>
	</c:forEach>
</select>
<script src="${ctx}/views/pages/caseRegister/index.js"></script>
</body>