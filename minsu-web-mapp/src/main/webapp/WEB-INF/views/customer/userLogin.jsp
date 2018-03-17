<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">    
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>登录</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
	
</head>
<body>

<div class="loginBox">
	<div class="logo"></div>

	<div class="content">
		
		<form class="checkFormChild" >
		
		     <input type="hidden" id="phone"  value=""/>
		     <input type="hidden" id=email  value=""/>
		     <input type="hidden" id="userName"  value=""/>
		     <input type="hidden" id="password"  value=""/>
		     <input type="hidden" id="currImgId"  value=""/>
		     <input type="hidden" id="currImgVValue"  value=""/>
			<ul class="loginList">
				<li class="bor_b">
					<span class="l_icon icon_user"></span>
					<input type="text" placeholder="手机号/邮箱" id="login_user"  onkeyup="inputVal(this,'#forUser')" onfocus="inputVal(this,'#forUser')">
					<span class="l_icon icon_close" id="forUser"  onclick="clearInp(this,'#login_user')"></span>
				</li>
				<li class="bor_b">
					<span class="l_icon icon_pas"></span>
					<input type="password" placeholder="密码" id="login_pas"  onkeyup="inputVal(this,'#forPas')" onfocus="inputVal(this,'#forPas')" >
					<span class="l_icon icon_close" id="forPas"  onclick="clearInp(this,'#login_pas')" onfocus="clearInp(this,'#login_pas')"></span>
				</li>
				<li class="bor_b" id="userVcode" style="display: none;" >
					<span class="l_icon icon_code" ></span>
					<input type="text" placeholder="验证码" id="imgVValue"   onkeyup="inputVal(this,'#forCode')" onfocus="inputVal(this,'#forCode')">
					<img  class="imgCode" id="imgUrlId"	src="${imgUrl }${imgId}.jpg" imgId="${imgId }"  imgUrl="${imgUrl }"  id="imgUrlId" onclick="Customer.reloadImg()">
					<span class="l_icon icon_close forCode" id="forCode"   onclick="clearInp(this,'#imgVValue')"></span>
				</li>
			</ul>

			<div class="mt85">
				<input type="button" value="登录" class="org_btn disable_btn" id="userLogin">
			</div>			

		</form>

		<p class="bottomLinks"><a href="${basePath }customer/forgetPw">忘记密码</a><a href="${basePath }customer/toRegister">手机号注册</a></p>
	</div>


</div>
<input type="hidden" value="${imgId }" id="imgId">
<input type="hidden" value="${imgUrl }" id="imgUrl">

<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/jquery.md5.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/customer/customer.js${VERSION}003"></script>
</body>
</html>