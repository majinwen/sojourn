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

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css?${VERSION}" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
    <link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
    <style>
        .lightBoxGallery img {
            margin: 5px;
            width: 160px;
        }
        .picline{
        	display: inline-block;
        }
        .picjz{
        	display: inline-block;vertical-align: middle;
        }
        .row {
		    display: -webkit-box;
		    display: -webkit-flex;
		    display: -ms-flexbox;
		    display: flex;
		    flex-wrap: wrap;
		}
		.row > [class*='col-'] {
		    display: flex;
		    flex-direction: column;
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
       <div class="wrapper wrapper-content">
        <form action="cityFile/insertColumnRegion" method="post" id="saveColumnRegion">
	   	<input type="hidden"  name="columnCityFid"  value="${columnCityFid }" id="columnCityFid">
	   	<input type="hidden"  value="${cityCode }" id="cityCode">
         <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>选择景点商圈：</h4></div>
                            <div class="col-md-2">
                       	       <select class="form-control m-b" name="regionFid"   onchange="changeRegion();" id="regionFid">
                               		<option value="">请选择</option>
                               <c:forEach items="${regionList }" var="region">
                               		<option value="${region.fid }">${region.regionName }</option>
                               </c:forEach>
                               </select>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>主图：</h4></div>
                            <div class="col-md-2">
                            	<div class="lightBoxGallery"  id="mainPic">
					            </div>
                            </div>
                            <div class="col-md-1 text-center"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showUpLoadPage();"><span>上传主图</span></button></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>景点商圈轮播图：</h4></div>
                            <div class="col-md-6">
                            	<div class="lightBoxGallery"  id="carouselPic">
					            </div>
                            </div>
                            <div class="col-md-1 text-center"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showCarouselPic();"><span>上传轮播图</span></button></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>选择推荐项目：</h4></div>
                            <div class="col-md-2">
                                <select class="form-control m-b" id="regionItem" >
                                		<option value=''>请选择</option>
                                </select>
                                <button type='button'  class='btn btn-primary'   onclick="addRegionItem();"><span>添加项目</span></button>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-9">
                                <h4>已推荐项目：</h4>
                                <div id="items">
                                </div>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>房源编码：</h4></div>
                            <div class="col-md-4"><input name="houseFids" value=""  type="text" class="form-control m-b" >&nbsp;&nbsp;多个以逗号(半角)隔开</div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>展示房源数量：</h4></div>
                            <div class="col-md-2"><input name="showHouseNum" value=""  type="text" class="form-control m-b" ></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>备用字段：</h4></div>
                            <div class="col-md-2"><input name="standbyField" value=""  type="text" class="form-control m-b" ></div>
                        </div>                    
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-2">
	             <customtag:authtag authUrl="cityFile/insertColumnRegion"><button class="btn btn-primary" type="submit" >保存内容</button></customtag:authtag>
	        </div>
         </div>
         </form>
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
	   <script type="text/javascript">
         // 添加全局站点信息
       	 var BASE_URL = 'js/plugins/webuploader';
         var UPLOAD_BASE_URL='${basePath}';
       </script>
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/columnWebuploader.js?${VERSION}"></script>
	
	   <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   
	   <!-- Bootstrap table -->
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	
	   <!--  validate -->
	   <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/Math.uuid.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>
	   
	   <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}"></script>
	   <!-- blueimp gallery -->
       <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   
       <script type="text/javascript">
		
		$("#saveColumnRegion").validate({
		    rules: {
		    	regionFid: {
		          required: true
		        },
		        showHouseNum: {
		          required: true
		        }
		    },
			submitHandler:function(){
				if($("input[name='mainPicParam']").length==0){
					layer.alert('请上传主图', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if($("input[name='carouselPicParam']").length==0){
					layer.alert('请上传轮播图', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if($("input[name='itemPicParam']").length==0){
					layer.alert('请完善推荐项目图标', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				$.ajax({
		            type: "POST",
		            url: "cityFile/insertColumnRegion",
		            dataType:"json",
		            data: $("#saveColumnRegion").serialize(),
		            success: function (result) {
		            	if(result.code==0){
							$.callBackParent('cityFile/columnRegionList?columnCityFid='+$("#columnCityFid").val()+'&cityCode='+$("#cityCode").val(),true,saveColumnRegionCallback);
		            	}else{
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
		}});
		//保存商机刷新父页列表
		function saveColumnRegionCallback(panrent){
			panrent.refreshData("listTable");
		}
		
        //显示弹出框
	    function  showUpLoadPage(){
		    $('#myModal').modal('toggle');
		    createUploader(0);
	    }
        
        //显示上传轮播图弹出框
        function showCarouselPic(){
		    $('#myModal').modal('toggle');
		    createUploader(1);
        }
        
        //添加推荐项目
        function addRegionItem(){
        	var regionItemFid=$("#regionItem").val();
        	var regionItemName=$("#regionItem").find("option:selected").text();
        	if($("#item"+regionItemFid).length==0&&$("#regionItem").val()!=''){
	        	var itemHtml="<div class='row show-grid' id='item"+regionItemFid+"'><div class='col-md-2'><h4>项目名称：</h4></div><div class='col-md-2'>"+regionItemName+"</div>"
	        	+"<div class='col-md-2'><h4>项目图标：</h4></div><div class='col-md-4'><div class='lightBoxGallery'  id='itemPic"+regionItemFid+"'></div>"
	        	+"<button type='button'  class='btn btn-primary' data-target='#myModal'  onclick='showItemPic(\""+regionItemFid+"\");'><span>上传项目图标</span></button></div>"
	        	+"<div class='col-md-2'><button type='button'  class='btn btn-primary' onclick='delRegionItem(\""+regionItemFid+"\");'><span>删除项目</span></button></div></div>";
	        	$("#items").append(itemHtml);
        	}
        }
        
        //显示上传项目图标弹出框
        function showItemPic(regionItemFid){
		    $('#myModal').modal('toggle');
		    createUploader(2,regionItemFid);
        }
        
        //删除图片
        function delMainPic(picUid){
        	$("#"+picUid).remove();
        }
        
        //删除项目
        function delRegionItem(regionItemFid){
        	$("#item"+regionItemFid).remove();
        }
        
        //改变商圈景点
        function changeRegion(){
        	$("#regionItem").empty();
        	$.ajax({
	            type: "POST",
	            url: "cityFile/getItemsByHotRegionFid",
	            dataType:"json",
	            data: {"regionFid":$("#regionFid").val()},
	            success: function (result) {
	            	if(result.code==0){
	            		$("#regionItem").append("<option value=''>请选择</option>");
						for(var i=0;i<result.data.items.length;i++){
							$("#regionItem").append("<option value='"+result.data.items[i].fid+"'>"+result.data.items[i].itemName+"</option>"); 
						}
	            	}else{
	            		$("#regionItem").append("<option value=''>请选择</option>");
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
