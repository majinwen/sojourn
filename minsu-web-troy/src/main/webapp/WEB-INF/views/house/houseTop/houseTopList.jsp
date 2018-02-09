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
	<div class="wrapper wrapper-content animated fadeInRight"  style="position:relative; z-index:999;">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房源名称:</label>
							</div>
							<div class="col-sm-2">
								<input id="houseName" name="houseName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房源编号:</label>
							</div>
							<div class="col-sm-2">
								<input id="houseSn" name="houseSn" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">内容状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="topStatus" id="topStatus">
									  <option value="">请选择</option>
	                           	   	  <option value="0">新建</option>
	                           	   	  <option value="1">上线</option>
	                           	   	  <option value="2">下线</option>
								</select>
							</div>
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
						</div>
					    <div class="row m-b">
					        <div class="col-sm-1 text-right">
		                    <label class="control-label"
									style="height: 35px; line-height: 35px;">内容创建时间：</label>
							</div>		
                           	<div class="col-sm-2">
                               <input id="startDate"  value=""  name="startDate" class="laydate-icon form-control layer-date">
                           	</div>
                           	<div class="col-sm-1 text-right">
                           	<label class="control-label"
									style="height: 35px; line-height: 35px;">到:</label>
							</div>
                            <div class="col-sm-2">
                               <input id="endDate" value="" name="endDate" class="laydate-icon form-control layer-date">
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
								data-single-select="true" data-url="house/houseTop/houseTopListData">
								<thead>
									<tr>
										<th data-field="fid" data-visible="false"></th>
										<th data-field="houseBaseFid" data-visible="false"></th>
										<th data-field="rentWay" data-visible="false"></th>
										<th data-field="index" data-formatter="indexFormatter" data-align="center" data-width="15%" >序号</th>
										<th data-field="houseSn" data-width="10%" data-align="center" >房源编号</th>
										<th data-field="houseName" data-width="25%" data-align="center" >房源名称</th>
										<th data-field="cityCode" data-width="10%" data-align="center" data-formatter="formateCode" >城市</th>
										<th data-field="createDate" data-width="10%" data-align="center" data-formatter="formateDate">内容创建时间</th>
										<th data-field="topStatus" data-width="10%" data-align="center" data-formatter="formateColStatus">内容状态</th>
										<th data-field="empName" data-width="10%" data-align="center" >操作人</th>
										<th data-field="operate" data-width="25%" data-align="center" data-formatter="formateOperate">操作</th>
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
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/minsu/common/jquery.combo.select.js${VERSION}"></script>
	
	
	<script>
		 //
		function columnCityAdd(){
			$.openNewTab("columnCityAdd","cityFile/columnCityAdd", "新增城市专栏");
		}
		 
		//序号
		function indexFormatter(value, row, index) {
            return index+1;  
        }
		 
		//分页查询参数
		function paginationParam(params) {
	    	var startDate  = $.trim($('#startDate').val());
	        if(!isNullOrBlank(startDate)){
	        	startDate = startDate+" 00:00:00";
	        }
	        
	        var endDate =$.trim($('#endDate').val());
	        if(!isNullOrBlank(endDate)){
	        	endDate = endDate+" 23:59:59";
	        }
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				houseName:$.trim($('#houseName').val()),
				houseSn:$.trim($('#houseSn').val()),
				topStatus:$.trim($('#topStatus').val()),
				startDate:startDate,
				endDate:endDate,
				cityCode:$.trim($('#cityCode').val())
			}
		}
	    function isNullOrBlank(obj){
	    	return obj == undefined || obj == null || $.trim(obj).length == 0;
	    }
		//条件查询
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		// 专栏状态
		function formateColStatus(value, row, index){
			if(value==0){
				return "新建";
			}else if(value==1){
				return "上线";
			}else if(value==2){
				return "下线";
			} else {
				return "新建";
			}
		}
		
		//操作
		function formateOperate(value, row, index){
			if(isNullOrBlank(row.fid)){
				 return "<a href='javascript:;' <customtag:authtag authUrl='house/houseTop/topHouseAdd'>onclick='createTopHouseContent(\""+row.houseBaseFid+"\","+row.rentWay+");'</customtag:authtag>><span>创建内容</span></a>";	
			} else if(row.topStatus==0 || row.topStatus==2){
				return "<a href='javascript:;' <customtag:authtag authUrl='house/houseTop/topHouseUpdate'>onclick='updateTopHouse(\""+row.fid+"\");'</customtag:authtag>><span>修改</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' <customtag:authtag authUrl='house/houseTop/upDownTopHouse'>onclick='upDownTopHouse(\""+row.fid+"\");'</customtag:authtag>><span>上线</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;' onclick='sampleTopHouse(\""+row.houseBaseFid+"\","+row.rentWay+");'><span>预览</span></a>"
			} else {
				return "<a href='javascript:;' <customtag:authtag authUrl='house/houseTop/upDownTopHouse'>onclick='upDownTopHouse(\""+row.fid+"\");'</customtag:authtag>><span>下线</span></a>"
				+"&nbsp;&nbsp;<a href='javascript:;'onclick='sampleTopHouse(\""+row.houseBaseFid+"\","+row.rentWay+");'><span>预览</span></a>"
			}
		}
		
		//修改top房源内容
		function updateTopHouse(fid){
			$.openNewTab(new Date().getTime(),'house/houseTop/topHouseUpdate?fid='+fid, "top房源内容更新");
		}
		
		//创建top房源内容
		function createTopHouseContent(houseBaseFid,rentWay){
			$.openNewTab(new Date().getTime(),'house/houseTop/topHouseAdd?houseBaseFid='+houseBaseFid+'&rentWay='+rentWay, "top房源内容添加页");
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

		//上下线top房源内容
		function upDownTopHouse(fid){
			$.ajax({
	            type: "POST",
	            url: "house/houseTop/upDownTopHouse",
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
		
		//预览
		function sampleTopHouse(houseBaseFid,rentWay){
			var sampleUrl='${sample_top_house_url}';
			if(rentWay==0){
				sampleUrl=sampleUrl+"?fid="+houseBaseFid+"&rentWay="+rentWay;
			}
			if(rentWay==1){
				sampleUrl=sampleUrl+"?fid="+houseBaseFid.split(",")[1]+"&rentWay="+rentWay;
			}
			window.open(sampleUrl);
		}
		
		$(function() {
		    $('#cityCode').comboSelect();
		    var start = {  
		            elem: '#startDate', //选择ID为START的input  
		            format: 'YYYY-MM-DD', //自动生成的时间格式  
		            max: '2099-06-16', //最大日期  
		            istime: true, //必须填入时间  
		            istoday: false,  //是否是当天  
		            start: laydate.now(0,"YYYY-MM-DD"),  //设置开始时间为当前时间  
		            choose: function(datas){  
		                 end.min = datas; //开始日选好后，重置结束日的最小日期  
		                 end.start = datas //将结束日的初始值设定为开始日  
		            }  
		        };  
		        var end = {  
		            elem: '#endDate',  
		            format: 'YYYY-MM-DD',  
		            max: '2099-06-16',  
		            istime: true,  
		            istoday: false,  
		            start: laydate.now(0,"YYYY-MM-DD"),  
		            choose: function(datas){  
		                start.max = datas; //结束日选好后，重置开始日的最大日期  
		            }  
		        };  
		        laydate(start);  
		        laydate(end);  
		});
	</script>
</body>
</html>
