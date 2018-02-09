<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <form class="form-group">
                <div class="hr-line-dashed"></div>
                <b >活动信息</b>
                <div class="hr-line-dashed"></div>
                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动码:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${ac.actSn}" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动名称:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${ac.actName}" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动来源:</label>
                    </div>
                    <div class="col-sm-2">
                        <c:if test="${ac.actSource == 1 }">
                            <input type="text" class="form-control" value="平台" readonly="readonly">
                        </c:if>
                        <c:if test="${ac.actSource == 2 }">
                            <input type="text" class="form-control" value="房东" readonly="readonly">
                        </c:if>
                        <c:if test="${ac.actSource == 3 }">
                            <input type="text" class="form-control" value="用户" readonly="readonly">
                        </c:if>
                    </div>
                </div>





                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动状态:</label>
                    </div>
                    <div class="col-sm-2">
                        <c:if test="${ac.actStatus == 1 }">
                            <input type="text" class="form-control" value="未启用" readonly="readonly">
                        </c:if>
                        <c:if test="${ac.actStatus == 2 }">
                            <input type="text" class="form-control" value="已启用" readonly="readonly">
                        </c:if>
                        <c:if test="${ac.actStatus == 3 }">
                            <input type="text" class="form-control" value="已中止" readonly="readonly">
                        </c:if>
                    </div>


                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">优惠券类型:</label>
                    </div>
                    <div class="col-sm-2">
                        <c:if test="${detail.actType == 1 }">
                            <input type="text" class="form-control" value="优惠券抵现金活动" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 2 }">
                            <input type="text" class="form-control" value="折扣券活动" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 3 }">
                            <input type="text" class="form-control" value="随机现金券活动" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 4 }">
                            <input type="text" class="form-control" value="免天优惠券活动" readonly="readonly">
                        </c:if>
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">生成数量:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${ac.couponNum}" readonly="readonly">
                    </div>
                </div>



                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动开始时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${ac.actStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动结束时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${ac.actEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">活动创建时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${ac.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">

                    </div>
                </div>




                <div class="hr-line-dashed"></div>
                券信息
                <div class="hr-line-dashed"></div>







                <div class="row m-b">

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">优惠券编号:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${detail.couponSn }" readonly="readonly">

                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">券有效期从:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value=" <fmt:formatDate value="${detail.couponStartTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">券有效期到:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value=" <fmt:formatDate value="${detail.couponEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">


                    </div>



                </div>
                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">是否限制入住时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <c:if test="${check == 1 }">
                            <input type="text" class="form-control" value="不限制使用时间" readonly="readonly">
                        </c:if>
                        <c:if test="${check != 1 }">
                            <input type="text" class="form-control" value="限制使用时间" readonly="readonly">
                        </c:if>
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">入住时间从:</label>
                    </div>
                    <div class="col-sm-2">

                        <c:if test="${checkInTime == 1 }">
                            <input type="text" class="form-control" value="不限制" readonly="readonly">
                        </c:if>
                        <c:if test="${checkInTime != 1 }">
                            <input type="text" class="form-control" value="<fmt:formatDate value="${detail.checkInTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                        </c:if>


                       </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">入住时间到:</label>
                    </div>
                    <div class="col-sm-2">

                        <c:if test="${checkOutTime == 1 }">
                            <input type="text" class="form-control" value="不限制" readonly="readonly">
                        </c:if>
                        <c:if test="${checkOutTime != 1 }">
                            <input type="text" class="form-control" value="<fmt:formatDate value="${detail.checkOutTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                        </c:if>
                    </div>

                </div>
                <div class="row m-b">


                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">优惠券生成时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                    </div>


                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">限制领取次数:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${detail.times}" readonly="readonly">
                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">使用条件:</label>
                    </div>
                    <div class="col-sm-2">
                        <c:if test="${detail.actLimit == 0 }">
                            <input type="text" class="form-control" value="任意订单可用" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actLimit != 0 }">
                            <input type="text" class="form-control" value="订单满${detail.actLimit/100}元可用" readonly="readonly">
                        </c:if>

                    </div>

                </div>
                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">限制房源:</label>
                    </div>
                    <c:if test="${detail.isLimitHouse == 0 }">
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="不限制" readonly="readonly">
                    </div>
                    </c:if>
                    <c:if test="${detail.isLimitHouse == 1 }">
                        <div class="col-sm-10">
                            <input type="text" class="form-control" value="${houseSns}" readonly="readonly">
                        </div>
                    </c:if>

                </div>

                <div class="hr-line-dashed"></div>
                领取人信息
                <c:if test="${has != 1 }">【未被领取】
                </c:if>
                <c:if test="${has == 1 }">【已经被领取】
                </c:if>
                <div class="hr-line-dashed"></div>

                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">优惠券状态:</label>
                    </div>
                    <div class="col-sm-2">

                        <c:if test="${detail.couponStatus == 1 }">
                            <input type="text" class="form-control" value="未领取" readonly="readonly">
                        </c:if>

                        <c:if test="${detail.couponStatus == 2 }">
                            <input type="text" class="form-control" value="已领取" readonly="readonly">
                        </c:if>

                        <c:if test="${detail.couponStatus == 3 }">
                            <input type="text" class="form-control" value="已冻结" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.couponStatus == 4 }">
                            <input type="text" class="form-control" value="已使用" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.couponStatus == 5 }">
                            <input type="text" class="form-control" value="已过期" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.couponStatus == 6 }">
                            <input type="text" class="form-control" value="已发送" readonly="readonly">
                        </c:if>

                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">领取人:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${detail.uid}" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">领取时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${detail.usedTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
                    </div>
                </div>

                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">优惠:</label>
                    </div>
                    <div class="col-sm-2">

                        <c:if test="${detail.actType == 1 }">
                            <input type="text" class="form-control" value="立减${detail.actCut/100}元" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 2 }">
                            <input type="text" class="form-control" value="打${detail.actCut/10}折" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 3 }">
                            <input type="text" class="form-control" value="立减${detail.actCut/100}元" readonly="readonly">
                        </c:if>
                        <c:if test="${detail.actType == 4 }">
                            <input type="text" class="form-control" value="${detail.actCut}天免费" readonly="readonly">
                        </c:if>
                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">使用订单:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${detail.orderSn}" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">使用时间:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${detail.usedTime}" readonly="readonly">
                    </div>

                </div>

                <c:if test="${detail.actMax > 0 }">
                    <div class="row m-b">
                        <div class="col-sm-1 text-right">
                            <label class="control-label" style="height: 35px; line-height: 35px;">优惠限制:</label>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" value="最大优惠金额小于${detail.actMax/100}元" readonly="readonly">
                        </div>

                    </div>
                </c:if>




            </form>
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

        $(function(){
            $(".feeItemCode").each(function(i,n){
                var val=$(this).text();
                $(this).text(getfeeItemStr(val))
            });
            $(".callStatus").each(function(i,n){
                var val=$(this).text();
                $(this).text(getCallStatus(val))
            });
        })

        function getfeeItemStr(value){
            var feeItemStr= "*";
            if(value == 1){
                feeItemStr= "房租";
            }else if(value == 2){
                feeItemStr= "押金";
            }else if(value == 3){
                feeItemStr= "赔付款";
            }else if(value == 4){
                feeItemStr= "违约金";
            }else if(value == 5){
                feeItemStr= "用户结算";
            }else if(value == 6){
                feeItemStr= "提现";
            }else if(value == 7){
                feeItemStr= "额外消费";
            }
            return feeItemStr;
        }

        function test11(value) {
            alert(value);
            return 11;
        }

        function getCallStatus(value){
            var status="*";
            if(value == 0){
                status= "失败";
            }else if(value == 1){
                status= "成功";
            }
            return status;
        }
    </script>
</body>
</html>
