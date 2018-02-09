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
           <div class="col-sm-12">
               <div class="ibox float-e-margins">
			  	   <div class="ibox-content">
			           	<form class="form-group">
	                       <div class="row">
	                           <div class="col-sm-1">
			                   	   <label class="control-label">国家:</label>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b m-b" id="nationCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">省份:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b " id="provinceCode">
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label">城市:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" id="cityCode">
                                   </select>
                               </div>
	                       </div>
	                       <div class="row">
			                   <label class="col-sm-1 control-label mtop">类型:</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" id="regionType">
	                                   <option value="">请选择</option>
	                                   <c:forEach items="${hotRegionEnumList }" var="regionType">
	                                       <option value="${regionType.key }">${regionType.text }</option>
	                                   </c:forEach>
                                   </select>
                               </div>
			                   <label class="col-sm-1 control-label mtop">名称:</label>
	                           <div class="col-sm-2">
                                   <input id="regionName" name="regionName" type="text" class="form-control m-b">
                               </div>
			                   <label class="col-sm-1 control-label mtop">半径(KM):</label>
	                           <div class="col-sm-2">
                                   <input id="radii" name="radii" type="text" class="form-control m-b">
                               </div>
                               <div class="col-sm-1">
			                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                   </div>
	                       </div>
		               </form>
		           </div>
		           <div class="ibox float-e-margins">
	                   <div class="ibox-content">
			               <div class="row row-lg">
			                   <div class="col-sm-12">
			                       <!-- Example Pagination -->
			                       <customtag:authtag authUrl="config/hotRegion/addHotRegion">
			                       	   <button class="btn btn-primary " type="button" onclick="toAddHotRegion();"><i class="fa fa-plus"></i>&nbsp;添加</button>
			                       </customtag:authtag>
			                       <customtag:authtag authUrl="config/hotRegion/editHotRegion">
			                           <button class="btn btn-info " type="button" onclick="toEditHotRegion();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
			                       </customtag:authtag>
			                       <div class="example-wrap">
			                           <div class="example">
			                           		<table id="listTable" class="table table-hover table-bordered"  data-click-to-select="true"
												data-toggle="table" data-side-pagination="server"
												data-pagination="true" data-page-list="[10,20,50]"
												data-pagination="true" data-page-size="10"
												data-pagination-first-text="首页" data-pagination-pre-text="上一页"
												data-pagination-next-text="下一页" data-pagination-last-text="末页"
												data-content-type="application/x-www-form-urlencoded"
												data-query-params="paginationParam" data-method="get"
												data-single-select="true"
												data-height="500" data-url="config/hotRegion/showHotRegions">
												<thead>
													<tr>
						                            <th data-field="fid" data-visible="false"></th>
						                            <th data-field="id" data-width="5%" data-radio="true"></th>
						                            <th data-field="regionType" data-width="15%" data-align="center" data-formatter="formateRegionType">类型</th>
						                            <th data-field="regionName" data-width="15%" data-align="center">名称</th>
						                            <th data-field="hot" data-width="15%" data-align="center">热度</th>
						                            <th data-field="radii" data-width="15%" data-align="center">半径</th>
						                            <th data-field="regionStatus" data-width="15%" data-align="center" data-formatter="formateRegionStatus">状态</th>
						                            <th data-field="location" data-width="15%" data-align="center" data-formatter="formateLocation">坐标</th>
						                            <th data-field="handle" data-width="15%" data-align="center" data-formatter="formateOperate">操作</th>
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
           </div>
	   </div>
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	    <!-- 自定义js -->
	    <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
		<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
		<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
		<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
	   		$(function(){
	   			// 多级联动
	   			var areaJson = ${cityTreeList};
	   			var options = {
   					geoJson	: areaJson
	   			};
	   			geoCascade(options);
	   		});
	   
		    function paginationParam(params) {
			    return {
			        limit: params.limit,
			        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			        nationCode:$('#nationCode option:selected').val(),
			        provinceCode:$('#provinceCode option:selected').val(),
			        cityCode:$('#cityCode option:selected').val(),
			        regionType:$('#regionType option:selected').val(),
			        regionName:$.trim($('#regionName').val()),
			        radii:$.trim($('#radii').val())
		    	};
			}
		    
		    //查询
		    function query(){
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
			
			//类型
			function formateRegionType(value, row, index){
				var regionTypeJson = ${regionTypeJson };
				var result = '';
				$.each(regionTypeJson,function(i,n){
					if(value == n.key){
						result = n.text;
						return false;
					}
				});
				return result;
			}
			
			//状态
			function formateRegionStatus(value, row, index){
				if(value == '1'){
					return "已启用";
				}else{
					return "已停用";
				}
			}
			
			//经纬度
			function formateLocation(value, row, index){
				var longitude = row.longitude;
				var latitude = row.latitude;
				return longitude + "," + latitude;
			}
		    
			// 操作列
			function formateOperate(value, row, index){
				if (row.regionStatus == '1') {
					return "<button type='button'  class='btn btn-primary btn-sm' onclick='editHotRegionStatus(\""+ row.fid + "\",\""+ row.regionStatus + "\");' ><span > 停用</span></button>";
				} else {
					return "<button type='button'  class='btn btn-red btn-sm' onclick='editHotRegionStatus(\""+ row.fid + "\",\""+ row.regionStatus + "\");' ><span > 启用</span></button>";
				}
			}
		    
		    //逻辑删除热门区域
		    function editHotRegionStatus(hotRegionFid, operateFlag){
		    	var mes = operateFlag == 1 ? '确定要停用吗？' : '确定要启用吗';// 提示信息
		    	var iconNum = operateFlag == 1 ? 5 : 6; //显示icon层设置 6：笑脸  5：沮丧
				layer.confirm(mes, {
					icon : iconNum,
					title : '提示'
				}, function(index) {
					$.ajax({
						url : "config/hotRegion/editHotRegionStatus",
						data : {
							"hotRegionFid" : hotRegionFid
						},
						dataType : "json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								$('#listTable').bootstrapTable(
										'refresh');
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

			function toAddHotRegion() {
				$.openNewTab(new Date().getTime(),"config/hotRegion/addHotRegion", "新增热门区域");
			}

			function toEditHotRegion() {
				var rows = $('#listTable').bootstrapTable('getSelections');
				if (rows.length == 0) {
					layer.alert("请选择一条记录进行操作", {
						icon : 6,
						time : 2000,
						title : '提示'
					});
					return;
				}
				$.openNewTab(new Date().getTime(),"config/hotRegion/editHotRegion?hotRegionFid="+rows[0].fid, "修改热门区域");
			}
			</script>
	</body>
</html>
