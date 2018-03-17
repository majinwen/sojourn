/**
 * 公用js使用，能提出来的js大家都提出来哈
 * @param $ 
 */

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
//#登录并且不加密
//LOGIN_UNAUTH=43e881
//#不登录并且不加密
//NO_LOGIN_AUTH=ee5f86
var LOGIN_UNAUTH = "43e881";
var NO_LOGIN_AUTH = "ee5f86";
(function ($) {
	
	var commonUtils = {};
	
	/**
	 *  所有的ajax调用  可以使用此方法提交 url:要提交的路径 data:提交的数据 binary:提交成功的回调函数
	 *  @param url 请求的url
	 *  @param data 请求数据
	 *  @param callback 回调函数
	 */
	commonUtils.ajaxPostSubmit =  function (url,data,callback){
		$.ajax({
	    type: "POST",
	    url: url,
	    dataType:"json",
	    data: data,
	    success: function (result) {
	    	callback(result);
	    },
	    error: function(result) {
	       alert("error:"+result);
	        }
	     });
	}
	
	/**
	 * 生成uuid
	 */
	commonUtils.uuid = function() {
		var s = [];
		var hexDigits = "0123456789abcdef";
		for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
		}
		s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
		s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
		s[8] = s[13] = s[18] = s[23] = "-";

		var uuid = s.join("");
		return uuid;
		} 
	/**
	 * 
	 * @param url
	 * @param data
	 * @param headersParams 头参数
	 * @param callback
	 */
	commonUtils.ajaxPostHeaderSubmit =  function (url,data,headersParams,callback){
		$.ajax({
	    type: "POST",
	    url: url,
	    dataType:"json",
	    data: data,
	    headers: headersParams,
	    success: function (result) {
	    	callback(result);
	    },
	    error: function(result) {
	       alert("error:"+result);
	        }
	     });
	}
	/**
	 * 获取订单状态中文：//订单状态 10:待确认 20:待入住 30:强制取消 31:房东已拒绝 32:房客取消 33:未支付超时取消 40:已入住未生成单据 41：已入住已生成单据 50:正常退房中  51：提前退房中 60:待用户确认额外消费 71:提前退房完成 72:正常退房完成
	 * @param value
	 * @param row
	 * @param index
	 */
	commonUtils.getOrderStatu = function(value, row, index){
		
		var orderStatuStr="";
		if(value==10){
			orderStatuStr =  "待确认";
		}
		if(value == 20){
			orderStatuStr =  "待入住";
		}
		if(value==30){
			orderStatuStr =  "强制取消";
		}
		if(value == 31){
			orderStatuStr =  "房东已拒绝";
		}
		if(value==32){
			orderStatuStr =  "房客取消";
		}
		if(value == 33){
			orderStatuStr =  "未支付超时取消";
		}
		if(value == 34){
			orderStatuStr =  "强制取消申请中";
		}
		if(value==40){
			orderStatuStr =  "已入住未生成单据";
		}
		if(value == 41){
			orderStatuStr =  "已入住已生成单据 ";
		}
		if(value==50){
			orderStatuStr =  "正常退房中";
		}
		if(value==51){
			orderStatuStr =  "提前退房中 ";
		}
		if(value == 60){
			orderStatuStr =  "系待用户确认额外消费";
		}
		if(value==70){
			orderStatuStr =  "提前退房完成";
		}
		if(value == 71){
			orderStatuStr =  "正常退房完成";
		}
		if(orderStatuStr == ""){
			orderStatuStr =  "*";
		}
		
		return orderStatuStr;
	}
	
	
	
	
	/**
	 * 同意查询接口
	 */
	commonUtils.query = function(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}
	//分转化成元
	commonUtils.getMoney = function (value, row, index){
		
		if(value==null||value==""||value==undefined) {
			return "*";
		}
    	return parseFloat(value)/100;
		
	}
	// 格式化时间
	commonUtils.formateDate =  function(value, row, index) {
		
		  if(value==null||value == ""||value==undefined){
			  return "-";
		  }
		  var date = new Date(value);
		  var month = date.getMonth()+1;
		  var day = date.getDate();
		  var hours = date.getHours();
		  var minutes = date.getMinutes();
		  var seconds = date.getSeconds();
		  month = month<10?'0'+month:month;
		  day = day<10?'0'+day:day;
		  hours = hours<10?'0'+hours:hours;
		  minutes = minutes<10?'0'+minutes:minutes;
		  seconds = seconds<10?'0'+seconds:seconds;
		  return date.getFullYear()+"-"+month+"-"+day+"  "+hours+":"+minutes+":"+seconds;
	}
	// 格式化日期
	commonUtils.formateDay =  function(value, row, index) {
		
		  if(value==null||value == ""||value==undefined){
			  return "-";
		  }
		  var date = new Date(value);
		  var month = date.getMonth()+1;
		  var day = date.getDate();
		  var date = new Date(value);
		  month = month<10?'0'+month:month;
		  day = day<10?'0'+day:day;
		  return date.getFullYear()+"-"+month+"-"+day;
	}
	
	/**
	 * 获取常量1
	 */
	commonUtils.getConstant = function(value, row, index){
		
		return 1;
	}

	/* 加载树  公共方法封装  domId:id信息  obj:树的数据  binary：点击回调函数*/
	commonUtils.treeViewCommon =  function (domId,obj,binary){
		//加载左侧树	
	    $('#'+domId).treeview({
	        color: " inherit",
	        data: obj,
	        onNodeSelected: function (event, node) {
	        binary(event, node);
	     }
	  })
	}
	/* layer方法confirm  mes:展示的信息 iconNum：layer的icon binary：confirm方法回调  */
	 commonUtils.layerConfirm = function(mes,iconNum,binary){
		layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
			binary(index);
		});
	}
	/* 笑脸图标，显示2000ms 自动消失，不需要用户确认*/
	 commonUtils.msgLayer =  function (mesg){
		layer.msg(mesg, {icon: 6,time: 2000, title:'提示'});
	}
	 /**
	  * 获取国家 省 市  区的下拉列表 
	  * 条件：国家id必须为： nationCode  省id必须为：provinceCode 市id必须为cityCode  区id必须为areaCode
	  */
	 commonUtils.getNationInfo = function(id,obj){
		 
		 var pcode = $(obj).val();
		if(id === 'nationCode'){
			$("#provinceCode").empty();
			$("#cityCode").empty();
			$("#areaCode").empty();
		}else if(id === 'provinceCode'){
			$("#cityCode").empty();
			$("#areaCode").empty();
		}else if(id === 'cityCode'){
			$("#areaCode").empty();
		}
		 if(id!=undefined&&id!=null&&id!=null){
			 var _html="";
			 $("#"+id).empty();
			 commonUtils.ajaxPostSubmit(SERVER_CONTEXT+"config/city/getNationInfo", {"pcode":pcode}, function(nation){
				 if(nation){
					 _html +="<option value=''>请选择</option>";
					 for (var i = 0; i < nation.length; i++) {
						 _html +="<option value='"+nation[i].code+"'>"+nation[i].showName+"</option>";
					}
					 $("#"+id).html(_html);
				 }
			 })
		 }
		
	 }
	 /* 日历插件 */
	 commonUtils.datePicker = function(elemId){
		 laydate({
		      elem: '#'+elemId, //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		  });
	 }
	 
	 /*paramObj示例 {isWeightS:$('#test1').val(),zoName:$('#zoName').val()}*/
	 /* 分页  tableName:table名称,paramObj:参数对象,params:table内部对象     */
	 commonUtils.paginationCommon = function(tableName,paramObj,params){
			var result = {};
			result.limit = params.limit;
			result.page = $('#'+tableName).bootstrapTable('getOptions').pageNumber;
			
			if(typeof(paramObj) != undefined && typeOf(paramObj) == "object"){
				for(var param in paramObj){
					result.param = paramObj.param;
				}
			}
			return result;
	}
	/*发布房源步骤链接跳转*/
	commonUtils.houseUrlTurn=function(nowStep,clickStep){
		 var houseFid=$("#houseFid").val(); 
		 if(nowStep>=clickStep&&clickStep==0){
			 window.location.href="/houseIssue/locationMsg/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==1){
			 window.location.href="/houseIssue/basicMsg/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==2){
			 window.location.href="/houseIssue/configMsg/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==3){
			 window.location.href="/houseIssue/desc/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==4){
			 window.location.href="/houseIssue/toWholeOrSublet/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==5){
			 window.location.href="/houseIssue/pic/"+houseFid
		 }else if(nowStep>=clickStep&&clickStep==6){
			 window.location.href="/houseReleaseExtras/initExtrasInfo/"+houseFid
		 }
	} 
	window.CommonUtils = commonUtils;
}(jQuery));