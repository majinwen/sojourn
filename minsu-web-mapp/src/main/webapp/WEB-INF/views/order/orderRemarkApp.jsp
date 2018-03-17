<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head lang="zh">
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
	<title>订单备注</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css${VERSION}001">
</head>
<body>

	<header class="header">
		<c:if test="${sourceOrg == 1}">
			<a href="javascript:;" onclick="window.WebViewFunc.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 2}">
			<a href="javascript:;" onclick="window.popToParent();" class="goback"></a>
		</c:if>
		<c:if test="${sourceOrg == 0}">
			<a href="javascript:;" class="goback" onclick="history.go(-1);"></a>
		</c:if>
		<h1>订单备注</h1>
	</header>
	
	<div class="main">
		<form class="checkFormChild"  id="form" action="" method="post">
			<input type="hidden" id="orderSn" name="orderSn" value="${orderSn}">
			<div class="tousu">
				<textarea placeholder="请填写您的订单备注信息，50字以内。" id="remark" name="remark" onkeyup="this.value = this.value.substring(0, 50)"></textarea>
			</div>	
			<div class="boxP075">
				<input type="button" value="确认" class="block_btn org_btn disabled_btn" id="saveBtn" />
			</div>
		</form>
	</div>

<script src="${staticResourceUrl }/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common.js${VERSION}001"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script type="text/javascript">
	$('#saveBtn').click(function(){
		$.post( SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/saveOrderRemark",{"orderSn":$("#orderSn").val(),"remark":$("#remark").val()},function(data){
			if(data.code == 0){
				var sourceOrg = '${sourceOrg}';
				if (sourceOrg == 1){
					window.WebViewFunc.popToParent();
				}else if (sourceOrg == 2){
					window.popToParent();
				}else{
					window.location.href = SERVER_CONTEXT+"orderland/"+LOGIN_UNAUTH+"/showDetail?orderSn="+$("#orderSn").val()+"&myRemark";
				}
			}else{
				showShadedowTips(data.msg, 1);
			}
		},'json');
	});
</script>
</body>
</html>