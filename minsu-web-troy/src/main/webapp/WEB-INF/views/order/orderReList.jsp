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
			                   <label class="col-sm-1 control-label left2">预订人姓名：</label>
			                   <div class="col-sm-2 left2">
			                       <input id="newUserName" name="newUserName"  type="text" class="form-control" >
			                   </div>
			                  
			                   <label class="col-sm-1 control-label left2">预订人手机号：</label>
                               <div class="col-sm-2 left2">
			                       <input id="newUserTel" name="newUserTel"  type="text" class="form-control" >
			                   </div>
			                   <label class="col-sm-1 control-label left2">审核状态：</label>
			                   <div class="col-sm-2 left2">
			                     <select class="form-control" id="checkedStatus" name="checkedStatus" >
			                           <option value="">全部</option>
                                       <option value="0">未关联 </option>
                                       <option value="1">待审批</option>
                                       <option value="2">人工审核通过</option>
                                       <option value="3">系统审核通过</option>
                                       <option value="4">已拒绝 </option>
                                       
                                   </select>
			                   </div>
			           		</div>
			           		<div class="row">
			                   <label class="col-sm-1 control-label left2">创建时间：</label>
	                           <div class="col-sm-2 left2">
	                               <input id="createTimeStart"  value=""  name="createTimeStart" class="laydate-icon form-control layer-date">
	                           </div>
	                           <label class="col-sm-1 control-label left2" style="text-align: right;padding-right:27px;">到</label>
	                            <div class="col-sm-2 left2">
	                               <input id="createTimeEnd" value=""   name="createTimeEnd" class="laydate-icon form-control layer-date">
	                           </div>
			                    <label class="col-sm-1 control-label left2">已取消订单编号：</label>
			                    <div class="col-sm-2 left2" >
			                       <input id="oldOrderSn" name="oldOrderSn"  type="text" class="form-control"  >
			                   </div>
			           		
			           		</div>
			           		<div class="row">
	                            <label class="col-sm-1 control-label left2" >国家：</label>
			                   <div class="col-sm-2 left2" >
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
                               <div class="col-sm-2 left2">
			                       <button class="btn btn-primary" type="button" onclick="CommonUtils.query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
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
		                    data-query-params="Order.orderReParam"
		                    data-method="post"
		                    data-single-select="true" 
		                    data-height="475"
		                    data-url="order/orderReDataList">                    
		                    <thead>
		                        <tr>
		                            <th data-field="fid" data-visible="false"></th>
		                            <th data-field="oldOrderSn" data-width="5%" data-align="center">已取消订单编号</th>
		                            <th data-field="oldHouseName" data-width="5%" data-align="center">已取消订单房源别名</th>
		                            <th data-field="newOrderSn" data-width="5%" data-align="center">新订单编号</th>
		                            <th data-field="newHouseName" data-width="5%" data-align="center">新订单房源别名</th>
		                            <th data-field="newCityCode" data-width="5%" data-align="center">新订单城市</th>
		                            <th data-field="newUserName" data-width="5%" data-align="center">新订单预定人姓名</th>
		                            <th data-field="newUserTel" data-width="10%" data-align="center">新订单预定人手机号</th>
		                            <th data-field="oldRentalMoney" data-width="5%" data-align="center" data-formatter="CommonUtils.getMoney">旧房源租金(元)</th>
		                            <th data-field="newRentalMoney" data-width="5%" data-align="center" data-formatter="CommonUtils.getMoney">新房源租金(元)</th>
		                            <th data-field="newCreateTime" data-width="5%" data-align="center" data-formatter="CommonUtils.formateDate">新订单创建时间</th>
		                            <th data-field="applyTime" data-width="5%" data-align="center" data-formatter="CommonUtils.formateDate">强制取消申请日期</th>
		                            <th data-field="checkedStatus" data-width="20%" data-align="center" data-formatter="CommonUtils.getCheckedStatu"> 状态 </th>
		                            <th data-field="checkedStatus" data-align="center"  data-width="20%" data-formatter="Order.orderReOper">操作</th>
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
	    <div class="modal-dialog">
	        <div class="modal-content ">
	            <div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
					<h4 class="modal-title">关联旧订单</h4>
				</div>
				<div class="modal-body">
				 
					 <label class="col-sm-4 control-label left2">旧订单编号：</label> 
					 <input id="oldOrderSnM" value="" readonly="readonly" style="width: 250px;"  name="oldOrderSnM" class="form-control left2">
					 
			     	 <label class="col-sm-4 control-label left2">新订单编号：</label> 
					 <input id="newOrderSnM" value=""   style="width: 250px;"  name="newOrderSn" class="form-control">
					
					 
					 <input value="获取差额" type="button"  onclick="Order.getTwoOrderBlanace()" style="width: 70px;margin-left:445px;margin-top:-30 px;"  >
					 <div style="display:none;" id="twoOrderBlanaceDiv">
					  <label class="col-sm-4 control-label left2">新旧订单差额：</label> 
					   <input id="twoOrderBlanace" value=""   style="width: 250px;" readonly="readonly"  name="twoOrderBlanace" class="form-control left2">
					 </div>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"  onclick="Order.saveOrderRelation()">保存</button>
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
	src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script
	src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script
	src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/minsu/order/order.js${VERSION}" type="text/javascript"></script>
</body>

</html>
