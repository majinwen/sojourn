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
        <form action="house/houseTop/saveTopHouse" method="post" id="saveTopHouse">
	   	<input type="hidden"  name="houseBaseFid"  value="${houseBaseFid }" >
	   	<input type="hidden"  name="roomFid"  value="${roomFid }" >
	   	<input type="hidden"  name="rentWay"  value="${rentWay }" >
         <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>请选择标题：</h4></div>
                            <div class="col-md-11">
                       	       <select class="form-control m-b" name="topTitle">
	                               <c:forEach items="${resList }" var="title">
	                               		<option value="${title.resContent }" >${title.resContent }</option>
	                               </c:forEach>
                               </select>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>标题图片：</h4></div>
                            <div class="col-md-11">
                            	<div class="lightBoxGallery"  id="mainPic">
                            	<c:forEach items="${titlePicList }" var="titlePic" varStatus="status">
									<div class='picline' ><input type="radio" name="topTitlePic"  ${status.index==0?'checked':'' } value="${titlePic.picUrl }" ><a href='${picBaseAddrMona }${titlePic.picBaseUrl }${titlePic.picSuffix}${picSize}${titlePic.picSuffix}' title='主图' data-gallery='blueimp-gallery-main'><img src='${picBaseAddrMona }${titlePic.picBaseUrl }${titlePic.picSuffix}${picSize}${titlePic.picSuffix}' style='height: 135px; width: 165px;'></a></div>
                            	</c:forEach>					            
                            	</div>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>中部图片：</h4></div>
                            <div class="col-md-11">
                            	<div class="lightBoxGallery"  id="carouselPic">
                            	<c:forEach items="${middlePicList }" var="mPic" varStatus="status" >
									<div class='picline' ><input type="radio" name="topMiddlePic"  ${status.index==0?'checked':'' } value="${mPic.picUrl }"  ><a href='${picBaseAddrMona }${mPic.picBaseUrl }${mPic.picSuffix}${picSize}${mPic.picSuffix}' title='主图' data-gallery='blueimp-gallery-main'><img src='${picBaseAddrMona }${mPic.picBaseUrl }${mPic.picSuffix}${picSize}${mPic.picSuffix}' style='height: 135px; width: 165px;'></a></div>
                            	</c:forEach>                            	
					            </div>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>房源标签：</h4></div>
                            <div class="col-md-11">
                            	<div>
                            	<c:forEach items="${tagList }" var="tag">
                            		<input type="checkbox" name="tagFids"  value="${tag.fid }|${tag.tagType }">&nbsp;&nbsp;${tag.tagName }
                            	</c:forEach>
                            	</div>
                            </div>
                        </div>
                        <div class="row show-grid">
                        	<div class="col-md-1 text-center"><h4>分享标题：</h4></div>
							<div class="col-md-11"><input name="topShareTitle" value=""  type="text" class="form-control m-b" ></div>
                        </div>
                        <div class="row show-grid">
                        	<div class="col-md-1 text-center"><h4>亮点标题：</h4></div>
							<div class="col-md-11"><input name="sternTitle" value=""  type="text" class="form-control m-b" ></div>
                        </div>
                        <div class="row show-grid">
                        	<div class="col-md-1 text-center"><h4>亮点文本：</h4></div>
							<div class="col-md-11"><textarea id="sternContent"  name="sternContent" rows="8"  class="form-control m-b" ></textarea></div>
                        </div>
                        <div class="row show-grid">
                        	<div class="col-md-1 text-center"><h4>封面图片：</h4><input type="hidden" id="coverPicParam" name="coverPicParam"></div>
							<div class="col-md-10"  id="coverPic"></div>
							<div class="col-md-1"><button type='button'  class='btn btn-primary'   onclick="showUpLoadPage(2);"><span>上传图片</span></button></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-12">
                                <h4>已添加条目：</h4>
                                <div id="cc" style="height: 500px; overflow: auto;">
	                                <div>
	                                <ul class="sortable-list connectList agile-list ui-sortable" id="items">
	                                </ul>
	                                </div>
                                </div>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>条目类型：</h4></div>
                            <div class="col-md-1">
                       	       <select class="form-control m-b" onchange="checkColumnType();" id="columnType">
                               		<option value="">请选择</option>
                               <c:forEach items="${columnTypeEnum }" var="type">
                               <c:if test="${type.key!=101&&type.key!=201&&type.key!=302 }">
                               		<option value="${type.key }">${type.value }</option>
                               </c:if>
                               </c:forEach>
                               </select>
                            </div>
                            <div class="col-md-1 text-center"><h4>条目样式：</h4></div>
                            <div class="col-md-1">
                       	       <select class="form-control m-b"  id="columnStyle">
                               <c:forEach items="${columnStyleEnum }" var="style">
                               		<option value="${style.key }">${style.value }</option>
                               </c:forEach>
                               </select>
                            </div>
                            <div class="col-md-1 title">
                            	<h4>标题内容：</h4>
                            </div>
                            <div class="col-md-2 title">
                            	<input id="titleContent" value=""  type="text" class="form-control m-b" >
                            </div>
                            <div class="col-md-1 pic">
                            	<h4>图片：<font color="red">头图需上传格式：1200_1800，其他图片上传格式：1200_800</font></h4>
                            </div>
                              <div class="col-md-2 pic">
                                 <input type="hidden" id="itemPic">
                                 <input type="hidden" id="itemPicUrl">
                            	<div class="lightBoxGallery"  id="columnPic">
					            </div>
                            </div>
                            <div class="col-md-1 text-center pic"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showUpLoadPage(1);"><span>上传主图</span></button></div>
                            <div class="col-md-1 text">
                            	<h4>文本内容：</h4>
                            </div>
                            <div class="col-md-3 text">
                            	<textarea id="textContent" rows="10"  class="form-control m-b" ></textarea>
                            </div>
                            <div class="col-md-1"><button type='button'  class='btn btn-primary'   onclick="addTopColumn();"><span>添加条目</span></button></div>
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
	   <script src="${staticResourceUrl}/js/jquery-ui-1.10.4.min.js${VERSION}001"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}001"></script>
	   <script src="js/minsu/common/topColumnWebuploader.js?${VERSION}006"></script>
	
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
       <!-- ckeditor.js-->
       <script type="text/JavaScript" src="${staticResourceUrl}/ckeditor/ckeditor.js"></script>

       <script type="text/javascript">
		
		$("#saveTopHouse").validate({
		    rules: {
		    	topShareTitle: {
		          required: true,
		          maxlength:400
		        },
		        sternTitle: {
			          required: true,
			          maxlength:400
			    },
			    sternContent: {
			          required: true,
			          maxlength:3000
			    }
		    },
			submitHandler:function(){
				if($("#coverPicParam").val().length==0){
					layer.alert('请上传封面图片', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				$.ajax({
		            type: "POST",
		            url: "house/houseTop/saveTopHouse",
		            dataType:"json",
		            data: $("#saveTopHouse").serialize(),
		            success: function (result) {
		            	if(result.code==0){
							$.callBackParent('house/houseTop/houseTopList',true,saveColumnRegionCallback);
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
	    function  showUpLoadPage(obj){
		    $('#myModal').modal('toggle');
		    console.log(obj);
		    createUploader(obj);
	    }
               
        
        function isNullOrBlank(obj){
            return obj == undefined || obj == null || $.trim(obj).length == 0;
        }

        
        //删除项目
        function delColumnItem(regionItemFid){
        	$("#item"+regionItemFid).remove();
        }
        
        $(document).ready(function () {
            $(".sortable-list").sortable({
                connectWith: ".connectList",
                axis: 'y',
                containment:'parent',
                scroll: true,
                scrollSensitivity:50
            }).disableSelection();
            $("#cc>div").scroll(function(e) {
                $( "#sortable-list").sortable( "refresh" ); //触发滚动时刷新位置信息
                //$( "#sortable2").sortable( "refreshPositions" ); //同上，使用其中一个即可
            });
            $(".title").hide();
            $(".text").hide();
            $(".pic").hide();
        });
        //改变条目类型
        function checkColumnType(){
        	var type=$("#columnType").val();
        	$("#titleContent").val('');
        	$("#textContent").val('');
        	$("#itemPic").val('');
        	$("#columnStyle").val('101');
        	$("#itemPicUrl").val('');
        	if(type.substring(0,1)=='1'){
        		$(".title").show();
                $(".text").hide();
                $(".pic").hide();
        	} else if(type.substring(0,1)=='2'){
        		$(".title").hide();
                $(".text").show();
                $(".pic").hide();
        	} else if(type.substring(0,1)=='3'){
        		$(".title").hide();
                $(".text").hide();
                $(".pic").show();
        	} else if(type==401||type==501){
        		$(".title").hide();
                $(".text").show();
                $(".pic").show();  
        	} else {
                $(".title").hide();
                $(".text").hide();
                $(".pic").hide();
        	}
        }
        //添加条目
        function addTopColumn(){
        	var columnType=$("#columnType").val();
        	var columnTypeText=$("#columnType").find("option:selected").text(); 
        	var columnStyle=$("#columnStyle").val();
        	var columnStyleText=$("#columnStyle").find("option:selected").text(); 
        	var titleContent=$("#titleContent").val();
        	var textContent=$("#textContent").val();
        	var itemPic=$("#itemPic").val();
        	var itemPicUrl=$("#itemPicUrl").val();
        	var columnText="";
        	//拼接参数
        	var columnParam="";
        	if(isNullOrBlank(columnType)){
        		layer.alert("请选择条目类型！", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if(isNullOrBlank(columnStyle)){
        		layer.alert("请选择条目样式！", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if(columnType.substring(0,1)=='1'){
            	if(isNullOrBlank(titleContent)){
            		layer.alert("请填写标题内容！", {icon: 6,time: 2000, title:'提示'});
            		return;
            	}
            	columnParam=columnType+"|"+columnStyle
            	columnText="<div class='col-md-1'><h4>标题内容：</h4></div><div class='col-md-2'><input  name='columnContent' value='"+titleContent+"'  type='text' class='form-control m-b' ></div>";
        	} else if(columnType.substring(0,1)=='2'){
            	if(isNullOrBlank(textContent)){
            		layer.alert("请填写文本内容！", {icon: 6,time: 2000, title:'提示'});
            		return;
            	}
            	columnParam=columnType+"|"+columnStyle
            	columnText="<div class='col-md-1'><h4>文本内容：</h4></div><div class='col-md-2'><textarea name='columnContent' rows='10'  class='form-control m-b'>"+textContent+"</textarea></div>";
        	} else if (columnType.substring(0,1)=='3'){
            	if(isNullOrBlank(itemPic)){
            		layer.alert("请上传图片！", {icon: 6,time: 2000, title:'提示'});
            		return;
            	}
            	columnParam=columnType+"|"+columnStyle+"|"+itemPic;
            	columnText="<div class='col-md-1'><h4>图片：</h4></div><div class='col-md-2'><a href='"+itemPicUrl+"' title='图片' data-gallery='blueimp-gallery-main'><img src='"+itemPicUrl+"' style='height: 135px; width: 165px;'></a><input  name='columnContent' value='"+itemPic+"'  type='hidden' class='form-control m-b' ></div>";
        	} else if(columnType==401||columnType==501){
            	if(isNullOrBlank(itemPic)){
            		layer.alert("请上传图片！", {icon: 6,time: 2000, title:'提示'});
            		return;
            	}
            	if(isNullOrBlank(textContent)){
            		layer.alert("请填写视频地址！", {icon: 6,time: 2000, title:'提示'});
            		return;
            	}
            	columnParam=columnType+"|"+columnStyle+"|"+itemPic;
            	columnText="<div class='col-md-1'><h4>文本内容：</h4></div><div class='col-md-2'><textarea name='columnContent' rows='10'  class='form-control m-b'>"+textContent+"</textarea></div>"
            	+"<div class='col-md-1'><h4>图片：</h4></div><div class='col-md-2'><a href='"+itemPicUrl+"' title='图片' data-gallery='blueimp-gallery-main'><img src='"+itemPicUrl+"' style='height: 135px; width: 165px;'></a></div>";
        	}
            var columnUid="cn"+guid();
        	var itemHtml="<li class='warning-element' id='item"+columnUid+"'><div class='row show-grid'><div class='col-md-1'><h4>条目类型：</h4></div><div class='col-md-1'>"+columnTypeText+"</div>"
        	+"<div class='col-md-1'><h4>条目样式：</h4></div><div class='col-md-1'>"+columnStyleText+"</div>"+columnText
        	+"<input type='hidden' name='columnS' value='"+columnParam+"'>"
        	+"<div class='col-md-1'><button type='button'  class='btn btn-primary' onclick='delColumnItem(\""+columnUid+"\");'><span>删除条目</span></button></div></div></li>";
        	$("#items").append(itemHtml);
        	//清空填写值
        	$("#columnType").val('');
        	$("#columnStyle").val('101');
        	$("#titleContent").val('');
        	$("#textContent").val('');
        	$("#itemPic").val('');
        	$("#itemPicUrl").val('');
        	$("#columnPic").html('');
            $(".title").hide();
            $(".text").hide();
            $(".pic").hide();
            //控制滚动条到底部
            var div = document.getElementById("cc"); 
            boxScroll(div);
        }
        //生成唯一id 
        function S4() {
       	   return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
       	}
       	function guid() {
       	   return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
       	}
        function boxScroll(o){  
            o.scrollTop = o.scrollHeight;  
        }  
	   </script>
	</body>
</html>
