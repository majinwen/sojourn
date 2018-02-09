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
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style>
.red-row {
	background-color: #F44336;
	color : #0D1017;
}
.green-row {
	background-color: #1CDDB1;
	color : #0D1017;
}


</style>
</head>
<body class="gray-bg" >
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<font color="red">房东未回复的申请预定：</font>
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam1" data-method="post"
								data-row-style="dataRowStyle1"
								data-height="520"
								data-single-select="true" data-url="customerserve/getNotReplyList">
								<thead>
									<tr>
										<th data-field="orderSn" data-width="11%" data-align="center">订单编号</th>
										<th data-field="houseName" data-width="13%" data-align="center" data-formatter="formatContent">房源名称</th>
										<th data-field="cityName" data-width="5%" data-align="center">城市</th>
										<th data-field="userName" data-width="10%" data-align="center">预订人姓名</th>
                                        <th data-field="userTel" data-width="10%" data-align="center">预定人手机号</th>
                                        <th data-field="landlordName" data-width="10%" data-align="center">房东姓名</th>
                                        <th data-field="landlordTel" data-width="10%" data-align="center">房东手机号</th>
                                        <th data-field="startTime" data-width="11%" data-align="center" data-formatter="CommonUtils.formateDate">入住时间</th>
                                        <th data-field="endTime" data-width="11%" data-align="center" data-formatter="CommonUtils.formateDate">离开时间</th>
										<th data-field="notReplyTime" data-width="10%" data-align="center">房东未回复时间</th>
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
	
	
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<font color="red">房东拒绝的申请预定（12小时以内）：</font>
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable2"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam2" data-method="post"
								data-row-style="dataRowStyle2"
								data-height="520"
								data-single-select="true" data-url="customerserve/getRefuseOrderList">
								<thead>
									<tr>
										<th data-field="orderSn" data-width="13%" data-align="center">订单编号</th>
										<th data-field="houseName" data-width="13%" data-align="center" data-formatter="formatContent">房源名称</th>
										<th data-field="cityName" data-width="11%" data-align="center">城市</th>
										<th data-field="userName" data-width="11%" data-align="center">预订人姓名</th>
                                        <th data-field="userTel" data-width="11%" data-align="center">预定人手机号</th>
                                        <th data-field="landlordName" data-width="11%" data-align="center">房东姓名</th>
                                        <th data-field="landlordTel" data-width="11%" data-align="center">房东手机号</th>
										<th data-field="lastModifyDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">房东拒绝时间</th>
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
	
	
	
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<font color="red">房东未回复的IM记录（1小时以内）：</font>
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable3"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam3" data-method="post"
								data-row-style="dataRowStyle3"
								data-height="520"
								data-single-select="true" data-url="customerserve/getNotReplyIMList">
								<thead>
									<tr>
										<th data-field="houseName" data-width="13%" data-align="center" data-formatter="formatTitle">房源名称</th>
										<th data-field="cityName" data-width="11%" data-align="center">城市</th>
										<th data-field="tenantName" data-width="11%" data-align="center">预订人姓名</th>
                                        <th data-field="tenantTel" data-width="11%" data-align="center">预定人手机号</th>
                                        <th data-field="landlordName" data-width="11%" data-align="center">房东姓名</th>
                                        <th data-field="landlordTel" data-width="11%" data-align="center">房东手机号</th>
                                        <th data-field="msgContent" data-width="11%" data-align="center" data-formatter="formatContent">消息内容</th>
                                        <th data-field="notReplyTime" data-width="11%" data-align="center">房东未回复时间</th>
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
	<div class="modal inmodal fade" id="showMsgChat" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:10px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="wrapper wrapper-content animated fadeInRight" style="margin-top: 15px;">
					<div class="row">
						<div class="col-sm-12">
							<div class="ibox chat-view">
								<div class="ibox-title">
									聊天窗口
								</div>
								<div class="ibox-content">
									<div class="row">
										<div class="col-md-12 ">
											<div class="chat-discussion" id="chat-discussion">

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
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	
	<script>
	function myrefresh(){ 
		window.location.reload(); 
	} 
	setTimeout('myrefresh()',1000*60*5); //指定5分钟刷新一次 

	//分页查询参数
	function paginationParam1(params) {
		return {
			limit : params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber
		};
	}
	//分页查询参数
	function paginationParam2(params) {
		return {
			limit : params.limit,
			page : $('#listTable2').bootstrapTable('getOptions').pageNumber
		};
	}
	//分页查询参数
	function paginationParam3(params) {
		return {
			limit : params.limit,
			page : $('#listTable3').bootstrapTable('getOptions').pageNumber
		};
	}
	
	
	function dataRowStyle1(row,index){
		// 未回复时间 = now-下单时间
		var time = (new Date() - row.createTime)/60/1000;
		
		// 红色：提醒时限+5 < 未回复时间
		var remindMinute = ${remindMinute};
		if(remindMinute + 5 < time){
			return { classes: 'red-row'};
		}
		// 绿色：5分钟<未回复时间<提醒时限+5
		if(5 < time && time < remindMinute+5){
			return { classes: 'green-row'};
		}
		return { classes: ''};
	}
	
	function dataRowStyle2(row,index){
		var time = (new Date() - row.lastModifyDate)/60/1000;
		
		// 红色：20分钟 < now-拒绝时间
		if(20 < time){
			return { classes: 'red-row'};
		}
		// 绿色：5分钟< now-拒绝时间 <20分钟
		if(5 < time && time < 20){
			return { classes: 'green-row'};
		}
		return { classes: ''};
	}
	
	function dataRowStyle3(row,index){
		// 未回复时间 = now-下单时间
		var time = (new Date() - row.createTime)/60/1000;
		
		// 红色：20分钟 < now-创建时间
		if(20 < time){
			return { classes: 'red-row'};
		}
		// 绿色：5分钟<now-创建时间<20分钟
		if(5 < time && time < 20){
			return { classes: 'green-row'};
		}
		return { classes: ''};
	}
	
	
	
	function formatContent(field, value, row){
		var str ='';
		try {
			if(field==null || field.length==0){
				field='';
			}
			if(field!=null && field.length > 20){
				field = field.substring(0,20)+"..."; 
			}
			str = "<p title='"+field+"'>"+field+"</p>";
		} catch (e) {
			console.log(e);
		}
		
		
		return str;
	}

	function formatTitle(field, value, row){
		var str ='';
		try {
			if(field==null || field.length==0){
				field='';
			}
			if(field!=null && field.length > 20){
				field = field.substring(0,20)+"...";
			}
			str = "<a href='javascript:void(0);' onclick='showChatList(\""+value.msgHouseFid+"\")' title='"+field+"'>"+field+"</a>";
		} catch (e) {
			console.log(value);
		}
		
		return str;
	}

	//展示会话列表
	function showChatList(houseMsgFid) {
		if (!houseMsgFid){
			return;
		}
		$("#chat-discussion").empty();
		$("#showMsgChat").modal("toggle");
		//查询聊天记录
		$.getJSON("customerserve/listBothChatInfo",{houseMsgFid:houseMsgFid},function (data) {
			if (data.code == 0){
				var list = data.data.list;
				$.each(list,function (i, n) {
					var vDate = new Date(n.msgSendTime);
					var dateStr = vDate.format("yyyy-MM-dd HH:mm:ss");
					if (n.msgSenderType == 20){
						var _msg = '<div class="chat-message message-left">'
									+'	<img class="message-avatar message-avatar-left" src="'+n.headPic+'" alt="">'
								    +'	<div class="message message-left">'
								    +'		<a class="message-author" href="javascript:;"> '+n.nickName+'</a>'
									+'		<span class="message-date message-date-left">'+dateStr+'</span>'
									+'		<span class="message-content">'+n.content+'</span>'
									+'	</div>'
									+'</div>';
						$("#chat-discussion").append(_msg);
					}

					if (n.msgSenderType == 10){
						var _msg = '<div class="chat-message message-right">'
								+'	<img class="message-avatar message-avatar-right" src="'+n.headPic+'" alt="">'
								+'	<div class="message message-right">'
								+'		<a class="message-author" href="javascript:;"> '+n.nickName+'</a>'
								+'		<span class="message-date message-date-right">'+dateStr+'</span>'
								+'		<span class="message-content">'+n.content+'</span>'
								+'	</div>'
								+'</div>';
						$("#chat-discussion").append(_msg);
					}

				});

			}else{
				layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
			}
		});
	}
	
	</script>
</body>
</html>
