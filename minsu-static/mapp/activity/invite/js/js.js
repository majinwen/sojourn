// 是否为微信
function isWeiXin(){
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){
		return true;
	}else{
		return false;
	}
}

// 地址栏获取信息
function GetQueryString(name)
{
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}

// 翻转屏幕提示
window.addEventListener("onorientationchange" in window ? "orientationchange" : "resize", function() {
	detectOrientation();
}, false);

(function() {
	detectOrientation();
})();
function detectOrientation() {
	if (window.orientation === 180 || window.orientation === 0) { 
		$('.dialog-guide').hide();
	}  
	if (window.orientation === 90 || window.orientation === -90 ){  
		$('.dialog-guide').show();
	}
}

// Android 2.3.x 动画退化处理
function decideAndroid23(ua) {
	ua = (ua || navigator.userAgent).toLowerCase();
	return ua.match(/android.2\.3/) ? true : false;
}

// 弹层
function layerModal(obj){
	if(!obj.fn){
		obj.fn = function(){
			layer.close();
		}
	}
	layer.open({
		type: 1,
		title:obj.title || '',
		skin: 'layui-layer-demo', 
		area: ['7rem'],
		closeBtn:obj.isClose || 0,
		shadeClose:obj.shadeClose || false,
		content: obj.str || '',
		btn:obj.btn || '',
		success:function(){
			obj.fn();
		}

	});
}
(function(){
	$(".modal").click(function(){
		$(this).hide();
	})
})()