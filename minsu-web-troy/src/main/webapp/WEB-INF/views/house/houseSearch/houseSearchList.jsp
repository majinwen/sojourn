<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/searchHouse.css${VERSION}" rel="stylesheet">
    <!-- 下拉框搜索插件-->
    <link href="${staticResourceUrl}/css/combo.select.css${VERSION}" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="position:relative; z-index:999;">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form class="form-group">
                    <div class="row m-b">
                        <input type="hidden" id="initCityCode" value="${ paramsInfo.cityCode }">
                        <input type="hidden" id="initHotReginScenic" value="${ paramsInfo.hotReginScenic}">
                        <input type="hidden" id="initHotReginBusiness" value="${ paramsInfo.hotReginBusiness}">
                        <input type="hidden" id="initAreaCode" value="${ paramsInfo.areaCode}">
                        <input type="hidden" id="initSubway" value="${ paramsInfo.subway}">
                        <input type="hidden" id="initLineFid" value="${ paramsInfo.lineFid}">
                        <input type="hidden" id="initHouseFid" value="${ houseFid}">
                        <input type="hidden" id="initOrderStatus" value="${ orderStatus}">
                        <input type="hidden" id="initRentWay" value="${ paramsInfo.rentWay}">

                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">关键词查询:</label>
                        </div>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="q" name="q"/>
                        </div>

                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">城市:</label>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control" name="cityCode" id="cityCode" onchange="initCode(this.value)">
                                <option value="">请选择</option>
                            </select>
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">商圈:</label>
                        </div>
                        <div class="col-sm-2">
                            <select class="form-control" id="locationType" onchange="createLocationSelect(this.value);">
                                <option value="">请选择</option>
                            </select>
                        </div>

                    </div>
                    <div class="row m-b">
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">首日房租:</label>
                        </div>
                        <div class="col-sm-1">
                            <c:set var="paramsInfo_priceStart" value="${paramsInfo.priceStart }"> </c:set>
                            <c:if test="${empty paramsInfo_priceStart }"><c:set var="paramsInfo_priceStart"
                                                                                value="0"> </c:set></c:if>
                            <c:set var="paramsInfo_priceStart" value="${paramsInfo_priceStart*0.9 }"> </c:set>
                            <c:if test="${ paramsInfo_priceStart <0}"><c:set var="paramsInfo_priceStart"
                                                                             value="0"> </c:set></c:if>
                            <input id="priceStart" name="priceStart" type="text"
                            <c:if test="${!empty paramsInfo.priceStart }">
                                   value="<fmt:formatNumber  value='${paramsInfo_priceStart}'  pattern="#0" />"
                            </c:if>
                                   class="form-control"
                                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                        </div>
                        <div class="col-sm-1 text-right" style="width: 20px;">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">--</label>
                        </div>
                        <div class="col-sm-1">
                            <c:set var="paramsInfo_priceEnd" value="${paramsInfo.priceEnd }"> </c:set>
                            <c:if test="${empty paramsInfo_priceEnd }"><c:set var="paramsInfo_priceEnd"
                                                                              value="0"> </c:set></c:if>
                            <c:set var="paramsInfo_priceEnd" value="${paramsInfo_priceEnd*1.1 }"> </c:set>
                            <input id="priceEnd" name="priceEnd" type="text"
                                   <c:if test="${!empty paramsInfo.priceEnd }">value="<fmt:formatNumber  value='${paramsInfo_priceEnd}'  pattern="#0" />"
                            </c:if>
                                   class="form-control"
                                   onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                   onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">出租方式:</label>
                        </div>
                        <div class="col-sm-1">
                            <select class="form-control" name="rentWay" id="rentWay">
                                <option value="">不限</option>
                                <option value="0">整租</option>
                                <option value="1">分租</option>
                            </select>
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">人数:</label>
                        </div>
                        <div class="col-sm-1">
                            <select class="form-control" name="personCount" id="personCount">
                                <option value="0">不限</option>
                                <c:forEach begin="1" end="10" var="p" step="1">
                                    <option value="${p }" <c:if
                                            test="${!empty paramsInfo.personCount and p==paramsInfo.personCount }"> selected="selected"</c:if> >${p }人
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">预订类型:</label>
                        </div>
                        <div class="col-sm-1">
                            <select class="form-control" name="orderType" id="orderType">
                                <option value="">不限</option>
                                <option value="1">立即预订</option>
                                <option value="2">申请预订</option>
                            </select>
                        </div>

                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">床铺类型:</label>
                        </div>
                        <div class="col-sm-1">
                            <select class="form-control" name="bedType" id="bedType">
                                <option value="" selected="selected">请选择</option>
                                <c:forEach items="${bedTypeList}" var="obj">
                                    <option value="${obj.key}">${obj.text}</option>
                                </c:forEach>
                            </select>
                        </div>

                    </div>
                    <div class="row m-b">
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">入住时间：</label>
                        </div>
                        <div class="col-sm-2">
                            <input id="startTime" name="startTime" class="laydate-icon form-control layer-date"
                                   value="${paramsInfo.startTime }">
                        </div>
                        <div class="col-sm-1 text-right">
                            <label class="control-label"
                                   style="height: 35px; line-height: 35px;">离开时间:</label>
                        </div>
                        <div class="col-sm-2">
                            <input id="endTime" name="endTime" class="laydate-icon form-control layer-date"
                                   value="${paramsInfo.endTime }">
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" id="search_btn" onclick="query();"><i
                                    class="fa fa-search"></i>&nbsp;查询
                            </button>
                        </div>
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
                               data-page-list="[All]" data-only-info-pagination="true" data-cache="false"
                               data-page-size="50" data-pagination-first-text="首页"
                               data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
                               data-pagination-last-text="末页" data-response-handler="responseHandler"
                               data-content-type="application/x-www-form-urlencoded"
                               data-query-params="paginationParam" data-method="post"
                               data-height="520" data-sort-class="sortable" data-silent-sort="true"
                               data-single-select="true" data-url="house/houseSearch/houseQuery">
                            <thead>
                            <tr>
                                <th data-field="landlordName" data-width="10%" data-align="center" data-formatter="landlordNameshow">房东姓名</th>
                                <th data-field="landlordMobile" data-width="10%" data-align="center">房东手机号</th>
                                <th data-field="nickName" data-width="10%" data-align="center">房东昵称</th>
                                <th data-field="acceptOrderRateIn60Days" data-width="10%" data-align="center">
                                    <div id="acceptOrderRateIn60DaysShow" >
                                                                                                                 接单率
                                    </div>
                                </th> 
                                <th data-field="isAuditPassIn30DaysStr" data-width="10%" data-align="center">
                                     <div id="isAuditPassIn30DaysStrShow" >
                                       30天内上线
                                    </div>
                                  </th>
                                <th data-field="houseName" data-width="25%" data-align="center" data-formatter="houseNameshow">房源名称</th>
                                <th data-field="hotRegins" data-width="10%" data-align="center"
                                    data-formatter="formateRegin">商圈
                                </th>
                                <th data-field="price" data-width="10%" data-align="center" data-sortable="true"
                                    data-sort-name="price">首日房租
                                </th>
                                <th data-field="rentWayName" data-width="10%" data-align="center" data-sortable="true"
                                    data-sort-name="rentWay">出租方式
                                </th>
                                <th data-field="orderTypeName" data-width="10%" data-align="center" data-sortable="true"
                                    data-sort-name="orderType">预订类型
                                </th>
                                <th data-field="personCount" data-width="10%" data-align="center" data-sortable="true"
                                    data-sort-name="personCount" data-formatter="formatePersonCount">可住人数
                                </th>
                                <th data-field="acceptOrder60DaysCount" data-width="10%" data-align="center"
                                    data-sortable="true" data-sort-name="acceptOrder60DaysCount">60天确认订单
                                </th>
                                <th data-field="order60DaysCount" data-width="10%" data-align="center"
                                    data-sortable="true" data-sort-name="order60DaysCount">60天订单总数
                                </th>
                                <th data-field="operate" data-width="25%" data-align="center"
                                    data-formatter="formateOperate">操作
                                </th>
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

<div class="wrapper wrapper-content animated fadeInRight">
    <div id="allmap"></div>
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
<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
<!-- 时间日期操作 -->
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/jquery.combo.select.js${VERSION}"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=TMbMngjLXTSEcP80tADrcqzR"></script>
<script>

    //分页查询参数
    function paginationParam(params) {


        var cityCode = $.trim($("#initCityCode").val());

        if (isNullOrBlank(cityCode)) {
            cityCode = $.trim($('#cityCode').val());
        }

        var param_obj = {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            q: $.trim($('#q').val()),
            cityCode: cityCode,
            personCount: $.trim($('#personCount').val()),
            orderType: $.trim($('#orderType').val()),
            bedType: $.trim($('#bedType').val()),
            priceStart: $.trim($('#priceStart').val()),
            priceEnd: $.trim($('#priceEnd').val()),
            rentWay: $.trim($('#rentWay').val()),
            startTime: $.trim($('#startTime').val()),
            endTime: $.trim($('#endTime').val())
        };

        if (!isNullOrBlank(params.order) && !isNullOrBlank(params.sort)) {
            var sortMap = {};
            if (params.sort == "price") {
                sortMap = {price: params.order};
            } else if (params.sort == "rentWayName") {
                sortMap = {rentWay: params.order};
            } else if (params.sort == "orderTypeName") {
                sortMap = {orderType: params.order};
            } else if (params.sort == "personCount") {
                sortMap = {personCount: params.order};
            } else if (params.sort == "acceptOrder60DaysCount") {
                sortMap = {acceptOrder60DaysCount: params.order};
            } else if (params.sort == "order60DaysCount") {
                sortMap = {order60DaysCount: params.order};
            }

            param_obj.sorts = sortMap;

        }

        try {
            if (!isNullOrBlank($("#s_local_commercialRegion_div").find("select").val())) {
                param_obj.hotReginBusiness = $.trim($("#s_local_commercialRegion_div").find("select").val());
            }
        } catch (e) {
            console.log(e);
        }
        try {
            if (!isNullOrBlank($("#s_local_sceneryRegion_div").find("select").val())) {
                param_obj.hotReginScenic = $.trim($("#s_local_sceneryRegion_div").find("select").val());
            }
        } catch (e) {
            console.log(e);
        }
        try {
            if (!isNullOrBlank($("#s_local_district_div").find("select").val())) {
                param_obj.areaCode = $.trim($("#s_local_district_div").find("select").val());
            }
        } catch (e) {
            console.log(e);
        }
        try {
            if (!isNullOrBlank($("#s_local_subway_div").find("select").val())) {
                param_obj.lineFid = $.trim($("#s_local_subway_div").find("select").val());
            }
        } catch (e) {
            console.log(e);
        }
        try {
            if (!isNullOrBlank($("#s_local_subwayline_div").find("select").val())) {
                param_obj.subway = $.trim($("#s_local_subwayline_div").find("select").val());
            }
        } catch (e) {
            console.log(e);
        }
        console.log(param_obj);
        return param_obj;
    }

    function responseHandler(result) {
        //$('#listTable').bootstrapTable('removeAll');
        $('#listTable').bootstrapTable('load', result);
        return result;
    }

    function isNullOrBlank(obj) {
        return obj == undefined || obj == null || $.trim(obj).length == 0;
    }
    //条件查询
    function query() {
        $('#listTable').bootstrapTable('selectPage', 1);
    }

    //人数
    function formatePersonCount(value, row, index) {
        if (value == '0') {
            return '不限';
        }

        return value;
    }
    //操作
    function formateOperate(value, row, index) {
        var htm = '<input onclick="copy(this);" value="复制房源地址" title="复制房源地址" style="border: 0 none; cursor: pointer; display: inline-block;height: 26px;margin-right: 22px;padding-left: 22px;" type="button">';
        htm += '<input type="hidden" style="height:26px;width:10px;" value="' + row.shareUrl + '"  />'
        return htm;
    }
    //商圈
    function formateRegin(value, row, index) {

        var hotRegin = row.hotRegin;
        if (!isNullOrBlank(hotRegin)) {
            hotRegin = hotRegin.join("、");
            if (hotRegin.length > 6) {
                return '<div title="' + hotRegin + '" >' + hotRegin.substring(0, 6) + '...</div>';
            }
        }
        return '';
    }


    //复制地址
    function copy(btnObj) {

        var location = btnObj.nextElementSibling;
        var content = location.value;
        if (content != '') {
            var aux = document.createElement("input");
            aux.setAttribute("value", content);
            document.body.appendChild(aux);
            aux.select();
            document.execCommand("copy");
            document.body.removeChild(aux);
            layer.alert("复制成功", {icon: 6, time: 2000, title: '提示'});
        }
    }

    //商圈景点
    function initCode(cityCode) {

        if (isNullOrBlank(cityCode)) {
            return;
        }

        $.ajax({
            type: "POST",
            url: "${search_location_list}",
            dataType: "json",
            async: false,
            data: {"cityCode": cityCode},
            success: function (result) {
                if (result.status == "0") {
                    var locationTypeHtm = '<option value="">请选择</option>';
                    $("#locationType").empty();

                    var initLocationType = "";
                    if (!isNullOrBlank($.trim($("#initHotReginScenic").val()))) {
                        initLocationType = "sceneryRegion";
                    } else if (!isNullOrBlank($.trim($("#initHotReginBusiness").val()))) {
                        initLocationType = "commercialRegion";
                    } else if (!isNullOrBlank($.trim($("#initAreaCode").val()))) {
                        initLocationType = "district";
                    } else if (!isNullOrBlank($.trim($("#initSubway").val())) || !isNullOrBlank($.trim($("#initLineFid").val()))) {
                        initLocationType = "subway";
                    }

                    try {
                        if (!isNullOrBlank(result.data.subway.name)) {
                            if (!isNullOrBlank(initLocationType) && initLocationType == "subway") {
                                locationTypeHtm += '<option value="subway" selected="selected" >' + result.data.subway.name + '</option>';
                            } else {
                                locationTypeHtm += '<option value="subway">' + result.data.subway.name + '</option>';
                            }
                        }
                    } catch (e) {
                        console.log(e);
                    }
                    try {
                        if (!isNullOrBlank(result.data.commercialRegion.name)) {
                            if (!isNullOrBlank(initLocationType) && initLocationType == "commercialRegion") {
                                locationTypeHtm += '<option value="commercialRegion" selected="selected"  >' + result.data.commercialRegion.name + '</option>';
                            } else {
                                locationTypeHtm += '<option value="commercialRegion">' + result.data.commercialRegion.name + '</option>';
                            }
                        }
                    } catch (e) {
                        console.log(e);
                    }
                    try {
                        if (!isNullOrBlank(result.data.district.name)) {
                            if (!isNullOrBlank(initLocationType) && initLocationType == "district") {
                                locationTypeHtm += '<option value="district" selected="selected" >' + result.data.district.name + '</option>';
                            } else {
                                locationTypeHtm += '<option value="district">' + result.data.district.name + '</option>';
                            }

                        }
                    } catch (e) {
                        console.log(e);
                    }
                    try {
                        if (!isNullOrBlank(result.data.sceneryRegion.name)) {
                            if (!isNullOrBlank(initLocationType) && initLocationType == "sceneryRegion") {
                                locationTypeHtm += '<option value="sceneryRegion" selected="selected">' + result.data.sceneryRegion.name + '</option>';
                            } else {
                                locationTypeHtm += '<option value="sceneryRegion">' + result.data.sceneryRegion.name + '</option>';
                            }

                        }
                    } catch (e) {
                        console.log(e);
                    }
                    $("#locationType").append(locationTypeHtm);

                    window.search_location_list_Obj = result.data;

                    createLocationSelect(initLocationType);
                }
            },
            error: function (result) {
                layer.alert("初始化数据失败，请重新刷新页面再试！", {icon: 5, time: 2000, title: '提示'});
            }
        });

    }

    function createLocationSelect(locationType) {

        if (isNullOrBlank(locationType)) {
            return;
        }

        $("#locationType").parent().nextAll().remove();

        var htm = '';

        try {

            if (locationType == "commercialRegion") {

                var inval = $.trim($("#initHotReginBusiness").val());
                var id = "s_local_" + locationType + "_div";
                htm += '<div class="col-sm-2" id=' + id + ' >'
                    + '<select class="form-control" >'
                    + '<option value="">请选择</option>';
                var list = window.search_location_list_Obj.commercialRegion.list;
                if (!isNullOrBlank(list)) {
                    for (var i in list) {
                        if (!isNullOrBlank(inval) && $.trim(list[i]) == inval) {
                            htm += '<option value="' + $.trim(list[i]) + '" selected="selected" >' + list[i] + '</option>';
                        } else {
                            htm += '<option value="' + $.trim(list[i]) + '">' + list[i] + '</option>';
                        }
                    }
                }
                htm += '</select>'
                    + '</div>';
                $("#locationType").parent().after(htm);
            } else if (locationType == "district") {
                var inval = $.trim($("#initAreaCode").val());
                var id = "s_local_" + locationType + "_div";
                htm += '<div class="col-sm-2" id=' + id + ' >'
                    + '<select class="form-control" >'
                    + '<option value="">请选择</option>';
                var list = window.search_location_list_Obj.district.districts;
                if (!isNullOrBlank(list)) {
                    for (var i in list) {
                        if (!isNullOrBlank(inval) && $.trim(list[i].code) == inval) {
                            htm += '<option value="' + $.trim(list[i].code) + '" selected="selected" >' + list[i].name + '</option>';
                        } else {
                            htm += '<option value="' + $.trim(list[i].code) + '">' + list[i].name + '</option>';
                        }
                    }
                }
                htm += '</select>'
                    + '</div>';
                $("#locationType").parent().after(htm);
            } else if (locationType == "sceneryRegion") {
                var inval = $.trim($("#initHotReginScenic").val());

                var id = "s_local_" + locationType + "_div";
                htm += '<div class="col-sm-2" id=' + id + ' >'
                    + '<select class="form-control" >'
                    + '<option value="">请选择</option>';
                var list = window.search_location_list_Obj.sceneryRegion.list;
                if (!isNullOrBlank(list)) {
                    for (var i in list) {
                        if (!isNullOrBlank(inval) && $.trim(list[i]) == inval) {
                            htm += '<option value="' + $.trim(list[i]) + '" selected="selected" >' + list[i] + '</option>';
                        } else {
                            htm += '<option value="' + $.trim(list[i]) + '">' + list[i] + '</option>';
                        }

                    }
                }
                htm += '</select>'
                    + '</div>';
                $("#locationType").parent().after(htm);
            } else if (locationType == "subway") {
                var inval = $.trim($("#initLineFid").val());
                var invaled = false;
                var id = "s_local_" + locationType + "_div";
                htm += '<div class="col-sm-2" id=' + id + ' >'
                    + '<select class="form-control"  onchange="createSubwaySelect(this.value);" >'
                    + '<option value="">请选择</option>';
                var list = window.search_location_list_Obj.subway.list;
                if (!isNullOrBlank(list)) {
                    for (var i in list) {
                        if (!isNullOrBlank(inval) && $.trim(list[i].lineFid) == inval) {
                            htm += '<option value="' + $.trim(list[i].lineFid) + '" selected="selected" >' + list[i].name + '</option>';
                            invaled = true;
                        } else {
                            htm += '<option value="' + $.trim(list[i].lineFid) + '">' + list[i].name + '</option>';
                        }

                    }
                }
                htm += '</select>'
                    + '</div>';
                $("#locationType").parent().after(htm);

                if (invaled) {
                    createSubwaySelect(inval);
                }
            }
        } catch (e) {
            console.log(e);
        }

    }


    function createSubwaySelect(lineFid) {

        if (isNullOrBlank(lineFid)) {
            return;
        }
        var id = "s_local_subwayline_div";
        $("#" + id).remove();

        var inval = $.trim($("#initSubway").val());

        var htm = '<div class="col-sm-2" id=' + id + ' >'
            + '<select class="form-control" >'
            + '<option value="">请选择</option>';
        var list = window.search_location_list_Obj.subway.list;
        if (!isNullOrBlank(list)) {
            for (var i in list) {

                if (list[i].lineFid == lineFid) {
                    var stations = list[i].stations;
                    for (var j in stations) {
                        if (stations[j].name != "不限") {
                            if (!isNullOrBlank(inval) && $.trim(stations[j].name) == inval) {
                                htm += '<option value="' + $.trim(stations[j].name) + '" selected="selected" >' + stations[j].name + '</option>';
                            } else {
                                htm += '<option value="' + $.trim(stations[j].name) + '">' + stations[j].name + '</option>';
                            }
                        }
                    }
                }

            }
        }
        htm += '</select>'
            + '</div>';

        $("#s_local_subway_div").after(htm);
    }

    function initCity() {

        $.ajax({
            type: "POST",
            url: "${search_city_list}",
            dataType: "json",
            async: false,
            data: {},
            success: function (result) {
                if (result.status == "0") {

                    var htm = '';
                    var list = result.data.list;
                    var initCityCode = $.trim($("#initCityCode").val());
                    var cityCodeInited = false;
                    $(list).each(function (index, element) {
                        $(element.cityList).each(function (i, ct) {

                            if (!isNullOrBlank(initCityCode) && initCityCode == $.trim(ct.code)) {
                                htm += '<option value="' + $.trim(ct.code) + '" selected="selected" >' + ct.name + '</option>';
                                cityCodeInited = true;
                            } else {
                                htm += '<option value="' + $.trim(ct.code) + '">' + ct.name + '</option>';
                            }

                        });
                    });
                    $("#cityCode").append(htm).trigger("change");
                    $("#initCityCode").val("");
                    //$("#listTable").bootstrapTable('refresh', {});
                }
            },
            error: function (result) {
                layer.alert("初始化数据失败，请重新刷新页面再试！", {icon: 5, time: 2000, title: '提示'});
            }
        });

    }

    var start = {
        elem: '#startTime', //选择ID为START的input
        format: 'YYYY-MM-DD', //自动生成的时间格式
        max: laydate.now(120, "YYYY-MM-DD"), //最大日期
        min: laydate.now(0, "YYYY-MM-DD"),
        istime: true, //必须填入时间
        istoday: false,  //是否是当天
        start: laydate.now(0, "YYYY-MM-DD"),  //设置开始时间为当前时间
        choose: function (datas) {
            end.min = datas; //开始日选好后，重置结束日的最小日期
            end.start = datas //将结束日的初始值设定为开始日
        }
    };
    var end = {
        elem: '#endTime',
        format: 'YYYY-MM-DD',
        max: laydate.now(120, "YYYY-MM-DD"),
        min: laydate.now(0, "YYYY-MM-DD"),
        istime: true,
        istoday: false,
        start: laydate.now(0, "YYYY-MM-DD"),
        choose: function (datas) {
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };

    $(function () {
        initCity();
        laydate(start);
        laydate(end);
    });


    var curID = '';

    function setStar() {
        $('.star').each(function () {
            var val = $(this).attr('data-val') * 10 + '';
            var v1 = val.substr(0, 1);
            var v2 = val.substr(1, 2);
            var str = '';
            for (var i = 0; i < v1; i++) {
                $(this).find('i').eq(i).addClass('active');
            }
            if (v2 > 0) {
                $(this).find('i').eq(v1).addClass('half');
            }
        });
    };


    function ComplexCustomOverlay(point, text, colorType, fid, s) {
        this._point = point;
        this._text = text;
        this._colorType = colorType;
        this._fid = fid;
        this._s = s;
    }

    var divZindex = 9;
    $('#listTable').bootstrapTable({
        onLoadSuccess: function (data) {
            var mp = new BMap.Map("allmap", {enableMapClick: false});
            var rows = $('#listTable').bootstrapTable('getData', true);
            if (rows && rows.length > 0) {
                //初始化百度地图
                var _row = rows[0];
                var _loc = _row.loc;
                var _arr = _loc.split(',');
                mp.centerAndZoom(new BMap.Point(_arr[1], _arr[0]), 15);
                mp.enableScrollWheelZoom();
                ComplexCustomOverlay.prototype = new BMap.Overlay();


                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var loc = row.loc;
                    var arr = loc.split(',');
                    // 复杂的自定义覆盖物


                    ComplexCustomOverlay.prototype.initialize = function (map) {
                        this._map = map;
                        var that = this;

                        var fid = 'houseDetail_' + this._fid;
                        var div = this._div = document.createElement("div");
                        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
                        div.setAttribute('class', 'txtStyle ' + this._colorType);
                        div.setAttribute('id', 'txtStyle_' + this._fid);

                        var span = this._span = document.createElement("span");
                        div.appendChild(span);
                        if (this._s) {
                            span.setAttribute('class', 'san');
                        }

                        span.appendChild(document.createTextNode(this._text));
                        var that = this;

                        var arrow = this._arrow = document.createElement("b");
                        arrow.setAttribute('class', 'arrow');
                        arrow.style.left = "50%";
                        div.appendChild(arrow);

                        var aaa = row['bedList'][0];
                        var houseDetail = document.createElement("div");
                        var personCount;
                        if (row['personCount'] > 0) {
                            personCount = row['personCount'] + "位房客";
                        } else {
                            personCount = "不限入住人数";
                        }
                        var sDetail = "<div class='img'>"
                            + "<img src=" + row['picUrl'] + " onclick='formateImg( \"" + row['fid'] + "\",\"" + row['rentWay'] + "\")'>"
                            + "</div>"
                            + "<div class='txt'>"
                            + "<div class='p1 clearfix'>"
                            + "<p class='price col-xs-4'>￥" + row['price'] + "</p>"
                            + "<div class='col-xs-8'>"
                            + "<span class='star' data-val='" + row['realEvaluateScore'] + "'><i></i><i></i><i></i><i></i><i></i></span>"
                            + "<span class='pingjia'>" + row['evaluateCount'] + "条评价</span>"
                            + "</div>"
                            + "</div>"
                            + "<div class='p2'>" + row['houseName'] + "</div>"
                            + "<div class='p3'>" + row['rentWayName'];
                        if (!isNullOrBlank(row['houseTypeName'])) {
                            sDetail += "／" + row['houseTypeName'];
                        }

                        sDetail += " · " + row['bedList'].length + "张床 · " + personCount + "<a href='javascript:;' onclick='closeHouse(this)' class='close'  style='font-size:12px; color:#f60; opacity:1;'>[关闭]</a></div>"
                            + "</div><b class='arr'></b>";

                        houseDetail.innerHTML = sDetail;
                        houseDetail.setAttribute('class', 'houseDetail');
                        houseDetail.setAttribute('id', fid);

                        div.appendChild(houseDetail);
                        houseDetail.onclick = function (e) {
                            e.stopPropagation();
                        }
                        div.onclick = function (e) {
                            curID = that._fid;
                            console.log('curID=' + curID);
                            var curClass = this.getAttribute('class').replace('gray', '');
                            if (curClass.indexOf('red') >= 0) {
                                return;
                            }
                            this.style.zIndex = divZindex++;
                            $('.txtStyle').each(function () {
                                if ($(this).hasClass('red')) {
                                    $(this).removeClass('red').addClass('gray');
                                }
                            });
                            if (curClass.indexOf('red') < 0) {
                                this.setAttribute('class', curClass + ' red');
                            }
                            $('.houseDetail').hide();
                            $('#' + fid).show();

                            //mp.centerAndZoom(new BMap.Point(that._point.lng,that._point.lat), mp.getZoom());
                            setStar();
                            // e.stopPropagation();
                        }

                        mp.getPanes().labelPane.appendChild(div);
                        return div;
                    }

                    ComplexCustomOverlay.prototype.draw = function () {
                        var map = this._map;
                        var pixel = map.pointToOverlayPixel(this._point);
                        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
                        this._div.style.top = pixel.y - 30 + "px";
                    }

                    var initHouseFid = $('#initHouseFid').val();
                    var initRentWay = $('#initRentWay').val();
                    var initOrderStatus = $('#initOrderStatus').val();
                    var houseFid = row['fid'];
                    var rentWay = row['rentWay'];
                    var color = 'org';
                    if (rentWay == initRentWay && houseFid == initHouseFid) {
                        color = 'blue';
                    }
                    var orderType = row['orderType'];
                    if (orderType == 2) {
                        orderType = 0;
                    }
                    var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(arr[1], arr[0]), '￥' + row['price'], color, i, orderType);

                    mp.addOverlay(myCompOverlay);

//                        mp.addEventListener('click', function () {
//                            $('.houseDetail').hide();
//                            if (!$('#txtStyle_' + curID).hasClass('gray')) {
//                                $('#txtStyle_' + curID).removeClass('red').addClass('gray');
//                            }
//                        });
                }
            }
        },

        onLoadError: function (data) {

        }
    });

    function closeHouse(obj) {
        $('.houseDetail').hide();

        if (!$('#txtStyle_' + curID).hasClass('gray')) {

            $('#txtStyle_' + curID).removeClass('red').addClass('gray');

        }

        console.log('#txtStyle_' + curID);
        //e.stopPropagation();

    }

    //跳转到房源详情
    function formateImg(fid, rentWay) {
    	
    	 //跳转到pc端房源详情页
	     var jumpUrl = "${search_ziroomstay_url}"+rentWay+"/"+fid;
		 window.open(jumpUrl);
		 
       /*  CommonUtils.ajaxPostSubmit("house/houseSearch/getHouseDetailAuth", {"url": "house/houseMgt/houseDetail"}, function (data) {
            if (data == 1) {
                if (rentWay == '0') {
                    $.openNewTab(new Date().getTime(), 'house/houseMgt/houseDetail?rentWay=0&houseFid=' + fid, "房源详情");
                } else {
                    $.openNewTab(new Date().getTime(), 'house/houseMgt/houseDetail?rentWay=1&houseFid=' + fid, "房间详情");
                }
            } else {
                layer.alert("没有权限", {icon: 6, time: 2000, title: '提示'});
            }
        }); */
    }

    function showHouseDetail(houseBaseFid) {
        $.openNewTab(new Date().getTime(), 'house/houseMgt/houseDetail?rentWay=0&houseFid=' + houseBaseFid, "房源详情");
    }

    //展示房间详情
    function showRoomDetail(houseRoomFid) {
        $.openNewTab(new Date().getTime(), 'house/houseMgt/houseDetail?rentWay=1&houseFid=' + houseRoomFid, "房间详情");
    }

    function landlordNameshow(value, row, index){
        return "<div style='overflow: hidden; white-space: nowrap; text-overflow: clip;width:6em;'>"+value+"</div>";
    }

    function houseNameshow(value, row, index){
        return "<div style='overflow: hidden; white-space: nowrap; text-overflow: clip;width:20em;'>"+value+"</div>";
    }

    $("#listTable").delegate("td","mouseover",function(){$(this).attr("title",$(this).text())});
    
    $("#acceptOrderRateIn60DaysShow").click(function(){
    	 layer.alert("60天内创建的订单中，该房东所有申请预定的订单通过数/该房东所有申请预定的订单数*100%", {icon: 6, time: 5000, title: '提示'});
    }); 
    $("#isAuditPassIn30DaysStrShow").click(function(){
    	layer.alert("根据该房东审核通过的时间计算，如不足30天显示‘是’，超过30天显示‘否’", {icon: 6, time: 5000, title: '提示'});
    });
</script>
</body>
</html>
