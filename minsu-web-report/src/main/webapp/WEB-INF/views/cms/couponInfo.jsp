<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath }">
	<title>自如民宿后台管理系统</title>
	<meta name="keywords" content="自如民宿后台管理系统">
	<meta name="description" content="自如民宿后台管理系统">
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
	<div class="row row-lg">
		<div class="tabs-container">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="javascript:void(0)">优惠券信息报表</a>
				</li>

			</ul>
		</div>
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">活动创建时间:</label>
							<div class="col-sm-2 ">
								<input id="actCreateStartTime" name="actCreateStartTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="actCreateEndTime" name="actCreateEndTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">活动编号:</label>
							<div class="col-sm-2 ">
								<input id="actSn" name="actSn" class="form-control m-b">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">优惠券创建时间:</label>
							<div class="col-sm-2 ">
								<input id="couponCreateStartTime" name="couponCreateStartTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="couponCreateEndTime" name="couponCreateEndTime" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">优惠券开始时间:</label>
							<div class="col-sm-2 ">
								<input id="couponStartTimeStart" name="couponStartTimeStart" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="couponStartTimeEnd" name="couponStartTimeEnd" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">优惠券状态:</label>
							<div class="col-sm-2 ">
								<select class="form-control m-b" id="couponStatus" name="couponStatus" >
									<option value="">请选择</option>
									<option value="1">未领取</option>
									<option value="6">已发送</option>
									<option value="2">已领取</option>
									<option value="3">已冻结</option>
									<option value="4">已使用</option>
									<option value="5">已过期</option>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">领取时间:</label>
							<div class="col-sm-2 ">
								<input id="gotTimeStart" name="gotTimeStart" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="gotTimeEnd" name="gotTimeEnd" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
								<button class="btn btn-primary" type="button" onclick="query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
								<button class="btn btn-primary" type="button" onclick="exportFile();" >
									<i class="fa fa-search"></i>&nbsp;导出文件
								</button>
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
								   data-single-select="true"
								   data-height="500"
								   data-url="couponInfo/getCouponInfo">
								<thead>
								<tr class="trbg">
									<th data-field="actName" data-align="center">活动名称</th>
									<th data-field="actSn" data-align="center">编号</th>
									<th data-field="couponSn" data-align="center">优惠券编号</th>
									<th data-field="couponStatus" data-align="center">优惠券状态</th>
									<th data-field="actType" data-align="center">优惠类型</th>
									<th data-field="actCut" data-align="center">优惠金额</th>
									<th data-field="actCreateTime" data-align="center" data-formatter="getLocalTime">活动创建时间</th>
									<th data-field="couponCreateTime" data-align="center" data-formatter="getLocalTime">优惠券创建时间</th>
									<th data-field="couponStartTime" data-align="center" data-formatter="getLocalTime">优惠券开始时间</th>
									<th data-field="couponEndTime" data-align="center" data-formatter="getLocalTime">优惠券结束时间</th>
									<th data-field="userUid" data-align="center">领取人UID</th>
									<th data-field="customerMobile" data-align="center">领取电话</th>
									<th data-field="gotTime" data-align="center" data-formatter="getLocalTime">领取时间</th>
									<th data-field="orderSn" data-align="center">订单号</th>
									<th data-field="usedTime" data-align="center">使用时间</th>
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

	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}
	function paginationParam(params) {
		return {
			limit: params.limit,
			page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			actCreateStartTime:$('#actCreateStartTime').val(),
			actCreateEndTime:$('#actCreateEndTime').val(),
			actSn:$('#actSn').val(),
			couponCreateStartTime:$('#couponCreateStartTime').val(),
			couponCreateEndTime:$('#couponCreateEndTime').val(),
			couponCreateEndTime:$('#couponCreateEndTime').val(),
			couponStartTimeStart:$('#couponStartTimeStart').val(),
			couponStartTimeEnd:$('#couponStartTimeEnd').val(),
			couponStatus:$('#couponStatus').val(),
			gotTimeStart:$('#gotTimeStart').val(),
			gotTimeEnd:$('#gotTimeEnd').val()
		};
	}


	function exportFile(){
		var actCreateStartTime = $('#actCreateStartTime').val();
		var actCreateEndTime = $('#actCreateEndTime').val();
		var actSn = $('#actSn').val();
		var couponCreateStartTime = $('#couponCreateStartTime').val();
		var couponCreateEndTime = $('#couponCreateEndTime').val();
		var couponStartTimeStart = $('#couponStartTimeStart').val();
		var couponStartTimeEnd = $('#couponStartTimeEnd').val();
		var couponStatus = $('#couponStatus').val();
		var gotTimeStart = $('#gotTimeStart').val();
		var gotTimeEnd = $('#gotTimeEnd').val();
		var url = "couponInfo/couponInfoExcel?"+
						"actCreateStartTime="+actCreateStartTime+
						"&actCreateEndTime="+actCreateEndTime+
						"&actSn="+actSn+
						"&couponCreateStartTime="+couponCreateStartTime+
						"&couponCreateEndTime="+couponCreateEndTime+
						"&couponStartTimeStart="+couponStartTimeStart+
						"&couponStartTimeEnd="+couponStartTimeEnd+
						"&couponStatus="+couponStatus+
						"&gotTimeStart="+gotTimeStart+
						"&gotTimeEnd="+gotTimeEnd;
		window.location.href = url;
	}


	function ajaxGetSubmit(url,data,callback){
		$.ajax({
			type: "GET",
			url: url,
			dataType:"json",
			data: data,
			success: function (result) {
				callback(result);
			},
			error: function(result) {
				alert("error:"+result);
			}
		});
	}

	$(function (){
		//初始化日期
		datePickerFormat('actCreateStartTime', '00:00:00');
		datePickerFormat('actCreateEndTime', '23:59:59');
		datePickerFormat('couponCreateStartTime', '00:00:00');
		datePickerFormat('couponCreateEndTime', '23:59:59');
		datePickerFormat('couponStartTimeStart', '00:00:00');
		datePickerFormat('couponStartTimeEnd', '23:59:59');
		datePickerFormat('gotTimeStart', '00:00:00');
		datePickerFormat('gotTimeEnd', '23:59:59');
	});


	function datePickerFormat(elemId, time){
		laydate({
			elem: '#' + elemId,
			format: 'YYYY-MM-DD ' + time,
			istime: false,
			istoday: true
		});
	}


	function getLocalTime(value) {
		// return new Date(parseInt(nS)).toLocaleString().substr(0,17)
		if(value==null||value == ""||value==undefined){
			return "";
		}
		var date = new Date(value);
		var month = date.getMonth()+1;
		var day = date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		month = month<10?'0'+month:month;
		day = day<10?'0'+day:day;
		hours = hours<10?'0'+hours:hours;
		minutes = minutes<10?'0'+minutes:minutes;
		seconds = seconds<10?'0'+seconds:seconds;
		return date.getFullYear()+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	}




</script>
</body>

</html>
