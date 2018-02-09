<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="<%=basePath%>">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">

<link rel="${staticResourceUrl}/shortcut icon"
	href="${staticResourceUrl}/favicon.ico">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"
	rel="stylesheet">
<link
	href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
 <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/plugins/webuploader/webuploader.css${VERSION}">
   <link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/demo/webuploader-demo.css${VERSION}">
<link href="resources/css/custom-z.css${VERSION}" rel="stylesheet">
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
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>添加静态资源模板</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a> <a class="dropdown-toggle" data-toggle="dropdown"
								href="form_basic.html#"> <i class="fa fa-wrench"></i>
							</a>
							<ul class="dropdown-menu dropdown-user">
								<li><a href="form_basic.html#">选项1</a></li>
								<li><a href="form_basic.html#">选项2</a></li>
							</ul>
							<a class="close-link"> <i class="fa fa-times"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form id="staticEditForm"
							action="${basePath }staticMgt/saveStaticResource"
							class="form-horizontal" onsubmit="document.charset='utf-8'"
							method="post">
							<input type="hidden" class="form-control" name="id" value="">
							<input type="hidden" class="form-control" name="fid" value="">
							<div class="form-group">
								<label class="col-sm-2 control-label">主题编码</label>
								<div class="col-sm-2">
								<input type="text" class="form-control" id="resCode" name="resCode" value="">
								<%-- <c:if test="${empty smsTemplateEntity}">
								  <input type="text" class="form-control" name="smsCode" value="">
								</c:if>
								<c:if test="${not empty smsTemplateEntity }">
								  <input type="text" class="form-control" name="smsCode" value="${smsTemplateEntity.smsCode }">
								</c:if> --%>
								</div>
							</div>

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">父级编码</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" id="parentCode" name="parentCode" value="" placeholder="可以不填">
								</div>
							</div>

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">排序编号</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" id="orderCode" name="orderCode" value="" placeholder="可以不填">
								</div>
							</div>

							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">主题名称</label>

								<div class="col-sm-2">
								<input type="text" class="form-control" id="resTitle" name="resTitle" value="">
								</div>
							</div>


							
							 <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <label class="col-sm-2 control-label">资源类型</label>
	                           <div class="col-sm-1">
                                   <select class="form-control m-b" name="resType" value="" id="resType">
                                        	<!-- <option value="">请选择</option> -->
	                                    <c:forEach items="${staticResourceTypeMap }" var="obj">
		                                    <option value="${obj.key }">${obj.value }</option>
	                                    </c:forEach>
                                   </select>
                               </div>
                            </div>
                            <div class="hr-line-dashed"></div>
							<div id="textContent" class="form-group">
								<label class="col-sm-2 control-label">资源内容</label>
								<div class="col-sm-2">
								<textarea  style="hight:200px;width: 500px" class="form-control" id="resContent" name="resContent" ></textarea>
									<%-- <c:if test="${empty smsTemplateEntity}">
								  	   <textarea  style="hight:200px;width: 500px" class="form-control" name="resContent" ></textarea>
								    </c:if>
								    <c:if test="${not empty smsTemplateEntity }">
						        	   <textarea  style="hight:200px;width: 500px" class="form-control" name="smsComment" > ${smsTemplateEntity.smsComment }</textarea>
								    </c:if> --%>
								</div>
							</div>
							<div id="picContent" class="form-group">
							<label class="col-sm-2 control-label">图片：</label>
								<div class="row show-grid">
								
	                            <!-- <div class="col-md-1 text-center"><h4>图片：</h4></div> -->
		                            <div class="col-md-2">
		                            	<div class="lightBoxGallery"  id="mainPic">
							            </div>
		                            </div>
		                            <div class="col-md-1 text-center"><button type='button'  class='btn btn-primary' data-target="#myModal"  onclick="showUpLoadPage();"><span>上传图片</span></button></div>
		                        </div>
							</div>
							
							<div class="hr-line-dashed"></div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">备注</label>
								<div class="col-sm-2">
								<textarea  style="hight:200px;width: 500px" class="form-control" id="resRemark" name="resRemark" ></textarea>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
								<customtag:authtag authUrl="staticMgt/saveStaticResource">
									<button class="btn btn-primary" type="submit">保存内容</button>
								</customtag:authtag>
									<button class="btn btn-white"
										onclick="javascript:history.back(-1);">取消</button>
								</div>
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
	<script src="${staticResourceUrl}/js/jquery.min.js?v=2.1.4"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?v=3.3.6"></script>
	<!-- 全局js -->
   <script type="text/javascript">
        // 添加全局站点信息
      	 var BASE_URL = 'js/plugins/webuploader';
        var UPLOAD_BASE_URL='${basePath}';
      </script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js?v=1.0.0"></script>

	<!-- iCheck -->
	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js"></script>
		<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js"></script>
	<script src="${staticResourceUrl}/js/plugins/webuploader/webuploader.min.js?${VERSION}001"></script>	
	 <script src="${staticResourceUrl}/js/minsu/common/topUploader.js?${VERSION}"></script> 
	<%--  <script src="js/minsu/common/topUploader.js?${VERSION}"></script> --%>
	<script type="text/javascript">
    	 $(document).ready(function () {
    		 $("#picContent").hide();
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
            var icon = "<i class='fa fa-times-circle'></i> ";
           /*  $("#staticEditForm").validate({
                rules: {
                	resCode: "required",
                	resTitle: "required",
                	
                    
                },
                messages: {
                	resTitle: icon + "请输入资源名称！",
                	resCode: icon +"请输入资源码！"
                	
                }
            }); */
             
           /*  var smsType = ${smsTemplateEntity.smsType};
            if(smsType!=null&&smsType!=undefined&&smsType!=null){
            	$("select option[value='"+smsType+"']").attr("select","selected"); 
            	var value = $("select option[value='"+smsType+"']").val(); 
            	$("#smsType").val(value);
            } */
            
    	 })
   		function isNullOrBlank(obj){
	    	return obj == undefined || obj == null || $.trim(obj).length == 0;
	    }
    	 $("#staticEditForm").validate({
		    
			submitHandler:function(form){
				var type = $("#resType option:selected").val();
				//alert(type);
				if(type == 2){
					if($("input[name='mainPicParam']").length==0){
						layer.alert('请上传主图', {icon: 5,time: 2000, title:'提示'});
						return false;
					}	
				}
				var resCode = $("#resCode").val();
				if(isNullOrBlank(resCode)){
					layer.alert('请填写资源码', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				if(resCode.length >50){
					layer.alert('资源编码不可以超过长度50', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				/*var resTitle = $("#resTitle").val();
				if(isNullOrBlank(resTitle)){
					layer.alert('请填写资源名称', {icon: 5,time: 2000, title:'提示'});
					return false;
				}*/
				if(resTitle.length >50){
					layer.alert('资源名称不可以超过长度50', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				var resRemark = $("#resRemark").val();
				if(resRemark.length >255){
					layer.alert('备注不可以超过长度255', {icon: 5,time: 2000, title:'提示'});
					return false;
				}
				form.submit();
				/* $.ajax({
		            type: "POST",
		            url: "staticMgt/saveStaticResource",
		            dataType:"json",
		            async:false,
		            data: $("#staticEditForm").serialize(),
		            success: function (result) {
		            	if(result.code != 0){
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      }); */
			}
    	 
    	 });
    	 
    	 //显示弹出框 上传图片
	    function  showUpLoadPage(){
		    $('#myModal').modal('toggle');
		    createUploader();
	    }
	    
    	//删除图片
         function delMainPic(picUid){
         	$("#"+picUid).remove();
         }
    	 
    	 $('#resType').change(function(){
    		 /* alert($(this).children('option:selected').val()); */
    		 var type = $(this).children('option:selected').val();
    		 if(type == "2"){
    			 $("#picContent").show();
    			 $("#textContent").hide();
    			 
    		 }else if(type == "1"){
    			 $("#textContent").show();
    			 $("#picContent").hide();
    		 }
    	 })
    		 
			
    </script>
</body>
</html>