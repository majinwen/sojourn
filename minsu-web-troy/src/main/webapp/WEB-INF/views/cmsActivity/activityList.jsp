<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<div class="row row-lg">
		 <div class="col-sm-12">
            <div class="ibox float-e-margins">
               <div class="ibox-content">
	              <form class="form-horizontal m-t" id="commentForm">
	           		<div class="form-group">
					<div class="row">
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动名称：</label>
							<div class="col-sm-3 left2">
								<input id="activityName" name="activityName" type="text" class="form-control">
							</div>
							<label class="col-sm-3 control-label left2">活动城市：</label>
							<div class="col-sm-3 left2">
								<select id="cityCode" name="cityCode" class="form-control m-b m-b">
									<option value="">请选择</option>
									<option value="0">全部</option>
									<c:forEach items="${cityMap}" var="obj">
										<option value="${obj.key }">${obj.value }</option>
									</c:forEach>
								</select> <span class="help-block m-b-none"></span>
							</div>
						</div>
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动形式：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="activityType" name="activityType">
									<option value="">全部</option>
									<option value="1">免佣金</option>
								</select>
							</div>
							<label class="col-sm-3 control-label left2">活动状态：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="activityStatus" name="activityStatus">
									<option value="">全部</option>
									<option value="1">未启用</option>
									<option value="2">已启用</option>
									<option value="3">进行中</option>
									<option value="4">已结束</option>
								</select>
							</div>
						</div>
                	</div>
                	<div class="row">
                		<div class="col-sm-6">
		                    <label class="col-sm-3 control-label left2">活动日期：</label>
                           	<div class="col-sm-3 left2">
                               <input id="createTimeStart"  value=""  name="createTimeStart" class="laydate-icon form-control layer-date">
                           	</div>
                           	<label class="col-sm-3 control-label left2" style="text-align: center;">到</label>
                            <div class="col-sm-3 left2">
                               <input id="createTimeEnd" value="" name="createTimeEnd" class="laydate-icon form-control layer-date">
                           	</div>
	                	</div>
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动对象：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="userRole" name="userRole">
									<option value="">全部</option>
									<option value="1">种子房东</option>
								</select>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
								<button class="btn btn-primary" type="button" onclick="query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</div>
						</div>
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
					<customtag:authtag authUrl="cmsactivity/toSaveActivity">
						<button id="toAddActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 新增 </button>
				    </customtag:authtag>
				    <customtag:authtag authUrl="cmsactivity/startActivity">
						<button id="startActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 启用 </button>
				    </customtag:authtag>
				    <customtag:authtag authUrl="cmsactivity/toUpdateActivity">
						<button id="updateActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 修改 </button>
				    </customtag:authtag>
					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
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
			                    data-height="500"
			                    data-url="cmsactivity/showActivityDataList">                    
			                    <thead>
			                        <tr class="trbg">
			                            <th data-field="id" data-width="5%" data-radio="true"></th>
			                            <th data-field="" data-visible="false"></th>
			                            <th data-field="actName" data-width="10%" data-align="center">活动名称</th>
			                            <th data-field="actType" data-width="10%" data-align="center" data-formatter="formateActivityType">活动形式</th>
			                            <th data-field="cityCode" data-width="5%" data-align="center">活动城市</th>
			                            <th data-field="actStartTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动开始时间</th>
			                            <th data-field="actEndTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动结束时间</th>
			                            <th data-field="actStatus" data-width="10%" data-align="center" data-formatter="formateActivityStatus">活动状态</th>
			                            <th data-field="roleCode" data-width="5%" data-align="center" data-formatter="formateUserRole">活动对象</th>
			                            <th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>

<script type="text/javascript">

	$(function (){
		//初始化日期
		CommonUtils.datePickerFormat('createTimeStart');
		CommonUtils.datePickerFormat('createTimeEnd');
	});

	function paginationParam(params) {
		return CommonUtils.paginationCommon('listTable', {}, params);
	};
    function query(){
    	$('#listTable').bootstrapTable('selectPage', 1);
    }
    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,

	        cityCode:$('#cityCode').val(),
	        activityName:$('#activityName').val(),
	        createTimeStart:$('#createTimeStart').val(),
	        createTimeEnd:$('#createTimeEnd').val(),
	        activityType:$('#activityType').val(),
	        activityStatus:$('#activityStatus').val(),
	        userRole:$('#userRole').val()
    	};
	}
    
    
    //新增活动
    $("#toAddActivityBtn").click(function(){
    	window.location.href = "cmsactivity/toSaveActivity";
    });
    
  	//修改活动
    $("#updateActivityBtn").click(function(){
    	var selectVar=$('#listTable').bootstrapTable('getSelections');
    	if(selectVar.length == 0){
    		layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
    		return;
    	}
    	if(selectVar[0].activityStatus != 1){
    		layer.alert("该活动已启用，不允许修改！", {icon: 6,time: 2000, title:'提示'});
    		return ;
    	}
    	window.location.href = "cmsactivity/toUpdateActivity?activitySn="+selectVar[0].activitySn;
    });
    
    //启动活动
    $("#startActivityBtn").click(function(){
    	var selectVar=$('#listTable').bootstrapTable('getSelections');
    	if(selectVar.length == 0){
    		layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
    		return;
    	}
    	if(selectVar[0].activityStatus != 1){
    		layer.alert("该活动已启用！", {icon: 6,time: 2000, title:'提示'});
    		return ;
    	}
    	$.ajax({
			url:"cmsactivity/startActivity",
			data:{
				activitySn : selectVar[0].activitySn
			},
			dataType:"json",
			type:"post",
			async: false,
			success:function(data) {
				if(data.code === 0){
					layer.alert("启用成功！", {icon: 6,time: 2000, title:'提示'});
					setTimeout(function(){
						query();
					},1500);
				}else{
					layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
				}
			},
			error:function(result){
				layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
			}
		});
    });
    
    
    
    
    

	
	function formateActivityType(value, row, index){
		if(value == 1){
			return "免佣金";
		}
	}
	function formateActivityStatus(value, row, index){
		if(value == 1){
			return "未启用";
		}else if(value == 2){
			return "已启用";
		}else if(value == 3){
			return "进行中";
		}else if(value == 4){
			return "已结束";
		}
	}
	function formateUserRole(value, row, index){
		if(value == 0){
			return "全部";
		}else if(value == 1){
			return "种子房东";
		}
	}
	
</script>
</body>

</html>
