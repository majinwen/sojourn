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
			<li class="active">
				<a href="javascript:void(0)">订单统计列表</a>
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
								<div class="col-sm-2">
									<customtag:authtag authUrl="order/orderStaticsDataList">
										<button class="btn btn-primary" type="button" onclick="query();">
											<i class="fa fa-search"></i>&nbsp;查询
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
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
								<customtag:authtag authUrl="order/orderStaticsExcelList">
									<button class="btn btn-primary" type="button" onclick="exportFile();" >
										<i class="fa fa-search"></i>&nbsp;导出文件
									</button>
								</customtag:authtag>
							</div>
						</div>

						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
			                    data-toggle="table"
			                    data-side-pagination="server"
			                    data-pagination="true"
			                    data-page-list="[10,20,50]"
			                    data-pagination="true"
			                    data-page-size="10"
			                    data-pagination-first-text="首页"
			                    data-pagination-pre-text="上一页"
			                    data-pagination-next-text="下一页"
			                    data-pagination-last-text="末页"
	                            data-content-type="application/x-www-form-urlencoded"
			                    data-query-params="paginationParam"
			                    data-method="post"
			                    data-single-select="true" 
			                    data-height="500"
			                    data-url="order/orderStaticsDataList">
			                    <thead>
			                        <tr class="trbg">
			                        <th data-field="cityCode" data-width="10%" data-align="center">城市code</th>
			                        <th data-field="cityName" data-width="10%" data-align="center">城市名字</th>
			                        <th data-field="orderNum" data-width="10%" data-align="center">总订单量</th>
			                        <th data-field="diffDay" data-width="10%" data-align="center"> 总间夜</th>
			                        <th data-field="rentalMoney" data-width="10%" data-align="center" data-formatter="moneyDataFormat">房租租金</th>
			                        <th data-field="depositMoney" data-width="10%" data-align="center" data-formatter="moneyDataFormat">押金</th>
			                        <th data-field="serviceMoney" data-width="10%" data-align="center" data-formatter="moneyDataFormat">服务费</th>
			                        <th data-field="needMoney" data-width="10%" data-align="center" data-formatter="moneyDataFormat">总交易额</th>
			                        <th data-field="stayNight" data-width="10%" data-align="center">居住间夜</th>
			                       <th data-field="stayNightServiceMoney" data-width="10%" data-align="center" data-formatter="moneyDataFormat">居住间夜服务费</th>
			                       
			                        </tr>
			                    </thead>             
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
	        cityCode:$('#cityCode').val(),
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val() 
    	};
	}
    
    function moneyDataFormat(value,row,index){
    	return value;
    }
    
    function exportFile(){
    	var cityCode = $('#cityCode').val();
        var beginTime = $('#beginTime').val();
        var endTime = $('#endTime').val();
		var url = "order/orderStaticsExcelList?"+
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


	$(function (){
		//初始化日期
		datePickerFormat('beginTime', '00:00:00');
		datePickerFormat('endTime', '23:59:59');
	});


	function datePickerFormat(elemId, time){
		laydate({
			elem: '#' + elemId,
			format: 'YYYY-MM-DD ' + time,
			istime: false,
			istoday: true
		});
	}
    

	
</script>
</body>

</html>
