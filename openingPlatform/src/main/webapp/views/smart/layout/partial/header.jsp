<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<header id='header'>
    <a class="header-logo" href="${ctx}/admin.htm" style="display: block;"></a>
    <div class="header-user">
        <c:if test="${not empty loginUser}">
            <label>欢迎您：</label>
            <a href="javascript:void(0);"><label class="fa fa-user"></label>${loginUser.loginName}</a>
            <a href="${ctx}/logout">[退出]</a>
        </c:if>
    </div>
    <div class="header-menu">
        <c:forEach items="${userPlugins}" var="plugin">
            <c:if test="${plugin.display}">
                <a href="${ctx}${plugin.moduleMenus[0].url}" class="<c:if test='${plugin.id == currentPlugin.id}'>k-state-selected</c:if>">${plugin.name}</a>
            </c:if>
        </c:forEach>
    </div>
</header>

<div id="userOperateSetting" style="display:none;">${userOperates}</div>