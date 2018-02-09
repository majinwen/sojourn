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
    <link rel="stylesheet" type="text/css" media="screen" href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid-bootstrap.css" />





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
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">预订人姓名：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="userName" name="userName"  type="text" class="form-control" >
                                    </div>


                                    <label class="col-sm-3 control-label left2">预订人手机号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="userTel" name="userTel"  type="text" class="form-control" >
                                    </div>

                                </div>
                                <div class="col-sm-6">

                                    <label class="col-sm-3 control-label left2">订单状态：</label>
                                    <div class="col-sm-3 left2">

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
                                            <option value="35">房东未确认超时取消</option>
                                            <option value="40">已入住未生成单据</option>
                                            <option value="41">已入住已生成单据</option>
                                            <option value="50">待房东确认额外消费</option>
                                            <option value="51">提前退房待房东确认额外消费</option>
                                            <option value="60">待房客确认额外消费</option>
                                            <option value="61">提前退房待房客确认额外消费</option>
                                            <option value="70">正常退房完成</option>
                                            <option value="71">提前退房完成</option>
                                        </select>
                                    </div>

                                    <label class="col-sm-3 control-label left2">房源名称：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="houseName" name="houseName"  type="text" class="form-control" >
                                    </div>

                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6">

                                    <label class="col-sm-3 control-label left2">订单编号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="orderSn" name="orderSn"  type="text" class="form-control"  >
                                    </div>
                                    <label class="col-sm-3 control-label left2">房源编号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="houseSn" name="houseSn" type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="col-sm-6">

                                    <label class="col-sm-3 control-label left2">房东姓名：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="landlordName" name="landlordName"  type="text" class="form-control" >
                                    </div>

                                    <label class="col-sm-3 control-label left2">房东电话：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="landlordTel" name="landlordTel" type="text" class="form-control">
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">国家：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control m-b m-b" id="nationCode" onchange="CommonUtils.getNationInfo('provinceCode',this);">
                                        </select>
                                    </div>
                                    <label class="col-sm-3 control-label left2">省：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control m-b m-b" id="provinceCode" onchange="CommonUtils.getNationInfo('cityCode',this);">
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6">

                                    <label class="col-sm-3 control-label left2">房源城市：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control m-b m-b" id="cityCode" >
                                        </select>
                                    </div>
                                    <label class="col-sm-3 control-label left2">支付状态：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control" id="payStatus" name="payStatus" >
                                            <option value="">全部</option>
                                            <option value="0">未支付</option>
                                            <option value="1">已支付</option>
                                        </select>
                                    </div>

                                </div>
                            </div>

                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">每页人数：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control" id="limit" name="limit" >
                                            <option value="3">3人</option>
                                            <option value="5" selected>5人</option>
                                            <option value="10">10人</option>
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
                            <div style="margin-left:20px;">
                                <table id="jqGrid"></table>
                                <div id="jqGridPager" style="height: 50px"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/i18n/grid.locale-cn.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/jquery.jqGrid.min.js${VERSION}"></script>
<script>
    $.jgrid.defaults.width = 780;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>

<script type="text/javascript">
    $(function () {
        CommonUtils.getNationInfo('nationCode');
    })

    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url:"follow/followDataList",
            datatype: "json",
            //data: mydata,
            postData: {
                limit: $('#limit option:selected').val(),
            },
            height: 500,
            width: document.body.clientWidth-80,
            rowNum: 1000,
            //rowList: [5,10,15,20],
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            colModel: [
                { label: '跟进状态', name: 'followPeopleStatus', width: 100},
                { label: '预订人uid', name: 'userUid', hidden: true},
                { label: '预订人姓名', name: 'userName', width: 100},
                { label: '预订人手机', name: 'userTel', width: 100 },
                { label: '订单编号', name: 'orderSn', width: 160, formatter:showLink},
                { label: '房源名称', name: 'houseName', width: 150,formatter: formatHouseName},
                { label: '城市', name: 'cityName', width: 100 },
                { label: '房东姓名', name: 'landlordName', width: 100 },
                { label: '房东手机', name: 'landlordTel', width: 100 },
                { label: '支付状态', name: 'payStatus', width: 100, formatter:showPayStatus},
                { label: '订单状态', name: 'orderStatus', width: 150, formatter:CommonUtils.getOrderStatu},
                { label: '创建时间', name: 'createTime', width: 150, formatter:CommonUtils.formateDate },
                { label: '入住时间', name: 'startTime', width: 150, formatter:CommonUtils.formateDate },
                { label: '离开时间', name: 'endTime', width: 150, formatter:CommonUtils.formateDate }
            ],
            // viewrecords: true, // show the current page, data rang and total records on the toolbar
            //caption: "Custom Summary Type",
            shrinkToFit:false,
            autoScroll: true,
            grouping: true,
            groupingView: {
                groupField: ["userName"],
                groupDataSorted : false,
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            },
            pager: "#jqGridPager"
        });
    });



    function query(){
        $("#jqGrid").jqGrid('setGridParam',{
            url:"follow/followDataList",
            postData:{
                limit : $('#limit option:selected').val(),
                userName : $.trim($('#userName').val()),
                userTel : $.trim($('#userTel').val()),
                orderStatus : $('#orderStatus option:selected').val(),
                orderSn : $('#orderSn').val(),
                houseName : $('#houseName').val(),
                houseSn : $('#houseSn').val(),
                landlordName : $('#landlordName').val(),
                landlordTel : $('#landlordTel').val(),
                nationCode : $('#nationCode').val(),
                provinceCode : $('#provinceCode').val(),
                cityCode : $('#cityCode').val(),
                payStatus : $('#payStatus option:selected').val()
            }, //发送数据
            page:1
        }).trigger("reloadGrid"); //重新载入
    }


    function showPayStatus(value, row, index){
        if(value == "1"){
            return "已支付";
        }else{
            return "未支付";
        }
    }

    
    function formatHouseName(field, value, row){
    	
    	var houseFid= row.houseFid;
    	if(row.rentWay==1){
    		 houseFid= row.roomFid;
    	}
    	return "<a onclick='toHouseMap(\""+houseFid+"\",\""+row.cityCode+"\",\""+row.orderStatus+"\",\""+row.orderType+"\",\""+row.peopleNum+"\",\""+row.rentWay+"\","+row.startTime+","+row.endTime+",\""+row.orderSn+"\")' href='javascript:void(0);'>"+field+"</a>";
    }
    
    function isNullOrBlank(obj){
    	return obj == undefined || obj == null || $.trim(obj).length == 0;
    }
    
    function toHouseMap(houseFid,cityCode,orderStatus,orderType,peopleNum,rentWay,startTime,endTime,orderSn) {
    	var url='house/houseSearch/houseSearchList?';
    	if(!isNullOrBlank(houseFid)){
    		url+='&houseFid='+houseFid
    	}
    	if(!isNullOrBlank(cityCode)){
    		url+='&cityCode='+cityCode
    	}
    	if(!isNullOrBlank(orderStatus)){
    		url+='&orderStatus='+orderStatus
    	}
    	if(!isNullOrBlank(orderType)){
    		url+='&orderType='+orderType
    	}
    	if(!isNullOrBlank(peopleNum)){
    		url+='&personCount='+peopleNum
    	}
    	if(!isNullOrBlank(rentWay)){
    		url+='&rentWay='+rentWay
    	}
    	if(!isNullOrBlank(startTime)){ 
    		console.log(startTime);
    		console.log(CommonUtils.formateDay(startTime));
    		url+='&startTime='+CommonUtils.formateDay(startTime)
    	}
    	if(!isNullOrBlank(endTime)){ 
    		url+='&endTime='+CommonUtils.formateDay(endTime)
    	}
    	if(!isNullOrBlank(orderSn)){
    		url+='&orderSn='+orderSn
    	}     	
		$.openNewTab(new Date().getTime(),url, "找相似房源");
    }
    
    //显示信息详情
    function showLink(field, value, row){
        return "<a onclick='showDetail(\""+field+"\")' href='javascript:void(0);'>"+field+"</a>";
    }

    var showDetail =  function(orderSn){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(),'follow/getFollowDetail?orderSn='+orderSn, "订单跟进");
    }

</script>

</body>

</html>
