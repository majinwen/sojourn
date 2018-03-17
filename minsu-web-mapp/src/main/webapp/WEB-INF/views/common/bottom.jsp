<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="bottomNav">
	<a href="${basePath }orderland/${loginUnauth }/showlist"  id="orderMenu"><i class="dd"></i>订单</a>
	<a href="${basePath }houseMgt/${loginUnauth }/myHouses"  id="houseMenu"><i class="fy"></i>房源</a>
	<a href="${basePath }im/${loginUnauth }/imIndex"  id="imMenu"><i class="xx" ></i>消息</a>
	<a href="${basePath }personal/${loginUnauth }/initPersonalCenter"  id="userCenterMenu"><i class="wd"></i>我的</a>
</div>
<input  type="hidden" id="menuType" value="${menuType }" />
<input  type="hidden" id="landlordUid_menu" value="${landlordUid }" />
<script src="${staticResourceUrl}/js/jquery-2.1.1.min.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl }/js/common/commonUtils.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/layer/layer.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/common.js${VERSION}001"></script>
<script type="text/javascript">

$(function(){
	var menuType = $("#menuType").val();
	var uid = $("#landlordUid_menu").val();
	initMenu(menuType);
	
	if(uid != ""){
		showAllUnReadMsgNum(uid);
	}
})

function showAllUnReadMsgNum(uid){
	$.getJSON(SERVER_CONTEXT+"im/"+LOGIN_UNAUTH+"/getAllUnReadMsgNum",{landlordUid:uid},function(data){
		if(data.code == 0){
			if(data.data.count > 0){
				$("#imMenu").append("<b class='n' id='totalNums'>"+data.data.count+"</b>");
			}else{
				$("#totalNums").remove();
			}
		}
	});
}
/**
 * 初始化菜单选中状态
 */
function initMenu(menuType){
	if(menuType==null||menuType==""||menuType==undefined){
		menuType = "order";
	}
	var id = menuType+"Menu";
	switch (menuType) {
	case "order":
		$("#"+id).addClass("active");
		break;
    case "house":
    	$("#"+id).addClass("active");
		break;
   case "im":
	   $("#"+id).addClass("active");
		break;
   case "userCenter":
	   $("#"+id).addClass("active");
		break;
	default:
		$("#order").addClass("active");
		break;
	}
	
}
</script>
