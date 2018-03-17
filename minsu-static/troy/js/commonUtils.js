/**
 * 公用js使用，能提出来的js大家都提出来哈
 * @param $
 */
(function ($) {
	
	var commonUtils = {};
	
	//获取当前网址，如： http://localhost:8080/Tmall/index.jsp 
	var curWwwPath=window.document.location.href; 

	//获取主机地址之后的目录如：/Tmall/index.jsp 
	var pathName=window.document.location.pathname; 
	var pos=curWwwPath.indexOf(pathName); 

	//获取主机地址，如： http://localhost:8080 
	var localhostPaht=curWwwPath.substring(0,pos); 

	//获取带"/"的项目名，如：/Tmall 
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
	
	var SERVER_CONTEXT = localhostPaht+projectName+"/";
	
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
	 * 获取订单状态中文：//订单状态 10:待确认 20:待入住 30:强制取消 31:房东已拒绝 32:房客取消 33:未支付超时取消 40:已入住未生成单据 41：已入住已生成单据 50:正常退房中  51：提前退房中 60:待用户确认额外消费 71:提前退房完成 72:正常退房完成
	 * @param value
	 * @param row
	 * @param index
	 */
	commonUtils.getOrderStatu = function(value, row, index){
		if(value==10){
			return "待确认";
		}
		if(value == 20){
			return "待入住";
		}
		if(value==30){
			return "强制取消";
		}
		if(value == 31){
			return "房东已拒绝";
		}
		if(value==32){
			return "房客取消";
		}
		if(value == 33){
			return "未支付超时取消";
		}
		if(value == 34){
			return "强制取消申请中";
		}
		if(value == 35){
			return "房东未确认超时取消";
		}
		if(value == 36){
			return "未确认取消";
		}
		if(value==40){
			return "已入住未生成单据";
		}
		if(value == 41){
			return "已入住已生成单据 ";
		}
		if(value==50){
			return "正常退房中";
		}
		if(value==51){
			return "提前退房中 ";
		}
		if(value == 60){
			return "系待用户确认额外消费";
		}
		if(value==71){
			return "提前退房完成";
		}
		if(value == 72){
			return "正常退房完成";
		}
		if(value == 73){
			return "客服协商退房";
		}
	}
	
	/**
	 * 同意查询接口
	 */
	commonUtils.query = function(){
		$('#listTable').bootstrapTable('selectPage', 1);
	}
	//分转化成元
	commonUtils.getMoney = function (value, row, index){
    	return parseFloat(value)/100;
		
	}
	// 格式化时间
	commonUtils.formateDate =  function(value, row, index) {
		  var date = new Date(value);
		  return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+"  "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
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
	window.CommonUtils = commonUtils;
}(jQuery));