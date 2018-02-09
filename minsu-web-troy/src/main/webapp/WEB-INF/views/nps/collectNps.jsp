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

<style type="text/css">
.left2{
    margin-top: 10px;
}
.left3{padding-left:62px;}
.btn1
{
    border-right: #7b9ebd 1px solid;
    padding-right: 2px;
    border-top: #7b9ebd 1px solid;
    padding-left: 2px;
    font-size: 12px;
    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,  StartColorStr=#ffffff,  EndColorStr=#cecfde);
    border-left: #7b9ebd 1px solid;
    cursor: hand;
    padding-top: 2px;
    border-bottom: #7b9ebd 1px solid;
}
.row{margin:0;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">

	<!-- Panel Other -->
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<button class="btn btn-primary" type="button" onclick="refresh();"><i class="fa "></i>&nbsp;刷新页面</button>
					<!-- Example Pagination -->
					<customtag:authtag authUrl="black/addBlack">
						<button class="btn btn-warning" type="button" onclick="pause();"><i class="fa "></i>&nbsp;暂停</button>
					</customtag:authtag>
					<customtag:authtag authUrl="black/editBlack">
						<button class="btn btn-warning" type="button" onclick="stop();"><i class="fa "></i>&nbsp;终止</button>
					</customtag:authtag>
					<customtag:authtag authUrl="black/delBlack">
						<button class="btn btn-warning" type="button" onclick="start();"><i class="fa "></i>&nbsp;开始</button>
					</customtag:authtag>

					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover"  data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[10,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
			                            data-content-type="application/x-www-form-urlencoded"
					                    data-query-params="paginationParam"
					                    data-method="post"
					                    data-single-select="true" 
					                    data-height="470"
					                    data-url="${basePath}nps/queryNps">
					                    <thead>
					                        <tr class="trbg">
												<th data-field="id" data-width="5%"  data-checkbox="true"></th>
												<th data-field="npsCode" data-visible="false" ></th>
					                            <th data-field="npsName" data-align="center" >NPS名称</th>
					                            <th data-field="npsStatus" data-align="center" data-formatter="formateStatus" >状态</th>
					                            <th data-field="startTime"data-align="center" data-formatter="CommonUtils.formateDate">开始时间</th>
					                            <th data-field="endTime"data-align="center" data-formatter="CommonUtils.formateDate">结束时间</th>
					                            <th data-field="createTime"data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
												<th data-field="userType" data-align="center" data-formatter="formateUserType" >面向对象</th>
												<th data-field="npsRate" data-align="center" data-formatter="formateNpsRate" >NPS值</th>
					                        </tr>
					                    </thead>             
			              </table>
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
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}0013" type="text/javascript"></script>

<script type="text/javascript">


	function query(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}

	//分页查询参数
	function paginationParam(params) {
		return {
			limit:params.limit,
			page : $('#listTable').bootstrapTable('getOptions').pageNumber,
			npsCode : $.trim($('#npsCode').val())
		};
	}

	function formateStatus(value, row, index){
		if(value == 0){
			return "初始值";
		}else if(value == 1){
			return "开始";
		}else if(value == 2){
			return "暂停";
		}else if(value == 3){
			return "终止";
		}else{
			return value;
		}
	}

	function pause() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
		var npsCode = rows[0].npsCode;
		$.ajax({
			type: "post",
			url: "nps/editNpsStatus",
			dataType:"json",
			data: {
				"npsCode" : npsCode,
				"npsStatus" : 2
			},
			success: function (result) {
				if(result.code == 0){
					alert("操作成功");
				}else{
					alert(result.msg);
				}
				window.location.reload();
			},
			error: function(result) {
				alert(result.msg);
				window.location.reload()
			}
		});
	}

	function stop() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
		var npsCode = rows[0].npsCode;
		$.ajax({
			type: "post",
			url: "nps/editNpsStatus",
			dataType:"json",
			data: {
				"npsCode" : npsCode,
				"npsStatus" : 3
			},
			success: function (result) {
				if(result.code == 0){
					alert("操作成功");
				}else{
					alert(result.msg);
				}
				window.location.reload();
			},
			error: function(result) {
				alert(result.msg);
				window.location.reload();
			}
		});
	}

	function start() {
		var rows = $('#listTable').bootstrapTable('getSelections');
		if (rows.length == 0) {
			layer.alert("请选择一条记录进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
			return;
		}
		var npsCode = rows[0].npsCode;
		$.ajax({
			type: "post",
			url: "nps/editNpsStatus",
			dataType:"json",
			data: {
				"npsCode" : npsCode,
				"npsStatus" : 1
			},
			success: function (result) {
				if(result.code == 0){
					alert("操作成功");
				}else{
					alert(result.msg);
				}
				window.location.reload()
			},
			error: function(result) {
				alert(result.msg);
				window.location.reload()
			}
		});
	}

	function refresh(){
		window.location.reload();
	}

	function formateUserType(value, row, index){
		if(value == 1){
			return "房东";
		}else if(value == 2){
			return "房客";
		}else{
			return value;
		}
	}
	function formateNpsRate(value, row, index){
		value = (value * 100).toFixed(2);
		return value+"%";
	}

</script>
</body>

</html>
