<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>执法办案监管系统</title>

  	<script src="${ctx}/views/smart/assets/jquery/jquery-2.2.3.min.js"></script>
    <script src="${ctx}/views/smart/login/index.js"></script>
    <script src="${ctx}/views/smart/login/js/verificationNumbers.js"></script>
    <script src="${ctx}/views/smart/login/js/particleground.js"></script>
	<link href="${ctx}/views/smart/login/css/index.css" rel="stylesheet"/>
</head>
<body>
	<dl class="admin_login">
		<dt>
			<img src="${ctx}/views/smart/assets/smart/images/login_title.png" width="100%">
		</dt>
		<form action="${ctx}/login" method="post">
			<dd class="user_icon">
				<input type="text" name="username" id="account" class="login_txtbx" />
			</dd>
			<dd class="pwd_icon">
				<input type="password" name="password" class="login_txtbx" />
			</dd>
			<span style="display:none;color:#cc0200" id="errorMsg">账号或密码错误</span>
			<span style="display:none;color:#cc0200" id="logoutMsg">退出成功</span>
			<dd>
				<input type="submit" value="登录" class="submit_btn" />
			</dd>
		</form>
	</dl>
</body>
</html>
