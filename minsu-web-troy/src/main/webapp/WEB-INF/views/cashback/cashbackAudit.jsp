<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com"%>
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
.modal-lm {
	min-width: 880px;
}
.row {
	margin: 0;
}
#operList td {
	font-size: 12px;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">返现单号:</label>
							</div>
							<div class="col-sm-2">
								<input id="cashbackSn" name="cashbackSn" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单号:</label>
							</div>
							<div class="col-sm-2">
								<input id="orderSn" name="orderSn" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">返现单状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="cashbackStatus" id="cashbackStatus">
									<option value="">全部</option>
									<option value="10">初始</option>
									<option value="20">已审核</option>
									<option value="30">已驳回</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">返现项目:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="actSn" id="actSn">
									<option value="">全部</option>
									<c:forEach items="${acts}" var="act" varStatus="s">
										<option value="${act.key}">${act.value}</option>
								　　</c:forEach>
								</select>
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人角色:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="receiveType" id="receiveType">
									<option value="">全部</option>
									<option value="1">房东</option>
									<option value="2">房客</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人UID:</label>
							</div>
							<div class="col-sm-2">
								<input id="receiveUid" name="receiveUid" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人:</label>
							</div>
							<div class="col-sm-2">
								<input id="receiveName" name="receiveName" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">联系电话:</label>
							</div>
							<div class="col-sm-2">
								<input id="receiveTel" name="receiveTel" type="text" class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">审核/驳回日期:</label>
							</div>
							<div class="col-sm-2">
								<input id="operTimeStart" name="operTimeStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">到:</label>
							</div>
							<div class="col-sm-2">
								<input id="operTimeEnd" name="operTimeEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">返现单新建日期:</label>
							</div>
							<div class="col-sm-2">
								<input id="createTimeStart" name="createTimeStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">到:</label>
							</div>
							<div class="col-sm-2">
								<input id="createTimeEnd" name="createTimeEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
						</div>
						
						<div class="row m-b">
							<div class="col-sm-1 text-right">
							</div>
							<div class="col-sm-2">
							<customtag:authtag authUrl="cashback/querAuditList">
								<button class="btn btn-primary" type="button" onclick="queryData();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</customtag:authtag>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<customtag:authtag authUrl="cashback/audit">
						<button class="btn btn-primary " type="button" onclick="auditShow();">
							<i class="fa fa-plus"></i>&nbsp;审核
						</button>
					</customtag:authtag>
					<customtag:authtag authUrl="cashback/reject">
						<button class="btn btn-info" type="button" onclick="rejectShow();">
							<i class="fa fa-edit"></i>&nbsp;驳回
						</button>
					</customtag:authtag>
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable" data-click-to-select="true" data-toggle="table" data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20]" data-pagination="true" data-page-size="10" data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页" data-content-type="application/x-www-form-urlencoded" data-query-params="paginationParam"
								data-method="post" data-height="760" data-single-select="false" data-url="cashback/querAuditList">
								<thead>
									<tr>
										<th data-field="id" data-checkbox="true"></th>
										<th data-field="orderSn" data-align="center">订单号</th>
										<th data-field="cashbackSn" data-align="center">返现单号</th>
										<th data-field="cashbackStatus" data-align="center" data-formatter="formateActivityType">返现单状态</th>
										<th data-field="actName" data-align="center">返现项目</th>
										<th data-field="totalFee" data-align="center" data-formatter="CommonUtils.getMoneyStr">返现金额</th>
										<th data-field="receiveType" data-align="center" data-formatter="CommonUtils.receiveType">角色</th>
										<th data-field="receiveUid" data-align="center">收款人uid</th>
										<th data-field="isCustomerBlack" data-align="center" style="color:red">是否在民宿黑名单</th>
										<th data-field="receiveName" data-align="center">收款人</th>
										<th data-field="receiveTel" data-align="center">收款电话</th>
										<th data-field="pvSn" data-align="center">付款单号</th>
										<th data-field="log" data-align="center" data-formatter="formateLog">日志</th>
										<th data-field="applyRemark" data-align="center" data-formatter="formatContent">备注</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
					<div align="right"><font size="5px"><label id="sumFee"></label></font></div>
				</div>
			</div>
		</div>
	</div>
	<!--弹出框 -->
	<div class="modal inmodal fade" id="auditShowModel" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding: 5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<input type="hidden" id="uid" value="">
				<div class="ibox-content" style="border: none; padding-bottom: 0;" align="center">
					<strong><font size="5px"><label id="auditMsg"></label></font></strong>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-6 text-right">
						<customtag:authtag authUrl="cashback/audit">
							<button type="button" class="btn btn-success btn-sm" id="auditButton" onclick="audit();">确认</button>
						</customtag:authtag>
					</div>
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-sm" id="modalClose" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--弹出框 -->
	<div class="modal inmodal fade" id="rejectShowModel" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding: 5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<input type="hidden" id="uid" value="">
				<div class="ibox-content" style="border: none; padding-bottom: 0;" align="center">
					<strong><font size="5px"><label id="rejectMsg"></label></font></strong>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-6 text-right">
						<customtag:authtag authUrl="cashback/reject">
							<button type="button" class="btn btn-success btn-sm" id="rejectButton" onclick="reject();">确认</button>
						</customtag:authtag>
					</div>
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-sm" id="modalClose" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 弹出框 -->
	<div class="modal inmodal fade" id="logShowModel" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding: 5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				<input type="hidden" id="uid" value="">
				<div class="ibox-content" style="border: none; padding-bottom: 0;">
					<div class="row">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<div class="hr-line-dashed"></div>
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">日志:</label>
										</div>
										<div class="col-sm-10">
											<table id="operList" class="table table-hover">
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-6 text-right">
						<button type="button" class="btn btn-primary btn-sm" style="" id="modalClose" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js?${VERSION}001" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	<!-- layerDate plugin javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>

	<script>
	$(function (){
		//初始化日期
		CommonUtils.datePickerFormat('operTimeStart');
		CommonUtils.datePickerFormat('operTimeEnd');
		CommonUtils.datePickerFormat('createTimeStart');
		CommonUtils.datePickerFormat('createTimeEnd');
		querySumFee();
	});
	
	
	function queryData(){
		$('#listTable').bootstrapTable('selectPage', 1);
		querySumFee();
	}
	
	//查询金额合计
	function querySumFee(){
		$.ajax({
			url:"${basePath}cashback/getAuditCashbackSumFee",
			data: paginationParam(1),
			dataType:"json",
			type:"post",
			async: false,
			success:function(result) {
				if(result.code === 0){
					$('#sumFee').text("金额合计："+ CommonUtils.getMoneyStr(result.data.sumFee));
				}
			},
			error:function(result){
			}
		});
	}
	
	
	//分页查询参数
	function paginationParam(params) {
		return {
			limit : params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			cashbackSn : $.trim($('#cashbackSn').val()),
			orderSn : $.trim($('#orderSn').val()),
			cashbackStatus : $('#cashbackStatus option:selected').val(),
			actSn : $('#actSn option:selected').val(),
			receiveType : $('#receiveType option:selected').val(),
			receiveUid : $.trim($('#receiveUid').val()),
			receiveName : $.trim($('#receiveName').val()),
			receiveTel : $.trim($('#receiveTel').val()),
			operTimeStart : $('#operTimeStart').val(),
			operTimeEnd : $('#operTimeEnd').val(),
			createTimeStart : $('#createTimeStart').val(),
			createTimeEnd : $('#createTimeEnd').val()
		};
	}
	
	//返现审核弹框
	function auditShow(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请最少选择一条记录进行操作", {
				icon : 5,
				time : 2000,
				title : '提示'
			});
			return;
		}

		for(var i = 0; i < rows.length; i++){
		    var status = rows[i].cashbackStatus;
		    if (status != 10){
                layer.alert("当前选中的第"+ parseInt(i+1) +"行返现单不是初始状态", {icon: 5,time: 4000, title:'提示'});
                return;
			}
		}

		/* var i = 0;
		$.each(rows,function(index,row){
			if(row.cashbackStatus == 10){
				i++;
			}
		});
		if(i == 0){
			layer.alert("当前选中的返现单均不为初始状态", {icon: 5,time: 4000, title:'提示'});
			return;
		} */
		$('#auditMsg').text("是否确认给" + rows.length + "个用户打款？");
		
//		$('#auditMsg').text("是否确认给此用户打款？");
		//弹框
		$("#auditShowModel").modal("toggle");
	}
	
	
	
	//返现审核
	function audit(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请最少选择一条记录进行操作", { icon : 5, time : 3000, title : '提示' });
			return;
		}

		var params = {};
		var i = 0;
		$.each(rows,function(index,row){
			if(row.cashbackStatus != 10){
                layer.alert("当前选中的第"+ parseInt(index+1) +"行返现单不为初始状态", {icon: 5,time: 4000, title:'提示'});
                return;
			}else {
				params["cashbackSns["+ i++ +"]"] = row.cashbackSn;
			}
		});

        $('#auditMsg').text("是否确认给" + rows.length + "个用户打款？");

		//审核
		$('#auditButton').attr("disabled","disabled");
		$.ajax({
			url:"${basePath}cashback/audit",
			data:params,
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#auditShowModel').modal('hide');
					queryData();
					layer.alert("审核成功！", {icon: 6,time: 0, title:'提示'});
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
				}
				$('#auditButton').removeAttr("disabled");
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 3000, title:'提示'});
				$('#auditButton').removeAttr("disabled");
			}
		});
	}
	
	
	//驳回弹框
	function rejectShow(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请最少选择一条记录进行操作", {
				icon : 5,
				time : 2000,
				title : '提示'
			});
			return;
		}

        for(var i = 0; i < rows.length; i++){
            var status = rows[i].cashbackStatus;
            if (status != 10){
                layer.alert("当前选中的第"+ parseInt(i+1) +"行返现单不是初始状态", {icon: 5,time: 4000, title:'提示'});
                return;
            }
        }

        $('#rejectMsg').text("是否确认驳回" + rows.length + "个用户的申请？");
		//弹框
		$("#rejectShowModel").modal("toggle");
	}
	
	
	//返现驳回
	function reject(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请最少选择一条记录进行操作", {
				icon : 5,
				time : 3000,
				title : '提示'
			});
			return;
		}
		
		var params = {};
		var i = 0;
		$.each(rows,function(index,row){
		    if (row.cashbackStatus != 10){
                layer.alert("当前选中的第"+parseInt(index)+"行返现单不为初始状态", {icon: 5,time: 4000, title:'提示'});
                return;
			}else{
                params["cashbackSns["+ i++ +"]"] = row.cashbackSn;
			}
		});

        $('#rejectMsg').text("是否确认驳回" + rows.length + "个用户的申请？");

		//驳回
		$('#rejectButton').attr("disabled","disabled");
		$.ajax({
			url:"${basePath}cashback/reject",
			data:params,
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#rejectShowModel').modal('hide');
					queryData();
					layer.alert("驳回成功！", {icon: 6,time: 0, title:'提示'});
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
				}
				$('#rejectButton').removeAttr("disabled");
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 3000, title:'提示'});
				$('#rejectButton').removeAttr("disabled");
			}
		});
	}
	
	
	function formateActivityType(value, row, index){
		if(value == 10){
			return "初始";
		}
		if(value == 20){
			return "已审核";
		}
		if(value == 30){
			return "已驳回";
		}
		return "*";
	}
	
	
	function formateLog(value, row, index){
		return "<a href='javascript:logDetail(\""+row.cashbackSn+"\");'>详情</a>";
	}
	function logDetail(cashbackSn){
		$.ajax({
			url:"${basePath}cashback/getLog",
			data:{cashbackSn:cashbackSn},
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#logShowModel').modal('toggle');
					$("#operList").empty();
					$.each(result.data.logs,function(i,n) {
						var tr = "<tr><td>"
								+ (i+1)
								+ "</td><td>"
								+ CommonUtils.formateDate(n.createDate)
								+ "</td><td>"
								+ n.createId + formateOperStatus(n.toStatus) + "了返现"
								+ "</td>";
						$("#operList").append(tr);
					});
					
				}else{
					layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
				}
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
			}
		});
	}
	
	
	function formateOperStatus(value){
		if(value == 10){
			return "新建";
		}
		if(value == 20){
			return "审核";
		}
		if(value == 30){
			return "驳回";
		}
		return "*";
	}
	
	
	function formatContent(field, value, row){
		if(field.length > 8){
		    return "<p title='"+field+"'>"+field.substring(0,8)+"...</p>";
		}
		return "<p title='"+field+"'>"+field+"</p>";
	}
	
	</script>
</body>
</html>
