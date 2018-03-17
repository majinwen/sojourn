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
		<a href="${basePath }personal/43e881/initPersonalCenter" class="goback"></a>
		<h1>个人资料</h1>
	</header>
	<form accept="" method="post" id="form">	
		<div class="main myCenterListNoneA myCenterListGeren" id="main">
		<div id="mainScroll">
			
		
			<ul class="myCenterList ">
				<li class="clearfix">
					昵称
					<input type="text" placeholder="请输入您的昵称" name="nickName" value="${customerBase.nickName }">			
				</li>
			</ul>
			
			<ul class="myCenterList">
				<li class="clearfix bor_b ">
					性别
					<input type="text" placeholder="请选择您的性别" id="sex" value="${sexName }">
					<select onchange="setValToInput(this,'sex')" id="customerSex" name="customerSex">
						<option value="" <c:if test="${customerBase.customerSex == 0 }">selected="selected"</c:if> >请选择性别</option>
						<option value="2" <c:if test="${customerBase.customerSex == 2 }">selected="selected"</c:if>>男</option>
						<option value="1" <c:if test="${customerBase.customerSex == 1 }">selected="selected"</c:if>>女</option>
					</select>
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix">
					出生日期
					<input type="text" placeholder="请选择您的出生日期" id="date" name="customerBirthday" value="<fmt:formatDate value='${customerBase.customerBirthday }' pattern='yyyy-MM-dd' />">
					<input type="date" onchange="setValToInputDate(this,'date')">
					<span class="icon_r icon"></span>
				</li>
			</ul>
			<ul class="myCenterList">
				<li class="clearfix bor_b">
					<a href="${basePath }customer/${loginUnauth}/updateMobile">
						联系方式
						<input type="text" placeholder="请验证您的联系方式" value="${customerBase.customerMobile }"><span class="icon_r icon"></span>
					</a>
					
				</li>
				<li class="clearfix">
					邮箱地址
					<input type="text" placeholder="请输入邮箱地址" name="customerEmail" value="${customerBase.customerEmail }">
				</li>
			</ul>
			<ul class="myCenterList">
				<li class="clearfix bor_b">
					居住地址
					<input type="text" placeholder="请输入您的居住地址" name="resideAddr" value="${customerBase.resideAddr }">
				</li>
				<li class="clearfix bor_b">
					教育背景
					<input type="text" placeholder="请选择您的教育背景" id="education" value="${customerEduName}">
					<select onchange="setValToInput(this,'education')" id="customerEdu"  name="customerEdu">
					<c:if test="customerBase.customerEdu == 0">selected="selected"</c:if>
						<option value="0" <c:if test="${customerBase.customerEdu == 0}">selected="selected"</c:if> >其他</option>
						<option value="1" <c:if test="${customerBase.customerEdu == 1}">selected="selected"</c:if> >小学</option>
						<option value="2" <c:if test="${customerBase.customerEdu == 2}">selected="selected"</c:if> >初中</option>
						<option value="3" <c:if test="${customerBase.customerEdu == 3}">selected="selected"</c:if> >高中</option>
						<option value="4" <c:if test="${customerBase.customerEdu == 4}">selected="selected"</c:if> >中技</option>
						<option value="5" <c:if test="${customerBase.customerEdu == 5}">selected="selected"</c:if> >中专</option>
						<option value="6" <c:if test="${customerBase.customerEdu == 6}">selected="selected"</c:if> >大专</option>
						<option value="7" <c:if test="${customerBase.customerEdu == 7}">selected="selected"</c:if> >本科</option>
						<option value="8" <c:if test="${customerBase.customerEdu == 8}">selected="selected"</c:if> >MBA</option>
						<option value="9" <c:if test="${customerBase.customerEdu == 9}">selected="selected"</c:if> >硕士</option>
						<option value="10" <c:if test="${customerBase.customerEdu == 10}">selected="selected"</c:if>>博士</option>
					</select>
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix bor_b">
					职业
					<c:if test="${customerBase.customerJob == ''}">
						<input type="text" placeholder="请输入您的职业" value="" name="customerJob">
					</c:if>
					<c:if test="${customerBase.customerJob != ''}">
						<input type="text" placeholder="请输入您的职业" value="${customerBase.customerJob}" name="customerJob">
					</c:if>
				</li>
				
				<li class="clearfix">
					<a href="javascript:;" id="toIntorduce" >
						个人介绍
						<c:if test="${hasIntroduce == 1 }">
							<span class="gray gray6">已完成</span>
						</c:if>
						<c:if test="${empty hasIntroduce}">
							<span class="gray">请输入您的个人介绍</span>
						</c:if>
						<span class="icon_r icon"></span>
					</a>
					
				</li>
			</ul>
			</div>
		</div><!--/main-->
		
		<div class="box box_bottom clearfix">
				<input type="button" id="saveBtn" value="提交" class="org_btn">
			</div>
	</form>

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<%-- <script src="${staticResourceUrl}/js/iscroll.js${VERSION}006"></script> --%>
	<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
	
	<script type="text/javascript">
		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });
		var conHeight = $(window).height()-$('.header').height()-$('.box_bottom').height();
		
		$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$('#mainScroll').css({'position':'absolute','width':'100%'});
		
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);

		$("#toIntorduce").click(function(){
			saveBaiscMsg(1);
		});
		
		$("#saveBtn").click(function(){
			saveBaiscMsg(2);
		});
		
		function saveBaiscMsg(type){
			
			if(!validate()){
				return;
			}
	    	$.ajax({
				url:"${basePath }/personal/${loginUnauth }/updateCustomerInfo",
				data:$('#form').serialize(),
				dataType:"json",
				type:"post",
				async: false,
				success:function(result) {
					if(result.code === 0){
						if(type == 1){
							window.location.href = "${basePath}personal/${loginUnauth}/introduce?type=1";
						}else{
							showShadedowTips("操作成功",1);
							setTimeout(function(){
								window.location.href='${basePath }personal/${loginUnauth }/initPersonalCenter';
							},1000);
						}
					}else{
						showShadedowTips(result.msg,1);
					}
				},
				error:function(result){
					showShadedowTips("未知错误",1);
				}
			});
	    	$(this).removeAttr("disabled");
		}
		
		//校验填写信息
		function validate(){
	    	var nickName = $("input[name='nickName']").val();
	    	var flag = true;
	    	if(nickName.length >20){
	    		showShadedowTips("昵称不能超过20字",1);
	    		flag = false;
	    	}
	    	
	    	var email = $("input[name='customerEmail']").val();
	    	if(email != ""){
		    	if(!checkEmail(email)){
		    		showShadedowTips("邮件格式不正确",1);
		    		flag = false;
		    	}
	    	}
	    	var resideAddr = $("input[name='resideAddr']").val();
	    	if(resideAddr.length > 150){
	    		showShadedowTips("地址不能超过150字",1);
	    		flag = false;
	    	}
	    	var customerJob = $("input[name='customerJob']").val();
	    	if(customerJob.length > 40){
	    		showShadedowTips("职业不能超过40字",1);
	    		flag = false;
	    	}
	    	
	    	return flag;
		}
		
	</script>
</body>
</html>
