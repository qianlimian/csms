<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnBdmRoomIndex">
    <div class="s-row-fluid">
        <div class="s-pct30" id="indexGrid"></div>
        <div class="s-pct70">
            <script id="template" type="text/x-kendo-template">
                <a id="btnDoNew" class="k-button" smart-ability="edit"><span class="fa fa-plus"></span>新增</a>
                <a id="btnDoEdit" class="k-button" smart-ability="edit"><span class="fa fa-edit"></span>编辑</a>
                <a id="btnDoDelete" class="k-button" smart-ability="delete"><span class="fa fa-remove"></span>删除</a>
            </script>
            <div id="mainGrid"></div>

            <div id="tabstrip">
                <ul>
                    <li class="k-state-active">基站</li>
                    <li>摄像头</li>
                </ul>
                <div>
                    <div id="stationSubGrid"></div>
                </div>
                <div>
                    <div id="cameraSubGrid"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="ctnBdmRoomEditWrap"></div>

<script src="${ctx}/views/pages/bdmRoom/index.js"></script>

</body>

