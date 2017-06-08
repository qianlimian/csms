<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div id="s-main-panel">

    <%@ include file="theme.jsp" %>

    <div class="s-container">
        <%@ include file="breadcrumb.jsp" %>

        <sitemesh:write property='body'/>

        <div id="notification"></div>
    </div>

</div>