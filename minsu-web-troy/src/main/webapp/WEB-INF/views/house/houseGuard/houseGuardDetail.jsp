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
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style>
        .lightBoxGallery img {margin: 5px;width: 160px;}
        label{font-weight:500;margin-top:8px;}
    </style>
</head>
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				          <div class="form-group">
	                           <div class="hr-line-dashed"></div>
		                       <h2 class="title-font">房源基本情况</h2>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源编号:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseSn" name="houseSn" value="${houseGuardVo.houseSn}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseName" name="houseName" value="${houseGuardVo.houseName}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房源地址:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="houseAddr" name="houseAddr" value="${houseGuardVo.houseAddr}" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label">房东名称:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <input id="landlordName" name="landlordName" value="${houseGuardVo.landlordName }" readonly="readonly" type="text" class="form-control m-b">
	                               </div>
		                       </div>
		                       
		                       <%-- <div class="hr-line-dashed"></div>
				          	   <h2 class="title-font">地推管家选择</h2>
	                           <div class="row">
                                   <input type="hidden" class="form-control m-b" name="empPushFid"  id="empPushFid">
	                               <label class="col-sm-1 control-label">管家姓名</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empPushName }" name="empPushName"  id="empPushName" readonly="readonly" >
		                               </div>
	                               </div>  
	                               <label class="col-sm-1 control-label">管家系统号</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empPushCode }" name="empPushCode"  id="empPushCode" readonly="readonly" >
		                               </div>
	                               </div>  
	                               <label class="col-sm-1 control-label">管家手机</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empPushMobile }" name="empPushMobile"  id="empPushMobile" readonly="readonly" >
		                               </div>
	                               </div>  
	                           </div> --%>
		                       
		                       <div class="hr-line-dashed"></div>
				          	   <h2 class="title-font">选择运营专员</h2>
	                           <div class="row">
                                   <input type="hidden" class="form-control m-b" name="empGuardFid"  id="empGuardFid">
	                               <label class="col-sm-1 control-label">专员姓名</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empGuardName }" name="empGuardName"  id="empGuardName" readonly="readonly" >
		                               </div>
	                               </div>  
	                               <label class="col-sm-1 control-label">专员系统号</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empGuardCode }" name="empGuardCode"  id="empGuardCode" readonly="readonly" >
		                               </div>
	                               </div>  
	                               <label class="col-sm-1 control-label">专员手机号</label>
	                               <div class="col-sm-2">
		                               <div class="input-group">
		                                   <input type="text" class="form-control m-b" value="${houseGuardVo.empGuardMobile }" name="empGuardMobile"  id="empGuardMobile" readonly="readonly" >
		                               </div>
	                               </div>  
	                           </div>
	                           
	                           <br/>
							   <div class="form-group">
								   <div class="col-sm-4 col-sm-offset-5">
									   <customtag:authtag authUrl="house/houseGuard/showHouseGuardLogList">
									       <button type="button" class="btn btn-white" data-toggle="modal" data-target="#hgLogModel">运营专员历史记录</button>
									   </customtag:authtag>
								   </div>
							   </div>
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	   </div>
	   
	   <!-- 运营专员历史记录弹出框 -->
	  <div class="modal inmodal fade" id="hgLogModel" tabindex="-1" role="dialog"  aria-hidden="true">
	         <div class="modal-dialog modal-lg">
	             <div class="modal-content">
	                 <div class="modal-header">
	                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	                     <h4 class="modal-title">运营专员历史记录</h4>
	                 </div>
	                 <div class="ibox-content">
		                 <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <div class="example-wrap">
		                            <div class="example">
					                    <table class="table table-bordered" id="listTable"
											data-click-to-select="true" 
											data-toggle="table"
											data-side-pagination="server" 
											data-pagination="true"
											data-page-list="[10,20,50]" 
											data-page-size="10" 
											data-pagination-first-text="首页"
											data-pagination-pre-text="上一页" 
											data-pagination-next-text="下一页"
											data-pagination-last-text="末页"
											data-content-type="application/x-www-form-urlencoded"
											data-query-params="paginationParam" 
											data-method="post"
											data-height="300"
											data-single-select="true" 
											data-url="house/houseGuard/showHouseGuardLogList">
											<thead>
												 <tr>
									                  <th data-field="oldGuardCode" data-width="14%" data-align="center">专员系统号</th>
									                  <th data-field="oldGuardName" data-width="14%" data-align="center">运营专员</th>
									                  <th data-field="oldGuardMobile" data-width="14%" data-align="center">专员手机号</th>
									                  <!-- <th data-field="oldPushCode" data-width="14%" data-align="center">系统号</th>
									                  <th data-field="oldPushName" data-width="14%" data-align="center">地推管家</th>
									                  <th data-field="oldPushMobile" data-width="14%" data-align="center" data-formatter="formateMobile">管家手机</th> -->
									                  <th data-field="createDate" data-width="16%" data-align="center" data-formatter="formateDate">修改时间</th>
									             </tr>
											</thead>
										</table>            
						            </div>
						        </div>
						    </div>
						 </div>
				     </div>      
	                 <div class="modal-footer">
	                     <button type="button" class="btn btn-primary"  data-dismiss="modal">关闭</button>
	                 </div>
	             </div>
	         </div>
	     </div>
	   
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	   
	   <script type="text/javascript">
		   function paginationParam(params) {
			    return {
			    	limit:params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        houseGuardFid:'${houseGuardVo.fid}',
		    	};
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
		   
		   function formateMobile(value, row, index) {
			   if (value != null) {
				   return value;
			   } else {
				   return "";
			   }
		   }
		   
	   </script>
	</body>
</html>
