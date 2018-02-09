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

    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">

	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
           <div class="col-sm-12">
               <div class="ibox float-e-margins">
			  	   <div class="ibox-content">
			           	<form class="form-group">
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
	                       <div class="row">
			                   <label class="col-sm-1 control-label mtop">地铁线:</label>
	                           <div class="col-sm-2">
                                   <input id="lineName" name="lineName" type="text" class="form-control m-b">
                               </div>
			                   <label class="col-sm-1 control-label mtop">地铁站:</label>
	                           <div class="col-sm-2">
                                   <input id="stationName" name="stationName" type="text" class="form-control m-b">
                               </div>
                               <div class="col-sm-1">
			                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                   </div>
	                       </div>
		               </form>
		           </div>
                   <div class="ibox-content">
		               <div class="row row-lg">
		                   <div class="col-sm-12">
		                   <customtag:authtag authUrl="config/subway/addSubway">
		                       <button class="btn btn-primary " type="button" onclick="toAddSubway();"><i class="fa fa-plus"></i>&nbsp;添加</button>
		                   </customtag:authtag>
		                   <customtag:authtag authUrl="config/subway/toEditSubway">
		                       <button class="btn btn-info " type="button" onclick="toEditSubway();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
		                   </customtag:authtag>
		                       <div class="example-wrap">
		                           <div class="example">
		                               <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[10,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
			                            data-content-type="application/x-www-form-urlencoded"
					                    data-query-params="paginationParam"
					                    data-method="post"
					                    data-height="530"
					                    data-single-select="true" 
					                    data-url="config/subway/showSubway">                    
					                    <thead>
					                        <tr>
					                            <th data-field="lineFid" data-visible="false"></th>
					                            <th data-field="stationFid" data-visible="false"></th>
					                            <th data-field="outFid" data-visible="false"></th>
					                            <th data-field="id" data-width="5%" data-checkbox="true"></th>
					                            <th data-field="lineName" data-width="15%" data-align="center">地铁线</th>
					                            <th data-field="stationName" data-width="15%" data-align="center">地铁站</th>
					                            <th data-field="stationLocation" data-width="15%" data-align="center" data-formatter="formateStationLocation">地铁站坐标</th>
					                            
					                            <!-- <th data-field="stationLongitude" data-width="15%" data-align="center">地铁站经度</th>
					                            <th data-field="stationLatitude" data-width="15%" data-align="center">地铁站纬度</th> -->
					                            
					                            <th data-field="outName" data-width="15%" data-align="center">地铁站出口</th>
					                            <th data-field="outLocation" data-width="15%" data-align="center" data-formatter="formateOutLocation">地铁站坐标</th>
					                            
					                            <!-- <th data-field="outLongitude" data-width="15%" data-align="center">出口经度</th>
					                            <th data-field="outLatitude" data-width="15%" data-align="center">出口纬度</th> -->
					                        </tr>
					                    </thead>             
			                           </table>
		                           </div>
		                       </div>
		                       <!-- End Example Pagination -->
		                   </div>
		               </div>
		           </div>
               </div>
           </div>
	   </div>
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
	
	    <!-- 自定义js -->
	    <script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>
	
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
		<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
		   $(function(){
	  			// 多级联动
	  			var areaJson = ${cityTreeList};
	  			var options = {
						geoJson	: areaJson
	  			};
	  			geoCascade(options);
	  		});
	   
		    function paginationParam(params) {
			    return {
			        limit: params.limit,
			        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			        
			        nationCode:$('#nationCode option:selected').val(),
			        provinceCode:$('#provinceCode option:selected').val(),
			        cityCode:$('#cityCode option:selected').val(),
			        
			        lineName:$('#lineName').val(),
			        stationName:$.trim($('#stationName').val())
		    	};
			}
		    
		    //查询
		    function query(){
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
			
			//经纬度
			function formateStationLocation(value, row, index){
				var longitude = row.stationLongitude;
				var latitude = row.stationLatitude;
				if(longitude== null || latitude==null){
					return '-';
				}
				return longitude + "," + latitude;
			}
			function formateOutLocation(value, row, index){
				var longitude = row.outLongitude;
				var latitude = row.outLatitude;
				if(longitude== null || latitude==null){
					return '-';
				}
				return longitude + "," + latitude;
			}
			
		    function appendHtml($obj,resultJson){
	    		$obj.append("<option value=\"\">请选择</option>");
		    	$.each(resultJson, function (i, item) {
		    		$obj.append("<option value=\""+item.code+"\">"+item.showName+"</option>");
	            });
		    }
		    
		    function toAddSubway(){
		    	window.location.href = "config/subway/addSubway";
		    }
		    
		    function toEditSubway(){
		    	var rows = $('#listTable').bootstrapTable('getSelections');
				if(rows.length == 0){
					layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
					return;
				}
				window.location.href = "config/subway/toEditSubway?lineFid="+rows[0].lineFid+"&stationFid="+rows[0].stationFid+"&outFid="+rows[0].outFid;
		    }
		    

		</script>
	</body>
</html>