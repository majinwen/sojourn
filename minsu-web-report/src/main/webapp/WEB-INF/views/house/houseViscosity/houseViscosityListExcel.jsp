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
					<a href="javascript:void(0)">房源粘性报表</a>
				</li>

			</ul>
		</div>
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
						    <label class="col-sm-1 control-label mtop">国家：</label>
							<div class="col-sm-2">
								<select class="form-control m-b cc-region" id="nation" name="nation">
									<option value="">请选择</option>
									<c:forEach items="${allNationList}" var="obj">
										<option value="${obj.nationCode }">${obj.nationName }</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label mtop">大区：</label>
							<div class="col-sm-2">
								<select class="form-control m-b cc-region" id="region" name="regionName">
									<option value="">请选择</option>
									<c:forEach items="${allRegionList}" var="obj">
										<option value="${obj.fid }">${obj.regionName }</option>
									</c:forEach>
								</select>
							</div>
							<label class="col-sm-1 control-label mtop">城市：</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="city" name="city">
									<option value="">请选择</option>
									<c:forEach items="${cityList}" var="obj">
										<option value="${obj.code }">${obj.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">首次上架时间:</label>
							<div class="col-sm-2 ">
								<input id="createStartTime" name="firstUpBeginDate" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="createEndTime" name="firstUpEndDate" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">房源状态：</label>
							<div class="col-sm-2">
								<select class="form-control m-b cc-region" id="houseStatus" name="houseStatus">
									<option value="">请选择</option>
									<c:forEach items="${houseStatusMap}" var="obj">
										<option value="${obj.key }">${obj.value }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
						    <label class="col-sm-1 control-label mtop">数据查询期间:</label>
							<div class="col-sm-2 ">
								<input id="dataQueryBeaginDate" name="dataQueryBeaginDate" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="dataQueryEndDate" name="dataQueryEndDate" class="laydate-icon form-control layer-date">
							</div>
							
							<label class="col-sm-1 control-label mtop">出租类型：</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="rentWay" name="rentWay" >
									<option value="0">整套出租</option>
									<option value="1">独立房间</option>
								</select>
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
								   data-url="houseViscosity/getHouseViscosityVoList"> 
								<thead>
								<tr class="trbg">
									<th data-field="nationName" data-align="center">国家</th>
									<th data-field="regionName" data-align="center">大区</th>
									<th data-field="cityName" data-align="center">城市</th>
									<th data-field="houseSn" data-align="center">房源或房间编号</th>
									<th data-field="houseStatusName" data-align="center">房源状态</th>
									<th data-field="firstUpDate" data-align="center" data-formatter="getLocalTime">首次上架时间</th>
									<th data-field="cumulViewNum" data-align="center" >累计浏览量</th>
									<th data-field="cumulAdviceNum" data-align="center" >累计咨询量</th>
									<th data-field="cumulApplyNum" data-align="center" >累计申请量</th>
									<th data-field="cumulGetOrderNum" data-align="center">累计接单量</th>
									<th data-field="cumulOrderNum" data-align="center" >累计定单量</th>
									<th data-field="cumulReserveJYNum" data-align="center" >累计预定间夜量</th>
									<th data-field="cumulCheckInOrderNum" data-align="center" >累计入住订单量</th>
									<th data-field="cumulCheckInJYNum" data-align="center" >累计入住间夜量</th>
									<th data-field="cumulShieldJYNum" data-align="center" >累计屏蔽间夜量</th>
									<th data-field="rentRate" data-align="center" >出租率</th>
									<th data-field="cumulGetEvalNum" data-align="center" >累计收到评价量</th>
									<th data-field="cumulProfit" data-align="center" >累计房租收益</th>
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
			nationCode:$('#nation').val(),
			regionCode:$('#region').val(),
			cityCode:$('#city').val(),
			firstUpBeginDate:$('#createStartTime').val(),
			firstUpEndDate:$('#createEndTime').val(),
			houseStatus:$('#houseStatus').val(),
			dataQueryBeaginDate:$('#dataQueryBeaginDate').val(),
			dataQueryEndDate:$('#dataQueryEndDate').val(),
			rentWay:$('#rentWay').val()
		};
	}


	 function exportFile(){
		var nationCode = $('#nation').val();
	    var regionCode = $('#region').val();
	    var cityCode = $('#city').val();
	    var firstUpBeginDate = $('#createStartTime').val();
	    var firstUpEndDate = $('#createEndTime').val();
	    var houseStatus = $('#houseStatus').val();
	    var dataQueryBeaginDate = $('#dataQueryBeaginDate').val();
		var dataQueryEndDate = $('#dataQueryEndDate').val();
	    var rentWay = $('#rentWay').val();
		var url = "houseViscosity/getHouseViscosityExcel?"+
					    "nationCode="+nationCode+
						"&regionCode="+regionCode+
						"&cityCode="+cityCode+
						"&firstUpBeginDate="+firstUpBeginDate+
						"&firstUpEndDate="+firstUpEndDate+
						"&houseStatus="+houseStatus+
						"&dataQueryBeaginDate="+dataQueryBeaginDate+
						"&dataQueryEndDate="+dataQueryEndDate+
						"&rentWay="+rentWay;
		window.location.href = url;
	} 

/* 
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
	} */

	$(function (){
		//初始化日期
		datePickerFormat('createStartTime', '00:00:00');
		datePickerFormat('createEndTime', '23:59:59');
		datePickerFormat('dataQueryBeaginDate', '00:00:00');
		datePickerFormat('dataQueryEndDate', '23:59:59');
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
