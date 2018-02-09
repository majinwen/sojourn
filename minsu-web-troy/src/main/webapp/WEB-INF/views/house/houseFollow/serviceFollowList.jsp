<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid.css${VERSION}001" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房源编号:</label>
							</div>
							<div class="col-sm-2">
								<input id="houseSn" name="houseSn" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordName" name="landlordName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房东手机号:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordMobile" name="landlordMobile" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">客服专员:</label>
							</div>
							<div class="col-sm-2">
								<input id="empName" name="empName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">跟进状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="followStatus" id="followStatus">
									<option value="">请选择</option>
									<option value="101">客服待跟进</option>
									<option value="102">客服跟进中</option>
								</select>
						    </div>
						    <div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">是否排除锁定记录:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="isNotLock" id="isNotLock">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
						    </div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">国家:</label>
							</div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b m-b" id="nationCode">
                                </select>
                            </div>
                            <div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">省份:</label>
							</div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b " id="provinceCode">
                                </select>
                            </div>
                            <div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">城市:</label>
							</div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b" id="cityCode">
                                </select>
                            </div>
							<div class="col-sm-2">
			                    <button class="btn btn-primary" type="button" onclick="queryList();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                </div>
						</div>
					</form>
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

<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/i18n/grid.locale-cn.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/jquery.jqGrid.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
<!--treeview-->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<script type="text/javascript">
	//初始化列表数据
	$(document).ready(function () {
	    $("#jqGrid").jqGrid({
	        url: 'house/houseFollow/serviceFollowData',
	        mtype: "post",
	        datatype: "json",
	        colModel: [
                   { label: '房东姓名', name: 'landlordName', width: 100},
                   { label: '跟进记录fid', name: 'fid',hidden: true},
                   { label: '房东uid', name: 'landlordUid',hidden: true},
                   { label: '房源出租方式', name: 'rentWay',hidden: true},
                   { label: '跟进状态', name: 'followStatusStr', width: 100},
                   { label: '房东手机号', name: 'landordMobile', width: 100},
                   { label: '城市', name: 'cityName', width: 100 },
                   { label: '房源编号', name: 'houseSn', width: 160, formatter:showLink},
                   { label: '房源名称', name: 'houseName', width: 150},
                   { label: '未通过原因', name: 'auditCause', width: 100 },
                   { label: '说明', name: 'refuseMark', width: 100 },
                   { label: '发布时间', name: 'pushDate', width: 100,formatter:CommonUtils.formateDate},
                   { label: '审核未通过时间', name: 'auditStatusTime', width: 100,formatter:CommonUtils.formateDate},
                   { label: '当前房源状态', name: 'houseStatusStr', width: 100}
	        ],
	        jsonReader: {
	            root:"dataList", page:"currPage", total:"totalpages",
	            records:"totalCount", repeatitems:false, id : "id"
	        },
	        height: 500,
	        rowNum: 1000,
	        viewrecords: true,
	        grouping: true,
	        groupingView: {
	        	groupField: ["landlordName"],
	            groupColumnShow: [false],
	            groupText: ["<strong>{0}<strong>"],
	            groupOrder: ["asc"],
	            groupSummary: [false],
	            groupCollapse: false
	        },
	        pager: "#jqGridPager"
	    });
		// 多级联动
		var areaJson = ${cityTreeList};
		var defaults = {
		geoJson	: areaJson
		};
		geoCascade(defaults);
	});
	
    function queryList() {
       $("#jqGrid").jqGrid('setGridParam',{
           url:"house/houseFollow/serviceFollowData",
           postData:{
        	   houseSn : $.trim($('#houseSn').val()),
        	   landlordName : $.trim($('#landlordName').val()),
        	   landlordMobile : $.trim($('#landlordMobile').val()),
        	   empName : $.trim($('#empName').val()),
        	   followStatus : $.trim($('#followStatus').val()),
        	   isNotLock : $.trim($('#isNotLock').val()),
        	   nationCode : $.trim($('#nationCode').val()),
        	   provinceCode : $.trim($('#provinceCode').val()),
        	   cityCode : $.trim($('#cityCode').val())
           }, //发送数据
           page:1
       }).trigger("reloadGrid"); //重新载入
    }
 
    //显示信息详情
    function showLink(field, value, row){
        return "<a onclick='showDetail(\""+row.fid+"\",\""+row.houseSn+"\","+row.rentWay+",\""+row.landordMobile+"\",\""+row.landlordUid+"\")' href='javascript:void(0);'>"+field+"</a>";
    }

    var showDetail =  function(followFid,houseSn,rentWay,landordMobile,landlordUid){
		$.ajax({
            type: "POST",
            url: "house/houseFollow/lockAndSaveHouseFollow",
            dataType:"json",
            data: {"followFid":followFid,"houseSn":houseSn,"rentWay":rentWay,"houseLockCode":"11001","customerMobile":landordMobile,"landlordUid":landlordUid},
            success: function (result) {
            	if(result.code==0){
            		$.openNewTab(new Date().getTime(),'house/houseFollow/houseFollowDetail?followFid='+result.data.followFid+"&rentWay="+rentWay, "房源跟进");
            	}else{
            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
            	}
            },
            error: function(result) {
            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }
    
    //刷新列表
    function refreshJqGrid(gridId){
    	$("#"+gridId).trigger("reloadGrid");
    }
</script>
<script>
    $.jgrid.defaults.width = 1800;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>
</body>
</html>
