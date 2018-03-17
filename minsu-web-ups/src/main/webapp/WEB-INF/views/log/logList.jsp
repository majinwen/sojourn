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
    <link href="css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="css/animate.css${VERSION}" rel="stylesheet">
    <link href="css/style.css${VERSION}" rel="stylesheet">
    <link href="css/custom-z.css${VERSION}" rel="stylesheet">
 </head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
         <div class="row row-lg">
         	<div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form class="form-horizontal m-t" id="commentForm">
			           		<div class="form-group">
			                   <label class="col-sm-1 control-label">员工工号：</label>
			                   <div class="col-sm-2">
			                       <input id="empCode" name="empCode" minlength="1" type="text" class="form-control" required="true" aria-required="true">
			                   </div>
			                   <label class="col-sm-1 control-label">员工姓名：</label>
			                   <div class="col-sm-1">
			                       <input id="empName" name="empName" minlength="1" type="text" class="form-control" required="true" aria-required="true">
			                   </div>
			                   <label class="col-sm-1 control-label">操作路径：</label>
                               <div class="col-sm-2">
			                       <input id="opUrl" name="opUrl" minlength="1" type="text" class="form-control" required="true" aria-required="true">
			                   </div>
                               <div class="col-sm-1">
			                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                   </div>
			                </div>
			           </form>
			           
			        </div>
                </div>
            </div>
        </div>
        <!-- Panel Other -->
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="row row-lg">
                    <div class="col-sm-12">
                        <div class="example-wrap">
                            <div class="example">
                                <table id="listTable" class="table table-bordered" data-click-to-select="true"
			                    data-toggle="table"
			                    data-side-pagination="server"
			                    data-pagination="true"
			                    data-striped="true"
			                    data-page-list="[10,20,50]"
			                    data-pagination="true"
			                    data-page-size="10"
			                    data-pagination-first-text="首页"
			                    data-pagination-pre-text="上一页"
			                    data-pagination-next-text="下一页"
			                    data-pagination-last-text="末页"
			                    data-content-type="application/x-www-form-urlencoded"
			                    data-query-params="paginationParam"
			                    data-method="post" 
			                    data-single-select="true"
			                    data-classes="table table-hover table-condensed"
			                    data-height="498"
			                    data-url="log/logResDataList">                    
			                    <thead>
			                        <tr>
			                            <th data-field=opEmployeeId >操作人fid</th>
			                            <th data-field="opEmployeeName">操作人姓名</th>
			                            <th data-field="opEmployeeCode">操作人工号</th>
			                            <th data-field="opAppName">操作系统名称</th>
			                            <th data-field="opUrl">操作URL</th>
			                            <th data-field="createTime" data-formatter="CommonUtils.formateDate">操作时间</th>
			                            
			                        </tr>
			                    </thead>
			                    </table>             
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 全局js -->
    <script src="js/jquery.min.js${VERSION}"></script>
    <script src="js/bootstrap.min.js${VERSION}"></script>

    <!-- 自定义js -->
    <script src="js/content.js${VERSION}"></script>


    <!-- Bootstrap table -->
    <script src="js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
    <script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
    <script src="js/minsu/common/commonUtils.js${VERSION}005"></script>
    <script src="js/minsu/common/custom.js${VERSION}"></script>
	<script src="js/plugins/layer/layer.min.js${VERSION}"></script>

    <script type="text/javascript">
	   
    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        empName:$.trim($('#empName').val()),
		        empCode:$.trim($('#empCode').val()),
		        empMobile:$.trim($('#empMobile').val()),
		        opUrl:$('#opUrl').val()
	    	};
		}
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
	</script>

</body>
  
</html>
