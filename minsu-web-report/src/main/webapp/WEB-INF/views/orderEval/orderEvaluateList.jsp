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
					<a href="javascript:void(0)">订单评价报表</a>
				</li>

			</ul>
		</div>
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">大区：</label>
							<div class="col-sm-2">
								<select class="form-control m-b cc-region" id="region" name="region">
									<option value="">请选择</option>
									<c:forEach items="${regionList}" var="obj">
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
							<label class="col-sm-1 control-label mtop">预定时间:</label>
							<div class="col-sm-2 ">
								<input id="createStartTime" name="createStartTime" class="laydate-icon form-control layer-date" >
							</div>
							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="createEndTime" name="createEndTime" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">实际退房时间:</label>
							<div class="col-sm-2 ">
								<input id="realEndTimeStart" name="realEndTimeStart" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="realEndTimeEnd" name="realEndTimeEnd" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">房客评价时间：</label>
							<div class="col-sm-2">
								<input id="tenantEvaStartTime" name="tenantEvaStartTime" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">到：</label>
							<div class="col-sm-2">
								<input id="tenantEvaEndTime" name="tenantEvaEndTime" class="laydate-icon form-control layer-date">
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-12">
							<label class="col-sm-1 control-label mtop">房东评价时间：</label>
							<div class="col-sm-2">
								<input id="landlordEvaStartTime" name="landlordEvaStartTime" class="laydate-icon form-control layer-date">
							</div>
							<label class="col-sm-1 control-label mtop">到：</label>
							<div class="col-sm-2">
								<input id="landlordEvaEndTime" name="landlordEvaEndTime" class="laydate-icon form-control layer-date">
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
								   data-url="orderEval/getOrderEvaluate">
								<thead>
								<tr class="trbg">
									<th data-field="nation" data-align="center">国家</th>
									<th data-field="regionName" data-align="center">大区</th>
									<th data-field="cityName" data-align="center">城市</th>
									<th data-field="orderSn" data-align="center">订单号</th>
									<th data-field="realEndTime" data-align="center" data-formatter="getLocalTime">订单结束时间</th>
									<th data-field="tenantEvaTime" data-align="center" data-formatter="getLocalTime">客户评价时间</th>
									<th data-field="avgTenantScore" data-align="center">房客评分</th>
									<th data-field="scoreHouseClean" data-align="center">清洁度</th>
									<th data-field="scoreDescriptionMatch" data-align="center">描述相符</th>
									<th data-field="scoreSafetyDegree" data-align="center">房东印象</th>
									<th data-field="scoreTrafficPosition" data-align="center">周边环境</th>
									<th data-field="scoreCostPerformance" data-align="center">性价比</th>
									<th data-field="tenantContent" data-align="center" data-formatter="contentFormatter">评价内容</th>
									<th data-field="landlordEvaTime" data-align="center">房东评价时间</th>
									<th data-field="landlordContent" data-align="center" data-formatter="contentFormatter">房东评价内容</th>
									<th data-field="landlordSatisfied" data-align="center">房东评分</th>
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
    //获取两个月前时间
    var oneMonthBefore = getValueMonthBefore(1);
	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}
	function paginationParam(params) {
		return {
			limit: params.limit,
			page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			region:$('#region').val(),
			city:$('#city').val(),
			createStartTime:$('#createStartTime').val(),
			createEndTime:$('#createEndTime').val(),
			tenantEvaStartTime:$('#tenantEvaStartTime').val(),
			tenantEvaEndTime:$('#tenantEvaEndTime').val(),
			landlordEvaStartTime:$('#landlordEvaStartTime').val(),
			landlordEvaEndTime:$('#landlordEvaEndTime').val(),
			realEndTimeStart:$('#realEndTimeStart').val(),
			realEndTimeEnd:$('#realEndTimeEnd').val()
		};
	}


	function exportFile(){
		var region = $('#region').val();
		var city = $('#city').val();
		var createStartTime = $('#createStartTime').val();
		var createEndTime = $('#createEndTime').val();
		var tenantEvaStartTime = $('#tenantEvaStartTime').val();
		var tenantEvaEndTime = $('#tenantEvaEndTime').val();
		var landlordEvaStartTime = $('#landlordEvaStartTime').val();
		var landlordEvaEndTime = $('#landlordEvaEndTime').val();
		var realEndTimeStart = $('#realEndTimeStart').val();
		var realEndTimeEnd = $('#realEndTimeEnd').val();
		var url = "orderEval/orderEvaluateExcel?"+
						"region="+region+
						"&city="+city+
						"&createStartTime="+createStartTime+
						"&createEndTime="+createEndTime+
						"&tenantEvaStartTime="+tenantEvaStartTime+
						"&tenantEvaEndTime="+tenantEvaEndTime+
						"&landlordEvaStartTime="+landlordEvaStartTime+
						"&landlordEvaEndTime="+landlordEvaEndTime+
						"&realEndTimeStart="+realEndTimeStart+
						"&realEndTimeEnd="+realEndTimeEnd;
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
		$('#createStartTime').val(oneMonthBefore);	
		datePickerFormat('createStartTime', '00:00:00');
		datePickerFormat('createEndTime', '23:59:59');
		datePickerFormat('tenantEvaStartTime', '00:00:00');
		datePickerFormat('tenantEvaEndTime', '23:59:59');
		datePickerFormat('landlordEvaStartTime', '00:00:00');
		datePickerFormat('landlordEvaEndTime', '23:59:59');
		datePickerFormat('realEndTimeStart', '00:00:00');
		datePickerFormat('realEndTimeEnd', '23:59:59');
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


	function contentFormatter(value) {
		if(value == null){
			return "-";
		}
		if(value!=null && value.length > 10){
			return "<p title='"+value+"'>"+value.substring(0,10)+"...</p>";
		}
		return "<p title='"+value+"'>"+value+"</p>";
	}

    /* 获取value个月之前的时间戳 */
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
