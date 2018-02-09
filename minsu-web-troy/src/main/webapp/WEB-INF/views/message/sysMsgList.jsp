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
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001"
	rel="stylesheet">
<link
	href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row row-lg">
			<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">消息标题:</label>
							</div>
							<div class="col-sm-2">
								<input id="msgTitle" name="msgTitle" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">消息类型:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="msgType"
									name="msgType">
									<option value="1" selected="selected">个人</option>
									<option value="2">房东</option>
									<option value="3">房客</option>
									<option value="4">全部用户</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">发布状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="isRelease" name="isRelease">
									<option value="0" selected="selected">未发布</option>
									<option value="1">已发布</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<button class="btn btn-primary" type="button" style="margin-left: 77px;"
									onclick="CommonUtils.query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</div>
						</div>
					</form>	
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
					<button class="btn btn-primary" type="button" onclick="toAddSysMsg(0);">
						<i class="fa fa-save"></i>&nbsp;添加
					</button>
					<button class="btn btn-info " type="button" onclick="toAddSysMsg(1);">
						<i class="fa fa-edit"></i>&nbsp;编辑
					</button>
					<button class="btn btn-default " type="button"
						onclick="releaseMsg();">
						<i class="fa fa-delete"></i>&nbsp;发布
					</button> 
					<div class="example-wrap">
						<div class="example">
							 <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
								data-toggle="table" data-side-pagination="server"
								data-pagination="true" data-page-list="[10,20,50]"
								data-pagination="true" data-page-size="10"
								data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="get"
								data-single-select="true"
								data-classes="table table-hover table-condensed"
								data-height="500" data-url="${basePath }message/getSysMsgList">
								<thead>
									<tr>
										<th data-align="center" data-checkbox="true" ></th>
										<th data-field="createUid">创建人</th>
										<th data-field="msgTitle">消息标题</th>
										<th data-field="msgContent">消息内容</th>
										<th data-field="isRelease" data-formatter="getRelease" data-align="center">是否发布</th>
										<th data-field="msgTargetType" data-formatter="getType" data-align="center">通知类型</th>
										<th data-field="msgTargetUid">通知人</th>
										<th data-field="modifyUid">修改人</th>
										<th data-field="createTime" data-formatter="formateDate">创建时间</th>
										<th data-field="lastModifyDate" data-formatter="formateDate">修改时间</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<script type="text/javascript">
	   //外部js调用
    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        msgTitle:$("#msgTitle").val(),
		        msgTargetType:$("#msgType option:selected").val(),
		        isRelease:$("#isRelease option:selected").val()
		    	
	    	};
		}
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		// 格式化时间
		function formateDate(value, row, index) {
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd HH:mm:ss");
			} else {
				return "";
			}
		}
	    /**
	    *添加短信模板
	    */
		function toAddSysMsg(flag){
	    	var url= "message/addSysMsg?flag="+flag;
	    	if(flag==1||flag=="1"){
	    		var selectVar=$('#listTable').bootstrapTable('getSelections');
				if(selectVar.length == 0){
					alert("请选择一条记录");
					return;
				}
				url = url +"&fid="+selectVar[0].fid;
	    	}
			window.location.href=url;
		}
	    //发布消息
	    function releaseMsg(){
	    	var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				alert("请选择一条记录");
				return;
			}
			var isRelease = selectVar[0].isRelease;
			console.log("isRelease:"+isRelease);
			if(isRelease == 1 ){
				alert("消息已发布");
				return;
			}
			window.location.href="message/releaseSysMsg?fid="+selectVar[0].fid;
	    }
	    //状态选择
		function getRelease(value, row, index){
			if(value==0){
				return "<span class='label label-success'>未发布</span>";
			}else{
				return "<span class='label label-primary'>已发布</span>";
			}
		}
		   //消息类型
		function getType(value, row, index){
			if(value==1){
				return "个人";
			}else if(value==2){
				return "房东";
			}else if(value==3){
				return "房客";
			}else if(value == 4){
				return "所有人";
			}
		}
		
		
	</script>

</body>

</html>
