<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style>
        .lightBoxGallery img {margin: 5px;width: 160px;}
        label{font-weight:500;margin-top:8px;}
        .room-pic{float:left;}
        .room-pic p{text-align:center;}
        .blueimp-gallery>.title{left:0;bottom:45px;top:auto;width:100%;text-align:center;}
        .house-desc-color{position:absolute;right:20px; bottom:6px;color:cc3366;font-size:6px}
    </style>
</head>
  <body class="gray-bg">
	   <div id="blueimp-gallery" class="blueimp-gallery">
	       <div class="slides"></div>
	       <h3 class="title"></h3>
	       <a class="prev">‹</a>
	       <a class="next">›</a>
	       <a class="close">×</a>
	       <a class="play-pause"></a>
	       <ol class="indicator"></ol>
	   </div>
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
				           	<h2 class="title-font">城市信息</h2>
		                       <div class="row">
	                               <div class="col-sm-1">
			                   	   <label class="control-label">城市名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                               	   <input id="showName" name="showName" value="${showName}" readonly="readonly" type="text" class="form-control m-b">    
	                               </div>
	                           </div>
	   		  			 	   <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">城市内容:</label>
	                               </div>
		                           <div class="col-sm-5">
	                                  <%--  <textarea id="cityFileContent" name="cityFileContent"  readonly="readonly"  rows="100" cols="100" escape="false">
	                                   		${fileCity.cityFileContent}
	                                   </textarea> --%>
	                                   ${fileCity.cityFileContent}
	                               </div>
	                           </div>
	   		  			 </div>  	
	   		  		  </div>
	   		  	  </div>
	   		   </div>	
		  </div>                   
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
 	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   
	   
	</body>
</html>
