<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<body>
<%@ include file="common/header.jsp" %>

<div class="container case-content">
	<div class="row">
		<c:if test="${not empty error}">
			<span style="font-size:medium; color:red; text-align:center;">${error}</span>
		</c:if>
	</div>
	<div class="row">案件编号：${caseRecord.caseCode}</div>
	<div class="row">案件基本信息：${caseRecord.caseSummary}</div>
	<div class="row">案件类别：${caseRecord.caseType}</div>
	<div class="row">办理状态：${caseRecord.caseHandle}</div>
	<div class="row">主办单位：${caseRecord.masterUnit}</div>
	<div class="row">协办单位：${caseRecord.slaveUnit}</div>
	<div class="row">执法民警：${caseRecord.masterPoliceName}</div>
	<div class="row">案件接收单位：${caseRecord.masterUnit}</div>
	<div class="row">接案时间：${caseRecord.acceptDate}</div>
	<div class="row">立案时间：${caseRecord.registerDate}</div>
	<div class="row">办案时间：${caseRecord.startDate}</div>
	<div class="row dashed-line">
		是否已聘请律师：
		<c:if test="${caseRecord.hasLawyers}">已聘请律师</c:if>
		<c:if test="${!caseRecord.hasLawyers}">尚未聘请律师</c:if>
	</div>
	<table align="center" class="law_list" cellspacing="0">
		<tr>
			<td colspan="3">相关法律法规</td>
		</tr>
		<tr>
			<td>编号</td>
			<td>法律法规类型</td>
			<td>法律法规名称</td>
		</tr>
		<c:forEach items="${caseRecord.laws}" var="law" varStatus="status">
			<tr>
				<td>${status.index + 1}</td>
				<td>${law.type.value()}</td>
				<td><a href="${ctx}/lawContent.htm">${law.chapterName}</a></td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>