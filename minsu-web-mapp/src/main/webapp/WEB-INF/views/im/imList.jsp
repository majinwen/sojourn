<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
		<meta charset="utf-8">
		<title>我的消息</title>
		<script type="text/javascript" src="${staticResourceUrl }/js/header.js"></script>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<link rel="stylesheet" href="${staticResourceUrl }/css/mui.min.css">
		<link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles.css">
	</head>
<body class="bgF">
			<header class="header">
			<c:if test="${msgType ==2 }">
			   <a href="javascript:;"  id="goback" class="goback" onclick="Im.goBack();"></a>
			</c:if>
				<h1>我的消息</h1>
			</header>
			<div id="main">
				<div class="mui-content">
					<ul id="OA_task_1" class="mui-table-view">
					</ul>
				</div>
			</div>
		<input type="hidden" id="imUrl" value="${imUrl }" />
		<input type="hidden" id="msgType" value="${msgType }" />
	    <input type="hidden" id="imSource" value="${sourceType }" />
	    <input type="hidden" id="version" value="${version }" />
	    <input type="hidden" id="imSourceList" value="${imSourceList }" />
	    
		
		<c:if test="${msgType !=2 }">
		  <%@ include file="../common/bottom.jsp" %>
		</c:if>

		<c:if test="${msgType ==2 }">
	        <script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
			<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
			<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
			<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
		</c:if>
		
		<script src="${staticResourceUrl}/js/mui.min.js${VERSION}006"></script>
		<script src="${staticResourceUrl}/js/iscroll-probe.js${VERSION}006"></script>
		<script src="${staticResourceUrl}/js/im/im.js${VERSION}004" type="text/javascript"></script>

</body>
		
</html>