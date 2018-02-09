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
	<link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
	<link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">




</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="ibox float-e-margins">
	</div>
	<div class="ibox float-e-margins">
		<div class="col-sm-16">
			<div class="ibox float-e-margins">
				<div class="col-sm-10">
					<%--<!-- 左侧树div -->--%>
					<%--<div class="col-sm-2">--%>
						<%--<div id="treeview" class="test"></div>--%>
					<%--</div>--%>
					<!-- 右侧table div -->
					<div class="col-sm-10">


						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">民宿索引</h3>
							</div>
							<div class="panel-body">

								<div >
									<form id="complate" class="form-horizontal m-t" >
										<div class="form-group">
											<label class="col-sm-3 control-label">请输入刷新房源fid</label>
											<div class="col-sm-8">
												<input id="houseFid" name="houseFid"  type="text" value="">
												<button id="addMenuButton" type="button" class="btn btn-primary" onclick="doFresh()" >刷新</button>
											</div>
										</div>
									</form>
								</div>

								<div >
									<form id="freshAreaCode" class="form-horizontal m-t" >
										<div class="form-group">
											<label class="col-sm-3 control-label">请输入刷新区域code</label>
											<div class="col-sm-8">
												<input id="areaCode" name="areaCode"  type="text" value="">
												<button id="freshAreaCodeButton" type="button" class="btn btn-primary" onclick="doFreshAreaCode()" >刷新</button>
											</div>
										</div>
									</form>
								</div>

								<div >
									<form id="freshAll"  class="form-horizontal m-t" >
										<div class="form-group">
											<div class="col-sm-8">
												<button id="freshAllButton" type="button" class="btn btn-primary" onclick="doFreshAll('1')" >强制刷新</button>
											</div>
										</div>
									</form>
								</div>


							</div>
						</div>


						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">驿站索引</h3>
							</div>
							<div class="panel-body">
								<div >
									<form id="freshAllZry"  class="form-horizontal m-t" >
										<div class="form-group">
											<div class="col-sm-8">
												<input id="projectId" name="projectId"  type="text" value="">
												<button id="freshAllZryButton" type="button" class="btn btn-primary" onclick="doFreshAll('2')" >强制刷新</button>
											</div>
										</div>
									</form>
								</div>


							</div>
						</div>

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">攻略索引</h3>
							</div>
							<div class="panel-body">
								<div >
									<form id="freshAllCms"  class="form-horizontal m-t" >
										<div class="form-group">
											<div class="col-sm-8">
												<button id="freshAllCmsButton" type="button" class="btn btn-primary" onclick="doFreshAll('3')" >强制刷新</button>
											</div>
										</div>
									</form>
								</div>


							</div>
						</div>






                        <div class="col-sm-11"  >
							<div class="form-group">
								刷新结果
								<textarea id="valueInfo" class="form-control" rows="10"></textarea>
							</div>
						</div>

						<div class="col-sm-12">

							<!-- 菜单列表结束 -->
						</div>
					</div>
					</div>



				<div class="clearfix">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js?v=2.1.4"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js?v=3.3.6"></script>

<script src="${staticResourceUrl}/js/plugins/suggest/bootstrap-suggest.min.js"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js?v=1.0.0"></script>

<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js"></script>

<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>



<!-- Bootstrap-Treeview plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js"></script>

<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js"></script>


<script type="text/javascript">






	var contentPath = "${path}"; //系统路径
	var complateUrl = "search/query/freshHouseIndex";
    var freshAllUrl = "search/query/freshAll";
    var freshAreaCodeUrl = "search/query/freshAreaCode";



    function doFreshAreaCode(){
        $.ajax({
            type: "POST",
            url: freshAreaCodeUrl,
            dataType:"json",
            data: $("#freshAreaCode").serialize(),
            success: function (result) {
                $("#valueInfo").val(result.msg);
            },
            error: function(result) {
                alert("error:"+result);
            }
        });
    }


	function doFresh(){
		$.ajax({
			type: "POST",
			url: complateUrl,
			dataType:"json",
			data: $("#complate").serialize(),
			success: function (result) {
				$("#valueInfo").val(result.msg);
			},
			error: function(result) {
				alert("error:"+result);
			}
		});
	}



    function doFreshAll(dataSource){

	    $("#valueInfo").val("");

	    var param ={dataSource:dataSource};
	    if(dataSource=='2'){
            param.projectId = $("#projectId").val();
		}
        $.ajax({
            type: "POST",
            url: freshAllUrl,
            dataType:"json",
            data: param,
            success: function (result) {
                if(result.code==0 || result.code=="0"){
                    $("#valueInfo").val("OK");
				}else{
                    $("#valueInfo").val(result.msg);
				}
            },
            error: function(result) {
                alert("error:"+result);
            }
        });
    }

</script>


</body>

</html>
