<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<base href="${basePath }>">
	<title>自如民宿后台管理系统</title>
	<meta name="keywords" content="自如民宿后台管理系统">
	<meta name="description" content="自如民宿后台管理系统">
	<link rel="shortcut icon" href="favicon.ico">
	<link href="css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	<link href="css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
	<style>
		.thumbnail:hover{
			border-color: #000;
		}
		.system_icon{
			width: 120px;
			height: 120px;
		}
	</style>
</head>
<body>
<div class="row" style="margin: 10px;">
	<c:forEach var="item" items="${sysList}">
		<div class="col-sm-4 col-md-4">
			<a href="javascript:;" style="text-decoration : none" data-url="${item.sysUrl}?userName=${__CURRENT_LOGIN_USER_NAME__.userAccount }">
				<div class="thumbnail" style="border:0px;padding:50px">
					<c:if test="${item.sysCode=='troy' }">
					<img class="img-circle system_icon" src="img/troy.jpg">
					</c:if>
				    <c:if test="${item.sysCode=='report' }">
					<img class="img-circle system_icon" src="img/report.jpg">
					</c:if>
					<c:if test="${item.sysCode=='ares' }">
						<img class="img-circle system_icon" src="img/ares.png">
					</c:if>
                    <c:if test="${item.sysCode=='eunomia' }">
                        <img class="img-circle system_icon" src="img/eunomia.jpg">
                    </c:if>
                    <c:if test="${item.sysCode=='zry' }">
                        <img class="img-circle system_icon" src="img/zry.png">
                    </c:if>
                    <c:if test="${item.sysCode=='zra' }">
                        <img class="img-circle system_icon" src="img/zra.png">
                    </c:if>
                    <c:if test="${item.sysCode=='zyu' }">
                        <img class="img-circle system_icon" src="img/zyu.png">
                    </c:if>
					<div class="caption text-center">
						<h3 style="font-size:20px; height:30px; font-family:黑体; font-weight:bold;">${item.sysName}</h3>
					</div>
				</div>
			</a>
		</div>
	</c:forEach>
		<div class="col-sm-4 col-md-4">
			<a href="javascript:;" style="text-decoration : none" data-url="index">
				<div class="thumbnail" style="border:0px;padding:50px">
					<img class="img-circle system_icon" src="img/ups.jpg">
					<div class="caption text-center">
						<h3 style="font-size:20px; height:30px; font-family:黑体; font-weight:bold;">权限系统</h3>
					</div>
				</div>
			</a>
		</div>
</div>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">
	$("a").click(function(){
		var url = $(this).attr("data-url");
		window.parent.location.href = url;
	});
</script>
</body>
</html>
