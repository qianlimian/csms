<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="s-left-panel">
    <!--shrink 一级菜单-->
    <ul class="s-menu s-module-menu left-nav" style="display: none">
        <c:forEach items="${userModuleMenus}" var="moduleMenu">
            <li>
                <a>
                    <span class="fa fa-th-large"></span><b>${moduleMenu.name}</b>
                    <span class="s-icon-right fa ${moduleMenu.id == currentModuleMenu.id ?'fa-chevron-down':'fa-chevron-right'}"></span>
                </a>
                <!--二级菜单-->
                <ul class="s-menu s-group-menu" style="${moduleMenu.id == currentModuleMenu.id ?'display:block':'display:none'}">
                    <c:forEach items="${moduleMenu.groupMenus}" var="groupMenu">
                        <li>
                            <a>
                                <span class="fa fa-th-list"></span>${groupMenu.name}
                                <span class="s-icon-right fa ${groupMenu.id == currentGroupMenu.id ?'fa-chevron-down':'fa-chevron-right'}"></span>
                            </a>
                            <!--三级菜单-->
                            <ul class="s-menu s-leaf-menu" style="${groupMenu.id == currentGroupMenu.id ?'display:block':'display:none'}">
                                <c:forEach items="${groupMenu.leafMenus}" var="leafMenu">
                                    <li>
                                        <a href="${ctx}${leafMenu.url}" class="<c:if test='${leafMenu.id == currentLeafMenu.id}'>k-state-selected</c:if>"><span class="fa fa-caret-right"></span>${leafMenu.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                    <c:forEach items="${moduleMenu.leafMenus}" var="leafMenu">
                        <li>
                            <a href="${ctx}${leafMenu.url}" class="<c:if test='${leafMenu.id == currentLeafMenu.id}'>k-state-selected</c:if>"><span class="fa fa-caret-right"></span>${leafMenu.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>

    <!--expend 二级菜单-->
    <ul class="s-nav s-group-menu top-nav">
        <c:forEach items="${userGroupMenus}" var="groupMenu">
            <li>
                <a>
                    <span class="fa fa-th-list"></span>${groupMenu.name}
                    <span class="s-icon-right fa ${groupMenu.id == currentGroupMenu.id ?'fa-chevron-down':'fa-chevron-right'}"></span>
                </a>
                <!--三级菜单-->
                <ul class="s-nav s-leaf-menu" style="${groupMenu.id == currentGroupMenu.id ?'display:block':'display:none'}">
                    <c:forEach items="${groupMenu.leafMenus}" var="leafMenu">
                        <li>
                            <a href="${ctx}${leafMenu.url}" class="<c:if test='${leafMenu.id == currentLeafMenu.id}'>k-state-selected</c:if>"><span class="fa fa-caret-right"></span>${leafMenu.name}</a>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        <c:forEach items="${userLeafMenus}" var="leafMenu">
            <li>
                <a href="${ctx}${leafMenu.url}" class="<c:if test='${leafMenu.id == currentLeafMenu.id}'>k-state-selected</c:if>"><span class="fa fa-caret-right"></span>${leafMenu.name}</a>
            </li>
        </c:forEach>
    </ul>

	<div id="minify"><span class="fa fa-arrow-circle-left"></span></div>
</div>
