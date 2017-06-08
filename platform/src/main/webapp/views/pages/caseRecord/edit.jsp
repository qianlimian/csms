<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div id="ctnCaseRecordEdit">
    <div class="s-row-fluid" id="tabstrip">
        <ul>
            <li class="k-state-active">案件信息</li>
            <li>办案视频</li>
            <li>办案登记表</li>
        </ul>
        <div>
            <jsp:include page="partial/case.jsp"></jsp:include>
        </div>
        <div>
            <jsp:include page="partial/video.jsp"></jsp:include>
        </div>
        <div>
            <jsp:include page="partial/table.jsp"></jsp:include>
        </div>
    </div>
</div>

<script src="${ctx}/views/pages/caseRecord/edit.js"></script>
</body>   
