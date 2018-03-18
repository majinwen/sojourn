<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %>
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
                    </div>

					<div class="row">
						<div class="col-sm-6">
							<div class="col-sm-1 left2 left3">
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
			                    data-url="order/dataList">
			                    <thead>
			                        <tr class="trbg">
			                        <th data-field="orderSn" data-width="10%" data-align="center">订单编号</th>
			                        <th data-field="cityCode" data-width="10%" data-align="center">城市</th>
			                        <th data-field="landlordName" data-width="10%" data-align="center">房东名字</th>
			                        <th data-field="userName" data-width="10%" data-align="center">房客名字</th>
			                        <th data-field="sumMoney" data-width="10%" data-align="center">订单总金额</th>
			                        <th data-field="rentalMoney" data-width="10%" data-align="center">房租</th>
			                        <th data-field="lanCommMoney" data-width="10%" data-align="center">预计房东佣金</th>
			                        <th data-field="userCommMoney" data-width="10%" data-align="center">预计房客佣金</th>
			                        <th data-field="realLanMoney" data-width="10%" data-align="center">实际房东佣金</th>
			                        <th data-field="realUserMoney" data-width="10%" data-align="center">实际房客佣金</th>
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
			orderSn:$('#orderSn').val()
    	};
	}
    function exportFile(){
		var url = "order/dataListExcel"
		
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
    $(document).ready(function () {
    })
    

	
</script>
</body>

</html>
