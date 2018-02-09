<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String weightEnumMap = (String) request.getAttribute("weightEnumMap");
    String totalScoreFuc = (String) request.getAttribute("totalScoreFuc");
%>
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
    <link href="${staticResourceUrl}/css/star-rating.css${VERSION}001" rel="stylesheet" type="text/css"/>

    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <style type="text/css">
        .left2 {
            margin-top: 10px;
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
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="row m-b">
                                <label class="col-sm-1 control-label mtop">搜索关键词:</label>
                                <div class="col-sm-2">
                                    <input id="q" name="q" class="form-control m-b">
                                </div>
                                <label class="col-sm-1 control-label mtop">城市code:</label>
                                <div class="col-sm-2">
                                    <input id="cityCode" name="cityCode" class="form-control m-b">
                                </div>
                                <label class="col-sm-1 control-label mtop">景点:</label>
                                <div class="col-sm-2">
                                    <input id="hotReginScenic" name="hotReginScenic" class="form-control m-b">
                                </div>
                                <label class="col-sm-1 control-label mtop">商圈:</label>
                                <div class="col-sm-2">
                                    <input id="hotReginBusiness" name="hotReginBusiness" class="form-control m-b">
                                </div>
                            </div>

                            <div class="row m-b">
                                <label class="col-sm-1 control-label mtop">人数:</label>
                                <div class="col-sm-2">
                                    <input id="personCount" name="personCount" class="form-control m-b">
                                </div>

                                <label class="col-sm-1 control-label mtop">房间数:</label>
                                <div class="col-sm-2">
                                    <input id="roomCount" name="roomCount" class="form-control m-b">
                                </div>

                                <label class="col-sm-1 control-label mtop">房屋类型:</label>
                                <div class="col-sm-2">
                                    <input id="houseType" name="houseType" class="form-control m-b">
                                </div>

                                <label class="col-sm-1 control-label mtop">出租方式:</label>
                                <div class="col-sm-2">
                                    <input id="rentWay" name="rentWay" class="form-control m-b">
                                </div>
                            </div>

                            <div class="row m-b">
                                <label class="col-sm-1 control-label mtop">价格:</label>
                                <div class="col-sm-2">
                                    <input id="priceStart" name="priceStart" class="form-control m-b">
                                </div>
                                <label class="col-sm-1 control-label mtop">到</label>
                                <div class="col-sm-2">
                                    <input id="priceEnd" name="priceEnd" class="form-control m-b">
                                </div>

                                <label class="col-sm-1 control-label mtop">入住时间：</label>
                                <div class="col-sm-2">
                                    <input id="startTime" name="startTime" class="laydate-icon form-control layer-date">
                                </div>
                                <label class="col-sm-1 control-label mtop">到</label>
                                <div class="col-sm-2">
                                    <input id="endTime" name="endTime" class="laydate-icon form-control layer-date">
                                </div>
                            </div>

                            <div class="row m-b">
                                <label class="col-sm-1 control-label mtop">预订类型:</label>
                                <div class="col-sm-2">
                                    <input id="orderType" name="orderType" class="form-control m-b">
                                </div>

                                <label class="col-sm-1 control-label mtop">经纬度：</label>
                                <div class="col-sm-2">
                                    <input id="loc" name="loc" class="form-control m-b"
                                           placeholder="39.975842,116.495458">
                                </div>

                                <label class="col-sm-1 control-label mtop">排序：</label>
                                <div class="col-sm-2">
                                    <select class="form-control" id="sortType">
                                        <c:forEach items="${sortList}" var="sort">
                                            <option value="${sort.code}" <c:if test="${sort.code == 0}">selected="selected"</c:if>>${sort.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="row m-b">
                                <button class="btn btn-primary" type="button" onclick="query()"><i
                                        class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                        </div>
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
                            <table id="listTable" class="table table-bordered table-hover">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 房源得分 菜单 弹出框 -->
<div class="modal fade text-center" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document" style="display:inline-block;width:auto;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">默认排序得分</h4>
            </div>
            <div class="modal-body">
                <table id="scoreTable" class="table table-bordered">
                </table>
            </div>
            <div class="modal-footer" style="text-align: center;">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>


<script type="text/javascript">

    // 权重明细翻译，格式：{"权重枚举名":"权重说明-权重系数"}
    var weightEnumMap = <%=weightEnumMap%>;

    // 默认排序得分规则
    var totalScoreFuc = "<%=totalScoreFuc%>";

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
        laydate({
                elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        strapTable('listTable', 'search/query/select', paginationParam, columnsData);
    });

    function paginationParam(params) {
        return {
            limit: params.limit,
            page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            q: $('#q').val(),
            cityCode: $('#cityCode').val(),
            hotReginScenic: $('#hotReginScenic').val(),
            hotReginBusiness: $('#hotReginBusiness').val(),
            houseType: $('#houseType').val(),
            rentWay: $('#rentWay').val(),
            orderType: $('#orderType').val(),
            priceStart: $('#priceStart').val(),
            priceEnd: $('#priceEnd').val(),
            personCount: $('#personCount').val(),
            roomCount: $('#roomCount').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val(),
            loc: $('#loc').val(),
            sortType: $('#sortType').val()
        };
    }

    //初始化列表列
    var columnsData = [
        {
            field: 'houseName',
            title: '房源名称',
            align: 'center',
            formatter: 'formatHouseDetail'
        }, {
            field: 'cityName',
            title: '城市',
            align: 'center'
        }, {
            field: 'dist',
            title: '距离',
            align: 'center'
        }, {
            field: 'nickName',
            title: '房东昵称',
            align: 'center'
        }, {
            field: 'rentWayName',
            title: '出租方式',
            align: 'center'
        }, {
            field: 'orderTypeName',
            title: '预订类型',
            align: 'center'
        }, {
            field: 'picUrl',
            title: '图片',
            align: 'center',
            formatter: 'formatUrl'
        }, {
            field: 'personCount',
            title: '人数',
            align: 'center'
        }, {
            field: 'evaluateScore',
            title: '评分',
            align: 'center'
        }, {
            field: 'evaluateCount',
            title: '评价数',
            align: 'center'
        }, {
            field: 'price',
            title: '价格',
            align: 'center',
            formatter: 'CommonUtils.getMoney'
        }, {
            title: '今夜特价',
            align: 'center',
            formatter: 'formatTonightDiscount'
        }, {
            field: 'updateTime',
            title: '更新时间',
            align: 'center',
            formatter: 'CommonUtils.formateTimeStr'
        }, {
            field: 'score',
            title: 'Solr得分',
            align: 'center'
        }, {
            field: 'weights',
            title: '权重得分',
            align: 'center'
        }, {
            field: 'totalScore',
            title: '默认排序得分',
            align: 'center',
            formatter: 'formatScore'
        }];

    // 查询
    function query() {

        var sortType = $('#sortType').val();

        // 按照默认排序的话就隐藏综合得分
        if (sortType != 0) {
            $('#listTable').bootstrapTable('hideColumn', 'totalScore');
        } else {
            $('#listTable').bootstrapTable('showColumn', 'totalScore');
        }

        $('#listTable').bootstrapTable('selectPage', 1);
    }

    // 房源详情
    function formatHouseDetail(value, row, index) {
        return "<a href='javascript:showHouseDetail(\"" + row.fid + "\",\"" + row.rentWay + "\");'>" + value + "</a>";
    }

    // 展示房源详情
    function showHouseDetail(fid, rentWay) {
        $.openNewTab(new Date().getTime(), 'house/houseMgt/houseDetail?rentWay=' + rentWay + '&houseFid=' + fid, "房源详情");
    }

    // 今夜特价
    function formatTonightDiscount(value, row, index) {
        if (row.isToNightDiscount == 1 && typeof(row.tonightDiscountInfoVo) != "undefined") {
            return row.tonightDiscountInfoVo.tonightDiscount;
        } else {
            return "-";
        }
    }

    // 图片链接
    function formatUrl(value, row, index) {
        return "<a href=\"" + value + "\" target=\"_blank\">图片</a>";
    }

    // 得分明细
    function formatScore(value, row, index) {
        var totalScore = row.score * 100 * 0.4 + row.weights * 0.6;
        totalScore = totalScore.toFixed(2);
        return "<a href='javascript:showScoreDetail(\"" + row.score + "\",\"" + row.weights + "\",\"" + row.weightsComposition + "\",\"" + totalScore + "\");'>" + totalScore + "</a>";
    }

    // 得分明细
    var scoreColumns = [
        {
            field: 'scoreItemName',
            title: '名称',
            align: 'center'
        }, {
            field: 'weightRatio',
            title: '系数 × 原始分',
            align: 'center'
        }, {
            field: 'score',
            title: '得分',
            align: 'center'
        }];

    function showScoreDetail(solrScore, weights, weightsComposition, totalScore) {

        var scoreData = new Array();

        if(typeof(weightsComposition) != "undefined" && weightsComposition != ""){
            var weightArr = weightsComposition.split("&");
            for (var i in weightArr) {
                var weightStr = weightArr[i];
                var weightItem = weightStr.split("=");

                // 权重枚举类的名称与系数
                var names = weightEnumMap[weightItem[0]].split("-");
                var weightName = names[0];
                var ratio = names[1];

                // 权重得分
                var weight = weightItem[1];
                // 原始分
                var originWeight = weight / ratio;

                scoreData.push(
                    {
                        scoreItem: "weight",
                        scoreItemName: weightName,
                        weightRatio: ratio + " × " + originWeight,
                        score: weight
                    }
                );
            }
        }

        scoreData.push(
            {
                scoreItem: "weights",
                scoreItemName: "权重得分",
                score: weights
            }, {
                scoreItem: "solr",
                scoreItemName: "Solr得分",
                score: solrScore
            }, {
                scoreItem: "totalScore",
                scoreItemName: "综合得分",
                weightRatio: totalScoreFuc,
                score: totalScore
            }
        );

        $('#scoreTable').bootstrapTable('destroy');
        $('#scoreTable').bootstrapTable({
            data: scoreData,
            columns: scoreColumns,
            rowStyle: 'scoreTableRowStyle'
        });

        $('#myModal').modal('show');
    }

    function scoreTableRowStyle(row, index) {
        if (row.scoreItem == "totalScore") {
            return {classes: 'success'};
        } else if (row.scoreItem == "solr" || row.scoreItem == "weights") {
            return {classes: 'info'};
        } else if (row.scoreItem == "weight") {
            return {classes: 'warning'};
        } else {
            return {classes: 'danger'};
        }
    }

</script>

</body>

</html>
