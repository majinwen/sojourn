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
				                   <label class="col-sm-2 control-label left2">收入编号：</label>
				                   <div class="col-sm-2 left2">
				                       <input id="incomeSn" name="incomeSn"  type="text" class="form-control" >
				                   </div>
				                   <label class="col-sm-2 control-label left2">收入类型：</label>
				                   <div class="col-sm-2 left2">
				                      <select class="form-control m-b" id="incomeType" name="incomeType" >
				                           <option value="">全部</option>
	                                       <option value="1">客户房租佣金</option>
	                                       <option value="2">客户违约金佣金</option>
	                                       <option value="3">房东房租佣金</option>
	                                       <option value="4">房东违约金佣金 </option>
	                                       <option value="5">房东违约金</option>
	                                       <option value="6">用户清洁费佣金</option>
	                                   </select>
				                   </div>
				                 
				                    <label class="col-sm-2 control-label left2">订单编号：</label>
				                    <div class="col-sm-2 left2">
				                       <input id="orderSn" name="orderSn"  type="text" class="form-control">
				                   </div>
			           			</div>
			           			<div class="row">
				                   <label class="col-sm-2 control-label left2">收入确认日期：</label>
		                           <div class="col-sm-2 left2">
		                               <input id="actualStartTime"  value=""  name="actualStartTime" class="laydate-icon form-control layer-date">
		                           </div>
		                           <label class="col-sm-1 control-label left2" style="text-align: left;width:30px;">到</label>
		                            <div class="col-sm-2 left2">
		                               <input id="actualEndTime" value="" name="actualEndTime" class="laydate-icon form-control layer-date">
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
					                    data-query-params="Bill.incomeParam"
					                    data-method="post"
					                    data-height="520"
					                    data-single-select="true"
                                        data-query-params="paginationParam" data-method="post"
					                    data-url="bill/queryFinanceIncome">                    
					                    <thead>
					                        <tr>
					                            <!-- <th data-field="fid" data-visible="false"></th> -->
					                            <th data-field="incomeSn" data-width="15%" data-align="center" data-formatter="showLink"  >收入编号</th>
					                            <th data-field="orderSn" data-width="15%" data-align="center">订单编号</th>
					                            <th data-field="orderStatus" data-width="10%" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
					                            <th data-field="userName" data-width="10%" data-align="center">客户人姓名</th>
					                            <th data-field="totalFee" data-width="10%" data-align="center" data-formatter = "CommonUtils.getMoney">收入确认金额</th>
					                            <th data-field="incomeType" data-width="5%" data-align="center" data-formatter = "CommonUtils.getIncomeType">收入类型</th>
                                                <th data-field="incomeStatus" data-width="5%" data-align="center" data-formatter = "CommonUtils.getIncomeStatus">收入状态</th>
                                                <th data-field="syncStatus" data-width="5%" data-align="center" data-formatter = "CommonUtils.getIncomeSyncStatus">同步状态</th>
					                            <th data-field="num" data-width="5%"  data-align="center" data-formatter = "CommonUtils.getConstant">同笔订单第几次</th>
					                            <th data-field="actualIncomeTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDay">收入确认日期</th>
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



    <div class="modal inmodal fade" id="incomeDialog" tabindex="-1" role="dialog" aria-hidden="true">
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
                                                       style="height: 35px; line-height: 35px;">付款人:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_userName" readonly="readonly" />
                                            </div>

                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">UID:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_payUid" readonly="readonly" />
                                            </div>

                                        </div>

                                        <div class="row">
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">收入编号:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_incomeSn"
                                                       readonly="readonly" />
                                            </div>


                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">订单编号:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_orderSn"
                                                       readonly="readonly" />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">同笔第几次款:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="11"
                                                       readonly="readonly" value="1"/>
                                            </div>
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">收款类型:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_incomeType"
                                                       readonly="readonly" />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">应付金额:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_total_fee"
                                                       readonly="readonly" />
                                            </div>
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">实付金额:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_total_fee_real"
                                                       readonly="readonly" />
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">应付款日期:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_runTime"
                                                       readonly="readonly" />
                                            </div>
                                            <div class="col-sm-2 text-right">
                                                <label class="control-label"
                                                       style="height: 35px; line-height: 35px;">实际付款日期:</label>
                                            </div>
                                            <div class="col-sm-4">
                                                <input class="form-control" id="D_actualIncomeTime"
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
<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
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

    //显示信息详情
    function showLink(field, value, row){
        return "<a onclick='showDetail(\""+field+"\")' href='javascript:void(0);'>"+field+"</a>";
    }

    //显示收款单详情
    function showDetail(value){
        //获取收款单详情信息
        $.getJSON("bill/incomeDetail",{fid:value},function(data){
            if(data.code == 0){
                $("#D_userName").val(data.data.incomeDetail.userName);
                $("#D_payUid").val(data.data.incomeDetail.payUid);
                $("#D_incomeSn").val(data.data.incomeDetail.incomeSn);
                $("#D_orderSn").val(data.data.incomeDetail.orderSn);

                var D_incomeType = CommonUtils.getIncomeType(data.data.incomeDetail.incomeType);
                $("#D_incomeType").val(D_incomeType);

                var fee = CommonUtils.getMoneyStr(data.data.incomeDetail.totalFee);
                //应付金额
                $("#D_total_fee").val(fee);

                //实付金额
                if(data.data.incomeDetail.actualIncomeTime === null){
                    $("#D_total_fee_real").val(fee);
                }
                $("#D_runTime").val(data.data.incomeDetail.runTime);
                $("#D_actualIncomeTime").val(data.data.incomeDetail.actualIncomeTime);
            }else{
                layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
            }
        });
        $("#incomeDialog").modal("toggle");
    }

    //分页查询参数
    function paginationParam(params) {
        return {
            limit : params.limit,
            page : $('#listTable').bootstrapTable('getOptions').pageNumber,
            userName : $.trim($('#userName').val()),
            userTel : $.trim($('#userTel').val()),
            incomeSn : $('#incomeSn').val(),
            orderSn : $('#orderSn').val(),
            incomeType : $('#incomeType option:selected').val(),
            actualStartTime : $('#actualStartTime').val(),
            actualEndTime : $('#actualEndTime').val()
        };
    }

</script>


</body>

</html>
