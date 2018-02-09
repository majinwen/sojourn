<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="<%=basePath%>">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <style type="text/css">  
	html{height:100%}  
	body{height:100%;margin:0px;padding:0px}  
	#container{height:100%}  
	</style>  

    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
</head>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TMbMngjLXTSEcP80tADrcqzR"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js "></script> 
</head>  

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>获取坐标</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown"
								href="form_basic.html#"> <i class="fa fa-wrench"></i>
							</a>
							<ul class="dropdown-menu dropdown-user">
								<li><a href="form_basic.html#">选项1</a></li>
								<li><a href="form_basic.html#">选项2</a></li>
							</ul>
							<a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<div class="form-group">
							<label class="col-sm-2 control-label">经纬度</label>
							<div class="col-sm-2">
						  	<input type="text" class="form-control" id="location" name="location" value="">
							</div>
							<div class="col-sm-4 col-sm-offset-2">
								<button class="btn btn-primary" id="copyBtn" type="button" onclick="copy();">复制</button>
							</div>
						</div>
						<div class="hr-line-dashed" ></div>
					</div>
					<div class="ibox-content">
						<div class="form-group">
							<label class="col-sm-2 control-label">要查询的地址</label>
							<div class="col-sm-2">
						  	<input type="text" class="form-control" id="text_" name="text_" value="${stationName}">
							</div>
							<div class="col-sm-4 col-sm-offset-2">
								<button class="btn btn-primary" id="queryBtn" type="button" onclick="searchByStationName();">查询结果(经纬度)</button>
							</div>
						</div>
						<div class="hr-line-dashed" ></div>
					</div>
				</div>
			</div>
		</div>
		<div id="container"></div> 
	</div>			
	
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<script type="text/javascript"> 
		var map = new BMap.Map("container", {enableMapClick:false});          // 创建地图实例  ，关闭底图可点功能
		var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
		map.centerAndZoom(point, 11);                 // 初始化地图，设置中心点坐标和地图级别  
		map.enableScrollWheelZoom(true);
		
		//单击获取点击的经纬度
		map.addEventListener("click",function(e){
			document.getElementById("location").value = e.point.lng + "," + e.point.lat;
			map.clearOverlays();
			
			// 创建标注对象并添加到地图
			var marker = new BMap.Marker(e.point);
			map.addOverlay(marker);
			marker.setAnimation(BMAP_ANIMATION_BOUNCE);
		});
		
		
		function copy(){
			var location=document.getElementById("location");
			if(location.value != ''){
				location.select(); // 选择对象
				document.execCommand("Copy"); // 执行浏览器复制命令
				layer.alert("复制成功", {icon: 6,time: 2000, title:'提示'});
			}
		}
		
		
		//搜索
		var localSearch = new BMap.LocalSearch(map);
		localSearch.enableAutoViewport(); //允许自动调节窗体大小
		function searchByStationName() {
			map.clearOverlays();//清空原来的标注，重新加载当前区域标注
			var keyword = document.getElementById("text_").value;
			if(keyword == ''){
				layer.alert("请输入要查询的地址", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			localSearch.setSearchCompleteCallback(function(searchResult) {
				var poi = searchResult.getPoi(0);
				document.getElementById("location").value = poi.point.lng + "," + poi.point.lat; //获取经度和纬度，将结果显示在文本框中
				map.centerAndZoom(poi.point, 14);

				// 创建标注对象并添加到地图
				var marker = new BMap.Marker(poi.point);
				map.addOverlay(marker);
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); 

			});
			localSearch.search(keyword);
		}
		
		$(function(){
			if($("#text_").val() != ''){
				searchByStationName();
			}
		});
		
	</script>




</body>  
</html>