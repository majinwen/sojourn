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
		<div class="tabs-container">
			<div class="panel-heading">
				<h4 class="panel-title">项目列表</h4>
				<input id="groupFid" value="${groupFid}" hidden="hidden"/>
			</div>
					<!-- Panel Other -->
					<div class="ibox float-e-margins">
						<div class="ibox-content">
							<div class="row row-lg">
								<div class="col-sm-12">
									<customtag:authtag authUrl="activityGroup/toAddProject">
										<button id="toAddProjectBtn" class="btn btn-primary" type="button" onclick="toAddProject()">
											<i class="fa fa-save"></i> 添加项目 </button>
									</customtag:authtag>

									<customtag:authtag authUrl="activityGroup/toAddLayout">
										<button id="toAddLayoutBtn" class="btn btn-primary" type="button" onclick="toAddLayout()">
											<i class="fa fa-save"></i> 添加户型 </button>
									</customtag:authtag>

									<customtag:authtag authUrl="activityGroup/toAddRoom">
										<button id="toAddRoomBtn" class="btn btn-primary" type="button" onclick="toAddRoom()">
											<i class="fa fa-save"></i> 添加房间 </button>
									</customtag:authtag>

									<customtag:authtag authUrl="activityGroup/deleteHouseRel">
										<button id="deleteHouseRel" class="btn btn-primary" type="button" onclick="deleteHouseRel()">
											<i class="fa fa-save"></i> 删除 </button>
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
												   data-url="activityGroup/groupHouseList">
												<thead>
													<tr class="trbg">
														<th data-field="fid" data-width="5%" data-radio="true"></th>
														<th data-field="id" data-visible="false"></th>
														<th data-field="projectName" data-width="10%" data-align="center">项目名称</th>
														<th data-field="layoutName" data-width="10%" data-align="center">户型名称</th>
														<th data-field="roomNumber" data-width="10%" data-align="center">房间编号</th>
														<th data-field="createDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">添加时间</th>
														<th data-field="createName" data-width="10%" data-align="center">添加人</th>
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
			</div>
		</div>
	</div>

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
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

<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>


<script type="text/javascript">

    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
            groupFid : $("#groupFid").val()
    	};
	}

	function toAddProject() {
        $.openNewTab(new Date().getTime(),'activityGroup/toAddProject?groupFid='+$("#groupFid").val(), "添加项目");
    }

    function toAddLayout() {
        $.openNewTab(new Date().getTime(),'activityGroup/toAddLayout?groupFid='+$("#groupFid").val(), "添加户型");
    }

    function toAddRoom() {
        $.openNewTab(new Date().getTime(),'activityGroup/toAddRoom?groupFid='+$("#groupFid").val(), "添加房间");
    }

    //删除房源组中某一成员
    function deleteHouseRel() {
        var selectVar=$('#listTable').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
        layer.confirm("确认删除吗?", {icon: 5,title:'提示'},function(index){
            $.ajax({
                url:"${basePath}activityGroup/deleteHouseRel",
                data:{id:selectVar[0].id},
                dataType:"json",
                type:"post",
                success:function (result) {
                    if(result.code === 0){
                        layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                        refreshData();
                    }else{
                        layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                    }
                },
                error:function(result){
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                }
            });
            layer.close(index);
        });
    }

    function refreshData() {
        $('#listTable').bootstrapTable('refresh');
    }


</script>
</body>

</html>
