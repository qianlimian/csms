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
            <li class="k-state-active">办案区视频</li>
            <li>本地视频</li>
        </ul>
        <div>
            <div id="caseRecordId" class="display-none">${caseRecord.id }</div>
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
                    <label>视频类型：<input id="upload_categories"></label>
                </div>
                
            </div>
            <div style="text-align: center;float:right">
                    <a id="btnDoMediaUpload" class="k-button" disabled><span class="fa fa-upload"></span>上传</a>
                </div>
        </div>
        <div style="height:300px">
            <div>
                <input id="edit_mediaCategory">
                <span class="btn btn-success fileinput-button k-state-disabled" id="uploadButton">
                    <i class="glyphicon glyphicon-plus"></i>
                    <span>选取视频</span>
                    <input type="file" id="MediaFile" name="myFile" disabled="disabled">
                </span>
            </div>
            
            <div class="fileContainer" id="fileContainer" align='center'>
            	<div class="fileListtitle">
            		<span>视频上传列表</span>
            		<div style="float:right"><button onclick="batchUpload()">批量上传</button></div>
            	</div>
            	<div style='clear:both;margin-bottom:30px;margin-top: 6px;'>
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
        </div>
    </div>
</div>
<div>
</div>
<script src="${ctx}/views/pages/caseMedia/upload.js"></script>
<script src="${ctx}/views/pages/caseMedia/fileUpload.js"></script>
</body>
<style>
	.k-treeview span.k-in{
		overflow: hidden; 
		text-overflow:ellipsis; 
		white-space: nowrap;
		cursor:default;
		width:92%;
	}
	#fileName{
	word-wrap: break-word;
	}
	.fileListtitle{
		border-bottom: 1px solid silver;
		height: 21px;
    	padding: 4px;
	}
    .fileContainer {
        width: 100%;
        min-height: 80%;
        margin-top: 5px;
        border: 1px solid silver;
    }

    #btnDoTreeViewFresh {
        float: right;
        margin: 0 10px;
    }
	
    .subArea {
        border: 1px solid silver;
        min-height: 300px !important;
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
    .s-row-fluid .s-pct5{
    	width:5%
    }
    .s-row-fluid .s-pct8{
    	width:8%
    }
</style>