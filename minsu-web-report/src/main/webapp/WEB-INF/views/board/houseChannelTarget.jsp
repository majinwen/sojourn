<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="${basePath }">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <style type="text/css">
        .mbottom{
            margin-bottom: 5px;
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
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">国家：</label>
                            </div>
                            <div class="col-sm-2 ">
                                <select class="form-control m-b cc-region" id="nation" name="nation">
                                    <option value="">请选择</option>
                                    <c:forEach items="${nationList}" var="obj">
                                        <option value="${obj.code }">${obj.showName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">目标月份：</label>
                            </div>
                            <div class="col-sm-2">
                                <input name="targetMonth" id="targetMonth" class="laydate-icon form-control layer-date">
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="queryList();">
                                    <i class="fa fa-search"></i>&nbsp;查询
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
                            <div style="width: 100%;">
                                <table id="jqGrid" style="width: 100%;"></table>
                                <div id="jqGridPager"></div>
                            </div>
                        </div>
                    </div>
                </div>
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
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<!--jqgrid-->
<script src="${staticResourceUrl}/js/plugins/jqgrid/jquery.jqGrid.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/i18n/grid.locale-cn.js${VERSION}001"></script>

<!--treeview-->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<script type="text/javascript">
    //初始化列表数据
    $(document).ready(function () {
    	CommonUtils.datePickerMonth('targetMonth');
        $("#jqGrid").jqGrid({
            url: 'houseChannel/showData',
            mtype: "post",
            datatype: "json",
            colModel: [
                 {label: '大区',align:"center", name: 'regionName', width: 30,sortable :false},
                 {label: '城市',align:"center", name: 'cityName', width: 30,sortable :false},
                 {label: '地推目标',align:"center", name: 'toPushTarget',width: 30,sortable :false},
                 {label: '地推发布',align:"center", name: 'toPushNum',width: 30,sortable :false},
                 {label: '上架数量',align:"center", name: 'toPushUpNum',width: 30,sortable :false},
                 {label: '地推达成率',align:"center", name: 'toPushRateS',width: 30,sortable :false},
                 {label: '地推占比',align:"center", name: 'toPushDutyS',width: 30,sortable :false},
                 {label: '环比上月',align:"center", name: 'toPushQoQS',width: 30,sortable :false},
                 {label: '自主目标',align:"center", name: 'autoTarget', width: 30,sortable :false},
                 {label: '自主发布',align:"center", name: 'autoNum',width: 30,sortable :false},
                 {label: '上架数量',align:"center", name: 'autoUpNum',width: 30,sortable :false},
                 {label: '自主达成率',align:"center", name: 'autoRateS',width: 30,sortable :false},
                 {label: '自主占比',align:"center", name: 'autoDutyS',width: 30,sortable :false},
                 {label: '环比上月', align:"center",name: 'autoQoQS',width: 30,sortable :false},
            ],
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            height: 600,
            rowNum: 1000,
            viewrecords: true,
            grouping: true,
            groupingView: {
            	groupField: ["regionName"],
                groupColumnShow: [true],
                groupText: ["<strong>{0}<strong>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            },
            pager: "#jqGridPager"
        });
    });
   
   function queryList() {
       $("#jqGrid").jqGrid('setGridParam',{
           url:"houseChannel/showData",
           postData:{
               limit : $('#selectLimit option:selected').val(),
               targetMonth : $.trim($('#targetMonth').val()),
               nationCode : $.trim($('#nation').val())
           }, //发送数据
           page:1
       }).trigger("reloadGrid"); //重新载入
   }

   //导出数据
   function exportFile(){
       alert("导出数据按钮被单击");
        //empTarget/dataListToExcel?methodName=findGaurdAreaForExcel
       window.location.href="empTarget/dataListToExcel?methodName=findGaurdAreaForExcel";
   }
</script>

<script>
    $.jgrid.defaults.width = 1600;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>
</body>
</html>
