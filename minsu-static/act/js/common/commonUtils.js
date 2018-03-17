
//获取当前网址，如： http://localhost:8080/Tmall/index.jsp 
var curWwwPath=window.document.location.href; 

//获取主机地址之后的目录如：/Tmall/index.jsp 
var pathName=window.document.location.pathname; 
var pos=curWwwPath.indexOf(pathName); 

//获取主机地址，如： http://localhost:8080 
var localhostPaht=curWwwPath.substring(0,pos); 

//获取带"/"的项目名，如：/Tmall 
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
var SERVER_CONTEXT = localhostPaht+"/";
if(localhostPaht.indexOf("localhost")>0||localhostPaht.indexOf("127.0.0.1")>0||localhostPaht.indexOf("10.30.")>0){
	SERVER_CONTEXT = localhostPaht+projectName+"/";
}
var LOGIN_UNAUTH = "43e881";
var NO_LOGIN_AUTH = "ee5f86";
