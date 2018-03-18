<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath }">
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

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row row-lg">
         <div class="tabs-container">
            <ul class="nav nav-tabs">
			<li class="active" id="entireLi">
				<a href="javascript:entireHouseOrderList();">整租房源订单统计列表</a>
			</li>
			<li id="subLi">
				<a href="javascript:subHouseOrderList();">合租房源订单统计列表</a>
			</li>
			
			
		 </ul>
         </div>
		 <div class="col-sm-12">
		    <div class="ibox float-e-margins">
               <div class="ibox-content">
	              <div class="row">
               		<div class="col-sm-12">
               		<label class="col-sm-1 control-label mtop">从:</label>
                          <div class="col-sm-2 ">
                               <input id="beginTime"  value=""  name="beginTime" class="laydate-icon form-control layer-date">
                          </div>
                          
                          <label class="col-sm-1 control-label mtop">到:</label>
                          <div class="col-sm-2">
                               <input id="endTime" value="" name="endTime" class="laydate-icon form-control layer-date">
                          </div>
                         </div>
				   </div>
					<div class="row">
						
					    <label class="col-sm-1 control-label mtop">城市:</label>
                            <div class="col-sm-2">
                               <select class="form-control m-b" id="cityCode" name="cityCode" >
                               <option value="">请选择</option>
                               <c:forEach items="${cityList}" var="obj">
									<option value="${obj.code }">${obj.name }</option>
								</c:forEach>
                              </select>
                            </div>
                            <label class="col-sm-1 control-label left2">房东手机号：</label>
                              <div class="col-sm-2 left2">
		                       <input id="lanCustomerMobile" name="lanCustomerMobile"  type="text" class="form-control" >
		                   </div>
		                   
		                   <label class="col-sm-1 control-label left2">运营专员姓名：</label>
                              <div class="col-sm-2 left2">
		                       <input id="empGuardName" name="empGuardName"  type="text" class="form-control" >
		                   </div>
		                   
		                   <!-- <label class="col-sm-1 control-label left2">地推管家姓名：</label>
                              <div class="col-sm-2 left2">
		                       <input id="empPushName" name="empPushName"  type="text" class="form-control" >
		                   </div> -->
		                   
					</div>
					<div class="row">
					<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
							<customtag:authtag authUrl="order/houseOrderDataList">
								<button class="btn btn-primary" type="button" onclick="query();">
									<i class="fa fa-search"></i>&nbsp;查询
								</button>
							</customtag:authtag>	
							</div>
						</div>
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
							<customtag:authtag authUrl="order/houseOrderExcelList">
								<button class="btn btn-primary" type="button" onclick="exportFile();" >
								 <i class="fa fa-search"></i>&nbsp;导出文件
							    </button>
							</customtag:authtag>    
							</div>
					    </div>
					</div>
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
						 <table id="listTable" class="table table-bordered table-hover" >           
	                     </table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
 

<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>


<script type="text/javascript">

    function query(){
    	$('#listTable').bootstrapTable('selectPage', 1);
    }
    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        requestType:1,
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val() ,
	        cityCode:$('#cityCode').val() ,
	        lanCustomerMobile:$('#lanCustomerMobile').val() ,
	        empGuardName:$('#empGuardName').val() //,
	        //empPushName:$('#empPushName').val() 
    	};
	}
    
    function subPaginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        requestType:2,
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val() ,
	        cityCode:$('#cityCode').val() ,
	        lanCustomerMobile:$('#lanCustomerMobile').val() ,
	        empGuardName:$('#empGuardName').val() //,
	        //empPushName:$('#empPushName').val() 
    	};
	}
    
    function moneyDataFormat(value,row,index){
    	return value/100;
    }
    //导出报表excel
    var exportUrl='order/houseOrderExcelList?requestType=1';
    function exportFile(){
    	var cityCode = $('#cityCode').val();
        var lanCustomerMobile = $('#lanCustomerMobile').val();
        var empGuardName = $('#empGuardName').val();
        //var empPushName = $('#empPushName').val();
        var url = exportUrl+"&cityCode="+cityCode+
        		"&lanCustomerMobile="+lanCustomerMobile+
        		"&empGuardName="+empGuardName
        		//+"&empPushName="+empPushName
        		;
		
		window.location.href = url
	}
    
    
    function ajaxGetSubmit(url,data,callback){
		$.ajax({
	    type: "GET",
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


    $(function (){
        //初始化日期
        datePickerFormat('beginTime', '00:00:00');
        datePickerFormat('endTime', '23:59:59');
        entireHouseOrderList();
    });


    function datePickerFormat(elemId, time){
        laydate({
            elem: '#' + elemId,
            format: 'YYYY-MM-DD ' + time,
            istime: false,
            istoday: true
        });
    }

    //初始化列表列
    var columnsData=[
     { 
 	    field: 'cityCode', 
 	    title: '城市code',
 	    align:'center'
 	 }, { 
 	    field: 'houseSn', 
 	    title: '房源/房间编号',
	 	align:'center'
 	 }, { 
 	    field: 'houseName', 
 	    title: '房源/房间名称',
 	    align:'center'
 	 }, { 
 	    field: 'houseAddr', 
 	    title: '房源地址',
 	    align:'center'
 	 },{ 
	 	field: 'empGuardName', 
	 	title: '运营专员姓名',
	 	align:'center'
	 },/* { 
 	    field: 'empPushName', 
 	    title: '地推管家姓名',
 	    align:'center'
 	 }, */{ 
 	    field: 'lanRealName', 
 	    title: '房东姓名',
 	    align:'center'
 	 },{ 
 	    field: 'lanCusMobile', 
 	    title: '房东手机号',
 	    align:'center'
 	 },{
  	    field: 'orderNum', 
 	    title: '订单量',
 	    align:'center'
 	 },{ 
 	    field: 'orderNight', 
 	    title: '订单间夜',
 	    align:'center'
 	 },{ 
 	    field: 'rentalMoney', 
 	    title: '房租租金',
 	    align:'center',
 	    formatter:'moneyDataFormat'
 	 },{ 
 	    field: 'orderServiceMoney', 
 	    title: '订单服务费',
 	    align:'center',
 	    formatter:'moneyDataFormat'
 	 },{ 
 	    field: 'statyNight', 
 	    title: '居住间夜',
 	    align:'center'
 	 },{ 
 	    field: 'stayServiceMoney', 
 	    title: '居住间夜服务费',
 	    align:'center',
 	    formatter:'moneyDataFormat'
 	 },{ 
 	    field: 'evaNum', 
 	    title: '评价量',
 	    align:'center'
 	 },{ 
 	    field: 'willStatyNight', 
 	    title: '未来30天入住间夜',
 	    align:'center'
 	 },{ 
 	    field: 'willServiceMoney', 
 	    title: '未来30天入住间夜服务费',
 	    align:'center'
 	 }]; 
    
    //初始化房源订单列表
    function entireHouseOrderList(){
    	$("#entireLi").addClass('active');
    	$("#subLi").removeClass('active');
    	exportUrl='order/houseOrderExcelList?requestType=1';
    	CommonUtils.strapTable('listTable','order/houseOrderDataList',paginationParam,columnsData);
    }
    
    //初始化合租房源订单列表
    function subHouseOrderList(){
    	$("#subLi").addClass('active');
    	$("#entireLi").removeClass('active');
    	exportUrl='order/houseOrderExcelList?requestType=2';
    	CommonUtils.strapTable('listTable','order/houseOrderDataList',subPaginationParam,columnsData);
    }
</script>
</body>

</html>
