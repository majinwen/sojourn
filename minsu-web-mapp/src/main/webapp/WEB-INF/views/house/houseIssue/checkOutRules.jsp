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
	<title>退订政策</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles_new.css${VERSION}001">
</head>
<body>
	<div class="bodys">
		<div class="main myCenterListNoneA">
			<header class="header">
				<a href="javascript:;" id="goback" class="goback" onclick="history.go(-1)"></a>
				<h1>退订政策</h1>
			</header>
			<input name="houseBaseFid" value="${houseBaseFid }" type="hidden"/>	
			<input id="checkOutRulesCode" name="checkOutRulesCode" value="${checkOutRulesCode }" type="hidden"/>	
			<c:forEach items="${checkOutList }" var="obj" varStatus="no">
			 
		 	<c:if test="${obj.checkOutRulesCode  =='TradeRulesEnum005004' && !empty longTermLimit && longTermLimit<500}">
				  <ul class="myCenterList" >
					<li class="clearfix pl30 bor_b">
						<span class="icon_l" id="${obj.checkOutRulesCode }"></span>
						<span>${obj.checkOutRulesName }</span>
					</li>
					
					<li class="clearfix txt_li">
						<c:if test="${not empty obj.checkOutRulesVal4 }">
						  <p>${obj.checkOutRulesVal4 }</p>
						</c:if>
					</li>
				</ul>
				</c:if>
			  
				
				<c:if test="${obj.checkOutRulesCode  !='TradeRulesEnum005004' && obj.checkOutRulesCode  !='TradeRulesEnum005005'}">
				  <ul class="myCenterList">
					<li class="clearfix pl30 bor_b">
						<c:if test="${obj.checkOutRulesCode == checkOutRulesCode }">
							<span class="icon_l icon_radio active" id="${obj.checkOutRulesCode }"></span>
						</c:if>
						<c:if test="${obj.checkOutRulesCode != checkOutRulesCode }">
							<span class="icon_l icon_radio" id="${obj.checkOutRulesCode }"></span>
						</c:if> 
						<span>${obj.checkOutRulesName }</span>
					</li>
					
					<li class="clearfix txt_li"> 
				   	   	<p>${obj.checkOutRulesVal1 }</p>
						<p>${obj.checkOutRulesVal2 }</p>
						<p>${obj.checkOutRulesVal3 }</p>
						<c:if test="${not empty obj.checkOutRulesVal4 }">
						  <p>${obj.checkOutRulesVal4 }</p>
						</c:if> 					 
					</li>
				</ul>
				</c:if>
				<c:if test="${obj.checkOutRulesCode =='TradeRulesEnum005005' && !empty obj.explain}">
					<ul class="myCenterList">
						<li class="clearfix pl30 bor_b">
							<span class="icon_l" id="${obj.checkOutRulesCode }"></span>
							<span>${obj.checkOutRulesName }</span> 
						</li>
						
						<li class="clearfix txt_li">
							<p>${obj.explain }</p> 
						</li>
					</ul>
				</c:if>
				
			</c:forEach>
		</div><!--/main-->
	</div>
	<div class="box box_bottom">
		<a href="javascript:;">
			<input type="button" id="submitBtn" value="确认" class="org_btn ">
		</a>
	</div>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	<script type="text/javascript">
		$(function(){
			init();
		})
		
		function init(){
			if($.trim($("#checkOutRulesCode").val()).length != 0){
				$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
			}else{
				$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			}
		}
		
		$(".icon_radio").each(function(){
			$(this).click(function(){
				$(".icon_radio").removeClass("active");
				$(this).addClass("active");
				$("#checkOutRulesCode").val($(this).attr("id"));
				init();
			})
			
		})
		
		$(".myCenterList li.bor_b").click(function(){
			var id   = $(this).find(".icon_l").attr("id");
			if(id != 'TradeRulesEnum005004'){
				$(".icon_radio").removeClass("active");
				$(this).find(".icon_radio").addClass("active");
				$("#checkOutRulesCode").val(id);
				init();
			}
		})
		
		$("#submitBtn").click(function(){
			if($("#submitBtn").hasClass("disabled_btn")){
				return false;
			}
			$("#submitBtn").addClass("disabled_btn").removeClass("org_btn");
			updateExchangeMsg();
			$("#submitBtn").removeClass("disabled_btn").addClass("org_btn");
		})
		
		function updateExchangeMsg(){
	    	$.ajax({
				url:"${basePath }/houseIssue/${loginUnauth }/updateExchangeMsg",
				data:toJson($("input[type='hidden']").serializeArray()),
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(result.code === 0){
						window.location.href = "${basePath }houseIssue/${loginUnauth }/toExchangeMsg?houseBaseFid=${houseBaseFid }&rentWay=${rentWay }&houseRoomFid=${houseRoomFid }&flag=${flag}&houseFive=${houseFive}";
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
