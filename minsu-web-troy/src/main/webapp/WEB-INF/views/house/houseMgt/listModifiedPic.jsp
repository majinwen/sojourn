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

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
	
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style type="text/css">
		.oneline{width:100px;display:inline-block;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;}
	</style>
</head>
  
  <body class="gray-bg">
       <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
                  <div class="tabs-container">
                    <ul class="nav nav-tabs">
                        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 整租</a></li>
                        <li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">合租</a></li>
                    </ul>
                    <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
                      		<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                    	<div class="row">
					                    	    <label class="col-sm-1 control-label mtop">房源编号:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="houseSn" name="houseSn"/>
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房东姓名:</label>
					                            <div class="col-sm-2">
				                                   <input id="landlordName" name="landlordName" type="text" class="form-control m-b">
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房东手机:</label>
					                            <div class="col-sm-2">
				                                   <input id="landlordMobile" name="landlordMobile" type="text" class="form-control m-b">
				                                </div>
						                     </div>
					                       	<div class="row">
				                                <label class="col-sm-1 control-label mtop">摄影师姓名:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanName" name="cameramanName"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">摄影师手机:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanMobile" name="cameramanMobile"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">是否上传照片:</label>
					                            <div class="col-sm-2">
				                                   <select class="form-control m-b" id="isPic">
					                                   <option value="">请选择</option>
					                                   <option value="0">否</option>
				                                       <option value="1">是</option>
				                                   </select>
				                                </div>
					                        </div>
						                     <div class="row">
					                               <label class="col-sm-1 control-label mtop">国家:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b m-b" id="nationCode">
					                                   </select>
					                               </div>
					                               <label class="col-sm-1 control-label mtop">省份:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b " id="provinceCode">
					                                   </select>
					                               </div>
					                               <label class="col-sm-1 control-label mtop">城市:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b" id="cityCode">
					                                   </select>
					                               </div>
					                              
						                       </div>
						                       
						                              
						                     <div class="row">
						                        <label class="col-sm-1 control-label mtop">房源名称:</label>
					                            <div class="col-sm-2">
				                                   <input id="houseName" name="houseName" type="text" class="form-control m-b">
				                                </div>
							                    <label class="col-sm-1 control-label mtop">最近修改时间：</label>
					                           	<div class="col-sm-2">
					                               <input id="houseCreateTimeStart"  value=""  name="houseCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label" style="text-align: center;padding:1px 1px;">到</label>
					                            <div class="col-sm-2">
					                               <input id="houseCreateTimeEnd" value="" name="houseCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
								                <div class="col-sm-1">
								                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
								                   </div>
						                	</div>
					                    </div>
					                </div>
					            </div>
					        </div>
					        <div class="ibox float-e-margins">
					            <div class="ibox-content">
					                <div class="row row-lg">
					                    <div class="col-sm-12">
					                        <div class="example-wrap">
					                            <div class="example">
					                                <table id="listTable" class="table table-bordered"  data-click-to-select="true"
								                    data-toggle="table"
								                    data-side-pagination="server"
								                    data-pagination="true"
								                    data-striped="true"
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
								                    data-single-select="true"
								                    data-height="496"
								                    data-classes="table table-hover table-condensed"
								                    data-url="house/houseMgt/showModifiedPic">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-visible="false"></th>
								                             <th data-field="houseSn" data-width="5%" data-align="center" data-formatter="formateHouseSn">房源编号</th>
								                            <th data-field="houseName" data-width="10%" data-align="center">房源名称</th>
								                            <th data-field="houseAddr" data-width="15%" data-align="center">房源地址</th>
								                            <th data-field="landlordName" data-width="5%" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="10%" data-align="center">房东手机</th>
								                            <th data-field="cameramanName" data-width="5%" data-align="center">摄影师</th>
								                            <th data-field="cameramanMobile" data-width="10%" data-align="center">摄影师手机</th>
								                            <th data-field="zoName" data-width="5%" data-align="center">运营专员</th>
								                            <th data-field="zoMobile" data-width="10%" data-align="center">运营专员手机号</th>
								                            <th data-field="lastModifyDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">最近修改时间</th>
								                        </tr>
								                    </thead>
								                    </table>             
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
					                    	<div class="row">
					                    	    <label class="col-sm-1 control-label mtop">房间编号:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="roomSn" name="roomSn"/>
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房东姓名:</label>
					                            <div class="col-sm-2">
				                                   <input id="landlordNameS" name="landlordNameS" type="text" class="form-control m-b">
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房东手机:</label>
					                            <div class="col-sm-2">
				                                   <input id="landlordMobileS" name="landlordMobileS" type="text" class="form-control m-b">
				                                </div>
						                     </div>
					                       	<div class="row">
				                                <label class="col-sm-1 control-label mtop">摄影师姓名:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanNameS" name="cameramanNameS"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">摄影师手机:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanMobileS" name="cameramanMobileS"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">是否上传照片:</label>
					                            <div class="col-sm-2">
				                                   <select class="form-control m-b" id="isPicS">
					                                   <option value="">请选择</option>
					                                   <option value="0">否</option>
				                                       <option value="1">是</option>
				                                   </select>
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">房源地址:</label>
					                            <div class="col-sm-2">
				                                   <input id="houseAddrS" name="houseAddrS" type="text" class="form-control m-b">
				                                </div>
					                        </div>
						                     <div class="row">
					                               <label class="col-sm-1 control-label mtop">国家:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b m-b" id="nationCodeS">
					                                   </select>
					                               </div>
					                               <label class="col-sm-1 control-label mtop">省份:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b " id="provinceCodeS">
					                                   </select>
					                               </div>
					                               <label class="col-sm-1 control-label mtop">城市:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b" id="cityCodeS">
					                                   </select>
					                               </div>
					                            
						                       </div>
						                       
						                        <div class="row">
						                        <label class="col-sm-1 control-label mtop">房间名称:</label>
					                            <div class="col-sm-2">
				                                   <input id="houseNameS" name="houseNameS" type="text" class="form-control m-b">
				                                </div>
                                                <label class="col-sm-1 control-label mtop">房间类型:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b m-b" id="roomType">
					                                     <option value="">请选择</option>
					                                     <option value="0">房间</option>
					                                     <option value="1">客厅</option>
					                                </select>
					                            </div>
							                    <label class="col-sm-1 control-label mtop">最近修改时间：</label>
					                           	<div class="col-sm-2">
					                               <input id="roomCreateTimeStart"  value=""  name="roomCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label" style="text-align: center;padding:1px 1px;">到</label>
					                            <div class="col-sm-2">
					                               <input id="roomCreateTimeEnd" value="" name="roomCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
				                           	      <div class="col-sm-1">
								                       <button class="btn btn-primary" type="button" onclick="queryS();"><i class="fa fa-search"></i>&nbsp;查询</button>
								                   </div>
						                	</div>
					                    </div>
					                </div>
					            </div>
					        </div>
					        <div class="ibox float-e-margins">
					            <div class="ibox-content">
					                <div class="row row-lg">
					                    <div class="col-sm-12">
					                        <div class="example-wrap">
					                            <div class="example">
					                                <table id="listTableS"  class="table table-bordered"  data-click-to-select="true"
								                    data-toggle="table"
								                    data-side-pagination="server"
								                    data-pagination="true"
								                    data-striped="true"
								                    data-page-list="[10,20,50]"
								                    data-pagination="true"
								                    data-page-size="10"
								                    data-pagination-first-text="首页"
								                    data-pagination-pre-text="上一页"
								                    data-pagination-next-text="下一页"
								                    data-pagination-last-text="末页"
								                    data-content-type="application/x-www-form-urlencoded"
								                    data-query-params="paginationParamS"
								                    data-method="post" 
								                    data-single-select="true"
								                    data-classes="table table-hover table-condensed"
								                    data-height="496"
								                    data-url="house/houseMgt/showModifiedPic">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-visible="false"></th>
								                            <th data-field="roomSn" data-width="5%" data-align="center" data-formatter="formateRoomSn">房间编号</th>
								                            <th data-field="houseName" data-width="10%" data-align="center">房间名称</th>
								                            <th data-field="roomTypeStr" data-width="100px" data-align="center" >房间类型</th>
								                            <th data-field="houseAddr" data-width="15%" data-align="center">房源地址</th>
								                            <th data-field="landlordName" data-width="5%" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="10%" data-align="center">房东手机</th>
								                            <th data-field="cameramanName" data-width="5%" data-align="center">摄影师</th>
								                            <th data-field="cameramanMobile" data-width="10%" data-align="center">摄影师手机</th>
								                            <th data-field="zoName" data-width="5%" data-align="center">运营专员</th>
								                            <th data-field="zoMobile" data-width="10%" data-align="center">专员手机号</th>
								                            <th data-field="lastModifyDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创最近修改建时间</th>
								                        </tr>
								                    </thead>
								                    </table>             
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
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	    
	    <script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>	    
	    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
		   $(function(){
			   
			 //外部js调用
		        laydate({
		            elem: '#houseCreateTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#houseCreateTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#roomCreateTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#roomCreateTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
				// 多级联动
				var areaJson = ${cityTreeList};
				var defaults = {
					geoJson	: areaJson
				};
				geoCascade(defaults);
			});
		   
	   		//整租
		    function paginationParam(params) {
	   			
		    	var houseCreateTimeStart  = $.trim($('#houseCreateTimeStart').val());
		        var houseCreateTimeEnd =$.trim($('#houseCreateTimeEnd').val());

		        if(houseCreateTimeStart!=null&&houseCreateTimeStart!=""&&houseCreateTimeStart!=undefined){
		        	houseCreateTimeStart = houseCreateTimeStart+" 00:00:00"
		        }
		        if(houseCreateTimeEnd!=null&&houseCreateTimeEnd!=""&&houseCreateTimeEnd!=undefined){
		        	houseCreateTimeEnd = houseCreateTimeEnd+" 23:59:59"
		        }
			    return {
			    	limit: params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        landlordName:$.trim($('#landlordName').val()),
			        landlordMobile:$.trim($('#landlordMobile').val()),
			        houseName:$.trim($('#houseName').val()),
			        houseStatus:$('#houseStatus option:selected').val(),
			        cameramanName:$.trim($('#cameramanName').val()),
			        cameramanMobile:$.trim($('#cameramanMobile').val()),
			        isPic:$('#isPic option:selected').val(),
			        nationCode:$('#nationCode option:selected').val(),
			        provinceCode:$('#provinceCode option:selected').val(),
			        cityCode:$('#cityCode option:selected').val(),
			        houseCreateTimeStart:houseCreateTimeStart,
			        houseCreateTimeEnd:houseCreateTimeEnd,
			        houseSn:$.trim($('#houseSn').val()),
			        rentWay:0
		    	};
			}
		    
		  	//合租
		    function paginationParamS(params) {
		  		
		    	var roomCreateTimeStart  = $.trim($('#roomCreateTimeStart').val());
		        var roomCreateTimeEnd =$.trim($('#roomCreateTimeEnd').val());

		        if(roomCreateTimeStart!=null&&roomCreateTimeStart!=""&&roomCreateTimeStart!=undefined){
		        	roomCreateTimeStart = roomCreateTimeStart+" 00:00:00"
		        }
		        if(roomCreateTimeEnd!=null&&roomCreateTimeEnd!=""&&roomCreateTimeEnd!=undefined){
		        	roomCreateTimeEnd = roomCreateTimeEnd+" 23:59:59"
		        }
			    return {
			    	limit:params.limit,
			        page:$('#listTableS').bootstrapTable('getOptions').pageNumber,
			        landlordNameS:$.trim($('#landlordNameS').val()),
			        landlordMobileS:$.trim($('#landlordMobileS').val()),
			        houseNameS:$.trim($('#houseNameS').val()),
			        houseStatusS:$('#houseStatusS option:selected').val(),
			        cameramanNameS:$.trim($('#cameramanNameS').val()),
			        cameramanMobileS:$.trim($('#cameramanMobileS').val()),
			        isPicS:$('#isPicS option:selected').val(),
			        nationCodeS:$('#nationCodeS option:selected').val(),
			        provinceCodeS:$('#provinceCodeS option:selected').val(),
			        cityCodeS:$('#cityCodeS option:selected').val(),
			        roomCreateTimeStart:roomCreateTimeStart,
			        roomCreateTimeEnd:roomCreateTimeEnd,
			        roomSn:$.trim($('#roomSn').val()),
			        rentWay:1,
			        roomType:$('#roomType option:selected').val()
		    	};
			}
		    
		    //整租
		    function query(){
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
		  
	   		//合租
		    function queryS(){
		    	$('#listTableS').bootstrapTable('selectPage', 1);
		    }
		    
		  	//房源名称
			function formateHouseSn(value, row, index){
				return "<a href='javascript:;' <customtag:authtag authUrl='house/houseMgt/auditModifiedPic'>onclick='auditHouseModifiedPic(\""+row.fid+"\");'</customtag:authtag>><span>"+value+"</span></a>";
			}
		  	
		  	//房间名称
			function formateRoomSn(value, row, index){
				return "<a href='javascript:;' <customtag:authtag authUrl='house/houseMgt/auditModifiedPic'>onclick='auditRoomModifiedPic(\""+row.fid+"\");'</customtag:authtag>><span>"+value+"</span></a>";
			}
			
			//跳转房源照片修改审核
			function auditHouseModifiedPic(houseBaseFid){
				$.openNewTab(new Date().getTime(),'house/houseMgt/auditModifiedPic?rentWay=0&houseFid='+houseBaseFid, "上架房源信息修改审核");
			}
			
			//跳转房间照片修改审核
			function auditRoomModifiedPic(houseRoomFid){
				$.openNewTab(new Date().getTime(),'house/houseMgt/auditModifiedPic?rentWay=1&houseFid='+houseRoomFid, "上架房源信息修改审核");
			}
		</script>
	</body>
</html>
