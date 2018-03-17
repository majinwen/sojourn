<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
	<base href="${basePath}">
    <title>自如民宿管理系统 - 主页</title>

    <meta name="keywords" content="自如民宿管理系统">
    <meta name="description" content="自如民宿管理系统">

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

    <link href="${staticResourceUrl}/favicon.ico" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
</head>
<body >
	<%-- <div>
		<c:if test="${empty uid }">
			<a href="http://passport.q.ziroom.com/login.html?return_url=http://minsu.ziroom.com:8099">登陆</a>
			<a href="http://passport.q.ziroom.com/register.html?return_url=http://minsu.ziroom.com:8099">注册</a>
		</c:if>
		<c:if test="${not empty uid }">
			用户uid：${uid }
			<a href="http://passport.q.ziroom.com/logout.html?return_url=http://minsu.ziroom.com:8099">退出</a>
		</c:if>
	</div> --%>
	<ul>
		<li id="loginbar">您好！欢迎来到民宿！<a href="http://passport.q.ziroom.com/login.html?return_url=http://minsu.ziroom.com:8201">[登录]</a>&nbsp;<a href="http://passport.q.ziroom.com/register.html?return_url=http://minsu.ziroom.com:8201">[免费注册]</a></li>
	</ul>
	

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>

<script type="text/javascript">
var MINSU = {
		checkLogin : function(){
			$.ajax({
				url : "${basePath}user/login",
				dataType : "json",
				type : "GET",
				success : function(data){
					if(data.code == 0){
						var username = data.data.name;
						var html = username + "，欢迎来到自如民宿！<a href=\"http://passport.q.ziroom.com/logout.html?return_url=http://minsu.ziroom.com:8201\" class=\"link-logout\">[退出]</a>";
						$("#loginbar").html(html);
					}
				}
			});
		}
	}
	$(function(){
		// 查看是否已经登录，如果已经登录查询登录信息
		MINSU.checkLogin();
	});
	
</script>
</body>
</html>
