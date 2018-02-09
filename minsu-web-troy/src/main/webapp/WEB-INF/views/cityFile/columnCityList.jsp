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

<link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<!-- 下拉框搜索插件-->
<link href="${staticResourceUrl}/css/combo.select.css${VERSION}" rel="stylesheet">
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
									style="height: 35px; line-height: 35px;">模板名称:</label>
							</div>
							<div class="col-sm-2">
								<input id="tempName" name="tempName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">模板类型:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="tempType" id="tempType">
									  <option value="">请选择</option>
	                           	   	  <option value="0">APP</option>
	                           	   	  <option value="1">PC</option>
	                           	   	  <option value="2">APP/PC</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">专栏标题:</label>
							</div>
							<div class="col-sm-2">
								<input id="colTitle" name="colTitle" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">城市:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="cityCode" id="cityCode">
									<option value="">请选择</option>
									<c:forEach items="${openCityList }" var="city">
									<option value="${city.code }">${city.name }</option>
									</c:forEach>
								</select>
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
					<customtag:authtag authUrl="cityFile/columnCityAdd"><button class="btn btn-primary" type="button" onclick="columnCityAdd();"><i class="fa fa-save"></i>&nbsp;添加</button></customtag:authtag>
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
								data-single-select="true" data-url="cityFile/findColumnCityList">
								<thead>
									<tr>
										<th data-field="fid" data-visible="false"></th>
										<th data-field="colTitle" data-width="15%" data-align="center" >城市专栏标题</th>
										<th data-field="cityCode" data-width="15%" data-align="center" data-formatter="formateCode" >城市</th>
										<th data-field="tempName" data-width="15%" data-align="center" >模板名称</th>
										<th data-field="tempType" data-width="5%" data-align="center"  data-formatter="formateTempType">模板类型</th>
										<th data-field="createDate" data-width="10%" data-align="center" data-formatter="formateDate">注册日期</th>
										<th data-field="colStatus" data-width="5%" data-align="center" data-formatter="formateColStatus">模板状态</th>
										<th data-field="empName" data-width="15%" data-align="center" >创建人</th>
										<th data-field="operate" data-width="15%" data-align="center" data-formatter="formateOperate">操作</th>
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
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	<!-- 时间日期操作 -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/jquery.combo.select.js${VERSION}"></script>
	
	
	<script>
		 //新增专栏
		function columnCityAdd(){
			$.openNewTab("columnCityAdd","cityFile/columnCityAdd", "新增城市专栏");
		}
		 
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				tempName:$.trim($('#tempName').val()),
				tempType:$.trim($('#tempType').val()),
				colTitle:$.trim($('#colTitle').val()),
				cityCode:$.trim($('#cityCode').val())
			}
		}
		
		//条件查询
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		// 模板类型
		function formateTempType(value, row, index){
			if(value==0){
				return "APP";
			}else if(value==1){
				return "PC";
			}else if(value==2){
				return "APP/PC";
			}
		}

		// 专栏状态
		function formateColStatus(value, row, index){
			if(value==0){
				return "新建";
			}else if(value==1){
				return "上线";
			}else if(value==2){
				return "下线";
			}
		}
		
		//操作
		function formateOperate(value, row, index){
			if(row.colStatus==0||row.colStatus==2){
				return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/createPage'>onclick='createPage(\""+row.fid+"\");'</customtag:authtag>><span>生成页</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/updateColumnCity'>onclick='updateColumnCity(\""+row.fid+"\");'</customtag:authtag>><span>修改</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/upDownColumnCity'>onclick='upDownColumnCity(\""+row.fid+"\");'</customtag:authtag>><span>上线</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionList'>onclick='columnRegionList(\""+row.fid+"\",\""+row.colTitle+"\",\""+row.cityCode+"\");'</customtag:authtag>><span>专栏景点商圈</span></a>"
			} else {
				return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/createPage'>onclick='createPage(\""+row.fid+"\");'</customtag:authtag>><span>生成页</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/updateColumnCity'>onclick='updateColumnCity(\""+row.fid+"\");'</customtag:authtag>><span>修改</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/upDownColumnCity'>onclick='upDownColumnCity(\""+row.fid+"\");'</customtag:authtag>><span>下线</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionList'>onclick='columnRegionList(\""+row.fid+"\",\""+row.colTitle+"\",\""+row.cityCode+"\");'</customtag:authtag>><span>专栏景点商圈</span></a>"

			}
		}
		
		//修改城市专栏
		function updateColumnCity(fid){
			$.openNewTab(new Date().getTime(),'cityFile/updateColumnCity?fid='+fid, "修改城市专栏");
		}
		
		//专栏包含景点商圈列表
		function columnRegionList(fid,colTitle,cityCode){
			$.openNewTab(new Date().getTime(),'cityFile/columnRegionList?columnCityFid='+fid+'&cityCode='+cityCode, colTitle+"的商圈景点");
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
		//格式化城市编码
		function formateCode(value,row,index){
//			alert(value);findCityNameByCode
			var cityName = '';
			$.ajax({
				type: "POST",
				url: "cityFile/findCityNameByCode",
				dataType:"json",
				async:false,
				data: {"cityCode":value},
				success: function (result) {
					console.log(result);
					if(result.code==0){
						cityName = result.data.cityName;
					}else{
						cityName = '';
					}
				},
				error: function(result) {
					cityName = '';
				}
			});
			return cityName;
		}


		//生成页面
		function createPage(fid) {
			$.ajax({
				type: "POST",
				url: "cityFile/createPage",
				dataType:"json",
				data: {"fid":fid},
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

		//上下线专栏
		function upDownColumnCity(fid){
			$.ajax({
	            type: "POST",
	            url: "cityFile/upDownColumnCity",
	            dataType:"json",
	            data: {"fid":fid},
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
		
		$(function() {
		    $('#cityCode').comboSelect();
		});
	</script>
</body>
</html>
