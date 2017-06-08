<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnBdmRoomEdit">
    <%-- formToolbar --%>
    <div class="s-row-fluid s-toolbar">
        <a class="k-button" id="btnDoNew" smart-ability="edit"><i class="fa fa-plus"></i>新增</a>
        <a class="k-button" id="btnDoSave" smart-ability="edit"><i class="fa fa-save"></i>保存</a>
        <a class="k-button" id="btnDoDelete" smart-ability="delete"><i class="fa fa-remove"></i>删除</a>
    </div>

    <form class="s-row-fluid">
        <div class="s-row-fluid">
            <input type="hidden" id="edit_handlingAreaId" data-bind="value:bdmRoom.handlingAreaId" />

            <label class="s-span6 s-required">编码</label>
            <input class="s-span12" type="text" id="edit_code" data-bind="value:bdmRoom.code" />

            <label class="s-span6 s-required">名称</label>
            <input class="s-span12" type="text" id="edit_name" data-bind="value:bdmRoom.name" />

            <label class="s-span6 s-required">房间类型</label>
            <input class="s-span12" type="text" id="edit_roomType" data-bind="value:bdmRoom.roomType" />
        </div>

        <div class="s-row-fluid">
            <label class="s-span6">房间状态</label>
            <input class="s-span12" type="text" id="edit_status" data-bind="value:bdmRoom.status" />
        </div>
    </form>

    <%-- gridToolbar --%>
    <script id="station_template" type="text/x-kendo-template">
        <a class="k-button" id="stationBtnDoNewSub" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
        <a class="k-button" id="stationBtnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
    </script>

    <script id="camera_template" type="text/x-kendo-template">
        <a class="k-button" id="cameraBtnDoNewSub" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
        <a class="k-button" id="cameraBtnDoDeleteSub" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
    </script>

    <%-- tabStripGrid --%>
    <div class="s-row-fluid" id="tabstripEdit">
        <ul>
            <li class="k-state-active">基站</li>
            <li>摄像头</li>
        </ul>
        <div>
            <div id="stationSubGridEdit"></div>
        </div>
        <div>
            <div id="cameraSubGridEdit"></div>
        </div>
    </div>
</div>

<script src="${ctx}/views/pages/bdmRoom/edit.js"></script>
</body>   

