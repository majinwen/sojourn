<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <style>
		.lightBoxGallery img {margin: 5px;width: 160px;}
		.room-pic {float: left;}
		.room-pic p {text-align: center;}
		.blueimp-gallery>.title {left: 0;bottom: 45px;top: auto;width: 100%;text-align: center;}
		.picline {display: inline-block;}
		.picjz {display: inline-block;vertical-align: middle;}
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
                    <div class="ibox-title">
                        <h5>房源照片查看</h5>
                    </div>
                    <div class="ibox-content">
                        <form id="housePicForm" method="post" class="form-horizontal">
                        <input type="hidden"  id="houseBaseFid" value="${housePicAuditVo.fid}" >
                        <input type="hidden"  id="roomFid" value="">
                        <input type="hidden"  id="picType" value="">
                        <input type="hidden"  id="picBaseAddr" value="${picBaseAddr }">
                        <input type="hidden"  id="picBaseAddrMona" value="${picBaseAddrMona }">
                        <input type="hidden"  id="picSize" value="${picSize }">
                        <input type="hidden"  id="picWaterM" value="${picWaterM }">
                        <input type="hidden"  id="houseRentWay" value="${housePicAuditVo.rentWay }">
                        <input type="hidden"  id="houseRoomFid" value="${ houseRoomFid}"/>
                        <input type="hidden"  id="operateType" value="${ operateType}"/>
                        	<div class="row">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">房东名称</label>
	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" value="${housePicAuditVo.landlordName }" disabled="">
	                                </div>
	                                <label class="col-sm-2 control-label">房东电话</label>
	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" value="${housePicAuditVo.landlordMobile }" disabled="">
	                                </div>
	                            </div>
                            </div>
                            <span class="help-block m-b-none">房源基本情况</span>
                            <div class="hr-line-dashed"></div>
                            <div class="row">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">楼盘名称</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" value="${housePicAuditVo.communityName }" disabled="">
	                                </div>
	                                <label class="col-sm-2 control-label">房源名称</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" value="${housePicAuditVo.houseName }" disabled="">
	                                </div>
	                            </div>
                            </div>
                            <div class="row">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">详细地址</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" value="${housePicAuditVo.houseAddr }" disabled="">
	                                </div>
	                                <label class="col-sm-2 control-label">房源户型</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" disabled="" value="${housePicAuditVo.houseModel }">
	                                </div>
	                            </div>
                            </div>
                            <div class="row">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">出租类型</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" disabled="" value="${housePicAuditVo.rentWayStr }">
	                                </div>
	                            </div>
                            </div>
                            <span class="help-block m-b-none">照片上传</span>
                            <div class="hr-line-dashed"></div> 
                            <div class="row">
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">摄影师姓名：</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control"  id="cameramanName" name="cameramanName" value="${housePicAuditVo.cameramanName }">
	                                    <c:set var="cameramanSavedDef" value="false"></c:set>
	                                    <c:if test="${housePicAuditVo.cameramanName!=null && housePicAuditVo.cameramanMobile!=null }">
	                                    	<c:set var="cameramanSavedDef" value="true"></c:set>
	                                    </c:if>
	                                    
	                                    <input type="hidden" id="cameramanSaved" value="${cameramanSavedDef }"  >
	                                </div>
	                                <div class="col-sm-2">
	                                    <input type="checkbox" id="isLandlord" onclick=""><label class="control-label">照片由房东提供</label>
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label">摄影师电话：</label>
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control"  id="cameramanMobile" name="cameramanMobile" value="${housePicAuditVo.cameramanMobile }">
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label class="col-sm-2 control-label"></label>
	                                <div class="col-sm-2">
	                                    <customtag:authtag authUrl="house/houseMgt/insertCameramanMsg"><button type='button' id="saveCameramanBtn" class='btn btn-primary' onclick="saveCameraman()"><span>保存摄影师</span></button></customtag:authtag>
	                                </div>
	                                <c:if test="${not empty picRules }">
		                                <div class="col-sm-6" style="color:#F00">
		                                	<label class="control-label">${picRules }</label>
		                                </div>
	                                </c:if>
	                            </div>
                     		</div>
                            <c:forEach items="${housePicAuditVo.roomPicList }" var="room" varStatus="status">
                            	<div class="row">
		                            <div class="form-group">
		                                <label class="col-sm-2 control-label">房间名称</label>
		                                <div class="col-sm-2">
		                                    <input type="text" class="form-control" disabled="" value="${room.picTypeName }">
		                                </div>
		                            </div>
	                            </div>
	                            <div class="row">
		                            <div class="form-group">
		                            	<label class="col-sm-2 control-label">房间图片</label>
		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
			                                	<div id="roomPicBox${room.roomFid }">
					                                <c:forEach items="${room.picList }" var="pic">
					                                	<div class="picline" id="pic${pic.fid }" >
						                                	<a href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" <c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize and !empty pic.widthDpi and !empty pic.heightDpi }">title="${pic.widthPixel }*${pic.heightPixel } W:${pic.widthDpi }DPI H:${pic.heightDpi }DPI ${pic.picSize }KB"</c:if> title="图片" data-gallery="blueimp-gallery${room.roomFid }">
						                                		<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
						                                		<c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize }">
							                                		<p align="center">${pic.widthPixel }*${pic.heightPixel } ${pic.picSize }KB</p>
							                                	</c:if>
						                                	</a>
						                                	<div class="picjz">
					                                			<p><button type='button' class='btn btn-danger btn-xs' onclick="delPic('${pic.fid }','${pic.picType }','${room.roomFid }');"><span>删除</span></button></p>
					                                			<input type="hidden" class="${room.roomFid }roomPicFids" disabled="" value="${pic.fid }">
					                                			<c:if test="${pic.fid != housePicAuditVo.defaultPicFid}">
							                                		<p><button type='button' class='btn btn-info btn-xs' onclick="setDefaultPic('${pic.fid }','${pic.houseBaseFid }','${room.roomFid }','${pic.picType }');"><span>设为默认</span></button></p>
					                                			</c:if>
					                                		</div>
					                                	</div>
					                                </c:forEach>
				                                </div>
					                        </div>
					                        <button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showHousePhy('${room.roomFid}','${room.picType }');"><span>上传照片</span></button>
					                        <button type='button'  class='btn btn-danger' data-target="#myModal"  onclick="delAll('${room.picType }','${room.roomFid}');"><span>删除所有</span></button>
		                                </div>
		                            </div>
	                            </div>
                            </c:forEach>
                            <c:forEach items="${housePicAuditVo.housePicList }"  var="house" varStatus="status">
                            	<div class="row">
		                            <div class="form-group">
		                            	<label class="col-sm-2 control-label">${house.picTypeName }</label>
		                                <div class="col-sm-9">
			                                <div class="lightBoxGallery">
			                                	<div id="picTypeBox${house.picType }">
					                                <c:forEach items="${house.picList }" var="pic">
					                                	<div  class="picline" id="pic${pic.fid }" >
					                                		<a href="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" <c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize and !empty pic.widthDpi and !empty pic.heightDpi }">title="${pic.widthPixel }*${pic.heightPixel } W:${pic.widthDpi }DPI H:${pic.heightDpi }DPI ${pic.picSize }KB"</c:if> title="图片" data-gallery="blueimp-gallery${house.picType }">
					                                			<img src="${picBaseAddrMona}${pic.picBaseUrl }${pic.picSuffix}${picSize}.jpg" style="height: 135px; width: 165px;">
					                                			<c:if test="${!empty pic.widthPixel and !empty pic.heightPixel and !empty pic.picSize }">
							                                		<p align="center">${pic.widthPixel }*${pic.heightPixel } ${pic.picSize }KB</p>
							                                	</c:if>
					                                		</a>
					                                		<div class="picjz">
					                                			<p><customtag:authtag authUrl="house/houseMgt/deleteHousePic"><button type='button'  class='btn btn-danger btn-xs' onclick="delPic('${pic.fid }','${pic.picType }');"><span>删除</span></button></customtag:authtag></p>
					                                			<input type="hidden" class="${pic.picType }housePicFids" disabled="" value="${pic.fid }">
					                                			<c:if test="${pic.fid != housePicAuditVo.defaultPicFid}">
							                                		<p><customtag:authtag authUrl="house/houseMgt/housePicAuditDetail"><button type='button'  class='btn btn-info btn-xs' onclick="setDefaultPic('${pic.fid }','${pic.houseBaseFid }','${room.roomFid }','${pic.picType }');"><span>设为默认</span></button></customtag:authtag></p>
					                                			</c:if>
					                                		</div>
					                                	</div>
					                                	
					                                </c:forEach>
				                                </div>
					                        </div>
					                        <div style="margin-left: 5px;">
						                        <button type='button' class='btn btn-primary' data-target="#myModal"  onclick="showHousePhy('${house.roomFid}','${house.picType }');"><span>上传照片</span></button>
						                        <button type='button' class='btn btn-danger' data-target="#myModal"  onclick="delAll('${house.picType }');"><span>删除所有</span></button>
						                        
					                        </div>
		                                </div>
		                            </div>
	                            </div>
                            </c:forEach>
                            
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

    <!-- 自定义js -->
    <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>

    <!-- iCheck -->
    <script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}"></script>
    
    <!-- blueimp gallery -->
    <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
    
    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>    
    <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}001"></script>  

    <script> 
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
            $("#housePicForm").validate({
    	        rules: {
    	        	cameramanName: nameRules,
    	            cameramanMobile: mobileRules
    	        },
    	        messages: { 
    	  	      cameramanName: {
    	  	        required: "请输入摄影师姓名",
    	  	        maxlength: "姓名过长"
    	  	      },
    	  	      cameramanMobile: {
    	  	        required: "请输入摄影师手机号",
    	  	        isMobile: "手机号码不正确"
    	  	      } 
    	  	    }
    	    });
            
        });
        
        //显示弹出框
	    function showHousePhy(roomFid,picType){
        	
	    	if(!$("#housePicForm").valid() || !$("#cameramanSaved").val() || $("#cameramanSaved").val()=='false'){
	    		layer.alert("请先保存摄影师姓名和电话", {icon: 5,time: 2000, title:'提示'});
		    	return;
	    	}
	    	
	    	
	    	
	    	
        	$("#roomFid").val(roomFid);
        	$("#picType").val(picType);
		    $('#myModal').modal('toggle');
		    createUploader();
	    }
        //删除图片 
        function delPic(picFid,picType,roomFid){
        	var houseRoomFid=$("#houseRoomFid").val();
        	var rentWay = $("#houseRentWay").val();
        	layer.confirm("确定要删除吗?", {icon: 5, title:'提示'}, function(index) {
	        	var data={"houseBaseFid":$("#houseBaseFid").val(),"houseRoomFid":roomFid,"housePicFid":picFid,"picType":picType,"roomFid":houseRoomFid,"rentWay":rentWay};
	        	var binary =  function delPicResult(resultJson){
	            	var code=resultJson.code;
	            	var msg=resultJson.msg;
	            	if(code==1){
	            		msgLayer(msg)
	            	}else if(code==0){
	            		$("#pic"+picFid).remove();
	            		msgLayer("删除成功");
	            	}
	            }
	        	ajaxPostSubmit("house/houseMgt/deleteHousePic",data,binary);
	        	layer.close(index);
			});
        }
        
        
      	//批量删除图片
		function delAll(picType,roomFid){
			var houseRoomFid=$("#houseRoomFid").val();
			var houseBaseFid=$("#houseBaseFid").val();
			var rentWay = $("#houseRentWay").val();
			var picFids = "";
			if(picType == "0"){
				  $("."+roomFid+"roomPicFids").each(function(){
				     picFids = picFids + $(this).val()+"-";
				  });
			}else{
				$("."+picType+"housePicFids").each(function(){
				     picFids = picFids + $(this).val()+"-";
				  });
			}
			layer.confirm("确定要删除吗?", {icon: 5, title:'提示'}, function(index) {
				var data={"houseBaseFid":houseBaseFid,"houseRoomFid":roomFid,"picFids":picFids,"picType":picType,"roomFid":houseRoomFid,"rentWay":rentWay};
				var binary =  function delAllResult(resultJson){
					var code=resultJson.code;
					var msg=resultJson.msg;
					if(code==1){
						msgLayer(msg);
					}else if(code==0){
						for(var i=0;i<resultJson.data.okPicList.length;i++){
							$("#pic"+resultJson.data.okPicList[i]).remove();
						}
						msgLayer("删除成功");
					}
				}
				ajaxPostSubmit("house/houseMgt/batchDeletes",data,binary);
				layer.close(index);
			});
		}
        //设置默认图片
        function setDefaultPic(picFid,houseBaseFid,roomFid,picType){
        	var houseRoomFid=$("#houseRoomFid").val();
        	$.post("house/houseMgt/defaultPic",{housePicFid:picFid,picType:picType,houseRoomFid:houseRoomFid,houseBaseFid:houseBaseFid,operateSource:2},function(data){
        		if(data.code == 0){
        			var rentWay = $("#houseRentWay").val();
					var operateType = $("#operateType").val();
					if(isNullOrBlank(operateType)){
						operateType =1;
					}
        			//跳转不同页
        			if(rentWay == 0){
        				window.location.href = "house/houseMgt/housePicAuditDetail?houseBaseFid="+houseBaseFid+"&operateType="+operateType;
        			}else if(rentWay == 1){
        				window.location.href = "house/houseMgt/housePicAuditDetail?houseRoomFid="+houseRoomFid+"&operateType="+operateType;
        			}
        		}else{
        			layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
        		}
        	},"json");
        }
		function isNullOrBlank(obj){
			return obj == undefined || obj == null || $.trim(obj).length == 0;
		}
        //保存摄影师
        function saveCameraman(){
        		 
        	if(!$("#housePicForm").valid()){
        		return;
        	}
        		
        	
        	var data={"houseBaseFid":$("#houseBaseFid").val(),"cameramanName":$("#cameramanName").val(),"cameramanMobile":$("#cameramanMobile").val()};
        	var binary =  function saveCameramanResult(resultJson){
            	var code=resultJson.code;
            	var msg=resultJson.msg;
            	if(code==1){
            		layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
            	}else if(code==0){
            		$("#cameramanSaved").val("true");
            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
            		parent.$('iframe').filter('[src="house/houseMgt/listHouseMsgUpPic"]')[0].contentWindow.refresh();
            	}
            }
        	ajaxPostSubmit("house/houseMgt/insertCameramanMsg",data,binary);
        }
        
    	var nameRules = {
                required: true,
                maxlength: 20
            };
    	var mobileRules = {
            	required: true,
            	isMobile: true
            };
    	
    	var initName = "${housePicAuditVo.cameramanName }";
    	var initMobile = "${housePicAuditVo.cameramanMobile }";
        $("#isLandlord").click(function(){
        	var isChecked = $(this).is(':checked');
        	if(isChecked){
        		
        		//$("#cameramanName").rules("remove", "required maxlength");
        		//$("#cameramanMobile").rules("remove", "required isMobile");
        		//$("#saveCameramanBtn").hide();
        		//$("#cameramanSaved").val(true);
        		
        		$("#cameramanName").attr("readonly","readonly");
        		$("#cameramanMobile").attr("readonly","readonly");
        		$("#cameramanName").val("房东");
        		$("#cameramanMobile").val("13333333333");
        	} else {
        		//$("#cameramanName").rules("add", nameRules);
        		//$("#cameramanMobile").rules("add", mobileRules);
        		//$("#saveCameramanBtn").show();
        		
        		//var valid = !checkNull($("#cameramanName").val()) && !checkNull($("#cameramanMobile").val());
        		//$("#cameramanSaved").val(valid);
        		
        		var cameramanName = $("#cameramanName").val();
        		var cameramanMobile = $("#cameramanMobile").val();
        		if(cameramanName === '房东' && cameramanMobile === '13333333333'){
        			$("#cameramanName").val(initName)
        			$("#cameramanMobile").val(initMobile)
        		}
        		
        		$("#cameramanName").removeAttr("readonly");
        		$("#cameramanMobile").removeAttr("readonly");
        	}
        });
        
        function checkNull(val){
        	return typeof val == 'undefined' || val == null || $.trim(val).length == 0;
        }
    </script>
    <script type="text/javascript">
        // 添加全局站点信息
        var BASE_URL = 'js/plugins/webuploader';
        var UPLOAD_BASE_URL='${basePath}';
    </script>
    <script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}001"></script>
    
    <!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}001"></script>
    
    <script src="${staticResourceUrl}/js/minsu/common/webuploader.js?${VERSION}002"></script>
    <script src="resources/js/content.js?${VERSION}001"></script>
</body>
</html>
