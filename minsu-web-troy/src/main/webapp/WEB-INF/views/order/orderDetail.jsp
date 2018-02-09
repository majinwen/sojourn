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
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style>
.modal-lm {
	min-width: 880px;
}

.lightBoxGallery img {
	margin: 5px;
	width: 160px;
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
					<form class="form-group">
						<div class="hr-line-dashed"></div>
						<b >订单信息</b>
						<div class="hr-line-dashed"></div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.orderSn}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单状态:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.orderStatuChineseName} 【 ${detail.orderStatus} 】" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">下单时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预订人姓名:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">手机号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userTel}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预计入住时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">

							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">实际入住时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预计退房时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">


							</div>

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">实际退房时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.realEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">

							</div>

						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预订人uid:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userUid}" readonly="readonly">
							</div>
						</div>

						<div class="hr-line-dashed"></div>
						房源信息
						<div class="hr-line-dashed"></div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.landlordName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东手机号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.landlordTel}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源地址:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.houseAddr}" readonly="readonly">
							</div>

						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">出租类型:</label>
							</div>
							<div class="col-sm-2">
								<c:if test="${detail.rentWay == 1 }">
									<input type="text" class="form-control" value="分租" readonly="readonly">
								</c:if>
								<c:if test="${detail.rentWay  != 1 }">
									<input type="text" class="form-control" value="整租" readonly="readonly">
								</c:if>
							</div>

							<c:if test="${detail.rentWay == 1 }">
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房间名称:</label>
								</div>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="${detail.roomName}" readonly="readonly">
								</div>
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房间编号:</label>
								</div>
							</c:if>

							<c:if test="${detail.rentWay  != 1 }">
								<div class="col-sm-1 text-right">
									<label class="control-label" style="height: 35px; line-height: 35px;">房源名称:</label>
								</div>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="${detail.houseName}" readonly="readonly">
								</div>
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房源编号:</label>
								</div>
							</c:if>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.houseSn}" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">退订政策:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${checkOutRulesName}" readonly="readonly">
							</div>
						</div>



						<div class="hr-line-dashed"></div>
						订单金额
						<div class="row m-b" style="text-align: center">
							<div id="cityCheckBox" class="col-sm-11" >
								累计房租：【${all/100}元】  价格列表：
								<c:forEach items="${prices}" var="price">
									【${price.day} 】:${price.price/100}元
								</c:forEach>
							</div>
						</div>

						<div class="hr-line-dashed"></div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付方式:</label>
							</div>
							<div class="col-sm-2">
								<c:if test="${pay.payType == 1 }">
									<input type="text" class="form-control" value="银联支付" readonly="readonly">
								</c:if>
								<c:if test="${pay.payType == 2 }">
									<input type="text" class="form-control" value="支付宝" readonly="readonly">
								</c:if>
								<c:if test="${pay.payType == 3 }">
									<input type="text" class="form-control" value="微信IOS支付" readonly="readonly">
								</c:if>
								<c:if test="${pay.payType == 4 }">
									<input type="text" class="form-control" value="微信Android支付" readonly="readonly">
								</c:if>

							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付状态:</label>
							</div>
							<div class="col-sm-2">
								<c:if test="${detail.payStatus == 1 }">
									<input type="text" class="form-control" value="已支付" readonly="readonly">
								</c:if>
								<c:if test="${detail.payStatus != 1 }">
									<input type="text" class="form-control" value="未支付" readonly="readonly">
								</c:if>

							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">流水号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${pay.tradeNo}" readonly="readonly">
							</div>
						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">实际支付金额:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.payMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">押金:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.depositMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">清洁费:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.cleanMoney/100}" readonly="readonly">
							</div>
						</div>




						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">优惠券:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.couponMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付服务费:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userCommMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">折扣金额:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.discountMoney/100}" readonly="readonly">
							</div>
						</div>

						<div class="hr-line-dashed"></div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房租:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.rentalMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">其他消费:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.otherMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">违约金:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.penaltyMoney/100}" readonly="readonly">
							</div>
						</div>

						<div class="hr-line-dashed">退款</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">退款金额:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.refundMoney/100}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">【${name}】服务费:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${userComm/100}" readonly="readonly">
							</div>
						</div>
					</form>
			</div>
		</div>
	
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
					<div class="example-wrap">
						<div class="example">
							<h2 class="title-font">入住人信息</h2>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="20%">入住人姓名</th>
										<th width="20%">联系电话</th>
										<th width="20%">证件类型</th>
										<th width="40%">证件号</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${contacts}" var="usual">
										<tr>
											<td>${usual.conName}</td>
											<td>${usual.conTel}</td>
											<td>${usual.cardType}</td>
											<td>${usual.cardValue}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
			</div>
		</div>
	</div>

<!-- 订单活动信息 start -->
 <c:if test="${(listOrderAc)!= null && fn:length(listOrderAc) > 0}">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
					<div class="example-wrap">
						<div class="example">
							<h2 class="title-font">订单活动信息</h2>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="30%">活动名称</th>
										<th width="30%">活动类型</th>
										<th width="40%">活动优惠金额(金额单位：元)</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listOrderAc}" var="orderAc">
										<tr>
											<td>${orderAc.acName}</td>
											<td>
											<c:if test="${orderAc.acType ==1}">房东折扣</c:if>
											<c:if test="${orderAc.acType ==2}">优惠券</c:if>
											<c:if test="${orderAc.acType ==3}">房东免佣金</c:if>
											<c:if test="${orderAc.acType ==4}">房东设置的折扣</c:if>
											<c:if test="${orderAc.acType ==5}">房客享受灵活定价</c:if>
											<c:if test="${orderAc.acType ==6}">今夜特价</c:if>
											<c:if test="${orderAc.acType ==7}">首单立减</c:if>
											</td>
											<td>${orderAc.acMoney/100}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
			</div>
		</div>
	</div>
</c:if>
<!-- 订单活动信息 end -->

<!-- 订单优惠信息 start -->
<c:if test="${(listOrderSpecialOfferVo)!= null && fn:length(listOrderSpecialOfferVo) > 0}">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
					<div class="example-wrap">
						<div class="example">
							<h2 class="title-font">订单优惠信息</h2>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="50%">优惠名称</th>
										<th width="50%">优惠折扣</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listOrderSpecialOfferVo}" var="specialOffer">
										<tr>
											<td>${specialOffer.showName}</td>
											<td>${specialOffer.showVal}折</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
			</div>
		</div>
	</div>
</c:if>
<!-- 订单优惠信息  end -->
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
							<h2 class="title-font">订单操作日志</h2>
							<table class="table table-bordered">
								<thead>
								<tr>
									<th width="20%">操作时间</th>
									<th width="20%">初始状态</th>
									<th width="20%">变更为</th>
									<th width="40%">备注</th>
								</tr>
								</thead>
								<tbody>
								<c:forEach items="${logs}" var="log">
									<tr>
										<td><fmt:formatDate type="both" value="${log.createDate}" /></td>
										<td >${log.fromStatus}</td>
										<td >${log.toStatus}</td>
										<td >${log.mark}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- End Example Pagination -->
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
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>

	<script>

	$(function(){
		$(".feeItemCode").each(function(i,n){
			var val=$(this).text();
			$(this).text(getfeeItemStr(val))
		});
		$(".callStatus").each(function(i,n){
			var val=$(this).text();
			$(this).text(getCallStatus(val))
		});
	})
	
	function getfeeItemStr(value){
        var feeItemStr= "*";
        if(value == 1){
        	feeItemStr= "房租";
        }else if(value == 2){
        	feeItemStr= "押金";
        }else if(value == 3){
        	feeItemStr= "赔付款";
        }else if(value == 4){
        	feeItemStr= "违约金";
        }else if(value == 5){
        	feeItemStr= "用户结算";
        }else if(value == 6){
        	feeItemStr= "提现";
        }else if(value == 7){
        	feeItemStr= "额外消费";
        }
        return feeItemStr;
    }

	function test11(value) {
		alert(value);
		return 11;
	}
	
	function getCallStatus(value){
		var status="*";
		if(value == 0){
			status= "失败";
        }else if(value == 1){
        	status= "成功";
        }
		return status;
	}
	</script>
</body>
</html>
