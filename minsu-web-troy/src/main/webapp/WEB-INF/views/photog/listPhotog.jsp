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
<link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">

<style>
    .textarea-desc-color{position:absolute;right:20px; bottom:6px;color:cc3366;font-size:6px}
</style>
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
									<label class="col-sm-1 control-label mtop">摄影师姓名:</label>
									<div class="col-sm-2">
										<input id="realName" name="realName" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">摄影师电话:</label>
									<div class="col-sm-2">
										<input id="mobile" name="mobile" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">城市:</label>
									<div class="col-sm-2">
										<select class="form-control m-b" id="cityCode">
		                                    <option value="">请选择</option>
		                                    <c:forEach items="${cityList }" var="obj">
			                                    <option value="${obj.code }">${obj.name }</option>
		                                    </c:forEach>
	                                    </select>
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
						<customtag:authtag authUrl="photog/addPhotog">
						    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal"><i class="fa fa-plus"></i>&nbsp;添加</button>
						</customtag:authtag>
						<customtag:authtag authUrl="photog/editPhotog">
						    <button class="btn btn-info" type="button" onclick="toEditPhotog();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
						</customtag:authtag>
						<customtag:authtag authUrl="photog/delPhotog">
						    <button class="btn btn-warning" type="button" onclick="delPhotog();"><i class="fa fa-times"></i>&nbsp;删除</button>
						</customtag:authtag>
						<div class="example-wrap">
							<div class="example">
								<table id="listTable" class="table table-bordered"  
								data-click-to-select="true"
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
			                    data-height="496"
			                    data-classes="table table-hover table-condensed"
			                    data-url="photog/showPhotog">                    
			                    <thead>
			                        <tr>
			                        	<th data-field="photographerUid" data-visible="false"></th>
			                        	<th data-field="id" data-width="2%" data-radio="true"></th>
			                            <th data-field="realName" data-width="14%" data-align="center">姓名</th>
			                            <th data-field="nickName" data-width="14%" data-align="center">昵称</th>
			                            <th data-field="mobile" data-width="14%" data-align="center">手机</th>
			                            <th data-field="cityName" data-width="14%" data-align="center">城市名称</th>
			                            <th data-field="photographerStartTime" data-width="14%" data-align="center" data-formatter="formateDate">参加摄影工作时间</th>
			                            <th data-field="photographerGrade" data-width="14%" data-align="center">摄影师等级</th>
			                            <th data-field="photographerSource" data-width="14%" data-align="center" data-formatter="formateSource">摄影师</th>
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
	
	<!-- 添加 添加摄影师  弹出框 -->
	<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" style="width:50%">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">添加摄影师</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="addForm" class="form-horizontal m-t" >
					                   <div class="row">
					                       <label class="col-sm-2 control-label">姓名：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" name="base.realName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                       <label class="col-sm-2 control-label">昵称：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" name="base.nickName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">手机号码：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" name="base.mobile">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                       <label class="col-sm-2 control-label">城市:</label>
						                   <div class="col-sm-4">
					                           <input type="hidden" name="base.cityName">
						                       <select class="form-control m-b" name="base.cityCode" required>
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${cityList }" var="obj">
					                                   <option value="${obj.code }">${obj.name }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
					                   <div class="row">
				                   		   <label class="col-sm-2 control-label">参加工作时间：</label>
				                           <div class="col-sm-4">
				                               <input id="add_photographerStartTime"  value=""  name="base.photographerStartTime" class="laydate-icon form-control layer-date">
				                           </div>
				                   		   <label class="col-sm-2 control-label">邮箱：</label>
				                           <div class="col-sm-4">
				                               <input class="form-control" type="text" name="base.email">
					                           <span class="help-block m-b-none"></span>
				                           </div>
			                           </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">性别:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="base.sex">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${sexMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">摄影师等级:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="base.photographerGrade">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogGradeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">摄影师来源:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="base.photographerSource">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogSourceMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">摄影师状态:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="base.photographerStatu" required>
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogStatuMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">证件类型:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="ext.idType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${idTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">证件号码:</label>
						                   <div class="col-sm-4">
						                   	   <input class="form-control" type="text" name="ext.idNo">
					                           <span class="help-block m-b-none"></span>
						                   </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">居住地址:</label>
						                   <div class="col-sm-4">
						                   	   <input class="form-control" type="text" name="ext.resideAddr">
					                           <span class="help-block m-b-none"></span>
						                   </div>
					                       <label class="col-sm-2 control-label">工作类型:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" name="ext.jobType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${jobTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
				                       <div class="row">
				                           <label class="col-sm-2 control-label">个人介绍</label>
					                       <div class="col-sm-10">
					                           <textarea rows="3" name="ext.photographyIntroduce" class="form-control m-b" onkeydown="calNum(this)" onkeyup="calNum(this)" onchange="calNum(this)"></textarea>
					                       	   <p class="textarea-desc-color">字数：<span>0</span><span>/1000</span></p>
					                       </div>
				                       </div>
					                   <!-- 用于 将表单缓存清空 -->
	                                   <input type="reset" style="display:none;" />
					               </form>
				               </div>
				           </div>
			           </div>
		           </div>
	         </div>
	     </div>
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	         <button type="button" id="addPhotog" class="btn btn-primary">保存</button>
	      </div>
	     </div>
	   </div>
	</div>
	
	<!-- 修改 摄影师  弹出框 -->
	<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" style="width:50%">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">修改摄影师</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="editForm" class="form-horizontal m-t" >
			                           <input type="hidden" id="base_photographerUid" name="base.photographerUid">
					                   <div class="row">
					                       <label class="col-sm-2 control-label">姓名：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" id="base_realName" name="base.realName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                       <label class="col-sm-2 control-label">昵称：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" id="base_nickName" name="base.nickName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">手机号码：</label>
					                       <div class="col-sm-4">
					                           <input class="form-control" type="text" id="base_mobile" name="base.mobile">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                       <label class="col-sm-2 control-label">城市:</label>
						                   <div class="col-sm-4">
					                           <input type="hidden" id="base_cityName" name="base.cityName">
						                       <select class="form-control m-b" id="base_cityCode" name="base.cityCode" required>
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${cityList }" var="obj">
					                                   <option value="${obj.code }">${obj.name }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
					                   <div class="row">
				                   		   <label class="col-sm-2 control-label">参加工作时间：</label>
				                           <div class="col-sm-4">
				                               <input id="base_photographerStartTime"  value=""  name="base.photographerStartTime" class="laydate-icon form-control layer-date">
				                           </div>
				                   		   <label class="col-sm-2 control-label">邮箱：</label>
				                           <div class="col-sm-4">
				                               <input class="form-control" type="text" id="base_email" name="base.email">
					                           <span class="help-block m-b-none"></span>
				                           </div>
			                           </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">性别:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="base_sex" name="base.sex">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${sexMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">摄影师等级:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="base_photographerGrade" name="base.photographerGrade">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogGradeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">摄影师来源:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="base_photographerSource" name="base.photographerSource">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogSourceMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">摄影师状态:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="base_photographerStatu" name="base.photographerStatu" required>
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${photogStatuMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
		                               <input type="hidden" id="ext_photographerUid" name="ext.photographerUid">
					                   <div class="row">
					                       <label class="col-sm-2 control-label">证件类型:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="ext_idType" name="ext.idType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${idTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                       <label class="col-sm-2 control-label">证件号码:</label>
						                   <div class="col-sm-4">
						                   	   <input class="form-control" type="text" id="ext_idNo" name="ext.idNo">
					                           <span class="help-block m-b-none"></span>
						                   </div>
					                   </div>
					                   <div class="row">
					                       <label class="col-sm-2 control-label">居住地址:</label>
						                   <div class="col-sm-4">
						                   	   <input class="form-control" type="text" id="ext_resideAddr" name="ext.resideAddr">
					                           <span class="help-block m-b-none"></span>
						                   </div>
					                       <label class="col-sm-2 control-label">工作类型:</label>
						                   <div class="col-sm-4">
						                       <select class="form-control m-b" id="ext_jobType" name="ext.jobType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${jobTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
		                                       </select>
						                   </div>
					                   </div>
				                       <div class="row">
				                           <label class="col-sm-2 control-label">个人介绍</label>
					                       <div class="col-sm-10" style="position: relative;">
					                           <textarea rows="3" id="ext_photographyIntroduce" name="ext.photographyIntroduce" class="form-control m-b" onkeydown="calNum(this)" onkeyup="calNum(this)" onchange="calNum(this)"></textarea>
					                           <p class="textarea-desc-color">字数：<span>0</span><span>/1000</span></p>
					                       </div>
				                       </div>
				                       <div class="hr-line-dashed"></div>
				                       <div class="row" style="margin-top:15px;">
				                       	   <input type="hidden" id="picType">
										   <div class="col-sm-2 text-right">
											   <label class="control-label" style="height: 35px; line-height: 35px;">头像:</label>
										   </div>
										   <div class="col-sm-9">
											   <div class="lightBoxGallery">
											       <a href="${staticResourceUrl}/img/default.png" id="headPic" data-gallery="blueimp-gallery">
													   <img style="height: 90px; width: 90px;" alt="" src="${staticResourceUrl}/img/default.png">
												   </a>
											   </div>
											   <div style="width: 100px; text-align: center; margin-top: 5px;">
												   <customtag:authtag authUrl="photog/delPhotogPic" >
													   <button type="button" class="btn btn-success btn-sm" onclick="delPhotogPic('3')">删除</button>
												   </customtag:authtag>
												   <customtag:authtag authUrl="photog/uploadPhotogPic" >
													   <button type="button" class="btn btn-success btn-sm" onclick="showUploadModal('3');">上传</button>
												   </customtag:authtag>
											   </div>
										   </div>
									   </div>
					                   <!-- 用于 将表单缓存清空 -->
	                                   <input type="reset" style="display:none;" />
					               </form>
				               </div>
				           </div>
			          </div>
		         </div>
	         </div>
	     </div>
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	         <button type="button" id="editPhotog" class="btn btn-primary">保存</button>
	      </div>
	     </div>
	   </div>
	</div>
	
	<!--上传弹出框 -->
	<div class="modal inmodal fade" id="myModal" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title">图片上传</h4>
				</div>
				<div class="modal-body">
					<div class="page-container">
						<div id="uploader" class="wu-example">
							<div class="queueList">
								<div id="dndArea" class="placeholder">
									<div id="filePicker"></div>
								</div>
							</div>
							<div class="statusBar" style="display: none;">
								<div class="progress">
									<span class="text">0%</span> <span class="percentage"></span>
								</div>
								<div class="info"></div>
								<div class="btns">
									<div id="filePicker2"></div>
									<div class="uploadBtn">开始上传</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<input type="hidden" id="picBaseAddrMona" value="${picBaseAddrMona }" />
	<input type="hidden" id="picSize" value="${picSize }" />

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>

	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- 图片上传 -->
	<script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/photogUploader.js?${VERSION}"></script>


	<!-- Page-Level Scripts -->
	<script>
		// 添加全局站点信息
		var BASE_URL = 'js/plugins/webuploader';
		var UPLOAD_BASE_URL = '${basePath}';
		var DEFAULT_PIC = "${staticResourceUrl}/img/default.png";
		
		$(function(){
			//外部js调用
	        laydate({
	            elem: '#add_photographerStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
	        laydate({
	            elem: '#base_photographerStartTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});
		
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				realName : $.trim($('#realName').val()),
				mobile : $.trim($('#mobile').val()),
				cityCode : $('#cityCode option:selected').val(),
				isDel : 0
			};
		}

		function query() {
			$('#listTable').bootstrapTable('selectPage', 1);
		}

		// 格式化时间
		function formateDate(value, row, index) {
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd");
			} else {
				return "";
			}
		}

		// 摄影师来源
		function formateSource(value, row, index){
			var photogSourceJson = ${photogSourceJson };
			var result = '';
			$.each(photogSourceJson,function(i,n){
				if(value == i){
					result = n;
					return false;
				}
			});
			return result;
		}
		
		// 增加摄影师校验
		$("#addForm").validate({
            rules: {
            	"base.realName": {
                    required: true,
                    maxlength: 50
                },
            	"base.nickName": {
                    maxlength: 50
                },
                "base.mobile": {
                    required: true,
                    isMobile: true,
                },
                "base.email": {
                	email: true,
                    maxlength: 50
                },
            	"ext.idNo": {
                    maxlength: 50
                },
            	"ext.resideAddr": {
                    maxlength: 256
                },
            	"ext.photographyIntroduce": {
                    maxlength: 1000
                }
            }
        });
		
		// 新增摄影师
		$("#addPhotog").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#addForm").valid()){
				$.ajax({
		            type: "post",
		            url: "photog/addPhotog",
		            dataType:"json",
		            data: {"jsonStr":toJsonStr($("#addForm").serializeArray())},
		            success: function (result) {
		            	if(result.code === 0){
				           	$('#addModal').modal('hide')
				           	$("input[type=reset]").trigger("click");
				           	clearCalNum();
				           	$('#listTable').bootstrapTable('refresh');
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			}
			$(this).removeAttr("disable");
		})
 		
		// 修改摄影师
		function toEditPhotog() {
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			$.ajax({
	            type: "post",
	            url: "photog/findPhotog",
	            dataType:"json",
	            data: {
	            	"photographerUid" : rows[0].photographerUid
	            },
	            success: function (result) {
	            	if(result.code === 0){
	            		$("#base_photographerUid").val(result.data.obj.photographerUid);
	            		$("#base_realName").val(result.data.obj.realName);
	            		$("#base_nickName").val(result.data.obj.nickName);
	            		$("#base_mobile").val(result.data.obj.mobile);
	            		$("#base_cityName").val(result.data.obj.cityName);
	            		$("#base_cityCode").val(result.data.obj.cityCode);
	            		$("#base_photographerStartTime").val(formateDate(result.data.obj.photographerStartTime));
	            		$("#base_email").val(result.data.obj.email);
	            		$("#base_sex").val(result.data.obj.sex);
	            		$("#base_photographerGrade").val(result.data.obj.photographerGrade);
	            		$("#base_photographerSource").val(result.data.obj.photographerSource);
	            		$("#base_photographerStatu").val(result.data.obj.photographerStatu);
	            		$("#ext_photographerUid").val(result.data.obj.ext.photographerUid);
	            		$("#ext_idType").val(result.data.obj.ext.idType);
	            		$("#ext_idNo").val(result.data.obj.ext.idNo);
	            		$("#ext_resideAddr").val(result.data.obj.ext.resideAddr);
	            		$("#ext_jobType").val(result.data.obj.ext.jobType);
	            		var _photographyIntroduce = $("#ext_photographyIntroduce");
	            		_photographyIntroduce.val(result.data.obj.ext.photographyIntroduce);
	            		// 计算textarea数量
	            		calNum(_photographyIntroduce);
	            		handlePic(result.data.obj.picList);
					}
		           	$('#editModal').modal('show');
	            },
	            error: function(result) {
		           	$('#editModal').modal('show');
	            }
	      });
		}
		
		//计算数量
		function calNum(obj) {
			var num = $(obj).val().length;
			$(obj).next().children().filter("span").first().text(num);
		}
		
		//清空计算数量
		function clearCalNum() {
			$("textarea").each(function(){
				$(this).next().children().filter("span").first().text(0);
			});
		}
		
		//处理图片
		function handlePic(data) {
			var picBaseAddrMona = $("#picBaseAddrMona").val();
	    	var picSize = $("#picSize").val();
			$.each(data, function(i, n) {
				if(!n.picServerUuid){
					return true;
				}
				var picurl = "";
				try {
					picurl = picBaseAddrMona + n.picBaseUrl + n.picSuffix + picSize + n.picSuffix.toLowerCase();
				} catch (e) {
					picurl = DEFAULT_PIC;
				}
				if (n.picType == "3") {
					$("#headPic").attr("href", picurl);
					$("#headPic img").attr("src", picurl);
				}
			});
		}
		
		// 修改摄影师校验
		$("#editForm").validate({
			rules: {
            	"base.realName": {
                    required: true,
                    maxlength: 50
                },
            	"base.nickName": {
                    maxlength: 50
                },
                "base.mobile": {
                    required: true,
                    isMobile: true,
                },
                "base.email": {
                	email: true,
                    maxlength: 50
                },
            	"ext.idNo": {
                    maxlength: 50
                },
            	"ext.resideAddr": {
                    maxlength: 256
                },
            	"ext.photographyIntroduce": {
                    maxlength: 1000
                }
            }
        });
		
		// 修改摄影师
		$("#editPhotog").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#editForm").valid()){
				$.ajax({
		            type: "post",
		            url: "photog/editPhotog",
		            dataType:"json",
		            data: {"jsonStr":toJsonStr($("#editForm").serializeArray())},
		            success: function (result) {
		            	if(result.code === 0){
				           	$('#editModal').modal('hide')
				           	$("input[type=reset]").trigger("click");
				           	clearCalNum();
				           	$('#listTable').bootstrapTable('refresh');
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			}
			$(this).removeAttr("disable");
		})
		
		// 逻辑删除摄影师
		function delPhotog() {
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			layer.confirm("确认删除吗?", {icon: 5,title:'提示'},function(index){
				$.ajax({
		            type: "post",
		            url: "photog/delPhotog",
		            dataType:"json",
		            data: {
		            	"photographerUid" : rows[0].photographerUid
		            },
		            success: function (result) {
		            	if(result.code === 0){
		            		$('#listTable').bootstrapTable('refresh');
						} else {
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			  layer.close(index);
			});
		}
		
		$("[name='base.cityCode']").change(function(){
			var text = $(this).children().filter("option:selected").text();
			$(this).prev().val(text);
		});
		
		// 删除摄影师头像图片
		function delPhotogPic(type) {
			var photographerUid = $("#base_photographerUid").val();
			$.post("photog/delPhotogPic", {
				photographerUid : photographerUid,
				picType : type
			}, function(data) {
				if (data.code == 0) {
					$("#headPic").attr("href", DEFAULT_PIC);
					$("#headPic img").attr("src", DEFAULT_PIC);
					layer.alert("删除成功", {icon: 5,time: 2000, title:'提示'});
				}else{
					layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
				}
			}, "json");
		}
		
		//上传参数
		function showUploadModal(type) {
			$("#picType").val(type);
			createUploader();
			$('#myModal').modal('show');
		}

		function toJsonStr(array){
			var base = {};
			var ext = {};
			$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($.trim(value).length != 0 && key.indexOf("base.") != -1){
					key = key.replace("base.","");
					base[key] = value;
				}
				if($.trim(value).length != 0 && key.indexOf("ext.") != -1){
					key = key.replace("ext.","");
					ext[key] = value;
				}
			});
			var json = {};
			json.base = base;
			json.ext = ext;
			console.log(JSON.stringify(json));
			return JSON.stringify(json);
		}	
	</script>
</body>
</html>
