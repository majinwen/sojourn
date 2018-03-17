<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device" content="mobile">
    <meta content="fullscreen=yes,preventMove=yes" name="ML-Config">
    <script type="text/javascript" src="${staticResourceUrl }/js/header.js${VERSION}001"></script>
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name = "format-detection" content = "telephone=no">
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="yes" name="apple-touch-fullscreen"/>
    <title>房间信息(5/6)</title>
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/mui.picker.min.css${VERSION}002" />  
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/styles_new.css${VERSION}001">
    <link rel="stylesheet" type="text/css" href="${staticResourceUrl }/css/css_830.css${VERSION}001">
</head>
<body>
	<header class="header"> 
	
	       <c:choose>
	    	<c:when test="${flag eq 1 ||flag eq 3}">
	    	<a href="${basePath}houseInput/43e881/goToHouseUpdate?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}&flag=${flag}&houseRoomFid=${houseRoomFid}" id="goback" class="goback" ></a>
	    	</c:when>
	    	<c:otherwise>
	    	 <a href="${basePath}houseInput/43e881/findHouseDetail?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}" id="goback" class="goback" ></a>
	    	</c:otherwise>
	       </c:choose>
       	<h1>房间信息
       	<c:if test="${flag ne 1&&flag ne 3 }">
         	(5/6)
       	</c:if>
       	</h1>
       	<%-- 房东指南 --%>
		<a href="https://zhuanti.ziroom.com/zhuanti/minsu/zhinan/fabuliucheng2.html" class="toZhinan"></a>
	</header>
		<div class="main" id="main">
			<div class="box z_scrollBox">
				<form id="addHouseFourForm" >
				    <input type="hidden" name="houseBaseFid" value="${houseBaseMsg.fid}">
				    <input type="hidden" name="rentWay" value="${houseBaseMsg.rentWay}">
				    <input type="hidden" name="roomNum" value="${houseBaseMsg.roomNum}">
				     <input type="hidden" name="houseRoomFid" id="houseRoomFid" value="${houseRoomFid}">
				    <input type="hidden" name="flag" id="flag" value="${flag}">
				    <%-- <input type="hidden" name="houseRoomFid" value="${houseRoomFid}"> --%>
					<ul class="z_list">
						<li class="mui-btn s mui-btn-active" id="houseBaseData"> 
							<input id="houseBaseDataShow" type="text" placeholder="请选择整套房屋的户型" value="${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台" readonly="readonly" class=" text">
							<span class="c_span">房源户型</span>
							<span class="c_ipt" id="houseBaseText">${houseBaseMsg.roomNum}室${houseBaseMsg.hallNum}厅${houseBaseMsg.kitchenNum}厨${houseBaseMsg.toiletNum}卫${houseBaseMsg.balconyNum}阳台</span>
							<span class="icon_r icon"></span>
						</li>
						<li class="z_fx_tips s">
							1、无独立客厅，例如loft、开间等房型，请选择0厅
							<br /> 2、发布独立房间时，需要选择整套房源的户型，并至少完善1间房间信息
							<br /> 3、发布整套房源时，至少为其中1个卧室添加床位信息	
						</li>
					</ul>
					<!--
						<li>					   
						    <span class="c_span">房间1</span>
						    <span class="c_ipt">已完成</span>
						    <span class="icon_r icon"></span>					   
						</li>
						<li class="s">
						 	<span class="text">完善房间2的信息</span>
						    <span class="icon_r icon"></span>
						</li>
					-->
					<ul id="baseHouseList" class="z_list hide" >
			            <c:forEach var="roomInfo" items="${roomList}" varStatus="no" >
							<li class="mui-table-view-cell saved_room" onclick="toRoomDetail(this)">
								<div class="mui-slider-right mui-disabled">	
								    <input class="fid" type="hidden" value="${roomInfo.fid }">
									<a class="mui-btn mui-btn-red">删除</a>
								</div>
								<div class="mui-slider-handle" onclick="">
								<c:if test="${ empty roomList }">				   
								    <span class="c_span">房间<span class="liIndex">${no.count }</span></span>
								    <span class="c_ipt">已完成</span></c:if>
								<c:if test="${ !empty roomList }">
									<%-- <c:if test="${fn:length(roomInfo.roomName) >17}"><span class="text">${fn:substring(roomInfo.roomName,0,17)}...</span></c:if> 
									<c:if test="${fn:length(roomInfo.roomName) <=17}"><span class="text">${roomInfo.roomName}</span></c:if>  --%>
									<span class="text">${roomInfo.roomName}</span>
								</c:if>  
								    <span class="icon_r icon"></span>
							    </div>					   
							</li>
			            </c:forEach>
		            </ul>
	            	<c:if test="${flag ne 1 && flag ne 3}">
		            	<ul class="z_list">
						<li class=" " onclick="toOptionalInfo();"> 
							<a href="javascript:void(0);">							
								<span class="text">可选信息</span>
								<span class="icon_r icon"></span>
							</a>
						</li>
					  </ul>
		            </c:if>
					
				</form>
			</div>
		</div><!--/main-->
		
	<ul class="bottomBtns clearfix" id="bottomBtns">
		<li id="addBtn" class="addBtn addBtn_disabled">添加房间</li>
	  		<c:choose>
		    	<c:when test="${flag eq 1 || flag eq 3}">
		    	   <li id="submitBtn" class="submitBtn submitBtn_disabled">确认</li>
		    	</c:when>
		    	<c:otherwise>
		    	   <li id="submitBtn" class="submitBtn submitBtn_disabled">下一步</li>
		    	</c:otherwise>
	       </c:choose>
	
	</ul>
	<input type="hidden" name="sIpt" id="sIpt" value="${houseBaseMsg.roomNum}" >
	<input type="hidden" name="tIpt" id="tIpt" value="${houseBaseMsg.hallNum}" >
	<input type="hidden" name="cIpt" id="cIpt" value="${houseBaseMsg.kitchenNum}" >
	<input type="hidden" name="wIpt" id="wIpt" value="${houseBaseMsg.toiletNum}" >
	<input type="hidden" name="yIpt" id="yIpt" value="${houseBaseMsg.balconyNum}" >
	<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/layer/layer.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl}/js/common/commonUtils2.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/bed_style2.js${VERSION}003"></script>
	
	<script type="text/javascript" src="${staticResourceUrl }/js/mui.min.js${VERSION}001"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/mui.picker.min.js${VERSION}002"></script>
	<script type="text/javascript" src="${staticResourceUrl }/js/iscroll-probe.js${VERSION}001"></script>
	<script type="text/javascript">
		var myScroll = new IScroll('#main', { scrollbars: true, probeType: 3, mouseWheel: true });
		var iWinHeight = $$(window).height();
		var top = $$(window).scrollTop()+iWinHeight;		
		var conHeight = $$(window).height()-$$('.header').height()-$$('.box_bottom').height();
		$$('#main').css({'position':'relative','height':conHeight,'overflow':'hidden'});
		$$('#main .box').css({'position':'absolute','width':'100%'});
		myScroll.refresh();
		document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	</script>
	<script type="text/javascript">
		(function($) {
	        $.init();
	        var result = $('#houseBaseDataShow')[0];
	        var btns = $('#houseBaseData');
	        
	        var oldVal = $$('#houseBaseDataShow').val();
	        var oldS = Number($$("#sIpt").val());//卧室
	        var oldT = Number($$("#tIpt").val());//客厅
	        var oldC = Number($$("#cIpt").val());//厨房
	        var oldW = Number($$("#wIpt").val());//卫生间
	        var oldY = Number($$("#yIpt").val());//阳台
	        var curVal = '';
	        var params = {};
	        btns.each(function(i, btn) {
	            btn.addEventListener('tap', function() {
	                var optionsJson = this.getAttribute('data-options') || '{}';
	                var options = JSON.parse(optionsJson);
	                var id = this.getAttribute('id');                       
	                var picker = new $.DtPicker(options);
	                picker.show(function(rs) { 
	                    $$('#houseBaseData').addClass('mui-btn-active');
	                	curVal = rs.text;
	                    var s = Number($$("#sIpt").val());//卧室
	                    var t = Number($$("#tIpt").val());//客厅
	                    var c = Number($$("#cIpt").val());//厨房
	                    var w = Number($$("#wIpt").val());//卫生间
	                    var y = Number($$("#yIpt").val());//阳台
                        
                        if(oldVal != curVal){
	                    	var fid = $$("input[name='houseBaseFid']").val();
	                    	params = {fid:fid,roomNum:s,hallNum:t,kitchenNum:c,toiletNum:w,balconyNum:y};
		                    if(oldS == 0){
		                    	ajaxPostSubmit(addRoom);
		                    } else if(oldS <= s){
		                    	ajaxPostSubmit();
		                    } else if(oldS > s){
		                    	if(s == 0){
		                    		mui.confirm('独立房间至少需要保留一个房间！', '', ['关闭'], function(e) {
		        					});
                        			restore();
		                    		return;
		                    	}
		                    	var savedSize = $$('#baseHouseList .saved_room').size();//页面新增房间数量
		                    	if(s >= savedSize){
			                    	ajaxPostSubmit(deleteRoom);
		                    	} else {
									layer.open({
										shade: true,
										shadeClose:true,
										content: '<div class="loginTips" style="line-height: 1.1rem">修改户型信息，会导致房间信息丢失，请确认是否继续修改？</div>',
										btn:['继续编辑', '放弃'],
										no: function(){
											layer.closeAll();
										},
										yes: function(){
											ajaxPostSubmit(deleteRoom);
										}
									});
		                        	/*mui.confirm('修改户型信息，会导致房间信息丢失，请确认是否继续修改？', '', btnArray, function(e) {
		                        		if (e.index == 1) { //这是确认
					                    	ajaxPostSubmit(deleteRoom);
		                        		}else {//这是取消
		                        			restore();
		                        		}
		                        	});*/
		                    	}
		                    }
                        }
                        countLiIndex();
        				checkButtonAvailable();
	                    picker.dispose();
	                }); 
	            }, false);
	        });
	        var btnArray = ['取消', '确认'];
	        
	        /**
	         * 保存房型
	         *
	         * @param func
	         */
	        ajaxPostSubmit = function(func){
	        	$$.ajax({
            	    url: "${basePath}roomMgt/${loginUnauth}/deleteHouseRooms",
            	    dataType:"json",
            	    data: params,
            	    type: "POST",
            	    success: function (result) {
            	    	if(result.code == 0){
            	    		$$('#houseBaseDataShow').val(curVal+'台');
    	                    $$('#houseBaseText').html(curVal+'台');
            	    		$$("input[name='roomNum']").val(params.roomNum);
            	    		oldVal = curVal;
            	    		oldS = params.roomNum;
            	    		oldT = params.hallNum;
            	    		oldC = params.kitchenNum;
            	    		oldW = params.toiletNum;
            	    		oldY = params.balconyNum;
            	    		
            	    		if(typeof(func) == "function"){
		                    	func(params);
            	    		}
        				}else{
    	                    $$("#sIpt").val(oldS);//卧室
    	                    $$("#tIpt").val(oldT);//客厅
    	                    $$("#cIpt").val(oldC);//厨房
    	                    $$("#wIpt").val(oldW);//卫生间
    	                    $$("#yIpt").val(oldY);//阳台
							showShadedowTips(result.msg,1);
        				}
            	    },
            	    error:function(result){
	                    $$("#sIpt").val(oldS);//卧室
	                    $$("#tIpt").val(oldT);//客厅
	                    $$("#cIpt").val(oldC);//厨房
	                    $$("#wIpt").val(oldW);//卫生间
	                    $$("#yIpt").val(oldY);//阳台
						showShadedowTips("未知错误",1);
					}
           	    });
	        }
	        
	        /**
			 * 页面删除房间dom
			 */
	        deleteRoom = function(params){
	        	var liSize = $$('#baseHouseList li').size();
    			if(liSize > params.roomNum){ //当房间条数大于选择的条数时
    				$$('#baseHouseList li').each(function(){
    					if($$(this).index() > (params.roomNum-1)){
    						$$(this).remove(); //删除房间信息
    					}
    				});
    			}
	        }
	        
	        /**
	         * 还原房型状态
	         */
	        restore = function(){
	        	$$("#sIpt").val(oldS);//卧室
                $$("#tIpt").val(oldT);//客厅
                $$("#cIpt").val(oldC);//厨房
                $$("#wIpt").val(oldW);//卫生间
                $$("#yIpt").val(oldY);//阳台
	        }
	    })(mui);
		
		mui.init();
		(function($) {
			$('#baseHouseList').on('tap', '.mui-btn', function(event) {
				var elem = this;
				var li = elem.parentNode.parentNode;
				var liSize = $$('#baseHouseList li').size();
	
				if(liSize==1){
					mui.confirm('最后一条房间信息不可删除！', '', ['关闭'], function(e) {
						$.swipeoutClose(li);
					});
				}else{
					mui.confirm('确认删除该条房间信息？', '', btnArray, function(e) {
						if (e.index == 1) { //这是确认
							var roomFid = $$(li).find(".fid").val();
							if(checkNullOrBlank(roomFid)){
								li.parentNode.removeChild(li);
								countLiIndex();
								checkButtonAvailable();
					        } else {
								$$.ajax({
				            	    url: "${basePath}roomMgt/${loginUnauth}/delRoomInfo",
				            	    dataType:"json",
				            	    data: {roomFid:roomFid},
				            	    type: "POST",
				            	    success: function (result) {
				            	    	if(result.code === 0){
											li.parentNode.removeChild(li);
											countLiIndex();
											checkButtonAvailable();
				            	    	} else {
											showShadedowTips("删除失败",1);
				            	    	}
				            	    },
				            	    error:function(result){
										showShadedowTips("未知错误",1);
									}
				           	    });
					        }
						} else {//这是取消
							setTimeout(function() {
								$.swipeoutClose(li);
							}, 0);
						}
					});
				}
			});
			var btnArray = ['取消', '确认'];
		})(mui);
	</script>
	<script type="text/javascript">
		$$(function(){
			init();
			
		});
		
		// 初始化
		function init(){
			var roomNum = Number($$("input[name='roomNum']").val());
	       	var liSize = $$('#baseHouseList li').size();
			if(roomNum > 0 && liSize === 0){
				addRoom();
			}
			$$("#baseHouseList").show();
			myScroll.refresh();
			checkButtonAvailable();
		}
		
		/**
		 * 页面增加房间dom
		 */
		function addRoom(){
       		var liSize = $$('#baseHouseList li').size();
       		liSize++;
       		
       		if(liSize > Number($$("#sIpt").val())){
       			mui.confirm('添加房间数不能大于总房间数！', '', ['关闭'], function(e) {
       			});
       			return;
       		}
       		var _html='<li class="mui-table-view-cell unsaved_room" onclick="toRoomDetail(this)">'
						+'<div class="mui-slider-right mui-disabled">'
							+'<input class="fid" type="hidden">'
							+'<a class="mui-btn mui-btn-red">删除</a>'
						+'</div>'
						+'<div class="mui-slider-handle">'
								+'<span class="text">完善房间<span class="liIndex">'+liSize+'</span>的信息</span>'
								+'<span class="icon_r icon"></span>'
						+'</div>'
					 +'</li>';
       		$$('#baseHouseList').append(_html).show();
       		myScroll.refresh();
       		checkButtonAvailable();
	    }
		
		function countLiIndex(){
			$$('.liIndex').each(function(){
				$$(this).html($$(this).parents('li').index()+1);
			});
		}

    	function checkNullOrBlank(param){
    		return typeof(param) == 'undefined' || param == null || $$.trim(param).length == 0;
    	}
    	
    	/**
		 * 校验按钮是否可用
		 */
    	function checkButtonAvailable(){
    		var roomNum = Number($$("#sIpt").val());//房间数量
    		var listSize = $$('#baseHouseList li').size();//页面房间数量
    		var unsavedSize = $$('#baseHouseList .unsaved_room').size();//页面新增房间数量
    		if(roomNum == 0 || roomNum == listSize || unsavedSize > 0){
    			$$('#addBtn').addClass('addBtn_disabled');
    		} else {
    			$$('#addBtn').removeClass('addBtn_disabled');
    		}
    		
    		if(roomNum == 0 || unsavedSize > 0){
    			$$('#submitBtn').addClass('submitBtn_disabled');
    		} else {
    			$$('#submitBtn').removeClass('submitBtn_disabled');
    		}
    	}
    	
    	/**
		 * 跳转房间信息页面
		 *
		 * param obj 页面房间dom元素
		 */
    	function toRoomDetail(obj){
    		var houseBaseFid = $$("input[name='houseBaseFid']").val();
        	var flag = $$("input[name='flag']").val();
        	var rentWay = $$("input[name='rentWay']").val();
       		var roomFid = $$(obj).find(".fid").val();
       		window.location.href = "${basePath}roomMgt/${loginUnauth}/toRoomDetail?houseBaseFid="+houseBaseFid+"&flag="+flag+"&rentWay="+rentWay+"&roomFid="+roomFid;
    	}
        
    	//分租  户型下一步，去更新步骤 为第5步，更新条件：a.当前房源为待发布状态   b.当前步骤为第4步
    	
        $$("#submitBtn").click(function(){
            if($$('#submitBtn').hasClass("submitBtn_disabled")){
            	CommonUtils.showShadedowTips("请先完善房间信息",1);
            }else{
            	var flag = $$("#flag").val();
            	if(flag == 1 ||flag == 3 ){
            		 window.location.href = "${basePath}houseInput/${loginUnauth}/goToHouseUpdate?houseBaseFid=${houseBaseMsg.fid}&houseRoomFid=${houseRoomFid}&rentWay=1"
            	}else{
            		CommonUtils.ajaxPostSubmit("${basePath}roomMgt/${loginUnauth}/updateHouseBase", {"houseBaseFid":"${houseBaseMsg.fid}"}, function(data){
               		   window.location.href = "${basePath}houseInput/${loginUnauth}/goToUpHousePic?houseBaseFid=${houseBaseMsg.fid}&rentWay=1&flag=${flag}&houseRoomFid=${houseRoomFid}"
            		})
            		
            		
            	}

            }
        })
        
        $$('#addBtn').on('click',function(){
        	var roomNum = Number($$("#sIpt").val());//房间数量
    		var listSize = $$('#baseHouseList li').size();//页面房间数量
    		if(roomNum == listSize){
	       		CommonUtils.showShadedowTips("添加房间数不能大于总房间数",1);
    			return;
    		}
    		
	       	if($$('#addBtn').hasClass("addBtn_disabled")){
	       		CommonUtils.showShadedowTips("请先完善房间信息",1);
	       	} else {
		       	addRoom();
	       	}
       });
        
       function toOptionalInfo(){
    	   window.location.href = "${basePath}houseIssue/${loginUnauth}/showOptionalInfo?houseBaseFid=${houseBaseMsg.fid}&rentWay=${houseBaseMsg.rentWay}&houseFive=2&flag=${flag}";
       }
	</script>
</body>
</html>
