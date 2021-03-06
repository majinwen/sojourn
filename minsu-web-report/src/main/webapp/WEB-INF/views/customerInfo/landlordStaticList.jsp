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
					<a href="javascript:void(0)">房东粘性报表</a>
				</li>
			</ul>
         </div>
		 <div class="col-sm-12">
		    <div class="ibox float-e-margins">
               <div class="ibox-content">
	              <div class="row">
						<div class="col-sm-12">
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">国家：</label>
                            </div>
                            <div class="col-sm-2 ">
                                <select class="form-control m-b cc-region" id="nation" name="nation">
                                    <option value="">请选择</option>
                                    <c:forEach items="${nationList}" var="obj">
                                        <option value="${obj.code }">${obj.showName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">大区：</label>
                            </div>
                            <div class="col-sm-2 ">
                                <select class="form-control m-b cc-region" id="region" name="region">
                                    <option value="">请选择</option>
                                    <c:forEach items="${regionList}" var="obj">
                                        <option value="${obj.fid }">${obj.regionName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-sm-1 text-right mtop">
                                <label class="control-label">城市：</label>
                            </div>
                            <div class="col-sm-2 ">
                                <select class="form-control m-b cc-region" id="city" name="city">
                                    <option value="">请选择</option>
                                    <c:forEach items="${cityList}" var="obj">
                                        <option value="${obj.code }">${obj.name }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
				    </div>
				    <div class="row">
                        <div class="col-sm-12">
                        	<div class="col-sm-1 text-right mtop">
                            <label class="control-label">从:</label>
                            </div>
                            <div class="col-sm-2 ">
                                 <input id="beginTime"  value=""  name="beginTime" class="laydate-icon form-control layer-date">
                            </div>
                            <div class="col-sm-1 text-right mtop">
                            <label class="control-label">到:</label>
                            </div>
                            <div class="col-sm-2">
                                 <input id="endTime" value="" name="endTime" class="laydate-icon form-control layer-date">
                            </div>
                            <div class="col-sm-1">
                                <button class="btn btn-primary" type="button" onclick="query();">
                                    <i class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </div>
                           	<div class="col-sm-2">
                                <button class="btn btn-primary" type="button" onclick="exportFile();">
                                    <i class="fa fa-search"></i>&nbsp;导出文件
                                </button>
                                
                            </div>
                        </div>
                    </div>
                    <div class="row">
                       <div class="col-sm-12">
                          <div class="col-sm-2"></div>
                          <h3 class="example-title" id="title" align="left">备注：本表的房东是“房子有上过架的房东”</h3>
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
			                    data-url="landlordStatic/listData">
			                    <thead>
			                        <tr class="trbg">
				                        <th data-field="countroy" data-width="10%" data-align="center">国家</th>
				                        <th data-field="region" data-width="10%" data-align="center">大区</th>
				                        <th data-field="city" data-width="10%" data-align="center">城市</th>
				                        <th data-field="landlordUid" data-width="10%" data-align="center">房东UID</th>
				                        <th data-field="sjedHouseCount" data-width="10%" data-align="center">累计上过架的房源数量</th>
				                        <th data-field="sjingHouseCount" data-width="10%" data-align="center">目前上架状态房源数量</th>
				                        <th data-field="pvCount" data-width="10%" data-align="center">累计浏览量</th>
				                        <th data-field="adviseCount" data-width="10%" data-align="center">累计咨询量</th>
				                        <th data-field="bookOrderCount" data-width="10%" data-align="center">累计申请量</th>
				                        <th data-field="acptOrderCount" data-width="10%" data-align="center" >累计接单量</th>
				                        <th data-field="paidOrderCount" data-width="10%" data-align="center" >累计支付订单量</th>
				                        <th data-field="bookDays" data-width="10%" data-align="center">累计预订间夜量</th>
				                        <th data-field="peopleNum" data-width="10%" data-align="center">累计入住客户量</th>
				                        <th data-field="checkinDays" data-width="10%" data-align="center">累计入住间夜</th>
				                        <th data-field="paiedMoney" data-width="10%" data-align="center">累计房租收益(元)</th>
				                        <th data-field="ratedCount" data-width="10%" data-align="center">累计收到评价量</th>
				                        <th data-field="rateCount" data-width="10%" data-align="center" >累计发出评价量</th>
				                        <th data-field="ratedScore" data-width="10%" data-align="center">累计获得评分平均分</th>
				                        <th data-field="rateScore" data-width="10%" data-align="center">累计发出评分平均分</th>
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
	        beginTime:$('#beginTime').val(),
	        endTime:$('#endTime').val(),
	        nationCode: $('#nation option:selected').val(),
        	regionCode: $('#region option:selected').val(),
        	cityCode: $('#city option:selected').val()
    	};
	}
    
    function exportFile(){
    	var nationCode = $('#nation option:selected').val();
    	var regionCode = $('#region option:selected').val();
    	var cityCode = $('#city option:selected').val();
        var beginTime = $('#beginTime').val();
        var endTime = $('#endTime').val();
		var url = "landlordStatic/exportList?nationCode="+nationCode+
											"&regionCode="+regionCode+
											"&cityCode="+cityCode+
											"&beginTime="+beginTime+
											"&endTime="+endTime
		;
		
		window.location.href = url;
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
