<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<body>
<div id="ctnUserSelect" style="height:100%;">
    <script id="template" type="text/x-kendo-template">
        <a id="btnDoSelect" class="k-button"><span class="fa fa-check-square-o"></span>选取</a>
    </script>

    <div id="mainGrid"></div>
</div>

<script src="${ctx}/views/smart/frame/user/select.js"></script>
</body>
