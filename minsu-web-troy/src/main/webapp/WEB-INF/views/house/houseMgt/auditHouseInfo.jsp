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
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">

</head>
  
  <jsp:include page="houseDetail.jsp"/>
  <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="form" class="form-group">
							<input id="houseFid" value="${houseFid }" type="hidden">
							<input id="rentway" value="${rentWay }" type="hidden">
							<div class="row">
								<div class="form-group">
									<label class="col-sm-1 control-label">补充信息：</label>
									<div class="col-sm-8">
										<textarea id="addtionalInfo" name="addtionalInfo" class="form-control" rows="10"></textarea>
									</div>
								</div>
							</div>
							<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-sm-1 control-label">备注：</label>
									<div class="col-sm-8">
										<textarea id="remark" name="remark" class="form-control" rows="5"></textarea>
									</div>
								</div>
							</div>
						</form>
						<form class="form-group">
							<div class="row">
								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-5">
										<c:if test="${rentWay == 0 }">
											<customtag:authtag authUrl="house/houseMgt/approveHouseInfo">
												<button type="button" id="saveBtn" class="btn btn-primary">通过</button>&nbsp;&nbsp;
											</customtag:authtag>	
											<customtag:authtag authUrl="house/houseMgt/unApproveHouseInfo">
												<button type="button" id="refuseBtn" class="btn btn-white">拒绝</button>
											</customtag:authtag>
										</c:if>	
										<c:if test="${rentWay == 1 }">
											<customtag:authtag authUrl="house/houseMgt/approveRoomInfo">
												<button type="button" id="saveBtn" class="btn btn-primary">通过</button>&nbsp;&nbsp;
											</customtag:authtag>	
											<customtag:authtag authUrl="house/houseMgt/unApproveHouseInfo">
												<button type="button" id="refuseBtn" class="btn btn-white">拒绝</button>
											</customtag:authtag>
										</c:if>
										<customtag:authtag authUrl="house/houseMgt/showHouseOperateLogList">
											<button type="button" class="btn btn-white" data-toggle="modal" data-target="#opLogModel">查看审核历史</button>
										</customtag:authtag>
									</div>
								</div>
							</div>
						</form>
					</div>
					</div>
				</div>
			</div>
		</div>
	<!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	    
	    <!-- iCheck -->
    	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
	
		<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
        <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
	   
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
	   		$(function (){
	   			$('.i-checks').iCheck({
	                checkboxClass: 'icheckbox_square-green',
	                radioClass: 'iradio_square-green',
	            });
		 		
		 		var icon = "<i class='fa fa-times-circle'></i> ";
	            $("#form").validate({
	                rules: {
	                    remark: {
	                        required: true,
	                        maxlength: 100
	                    },
	                    addtionalInfo: {
	                        maxlength: 1000
	                    }
	                }
	            });
	   		});
	   		
			var houseFid = $("#houseFid").val();
			var rentWay = $("#rentway").val();
			// 审核通过
			$("#saveBtn").click(function(){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				if(rentWay == 0){
					approveHouseInfo(houseFid);
				}else if(rentWay == 1){
					approveRoomInfo(houseFid);
				}
				
				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			// 审核不通过
			$("#refuseBtn").click(function(){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				if(rentWay == 0){
					unApproveHouseInfo(houseFid);
				}else if(rentWay == 1){
					unApproveRoomInfo(houseFid);
				}
				
				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			//信息审核
			function approveHouseInfo(houseBaseFid){
				if(houseBaseFid){
					
					var houseChannel =$("#houseChannel");
					if(houseChannel.length>0 && houseChannel.is("select")){
						var houseChannelVal = $("#houseChannel option:selected").val();
						if(checkNullOrBlank(houseChannelVal)){
							layer.alert("需要选择房源渠道", {icon: 5,time: 2000, title:'提示'});
							return false;
						}	
						
					}
					
					$.ajax({
						beforeSend : function(){
							return $("#form").valid();
						},
						url : "house/houseMgt/approveHouseInfo",
						data:{
							"houseBaseFid":houseBaseFid,
							"addtionalInfo":$("#addtionalInfo").val(),
							"remark":$("#remark").val(),
							"houseChannel":houseChannelVal
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgInfo",true,approveHouseInfoCallback);
							} else {
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			//审核房源刷新父页面数据
			function approveHouseInfoCallback(parent){
				parent.refreshData("listTable");
			}
			
			function approveRoomInfo(houseRoomFid){
				if(houseRoomFid){
					var houseChannel =$("#houseChannel");
					if(houseChannel.length>0 && houseChannel.is("select")){
						var houseChannelVal = $("#houseChannel option:selected").val();
						if(checkNullOrBlank(houseChannelVal)){
							layer.alert("需要选择房源渠道", {icon: 5,time: 2000, title:'提示'});
							return false;
						}	
						
					}
					
					$.ajax({
						beforeSend : function(){
							return $("#form").valid();
						},
						url : "house/houseMgt/approveRoomInfo",
						data:{
							"houseRoomFid":houseRoomFid,
							"addtionalInfo":$("#addtionalInfo").val(),
							"remark":$("#remark").val(),
							"houseChannel":houseChannelVal
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgInfo",true,approveRoomInfoCallback);
							} else {
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			
			//审核房间刷新父页面数据
			function approveRoomInfoCallback(parent){
				parent.refreshData("listTableS");
			}
			
			function unApproveHouseInfo(houseBaseFid){
				if(houseBaseFid){
					$.ajax({
						beforeSend : function(){
							return $("#form").valid();
						},
						url : "house/houseMgt/unApproveHouseInfo",
						data:{
							"houseBaseFid":houseBaseFid,
							"addtionalInfo":$("#addtionalInfo").val(),
							"remark":$("#remark").val()
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgInfo",true,approveHouseInfoCallback);
							} else {
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			
			function unApproveRoomInfo(houseRoomFid){
				if(houseRoomFid){
					$.ajax({
						beforeSend : function(){
							return $("#form").valid();
						},
						url : "house/houseMgt/unApproveRoomInfo",
						data:{
							"houseRoomFid":houseRoomFid,
							"addtionalInfo":$("#addtionalInfo").val(),
							"remark":$("#remark").val()
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgInfo",true,approveRoomInfoCallback);
							} else {
								layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			
			function checkNullOrBlank(value){
				return typeof(value) == 'undefined' || value == null || $.trim(value).length == 0;
			}
			
	   </script>
	</body>
</html>