<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="applicable-device" content="mobile">
	<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="yes" name="apple-touch-fullscreen" />
	<title>自如民宿</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}" />
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}"></script>
	
	<link rel="stylesheet" type="text/css" href="${basePath }css/styles.css">
	<style type="text/css">
	.app_btn .btn {
    width: 50%;
    float: left;
    text-align: center;
    display:none;
}
	</style>
</head>
<body class="bgF">

	<header class="header"> 
		<a href="javascript:history.go(-1);" id="goback" class="goback"></a>
		<h1>自如民宿</h1>
	</header>
			
	<div class="main app_box" id="main">
		<!--<div class="box z_scrollBox" style="height: 100%;">
		</div>-->
		<div class="app_btn">
			<a href="javascript:openIos();" class="btn" id="openIos"  ><img src="${basePath }images/app_btn1.jpg"/></a>
			<a href="javascript:openAppstore();" class="btn" id="openAppstore" ><img src="${basePath }images/app_btn2.jpg"/></a>
			<a href="javascript:openAndrApp();" class="btn" id="openAndrApp" ><img src="${basePath }images/app_btn1.jpg"/></a>
			<a href="javascript:openYingYongBao();" class="btn" id="openYingYongBao" ><img src="${basePath }images/app_btn3.jpg"/></a>
		</div>
	</div><!--/main-->
		
		
	<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/common/commonUtils2.js${VERSION}"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}"></script>
	<script src="${staticResourceUrl }/js/common1.js${VERSION}"></script>
	<script src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}"></script>

	<script type="text/javascript">
	var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });

		
		var iWinHeight = $$(window).height();
		var top = $$(window).scrollTop()+iWinHeight;		
		var conHeight = $$(window).height()-$$('.header').height();
		
		
		$$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

	</script>
		
		<script language="javascript"type="text/javascript"> 
	
	  var u = navigator.userAgent;
	   if(!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)){
	       //iOS
	     $$("#openIos").show();
	     $$("#openAppstore").show();
	      }
	   if(u.indexOf('Android') > -1 || u.indexOf('Linux') > -1){
	       //android终端
	        $$("#openAndrApp").show();
	        $$("#openYingYongBao").show();
	   }  
    
	   function openAppstore(){
		   window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer";
	   }
	   function openIos(){
		   window.location.href="${IM_APP_IOS}${params}"; 
	   }
	   function openYingYongBao(){
		   window.location.href="http://a.app.qq.com/o/simple.jsp?pkgname=com.ziroom.ziroomcustomer";
	   }
	   function openAndrApp(){
		   window.location.href="${IM_APP_ANDROID}${params}"; 
	   }
    </script> 


	</body>
</html>