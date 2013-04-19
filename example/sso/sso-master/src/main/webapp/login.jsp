<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录页面</title>
</head>
<body>
	<form action="login" method="post">
		<input name="clientURL" type="hidden" value="${clientURL }"> 
		<input name="clientJSessionId" type="hidden" value="${clientJSessionId }"> 
		<input name="clientLoggotURL" type="hidden" value="${clientLoggotURL }">
		名称:<input name="login" type="text"> <br />
		<input type="submit">
	</form>
</body>
</html>