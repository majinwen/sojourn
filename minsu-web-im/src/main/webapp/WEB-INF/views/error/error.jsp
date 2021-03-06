<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
   	<meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">        
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
	<title></title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
	<style type="text/css">
		.img_box{width: 100%;margin: 3rem 0 2rem 0;text-align: center;}
		.img_box img{margin: 0 auto;}
		.error_message{text-align: center;padding: 0 1rem ;font-size: 0.8rem;}
		.error_message a{color: #4A90E2;border: 1px solid #4A90E2;padding: 0.2rem;border-radius: 0.2rem;}
	</style>
</head>
<body>


	<div class="main myCenterListNoneA">
		
		<div class="img_box">
			<img src="${staticResourceUrl }/images/booking_failure.png">
			
		</div>
		<p class="error_message"><a href="${basePath }/orderland/${loginUnauth}/showlist" >返回</a></p>
	</div><!--/main-->
	
<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
</body>
</html>