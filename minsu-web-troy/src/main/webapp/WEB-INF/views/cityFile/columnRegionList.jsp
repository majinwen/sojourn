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
</head>
<body class="gray-bg">
	<input type="hidden"  name="columnCityFid"  value="${columnCityFid }" id="columnCityFid">
	<input type="hidden"  name="pageUrl"  value="${pageUrl }" id="pageUrl">
	<input type="hidden" name="cityCode" value="${cityCode }" id="cityCode">
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<customtag:authtag authUrl="cityFile/columnCityAdd"><button class="btn btn-primary" type="button" onclick="columnRegionAdd();"><i class="fa fa-save"></i>&nbsp;添加</button></customtag:authtag>
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
								data-single-select="true" data-url="cityFile/findColumRegionList">
								<thead>
									<tr>
										<th data-field="fid" data-visible="false"></th>
										<th data-field="colTitle" data-width="15%" data-align="center" >城市专栏标题</th>
										<th data-field="regionName" data-width="15%" data-align="center" >商圈/景点名称</th>
										<th data-field="createDate" data-width="10%" data-align="center" data-formatter="formateDate">注册日期</th>
										<th data-field="empName" data-width="15%" data-align="center" >创建人</th>
										<th data-field="sortOperate" data-width="15%" data-align="center"  data-formatter="sortOperate">排序操作</th>
										<th data-field="operate" data-width="15%" data-align="center" data-formatter="formateOperate">操作</th>
										<th data-field="pageUrl" data-width="15%" data-align="center" data-formatter="formateUrl">专栏URL</th>
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
	
	
	<script>
		 //新增专栏
		function columnRegionAdd(){
			$.openNewTab("columnRegionAdd","cityFile/columnRegionAdd?columnCityFid="+$("#columnCityFid").val()+"&cityCode="+$("#cityCode").val(), "新增专栏商圈景点");
		}
		 
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				columnCityFid:$.trim($('#columnCityFid').val())
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
		
		//操作
		function formateOperate(value, row, index){
			return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionUpdate'>onclick='updateColumnRegion(\""+row.fid+"\");'</customtag:authtag>><span>修改</span></a>"
			+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/delColumnRegion'>onclick='delColumnRegion(\""+row.fid+"\");'</customtag:authtag>><span>删除</span></a>"
		}
		
		//修改城市专栏景点商圈
		function updateColumnRegion(fid){
			$.openNewTab(new Date().getTime(),'cityFile/columnRegionUpdate?fid='+fid+'&cityCode='+$("#cityCode").val(), "修改城市专栏景点商圈");
		}
		
		//专栏包含景点商圈列表
		function columnRegionList(fid,colTitle){
			$.openNewTab(new Date().getTime(),'cityFile/columnRegionList?columnCityFid='+fid, colTitle+"的商圈景点");
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

		//专栏访问URL
		function formateUrl(value, row, index) {
			var columnCityFid = $("#columnCityFid").val();
			var pageUrl = $("#pageUrl").val();
			return pageUrl+columnCityFid+"/"+row.fid+".html";
		}

		//删除景点商圈
		function delColumnRegion(fid){
			$.ajax({
	            type: "POST",
	            url: "cityFile/delColumnRegion",
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
		//排序操作
		function sortOperate(value, row, index){
			var data=$('#listTable').bootstrapTable('getData');
			if(data.length>1){
				if(index==0){
					return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionUpdate'>onclick='upColumnRegionSort(\""+data[index+1].fid+"\","+row.orderSort+",\""+row.fid+"\","+data[index+1].orderSort+");'</customtag:authtag>><span>下移</span></a>";
				}else if(index>0&&index<($('#listTable').bootstrapTable('getData').length-1)){
					return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionUpdate'>onclick='upColumnRegionSort(\""+row.fid+"\","+data[index-1].orderSort+",\""+data[index-1].fid+"\","+row.orderSort+");'</customtag:authtag>><span>上移</span></a>"
					+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionUpdate'>onclick='upColumnRegionSort(\""+data[index+1].fid+"\","+row.orderSort+",\""+row.fid+"\","+data[index+1].orderSort+");'</customtag:authtag>><span>下移</span></a>";
				}else{
					return "<a href='javascript:;' <customtag:authtag authUrl='cityFile/columnRegionUpdate'>onclick='upColumnRegionSort(\""+row.fid+"\","+data[index-1].orderSort+",\""+data[index-1].fid+"\","+row.orderSort+");'</customtag:authtag>><span>上移</span></a>"
				}
			}
		}
		
		function upColumnRegionSort(upFid,upSort,downFid,downSort){
			$.ajax({
	            type: "POST",
	            url: "cityFile/upColumnRegionOrderSort",
	            dataType:"json",
	            async: false,
	            data: {"upFid":upFid,"downFid":downFid,"upSort":upSort,"downSort":downSort},
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
