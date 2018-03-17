<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath}">
<title>自如民宿权限管理系统</title>
<meta name="keywords" content="自如民宿权限管理系统">
<meta name="description" content="自如民宿权限管理系统">

<link href="css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="css/font-awesome.min.css${VERSION}" rel="stylesheet">
<link href="css/animate.css${VERSION}" rel="stylesheet">
<link href="css/style.css${VERSION}" rel="stylesheet">
<link href="css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
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
									style="height: 35px; line-height: 35px;">系统名称:</label>
							</div>
							<div class="col-sm-2">
								<input id="systemName" name="systemName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">系统code:</label>
							</div>
							<div class="col-sm-2">
								<input id="systemCode" name="systemCode" type="text"
									class="form-control">
							</div>
							<div class="col-sm-2">
			                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                </div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<button class="btn btn-primary" type="button" onclick="addSystem();"><i class="fa fa-save"></i>&nbsp;添加</button>
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="post"
								data-height="520"
								data-single-select="true" data-url="system/findSystemList">
								<thead>
									<tr>
										<th data-field="fid" data-visible="false"></th>
										<th data-field="sysName" data-width="20%" data-align="center" >系统名称</th>
										<th data-field="sysCode" data-width="20%" data-align="center" >系统code</th>
										<th data-field="sysUrl" data-width="20%" data-align="center" >系统访问地址</th>
										<th data-field="createDate" data-width="20%" data-align="center" data-formatter="formateDate">创建时间</th>
										<th data-field="operate" data-width="20%" data-align="center" data-formatter="formateOperate">操作</th>
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
	<!-- 全局js -->
	<script src="js/jquery.min.js?${VERSION}"></script>
	<script src="js/bootstrap.min.js?${VERSION}"></script>
	<script src="js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="js/minsu/common/custom.js?${VERSION}"></script>
	<script src="js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 时间日期操作 -->
	<script src="js/minsu/common/date.proto.js${VERSION}"></script>
	
	
	<script>
		 //新增系统
		function addSystem(){
			$.openNewTab("addSystem","system/insertSystemPage", "新增系统信息");
		}
		 
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				systemName:$.trim($('#systemName').val()),
				systemCode:$.trim($('#systemCode').val())
			}
		}
		
		//条件查询
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		//操作
		function formateOperate(value, row, index){
			return "<a href='javascript:;' onclick='updateSystem(\""+row.fid+"\");'><span>修改</span></a>"
			+"&nbsp;&nbsp;&nbsp;<a href='javascript:;' onclick='delSystem(\""+row.fid+"\");'><span>删除</span></a>"
		}
		
		//修改城市专栏
		function updateSystem(fid){
			$.openNewTab(new Date().getTime(),'system/updateSystemPage?fid='+fid, "修改系统信息");
		}
		
			
		//格式化录入时间
		function formateDate(value, row, index){
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd");
			} else {
				return "";
			}
		}
		//刷新列表数据
		function refreshData(listTableId){
			$('#'+listTableId).bootstrapTable('refresh');
		}
		//删除系统
		function delSystem(fid){
			$.ajax({
	            type: "POST",
	            url: "system/updateSystem",
	            dataType:"json",
	            data:{"fid":fid,"isDel":1},
	            success: function (result) {
	            	if(result.code==0){
	            		$('#listTable').bootstrapTable('refresh');
	            	}else{
	            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	            	}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
	            }
	      });
		}
	</script>
</body>
</html>
