<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <title>资质认证</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">

</head>

<body>

<!-- <form accept="" method="">
-->	<div class="main myCenterListNoneA">
    <header class="header">
        <a href="${basePath}auth/${loginUnauth}/init?checkFlag=1" id="goback" class="goback" ></a>
        <h1>联系方式</h1>
    </header>
    <ul class="myCenterList ">
        <li class="clearfix">
            <a href="${basePath}auth/${loginUnauth}/initAddTel">
                修改联系方式
                <input type="text" id="customerMobile" name="customerMobile" value="${customerMobile}" >
                <span class="icon_r icon" ></span>
            </a>
        </li>

    </ul>
</div><!--/main-->

<!-- </form> -->
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>

</body>
</html>