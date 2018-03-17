<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<title>申请临时密码</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/mui.picker.min.css">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	
</head>
<body>
		<header class="header">
			<a href="${basePathHttps }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}" class="goback"></a>	
			<h1>申请临时密码</h1>
		</header>
		<form accept="" method="post" class="checkFormChild" id="form">	
			<input type="hidden" name="houseBaseFid" value="${houseBaseFid }">
			<div class="main myCenterListNoneA myCenterListGeren guanlimimaList3">
			<ul class="myCenterList">			
				<li class="clearfix bor_b ">
					起始时间
					<input id="startTime" data-options="{}" class="mui-btn" name="start_time" placeholder="选择起始时间"/>
 
		
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix bor_b">
					截止时间
							
					<input id="endTime" data-options="{}" class="mui-btn" name="end_time" placeholder="选择截止时间"/>
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix">
					接收密码手机号
					<input type="tel" placeholder="请填写接收密码手机号" id="tel" name="phoneNum" value="">
				</li>
			</ul>
			</div>
			<div class="boxP075 mt85 mb85">
				<input type="button" value="确定" class="org_btn disabled_btn" id="saveBtn">
			</div>
	</form>
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	
	<script src="${staticResourceUrl}/js/mui.min.js" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/mui/mui.date.min.js" type="text/javascript"></script>
	
	<script type="text/javascript">
	(function($){
		$.init();
		var btns = $('.mui-btn');
		btns.each(function(i, btn) {
			btn.addEventListener('tap', function() {
				var optionsJson = this.getAttribute('data-options') || '{}';
				var options = JSON.parse(optionsJson);
				var id = this.getAttribute('id');
				
				var picker = new $.DtPicker(options);
				picker.show(function(rs) {
					console.log(rs);
					//result.innerText = '选择结果: ' + rs.text;
					document.getElementById(id).value=rs.text;
					picker.dispose();
				});
			}, false);
		});
		
	}(mui));
	
	(function($){
		$("#tel").keyup(function(){
			keepFloat(this,0);
		})
		$("#saveBtn").click(function(){
			if($(this).hasClass("disabled_btn")){
				return;
			}
			if(!validate()){
				return;
			}
			$(this).addClass("disabled_btn");
			$.post("${basePathHttps}smartLock/${loginUnauth}/sendTempPass",$("#form").serialize(),function(response){
				if(response.code == 0){
					layer.open({
				    	content: '<div class="box" >您申请临时密码的请求已发送，我们将以短信形式通知您发送结果，请关注您的短信。</div>',
				    	btn:['确定'],
				    	yes: function(){
				        	layer.closeAll();
				        	window.location.href="${basePathHttps }smartLock/${loginUnauth }/showPanel?houseBaseFid=${houseBaseFid}&houseRoomFid=${houseRoomFid}&rentWay=${rentWay}";
				        }
					});
				}else{
					showShadedowTips(reponse.msg,1);
				}
			},'json');
			
		});		
	}(jQuery));
	
	function validate(){
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
    	var telVal = $("#tel").val();
    	var flag = true;
    	

    	var n = new Date();
        var curDate = n.getFullYear()+'-'+(n.getMonth() + 1)+'-'+n.getDate()+' '+n.getHours()+':'+n.getMinutes();
    	var oDate0 = Date.parse(new Date(curDate));
    	var oDate1 = Date.parse(new Date(startTime));
		var oDate2 = Date.parse(new Date(endTime));

    	if(oDate1<oDate0 || oDate2<oDate0){
    		showShadedowTips("选择时间不能小于当前时间",1);
    		flag = false;
    		return;
    	}

    	if(oDate1>=oDate2){
    		showShadedowTips("截止时间不能小于起始时间",1);
    		flag = false;
    		return;
    	}

    	if(!checkMobile(telVal)){
    		showShadedowTips("请输入正确的手机号",1);
    		flag = false;
    		return;
    	}
    	return flag;
	}
	
	</script>
</body>
</html>
