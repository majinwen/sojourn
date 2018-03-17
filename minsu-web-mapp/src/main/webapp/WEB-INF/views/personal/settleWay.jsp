<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
	String path = request.getContextPath(); 
	request.setAttribute("path", path);
%>
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
	<title>结算方式</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body>
	<header class="header">
		<a href="javascript:;" onclick="history.back();" class="goback"></a>
		<h1>结算方式</h1>
	</header>
	<div class="main">
		<div class="bgF boxP075 mt75">
			<ul class="jsType">
				<c:if test="${clearingCode == 1}">
					<li class="bor_b active">
				</c:if>
				<c:if test="${clearingCode == 2}">
					<li class="bor_b">
				</c:if>
					<span id="byOrder" class="radio" data-val="1" data-name="jsType"></span>
					<h2>按订单结算 </h2>
					<p class="gray">小于7天的订单每单结算1次</p>
					<p class="gray">大于7天的订单每7天结算1次</p>        
         		</li>
         		<c:if test="${clearingCode == 1}">
					<li class="">
				</c:if>
				<c:if test="${clearingCode == 2}">
					<li class=" active">
				</c:if>
         			<span id="byDay" class="radio" data-val="2" data-name="jsType"></span>
         			<h2>按天结算</h2>
         			<p class="gray">每天结算1次</p>
         		</li>
			</ul>
			<input type="hidden" id="jsTypeVal" value="${clearingCode }">
		</div>
		<div class="boxP075 mt85">
			<input type="button" value="确认" class="block_btn org_btn" id="saveBtn" />
		</div>
	</div><!--/main-->

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	<script type="text/javascript">
		var oJsType=$('#jsTypeVal');
		var _val= oJsType.val();
		
		$(function(){
			$('.jsType li').click(function(){
			    $(this).addClass('active').siblings().removeClass('active');
			    _val =  $(this).index()+1;
			});

			
		})
		
		$("#saveBtn").click(function(){
	    	$(this).attr("disabled","disabled");
	    	$.ajax({
				url:"${basePathHttps }personal/${loginUnauth }/savePayInfo",
				data:{
					clearingCode : _val
				},
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(result.code === 0){
						showShadedowTips("操作成功",1);
						setTimeout(function(){
							window.location.href='${basePathHttps }personal/${loginUnauth }/toMyBankAcount';
						},1500);
					}else{
						showShadedowTips("操作失败", 1);
					}
				},
				error:function(result){
					showShadedowTips("未知错误", 1);
				}
			});
		})
	</script>
</body>
</html>