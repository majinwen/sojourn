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
			width: 200px;;
		}
		/* td{
			white-space: nowrap;text-overflow: ellipsis;overflow: hidden;
		} */
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
					                            <label class="col-sm-1 control-label mtop">房源渠道:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="houseChannel">
					                                	 <option value="">请选择</option>
					                                    <c:forEach items="${houseChannel }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
					                            </div>
						                    </div>
						                    <div class="row">
					                            <!-- <label class="col-sm-1 control-label mtop">地推管家:</label>
					                            <div class="col-sm-2">
				                                    <input id="empPushName" name="empPushName" type="text" class="form-control m-b">
				                                </div> -->
					                            <label class="col-sm-1 control-label mtop">运营专员:</label>
					                            <div class="col-sm-2">
				                                    <input id="empGuardName" name="empGuardName" type="text" class="form-control m-b">
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
												<label class="col-sm-1 control-label mtop">房源品质:</label>
												<div class="col-sm-2">
													<select class="form-control m-b" id="houseQualityGradeZ">
														<option value="">请选择</option>
														<option value="TOP">塔尖</option>
														<option value="A">A类</option>
														<option value="B-">B-级</option>
											  			<option value="B">B级</option>
											  			<option value="B+">B+级</option>
														<option value="C">C类</option>
													</select>
												</div>
												<label class="col-sm-1 control-label mtop">预定类型:</label>
												<div class="col-sm-2">
													<select class="form-control m-b" id="orderTypeZ">
														<option value="">请选择</option>
														<option value="1">立即预订</option>
														<option value="2">申请预订</option>
													</select>
												</div>
						                    </div>
						                    <div class="row">
							                    <label class="col-sm-1 control-label mtop">创建时间：</label>
					                           	<div class="col-sm-2">
					                                <input id="houseCreateTimeStart"  value=""  name="houseCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label">到</label>
					                            <div class="col-sm-2">
					                                <input id="houseCreateTimeEnd" value="" name="houseCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>

					                           	<label class="col-sm-1 control-label mtop">上架时间：</label>
					                           	<div class="col-sm-2">
					                                <input id="houseOnlineTimeStart"  value=""  name="houseOnlineTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label mtop">到</label>
					                            <div class="col-sm-2">
					                                <input id="houseOnlineTimeEnd" value="" name="houseOnlineTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
					                        </div>
					                        <br/>
											<div class="row">											
												<%-- <label class="col-sm-1 control-label mtop">实勘结果:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="surveyResultZ">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${surveyResult }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
					                            </div> --%>
												<label class="col-sm-1 control-label mtop">最新发布时间：</label>
												<div class="col-sm-2">
													<input id="houseIssueLastTimeStart"  value=""  name="houseIssueLastTimeStart" class="laydate-icon form-control layer-date">
												</div>
												<label class="col-sm-1 control-label">到</label>
												<div class="col-sm-2">
													<input id="houseIssueLastTimeEnd" value="" name="houseIssueLastTimeEnd" class="laydate-icon form-control layer-date">
												</div>

					                           	<div class="col-sm-1">
								                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
								                </div>
								                <div class="col-sm-1">
								                    <customtag:authtag authUrl="house/houseMgt/insertHouseMsg"><button class="btn btn-primary" type="button" onclick="addHouse();">&nbsp;添加房源</button></customtag:authtag>
								                </div>

											</div>
					                    </div>
					                </div>
					            </div>
					        </div>	
					        <div class="ibox float-e-margins">
					            <div class="ibox-content">
					                <div class="row row-lg">
					                    <div class="col-sm-14">
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
								                    data-url="house/houseMgt/showHouseMsg">                    
								                    <thead>
								                        <tr>
								                            <th data-field="houseSn" data-width="100px" data-align="center" data-formatter="formateHouseSn">房源编号</th>
								                            <th data-field="houseFid" data-width="200px" data-align="center">房源fid</th>
								                            <th data-field="houseName" data-width="200px" data-align="center">房源名称</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
															<th data-field="houseQualityGrade" data-width="100px" data-align="center">房源品质</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
															<th data-field="orderType" data-width="100px" data-align="center" data-formatter="formateOrderType">预定类型</th>
								                            <th data-field="isPic" data-width="50px" data-align="center" data-formatter="formateIsPic">是否上传照片</th>
								                            <th data-field="cameramanName" data-width="100px" data-align="center">摄影师</th>
								                            <th data-field="cameramanMobile" data-width="100px" data-align="center">摄影师手机</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
								                            <!-- <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="createDate" data-width="200px" data-align="center" data-formatter="formateDate">创建时间</th>
								                            <!-- <th data-field="zoDate" data-width="100px" data-align="center" data-formatter="formateDate">管家审核通过日期</th> -->
								                            <th data-field="onlineDate" data-width="200px" data-align="center" data-formatter="formateDate">上架时间</th>
															<th data-field="lastDeployDate" data-width="100px" data-align="center" data-formatter="formateDate">最新发布时间</th>
								                            <th data-field="houseStatus" data-width="150px" data-align="center" data-formatter="formateHouseStatus">房源状态</th>
								                        	<th data-field="auditCause" data-width="100px" data-align="center">审核说明</th>
								                            <th data-field="houseChannel" data-width="100px" data-align="center" data-formatter="formateHouseChannel">房源渠道</th>
								                            <!-- <th data-field="surveyResult" data-width="100px" data-align="center" data-formatter="formateSurveyResult">实勘结果</th> -->
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
					                            <label class="col-sm-1 control-label mtop">房源渠道:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="houseChannelS">
					                                	 <option value="">请选择</option>
					                                    <c:forEach items="${houseChannel }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
					                            </div>
						                    </div>
						                    <div class="row">
						                    	<!-- <label class="col-sm-1 control-label mtop">地推管家:</label>
					                            <div class="col-sm-2">
				                                    <input id="empPushNameS" name="empPushNameS" type="text" class="form-control m-b">
				                                </div> -->
					                            <label class="col-sm-1 control-label mtop">运营专员:</label>
					                            <div class="col-sm-2">
				                                    <input id="empGuardNameS" name="empGuardNameS" type="text" class="form-control m-b">
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
												<label class="col-sm-1 control-label mtop">房源品质:</label>
												<div class="col-sm-2">
													<select class="form-control m-b" id="houseQualityGradeF">
														<option value="">请选择</option>
														<option value="TOP">塔尖</option>
														<option value="A">A类</option>
										                <option value="B-">B-级</option>
											  			<option value="B">B级</option>
											  			<option value="B+">B+级</option>
											  			<option value="C">C类</option>
													</select>
												</div>
												 <label class="col-sm-1 control-label mtop">房间类型:</label>
						                         <div class="col-sm-2">
					                                <select class="form-control m-b m-b" id="roomType">
					                                     <option value="">请选择</option>
					                                     <option value="0">房间</option>
					                                     <option value="1">客厅</option>
					                                </select>
					                            </div>
						                    </div>
						                    <div class="row">
							                    <label class="col-sm-1 control-label mtop">创建时间：</label>
					                           	<div class="col-sm-2">
					                                <input id="roomCreateTimeStart"  value=""  name="roomCreateTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label mtop">到</label>
					                            <div class="col-sm-2">
					                                <input id="roomCreateTimeEnd" value="" name="roomCreateTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<!-- <label class="col-sm-1 control-label mtop">管家审核通过日期：</label>
												<div class="col-sm-2">
												   <input id="zoDateStartR"  value=""  name="zoDateStartR" class="laydate-icon form-control layer-date">
												</div>
					                           	<label class="col-sm-1 control-label mtop">到</label>
												<div class="col-sm-2">
												   <input id="zoDateEndR" value="" name="zoDateEndR" class="laydate-icon form-control layer-date">
												</div> -->
					                           	<label class="col-sm-1 control-label mtop">上架时间：</label>
					                           	<div class="col-sm-2">
					                                <input id="roomOnlineTimeStart"  value=""  name="roomOnlineTimeStart" class="laydate-icon form-control layer-date">
					                           	</div>
					                           	<label class="col-sm-1 control-label mtop">到</label>
					                            <div class="col-sm-2">
					                                <input id="roomOnlineTimeEnd" value="" name="roomOnlineTimeEnd" class="laydate-icon form-control layer-date">
					                           	</div>
						                	</div>
					                        <br/>
											<div class="row">
												<%-- <label class="col-sm-1 control-label mtop">实勘结果:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="surveyResultF">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${surveyResult }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
					                            </div> --%>
												<label class="col-sm-1 control-label mtop">最新发布时间：</label>
												<div class="col-sm-2">
													<input id="roomIssueLastTimeStart"  value=""  name="roomIssueLastTimeStart" class="laydate-icon form-control layer-date">
												</div>
												<label class="col-sm-1 control-label">到</label>
												<div class="col-sm-2">
													<input id="roomIssueLastTimeEnd" value="" name="roomIssueLastTimeEnd" class="laydate-icon form-control layer-date">
												</div>
												<label class="col-sm-1 control-label mtop">预定类型:</label>
												<div class="col-sm-2">
													<select class="form-control m-b" id="orderTypeF">
														<option value="">请选择</option>
														<option value="1">立即预订</option>
														<option value="2">申请预订</option>
													</select>
												</div>
												<label class="col-sm-1 control-label mtop">是否与房东同住:</label>
												<div class="col-sm-2">
													<select class="form-control m-b" id="isTogetherLandlordF">
														<option value="">请选择</option>
														<option value="0">否</option>
														<option value="1">是</option>
													</select>
												</div>

											</div>
											<div class="row">
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
					                    <div class="col-sm-14">
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
								                    data-url="house/houseMgt/showHouseMsg">                    
								                    <thead>
								                        <tr>
								                        	<th data-field="roomSn" data-width="100px" data-align="center" data-formatter="formateRoomSn">房间编号</th>
								                        	<th data-field="roomFid" data-width="200px" data-align="center">房间fid</th>
								                        	<th data-field="houseFid" data-width="200px" data-align="center">房源fid</th>
								                            <th data-field="houseName" data-width="200px" data-align="center">房间名称</th>
								                            <th data-field="roomTypeStr" data-width="100px" data-align="center" >房间类型</th>
								                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
															<th data-field="houseQualityGrade" data-width="100px" data-align="center">房源品质</th>
								                            <th data-field="landlordName" data-width="100px" data-align="center">房东</th>
								                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
															<th data-field="orderType" data-width="100px" data-align="center" data-formatter="formateOrderType">预定类型</th>
															<th data-field="isTogetherLandlord" data-width="100px" data-align="center" data-formatter="formateIsTogetherLandlord">是否与房东同住</th>
								                            <th data-field="isPic" data-width="50px" data-align="center" data-formatter="formateIsPic">是否上传照片</th>
								                            <th data-field="cameramanName" data-width="100px" data-align="center">摄影师</th>
								                            <th data-field="cameramanMobile" data-width="100px" data-align="center">摄影师手机</th>
								                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th> -->
								                            <!-- <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
								                            <!-- <th data-field="empGuardMobile" data-width="100px" data-align="center">管家手机</th> -->
								                            <th data-field="createDate" data-width="200px" data-align="center" data-formatter="formateDate">创建时间</th>
								                            <!-- <th data-field="zoDate" data-width="100px" data-align="center" data-formatter="formateDate">管家审核通过日期</th> -->
								                            <th data-field="onlineDate" data-width="200px" data-align="center" data-formatter="formateDate">上架时间</th>
															<th data-field="lastDeployDate" data-width="100px" data-align="center" data-formatter="formateDate">最新发布时间</th>
								                            <th data-field="houseStatus" data-width="150px" data-align="center" data-formatter="formateHouseStatus">房源状态</th>
								                            <th data-field="auditCause" data-width="100px" data-align="center">审核说明</th>
								                            <th data-field="houseChannel" data-width="100px" data-align="center" data-formatter="formateHouseChannel">房源渠道</th>
								                            <!-- <th data-field="surveyResult" data-width="100px" data-align="center" data-formatter="formateSurveyResult">实勘结果</th> -->
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
	  <%--   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script> --%>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
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
		        laydate({
		            elem: '#houseOnlineTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#houseOnlineTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#roomOnlineTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#roomOnlineTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });

			   laydate({
				   elem: '#houseIssueLastTimeStart',
				   istime: true,
				   format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
				   event: 'focus'
			   });
			   laydate({
				   elem: '#houseIssueLastTimeEnd',
				   istime: true,
				   format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
				   event: 'focus'
			   });
			   laydate({
				   elem: '#roomIssueLastTimeStart',
				   istime: true,
				   format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
				   event: 'focus'
			   });
			   laydate({
				   elem: '#roomIssueLastTimeEnd',
				   istime: true,
				   format: 'YYYY-MM-DD hh:mm:ss', // 分隔符可以任意定义，该例子表示只显示年月
				   event: 'focus'
			   });


		        /* laydate({
		            elem: '#zoDateStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#zoDateEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#zoDateStartR', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        });
		        laydate({
		            elem: '#zoDateEndR', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		        }); */
		        
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
		        
		    	var houseOnlineTimeStart  = $.trim($('#houseOnlineTimeStart').val());
		        if(!isNullOrBlank(houseOnlineTimeStart)){
		        	houseOnlineTimeStart = houseOnlineTimeStart+" 00:00:00";
		        }
		        
		        var houseOnlineTimeEnd = $.trim($('#houseOnlineTimeEnd').val());
		        if(!isNullOrBlank(houseOnlineTimeEnd)){
		        	houseOnlineTimeEnd = houseOnlineTimeEnd+" 23:59:59"
		        }
		        
		        /* var zoDateStart  = $.trim($('#zoDateStart').val());
		        if(!isNullOrBlank(zoDateStart)){
		        	zoDateStart = zoDateStart+" 00:00:00";
		        }
		        
		        var zoDateEnd =$.trim($('#zoDateEnd').val());
		        if(!isNullOrBlank(zoDateEnd)){
		        	zoDateEnd = zoDateEnd+" 23:59:59";
		        } */
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
			        houseOnlineTimeStart:houseOnlineTimeStart,
			        houseOnlineTimeEnd:houseOnlineTimeEnd,
			        auditCause:$('#auditCause option:selected').val(),
			        empPushName:$.trim($('#empPushName').val()),
			        empGuardName:$.trim($('#empGuardName').val()),
			        rentWay:0,
					houseQualityGrade:$.trim($("#houseQualityGradeZ").val()),
                    orderType:$.trim($("#orderTypeZ").val()),
					/* zoDateStart:zoDateStart,
					zoDateEnd:zoDateEnd, */
			        houseChannel:$('#houseChannel option:selected').val(),
			        surveyResult:$('#surveyResultZ option:selected').val(),
					issueLastTimeStart:$.trim($('#houseIssueLastTimeStart').val()),
					issueLastTimeEnd:$.trim($('#houseIssueLastTimeEnd').val())
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

		        var roomOnlineTimeStart = $.trim($('#roomOnlineTimeStart').val());
		        if(!isNullOrBlank(roomOnlineTimeStart)){
		        	roomOnlineTimeStart = roomOnlineTimeStart+" 00:00:00";
		        }

		        var roomOnlineTimeEnd = $.trim($('#roomOnlineTimeEnd').val());
		        if(!isNullOrBlank(roomOnlineTimeEnd)){
		        	roomOnlineTimeEnd = roomOnlineTimeEnd+" 23:59:59";
		        }

		        /* var zoDateStart  = $.trim($('#zoDateStartR').val());
		        if(!isNullOrBlank(zoDateStart)){
		        	zoDateStart = zoDateStart+" 00:00:00";
		        }
		        
		        var zoDateEnd =$.trim($('#zoDateEndR').val());
		        if(!isNullOrBlank(zoDateEnd)){
		        	zoDateEnd = zoDateEnd+" 23:59:59";
		        } */
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
			        roomOnlineTimeStart:roomOnlineTimeStart,
			        roomOnlineTimeEnd:roomOnlineTimeEnd,
			        auditCause:$('#auditCauseS option:selected').val(),
			        empPushName:$.trim($('#empPushNameS').val()),
			        empGuardName:$.trim($('#empGuardNameS').val()),
			        rentWay:1,
					houseQualityGrade:$.trim($("#houseQualityGradeF").val()),
					/* zoDateStart:zoDateStart,
					zoDateEnd:zoDateEnd, */
			        houseChannel:$('#houseChannelS option:selected').val(),
			        surveyResult:$('#surveyResultF option:selected').val(),
					issueLastTimeStart:$.trim($('#roomIssueLastTimeStart').val()),
					issueLastTimeEnd:$.trim($('#roomIssueLastTimeEnd').val()),
                    orderType:$.trim($("#orderTypeF").val()),
                    isTogetherLandlord:$.trim($("#isTogetherLandlordF").val()),
					roomType:$('#roomType option:selected').val()

		    	};
			}
		    
		  	//整租
		    function query(){
				console.log($("#houseQualityGradeZ").val());
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
		  	
	   		//合租
		    function queryS(){
		    	$('#listTableS').bootstrapTable('selectPage', 1);
		    }
		   
			// 房源编号
			function formateHouseSn(value, row, index){
				 return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showHouseDetail(\""+row.houseFid+"\");'</customtag:authtag>>"+value+"</a>";
			}
			
			// 房间编号
			function formateRoomSn(value, row, index){
				return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showRoomDetail(\""+row.roomFid+"\");'</customtag:authtag>>"+value+"</a>";
			}
			
			// 是否上传照片
			function formateIsPic(value, row, index){
				if(value == '0'){
					return "否";
				}else{
					return "是";
				}
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
			
			//格式化房源渠道
			function formateHouseChannel(value, row, index){
				if(value == '3'){
					return "地推";
				}else if(value == '1'){
					return "直营";
				}else if(value == '2'){
					return "房东";
				}else{
					return "";
				}
			}
			
			//格式化实勘结果
			function formateSurveyResult(value, row, index){
				if(value == '0'){
					return "未实勘";
				}else if(value == '1'){
					return "符合品质";
				}else if(value == '2'){
					return "不符品质";
				}else{
					return "";
				}
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
			
			//展示房源详情
			function showHouseDetail(houseBaseFid){
				$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=0&houseFid='+houseBaseFid, "房源详情");
			}
			
			//展示房间详情
			function showRoomDetail(houseRoomFid){
				$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=1&houseFid='+houseRoomFid, "房间详情");
			}
			
			//添加房源
			function addHouse(){
				$.openNewTab("addHouse","house/houseMgt/insertHouseMsg", "添加房源");
			}

			//格式化预定类型
           function formateOrderType(value, row, index){
               if(value == '1'){
                   return "立即预定";
               }else if(value == '2'){
                   return "申请预订";
               }
           }

           // 是否上传照片
           function formateIsTogetherLandlord(value, row, index){
               if(value == '0'){
                   return "否";
               }else{
                   return "是";
               }
           }

		</script>
	</body>
</html>
