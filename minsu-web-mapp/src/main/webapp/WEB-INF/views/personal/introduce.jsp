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
	<title>个人介绍</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	<style type="text/css">
		.tousu{position: relative;background: #fff;border-radius: 10px;padding-bottom: 1.8rem;}
		.tousu_num{position: absolute;bottom: 0.6rem;right: 1.35rem;font-size: 0.7rem;color: #666;}
	</style>
</head>
<body>
	<header class="header">
		<c:if test="${type == 1}">
			<a href="${basePath }personal/${loginUnauth }/showBasicDetail"  class="goback"></a>
		</c:if>
		<c:if test="${type != 1}">
			<a href="${basePath }auth/${loginUnauth }/headPic"  class="goback"></a>
		</c:if>
		<h1>个人介绍</h1>
	</header>
	<div class="main">
		<form class="checkFormChild"  id="form" action="${basePath }personal/${loginUnauth }/saveIntroduce" method="post">
			<div class="tousu">
				<textarea  class="tousu_area" placeholder="中英双语皆可，有个人特色，语言生动有趣，能够体现出如热情好客，友善等特征。100-500字。" 
				name="introduce" min-length="100" max-length="500">${customerIntroduce}</textarea>
				<span class="tousu_num"><i class="num">0</i>/500</span>
			</div>	
			<div class="boxP075">
				<input type="button" value="确认" class="block_btn org_btn disabled_btn" id="saveBtn" />
			</div>
		</form>
	</div><!--/main-->
	
	<input type="hidden" id="jumpType" value="${type }"/>
<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/jquery.form.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script type="text/javascript">
(function ($) {
	$("#saveBtn").click(function(){
		
		var txt = $.trim($("textarea").val());
		if(txt.length>500){
			showShadedowTips('最大字数不超过500字',1);
			return;
		}
		if(txt.length<100){
			showShadedowTips('字数不能少于100字',1);
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
						var jumpType = $("#jumpType").val();
						if(jumpType == 1){
							//返回基本信息
							window.location.href = "${basePath }personal/${loginUnauth }/showBasicDetail";
						}else if(jumpType == 2){
							//返回基本信息
							window.location.href = "${basePath }auth/${loginUnauth }/headPic";
						}
					}else{
						showShadedowTips(data.msg,1);
						$("#saveBtn").removeAttr("disabled");
					}
				}
			}
		$("#saveBtn").attr("disabled","disabled");  
		$('#form').ajaxSubmit(options);
<<<<<<< HEAD
	});
=======
	})
>>>>>>> refs/remotes/origin/test
	// 个人介绍字数
	$(".tousu_area").each(function(){
        	if($(this).val() != ''){
        		$(this).parents(".tousu").find(".num").html($(this).val().length);
        	}else{
        		$(this).parents(".tousu").find(".num").html(0);
        	}
        	$(this).on('input pasteafter',function(){
        		if($(this).val().length >= 500){
        			$(this).val($(this).val().slice(0,500));
        			$(this).parents(".tousu").find(".num").html(500);
        		}else{
        			$(this).parents(".tousu").find(".num").html($(this).val().length);
        		}
        	})
        })
}(jQuery))
</script>
</body>
</html>
