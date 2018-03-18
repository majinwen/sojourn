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
                                <label class="control-label">日期：</label>
                            </div>
                            <div class="col-sm-2">
                                <input name="queryDate" id="queryDate" class="laydate-icon form-control layer-date">
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="query();">
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

                        <div class="col-sm-6">
                            <div class="col-sm-1 left2 left3">
                                <customtag:authtag authUrl="order/orderEvaluateExcelList">
                                    <button class="btn btn-primary" type="button" onclick="exportFile();">
                                        <i class="fa fa-search"></i>&nbsp;导出文件
                                    </button>
                                </customtag:authtag>
                            </div>

                            <h3 class="example-title" id="title" align="center"></h3>
                        </div>

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

<script>
    var now = new Date();
	laydate({
		elem: '#queryDate',
		min: '2017-02-16',
		max: now.getFullYear() + "-" + (now.getMonth() > 8 ? (now.getMonth() + 1) : '0' + (now.getMonth() + 1)) + "-" + now.getDate(),
		event: 'focus'
	});
</script>
<script type="text/javascript">
    //初始化列表数据
    $(function () {
    	init();
    });
    
    function init(){
    	initTitle();
    	initConfig();
        $("#jqGrid").jqGrid({
            url: 'landlordData/dataList',
            mtype: "GET",
            datatype: "json",
            colModel: [
                {label: '国家/大区', name: 'regionName', key: true, width: 150},
                {label: '城市', name: 'cityName', width: 150},
                {label: '房源量', name: 'totalHouseNum', width: 150},
                {label: '房东量', name: 'totalLandNum', width: 150},
                {label: '开通立即预定房源量', name: 'immediateBookTypeNum', width: 150},
                {label: '人均维护房源量', name: 'perHouseNum', width: 150, formatter: formatFloat},
                {label: '体验型房东量', name: 'expLandNum', width: 150},
                {label: '非专业型房东量', name: 'nonProLandNum', width: 150},
                {label: '专业型房东量', name: 'proLandNum', width: 150},
                {label: '体验型房东占比', name: 'expRate', width: 150, formatter: formatPercent},
                {label: '非专业型房东占比', name: 'nonProRate', width: 150, formatter: formatPercent},
                {label: '专业型房东占比', name: 'proRate', width: 150, formatter: formatPercent}
            ],
            //loadonce: true, // just for demo purpose
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            postData: {
            	limit: 1
            },
            height: 400,
            rowNum: 1000,
            pager: "#jqGridPager",
            viewrecords: true,
            grouping: true,
            groupingView: {
                groupField: ["regionName"],
                groupColumnShow: [true],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            }
        });
    }
    
    function initTitle(){
        var queryDate = $('#queryDate').val();
    	if(isNullOrBlank(queryDate)){
    		now = new Date();
    		queryDate = now.getFullYear() + "-" 
    			+ (now.getMonth() > 8 ? (now.getMonth() + 1) : '0' + (now.getMonth() + 1)) + "-" 
    			+ now.getDate();
    	}
   		$('#title').html(queryDate+'房东数据汇总表'+'(房东量=体验型房东+非专业型房东+专业型房东)');
    }
    
    function initConfig(){
	    $.jgrid.defaults.width = 1600;
	    $.jgrid.defaults.responsive = true;
	    $.jgrid.defaults.styleUI = 'Bootstrap';
    }
    
    function formatFloat(value, index, row){
    	var result = parseFloat(value).toFixed(2);
    	return result;
    }
    
    function formatPercent(value, index, row){
    	var result = parseFloat(value*100).toFixed(2) + "%";
    	return result;
    }
    
    function isNullOrBlank(obj){
		return typeof(obj) == 'undefined' || obj == null || $.trim(obj).length == 0;
	}
    
    function query(){
    	initTitle();
    	$("#jqGrid").jqGrid('setGridParam',{
            url:"landlordData/dataList",
            postData:{
            	nationCode: $('#nation option:selected').val(),
            	queryDate: $.trim($('#queryDate').val()),
                limit : 1
            }, //发送数据
            page:1
        }).trigger("reloadGrid"); //重新载入
    }
    
    //导出数据
    function exportFile(){
        var queryDate = $('#queryDate').val();
		window.location.href = "landlordData/dataListToExcel?queryDate="+queryDate;
	}
</script>
</body>
</html>
