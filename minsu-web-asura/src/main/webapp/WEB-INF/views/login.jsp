<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>asura服务监控和管理平台</title>
<link href="js/dwz/themes/css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div id="login">
		<div id="login_content">
			<div class="loginForm">
				<form action="login.do" method="post">
					<p>
						<label>用户名：</label>
						<input type="text" name="loginName" size="20" class="login_input" value="" />
					</p>
					<p>
						<label>密码：</label>
						<input type="password" name="loginPwd" size="20" class="login_input" value="" />
					</p>
					
					<p style="color: red">
						<label>&nbsp;</label>
						<c:if test="${not empty error}">
							<c:out value="${error}"></c:out>
						</c:if>
					</p>
					
					<div class="login_bar">
						<input class="sub" type="submit" value=" " />
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="login_footer">
		Copyright &copy; 2011-2015 Asura 版权所有. All Rights Reserved.
	</div>
</body>
</html>