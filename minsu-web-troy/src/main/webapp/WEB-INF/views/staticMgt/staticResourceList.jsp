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
									<label class="col-sm-1 control-label mtop">主题名称:</label>
									<div class="col-sm-2">
										<input id="resTitle" name="resTitle" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">资源编码:</label>
									<div class="col-sm-2">
										<input id="resCode" name="resCode" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">资源类型:</label>
									<div class="col-sm-2">
										<select class="form-control m-b" id="resType">
		                                    <option value="">请选择</option>
		                                    <c:forEach items="${staticResourceTypeMap }" var="obj">
			                                    <option value="${obj.key }">${obj.value }</option>
		                                    </c:forEach>
	                                    </select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="row" style="margin-left: 10px;">
									<label class="col-sm-1 control-label mtop">创建人:</label>
									<div class="col-sm-2">
										<input id="creatorName" name="creatorName" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">创建时间：</label>
		                           	<div class="col-sm-2">
		                                <input id="createDateStart"  value=""  name="createDateStart" class="laydate-icon form-control layer-date">
		                           	</div>
		                           	<label class="col-sm-1 control-label">到</label>
		                            <div class="col-sm-2">
		                                <input id="createDateEnd" value="" name="createDateEnd" class="laydate-icon form-control layer-date">
		                           	</div>
									<div class="col-sm-1">
										<button class="btn btn-primary" type="button" onclick="query();">
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
						<customtag:authtag authUrl="">
						    <button class="btn btn-primary" type="button" onclick="addStaticResource();"><i class="fa fa-plus"></i>&nbsp;新增主题</button>
						</customtag:authtag>
						<button class="btn btn-primary" type="button" onclick="editStaticResource();"><i class="fa fa-plus"></i>&nbsp;修改主题</button>
						<div class="example-wrap">
							<div class="example">
								<table id="listTable" 
									class="table table-bordered" 
									data-click-to-select="true"
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
									data-height="560"
									data-url="staticMgt/showResourceList">
									<thead>
										<tr>
											<th data-field="fid" data-visible="false"></th>
											<th data-field="id" data-checkbox="true"></th>
											<th data-field="resCode" data-align="center">资源编码</th>
											<th data-field="resTitle" data-align="center">主题名称</th>
											<th data-field="resType" data-align="center" data-formatter="formateResType">资源类型</th>
											<th data-field="resContent" data-align="center">资源内容</th>
											<th data-field="resRemark" data-align="center">资源备注</th>
											<th data-field="creatorName" data-align="center">创建人</th>
											<th data-field="createDate" data-align="center" data-formatter="formateDate">创建时间</th>
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
	
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>

	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>


	<!-- Page-Level Scripts -->
	<script>
		$(function (){
			//初始化日期
			datePickerFormat('createDateStart', '00:00:00');
			datePickerFormat('createDateEnd', '23:59:59');
		});
	
	
		function datePickerFormat(elemId, time){
			laydate({
				elem: '#' + elemId,
				format: 'YYYY-MM-DD ' + time,
				istime: false,
				istoday: true
			});
		}
		
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				resTitle : $.trim($('#resTitle').val()),
				resCode : $.trim($('#resCode').val()),
				resType : $('#resType option:selected').val(),
				creatorName : $.trim($('#creatorName').val()),
				createDateStart : $.trim($('#createDateStart').val()),
				createDateEnd : $.trim($('#createDateEnd').val())
			};
		}

		function query() {
			$('#listTable').bootstrapTable('selectPage', 1);
		}
		
		// 资源类型
		function formateResType(value, row, index){
			var staticResourceTypeJson = ${staticResourceTypeJson };
			var result = '';
			$.each(staticResourceTypeJson,function(i,n){
				if(value == i){
					result = n;
					return false;
				}
			});
			return result;
		}
   
		function addStaticResource(){
			window.location.href = "staticMgt/addStaticResource";
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
		
		function editStaticResource(){
			var resCode=$("#listTable").bootstrapTable('getSelections')[0].resCode;
			$.openNewTab(new Date().getTime(),'staticMgt/editStaticResource?resCode='+resCode, "修改静态资源");
		}
	</script>
</body>
</html>
