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
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row row-lg">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-group">
                        <div class="row m-b">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">用户姓名:</label>
                            </div>
                            <div class="col-sm-2">
                                <input id="complainUsername" name="complainUsername" type="text"
                                       class="form-control">
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">用户手机:</label>
                            </div>
                            <div class="col-sm-2">
                                <input id="complainMphone" name="complainMphone" type="text"
                                       class="form-control">
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">反馈内容:</label>
                            </div>
                            <div class="col-sm-2">
                                <input id="content" name="content" type="text"
                                       class="form-control">
                            </div>
                        </div>
                        <div class="row m-b">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">反馈日期:</label>
                            </div>
                            <div class="col-sm-6 daop">
                                <div class="col-sm-4 left2">
                                    <input id="createTimeStart" value="" name="createTimeStart"
                                           class="laydate-icon form-control layer-date">
                                </div>
                                <label class="col-sm-2 control-label left2" style="text-align: center;">到:</label>
                                <div class="col-sm-4 left2">
                                    <input id="createTimeEnd" value="" name="createTimeEnd"
                                           class="laydate-icon form-control layer-date">
                                </div>
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="query();"><i
                                        class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
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
                        <table id="listTable" class="table table-bordered table-hover" data-toggle="table"
                               data-side-pagination="server"
                               data-pagination="true"
                               data-page-list="[10,20,50]"
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
                               data-url="message/getLandlordComplain">
                            <thead>
                            <tr class="trbg">
                                <th data-align="center" data-checkbox="true"></th>
                                <th data-field="id" data-visible="false">id</th>
                                <th data-field="complainUid" data-visible="false">用户id</th>
                                <th data-field="complainUsername" data-formatter="complainUsernameFormatter">用户姓名</th>
                                <th data-field="complainMphone">用户手机</th>
                                <th data-field="content">反馈内容</th>
                                <th data-field="createDate" data-formatter="CommonUtils.formateDate">反馈时间</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!--投诉详情 -->
<div class="modal inmodal" id="detailModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:50%">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">房东反馈消息详情</h4>
            </div>
            <div class="modal-body">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="ibox float-e-margins">
                                <div class="ibox-content">
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="row" style="margin-left: 10px;">
                                                <label class="col-sm-2 control-label mtop">房东姓名:</label>
                                                <div class="col-sm-10">
                                                    <input id="c_username" name="username" type="text"
                                                           class="form-control">
                                                </div>
                                            </div>
                                            <div class="row" style="margin-left: 10px;">
                                                <label class="col-sm-2 control-label mtop">房东电话:</label>
                                                <div class="col-sm-10">
                                                    <input id="c_phone" name="phone" type="text" class="form-control">
                                                </div>
                                            </div>
                                            <div class="row" style="margin-left: 10px;">
                                                <label class="col-sm-2 control-label mtop">反馈内容:</label>
                                                <div class="col-sm-10">
                                                    <textarea id="c_content" name="c_content" type="text" rows="8"
                                                              cols="" class="form-control"></textarea>
                                                </div>
                                            </div>
                                            <div class="row" style="margin-left: 10px;">
                                                <label class="col-sm-2 control-label mtop">反馈时间:</label>
                                                <div class="col-sm-10">
                                                    <input id="c_createDate" name="c_createDate" type="text"
                                                           class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
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
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}001"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}004" type="text/javascript"></script>
<script type="text/javascript" src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
<script type="text/javascript">
    //外部js调用
    function paginationParam(params) {
        var createTimeStart = $.trim($('#createTimeStart').val());
        if (!isNullOrBlank(createTimeStart)) {
            createTimeStart = createTimeStart + " 00:00:00";
        }

        var createTimeEnd = $.trim($('#createTimeEnd').val());
        if (!isNullOrBlank(createTimeEnd)) {
            createTimeEnd = createTimeEnd + " 23:59:59";
        }

        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            complainUsername: $.trim($('#complainUsername').val()),
            complainMphone: $.trim($('#complainMphone').val()),
            content: $.trim($('#content').val()),
            createTimeStart: createTimeStart,
            createTimeEnd: createTimeEnd,
        };
    }

    function query() {
        $('#listTable').bootstrapTable('selectPage', 1);
    }

    function isNullOrBlank(obj) {
        return obj == undefined || obj == null || $.trim(obj).length == 0;
    }
    // 格式化时间
    function formateDate(value, row, index) {
        if (value != null) {
            var vDate = new Date(value);
            return vDate.format("yyyy-MM-dd HH:mm:ss");
        } else {
            return "";
        }
    }
    $(function () {
        //外部js调用
        laydate({
            elem: '#createTimeStart', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
        laydate({
            elem: '#createTimeEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        });
    });

    function complainUsernameFormatter(value, row, index) {
        return "<a href='javascript:;' class='oneline line-width' title=\"" + value + "\" onclick='showDetail(\"" + row.id + "\");'>" + value + "</a>";
    }

    function showDetail(id) {
        $("#detailModal").modal("toggle");
        $.getJSON("message/getLandlordComplainById", {complainId: id}, function (data) {
            console.info(data)
            if (data.code === 0) {
                var detail = data.data.result;
                if (detail == null) {
                    return;
                }
                $("#c_username").val(detail.complainUsername).attr("readonly", "readonly");
                $("#c_phone").val(detail.complainMphone).attr("readonly", "readonly");
                $("#c_content").val(detail.content).attr("readonly", "readonly");
                $("#c_createDate").val(formateDate(detail.createDate, 1, 1)).attr("readonly", "readonly");
            } else {
                layer.alert("查询失败", {icon: 5, time: 2000, title: '提示'});
            }
        });
    }

</script>

</body>

</html>
