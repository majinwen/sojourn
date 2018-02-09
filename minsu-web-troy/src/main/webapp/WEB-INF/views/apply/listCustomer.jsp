<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        .modal-lm{min-width:880px;}
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }
        .row{margin:0;}
        #operList td{font-size:12px;}
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
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form class="form-group">
                    <div class="row m-b">
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">姓名:</label>
                        </div>
                        <div class="col-sm-2">
                            <input id="S_realName" name="realName" type="text"
                                   class="form-control">
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">用户名:</label>
                        </div>
                        <div class="col-sm-2">
                            <input id="S_nickName" name="nickName" type="text"
                                   class="form-control">
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">手机号:</label>
                        </div>
                        <div class="col-sm-3">
                            <input id="S_customerMobile" name="customerMobile" type="text"
                                   class="form-control">
                        </div>
                    </div>
                    <div class="row m-b">
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">角色:</label>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control" name="isLandlord" id="S_isLandlord">
                                <option value="">全部</option>
                                <option value="1">房东</option>
                                <option value="0">房客</option>
                            </select>
                        </div>

                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">资质:</label>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control" name="roleCode" id="S_roleCode">
                                <option value="">全部</option>
                                <c:forEach items="${list }" var="obj">
                                    <option value="${obj.roleCode }" >${obj.roleName }</option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">审核状态:</label>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control" id="S_auditStatus" name="auditStatus">
                                <option value="">全部</option>
                                <option value="0">未审核</option>
                                <option value="1">审核通过</option>
                                <option value="2">审核未通过</option>
                            </select>
                        </div> -->


                        <customtag:authtag authUrl="customer/listCustomerMsg" >
                            <button class="btn btn-primary" type="button" style="margin-left: 77px;"
                                    onclick="CommonUtils.query();">
                                <i class="fa fa-search"></i>&nbsp;查询
                            </button>
                        </customtag:authtag>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="row row-lg">
            <div class="col-sm-12">
                <div class="example-wrap">
                    <div class="example">
                        <table class="table table-bordered" id="listTable"
                               data-click-to-select="true" data-toggle="table"
                               data-side-pagination="server" data-pagination="true"
                               data-page-list="[10,20,50]" data-pagination="true"
                               data-page-size="10" data-pagination-first-text="首页"
                               data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
                               data-pagination-last-text="末页"
                               data-content-type="application/x-www-form-urlencoded"
                               data-query-params="paginationParam" data-method="post"
                               data-height="520"
                               data-single-select="true" data-url="customer/customerDataList">
                            <thead>
                            <tr>
                                <th data-field="uid" data-visible="false"></th>
                                <th data-field="realName" data-width="13%" data-align="center" >姓名</th>
                                <th data-field="customerMobile" data-width="13%" data-align="center">手机号</th>
                                <th data-field="nickName" data-width="12%" data-align="center">用户名</th>
                                <th data-field="isLandlord" data-width="11%" data-align="center" data-formatter="customerDetail.role">角色</th>
                                <th data-field="customerJob" data-width="11%" data-align="center">职业</th>
                                <th data-field="roleNames" data-width="11%" data-align="center">资质</th>
                                <th data-field="operate" data-width="10%" data-align="center" data-formatter="opInfo">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <!-- End Example Pagination -->
            </div>
        </div>
    </div>
</div>
<!--弹出框 -->




<!-- 弹出框角色 -->
<div class="modal inmodal fade" id="roleModel" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title">资质列表</h4>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-sm-12">
                        <div class="example-wrap">
                            <div class="example">
                                <!-- 弹出框列表 -->
                                <table id="roleTable" class="table table-hover table-bordered"  data-click-to-select="true"
                                       data-toggle="table"
                                       data-side-pagination="server"
                                       data-pagination="true"
                                       data-page-list="[1,20,50]"
                                       data-pagination="true"
                                       data-page-size="10"
                                       data-show-refresh="true"
                                       data-pagination-first-text="首页"
                                       data-pagination-pre-text="上一页"
                                       data-pagination-next-text="下一页"
                                       data-pagination-last-text="末页"
                                       data-query-params="roleParam"
                                       data-method="get"
                                       data-height="500"
                                       data-url="apply/showRoles">
                                    <thead>
                                    <tr>
                                        <th data-field="id" data-checkbox="true"></th>
                                        <th data-field="fid" data-visible="false"></th>
                                        <th data-field="roleName" data-align="center">名称</th>
                                        <th data-field="roleCode" data-align="center">编码</th>
                                        <th data-field="isFrozen" data-formatter="formateRoleStatus" >状态</th>
                                        <th data-field="op" data-align="center"  data-formatter="formateRoleOp">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="getSelectRole()" class="btn btn-primary"  data-dismiss="modal">保存</button>
            </div>
        </div>
    </div>
</div>



<input type="hidden" id="op_uid">

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


    /*角色列表参数*/
    function roleParam(params) {
        return {
            limit: params.limit,
            page: $('#roleTable').bootstrapTable('getOptions').pageNumber,
            uid:$('#op_uid').val()
        };
    }

    var picBaseAddrMona = '${picBaseAddrMona}';
    var picSize = '${picSize}';

    var customerDetail = {
        idtype : function(value) {
            if (value == "1") {
                return "身份证";
            }else if(value == "2"){
                return "护照";
            }else if(value == "0"){
                return "其他";
            }else if(value == "6"){
                return "台湾居民来往通行证";
            }else if(value == "13"){
                return "港澳居民来往通行证";
            }
        },
        //审核状态
        audit : function(value) {
            if (value == "0") {
                return "未审核";
            } else if (value == "1") {
                return "审核通过";
            } else {
                return "审核未通过";
            }
        },
        sex : function(value) {
            if (value == "1") {
                return "女";
            } else if(value == "2"){
                return "男";
            }else{
                return "";
            }
        },
        role : function(value, row, index) {
            if (value == "1") {
                return "房东";
            } else if (value == "0") {
                return "房客";
            }
        },

        formateSeed : function(value, row, index) {
            if(value == 0){
                return  "未成为种子房东";
            }else  if(value == 1){
                return  "种子房东";
            }else if(value == 2){
                return  "种子房东已冻结";
            }
        },

        auditStatus : function(value, row, index) {
            if (value == "0") {
                return "未审核";
            } else if (value == "1") {
                return "审核通过";
            } else if (value == "2") {
                return "审核未通过";
            }
        },
        nameshow:function(value){
            return "<a href='javascript:void(0);'>"+value+"</a>";
        },
        showDetail : function(uid) {
            $.getJSON("customer/customerDetailInfo",{uid:uid},
                    function(data) {
                        console.log(data);
                        $.each(data,function(i, n) {
                            if (i == "idType") {
                                $("#idType").val(customerDetail.idtype(n));
                            }if (i == "customerSex") {
                                console.log(n);
                                $("#customerSex").val(customerDetail.sex(n));
                            } else if (i == "isLandlord") {
                                $("#isLandlord").val(customerDetail.role(n));
                            } else if (i == "auditStatus") {
                                $("#auditStatus").val(customerDetail.audit(n));
                            } else if(i == "customer Birthday"){
                                $("#customerBirthday").val(new Date(n).format("yyyy-MM-dd"));
                            }else if(i == "idType"){
                                $("#idType").val(customerDetail.idtype(n));
                            } else if (i == "customerPicList") {
                                $("#customerPicHeader").empty();
                                $("#customerPicList").empty();
                                $.each(n,function(i,n) {
                                    var picStr = "<a href='"+picBaseAddrMona + n.picBaseUrl + n.picSuffix + picSize + n.picSuffix+"' data-gallery='blueimp-gallery'><img src='"+picBaseAddrMona + n.picBaseUrl + n.picSuffix + picSize + n.picSuffix+"' style='height:135px;width:165px;' /></a>";
                                    if (n.picType == "3") {
                                        if(n.picServerUuid){
                                            $("#customerPicHeader").append(picStr);
                                        }else{
                                            var str = "<a href='" + n.picBaseUrl +"' data-gallery='blueimp-gallery'><img src='"+ n.picBaseUrl +"' style='height:135px;width:165px;' /></a>";
                                            $("#customerPicHeader").append(str);
                                        }
                                    } else {
                                        $("#customerPicList").append(picStr);
                                    }

                                });
                            } else if (i == "customerOperHistoryList") {
                                $("#operList").empty();
                                $.each(n,function(i,n) {
                                    var tr = "<tr><td>"
                                            + (i+1)
                                            + "</td><td>"
                                            + customerDetail.audit(n.auditAfterStatus)
                                            + "</td><td>"
                                            + n.operRemark
                                            + "</td><td>"
                                            + (new Date(n.createTime).format("yyyy-MM-dd"))
                                            + "</td>";
                                    $("#operList").append(tr);
                                });
                            } else if(i == "customerBaseMsgExtEntity"){
                                if(n){
                                    $("#introduce").val(n.customerIntroduce);
                                } else {
                                    $("#introduce").val("");
                                }
                            }else {
                                $("#" + i).val(n);
                            }
                        });
                    });

        }
    }

    //分页查询参数
    function paginationParam(params) {
        return {
            limit : params.limit,
            page : $('#listTable').bootstrapTable('getOptions').pageNumber,
            customerMobile : $.trim($('#S_customerMobile').val()),
            realName : $.trim($('#S_realName').val()),
            nickName : $('#S_nickName').val(),
            isLandlord : $('#S_isLandlord option:selected').val(),
            roleCode : $('#S_roleCode option:selected').val(),
            auditStatus : $('#S_auditStatus option:selected').val()
        };
    }
    //点击单元格
    $('#listTable').on('click-cell.bs.table',
            function(field, value, row, $element) {
                if (value == "uid") {
                    $("#customerDetailInfoModel").modal("toggle");
                    customerDetail.showDetail(row);
                }
            });


    $("#modalClose").click(function() {
        $('#customerDetailInfoModel').modal('hide')
    })




    //操作
    function formateRoleStatus(value, row, index){
        if(row.isFrozen == 0){
            return  "审核通过" ;
        }else  if(row.isFrozen == 1){
            return  "已冻结";
        }else{
            return  "未通过";
        }
    }


    //操作
    function formateStatus(value, row, index){

        if(row.isSeed == 0){
            return  "未成为种子房东";
        }else  if(row.isSeed == 1){
            return  "种子房东";
        }else if(row.isSeed == 2){
            return  "种子房东已冻结";
        }
    }

    //操作
    function opInfo(value, row, index){

        return "<button type='button' class='btn btn-white'  onclick='showInfo(\""+row.uid+"\")' ><span>详情</span></button>";
    }


    //操作
    function showInfo(uid){
        console.log(uid);
        $('#op_uid').val(uid);
        $('#roleModel').modal('show');
        $('#roleTable').bootstrapTable('refresh', {query: {}});
    }




    //操作
    function formateRoleOp(value, row, index){
        if(row.isFrozen == 0){
            return "<button type='button' class='btn btn-red btn-sm' onclick='frozenSeed(\""+row.uid+"\",\""+row.roleCode+"\");'><span> 冻结</span></button>";
        }else  if(row.isFrozen == 1){
            return "<button type='button' class='btn btn-red btn-sm' onclick='unfrozenSeed(\""+row.uid+"\",\""+row.roleCode+"\");'><span> 解冻</span></button>";
        }else{
            return "<button type='button' class='btn btn-red btn-sm' onclick='agreeSeed(\""+row.uid+"\",\""+row.roleCode+"\");'><span> 同意</span></button>";
        }
    }

    //操作
    function formateOperate(value, row, index){
        if(row.isSeed == 0){
        	return "<button type='button' class='btn btn-red btn-sm' onclick='agreeSeed(\""+row.uid+"\");'><span> 同意</span></button>";
        }else  if(row.isSeed == 1){
        	return "<button type='button' class='btn btn-red btn-sm' onclick='frozenSeed(\""+row.uid+"\");'><span> 冻结</span></button>";
            
        }else if(row.isSeed == 2){
        	return "<button type='button' class='btn btn-red btn-sm' onclick='unfrozenSeed(\""+row.uid+"\");'><span> 解冻</span></button>";
        }
    }

    //同意成为种子房东
    function agreeSeed(uid,roleCode){
    	CommonUtils.ajaxPostSubmit("apply/agreeSeed",{'uid':uid,'roleCode':roleCode},function(result){
    		if(result.code == 0){
    			layer.alert("成功", {icon: 6,time: 2000, title:'提示'});
    			//刷新table
                refreshRoleTable();
                refreshTable()
    		}else{
    			layer.alert(result.msg, {icon: 6,time: 2000, title:'提示'});
    		}
    	});
    }

    //冻结种子房东
    function frozenSeed(uid,roleCode){
    	CommonUtils.ajaxPostSubmit("apply/frozenSeed",{'uid':uid,'roleCode':roleCode},function(result){
    		if(result.code == 0){
    			layer.alert("成功", {icon: 6,time: 2000, title:'提示'});
    			//刷新table
                refreshRoleTable();
                refreshTable()
    		}else{
    			layer.alert(result.msg, {icon: 6,time: 2000, title:'提示'});
    		}
    	});

    }


    //解冻种子房东
    function unfrozenSeed(uid,roleCode){
    	CommonUtils.ajaxPostSubmit("apply/unfrozenSeed",{'uid':uid,'roleCode':roleCode},function(result){
    		if(result.code == 0){
    			layer.alert("成功", {icon: 6,time: 2000, title:'提示'});
    			//刷新table
                refreshRoleTable();
                refreshTable()
    		}else{
    			layer.alert(result.msg, {icon: 6,time: 2000, title:'提示'});
    		}
    	});

    }


    function refreshTable(){
        $('#listTable').bootstrapTable('refresh', {query: {}});
    }

    function refreshRoleTable(){
        $('#roleTable').bootstrapTable('refresh', {query: {}});
    }

    //更新个人描述
    function upCustomerIntroduce(){
        $.ajax({
            url:"${basePath}customer/updataCustomerIntroduce",
            data:{"uid":$("#uid").val(),"introduce":$("#introduce").val()},
            dataType:"json",
            type:"post",
            async: true,
            success:function(result) {
                if(result.code === 0){
                    $('#customerDetailInfoModel').modal('hide');
                }else{
                    layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
                }
            },
            error:function(result){
                layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }
</script>
</body>
</html>
