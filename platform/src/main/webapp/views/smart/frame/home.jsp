<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<title>首页</title>
<body>
	<div class="s-portal-shortcut">
  		<div class="s-fieldset">
    		<label>系统快捷入口</label>
  		</div>
		<div class="s-portal-image">
            <%--<c:forEach items="${userPlugins}" var="plugin">
				<a href="${ctx}${plugin.moduleMenus[0].url}" class="s-plugin-image-${fn:toLowerCase(plugin.plugin)}"></a>
			</c:forEach>--%>

            <%--changed：显示module菜单，按项目需求调整--%>
            <c:forEach items="${userPlugins[0].moduleMenus}" var="moduleMenu">
                <a href="${ctx}${moduleMenu.url}" class="s-plugin-image-${fn:toLowerCase(moduleMenu.name)}"></a>
            </c:forEach>
  		</div>
	</div>
</body>   