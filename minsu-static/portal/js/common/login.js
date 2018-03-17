$(function(){
		var ms = {
				checkLogin : function(){
					$.ajax({
						url : PORTAL_FRONT_URL+"/user/login?"+Math.random(),
						dataType : "jsonp",
						type : "GET",
						success : function(data){
							if(data.code == 0){
								var _username = data.data.name;
								var _loginHtml = "<li class='usersInfo'>"+
								"<p class='icon_down'>"+
								"<a href=''>HI,"+_username+"</a>"+
								"</p>"+
								"<div class='c'>"+
								"<a href='"+PORTAL_FD_URL+"/house/lanHouseList' class='s'>我的房源</a>"+
								"<a href='"+PORTAL_FD_URL+"/customer/initPersonData' class='s'>个人资料</a>"+
								"<a id='doLoginOut' href='javascript:;'>退出登录</a>"+
								"</div>"+
								"</li>";
								$("li").remove(".users");
								$("li.downLoad").after(_loginHtml);
								
								//点击退出登陆，清除cookie
								$(".usersInfo").delegate('#doLoginOut','click',function(){
									ms.loginOut();
								})
							}
						}
					});
				},
				
				loginOut:function(){
					var currentUrl = window.location.href;
					$.ajax({
						url : PORTAL_FRONT_URL+"/user/loginOut",
						dataType : "jsonp",
						type : "GET",
						success : function(data){
							var _outHtml = '<li class="users">'
								+'<a href="'+SSO_USER_LOGIN_URL+currentUrl+'">登录</a>'
								+'<i>|</i>'
								+'<a href="'+SSO_USER_REGISTER_URL+currentUrl+'">注册</a>'
								+'</li>';
							
							$("li").remove(".usersInfo");
							$("li.downLoad").after(_outHtml);
							
							window.location.href = currentUrl;
						}
					});
				}
		}
		ms.checkLogin();
});
