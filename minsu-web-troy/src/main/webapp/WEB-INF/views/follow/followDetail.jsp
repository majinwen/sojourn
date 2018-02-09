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
						<b >订单信息</b>
						<div class="hr-line-dashed"></div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.orderSn}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">订单状态:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.orderStatusName}【 ${detail.orderStatus} 】" readonly="readonly">
							</div>

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">支付状态:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" ${detail.payStatusName}" readonly="readonly">
							</div>

						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">预订人姓名:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">手机号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.userTel}" readonly="readonly">
							</div>

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">下单时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
							</div>

						</div>
						<div class="row m-b">

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">开始时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">

							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">结束时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">
							</div>

							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">实际退房时间:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value=" <fmt:formatDate value="${detail.realEndTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly">

							</div>

						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.landlordName}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房东手机号:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.landlordTel}" readonly="readonly">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">房源地址:</label>
							</div>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.houseAddr}" readonly="readonly">
							</div>

						</div>

						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label" style="height: 35px; line-height: 35px;">出租类型:</label>
							</div>
							<div class="col-sm-2">
								<c:if test="${detail.rentWay == 1 }">
									<input type="text" class="form-control" value="分租" readonly="readonly">
								</c:if>
								<c:if test="${detail.rentWay  != 1 }">
									<input type="text" class="form-control" value="整租" readonly="readonly">
								</c:if>
							</div>

							<c:if test="${detail.rentWay == 1 }">
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房间名称:</label>
								</div>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="${detail.roomName}" readonly="readonly">
								</div>
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房间编号:</label>
								</div>
							</c:if>

							<c:if test="${detail.rentWay  != 1 }">
								<div class="col-sm-1 text-right">
									<label class="control-label" style="height: 35px; line-height: 35px;">房源名称:</label>
								</div>
								<div class="col-sm-2">
									<input type="text" class="form-control" value="${detail.houseName}" readonly="readonly">
								</div>
								<div class="col-sm-1 text-right">

									<label class="control-label" style="height: 35px; line-height: 35px;">房源编号:</label>
								</div>
							</c:if>
							<div class="col-sm-2">
								<input type="text" class="form-control" value="${detail.houseSn}" readonly="readonly">
							</div>
							<div class="col-sm-1">
								<button type="button" class="btn btn-info" id="setHouseCalendar" onclick="toHouseMap()">找相似房源</button>
							</div>
						</div>
						<div class="hr-line-dashed"></div>

					</form>




				<form class="form-group">

					<c:forEach items="${list}" var="log">

						<div class="row">
							<div class="col-sm-1">

							</div>
							<div class="col-sm-7">
								跟进订单状态:${log.orderStatusName}[${log.orderStatus}]
								---跟进人:${log.createName}
								---跟进时间:<fmt:formatDate type="both" value="${log.createTime}" />
							</div>
						</div>

						<div class="row">
							<div class="col-sm-1">
								<label class="control-label">跟进记录:[${log.index}]</label>
							</div>
							<div class="col-sm-7">
								<textarea rows="8"  class="form-control m-b" readonly="readonly">${log.content }</textarea>
							</div>
						</div>


						<div class="row">
						</div>

					</c:forEach>

				</form>


				<c:if test="${has  != 1 }">
					<form class="form-group" >
						<div class="row">
							<div class="col-sm-1">

							</div>
							<div class="col-sm-7">
								<textarea rows="8" id="content"  class="form-control m-b" ></textarea>
								<input type="hidden" id="orderSn" value="${detail.orderSn}">
							</div>
						</div>

						<div class="row">
							<div class="col-sm-2">

							</div>
							<div class="col-sm-2">
								<button class="btn btn-primary" type="button" id="continueButton" onclick="continueFollow()"><i class="fa fa-search"></i>&nbsp;继续跟进</button>
							</div>
							<div class="col-sm-2">
								<button class="btn btn-primary" type="button" id="finishButton" onclick="finishFollow()"><i class="fa fa-search"></i>&nbsp;完成</button>
							</div>
						</div>
					</form>
				</c:if>

			</div>
		</div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>

	<script>

		function continueFollow() {
			var content = $("#content").val();
			var orderSn = $("#orderSn").val();
			if(content==null || content==''){
				layer.alert("请填写跟进记录", {icon: 5,time: 3000, title:'提示'});
				return;
			}
            $('#continueButton').attr("disabled","disabled");
			var callBack = function(result){
				if(result.code == 0){
					layer.alert("跟进成功", {icon: 6,time: 2000, title:'提示'});
                    $('#continueButton').removeAttr("disabled");
					window.location.reload();
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
                    $('#continueButton').removeAttr("disabled");
				}
			}
			CommonUtils.ajaxPostSubmit("follow/saveFollow",{'content':content,'orderSn':orderSn,'followStatus':1},callBack);
		}


		function finishFollow() {
			var content = $("#content").val();
			var orderSn = $("#orderSn").val();
			if(content==null || content==''){
				layer.alert("请填写跟进记录", {icon: 5,time: 3000, title:'提示'});
				return;
			}
            $('#finishButton').attr("disabled","disabled");
			var callBack = function(result){
				if(result.code == 0){
					layer.alert("跟进成功", {icon: 6,time: 2000, title:'提示'});
                    $('#finishButton').removeAttr("disabled");
					window.location.reload();
				}else{
					layer.alert(result.msg, {icon: 5,time: 3000, title:'提示'});
                    $('#finishButton').removeAttr("disabled");
				}
			}

			CommonUtils.ajaxPostSubmit("follow/saveFollow",{'content':content,'orderSn':orderSn,'followStatus':2},callBack);
		}
        
	    function isNullOrBlank(obj){
	    	return obj == undefined || obj == null || $.trim(obj).length == 0;
	    }
		
        function toHouseMap() { 
        		  
        	var url='house/houseSearch/houseSearchList?';
        	
        	var houseFid ='${detail.houseFid}'; 
        	var roomFid ='${detail.roomFid}';     
        	var cityCode ='${detail.cityCode}';
        	var areaCode='${detail.areaCode}';
        	var orderStatus='${detail.orderStatus}';
        	var orderType='${detail.orderType}';
        	var peopleNum='${detail.peopleNum}';
        	var rentWay='${detail.rentWay}';
        	var startTime='${detail.startTime}';
        	var endTime='${detail.endTime}'; 
        	var orderSn='${detail.orderSn}'; 
        	
        	
        	if(!isNullOrBlank(cityCode)){
        		url+='&cityCode='+cityCode
        	}
        	if(!isNullOrBlank(orderStatus)){
        		url+='&orderStatus='+orderStatus
        	}
        	if(!isNullOrBlank(orderType)){
        		url+='&orderType='+orderType
        	}
        	if(!isNullOrBlank(peopleNum)){
        		url+='&personCount='+peopleNum
        	}
        	if(!isNullOrBlank(rentWay)){
        		url+='&rentWay='+rentWay;
        		if(!isNullOrBlank(houseFid) && rentWay==0){
            		url+='&houseFid='+houseFid
            	}else if(!isNullOrBlank(roomFid) && rentWay==1){
            		url+='&houseFid='+roomFid
            	}
        		
        	}
        	
        	
        	if(!isNullOrBlank(startTime)){
        		console.log(startTime);
        		console.log(CommonUtils.formatCSTDay(startTime));
        		url+='&startTime='+CommonUtils.formatCSTDay(startTime)
        	}
        	if(!isNullOrBlank(endTime)){
        		url+='&endTime='+CommonUtils.formatCSTDay(endTime)
        	} 
        	if(!isNullOrBlank(orderSn)){
        		url+='&orderSn='+orderSn
        	}
        	
        	
        		  
    		$.openNewTab(new Date().getTime(),url, "找相似房源");
        }
        
	</script>
</body>
</html>
