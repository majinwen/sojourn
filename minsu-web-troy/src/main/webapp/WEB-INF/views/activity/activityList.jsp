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
							<label class="col-sm-3 control-label left2">活动城市：</label>
							<div class="col-sm-3 left2">
								<select id="cityCode" name="cityCode" class="form-control m-b m-b">
									<option value="">请选择</option>
									<option value="0">全部</option>
									<c:forEach items="${cityList}" var="obj">
										<option value="${obj.code }">${obj.name }</option>
									</c:forEach>
								</select> <span class="help-block m-b-none"></span>
							</div>
						</div>
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动形式：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="actKind" name="actKind">
									<option value="">全部</option>
									<option value="1">优惠券活动</option>
                                    <option value="2">普通活动</option>
								</select>
							</div>

                            <label class="col-sm-3 control-label left2">活动类型：</label>
                            <div class="col-sm-3 left2">
                                <select class="form-control" id="actType" name="actType">
                                    <option value="">全部</option>
                                    <option value="4">免天优惠券活动</option>
                                    <option value="20">种子房东免佣</option>
                                </select>
                            </div>

						</div>
                	</div>
                	<div class="row">
                		<div class="col-sm-6">
		                    <label class="col-sm-3 control-label left2">活动日期：</label>
                           	<div class="col-sm-3 left2">
                               <input id="createTimeStart"  value=""  name="createTimeStart" class="laydate-icon form-control layer-date">
                           	</div>
                           	<label class="col-sm-3 control-label left2" style="text-align: center;">到</label>
                            <div class="col-sm-3 left2">
                               <input id="createTimeEnd" value="" name="createTimeEnd" class="laydate-icon form-control layer-date">
                           	</div>
	                	</div>
						<div class="col-sm-6">
							<label class="col-sm-3 control-label left2">活动对象：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="roleCode" name="roleCode">
									<option value="">全部</option>
                                    <c:forEach items="${roles}" var="obj">
                                        <option value="${obj.roleCode }">${obj.roleName }</option>
                                    </c:forEach>


								</select>
							</div>

                            <label class="col-sm-3 control-label left2">活动状态：</label>
                            <div class="col-sm-3 left2">
                                <select class="form-control" id="actStatus" name="actStatus">
                                    <option value="">全部</option>
                                    <option value="1">未启用</option>
                                    <option value="2">已启用</option>
                                    <option value="3">已终止</option>
                                </select>
                            </div>

						</div>
					</div>
					<div class="row">
						<div class="col-sm-6">

							<label class="col-sm-3 control-label left2">活动码：</label>
							<div class="col-sm-3 left2">
								<input id="actSn" name="actSn" type="text" class="form-control">
							</div>

							<label class="col-sm-3 control-label left2">活动组：</label>
							<div class="col-sm-3 left2">
								<select class="form-control" id="groupSn" name="groupSn">
									<option value="">不限</option>
									<c:forEach items="${groupList}" var="group">
										<option value="${group.groupSn}">${group.groupName}</option>
									</c:forEach>
								</select>
							</div>

                            <label class="col-sm-3 control-label left2">业务线：</label>
                            <div class="col-sm-3 left2">
                                <select class="form-control" id="serviceLine" name="serviceLine">
                                    <option value="">不限</option>
                                    <option value="1">民宿</option>
                                    <option value="2">公寓</option>
                                </select>
                            </div>

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
					<customtag:authtag authUrl="normalActivity/toSaveActivity">
						<button id="toAddActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 新增普通活动 </button>
				    </customtag:authtag>


                    <customtag:authtag authUrl="activity/toEditActivity">
                        <button id="toAddActivitCouponyBtn" class="btn btn-primary" type="button">
                            <i class="fa fa-save"></i> 新增优惠券活动 </button>
                    </customtag:authtag>


				    <customtag:authtag authUrl="activity/startActivity">
						<button id="startActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 启用/生成券 </button>
				    </customtag:authtag>

                    <customtag:authtag authUrl="activity/endActivity">
                        <button id="endActivityBtn" class="btn btn-primary" type="button">
                            <i class="fa fa-save"></i> 终止 </button>
                    </customtag:authtag>

                    <customtag:authtag authUrl="normalActivity/toQueryActivity">
						<button id="queryActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i>查询活动详情</button>
				    </customtag:authtag>
				    <customtag:authtag authUrl="normalActivity/toUpdateActivity">
						<button id="updateActivityBtn" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i> 修改 </button>
				    </customtag:authtag>

					<customtag:authtag authUrl="activity/extActivity">
						<button id="extActivityBtn" class="btn btn-primary" type="button">
							<i class="fa fa-save"></i> 追加优惠券 </button>
					</customtag:authtag>

					<customtag:authtag authUrl="activity/extensionActivity">
						<button id="extensionActivityBtn" class="btn btn-primary" type="button">
							<i class="fa fa-save"></i> 延期 </button>
					</customtag:authtag>

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
			                    data-url="activity/activityDataList">
			                    <thead>
			                        <tr class="trbg">
			                            <th data-field="id" data-width="5%" data-radio="true"></th>
			                            <th data-field="" data-visible="false"></th>
			                            <th data-field="actSn" data-width="10%" data-align="center">活动码</th>
			                            <th data-field="actName" data-width="10%" data-align="center">活动名称</th>
			                            <th data-field="actKind" data-width="10%" data-align="center" data-formatter="formateActKind">活动形式</th>
                                        <th data-field="actType" data-width="5%" data-align="center"
                                            data-formatter="formateActType">活动类型
                                        </th>
                                        <th data-field="serviceLine" data-width="5%" data-align="center"
                                            data-formatter="formateServiceLine">业务线
                                        </th>
			                            <th data-field="cityStr" data-width="5%" data-align="center">活动城市</th>
			                            <th data-field="actStartTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动开始时间</th>
			                            <th data-field="actEndTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">活动结束时间</th>
			                            <th data-field="actStatus" data-width="10%" data-align="center" data-formatter="formateActivityStatus">活动状态</th>
			                            <th data-field="roleCode" data-width="5%" data-align="center" >活动对象</th>
			                            <th data-field="createTime" data-width="10%" data-align="center" data-formatter="CommonUtils.formateDate">创建时间</th>
                                        <th data-field="isCoupon" data-width="10%" data-align="center" data-formatter="formateIsCoupon">优惠券</th>
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

	<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">追加优惠券</h4>
				</div>
				<div class="modal-body">
					<div class="wrapper wrapper-content animated fadeInRight">
						<form id="extForm"  class="form-horizontal m-t" >
							<div id="parentDiv" class="form-group" style="text-align: center">
								<span >已经生成的优惠券不变，追加的数量表示在原来的基础上新增一定数量优惠券</span>
							</div>
							<div class="form-group">
								<input id="extActivitySn" name="extActivitySn" class="form-control" type="hidden" value="">
								<label class="col-sm-3 control-label">追加优惠券(张)</label>
								<div class="col-sm-8">
									<input id="extNum" name="extNum" class="form-control" type="text" value="">
									<span class="help-block m-b-none"></span>
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
					<button type="button" id="saveMenuRes" class="btn btn-primary" >确定</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal inmodal" id="extensionModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					</button>
					<h4 class="modal-title">延期</h4>
				</div>
				<div class="modal-body">
					<div class="wrapper wrapper-content animated fadeInRight">
						<form id="extensionForm"  class="form-horizontal m-t" >
								<div class="form-group">
								<input id="extensionActivitySn" name="extensionActivitySn" class="form-control" type="hidden" value="">
								<input id="actStartTime" name="actStartTime" class="form-control" type="hidden" value="">
								<label class="col-sm-3 control-label">请选择截止日期</label>
								<div class="col-sm-5 left2">
									<input id="actEndTime"  value=""  name="actEndTime" class="laydate-icon form-control layer-date">
								</div>
								</div>
							<%--</div>--%>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
					<button type="button" id="applyButton" class="btn btn-primary" >确定</button>
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

	var extUrl = "activity/extActivity";//增加模板


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
		}else if(value== 25){
			return "下单立减";
		}
        return value;
    }


    $(function (){
		//初始化日期
		CommonUtils.datePickerFormat('createTimeStart');
		CommonUtils.datePickerFormat('createTimeEnd');
        CommonUtils.datePickerFormat('actEndTime');


		//新增活动
		$("#toAddActivityBtn").click(function(){
			$.openNewTab(new Date().getTime(),'normalActivity/toSaveActivity', "新建普通活动");
		});

		//新增活动
		$("#toAddActivitCouponyBtn").click(function(){
			$.openNewTab(new Date().getTime(),'activity/toSaveActivity', "新建优惠券活动");
		});


		//正整数
		function isPInt(str) {
			var g = /^[1-9]*[1-9][0-9]*$/;
			return g.test(str);
		}


		/*保存菜单*/
		$("#saveMenuRes").click(function(){
			if(!isPInt($("#extNum").val())){
				layer.alert("请输入正确的正整数", {icon: 6,time: 2000, title:'提示'});
				$("#extNum").val(1);
				return false;
			}
			$.ajax({
				type: "POST",
				url: extUrl,
				dataType:"json",
				data: $("#extForm").serialize(),
				success: function (result) {
					if (result.code == 0){
						$('#myModal').modal('hide');
						query();
					}else {
						layer.alert(result.msg, {icon: 6,time: 2000, title:'提示'});
					}
				},
				error: function(result) {
					alert("error:"+result);
				}
			});
		});


		//修改活动
		$("#updateActivityBtn").click(function(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			if(selectVar[0].actStatus != 1){
				layer.alert("该活动已启用，不允许修改！", {icon: 6,time: 2000, title:'提示'});
				return ;
			}
			if(selectVar[0].actKind == 2){
				//普通活动
				$.openNewTab(new Date().getTime(),'normalActivity/toUpdateActivity?activitySn='+selectVar[0].actSn, "修改普通活动");
			}else if(selectVar[0].actKind == 1){
				//优惠券活动
				$.openNewTab(new Date().getTime(),'activity/toUpdateActivity?activitySn='+selectVar[0].actSn, "修改优惠券活动");
			}else{
				layer.alert("异常的活动形式", {icon: 6,time: 2000, title:'提示'});
			}
		});



		//查看活动详情
		$("#queryActivityBtn").click(function(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			
			if(selectVar[0].actKind == 2){
				//普通活动
				$.openNewTab(new Date().getTime(),'normalActivity/toQueryActivity?activitySn='+selectVar[0].actSn, "普通活动详情");
			}else if(selectVar[0].actKind == 1){
				//优惠券活动
				$.openNewTab(new Date().getTime(),'activity/toQueryActivity?activitySn='+selectVar[0].actSn, "优惠券活动详情");
			}else{
				layer.alert("异常的活动形式", {icon: 6,time: 2000, title:'提示'});
			}
		});

		//终止活动
		$("#endActivityBtn").click(function(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			if(selectVar[0].actStatus != 2){
				layer.alert("只有进行中的活动才能被终止！", {icon: 6,time: 2000, title:'提示'});
				return ;
			}
			$.ajax({
				url:"activity/endActivity",
				data:{
					activitySn : selectVar[0].actSn
				},
				dataType:"json",
				type:"post",
				async: false,
				success:function(data) {
					if(data.code === 0){
						layer.alert("终止成功！", {icon: 6,time: 2000, title:'提示'});
						setTimeout(function(){
							query();
						},1500);
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		});

		//追加活动
		$("#extActivityBtn").click(function(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			if(selectVar[0].actKind != 1){
				var msg = "只有优惠券活动才能追加";
				layer.alert(msg, {icon: 6,time: 2000, title:'提示'});
				return ;
			}

			var extActivitySn = selectVar[0].actSn;
			$('#extActivitySn').val(extActivitySn);
			$('#myModal').modal('toggle');
		});

        //延期活动
        $("#extensionActivityBtn").click(function(){
            var selectVar=$('#listTable').bootstrapTable('getSelections');
            if(selectVar.length == 0){
                layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
                return;
            }

            var extensionActivitySn = selectVar[0].actSn;
            var actStartTime=selectVar[0].actStartTime;
            $('#extensionActivitySn').val(extensionActivitySn);
            $('#actStartTime').val(actStartTime);
            $('#extensionModal').modal('toggle');
        });

        //保存延期
        $("#applyButton").click(function(){
			$.ajax({
                type: "POST",
                url: "activity/extensionActivity",
                dataType:"json",
                data: $("#extensionForm").serialize(),
                success: function (result) {
                    if (result.code == 0){
                        $('#extensionModal').modal('hide');
                        query();
                    }else {
                        layer.alert(result.msg, {icon: 6,time: 2000, title:'提示'});
                    }
                },
                error: function(result) {
                    alert("error:"+result);
                }
            });
        });




		//启动活动
		$("#startActivityBtn").click(function(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			if(selectVar.length == 0){
				layer.alert("请选择一条记录进行操作", {icon: 6,time: 2000, title:'提示'});
				return;
			}
			if(selectVar[0].actStatus != 1){
				var msg = "";
				if(selectVar[0].actKind == 1){
					msg = "优惠券已经生成"
				}else if(selectVar[0].actKind == 2){
					msg = "该活动已启用"
				}else{
					msg = "异常的活动类型"
				}
				layer.alert(msg, {icon: 6,time: 2000, title:'提示'});
				return ;
			}
			$.ajax({
				url:"activity/startActivity",
				data:{
					activitySn : selectVar[0].actSn,
					actKind : selectVar[0].actKind
				},
				dataType:"json",
				type:"post",
				async: false,
				success:function(data) {
					if(data.code === 0){
						layer.alert("启用成功！", {icon: 6,time: 2000, title:'提示'});
						setTimeout(function(){
							query();
						},1500);
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		});


	});

    function query(){
    	$('#listTable').bootstrapTable('selectPage', 1);
    }
    function paginationParam(params) {
	    return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,

	        cityCode:$('#cityCode').val(),
	        actName:$('#actName').val(),
	        createTimeStart:$('#createTimeStart').val(),
	        createTimeEnd:$('#createTimeEnd').val(),
            actKind:$('#actKind').val(),
            actType:$('#actType').val(),
            actStatus:$('#actStatus').val(),
            roleCode:$('#roleCode').val(),
			actSn:$('#actSn').val(),
			groupSn:$('#groupSn option:selected').val(),
            serviceLine: $("#serviceLine").val()
    	};
	}




    function formateIsCoupon(value, row, index){
        if(value == 0){
            return "无";
        }else if(value == 1){
            return "未生成";
        }else if(value == 2){
            return "已生成";
        }else if(value == 3){
            return "生成中";
        }else{
            return value;
        }
    }


	function formateActivityStatus(value, row, index){
		if(value == 1){
			return "未启用";
		}else if(value == 2){
			return "已启用";
		}else if(value == 3){
			return "已终止";
		}else {
			return value;
		}
	}

    function formateServiceLine(value, row, index) {
        if (value == 1) {
            return "民宿";
        }
        if (value == 2) {
            return "公寓";
        }
    }
	
</script>
</body>

</html>
