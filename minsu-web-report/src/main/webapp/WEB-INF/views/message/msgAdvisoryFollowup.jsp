<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <base href="${basePath }>">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}"rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}"rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" media="screen" href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid-bootstrap.css${VERSION}" />
    <link rel="stylesheet" type="text/css" media="screen" href="${staticResourceUrl}/css/plugins/jqgrid/ui.jqgrid-bootstrap.css" />





</head>
<body class="gray-bg">


<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row row-lg">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="commentForm">
                        <div class="form-group">
                             <div class="row">
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">预订人姓名:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="tenantName" name="tenantName" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">预订人手机号:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="tenantTel" name="tenantTel" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房源名称:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="houseName" name="houseName" type="text"
                                           class="form-control">
                                </div>
                            </div>
                           <div class="row">
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房东姓名:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="landlordName" name="landlordName" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房东电话:</label>
                                </div>
                                <div class="col-sm-2">
                                    <input id="landlordTel" name="landlordTel" type="text"
                                           class="form-control">
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">跟进状态:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="followStatus" id="followStatus">
                                        <option value="">请选择</option>
                                        <option value="10">未跟进</option>
                                        <option value="20">跟进中</option>
                                        <option value="30">跟进結束</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">国家:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="nationCode" id="nationCode">
                                        <option value="">请选择</option>
                                        <option value="100000">中国</option>
                                        <option value="386">日本</option>
                                    </select>
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">房源城市:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="nationCode" id="cityCode">
                                        <option value="">请选择</option>
                                        <c:forEach items="${cityList}" var="obj">
                                            <option value="${obj.code }">${obj.name }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-1 text-right">
                                    <label class="control-label"
                                           style="height: 35px; line-height: 35px;">每页人数:</label>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" name="limit" id="limit">
                                        <option value="3">3人</option>
                                        <option value="5" selected>5人</option>
                                        <option value="10">10人</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                    <div class="col-sm-1 text-right">
                                        <label class="control-label"
                                           style="height: 35px; line-height: 35px;">首次咨询日期：</label>
                                    </div>
                                    <div class="col-sm-2">
                                     <input id="msgSendStartTime" name="msgSendStartTime" class="laydate-icon form-control layer-date" >
                                    </div>
                                     <div class="col-sm-1 text-right">
                                        <label class="control-label"
                                           style="height: 35px; line-height: 35px;">到：</label>
                                    </div>
                                    <div class="col-sm-3 ">
                                        <input id="msgSendEndTime" name="msgSendEndTime" class="laydate-icon form-control layer-date">
                                    </div>
                            </div>
                            <div class="row">
								<div class="col-sm-12">
									<font color="#FF0000">如“订单创建时间未”未选择，默认为1个月前</font>
								</div>
							</div>

                           <div class="row">
	                                <div class="col-sm-1">
	                                </div>
	                                <div class="col-sm-6">
	                                    <div class="col-sm-1 ">
	                                        <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
	                                    </div>
	                                </div>
	                                <div class="col-sm-3">
									     <div class="col-sm-1 ">
											<button class="btn btn-primary" type="button" onclick="exportFile();" >
												<i class="fa fa-search"></i>&nbsp;导出文件
											</button>
									    </div>
	                               </div>
						 </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <div class="example-wrap">
                        <div class="example">
                            <div style="margin-left:20px;">
                                <table id="jqGrid"></table>
                                <div id="jqGridPager" style="height: 50px"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</div>

<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/i18n/grid.locale-cn.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/jqgrid/jquery.jqGrid.min.js${VERSION}"></script>
<script>
    $.jgrid.defaults.width = 780;
    $.jgrid.defaults.responsive = true;
    $.jgrid.defaults.styleUI = 'Bootstrap';
</script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}001" type="text/javascript"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<script type="text/javascript">
/*      $(function () {
        CommonUtils.getNationInfo('nationCode');
    })  */

    $(document).ready(function () {
        $("#jqGrid").jqGrid({
            url:"messageFollowup/queryAllNeedFollowList",
            datatype: "json",
            //data: mydata,
            postData: {
                limit: $('#limit option:selected').val(),
                msgSendStartTime:beforeSixMonthStr,
                msgSendEndTime:nowdateStr,
            },
            height: 500,
            width: document.body.clientWidth-80,
            rowNum: 1000,
            //rowList: [5,10,15,20],
            jsonReader: {
                root:"dataList", page:"currPage", total:"totalpages",
                records:"totalCount", repeatitems:false, id : "id"
            },
            colModel: [
                { label: '预订人uid姓名', name: 'tenantUid', hidden:true,width: 100}, 
                { label: '预订人姓名/昵称', name: 'tenantName', width: 150},
                { label: '预订人手机', name: 'tenantTel', width: 100 },
                { label: '房源名称', name: 'houseName', width: 200},
                { label: '城市', name: 'cityName', width: 100 },
                { label: '房东姓名', name: 'landlordName', width: 100 },
                { label: '房东手机', name: 'landlordTel', width: 100 },
                { label: '首咨创建时间', name: 'createTimeStr', width: 160 },
                { label: '跟进状态', name: 'followStatusName', width: 100},
                { label: '所有跟进人', name: 'empNameList', width: 120},
            ],
            // viewrecords: true, // show the current page, data rang and total records on the toolbar
            //caption: "Custom Summary Type",
            shrinkToFit:false,
            autoScroll: true,
            grouping: true,
            groupingView: {
                groupField: ["tenantUid"],
                groupDataSorted : false,
                groupColumnShow: [false],
                groupText: ["<b>{0}</b>"],
                groupOrder: ["asc"],
                groupSummary: [false],
                groupCollapse: false
            },
            pager: "#jqGridPager"
        });
    });
    var nowdate = new Date();
    var nowdateStr = check(nowdate,' YYYY-MM-dd HH:mm:ss');
    var beforeSixMonth = startTime(nowdate,2);
    var beforeSixMonthStr = check(beforeSixMonth,' YYYY-MM-dd HH:mm:ss');
  
    //查询当前时间month个月之前的时间
    function startTime(mydate,month){
	     nowdate.setMonth(nowdate.getMonth()-month);
	     var y = nowdate.getFullYear();
	     var m = nowdate.getMonth();
	     var d = nowdate.getDate();
	     var h = nowdate.getHours();
	     var a= nowdate.getMinutes();
	     var s = nowdate.getSeconds();
	     var newNowDate = new Date(y,m,d,h,a,s)
	     return newNowDate;
    };

    //比较两个date是否在month个月内
    function compare(mydate1,mydate2,month){
	    mydate1.setMonth(mydate1.getMonth() + month);
	    var y = mydate1.getFullYear();
	    var m = parseInt(mydate1.getMonth())+1;
	    var d = mydate1.getDate();
	    if(m>=(mydate2.getMonth()+1) ){
	    return true;
    }
    return false;
    };

    //比较两个字符串时间参数之间相差的天数
    function compareStr(beforeStr,afterStr){
    	if(beforeStr == null || beforeStr == undefined || beforeStr == ''){
    		 layer.alert("开始时间不能为空！", {icon: 6,time: 4000, title:'提示'});
  	         return false;
    	}
    	if(afterStr == null || afterStr == undefined || afterStr == ''){
   		     layer.alert("截止时间不能为空！", {icon: 6,time: 4000, title:'提示'});
 	         return false;
     	}
	    var s_str = (beforeStr).replace(/-/g,"/");
	    var s_date = new Date(s_str);
	
	    var end_str = (afterStr).replace(/-/g,"/");
	    var end_date = new Date(end_str);
	    if(s_date>=end_date){
	    	 layer.alert("开始时间必须小于结束时间！", {icon: 6,time: 4000, title:'提示'});
		     return false;
	    }
	    var num = (end_date-s_date)/(1000*3600*24);
	    var days = parseInt(Math.ceil(num));
	    if(days > 180){
	       layer.alert("开始时间到截止时间应在6个月之内！", {icon: 6,time: 4000, title:'提示'});
	       return false;
	    }
	    return true;
    };
    
    function query(){
    	var isCanSubmit = compareStr($('#msgSendStartTime').val(),$('#msgSendEndTime').val());
    	if(isCanSubmit){
    		$("#jqGrid").jqGrid('setGridParam',{
                url:"messageFollowup/queryAllNeedFollowList",
                postData:{
                    limit : $('#limit option:selected').val(),
                    tenantName : $.trim($('#tenantName').val()),
                    tenantTel : $.trim($('#tenantTel').val()),
                    houseName : $('#houseName').val(),
                    landlordName : $('#landlordName').val(),
                    landlordTel : $('#landlordTel').val(),
                    nationCode : $('#nationCode').val(),
                    provinceCode : $('#provinceCode').val(),
                    cityCode : $('#cityCode').val(),
                    followStatus : $('#followStatus option:selected').val(),
                    msgSendStartTime : $('#msgSendStartTime').val(),
                    msgSendEndTime : $('#msgSendEndTime').val(), 
                }, //发送数据
                page:1
            }).trigger("reloadGrid"); //重新载入
    	}
    }
    
    function exportFile(){
    	var isCanSubmit = compareStr($('#msgSendStartTime').val(),$('#msgSendEndTime').val());
    	if(isCanSubmit){
    		 var tenantName = $('#tenantName').val();
        	 var tenantTel = $('#tenantTel').val();
        	 var houseName = $('#houseName').val();
        	 var landlordName = $('#landlordName').val();
        	 var  landlordTel = $('#landlordTel').val();
        	 var nationCode = $('#nationCode').val();
        	 var cityCode = $('#cityCode').val();
        	 var followStatus = $('#followStatus option:selected').val();
        	 var msgSendStartTime = $('#msgSendStartTime').val();
        	 var msgSendEndTime = $('#msgSendEndTime').val();
    		 var url = "messageFollowup/queryAllNeedFollowListExcel?"+
    		                "tenantName="+tenantName+
    						"&tenantTel="+tenantTel+
    						"&houseName="+houseName+
    						"&landlordName="+landlordName+
    						"&landlordTel="+landlordTel+
    						"&nationCode="+nationCode+
    						"&cityCode="+cityCode+
    						"&followStatus="+followStatus+
    						"&msgSendStartTime="+msgSendStartTime+
    						"&msgSendEndTime="+msgSendEndTime;
    		window.location.href = url;
    	}
	} 


    function showPayStatus(value, row, index){
        if(value == "1"){
            return "已支付";
        }else{
            return "未支付";
        }
    }

    //显示信息详情
    function showLink(field, value, row){
        return "<a onclick='showDetail(\""+field+"\")' href='javascript:void(0);'>"+field+"</a>";
    }

    var showDetail =  function(orderSn){
        if(orderSn == null||orderSn=="" ||orderSn == undefined){
            alert("订单不存在");
            return false;
        }
        $.openNewTab(new Date().getTime(),'follow/getFollowDetail?orderSn='+orderSn, "订单跟进");
    }
    
  //获取两个月前时间
    var oneMonthBefore = getValueMonthBefore(1);
    $(function (){
    	//初始化日期
		$('#msgSendStartTime').val(oneMonthBefore);
		//初始化日期
		datePickerFormat('msgSendStartTime', '00:00:00');
		datePickerFormat('msgSendEndTime', '23:59:59');
	});


	function datePickerFormat(elemId, time){
		laydate({
			elem: '#' + elemId,
			format: 'YYYY-MM-DD ' + time,
			istime: false,
			istoday: true
		});
	}
	
	 /* 获取value个月之前的时间戳 */
	function getValueMonthBefore(value){       
	       var resultDate,year,month,date,hms;      
	       var currDate = new Date();     
	       year = currDate.getFullYear();     
	       month = currDate.getMonth()+1;     
	       date = currDate.getDate();     
	       hms = currDate.getHours() + ':' + currDate.getMinutes() + ':' + (currDate.getSeconds() < 10 ? '0'+currDate.getSeconds() : currDate.getSeconds());      
	       switch(month)     
	       {     
	            case 1:     
	            case 2:     
	            case 3:     
	                month += 9;     
	                year--;     
	                break;     
	            default:     
	                month -= value;     
	                break;     
	       }     
	        month = (month < 10) ? ('0' + month) : month;     
	             
	       resultDate = year + '-'+month+'-'+date+' ' + hms; 
	       resultDate = getLocalTime(resultDate);
	    return resultDate;     
	} 

    //将date格式转换为string格式
    function check(date, format) { 
          var v = ""; 
          if (typeof date == "string" || typeof date != "object") { 
            return; 
          } 
          var year  = date.getFullYear(); 
          var month  = date.getMonth()+1; 
          var day   = date.getDate(); 
          var hour  = date.getHours(); 
          var minute = date.getMinutes(); 
          var second = date.getSeconds(); 
          var weekDay = date.getDay(); 
          var ms   = date.getMilliseconds(); 
          var weekDayString = ""; 
            
          if (weekDay == 1) { 
            weekDayString = "星期一"; 
          } else if (weekDay == 2) { 
            weekDayString = "星期二"; 
          } else if (weekDay == 3) { 
            weekDayString = "星期三"; 
          } else if (weekDay == 4) { 
            weekDayString = "星期四"; 
          } else if (weekDay == 5) { 
            weekDayString = "星期五"; 
          } else if (weekDay == 6) { 
            weekDayString = "星期六"; 
          } else if (weekDay == 7) { 
            weekDayString = "星期日"; 
          } 
      
          v = format; 
          //Year 
          v = v.replace(/yyyy/g, year); 
          v = v.replace(/YYYY/g, year); 
          v = v.replace(/yy/g, (year+"").substring(2,4)); 
          v = v.replace(/YY/g, (year+"").substring(2,4)); 
      
          //Month 
          var monthStr = ("0"+month); 
          v = v.replace(/MM/g, monthStr.substring(monthStr.length-2)); 
      
          //Day 
          var dayStr = ("0"+day); 
          v = v.replace(/dd/g, dayStr.substring(dayStr.length-2)); 
      
          //hour 
          var hourStr = ("0"+hour); 
          v = v.replace(/HH/g, hourStr.substring(hourStr.length-2)); 
          v = v.replace(/hh/g, hourStr.substring(hourStr.length-2)); 
      
          //minute 
          var minuteStr = ("0"+minute); 
          v = v.replace(/mm/g, minuteStr.substring(minuteStr.length-2)); 
      
          //Millisecond 
          v = v.replace(/sss/g, ms); 
          v = v.replace(/SSS/g, ms); 
            
          //second 
          var secondStr = ("0"+second); 
          v = v.replace(/ss/g, secondStr.substring(secondStr.length-2)); 
          v = v.replace(/SS/g, secondStr.substring(secondStr.length-2)); 
            
          //weekDay 
          v = v.replace(/E/g, weekDayString);
          return v; 
        } 
    
    function getLocalTime(value) {
		// return new Date(parseInt(nS)).toLocaleString().substr(0,17)
		if(value==null||value == ""||value==undefined){
			return "";
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
		return date.getFullYear()+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
	}

</script>

</body>

</html>
