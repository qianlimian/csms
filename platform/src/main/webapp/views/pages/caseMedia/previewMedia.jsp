<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>

<style>
		
    .previewMedia {
        border: 1px solid silver;
        min-height: 355px !important;
    }

    .previewTitle {
        border-bottom: 1px solid silver;
        width: 100%;
        padding: 5px 0;
        text-align: center;
    }

    .previewDetail {
        padding: 5px;
        word-wrap: break-word;
    }

    .previewDetail > label {
        width: 100%;
        text-align: left;
    }
    .s-row-fluid .s-pct5{
    	width:5%
    }
    .s-row-fluid .s-pct8{
    	width:8%
    }
    .s-row-fluid .s-span70{
    	width:70%;
    }
    .s-row-fluid .s-span25{
    	width:25%;
    }
</style>

<%--视频浏览--%>
<div id="playWindow" class="s-row-fluid">
	 <div class="s-span70 previewMedia">
	      <div class="previewTitle">视频内容</div>
	      <video controls="controls" width="100%" src="${media.url }"></video>
	  </div>
	  <div class="s-span25 previewMedia">
	      <div class="previewTitle">视频详情</div>
	      <div class="previewDetail">
	          <label>视频名称:<span>${media.title }</span></label>
	          <label>视频分类:<span>${media.category }</span></label>	          
	          <label>所属案件:<span>${media.caseName }</span></label>
	          <label>涉案人员:<span>${media.casePeopleName }</span></label>
	          <label>主办民警:<span>${media.masterPoliceName }</span></label>
	          <label>协办民警:<span>${media.slavePoliceName }</span></label>
	          <label>主办单位:<span>${media.masterPoliceStation }</span></label>
	          <label>协办单位:<span>${media.slavePoliceStation }</span></label>
	          <label>时间:<span>${media.updateDate }</span></label>	         
	      </div>	      
	  </div>
</div>
</body>
