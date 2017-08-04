<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<ol class="s-breadcrumb">
    <li><a href="${ctx}">首页</a></li>
    <li><a href="${ctx}${currentModuleMenu.url}">${currentModuleMenu.name}</a></li>
    <c:if test="${not empty currentGroupMenu}">
        <li>${currentGroupMenu.name}</li>
    </c:if>
    <li>${currentLeafMenu.name}</li>
</ol>
