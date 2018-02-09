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
		.line-width{
			width: 270px;;
		}
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
					                        	<label class="col-sm-1 control-label mtop">房源名称:</label>
					                            <div class="col-sm-2">
				                                   <input id="houseName" name="houseName" type="text" class="form-control m-b">
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
				                                <label class="col-sm-1 control-label mtop">房源状态:</label>
					                            <div class="col-sm-2">
				                                   <select class="form-control m-b" id="houseStatus">
					                                   <option value="">请选择</option>
					                                   <c:forEach items="${houseStatusMap }" var="obj">
						                                   <option value="${obj.key }">${obj.value }</option>
					                                   </c:forEach>
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
							                    <label class="col-sm-1 control-label mtop">创建时间：</label>
					                           	<div class="col-sm-2">
					                               <input id="houseCreateTimeStart"  value=""  name="houseCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label" style="text-align: center;padding:1px 1px;">到</label>
					                            <div class="col-sm-2">
					                               <input id="houseCreateTimeEnd" value="" name="houseCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label mtop">审核说明:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="auditCause">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${causeMap }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
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
								                    data-url="house/houseMgt/showHouseMsgInfo">                    
								                    <thead>
								                        <tr>
								                        	<th data-field="fid" data-visible="false"></th>
								                        	<th data-field="houseSn" data-width="50px" data-align="center" data-formatter="formateHouseSn">房源编号</th>
								                            <th data-field="houseName" data-width="150px" data-align="center">房源名称</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="150px" data-align="center">房东手机</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
								                            <!-- <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="createDate" data-width="100px" data-align="center" data-formatter="formateDate">创建时间</th>
								                            <th data-field="houseStatus" data-width="100px" data-align="center" data-formatter="formateHouseStatus">房源状态</th>
								                        	<th data-field="auditCause" data-width="100px" data-align="center">审核说明</th>
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
					                        	<label class="col-sm-1 control-label mtop">房间名称:</label>
					                            <div class="col-sm-2">
				                                   <input id="houseNameS" name="houseNameS" type="text" class="form-control m-b">
				                                </div>
					                        </div>
					                       	<div class="row">
				                                <label class="col-sm-1 control-label mtop">摄影师姓名:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanNameS" name="cameramanNameS"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">摄影师手机:</label>	
				                                <div class="col-sm-2">
				                                    <input type="text" class="form-control" id="cameramanMobileS"/>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">是否上传照片:</label>
					                            <div class="col-sm-2">
				                                   <select class="form-control m-b" id="isPicS">
					                                   <option value="">请选择</option>
					                                   <option value="0">否</option>
				                                       <option value="1">是</option>
				                                   </select>
				                                </div>
				                                <label class="col-sm-1 control-label mtop">房间状态:</label>
					                            <div class="col-sm-2">
				                                   <select class="form-control m-b" id="houseStatusS">
					                                   <option value="">请选择</option>
					                                   <c:forEach items="${houseStatusMap }" var="obj">
						                                   <option value="${obj.key }">${obj.value }</option>
					                                   </c:forEach>
				                                   </select>
				                                </div>
					                        </div>
						                     <div class="row">
					                               <label class="col-sm-1 control-label mtop">国家:</label>
						                           <div class="col-sm-2">
					                                   <select class="form-control m-b m-b" id="nationCodeS">
						                                   <option value="">请选择</option>
						                                   <c:forEach items="${nations }" var="nation">
						                                   		<option value="${nation.code }">${nation.showName }</option>
						                                   </c:forEach>
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
							                    <label class="col-sm-1 control-label mtop">创建时间：</label>
					                           	<div class="col-sm-2">
					                               <input id="roomCreateTimeStart"  value=""  name="roomCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label" style="text-align: center;padding:1px 1px;">到</label>
					                            <div class="col-sm-2">
					                               <input id="roomCreateTimeEnd" value="" name="roomCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label mtop">审核说明:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="auditCauseS">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${causeMap }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
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
								                    data-url="house/houseMgt/showHouseMsgInfo">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-visible="false"></th>
								                            <th data-field="roomSn" data-width="50px" data-align="center" data-formatter="formateRoomSn">房间编号</th>
								                            <th data-field="houseName" data-width="150px" data-align="center">房间名称</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="150px" data-align="center">房东手机</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th>
								                            <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="createDate" data-width="100px" data-align="center" data-formatter="formateDate">创建时间</th>
								                            <th data-field="houseStatus" data-width="100px" data-align="center" data-formatter="formateHouseStatus">房源状态</th>
								                        	<th data-field="auditCause" data-width="100px" data-align="center">审核说明</th>
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
	    <script src="resources/js/content.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	    
	    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
	
	
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
		   
		    function isNullOrBlank(obj){
		    	return obj == undefined || obj == null || $.trim(obj).length == 0;
		    }
	   
	   		//整租
		    function paginationParam(params) {
		    	var houseCreateTimeStart  = $.trim($('#houseCreateTimeStart').val());
		        if(!isNullOrBlank(houseCreateTimeStart)){
		        	houseCreateTimeStart = houseCreateTimeStart+" 00:00:00";
		        }
		        
		        var houseCreateTimeEnd =$.trim($('#houseCreateTimeEnd').val());
		        if(!isNullOrBlank(houseCreateTimeEnd)){
		        	houseCreateTimeEnd = houseCreateTimeEnd+" 23:59:59";
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
			        auditCause:$('#auditCause option:selected').val(),
			        rentWay:0
		    	};
			}
		    
		  	//合租
		    function paginationParamS(params) {
		    	var roomCreateTimeStart  = $.trim($('#roomCreateTimeStart').val());
		        if(!isNullOrBlank(roomCreateTimeStart)){
		        	roomCreateTimeStart = roomCreateTimeStart+" 00:00:00";
		        }
		        
		        var roomCreateTimeEnd =$.trim($('#roomCreateTimeEnd').val());
		        if(!isNullOrBlank(roomCreateTimeEnd)){
		        	roomCreateTimeEnd = roomCreateTimeEnd+" 23:59:59";
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
			        auditCause:$('#auditCauseS option:selected').val(),
			        rentWay:1
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
				return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/auditHouseInfo'>onclick='auditHouseInfo(\""+row.fid+"\");'</customtag:authtag>>"+value+"</a>";
			}
		  	
		  	//房间名称
			function formateRoomSn(value, row, index){
				return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/auditHouseInfo'>onclick='auditRoomInfo(\""+row.fid+"\");'</customtag:authtag>>"+value+"</a>";
			}
			
			// 房源状态
			function formateHouseStatus(value, row, index){
				var houseStatusJson = ${houseStatusJson };
				var result = '';
				$.each(houseStatusJson,function(i,n){
					if(value == i){
						result = n;
						return false;
					}
				});
				return result;
			}
			
			// 格式化时间
			function formateDate(value, row, index) {
				if (value != null) {
					var vDate = new Date(value);
					return vDate.format("yyyy-MM-dd HH:mm:ss");
				} else {
					return "";
				}
			}
			
			//跳转房源信息审核
			function auditHouseInfo(houseBaseFid){
				var data={"businessType":"MSG_AUDIT","rentWay":"ENTIRE_HOUSE","fid":houseBaseFid};
	        	var binary =  function lockHouseResult(resultJson){
	            	var code=resultJson.code;
	            	var msg=resultJson.msg;
	            	if(code==1){
	            		msgLayer(msg)
	            	}else if(code==0){
	            		$.openNewTab(new Date().getTime(),'house/houseMgt/auditHouseInfo?rentWay=0&houseFid='+houseBaseFid, "管家审核-整租");
	            	}
	            }
	        	ajaxPostSubmit("house/houseMgt/judgeHouseOrRoomLock",data,binary);
			}
			
			//跳转房间信息审核
			function auditRoomInfo(houseRoomFid){
				var data={"businessType":"MSG_AUDIT","rentWay":"PART_ROOM","fid":houseRoomFid};
	        	var binary =  function lockHouseResult(resultJson){
	            	var code=resultJson.code;
	            	var msg=resultJson.msg;
	            	if(code==1){
	            		msgLayer(msg)
	            	}else if(code==0){
	            		$.openNewTab(new Date().getTime(),'house/houseMgt/auditHouseInfo?rentWay=1&houseFid='+houseRoomFid, "管家审核-分租");
	            	}
	            }
	        	ajaxPostSubmit("house/houseMgt/judgeHouseOrRoomLock",data,binary);
			}
		</script>
	</body>
</html>