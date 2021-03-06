<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath }">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台爬虫系统">
<meta name="description" content="自如民宿后台爬虫系统">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">   
<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
		<div class="col-sm-12">
			  <div class="tabs-container">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">房源列表爬取</a></li>
                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="true">爬取房源详情</a></li>
                        <li class=""><a data-toggle="tab" href="#tab-3" aria-expanded="true">爬取房东详情</a></li>
                    </ul>
                    <div class="tab-content">
                    	<div id="tab-1" class="tab-pane active">
                    		<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content"> 
						                    <div class="panel panel-default">
											    <div class="panel-heading">
											        <h3 class="panel-title">房源列表URL，如：</h3>
											        <br>
											        <p style="margin-right: 20px;color: red;display: inline-block;word-break:break-all;">
											        	http://bj.xiaozhu.com/zhengzu-duanzufang-2/?guestnum=3&facilities=facility_AirCondition%7Cfacility_Tv%7C&housetyperoomcnt=yiju%7C
											        </p>
											        <p>
											        	<span style="color: red;font-weight: bolder;">注：请确保此处的URL为将原列表URL在新的标签页打开之后转成的英文URL，此爬虫时间可能较长</span> 
											        </p>
											    </div>
											    <div class="panel-body">
											       	<input id="houseListUrl" name="houseListUrl" type="text" class="form-control m-b">
				                                    <br>
								                    <button class="btn btn-primary" type="button" onclick="spiderListByUrlStart();">&nbsp;启动房源列表爬虫</button>
								                    <button class="btn btn-primary" type="button" onclick="spiderListByUrlStop();">&nbsp;停止房源列表爬虫</button>
											    </div>
											</div>
											<div class="panel panel-default">
											    <div class="panel-heading">
											        <h3 class="panel-title">房源所在城市，如：</h3>
											        <br>
											        <p style="margin-right: 20px;color: red;display: inline-block;word-break:break-all;">
											        	<span style="font-weight: bold;">bj</span>
											        </p>
											        <p>
											        	<span style="color: red;font-weight: bolder;">注：此爬虫时间可能较长</span> 
											        </p>
											    </div>
											    <div class="panel-body">
											       	<input id="spidercity" name="spidercity" type="text" class="form-control m-b">
				                                    <br>
								                    <button class="btn btn-primary" type="button" onclick="spiderListByCityStart();">&nbsp;启动城市爬虫</button>
								                    <button class="btn btn-primary" type="button" onclick="spiderListByCityStop();">&nbsp;停止城市爬虫</button>
											    </div>
											</div>
											<div class="panel panel-default">
											    <div class="panel-heading">
											        <h3 class="panel-title">按照默认设置爬取</h3>
											        <p>
											        	<span style="color: red;font-weight: bolder;">注：此爬虫时间可能较长</span> 
											        </p>
											    </div>
											    <div class="panel-body">
											       	<button class="btn btn-primary" type="button" onclick="spiderAllByDefaultStart();">&nbsp;启动默认爬虫</button>
								                    <button class="btn btn-primary" type="button" onclick="spiderAllByDefaultStop();">&nbsp;停止默认爬虫</button>
											    </div>
											</div>
											
					                    </div>
					                </div>
					            </div>
                    		</div>
                    	</div>
                    	<div id="tab-2" class="tab-pane">
                    		<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                    	<div class="panel panel-default">
											    <div class="panel-heading">
											        <h3 class="panel-title">房源详情URL，如：<span style="color: red;">http://bj.xiaozhu.com/fangzi/5224005014.html</span> </h3>
											    </div>
											    <div class="panel-body">
				                                    <input id="houseDetailUrl" name="houseDetailUrl" type="text" class="form-control m-b">
				                                    <br>
								                    <button class="btn btn-primary" type="button" onclick="spiderHouseByUrl(this);">&nbsp;爬取房源详情</button>
											    </div>											    
											</div> 
					                    </div>
					                </div>
					            </div>
                    		</div>
                    	</div>
                    	<div id="tab-3" class="tab-pane ">
                    		<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                    	<div class="panel panel-default">
											    <div class="panel-heading">
											        <h3 class="panel-title">房东详情URL，如：<span style="color: red;">http://www.xiaozhu.com/fangdong/508442900/</span></h3>
											    </div>
											    <div class="panel-body">
				                                    <input id="hostDetailUrl" name="hostDetailUrl" type="text" class="form-control m-b">
				                                    <br>
								                    <button class="btn btn-primary" type="button" onclick="spiderHostByUrl(this);">&nbsp;爬取房东详情</button>
											    </div>											    
											</div> 
					                    </div>
					                </div>
					            </div>
                    		</div>
                    	</div>
                    	
                    	
              		</div>
			</div>
		</div>
	</div>
	
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>


<script type="text/javascript">

   function spiderAllByDefaultStart(){
	   ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderAllByDefaultStart",{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
	   });
   }
   
   function spiderAllByDefaultStop(){
	   ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderAllByDefaultStop",{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
	   });
   }
   
   function spiderListByUrlStart(){
	    var url =$.trim($("#houseListUrl").val());
	    
	    var strRegex = "^(http://)[a-z]{1,10}(.xiaozhu.com/)"
	    var re=new RegExp(strRegex);
	    if (!re.test(url)){
	    	layer.alert('url不正确，请按照示例url爬取！', {icon: 1,time: 2000, title:'提示'});	
	    	return;
	    } 
	    ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderListByUrlStart",{
	    	listUrl:url
	    },function(result){
			   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
		   },function(result){
			   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
		   });
		   
   }
   
   function spiderListByUrlStop(){
	   ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderListByUrlStop",{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
	   });
   }
   
   function spiderListByCityStart(){
	    var spidercity =$.trim($("#spidercity").val());
	    var strRegex = "^[a-z]{1,10}$"
	    var re=new RegExp(strRegex);
	    if (!re.test(spidercity)){
	    	layer.alert('城市参数不正确，请按照示例参数爬取！', {icon: 1,time: 2000, title:'提示'});	
	    	return;
	    } 
	    ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderAllByCityStart",{
	    	city:spidercity
	    },function(result){
			   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
		   },function(result){
			   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
		   });
		   
  }
  
  function spiderListByCityStop(){
	   ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderAllByCityStop",{},function(result){
		   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});		   
	   },function(result){
		   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
	   });
  }
   
   function spiderHouseByUrl(btnObj){
	   if($(btnObj).hasClass("btn-disabled")){
		   return;
	   }
	   
	    var url =$.trim($("#houseDetailUrl").val());
	    var strRegex = "^(http://)[a-z]{1,10}(.xiaozhu.com/fangzi/)[0-9]{1,20}(.html)$"
	    var re=new RegExp(strRegex);
	    if (!re.test(url)){
	    	layer.alert('url不正确，请按照示例url爬取！', {icon: 1,time: 2000, title:'提示'});	
	    	return;
	    } 
	    $(btnObj).removeClass("btn-primary").addClass("btn-disabled");
	    
	    ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderHouseByUrl",{
	    	url:url
	    },function(result){
			   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});	
			   $("#houseDetailUrl").val("");
			   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");   
		   },function(result){
			   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
			   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");   
		   });
	     
  }
   
  function spiderHostByUrl(btnObj){
	  if($(btnObj).hasClass("btn-disabled")){
		   return;
	   }
	  var url =$.trim($("#hostDetailUrl").val());
	  var strRegex = "^(http://)(www.xiaozhu.com/fangdong/)[0-9]{1,20}"
	    var re=new RegExp(strRegex);
	    if (!re.test(url)){
	    	layer.alert('url不正确，请按照示例url爬取！', {icon: 1,time: 2000, title:'提示'});	
	    	return;
	    } 
	    $(btnObj).removeClass("btn-primary").addClass("btn-disabled");
	    ajaxGetSubmit("${basePath}/xiaozhu/spider/spiderHostByUrl",{
	    	url:url
	    },function(result){
	    		$("#hostDetailUrl").val("");
			   layer.alert(result.msg, {icon: 1,time: 2000, title:'提示'});	
			   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
		   },function(result){
			   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});	
			   $(btnObj).removeClass("btn-disabled").addClass("btn-primary");  
		   });
		   
 } 
    
    function ajaxGetSubmit(url,data,successCallback,failCallback){
		$.ajax({
	    type: "GET",
	    url: url,
	    dataType:"json",
	    data: data,
	    success: function (result) {
	    	successCallback(result);
	    },
	    error: function(result) {
	    	failCallback(result);
	    }
	 });
	}
     
    

	
</script>
</body>

</html>
