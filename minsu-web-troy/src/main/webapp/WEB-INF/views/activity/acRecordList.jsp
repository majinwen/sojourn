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
	<div class="row row-lg">
		 <div class="col-sm-12">
            <div class="ibox float-e-margins">
               <div class="ibox-content">
	              <form class="form-horizontal m-t" id="commentForm">
	           		<div class="form-group">
					<div class="row">
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动名称：</label>
							<div class="col-sm-3 left2">
								<input id="actName" name="actName" type="text" class="form-control">
							</div>
							<label class="col-sm-3 control-label left2">活动形式：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="actKind" name="actKind">
									<option value="">全部</option>
									<option value="1">优惠券活动</option>
                                    <option value="2">普通活动</option>
								</select>
							</div>

						</div>
						<div class="col-sm-6">
                            <label class="col-sm-3 control-label left2">活动类型：</label>
                            <div class="col-sm-3 left2">
                                <select class="form-control" id="actType"  name="actType">
                                    <option value="">全部</option>
                                    <option value="4">免天优惠券活动</option>
                                    <option value="20">种子房东免佣</option>
                                    <option value="30">礼品活动</option>
                                </select>
                            </div>
							<label class="col-sm-3 control-label left2">领取电话：</label>
							<div class="col-sm-3 left2">
								<input id="userMobile" name="userMobile" type="text" class="form-control">
							</div>
						</div>
						<div class="col-sm-6">

							<div class="col-sm-1 left2 left3">
								<button class="btn btn-primary" type="button" onclick="query();">
									<i class="fa fa-search"></i>&nbsp;查询
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
				    <customtag:authtag authUrl="activity/sendCoupon">
                        <button id="sendCoupon" class="btn btn-primary" type="button">
                            <i class="fa fa-save"></i>发送优惠劵 </button>
                    </customtag:authtag>

					<div class="example-wrap">
						<div class="example">
						 <table id="listTable" class="table table-bordered table-hover" data-click-to-select="true"
			                    data-toggle="table"
			                    data-side-pagination="server"
			                    data-pagination="true"
			                    data-page-list="[10,20,50]"
			                    data-pagination="true"
			                    data-page-size="20"
			                    data-pagination-first-text="首页"
			                    data-pagination-pre-text="上一页"
			                    data-pagination-next-text="下一页"
			                    data-pagination-last-text="末页"
	                            data-content-type="application/x-www-form-urlencoded"
			                    data-query-params="paginationParam"
			                    data-method="post"
			                    data-single-select="true" 
			                    data-height="500"
			                    data-url="activity/queryAcRecordList">
			                    <thead>
			                        <tr class="trbg">
			                            <th data-field="groupSn" data-width="10%" data-align="center">活动组码</th>
			                            <th data-field="actSn" data-width="10%" data-align="center">活动码</th>
			                            <th data-field="actName" data-width="10%" data-align="center">活动名称</th>
			                            <th data-field="actKind" data-width="10%" data-align="center" data-formatter="formateActKind">活动形式</th>
			                            <th data-field="actType" data-width="10%" data-align="center" data-formatter="formateActType">活动类型</th>
			                            <th data-field="giftName" data-width="10%" data-align="center">礼品名称</th>
			                            <th data-field="giftRemark" data-width="10%" data-align="center">礼品备注</th>
			                            <th data-field="userName" data-width="10%" data-align="center" >领取人姓名</th>
			                            <th data-field="userAdress" data-width="10%" data-align="center" >领取人地址</th>
			                            <th data-field="userMobile" data-width="10%" data-align="center" >领取人手机号</th>
			                            <th data-field="axRemark" data-width="10%" data-align="center">礼品领取备注</th>
			                            <th data-field="createDate" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
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
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>
<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
<!-- layerDate plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
<script src="${basePath}js/minsu/common/commonUtils.js${VERSION}005" type="text/javascript"></script>

<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>


<script type="text/javascript">

	$(function (){
		//初始化日期
	/* 	CommonUtils.datePickerFormat('createTimeStart');
		CommonUtils.datePickerFormat('createTimeEnd') */
		//新增活动
		$("#sendCoupon").click(function(){
			$.openNewTab(new Date().getTime(),'activity/sendCoupon', "发送优惠劵");
		});

	
	});

    function formateActKind(value, row, index){
        if(value == 1){
            return "优惠券活动";
        }else if(value == 2){
            return "普通活动";
        }
        return value;
    }
    
    function formateActType(value, row, index){
        if(value == 1){
            return "优惠券抵现金";
        }else if(value == 2){
            return "折扣券";
        }else if(value == 3){
            return "随机现金券";
        }else if(value == 4){
            return "免天优惠券";
        }else if(value == 20){
            return "免佣金";
        }else if(value == 21){
            return "满减";
        }else if(value == 22){
            return "免天";
        }else if(value == 23){
			return "返现";
		}else if(value == 30){
			return "礼品";
		}
        return value;
    }
    

	//正整数
	function isPInt(str) {
		var g = /^[1-9]*[1-9][0-9]*$/;
		return g.test(str);
	}
    function query(){
    	$('#listTable').bootstrapTable('selectPage', 1);
    }
    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        actName:$('#actName').val(),
			userMobile:$('#userMobile').val(),
            actKind:$('#actKind').val(),
            actType:$('#actType').val()
    	};
	}
	
</script>
</body>

</html>
