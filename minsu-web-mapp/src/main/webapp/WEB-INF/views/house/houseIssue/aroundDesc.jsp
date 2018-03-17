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
	<title>周边情况</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
</head>
<body>
	<form id="form" accept="" method="">	
		<div class="main myCenterListNoneA">
			<header class="header">
				<a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a>
				<h1>周边情况</h1>
			</header>
			<div class="area_box">
				<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>	
				<textarea id="arround" name="houseAroundDesc" class="area_txt" maxlength="1000" 
				placeholder="请描述房子附近的景点，地标建筑信息，周边吃喝玩乐生活配套设施，以及有趣好玩的推荐。有场景感和生活气息。中英双语为佳，生活方式类的推荐尤佳。100-1000字。">${location.houseAroundDesc }</textarea>
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
			if($.trim($("#arround").val()).length != 0){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		$("#arround").keyup(function(){
			init();
		});
		
		$("#submitBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return false;
			}
			if($.trim($("#arround").val()).length == 0){
				showShadedowTips("请填写周边情况",1);
				return false;
			}
			if($.trim($("#arround").val()).length < 100 || $.trim($("#arround").val()).length > 1000){
				showShadedowTips("周边情况字数限制为100-1000字",1);
				return false;
			}
			$(this).addClass("disabled_btn").removeClass("org_btn");
	    	updateLocationMsg();
	    	$(this).removeClass("disabled_btn").addClass("org_btn");
		});
		
		function updateLocationMsg(){
	    	$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateLocationMsg?upType=1",
				data:toJson($("#form").serializeArray()),
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(result.code === 0){
						window.location.href = "${basePath }houseIssue/${loginUnauth }/toLocationMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}";
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
