<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
	<style>
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }
        .mtop{margin-top:10px;}
        .help-block{margin-left:-10px;}
    </style>
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	   	<form action="house/houseBusiness/saveHouseBusiness" method="post" id="saveHouseBusiness">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				           	<div class="form-group">
		                       <div class="row">
		                             <div class="col-sm-1">
			                   	   <label class="control-label mtop"><font size=3 color=red>*</font>国家/地区:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b m-b" name="businessMsg.nationCode" id="nationCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>省:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b " name="businessMsg.provinceCode" id="provinceCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>市:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="businessMsg.cityCode"  id="cityCode" required>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>区:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="businessMsg.areaCode"  id="areaCode" required>
	                                   </select>
	                               </div>
		                       </div>
		                       
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房源商机录入</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>小区名称:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="businessMsg.communityName" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>详细地址:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="businessMsg.houseAddr" value=""  type="text" class="form-control m-b" required>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>出租方式:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <select class="form-control m-b" name="businessMsg.rentWay" >
		                           	   	  <option value="0">整租</option>
		                           	   	  <option value="1">合租</option>
		                           	   	  <option value="2">可整可分</option>
	                                   </select>
	                               </div>
	                               
	                               <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>商圈:</label>
		                           <div class="col-sm-1">
	                                   <input name="businessMsg.businessArea" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房源其他信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>信息来源:</label>
		                           <div class="col-sm-1">
		                           	   <select class="form-control m-b" name="businessSource.busniessSource" >
		                           	   	  <option value="0">地推扩展</option>
		                           	   	  <option value="1">房东自住发布</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>是否在作业范围:</label>
		                           <div class="col-sm-1">
		                           	   <select class="form-control m-b" name="businessSource.isJobArea" >
		                           	   	  <option value="0">否</option>
		                           	   	  <option value="1">是</option>
	                                   </select>
	                               </div>
				                   <label class="col-sm-1 control-label  mtop"><font size=3 color=red>*</font>平台链接:</label>
		                           <div class="col-sm-3 ">
		                           	   <input id="housePrice" name="businessSource.sourceUrl" value="" type="text" class="form-control m-b" required>
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>评价量:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="businessSource.evaluateNum" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>评分:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="businessSource.houseGrade" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房东信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>房东姓名:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordName" value="" type="text" class="form-control m-b" required>
	                               </div>
	                                <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>房东手机:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordMobile" value="" type="text" class="form-control m-b" required>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房东昵称:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordNickName" value="" type="text" class="form-control m-b" >
	                               </div>
	                                <label class="col-sm-1 control-label mtop">QQ号码:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordQq" value="" type="text" class="form-control m-b" >
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">微信昵称:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordWechat" value="" type="text" class="form-control m-b" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">电子邮箱:</label>
		                           <div class="col-sm-2">
		                           	   <input name="businessExt.landlordEmail" value="" type="text" class="form-control m-b" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房东类型:</label>
		                           <div class="col-sm-1">
		                           	     <select class="form-control m-b" name="businessExt.landlordType" >
			                           		<option value="0">专业型</option>
			                           		<option value="1">半专业型</option>
			                           		<option value="2">体验型</option>
			                             </select>
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">业务信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>经理:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.managerName" value="" type="text" class="form-control m-b" id="managerName" readonly="readonly" required>
		                           	   <input name="businessExt.managerCode" value="" type="hidden" class="form-control m-b" id="managerCode">
		                           	   <span class="input-group-btn">
	                                   <button type="button" class="btn btn-white" data-toggle="modal" data-target="#myModal5" onclick="andManager();">
	                                  	选择维护管家经理
	                              	   </button>
	                              	   </span>
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop"><font size=3 color=red>*</font>维护管家:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.whGuardName" value="" type="text" class="form-control m-b" id="whGuardName" readonly="readonly" required>
		                           	   <input name="businessExt.whGuardCode" value="" type="hidden" class="form-control m-b" id="whGuardCode">
		                           	   <span class="input-group-btn">
	                                   <button type="button" class="btn btn-white" data-toggle="modal" data-target="#myModal5" onclick="andWhGuard();">
	                                  	选择维护管家
	                              	   </button>
	                              	   </span>
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">地推信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">是否与房东见面沟通过:</label>
		                           <div class="col-sm-1">
		                           	    <select class="form-control m-b" name="businessExt.isMeet" >
		                           	   	  <option value="0">否</option>
		                           	   	  <option value="1">是</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">目前进度:</label>
		                           <div class="col-sm-2">
		                           	    <select class="form-control m-b" name="businessExt.businessPlan" >
		                           	   	  <option value="0">已获取房源信息</option>
		                           	   	  <option value="1">已获取联系方式</option>
		                           	   	  <option value="2">已沟通意向</option>
		                           	   	  <option value="3">已发布</option>
		                           	   	  <option value="4">管家驳回</option>
		                           	   	  <option value="5">品质驳回</option>
		                           	   	  <option value="6">已上架</option>
		                           	   	  <option value="7">已线下核验</option>
		                           	   	  <option value="8">已拍照</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">驳回原因:</label>
		                           <div class="col-sm-2">
		                           	    <select class="form-control m-b" name="businessExt.rejectReason" >
		                           	      <option value="">请选择</option>
		                           	   	  <option value="0">房东挂错</option>
		                           	   	  <option value="1">品质不符无法升级</option>
		                           	   	  <option value="2">品质不符需要升级</option>
		                           	   	  <option value="3">其他</option>
	                                   </select>
	                               </div>
		                       </div>
							   <div class="row">
							   	   <label class="col-sm-1 control-label mtop">跟进内容:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="businessExt.followContent" class="form-control m-b"></textarea>
	                               </div>
							   </div>
							   <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">发布时间:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="releaseDate" name="releaseDate" class="laydate-icon form-control layer-date" >
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">上架时间:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="putawayDate" name="putawayDate" class="laydate-icon form-control layer-date" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">预约线下核验日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="makeCheckDate" name="makeCheckDate" class="laydate-icon form-control layer-date" >
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">实际线下核验日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="realCheckDate" name="realCheckDate" class="laydate-icon form-control layer-date" >
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">预约拍照日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="makePhotoDate" name="makePhotoDate" class="laydate-icon form-control layer-date" >
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">实际拍照日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="realPhotoDate" name="realPhotoDate" class="laydate-icon form-control layer-date" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">登记日期:</label>
		                           <div class="col-sm-2">
		                           	   <input  id="registerDate" name="registerDate"  value="${registerDate}" class="laydate-icon form-control layer-date" >
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">备注信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
							   	   <label class="col-sm-1 control-label mtop">备注信息:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="businessMsg.businessRemark" class="form-control m-b"></textarea>
	                               </div>
							   </div>
			               </div>
			           </div>
	               </div>
       		       <div class="col-sm-4 col-sm-offset-2">
	                   <customtag:authtag authUrl="house/houseBusiness/saveHouseBusiness"><button class="btn btn-primary" type="submit" >保存内容</button></customtag:authtag>
	               </div>
	           </div>
	       </div>
	   </form>
	   </div>
	   <jsp:include page="../../common/selectEmp.jsp"></jsp:include>
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	   <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   
	   <!-- Bootstrap table -->
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	
	   <!--  validate -->
	   <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/Math.uuid.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	   
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}"></script>
	   
       <script type="text/javascript">
	   	$(function(){  
			//外部js调用
		   laydate({
		      elem: '#releaseDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#putawayDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#makeCheckDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#realCheckDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#makePhotoDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#realPhotoDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
		   laydate({
		      elem: '#registerDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		   }); 
			// 多级联动
			var areaJson = ${cityTreeList};
			var defaults = {
			geoJson	: areaJson
			};
			geoCascade(defaults);
		});
		
		//选择维护管家经理
		function andManager(){
	   		$("#empCodeId").val("managerCode");
	   		$("#empNameId").val("managerName");
	   	}
		function andWhGuard(){
			$("#empCodeId").val("whGuardCode");
	   		$("#empNameId").val("whGuardName");
		}
		
		$("#saveHouseBusiness").validate({
			rules:{
				"businessSource.evaluateNum": {
					digits:true ,
					maxlength:11
		         },
				"businessSource.houseGrade": {
					number:true,
					maxlength:11
         		 },
				"businessExt.landlordMobile": {
					required:true,
					maxlength:12,
					digits:true 
		         },
				"businessMsg.communityName": {
					required:true,
					maxlength:99
		         },
				"businessMsg.houseAddr": {
					required:true,
					maxlength:254
		         },
				"businessMsg.businessArea": {
					required:true,
					maxlength:99
		         },
				"businessSource.sourceUrl": {
					required:true,
					maxlength:500
		         },
				"businessExt.landlordNickName": {
					maxlength:50
		         },
				"businessExt.landlordName": {
					required:true,
					maxlength:50
		         },
				"businessExt.landlordQq": {
					maxlength:20
		         },
				"businessExt.landlordWechat": {
					maxlength:50
		         },
				"businessExt.followContent": {
					maxlength:1500
		         },
				"businessMsg.businessRemark": {
					maxlength:1500
		         },
				"businessExt.landlordEmail": {
					email:true,
					maxlength:50
         		 }
		    },
			submitHandler:function(){
				$.ajax({
		            type: "POST",
		            url: "house/houseBusiness/saveHouseBusiness",
		            dataType:"json",
		            data: $("#saveHouseBusiness").serialize(),
		            success: function (result) {
		            	if(result.code==0){
							$.callBackParent("house/houseBusiness/houseBusinessList",true,saveHouseBusinessListCallback);
		            	}else{
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
		}});
		//保存商机刷新父页列表
		function saveHouseBusinessListCallback(panrent){
			panrent.refreshData("listTable");
		}
	   </script>
	</body>
</html>
