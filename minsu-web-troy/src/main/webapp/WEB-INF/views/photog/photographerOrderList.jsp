<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
			<div class="row">
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<form class="form-horizontal">
								<div class="form-group">
									<div class="row">
										<label class="col-sm-1 control-label">房源编号:</label>
										<div class="col-sm-2">
											<input id="houseSn" name="houseSn" type="text" class="form-control"/>
										</div>
										<label class="col-sm-1 control-label ">房源名称:</label>
										<div class="col-sm-2">
											<input id="houseName" name="houseName" type="text" class="form-control m-b">
										</div>
										<label class="col-sm-1 control-label ">房东姓名:</label>
										<div class="col-sm-2">
											<input id="customerName" name="customerName" type="text"
												   class="form-control m-b">
										</div>
										<label class="col-sm-1 control-label ">房东手机:</label>
										<div class="col-sm-2">
											<input id="customerMobile" name="customerMobile" type="text"
												   class="form-control m-b">
										</div>
									</div>
									<div class="row">
										<label class="col-sm-1 control-label">摄影师姓名:</label>
										<div class="col-sm-2">
											<input id="photographerName" name="photographerName" type="text" class="form-control"/>
										</div>
										<label class="col-sm-1 control-label ">摄影师手机:</label>
										<div class="col-sm-2">
											<input id="photographerMobile" name="photographerMobile" type="text" class="form-control m-b">
										</div>
										<label class="col-sm-1 control-label ">预约人姓名:</label>
										<div class="col-sm-2">
											<input id="bookerName" name="bookerName" type="text"
												   class="form-control m-b">
										</div>
										<label class="col-sm-1 control-label ">预约人手机:</label>
										<div class="col-sm-2">
											<input id="bookerMobile" name="bookerMobile" type="text"
												   class="form-control m-b">
										</div>
									</div>

									<div class="row">
										<label class="col-sm-1 control-label">创建时间:</label>
										<div class="col-sm-2">
											<input id="bookCreateStartTime" name="bookCreateStartTime" type="text" class="laydate-icon form-control layer-date"/>
										</div>
										<label class="col-sm-1 control-label ">到</label>
										<div class="col-sm-2 ">
											<input id="bookCreateEndTime" value="" name="bookCreateEndTime" class="laydate-icon form-control layer-date"/>
										</div>

										<label class="col-sm-1 control-label ">城市:</label>
										<div class="col-sm-2">
											<select class="form-control m-b" id="cityCodeForSearch">
												<option value="">请选择</option>
												<c:forEach items="${cityList }" var="obj">
													<option value="${obj.code }">${obj.name }</option>
												</c:forEach>
											</select>
										</div>

										<label class="col-sm-1 control-label ">预约状态:</label>
										<div class="col-sm-2 ">
											<select class="form-control m-b" id="bookOrderStatu">
												<option value="">请选择</option>
												<c:forEach items="${bookOrderStatusMap }" var="obj">
													<option value="${obj.key }">${obj.value }</option>
												</c:forEach>
											</select>
										</div>


									</div>

									<div class="row">
										<label class="col-sm-1 control-label ">预约时间:</label>
										<div class="col-sm-2">
											<input type="text" class="laydate-icon form-control layer-date" id="bookStartTime"
												   name="bookStartTime"/>
										</div>
										<label class="col-sm-1 control-label">到</label>
										<div class="col-sm-2">
											<input type="text" class="laydate-icon form-control layer-date" id="bookEndTime"
												   name="bookEndTime"/>
										</div>
										<label class="col-sm-1 control-label ">上门时间:</label>
										<div class="col-sm-2">
											<input id="doorHomeStartTime" name="doorHomeStartTime" type="text" class="laydate-icon form-control layer-date"/>
										</div>
										<label class="col-sm-1 control-label ">到:</label>
										<div class="col-sm-2">
											<input id="doorHomeEndTime" name="doorHomeEndTime" type="text" class="laydate-icon form-control layer-date"/>
										</div>
									</div>

                                    <div class="row" style="margin-top: 15px;">
                                        <label class="col-sm-1 control-label ">预约编号:</label>
                                        <div class="col-sm-2">
                                            <input type="text" class="form-control" id="bookOrderSn"
                                                   name="bookOrderSn"/>
                                        </div>

                                        <div class="col-sm-1">
                                            <button class="btn btn-primary" type="button" onclick="query();"><i
                                                    class="fa fa-search"></i>&nbsp;查询
                                            </button>
                                        </div>
                                    </div>
								</div>
							</form>
						</div>
					</div>
			</div>
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="row">
						<button class="btn btn-primary" type="button" onclick="findLog();"><i class="fa "></i>&nbsp;查看日志</button>
							<div class="example-wrap">
								<div class="example">
									<table id="listTable" style="table-layout:fixed" class="table table-bordered table-condensed table-hover table-striped"  data-click-to-select="true"
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
									data-url="photog/findPhotographerOrderList">
									<thead>
										<tr>
											<th data-field="id" data-radio="true"></th>
											<th data-field="houseFid" data-visible="false"></th>
											<th data-field="bookOrderSn" data-width="200px" data-align="center">预约编号</th>
											<th data-field="cityCode" data-width="130px" data-align="center" data-formatter="formateCityCode">城市</th>
											<th data-field="houseName" data-width="150px" data-align="center" data-cell-style="cellStyle">房源名称</th>
											<th data-field="houseSn" data-width="150px" data-align="center" data-formatter="handHouseSn">房源编号</th>
											<th data-field="shotAddr" data-width="250px" data-align="center" data-cell-style="cellStyle">房源地址</th>
											<th data-field="customerName" data-width="150px" data-align="center">房东姓名</th>
											<th data-field="customerMobile" data-width="150px" data-align="center">房东手机</th>
											<th data-field="photographerName" data-width="130px" data-align="center">摄影师姓名</th>
											<th data-field="photographerMobile" data-width="150px" data-align="center">摄影师手机</th>
											<th data-field="appointPhotogDate" data-width="150px" data-align="center" data-formatter="formateDate" >指定摄影师时间</th>
											<th data-field="bookStartTime" data-width="150px" data-align="center" data-formatter="formateDate">预约开始时间</th>
											<th data-field="doorHomeTime" data-width="150px" data-align="center" data-formatter="formateDate">实际上门时间</th>
											<th data-field="receivePictureTime" data-width="150px" data-align="center" data-formatter="formateDate">收图时间</th>
											<th data-field="bookEndTime" data-width="150px" data-align="center" data-formatter="formateDate">预约结束时间</th>
											<th data-field="bookOrderStatu" data-width="300px" data-align="center" data-formatter="formateOrderStatus">状态</th>
											<th data-field="bookOrderRemark" data-width="200px" data-align="center" data-cell-style="cellStyle">备注</th>
											<th data-field="bookOrderStatu" data-width="300px" data-align="center" data-formatter="formateOperater">操作</th>
										</tr>
									</thead>
									</table>
								</div>
							</div>
					</div>
				</div>
			</div>

	   </div>

	   <!-- 上传图片分租  弹出框 -->
	   <div class="modal inmodal" id="uploadModal" tabindex="-1" role="dialog" aria-hidden="true">
		   <div class="modal-dialog" style="width:50%">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
					   <h4 class="modal-title">选择房间上传图片</h4>
					   <input id="houseBaseFid" type="hidden" value="">
				   </div>
				   <div class="modal-body">
					   <input type="hidden" id="roomCameramanName">
					   <input type="hidden" id="roomCameramanMobile">
					<div class="row">
                         <label class="col-sm-2 control-label mtop">选择要上传图片的房间:</label>
                         <div class="col-sm-4 mtop">
                             <select class="form-control m-b" id="roomSelect">

                             </select>
                          </div>
				     </div>
				   </div>
				   <div class="modal-footer">
					   <button type="button" id="uploadPic" class="btn btn-primary">上传</button>
				   </div>
			   </div>
		   </div>
	   </div>
	   	<!-- 添加 添加摄影师  弹出框 -->
	<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" style="width:50%">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">指派摄影师</h4>
	          <input id="orderID" type="hidden" >
	       </div>
	       <div class="modal-body">
		         	<div class="wrapper wrapper-content animated fadeInRight">
						<div class="row">
							<div class="col-sm-12">
								<div class="ibox float-e-margins">
									<div class="ibox-content">
										<div class="row" >
											<div class="form-group">
												<div class="row" style="margin-left: 10px;">
													<label class="col-sm-2 control-label mtop">摄影师姓名:</label>
													<div class="col-sm-3">
														<input id="realName" name="realName" type="text" class="form-control">
													</div>
													<label class="col-sm-2 control-label mtop">摄影师电话:</label>
													<div class="col-sm-3">
														<input id="mobile" name="mobile" type="text" class="form-control">
													</div>
												</div>
												<div class="row" style="margin-left: 10px;">
													<label class="col-sm-2 control-label mtop">上门时间：</label>
													<div class="col-sm-3 mtop" >
														<input id="doorHomeTime"  value=""  name="doorHomeTime" class="laydate-icon form-control layer-date">
													</div>
													<label class="col-sm-2 control-label mtop">城市:</label>
													<div class="col-sm-3">
														<select class="form-control m-b" id="cityCode">
						                                    <option value="">请选择</option>
						                                    <c:forEach items="${cityList }" var="obj">
							                                    <option value="${obj.code }">${obj.name }</option>
						                                    </c:forEach>
					                                    </select>
													</div>
													<div class="col-sm-1">
														<button class="btn btn-primary" type="button" onclick="queryS();">
															<i class="fa fa-search"></i>&nbsp;查询
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Panel Other -->
						<div class="ibox float-e-margins">
							<div class="ibox-content">
								<div class="row row-lg">
									<div class="col-sm-12"> 
										<div class="example-wrap">
											<div class="example">
												<table id="listTableS" class="table table-bordered"  
												data-click-to-select="true"
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
							                    data-height="496"
							                    data-classes="table table-hover table-condensed"
							                    data-url="photog/showPhotog">                    
							                    <thead>
							                        <tr>
							                        	<th data-field="photographerUid" data-visible="false"></th>
							                        	<th data-field="id" data-width="2%" data-radio="true"></th>
							                            <th data-field="realName" data-width="14%" data-align="center">姓名</th>
							                            <th data-field="nickName" data-width="14%" data-align="center">昵称</th>
							                            <th data-field="mobile" data-width="14%" data-align="center">手机</th>
							                            <th data-field="cityName" data-width="14%" data-align="center">城市名称</th>
							                            <th data-field="photographerStartTime" data-width="14%" data-align="center" data-formatter="formateDate">参加摄影工作时间</th>
							                            <th data-field="photographerGrade" data-width="14%" data-align="center">摄影师等级</th>
							                            <th data-field="photographerSource" data-width="14%" data-align="center" data-formatter="formateSource">摄影师</th>
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
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	         <button type="button" id="addPhotog" class="btn btn-primary">派单</button>
	         <input type="hidden" id="updateType" > 
	      </div>
	     </div>
	   </div>
	</div>

	   <!-- 详情-->
	   <div class="modal inmodal" id="detailModal" tabindex="-1"  role="dialog" aria-hidden="true">
		   <div class="modal-dialog">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
					   <h5 class="modal-title">预约单详情</h5>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-20">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row" style="margin-left: 10px;">
											   <label class="col-sm-3 control-label">预约单号:</label>
											   <div class="col-sm-6">
												   <label id="bookSn">预约单号:</label>
											   </div>
										   </div>
										   <div class="row" style="margin-left: 10px;">
										   		 <label class="col-sm-3 control-label">房源名称:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="houseBaseName"></label>
											   </div>
											</div>
                                           <div class="row" style="margin-left: 10px;">
                                               <label class="col-sm-3 control-label">房源编码:</label>
                                               <div class="col-sm-6">
                                                   <label class="lb_detail" id="houseBaseSn"></label>
                                               </div>
                                           </div>
										   <div class="row" style="margin-left: 10px;">
											 <label class="col-sm-3 control-label">房东姓名：</label>
											   <div class="col-sm-6" >
												   <label class="lb_detail" id="landlordName"></label>
											   </div>
											</div>

										    <div class="row" style="margin-left: 10px;">
										    	<label class="col-sm-3 control-label">房源地址:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="houseAddr"></label>
											   </div>
											 </div>  
										   <div class="row" style="margin-left: 10px;">
										   		<label class="col-sm-3 control-label">房东手机:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="landlordMobile"></label>
											   </div>
											   
											</div>
											<div class="row" style="margin-left: 10px;">
												<label class="col-sm-3 control-label">预约时间:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="startTime"></label>
											   </div>
											</div>
										   <div class="row" style="margin-left: 10px;">
											   <label class="col-sm-3 control-label">备注:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="bookOrderRemark"></label>
											   </div>
										   </div>
									   </div>
								   </div>

								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row" style="margin-left: 10px;">
											   <label class="col-sm-3 control-label">摄影师名称:</label>
											   <div class="col-sm-6">
												   <label class="lb_detail" id="grapherName"></label>
											   </div>
											 </div>
											 <div class="row" style="margin-left: 10px;">
												   <label class="col-sm-3 control-label">摄影师手机:</label>
												   <div class="col-sm-6">
													   <label class="lb_detail" id="grapherMobile"></label>
												   </div>
												  </div>
											  <div class="row" style="margin-left: 10px;">
												   <label class="col-sm-3 control-label">实际上门时间:</label>
												   <div class="col-sm-6">
													   <label class="lb_detail" id="homeTime"></label>
												   </div>
											 </div>
											 <div class="row" style="margin-left: 10px;">
												   <label class="col-sm-3 control-label">收图时间:</label>
												   <div class="col-sm-6">
													   <label class="lb_detail" id="receiveTime"></label>
												   </div>
											 </div>
											   <div class="row" style="margin-left: 10px;">
												   <label class="col-sm-3 control-label">创建时间：</label>
												   <div class="col-sm-6" >
													   <label class="lb_detail" id="createDate"></label>
												   </div>
											   </div>
											   <div class="row" style="margin-left: 10px;">
												   <label class="col-sm-3 control-label">单据状态:</label>
												   <div class="col-sm-6">
													   <label class="lb_detail" id="bookStatu"></label>
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

	   <!-- 日志-->
	   <div class="modal inmodal" id="logModal" tabindex="-1"  role="dialog" aria-hidden="true">
		   <div class="modal-dialog">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
					   <h4 class="modal-title">预约单日志</h4>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-20">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row row-lg">
											   <div class="col-sm-12">
												   <div class="example-wrap">
													   <div class="example">
														   <h2 class="title-font">预约单操作日志</h2>
														   <table class="table table-bordered" id="log">
															   <thead>
															   <tr>
																   <th width="20%">操作时间</th>
																   <th width="20%">前状态</th>
																   <th width="20%">变更为</th>
																   <th width="40%">操作人</th>
															   </tr>
															   </thead>
															   <tbody id="logTable">
																   <%--<tr>
																	   <td><fmt:formatDate type="both" value="${log.createDate}" /></td>
																	   <td >${log.fromStatus}</td>
																	   <td >${log.toStatus}</td>
																	   <td >${log.mark}</td>
																   </tr>--%>
															   </tbody>
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
					   </div>
				   </div>
				   <div class="modal-footer" style="text-align: center;">
					   <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					   <%--<button type="button" class="btn btn-primary" onclick="javascript:cancelSaveReason();">保存</button>--%>
				   </div>
			   </div>
		   </div>
	   </div>

	   <!-- 收图-->
	   <div class="modal inmodal" id="receiveModal" tabindex="-1"  role="dialog" aria-hidden="true">
		   <div class="modal-dialog">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
					   <h4 class="modal-title">确认收图</h4>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-14">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row" >
											   <div class="form-group">
												   <div class="row" style="margin-left: 10px;">
													   <label class="col-sm-3 control-label mtop">收图时间：</label>
													   <div class="col-sm-5 mtop" >
														   <input id="receivePhotoTimeStr"  value=""  name="receivePhotoTimeStr" class="laydate-icon form-control layer-date">
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
				   <div class="modal-footer" style="text-align: center;">
					   <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					   <button type="button" class="btn btn-primary" id="receiveButton">保存</button>
				   </div>
			   </div>
		   </div>
	   </div>

	   <!-- 作废-->
	   <div class="modal inmodal" id="cancelModal" tabindex="-1"  role="dialog" aria-hidden="true">
		   <div class="modal-dialog">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
					   <h4 class="modal-title">申请预约</h4>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-14">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row" >
											   <div class="form-group">
												   <div class="row" style="margin-left: 10px;">
													   <label class="col-sm-2 control-label mtop">作废原因：</label>
													   <div class="col-sm-4 mtop" >
														   <select class="form-control m-b" id="cancelSelect">
															   <option value="0">请选择</option>
															   <option value="14">上门未拍摄</option>
															   <option value="15">未上门未拍摄</option>
															   <option value="13">不再拍摄</option>
														   </select>
													   </div>
												   </div>
												   <div class="row" style="margin-left: 10px;">
													   <label class="col-sm-2 control-label mtop">备注：</label>
													   <div class="col-sm-4 mtop" >
														   <input id="cancelRemark"  value=""  name="cancelRemark" type="text" class="form-control">
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
				   <div class="modal-footer" style="text-align: center;">
					   <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					   <button type="button" class="btn btn-primary" id="cancelButton">保存</button>
				   </div>
			   </div>
		   </div>
	   </div>

	   <div class="modal inmodal" id="roomsModal" tabindex="-1"  role="dialog" aria-hidden="true">
		   <div class="modal-dialog">
			   <div class="modal-content animated bounceInRight">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal">
						   <span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-14">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <div class="row" >
											   <div class="form-group">
												   <table class="table table-bordered">
													   <thead>
														   <tr>
															   <th>房间编码</th>
															   <th>房间名称</th>
														   </tr>
													   </thead>
													   <tbody id="roomListTd">

													   </tbody>
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
				//初始化时间方法
		   		function initDate(id){
					laydate({
						elem: '#'+id,
						istime: true,
						format: 'YYYY-MM-DD hh:mm:ss'
					});
				}

				initDate("bookCreateStartTime");
				initDate("bookCreateEndTime");
				initDate("doorHomeTime");
				initDate("receivePhotoTimeStr");
				initDate("bookStartTime");
				initDate("bookEndTime");
                initDate("doorHomeStartTime");
                initDate("doorHomeEndTime");
				
				//参数
			    function paginationParam(params) {
				    return {
				        limit: params.limit,
				        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
				        houseSn:$.trim($('#houseSn').val()),
				        houseName:$.trim($('#houseName').val()),
				        customerName:$.trim($('#customerName').val()),
				        customerMobile:$.trim($('#customerMobile').val()),
				        photographerName:$.trim($('#photographerName').val()),
				        photographerMobile:$.trim($('#photographerMobile').val()),
				        bookerName:$.trim($('#bookerName').val()),
				        bookerMobile:$.trim($('#bookerMobile').val()),
				        bookOrderStatu:$('#bookOrderStatu option:selected').val(),
						bookCreateStartTimeStr:$.trim($('#bookCreateStartTime').val()),
						bookCreateEndTimeStr:$.trim($('#bookCreateEndTime').val()),
				        bookStartTimeStr:$.trim($('#bookStartTime').val()),
				        bookEndTimeStr:$.trim($('#bookEndTime').val()),
                        doorHomeStartTimeStr:$.trim($('#doorHomeStartTime').val()),
                        doorHomeEndTimeStr:$.trim($('#doorHomeEndTime').val()),
				        cityCode:$('#cityCodeForSearch option:selected').val(),
				        bookOrderSn:$.trim($('#bookOrderSn').val())
			    	};
				}

				// 格式化城市code
				function formateCityCode(value, row, index){
					var cityName = '';
					$.ajax({
						type: "POST",
						url: "cityFile/findCityNameByCode",
						dataType:"json",
						async:false,
						data: {"cityCode":value},
						success: function (result) {
							//console.log(result);
							if(result.code==0){
								cityName = result.data.cityName;
							}else{
								cityName = '';
							}
						},
						error: function(result) {
							cityName = '';
						}
					});
					return cityName;
					 //return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showHouseDetail(\""+row.houseFid+"\");'</customtag:authtag>>"+value+"</a>";
				}


				function handHouseSn(value, row, index) {
					//console.log(value,row,index);
					//分租重新处理
					if (row.rentWay == 1){
						return "<a href='javascript:void(0);' onclick='showHouseRooms(\""+row.houseFid+"\")'>"+value+"</a>";
					}else{
						return value;
					}
				}

				//展示分租房间信息
				function showHouseRooms(houseFid) {
					$("#roomListTd").empty();
					$('#roomsModal').modal('toggle');
					$.getJSON("photog/listRooms",{houseFid:houseFid},function (data) {
						if (data.code == 0){
							var list = data.data.list;
							var html = "";
							$.each(list,function (index, n) {
								var _td = "<tr><td>"+n.roomSn+"</td><td>"+n.roomName+"</td></tr>";
								html += _td;
							});
							$("#roomListTd").append(html);
						}else{
							layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
						}
					});


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
				
				//格式化预约状态
				function formateOrderStatus(value, row, index){
					if(value == '10'){
						return "预约中";
					}else if(value == '11'){
						return "已派单";
					}else if(value == '110'){
						return "已收图";
					}else if(value == '12'){
						return "完成";
					}else if(value == '13'){
						return "作废";
					}else if(value == '14'){
						return "上门未拍摄";
					}else if(value == '15'){
						return "未上门未拍摄";
					}else{
						return "";
					}
				}
				
				//格式化 操作
				function formateOperater(value, row, index){
				   //10=预约中 11=预约成功 110=收图 12=完成 13=作废
				  
					if(value == '10'){
						return '<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#addModal" onclick="clrModifyVal();" ><i class="fa"></i>&nbsp;派单</button>'
								+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#cancelModal" onclick="cancel();" ><i class="fa"></i>&nbsp;作废</button>'
								+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#detailModal" onclick="detail(\''+row.bookOrderSn+'\');" ><i class="fa"></i>&nbsp;详情</button>';
					}else if(value == '11'){
						return '&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#receiveModal" onclick="receivePhoto(this);" ><i class="fa"></i>&nbsp;收图</button>'
								+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#addModal" onclick="modifyVal();" ><i class="fa"></i>&nbsp;重派</button>'
								+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#cancelModal" onclick="cancel();" ><i class="fa"></i>&nbsp;作废</button>'
								+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#detailModal" onclick="detail(\''+row.bookOrderSn+'\');" ><i class="fa"></i>&nbsp;详情</button>';
						/*return '<button class="btn btn-primary" type="button" onclick="assignPhotographerComplate(this);" ><i class="fa"></i>&nbsp;完成</button>'
							+'&nbsp;&nbsp;<button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal" onclick="modifyVal();" ><i class="fa"></i>&nbsp;重新指定</button>';*/
					}else if(value == '110') {
						return '<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#receiveModal" onclick="receivePhoto(this);" ><i class="fa"></i>&nbsp;收图</button>'
						+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" onclick="uploadPhoto();" ><i class="fa"></i>&nbsp;上传</button>'
						+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" onclick="assignPhotographerComplate(this);" ><i class="fa"></i>&nbsp;完成</button>'
						+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#cancelModal" onclick="cancel();" ><i class="fa"></i>&nbsp;作废</button>'
						+'&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#detailModal" onclick="detail(\''+row.bookOrderSn+'\');" ><i class="fa"></i>&nbsp;详情</button>';
					}else if(value == '12'){
						return '&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#detailModal" onclick="detail(\''+row.bookOrderSn+'\');" ><i class="fa"></i>&nbsp;详情</button>';
					}else{
						return '&nbsp;&nbsp;<button class="btn btn-sm btn-primary" type="button" data-toggle="modal" data-target="#detailModal" onclick="detail(\''+row.bookOrderSn+'\');" ><i class="fa"></i>&nbsp;详情</button>';
					}
				}
				//处理长文本
				function cellStyle(value, row, index, field) {
					return {
						css: {"overflow":"hidden","white-space":"nowrap","text-overflow":"ellipsis"}
					};
				}

				//重新拍摄类型 1,"正常修改" 2,"指定摄影师" 3,"摄影拍摄完成" 4,"重新指定摄影师" 5,"作废摄影预约单" 6,"收图操作标志"
				function modifyVal(){
					$("#updateType").val("4");
					$("#doorHomeTime").val("");
				}
				//指定摄影师
				function clrModifyVal(){
					$("#updateType").val("2");
					$("#doorHomeTime").val("");
				}
				//作废摄影单类型
				function cancel(){
					$("#updateType").val("5");
					$("#cancelRemark").val("");
					$("#cancelSelect option:first").prop("selected", 'selected');
				}
				//收图操作
				function receivePhoto(){
					$("#updateType").val("6");
					$("#receivePhotoTimeStr").val("");
				}

				//查看日志
				function findLog(){
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					//清空上一次日志记录
					$("#log tbody").html("");
					if (orderRows.length == 0) {
						layer.alert("请重新选择要收图的订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					var bookOrderSn = orderRows[0].bookOrderSn;
					$.ajax({
						type: "post",
						url: "photog/findLogsByBookOrderSn",
						dataType:"json",
						data: {
							"bookOrderSn" : bookOrderSn
						},
						success: function (result) {
							if(result.code == 0){
								var logs = result.data.logs;
								if(isNullOrBlank(logs)){
									layer.alert("没有操作日志", {icon: 5,time: 2000, title:'提示'});
								}else{
									for(var i=0;i<logs.length;i++){
										var fromStat = '-';
										if(logs[i].fromStatu =="10"){
											fromStat ="预约中";
										}else if(logs[i].fromStatu =="11"){
											fromStat ="已派单";
										}else if(logs[i].fromStatu =="110"){
											fromStat ="收图";
										}else if(logs[i].fromStatu =="12"){
											fromStat ="完成";
										}else if(logs[i].fromStatu =="13"){
											fromStat ="作废";
										}else if(logs[i].fromStatu =="14"){
											fromStat ="上门未拍摄";
										}else if(logs[i].fromStatu =="15"){
											fromStat ="未上门未拍摄";
										}
										var toStat = '-';
										if(logs[i].toStatu =="10"){
											toStat ="预约中";
										}else if(logs[i].toStatu =="11"){
											toStat ="已派单";
										}else if(logs[i].toStatu =="110"){
											toStat ="收图";
										}else if(logs[i].toStatu =="12"){
											toStat ="完成";
										}else if(logs[i].toStatu =="13"){
											toStat ="作废";
										}else if(logs[i].toStatu =="14"){
											toStat ="上门未拍摄";
										}else if(logs[i].toStatu =="15"){
											toStat ="未上门未拍摄";
										}
										var createName = logs[i].createName;
										if(isNullOrBlank(createName)){
											createName = '-';
										}
										$("#logTable").append("<tr>" +
												"<td>" + logs[i].createDateStr+"</td>"+
												"<td >"+ fromStat +"</td>" +
												"<td >" + toStat + "</td>" +
												"<td >"+ createName+"</td> "+
												+"</tr>");
									}
									$("#logModal").modal('show');
								}
								
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error: function(result) {
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
				
				//预约摄影单详情
				function detail(bookOrderSn){
					$(".lb_detail").text("");
					$.ajax({
						type: "post",
						url: "photog/findPhotoOrder",
						async:false,
						dataType:"json",
						data: {
							"bookOrderSn" : bookOrderSn
						},
						success: function (result) {
							if(result.code == 0){
								//赋值
								var bookOrder = result.data.bookOrder;
								$("#bookSn").text(bookOrder.bookOrderSn);
								$("#houseBaseName").text(bookOrder.houseName);
                                $("#houseBaseSn").text(bookOrder.houseSn);
								$("#landlordName").text(bookOrder.bookerName);
								$("#createDate").text(result.data.createTime);

								var value = bookOrder.bookOrderStatu;
								if(value == '10'){
									value = "预约中";
								}else if(value == '11'){
									value = "预约成功";
								}else if(value == '110'){
									value = "已收图";
								}else if(value == '12'){
									value = "完成";
								}else if(value == '13'){
									value = "作废";
								}else if(value == '14'){
									value = "上门未拍摄";
								}else if(value == '15'){
									value = "未上门未拍摄";
								}else {
									value = "暂无";
								}
								$("#bookStatu").text(value);
								$("#houseAddr").text(bookOrder.shotAddr);
								$("#landlordMobile").text(bookOrder.bookerMobile);
								$("#startTime").text(bookOrder.startTime);
								
								$("#grapherName").text(bookOrder.photographerName);
								$("#grapherMobile").text(bookOrder.photographerMobile);
								$("#homeTime").text(bookOrder.homeTime);
								$("#receiveTime").text(bookOrder.receiveTime);
								$("#bookOrderRemark").text(bookOrder.bookOrderRemark);

							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error: function(result) {
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});

				}
				
				function getLocalTime(nS) {     
				    return new Date(parseInt(nS) * 1000).toLocaleString().substr(0,17)}  
				//分租情况选择不同房间上传照片
				$("#uploadPic").click(function(){
					//获取选中行
					var roomFid = $("#roomSelect option:selected").val();
					if(isNullOrBlank(roomFid)){
						layer.alert("请重新选择要上传的房间", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					var cameramanName = $("#roomCameramanName").val();
					var cameramanMobile = $("#roomCameramanMobile").val();
					//跳转分租上传照片页面
					$.openNewTab(new Date().getTime(),encodeURI('house/houseMgt/housePicAuditDetail?operateType=2&cameramanName='+cameramanName+'&cameramanMobile='+cameramanMobile+'&houseRoomFid='+roomFid), "上传房源照片");
					$('#uploadModal').modal('hide');
				})

				//上传图片
				function uploadPhoto(){
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					$(this).attr("disable", "disable");
					if (orderRows.length == 0) {
						layer.alert("请重新选择要上传图片的订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					var houseFid = orderRows[0].houseFid;
					$("#houseBaseFid").val(houseFid);
					if(isNullOrBlank(houseFid)){
						layer.alert("请重新选择要上传图片的订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					var cameramanName = orderRows[0].photographerName;
					var cameramanMobile = orderRows[0].photographerMobile;
					cameramanName = encodeURIComponent(cameramanName);
					$.ajax({
						type: "post",
						url: "photog/findHouseOrRoomDetail",
						dataType:"json",
						data: {
							"houseFid" : houseFid
						},
						success: function (result) {
							if(result.code == 0){
								if(result.data.ok == 0){
									//整租情况
									$.openNewTab(new Date().getTime(),encodeURI('house/houseMgt/housePicAuditDetail?operateType=2&cameramanName='+cameramanName+'&cameramanMobile='+cameramanMobile+'&houseBaseFid='+houseFid), "上传房源照片");
								}else if(result.data.ok == 1){
									$("#roomCameramanName").val("");
									$("#roomCameramanMobile").val("");
									//分租情况
									var rooms = result.data.rooms;
									var roomSelect = $("#roomSelect");
									$("#roomCameramanName").val(cameramanName);
									$("#roomCameramanMobile").val(cameramanMobile);
									roomSelect.html("<option value='"+"'>"+"请选择房间"+"</option>");
									for(var i = 0; i< rooms.length;i++){
										roomSelect.append("<option value='"+rooms[i].fid+"'>"+rooms[i].roomSn+"&nbsp;&nbsp;"+rooms[i].roomName+"</option>");
									}
									$('#uploadModal').modal('show');
								}else {
                                    layer.alert("房源不存在或已删除", {icon: 5,time: 2000, title:'提示'});
                                }
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error: function(result) {
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
					$(this).removeAttr("disable");

				}

			    function isNullOrBlank(obj){
			    	return obj == undefined || obj == null || $.trim(obj).length == 0;
			    }
			   
			    
			    function query(){
			    	$('#listTable').bootstrapTable('selectPage', 1);
			    }
			  	 
				//展示房源详情
				function showHouseDetail(houseBaseFid){
					$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=0&houseFid='+houseBaseFid, "房源详情");
				}


				function paginationParamS(params) {
					return {
						limit : params.limit,
						page : $('#listTableS').bootstrapTable('getOptions').pageNumber,
						realName : $.trim($('#realName').val()),
						mobile : $.trim($('#mobile').val()),
						cityCode : $('#cityCode option:selected').val(),
						isDel : 0
					};
				}

				//房间列表表格
				function paginationParamR(params) {
					return {
						limit : params.limit,
						page : $('#listTableRoom').bootstrapTable('getOptions').pageNumber,
						houseFid : $.trim($('#houseBaseFid').val())
					};
				}

				function queryForRoom(){
			    	$('#listTableRoom').bootstrapTable('selectPage', 1);
			    }
				
				function queryS() {
					$('#listTableS').bootstrapTable('selectPage', 1);
				}
				
				// 摄影师来源
				function formateSource(value, row, index){
					var photogSourceJson = ${photogSourceJson };
					var result = '';
					$.each(photogSourceJson,function(i,n){
						if(value == i){
							result = n;
							return false;
						}
					});
					return result;
				}

				//收图具体操作
				$("#receiveButton").click(function(){
					var receivePhotoTimeStr  = $.trim($('#receivePhotoTimeStr').val());
					//console.log($("#updateType").val());
					//获取选中行
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					$(this).attr("disable", "disable");
					if (orderRows.length == 0) {
						layer.alert("请重新选择要收图的订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						$('#receiveModal').modal('hide');
						return;
					}
					if(isNullOrBlank(receivePhotoTimeStr)){
						layer.alert("请填写收图时间", {icon: 5,time: 2000, title:'提示'});
						return;
					}

					$.ajax({
						type: "post",
						url: "photog/receOrCancelPhoto",
						dataType:"json",
						data: {
							"receivePhotoTimeStr" : receivePhotoTimeStr,
							"bookOrderSn":orderRows[0].bookOrderSn,
							"updateType":$("#updateType").val()
						},
						success: function (result) {
							if(result.code === 0){
								$('#receiveModal').modal('hide');
								$('#listTable').bootstrapTable('refresh');
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error: function(result) {
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
					$(this).removeAttr("disable");
				})

				//作废具体操作
				$("#cancelButton").click(function(){
					var cancelRemark  = $.trim($('#cancelRemark').val());
					var cancelStatus = $("#cancelSelect option:selected").val();
					if(cancelStatus == 13 && isNullOrBlank(cancelRemark)){
						layer.alert("此作废备注必填", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					if(cancelStatus==0){
						layer.alert("请选择作废原因", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					//console.log(cancelStatus);
					//获取选中行
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					$(this).attr("disable", "disable");
					if (orderRows.length == 0) {
						layer.alert("请重新选择要作废的订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						$('#cancelModal').modal('hide');
						return;
					}
					$.ajax({
						type: "post",
						url: "photog/receOrCancelPhoto",
						dataType:"json",
						data: {
							"bookOrderRemark" : cancelRemark,
							"bookOrderSn":orderRows[0].bookOrderSn,
							"updateType":cancelStatus
						},
						success: function (result) {
							if(result.code === 0){
								$('#cancelModal').modal('hide');
								$('#listTable').bootstrapTable('refresh');
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error: function(result) {
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
					$(this).removeAttr("disable");
				})

				//指定摄影师
				$("#addPhotog").click(function(){
					$(this).attr("disable", "disable");
					
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					if (orderRows.length == 0) {
						layer.alert("请重新选择预约订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						$('#addModal').modal('hide');
						return;
					}
					
					var rows = $('#listTableS').bootstrapTable('getSelections');
					if (rows.length == 0) {
						layer.alert("请选择摄影师", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					var doorHomeTime = $.trim($('#doorHomeTime').val());
					if(isNullOrBlank(doorHomeTime)){
						layer.alert("请填写上门时间", {icon: 5,time: 2000, title:'提示'});
						return;
					}

					$.ajax({
			            type: "post",
			            url: "photog/assignPhotographer",
			            dataType:"json",
			            data: {
			            	"photographerUid" : rows[0].photographerUid,
			            	"photographerName" : rows[0].realName,
			            	"photographerMobile" : rows[0].mobile,
			            	"bookOrderSn":orderRows[0].bookOrderSn,
							"realTime":doorHomeTime,
			            	"updateType":$("#updateType").val()
			            },
			            success: function (result) {
			            	if(result.code === 0){
					           	$('#addModal').modal('hide');
					           	$('#listTable').bootstrapTable('refresh');
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
			            },
			            error: function(result) {
			            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
			            }
			      }); 
				   $(this).removeAttr("disable");
				   
				})

				//预约完成操作
				function assignPhotographerComplate(obj){
					$(obj).attr("disable", "disable");
					var orderRows = $('#listTable').bootstrapTable('getSelections');
					if (orderRows.length == 0) {
						layer.alert("请重新选择预约订单", {
							icon : 6,
							time : 2000,
							title : '提示'
						});
						return;
					}
					$.ajax({
			            type: "post",
			            url: "photog/assignPhotographerComplate",
			            dataType:"json",
			            data: {   
			            	"bookOrderSn":orderRows[0].bookOrderSn
			            },
			            success: function (result) {
			            	if(result.code === 0){ 
					           	$('#listTable').bootstrapTable('refresh');
							}else{
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
			            },
			            error: function(result) {
			            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
			            }
			      }); 
				   $(obj).removeAttr("disable");
					
				}

		   
			 
		</script>
	</body>
</html>
