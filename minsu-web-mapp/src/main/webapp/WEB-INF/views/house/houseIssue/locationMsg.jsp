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
	<title>位置信息</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
</head>
<body>
	<form id="form" accept="" method="">
		<div class="main myCenterListNoneA">
			
			<header class="header">
				<a href="${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}" class="goback"></a>
				<h1>位置信息</h1>
			</header>
			<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>	
			
				<ul class="myCenterList">
				<li class="clearfix bor_b no_icon">				
					楼号
					<input type="text" placeholder="用户支付后可见" id="buildingNum" name="buildingNum" maxlength="10" value="${location.buildingNum }">
				</li>
				<li class="clearfix bor_b no_icon">				
					单元
					<input type="text" placeholder="用户支付后可见" id="unitNum" maxlength="5" name="unitNum" value="${location.unitNum }">
				</li>
				<li class="clearfix bor_b no_icon">				
					楼层
					<input type="text" placeholder="用户支付后可见" id="floorNum" maxlength="5" name="floorNum" value="${location.floorNum }">
				</li>
				<li class="clearfix bor_b no_icon">				
					门牌号
					<input type="text" placeholder="用户支付后可见" id="houseNum" name="houseNum" maxlength="5" value="${location.houseNum }">
				</li>
				<li class="clearfix ">				
					<a href="javascript:;" id="houseAroundDesc">
						周边情况
						<c:if test="${empty isComplete || isComplete == 0}">
							<span class="span_r">未完成</span>
						</c:if>
						<c:if test="${isComplete == 1}">
							<span class="span_r span_r_done">已完成</span>
						</c:if>
						<span class="icon_r icon" ></span>
					</a>
				</li>
			</ul>
			
		</div><!--/main-->
		<div class="boxP075 mt85 mb85">
			<input type="button" value="确认" class="disabled_btn btn_radius" id="submitBtn">
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
			if(valid()){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		 function valid(){
			 return $.trim($("#buildingNum").val()).length != 0 || $.trim($("#unitNum").val()).length != 0 
						|| $.trim($("#floorNum").val()).length != 0 || $.trim($("#houseNum").val()).length != 0; 
		}
		
		$("input").keyup(function(){
			init();
			//keepFloat(this,0);
		})
	
		$("#houseAroundDesc").click(function(){
	    	updateLocationMsg(false);
			window.location.href='${basePath }houseIssue/${loginUnauth }/toAroundDesc?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}';
		});
		
		$("#submitBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return false;
			}
	    	$(this).addClass("disabled_btn").removeClass("org_btn");
	    	updateLocationMsg(true);
	    	$(this).removeClass("disabled_btn").addClass("org_btn");
		});
		
		function updateLocationMsg(isShow){
		    	$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateLocationMsg",
				data:toJson($("#form").serializeArray()),
				dataType:"json",
				type:"post",
				async: true,
				success:function(result) {
					if(!isShow){
						return;
					}
					if(result.code === 0){
						window.location.href='${basePath }houseIssue/${loginUnauth }/showOptionalInfo?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}';
					}else{
						showShadedowTips("操作失败",1);
					}
				},
				error:function(result){
					if(!isShow){
						return;
					}
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
