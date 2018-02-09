<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<base href="${basePath }>">
	<title>自如民宿后台管理系统</title>
	<meta name="keywords" content="自如民宿后台管理系统">
	<meta name="description" content="自如民宿后台管理系统">
	<link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
	<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
	<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/star-rating.css${VERSION}001"  rel="stylesheet" type="text/css"/>

	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style type="text/css">
		.left2{
			margin-top: 10px;
		}
		.row{margin:0;}
	</style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row row-lg">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="commentForm">
						<div class="form-group">
							<div class="row">
								<label class="col-sm-1 control-label left2">房东姓名：</label>
								<div class="col-sm-2 left2">
									<input id="landlordName" name="landlordName" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label left2">房东手机号：</label>
								<div class="col-sm-2 left2">
									<input id="landlordPhone" name="landlordPhone" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label left2">房源/房间名称：</label>
								<div class="col-sm-2 left2">
									<input id="houseName" name="houseName" type="text" class="form-control">
								</div> 
								<label class="col-sm-1 control-label left2">房源/房间编号：</label>
								<div class="col-sm-2 left2">
									<input id="houseSn" name="houseSn" type="text" class="form-control">
								</div> 
							</div>
							<div class="row">
								<label class="col-sm-1 control-label left2">房源Fid：</label>
								<div class="col-sm-2 left2">
									<input id="houseFid" name="houseFid" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label left2">房间Fid：</label>
								<div class="col-sm-2 left2">
									<input id="roomFid" name="roomFid" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label left2">出租方式：</label>
								<div class="col-sm-2 left2">
									<input id="rentWay" name="rentWay" type="text" class="form-control">
								</div>
								<div class="col-sm-1 left2">
									<button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- Panel Other -->
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<button class="btn btn-primary" type="button" onclick="openSaveModel()">
						<i class="fa fa-save"></i>&nbsp;添加
					</button>
					<div class="example-wrap">
						<div class="example">
							<table id="listTable" style="table-layout:fixed" class="table table-bordered table-condensed table-hover table-striped" data-click-to-select="true"
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
								   data-height="500" data-url="houseSyncCalendar/dataList">
								<thead>
								<tr>
									<th data-field="fid" data-visible="false" ></th>
									<th data-field="houseFid" data-align="center" data-width="220px" >房源Fid</th>
									<th data-field="roomFid" data-align="center"  data-width="220px">房间Fid</th>
									<th data-field="rentWay" data-align="center" data-width="100px" data-formatter="formateRentWay">出租方式</th>
									<th data-field="houseName"  data-align="center" data-width="200px"  data-cell-style="cellStyle">房源/房间名称</th>
									<th data-field="houseSn"  data-align="center" data-width="120px" data-formatter="formateHouseSn">房源/房间编号</th>
									<th data-field="landlordName"  data-align="center" data-width="100px" >房东姓名</th>
									<th data-field="landlordMobile"  data-align="center" data-width="100px">房东手机号</th>
									<th data-field="houseChannel" data-align="center" data-width="100px" data-formatter="formateHouseChannel" >房源渠道</th>
									<th data-field="tillDate" data-align="center" data-width="100px" data-formatter="formateDate" >截止时间</th>
									<th data-field="houseStatus"  data-align="center" data-width="100px" data-formatter="formateHouseStatus">房源状态</th>
									<th data-field="calendarUrl"  data-align="center" data-width="200px" data-editable="true" data-formatter="formateUrl" data-cell-style="cellStyle">日历URl</th>
									<th data-field="createTime" data-align="center" data-width="120px" data-formatter="formateDate" >创建时间</th>
									<th data-field="createTime" data-align="center" data-width="100px"  data-formatter="formateOper">操作</th>
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
<!-- 添加 菜单  弹出框 -->
<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content animated bounceInRight">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
				</button>
				<h4 class="modal-title">新增同步关联</h4>
			</div>
			<div class="modal-body">
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-sm-12">
							<div class="form-group">
								<div class="row">
									<label class="col-sm-2 control-label mtop">房源FID:</label>
									<div class="col-sm-4">
										<input id="saveHouseFid" name="saveHouseFid" type="text" class="form-control m-b">
									</div>
									<label class="col-sm-2 control-label mtop">出租方式:</label>
									<div class="col-sm-4">
										<select name="saveRentWay" id="saveRentWay" class="form-control">
											<option value="0">整租</option>
											<option value="1">分租</option>
										</select>
									</div>
								</div>
								<div class="row">
									<label class="col-sm-2 control-label mtop">Airbnb日历Url:</label>
									<div class="col-sm-4">
										<input id="saveAriBnb" name="saveAriBnb" type="text" class="form-control m-b">
									</div>

									<label class="col-sm-2 control-label mtop room-div" style="display: none">房间FID:</label>
									<div class="col-sm-4 room-div" style="display: none">
										<input id="saveRoomFid" name="saveRoomFid" type="text" class="form-control m-b">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer" style="text-align: center;">
				<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" onclick="saveHouseRelate()">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>


<script type="text/javascript">

	function cellStyle(value, row, index, field) {
		return {
			css: {"overflow":"hidden","white-space":"nowrap","text-overflow":"ellipsis"}
		};
	}

	function paginationParam(params) {
		return {
			limit: params.limit,
			page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			houseFid:$.trim($('#houseFid').val()),
			roomFid:$.trim($('#roomFid').val()),
			rentWay:$.trim($('#rentWay').val()),
			landlordName:$.trim($('#landlordName').val()),
			landlordPhone:$.trim($('#landlordPhone').val()),
			houseName:$.trim($('#houseName').val()),
			houseSn:$.trim($('#houseSn').val())
		};
	}

	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}

	function formateDate(value, row, index) {
		if (value != null) {
			var vDate = new Date(value);
			return vDate.format("yyyy-MM-dd HH:mm:ss");
		} else {
			return "";
		}
	}

	function formateRentWay(value, row, index) { 
		if(value == 0){
			return "整租";
		}
		if(value == 1){
			return "分租";
		}
	}
	
	// 房源编号
	function formateHouseSn(value, row, index){
		
		if(row.rentWay==0){
			return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showHouseDetail(\""+row.houseFid+"\");'</customtag:authtag>>"+value+"</a>";
		}else if(row.rentWay==1){
			return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showRoomDetail(\""+row.roomFid+"\");'</customtag:authtag>>"+value+"</a>";
		}
		return '';
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
	
	// 房源渠道
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
	
	// url
	function formateUrl(value, row, index){
		return '<span style="word-wrap:break-word;word-break:break-all;" >'+value+'</span>';
	} 

	function formateOper(value,row,index) {
		return "<button type='button' class='btn btn-primary btn-xs' onclick='syncCalendar(\""+row.houseFid+"\",\""+row.roomFid+"\",\""+row.rentWay+"\")'>同步日历</button>" + 
			"&nbsp;&nbsp;<button type='button' class='btn btn-primary btn-xs' onclick='showDelHouseRelateModel(\""+row.fid+"\")'>解除</button>";
	}
	
	//展示房源详情
	function showHouseDetail(houseBaseFid){
		$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=0&houseFid='+houseBaseFid, "房源详情");
	}
	
	//展示房间详情
	function showRoomDetail(houseRoomFid){
		$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=1&houseFid='+houseRoomFid, "房间详情");
	}
	
	var flag = false;
	//开始同步日历
	function syncCalendar(houseFid, roomFid, rentWay) {
		if (flag){
			return;
		}
		flag = true;
		 var index = layer.load(1, {
			shade: [0.1,'#fff'] //0.1透明度的白色背景
		}); 
		if (rentWay == '0'){
			roomFid = "";
		}
		
		$.ajax({
		    type: "POST",
		    url: "houseSyncCalendar/syncCalendar",
		    dataType:"json",
		    data: {"houseFid":houseFid,"roomFid":roomFid,"rentWay":rentWay},
		     success: function (data) {
		    	 if(data){
		    		flag = false;
		 			layer.closeAll("loading");
		 			if (data.code == 0){
		 				layer.alert("同步成功", {icon: 1,time: 2000, title:'提示'});
		 			}else{
		 				layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
		 			}
		    		
		    	 }else{
		    			layer.alert("同步失败", {icon: 1,time: 2000, title:'提示'});
		    	 }
		    },
		    error: function(result) {
		    	layer.closeAll("loading");
		    	layer.alert("同步失败", {icon: 1,time: 2000, title:'提示'});
		        }
		     });
	}

	//解除关系
	function showDelHouseRelateModel(fid){
		layer.confirm("解除房源同步关系？", function(){
			$.ajax({
			    type: "POST",
			    url: "houseSyncCalendar/updateHouseRelate",
			    dataType:"json",
			    data: {"fid":fid, "isDel":1},
			     success: function (data) {
			    	 if(data){
			 			layer.closeAll("loading");
			 			if (data.code == 0){
			 				layer.alert("已解除", {icon: 1,time: 2000, title:'提示'});
			 				$("#listTable").bootstrapTable('refresh');
			 			}else{
			 				layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
			 			}
			    	 }else{
			    		layer.alert("解除失败", {icon: 5,time: 2000, title:'提示'});
			    	 }
			    },
			    error: function(result) {
			    	layer.closeAll("loading");
			    	layer.alert("解除失败", {icon: 5,time: 2000, title:'提示'});
			        }
			     });
		});
	}
	
	function openSaveModel() {
		$('#myModal').modal('show');
	}

	function saveHouseRelate() {
		var houseFid = $.trim($("#saveHouseFid").val());
		var roomFid = $.trim($("#saveRoomFid").val());
		var rentWay = $("#saveRentWay").val();
		var airbnbSn = $.trim($("#saveAriBnb").val());
		if (houseFid == "" || airbnbSn == "" || rentWay == ""){
			return;
		}
		if (rentWay == "1" && roomFid == ""){
			return;
		}
		$.post("/houseSyncCalendar/saveHouseRelate",{"houseFid":houseFid,"roomFid":roomFid,"rentWay":rentWay,"calendarUrl":airbnbSn},function (data) {
			if (data.code == 0){
				$('#myModal').modal('hide');
				$("#listTable").bootstrapTable('refresh');
				layer.alert("保存成功", {icon: 1,time: 2000, title:'提示'});
			}else{
				layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
			}

		},"json");


	}
	$(function () {
		$("#saveRentWay").change(function () {
			var rent = $(this).val();
			if(rent == 1){
				$(".room-div").show();
			}
			if (rent == 0){
				$(".room-div").hide();
				$("#saveRoomFid").val("");
			}
		});
	});

</script>

</body>

</html>
