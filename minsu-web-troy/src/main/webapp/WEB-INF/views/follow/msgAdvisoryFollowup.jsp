<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen"
          href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid-bootstrap.css"/>


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
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">预订人姓名:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="tenantName" name="tenantName" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">预订人手机号:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="tenantTel" name="tenantTel" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房源名称:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="houseName" name="houseName" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房东姓名:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="landlordName" name="landlordName" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房东电话:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="landlordTel" name="landlordTel" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">跟进状态:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="followStatus" id="followStatus">
                                        <option value="" >请选择</option>
                                        <option value="10">未跟进</option>
                                        <option value="20">跟进中</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">

                                    <label class="col-sm-1 control-label ">国家：</label>
                                    <div class="col-sm-2">
                                        <select class="form-control m-b m-b" id="nationCode" onchange="CommonUtils.getNationInfo('provinceCode',this);">
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label ">省：</label>
                                    <div class="col-sm-2 ">
                                        <select class="form-control m-b m-b" id="provinceCode" onchange="CommonUtils.getNationInfo('cityCode',this);">
                                        </select>
                                    </div>

                                    <label class="col-sm-1 control-label ">房源城市：</label>
                                    <div class="col-sm-2 ">
                                        <select class="form-control m-b m-b" id="cityCode" >
                                        </select>
                                    </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">每页人数:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="limit" id="limit">
                                        <option value="3">3人</option>
                                        <option value="5" selected>5人</option>
                                        <option value="10">10人</option>
                                    </select>
                                </div>

                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房东是否已回复:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="isReplay" id="isReplay">
                                        <option value="" >请选择</option>
                                        <option value="0">未回复</option>
                                        <option value="1">已回复</option>
                                    </select>
                                </div>

                               <%-- <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">是否有订单:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="isOrder" id="isOrder">
                                        <option value="" >请选择</option>
                                        <option value="0">否</option>
                                        <option value="2">已支付的订单</option>
                                        <option value="1">未支付的订单</option>
                                    </select>
                                </div>--%>

                                <div class="col-sm-1 ">
                                        <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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

    var flag=0;
    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url: "messageFollowup/msgAdvisoryFollowList",
            datatype: "json",
            //data: mydata,
            postData: {
                limit: $('#limit option:selected').val(),
                //followStatus: $('#followStatus option:selected').val(),

            },
            height: 500,
            width: document.body.clientWidth - 80,
            rowNum: 1000,
            //rowList: [5,10,15,20],
            jsonReader: {
                root: "dataList", page: "currPage", total: "totalpages",
                records: "totalCount", repeatitems: false, id: "id"
            },
            colModel: [
                {label: '预订人姓名/昵称', name: 'tenantName', width: 150},
                {label: '跟进状态', name: 'followStatusName', width: 100},
                {label: '用户uidName',align:"center", name: 'tenantUid',hidden: true,sortable :false},
                {label: '预订人手机', name: 'tenantTel', width: 100, formatter: mobileShowLink},
                {label: '房东是否已回复', name: 'isReplay', width: 100, formatter: formateIsReplay},
                {label: '订单号', name: 'orderSn', hidden: true},
                {label: '是否有订单', name: 'isOrder', width: 100, formatter: formateIsOrder},
                {label: '房源名称', name: 'houseName', width: 200, formatter: formatHouseName},
                {label: '城市', name: 'cityCode', width: 100, hidden: true},
                {label: '城市', name: 'cityName', width: 100},
                {label: '房东姓名', name: 'landlordName', width: 100},
                {label: '房东手机', name: 'landlordTel', width: 100},
                {label: '创建时间', name: 'createTime', width: 150, formatter: CommonUtils.formateDate},
                {label: '操作', name: 'msgBaseFid', width: 100, formatter: showLink}
            ],
            // viewrecords: true, // show the current page, data rang and total records on the toolbar
            //caption: "Custom Summary Type",
            shrinkToFit: false,
            autoScroll: true,
            grouping: true,
            groupingView: {
                groupField: ["tenantUid"],
                groupDataSorted: false,
                groupColumnShow: [false],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            },
            pager: "#jqGridPager",
            loadComplete: function () {
                 var rowNum = $("#jqGrid").jqGrid('getGridParam','records');
                 if(rowNum!=0 && flag==1){
                	 $("#norecordsPre").hide();
                	 return;
                 }
                 if(rowNum==0 && flag==1){
                	 $("#norecordsPre").show();
                	 return;
                 }
                 if (rowNum==0 && flag==0){
                     $("#jqGrid").parent().append("<pre id='norecordsPre'><div id='norecords' style='text-align:center'>没有找到匹配的记录！</div></pre>");
                     $("#norecords").show();
                     flag=1;
                 }else{
                    $("#norecordsPre").hide();
                 }
            }
        });
    });


    function query() {
        $("#jqGrid").jqGrid('setGridParam', {
            url: "messageFollowup/msgAdvisoryFollowList",
            postData: {
                limit: $('#limit option:selected').val(),
                tenantName: $.trim($('#tenantName').val()),
                tenantTel: $.trim($('#tenantTel').val()),
                orderStatus: $('#orderStatus option:selected').val(),
                orderSn: $('#orderSn').val(),
                houseName: $('#houseName').val(),
                houseSn: $('#houseSn').val(),
                landlordName: $('#landlordName').val(),
                landlordTel: $('#landlordTel').val(),
                nationCode: $('#nationCode').val(),
                provinceCode: $('#provinceCode').val(),
                cityCode: $('#cityCode').val(),
                followStatus: $('#followStatus option:selected').val(),
                isReplay:$('#isReplay option:selected').val(),
                isOrder:$('#isOrder option:selected').val()
            }, //发送数据
            page: $("#jqGrid").getGridParam('page')
        }).trigger("reloadGrid"); //重新载入
    }


    function showPayStatus(value, row, index) {
        if (value == "1") {
            return "已支付";
        } else {
            return "未支付";
        }
    }

    //显示信息详情
    function mobileShowLink(field, value, row) {
        var msgBaseFid = row['msgBaseFid'];
        var followStatus = row['followStatus'];
        return "<a onclick='showDetail(\"" + msgBaseFid + "\", \"" + followStatus + "\", \"" + 0 + "\")' href='javascript:void(0);'>" + field + "</a>";

    }

    //房东是否已回复
    function formateIsReplay(field, value, row) {
        if(field == '1'){
            return '是';
        }else{
            return '否';
        }
    }

    //房东是否已回复
    function formateIsOrder(field, value, row) {
        if(field == '0'){
            return '否';
        }else if(field == 1){
            return "<a onclick='showOrderFollowDetail(\""+row.orderSn+"\")' href='javascript:void(0);'>"+"未支付"+"</a>";
//            return '未支付';
        }else if(field == 2){
            return "<a onclick='showOrderFollowDetail(\""+row.orderSn+"\")' href='javascript:void(0);'>"+"已支付"+"</a>";
//            return '已支付';
        }
    }

    var showOrderFollowDetail =  function(orderSn){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(),'follow/getFollowDetail?orderSn='+orderSn, "订单跟进");
    }

    //显示信息详情
    function showLink(field, value, row) {
        var followStatus = row['followStatus'];
        return "<a onclick='showDetail(\"" + field + "\", \"" + followStatus + "\", \"" + 1 + "\")' href='javascript:void(0);'>" + "跟进操作" + "</a>";

    }

    var showDetail = function (msgBaseFid, followStatus, showButton) {
        if (msgBaseFid == null || msgBaseFid == "" || msgBaseFid == undefined) {
            alert("消息记录id不存在");
            return false;
        }
        if (followStatus == null || followStatus == "" || followStatus == undefined) {
            alert("跟进状态不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(), 'message/toMessageFollowDetail?msgBaseFid=' + msgBaseFid + '&followStatus=' + followStatus + '&showButton=' + showButton, "IM跟进详情");
    }

    //跳转到地图找房
    function formatHouseName(field, value, row) {
        return "<a onclick='toHouseMap(\"" + row.houseFid + "\",\""  + row.rentWay + "\",\"" + row.msgBaseFid + "\",\"" + "\")' href='javascript:void(0);'>" + field + "</a>";
    }

    function isNullOrBlank(obj) {
        return obj == undefined || obj == null || $.trim(obj).length == 0;
    }

    function toHouseMap(houseFid,  rentWay, msgBaseFid) {
        var url = 'house/houseSearch/houseSearchList?';
//        if (!isNullOrBlank(houseFid)) {
//            url += '&houseFid=' + houseFid
//        }
//        if (!isNullOrBlank(rentWay)) {
//            url += '&rentWay=' + rentWay
//        }
        if (!isNullOrBlank(msgBaseFid)) {
            url += 'msgBaseFid=' + msgBaseFid
        }
        $.openNewTab(new Date().getTime(), url, "找相似房源");

    }


</script>

</body>

</html>
