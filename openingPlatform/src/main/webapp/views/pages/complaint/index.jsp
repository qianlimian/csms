<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<body>
<div id="ctnComplaintIndex">
	<script id="template" type="text/x-kendo-template">
		<a id="btnShowReply" class="k-button"><span class="fa fa-envelope-o"></span>回复</a>
		<a id="btnExport" class="k-button"><span class="fa fa-file-excel-o"></span>导出</a>
	</script>
	<div id="mainGrid"></div>
</div>
<div id="ctnComplaintEditWrap"></div>
<div id="ctnReplyWrap"></div>
<script src="${ctx}/views/pages/complaint/index.js"></script>

</body>