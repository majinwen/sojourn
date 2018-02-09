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

<link href="resources/css/custom-z.css${VERSION}" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>添加消息模板</h5>
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
						<form id="smsEditForm"
							action="${basePath }sms/saveSmsTeplate"
							class="form-horizontal" onsubmit="document.charset='utf-8'"
							method="post">
							<input type="hidden" class="form-control" name="id" value="${smsTemplateEntity.id }">
							<input type="hidden" class="form-control" name="fid" value="${smsTemplateEntity.fid }">
							<div class="form-group">
								<label class="col-sm-2 control-label">消息编码</label>

								<div class="col-sm-2">
								<c:if test="${empty smsTemplateEntity}">
								  <input type="text" class="form-control" name="smsCode" value="">
								</c:if>
								<c:if test="${not empty smsTemplateEntity }">
								  <input type="text" class="form-control" name="smsCode" value="${smsTemplateEntity.smsCode }">
								</c:if>
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">消息名称</label>

								<div class="col-sm-2">
								
								<c:if test="${empty smsTemplateEntity}">
								  	<input type="text" class="form-control" name="smsName" value="">
								</c:if>
								<c:if test="${not empty smsTemplateEntity }">
						        	<input type="text" class="form-control" name="smsName" value="${smsTemplateEntity.smsName }">
								</c:if>
								
								
								</div>
							</div>
							
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">消息内容</label>
								<div class="col-sm-2">
									<c:if test="${empty smsTemplateEntity}">
								  	   <textarea  style="hight:200px;width: 500px" class="form-control" name="smsComment" ></textarea>
								    </c:if>
								    <c:if test="${not empty smsTemplateEntity }">
						        	   <textarea  style="hight:200px;width: 500px" class="form-control" name="smsComment" > ${smsTemplateEntity.smsComment }</textarea>
								    </c:if>
								</div>
							</div>
							
							 <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <label class="col-sm-2 control-label">消息类型</label>
	                           <div class="col-sm-1">
                                   <select class="form-control m-b" name="smsType" value="" id="smsType">
                                        <option value="3">消息</option>
                                        <option value="1">短信消息</option>
                                        <option value="2">邮件消息</option>
                                        <option value="4">其他</option>
                                   </select>
                               </div>
                            </div>
							<div class="hr-line-dashed"></div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label"> 是否启用 </label>
								<div class="radio i-checks">
								
								<c:choose>
							   <c:when test="${smsTemplateEntity.smsEnabled==1 || smsTemplateEntity.smsEnabled=='1'}"> 
							             </label> <label> <input type="radio" value="1" checked="checked" name="smsEnabled">
										<i></i>是
									</label>
									<label> <input type="radio" value="0"  name="smsEnabled">
										<i></i>否
							   </c:when>
							   <c:otherwise>
							       </label> <label> <input type="radio" value="1" name="smsEnabled">
										<i></i>是
									</label>
									<label> <input type="radio" value="0" checked="checked" name="smsEnabled">
										<i></i>否
							   </c:otherwise>
							</c:choose>
									
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
								<customtag:authtag authUrl="sms/saveSmsTeplate">
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
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?v=2.1.4"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?v=3.3.6"></script>

	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js?v=1.0.0"></script>

	<!-- iCheck -->
	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js"></script>

	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js"></script>
	<script
		src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js"></script>

	<script type="text/javascript">
    	 $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
            
            var icon = "<i class='fa fa-times-circle'></i> ";
            $("#userEditForm").validate({
                rules: {
                	smsCode: "required",
                	smsName: "required",
                	smsComment: "required"
                    
                },
                messages: {
                	smsName: icon + "请输入用户名！",
                	smsCode: icon +"请输入密码！",
                	smsComment: icon +"请选择员工！"
                }
            });
            
            var smsType = ${smsTemplateEntity.smsType};
            if(smsType!=null&&smsType!=undefined&&smsType!=null){
            	$("select option[value='"+smsType+"']").attr("select","selected"); 
            	var value = $("select option[value='"+smsType+"']").val(); 
            	$("#smsType").val(value);
            }
            
    	 })
   
    </script>
</body>
</html>