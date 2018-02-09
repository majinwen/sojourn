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
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	   	<form action="cityFile/insertColumnCity" method="post" id="saveColumnCity">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				           	<div class="form-group">
				           		<div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>选择模板:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <select class="form-control m-b" name="tempFid" >
	                                   <c:forEach items="${tempList }" var="temp">
	                                   		<option value="${temp.fid }">${temp.tempName }</option>
	                                   </c:forEach>
	                                   </select>
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>选择城市:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <select class="form-control m-b" name="cityCode"  required>
	                                   <option value="">请选择</option>
	                                   <c:forEach items="${cityList }" var="temp">
	                                   		<option value="${temp.code }">${temp.name }</option>
	                                   </c:forEach>
	                                   </select>
	                               </div>
		                       </div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop"><font size=3 color=red>*</font>主标题:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="colTitle" value="" type="text" class="form-control m-b" required>
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label mtop">副标题:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="colDeputyTitle" value=""  type="text" class="form-control m-b" >
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <div class="col-sm-1">
				                   	   <label class="control-label mtop">分享标题:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="colShareTitle" value=""  type="text" class="form-control m-b" >
	                               </div>
		                       </div>
		                       <div class="row">
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">背景颜色:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="colBackColor" value=""  type="text" class="form-control m-b" >
	                               </div>                       
	                           </div>
			               </div>
			           </div>
	               </div>
       		       <div class="col-sm-4 col-sm-offset-2">
	                   <customtag:authtag authUrl="cityFile/insertTemplate"><button class="btn btn-primary" type="submit" >保存内容</button></customtag:authtag>
	               </div>
	           </div>
	       </div>
	   </form>
	   </div>
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
	   
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}"></script>
	   
       <script type="text/javascript">
		
		$("#saveColumnCity").validate({
			submitHandler:function(){
				$.ajax({
		            type: "POST",
		            url: "cityFile/insertColumnCity",
		            dataType:"json",
		            data: $("#saveColumnCity").serialize(),
		            success: function (result) {
		            	if(result.code==0){
							$.callBackParent("cityFile/columnCityList",true,saveColumnCityCallback);
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
		function saveColumnCityCallback(panrent){
			panrent.refreshData("listTable");
		}
	   </script>
	</body>
</html>
