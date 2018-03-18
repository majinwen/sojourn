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
                                <label class="control-label">专员系统号：</label>
                            </div>
                            <div class="col-sm-2 ">
								<input id="sEmpCode" name="sEmpCode" type="text" class="form-control">
                            </div>

                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">专员姓名：</label>
                            </div>
                            <div class="col-sm-2">
								<input id="sEmpName" name="sEmpName" type="text" class="form-control">
                            </div>

                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">月份：</label>
                            </div>
                            <div class="col-sm-2">
                                <input name="sTargetMonth" id="sTargetMonth" class="laydate-icon form-control layer-date">
                            </div>

                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="queryList();">
                                    <i class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                            <div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="exportFile();">
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

<!--设置专员目标 弹层-->
<div class="modal inmodal" id="cityTargetModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content animated bounceInRight" style="width: 900px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="clearData()"><span aria-hidden="true">&times;</span><span
                        class="sr-only">关闭</span></button>
                <h4 class="modal-title">设置专员目标</h4>
            </div>
            <div class="modal-body">
                    <div class="ibox float-e-margins">
                        <form id="setForm" class="form-horizontal m-t">
                        <input type="hidden"  name="empCode" id="empCode"/>
                            <div class="form-group">
                                <div class="row mbottom">
                                    <div class="col-sm-2">
                                        <label class="control-label">目标月份：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input name="targetMonth" id="targetMonth" class="laydate-icon form-control layer-date">
                                    </div>
                                     <div class="col-sm-2">
                                        <label class="control-label">房源发布目标：</label>
                                    </div>
                                    <div class="col-sm-3">
                                        <input class="form-control" type="text" id="targetHouseNum" name="targetHouseNum">
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!--历史记录-->
                    <div class="row">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>历史记录</h5>
                            </div>
                            <div class="ibox-content">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>房源发布目标</th>
                                            <th>操作人</th>
                                            <th>操作日期</th>
                                        </tr>
                                    </thead>
                                    <tbody id="logBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal" onclick="clearData()">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"  onclick="saveEmpTarget()">保存</button>
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
    $(function () {
        //初始化日期
        CommonUtils.datePickerMonth('sTargetMonth');
        CommonUtils.datePickerMonthChoose('targetMonth',function (dates){
        	$.ajax({
	            type: "POST",
	            url: "empTarget/findEmpTargetByMcode",
	            dataType:"json",
	            data: {"targetMonth":dates,"empCode":$("#empCode").val()},
	            success: function (result) {
	            	$("#logBody").html("");
	            	if(result.code==0){
	            		if(result.data.empTarget){
	            			$("#targetHouseNum").val(result.data.empTarget.targetHouseNum);
	            			if(result.data.logList){
	            				for(var i=0;i<result.data.logList.length;i++){
	            					$("#logBody").append("<tr><td>"+result.data.logList[i].targetHouseNum+"</td><td>"+result.data.logList[i].createEmpName+"</td><td>"+CommonUtils.formateDate(result.data.logList[i].createDate)+"</td></tr>");
	            				}
	            			}
	            		}
	            	}else{
	            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	            	}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
	            }
	        });
        });
    });
    //初始化列表数据
    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url: 'empTarget/dataList',
            mtype: "post",
            datatype: "json",
            colModel: [
                 {label: '目标fid', name: 'empTargetFid', hidden: true},
                 {label: '专员', name: 'empName', width: 75},
                 {label: '专员code', name: 'empCode',hidden: true},
                 {label: '目标月份', name: 'targetMonth', width: 150},
                 {label: '房源上架目标', name: 'targetHouseNum', width: 150,sorttype:"int",editable:true, summaryType:'sum',summaryTpl:'<b>总目标: {0}</b>',frozen:true}
            ],
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            height: 400,
            rowNum: 1000,
            viewrecords: true,
            grouping: true,
            groupingView: {
            	groupField: ["empCode"],
                groupColumnShow: [false],
                groupText: ["<strong>员工编号：{0}<strong>&nbsp;&nbsp;&nbsp;"+'<button onclick="setEmpTarget({0})" class="btn btn-primary btn-xs btn-target-set" type="button" style="margin: 0;" data-toggle="modal" data-target="#cityTargetModal" data-code="{0}">设置</button>'],
                groupOrder: ["asc"],
                groupSummary: [true],
                groupCollapse: false
            },
            pager: "#jqGridPager"
        });
    });
   
   //设置专员目标按钮
   function setEmpTarget(empCode) {
        $("#empCode").val(empCode);
   }
   //清空数据
   function clearData(){
		$("#targetMonth").val("");
		$("#targetHouseNum").val("");
		$("#empCode").val("");
		$("#logBody").html("");
   }
   //保存或更新专员目标
   function saveEmpTarget(){
   	$.ajax({
        type: "POST",
        url: "empTarget/insertUpData",
        dataType:"json",
        data: {"targetMonth":$("#targetMonth").val(),"targetHouseNum":$("#targetHouseNum").val(),"empCode":$("#empCode").val()},
        success: function (result) {
        	if(result.code==0){
        		$("#targetMonth").val("");
        		$("#targetHouseNum").val("");
        		$("#empCode").val("");
        		$("#logBody").html("");
      		    $("#jqGrid").trigger("reloadGrid");
        		$("#targetMonth").val();
        	}else{
        		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
        	}
        },
        error: function(result) {
        	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
        }
    });
   }

   function queryList() {
       $("#jqGrid").jqGrid('setGridParam',{
           url:"empTarget/dataList",
           postData:{
               limit : $('#selectLimit option:selected').val(),
               empCode : $.trim($('#sEmpCode').val()),
               empName : $.trim($('#sEmpName').val()),
               targetMonth : $('#sTargetMonth').val()
           }, //发送数据
           page:1
       }).trigger("reloadGrid"); //重新载入
   }

   //导出数据
   function exportFile(){
//       alert("导出数据按钮被单击");
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
