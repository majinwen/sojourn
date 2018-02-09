<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link href="${staticResourceUrl}/css/star-rating.css${VERSION}001"  rel="stylesheet" type="text/css"/>

<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
<style type="text/css">
.left2{
    margin-top: 10px;
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
				                    <label class="col-sm-1 control-label left2">订单编号：</label>
				                   <div class="col-sm-2 left2">
				                       <input id="orderSn" name="orderSn" minlength="1" type="text" class="form-control" required="true" aria-required="true">
				                   </div>

				                   <label class="col-sm-1 control-label left2">评价日期：</label>
		                           <div class="col-sm-6 daop">
		                           		<div class="col-sm-4 left2">
		                                	<input id="startTime"  value=""  name="startTime" class="laydate-icon form-control layer-date">
		                            	</div>
		                           		<label class="col-sm-2 control-label left2" style="text-align: center;">到</label>
		                            	<div class="col-sm-4 left2">
		                                	<input id="endTime" value=""   name="endTime" class="laydate-icon form-control layer-date">
		                           		</div>
		                           </div>
			               		</div>
			                   <div class="row">
				                   <label class="col-sm-1 control-label left2">评价内容：</label>
				                   <div class="col-sm-2 left2">
				                       <input id="content" name="content" minlength="1" type="text" class="form-control" required="true" aria-required="true">
				                   </div>

				                 <label class="col-sm-1 control-label mtop">评价状态：</label>
				                   <div class="col-sm-2 left2">
				                     <select class="form-control m-b"  name="evaStatu" id="evaStatu">
				                           <option value="">全部</option>
	                                       <option value="1">待审核</option>
	                                       <option value="2">系统下线</option>
	                                       <option value="3">人工下线</option>
	                                       <option value="4">已发布</option>
	                                       <option value="5">已举报</option>
	                                   </select>
				                   </div>
				                   <label class="col-sm-1 control-label left2">评论类型：</label>
				                   <div class="col-sm-2 left2">
				                       <select class="form-control m-b" name="evaUserType" id="evaUserType">
				                           <option value="0">全部</option>
	                                       <option value="1">房东评论</option>
	                                       <option value="2">房客评论</option>
	                                       <option value="10">房东回复</option>
	                                   </select>
				                   </div>
			                 	</div>

								<div class="row">
									<label class="col-sm-1 control-label left2">房源编号：</label>
									<div class="col-sm-2 left2">
										<input id="houseSn" name="houseSn" minlength="1" type="text" class="form-control" required="true" aria-required="true">
									</div>
									<label class="col-sm-1 control-label left2">运营专员：</label>
									<div class="col-sm-2 left2">
										<input id="empGuardName" name="empGuardName" minlength="1" type="text" class="form-control" required="true" aria-required="true">
									</div>
									<div class="col-sm-1 left2">
										<button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
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
				   <%--  <customtag:authtag authUrl="cms/toEditActivity/new"> --%>
						<button id="saveLanlordEva" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i>&nbsp;添加房东评价</button>
				    <%-- </customtag:authtag> --%>

				    <%-- <customtag:authtag authUrl="cms/toEditActivity"> --%>
						<button id="saveTenantEva" class="btn btn-primary" type="button">
						<i class="fa fa-save"></i>&nbsp;添加房客评价</button>
				   <%--  </customtag:authtag> --%>
					<!-- <button class="btn btn-primary" type="button" onclick="toAddSms(0);">
						<i class="fa fa-save"></i>&nbsp;添加
					</button>
					<button class="btn btn-info " type="button" onclick="toAddSms(1);">
						<i class="fa fa-edit"></i>&nbsp;编辑
					</button>
					<button class="btn btn-default " type="button"
						onclick="enabledSms();">
						<i class="fa fa-delete"></i>&nbsp;启用
					</button> -->
					<div class="example-wrap">
						<div class="example">
							<table id="listTable" style="table-layout:fixed" class="table table-bordered table-condensed table-hover table-striped" data-click-to-select="true"
								data-toggle="table" data-side-pagination="server"
								data-pagination="true" data-page-list="[10,20,50]"
								data-pagination="true" data-page-size="10"
								data-pagination-first-text="首页" data-pagination-pre-text="上一页"
								data-pagination-next-text="下一页" data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="post"
								data-single-select="true"
								data-height="500" data-url="${basePath }evaluate/queryEvaluateByPage">
								<thead>
									<tr>
										<th data-field="orderSn"  data-width="10%" >订单编号</th>
										<th data-field="houseSn"  data-width="10%" >房源编号</th>
										<th data-field="empGuardName"  data-width="5%" >运营专员</th>
										<th data-field="cityName"  data-width="5%" >城市</th>
										<th data-field="evaUserName" data-width="5%"  >评价人</th>
										<th data-field="ratedUserName" data-width="5%" >被评人</th>
										<th data-field="evaUserType" data-width="5%" data-align="center" data-formatter="getEvaUserType">评价类型</th>
										<th data-field="content" data-width="25%" data-cell-style="cellStyle">评价内容</th>
										<th data-field="createTime" data-width="10%"data-formatter="formateDate" >评价日期</th>
										<th data-field="evaStatu" data-width="5%" data-align="center" data-formatter="getEvaStatu">状态</th>
										<th data-field="test" data-align="center"  data-width="15%" data-formatter="menuOperData">操作</th>
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
	<!-- 添加 菜单  弹出框 -->
<div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">评价详情</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
				           <form id="evaluateForm" action="" method="post">
			                   <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 165px;">房东详情</label>
			                   </div>
						        <div class="form-group">
									<label class="col-sm5 control-label"   id="landlordSatisfied">房东对房客的满意度：</label>
									<!--  <input id="input-21f"  value="" type="number" class="rating" min=0 max=5 step=0.5 data-size="md" > -->
									<!-- <div class="col-sm5" id="landlordSatisfied"></div> -->
						        </div>
						         <div class="form-group">
									<label class="col-sm5 control-label">房东评价内容：</label>
									<div  id="lan_content" class="col-sm5"></div>
						        </div>
						     	<div class="hr-line-dashed"></div>

						       <div class="form-group">
			                       <label class="col-sm8 control-label" style="margin-left: 165px;">房客评价详情</label>
			                   </div>
			                  <div class="form-group">
								<label class="col-sm5 control-label" id="houseClean">清洁度：</label>
								<!-- <div class="col-sm5" id="houseClean"></div> -->
							</div>
							  <div class="form-group">
								<label class="col-sm5 control-label" id="descriptionMatch">描述相符：</label>
								<!-- <div class="col-sm5" id="descriptionMatch"></div> -->

							</div>
							  <div class="form-group">
							    <label class="col-sm5 control-label" id="safetyDegree">房东印象：</label>
								<!-- <div class="col-sm5" id="safetyDegree"></div> -->
							</div>
							<div class="form-group">
								<label class="col-sm5 control-label" id="trafficPosition">周边环境：</label>
								<!-- <div class="col-sm5" id="trafficPosition"></div> -->
							</div>
							<div class="form-group">
								<label class="col-sm5 control-label" id="costPerformance">性价比：</label>
								<!-- <div class="col-sm5" id="costPerformance"></div> -->
							</div>
							<div class="form-group">
								<label class="col-sm5 control-label">评价内容：</label>
								<div class="col-sm5" id="ten_content" ></div>
							</div>
						   <div class="form-group">
							   <label class="col-sm5 control-label">房东回复：</label>
							   <div class="col-sm5" id="landlord_repaly" >暂无回复</div>
						   </div>
		                  <!-- 用于 将表单缓存清空 -->
                           <!--用来清空表单数据-->
                              <input type="reset" name="reset" style="display: none;" />
                           </form>
                      </div>
			       </div>
		       </div>
	         </div>
         </div>
     </div>
     <div class="modal-footer" style="text-align: center;">
         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
         <!-- <button type="button" id="saveMenuRes" class="btn btn-primary" >保存</button> -->
      </div>
     </div>
   </div>
</div>

	<!--更新评价 评分-->
    <div class="modal inmodal" id="updateModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
                    </button>
                    <h4 class="modal-title">评价详情</h4>
                </div>
                <div class="modal-body">
                    <div class="wrapper wrapper-content animated fadeInRight">
                        <div class="row">
                            <div class="col-sm-14">
                                <div class="ibox float-e-margins">
                                    <div class="ibox-content">
                                        <form id="evaluateFormUp" action="" method="post">
                                            <div class="form-group">
                                                <label class="col-sm8 control-label" style="margin-left: 165px;">房东详情</label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label"   id="landlordSatisfiedUp">房东对房客的满意度：</label>
                                                <!--  <input id="input-21f"  value="" type="number" class="rating" min=0 max=5 step=0.5 data-size="md" > -->
                                                <!-- <div class="col-sm5" id="landlordSatisfied"></div> -->
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label">房东评价内容：</label>
												<textarea id="lan_content_up" name="lan_content_up" class="form-control" rows="5"></textarea>
                                            </div>
                                            <div class="hr-line-dashed"></div>

                                            <div class="form-group">
                                                <label class="col-sm8 control-label" style="margin-left: 165px;">房客评价详情</label>
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label" id="houseCleanUp">清洁度：</label>
                                                <!-- <div class="col-sm5" id="houseClean"></div> -->
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label" id="descriptionMatchUp">描述相符：</label>
                                                <!-- <div class="col-sm5" id="descriptionMatch"></div> -->

                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label" id="safetyDegreeUp">房东印象：</label>
                                                <!-- <div class="col-sm5" id="safetyDegree"></div> -->
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label" id="trafficPositionUp">周边环境：</label>
                                                <!-- <div class="col-sm5" id="trafficPosition"></div> -->
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label" id="costPerformanceUp">性价比：</label>
                                                <!-- <div class="col-sm5" id="costPerformance"></div> -->
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label">评价内容：</label>
                                                <textarea id="ten_content_up" name="ten_content_up" class="form-control" rows="5"></textarea>
                                                <input id="evaOrderFid" hidden="hidden">
                                                <input id="orderSnUp" hidden="hidden">
                                                <input id="houseFid" hidden="hidden">
                                                <input id="roomFid" hidden="hidden">
                                                <input id="rentWay" hidden="hidden">
                                                <input id="tenantUid" hidden="hidden">
                                                <input id="landlordUid" hidden="hidden">
                                                <input id="evaStatuUp" hidden="hidden">
                                                <input id="evaUserTypeUp" hidden="hidden">
                                                <input id="evaOrderIdLandlord" hidden="hidden">
                                            </div>
                                            <div class="form-group">
                                                <label class="col-sm5 control-label">房东回复：</label>
                                                <div class="col-sm5" id="landlord_repaly_up" >暂无回复</div>
                                            </div>
                                            <!-- 用于 将表单缓存清空 -->
                                            <!--用来清空表单数据-->
                                            <input type="reset" name="reset" style="display: none;" />
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" style="text-align: center;">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" id="updateEvaluate" class="btn btn-primary" >保存</button>
                </div>
            </div>
        </div>
    </div>



	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	<!-- 自定义js -->
	<script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	<!-- Bootstrap table -->
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
	<script
		src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
	<!-- layer javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>
	<!-- <script src="js/demo/layer-demo.js"></script> -->
	<!-- layerDate plugin javascript -->
	<script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
	<script src="${staticResourceUrl}/js/star-rating.js" type="text/javascript"></script>


	   <!-- blueimp gallery -->
	   <script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>

	<script type="text/javascript">


	   	$("#saveTenantEva").click(function(){
			 $.openNewTab(new Date().getTime(),'evaluate/goToEvaluate?evaType=1', "添加房客评价");
	   	});

	 	$("#saveLanlordEva").click(function(){
			 $.openNewTab(new Date().getTime(),'evaluate/goToEvaluate?evaType=2', "添加房东评价");
	   	});
		function cellStyle(value, row, index, field) {
			return {
				css: {"overflow":"hidden","white-space":"nowrap","text-overflow":"ellipsis"}
			};
		}

        function isInteger(obj) {
            return obj%1 === 0
        }

        $('#updateEvaluate').click(function () {

            //房东对房客满意度
            var landlordSatisfied = $('#landlordSatisfiedUp > div > div.rating-container.rating-gly-star > input').val();
            //房东对房客评价
            var lanContentUp = $('#lan_content_up').val();
            //房东评价对应的evaOrderFid
			var evaOrderIdLandlord = $('#evaOrderIdLandlord').val();
            //房屋清洁度
            var upHouseClean = $('#houseCleanUp > div > div.rating-container.rating-gly-star > input').val();
            //描述相符
            var upDescriptionMatch = $('#descriptionMatchUp > div > div.rating-container.rating-gly-star > input').val();
            //房东印象
            var upSafetyDegree = $('#safetyDegreeUp > div > div.rating-container.rating-gly-star > input').val();
            //周边环境
            var upTrafficPosition = $('#trafficPositionUp > div > div.rating-container.rating-gly-star > input').val();
            //性价比
            var upCostPerformance = $('#costPerformanceUp > div > div.rating-container.rating-gly-star > input').val();

            //评价内容
            var upTenContent = $('#ten_content_up').val();
            var evaOrderFid = $('#evaOrderFid').val();
            var orderSn = $('#orderSnUp').val();
            var houseFid = $('#houseFid').val();
            var roomFid = $('#roomFid').val();
            var rentWay = $('#rentWay').val();
            var landlordUid = $('#landlordUid').val();
            var tenantUid = $('#tenantUid').val();
            var evaStatu = $('#evaStatuUp').val();
            var evaUserType = $('#evaUserTypeUp').val();

            $.ajax({
                type: "POST",
                url: "evaluate/updateTenantEva",
                dataType: "json",
                async: true,
                data: {"evaOrderFid": evaOrderFid, "houseClean":upHouseClean, "descriptionMatch":upDescriptionMatch,
                    "safetyDegree":upSafetyDegree, "trafficPosition":upTrafficPosition, "costPerformance":upCostPerformance,
                    "content":upTenContent, "orderSn":orderSn,
					"houseFid":houseFid, "roomFid":roomFid, "rentWay":rentWay,
					"landlordUid":landlordUid,"tenantUid":tenantUid,
                    "evaStatu":evaStatu, "evaUserType":evaUserType,
					"evaOrderIdLandlord":evaOrderIdLandlord,"landlordSatisfied":landlordSatisfied, "lanContent":lanContentUp},
                success: function (result) {
					if (result.code == 0){
						alert("操作成功");
						window.location.href = "evaluate/queryListEvaluate";
					}else{
						layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
					}
                },
                error: function (result) {
                    layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
                }
            });
        });

        var editFlag = "${editFlag}";

		$(function(){
			//var BASE_CONTEXT = ${basePath};
			//外部js调用
		   laydate({
		      elem: '#startTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
		      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
		  });
		   laydate({
			      elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			      event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			  });


		})
    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        id:$.trim($('#id').val()),
		        orderSn:$.trim($('#orderSn').val()),
		        houseSn:$.trim($('#houseSn').val()),
				empGuardName:$.trim($('#empGuardName').val()),
		        evaUserUid:$.trim($('#evaUserUid').val()),
		        ratedUserUid:$.trim($('#ratedUserUid').val()),
		        evaUserType:$.trim($('#evaUserType').val()),
		        content:$.trim($('#content').val()),
		        createTime:$.trim($('#createTime').val()),
		        evaStatu:$.trim($('#evaStatu').val()),
		        caozuo:$.trim($('#caozuo').val()),
		        startTime:$("#startTime").val(),
		        endTime:$("#endTime").val()
	    	};
		}
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }

		function formateDate(value, row, index) {
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd HH:mm:ss");
			} else {
				return "";
			}
		}
	    /**
	    *添加短信模板
	    */
		function toAddSms(flag){

	    	var url= "sms/addSmsTeplate?editFlag="+flag;

	    	if(flag==1||flag=="1"){
	    		var selectVar=$('#listTable').bootstrapTable('getSelections');
				if(selectVar.length == 0){
					alert("请选择一条记录");
					return;
				}
				url = url +"&smsId="+selectVar[0].id;
	    	}
			window.location.href=url;
		}
	    //评价人类型
		function getEvaUserType(value, row, index){
			if(value==1){
				return "房东评价";
			}
			if(value == 2){
				return "租客评价";
			}
			if(value == 10){
                return "房东回复";
			}
		}
	    //状态
	    function getEvaStatu(value, row, index){
	    	if(value==1){
				return "待审核";
			}
			if(value == 2){
				return "系统下线";
			}
			if(value==3){
				return "人工下线";
			}
			if(value == 4){
				return "已发布";
			}
			if(value==5){
				return "已举报";
			}

	    }

	  	/* 列表 操作 值设置 */
	   	function menuOperData(value, row, index){
	   		var _html = "";
            _html +="<button id='addMenuButton"+index+"' style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' orderSn='\""+row.orderSn+"\"' type='button' class='btn btn-primary'onclick='goToEvaluateInfo(\""+row.orderSn+"\");' data-toggle='modal' data-target='#myModal'>详情</button>&nbsp;";
            _html += "<button style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' orderSn='\"" + row.orderSn + "\"' type='button' class='btn btn-primary'onclick='upEvaluateInfo(\"" + row.orderSn + "\");' data-toggle='modal' data-target='#updateModal'>修改</button>&nbsp;";
            if(editFlag == 1){
                return _html;
            }
	   		if(row.evaStatu == 1){
	   			_html = "<button type='button' style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;'  class='btn btn-red'><span onclick='changeEvaStatu(\""+row.fid+"\",4,\""+row.orderSn+"\");'> 通过</span></button>&nbsp;&nbsp;&nbsp";
	   			_html +="<button type='button'  style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' class='btn btn-red'><span onclick='changeEvaStatu(\""+row.fid+"\",3,\""+row.orderSn+"\");'> 拒绝</span></button>&nbsp;&nbsp;&nbsp";
	   		}
            if(row.evaStatu == 4){
                _html +="<button type='button'  style='height: 26px;background-color: #1ab394;line-height: normal;border:1 solid #FFCC00;color: #FFFFFF;font-size: 9pt;font-style: normal;font-variant: normal;font-weight: normal;' class='btn btn-red'><span onclick='changeEvaStatu(\""+row.fid+"\",3,\""+row.orderSn+"\");'> 撤回</span></button>&nbsp;&nbsp;&nbsp";
            }

	   		return _html;
	  	}
	   	/**根据fid审核评价内容*/
	  	function changeEvaStatu(fid,statu,orderSn){
			if(fid.length == 0||fid == null||fid==undefined){
				alert("缺失记录编号，请联系技术");
				return;
			}
			if(statu == null ||statu==undefined){
				alert("异常请联系技术");
				return;
			}
			if (statu == 3){
				layer.confirm('你确定要下线这条评价？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					$.ajax({
						type: "POST",
						url: "evaluate/examineEvaluateByFid",
						dataType:"json",
						async:true,
						data: {"fid":fid,"evaStatu":statu,"orderSn":orderSn},
						success: function (result) {
							var resultInt = parseInt(result);
							if(resultInt>0){
								window.location.href = "evaluate/queryListEvaluate"
							}else{
								alert("操作失败");
							}
						},
						error: function(result) {
							alert("error:操作失败");
						}
					});
				}, function(){

				});
			}

	  	}
	   	//查询详情
	   	function goToEvaluateInfo(orderSn){
	   		if(orderSn!=null&&orderSn!=""&&orderSn!=undefined){
	   			$.ajax({
				    type: "POST",
				    url: "evaluate/goToEvaluateInfo",
				    dataType:"json",
				    async:true,
				    data: {"orderSn":orderSn},
				     success: function (result) {


				    	 if(result){
				    		 $("#lan_content").text("");

				    		 $("#ten_content").text("");
				    		 $("#landlord_repaly").text("暂无回复");
				    		 $(".star-rating").remove();
				    		 var _headHtml =  "<input value='";
				    		 var _tailhtml = "' type='range' disabled class='rating' min=0 max=5 step=1 data-size='md' >";// var _tailhtml = "' type='number' disabled class='rating' min=0 max=5 step=0.5 data-size='md' >";
				    		 var _html="";
				    		 if(result.landlordEvaluate){//房东评价

				    			  _html =_headHtml+result.landlordEvaluate.landlordSatisfied+_tailhtml;
				    			 $("#landlordSatisfied").append(_html);
					    		 $("#lan_content").text(result.landlordEvaluate.content);
				    		 }
				    		 if(result.tenantEvaluate){//房客评价
				    			  _html = _headHtml+result.tenantEvaluate.houseClean+_tailhtml
					    		  $("#houseClean").append(_html);
				    			  _html = _headHtml+result.tenantEvaluate.descriptionMatch+_tailhtml
				    			  $("#descriptionMatch").append(_html);
				    			  _html = _headHtml+result.tenantEvaluate.safetyDegree+_tailhtml
				    			  $("#safetyDegree").append(_html);
				    			  _html = _headHtml+result.tenantEvaluate.trafficPosition+_tailhtml
				    			  $("#trafficPosition").append(_html);
				    			  _html = _headHtml+result.tenantEvaluate.costPerformance+_tailhtml
				    			  $("#costPerformance").append(_html);
					    		 $("#ten_content").text(result.tenantEvaluate.content);
					    		 if(result.landlordReplyEntity){
                                     $("#landlord_repaly").text(result.landlordReplyEntity.content);
								 }
				    		 }
				    		 $(".rating").rating();
				             $(".glyphicon").remove();
				    		 $(".caption").remove();

				    	 }else{
				    		 alert("error:操作失败");
				    	 }
				    },
				    error: function(result) {
				          alert("error:操作失败");
				        }
				     });
	   		}

	   	}


        function upEvaluateInfo(orderSn){
            if(orderSn!=null&&orderSn!=""&&orderSn!=undefined){
                $.ajax({
                    type: "POST",
                    url: "evaluate/goToEvaluateInfo",
                    dataType:"json",
                    async:true,
                    data: {"orderSn":orderSn},
                    success: function (result) {


                        if(result){
                            $('#evaOrderFid').val("");
                            $('#orderSnUp').val(orderSn);
                            $("#lan_content_up").text("");

                            $("#ten_content_up").text("");
                            $("#landlord_repaly_up").text("暂无回复");
                            $("#evaOrderIdLandlord").val("");
                            $(".star-rating").remove();
                            var _headHtml =  "<input value='";
                            var _tailhtml = "' type='range' class='rating' min=0 max=5 step=1 data-size='md' >";// var _tailhtml = "' type='number' disabled class='rating' min=0 max=5 step=0.5 data-size='md' >";
                            var _html="";
                            if(result.landlordEvaluate){//房东评价

                                _html =_headHtml+result.landlordEvaluate.landlordSatisfied+_tailhtml;
                                $("#landlordSatisfiedUp").append(_html);
                                $("#lan_content_up").text(result.landlordEvaluate.content);
                                $("#evaOrderIdLandlord").val(result.evaOrderIdLandlord);
                                console.info("landlord evaOrderFid : ", result.landlordEvaluate.evaOrderFid);
                            }
                            if(result.tenantEvaluate){//房客评价
                                _html = _headHtml+result.tenantEvaluate.houseClean+_tailhtml
                                $("#houseCleanUp").append(_html);
                                _html = _headHtml+result.tenantEvaluate.descriptionMatch+_tailhtml
                                $("#descriptionMatchUp").append(_html);
                                _html = _headHtml+result.tenantEvaluate.safetyDegree+_tailhtml
                                $("#safetyDegreeUp").append(_html);
                                _html = _headHtml+result.tenantEvaluate.trafficPosition+_tailhtml
                                $("#trafficPositionUp").append(_html);
                                _html = _headHtml+result.tenantEvaluate.costPerformance+_tailhtml
                                $("#costPerformanceUp").append(_html);
                                $("#ten_content_up").val(result.tenantEvaluate.content);
                                if(result.landlordReplyEntity){
                                    $("#landlord_repaly_up").text(result.landlordReplyEntity.content);
                                }
                            }

                            if (result.evaluateOrderEntity){
                                $('#evaOrderFid').val(result.evaluateOrderEntity.fid);
                                console.info("evaOrderFid : ", result.evaluateOrderEntity.fid)
                                $('#houseFid').val(result.evaluateOrderEntity.houseFid);
                                $('#roomFid').val(result.evaluateOrderEntity.roomFid);
                                $('#rentWay').val(result.evaluateOrderEntity.rentWay);
                                $('#tenantUid').val(result.evaluateOrderEntity.evaUserUid);
                                $('#landLordUid').val(result.evaluateOrderEntity.ratedUserUid);
                                $('#evaStatuUp').val(result.evaluateOrderEntity.evaStatu);
                                $('#evaUserTypeUp').val(result.evaluateOrderEntity.evaUserType);
                            }
                            $(".rating").rating();
                            $(".glyphicon").remove();
                            $(".caption").remove();

                        }else{
                            alert("error:操作失败");
                        }
                    },
                    error: function(result) {
                        alert("error:操作失败");
                    }
                });
            }

        }

	</script>

</body>

</html>
