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
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">房东姓名：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="landlordName" name="landlordName"  type="text" class="form-control" >
                                    </div>
                                    <label class="col-sm-3 control-label left2">房东联系方式：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="landlordTel" name="landlordTel"  type="text" class="form-control" >
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">房客姓名：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="userName" name="userName"  type="text" class="form-control" >
                                    </div>
                                    <label class="col-sm-3 control-label left2">房客联系方式：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="userTel" name="userTel"  type="text" class="form-control" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">订单编号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="orderSn" name="orderSn"  type="text" class="form-control"  >
                                    </div>
                                    <label class="col-sm-3 control-label left2">订单状态：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control" id="orderStatus" name="orderStatus" >
                                            <option value="">全部</option>
                                            <option value="20">待入住</option>
                                            <option value="40">已入住未生成单据</option>
                                            <option value="41">已入住已生成单据</option>
                                            <option value="37">已支付客服协商取消</option>
                                            <option value="370">未支付客服协商取消</option>
                                            <option value="38">房东强制取消</option>
                                            <option value="72">客服协商退房</option>
                                            <option value="73">房东申请退房</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <div class="col-sm-1 left2 left3">
                                        <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
                <div class="col-sm-12">
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
                                   data-query-params="paginationParam"
                                   data-method="post"
                                   data-single-select="true"
                                   data-height="470"
                                   data-url="order/findCancelOrderList">
                                <thead>
                                <tr class="trbg">
                                    <th data-field="fid" data-visible="false"></th>
                                    <th data-field="orderSn"  data-width="10%" data-align="center" data-formatter="showLink">订单号</th>
                                    <th data-field="orderStatus" data-width="10%" data-align="center" data-formatter="CommonUtils.getOrderStatu">订单状态</th>
                                    <th data-field="payStatus" data-width="10%" data-align="center" data-formatter="showPayStatus">支付状态</th>
                                    <th data-field="payTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">支付时间</th>
                                    <th data-field="startTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">预计入住时间</th>
                                    <th data-field="endTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">预计退房时间</th>
                                    <th data-field="houseName" data-width="10%" data-align="center">房源名称</th>
                                    <th data-field="landlordName" data-width="5%" data-align="center">房东姓名</th>
                                    <th data-field="landlordTel" data-width="10%" data-align="center">房东联系方式</th>
                                    <th data-field="userName" data-width="5%" data-align="center">房客姓名</th>
                                    <th data-field="userTel" data-width="10%" data-align="center">房客联系方式</th>
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
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}003" type="text/javascript"></script>

<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>





<script type="text/javascript">
    function query(){
        $('#listTable').bootstrapTable('selectPage', 1);
    }
    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            landlordName:$('#landlordName').val(),
            landlordTel:$('#landlordTel').val(),
            userName:$('#userName').val(),
            userTel:$('#userTel').val(),
            orderSn:$('#orderSn').val(),
            orderStatus:$('#orderStatus').val()
        };
    }



    //显示信息详情
    function showLink(value, row, index){
        if(row.orderStatus == 37 || row.orderStatus == 72 || row.orderStatus == 38 || row.orderStatus == 73){
            return "<a onclick='showDetail(\""+value+"\",\""+row.orderStatus+"\")' href='javascript:void(0);'>"+value+"</a>";
        }else if(row.orderStatus == 20 || row.orderStatus == 40 || row.orderStatus == 41){
            return "<a onclick='showOperDetail(\""+value+"\")' href='javascript:void(0);'>"+value+"</a>";
        }else {
            return value;
        }
    }
    var showOperDetail =  function(orderSn){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单号不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(),'order/toCancleOrderInfo?orderSn='+orderSn, "取消订单详情");
    }

    var showDetail =  function(orderSn, orderStatus){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单号不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(),'order/showCancleOrderInfo?orderSn='+orderSn+"&orderStatusParam="+orderStatus, "查看取消详情");
    }
    
     function showPayStatus(value, row, index){
    	var payStatuStr="";
    	if(value==1){
    		payStatuStr="已支付";
    	}
        if(value == 0){
        	payStatuStr="未支付";
    	} 
        return payStatuStr;
    } 
</script>
</body>

</html>
