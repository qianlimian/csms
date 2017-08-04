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
  <!-- smart -->
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_base.css">
  <link rel="stylesheet" href="${ctx}/views/smart/assets/smart/styles/smart_layout.css">

  <!-- jQuery 2.2.3 -->
  <script src="${ctx}/views/smart/assets/jquery/jquery.min.js"></script>
  <!-- bootstrap-switch -->
  <script src="${ctx}/views/smart/assets/bootstrap-switch/js/bootstrap-switch.min.js"></script>

  <sitemesh:write property='head'/>  
</head>

<body>
<sitemesh:write property='body'/>
</body>

</html>

