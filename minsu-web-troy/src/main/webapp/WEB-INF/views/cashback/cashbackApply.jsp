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

input[type=file]{
	height:38px;cursor: pointer;
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
								<label class="control-label" style="height: 35px; line-height: 35px;">订单号:</label>
							</div>
							<div class="col-sm-2">
								<input id="orderSn" name="orderSn" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源fid:</label>
							</div>
							<div class="col-sm-2">
								<input id="houseFid" name="houseFid" type="text" class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="orderStatus" id="orderStatus">
									<option value="70">正常退房完成</option>
								</select>
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付日期:</label>
							</div>
							<div class="col-sm-2">
								<input id="payDateStart" name="payDateStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">到:</label>
							</div>
							<div class="col-sm-2">
								<input id="payDateEnd" name="payDateEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="payStatus" id="payStatus">
									<option value="1">已支付</option>
								</select>
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预定开始日期:</label>
							</div>
							<div class="col-sm-2">
								<input id="createOrderDateStart" name="createOrderDateStart" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预定结束日期:</label>
							</div>
							<div class="col-sm-2">
								<input id="createOrderDateEnd" name="createOrderDateEnd" type="text" class="laydate-icon form-control layer-date">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;"></label>
							</div>
							<div class="col-sm-2">
								<customtag:authtag authUrl="cashback/querApplyList">
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
					<customtag:authtag authUrl="cashback/apply">
					    <div class="col-sm-1">
							<button class="btn btn-primary " type="button" onclick="applyShow();">
								<i class="fa fa-plus"></i>&nbsp;返现申请
							</button>
						</div>
						<div class="col-sm-1" style="width: 1%;">
						</div>		
						<div class="col-sm-1">
							<button class="btn btn-primary " type="button" id="button-import">
									<i class="fa fa-plus"></i>&nbsp;批量导入
							</button>
						</div>
					</customtag:authtag>
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable" data-click-to-select="true" data-toggle="table" data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true" data-page-size="10" data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页" data-content-type="application/x-www-form-urlencoded" data-query-params="paginationParam"
								data-method="post" data-height="760" data-single-select="true" data-url="cashback/querApplyList">
								<thead>
									<tr>
										<th data-field="id" data-width="1%" data-radio="true"></th>
										<th data-field="orderSn" data-align="center">订单号</th>
										<th data-field="orderStatus" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
										<th data-field="houseName" data-align="center">房源名称</th>
										<th data-field="houseFid" data-align="center">房源fid</th>
										<th data-field="payTime" data-align="center" data-formatter="CommonUtils.formateDate">支付日期</th>
										<th data-field="payMoney" data-align="center" data-formatter="CommonUtils.getMoney">支付金额</th>
										<th data-field="createOrderTime" data-align="center" data-formatter="CommonUtils.formateDate">预定日期</th>
										<th data-field="landlordName" data-align="center">房东</th>
										<th data-field="landlordTel" data-align="center">联系方式</th>
										<th data-field="userName" data-align="center">房客</th>
										<th data-field="userTel" data-align="center">联系方式</th>
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
	
	<input type="hidden" id="isCustomerBlack" value="${isCustomerBlack}">
	<input type="hidden" id="hasCashBackNum" value="${hasCashBackNum}">
	<!--弹出框 -->
	<div class="modal inmodal fade" id="applyShowModel" tabindex="-1" role="dialog" aria-hidden="true">
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
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">返现给:</label>
										</div>
										<div class="col-sm-3">
											<select class="form-control" name="s_receiveType" id="s_receiveType">
												<option value="1">房东</option>
												<option value="2">房客</option>
											</select>
										</div>
										<div class="col-sm-2 text-left">
											<label class="control-label" style="height: 35px; line-height: 35px;color:red" id="isCustomerBlackTitle">是否民宿黑名单:</label>
										</div>
										<div class="col-sm-2 text-left">
											<input class="form-control" id="s_isCustomerBlack" readonly="readonly" style="color:red"/>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">姓名:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="s_name" readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">联系电话:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="s_tel" readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">返现项目:</label>
										</div>
										<div class="col-sm-3">
											<select class="form-control" name="s_actSn" id="s_actSn">
												<c:forEach items="${acts}" var="act" varStatus="s">
													<option value="${act.key}">${act.value}</option>
											　　</c:forEach>
											</select>
										</div>
										
										<div class="col-sm-2 text-left">
											<label class="control-label" style="height: 35px; line-height: 35px;color:red">该活动已存在返现单:</label>
										</div>
										<div class="col-sm-2 text-left">
											<input class="form-control" id="s_hasCashBackNum" readonly="readonly" style="color:red"/>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">金额(元):</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="s_totalFee" onkeyup="value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" onblur="value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="8"/>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">备注:</label>
										</div>
										<div class="col-sm-11">
											<textarea id="s_remark" rows="3" style="width: 100%;" onkeyup="if(value.length>500) value=value.substr(0,500)" ></textarea>
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
						<customtag:authtag authUrl="cashback/apply">
							<button type="button" class="btn btn-success btn-sm" id="applyButton" onclick="apply();">保存</button>
						</customtag:authtag>
					</div>
					<div class="col-sm-6">
						<button type="button" class="btn btn-primary btn-sm" id="modalClose" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js?${VERSION}" type="text/javascript"></script>
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
	<!-- 上传excel -->
    <script src="${staticResourceUrl}/js/jquery_ocupload.js"></script>
	<script>
	$(function (){
		//初始化日期
		CommonUtils.datePickerFormat('payDateStart');
		CommonUtils.datePickerFormat('payDateEnd');
		CommonUtils.datePickerFormat('createOrderDateStart');
		CommonUtils.datePickerFormat('createOrderDateEnd');
	});
	
	
	//分页查询参数
	function paginationParam(params) {
		return {
			limit : params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			houseFid : $.trim($('#houseFid').val()),
			orderSn : $.trim($('#orderSn').val()),
			orderStatus : $('#orderStatus option:selected').val(),
			payStatus : $('#payStatus option:selected').val(),
			payDateStart : $('#payDateStart').val(),
			payDateEnd : $('#payDateEnd').val(),
			createOrderDateStart : $('#createOrderDateStart').val(),
			createOrderDateEnd : $('#createOrderDateEnd').val()
		};
	}
	
	//返现申请弹框
	function applyShow(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 5,
				time : 2000,
				title : '提示'
			});
			return;
		}
		var receiveUid = rows[0].landlordUid;
		$("#s_receiveType").change(function(){
			if($('#s_receiveType').val() == 1){
				$('#s_name').val(rows[0].landlordName);
				$('#s_tel').val(rows[0].landlordTel);
				receiveUid = rows[0].landlordUid;
				document.getElementById('isCustomerBlackTitle').innerHTML = '房东是否为黑名单';
			}
			if($('#s_receiveType').val() == 2){
				$('#s_name').val(rows[0].userName);
				$('#s_tel').val(rows[0].userTel);
				receiveUid = rows[0].userUid;
				document.getElementById('isCustomerBlackTitle').innerHTML = '房客是否为黑名单';
			}
			getCustomerBlackAndCashBackNum(receiveUid, actSn);
			return;
		});
		$('#s_receiveType').val(1);
		$('#s_name').val(rows[0].landlordName);
		$('#s_tel').val(rows[0].landlordTel);
		//$('#s_actSn').val("");
		$('#s_totalFee').val("");
		$('#s_remark').val("");
		var actSn = $("#s_actSn option:first").val();
		if($('#s_actSn option').length == 0){
			$('#applyButton').attr("disabled","disabled");
		}else{
			$('#applyButton').removeAttr("disabled");
		}
		$("#s_actSn").change(function(){
			var actSn = $("#s_actSn").val();
			if($('#s_receiveType').val() == 1){
				$('#s_name').val(rows[0].landlordName);
				$('#s_tel').val(rows[0].landlordTel);
				receiveUid = rows[0].landlordUid;
				document.getElementById('isCustomerBlackTitle').innerHTML = '房东是否为黑名单';
			}
			if($('#s_receiveType').val() == 2){
				$('#s_name').val(rows[0].userName);
				$('#s_tel').val(rows[0].userTel);
				receiveUid = rows[0].userUid;
				document.getElementById('isCustomerBlackTitle').innerHTML = '房客是否为黑名单';
			}
			getCustomerBlackAndCashBackNum(receiveUid, actSn);
			return;
		});
		//弹框
		$("#applyShowModel").modal("toggle");
		getCustomerBlackAndCashBackNum(receiveUid, actSn);
	}
	
	//1，查询uid是否为很名单  2，查询uid对应的"此次活动该用户已有X笔返现申请"
	function getCustomerBlackAndCashBackNum(uid,actSn ){
		if(uid == null || uid == ''){
			layer.alert("当前没有选中的uid", {icon: 5,time: 3000, title:'提示'});
			return;
		}
		if(actSn == null || actSn == ''){
			layer.alert("当前没有返现活动", {icon: 5,time: 3000, title:'提示'});
			return;
		}
		//申请
		//$('#applyButton').attr("disabled","disabled");
		$.ajax({
			url:"${basePath}cashback/getCustomerBlackAndCashBackNum",
			data:{
				actSn : actSn,
				uid : uid
			},
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#s_isCustomerBlack').val(result.data.isCustomerBlack);
					$('#s_hasCashBackNum').val(result.data.hasCashBackNum); 
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
				}
				$('#applyButton').removeAttr("disabled");
				return;
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 3000, title:'提示'});
				$('#applyButton').removeAttr("disabled");
				return;
			}
			
		});
	}
	
	
	//返现申请
	function apply(){
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 5,
				time : 3000,
				title : '提示'
			});
			return;
		}
		
		var receiveType = $('#s_receiveType').val();
		var receiveUid = '';
		if(receiveType == 1){
			receiveUid = rows[0].landlordUid;
		}else if(receiveType == 2){
			receiveUid = rows[0].userUid;
		}
		var actSn = $('#s_actSn').val();
		if(actSn == null || actSn == ''){
			layer.alert("当前没有返现活动", {icon: 5,time: 3000, title:'提示'});
			return;
		}
		var totalFee = $('#s_totalFee').val();
		if (totalFee == null || totalFee == '' || !totalFee.match(/\d+(\.\d{0,2})?/) || totalFee==0) {
			layer.alert("金额输入有误！", {icon: 5,time: 3000, title:'提示'});
			return;
		}
		
		//申请
		$('#applyButton').attr("disabled","disabled");
		$.ajax({
			url:"${basePath}cashback/apply",
			data:{
				orderSn : rows[0].orderSn,
				actSn : actSn,
				receiveType : receiveType,
				receiveUid : receiveUid,
				totalFee : Math.round(totalFee * 100), //金额精确到 分
				applyRemark : $('#s_remark').val()
			},
			dataType:"json",
			type:"post",
			async: true,
			success:function(result) {
				if(result.code === 0){
					$('#applyShowModel').modal('hide');
					layer.alert("新建返现单成功！返现单号为：<br>" + result.data.cashbackSn, {icon: 6,time: 0, title:'提示'});
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
	
	$("#button-import").upload({
        action:'${basePath}cashback/batchApplyCashBack', //表单提交的地址
        name:"file",
        onComplete:function (data) { //提交表单之后
           var varflag = eval("("+data+")");//  "1"+"2"
        	if(varflag.code == 0){
				layer.alert("返现单批量申请成功"+"</br>"+varflag.data.customerBlackList+"</br>"+varflag.data.noOrderList+"</br>"+varflag.data.upPayOrderList+"</br>"
						+varflag.data.receiveTypeErrorList+"</br>"+varflag.data.actSnErrorList+"</br>"+varflag.data.paramNullList
						+"</br>"+varflag.data.numGreateOneList+"</br>"+varflag.data.numGreateOneTenList, {icon: 6,time: 900000, title:'提示'});
			}else{
				layer.alert(varflag.msg, {icon: 5,time: 3000, title:'提示'});
			}
			$('#applyButton').removeAttr("disabled");
        },
        onSelect: function() {//当用户选择了一个文件后触发事件
            //当选择了文件后，关闭自动提交
            this.autoSubmit=false;
            //校验上传的文件名是否满足后缀为.xls或.xlsx
            var regex =/^.*\.(?:xls|xlsx)$/i;
            //this.filename()返回当前选择的文件名称 (ps：我使用这个方法没好使，自己写了一个获取文件的名的方法) $("[name = '"+this.name()+"']").val())
            //alert(this.filename());
            if(regex.test($("[name = '"+this.name()+"']").val())){
                //通过校验
                this.submit();
                layer.alert("每个耗费0.2秒，一共需要等多少秒？开动脑筋自己算一下吧！anyway，don't shut down me！", {icon: 6,time: 8000, title:'提示'});
            }else{
                //未通过
            	layer.alert("文件格式不正确，请上传以xls或者xlsx结尾的excel文件", {icon: 5,time: 6000, title:'提示'});
            }
        }
    });
	</script>
</body>
</html>
