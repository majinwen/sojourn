<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="zh">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=750,user-scalable=no">
	<meta name="format-detection"content="telephone=no">
	<title>自如民宿-苏州私家园林宝俭堂 </title>
	<meta name="keywords" content=""/>
    <meta name="description" content=""/>
	<style type="text/css">
		*{ padding: 0; margin: 0;}
		html,body{ width: 100%;}
		@-webkit-keyframes suzhou_banner_show{
			from{
				opacity: 0;
			}
			to{
				opacity: 1;
			}
		}
		@keyframes suzhou_banner_show{
			from{
				opacity: 0;
			}
			to{
				opacity: 1;
			}
		}
		.suzhou_banner_show{ animation: suzhou_banner_show 4s 1s; -webkit-animation: suzhou_banner_show 4s 1s;}
		/*分享图样式*/
		.share{position: absolute; left: -300px; top:-300px; width: 300px; height: 300px;}
		.suzhou_text_box{ padding:20px 30px;}
		.suzhou_text_box p{ padding-top: 30px; padding-bottom: 20px; font-size: 25px; line-height: 38px; color: #999;}
	</style>
	<title>苏州</title>
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/styles.css${VERSION}001">
	<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/css/pageStyles.css${VERSION}001">
</head>
<body>
	<img src="${staticResourceUrl}/images/suzhou/share.jpg" class="share">
	<div class="jcBox">
		<div class="banners" style="position: relative; overflow: hidden;">
			<img src="${staticResourceUrl}/images/suzhou/suzhou_banner_text.png" class="suzhou_banner_show" style="width: 100%; position: absolute; left: 0; top: 0;"/>
			<div style="min-height: 960px; height: auto;">
				<img src="${staticResourceUrl}/images/suzhou/suzhou_banner_bg.jpg" style="width: 100%;"/>
			</div>
			<div class="suzhou_text_box">
				<p>这里是南宋左丞、户部尚书，观文殿大学士、词人叶梦得故居</p>
				<img src="${staticResourceUrl}/images/suzhou/suzhou_text_img.jpg" style="width: 100%;"/>
				<p>许青冠夫妇用七年时间倾尽所能还原修缮<br />守护千年古韵，阐释厚德载福之家训<br />在莲香榭作诗赏景<br />卧房内朱红床歇息<br />山水下细饮茶静心<br />山水、池鱼、花香、鸟鸣<br />遗落的文化瑰宝重焕新美境</p>
			</div>
			
		</div>
	</div>
	
	<ul class="jcList">
		<c:forEach items="${list }"  var="obj">
			<li>
			<a href="javascript:void(0);" onclick="minsutuijianJump('${obj.fid }','${obj.rentWay }','${shareFlag }','${shareUrl }');">		
				<div class="img">
					<img src="${obj.picUrl } " class="setImg">
					<span class="t"><b>￥</b><fmt:parseNumber integerOnly="true" value="${obj.price/100 }" />元/天</span>
				</div>
				<div class="txt">
					<div class="ts">
						<img src="${obj.landlordUrl }">
					</div>
					<h2>${obj.houseName }</h2>
					<p class="gray">${obj.rentWayName } ｜ 可住${obj.personCount }人  </p>
					<p class="star">
						<b class="myCalendarStar" data-val="${obj.evaluateScore }">
							<i></i><i></i><i></i><i></i><i></i> 
						</b>
						<span>${obj.evaluateScore }分</span>
						<c:if test="${obj.evaluateCount != 0}">
							<b class="gray">评论${obj.evaluateCount }条</b>
						</c:if>
					</p>

				</div>
			</a>
			</li>
		 </c:forEach>
	</ul>
	
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script src="${staticResourceUrl}/js/common.js${VERSION}002"></script>
	<%--<script src="http://catcher.ziroom.com/assets/js/boomerang.min.js"></script>--%>
	<%--<script>--%>
	   <%--BOOMR.init({--%>
	   <%--beacon_url: "http://catcher.ziroom.com/beacon",--%>
	   <%--beacon_type: "POST",--%>
	   <%--page_key: "minsu_H5_suzhou_160913"});--%>
	<%--</script>--%>
</body>
</html>