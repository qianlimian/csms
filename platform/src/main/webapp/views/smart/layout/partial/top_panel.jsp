<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="s-top-panel">
    <!--一级菜单-->
    <ul class="s-top-menu s-module-menu s-expend">
        <c:forEach items="${userModuleMenus}" var="moduleMenu">
            <li>
                <a href="${ctx}${moduleMenu.url}" class="${moduleMenu.id == currentModuleMenu.id ?'k-state-selected':''}">${moduleMenu.name}</a>
            </li>
        </c:forEach>
    </ul>
</div>

