<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>asura服务监控和管理平台 - 首页</title>

<link href="css/management.css" rel="stylesheet" type="text/css" />
<script src="js/management.js" type="text/javascript"></script>

<link href="js/dwz/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="js/dwz/themes/css/core.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
<link href="js/dwz/themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<script src="js/dwz/js/speedup.js" type="text/javascript"></script>
<!-- <script src="js/dwz/js/jquery-1.7.1.js" type="text/javascript"></script> -->
<script src="js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="js/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="js/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="js/dwz/js/jquery.bgiframe.js" type="text/javascript"></script>

<script src="js/dwz/js/dwz.min.js" type="text/javascript"></script>
<script src="js/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>

<script src="js/flot/jquery.flot.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	DWZ.init("js/dwz/dwz.frag.xml", {
		loginUrl:"logindialog.do", loginTitle:"重新登录",	// 弹出登录对话框
//		loginUrl:"login.do",	// 跳到登录页面
		debug:false,	// 调试模式 【true|false】
		pageInfo:{pageNum:"page", numPerPage:"pageSize", orderField:"orderField", orderDirection:"orderDirection"},
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"js/dwz/themes"});
		}
	});
});
</script>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<jsp:include flush="true" page="/header.do"/>
		</div>
		<div id="leftside">
			<jsp:include flush="true" page="/leftside.do"/>
		</div>
		<div id="container">
			<jsp:include flush="true" page="/container.do"/>
		</div>
	</div>
	<div id="footer">Copyright &copy; 2011-2035 Asura版权所有. All Rights Reserved.</div>
</body>
</html>