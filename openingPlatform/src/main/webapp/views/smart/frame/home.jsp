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
            <c:forEach items="${userPlugins}" var="plugin">
				<a href="${ctx}${plugin.moduleMenus[0].url}" style="text-align: center;" class="s-plugin-image-${fn:toLowerCase(plugin.plugin)}"><img src="${ctx}/views/smart/assets/smart/images/${fn:toLowerCase(plugin.plugin)}.png"/></a>
			</c:forEach>
  		</div>
	</div>
</body>   