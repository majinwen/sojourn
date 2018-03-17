<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
  <meta name="format-detection" content="telephone=no, email=no">
  <meta name="HandheldFriendly" content="true">
  <meta name="MobileOptimized" content="320">
  <meta http-equiv="Cache-Control" content="no-siteapp">
  <meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-title" content="">
	<meta name="apple-mobile-web-app-status-bar-style" content="white">
  <meta name="keywords" content="">
  <meta name="description" content="">
	<title></title>
	<style>	
		body{
			height:900px;
		}
		.gotoshare{
			width:100%;
			height:100%;
			background-color: rgba(0,0,0,.6);
			position: absolute;
			top:0;
			left:0;
			display: none;
			text-align: right;
		}
		.gotoshare img{
			width:60%!important;
			margin-right: 8px;
			margin-top: 10px;
		}
	</style>
</head>
<body>
	<div onclick="showTC()">
		点击我
	</div>
	<div class="gotoshare" onclick="hideTC();"><img src="${staticResourceUrl}/share/img/gotoApp.png${VERSION}001" width="90%" alt=""></div>
</body>
<script type="text/javascript" src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001"></script>
<script>
	function stopTouchMove( e ) {
	  e.preventDefault();
	}
	function showTC(){
    $('.gotoshare').show();
    $('body').bind('touchmove',stopTouchMove);
  }
  function hideTC(){
    $('.gotoshare').hide();
    $('body').unbind('touchmove',stopTouchMove);
  }
</script>
</html>