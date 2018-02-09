<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<style>
.modal-lm {
	min-width: 880px;
}

.lightBoxGallery img {
	margin: 5px;
	width: 160px;
}
.row {
	margin: 0;
}
#operList td {
	font-size: 12px;
}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
					<form class="form-group">
						<div class="hr-line-dashed"></div>
						<b >房源跟进记录</b>
						<div class="hr-line-dashed"></div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源编号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.houseSn}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">发布时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="<fmt:formatDate value="${houseFollowVo.pushDate}" type="both"/> " readonly="readonly">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">城市:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.cityName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源名称:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.houseName}" readonly="readonly">
							</div>
						</div>
						<div class="row m-b">

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" ${houseFollowVo.landlordName }" readonly="readonly">

							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东电话:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" ${ houseFollowVo.landordMobile}" readonly="readonly">
							</div>
						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">审核未通过时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="<fmt:formatDate value="${houseFollowVo.auditStatusTime}" type="both"/>" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">审核未通过原因:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.auditCause}" readonly="readonly">
							</div>
						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">未通过说明:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.refuseMark}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源跟进状态:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.followStatusStr}" readonly="readonly">
							</div>
							<div class="col-sm-2 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源对应预约摄影单状态:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${houseFollowVo.photoOrderStatus}" readonly="readonly">
							</div>
						</div>
						<div class="hr-line-dashed"></div>

					</form>




				<form class="form-group">

					<c:forEach items="${houseFollowVo.followLogList}" var="log" varStatus="status">
						<div class="row">
							<div class="col-sm-1">
								<label class="control-label">跟进记录:[${ status.index + 1}]</label>
							</div>
							<div class="col-sm-7">
								<textarea rows="8"  class="form-control m-b" readonly="readonly">${log.followDesc }</textarea>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-1">
							</div>
							<div class="col-sm-1">
							跟进人：
							</div>
							<div class="col-sm-2">
							${log.followEmpName }
							</div>
							<div class="col-sm-1">
							跟进日期：
							</div>
							<div class="col-sm-7">
							<fmt:formatDate value="${log.createDate}" type="both"/>
							</div>
						</div>

						<div class="row">
						</div>

					</c:forEach>

				</form>


				<form class="form-group" >
					<div class="row">
						<div class="col-sm-1">
							备注
						</div>
						<div class="col-sm-7">
							<textarea rows="8" id="content"  class="form-control m-b" ></textarea>
							<input type="hidden" id="followFid" value="${houseFollowVo.fid}">
							<input type="hidden" id="followStatus" value="${houseFollowVo.followStatus }">
							<input type="hidden" id="landlordUid" value="${houseFollowVo.landlordUid}">
							<input type="hidden" id="rentWay" value="${houseFollowVo.rentWay}">
							<input type="hidden" id="roomFid" value="${houseFollowVo.roomFid}">
							<input type="hidden" id="houseBaseFid" value="${houseFollowVo.houseBaseFid}">
						</div>
					</div>
					
					<div class="row">
						<div class="col-sm-1">
						</div>
						<div class="col-sm-7">
							<input type="checkbox"  id="notLandlord">未联系上房东
						</div>
					</div>

					<div class="row">
						<div class="col-sm-2">

						</div>
						<div class="col-sm-2">
							<button class="btn btn-primary" type="button" onclick="continueFollow()"><i class="fa fa-search"></i>&nbsp;确定</button>
						</div>
						<div class="col-sm-2">
							<button class="btn btn-primary" type="button" onclick="finishFollow()"><i class="fa fa-search"></i>&nbsp;取消</button>
						</div>
						<div class="col-sm-2">
							<button class="btn btn-primary" type="button" onclick="followEnd()"><i class="fa fa-search"></i>&nbsp;跟进结束</button>
						</div>
					</div>
				</form>

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
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>

	<script>

		function continueFollow() {
			var content = $("#content").val();
			var followFid = $("#followFid").val();
			var notLandlord=$("#notLandlord").prop("checked");
			var followStatus=$("#followStatus").val();
			var rentWay=$("#rentWay").val();
			var roomFid=$("#roomFid").val();
			var houseBaseFid=$("#houseBaseFid").val();
			var landlordUid=$("#landlordUid").val();
			if(content==null || content==''){
				layer.alert("请填写跟进记录", {icon: 5,time: 3000, title:'提示'});
				return;
			}		
			$.ajax({
	            type: "POST",
	            url: "house/houseFollow/saveHouseFollowLog",
	            dataType:"json",
	            data: {"followFid":followFid,"followDesc":content,"notLandlord":notLandlord,"fromStatus":followStatus,"rentWay":rentWay,"roomFid":roomFid,"houseBaseFid":houseBaseFid,"landlordUid":landlordUid},
	            success: function (result) {
	            	if(result.code==0){
	            		$.callBackParent("house/houseFollow/serviceFollowList",true,saveHouseFollowLogCallback);
	            	}else{
	            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
	            	}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
	            }
	        });
		}


		function finishFollow() {
			$.callBackParent("house/houseFollow/serviceFollowList",true,saveHouseFollowLogCallback);
		}
		
		//回调刷新列表
		function saveHouseFollowLogCallback(panrent){
			panrent.refreshJqGrid("jqGrid");
		}
		//客服结束 跟进
		function followEnd(){
			
			var content = $("#content").val();
			var fid = $("#followFid").val();
			
			if(content==null || content==''){
				layer.alert("请填写跟进备注", {icon: 5,time: 3000, title:'提示'});
				return;
			}	
			
			//这种基本不可能
			if(fid==null || fid==''){
				layer.alert("系统错误,请刷新重试！", {icon: 5,time: 3000, title:'提示'});
				return;
			}
			
			$.ajax({
	            type: "POST",
	            url: "house/houseFollow/updateHouseFollow",
	            dataType:"json",
	            data: {"fid":fid,"houseFollowLogDto.followDesc":content},
	            success: function (result) {
	            	if(result.code==0){
	            		$.callBackParent("house/houseFollow/serviceFollowList",true,saveHouseFollowLogCallback);
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
