<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'nologin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
		<!--
		function jumpLoginPage() {
			window.top.location.href = "${pageContext.request.contextPath}";
		}
		//-->
		</script>
  </head>
  
  <body>
    <font size="3" color="red">对不起！可能超时了，请您重新登录！</font><br>
    <span id="time" style="color:red">5</span>秒钟后自动跳转，如果不跳转，请点击下面链接<a href="javascript:jumpLoginPage();">重新登录</a>

		<script type="text/javascript">
		<!--
		function delayURL(url) {
			var delay = document.getElementById("time").innerHTML;
			if(delay > 0) {
			   delay--;
			   document.getElementById("time").innerHTML = delay;
			} else {
			   window.top.location.href = url;
			}
		    setTimeout("delayURL('" + url + "')", 1000); //delayURL(http://wwer)
		}
		
		delayURL("${pageContext.request.contextPath}");
		//-->
		
		</script>
  </body>
</html>
