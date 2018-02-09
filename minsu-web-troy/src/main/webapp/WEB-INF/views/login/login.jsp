<%--
  ~ Copyright (c) 2016. Copyright (c) 2016. ziroom.com. 
  --%>

<%--
  Created by IntelliJ IDEA.
  User: sunzhenlei
  Date: 2016/3/7
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>自如民宿管理系统系统 - 登录</title>
    <meta name="keywords" content="自如民宿管理系统">
    <meta name="description" content="自如民宿管理系统">

    <link rel="shortcut icon" href="${staticResourceUrl}/favicon.ico">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">

    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">H+</h1>

        </div>
        <h3>欢迎使用 自如民宿管理系统</h3>

        <form class="m-t" role="form" action="index.html">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" required="">
            </div>
            <button type="submit" class="btn btn-primary block full-width m-b">登 录</button>


            <p class="text-muted text-center">
                <%--<a href="login.html#">
                    <small>忘记密码了？</small>
                </a> | <a href="register.html">注册一个新账号</a>--%>
            </p>

        </form>
    </div>
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<%--<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>--%>
<!--统计代码，可删除-->

</body>

</html>