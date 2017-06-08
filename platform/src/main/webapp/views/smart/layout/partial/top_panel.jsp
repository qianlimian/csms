<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="s-top-panel">
    <!--expend 一级菜单-->
    <ul class="s-nav s-module-menu top-nav">
        <c:forEach items="${userModuleMenus}" var="moduleMenu">
            <li>
                <a href="${ctx}${moduleMenu.url}" class="${moduleMenu.id == currentModuleMenu.id ?'k-state-selected':''}">${moduleMenu.name}</a>
            </li>
        </c:forEach>
    </ul>
</div>

