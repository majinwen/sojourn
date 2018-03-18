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
				<li>
					<a href="houseAddress/toEntireHouseAddress">整租列表</a>
				</li>
				<li class="active">
					<a href="javascript:void(0)">合租列表</a>
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
									<c:forEach items="${nationList}" var="obj">
										<option value="${obj.code }">${obj.showName }</option>
									</c:forEach>
								</select>
							</div>

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
							<label class="col-sm-1 control-label mtop">首次发布时间:</label>
							<div class="col-sm-2 ">
								<input id="createStartTime" name="createStartTime" class="laydate-icon form-control layer-date">
							</div>

							<label class="col-sm-1 control-label mtop">到:</label>
							<div class="col-sm-2">
								<input id="createEndTime" name="createEndTime" class="laydate-icon form-control layer-date">
							</div>
							
							<label class="col-sm-1 control-label mtop">房源状态：</label>
							<div class="col-sm-2">
								<select class="form-control m-b" id="houseStatus">
					                <option value="">请选择</option>
					                <c:forEach items="${houseStatusMap }" var="obj">
						                 <option value="${obj.key }">${obj.value }</option>
					                </c:forEach>
				                </select>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
							<customtag:authtag authUrl="houseAddress/dataList">
								<button class="btn btn-primary" type="button" onclick="query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</customtag:authtag>	
							</div>
						</div>
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
							<customtag:authtag authUrl="houseAddress/houseAddressExcel">
								<button class="btn btn-primary" type="button" onclick="exportFile();" >
								 <i class="fa fa-search"></i>&nbsp;导出文件
							    </button>
							 </customtag:authtag>   
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
								   data-url="orderContact/orderContactList">
								<thead>
								<tr class="trbg">
									<th data-field="nationName" data-align="center">国家</th>
									<th data-field="regionName" data-align="center">大区</th>
									<th data-field="cityName" data-align="center">城市</th>
									<th data-field="houseSn" data-align="center">房源/房间编号</th>
									<th data-field="firstDeployDate" data-align="center">首次发布时间</th>
									<th data-field="houseStatusName" data-align="center">房源/房间状态</th>
									<th data-field="houseName" data-align="center">房源/房间名称</th>
									<th data-field="houseAddr" data-align="center">房源地址表</th>
									<th data-field="landlordUid" data-align="center">房东UID</th>
									<th data-field="lanMobile" data-align="center">房东电话</th>
									<th data-field="lanRealName" data-align="center">房东姓名</th>
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
			regionFid:$('#region').val(),
			cityCode:$('#city').val(),
			createStartTime:$('#createStartTime').val(),
			createEndTime:$('#createEndTime').val(),
			houseStatus:$('#houseStatus').val()
		};
	}

	//导出地址
	var exportUrl='houseAddress/houseAddressExcel?rentWay=1';
    function exportFile(){
    	var nation = $('#nation').val();
		var region = $('#region').val();
		var city = $('#city').val();
		var createStartTime = $('#createStartTime').val();
		var createEndTime = $('#createEndTime').val();
		var houseStatus = $('#houseStatus').val();
		var url = exportUrl+
						"nationCode="+nation+
						"&regionFid="+region+
						"&cityCode="+city+
						"&createStartTime="+createStartTime+
						"&createEndTime="+createEndTime+
						"&houseStatus="+houseStatus;
        window.location.href = url
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
    
    
    function moneyDataFormat(value,row,index){
    	return value/100;
    }
    $(function (){
		//初始化日期
		CommonUtils.datePickerFormat('createStartTime');
		CommonUtils.datePickerFormat('createEndTime');
	});
    /* $(function (){
		//初始化日期
		datePickerFormat('createStartTime', '00:00:00');
		datePickerFormat('createEndTime', '23:59:59');
	});


	function datePickerFormat(elemId, time){
		laydate({
			elem: '#' + elemId,
			format: 'YYYY-MM-DD ' + time,
			istime: false,
			istoday: true
		});
	}  */


</script>
</body>

</html>
