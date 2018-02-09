<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href='${staticResourceUrl}/css/plugins/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='${staticResourceUrl}/css/plugins/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
	<style type="text/css">
	 body {
		 margin: 40px 10px;
		 padding: 0;
		 font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		 font-size: 14px;
	 }
	 #calendar {
		 max-width: 700px;
		 margin: 0 auto;
	 }
	 .lan_lock{
		 width: 15px;
		 height:15px;
		 margin:auto 2px;
		 vertical-align:middle;
		 background-color: #ffa000;
		 display: inline-block;
	 }
	 .order_lock{
		 width: 15px;
		 height:15px;
		 margin:auto 2px;
		 vertical-align:middle;
		 background-color: #ddd;
		 display: inline-block;
	 }
	 .idol_lock{
		 width: 15px;
		 height:15px;
		 margin:auto 2px;
		 border:1px solid #d1d1d1;
		 vertical-align:middle;
		 background-color: #f3f3f4;
		 display: inline-block;
	 }
	 .sys_lock{
		 width: 15px;
		 height:15px;
		 margin:auto 2px;
		 border:1px solid #d1d1d1;
		 vertical-align:middle;
		 background-color: #ff0000;
		 display: inline-block;
	 }
	.box_text{
		display: inline-block;
	}
 </style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="col-sm-9">
					<div id='calendar'></div>
				</div>
				<div class="col-sm-3">
					<div class="row">
						<h3>${name}</h3>
					</div>
					<div class="row">
						<div class="col-sm-3">
							<div class="lan_lock"></div><div class="box_text">设为已租</div>
						</div>
						<div class="col-sm-3">
							<div class="order_lock"></div><div class="box_text">已出租</div>
						</div>
						<div class="col-sm-3">
							<div class="idol_lock"></div><div class="box_text">未出租</div>
						</div>
						<div class="col-sm-3">
							<div class="sys_lock"></div><div class="box_text">系统屏蔽</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!--锁定房源信息-->
	<div class="modal inmodal fade" id="setCalendarModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<div class="row">
										<div class="col-sm-2 text-right" style="margin-top: 5px;">
											<label class="control-label">开始时间:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="startDate" readonly="readonly" />
										</div>

										<div class="col-sm-2 text-right" style="margin-top: 5px;">
											<label class="control-label">结束时间:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="endDate" readonly="readonly" />
										</div>
									</div>
									<div class="row" style="margin-top: 5px;">
										<div class="col-sm-2 text-right" style="margin-top: 5px;">
											<label class="control-label">设置方式:</label>
										</div>
										<div class="col-sm-3">
											<select class="form-control" id="lockType">
												<option value="1">锁定</option>
												<option value="0">解锁</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom:10px;">
					<div class="col-sm-6 text-right">
						<button type="button" id="saveLockDate" class="btn btn-success btn-sm">保存</button>
					</div>
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-sm" id="modalClose" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="house_fid" value="${houseFid}">
	<input type="hidden" id="room_fid" value="${roomFid}">
	<input type="hidden" id="rent_way" value="${rentWay}">

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>

	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- fullcalendar Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<script src='${staticResourceUrl}/js/plugins/fullcalendar/moment.min.js'></script>
	<script src='${staticResourceUrl}/js/plugins/fullcalendar/fullcalendar.min.js'></script>
	<script src='${staticResourceUrl}/js/plugins/fullcalendar/zh-cn.js'></script>
	<script>
		$(document).ready(function() {

			$('#calendar').fullCalendar({
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month'
				},
				editable: true,
				selectable : true,
				events:{
					url:"/house/houseCalendar/getHouseCalendarData",
					type:"get",
					data:{
						houseFid:$("#house_fid").val(),
						roomFid:$("#room_fid").val(),
						rentWay:$("#rent_way").val()
					},
					error:function(){
						alert('there was an error while fetching events!');
					},
					color:"f3f3f4",
					textColor:"black"
				},
				select:function (start, end, jsEvent, view) {
					var startDate = moment(start._d).format('L');
					var endDate = moment(end._d).add(-1,'days').format('L');
					var current = moment(new Date()).format("L");
					//小于今天直接不处理
					if (startDate < current){
						return;
					}

					$("#startDate").val(startDate);
					$("#endDate").val(endDate);
					$('#setCalendarModal').modal('show');
				}

			});
			var flag = false;

			//锁定房源
			$("#saveLockDate").on('click',function () {
				if (flag){
					return;
				}
				flag = true;
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				if (startDate == "" || endDate == ""){
					return;
				}
				var houseFid = $("#house_fid").val();
				var roomFid = $("#room_fid").val();
				var rentWay = $("#rent_way").val();
				var lockType = $("#lockType").val();
				var param = {
					startDate:startDate,
					endDate:endDate,
					houseFid:houseFid,
					roomFid:roomFid,
					rentWay:rentWay,
					lockType:lockType
				}
				$.post("/house/houseCalendar/lockHouse",param,function (data) {
					flag = false;
					$('#setCalendarModal').modal('hide');
					$("#startDate").val("");
					$("#endDate").val("");
					if (data.code == 0){
						//锁定成功以后重新刷新当月的视图
						$('#calendar').fullCalendar("refetchEvents");
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},'json');
			});

		});

	</script>
</body>
</html>
