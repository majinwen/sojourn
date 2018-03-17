<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh">
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
	<title>手机号注册</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
	
</head>
<body>

<div class="loginBox">


		
		<div id="regStep_1">
			<header class="header">
				<h1>手机号注册</h1>
			</header>
			<div class="content ">
				<form class="checkFormChild" onsubmit="return false;">
					<ul class="loginList pt75">
						<li class="bor_b">
							<span class="l_icon icon_regtel"></span>
							<input type="tel" placeholder="请输入手机号" maxlength="11" id="reg_user" onkeyup="inputVal(this,'#forRegUser')" onfocus="inputVal(this,'#forRegUser')" >
							<span class="l_icon icon_close" id="forRegUser"  onclick="clearInp(this,'#reg_user')"></span>
						</li>
						
					</ul>

					<div class="mt85">
						<input type="button" value="下一步" class="org_btn disabled_btn" id="nextStep_1">
					</div>			

				</form>

				<p class="bottomLinks">已有账号?<a href="/customer/login" class="org">登录</a></p>
			</div>
		</div><!--/regStep_1-->
		<div id="regStep_2">
			<header class="header">
				<h1>填写验证码</h1>
			</header>
			<div class="content ">
				<form class="checkFormChild" onsubmit="return false;">
					<h2>验证码已发送到手机</h2>
					<h3 id="yzTelVal"></h3>
					<div class="inputs">
						<input type="text" class="val" id="vcode" maxlength="6">			
					</div>
					<p class="org" id="countDownTxt" onclick="countDown()">点击重新获取验证码</p>
					<p class="gray">若无法收到验证码，请拨打客服电话：400-100-1111</p>
					<div class="mt85">
						<input type="button" value="下一步" class="org_btn disabled_btn" id="nextStep_2">
					</div>	
				</form>
			</div>
		</div><!--/发送验证码regStep_2-->


		<div id="regStep_3">
			<header class="header">
				<h1>设置密码</h1>
			</header>
			<div class="content ">
				<form class="checkFormChild" onsubmit="return false;">
					<ul class="loginList pt75">
						<li class="bor_b">
							<span class="l_icon icon_pas"></span>
							<input type="password" placeholder="设置登录密码，6-16位" id="reg_pas" maxlength="16" onkeyup="inputVal(this,'#forRegPas')" onfocus="inputVal(this,'#forRegPas')" >
							<span class="l_icon icon_close" id="forRegPas"  onclick="clearInp(this,'#reg_pas')"></span>
						</li>
						
					</ul>

					<div class="mt85">
						<input type="button" value="完成" class="org_btn disabled_btn" id="regSubmit">
					</div>			

				</form>
			</div>
		</div><!--/设置密码regStep_3-->
		
	</div>
	


</div>
<input type="hidden" value="${imgId }" id="imgId">
<input type="hidden" value="${imgUrl }" id="imgUrl">
<input type="hidden" value="" id="vid">


<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/jquery.md5.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/customer/customer.js${VERSION}004"></script>
<script src="${staticResourceUrl }/js/customer/reg.js${VERSION}004"></script>

</body>
</html>
