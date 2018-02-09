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
<div class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">罚款单号:</label>
							</div>
							<div class="col-sm-2">
								<input id="penaltySn" name="penaltySn" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单号:</label>
							</div>
							<div class="col-sm-2">
								<input id="orderSn" name="orderSn" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="penaltyStatus" id="penaltyStatus">
									<option value="">请选择</option>
									<option value="0">未清</option>
									<option value="10">清还中</option>
									<option value="20">已清</option>
									<option value="30">作废</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordName" name="landlordName" type="text" class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东手机:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordPhone" name="landlordPhone" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">创建时间:</label>
							</div>
							<div class="col-sm-2">
								<input id="createDateStart" name="createDateStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">到:</label>
							</div>
							<div class="col-sm-2">
								<input id="createDateEnd" name="createDateEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">修改时间:</label>
							</div>
							<div class="col-sm-2">
								<input id="modifyDateStart" name="modifyDateStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">到:</label>
							</div>
							<div class="col-sm-2">
								<input id="modifyDateEnd" name="modifyDateEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-2">
								<customtag:authtag authUrl="penalty/dataList">
									<button class="btn btn-primary" type="button" onclick="CommonUtils.query();">
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
					<customtag:authtag authUrl="penalty/cancelPenalty">
						<button class="btn btn-primary " type="button" onclick="cancelShow();">
							<i class="fa fa-plus"></i>&nbsp;作废
						</button>
					</customtag:authtag>
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable" data-single-select="false" data-toggle="table" data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true" data-page-size="10" data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页" data-content-type="application/x-www-form-urlencoded" data-query-params="paginationParam"
								data-method="post" data-height="760" data-single-select="true" data-url="penalty/dataList">
								<thead>
									<tr>
										<th data-field="id" data-width="1%" data-radio="true"></th>
										<th data-field="penaltySn" data-width="20%" data-align="center">罚款单</th>
										<th data-field="orderSn" data-width="10%" data-align="center">订单号</th>
										<th data-field="landlordName" data-width="10%" data-align="center">房东名称</th>
										<th data-field="landlordTel" data-width="10%" data-align="center">房东电话</th>
										<th data-field="penaltyStatus" data-width="10%" data-align="center" data-formatter="handStatus">状态</th>
										<th data-field="penaltyFee" data-width="6%" data-align="center" data-formatter="CommonUtils.getMoney">罚款金额</th>
										<th data-field="penaltyLastFee" data-width="6%" data-align="center" data-formatter="CommonUtils.getMoney">剩余金额</th>
										<th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">生成时间</th>
										<th data-field="lastModifyDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">修改时间</th>
										<th data-field="penaltySn" data-width="5%" data-align="center" data-formatter="detailBtn">明细</th>
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
	<!--弹出框 -->
	<div class="modal fade" id="cancelShowModal" tabindex="-1" role="dialog"  aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-1">
							<label class="control-label">备注:</label>
						</div>
						<div class="col-sm-10">
							<textarea id="cancelRemark" rows="5" cols="60"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer" style="text-align: center">
					<button type="button" class="btn btn-primary" id="applyButton" onclick="doCancel();">确认作废</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div>

	<div class="modal fade" id="showDetailModal" tabindex="-1" role="dialog"  aria-hidden="true">
		<div class="modal-dialog" style="width:1000px">
			<div class="modal-content" style="width:1000px;">
				<div class="modal-body">
					<div class="row">
						<table class="table table-bordered">
							<thead>
								<tr>
									<th>原付款单号</th>
									<th>新付款单号</th>
									<th>付款单订单号</th>
									<th>扣款金额</th>
									<th>收入编号</th>
									<th>操作时间</th>
								</tr>
							</thead>
							<tbody id="tbody_id">

							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer" style="text-align: center">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js?${VERSION}"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
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
		CommonUtils.datePickerFormat('createDateStart');
		CommonUtils.datePickerFormat('createDateEnd');
		CommonUtils.datePickerFormat('modifyDateStart');
		CommonUtils.datePickerFormat('modifyDateEnd');
	});
	
	
	//分页查询参数
	function paginationParam(params) {
		return {
			limit : params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			penaltySn : $.trim($('#penaltySn').val()),
			orderSn : $.trim($('#orderSn').val()),
			penaltyStatus : $('#penaltyStatus option:selected').val(),
			landlordName : $.trim($('#landlordName').val()),
			landlordTel : $.trim($('#landlordPhone').val()),
			createDateStart : $.trim($('#createDateStart').val()),
			createDateEnd : $.trim($('#createDateEnd').val()),
			modifyDateStart : $.trim($('#modifyDateStart').val()),
			modifyDateEnd : $.trim($('#modifyDateEnd').val())
		};
	}

	function detailBtn(value, row, index) {
		if (row.penaltyStatus == 10 || row.penaltyStatus ==20){
			return "<button class='btn btn-info btn-xs' onclick='showDetail(\""+value+"\")'>详情</button>";
		}else{
			return "";
		}
	}

	function showDetail(penaltySn) {
		$("#showDetailModal").modal("show");
		$("#tbody_id").empty();
		$.getJSON("penalty/listRelDetail",{penaltySn:penaltySn},function (data) {
			if (data.code == 0){
				var list = data.data.list;
				var tr_str = "";
				$.each(list,function (i,n) {
					tr_str += "<tr>"
								+"<td>"+(n.parentPvSn == 0 ? "*":n.parentPvSn)+"</td>"
								+"<td>"+n.pvSn+"</td>"
								+"<td>"+n.pvOrderSn+"</td>"
								+"<td>"+n.totalFee/100.0+"</td>"
								+"<td>"+n.incomeSn+"</td>"
								+"<td>"+CommonUtils.formateTimeStr(n.createTime)+"</td>"
							+"</tr>";
				});
				$("#tbody_id").append(tr_str);
			}
		});
	}

	function handStatus(value, row, index) {
		var txt = "";
		switch (value){
			case 0:	txt="未清";break;
			case 10: txt="清还中";break;
			case 20: txt="已清";break;
			case 30: txt="作废";break;
		}
		return txt;
	}
	
	//取消罚款单
	function cancelShow(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {icon : 5, time : 2000});
			return;
		}
		if (rows[0].penaltyStatus != 0){
			layer.alert("只能作废未清罚款单", {icon : 5,time : 2000});
			return;
		}
		$('#cancelShowModal').modal("show");

	}
	function doCancel() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		var remark = $.trim($("#cancelRemark").val());
		if(!remark){
			layer.alert("请填写备注", {icon : 5, time : 2000});
			return;
		}
		if(remark.length > 100){
			layer.alert("备注长度100字以内！", {icon: 5,time: 2000, title:'提示'});
            return;
		}
		$('#applyButton').attr("disabled","disabled");
		$.ajax({
			url:"penalty/cancelPenalty",
			data:{
				penaltySn : rows[0].penaltySn,
				remark : remark
			},
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#cancelShowModal').modal('hide');
					layer.alert("取消成功", {icon: 6,time: 0, title:'提示'});
					$('#listTable').bootstrapTable('selectPage', 1);
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
				}
				$('#applyButton').removeAttr("disabled");
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 3000, title:'提示'});
				$('#applyButton').removeAttr("disabled");
			}
		});

	}



	
	</script>
</body>
</html>
