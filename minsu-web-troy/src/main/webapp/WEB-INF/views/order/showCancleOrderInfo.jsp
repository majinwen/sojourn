<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <style>
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }

        label {
            font-weight: 500;
            margin-top: 8px;
        }

        .room-pic {
            float: left;
        }

        .room-pic p {
            text-align: center;
        }

        .blueimp-gallery > .title {
            left: 0;
            bottom: 45px;
            top: auto;
            width: 100%;
            text-align: center;
        }

        .house-desc-color {
            position: absolute;
            right: 20px;
            bottom: 6px;
            color: cc3366;
            font-size: 6px
        }

        .checkbox {
            height: 50px;
        }
    </style>
</head>
<body class="gray-bg">
<div id="blueimp-gallery" class="blueimp-gallery">
    <div class="slides"></div>
    <h3 class="title"></h3>
    <a class="prev">‹</a>
    <a class="next">›</a>
    <a class="close">×</a>
    <a class="play-pause"></a>
    <ol class="indicator"></ol>
</div>
<div class="wrapper wrapper-content animated fadeInRight">
    <input id="orderSn" name="orderSn" value="${orderSn }" type="hidden">
    <input id="switchLanMoneyCur" name="switchLanMoneyCur" value="" type="hidden">
    <input id="switchTenCurDayCur" name="switchTenCurDayCur" value="" type="hidden">
    <input id="switchCleanFeeCur" name="switchCleanFeeCur" value="" type="hidden">
    <input id="curDay" name="curDay" value="" type="hidden">
    <input id="commissionRateLandlord" name="commissionRateLandlord" value="" type="hidden">
    <input id="curDayMoney" name="curDayMoney" value="" type="hidden">
    <input id="isHascurDayMoney" name="isHascurDayMoney" value="" type="hidden">
    <h2 class="title-font text-center">订单金额明细</h2>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="form-group">
                        <h2 class="title-font text-righ">订单支付金额</h2>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">房租(折扣之前):</label>
                            <div class="col-sm-1">
                                <input id="rentalMoneyAll" name="rentalMoneyAll" value="${response.orderInfo.rentalMoneyAll/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>


                            <label class="col-sm-1 control-label text-center">押金:</label>
                            <div class="col-sm-1 text-center">
                                <input id="depositMoneyAll" name="depositMoneyAll" value="${response.orderInfo.depositMoneyAll/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">房客服务费:</label>
                            <div class="col-sm-1 text-center">
                                <input id="userCommMoney" name="userCommMoney" value="${response.orderInfo.userCommMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">优惠券:</label>
                            <div class="col-sm-1">
                                <input id="couponMoneyAll" name="couponMoneyAll" value="${response.orderInfo.couponMoneyAll/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <h2 class="title-font">订单已消费金额</h2>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">房租(折扣之后):</label>
                            <div class="col-sm-1">
                                <input id="rentalMoneyConsume" name="rentalMoneyConsume" value="${response.orderInfo.rentalMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">房客服务费:</label>
                            <div class="col-sm-1">
                                <input id="userCommMoneyConsume" name="userCommMoneyConsume" value="${response.orderInfo.realUserMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">清洁费:</label>
                            <div class="col-sm-1">
                                <input id="cleanMoneyConsume" name="cleanMoneyConsume" value="${response.orderInfo.cleanMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">优惠券金额:</label>
                            <div class="col-sm-1">
                                <input id="couponMoney" name="couponMoney" value="${response.orderInfo.couponMoney/100}" readonly="readonly" type="text" class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">折扣金额:</label>
                            <div class="col-sm-1">
                                <input id="discountMoney" name="discountMoney" value="${response.orderInfo.discountMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                            <label class="col-sm-1 control-label text-center">违约金:</label>
                            <div class="col-sm-1">
                                <input id="penaltyMoney" name="penaltyMoney" value="${response.orderInfo.penaltyMoney/100}" readonly="readonly" type="text"
                                       class="form-control m-b">
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 第二个 开始 -->
    <h2 class="title-font text-center">取消订单操作</h2>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="form-group">
                        <h2 class="title-font text-righ">订单支付金额</h2>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">取消类型:</label>
                            <label class="col-sm-1 control-label text-left">
                                      ${response.cancelOrderServiceRequest.cancelTypeName}
                            </label>
                        </div>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">原因备注:</label>
                            <label class="col-sm-5 control-label text-left">
                                ${response.cancelReason}
                            </label>
                        </div>
                         <div class="row">
                            <c:if test="${orderStatus eq 38}">
                                      <label class="col-sm-1 control-label text-center">惩罚措施:  </label>
                            </c:if>
                             <c:if test="${orderStatus eq 73}">
                                      <label class="col-sm-1 control-label text-center">惩罚措施:  </label>
                            </c:if>
                            <c:if test="${response.cancelOrderServiceRequest.isTakeFirstNightMoney eq 1}">
                                <label class="col-sm-1 control-label text-center">
                                       <c:if test="${response.cancelOrderServiceRequest.isTakeFirstNightMoneyDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isTakeFirstNightMoneyDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.takeFirstNightMoneyName}
                                </label>
                            </c:if>
                            <c:if test="${response.cancelOrderServiceRequest.isTakeOneHundred eq 1}">
                                <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isTakeOneHundredDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isTakeOneHundredDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.takeOneHundredName}
                                </label>
                            </c:if>
                            <c:if test="${response.cancelOrderServiceRequest.isCancelAngel eq 1}">
                                     <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isCancelAngelDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isCancelAngelDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.cancelAngelName}
                                </label>
                            </c:if>
                            <c:if test="${response.cancelOrderServiceRequest.isAddSystemEval eq 1}">
                                <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isAddSystemEvalDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isAddSystemEvalDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.addSystemEvalName}
                                </label>
                            </c:if>
                            <c:if test="${response.cancelOrderServiceRequest.isUpdateRankFactor eq 1}">
                                 <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isUpdateRankFactorDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isUpdateRankFactorDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.updateRankFactorName}
                                </label>
                            </c:if>
                             <c:if test="${response.cancelOrderServiceRequest.isShieldCalendar eq 1}">
                                <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isShieldCalendarDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isShieldCalendarDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.shieldCalendarName}
                                </label>
                            </c:if>
                             <c:if test="${response.cancelOrderServiceRequest.isGiveCoupon eq 1}">
                                  <label class="col-sm-1 control-label text-center">
                                      <c:if test="${response.cancelOrderServiceRequest.isGiveCouponDone eq 1}">
                                            <input type='checkbox' checked='checked' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      <c:if test="${response.cancelOrderServiceRequest.isGiveCouponDone eq 0}">
                                            <input type='checkbox' disabled='disabled' name='' value=''/>
                                      </c:if>
                                      ${response.cancelOrderServiceRequest.giveCouponName}
                                </label>
                            </c:if>
                             <c:if test="${orderStatus eq 38}">
                                 <label class="col-sm-4 control-label text-left">(备注：选中表示该项惩罚措施已执行成功)</label>
                             </c:if>
                             <c:if test="${orderStatus eq 73}">
                                 <label class="col-sm-4 control-label text-left">(备注：选中表示该项惩罚措施已执行成功)</label>
                             </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <h2 class="title-font text-center">退款明细</h2>
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="form-group">

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">给房东打款:</label>
                            <label class="col-sm-5 control-label text-left">
                              <c:if test="${not empty response.lanPay}">
                                   <c:forEach items="${response.lanPay}" var="mymap">
		                            　                                     　<c:out value="${mymap.key}"/>:<c:out value="${mymap.value}"/>
		                            </c:forEach>
                              </c:if>
                              <c:if test="${ empty response.lanPay}">0</c:if>
                          
                            </label>
                        </div>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">给房客打款:</label>
                            <label class="col-sm-5 control-label text-left">
                            <c:if test="${not empty response.userPay}">
                                 <c:forEach items="${response.userPay}" var="mymap">
                            　                                                    　<c:out value="${mymap.key}"/>:<c:out value="${mymap.value}"/>
                                 </c:forEach>
                            </c:if>
                             <c:if test="${empty response.userPay}">
                                    0
                             </c:if>
                            </label>
                        </div>

                        <div class="row">
                            <label class="col-sm-1 control-label text-center">平台佣金:</label>
                            <label class="col-sm-5 control-label text-left">
                             <c:if test="${not empty response.income}">
                                 <c:forEach items="${response.income}" var="mymap">
						                            　　<c:out value="${mymap.key}"/>:<c:out value="${mymap.value}"/>
						          </c:forEach>
                             </c:if>
                              <c:if test="${empty response.income}">0</c:if>
                          
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 第三个 结束 -->

    <h2 class="title-font text-center">订单操作日志</h2>
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="example-wrap">
                        <div class="example">
                            <table class="table table-bordered">
                                <thead>
                                <tr>
                                    <th width="20%">操作时间</th>
                                    <th width="20%">初始状态</th>
                                    <th width="20%">变更为</th>
                                    <th width="40%">备注</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${logs}" var="log">
                                    <tr>
                                        <td><fmt:formatDate type="both" value="${log.createDate}"/></td>
                                        <td>${log.fromStatus}</td>
                                        <td>${log.toStatus}</td>
                                        <td>${log.mark}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- End Example Pagination -->
                </div>
            </div>
        </div>
    </div>
</div>



<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>

<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>

<!-- blueimp gallery -->
<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>

</body>
</html>