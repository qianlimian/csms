<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="${ctx}/views/smart/assets/fileUpload/css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="${ctx}/views/smart/assets/fileUpload/css/jquery.fileupload.css">
<script src="${ctx}/views/smart/assets/fileUpload/js/jquery-ui.min.js"></script>
<script src="${ctx}/views/smart/assets/fileUpload/js/jquery.iframe-transport.js"></script>
<script src="${ctx}/views/smart/assets/fileUpload/js/jquery.fileupload.js"></script>
<script src="${ctx}/views/smart/assets/fileUpload/js/jquery.fileupload-ui.js"></script>


<body>
<div id="ctnCaseMediaUpload">
    <div class="s-row-fluid" id="tabstrip">
        <ul>
            <li class="k-state-active">自动截取</li>
            <li>手动上传</li>
        </ul>
        <div>
            <div id="caseRecordId" class="display-none">${caseRecord.id}</div>
            <div class="s-span15 subArea">
                <div class="subAreaTitle">视频列表</div>
                <a id="btnDoTreeViewFresh"><span class="fa fa-refresh"></span></a>
                <div id="mediaTreeView"></div>
            </div>
            <div class="s-span30 subArea">
                <div class="subAreaTitle">视频预览</div>
                <video id="upload_mediaPreview" controls="controls" width="100%"></video>
            </div>
            <div class="s-span15 subArea">
                <div class="subAreaTitle">视频详情</div>
                <div class="subAreaDetail">
                    <label>视频名称：<span id="upload_mediaName"></span></label>
                    <label>房间名称：<span id="upload_roomName"></span></label>
                    <label>开始时间：<span id="upload_startDate"></span></label>
                    <label>结束时间：<span id="upload_endDate"></span></label>
                    <%--<label>视频类型：<input id="upload_categories"></label>--%>
                </div>
            </div>
            <div class="s-span15"></div>
            <div class="s-span30">
                <c:forEach items="${videoCategories}" var="category">
                    <label class="s-span6">${category.name}</label>
                    <input class="s-span3" type="radio" name="upload_category" value="${category.code}">
                </c:forEach>
            </div>
            <div style="float:right">
                <button class="btn btn-primary" id="btnDoMediaUpload">上  传</button>
            </div>
        </div>
        <div>
            <div class="fileContainer" id="fileContainer">
            	<div class="fileContainerTitle">
            		<span>视频上传列表</span>
            	</div>
            	<div class='fileContainerHeader s-row-fluid'>
            		<div class='s-pct5'>
            			<input type="checkbox" name="all_checkbox" onClick="allChecked()">
            		</div>
            		<div class='s-pct5'>类别</div>
            		<div class='s-pct20'>视频名称</div>
            		<div class='s-pct10'>视频大小</div>
            		<div class='s-pct45'>进度条</div>
            		<div class='s-pct10'>操作</div>
            	</div>
            </div>

            <div class="s-span15"></div>
            <div class="s-span30">
                <c:forEach items="${videoCategories}" var="category">
                    <label class="s-span6">${category.name}</label>
                    <input class="s-span3" type="radio" name="edit_mediaCategory" value="${category.code}">
                </c:forEach>
            </div>

            <div style="float:right">
                <span class="btn btn-primary fileinput-button" id="uploadButton">
                    <span>选取视频</span>
                    <input type="file" id="MediaFile" name="myFile">
                </span>
                <button class="btn btn-primary" onclick="batchUpload()">批量上传</button>
            </div>
        </div>
    </div>
</div>
<div>
</div>
<script src="${ctx}/views/pages/caseMedia/upload.js"></script>
<script src="${ctx}/views/pages/caseMedia/fileUpload.js"></script>
</body>
<style>
    #btnDoTreeViewFresh {
        float: right;
        margin: 0 10px;
    }
	
    .subArea {
        border: 1px solid silver;
        min-height: 400px !important;
    }

    .subAreaTitle {
        border-bottom: 1px solid silver;
        width: 100%;
        padding: 5px 0;
        text-align: center;
    }

    .subAreaDetail {
        padding: 5px;
        word-wrap: break-word;
    }

    .subAreaDetail > label {
        width: 100%;
        text-align: left;
    }

    .fileContainer {
        width: 100%;
        height: 400px;
        margin-bottom: 5px;
        border: 1px solid silver;
        text-align: center;
    }

    .fileContainerTitle {
        border-bottom: 1px solid silver;
        padding: 5px 0;
        text-align: center;
    }

    .fileContainerHeader {
        margin: 10px 0;
    }

    .s-row-fluid .s-pct5{
    	width:5%
    }
</style>