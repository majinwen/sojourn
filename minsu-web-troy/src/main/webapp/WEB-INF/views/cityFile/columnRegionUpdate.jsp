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
        <form action="cityFile/updateColumnRegion" method="post" id="saveColumnRegion">
	   	<input type="hidden" id="fid" name="fid" value="${columnRegionUpVo.fid }">
	   	<input type="hidden"  name="columnCityFid"  value="${columnRegionUpVo.columnCityFid }" id="columnCityFid">
	   	<input type="hidden"  value="${cityCode }" id="cityCode">
	   	<input type="hidden"  value="" id="delPicFid" name="delPicFids">
	   	<input type="hidden"  value=''  id="delItmeFid"  name="delItemFids">
         <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>景点商圈：</h4></div>
                            <div class="col-md-2">
                                 <select id="regionInfoSel" class="form-control m-b" name="regionFid" disabled="disabled">
                                 <c:forEach items="${regionList }" var="region">
                                 		<option value="${region.fid }" ${columnRegionUpVo.regionFid==region.fid?'selected':'' }>${region.regionName }</option>
                                 </c:forEach>
                                 </select>
                            </div>
							<div class="col-md-1 text-center">
								<customtag:authtag authUrl="archive/archiveMgt/searchRegionOne"><button id="regionInfo" class="btn btn-primary" type="button" >详细信息</button></customtag:authtag>
							</div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>主图：</h4></div>
                            <div class="col-md-2">
                            	<div class="lightBoxGallery"  id="mainPic">
                          		<c:forEach items="${columnRegionUpVo.picList }" var="vo">
	                                <c:if test="${vo.picType==0 }">
	                                	<div class='picline' id='${vo.fid }'>
	                                		<a href='${picBaseAddrMona}${vo.picBaseUrl }${vo.picSuffix}${picSize}.jpg' title='主图' data-gallery='blueimp-gallery-main'><img src='${picBaseAddrMona}${vo.picBaseUrl }${vo.picSuffix}${picSize}.jpg' style='height: 135px; width: 165px;'></a>
	                                		<div class='picjz'><p><button type='button' class='btn btn-danger btn-xs' onclick="delMainRegionPic('${vo.fid}');"><span>删除</span></button></p></div>
	                                	</div>
	                                </c:if>	
	                            </c:forEach>
					            </div>
                            </div>
                            <div class="col-md-1 text-center"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showUpLoadPage();"><span>上传主图</span></button></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>景点商圈轮播图：</h4></div>
                            <div class="col-md-6">
                            	<div class="lightBoxGallery"  id="carouselPic">
                                <c:forEach items="${columnRegionUpVo.picList }" var="vo">
                                <c:if test="${vo.picType==1 }">
                                	<div class='picline' id='${vo.fid }'>
                                		<a href='${picBaseAddrMona}${vo.picBaseUrl }${vo.picSuffix}${picSize}.jpg' title='轮播图' data-gallery='blueimp-gallery-main'><img src='${picBaseAddrMona}${vo.picBaseUrl }${vo.picSuffix}${picSize}.jpg' style='height: 135px; width: 165px;'></a>
                                		<div class='picjz'><p><button type='button' class='btn btn-danger btn-xs' onclick="delRegionPic('${vo.fid}');"><span>删除</span></button></p></div>
                                	</div>
                                </c:if>	
                                </c:forEach>
					            </div>
                            </div>
                            <div class="col-md-1 text-center"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showCarouselPic();"><span>上传轮播图</span></button></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>选择推荐项目：</h4></div>
                            <div class="col-md-2">
                                <select class="form-control m-b" id="regionItem" >
                                	  <option value="">请选择</option>
                                	  <c:forEach items="${items }" var="tem">
                                	  <option value="${tem.fid }">${tem.itemName }</option>
                                	  </c:forEach>
                                </select>
                                <button type='button'  class='btn btn-primary'   onclick="addRegionItem();"><span>添加项目</span></button>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-9">
                                <h4>已推荐项目：</h4>
                                <ul class="sortable-list connectList agile-list" id="items">
                                <c:forEach items="${columnRegionUpVo.itemList }" var="tem">
                                	<li class='warning-element' id="${tem.fid }">
                                    <input type="hidden" name="itemSortS" value="${tem.orderSort }">
                                    <input type="hidden" name="itemFidS" value="${tem.fid }">
                                	<div class='row show-grid' >
                                		<div class='col-md-2'>项目名称：</div>
                                		<div class='col-md-2'>
                                	    <c:forEach items="${ items}" var="item">
		                           			<c:if test="${item.fid==tem.regionItemsFid }">${item.itemName }</c:if>
		                           		</c:forEach>
                                		</div>
                                		<div class='col-md-2'>项目图标：</div>
                                		<div class='col-md-4'>
	                                		<div class='lightBoxGallery'  id='itemPic${tem.regionItemsFid}'>
		                                		<div class='picline' id='${tem.iconServerUuid }'>
			                           			<a href='${picBaseAddrMona}${tem.iconBaseUrl }${tem.iconSuffix}${picSize}.jpg' title='主图' data-gallery='blueimp-gallery-main'><img src='${picBaseAddrMona}${tem.iconBaseUrl }${tem.iconSuffix}${picSize}.jpg' style='height: 135px; width: 165px;'></a>
			                           			<div class='picjz'><p><button type='button' class='btn btn-danger btn-xs' onclick="delMainPic('${tem.iconServerUuid}');"><span>删除</span></button></p></div>
			                           			</div>
	                                		</div>
	                                		<button type='button'  class='btn btn-primary' data-target='#myModal'  onclick="showItemPic('${ tem.regionItemsFid}');"><span>上传项目图标</span></button>
                                		</div>
                                		<div class='col-md-2'><button type='button'  class='btn btn-primary' onclick="delRegionItemByFid('${tem.fid}');"><span>删除项目</span></button></div>
                                	</div>
                                	</li>
                                </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>房源编码：</h4></div>
                            <div class="col-md-4"><input name="houseFids" value="${columnRegionUpVo.houseFids }"  type="text" class="form-control m-b" >&nbsp;&nbsp;多个以逗号(半角)隔开</div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>展示房源数量：</h4></div>
                            <div class="col-md-2"><input name="showHouseNum" value="${columnRegionUpVo.showHouseNum }"  type="text" class="form-control m-b" ></div>
                        </div>
                        <div class="row show-grid">
                            <div class="col-md-1 text-center"><h4>备用字段：</h4></div>
                            <div class="col-md-2"><input name="standbyField" value="${columnRegionUpVo.standbyField }"  type="text" class="form-control m-b" ></div>
                        </div>                    
                    </div>
                </div>
            </div>
            <div class="col-sm-2 col-sm-offset-2">
	             <customtag:authtag authUrl="cityFile/updateColumnRegion"><button class="btn btn-primary" type="submit" >保存内容</button></customtag:authtag>
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
	   <script src="${staticResourceUrl}/js/jquery-ui-1.10.4.min.js${VERSION}002"></script>
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
	   <!-- ckeditor.js-->
	   <script type="text/JavaScript" src="${staticResourceUrl}/ckeditor/ckeditor.js"></script>
	   <!-- 修改  景点商圈弹出框 -->
	   <div class="modal" id="myModal4" tabindex="-1" role="dialog" aria-hidden="false" data-keyboard="false" data-backdrop="static">
		   <div class="modal-dialog" style="width:1000px;">
			   <div class="modal-content">
				   <div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					   </button>
					   <h4 class="modal-title">添加商圈景点内容</h4>
				   </div>
				   <div class="modal-body">
					   <div class="wrapper wrapper-content animated fadeInRight">
						   <div class="row">
							   <div class="col-sm-14">
								   <div class="ibox float-e-margins">
									   <div class="ibox-content">
										   <form id="region4" class="form-horizontal m-t">
											   <!-- 传递隐藏域参数cityCode-->
											   <input id="code4" type="hidden" name="cityCode" value="${cityCode}">
											   <input id="regionFid" type="hidden" name="regionFid" value="">
											   <div class="form-group">
												   <label class="col-sm-3 control-label">商圈/景点</label>
												   <div class="col-sm-8">
													   <%--<select id="regionsSelect" class="form-control m-b" onchange="backContent(this)" >
                                                           <option value="-1">请选择</option>
                                                           <c:forEach items="${regionList}" var="region">
                                                               <option id="hotRegionFid" value="${region.hotRegionFid}">${region.regionName}</option>
                                                           </c:forEach>
                                                       </select>--%>
													   <input id="hotRegionName" hotRegionName="brief" readonly="true" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
												   </div>
											   </div>
											   <div class="form-group">
												   <label class="col-sm-3 control-label">商圈描述</label>
												   <div class="col-sm-8">
													   <input id="hotRegionBrief4" name="brief" class="form-control" type="text"  value="" aria-required="true" aria-invalid="true" class="error">
												   </div>
											   </div>
											   <div class="form-group">
												   <label class="col-sm-3 control-label">商圈/景点档案</label>
											   </div>
											   <div class="form-group" align="center">
                                            <textarea name="hotRegionContent4" id="hotRegionContent4">
                                              <pre></pre>
                                            </textarea>
												   <script>
													   //CKEDITOR.replace( 'hotRegionContent' );
													   CKEDITOR.replace( 'hotRegionContent4' ,{
														   //图片上传发送路径，returnURL为参数,到服务器后跳转回来的页面
														   filebrowserImageUploadUrl : '${basePath}cityFile/uploadCkeditorPic',
														   filebrowserBrowseUrl : '${basePath}cityFile/uploadCkeditorPic'
													   });
												   </script>
											   </div>
											   <!-- 用于 将表单缓存清空 -->
											   <input type="reset" style="display:none;" />
										   </form>
									   </div>
								   </div>
							   </div>
						   </div>
					   </div>
				   </div>
				   <div class="modal-footer">
					   <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
					   <button type="button" id="updateRegion" class="btn btn-primary" >修改</button>
				   </div>
			   </div>
		   </div>
	   </div>
       <script type="text/javascript">
		
		$("#saveColumnRegion").validate({
		    rules: {
		    	regionFid: {
		          required: true
		        }
		    },
			submitHandler:function(){
				if($.trim($("#mainPic").html())==''){
					layer.alert('请上传主图', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if($.trim($("#carouselPic").html())==''){
					layer.alert('请上传轮播图', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if($.trim($("#items").html())==''){
					layer.alert('请添加项目', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				$.ajax({
		            type: "POST",
		            url: "cityFile/updateColumnRegion",
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
		function isNullOrBlank(obj){
			return obj == undefined || obj == null || $.trim(obj).length == 0;
		}
        //打开景点商圈的详细信息弹层
		$("#regionInfo").click(function () {
			var regionFid = '${columnRegionUpVo.regionFid }';
			var regionName=$("#regionInfoSel").find("option:selected").text();
//			alert(regionFid+regionName);18a9e9aa856126a5b0156126a5bd30001
			$.ajax({
				type: "POST",
				url: "archive/archiveMgt/searchRegionOne",
				dataType:"json",
				data: {"hotRegionFid":regionFid},
				success: function (result) {
					$('#myModal4').modal('show');
					if(!isNullOrBlank(result.data.region)){
						$("#regionFid").val(regionFid);
						$("#hotRegionName").val(regionName);
						$("#hotRegionBrief4").val(result.data.region.hotRegionBrief);
						CKEDITOR.instances['hotRegionContent4'].setData(result.data.region.hotRegionContent);
//                      $("#hotRegionContent").html(result.data.region.hotRegionContent);
					}else{
						$("#hotRegionBrief4").val("");
						CKEDITOR.instances['hotRegionContent4'].setData('');
					}
				},
				error: function(result) {
					alert(result.msg);
				}
			});
		})
		/** 修改景点商圈*/
		$("#updateRegion").click(function(){
//          alert("保存被单击了");
			var hotRegionFid = $.trim($("#regionFid").val());
			//var regionName=$("#regionsSelect").find("option:selected").text();
			//var txt = $('#regionsSelect  option:selected').text();
			var cityCode = $.trim($("#code4").val());
			var hotRegionBrief = $.trim($("#hotRegionBrief4").val());
			var hotRegionContent = CKEDITOR.instances['hotRegionContent4'].getData();
//          console.log(hotRegionFid+cityCode+hotRegionBrief+hotRegionContent);
			if(isNullOrBlank(cityCode) || isNullOrBlank(hotRegionFid)){
				alert("请选择景点商圈");
				$('#myModal4').modal('hide')
				$("input[type=reset]").trigger("click");
				return;
			}
			//校验通过  则提交
			$.ajax({
				type: "POST",
				url: "archive/archiveMgt/saveHotRegion",
				dataType:"json",
				data: {"hotRegionFid":hotRegionFid,"cityCode":cityCode,"hotRegionBrief":hotRegionBrief,"hotRegionContent":hotRegionContent},
				success: function (result) {
					/*$('#myModal4').modal('hide');
					$("input[type=reset]").trigger("click");*/
					layer.alert('修改成功,点击关闭或者X号退出弹层', {icon: 6,time: 2000, title:'提示'});
				},
				error: function(result) {
					alert(result.msg);
				}
			});

		})
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
	        	var itemHtml="<li class='warning-element' id='item"+regionItemFid+"'><div class='row show-grid'><div class='col-md-2'><h4>项目名称：</h4></div><div class='col-md-2'>"+regionItemName+"</div>"
	        	+"<div class='col-md-2'><h4>项目图标：</h4></div><div class='col-md-4'><div class='lightBoxGallery'  id='itemPic"+regionItemFid+"'></div>"
	        	+"<button type='button'  class='btn btn-primary' data-target='#myModal'  onclick='showItemPic(\""+regionItemFid+"\");'><span>上传项目图标</span></button></div>"
	        	+"<div class='col-md-2'><button type='button'  class='btn btn-primary' onclick='delRegionItem(\""+regionItemFid+"\");'><span>删除项目</span></button></div></div></li>";
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
        
        //删除原来的图片
        function delRegionPic(picFid){
        	var fids=$("#delPicFid").val();
        	if(fids==''){
        		fids=picFid;
        	}else{
        		fids=fids+"-"+picFid;
        	}
        	$("#delPicFid").val(fids);
        	$("#"+picFid).remove();
        }
        //删除原来的项目
        function delRegionItemByFid(itemFid){
        	var fids=$("#delItmeFid").val();
        	if(fids==''){
        		fids=itemFid;
        	}else{
        		fids=fids+"-"+itemFid;
        	}
        	$("#delItmeFid").val(fids);
        	$("#"+itemFid).remove();
        }
        //删除主图
        function delMainRegionPic(picFid){
        	$("#"+picFid).remove();
        }
        
        $(document).ready(function () {
            $(".sortable-list").sortable({
                connectWith: ".connectList",
                axis: 'y',
                containment:'parent',
                scroll: false
            }).disableSelection();
        });
	   </script>
	</body>
</html>
