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
<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
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
						<input type="hidden" id="houseBaseFid" value="${houseBaseFid }"/>
						<input type="hidden" id="picBaseAddr" value="${picBaseAddr }"/>
                        <input type="hidden" id="picBaseAddrMona" value="${picBaseAddrMona }">
                        <input type="hidden" id="picSize" value="${picSize }"/>
                        <input type="hidden" id="picType" value=""/>
						<form id="form" class="form-horizontal">
	                        <input type="hidden" id="surveyFid" name="fid" value="${houseSurveyMsg.fid }"/>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">房源编号:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="houseSn" value="${houseBaseMsg.houseSn }" readonly="readonly"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">房源名称:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control m-b" id="houseName" title='<c:out value="${houseBaseMsg.houseName}" escapeXml="true"/>' value="<c:out value="${houseBaseMsg.houseName}" escapeXml="true"/>" readonly="readonly"/>
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
	                                    <input type="text" class="form-control m-b" id="houseAddr" title='<c:out value="${houseBaseMsg.houseAddr}" escapeXml="true"/>' value="<c:out value="${houseBaseMsg.houseAddr}" escapeXml="true"/>" readonly="readonly"/>
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
					                <div class="col-sm-7">
					                    <div class="lightBoxGallery">
						                 	<div id="surveyPicBox">
							                    <c:forEach items="${picList }" var="pic">
								                   	<div class="picline" id="pic${pic.fid }" >
								                    	<a href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" title="图片" data-gallery="blueimp-gallery">
								                    		<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
								                    	</a>
								                    	<div class="picjz">
								                   			<p><button type='button' class='btn btn-danger btn-xs' onclick="delPic('${pic.fid }');"><span>删除</span></button></p>
								                   		</div>
								                   	</div>
							                    </c:forEach>
						                    </div>
						                    <div style="margin-left: 5px;">
							           			<button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showModal('0');"><span>上传照片</span></button>
						                	</div>
						                </div>
						            </div>
					            </div>
					        </div>
							<div class="row">
								<div class="form-group">
	                                <label class="col-sm-1 control-label mtop">实勘结果:</label>	
	                                <div class="col-sm-2">
	                                    <select class="form-control m-b" id="surveyResult" name="surveyResult">
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
	                                    <input id="surveyDate" name="surveyDate" class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${houseSurveyMsg.surveyDate }" pattern="yyyy-MM-dd"/>">
	                                </div>
	                                <label class="col-sm-1 control-label mtop">实勘专员:</label>
		                            <div class="col-sm-2">
	                                 	<input type="text" class="form-control m-b" id="surveyEmpName" value="${surveyEmp.empName }" readonly="readonly">
	                                </div>
		                        </div>
							</div>
							<div class="row">
								<div class="form-group">
		                            <label class="col-sm-1 control-label">备注:</label>
			                        <div class="col-sm-5">
			                            <textarea rows="3" id="remark" name="remark" class="form-control m-b" onkeydown="calNum(this)" onkeyup="calNum(this)" onchange="calNum(this)">${houseSurveyMsg.remark }</textarea>
			                       	    <p class="textarea-desc-color">字数：<span>0</span><span>/500</span></p>
			                        </div>
		                        </div>
	                        </div>
							<div class="row">
								<customtag:authtag authUrl="house/houseSurvey/updateHouseSurvey">
	                                <div class="col-sm-6" align="center">
		         						<input type="button" id="saveBtn" class="btn btn-primary" value="保存">
	                                </div>
					            </customtag:authtag>
	                        </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--上传弹出框 -->
    <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
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
                            <div class="statusBar" style="display:none;">
                                <div class="progress">
                                    <span class="text">0%</span>
                                    <span class="percentage"></span>
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
    
	<!-- webuploader -->
    <script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/minsu/common/surveyUploader.js?${VERSION}002"></script>
    
    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
    
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
    
    <script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001"></script>  

	<!-- Page-Level Scripts -->
	<script>
		$(function(){
	        calNum($("#remark"));

			//外部js调用
	        laydate({
	            elem: '#surveyDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
	            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
	        });
		});

		//计算数量
		function calNum(obj) {
			var num = $(obj).val().length;
			$(obj).next().children().filter("span").first().text(num);
		}
		
        //显示弹出框
	    function showModal(picType){
	    	$("#picType").val(picType);
		    $('#myModal').modal('toggle');
		    createUploader();
	    }
        
	    //删除实勘图片 
        function delPic(picFid){
        	layer.confirm("确定要删除吗?", {icon: 5, title:'提示'}, function(index) {
	        	var data={"surveyPicFid":picFid};
	        	var binary =  function delPicResult(resultJson){
	            	var code=resultJson.code;
	            	var msg=resultJson.msg;
	            	if(code==1){
	            		msgLayer(msg)
	            	}else if(code==0){
	            		$("#pic"+picFid).remove();
	            		CommonUtils.msgLayer("删除成功");
	            	}
	            }
	        	CommonUtils.ajaxPostSubmit("house/houseSurvey/deleteSurveyPic",data,binary);
	        	layer.close(index);
			});
        }
	    
	    $("#saveBtn").click(function(){
	    	$(this).attr("disabled", "disabled");

    		var picNum = $("#surveyPicBox").children().length;
    		if(picNum == 0){
				layer.alert("至少需要上传一张实勘图片", {icon: 5, title:'提示'});
		        $(this).removeAttr("disabled");
    			return;
    		}
    		
	    	if($("#form").valid()){
	    		updateHouseSurvey();
	    	}
	        $(this).removeAttr("disabled");
	    });
	    
		// 增加校验
		$("#form").validate({
            rules: {
            	"surveyResult": {
                    required: true,
                },
            	"surveyDate": {
                    required: true,
                },
            	"remark": {
                    maxlength: 500
                }
            }
        });

	    function updateHouseSurvey(){
	    	$.ajax({
	            type: "post",
	            url: "house/houseSurvey/updateHouseSurvey",
	            dataType:"json",
	            data: {"jsonStr":toJsonStr($("#form").serializeArray())},
	            success: function (result) {
	            	if(result.code === 0){
	            		$.callBackParent("house/houseSurvey/listSurveyHouse",true,callBack);
					} else {
						layer.alert("操作失败", {icon: 5, title:'提示'});
					}
	            },
	            error: function(result) {
	            	layer.alert("未知错误", {icon: 5, title:'提示'});
	            }
	        });
	    }
	    
	    function toJsonStr(array){
			var json = {};
			$.each(array, function(i, n){
				var key = n.name;
				var value = n.value;
				if($.trim(value).length != 0){
					json[key] = value;
				}
			});
			return JSON.stringify(json);
		}
	    
        function callBack(parent){
        	parent.refreshData("listTable");
        }
	</script>
	<script type="text/javascript">
        // 添加全局站点信息
        var BASE_URL = 'js/plugins/webuploader';
        var UPLOAD_BASE_URL='${basePath}';
    </script>
</body>
</html>