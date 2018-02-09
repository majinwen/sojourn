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
<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style type="text/css">
	.modal-lm{min-width:880px;}
	.row{margin:0;} 
</style>
</head>

<body class="gray-bg">
	<!-- 图片显示 -->
	<div id="blueimp-gallery" class="blueimp-gallery">
		<div class="slides"></div>
		<h3 class="title"></h3>
		<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a> <a
			class="play-pause"></a>
		<ol class="indicator"></ol>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_realName" name="realName" type="text" value="${realName }"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">用户名:</label>
							</div>
							<div class="col-sm-2">
								<input id="S_nickName" name="nickName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">手机号:</label>
							</div>
							<div class="col-sm-3">
								<input id="S_customerMobile" name="customerMobile" type="text" value="${customerMobile }"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">角色:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="isLandlord" id="S_isLandlord">
									<option value="1">房东</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">审核状态:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" id="S_auditStatus" name="auditStatus">
									<option value="">全部</option>
									<option value="0" <c:if test="${auditStatus == 0 }">selected</c:if>>未审核</option>
									<option value="2" <c:if test="${auditStatus == 2 }">selected</c:if>>审核未通过</option>
									<option value="3" <c:if test="${auditStatus == 3 }">selected</c:if>>审核通过驳回</option>
								</select>
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">是否存在待审核字段:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="S_isCanShow" id="S_isCanShow">
								    <option value="">全部</option>
									<!-- 这个1，紧紧代表要查询“审核通过后，再次修改审核字段的”房东，只在前台使用，数据库auditstats字段没有对应的值 -->
									<option value="1" >已认证信息修改待审核</option>
								</select>
							</div>
							<customtag:authtag authUrl="customer/listCheckCustomerMsg" ><button class="btn btn-primary" style="margin-left: 77px;" type="button" onclick="CommonUtils.query();">
								<i class="fa fa-search"></i>&nbsp;查询
							</button></customtag:authtag>
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
								data-page-list="[10,20,50]" 
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="post"
								data-height="520"
								data-single-select="true" data-url="customer/customerDataList">
								<thead>
									<tr>
										<th data-field="uid" data-visible="false"></th>
										<th data-field="realName" data-width="13%" data-align="center" 
											data-formatter="customerDetail.nameshow">姓名</th>
										<th data-field="customerMobile" data-width="13%"
											data-align="center">手机号</th>
										<th data-field="nickName" data-width="12%" data-align="center">用户名</th>
										<th data-field="isLandlord" data-width="11%"
											data-align="center" data-formatter="customerDetail.role">角色</th>
										<th data-field="customerJob" data-width="11%"
											data-align="center">职业</th>
										<th data-field="auditStatus" data-width="10%"
											data-align="center"
											data-formatter="customerDetail.auditStatus">审核状态</th>
									    <th data-field="operate" data-width="10%" data-align="center" data-formatter="formateOperate">操作</th>
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
	<!--弹出框 -->
	<div class="modal inmodal fade" id="customerDetailInfoModel"
		tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lm">
			<div class="modal-content">
				<div style="padding:5px 10px 0;">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
				</div>
				
				<div class="ibox-content" style="border:none;padding-bottom:0;">
					<div class="row row-lg">
						<div class="col-sm-12">
							<div class="example-wrap">
								<div class="example">
									<!-- 客户uid -->
									<input type="hidden" id="hideUid" /> <input type="hidden"
										id="customerPicType">
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">姓名:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="realName" readonly="readonly" />
										</div>
                                        <div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">性别:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerSex"
												readonly="readonly" />
										</div>
									</div>
									
									<div class="row">
									    <div class="col-sm-1 text-right ">
											<label class="control-label customerNickName_old" id="old_nickName_label"
												style="height: 35px; line-height: 35px; color: red;" >昵称:</label>
										</div>
										<div class="col-sm-3 customerNickName_old">
											<input class="form-control" id="nickName" readonly="readonly" style="color: red;"/>
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label customerNickName_audit" id="audit_nickName_label"
												style="height: 35px; line-height: 35px;">审核昵称:</label>
												<input id="unAuditNickNameFieldFid" value="" type="hidden"/>
											        
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="unAuditNickName" readonly="readonly"/>
										</div>
										<button type="button" class="btn btn-danger btn-xs customerNickName_old" onclick="rejectField($('#unAuditNickNameFieldFid').val(),$('#unAuditNickName'),$('#nickName'),$('.customerNickName_old'))">驳回</button>
										<button type="button" class="btn btn-danger btn-xs customerNickName_old" onclick="javascript:auditedCustomerInfo(3);">通过</button>
                                    </div>
                                    
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">出生日期:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerBirthday"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">电话:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerMobile"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">邮箱:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="customerEmail"
												readonly="readonly" />
										</div>
									</div>

									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">证件类别:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="idTypeName" readonly="readonly" />
										</div>

										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">证件号码:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="idNo" readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">居住城市:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="cityCode" readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">角色:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="isLandlord"
												readonly="readonly" />
										</div>
										<div class="col-sm-1 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">是否审核:</label>
										</div>
										<div class="col-sm-3">
											<input class="form-control" id="auditStatus"
												readonly="readonly" />
										</div>
									</div>
									<div class="row">
										<div class="customerIntroduce_new">
											<div class="col-sm-1 text-right" >
												<label class="control-label"
													style="height: 35px; line-height: 35px;">个人介绍:</label>
													<input id="customerIntroduceRejectId" value="" type="hidden"/>
													<button type="button" class="btn btn-danger btn-xs customerIntroduce_old" onclick="rejectField($('#customerIntroduceRejectId').val(),$('#introduce'),$('#originIntroduce'),$('.customerIntroduce_old'))">驳回</button>
											        <button type="button" class="btn btn-danger btn-xs customerIntroduce_old" onclick="javascript:auditedCustomerInfo(1);">通过</button>
											        
											
											</div>
											<div class="col-sm-11" style="position: relative;">
												<textarea id="introduce" rows="6" style="width: 100%;" onkeydown="calNum()" onkeyup="calNum()" onchange="calNum()"  class="sensitiv-words-validate"></textarea>
												<p style="position:relative;left:1px;bottom: 1px;height: 20px;line-height: 20px;text-align:right;">字数：<span id="introduceNum">0</span></p>
											</div>
										</div>
										<div class="customerIntroduce_old" >
											<div class="col-sm-1 text-right">
												<label class="control-label"
													style="height: 35px; line-height: 35px;">原版个人介绍:</label>
											</div>
											<div class="col-sm-11" style="position: relative;color: red;" >
											     <input type="hidden" id="originIntroduceValue" >
												<textarea id="originIntroduce" rows="6" style="width: 100%;" onkeydown="calNum()" onkeyup="calNum()" onchange="calNum()" ></textarea>
												<p style="position:relative;left:1px;bottom: 1px;height: 20px;line-height: 20px;text-align:right;">字数：<span id="originIntroduceNum">0</span></p>
											</div>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">身份信息:</label>
										</div>
										<div class="col-sm-10">
											<div class="col-sm-3">
												<div style="width: 100px; text-align: center;">
													<span id="frontPicText">证件正面照</span>
												</div>
												<div style="width: 100%" class="lightBoxGallery">
													<a href="http://minsustatic.d.ziroom.com/mapp/images/default.png" id="frontPic" data-gallery="blueimp-gallery">
														<img style="height: 90px; width: 90px;" alt="" src="http://minsustatic.d.ziroom.com/mapp/images/default.png">
													</a>
												</div>
												<div style="width: 100px; text-align: center; margin-top: 5px;" id="identityZ">
													<customtag:authtag authUrl="customer/delCustomerPic" >
														<button type="button" class="btn btn-success btn-sm" onclick="delCustomerPic('0')">删除</button>
													</customtag:authtag>
													<customtag:authtag authUrl="customer/uploadCustomerPic" >
														<button type="button" class="btn btn-success btn-sm" onclick="showUploadModal('0');">上传</button>
													</customtag:authtag>
												</div>
											</div>
											<div class="col-sm-3">
												<div style="width: 100px; text-align: center;">
													<span id="backPicText">证件反面照</span>
												</div>
												<div style="width: 100%" class="lightBoxGallery">
													<a href="http://minsustatic.d.ziroom.com/mapp/images/default.png" id="backPic" data-gallery="blueimp-gallery">
														<img style="height: 90px; width: 90px;" alt="" src="http://minsustatic.d.ziroom.com/mapp/images/default.png">
													</a>
												</div>
												<div style="width: 100px; text-align: center; margin-top: 5px;" id="identityF">
													<customtag:authtag authUrl="customer/delCustomerPic" >
													<button type="button" class="btn btn-success btn-sm" onclick="delCustomerPic('1')">删除</button>
													</customtag:authtag>
													<customtag:authtag authUrl="customer/uploadCustomerPic" >
														<button type="button" class="btn btn-success btn-sm" onclick="showUploadModal('1');">上传</button>
													</customtag:authtag>
												</div>
											</div>
											<div class="col-sm-3">
												<div style="width: 100px; text-align: center;">
													<span id="holdPicText">手持证件照</span>
												</div>
												<div class="lightBoxGallery">
													<a href="http://minsustatic.d.ziroom.com/mapp/images/default.png" id="holdPic" data-gallery="blueimp-gallery">
														<img style="height: 90px; width: 90px;" alt="" src="http://minsustatic.d.ziroom.com/mapp/images/default.png">
													</a>
												</div>
												<div class="" style="width: 100px; text-align: center; margin-top: 5px;" id="identityS">
													<customtag:authtag authUrl="customer/delCustomerPic" >
													   <button id="holdDelButton" type="button" class="btn btn-success btn-sm" onclick="delCustomerPic('2')">删除</button>
													</customtag:authtag>
													<customtag:authtag authUrl="customer/uploadCustomerPic" >
														<button id="holdUploadButton" type="button" class="btn btn-success btn-sm" onclick="showUploadModal('2');">上传</button>
													</customtag:authtag>
												</div>
											</div>
										</div>
									</div>
									<div class="row" style="margin-top:15px;">
									    
											<div class="col-sm-2 text-right">
												<label class="control-label"
													style="height: 35px; line-height: 35px;" id="auditHeadPicHtml">审核头像:</label>
													
													<div class="row">
													 <button type="button" class="btn btn-danger btn-xs customerHeadPic_old" onclick="rejectPicField($('#customerHeadPicRejectId').val(),$('#auditHeadPic'),$('#auditHeadPic img'),$('#headPic'),$('#headPic img'),$('.customerHeadPic_old'))">驳回</button>
											         <button type="button" class="btn btn-danger btn-xs customerHeadPic_old" onclick="javascript:auditedCustomerInfo(2);">通过</button>
											        </div>
											</div>
											<div class="col-sm-9">
												<div class="lightBoxGallery">
													<a href="http://minsustatic.d.ziroom.com/mapp/images/default.png" id="auditHeadPic" data-gallery="blueimp-gallery"><img
														style="height: 80px; width: 100px;" alt="" src="http://minsustatic.d.ziroom.com/mapp/images/default.png"></a>
														<input id="customerAuditHeadPicFid" value="" type="hidden"/>
												        <input id="customerHeadPicRejectId" value="" type="hidden" />
												        
												</div>
												<div
													style="width: 100px; text-align: center; margin-top: 5px;" id="identityT">
													<customtag:authtag authUrl="customer/uploadCustomerPic" ><button type="button" onclick="showUploadModal('3');"
														class="btn btn-success btn-sm ">上传</button></customtag:authtag>
												</div>
											</div>
										
										<!-- 原版头像-->
										<div class="customerHeadPic_old">
											<div class="col-sm-2 text-right customerHeadPic_old">
												<label class="control-label"
													style="height: 35px; line-height: 35px;color: red;" id="oldHeadPicHtml">原版头像:</label>
											</div>
											<div class="col-sm-9 customerHeadPic_old">
												<div class="lightBoxGallery">
													<a href="http://minsustatic.d.ziroom.com/mapp/images/default.png" id="headPic" data-gallery="blueimp-gallery"><img
														style="height: 80px; width: 100px;" alt="" src="http://minsustatic.d.ziroom.com/mapp/images/default.png"></a>
												</div>
												<%-- <div
													style="width: 100px; text-align: center; margin-top: 5px;">
													<customtag:authtag authUrl="customer/uploadCustomerPic" ><button type="button" onclick="showUploadModal('3');"
														class="btn btn-success btn-sm ">上传</button></customtag:authtag>
												</div> --%>
											</div>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="row">
										<div class="col-sm-2 text-right">
											<label class="control-label"
												style="height: 35px; line-height: 35px;">备注<span id="beizhu" style="display: none;">(驳回给房东发短信必填)</span>:</label>
										</div>
										<div class="col-sm-10">
											<textarea id="remarkContent" class="form-control" rows=""
												cols=""></textarea>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="row" style="margin-bottom:10px;">
									
									  <div class="col-sm-6 text-right auditStatusPass" style="display: none">
											<customtag:authtag authUrl="customer/auditCustomer" ><button type="button" class="btn btn-success btn-sm"
												onclick="auditCustomer('1')">通过</button></customtag:authtag>
										</div>
										<div class="col-sm-6 auditStatusPass">
											<customtag:authtag authUrl="customer/auditCustomer" ><button type="button" class="btn btn-danger btn-sm"
												onclick="auditCustomer('2')">不通过</button></customtag:authtag>
										</div>
										<div class="col-sm-6 text-right" id="auditStatusNotPass" style="display: none">
											<customtag:authtag authUrl="customer/auditCustomer" ><button type="button" class="btn btn-danger btn-sm"
												onclick="auditCustomer('3')">驳回短信通知房东</button></customtag:authtag>
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
	<input type="hidden" id="picBaseAddr" value="${picBaseAddr }" />
	<input type="hidden" id="picSize" value="${picSize }" />
	<input type="hidden" class="_access_token_" value="${access_token}">
	<input type="hidden" class="_access_token_" value="${sensitiveUrl}">
	
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- 图片上传 -->
	<script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}"></script>
     <script src="${staticResourceUrl}/js/minsu/common/customerUploader.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	
	 <!-- 欧诺弥亚 -->
	<script src="${staticResourceUrl}/../eunomia/js/minsu/common/minsu.eunomia.validate.js"></script>
	<!-- Page-Level Scripts -->
	<script type="text/javascript">
	   <!-- 欧诺弥亚 -->
	   $('.sensitiv-words-validate').sensitivWords({
        auto : false,
        url: '${sensitiveUrl}'
       });
	   
		// 添加全局站点信息
		var BASE_URL = 'js/plugins/webuploader';
		var UPLOAD_BASE_URL = '${basePath}';
		var PIC_URL = '${picResourceUrl}';
		var DEFAULT_PIC = "http://minsustatic.d.ziroom.com/mapp/images/default.png";
		var delBussinessLicensePic="delCustomerPic('4')";
    	var upBussinessLicensePic="showUploadModal('4');";
		//客户对象
		var customerDetail = {
			idtype : function(value) {
				if (value == "1") {
					return "身份证";
				}else if(value == "2"){
					return "护照";
				}else if(value == "0"){
					return "其他";
				}else if(value == "6"){
					return "台湾居民来往通行证";
				}else if(value == "13"){
					return "港澳居民来往通行证";
				}else if(value == "14"){
					return "港澳台居民身份证";
				}else if (value="12"){
					$("#holdDelButton").attr("onclick", delBussinessLicensePic);
					$("#holdUploadButton").attr("onclick", upBussinessLicensePic);
					$("#frontPicText").html("法人身份证正面");
					$("#backPicText").html("法人身份证反面");
					$("#holdPicText").html("营业执照正面照");
					return "营业执照";
				}
			},
			reverseAudit : function(value) {
				if (value == "未审核") {
					return "0";
				} else if (value == "审核通过") {
					return "1";
				} else if (value== "审核未通过"){
					return "2";
				}
				
			},
			//审核状态
			audit : function(value) {
				if (value == "0") {
					return "未审核";
				} else if (value == "1") {
					return "审核通过";
				} else if(value == "2"){
					return "审核未通过";
				}
			},
			sex : function(value) {
				if (value == "1") {
					return "女";
				} else if(value == "2"){
					return "男";
				}else{
					return "";
				}
			},
			role : function(value, row, index) {
				if (value == "1") {
					return "房东";
				} else if (value == "0") {
					return "房客";
				}
			},
			auditStatus : function(value, row, index) {
				if (value == "0") {
					return "未审核";
				} else if (value == "1") {
					return "审核通过";
				} else if (value == "2") {
					return "审核未通过";
				}
			},
			nameshow : function(value){
				return "<a href='javascript:void(0);'>"+value+"</a>";
			},
			showDetail : function(uid) {
				$.getJSON("customer/customerDetailInfo",{uid:uid}, function(data) {
					

					var bohuiNum = 0;
					var auditStatus = 	data.auditStatus;
					$("#remarkContent").val("");
					$.each(data, function(i, n) {
						if (i == "idType") {
							$("#idTypeName").val(customerDetail.idtype(n));
						}
						if (i == "customerSex") {
							$("#customerSex").val(customerDetail.sex(n));
						} else if (i == "isLandlord") {
							$("#isLandlord").val(customerDetail.role(n));
						} else if (i == "auditStatus") {
							$("#auditStatus").val(customerDetail.audit(n));
						} else if(i == "customerBirthday"){
							$("#customerBirthday").val(new Date(n).format("yyyy-MM-dd"));
						}else if (i == "customerPicList") {
							$(".lightBoxGallery a").attr("href", DEFAULT_PIC);
							$(".lightBoxGallery a img").attr("src", DEFAULT_PIC);
							var picBaseAddrMona=$("#picBaseAddrMona").val();
							var picBaseAddr = $("#picBaseAddr").val();
					    	var picSize=$("#picSize").val();
	
							$.each(n, function(i, n) {
								if(n.picType != "3"){
									if(!n.picServerUuid){
										return true;
									} 
								}
								var picurl = picBaseAddr + n.picBaseUrl + n.picSuffix;
								if (n.picType == "0") {
									$("#frontPic").attr("href", picurl);
									$("#frontPic img").attr("src", picurl);
								} else if (n.picType == "1") {
									$("#backPic").attr("href", picurl);
									$("#backPic img").attr("src", picurl);
								} else if (n.picType == "2") {
									$("#holdPic").attr("href", picurl);
									$("#holdPic img").attr("src", picurl);
								} else if (n.picType == "3") {
									var headPicCheck = n.picBaseUrl.indexOf("http");
									if(headPicCheck==-1){
										$("#headPic").attr("href", picurl);
										$("#headPic img").attr("src", picurl);
									}else{
										$("#headPic").attr("href", n.picBaseUrl);
										$("#headPic img").attr("src", n.picBaseUrl);
									}
								}else if (n.picType == "4") {
									$("#holdPic").attr("href", picurl);
									$("#holdPic img").attr("src", picurl);
									$("#holdDelButton").attr("onclick", delBussinessLicensePic);
									$("#holdUploadButton").attr("onclick", upBussinessLicensePic);
								}
							});
						} else if (i == "customerOperHistoryList") {
							
						}else if (i == "unAuditNickName") {
							if(n){
								$("#unAuditNickName").val(n);
								if(auditStatus == 1){
									  $('.customerNickName_old').css({
			                                visibility: 'visible',
			                            });
								}
								bohuiNum++;
							}else{
								$("#unAuditNickName").val($("#nickName").val());
							    $("#audit_nickName_label").text($("#old_nickName_label").text());
								$('.customerNickName_old').css({
	                                visibility: 'hidden',
	                            });
							}
						}else if (i == "unAuditNickNameFieldFid") {
							if(n){
								$("#unAuditNickNameFieldFid").val(n);
							}
						}else if(i == "customerBaseMsgExtEntity"){//原版个人介绍
							if(n){
								$("#originIntroduce").text(n.customerIntroduce);
								$("#originIntroduceNum").text(n.customerIntroduce.length);
							}else{
								$("#originIntroduce").text("");
								$("#originIntroduceNum").text("0");
							}
						} else if(i == "unCheckCustomerBaseMsgExt"){//待审核个人介绍
							if(n){
								$("#customerIntroduceRejectId").val(n.fid);//待审核个人介绍-newlog表对应的id
								$("#introduce").text(n.customerIntroduce);
								$("#introduceNum").text(n.customerIntroduce.length);
								 if(auditStatus == 1){
									  $('.customerIntroduce_old').css({
			                                visibility: 'visible',
			                            });
								  }
								
								bohuiNum++;
							}else{
								$("#introduce").text($("#originIntroduce").val());
								$("#introduceNum").text($("#originIntroduceNum").val());
								
							 
								$('.customerIntroduce_old').css({
	                                visibility: 'hidden',
	                            });
							
								
							}
						}else if(i == "latestUnAuditHeadPic"){//审核头像
							if(n){
								var picBaseAddr = $("#picBaseAddr").val();
								var picurl = picBaseAddr + n.picBaseUrl + n.picSuffix;
								var headPicCheck = n.picBaseUrl.indexOf("http");
								$("#customerAuditHeadPicFid").val(n.fid);//待审核头像fid
								$("#customerHeadPicRejectId").val(data.curCustomerFieldAuditFid);//待审核头像-newlog表对应的id
								if(headPicCheck==-1){
									$("#auditHeadPic").attr("href", picurl);
									$("#auditHeadPic img").attr("src", picurl);
								}else{
									$("#auditHeadPic").attr("href", n.picBaseUrl);
									$("#auditHeadPic img").attr("src", n.picBaseUrl);
								}
								  if(auditStatus == 1){
									  $('.customerHeadPic_old').css({
			                                visibility: 'visible',
			                            });
								  }
								
								bohuiNum++;
							}else{
								$("#auditHeadPic").attr("href", $("#headPic").attr("href"));
								$("#auditHeadPic img").attr("src",$("#headPic").attr("href"));
								$("#auditHeadPicHtml").html("头像");
								/* console.log("原版个人头像"+$("#headPic").attr("href"));
								$("#auditHeadPic").attr("href", $("#headPic").val());
								$("#auditHeadPic img").attr("src", $("#headPic").val());
								console.log("新的个人头像"+$("#headPic").attr("href"));
								console.log("新的个人头像"+$("#headPic").attr("href"));*/
								$('.customerHeadPic_old').css({
	                                visibility: 'hidden',
	                            }); 
							}
							
						}else {
							$("#" + i).val(n);
						}
					});
					$("#auditStatusNotPass").hide();
				   if(auditStatus == 1){
					   $(".auditStatusPass").hide();
					   if(bohuiNum>0){
						   $("#auditStatusNotPass").show(); 
					   }
					   $("#beizhu").show();
					   $("#identityZ").hide();
					   $("#identityF").hide();
					   $("#identityS").hide();
					   $("#identityT").hide();
				   }else{
					   $("#beizhu").hide();
					   $(".auditStatusPass").show();
					   $("#identityZ").show();
					   $("#identityF").show();
					   $("#identityS").show();
					   $("#identityT").show();
				   }
				});

			}
		}
		//计算数量
		function calNum() {
			var num = $("#introduce").val().length;
			$("#introduceNum").text(num);
		}
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				customerMobile : $.trim($('#S_customerMobile').val()),
				realName : $.trim($('#S_realName').val()),
				nickName : $('#S_nickName').val(),
				isLandlord : $('#S_isLandlord option:selected').val(),
				auditStatus : $('#S_auditStatus option:selected').val(),
				isCanShow : $('#S_isCanShow option:selected').val(),
			};
		}
		//删除客户图片
		function delCustomerPic(type) {
			var uid = $("#hideUid").val();
			$.post("customer/delCustomerPic", {
				uid : uid,
				picType : type
			}, function(data) {
				if (data.code == 0) {
					if (type == "0") {
						$("#frontPic").attr("href", DEFAULT_PIC);
						$("#frontPic img").attr("src", DEFAULT_PIC);
					} else if (type == "1") {
						$("#backPic").attr("href", DEFAULT_PIC);
						$("#backPic img").attr("src", DEFAULT_PIC);
					} else if (type == "2") {
						$("#holdPic").attr("href", DEFAULT_PIC);
						$("#holdPic img").attr("src", DEFAULT_PIC);
					} else if (type == "3") {
						$("#headPic").attr("href", DEFAULT_PIC);
						$("#headPic img").attr("src", DEFAULT_PIC);
					}else if (type == "4") {
						$("#holdPic").attr("href", DEFAULT_PIC);
						$("#holdPic img").attr("src", DEFAULT_PIC);
					}
					layer.alert("删除成功", {icon: 5,time: 2000, title:'提示'});
				}else{
					layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
				}
			}, "json");
		}

		//上传参数
		function showUploadModal(type) {
			$("#customerPicType").val(type);
			createUploader();
			$('#myModal').modal('show');
		}
		//审核操作
		function auditCustomer(after) {
			//如果审核未通过，需要填写未通过原因
			if(after == "2"){
				if($("#remarkContent").val() == ""){
					layer.alert("请填写备注", {icon: 5,time: 2000, title:'提示'});
					return;
				}
				$("#introduce").val("");
				$("#customerAuditHeadPicFid").val("");
			}
			
			if(after == "3"){
				if($("#remarkContent").val() == ""){
					layer.alert("请填写驳回备注才能通知房东", {icon: 5,time: 2000, title:'提示'});
					return;
				}
			}
			var before = customerDetail.reverseAudit($("#auditStatus").val());
			$.post("customer/auditCustomer", {
				uid : $("#hideUid").val(),
				afterOper : after,
				beforeOper : before,
				remark : $("#remarkContent").val(),
				//个人介绍和个人头像
				introduce:$("#introduce").val(),
				introduceRejectId:$("#customerIntroduceRejectId").val(),
				headPicFid:$("#customerAuditHeadPicFid").val(),	
				headPicRejectId:$("#customerHeadPicRejectId").val()
			}, function(data, status) {

				if(data.code == 0){
                    $("#customerDetailInfoModel").modal("hide");
					//重新刷新页面
					CommonUtils.query();
				}else{
					layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
				}
				
			}, "json");
		}

		//点击单元格
		$('#listTable').on('click-cell.bs.table',
				function(field, value, row, $element) {
					if(value == 'uid'){
						var auditStatus = $element.auditStatus;
						var isLandlord = $element.isLandlord;
						var isCanShow = $element.isCanShow;
						//只有未审核 审核未通过才显示
						if(isLandlord == 1){
							if(auditStatus == 0 || auditStatus == 2 || isCanShow==1 ||auditStatus == 1 ){ //上完线后 修改过来 TODO
								if (value == "uid") {
									$("#customerDetailInfoModel").modal("toggle");
									$("#hideUid").val(row);
									//赋值信息
									customerDetail.showDetail(row);
								}
							 }else{
								layer.alert("审核状态错误", {icon: 5,time: 2000, title:'提示'});
							}  
						}else{
							layer.alert("房客不需要审核", {icon: 5,time: 2000, title:'提示'});
						}
					}
				});
		//隐藏model
		$("#modalClose").click(function() {
			$('#customerDetailInfoModel').modal('hide');
		})
		//上传modal显示
		$("#uploadTest").click(function() {
			createUploader();
			//上传
			$('#myModal').modal('show');
		});
		
		//审核通过
		function rejectCustomerMsg(uid){
			$.ajax({
				url:"customer/auditCustomerReject",
				data:{"uid":uid},
				dataType:"json",
				type:"post",
				async: true,
				success:function(result) {
					if(result.code === 0){
						$('#listTable').bootstrapTable('refresh');
					}else{
						layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		}
		//操作
		function formateOperate(value, row, index){
			if(row.auditStatus==1){
				return "<a href='javascript:rejectCustomerMsg(\""+row.uid+"\");'>驳回</a>";
			}
		}
		
		   //审核驳回某一字段: id:字段审核表（t_house_update_field_audit_newlog）id或图片id
		   //obj1:字段新值input元素    obj2:字段老值span元素     obj3：审核驳回后需要隐藏的div元素
		    var uid = $("#hideUid").val();
		   function rejectField(id,obj1,obj2,obj3) {
               
			   var remark = $("#remarkContent").val();
			  /*  if(remark == ""||remark==undefined||remark==null){
					layer.alert("请填写驳回备注才能通知房东", {icon: 5,time: 2000, title:'提示'});
					return;
				} */
			   $.ajax({
					   url:"${basePath}/customer/rejectCustomerField",
					   data:{id:id,uid:uid},
                       dataType:"json",
					   type:"post",
					   success:function (result) {
                        if(result.code == 0){
                           	layer.alert("操作成功,驳回后请短信通知房东", {icon: 6,time: 3000, title:'提示'});
                            if(obj1!=null&&obj2!=null){
                                obj1.val(obj2.val());
                                obj2.val(null);
                                $("#customerIntroduceRejectId").val(null);
                            }
                            obj3.css({
                                visibility: 'hidden',
                            });
                            /* if(result.data.refreshDeal==1){
 	                    	   $("#customerDetailInfoModel").modal("hide");
 								$('#listTable').bootstrapTable('refresh');
 	                       } */

                        }else{
                            layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                        }
                    },
                    error:function(result){
                        layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                    }
				   });
        }
		   
		   
		 //审核驳回某一字段: id:字段审核表（t_house_update_field_audit_newlog）id或图片id
		   //obj1:字段新值href元素    obj2:字段新值img元素     obj3:字段老值href元素    obj4:字段老值img元素  obj5：审核驳回后需要隐藏的div元素
		   function rejectPicField(id,obj1,obj2,obj3,obj4,obj5) {
              
               var remark = $("#remarkContent").val();
			   
			  /*  if(remark == ""||remark==undefined||remark==null){
					layer.alert("请填写驳回备注才能通知房东", {icon: 5,time: 2000, title:'提示'});
					return;
				} */
			   $.ajax({
            	   url:"${basePath}/customer/rejectCustomerPicField",
            	   data:{id:$("#customerHeadPicRejectId").val(),uid:uid},
                   dataType:"json",
				   type:"post",
				   success:function (result) {
					   if(result.code == 0){
						   if(obj1!=null&&obj2!=null){
		                       	obj1.attr('href',obj3.attr('href'));
		                       	obj2.attr('src', obj4.attr('src'));
		                       	$("#customerHeadPicRejectId").val(null);
		                       	$("#customerAuditHeadPicFid").val(null);
		                       	layer.alert("操作成功,驳回后请短信通知房东", {icon: 6,time: 3000, title:'提示'});
		                       	 obj5.css({
		                                visibility: 'hidden',
		                         });
	                       }
						   /* if(result.data.refreshDeal==1){
	                    	   $("#customerDetailInfoModel").modal("hide");
								$('#listTable').bootstrapTable('refresh');
	                       } */
					   }else{
                    	   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                       }
				   },
				   error:function(result){
                       layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                   }
			   });
        }
		 
		 //审核信息 通过
		 function  auditedCustomerInfo(type){
			 
				var uid = $("#hideUid").val();
				//个人介绍和个人头像
			   if(uid == ""||uid==undefined||uid==null){
					layer.alert("请选择房东", {icon: 5,time: 2000, title:'提示'});
					return;
				}
				
			    var introduce = $("#introduce").val();
				var introduceRejectId=$("#customerIntroduceRejectId").val();
				var headPicFid=$("#customerAuditHeadPicFid").val();
				var headPicRejectId=$("#customerHeadPicRejectId").val();
			    var nickName = $('#unAuditNickName').val();
			    var unAuditNickNameFieldFid = $('#unAuditNickNameFieldFid').val();
				var dataJson = "";
				if(type == 1){
					dataJson ={"uid":uid,"introduceRejectId":introduceRejectId,"introduce":introduce};
				}
				if(type == 2){
					dataJson ={"uid":uid,"headPicFid":headPicFid,"headPicRejectId":headPicRejectId};
				}
				if(type == 3){
					dataJson ={"uid":uid,"unAuditNickNameFieldFid":unAuditNickNameFieldFid,"nickName":nickName};
				}
			   $.ajax({
            	   url:"${basePath}/customer/auditedCustomerInfo",
            	   data:dataJson,
                   dataType:"json",
				   type:"post",
				   success:function (result) {
					   if(result.code == 0){
							//赋值信息
							 layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
							customerDetail.showDetail(uid);
						   //审核头像
						 /*   if(headPicFid!=null&&introduceRejectId!=null){
		                       	obj1.attr('href',obj3.attr('href'));
		                       	obj2.attr('src', obj4.attr('src'));
		                       	$("#customerHeadPicRejectId").val(null);
		                       	$("#customerAuditHeadPicFid").val(null);
		                       	layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
		                       	
	                       }
						   
						   //审核个人介绍
						   if(introduce!=null){
							   obj1.val(obj2.text());
                               obj2.text(null);
                               $("#customerIntroduceRejectId").val(null);
		                       layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
	                       } */
	                        if(result.data.refreshDeal==1){
	                    	   $("#customerDetailInfoModel").modal("hide");
								$('#listTable').bootstrapTable('refresh');
	                       } 
					   }else{
                    	   layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
                       }
				   },
				   error:function(result){
                       layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                   }
			   });
		 }
	</script>
</body>
</html>
