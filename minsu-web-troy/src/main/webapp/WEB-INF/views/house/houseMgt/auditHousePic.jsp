<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<%@ page language="java" import="com.ziroom.minsu.valenum.houseaudit.HouseAuditCauseEnum"%>
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

</head>
  
  <jsp:include page="houseDetail.jsp"/>
  <body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<form id="form" class="form-group">
							<input id="houseName" value="${houseName }" type="hidden">
							<input id="landlordUid" value="${landlordUid }" type="hidden">
							<input id="houseFid" value="${houseFid }" type="hidden">
							<input id="rentway" value="${rentWay }" type="hidden">
							<input id="unApproveReason" type="hidden"/>
							<div class="row">
								<label class="col-sm-1 control-label m-b">审核不通过原因:</label>
								<div class="col-sm-8">
									<label class="col-sm-12 control-label m-b"><input name="unApproveReason" type="checkbox" value="<%=HouseAuditCauseEnum.REJECT.HOUSE_QUALITY.getCode()%>"/><%=HouseAuditCauseEnum.REJECT.HOUSE_QUALITY.getName()%></label>
									<label class="col-sm-12 control-label m-b"><input name="unApproveReason" type="checkbox" value="<%=HouseAuditCauseEnum.REJECT.PHOTO.getCode()%>"/><%=HouseAuditCauseEnum.REJECT.PHOTO.getName()%></label>
									<label class="col-sm-12 control-label m-b"><input name="unApproveReason" type="checkbox" value="<%=HouseAuditCauseEnum.REJECT.PHOTO_NONEED.getCode()%>"/><%=HouseAuditCauseEnum.REJECT.PHOTO_NONEED.getName()%></label>
									<c:forEach items="${rejectEnumMap }" var="obj">
										<c:if test="${obj.key != 13 and obj.key != 10 and obj.key != 16 and obj.key != 12 and obj.key != 14 }">
										<label class="col-sm-2 control-label m-b"><input name="unApproveReason" type="checkbox" value="${obj.key }"/>${obj.value }</label>
										</c:if>
									</c:forEach>
								</div>
							</div>
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
											<customtag:authtag authUrl="house/houseMgt/approveHousePic">
												<input type="button" id="saveBtn" value="通过" class="btn btn-primary">&nbsp;&nbsp;
											</customtag:authtag>	
											<customtag:authtag authUrl="house/houseMgt/unApproveHousePic">
												<input type="button" id="refuseBtn" value="拒绝" disabled="disabled" class="btn btn-white">
											</customtag:authtag>
										</c:if>	
										<c:if test="${rentWay == 1 }">
											<customtag:authtag authUrl="house/houseMgt/approveRoomPic">
												<input type="button" id="saveBtn" value="通过" class="btn btn-primary">&nbsp;&nbsp;
											</customtag:authtag>	
											<customtag:authtag authUrl="house/houseMgt/unApproveRoomPic">
												<input type="button" id="refuseBtn" value="拒绝" disabled="disabled" class="btn btn-white">
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
	                        maxlength: 450
	                   }
	               }
	           });

	  		});
		   
			var landlordUid = $("#landlordUid").val();
			var houseFid = $("#houseFid").val();
			var rentWay = $("#rentway").val();
			
			// 审核通过
			$("#saveBtn").click(function(){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				var houseChannel =$("#houseChannel");
				if(houseChannel.length>0 && houseChannel.is("select")){
					var houseChannelVal = $("#houseChannel option:selected").val();
					if(checkNullOrBlank(houseChannelVal)){
						layer.alert("需要选择房源渠道", {icon: 5,time: 2000, title:'提示'});
						return false;
					}	
					
				}
				
				if(rentWay == 0){
					approveHousePic(houseFid);
				}else if(rentWay == 1){
					approveRoomPic(houseFid);
				}
				
				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			// 房源照片审核通过
			function approveHousePic(houseFid){
				if(houseFid){
					var houseChannel =$("#houseChannel");
					var houseChannelVal;
					if(houseChannel.length>0 && houseChannel.is("select")){
						houseChannelVal=$("#houseChannel option:selected").val();
					}
					var houseQualityGrade = $("#houseQualityGrade").val();
					if (!houseQualityGrade){
						layer.alert("请选择房源品质等级", {icon: 5,time: 2000, title:'提示'});
						return false;
					}
					$.ajax({
						beforeSend : function(){
							if(checkNullOrBlank(houseQualityGrade)){
								layer.alert("请选择房源品质等级", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							/* var approveReason = $("#approveReason option:selected").val();
							if(checkNullOrBlank(approveReason)){
								layer.alert("需要选择审核通过说明", {icon: 5,time: 2000, title:'提示'});
								return false;
							} */
							var unApproveReason = $("#unApproveReason option:selected").val();
							if(!checkNullOrBlank(unApproveReason)){
								layer.alert("审核不通过原因必须为空", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							return $("#form").valid();
						},
						url : "house/houseMgt/approveHousePic",
						data:{
							"landlordUid":landlordUid,
							"houseBaseFid":houseFid,
							"auditCause":$("#approveReason option:selected").val(),
							"remark":$("#remark").val(),
							"houseChannel":houseChannelVal,
							"houseQualityGrade":houseQualityGrade
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgPic",true,approveHousePicCallback);
							} else {
								layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			
			//审核房源图片刷新父页面数据
			function approveHousePicCallback(panrent){
				panrent.refreshData("listTable");
			}
			
			// 房间照片审核通过
			function approveRoomPic(houseFid){
				if(houseFid){
					var houseChannel =$("#houseChannel");
					var houseChannelVal;
					if(houseChannel.length>0 && houseChannel.is("select")){
						houseChannelVal=$("#houseChannel option:selected").val();
					}
					var houseQualityGrade = $("#houseQualityGrade").val();
					$.ajax({
						beforeSend : function(){
							if(checkNullOrBlank(houseQualityGrade)){
								layer.alert("请选择房间品质等级", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							/* var approveReason = $("#approveReason option:selected").val();
							if(checkNullOrBlank(approveReason)){
								layer.alert("需要选择审核通过说明", {icon: 5,time: 2000, title:'提示'});
								return false;
							} */
							var unApproveReason = $("#unApproveReason option:selected").val();
							if(!checkNullOrBlank(unApproveReason)){
								layer.alert("审核不通过原因必须为空", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							return $("#form").valid();
						},
						url : "house/houseMgt/approveRoomPic",
						data:{
							"landlordUid":landlordUid,
							"houseRoomFid":houseFid,
							"auditCause":$("#approveReason option:selected").val(),
							"remark":$("#remark").val(),
							"houseChannel":houseChannelVal,
							"houseQualityGrade":houseQualityGrade
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgPic",true,approveRoomPicCallback);
							} else {
								layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
							}
						},
						error:function(result){
							layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
						}
					});
				}
			}
			//审核房源刷新父页面数据
			function approveRoomPicCallback(panrent){
				panrent.refreshData("listTableS");
			}
			
			// 审核不通过
			$("#refuseBtn").click(function(){
				// 禁用按钮
				$(this).attr("disabled","disabled");
				
				if(rentWay == 0){
					unApproveHousePic(houseFid);
				}else if(rentWay == 1){
					unApproveRoomPic(houseFid);
				}

				// 启用按钮
				$(this).removeAttr("disabled");
			});
			
			// 房源照片审核未通过
			function unApproveHousePic(houseFid){
				if(houseFid){
					$.ajax({
						beforeSend : function(){
							var unApproveReason = $("#unApproveReason").val();
							if(checkNullOrBlank(unApproveReason)){
								layer.alert("需要选择审核不通过原因", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							return $("#form").valid();
						},
						url : "house/houseMgt/unApproveHousePic",
						data:{
							"houseBaseFid":houseFid,
							"auditCause":$("#unApproveReason").val(),
							"remark":$("#remark").val()
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgPic",true,approveHousePicCallback);
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
			
			// 房间照片审核未通过
			function unApproveRoomPic(houseFid){
				if(houseFid){
					$.ajax({
						beforeSend : function(){
							var unApproveReason = $("#unApproveReason").val();
							if(checkNullOrBlank(unApproveReason)){
								layer.alert("需要选择审核不通过原因", {icon: 5,time: 2000, title:'提示'});
								return false;
							}
							/*var approveReason = $("#approveReason option:selected").val();
							if(!checkNullOrBlank(approveReason)){
								layer.alert("审核通过说明必须为空", {icon: 5,time: 2000, title:'提示'});
								return false;
							}*/
							return $("#form").valid();
						},
						url : "house/houseMgt/unApproveRoomPic",
						data:{
							"houseRoomFid":houseFid,
							"auditCause":$("#unApproveReason").val(),
							"remark":$("#remark").val()
						},
						dataType:"json",
						type : "post",
						success : function(result) {
							if (result.code === 0) {
								layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
								$.callBackParent("house/houseMgt/listHouseMsgPic",true,approveRoomPicCallback);
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
			
			<%-- $("#approveReason").change(function(){
				var value = $(this).val();
				var remark = assembleRemark();
				var _remark = $("#remark").val().replace(remark, "");
				if(value == '<%=HouseAuditCauseEnum.Approve.NEED.getCode()%>'){
					remark += _remark;
					$("#remark").val(remark);
				}else{
					$("#remark").val(_remark);
				} 
				//复选框全部反选
				$("input[name='unApproveReason']").each(function(){
			        $(this).attr("checked",false);
			    });  
			}); --%>
			
			function assembleRemark(){
				var remark = "";
				if(rentWay == 0){
					remark += "房源名称:" + $("#houseName").val();
					remark += " 房源编号:" + $("#houseSn").val();
				} else if(rentWay == 1){
					remark += "房间名称:" + $("#roomName").val();
					remark += " 房间编号:" + $("#roomSn").val();
				}
				remark += ";\n"
				return remark;
			}
			
			$("input[name='unApproveReason']").click(function(){
				var checkVal = $(this).val();
				if (!checkVal){
					return;
				}
				if (checkVal == '13'){
					$("input[name='unApproveReason']").each(function () {
						if ($(this).val() == 13){
							return true;
						}
						$(this).removeAttr("checked");
					});
				}else if(checkVal != '13'){
					$("input[name='unApproveReason']").filter("input[value='13']").removeAttr("checked");
					if (checkVal == '10'){
						$("input[name='unApproveReason']").filter("input[value='16']").removeAttr("checked");
					}
					if (checkVal == '16'){
						$("input[name='unApproveReason']").filter("input[value='10']").removeAttr("checked");
					}
				}

				var val = "";

				var i = 0;

				$("input[name='unApproveReason']").filter(":checked").each(function(){
					var _val = $(this).val();

					if(i == 0){
						val += _val;
					} else {
						val += ","+_val;
					}
					i++;
			    }); 

				if(checkNullOrBlank(val)){
					$("#saveBtn").removeAttr("disabled");
					$("#refuseBtn").attr("disabled","disabled");
				}else{
					$("#saveBtn").attr("disabled","disabled");
					$("#refuseBtn").removeAttr("disabled");
				}
					
				$("#unApproveReason").val(val);
				$("#approveReason").val('');
		    });
	   </script>
	</body>
</html>