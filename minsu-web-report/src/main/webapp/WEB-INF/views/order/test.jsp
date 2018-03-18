<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">


	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">


	<title>H+ 后台主题UI框架 - 百度ECHarts</title>
	<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
	<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

	<link rel="shortcut icon" href="favicon.ico"> <link href="${staticResourceUrl}css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	<link href="${staticResourceUrl}css/font-awesome.css?v=4.4.0" rel="stylesheet">

	<link href="${staticResourceUrl}css/animate.css" rel="stylesheet">
	<link href="${staticResourceUrl}css/style.css?v=4.1.0" rel="stylesheet">


</head>


<body class="gray-bg">
<div class="row  border-bottom white-bg dashboard-header">
	<div class="col-sm-12">
		<p>ECharts开源来自百度商业前端数据可视化团队，基于html5 Canvas，是一个纯Javascript图表库，提供直观，生动，可交互，可个性化定制的数据可视化图表。创新的拖拽重计算、数据视图、值域漫游等特性大大增强了用户体验，赋予了用户对数据进行挖掘、整合的能力。 <a href="http://echarts.baidu.com/doc/about.html" target="_blank">了解更多</a>
		</p>
		<p>ECharts官网：<a href="http://echarts.baidu.com/" target="_blank">http://echarts.baidu.com/</a>
		</p>

	</div>

</div>
<div class="wrapper wrapper-content animated fadeInRight">


	<div class="row">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-title">
					<h5>柱状图</h5>
					<div class="ibox-tools">
						<a class="collapse-link">
							<i class="fa fa-chevron-up"></i>
						</a>
						<a class="dropdown-toggle" data-toggle="dropdown" href="graph_flot.html#">
							<i class="fa fa-wrench"></i>
						</a>
						<ul class="dropdown-menu dropdown-user">
							<li><a href="graph_flot.html#">选项1</a>
							</li>
							<li><a href="graph_flot.html#">选项2</a>
							</li>
						</ul>
						<a class="close-link">
							<i class="fa fa-times"></i>
						</a>
					</div>
				</div>
				<div class="ibox-content">

					<div class="echarts" id="echarts-bar-chart"></div>
				</div>
			</div>
		</div>
	</div>


</div>
<!-- 全局js -->
<script src="${staticResourceUrl}js/jquery.min.js?v=2.1.4"></script>
<script src="${staticResourceUrl}js/bootstrap.min.js?v=3.3.6"></script>



<!-- ECharts -->
<script src="${staticResourceUrl}js/plugins/echarts/echarts-all.js"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}js/content.js?v=1.0.0"></script>


<!-- ECharts demo data -->
<script src="${staticResourceUrl}js/minsu/test/afi.js"></script>

<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
<!--统计代码，可删除-->

</body>
</html>
