<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <style>
        .modal-lm {
            min-width: 880px;
        }

        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }

        .row {
            margin: 0;
        }

        #operList td {
            font-size: 12px;
        }
    </style>
</head>
<body class="gray-bg">
<!-- 图片弹出层 -->
<div id="blueimp-gallery" class="blueimp-gallery">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a> <a
        class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<input type="hidden" id="uid" value="${uid}">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row row-lg">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="col-sm-12">
                        <h3>基本信息</h3>
                        <div class="row">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">姓名:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="realName" readonly="readonly"/>
                            </div>

                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">用户名:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="nickName" readonly="readonly"/>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">性别:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="customerSex"
                                       readonly="readonly"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">出生日期:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="customerBirthday"
                                       readonly="readonly"/>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">电话:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="customerMobile"
                                       readonly="readonly"/>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">邮箱:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="customerEmail"
                                       readonly="readonly"/>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">证件类别:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="idType" readonly="readonly"/>
                            </div>

                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">证件号码:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="idNo" readonly="readonly"/>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">居住城市:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="cityCode" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">角色:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="isLandlord"
                                       readonly="readonly"/>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">是否审核:</label>
                            </div>
                            <div class="col-sm-3">
                                <input class="form-control" id="auditStatus"
                                       readonly="readonly"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">个人介绍:</label>
                            </div>
                            <div class="col-sm-11" style="position: relative;">
                                <textarea id="introduce" rows="6" class="form-control" readonly="readonly"></textarea>
                                <p style="position:relative;left:1px;bottom: 1px;height: 20px;line-height: 20px;text-align:right;">
                                    字数：<span id="introduceNum">0</span></p>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <h3>图片信息</h3>
                        <div class="row">
                            <div class="col-sm-2 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">身份信息:</label>
                            </div>
                            <div class="col-sm-9">
                                <div class="lightBoxGallery" id="customerPicList"></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-2 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">头像:</label>
                            </div>
                            <div class="col-sm-9">
                                <div class="lightBoxGallery" id="customerPicHeader"></div>
                            </div>
                        </div>

                        <!-- 用户行为记录开始 -->
                        <div class="hr-line-dashed"></div>
                        <h3>用户行为记录</h3>
                        <div class="row m-b">
                            <button class="btn btn-primary" type="button" onclick="addBehavior()"><i
                                    class="fa fa-search"></i>&nbsp;新增用户行为
                            </button>
                        </div>
                        <div class="ibox float-e-margins">
                            <div class="ibox-content">
                                <div class="row row-lg">
                                    <div class="col-sm-12">
                                        <div class="example-wrap">
                                            <div class="example">
                                                <table id="listTable" class="table table-bordered table-hover">
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- 用户行为记录结束 -->

                        <div class="hr-line-dashed"></div>
                        <h3>审核历史</h3>
                        <div class="row">
                            <div class="col-sm-2 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">审核历史:</label>
                            </div>
                            <div class="col-sm-10">
                                <table id="operList" class="table table-hover">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 添加行为 菜单 弹出框 -->
<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                </button>
                <h4 class="modal-title">新增同步关联</h4>
            </div>
            <div class="modal-body">
                <div class="wrapper wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="form-group">
                                <div class="row">
                                    <label class="col-sm-2 control-label mtop">行为角色：</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="role">
                                            <option value="1" selected="selected">房东</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="col-sm-2 control-label mtop">行为属性：</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="attribute">
                                            <option value="1" selected="selected">限制行为</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="col-sm-2 control-label mtop">行为分类：</label>
                                    <div class="col-sm-8">
                                        <select class="form-control" id="type">
                                            <option value="1001" selected="selected">IM沟通违规(引流、提供联系方式、恶意引导等)</option>
                                            <option value="1002">辱骂客户</option>
                                            <option value="1003">刷单、优惠券套现</option>
                                            <option value="1004">营销活动作弊</option>
                                            <option value="1005">联系不上、不愿管理</option>
                                            <option value="1006">发布虚假房源</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="col-sm-2 control-label mtop">备注:</label>
                                    <div class="col-sm-8">
                                        <textarea rows="10" cols="50" id="remark" name="remark"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button type="button" class="btn btn-success" onclick="saveBehavior()">保存</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
<!-- 照片显示插件 -->
<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
<!-- Page-Level Scripts -->
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>

<script>

    var picBaseAddrMona = '${picBaseAddrMona}';
    var picSize = '${picSize}';
    $(function () {
        var uid = '${uid}';
        customerDetail.showDetail(uid);

    });
    var customerDetail = {
        idtype: function (value) {
            if (value == "1") {
                return "身份证";
            } else if (value == "2") {
                return "护照";
            } else if (value == "0") {
                return "其他";
            } else if (value == "6") {
                return "台湾居民来往通行证";
            } else if (value == "13") {
                return "港澳居民来往通行证";
            }
        },
        //审核状态
        audit: function (value) {
            if (value == "0") {
                return "未审核";
            } else if (value == "1") {
                return "审核通过";
            } else {
                return "审核未通过";
            }
        },
        sex: function (value) {
            if (value == "1") {
                return "女";
            } else if (value == "2") {
                return "男";
            } else {
                return "";
            }
        },
        role: function (value, row, index) {
            if (value == "1") {
                return "房东";
            } else if (value == "0") {
                return "房客";
            }
        },
        auditStatus: function (value, row, index) {
            if (value == "0") {
                return "未审核";
            } else if (value == "1") {
                return "审核通过";
            } else if (value == "2") {
                return "审核未通过";
            }
        },
        showDetail: function (uid) {
            $.getJSON("customer/customerDetailInfo", {uid: uid},
                function (data) {
                    $.each(data, function (i, n) {
                        if (i == "idType") {
                            $("#idType").val(customerDetail.idtype(n));
                        }
                        if (i == "customerSex") {
                            $("#customerSex").val(customerDetail.sex(n));
                        } else if (i == "isLandlord") {
                            $("#isLandlord").val(customerDetail.role(n));
                        } else if (i == "auditStatus") {
                            $("#auditStatus").val(customerDetail.audit(n));
                        } else if (i == "customerBirthday") {
                            $("#customerBirthday").val(new Date(n).format("yyyy-MM-dd"));
                        } else if (i == "idType") {
                            $("#idType").val(customerDetail.idtype(n));
                        } else if (i == "customerPicList") {
                            $("#customerPicHeader").empty();
                            $("#customerPicList").empty();
                            $.each(n, function (i, n) {
                                var picStr = "";
                                if (n.picBaseUrl) {
                                    if (n.picBaseUrl.indexOf("http") == -1) {
                                        picStr = picBaseAddrMona + n.picBaseUrl + n.picSuffix + picSize + n.picSuffix.toLowerCase();
                                    } else {
                                        picStr = n.picBaseUrl;
                                    }
                                }

                                var picStr = "<a href='" + picStr + "' data-gallery='blueimp-gallery'><img src='" + picStr + "' style='height:135px;width:165px;' /></a>";
                                if (n.picType == "3") {
                                    $("#customerPicHeader").append(picStr);
                                } else {
                                    $("#customerPicList").append(picStr);
                                }

                            });
                        } else if (i == "customerOperHistoryList") {
                            $("#operList").empty();
                            $.each(n, function (i, n) {
                                var tr = "<tr><td>"
                                    + (i + 1)
                                    + "</td><td>"
                                    + customerDetail.audit(n.auditAfterStatus)
                                    + "</td><td>"
                                    + n.operRemark
                                    + "</td><td>"
                                    + (new Date(n.createTime).format("yyyy-MM-dd"))
                                    + "</td>";
                                $("#operList").append(tr);
                            });
                        } else if (i == "customerBaseMsgExtEntity") {
                            if (n) {
                                $("#introduce").val(n.customerIntroduce);
                                $("#introduceNum").text(n.customerIntroduce.length);
                            } else {
                                $("#introduce").val("");
                                $("#introduceNum").text("0");
                            }
                        } else {
                            $("#" + i).val(n);
                        }
                    });
                });

        }
    }

    var strapTable = function (tableId, dataUrl, queryParam, columnsData) {
        $('#' + tableId).bootstrapTable('destroy');
        $('#' + tableId).bootstrapTable({
            url: dataUrl,   //请求后台的URL（*）
            contentType: "application/x-www-form-urlencoded",
            method: 'post',      //请求方式（*）
            cache: false,      //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,     //是否显示分页（*）
            queryParams: queryParam,//传递参数（*）
            sidePagination: "server",   //分页方式：client客户端分页，server服务端分页（*）
            pageSize: 10,      //每页的记录行数（*）
            pageList: [10, 20, 50],  //可供选择的每页的行数（*）
            columns: columnsData,
            toggle: 'table',
            paginationFirstText: '首页',
            paginationPreText: '上一页',
            paginationNextText: '下一页',
            paginationLastText: '末页',
            singleSelect: true,
            height: 520,
            clickToSelect: true
        });
    }

    $(function () {
        strapTable('listTable', 'customer/getCustomerBehaviorList', paginationParam, columnsData);
    });

    //分页查询参数
    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            uid: $.trim($("#uid").val())
        };
    }

    //初始化列表列
    var columnsData = [
        {
            field: 'typeName',
            title: '行为分类',
            align: 'center'
        }, {
            field: 'remark',
            title: '备注',
            align: 'center',
            formatter: 'formatRemark'
        }, {
            field: 'roleName',
            title: '行为角色',
            align: 'center'
        }, {
            field: 'attributeName',
            title: '行为属性',
            align: 'center',
            formatter: 'formatAttribute'
        }, {
            field: 'operationName',
            title: '记录人',
            align: 'center'
        }, {
            field: 'createTypeName',
            title: '数据来源',
            align: 'center'
        }, {
            field: 'createDate',
            title: '时间',
            align: 'center',
            formatter: 'CommonUtils.formateTimeStr'
        }];

    // 备注
    function formatRemark(value, row, index) {
        if (row.createType == 1) {
            return "<a href='javascript:showRemarkDetail(\"" + row.type + "\",\"" + row.proveFid + "\",\"" + row.remark + "\");'>详情</a>";
        } else if (row.createType == 2) {
            return value;
        }
    }

    // 备注详情
    function showRemarkDetail(type, proveFid, remark) {
        if (type == 1101 || type == 1102 || type == 2101) {
            $.openNewTab(new Date().getTime(), 'order/getOrderDetail?orderSn=' + proveFid, "订单详情");
        } else {
            layer.alert(remark);
        }
    }

    // 行为属性
    function formatAttribute(value, row, index) {
        if(row.createType == 1 && row.attribute == 1 && row.operated == 0){
            return value + "<button type='button' class='btn btn-primary btn-xs' onclick='updateAttribute(\""+row.fid+"\", 3)'>修改为中性行为</button>";
        }else{
            return value;
        }
    }

    // 修改行为属性
    function updateAttribute(fid) {
        layer.confirm("确定将此次行为修改为中性行为？", function(){
            $.ajax({
                type: "POST",
                url: "customer/updateCustomerBehaviorAttr",
                dataType:"json",
                data: {"fid":fid},
                success: function (data) {
                    if(data){
                        layer.closeAll("loading");
                        if (data.code == 0){
                            layer.alert("修改成功", {icon: 1,time: 2000, title:'提示'});
                            $("#listTable").bootstrapTable('refresh');
                        }else{
                            layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
                        }
                    }else{
                        layer.alert("修改失败", {icon: 5,time: 2000, title:'提示'});
                    }
                },
                error: function(result) {
                    layer.closeAll("loading");
                    layer.alert("修改失败", {icon: 5,time: 2000, title:'提示'});
                }
            });
        });
    }

    /**
     * 新增行为
     */
    function addBehavior(){
        $('#myModal').modal('show');
    }

    function saveBehavior() {

        var remark = $('#remark').val();
        if(remark == null || remark == ""){
            layer.alert("备注必填！", {icon: 5,time: 2000, title:'提示'});
            return;
        }
        if(remark.length > 100){
            layer.alert("备注长度100字以内！", {icon: 5,time: 2000, title:'提示'});
            return;
        }

        var role = $('#role').val();
        var attribute = $('#attribute').val();
        var type = $('#type').val();

        $.ajax({
            type: "POST",
            url: "customer/addCustomerBehavior",
            dataType:"json",
            data: {
                "uid": $.trim($("#uid").val()),
                "role":role,
                "attribute":attribute,
                "type":type,
                "remark":remark
            },
            success: function (data) {
                if(data){
                    layer.closeAll("loading");
                    if (data.code == 0){
                        layer.alert("增加成功", {icon: 1,time: 2000, title:'提示'});
                        $('#myModal').modal('hide');
                        $("#listTable").bootstrapTable('refresh');
                    }else{
                        layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
                    }
                }else{
                    layer.alert("增加失败", {icon: 5,time: 2000, title:'提示'});
                }
            },
            error: function(result) {
                layer.closeAll("loading");
                layer.alert("增加失败", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }

</script>
</body>
</html>
