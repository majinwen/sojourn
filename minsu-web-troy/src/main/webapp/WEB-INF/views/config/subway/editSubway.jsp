<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
           <div class="col-sm-12">
               <div class="ibox float-e-margins">
			  	   <div class="ibox-content">
	                   <form class="form-group" id="form">
	                       <input name="lineFid" value="${subwayLine.fid }" type="hidden">
	                       <input name="stationFid" value="${subwayStation.fid }" type="hidden">
	                       <div class="row">
	                           <div class="col-sm-1">
			                   	   <label class="control-label">国家:</label>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b m-b" id="nationCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">省份:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b " id="provinceCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">城市:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" id="cityCode">
                                   </select>
                               </div>
	                       </div>
	                       
	                       <div class="hr-line-dashed" ></div>
	                       <div class="row">
			                   <label class="col-sm-1 control-label">地铁线:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="lineName" value="${subwayLine.lineName}" type="text" class="form-control">
                               </div>
			                   <label class="col-sm-1 control-label">地铁站:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="stationName" value="${subwayStation.stationName}" type="text" class="form-control">
                               </div>
                               <label class="col-sm-1 control-label">地铁站坐标:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="stationLocation" value="${subwayStation.longitude },${subwayStation.latitude }" type="text" class="form-control">
                               </div>
                               <label class="col-sm-1 control-label"></label>
	                           <div class="col-sm-2">
                                   <button class="btn btn-primary" id="saveBtn" type="button" onclick="toMap();">没有获取坐标？去百度地图</button>
                               </div>
	                       </div>
	                    </form>
	                    
	                    <form class="form-group" id="form2">
	                       <input name="outFid" value="${subwayOut.fid }" type="hidden">
	                       <div class="hr-line-dashed" ></div>
	                       <div class="row">
			                   <label class="col-sm-1 control-label">地铁站出口:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="outName" value="${subwayOut.outName}" type="text" class="form-control">
                               </div>
			                   <label class="col-sm-1 control-label">地铁站出口坐标:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="outLocation" value="${subwayOut.longitude},${subwayOut.latitude}" type="text" class="form-control">
                               </div>
	                       </div>
	                    </form>
	                    
	                    <form class="form-group" id="form3">
	                       <div class="hr-line-dashed" ></div>
	                       <div class="row">
                               <div class="col-sm-1">
			                       <button id="updateButton" class="btn btn-primary" type="button" onclick="updateSubway();">保存</button>
			                   </div>
			                    <div class="col-sm-1">
					               <button class="btn btn-white" type="button" onclick="javascript:history.back(-1);">取消</button>
					            </div>
	                       </div>
	                       
							<div class="hr-line-dashed"></div>
							<div class="row">
								<div class="col-sm-1">
									<button class="btn btn-primary" id="saveBtn" type="button" onclick="toMap();">没有获取坐标？去百度地图</button>
								</div>
							</div>
	                   </form>
		           </div>
               </div>
           </div>
	   </div>
	   
		<!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
		<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
		<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	    
		<!-- 自定义js -->
		<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>
		
		<!-- iCheck -->
		<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}001"></script>
	
	   <!-- Page-Level Scripts -->
	   <script>
	   		$(function(){
	  			// 多级联动
	  			var areaJson = ${cityTreeList};
	  			var options = {
					geoJson	: areaJson,
					nationCode: '${subwayLine.nationCode}',
   					provinceCode: '${subwayLine.provinceCode}',
   					cityCode: '${subwayLine.cityCode}'
	  			};
	  			geoCascade(options);
	   			
	   			
	   			$('.i-checks').iCheck({
	                checkboxClass: 'icheckbox_square-green',
	                radioClass: 'iradio_square-green',
	            });
		 		
		 		//扩展经纬度校验
		 		/* jQuery.validator.addMethod("geo", function(value, element) {
		 			value = $.trim(value);
		 			var regex = /^(-?(180|(1[0-7]\d|[1-9]\d?)(\.\d{1,5})?))\,(-?(90|([1-8]\d|[1-9])(\.\d{1,5})?))$/;
	 	        	return this.optional(element) || regex.test(value);       
		 	    }, "经纬度格式不正确"); */
		 		
		 		var icon = "<i class='fa fa-times-circle'></i> ";
	            $("#form").validate({
	                rules: {
	                	nationCode: "required",
	                	provinceCode: "required",
	                	cityCode: "required",
	                	lineName: "required",
	                	stationName: "required",
	                	stationLocation: {
	                        required: true//,
	                        //geo: true
	                    }
	                }
	            });
	   		});
	   		
		    function appendHtml($obj,resultJson){
	  			$obj.append("<option value=\"\">请选择</option>");
		    	$.each(resultJson, function (i, item) {
		    		$obj.append("<option value=\""+item.code+"\">"+item.showName+"</option>");
	           });
		    }
		    
		    function updateSubway(){
		    	$("#updateButton").attr("disabled","disabled");
		    	var array = $("#form").serializeArray();
		    	var subway = toJsonString(array);
		    	$.ajax({
		    		beforeSend:function(){
	    				var valid = $("#form").valid();
	    				if(!valid){
	    					$("#updateButton").removeAttr("disabled");
	    				}
	    				return valid;
	    			},
					url:"config/subway/updateSubway",
					data:{
						"subway":subway
					},
					dataType:"json",
					type:"post",
					success:function(result) {
						if(result.code === 0){
							layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
							$("#updateButton").removeAttr("disabled");
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							$("#updateButton").removeAttr("disabled");
						}
					},
					error:function(result){
						layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				    	$("#updateButton").removeAttr("disabled");
					}
				});
		    }
		    
		    function toJsonString(array){
				var json = {};
				$.each(array, function(i, n){
					var key = n.name;
					var value = n.value;
					if(key == "stationLocation" && !$.trim(value) == ''){
						var arr = value.split(",");
						json["stationLongitude"] = parseFloat(arr[0]);
						json["stationLatitude"] = parseFloat(arr[1]);
						return true;
					}
					if(!$.trim(value) == ''){
						json[key] = value;
					}
				});
				
				var outList = [];
				$("#form2").each(
					function(i, field){
						var jsonOut = {};
						var out = decodeURIComponent($(this).serialize(),true);
						var arr = out.split("&");
						for (var i = 0; i < arr.length; i++) {
							var arr2 = arr[i].split("=");
							if(arr2[0] == "outLocation" && !$.trim(arr2[1]) == ''){
								var arr3 = arr2[1].split(",");
								jsonOut["outLongitude"] = parseFloat(arr3[0]);
								jsonOut["outLatitude"] = parseFloat(arr3[1]);
							} else if(!$.trim(arr2[1]) == ''){
								jsonOut[arr2[0]] = arr2[1];
							}
						}
						if(!jQuery.isEmptyObject(jsonOut)){
							outList.push(jsonOut);
						}
					}
				)
				json["outList"] = outList;
				
				return JSON.stringify(json);
			}
		    
		    
		    function toMap(){
				var stationName = $("input[name='stationName']").val();
				$.openNewTab("map_"+stationName,'config/subway/toMap?stationName='+stationName+"&random="+Math.random(), "地图搜索");
			}
		</script>
	</body>
</html>
