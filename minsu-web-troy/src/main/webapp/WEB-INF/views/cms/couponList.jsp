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

<style type="text/css">
.left2{
    margin-top: 10px;
}
.left3{padding-left:62px;}
.btn1
{
    border-right: #7b9ebd 1px solid;
    padding-right: 2px;
    border-top: #7b9ebd 1px solid;
    padding-left: 2px;
    font-size: 12px;
    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,  StartColorStr=#ffffff,  EndColorStr=#cecfde);
    border-left: #7b9ebd 1px solid;
    cursor: hand;
    padding-top: 2px;
    border-bottom: #7b9ebd 1px solid;
}
.row{margin:0;}
</style>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		
	<!-- Panel Other -->
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
			
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="example">
						 <table id="couponListTable" class="table table-bordered table-hover"  data-click-to-select="true"
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
					                    data-height="470"
					                    data-url="cms/couponDataList">                    
					                    <thead>
					                        <tr class="trbg">
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="couponSn"  data-width="10%" data-align="center" >优惠券号</th>
					                            <th data-field="couponName" data-width="10%" data-align="center">优惠券名称</th>
					                            <th data-field="couponStatus" data-width="10%" data-align="center" data-formatter="formateCouponStatus">优惠券状态状态</th>
					                           <!--  <th data-field="actCut" data-width="10%" data-align="center">折扣/折扣金额</th> -->
					                            <th data-field="couponStartTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">优惠券开始时间</th>
					                            <th data-field="couponEndTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">优惠券结束时间</th>
					                            <th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}0013" type="text/javascript"></script>

<script type="text/javascript">

 /* 页面设置 */
 function paginationParam(params) {
	 console.log(CommonUtils.paginationCommon('couponListTable',{actSn:"${actSn}"},params));
	return CommonUtils.paginationCommon('couponListTable',{actSn:"${actSn}"},params);
} 

function formateCouponStatus(value, row, index){
	if(value == 1){
		return "未领取";
	}else if(value == 2){
		return "已领取";
	}else if(value == 3){
		return "已冻结";
	}else if(value == 4){
		return "已使用";
	}else if(value == 5){
		return "已过期";
	}
}

</script>
</body>

</html>
