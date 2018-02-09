<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

	<style type="text/css">
		.left2{
			margin-top: 10px;
		}
		.left3{padding-left:62px;}
		.btn1
		{
			border-right: #7b9ebd 1px solid;
			padding-right: 2px;
			border-top: #7b9ebd 1px solid;
			padding-left: 2px;
			font-size: 12px;
			FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,  StartColorStr=#ffffff,  EndColorStr=#cecfde);
			border-left: #7b9ebd 1px solid;
			cursor: hand;
			padding-top: 2px;
			border-bottom: #7b9ebd 1px solid;
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
						<input name = "orderOprationType" id="orderOprationType" value="${orderOprationType }" type="hidden"/>
						<div class="form-group">
							<div class="row">
									<label class="col-sm-1 control-label left2">预订人姓名：</label>
									<div class="col-sm-2 left2">
										<input id="userName" name="userName"  type="text" class="form-control" >
									</div>
									<label class="col-sm-1 control-label left2">房源别名：</label>
									<div class="col-sm-2 left2">
										<input id="houseName" name="houseName"  type="text" class="form-control" >
									</div>
									<label class="col-sm-1 control-label left2">预订人手机号：</label>
									<div class="col-sm-2 left2">
										<input id="userTel" name="userTel"  type="text" class="form-control" >
									</div>
									<label class="col-sm-1 control-label left2">订单状态：</label>
									<div class="col-sm-2 left2">
										<c:if test="${orderOprationType == 1 }">
											<select class="form-control" id="orderStatus" name="orderStatus" >
												<option value="">全部</option>
												<option value="10">待确认 </option>
												<option value="20">待入住</option>
												<option value="30">强制取消</option>
												<option value="31">房东已拒绝</option>
												<option value="35">房东未确认超时取消</option>
												<option value="36">未确认取消</option>
												<option value="32">房客取消 </option>
												<option value="33">未支付超时取消 </option>
												<%--<option value="34">强制取消申请中</option>--%>
												<option value="35">房东未确认超时取消</option>
												<option value="36">未确认取消</option>
												<option value="37">协商取消</option>
												<option value="38">房东申请取消</option>
												<option value="72">协商退房</option>
												<option value="40">已入住未生成单据</option>
												<option value="41">已入住已生成单据</option>
												<option value="50">待房东确认额外消费</option>
												<option value="51">提前退房待房东确认额外消费</option>
												<option value="60">待房客确认额外消费</option>
												<option value="61">提前退房待房客确认额外消费</option>
												<option value="70">正常退房完成</option>
												<option value="71">提前退房完成</option>
												<option value="72">客服协商退房</option>
												<option value="73">房东申请退房</option>
											</select>
										</c:if>
										<c:if test="${orderOprationType == 2 }">
											<select class="form-control" id="orderStatus" name="orderStatus" >
												<option value="">全部</option>
												<option value="30">强制取消</option>
												<option value="32">房客取消 </option>
												<option value="33">未支付超时取消 </option>
												<option value="34">强制取消申请中</option>
											</select>
										</c:if>
									</div>
							</div>
							<div class="row">
								<label class="col-sm-1 control-label left2">订单编号：</label>
								<div class="col-sm-2 left2">
									<input id="orderSn" name="orderSn"  type="text" class="form-control"  >
								</div>
								<label class="col-sm-1 control-label left2">创建时间：</label>
								<div class="col-sm-2 left2">
									<input id="createTimeStart"  value=""  name="createTimeStart" class="laydate-icon form-control layer-date">
								</div>
								<label class="col-sm-1 control-label left2" style="text-align: center;">到</label>
								<div class="col-sm-2 left2">
									<input id="createTimeEnd" value="" name="createTimeEnd" class="laydate-icon form-control layer-date">
								</div>
								<label class="col-sm-1 control-label left2">房东姓名：</label>
								<div class="col-sm-2 left2">
									<input id="landlordName" name="landlordName"  type="text" class="form-control" >
								</div>
							</div>
							<div class="row">
								<label class="col-sm-1 control-label left2">国家：</label>
								<div class="col-sm-2 left2">
									<select class="form-control m-b m-b" id="nationCode" onchange="CommonUtils.getNationInfo('provinceCode',this);">
									</select>
								</div>
								<label class="col-sm-1 control-label left2">省：</label>
								<div class="col-sm-2 left2">
									<select class="form-control m-b m-b" id="provinceCode" onchange="CommonUtils.getNationInfo('cityCode',this);">
									</select>
								</div>

								<label class="col-sm-1 control-label left2">房源城市：</label>
								<div class="col-sm-2 left2">
									<select class="form-control m-b m-b" id="cityCode" >
									</select>
								</div>
								<label class="col-sm-1 control-label left2">支付状态：</label>
								<div class="col-sm-2 left2">
									<select class="form-control" id="payStatus" name="payStatus" >
										<option value="">全部</option>
										<option value="0">未支付</option>
										<option value="1">已支付</option>
									</select>
								</div>
							</div>
							<div class="row">
								<label class="col-sm-1 control-label left2">运营专员：</label>
								<div class="col-sm-2 left2">
									<input id="empGuardName" name="empGuardName" type="text" class="form-control">
								</div>
								<label class="col-sm-1 control-label left2">结算状态：</label>
								<div class="col-sm-2 left2">
									<select class="form-control" id="accountsStatus" name="accountsStatus" >
										<option value="">请选择</option>
										<option value="0">未结算</option>
										<option value="1">结算中</option>
										<option value="2">结算完</option>
									</select>
								</div>
								<label class="col-sm-1 control-label left2">订单类型：</label>
								<div class="col-sm-2 left2">
									<select class="form-control" id="orderType" name="orderType" >
										<option value="">请选择</option>
										<option value="1">立即预订</option>
										<option value="2">申请预订</option>
									</select>
								</div>
								<label class="col-sm-1 control-label left2">房源编号：</label>
								<div class="col-sm-2 left2">
									<input id="houseSn" name="houseSn" type="text" class="form-control">
								</div>

							</div>
							<div class="row">
								<label class="col-sm-1 control-label left2">入住时间：</label>
								<div class="col-sm-2 left2">
									<input id="checkInTimeStart"  value=""  name="checkInTimeStart" class="laydate-icon form-control layer-date">
								</div>
								<label class="col-sm-1 control-label left2" style="text-align: center;">到</label>
								<div class="col-sm-2 left2">
									<input id="checkInTimeEnd" value="" name="checkInTimeEnd" class="laydate-icon form-control layer-date">
								</div>
								<label class="col-sm-1 control-label left2">退房时间：</label>
								<div class="col-sm-2 left2">
									<input id="checkOutTimeStart"  value=""  name="checkOutTimeStart" class="laydate-icon form-control layer-date">
								</div>
								<label class="col-sm-1 control-label left2" style="text-align: center;">到</label>
								<div class="col-sm-2 left2">
									<input id="checkOutTimeEnd" value="" name="checkOutTimeEnd" class="laydate-icon form-control layer-date">
								</div>
							</div>
                            <div class="row">
                                <label class="col-sm-1 control-label left2">订单活动：</label>
                                <div class="col-sm-2 left2">
                                    <select class="form-control" id="orderActityType" name="orderActityType" >
                                        <option value="">全部</option>
                                        <option value="1">房东的折扣活动</option>
                                        <option value="2">优惠券</option>
                                        <option value="3">房东免佣金</option>
                                        <option value="4">房东设置的折扣</option>
                                        <option value="5">房客享受灵活定价</option>
                                        <option value="6">今夜特价</option>
                                        <option value="7">首单立减</option>
                                    </select>
                                </div>
                            </div>
							<div class="row">
								<div class="col-sm-6">
									<div class="col-sm-1 left2 left2">
										<button class="btn btn-primary" type="button" onclick="CommonUtils.query();"><i class="fa fa-search"></i>&nbsp;查询</button>
									</div>
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
				<div class="col-sm-13">
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered table-hover" id="listTable" data-click-to-select="true"
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
								   data-query-params="Order.paginationParam"
								   data-method="post"
								   data-single-select="true"
								   data-height="470"
								   data-url="order/orderDataList">
								<thead>
								<tr class="trbg">
									<th data-field="fid" data-visible="false"></th>
									<th data-field="orderSn"  data-width="5%" data-align="center" data-formatter="CommonUtils.nameShow">订单号</th>
									<th data-field="houseName" data-width="10%" data-align="center">房源别名</th>
									<th data-field="nationCode" data-width="5%" data-align="center">国家</th>
									<th data-field="cityCode" data-width="5%" data-align="center">城市</th>
									<th data-field="userName" data-width="5%" data-align="center">预订人姓名</th>
									<th data-field="userTel" data-width="10%" data-align="center">预定人手机号</th>
									<th data-field="landlordName" data-width="5%" data-align="center">房东姓名</th>
									<th data-field="landlordTel" data-width="10%" data-align="center">房东手机号</th>
									<th data-field="orderType" data-width="10%" data-align="center" data-formatter="CommonUtils.getOrderType">订单类型</th>
									<th data-field="empGuardName" data-width="10%" data-align="center">运营专员</th>
									<th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
									<th data-field="payStatus" data-width="2%" data-align="center" data-formatter="showPayStatus">支付状态</th>
									<th data-field="orderType" data-width="2%" data-align="center" data-formatter="showOrderType">订单类型</th>
									<th data-field="sumMoney" data-width="5%" data-align="center" data-formatter="CommonUtils.getMoney">订单金额(元)</th>
									<th data-field="realMoney" data-width="3%" data-align="center" data-formatter="CommonUtils.getMoney">实际成交金额(元)</th>
									<th data-field="reduceMoney" data-width="3%" data-align="center" data-formatter="CommonUtils.getMoney">优惠金额(元)</th>
									<c:if test="${orderOprationType == 2 }">
										<th data-field="lastModifyDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">强制取消申请日期</th>
									</c:if>
									<th data-field="orderStatus" data-width="10%" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
									<c:if test="${orderOprationType == 2 }">
										<th data-field="orderStatus" data-align="center"  data-width="10%" data-formatter="Order.menuOperData">操作</th>
									</c:if>
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

<!-- 新旧订单关联弹出层 -->
<div class="modal" id="mymodal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content ">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
				<h4 class="modal-title">同意选择</h4>
			</div>
			<div class="modal-body">
				<select class="form-control m-b"  id="orderCancelType" >
					<option value="1">退钱</option>
					<option value="2">换房</option>
				</select>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary"  onclick="Order.agreeCancelOrder()">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- 添加 菜单  弹出框 订单详情 -->
<div class="modal inmodal fade" id="orderDetail" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content animated bounceInRight">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
				</button>
				<h4 class="modal-title">订单详情</h4>
			</div>
			<div class="modal-body">
				<div class="wrapper wrapper-content animated fadeInRight">
					<div class="row">
						<div class="col-sm-14">
							<div class="ibox float-e-margins">
								<div class="ibox-content">
									<form id="orderDetailForm" action="" method="post">

									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer" style="text-align: center;">
				<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
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
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>

<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/order/order.js${VERSION}005" type="text/javascript"></script>




<script type="text/javascript">
	function showPayStatus(value, row, index){
		if(value == "1"){
			return "已支付";
		}else{
			return "未支付";
		}
	}

	function showOrderType(value, row, index){
		if(value == "1"){
			return "立即预订";
		}else if (value == "2"){
			return "申请预订";
		}

	}



	//   //点击单元格 查看订单详情
	//   $('#listTable').on('click-cell.bs.table',
	//		   function(field, value, row, $element) {
	//			   if(value == "fid"){
	//				   var orderSn = $element.orderSn;
	//				   getOrderDetail(orderSn);
	//			   }
	//		   });



</script>
</body>

</html>
