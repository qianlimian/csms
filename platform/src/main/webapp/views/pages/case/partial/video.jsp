<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="s-row-fluid">
    <div class="s-span15 subArea">
        <label class="subAreaTitle">视频列表</label>
        <a id="btnDoTreeViewFresh"><span class="fa fa-refresh"></span></a>
        <div id="video_mediaTreeView"></div>
    </div>
    <div class="s-span30 subArea">
        <label class="subAreaTitle">视频预览</label>
        <video id="video_mediaPreview" controls="controls" width="100%"></video>
    </div>
    <div class="s-span15 subArea">
        <label class="subAreaTitle">视频详情</label>
        <div class="subAreaDetail">
            <label>视频名称：<span id="video_mediaName"></span></label>
            <label>视频分类：<span id="video_category"></span></label>
            <label>上传时间：<span id="video_date"></span></label>
        </div>
    </div>
</div>
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
</style>