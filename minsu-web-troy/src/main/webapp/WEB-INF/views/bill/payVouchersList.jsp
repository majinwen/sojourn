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
.row{margin:0;}
.modal-lm{min-width:1000px;} 
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
			                   <label class="col-sm-2 control-label left2">收款人名称：</label>
			                   <div class="col-sm-2 left2">
			                       <input id="receiveName" name="receiveName"  type="text" class="form-control" >
			                   </div>
			                   <label class="col-sm-2 control-label left2">收款人电话：</label>
                               <div class="col-sm-2 left2">
			                       <input id="receiveTel" name="receiveTel"  type="text" class="form-control" >
			                   </div>
			                   <label class="col-sm-2 control-label left2">客户UID：</label>
                               <div class="col-sm-2 left2">
			                       <input id="userUid" name="userUid"  type="text" class="form-control" >
			                   </div>
			                   
			           		</div>
			           		<div class="row">
			           			<label class="col-sm-2 control-label left2">付款单编号：</label>
			                   <div class="col-sm-2 left2">
			                       <input id="pvSn" name="pvSn"  type="text" class="form-control" >
			                   </div>
			                   <label class="col-sm-2 control-label left2 text-left">付款单状态：</label>
			                   <div class="col-sm-2 left2">
			                      <select class="form-control m-b" id="paymentStatus" name="paymentStatus" >
			                           <option value="">全部</option>
                                       <option value="1">未付款</option>
                                       <option value="2">已消费</option>
                                       <option value="3">已申请打款 </option>
                                       <option value="4">已打款 </option>
                                       <option value="5">已打余额</option>
                                       <option value="6">已打冻结</option>
                                       <option value="7">提前退房取消 </option>
                                       <option value="8">未绑定银行卡 </option>
                                       <option value="9">失败</option>
                                       <option value="20">打款失败待处理</option>
                                       <option value="21">打款失败已处理</option>
									  <option value="30">罚款冲抵取消</option>
									  <option value="31">罚款冲抵重新生成取消</option>
									  <option value="99">黑名单不打款</option>
                                   </select>
			                   </div>
			                    <label class="col-sm-2 control-label left2">订单编号：</label>
			                    <div class="col-sm-2 left2">
			                       <input id="orderSn" name="orderSn"  type="text" class="form-control"  >
			                   </div>
			                 </div>
			                 <div class="row">
			                      <label class="col-sm-2 control-label left2">应付款日期：</label>
	                           <div class="col-sm-2 left2">
	                               <input id="runTimeStart"  value=""  name="runTimeStart" class="laydate-icon form-control layer-date">
	                           </div>
	                           <label class="col-sm-2 control-label left2" style="text-align: left;width: 30px;">到</label>
	                            <div class="col-sm-2 left2">
	                               <input id="runTimeEnd" value="" name="runTimeEnd" class="laydate-icon form-control layer-date">
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
						<customtag:authtag authUrl="bill/showPreReCreatePayVourchers">
							<button class="btn btn-primary " type="button" onclick="preReCreate();">
								<i class="fa fa-plus"></i>&nbsp;重新生成 
							</button>
						</customtag:authtag>
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
					                    data-query-params="PayVouchers.payVouchersParam"
					                    data-method="post"
					                    data-height="760"
					                    data-single-select="true" 
					                    data-url="bill/queryFinancePayVos">                    
			                    <thead>
			                        <tr>
		                            	<th data-field="id" data-width="1%" data-radio="true"></th>
			                            <th data-field="pvSn" data-width="4%" data-align="left" data-formatter="showLink">付款单编号</th>
			                            <th data-field="orderSn" data-width="4%" data-align="center">订单编号</th>
			                            <th data-field="orderStatus" data-width="6%" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
			                            <th data-field="paymentStatus" data-width="6%" data-align="center" data-formatter="CommonUtils.getPaymentStatus">付款单状态</th>
			                            <th data-field="paymentType" data-width="6%" data-align="center" data-formatter="PayVouchers.paymentType">付款类型</th>
			                            <th data-field="receiveName" data-width="5%" data-align="center">收款人</th>
			                            <th data-field="receiveType" data-width="5%" data-align="center" data-formatter="PayVouchers.receiveType">收款人角色</th>
			                            <th data-field="receiveTel" data-width="4%" data-align="center">收款人电话</th>
			                            <th data-field="totalFee" data-width="4%" data-align="center" data-formatter="CommonUtils.getMoney">付款金额</th>
			                            <th data-field="paySourceType" data-width="7%" data-align="center" data-formatter="CommonUtils.getPaySourceType">付款单来源</th>
			                            <th data-field="runTime" data-width="8%" data-align="center" data-formatter="CommonUtils.formateDate">应付款日期</th>
			                            <th data-field="actualPayTime" data-width="8%" data-align="center" data-formatter="CommonUtils.formateDate">财务处理日期</th>
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




	<!--弹出框 -->
	<div class="modal inmodal fade" id="reCreateModel" tabindex="-1" role="dialog" aria-hidden="true">
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
									<input type="hidden" id="m_canYlfh" name="m_canYlfh">
									<input type="hidden" id="m_isDiffBank" name="m_isDiffBank">
									<input type="hidden" id="m_paymentType" name="m_paymentType">

									<div class="row">
										<input type="hidden" id="m_oldPvSn"/>
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">付款单编号:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_newPvSn" readonly="readonly" />
										</div>
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">订单编号:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_orderSn" readonly="readonly" />
										</div>
									</div>

									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">付款人:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_payName" readonly="readonly" />
										</div>
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">付款人uid:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_payUid" readonly="readonly" />
										</div>
									</div>

									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">收款人:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_receiveName" readonly="readonly" />
										</div>

										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">收款人uid:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_receiveUid" readonly="readonly" />
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">收款银行:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_bankName" readonly="readonly" />
										</div>

										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">收款账号:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_bankcardNo" readonly="readonly" />
										</div>
									</div>
									
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">订单金额:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_sumMoney" readonly="readonly" />
										</div>

										<div class="col-sm-2 text-right">
											<label class="control-label" style="height: 35px; line-height: 35px;">付款单金额:</label>
										</div>
										<div class="col-sm-4">
											<input class="form-control" id="m_totalFee" readonly="readonly" />
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
																<tbody id="detailList">
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
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-6 text-right">
						<customtag:authtag authUrl="bill/reCreatePayVourchers">
							<button type="button" class="btn btn-success btn-sm" id="reCreateButton" onclick="reCreate();">保存</button>
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
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>


<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js"></script>

<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
<%-- <script src="${basePath}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script> --%>
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/order/payVouchers.js${VERSION}" type="text/javascript"></script>
<%--<script src="${basePath }js/minsu/order/payVouchers.js${VERSION}001" type="text/javascript"></script>--%>

<script type="text/javascript">

	// 显示详情链接
	function showLink(value, row, index){
		return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showDetail(\""+value+"\");'</customtag:authtag>>"+value+"</a>";
	}
	function showDetail(pvSn){
		$.openNewTab(new Date().getTime(),'bill/payVouchersDetail?pvSn='+pvSn, "付款单详情");
	}
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


	// 重新生成付款单弹框
	function preReCreate() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
		if(rows[0].paymentStatus != 20){
			layer.alert("当前状态不能重新生成付款单", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}

		// 如果是原路返回，需校验是否符合原路返回条件
		$("#m_canYlfh").val("false");
		if (rows[0].paymentType == 'ylfh') {
			$.ajax({
				url: "bill/checkCanYlfh",
				data: {
					"pvSn": rows[0].pvSn
				},
				dataType: "json",
				async: false,
				type: "post",
				success: function (result) {
					if (result.code === 0) {
						var checkFlag = result.data.checkFlag;
						if (checkFlag != true) {
							layer.confirm('此付款单类型为原路返回，但目前已不符合原路返回条件，是否生成银行付款的付款单？<br><font color="red">注：如果使用银行付款，用户必须绑定银行卡。</font>', {
								btn: ['是', '否']
							}, function (index) {
								layer.close(index);
								showCreateFrame(rows[0].pvSn);
							}, function () {
							});
						}else{
							$("#m_canYlfh").val("true");
							showCreateFrame(rows[0].pvSn);
						}
					}else{
						layer.alert(result.msg, {
							icon : 5,
							time : 5000,
							title : '提示'
						});
					}
				},
				error: function (result) {
					layer.alert("未知错误", {
						icon: 5,
						time: 2000,
						title: '提示'
					});
				}
			});

		} else {
			showCreateFrame(rows[0].pvSn);
		}
	}

	// 弹框预览
	function showCreateFrame(pvsn){
		$.ajax({
			url : "bill/showPreReCreatePayVourchers",
			data : {
				"pvSn" : pvsn
			},
			dataType : "json",
			type : "post",
			success : function(result) {
				if (result.code === 0) {
					$("#reCreateModel").modal("toggle");

					$("#m_isDiffBank").val(result.data.isDiffBank);
					$("#m_newPvSn").val(result.data.newPvSn);
					var detailVo = result.data.detailVo;
					$("#m_paymentType").val(detailVo.paymentType);
					$("#m_oldPvSn").val(detailVo.pvSn);
					$("#m_orderSn").val(detailVo.orderSn);
					$("#m_payName").val(detailVo.payName);
					$("#m_payUid").val(detailVo.payUid);
					$("#m_receiveName").val(detailVo.receiveName);
					$("#m_receiveUid").val(detailVo.receiveUid);
					$("#m_sumMoney").val(detailVo.orderMoney.sumMoney/100 + "元");
					$("#m_totalFee").val(detailVo.totalFee/100 + "元");
					var bankcard = result.data.bankcard;
					if(bankcard != null){
						$("#m_bankName").val(bankcard.bankName);
						$("#m_bankcardNo").val(bankcard.bankcardNo);
					}else{
						$("#m_bankName").val("");
						$("#m_bankcardNo").val("");
					}

					$("#detailList").empty();
					var detailList = result.data.detailVo.detailList;
					var tr = "";
					for(var i=0; i<detailList.length; i++){
						tr += "<tr><td>" + getfeeItemStr(detailList[i].feeItemCode) + "</td><td>" + detailList[i].itemMoney/100 + "元</td></tr>";
					}
					$("#detailList").append(tr);

				} else {
					layer.alert(result.msg, {
						icon : 5,
						time : 5000,
						title : '提示'
					});
				}
			},
			error : function(result) {
				layer.alert("未知错误", {
					icon : 5,
					time : 2000,
					title : '提示'
				});
			}
		});
	}
	
	//重新生成付款单
	function reCreate() {
		var isDiffBank = $("#m_isDiffBank").val();
		var paymentType = $("#m_paymentType").val();
		if (paymentType == 'yhfk' && isDiffBank == 'false' && !confirm("发现此用户银行卡信息未做修改！\n确定还要重新生成付款单吗？")) {
			return;
		}
		if (paymentType == 'ylfh' && $("#m_canYlfh").val() != 'true' && $("#m_bankcardNo").val() == '') {
			layer.msg('用户未绑定银行卡，不能生成银行付款的付款单', {shift: 6});
			return;
		}

		$('#reCreateButton').attr("disabled","disabled");
		$.ajax({
			url : "bill/reCreatePayVourchers",
			data : {
				"pvSn" : $("#m_oldPvSn").val()
			},
			dataType : "json",
			type : "post",
			success : function(result) {
				if (result.code === 0) {
					layer.alert(result.msg, {
						icon : 6,
						time : 2000,
						title : '保存成功！'
					});
					$('#reCreateModel').modal('hide')
					CommonUtils.query();
				} else {
					layer.alert(result.msg, {
						icon : 5,
						time : 5000,
						title : '提示'
					});
				}
				$('#reCreateButton').removeAttr("disabled");
			},
			error : function(result) {
				layer.alert("未知错误", {
					icon : 5,
					time : 2000,
					title : '提示'
				});
				$('#reCreateButton').removeAttr("disabled");
			}
		});
		
	}
</script>
</body>

</html>
