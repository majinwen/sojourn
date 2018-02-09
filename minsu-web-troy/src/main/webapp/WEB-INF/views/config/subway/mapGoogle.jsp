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
	body{height:100%;margin:0px;padding:0px;font-family: Circular, "Helvetica Neue", Helvetica, Arial, sans-serif}  
	#container{height:100%}  
	.ceter-icon{
                  width: 36px;
                  height: 36px;
                  position: absolute;
                  left: 50%;
                  top: 63%;
                  margin-left: -12px;
                  margin-top: -36px;
                  background: url(${staticResourceUrl}/img/marker_high.png) no-repeat;
                  background-size: 100% 100%;
              }
            .addressTag{
                font-size: 14px;
                color: #666666;
                line-height: 30px;
                position: absolute;
                background-color: white;
                left: 50%;
                top: 55%;
                margin-top: -36px;
                padding: 0 21px;
                box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.5);
                max-width: 90%;
                /*单行显示,多余字符显示省略号*/
                text-overflow:ellipsis; white-space:nowrap; overflow:hidden;
            }
            .addressArrow{
                width: 15.5px;
                height: 11px;
                position: absolute;
                left: 50%;
                top: 62%;
                margin-left: -5px;
                margin-top: -48px;
                background: url(${staticResourceUrl}/img/addressArrow.png) no-repeat;
                background-size: 100% 100%;
                display: none;
            }
	</style>  
	
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
</head>

<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TMbMngjLXTSEcP80tADrcqzR"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js "></script>  -->

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
		<div id="container">  <div class="addressTag"></div></div> 
          <div class="button-restoring" id="setCenter"></div>
	      <div class="ceter-icon"></div>
	      <div class="addressTag"></div>
	      <div class="addressArrow"></div>
	</div>			
	
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	
	<script type="text/javascript" src="http://maps.google.cn/maps/api/js?sensor=false&key=AIzaSyCT54l5ah9i7q1AEClN_B7RlDzRghdcgEg"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/googlemap.js${VERSION}003"></script> 
	
	<script type="text/javascript"> 
	
		if($("#text_").val() != ''){
			try {
				var keyword = document.getElementById("text_").value;
				getGoogleLocationByKeyword(keyword);		
			} catch (e) {
				console.log(e);
			}
		}
		
	</script>




</body>  
</html>