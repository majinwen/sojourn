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
			<ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"> 房源组</a></li>
				<li class=""><a data-toggle="tab" href="#tab-2" aria-expanded="false">用户组</a></li>
			</ul>
			<div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="ibox float-e-margins">
								<div class="ibox-content">
									<form class="form-horizontal m-t" id="commentForm_house">
										<div class="form-group">
											<div class="row">
												<div class="col-sm-6">
													<label class="col-sm-3 control-label left2">业务线名称：</label>
													<div class="col-sm-3 left2">
														<select id="serviceLine_house" name="serviceLine_house" class="form-control m-b m-b">
															<option value="">请选择</option>
															<c:forEach items="${serviceLineList}" var="obj">
																<option value="${obj.orcode }">${obj.name }</option>
															</c:forEach>
														</select>
													</div>
													<label class="col-sm-3 control-label left2">房源组名称：</label>
													<div class="col-sm-3 left2">
														<input id="groupName_house" name="groupName_house" type="text" class="form-control">
													</div>
												</div>
												<div class="col-sm-6">
													<label class="col-sm-3 control-label left2">项目名称：</label>
													<div class="col-sm-3 left2">
														<select id="projectName" name="projectName" class="form-control m-b m-b" onchange="htmlLayout()">
														</select>
													</div>
													<label class="col-sm-3 control-label left2">户型：</label>
													<div class="col-sm-3 left2">
														<select id="layout" name="layout" class="form-control m-b m-b" onchange="htmlRoomNum()">
														</select>
													</div>
												</div>
												<div class="col-sm-6">
													<label class="col-sm-3 control-label left2">房间号：</label>
													<div class="col-sm-3 left2">
														<select id="roomNum" name="roomNum" class="form-control m-b m-b">
														</select>
													</div>
													<div class="col-sm-3 left2">
														<button class="btn btn-primary" type="button" onclick="queryHouseGroupList();">
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
									<customtag:authtag authUrl="activityGroup/toSaveGroup">
										<button id="toAddHouseGroupBtn" class="btn btn-primary" type="button" onclick="toAddHouseGroup()">
											<i class="fa fa-save"></i> 新建房源组 </button>
									</customtag:authtag>
									<%--<customtag:authtag authUrl="activityGroup/deleteGroup">
										<button id="deleteHouseGroupBtn" class="btn btn-primary" type="button" onclick="deleteHouseGroup()">
											<i class="fa fa-save"></i> 删除 </button>
									</customtag:authtag>--%>

									<customtag:authtag authUrl="activityGroup/groupHouseList">
										<button id="toGroupHouseListBtn" class="btn btn-primary" type="button" onclick="toGroupHouseList()">
											<i class="fa fa-save"></i> 查看房源组详情 </button>
									</customtag:authtag>

									<div class="example-wrap">
										<div class="example">
											<table id="listTable_house" class="table table-bordered table-hover" data-click-to-select="true"
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
												   data-query-params="paginationParam_house"
												   data-method="post"
												   data-single-select="true"
												   data-height="500"
												   data-url="activityGroup/groupList">
												<thead>
													<tr class="trbg">
														<th data-field="id" data-width="5%" data-radio="true"></th>
														<th data-field="fid" data-visible="false"></th>
														<th data-field="groupName" data-width="10%" data-align="center">组名称</th>
														<th data-field="serviceLine" data-width="10%" data-align="center" data-formatter="formateServiceLine">业务线名称</th>
														<th data-field="createName" data-width="10%" data-align="center">创建人</th>
														<th data-field="createDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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


				<div id="tab-2" class="tab-pane">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="ibox float-e-margins">
								<div class="ibox-content">
									<form class="form-horizontal m-t" id="commentForm_user">
										<div class="form-group">
											<div class="row">
                                                <div class="col-sm-10">
                                                    <label class="col-sm-1 control-label left2">业务线名称：</label>
                                                    <div class="col-sm-2 left2">
														<select id="serviceLine_user" name="serviceLine_user" class="form-control m-b m-b">
															<option value="">请选择</option>
															<c:forEach items="${serviceLineList}" var="obj">
																<option value="${obj.orcode }">${obj.name }</option>
															</c:forEach>
														</select>
													</div>
                                                    <label class="col-sm-1 control-label left2">用户组名称：</label>
                                                    <div class="col-sm-2 left2">
														<input id="groupName_user" name="groupName_user" type="text" class="form-control">
													</div>

                                                    <label class="col-sm-1 control-label left2">用户名：</label>
                                                    <div class="col-sm-2 left2">
														<input id="customerName_user" name="customerName_user"
															   type="text" class="form-control">
													</div>

                                                    <label class="col-sm-1 control-label left2">注册手机号：</label>
                                                    <div class="col-sm-2 left2">
														<input id="customerMobile_user" name="customerMobile_user"
															   type="text" class="form-control">
													</div>
												</div>
                                                <div class="col-sm-2">
													<div class="col-sm-3 left2">
														<button class="btn btn-primary" type="button" onclick="queryUserGroupList();">
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
									<customtag:authtag authUrl="activityGroup/toSaveGroup">
										<button id="toAddUserGroupBtn" class="btn btn-primary" type="button" onclick="toAddUserGroup()">
											<i class="fa fa-save"></i> 新建用户组 </button>
									</customtag:authtag>
									<%--<customtag:authtag authUrl="activityGroup/deleteGroup">
										<button id="deleteUserGroupBtn" class="btn btn-primary" type="button" onclick="deleteUserGroup()">
											<i class="fa fa-save"></i> 删除 </button>
									</customtag:authtag>--%>

									<customtag:authtag authUrl="activityGroup/toGroupUserList">
										<button id="toGroupUserListBtn" class="btn btn-primary" type="button" onclick="toGroupUserList()">
											<i class="fa fa-save"></i> 查看用户组详情 </button>
									</customtag:authtag>


									<div class="example-wrap">
										<div class="example">
											<table id="listTable_user" class="table table-bordered table-hover" data-click-to-select="true"
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
												   data-query-params="paginationParam_user"
												   data-method="post"
												   data-single-select="true"
												   data-height="500"
												   data-url="activityGroup/groupList">
												<thead>
												<tr class="trbg">
													<th data-field="id" data-width="5%" data-radio="true"></th>
													<th data-field="fid" data-visible="false"></th>
													<th data-field="groupName" data-width="10%" data-align="center">组名称</th>
													<th data-field="peopleNum" data-width="10%" data-align="center">
														组人数
													</th>
													<th data-field="serviceLine" data-width="10%" data-align="center" data-formatter="formateServiceLine">业务线名称</th>
													<th data-field="createName" data-width="10%" data-align="center">创建人</th>
													<th data-field="createDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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

	<!--新增房源组弹出框 -->
	<div class="modal inmodal fade" id="addHouseGroupPage"
		 tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<form id="addHouseGroupForm" class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<label class="col-sm-3 control-label left2">业务线名称：</label>
												<div class="col-sm-6 left2">
													<select id="serviceLine_house_add" name="serviceLine_house_add" class="form-control m-b m-b">
														<option value="">请选择</option>
														<c:forEach items="${serviceLineList}" var="obj">
															<option value="${obj.orcode }">${obj.name }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>

										<br/>

										<div class="row">
											<div class="col-sm-12">
												<label class="col-sm-3 control-label left2">房源组名称：</label>
												<div class="col-sm-6 left2">
													<input id="groupName_house_add" name="groupName_house_add" type="text" class="form-control">
												</div>
											</div>
										</div>
									</form>


									<br/>

									<div class="row" style="margin-bottom:10px;">
										<div class="col-sm-6 text-right">
											<customtag:authtag authUrl="activityGroup/addGroup" ><button type="button" class="btn btn-success btn-sm"
																										 onclick="addHouseGroup()">确定</button></customtag:authtag>
										</div>
										<div class="col-sm-6">
											<customtag:authtag authUrl="activityGroup/addGroup" ><button type="button" class="btn btn-danger btn-sm"
																										 onclick="closeAddHouseGroupPage()">取消</button></customtag:authtag>
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

	<!--新增用户组弹出框 -->
	<div class="modal inmodal fade" id="addUserGroupPage"
		 tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>

				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<form id="addUserGroupForm" class="form-group">
										<div class="row">
											<div class="col-sm-12">
												<label class="col-sm-3 control-label left2">业务线名称：</label>
												<div class="col-sm-6 left2">
													<select id="serviceLine_user_add" name="serviceLine_user_add" class="form-control m-b m-b">
														<option value="">请选择</option>
														<c:forEach items="${serviceLineList}" var="obj">
															<option value="${obj.orcode }">${obj.name }</option>
														</c:forEach>
													</select>
												</div>
											</div>
										</div>

										<br/>

										<div class="row">
											<div class="col-sm-12">
												<label class="col-sm-3 control-label left2">用户组名称：</label>
												<div class="col-sm-6 left2">
													<input id="groupName_user_add" name="groupName_user_add" type="text" class="form-control">
												</div>
											</div>
										</div>
									</form>


									<br/>

									<div class="row" style="margin-bottom:10px;">
										<div class="col-sm-6 text-right">
											<customtag:authtag authUrl="activityGroup/addGroup" ><button type="button" class="btn btn-success btn-sm"
																											  onclick="addUserGroup()">确定</button></customtag:authtag>
										</div>
										<div class="col-sm-6">
											<customtag:authtag authUrl="activityGroup/addGroup" ><button type="button" class="btn btn-danger btn-sm"
																											  onclick="closeAddUserGroupPage()">取消</button></customtag:authtag>
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

    $(function(){
        $("#addHouseGroupForm").validate({
            rules: {
                serviceLine_house_add: {
                    required: true
                },
                groupName_house_add: {
                    required: true,
                    maxlength: 200
                }
            }
        });
        $("#addUserGroupForm").validate({
            rules: {
                serviceLine_user_add: {
                    required: true
                },
                groupName_user_add: {
                    required: true,
                    maxlength: 200
                }
            }
        });

        $.ajax({
            url:"${basePath}activityGroup/projectList",
            data:{
                limit: 9999,
                page: 1
            },
            dataType:"json",
            type:"post",
            success:function (result) {
                var projectList = result.rows;
                temp_html = "<option value=''>请选择</option>";
                $.each(projectList,function(i,project){
                    temp_html+="<option value='" + project.fid + "'>" + project.fname + "</option>";
                });
                $('#projectName').html(temp_html);
            },
            error:function(result){
                layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    });

    function htmlLayout() {
        var projectid = $('#projectName option:selected').val();
        if(projectid == '' || projectid == null){
            $('#layout').empty();
            $('#roomNum').empty();
        }else{
            $('#roomNum').empty();
            $.ajax({
                url:"${basePath}activityGroup/layoutList",
                data:{
                    limit: 9999,
                    page: 1,
                    projectid : $('#projectName option:selected').val()
                },
                dataType:"json",
                type:"post",
                success:function (result) {
                    var layoutList = result.rows;
                    temp_html = "<option value=''>请选择</option>";
                    $.each(layoutList,function(i,layout){
                        temp_html+="<option value='" + layout.housetypeid + "'>" + layout.housetypename + "</option>";
                    });
                    $('#layout').html(temp_html);
                },
                error:function(result){
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                }
            });
		}
    }

    function htmlRoomNum() {
        if($('#layout option:selected').val() == ''){
            $('#roomNum').empty();
		}else{
            $.ajax({
                url:"${basePath}activityGroup/roomList",
                data:{
                    limit: 9999,
                    page: 1,
                    housetypeid : $('#layout option:selected').val()
                },
                dataType:"json",
                type:"post",
                success:function (result) {
                    var roomList = result.rows;
                    temp_html = "<option value=''>请选择</option>";
                    $.each(roomList,function(i,room){
                        temp_html+="<option value='" + room.roomid + "'>" + room.roomnumber + "</option>";
                    });
                    $('#roomNum').html(temp_html);
                },
                error:function(result){
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                }
            });
		}
    }


    function formateServiceLine(value, row, index){
        if(value == 1||value == '1'){
            return "民宿";
        }else if(value == 2||value == '2'){
            return "公寓";
        }else if(value == 3||value == '3'){
            return "驿站";
        }else {
            return "";
        }
    }

    function queryHouseGroupList(){
    	$('#listTable_house').bootstrapTable('refresh');
    }

    function queryUserGroupList(){
        $('#listTable_user').bootstrapTable('refresh');
    }

    function paginationParam_house(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable_house').bootstrapTable('getOptions').pageNumber,
            groupName:$('#groupName_house').val(),
            groupType:2,
            serviceLine:$('#serviceLine_house option:selected').val(),
            projectId : $('#projectName option:selected').val(),
            layoutId: $('#layout option:selected').val(),
            roomId : $('#roomNum').val()
    	};
	}

    function paginationParam_user(params) {
        return {
            limit: params.limit,
            page: $('#listTable_user').bootstrapTable('getOptions').pageNumber,
            groupName:$('#groupName_user').val(),
			groupType:1,
            serviceLine: $('#serviceLine_user option:selected').val(),
            customerName: $('#customerName_user').val(),
            customerPhone: $('#customerMobile_user').val()
        };
    }

	function toAddHouseGroup() {
        $("#addHouseGroupPage").modal("toggle");
    }

    function toAddUserGroup() {
        $("#addUserGroupPage").modal("toggle");
    }

	function closeAddHouseGroupPage() {
        $("#addHouseGroupPage").modal("hide");
    }

    function closeAddUserGroupPage() {
        $("#addUserGroupPage").modal("hide");
    }

    function addHouseGroup() {
        $.ajax({
            beforeSend : function(){
                return $("#addHouseGroupForm").valid();
            },
            url:"${basePath}activityGroup/addGroup",
            data:{groupName:$('#groupName_house_add').val(),
				  groupType:2,
				  serviceLine:$('#serviceLine_house_add option:selected').val()},
            dataType:"json",
            type:"post",
            success:function (result) {
                if(result.code === 0){
                    layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                    $("#addHouseGroupPage").modal("hide");
                    queryHouseGroupList();
                }else{
                    layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                }
            },
            error:function(result){
                layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }

    function addUserGroup() {
        $.ajax({
            beforeSend : function(){
                return $("#addUserGroupForm").valid();
            },
            url:"${basePath}activityGroup/addGroup",
            data:{groupName:$('#groupName_user_add').val(),
                groupType:1,
                serviceLine:$('#serviceLine_user_add option:selected').val()},
            dataType:"json",
            type:"post",
            success:function (result) {
                if(result.code === 0){
                    layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                    $("#addUserGroupPage").modal("hide");
                    queryUserGroupList();
                }else{
                    layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                }
            },
            error:function(result){
                layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
        });
    }

	function deleteHouseGroup() {
        var selectVar=$('#listTable_house').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
        layer.confirm("确认删除吗?", {icon: 5,title:'提示'},function(index){
            $.ajax({
                url:"${basePath}activityGroup/deleteGroup",
                data:{id:selectVar[0].id},
                dataType:"json",
                type:"post",
                success:function (result) {
                    if(result.code === 0){
                        layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                        queryHouseGroupList();
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

    function deleteUserGroup() {
        var selectVar=$('#listTable_user').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
        layer.confirm("确认删除吗?", {icon: 5,title:'提示'},function(index){
            $.ajax({
                url:"${basePath}activityGroup/deleteGroup",
                data:{id:selectVar[0].id},
                dataType:"json",
                type:"post",
                success:function (result) {
                    if(result.code === 0){
                        layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
                        queryUserGroupList();
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

    //跳转房源组详情页
    function toGroupHouseList() {
        var selectVar=$('#listTable_house').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
        if(selectVar[0].serviceLine == 2 || selectVar[0].serviceLine == '2'){
            $.openNewTab(new Date().getTime(),'activityGroup/toGroupHouseList?groupFid='+selectVar[0].fid, selectVar[0].groupName);
        }else{
            layer.alert("现只支持公寓业务线", {icon: 6,time: 2000, title:'提示'});
        }
    }

    //跳转用户组详情页
    function toGroupUserList() {
        var selectVar=$('#listTable_user').bootstrapTable('getSelections');
        if(selectVar.length == 0){
            layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
            return;
        }
		$.openNewTab(new Date().getTime(),'activityGroup/toGroupUserList?groupFid='+selectVar[0].fid, selectVar[0].groupName);
    }




</script>
</body>

</html>
