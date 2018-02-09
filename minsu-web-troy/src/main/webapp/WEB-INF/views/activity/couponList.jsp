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

    <style type="text/css">
        .left2 {
            margin-top: 10px;
        }

        .left3 {
            padding-left: 62px;
        }

        .btn1 {
            border-right: #7b9ebd 1px solid;
            padding-right: 2px;
            border-top: #7b9ebd 1px solid;
            padding-left: 2px;
            font-size: 12px;
            FILTER: progid: DXImageTransform . Microsoft . Gradient(GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde);
            border-left: #7b9ebd 1px solid;
            cursor: hand;
            padding-top: 2px;
            border-bottom: #7b9ebd 1px solid;
        }

        .row {
            margin: 0;
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
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">活动编号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="actSn" name="actSn" type="text" class="form-control">
                                    </div>
                                    <label class="col-sm-3 control-label left2">优惠券号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="couponSn" name="couponSn" type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">优惠券状态：</label>
                                    <div class="col-sm-3 left2">
                                        <select class="form-control" id="couponStatus" name="couponStatus">
                                            <option value="">全部</option>
                                            <option value="1">未领取</option>
                                            <option value="2">已领取</option>
                                            <option value="3">已冻结</option>
                                            <option value="4">已使用</option>
                                            <option value="5">已过期</option>
                                            <option value="6">已发送</option>

                                        </select>
                                    </div>

                                    <label class="col-sm-3 control-label left2">领取电话：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="customerMobile" name="customerMobile" type="text"
                                               class="form-control">
                                    </div>

                                </div>


                            </div>


                            <div class="row">
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">uid：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="uid" name="uid" type="text" class="form-control">
                                    </div>
                                    <label class="col-sm-3 control-label left2">优惠券名称：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="couponName" name="couponName" type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <label class="col-sm-3 control-label left2">订单号：</label>
                                    <div class="col-sm-3 left2">
                                        <input id="orderSn" name="orderSn" type="text" class="form-control">
                                    </div>
                                    <div class="col-sm-1 left2 left3">
                                        <button class="btn btn-primary" type="button" onclick="CommonUtils.query();">
                                            <i class="fa fa-search"></i>&nbsp;查询
                                        </button>
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
                    <customtag:authtag authUrl="activity/cancelCoupon">
                        <button class="btn btn-warning" type="button" onclick="cancelShow();"><i class="fa fa-cancel"></i>&nbsp;作废</button>
                    </customtag:authtag>
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
                                   data-query-params="paginationParam"
                                   data-method="post"
                                   data-single-select="true"
                                   data-height="470"
                                   data-url="activity/couponDataList">
                                <thead>
                                <tr class="trbg">
                                    <th data-field="id" data-width="2%" data-radio="true"></th>
                                    <th data-field="fid" data-visible="false"></th>
                                    <th data-field="actSn" data-width="10%" data-align="center">活动编号</th>
                                    <th data-field="couponSn" data-width="15%" data-align="center"
                                        data-formatter="showLink">优惠券编号
                                    </th>
                                    <th data-field="couponName" data-width="10%" data-align="center">优惠券名称</th>
                                    <th data-field="couponStatus" data-width="10%" data-align="center"
                                        data-formatter="formateCouponStatus">优惠券状态
                                    </th>
                                    <th data-field="couponStartTime" data-width="10%" data-align="center"
                                        data-formatter="CommonUtils.formateDate">优惠券开始时间
                                    </th>
                                    <th data-field="couponEndTime" data-width="10%" data-align="center"
                                        data-formatter="CommonUtils.formateDate">优惠券结束时间
                                    </th>
                                    <th data-field="createTime" data-width="10%" data-align="center"
                                        data-formatter="CommonUtils.formateDate">创建时间
                                    </th>
                                    <th data-field="uid" data-width="10%" data-align="center">uid</th>
                                    <th data-field="customerMobile" data-width="10%" data-align="center">领取电话</th>
                                    <th data-field="orderSn" data-width="10%" data-align="center">订单编号</th>
                                    <th data-field="usedTime" data-width="10%" data-align="center"
                                        data-formatter="CommonUtils.formateDate">使用时间
                                    </th>
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
<!-- 优惠券作废弹出框 -->
<div class="modal fade" id="cancelModal" tabindex="-1"
     role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                </button>
                <h4 class="modal-title">优惠券作废</h4>
            </div>
            <div class="modal-body">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <form id="couponForm" class="form-horizontal m-t">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">备注：</label>
                                            <div class="col-sm-8">
                                                <textarea id="remark" name="remark" class="form-control"
                                                          rows="5"></textarea>
                                                <span class="help-block m-b-none"></span>
                                            </div>
                                        </div>
                                        <!-- 用于 将表单缓存清空 -->
                                        <input type="reset" style="display: none;"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <form class="form-group">
                    <div class="row">
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-3">
                                <button type="button" id="applyButton" class="btn btn-primary" onclick="cancelCoupon();">确认</button>&nbsp;&nbsp;
                                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div>
                </form>
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
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}0013" type="text/javascript"></script>

<script type="text/javascript">

    //分页查询参数
    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,

            actSn: $.trim($('#actSn').val()),
            couponSn: $.trim($('#couponSn').val()),
            uid: $('#uid').val(),
            couponStatus: $('#couponStatus option:selected').val(),
            couponName: $('#couponName').val(),
            customerMobile: $('#customerMobile').val(),
            orderSn: $('#orderSn').val()
        };
    }


    function formateCouponStatus(value, row, index) {
        if (value == 1) {
            return "未领取";
        } else if (value == 2) {
            return "已领取";
        } else if (value == 3) {
            return "已冻结";
        } else if (value == 4) {
            return "已使用";
        } else if (value == 5) {
            return "已过期";
        } else if (value == 6) {
            return "已发送";
        }
    }

    //显示信息详情
    function showLink(field, value, row) {
        return "<a onclick='showDetail(\"" + field + "\")' href='javascript:void(0);'>" + field + "</a>";
    }

    var showDetail = function (couponSn) {
        if (couponSn == null || couponSn == "" || couponSn == undefined) {
            alert("优惠券不存在");
            return false;
        }

        $.openNewTab(new Date().getTime(), 'activity/couponDetail?couponSn=' + couponSn, "优惠券详情");

    }
    //优惠券作废function
    function cancelShow() {
        var rows = $('#listTable').bootstrapTable('getSelections');
        if (rows.length==0) {
            layer.alert("请选择一条记录进行操作", {
                icon : 6,
                time : 2000,
                title : '提示'
            });
            return;
        }
        status=rows[0].couponStatus;
        if (status!=1&&status!=2&&status!=6) {
            layer.alert("请选择有效的优惠券", {
                icon : 6,
                time : 2000,
                title : '提示'
            });
            return;
        }
        $('#cancelModal').modal("show");
    }
    function cancelCoupon() {
        var rows = $('#listTable').bootstrapTable('getSelections');
        var remark = $.trim($("#remark").val());
        if(!remark){
            layer.alert("请填写备注", {icon : 5, time : 2000});
            return;
        }
        var couponSn = rows[0].couponSn;
        $('#applyButton').attr("disabled","disabled");
        if (couponSn) {
            $.ajax({
                url: "activity/cancelCoupon",
                data: {
                    "couponSn": couponSn,
                    "remark": remark
                },
                dataType:"json",
                type:"post",
                success: function (result) {
                    if (result.code ===0) {
                        $('#listTable').bootstrapTable('refresh');
                        $('#cancelModal').modal('hide');
                        $("input[type=reset]").trigger("click");
                    }else {
                        layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
                    }
                    $('#applyButton').removeAttr("disabled");
                },
                error:function(result){
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                    $('#applyButton').removeAttr("disabled");
                }
            });
        }
    }

</script>
</body>

</html>
