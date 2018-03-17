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
	<title>房屋守则</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
</head>
<body>
	<form id="form" accept="" method="">	
		<div class="main myCenterListNoneA">
			<header class="header">
				<!-- <a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a> -->
				<a href="${basePath }houseIssue/${loginUnauth }/toSelectHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}" id="goback" class="goback"></a>
				<h1>其它守则</h1>
			</header>
			<div class="area_box">
				<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>	
				<textarea id="houseRules" name="houseRules" class="area_txt" maxlength="1000" placeholder="请描述一下您期待的房客的行为举止，50-1000字。&#10;为保证您的房源呈现效果，请注意错别字、过多空行等格式问题。">${houseRules }</textarea>
			</div>
		</div><!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" id="submitBtn" value="确认" class="disabled_btn btn_radius"/>
		</div>
	</form>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	<script type="text/javascript">
		$(function(){
			init();
		})
		
		function init(){
			if($.trim($("#houseRules").val()).length != 0){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		$("#houseRules").keyup(function(){
			init();
		});
		
		$("#submitBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return false;
			}
			if($.trim($("#houseRules").val()).length == 0){
				showShadedowTips("请填写房屋守则",1);
				return false;
			}
			if($.trim($("#houseRules").val()).length < 50 || $.trim($("#houseRules").val()).length > 1000){
				showShadedowTips("房屋守则字数限制为50-1000字",1);
				return false;
			}
			$(this).addClass("disabled_btn").removeClass("org_btn");
			updateCheckInMsg();
			$(this).removeClass("disabled_btn").addClass("org_btn");
		});
		
		function updateCheckInMsg(){
	    	$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateLocationMsg",
				data:toJson($("#form").serializeArray()),
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(result.code === 0){
						window.location.href = "${basePath }houseIssue/${loginUnauth }/toSelectHouseRules?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
					}else{
						showShadedowTips("操作失败",1);
					}
				},
				error:function(result){
					showShadedowTips("未知错误",1);
				}
			});
		}
		
		function toJson(array){
			var json = {};
			$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($.trim(value).length != 0){
					json[key] = value;
				}
			});
			return json;
		}	
	</script>
</body>
</html>
