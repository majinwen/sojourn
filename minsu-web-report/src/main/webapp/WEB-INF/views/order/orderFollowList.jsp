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
					<a href="javascript:void(0)">客服跟进数据报表查询功能</a>
				</li>

			</ul>
		</div>
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">预订人姓名：</label>
							<div class="col-sm-2">
								<input id="userName" name="userName"  type="text" class="form-control" >
							</div>
							<label class="col-sm-1 control-label mtop">房源名称：</label>
							<div class="col-sm-2">
								<input id="houseName" name="houseName"  type="text" class="form-control" >
							</div>
							<label class="col-sm-1 control-label mtop">预订人手机号：</label>
							<div class="col-sm-2">
								<input id="userTel" name="userTel"  type="text" class="form-control" >
							</div>
							<label class="col-sm-1 control-label mtop">订单状态：</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="orderStatus" name="orderStatus" >
									<option value="">请选择</option>
									<c:forEach items="${orderStatusMap}" var="obj">
										<option value="${obj.key }">${obj.value }</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">订单编号：</label>
							<div class="col-sm-2">
								<input id="orderSn" name="orderSn"  type="text" class="form-control" >
							</div>
							<label class="col-sm-1 control-label mtop">订单创建时间:</label>
							<div class="col-sm-2 ">
								<input id="createStartTime" name="createStartTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="createEndTime" name="createEndTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">城市:</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="cityCode" name="cityCode" >
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
							<label class="col-sm-1 control-label mtop">入住时间：</label>
							<div class="col-sm-2">
								<input id="checkInStartTime" name="checkInStartTime" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">到：</label>
							<div class="col-sm-2">
								<input id="checkInEndTime" name="checkInEndTime" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">离开时间：</label>
							<div class="col-sm-2">
								<input id="checkOutStartTime" name="checkOutStartTime" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">到：</label>
							<div class="col-sm-2">
								<input id="checkOutEndTime" name="checkOutEndTime" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">房东姓名：</label>
							<div class="col-sm-2">
								<input id="landlordName" name="landlordName"  type="text" class="form-control" >
							</div>
							<label class="col-sm-1 control-label mtop">支付状态：</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="payStatus" name="payStatus" >
									<option value="">请选择</option>
									<option value="0">未支付</option>
									<option value="1">已支付</option>
								</select>
							</div>
							<%--<label class="col-sm-1 control-label mtop">地推管家：</label>
							<div class="col-sm-2">
								<input id="empPushName" name="empPushName"  type="text" class="form-control" >
							</div>--%>
							<label class="col-sm-1 control-label mtop">运营专员：</label>
							<div class="col-sm-2">
								<input id="empGuardName" name="empGuardName"  type="text" class="form-control" >
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<font color="#FF0000">如“订单创建时间未”未选择，默认为1个月前</font>
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
								   data-url="orderfollow/orderFollowList">
								<thead>
								<tr class="trbg">
									<th data-field="orderSn" data-align="center">订单号</th>
									<th data-field="nationName" data-align="center">国家</th>
									<th data-field="cityName" data-align="center">城市</th>
									<th data-field="userName" data-align="center">预订人姓名</th>
									<th data-field="userTel" data-align="center">预订人手机</th>
									<th data-field="landlordName" data-align="center">房东姓名</th>
									<th data-field="landlordTel" data-align="center">房东手机</th>
									<%--<th data-field="empPushName" data-align="center">地推管家</th>--%>
									<th data-field="empGuardName" data-align="center">运营专员</th>
									<th data-field="createTime" data-align="center" data-formatter="getLocalTime">创建时间</th>
									<th data-field="startTime" data-align="center" data-formatter="getLocalTime">入住时间</th>
									<th data-field="endTime" data-align="center" data-formatter="getLocalTime">离开时间</th>
									<th data-field="payStatusName" data-align="center">支付状态</th>
									<th data-field="orderMoney" data-align="center">订单金额</th>
									<th data-field="realMoney" data-align="center">实际成交金额</th>
									<th data-field="orderStatusName" data-align="center">订单状态</th>
									<th data-field="followOrderStatusName" data-align="center">客服跟进时订单状态</th>
									<th data-field="followPeople" data-align="center">跟进人</th>
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
			houseName:$('#houseName').val(),
			cityCode:$('#cityCode').val(),
			userName:$('#userName').val(),
			userTel:$('#userTel').val(),
			landlordName:$('#landlordName').val(),
			orderStatus:$('#orderStatus').val(),
			orderSn:$('#orderSn').val(),
			createStartTime:$('#createStartTime').val(),
			createEndTime:$('#createEndTime').val(),
			checkInStartTime:$('#checkInStartTime').val(),
			checkInEndTime:$('#checkInEndTime').val(),
			checkOutStartTime:$('#checkOutStartTime').val(),
			checkOutEndTime:$('#checkOutEndTime').val(),
			payStatus:$('#payStatus').val(),
			//empPushName:$('#empPushName').val(),
			empGuardName:$('#empGuardName').val()
		};
	}


	function exportFile(){
		var houseName = $('#houseName').val();
		var cityCode = $('#cityCode').val();
		var userName = $('#userName').val();
		var userTel = $('#userTel').val();
		var landlordName = $('#landlordName').val();
		var orderStatus = $('#orderStatus').val();
		var orderSn = $('#orderSn').val();
		var createStartTime = $('#createStartTime').val();
		var createEndTime = $('#createEndTime').val();
		var checkInStartTime = $('#checkInStartTime').val();
		var checkInEndTime = $('#checkInEndTime').val();
		var checkOutStartTime = $('#checkOutStartTime').val();
		var checkOutEndTime = $('#checkOutEndTime').val();
		var payStatus = $('#payStatus').val();
		//var empPushName = $('#empPushName').val();
		var empGuardName = $('#empGuardName').val();
		var url = "orderfollow/orderFollowListExcel?"+
						"houseName="+houseName+
						"&cityCode="+cityCode+
						"&userName="+userName+
						"&userTel="+userTel+
						"&landlordName="+landlordName+
						"&orderStatus="+orderStatus+
						"&orderSn="+orderSn+
						"&createStartTime="+createStartTime+
						"&createEndTime="+createEndTime+
						"&checkInStartTime="+checkInStartTime+
						"&checkInEndTime="+checkInEndTime+
						"&checkOutStartTime="+checkOutStartTime+
						"&checkOutEndTime="+checkOutEndTime+
						"&payStatus="+payStatus+
						//"&empPushName="+empPushName+
						"&empGuardName="+empGuardName;
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
		var oneMonthBefore = getValueMonthBefore(1);
		//var threeMonthBefore = getValueMonthBefore(3);
		console.log("oneMonthBefore:::::::"+oneMonthBefore);
		$('#createStartTime').val(oneMonthBefore);		
		datePickerFormat('createStartTime', '00:00:00');
		datePickerFormat('createEndTime', '23:59:59');
		datePickerFormat('checkInStartTime', '00:00:00');
		datePickerFormat('checkInEndTime', '23:59:59');
		datePickerFormat('checkOutStartTime', '00:00:00');
		datePickerFormat('checkOutEndTime', '23:59:59');		
		var createStartTime = $('#createStartTime').val();		
		console.log("createStartTime:::::::"+createStartTime);

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
	
	function getValueMonthBefore(value){       
	       var resultDate,year,month,date,hms;      
	       var currDate = new Date();     
	       year = currDate.getFullYear();     
	       month = currDate.getMonth()+1;     
	       date = currDate.getDate();     
	       hms = currDate.getHours() + ':' + currDate.getMinutes() + ':' + (currDate.getSeconds() < 10 ? '0'+currDate.getSeconds() : currDate.getSeconds());      
	       switch(month)     
	       {     
	            case 1:     
	            case 2:     
	            case 3:     
	                month += 9;     
	                year--;     
	                break;     
	            default:     
	                month -= value;     
	                break;     
	       }     
	        month = (month < 10) ? ('0' + month) : month;     
	             
	       resultDate = year + '-'+month+'-'+date+' ' + hms; 
	       resultDate = getLocalTime(resultDate);
	    return resultDate;     
	} 




</script>
</body>

</html>
