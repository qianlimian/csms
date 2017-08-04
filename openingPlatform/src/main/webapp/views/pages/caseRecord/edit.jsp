<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div id="ctnCaseRecordEdit">
    <div class="s-row-fluid" id="tabstrip">
        <ul>
            <li class="k-state-active">案件信息</li>
            <li>律师</li>
            <li>法律法规</li>
        </ul>
        <div>
            <jsp:include page="partial/edit/detail.jsp"></jsp:include>
        </div>
        <div>
            <jsp:include page="partial/edit/editLawyer.jsp"></jsp:include>
        </div>
        <div>
            <jsp:include page="partial/edit/editLaw.jsp"></jsp:include>
        </div>
    </div>
</div>

<script src="${ctx}/views/pages/caseRecord/edit.js"></script>
</body>