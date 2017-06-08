<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<body>
<style>
    .k-pager-sizes {
        padding: 0;
    }
</style>
<div id="ctnBdmRoomIndex">
    <div class="s-row-fluid">
        <div class="s-pct20" id="indexGrid"></div>
        <div class="s-pct80">
            <div id="mainGrid" class="s-pct20 room-list">
                <div class="k-grid-header title-room">
                    <a class="k-link" href="#">房间列表</a>
                </div>
            </div>
            <div class="s-pct80">
                <div class="k-header k-grid-toolbar">
                    <a id="btnDoSave" class="k-button"><span class="fa fa-plus"></span>保存</a>
                </div>
                <div id="snaptarget" class="ui-widget-header">
                </div>
            </div>
        </div>
    </div>
</div>
<div id="ctnBdmRoomEditWrap"></div>
<script src="${ctx}/views/pages/bdmRoomLayout/index.js"></script>
</body>

