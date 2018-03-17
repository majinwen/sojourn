<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   
	<base href="${basePath}">
    <title>自如民宿权限管理系统</title>
    <meta name="keywords" content="自如民宿权限管理系统">
    <meta name="description" content="自如民宿权限管理系统">

    <link href="css/bootstrap.min.css?${VERSION}001" rel="stylesheet">
    <link href="css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="css/animate.css${VERSION}001" rel="stylesheet">
    <link href="css/style.css${VERSION}001" rel="stylesheet">
 </head>
  
  <body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>添加系统</h5>
                    </div>
                    <div class="ibox-content">
                        <form id="systemForm" class="form-horizontal"  method="post">
                        <input type="hidden" name="fid"  value="${system.fid }">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">系统名称：</label>

                                <div class="col-sm-2">
                                    <input type="text" class="form-control"  name="sysName" value="${system.sysName }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">系统code：</label>

                               <div class="col-sm-2">
                                    <input type="text" class="form-control"  name="sysCode" value="${system.sysCode }">
                                </div>
                            </div>
    						<div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">系统访问地址：</label>

                               <div class="col-sm-3">
                                    <input type="text" class="form-control"  name="sysUrl" value="${system.sysUrl }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit" >保存内容</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 全局js -->
    <script src="js/jquery.min.js${VERSION}001"></script>
    <script src="js/bootstrap.min.js${VERSION}001"></script>

    <!-- 自定义js -->
    <script src="js/content.js${VERSION}001"></script>

    
    <!-- Bootstrap table -->
    <script src="js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
    <script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
    <script src="js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
    <script src="js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
    <script src="js/minsu/common/custom.js?${VERSION}"></script>
    <script src="js/content.js${VERSION}"></script>
    
    
    
    <script type="text/javascript">
	$("#systemForm").validate({
		submitHandler:function(){
			$.ajax({
	            type: "POST",
	            url: "system/updateSystem",
	            dataType:"json",
	            data: $("#systemForm").serialize(),
	            success: function (result) {
	            	if(result.code==0){
						$.callBackParent("system/systemList",true,saveSystemCallback);
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
	function saveSystemCallback(panrent){
		panrent.refreshData("listTable");
	}
    </script>
</body>
</html>
