<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> 
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}0010" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">
   
  
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="ibox float-e-margins">
      </div>
      <div class="ibox float-e-margins">
        <div class="col-sm-16">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="col-sm-10">
                        <div class="col-sm-12">
	                       <div>备注：<br/>
		                       <button type="button" class="btn btn-info btn-xs">已启用<i class="glyphicon glyphicon-ok"></i></button>点击之后会变为停用状态；
		                       <button type="button" class="btn btn-warning btn-xs">已停用</button>点击之后会变为启用状态；
		                       <button type="button" class="btn btn-danger btn-xs">未添加<i class="glyphicon glyphicon-plus"></i></button>点击之后会添加到特色标识并自动变为启用状态；
		                       <br/>
		                       <br/>												
	                       </div>
	                       <c:forEach var="typeMap" items="${featureMap}"> 
		                     	<div class="panel panel-default">
								    <div class="panel-heading">
								    	 <h3 class="panel-title">${typeMap.key}</h3>
								    </div>
								    <div class="panel-body">
								         <c:forEach var="featureTag" items="${typeMap.value}">
									    	<button type="button" 									    		 
									    		<c:if test="${ featureTag.isValid ==1 }" > class="btn btn-info btn-xs" </c:if>
									    		<c:if test="${ featureTag.isValid ==0 }" > class="btn btn-warning btn-xs"	</c:if>
									    		<c:if test="${empty featureTag.fid }" > class="btn btn-danger btn-xs"	</c:if>
									    		data-fid="${featureTag.fid}" data-isValid="${featureTag.isValid}" data-foreignFid="${featureTag.foreignFid}" data-tagName="${featureTag.tagName} "  onclick="changeStatus(this)" >
									   			
									   			${featureTag.tagName} 
									   			<c:if test="${ featureTag.isValid ==1 }" >
										   			<i class="glyphicon glyphicon-ok"></i>
									   			</c:if>
									   			<c:if test="${empty featureTag.fid }" >
										   			<i class="glyphicon glyphicon-plus"></i>
									   			</c:if>
											</button>
									    </c:forEach>
								    </div>
								</div> 
							     
							    <br> 
						   </c:forEach> 
	                       
			            </div>
			          </div>
                   <div class="clearfix"></div>
                </div>
            </div>
        </div>
   </div>
</div>
 
<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>


<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>



<!-- Bootstrap-Treeview plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>


<script type="text/javascript">
	var changeStatusUrl = "config/featureTag/changeStatus";  
	var addFeatureTagUrl = "config/featureTag/addFeatureTag";
   	
	function changeStatus(btnObj){
		
		var featureTagFid = $(btnObj).attr("data-fid");
		var tagName = $(btnObj).attr("data-tagName");
		var url="";
		var data={};
		var add = false;
		var target = 0;
		if(featureTagFid == undefined || featureTagFid==null || featureTagFid.length ==0){//新增
			add = true;
			target = 1;
			url = addFeatureTagUrl;
			var foreignFid = $(btnObj).attr("data-foreignFid");
			data.foreignFid=foreignFid;
		}else{
			url = changeStatusUrl;
			var isValid = $(btnObj).attr("data-isValid");				
			
			if(Number(isValid)==0){
				target = 1;
			}else{
				target = 0;
			}
			
			data.featureTagFid=featureTagFid;
			data.isValid=target;
			
		}
		
		
		$.ajax({
            type: "POST",
            url: url,
            dataType:"json",
            data: data,
            success: function (result) {
            	
            	var status = result.code;
            	
            	if(status==0 || status=='0'){
	            	
	            	if(add){
	            		featureTagFid = result.data.fid;
	            		$(btnObj).attr("data-fid",featureTagFid);
	            	}
	            	
            		if(Number(target)==1){
                		$(btnObj).attr("data-isValid",1);
                		$(btnObj).removeClass("btn-warning").removeClass("btn-danger").addClass('btn-info');
                		$(btnObj).html(tagName+'<i class="glyphicon glyphicon-ok"></i>');
            		}else{
            			$(btnObj).attr("data-isValid",0);
            			$(btnObj).removeClass("btn-info").removeClass("btn-danger").addClass('btn-warning');
            			$(btnObj).html(tagName);
            		}
            		
            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
	            	
            	}else{
            		layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
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
