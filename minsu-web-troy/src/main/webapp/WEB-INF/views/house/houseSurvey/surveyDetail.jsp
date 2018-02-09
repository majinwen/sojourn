<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style>
    .textarea-desc-color {
		position: absolute;
		right: 20px;
		bottom: 6px;
		color: cc3366;
		font-size: 6px
	}
	
	.lightBoxGallery img {
		margin: 5px;
		width: 160px;
	}
	
	.picline {
		display: inline-block;
	}
	
	.picjz {
		display: inline-block;
		vertical-align: middle;
	}
</style>
</head>

<body class="gray-bg">
	<div id="blueimp-gallery" class="blueimp-gallery">
       <div class="slides"></div>
       <h3 class="title"></h3>
       <a class="prev">‹</a>
       <a class="next">›</a>
       <a class="close">×</a>
       <a class="play-pause"></a>
       <ol class="indicator"></ol>
    </div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="form-group">
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">房源编号:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="houseSn" value="${houseBaseMsg.houseSn }" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">房源名称:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="houseName" value="${houseBaseMsg.houseName }" readonly="readonly"/>
	                                </div>
		                        </div>
		                    </div>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">房间编号:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="roomsSn" value="${roomsSn }" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">房源地址:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="houseAddr" value="${houseBaseMsg.houseAddr }" readonly="readonly"/>
	                                </div>
		                        </div>
		                    </div>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">房东姓名:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="landlordName" value="${landlord.realName }" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">房东手机:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="landlordMobile" value="${landlord.customerMobile }" readonly="readonly"/>
	                                </div>
		                        </div>
							</div>
							<div class="row">
					            <div class="form-group">
					            	<label class="col-sm-1 control-label">图片</label>
					                <div class="col-sm-6">
					                    <div class="lightBoxGallery">
						                 	<div id="surveyPicBox${houseSurveyMsg.fid }">
							                    <c:forEach items="${picList }" var="pic">
								                   	<div class="picline" id="pic${pic.fid }" >
								                    	<a href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" title="图片" data-gallery="blueimp-gallery">
								                    		<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
								                    	</a>
								                   	</div>
							                    </c:forEach>
						                    </div>
						                </div>
						            </div>
					            </div>
					        </div>
					        <br/>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">是否符合品质:</label>	
	                                <div class="col-sm-2">
	                                    <select class="form-control m-b" id="surveyResult" name="surveyResult" disabled="disabled">
		                                    <option value="">请选择</option>
		                                    <c:forEach items="${surveyResultMap }" var="obj">
		                                    	<option value="${obj.key }" <c:if test="${obj.key == houseSurveyMsg.surveyResult }">selected</c:if>>${obj.value }</option>
		                                    </c:forEach>
	                                    </select>
	                                </div>
		                        </div>
							</div>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">实勘日期:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="surveyDate" name="surveyDate" value="<fmt:formatDate value="${houseSurveyMsg.surveyDate }" pattern="yyyy-MM-dd"/>" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">实勘运营专员:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" name="surveyEmpName" value="${surveyEmp.empName }" readonly="readonly"/>
	                                </div>
		                        </div>
							</div>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">审阅日期:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="auditDate" value="<fmt:formatDate value="${houseSurveyMsg.auditDate }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">审阅人:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="auditEmpName" value="${auditEmp.empName }" readonly="readonly"/>
	                                </div>
		                        </div>
							</div>
							<div class="row">
								<div class="form-group">
		                            <label class="col-sm-1 control-label">备注:</label>
			                        <div class="col-sm-5">
			                            <textarea rows="3" id="remark" name="remark" class="form-control m-b" onkeydown="calNum(this)" onkeyup="calNum(this)" onchange="calNum(this)" readonly="readonly">${houseSurveyMsg.remark }</textarea>
			                       	    <p class="textarea-desc-color">字数：<span>0</span><span>/500</span></p>
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
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	<!-- blueimp gallery -->
    <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>

	<!-- Page-Level Scripts -->
	<script>
		$(function(){
	        calNum($("#remark"));
		});

		//计算数量
		function calNum(obj) {
			var num = $(obj).val().length;
			$(obj).next().children().filter("span").first().text(num);
		}
	</script>
</body>
</html>