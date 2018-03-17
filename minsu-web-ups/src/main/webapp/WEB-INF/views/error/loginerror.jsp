<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
	<base href="<%=basePath%>">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${staticResourceUrl}/css/animate.css" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css?v=4.1.0" rel="stylesheet">
 </head>
  
 <body class="gray-bg">


    <div class="middle-box text-center animated fadeInDown">
        <h3 class="font-bold">${errorMsg }</h3>

        <div class="error-desc">
            您可以退出换个账号看看
            <br/><a href="logout" class="btn btn-primary m-t">安全退出</a>
        </div>
    </div>

    <!-- 全局js -->
    <script src="js/jquery.min.js?v=2.1.4"></script>
    <script src="js/bootstrap.min.js?v=3.3.6"></script>
  </body>
</html>
