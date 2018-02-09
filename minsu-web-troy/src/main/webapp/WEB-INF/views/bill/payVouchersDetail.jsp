<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">付款人:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.payName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">付款人uid:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.payUid}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">付款单来源:</label>
							</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" value="${detail.paySourceName}" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.receiveName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人uid:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.receiveUid}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款人角色:</label>
							</div>
							<div class="col-sm-3">
								<input type="text" class="form-control" value="${detail.receiveTypeName}" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款银行:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${bankcard.bankName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">收款账号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${bankcard.bankcardNo}" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">付款单编号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.pvSn}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单编号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.orderSn}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">创建人:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.createId}" readonly="readonly">
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
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="50%">费用项目</th>
										<th width="50%">金额</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${detail.detailList}" var="l">
										<tr>
											<td class="feeItemCode">${l.feeItemCode}</td>
											<td>${l.itemMoney/100}元</td>
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
	
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
							<h2 class="title-font">付款单记录</h2>
							<table class="table table-bordered">
								<thead>
									<tr>
										<th width="10%">时间</th>
										<th width="80%">说明（金额需除100）</th>
										<th width="10%">结果</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${detail.logList}" var="l">
										<tr>
											<td><fmt:formatDate type="both" value="${l.createDate}" /></td>
											<td>${l.resultMsg}</td>
											<td class="callStatus">${l.callStatus}</td>
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
