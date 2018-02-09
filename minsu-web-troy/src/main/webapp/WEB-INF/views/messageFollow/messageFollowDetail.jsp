<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
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
                <b>IM跟进记录</b>
                <div class="hr-line-dashed"></div>
                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">城市:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" id="cityName" class="form-control" value="${firstDetail.cityName}"
                               readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">房源名称:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${firstDetail.houseName}" readonly="readonly">
                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">入住人数:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" id="personNum" class="form-control" value=" ${firstDetail.personNum}"
                               readonly="readonly">
                    </div>

                </div>
                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">创建日期:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control"
                               value=" <fmt:formatDate value="${firstDetail.createDate }" pattern="yyyy-MM-dd"/>"
                               readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">入住日期:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" id="startTime" class="form-control"
                               value=" <fmt:formatDate value="${firstDetail.startDate }" pattern="yyyy-MM-dd"/>"
                               readonly="readonly">
                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">离开日期:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" id="endTime" class="form-control"
                               value=" <fmt:formatDate value="${firstDetail.endDate }" pattern="yyyy-MM-dd"/>"
                               readonly="readonly">
                    </div>

                </div>
                <div class="row m-b">

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">预订人:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${firstDetail.tenantName}" readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">预订人电话:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${firstDetail.tenantMobile}" readonly="readonly">
                    </div>

                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">房东:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${firstDetail.landlordName}" readonly="readonly">
                    </div>

                </div>

                <div class="row m-b">
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">房东电话:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" value="${firstDetail.landlordMobile}"
                               readonly="readonly">
                    </div>
                    <div class="col-sm-1 text-right">
                        <label class="control-label" style="height: 35px; line-height: 35px;">首日租金:</label>
                    </div>
                    <div class="col-sm-2">
                        <input type="text" id="price" class="form-control" value="${firstDetail.price}"
                               readonly="readonly">
                    </div>
                    <div class="col-sm-1">
                        <button type="button" class="btn btn-info" id="setHouseCalendar" onclick="toHouseMap()">找相似房源
                        </button>
                    </div>
                </div>

                <div class="hr-line-dashed"></div>

            </form>


            <div class="wrapper wrapper-content">
                <div class="row">
                    <div class="col-sm-1">
                        <label class="control-label">聊天记录</label>
                    </div>
                    <div class="col-sm-7">
                        <div>
                            <div class="row">
                                <div class="col-md-12 ">
                                    <div class="chat-discussion" id="chat-discussion">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <form class="form-group">

                <c:forEach items="${followLog}" var="log">
                    <div class="row">
                        <div class="col-sm-1">
                            <label class="control-label">跟进记录:[${log.index}]</label>
                        </div>
                        <div class="col-sm-7">
                            <textarea rows="8" class="form-control m-b" readonly="readonly">${log.remark }</textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1">

                        </div>
                        <div class="col-sm-7">
                            跟进人:${log.empName}
                            ---跟进时间:<fmt:formatDate type="both" value="${log.createTime}"/>
                        </div>
                    </div>
                    <div class="row">
                        <hr/>
                    </div>

                </c:forEach>

            </form>


            <form class="form-group">
                <div class="row">
                    <div class="col-sm-1">
                        <input type="hidden" id="msgBaseFid" value="${firstDetail.msgBaseFid}">
                        <input type="hidden" id="cityCode" value="${firstDetail.cityCode}">
                    </div>
                </div>
            </form>
            <c:if test="${firstDetail.showButton == 1 }">
                <form class="form-group">
                    <div class="row">
                        <div class="col-sm-1">

                        </div>
                        <div class="col-sm-7">
                            还可以输入<span id="textCount">500</span>个字符<br/>
                            <textarea rows="8" id="content" class="form-control m-b" maxlength="512"
                                      onkeyup="words_deal();"></textarea>
                            <input type="hidden" id="msgAdvisoryFid" value="${firstDetail.msgAdvisoryFid}">
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" onclick="continueFollow()">继续跟进</button>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" onclick="finishFollow()">结束跟进</button>
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" onclick="listMsgAdvisoryFollow()">取消</button>
                        </div>
                    </div>
                </form>
            </c:if>

            <c:if test="${firstDetail.showButton != 1 }">
                <form class="form-group">
                    <div class="row">
                        <div class="col-sm-4"></div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" onclick="listMsgAdvisoryFollow()">取消</button>
                        </div>
                    </div>
                </form>
            </c:if>

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

    var msgBaseFid = $('#msgBaseFid').val();
    //查询聊天记录
    $.getJSON("message/listAdvisoryChatInfo", {msgBaseFid: msgBaseFid}, function (data) {
        if (data.code == 0) {
            var list = data.data.list;
            if (list && list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    var n = list[i];
                    var vDate = new Date(n.msgSendTime);
                    var dateStr = vDate.format("yyyy-MM-dd HH:mm:ss");
                    if (n.msgSenderType == 20) {
                        var _msg = '<div class="chat-message message-left">'
                            + '	<img class="message-avatar message-avatar-left" src="' + n.headPic + '" alt="">'
                            + '	<div class="message message-left">'
                            + '		<a class="message-author" href="javascript:;"> ' + n.nickName + '</a>'
                            + '		<span class="message-date message-date-left">' + dateStr + '</span>'
                            + '		<span class="message-content">' + n.content + '</span>'
                            + '	</div>'
                            + '</div>';
                        $("#chat-discussion").append(_msg);
                    }

                    if (n.msgSenderType == 10) {
                        var _msg = '<div class="chat-message message-right">'
                            + '	<img class="message-avatar message-avatar-right" src="' + n.headPic + '" alt="">'
                            + '	<div class="message message-right">'
                            + '		<a class="message-author" href="javascript:;"> ' + n.nickName + '</a>'
                            + '		<span class="message-date message-date-right">' + dateStr + '</span>'
                            + '		<span class="message-content">' + n.content + '</span>'
                            + '	</div>'
                            + '</div>';
                        $("#chat-discussion").append(_msg);
                    }
                }
            }
        } else {
            layer.alert(data.msg, {icon: 5, time: 2000, title: '提示'});
        }
    });

    function words_deal() {
        var curLength = $("#content").val().length;
        if (curLength > 500) {
            var num = $("#content").val().substr(0, 500);
            $("#content").val(num);
            alert("超过字数限制，多出的字将被截断！");
        }
        else {
            $("#textCount").text(500 - $("#content").val().length);
        }
    }

    function continueFollow() {
        var content = $("#content").val();
        var msgAdvisoryFid = $("#msgAdvisoryFid").val();
        var msgBaseFid = $("#msgBaseFid").val();
        if (content == null || content == '') {
            layer.alert("请填写跟进记录", {icon: 5, time: 3000, title: '提示'});
            return;
        }

        var callBack = function (result) {
            if (result.code == 0) {
                layer.alert("跟进成功", {icon: 6, time: 2000, title: '提示'});
                listMsgAdvisoryFollow();
              //  window.location.reload();
            } else {
                layer.alert(result.msg, {icon: 5, time: 3000, title: '提示'});
            }
        }
        CommonUtils.ajaxPostSubmit("message/saveMsgAdvisoryFollow", {
            'remark': content,
            'msgFirstAdvisoryFid': msgAdvisoryFid,
            'beforeStatus':'${followStatus}',
            'afterStatus': 20,
            msgBaseFid: msgBaseFid
        }, callBack);
    }


    function finishFollow() {
        var content = $("#content").val();
        var msgAdvisoryFid = $("#msgAdvisoryFid").val();
        var msgBaseFid = $("#msgBaseFid").val();
        if (content == null || content == '') {
            layer.alert("请填写跟进记录", {icon: 5, time: 3000, title: '提示'});
            return;
        }

        var callBack = function (result) {
            if (result.code == 0) {
                layer.alert("跟进成功", {icon: 6, time: 2000, title: '提示'});
//                window.location.reload();
              listMsgAdvisoryFollow();
            //    window.location.href = 'message/toMessageFollowDetail?msgBaseFid=' + msgBaseFid + '&followStatus=' + 30 + '&showButton=' + 0
            } else {
                layer.alert(result.msg, {icon: 5, time: 3000, title: '提示'});
            }
        }
        CommonUtils.ajaxPostSubmit("message/saveMsgAdvisoryFollow", {
            'remark': content,
            'msgFirstAdvisoryFid': msgAdvisoryFid,
            'beforeStatus':'${followStatus}',
            'afterStatus': 30,
            msgBaseFid: msgBaseFid
        }, callBack);
    }

    function listMsgAdvisoryFollow() {
        $.callBackParent("messageFollowup/listMsgAdvisoryFollow", true, callBack);
    }

    function callBack(parent) {
        parent.query();
    }

    function isNullOrBlank(obj) {
        return obj == undefined || obj == null || $.trim(obj).length == 0;
    }

    /*跳转到地图找房*/
    function toHouseMap() {
        var url = 'house/houseSearch/houseSearchList?a=1';
        var msgBaseFid = $('#msgBaseFid').val();
        var peopleNum = $('#personNum').val();
        var cityCode = $('#cityCode').val();
        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
        var price = $('#price').val();
        if (!isNullOrBlank(msgBaseFid)) {
            url += '&msgBaseFid=' + msgBaseFid
        }
        if (!isNullOrBlank(cityCode)) {
            url += '&cityCode=' + cityCode
        }
        if (!isNullOrBlank(peopleNum)) {
            url += '&personCount=' + peopleNum
        }
        if (!isNullOrBlank(startTime)) {
            url+='&startTime='+CommonUtils.formatCSTDay(startTime)
        }
        if (!isNullOrBlank(endTime)) {
            url+='&endTime='+CommonUtils.formatCSTDay(endTime)
        }
        if (!isNullOrBlank(price)) {
            url += '&priceStart=' + price + '&priceEnd=' + price
        }
        $.openNewTab(new Date().getTime(), url, "找相似房源");
    }
</script>
</body>
</html>
