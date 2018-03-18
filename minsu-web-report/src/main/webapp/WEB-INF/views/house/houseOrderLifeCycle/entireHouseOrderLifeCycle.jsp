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
					<a href="javascript:entireHouseOrderLife();">整租列表</a>
				</li>
				<li id="subLi">
					<a href="javascript:subHouseOrderLife();">合租列表</a>
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
                            
                            <label class="col-sm-1 control-label mtop">城市:</label>
                             <div class="col-sm-2">
	                               <select class="form-control m-b" id="cityCode" name="cityCode" >
	                               <option value="">请选择</option>
	                               <c:forEach items="${cityList}" var="obj">
										<option value="${obj.code }">${obj.name }</option>
									</c:forEach>
	                              </select>
                             </div>
                            </div>
						</div>
						
                	
						<div class="row">
							<div class="col-sm-6">
								<div class="col-sm-1 left2 left3">
								<customtag:authtag authUrl="houseOrderLifeCycle/dataList">
									<button class="btn btn-primary" type="button" onclick="query();">
										<i class="fa fa-search"></i>&nbsp;查询
									</button>
								</customtag:authtag>	
								</div>
							</div>
							<div class="col-sm-6">
								<div class="col-sm-1 left2 left3">
								<customtag:authtag authUrl="houseOrderLifeCycle/houseLifeCycleExcelList">
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
						 <table id="listTable" class="table table-bordered table-hover"></table>
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
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}002"></script>

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
    
    //整租查询
    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			rentWay:0 ,
			cityCode:$('#cityCode').val(),
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val() 
            
    	};
	}
    //合租查询
    function subPaginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			rentWay:1 ,
			cityCode:$('#cityCode').val(),
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val() 
            
    	};
	}
    //导出地址
    var exportUrl='houseOrderLifeCycle/houseLifeCycleExcelList?rentWay=0';
    function exportFile(){
    	var cityCode = $('#cityCode').val();
        var beginTime = $('#beginTime').val();
        var endTime = $('#endTime').val();
        
        var url =exportUrl+
        		"&beginTime="+beginTime+
        		"&endTime="+endTime+
        		"&cityCode="+cityCode
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
   
    function moneyDataFormat(value,row,index){
    	return value/100;
    }
    
    $(function (){
		//初始化日期
        datePickerFormat('beginTime', '00:00:00');
        datePickerFormat('endTime', '23:59:59');
		entireHouseOrderLife();
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
 	    field: 'lanMobile', 
 	    title: '房东电话',
 	    align:'center'
 	 },{ 
 	    field: 'price', 
 	    title: '日租金价格',
 	    align:'center',
 	    formatter:'moneyDataFormat'
 	 },{ 
 	    field: 'houseStatusName', 
 	    title: '房源/房间状态',
 	    align:'center'
 	 },{ 
 	    field: 'onLineDate', 
 	    title: '上架日期',
 	    align:'center',
 	    formatter:'CommonUtils.formateDate'
 	 },{ 
 	    field: 'firstOrderTime', 
 	    title: '首个订单日期',
 	    align:'center',
 	    formatter:'CommonUtils.formateDate'
 	 },{ 
 	    field: 'firstStartTime', 
 	    title: '首个入住日期',
 	    align:'center',
 	    formatter:'CommonUtils.formateDate'
 	 },{ 
 	    field: 'firstEvaTime', 
 	    title: '首个评价日期',
 	    align:'center',
 	    formatter:'CommonUtils.formateDate'
 	 }] 
    //初始化整租房源订单周期列表
    function entireHouseOrderLife(){
    	$("#entireLi").addClass('active');
    	$("#subLi").removeClass('active');
    	exportUrl='houseOrderLifeCycle/houseLifeCycleExcelList?rentWay=0';
    	CommonUtils.strapTable('listTable','houseOrderLifeCycle/dataList',paginationParam,columnsData);
    }
    //初始化合租房源订单周期列表
    function subHouseOrderLife(){
    	$("#subLi").addClass('active');
    	$("#entireLi").removeClass('active');
    	exportUrl='houseOrderLifeCycle/houseLifeCycleExcelList?rentWay=1';
    	CommonUtils.strapTable('listTable','houseOrderLifeCycle/dataList',subPaginationParam,columnsData);
    }
</script>
</body>

</html>
