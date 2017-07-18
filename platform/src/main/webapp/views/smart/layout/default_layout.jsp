<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>  
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><sitemesh:write property='title'/></title>
  <script type="text/javascript">
    var basePath = '<%=basePath%>';
  </script>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${ctx}/views/smart/assets/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/bootstrap-switch/css/bootstrap-switch.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${ctx}/views/smart/assets/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/font-awesome/css/font-awesome-animation.min.css">
  <!-- kendoUI -->
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.common.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.black.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.blueopal.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.bootstrap.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.default.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.fiori.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.flat.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.highcontrast.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.metro.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.moonlight.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/kendoui/styles/kendo.silver.css">
  <!-- smart -->
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_kendoui_enhances.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_base.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_layout.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_frame.css">

  <!-- jQuery 2.2.3 -->
  <script src="${ctx}/views/smart/assets/jquery/jquery.min.js"></script>
  <!-- bootstrap-switch -->
  <script src="${ctx}/views/smart/assets/bootstrap-switch/js/bootstrap-switch.min.js"></script>
  <!-- kendoUI -->
  <script src="${ctx}/views/smart/assets/kendoui/js/kendo.web.min.js"></script>
  <!-- smart -->
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_kendoui_enhances.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_kendoui_extends.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_kendoui_searchbox.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_ability.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_ajax.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_data.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_dialog.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_enum.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_event.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_layout.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_navbar.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_theme.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart_utils.js"></script>
  
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-index-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-edit-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-single-index-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-single-edit-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-multi-index-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-multi-edit-module.js"></script>
  <script src="${ctx}/views/smart/assets/smart/javascripts/smart-select-module.js"></script>
  
  <sitemesh:write property='head'/>  
</head>

<body>
<div id="s-panel" class="${userSetting.pageWidth} ${userSetting.menuPosition}">
  <%@ include file="partial/header.jsp" %>

  <%@ include file="partial/panel.jsp" %>

  <%@ include file="partial/footer.jsp" %>
</div>
</body>

</html>

