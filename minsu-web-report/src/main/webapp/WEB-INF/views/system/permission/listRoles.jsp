<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath}">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">

<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row" >
							<div class="form-group">
								<div class="row" style="margin-left: 10px;">
									<label class="col-sm-1 control-label mtop">角色名称:</label>
									<div class="col-sm-2">
										<input id="roleName" name="roleName" type="text"
											class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">创建人:</label>
									<div class="col-sm-2">
										<input id="createrName" name="createrName" type="text"
											class="form-control">
									</div>
									<div class="col-sm-1">
										<button class="btn btn-primary" type="button"
											onclick="query();">
											<i class="fa fa-search"></i>&nbsp;查询
										</button>
									</div>
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
						<!-- Example Pagination -->
						<customtag:authtag authUrl="system/permission/addRoleResource">
						    <button class="btn btn-primary" type="button" onclick="toAddRole();"><i class="fa fa-plus"></i>&nbsp;添加</button>
						</customtag:authtag>
						<customtag:authtag authUrl="system/permission/editRoleResource">
						    <button class="btn btn-info" type="button" onclick="editRoleResource();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
						</customtag:authtag>
						<div class="example-wrap">
							<div class="example">
								<table id="listTable" class="table table-bordered" data-click-to-select="true"
									data-toggle="table" data-side-pagination="server"
									data-pagination="true" data-page-list="[10,20,50]"
									data-pagination="true" data-page-size="10"
									data-pagination-first-text="首页" data-pagination-pre-text="上一页"
									data-pagination-next-text="下一页" data-pagination-last-text="末页"
									data-content-type="application/x-www-form-urlencoded"
									data-query-params="paginationParam" data-method="post"
									data-single-select="true"
									data-height="560"
									data-url="system/permission/showRoles">
									<thead>
										<tr>
											<th data-field="fid" data-visible="false"></th>
											<th data-field="id" data-width="5%" data-checkbox="true"></th>
											<th data-field="roleName" data-width="15%"
												data-align="center">名称</th>
											<th data-field="createrName" data-width="15%"
												data-align="center">创建人</th>
											<th data-field="modifyDate" data-width="20%"
												data-align="center" data-formatter="formateDate">修改时间</th>
											<th data-field="roleDesc" data-width="15%"
												data-align="center">备注</th>
											<th data-field="isDel" data-width="15%" data-align="center"
												data-formatter="formateIsDel">状态</th>
											<th data-field="handle" data-width="15%" data-align="center"
												data-formatter="formateOperate">操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>

	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>


	<!-- Page-Level Scripts -->
	<script>
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				roleName : $.trim($('#roleName').val()),
				createrName : $.trim($('#createrName').val())
			};
		}

		function query() {
			$('#listTable').bootstrapTable('selectPage', 1);
		}

		// 操作列
		function formateOperate(value, row, index) {
			if (row.isDel == '0') {
				return "<customtag:authtag authUrl='system/permission/editRoleStatus'><button type='button'  class='btn btn-primary btn-sm'><span onclick='editRoleStatus(\""+ row.fid + "\",\""+ row.isDel + "\");'> 停用</span></button></customtag:authtag>";
			} else {
				return "<customtag:authtag authUrl='system/permission/editRoleStatus'><button type='button'  class='btn btn-red btn-sm'><span onclick='editRoleStatus(\""+ row.fid + "\",\""+ row.isDel + "\");'> 启用</span></button></customtag:authtag>";
			}
		}

		// 状态
		function formateIsDel(value, row, index) {
			if (value == '0') {
				return "已启用";
			} else {
				return "已停用";
			}
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

		//启用|禁用角色
		function editRoleStatus(roleFid, operateFlag) {
			var mes = operateFlag == 0 ? '确定要停用吗？' : '确定要启用吗';// 提示信息
			var iconNum = operateFlag == 0 ? 5 : 6; //显示icon层设置 6：笑脸  5：沮丧
			layer.confirm(mes, {
				icon : iconNum,
				title : '提示'
			}, function(index) {
				$.ajax({
					url : "system/permission/editRoleStatus",
					data : {
						roleFid : roleFid
					},
					type : "post",
					dataType : "json",
					success : function(result) {
						if (result.code === 0) {
							$('#listTable').bootstrapTable('refresh');
						} else {
							layer.alert("操作失败", {
								icon : 5,
								time : 2000,
								title : '提示'
							});
						}
					},
					error : function(result) {
						layer.alert("未知错误", {
							icon : 5,
							time : 2000,
							title : '提示'
						});
					}
				});
				layer.close(index);
			});
		}

		function toAddRole() {
	    	$.openNewTab(new Date().getTime(),"system/permission/addRoleResource", "新增角色");
		}

		function editRoleResource() {
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
	    	$.openNewTab(new Date().getTime(),"system/permission/editRoleResource?roleFid="+rows[0].fid, "修改角色");
		}
	</script>
</body>
</html>
