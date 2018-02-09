<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">

    <style type="text/css">

    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

</div>
<!-- Panel Other -->
<input type="hidden" id="hidden-groupFid" value="${groupFid}">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="row row-lg">
            <div class="col-sm-12">
                <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#myModal"><i
                        class="fa fa-plus"></i>&nbsp;添加
                </button>
                <button class="btn btn-danger" type="button" data-toggle="modal" id="btn-del"><i
                        class="fa fa-times"></i>&nbsp;删除
                </button>
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
                               data-single-select="false"
                               data-height="500"
                               data-url="activityGroup/groupUserList">
                            <thead>
                            <tr class="trbg">
                                <th data-field="id" data-width="5%" data-checkbox="true"></th>
                                <th data-field="fid" data-visible="false"></th>
                                <th data-field="customerName" data-width="10%" data-align="center">用户名称</th>
                                <th data-field="customerPhone" data-width="10%" data-align="center">用户手机</th>
                                <th data-field="uid" data-width="10%" data-align="center">uid</th>
                                <th data-field="createDate" data-width="10%" data-align="center"
                                    data-formatter="CommonUtils.formateDate">创建时间
                                </th>
                                <th data-field="createName" data-width="5%" data-align="center">创建人</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <form id="extForm" class="form-horizontal m-t">
                        <div class="wrapper wrapper-content animated fadeInRight">
                            <div class="row">
                                <label class="col-sm-2 control-label">用户姓名:</label>
                                <div class="col-sm-3">
                                    <input id="userName" type="text" class="form-control" maxlength="20">
                                </div>

                                <label class="col-sm-2 control-label">电话:</label>
                                <div class="col-sm-3">
                                    <input id="userPhone" type="text" class="form-control" maxlength="20">
                                </div>
                                <div class="col-sm-2">
                                    <button class="btn btn-primary" onclick="queryUser()" type="button">查找</button>
                                </div>
                            </div>
                        </div>
                        <!-- Panel Other -->
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                <div class="row row-lg">
                                    <div class="col-sm-12">
                                        <button class="btn btn-sm btn-primary" id="btn-saveuser" type="button"
                                                data-toggle="modal"><i class="fa fa-plus"></i>&nbsp;添加
                                        </button>
                                        <div class="example-wrap">
                                            <div class="example">
                                                <table id="userTable" class="table table-bordered table-hover"
                                                       data-click-to-select="true"
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
                                                       data-query-params="userpaginationParam"
                                                       data-method="post"
                                                       data-single-select="true"
                                                       data-height="300"
                                                       data-url="activityGroup/searchUser">
                                                    <thead>
                                                    <tr class="trbg">
                                                        <th data-width="5%" data-radio="true"></th>
                                                        <th data-field="uid" data-visible="false"></th>
                                                        <th data-field="real_name" data-width="10%" data-align="center">
                                                            用户名称
                                                        </th>
                                                        <th data-field="phone" data-width="10%" data-align="center">
                                                            用户手机
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

                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>


<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>


<script type="text/javascript">


    $(function () {
        //删除关系
        $("#btn-del").click(function () {
            var arr = $('#listTable').bootstrapTable("getSelections");
            if (arr.length == 0) {
                layer.alert("请选择一条记录进行操作", {icon: 6, time: 2000, title: '提示'});
                return;
            }

            layer.confirm("确认删除吗?", function (index) {
                var fids = "";
                $.each(arr, function (i, n) {
                    fids += n.fid + ",";
                });

                $.post("activityGroup/delGroupUserRel", {fids: fids}, function (data) {
                    if (data.code == 0) {
                        layer.alert("删除成功", {icon: 5, time: 2000, title: '提示'});
                        $('#listTable').bootstrapTable('selectPage', 1);
                        return;
                    } else {
                        layer.alert(data.msg, {icon: 6, time: 2000, title: '提示'});
                    }
                }, 'json');
                layer.close(index);
            });


        });

        //保存关系
        $("#btn-saveuser").click(function () {

            var arr = $('#userTable').bootstrapTable("getSelections");
            if (arr.length == 0) {
                layer.alert("请选择一条记录进行操作", {icon: 6, time: 2000, title: '提示'});
                return;
            }

            layer.confirm("确认添加吗?", function (index) {
                var obj = arr[0];
                var postObj = new Object();
                postObj.groupFid = $("#hidden-groupFid").val();
                postObj.uid = obj.uid;
                postObj.customerName = obj.real_name;
                postObj.customerPhone = obj.phone;
                console.log(postObj);
                $.post("activityGroup/addGroupUserRel", postObj, function (data) {
                    if (data.code == 0) {
                        layer.alert("保存成功", {icon: 1, time: 2000, title: '提示'});
                        $('#myModal').modal('hide');
                        $('#listTable').bootstrapTable('refresh');
                        layer.close(index);
                        return;
                    } else {
                        layer.alert(data.msg, {icon: 6, time: 2000, title: '提示'});
                    }
                }, 'json');

            });
        });

    });

    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            groupFid: $("#hidden-groupFid").val()
        };
    }


    //用户分页
    function userpaginationParam(params) {
        return {
            limit: params.limit,
            page: $('#userTable').bootstrapTable('getOptions').pageNumber,
            userName: $.trim($("#userName").val()),
            phone: $.trim($("#userPhone").val())
        };
    }

    //用户查询
    function queryUser() {
        $('#userTable').bootstrapTable('refresh');
    }


</script>
</body>

</html>
