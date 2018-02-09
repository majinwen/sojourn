<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/iCheck/custom.css${VERSION}001"
	rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>添加系统消息</h5>
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
							action="${basePath }message/saveOrUpdateSysMsg"
							class="form-horizontal" onsubmit="document.charset='utf-8'"
							method="post">
							<input type="hidden" class="form-control" name="id" value="${sysMsgManagerEntity.id }">
							<input type="hidden" class="form-control" name="fid" value="${sysMsgManagerEntity.fid }">
							<div class="form-group">
								<label class="col-sm-2 control-label">消息标题</label>
								<div class="col-sm-2">
								  <input type="text" class="form-control" name="msgTitle" value="${sysMsgManagerEntity.msgTitle}">
								</div>
							</div>
							<div class="hr-line-dashed"></div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">消息内容</label>
								<div class="col-sm-2">
						        	   <textarea  style="hight:200px;width: 500px" class="form-control" name="msgContent" >${sysMsgManagerEntity.msgContent}</textarea>
								</div>
							</div>
							
							<div class="hr-line-dashed"></div>
							<div class="form-group">
                               <label class="col-sm-2 control-label">消息发送类型</label>
	                           <div class="col-sm-1">
	                           <c:if test="${empty sysMsgManagerEntity}">
	                           		 <select class="form-control m-b" name="msgTargetType">
	                           		 	<option value="1">指定用户</option>
                                        <option value="2">全部房东</option>
                                        <option value="3">全部房客</option>
                                        <option value="4" selected="selected">所有人</option>
                                     </select>
								</c:if>
								<c:if test="${not empty sysMsgManagerEntity }">
                                   <select class="form-control m-b" name="msgTargetType" value="${sysMsgManagerEntity.msgTargetType}">	
                                        <option value="1" <c:if test="${sysMsgManagerEntity.msgTargetType == '1'}">selected="selected"</c:if> >指定用户</option>
                                        <option value="2" <c:if test="${sysMsgManagerEntity.msgTargetType == '2'}">selected="selected"</c:if>>全部房东</option>
                                        <option value="3" <c:if test="${sysMsgManagerEntity.msgTargetType == '3'}">selected="selected"</c:if>>全部房客</option>
                                        <option value="4" <c:if test="${sysMsgManagerEntity.msgTargetType == '4'}">selected="selected"</c:if>>所有人</option>
                                   </select>
                                   </c:if>
                               </div>
                            </div>
							
							<div class="hr-line-dashed"></div>
							<!-- 指定发送给人，类型为1的时候选择 -->
							
							 <div class="form-group" id="targetUidSelect" style="display: none;">
                                <label class="col-sm-2 control-label">用户ID</label>
                                <div class="col-sm-2">
	                               <div class="input-group">
		                               <c:if test="${empty sysMsgManagerEntity}">
									 	 	<input type="text" class="form-control" name="msgTargetUid" value="">
									   </c:if>
										<c:if test="${not empty sysMsgManagerEntity }">
									  		<input type="text" class="form-control" name="msgTargetUid" value="${sysMsgManagerEntity.msgTargetUid }">
										</c:if>
	                                   <span class="input-group-btn">
	                                  <!--  <button type="button" class="btn btn-white" data-toggle="modal" data-target="#userModelList">
	                                  	选择用户
	                              	   </button> -->
	                              	   </span>
	                               </div>
                                </div>  
                            </div>
                            
							<div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button class="btn btn-primary" type="submit">保存内容</button>
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
	
	
	<div class="modal inmodal fade" id="userModelList" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">用户列表</h4>
                 </div>
                 <div class="ibox-content">
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="listTable" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[1,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-show-refresh="true"
					                    data-single-select="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="paginationParam"
					                    data-method="get" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="system/permission/employeeList">                    
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-checkbox="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">人员编号</th>
					                            <th data-field="empName">人员姓名</th>
					                            <th data-field="empSex">性别</th>
					                            <th data-field="empMobile">移动电话</th>
					                            <th data-field="empMail">电子邮箱</th>
					                            <th data-field="postName">部门</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="getSelectEmployee()" data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
     </div>
     
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>

	<!-- iCheck -->
	<script src="${staticResourceUrl}/js/plugins/iCheck/icheck.min.js${VERSION}001"></script>

	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

	<script type="text/javascript">
    	 $(document).ready(function () {
            //选择显示
            $("select").change(function(){
            	if($("select").val() == "1"){
            		$("#targetUidSelect").show();
            	}else{
            		$("#targetUidSelect").hide();
            	}
            });
            
            
            var icon = "<i class='fa fa-times-circle'></i> ";
            $("#smsEditForm").validate({
                rules: {
                	msgTitle: "required",
                	msgContent: "required",
                	msgTargetUid:{
                		required:  function(){
                			if($("select").val() == "1")
                				return true;
                		}
                	}
                },
                messages: {
                	msgTitle: icon + "请输入消息标题！",
                	msgContent: icon +"请输入消息内容！",
                	msgTargetUid:icon +"请选择用户ID"
                }
            });
            
    	 })
    	 
    	 /* function getSelectEmployee(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			$("#empName").val(selectVar[0].empName);
			$("#empId").val(selectVar[0].fid);
		} */
   
    </script>
</body>
</html>