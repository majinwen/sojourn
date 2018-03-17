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
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
    <meta name="format-detection" content="telephone=no">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
	<title>个人资料</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
</head>
<body>
	<header class="header">
		<a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>投诉建议</h1>
	</header>
	<div class="main">
		<form class="checkFormChild"  id="form" action="${basePath }personal/${loginUnauth }/saveComplain" method="post">
			<div class="tousu">
				<textarea placeholder="请告诉我们您对自如民宿的投诉和建议。" name="complain" max-length="100"></textarea>
			</div>	
			<div class="boxP075">
				<input type="button" value="确认" class="block_btn org_btn disabled_btn" id="saveBtn" />
			</div>
		</form>
	</div><!--/main-->
<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/jquery.form.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script type="text/javascript">
(function ($) {
	$("#saveBtn").click(function(){
		
		var txt = $.trim($("textarea").val());
		if(txt.length>100){
			showShadedowTips('最大字数不超过100字',1);
			return;
		}
		//过滤特殊字符
		txt = stripscript(txt);
		if(txt == ""){
			return;
		}
		
		 var options = {
				dataType:"json",
				success:function(data){
					if(data.code == 0){
						window.location.href = "${basePath }personal/${loginUnauth }/success?type=2";
					}else{
						showShadedowTips(data.msg,1);
					}
				}
			}
		$('#form').ajaxSubmit(options);
	});
}(jQuery))
</script>
</body>
</html>