<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="applicable-device" content="mobile">
<meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
<script type="text/javascript" src="${staticResourceUrl}/js/header.js${VERSION}002"></script>
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="yes" name="apple-touch-fullscreen" />
<title>自如民宿</title>
<link rel="stylesheet" type="text/css" href="${staticResourceUrl}/share/css/styles.css${VERSION}003">
</head>
<body>
	<header class="header">
		<c:if test="${ !empty sourceFrom && sourceFrom =='previewBtn' }">
			<a href="javascript:history.back(-1);" id="goback" class="goback"></a>
		</c:if>
		<h1>房东信息</h1>
	</header>
	<div class="fdxqContainer">
		<div class="fangzhu">
			<div class="touxiang"><div class="avator" style="background:url('${introduceVo.headPicUrl }') center no-repeat;background-size:cover;"></div></div>
			<h3>您好，我是${introduceVo.nickName }</h3>
			<p class="renzheng"><span>身份认证</span><span>房源认证</span></p>
			<p class="star p2 myCalendarStar" data-val="${introduceVo.eva }"><i class="s"></i><i class="s"></i><i class="s"></i><i class="h"></i><i></i> <span>${introduceVo.eva }分</span></p>
			<p class="geyan" id="geyan">${introduceVo.introduce }</p>
			<div class="btn active" id="geyanBtn"><a href="javascript:;">查看更多</a></div>
		</div>
		<div class="pingjia">
			<dl id="dl">
			</dl>
		</div>
		<%-- <div class="block3 fangyuan">
			<dl>
				<dt>我的其他房源</dt>
				<dd>
					<ul class="clearfix">
						<li>
							<c:forEach items="${list }" var="house">
								<a href="${basePath }tenantHouse/${noLoginAuth }/houseDetail?fid=${house.fid }&rentWay=${house.rentWay }">
									<div class="bannerImg">
										<img src="${house.picUrl }" width="100%" alt="">
										<span>¥ ${fn:split(house.price/100, ".")[0] }/晚</span>
									</div>
									<div class="describe">
										<p class="p1 overflowstyle">${house.picUrl }</p>
										<p class="condition">${house.rentWayName }｜<c:choose><c:when test="${house.personCount == 0 }">不限人数</c:when><c:otherwise>可住${house.personCount}人</c:otherwise></c:choose></p>
										<p class="star p2 myCalendarStar" data-val="${house.evaluateScore }"><i class="s"></i><i class="s"></i><i class="s"></i><i class="h"></i><i></i> <span>${house.evaluateScore }分</span></p>
									</div>
								</a>
							</c:forEach>
						</li>
					</ul>
				</dd>
			</dl>
		</div> --%>
	</div>

	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
	<script type="text/javascript">
		$(function(){
			setStar();
			queryTenEvaList(1,2);
			
			
			if($('#geyan').html()==''){
				$('#geyanBtn').hide();
				$('#geyan').hide();
			}
			
			if(($('#geyan').html().length<80)){
				$('#geyanBtn').hide();
			}
			
			$('#geyanBtn').click(function(){
			   $('#geyan').css('height','auto');
			   $(this).hide();				
			});

		});
		
		function setStar() {
	        $('.myCalendarStar').each(function(){
	            var iMyCalendarStar=$(this).attr('data-val').split('.');
	            
	            for(var i=0; i<iMyCalendarStar[0]; i++){
	                $(this).find('i').eq(i).addClass('s');
	            }
	
	            if(iMyCalendarStar[1]>0){
	                $(this).find('i').eq(iMyCalendarStar[0]).addClass('h');
	            }
	        });
		}
		
		function queryTenEvaList(page,limit){
			$.ajax({
				url:"${basePathHttps }/tenantEva/${noLoginAuth }/tenEvaList",
				data:{
					"landlordUid":"${landlordDto.landlordUid}",
					"page":page,
					"limit":limit
				},
				dataType:"json",
				type:"post",
				success:function(result) {
					if(result.code === 0){
						var htmlString = assembleHtml(result,page,limit);
						$("#dl").empty();
						$("#dl").append(htmlString);
					}else{
						showShadedowTips("操作失败",1);
					}
				},
				error:function(result){
					showShadedowTips("未知错误",1);
				}
			});
		}
		
		function assembleHtml(result,page,limit){
			var total = result.data.total;
			if(!total || total == 0){
				return;
			}
			
			var evaList = result.data.evaList;
			var htmlString = '';
			htmlString += '<dt>'+total+'个评价</dt>';
			htmlString += '<dd><ul>';
			$.each(evaList,function(i,eva){
				htmlString += '<li>';
				htmlString += '<div class="plren">';
				htmlString += '<div class="name">';
				htmlString += '<p class="p1">'+eva.nickName+'</p>';
				htmlString += '<p class="p2">'+eva.createTime+'入住</p>';
				htmlString += '</div>';
				/* htmlString += '<img src="'+eva.userHeadPic+'" alt="">'; */
				htmlString += '<div class="avator" style="background:url('+eva.userHeadPic+') center no-repeat;background-size:cover"></div>';
				htmlString += '</div>';
				htmlString += '<p class="plxq">'+eva.content+'</p>';
				htmlString += '</li>';
			});
			if(total > page*limit){
				htmlString += '<div class="btn"><a href="javascript:;" onclick="queryTenEvaList(1,'+total+')">所有评价</a></div>';
			}
			htmlString += '</ul></dd>';
			return htmlString;
		}
	</script>

</body>
</html>
