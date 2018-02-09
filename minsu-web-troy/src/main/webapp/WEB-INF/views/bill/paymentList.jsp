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
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001"rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001"rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">


<style type="text/css">
.left2{
    margin-top: 10px;
}
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
.modal-lm{
	width: 1200px;
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
				                   <label class="col-sm-2 control-label left2">客户姓名：</label>
				                   <div class="col-sm-2 left2">
				                       <input id="userName" name="userName"  type="text" class="form-control" >
				                   </div>
				                   <label class="col-sm-2 control-label left2">客户电话：</label>
	                               <div class="col-sm-2 left2">
				                       <input id="userTel" name="userTel"  type="text" class="form-control" >
				                   </div>
				                   <label class="col-sm-2 control-label left2">客户UID：</label>
	                               <div class="col-sm-2 left2">
				                       <input id="userUid" name="userTel"  type="text" class="form-control" >
				                   </div>
			           			</div>
			           			<div class="row">
				                   <label class="col-sm-2 control-label left2">收款单编号：</label>
				                   <div class="col-sm-2 left2">
				                       <input id="fid" name="fid"  type="text" class="form-control" >
				                   </div>
				                   <label class="col-sm-2 control-label left2">收款单来源：</label>
				                   <div class="col-sm-2 left2">
				                      <select class="form-control" id="sourceType" name="sourceType" >
				                           <option value="">全部</option>
	                                       <option value="1">房客</option>
	                                       <option value="2">房东</option>
	                                   </select>
				                   </div>
				                   <label class="col-sm-2 control-label left2">收款单类型：</label>
				                   <div class="col-sm-2 left2">
				                      <select class="form-control" id="paymentType" name="paymentType" >
				                           <option value="">全部</option>
	                                       <option value="1">订单 </option>
	                                       <option value="2">账单</option>
	                                       <option value="3">优惠券 </option>
	                                   </select>
				                   </div>
				                   
			           			</div>
			           			
			           			<div class="row">
				           		    <label class="col-sm-2 control-label left2">收款单状态：</label>
				                    <div class="col-sm-2 left2">
				                        <select class="form-control" id="paymentStatus" name="paymentStatus" >
				                           <option value="">全部</option>
	                                       <option value="1">未同步 </option>
	                                       <option value="2">同步成功</option>
	                                       <option value="3">同步失败 </option>
	                                    </select>
				                    </div>
		           					<label class="col-sm-2 control-label left2">订单编号：</label>
				                    <div class="col-sm-2 left2">
				                        <input id="orderSn" name="orderSn"  type="text" class="form-control"  >
				                   	</div>
				                 </div>
				                 <div class="">
			                        <label class="col-sm-2 control-label left2">实际付款日期：</label>
		                            <div class="col-sm-2 left2">
		                                <input id="actualStartTime"  value=""  name="actualStartTime" class="laydate-icon form-control layer-date">
		                            </div>
		                           <label class="col-sm-2 control-label left2" style="text-align: center;">到</label>
		                            <div class="col-sm-2 left2">
		                                <input id="actualEndTime" value=""   name="actualEndTime" class="laydate-icon form-control layer-date">
		                           </div>
			                    <button class="btn btn-primary" style="margin-top:10px;" type="button" onclick="CommonUtils.query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered" data-click-to-select="true"
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
			                    data-query-params="Bill.paymentParam"
			                    data-method="post"
			                    data-height="500"
			                    data-single-select="true" 
			                    data-url="bill/queryPaymentVo">                    
			                    <thead>
			                        <tr>
			                            <th data-field="paymentSn" data-width="10%" data-align="center" data-formatter="nameShow">收款单编号</th>
			                            <th data-field="orderSn" data-width="5%" data-align="center">订单编号</th>
			                            <th data-field="orderStatus" data-width="10%" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
			                            <th data-field="paymentType" data-width="10%" data-align="center" data-formatter="CommonUtils.getPaymentSta">收款单状态</th>
			                            <th data-field="userName" data-width="5%" data-align="center">客户人姓名</th>
			                            <th data-field="userTel" data-width="5%" data-align="center">客户手机号</th>
			                            <th data-field="needMoney" data-width="10%" data-align="center" data-formatter = "CommonUtils.getMoney">应收金额</th>
			                            <th data-field="totalFee" data-width="10%" data-align="center" data-formatter = "CommonUtils.getMoney">实收金额</th>
			                            <th data-field="sourceType" data-width="5%" data-align="center" data-formatter = "CommonUtils.getPaySourceType">收款单来源</th>
			                            <th data-field="payType" data-width="5%" data-align="center" data-formatter = "CommonUtils.getPayType">付款方式</th>
			                            <th data-field="num" data-width="5%"  data-align="center" data-formatter = "CommonUtils.getConstant">同笔订单第几次</th>
			                            <th data-field="runTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDay">应收款日期</th>
			                            <th data-field="actualSyncTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDay">实际收款日期</th>
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
	
	<!--收款单弹出框 -->
	<div class="modal inmodal fade" id="payMentDialog" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div class="ibox-content">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<form action="#">
											<div class="row">
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">租客姓名:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_userName" readonly="readonly" />
												</div>
		
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">租客UID:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_paymentUid" readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">收款单编号:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_tradeNo"
														readonly="readonly" />
												</div>
											</div>
		
											<div class="row">
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">订单编号:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_orderSn"
														readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">应收金额:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_needMoney"
														readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">实收金额:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_orderMoneyEntity.payMoney" readonly="readonly" />
												</div>
											</div>
											<div class="row">
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">同笔款项第几次收款:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id=""
														readonly="readonly" value="1"/>
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">收款类型:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_payType"
														readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">收款状态:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_payStyle"
														readonly="readonly" />
												</div>
											</div>
											<div class="row">
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">订单金额:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id="D_orderNeedMoney"
														readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">应收款日期:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id=""
														readonly="readonly" />
												</div>
												<div class="col-sm-2 text-right">
													<label class="control-label"
														style="height: 35px; line-height: 35px;">实际收款日期:</label>
												</div>
												<div class="col-sm-2">
													<input class="form-control" id=""
														readonly="readonly" />
												</div>
											</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="modalClose"
						data-dismiss="modal">关闭</button>
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
	<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<!-- Bootstrap table -->
<script
	src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script
	src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script
	src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/minsu/order/bill.js${VERSION}" type="text/javascript"></script>
<script type="text/javascript">
	//点击单元格
	$('#listTable').on('click-cell.bs.table',
		function(field, value, row, $element) {
			if (value == "uid") {
				$("#customerDetailInfoModel").modal("toggle");
				customerDetail.showDetail(row);
			}
	});
	//显示信息详情
	function nameShow(field, value, row){
		return "<a onclick='showDetail(\""+value.fid+"\")' href='javascript:void(0);'>"+value.paymentSn+"</a>";
	}
	//显示收款单详情
	function showDetail(value){
		//获取收款单详情信息
		$.getJSON("bill/paymentDetail",{fid:value},function(data){
			if(data.code == 0){
				$("#D_userName").val(data.data.paymentVo.userName);
				$("#D_paymentUid").val(data.data.paymentVo.paymentUid);
				$("#D_tradeNo").val(data.data.paymentVo.paymentSn);
				$("#D_orderSn").val(data.data.paymentVo.orderSn);
				$("#D_needMoney").val(data.data.paymentVo.needMoney);
				$("#D_orderMoneyEntity.payMoney").val(data.data.paymentVo.orderMoneyEntity.payMoney);
				$("#D_orderNeedMoney").val(data.data.paymentVo.needMoney);
				var sourcetype = "";
				if(data.data.paymentVo.sourceType == 1){
					sourcetype = "房客";
				}else if(data.data.paymentVo.sourceType == 2){
					sourcetype = "房东";
				}
				$("#payType").val(sourcetype);

				if(data.data.paymentVo.paymentType == 1){
					$("#payStyle").val("订单");
				}else if(data.data.paymentVo.paymentType == 2){
					$("#payStyle").val("账单");
				}else if(data.data.paymentVo.paymentType == 3){
					$("#payStyle").val("优惠券");
				}else{
					$("#payStyle").val("");
				}
				
				
			}else{
				layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
			}
		});
		$("#payMentDialog").modal("toggle");
	}
</script>
</body>
</html>
