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
                                <button class="btn btn-primary" type="button" onclick="query();">
                                    <i class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                            <div class="col-sm-1">
			                    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#modal">
			                        <i class="fa fa-search"></i>&nbsp;导出文件
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
                    	<h2 class="example-title" id="title" align="center"></h2>
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

<div class="modal inmodal" id="modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight" style="width: 700px;">
            <div class="modal-header">
                <h4 class="modal-title">选择目标周期</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="ibox float-e-margins">
                        <form id="addForm" class="form-horizontal m-t">
                            <div class="form-group">
                                <div class="row" align="center">
		                            <div class="col-sm-1 text-right mtop">
		                                <label class="control-label">月份：</label>
		                            </div>
		                            <div class="col-sm-2">
		                                <input name="targetMonthStart" id="targetMonthStart" class="laydate-icon form-control layer-date">
		                            </div>
		                            <div class="col-sm-1 text-right mtop">
		                                <label class="control-label">到</label>
		                            </div>
		                            <div class="col-sm-1 text-right mtop">
		                                <label class="control-label">月份：</label>
		                            </div>
		                            <div class="col-sm-2">
		                                <input name="targetMonthEnd" id="targetMonthEnd" class="laydate-icon form-control layer-date">
		                            </div>
                                    <div class="col-sm-2" align="center">
                                        <button type="button" id="btn-delRegion" class="btn btn-danger" onclick="exportFile()">确定</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
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
	laydate({
		elem: '#targetMonth',
		format: 'YYYY-MM'
	});
	
	var start = {
		elem: '#targetMonthStart',
		format: 'YYYY-MM',
		max: '2037-12-31', //最大日期
		choose: function(datas){
		   end.min = datas; //开始日选好后，重置结束日的最小日期
		   end.start = datas; //将结束日的初始值设定为开始日
		}
	};
	var end = {
	    elem: '#targetMonthEnd',
	    format: 'YYYY-MM',
	    max: '2037-12-31',
	    choose: function(datas){
	      start.max = datas; //结束日选好后，重置开始日的最大日期
	    }
	};
	laydate(start);
	laydate(end);
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
            url: 'regionData/dataList',
            mtype: "GET",
            datatype: "json",
            colModel: [
                {label: '国家/大区', name: 'regionName', key: true, width: 150},
                {label: '城市', name: 'cityName', width: 150},
                {label: '累计房源量', name: 'totalHouseNum', width: 150},
                {label: '房源目标', name: 'targetHouseNum', width: 150},
                {label: '房源发布', name: 'issueHouseNum', width: 150},
                {label: '上架数量', name: 'onlineHouseNum', width: 150},
                {label: '房源达成率', name: 'houseRate', width: 150, formatter: formatPercent},
                {label: '环比上月', name: 'houseQoQ', width: 150, formatter: formatPercent},
                {label: '订单目标', name: 'targetOrderNum', width: 150},
                {label: '订单数量', name: 'actualOrderNum', width: 150},
                {label: '订单达成率', name: 'orderRate', width: 150, formatter: formatPercent},
                {label: '环比上月', name: 'orderQoQ', width: 150, formatter: formatPercent},
                {label: '间夜目标', name: 'targetRentNum', width: 150},
                {label: '间夜数量', name: 'actualRentNum', width: 150},
                {label: '间夜达成率', name: 'nightRate', width: 150, formatter: formatPercent},
                {label: '平台出租率', name: 'rentRate', width: 150, formatter: formatPercent},
                {label: '环比上月', name: 'rentQoQ', width: 150, formatter: formatPercent},
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
        var targetMonth = $('#targetMonth').val();
        var month;
    	if(isNullOrBlank(targetMonth)){
    		month = new Date().getMonth() + 1;
    	}else{
    		var monthArray = targetMonth.split("-");
    		month = monthArray[1];
    	}
   		$('#title').html(month+'月目标');
    }
    
    function initConfig(){
	    $.jgrid.defaults.width = 1600;
	    $.jgrid.defaults.responsive = true;
	    $.jgrid.defaults.styleUI = 'Bootstrap';
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
            url:"regionData/dataList",
            postData:{
            	nationCode: $('#nation option:selected').val(),
            	targetMonth: $.trim($('#targetMonth').val()),
                limit : 1
            }, //发送数据
            page:1
        }).trigger("reloadGrid"); //重新载入
    }
    
    //导出数据
    function exportFile(){
        var targetMonthStart = $('#targetMonthStart').val();
        var targetMonthEnd = $('#targetMonthEnd').val();
    	if(isNullOrBlank(targetMonthStart)){
    		layer.alert('请选择起始周期', {
                icon: 5,
                skin: 'layer-ext-moon'
            });
    	}
    	if(isNullOrBlank(targetMonthStart)){
    		layer.alert('请选择结束周期', {
                icon: 5,
                skin: 'layer-ext-moon'
            });
    	}
		window.location.href = "regionData/dataListToExcel?targetMonthStart="+targetMonthStart+"&targetMonthEnd="+targetMonthEnd;
	}
</script>
</body>
</html>
