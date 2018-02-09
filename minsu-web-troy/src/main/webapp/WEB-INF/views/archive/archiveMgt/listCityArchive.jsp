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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	
	<style type="text/css">
		.line-width{
			width: 200px;;
		}
		/* td{
			white-space: nowrap;text-overflow: ellipsis;overflow: hidden;
		} */
	</style>
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
						<div id="tab-2" class="tab-pane">
							<div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
						                    <div class="row">
					                            <label class="col-sm-1 control-label mtop">城市名称:</label>
						                        <div class="col-sm-2">
													<input id="showName" name="showName" type="text" class="form-control m-b">
					                            </div>
					                            <label class="col-sm-1 control-label mtop">是否有档案:</label>
						                        <div class="col-sm-2">
					                                <select class="form-control m-b" id="isFile">
					                                	 <option value="">请选择</option>
														 <option value="0">否</option>
														 <option value="1">是</option>
					                                </select>
					                            </div>
												<label class="col-sm-1 control-label mtop">操作人:</label>
												<div class="col-sm-2">
													<input id="fullName" name="fullName" type="text" class="form-control m-b">
												</div>
												&nbsp;&nbsp;
												<!-- 查询开始-->
												<div class="col-sm-1">
													<button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
												</div>
						                    </div>

					                    </div>
					                </div>
					            </div>
					        </div>
					        <div class="ibox float-e-margins">
					            <div class="ibox-content">
					                <div class="row row-lg">
					                    <div class="col-sm-14">
					                        <div class="example-wrap">
					                            <div class="example">
					                                <table id="listTableS"  class="table table-bordered"  data-click-to-select="true"
								                    data-toggle="table"
								                    data-side-pagination="server"
								                    data-pagination="true"
								                    data-striped="true"
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
								                    data-classes="table table-hover table-condensed"
								                    data-height="496"
								                    data-url="archive/archiveMgt/showArchivesMsg">
								                    <thead>
								                        <tr>
								                        	<%--<th data-field="roomSn" data-width="100px" data-align="center" data-formatter="formateRoomSn"></th>
								                        	<th data-field="fid" data-width="200px" data-align="center"></th>--%>
								                            <!-- <th data-field="showName" data-width="200px" data-align="center">城市名称</th> -->
								                            <th data-field="showName" data-width="200px" data-align="center" data-formatter="formateCityDetail">城市名称</th>
								                            <th data-field="isFile" data-width="200px" data-align="center" data-formatter="formateIsFile">是否有档案</th>
															<th data-field="lastModifyDate" data-width="100px" data-align="center" data-formatter="formateDate">修改日期</th>
								                            <th data-field="fullName" data-width="100px" data-align="center">创建人</th>
								                            <th data-field="operator" data-width="100px" data-align="center">操作人</th>
								                            <th data-field="operate" data-width="100px" data-align="center" data-formatter="formateOperate">操作</th>
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
	   </div>
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	  <%--   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script> --%>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
	   <script>
		   function isNullOrBlank(obj){
			   return obj == undefined || obj == null || $.trim(obj).length == 0;
		   }
		   function paginationParam(params) {

			   return {
				   limit: params.limit,
				   page:$('#listTableS').bootstrapTable('getOptions').pageNumber,
				   showName:$.trim($('#showName').val()),
				   isFile:$('#isFile option:selected').val(),
				   fullName:$.trim($('#fullName').val()),
			   };
		   }
		   function query(){
			   console.log($("#houseQualityGradeZ").val());
			   $('#listTableS').bootstrapTable('selectPage', 1);
		   }
		   //查看城市档案详情
		   function formateCityDetail(value, row, index){
			   return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='archive/archiveMgt/cityArchiveDetail'>onclick='cityArchiveDetail(\""+row.fid+"\",\""+row.cityCode+"\",\""+row.showName+"\")'</customtag:authtag>>"+value+"</a>";
		   }
		   // 是否有城市档案
		   function formateIsFile(value, row, index){
			   if(value == '0'){
				   return "否";
			   }else{
				   return "是";
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
		   //操作判断
		   function formateOperate(value, row, index){
			   /* if(row.isFile == '0'){
				   return "<a href='javascript:;' onclick='addCityArchive(\""+row.fid+"\",\""+row.cityCode+"\",\""+row.showName+"\")'>"+"增加"+"</a>";
			   }else{
				   return "<a href='javascript:;' onclick='addCityArchive(\""+row.fid+"\",\""+row.cityCode+"\",\""+row.showName+"\")'>"+"修改"+"</a>";
			   } */
			   if(row.isFile == '0'){
				   return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='archive/archiveMgt/addCityArchive'>onclick='addCityArchive(\""+row.fid+"\",\""+row.cityCode+"\",\""+row.showName+"\")'</customtag:authtag>>"+"增加"+"</a>";
			   }else{
				   return "<a href='javascript:;' class='oneline line-width' <customtag:authtag authUrl='archive/archiveMgt/addCityArchive'>onclick='addCityArchive(\""+row.fid+"\",\""+row.cityCode+"\",\""+row.showName+"\")'</customtag:authtag>>"+"修改"+"</a>";
			   }
		   }
		   function addCityArchive(fid,cityCode,showName){
			   console.log(cityCode);
			   console.log(showName);
			   if(isNullOrBlank(fid)){
				   $.openNewTab(new Date().getTime(),'archive/archiveMgt/addCityArchive?cityCode='+cityCode+'&showName='+showName+'&isFile=0', "添加城市档案");
			   }else{
				   $.openNewTab(new Date().getTime(),'archive/archiveMgt/addCityArchive?cityCode='+cityCode+'&fid='+fid+'&showName='+showName+'&isFile=0', "添加城市档案");
			   }
		   }
		  function cityArchiveDetail(fid,cityCode,showName){
			  $.openNewTab(new Date().getTime(),'archive/archiveMgt/cityArchiveDetail?cityCode='+cityCode+'&fid='+fid+'&showName='+showName+'&isFile=0', "城市档案详情");
		  }
	   </script>
	</body>
</html>
