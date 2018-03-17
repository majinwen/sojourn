<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<h1><c:if test="${authType == 1}">认证信息</c:if><c:if test="${authType == 2}">身份信息</c:if></h1>
	</header>
	<form id="authForm" action="" class="checkFormChild" onsubmit="return false;">
	    <div class="main myCenterListNoneA formPicHeight formPicHeight2">
			<ul class="myCenterList myCenterListGeren">
				<li class="clearfix">				
					真实姓名<input type="text" name="realName" placeholder="请输入真实姓名" value="${customerDetail.realName }">
				</li>
			</ul>
			<ul class="myCenterList myCenterListGeren">
				<li class="clearfix bor_b ">				
					证件类型<input type="text" name="idTypeName" placeholder="请选择您的证件类型" id="IDType"  value="${idType == 0 ? '请选择您的证件类型' :  idTypName}">
					<select name="idType" onchange="setValToInput(this,'IDType')">
						<option value="" <c:if test="${idType == null || idType == 0}">selected="selected"</c:if> >请选择您的证件类型</option>
						<option value="1" <c:if test="${idType == 1}">selected="selected"</c:if> >身份证</option>
						<option value="2" <c:if test="${idType == 2}">selected="selected"</c:if> >护照</option>
						<option value="6" <c:if test="${idType == 6}">selected="selected"</c:if> >台湾居民来往通行证</option>
						<option value="13" <c:if test="${idType == 13}">selected="selected"</c:if> >港澳居民来往通行证</option>
					</select>
					<span class="icon_r icon"></span>
				</li>
				<li class="clearfix bor_b ">				
					证件号码<input type="text" name="idNo" placeholder="请输入您的证件号码" value="${customerDetail.idNo }">				
				</li>
				<li class="clearfix">
					证件照<span class="gray">${customerDetail.isIdentityAuth == 1 ? "已上传" : "请上传证件照"}</span>	
				</li>
			</ul>
			
			<!-- 重新选择后需要删除的照片   回显照片不删除-->
			<input type="hidden" name="upload_uuid_0" id="upload_uuid_0" >
			<input type="hidden" name="upload_uuid_1" id="upload_uuid_1" >
			<input type="hidden" name="upload_uuid_2" id="upload_uuid_2" >
			
			<!-- 需要提交的照片 -->
			<input class="ss"  type="hidden"  name="upload_data_0" id="upload_data_0" value="${upload_data_0 }">
			<input class="ss" type="hidden"  name="upload_data_1" id="upload_data_1" value="${upload_data_1 }">
			<input class="ss" type="hidden"  name="upload_data_2" id="upload_data_2" value="${upload_data_2 }">
			
			<!-- 照片回显的数据   如果不修改则不删除-->
			<input class="ss"  type="hidden"  name="old_upload_data_0" id="upload_data_0" value="${upload_data_0 }">
			<input class="ss" type="hidden"  name="old_upload_data_1" id="upload_data_1" value="${upload_data_1 }">
			<input class="ss" type="hidden"  name="old_upload_data_2" id="upload_data_2" value="${upload_data_2 }">
			
		</div><!--/main-->
		<div class="boxP075 mt85 mb85">
			<c:if test="${backshow == '1' }">
				<input type="button" value="提交" class="org_btn" id="saveBtn" />
			</c:if>
			<c:if test="${backshow != '1' }">
				<input type="button" value="提交" class="org_btn disabled_btn disabled_btn2" id="saveBtn" />
				
			</c:if>
		</div>
	</form>
	
	<ul class="myPicList clearfix myPicListPos">
			<li>
				<form enctype="multipart/form-data" method="post" id="form0" name="upform" dotype="ajax" 
						callback="form0" target="upload_target_0" action="${basePath }personal/${loginUnauth }/uploadCustomerPic">
						<div onclick="upoladPicAndroid(0)" class="img" id="img_0" <c:if test="${picZJZM != null}">style="background-image: url('${picZJZM}');"</c:if> >
							<input type="file" name="file" onchange="changePic(this,'0');">
							<span class="pic_loading" id="loading_0">正在上传</span>
						</div>			
				</form>		
			</li>
			<li>
				<form enctype="multipart/form-data" method="post" id="form1" name="upform" dotype="ajax" 
						callback="form1" target="upload_target_1" action="${basePath }personal/${loginUnauth }/uploadCustomerPic">
					<div onclick="upoladPicAndroid(1)" class="img" id="img_1" <c:if test="${picZJFM != null}">style="background-image: url('${picZJFM}');"</c:if> >
						<input type="file" name="file" onchange="changePic(this,'1');">
						<span class="pic_loading" id="loading_1">正在上传</span>
					</div>
				</form>		
			</li>
			<li>
				<form enctype="multipart/form-data" method="post" id="form2" name="upform" dotype="ajax"
					  callback="form2" target="upload_target_2" action="${basePath }personal/${loginUnauth }/uploadCustomerPic">
					<div onclick="upoladPicAndroid(2)" class="img" id="img_2" <c:if test="${picZJSC != null}">style="background-image: url('${picZJSC}');"</c:if> >
						<input type="file" name="file" onchange="changePic(this,'2');">
						<span class="pic_loading" id="loading_2">正在上传</span>
					</div>
				</form>			
			</li>
		</ul>
		<p class="myPicListPosTips">请分别上传证件正面、反面、本人手持证件照片或个人资料页、持照人签名页、本人手持个人资料页照片</p>
		
		
		<input type="hidden" id="sourceType" value="${sourceType }">
		<input type="hidden" id="list_small_pic" value="${list_small_pic }">
		<input type="hidden" id="picBaseAddrMona" value="${picBaseAddrMona }">
		<input type="hidden" id="picSuffix" value="${picSuffix}">
		<input type="hidden" id="authType" value="${authType}">
		
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/jquery.form.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common/commonUtils.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/personal/authDetail.js${VERSION}008"></script>
	
	
</body>
</html>