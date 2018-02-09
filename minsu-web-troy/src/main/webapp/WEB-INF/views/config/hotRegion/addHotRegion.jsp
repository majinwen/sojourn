<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">

</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
           <div class="col-sm-12">
               <div class="ibox float-e-margins">
			  	   <div class="ibox-content">
			           	<form class="form-group" id="form">
	                       <div class="row">
			                   <label class="col-sm-1 control-label">国家:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="nationCode" id="nationCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">省份:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="provinceCode" id="provinceCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">城市:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="cityCode" id="cityCode">
                                   </select>
                               </div>
	                       </div>
	                       <div class="row">
			                   <label class="col-sm-1 control-label">类型:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="regionType" id="regionType">
                                   	   <c:forEach items="${hotRegionEnumList }" var="obj">
	                                       <option value="${obj.key }">${obj.text }</option>
                                   	   </c:forEach>
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">名称:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="regionName" type="text" class="form-control">
                               </div>
			                   <label class="col-sm-1 control-label">获取中心坐标</label>
	                           <div class="col-sm-2">
			                       <input id="location" name="location" type="text" class="form-control">
			                   </div>
							   <div class="col-sm-3">
                                   <customtag:authtag authUrl="config/subway/toMap">
							  	   	   <button class="btn btn-primary" type="button" onclick="toBaiduMap();">没有获取坐标？去地图获取坐标</button>
			                   	   </customtag:authtag>
							   </div>
	                       </div>
	                       <div class="row">
			                   <label class="col-sm-1 control-label">热度:</label>
	                           <div class="col-sm-2">
                                   <input id="hot" name="hot" type="text" class="form-control">
                               </div>
			                   <label class="col-sm-1 control-label">半径(KM):</label>
	                           <div class="col-sm-2">
                                   <input id="radii" name="radii" type="text" class="form-control">
                               </div>
                               <div class="col-sm-1">
                                   <customtag:authtag authUrl="config/hotRegion/saveHotRegion">
			                           <button class="btn btn-primary" id="saveBtn" type="button" onclick="addHotRegion();">保存</button>
			                   	   </customtag:authtag>
			                   </div>
	                       </div>
	                       <div class="row">
						  	   <span style="color: red" >
						  		   <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>注意：热度取值范围为0-100,数值越大,热度越高。<br>
							   </span>
	                       </div>
		               </form>
		           </div>
               </div>
           </div>
	   </div>
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
	
	    <!-- iCheck -->
    	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
        <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
        
        <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
        <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
<%--         <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script> --%>
        <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
		<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	   <!-- Page-Level Scripts -->
	   <script>
		 	$(function(){
		 		// 多级联动
	   			var areaJson = ${cityTreeList};
	   			var options = {
   					'geoJson'	: areaJson
	   			};
	   			geoCascade(options);
		 		
		 		$('.i-checks').iCheck({
	                checkboxClass: 'icheckbox_square-green',
	                radioClass: 'iradio_square-green',
	            });
		 		
		 		var icon = "<i class='fa fa-times-circle'></i> ";
	            $("#form").validate({
	                rules: {
	                	nationCode: "required",
	                	provinceCode: "required",
	                	cityCode: "required",
	                	regionType: "required",
	                	regionName: "required",
	                	location: {
	                        required: true,
	                        geo: true
	                    },
	                	hot: {
	                        required: true,
	                        nonnegaInteger: true,
	                        min: 0,
	                        max: 100
	                    },
	                    radii: {
	                        nonnegaInteger: true,
	                        min: 1,
	                        max: 120
	                    }
	                }
	            });
		 	});
		    
		    function addHotRegion(){
		    	$("#saveBtn").attr("disabled","disabled");
		    	var array = $("#form").serializeArray();
		    	var hotRegion = toJsonString(array);
		    	$.ajax({
	    			beforeSend:function(){
	    				return $("#form").valid();
	    			},
					url:"config/hotRegion/saveHotRegion",
					data:{
						"hotRegion":hotRegion
					},
					dataType:"json",
					type:"post",
					success:function(result) {
						if(result.code === 0){
							layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
							$.callBackParent("config/hotRegion/listHotRegions",true,callBack);
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
					},
					error:function(result){
						layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
					}
				});
		    	$("#saveBtn").removeAttr("disabled");
		    }
		    
		    function toJsonString(array){
				var json = {};
				$.each(array, function(i, n){
					var key = n.name;
					var value = n.value;
					if(key == "location" && $.trim(value).length != 0){
						var arr = value.split(",");
						json["googleLongitude"] = parseFloat(arr[0]);
						json["googleLatitude"] = parseFloat(arr[1]);
						return true;
					}
					if($.trim(value).length != 0){
						json[key] = value;
					}
				});
				return JSON.stringify(json);
			}

	        function callBack(parent){
	        	parent.refreshData("listTable");
	        }
	        
			function toBaiduMap() {
				var regionName = $.trim($("#regionName").val());
				$.openNewTab(new Date().getTime(),'config/subway/toMap?stationName='+regionName+"&random="+Math.random(), "地图搜索");
			}
		</script>
	</body>
</html>
