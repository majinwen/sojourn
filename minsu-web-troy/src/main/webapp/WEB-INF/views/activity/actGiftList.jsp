<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<base href="${basePath}">
<title>自如民宿后台管理系统</title>
<meta name="keywords" content="自如民宿后台管理系统">
<meta name="description" content="自如民宿后台管理系统">

<link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
</head>

<body class="gray-bg">
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">
						<div class="row" >
							<div class="form-group">
								<div class="row" style="margin-left: 10px;">
									<label class="col-sm-1 control-label mtop">礼品名称:</label>
									<div class="col-sm-2">
										<input id="giftName" name="giftName" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">礼品类型:</label>
									<div class="col-sm-2">
										<select class="form-control m-b" id="giftType">
		                                    <option value="">请选择</option>
		                                    <c:forEach items="${giftTypeMap }" var="obj">
			                                    <option value="${obj.key }">${obj.value }</option>
		                                    </c:forEach>
	                                    </select>
									</div>
									<div class="col-sm-1">
										<button class="btn btn-primary" type="button" onclick="query();">
											<i class="fa fa-search"></i>&nbsp;查询
										</button>
									</div>
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
						<!-- Example Pagination -->
						<customtag:authtag authUrl="activity/addActGift">
						    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal"><i class="fa fa-plus"></i>&nbsp;添加</button>
						</customtag:authtag>
						<customtag:authtag authUrl="activity/editActGift">
						    <button class="btn btn-info" type="button" onclick="toEditActGift();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
						</customtag:authtag>
						<customtag:authtag authUrl="activity/delActGift">
						    <button class="btn btn-warning" type="button" onclick="delActGift();"><i class="fa fa-times"></i>&nbsp;删除</button>
						</customtag:authtag>
						<div class="example-wrap">
							<div class="example">
								<table id="listTable" class="table table-bordered"  
								data-click-to-select="true"
			                    data-toggle="table"
			                    data-side-pagination="server"
			                    data-pagination="true"
			                    data-striped="true"
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
			                    data-height="496"
			                    data-classes="table table-hover table-condensed"
			                    data-url="activity/showActGift">                    
			                    <thead>
			                        <tr>
			                        	<th data-field="id" data-width="2%" data-radio="true"></th>
			                            <th data-field="giftName" data-width="16%" data-align="center">礼品名称</th>
			                            <th data-field="giftValue" data-width="16%" data-align="center">礼品值</th>
			                            <th data-field="giftUnit" data-width="16%" data-align="center">礼品单位</th>
			                            <th data-field="giftType" data-width="16%" data-align="center" data-formatter="formateGiftType">礼品类型</th>
			                            <th data-field="remark" data-width="16%" data-align="center">备注</th>
			                            <th data-field="createTime" data-width="18%" data-align="center" data-formatter="formateDate">创建时间</th>
			                        </tr>
			                    </thead>
			                    </table>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 添加 活动礼品  弹出框 -->
	<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">添加活动礼品</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="addForm" class="form-horizontal m-t" >
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品名称：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" name="giftName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品值：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" name="giftValue">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品单位：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" name="giftUnit">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品类型</label>
						                   <div class="col-sm-8">
						                       <select class="form-control m-b" name="giftType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${giftTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
			                                     </select>
						                   </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">备注</label>
						                   <div class="col-sm-8">
						                       <textarea rows="8" name="remark" class="form-control m-b"></textarea>
						                   </div>
					                   </div>
					                  <!-- 用于 将表单缓存清空 -->
	                                  <input type="reset" style="display:none;" />
					            </form>
				           </div>
				       </div>
			       </div>
		         </div>
	         </div>
	     </div>
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	         <button type="button" id="addActGift" class="btn btn-primary">保存</button>
	      </div>
	     </div>
	   </div>
	</div>
	
	<!-- 添加 活动礼品  弹出框 -->
	<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">修改活动礼品</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="editForm" class="form-horizontal m-t" >
			                           <input type="hidden" id="actGiftFid" name="fid">
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品名称：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" id="actGiftName" name="giftName">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品值：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" id="actGiftValue" name="giftValue">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品单位：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" id="actGiftUnit" name="giftUnit">
					                           <span class="help-block m-b-none"></span>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">礼品类型</label>
						                   <div class="col-sm-8">
						                       <select class="form-control m-b" id="actGiftType" name="giftType">
				                                   <option value="">请选择</option>
				                                   <c:forEach items="${giftTypeMap }" var="obj">
					                                   <option value="${obj.key }">${obj.value }</option>
				                                   </c:forEach>
			                                     </select>
						                   </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">备注</label>
						                   <div class="col-sm-8">
						                       <textarea rows="3" id="actGiftRemark" name="remark" class="form-control m-b"></textarea>
						                   </div>
					                   </div>
					                  <!-- 用于 将表单缓存清空 -->
	                                  <input type="reset" style="display:none;" />
					            </form>
				           </div>
				       </div>
			       </div>
		         </div>
	         </div>
	     </div>
	     <div class="modal-footer">
	         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	         <button type="button" id="editActGift" class="btn btn-primary">保存</button>
	      </div>
	     </div>
	   </div>
	</div>

	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>

	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	
	<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>

	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/validate.ext.js${VERSION}"></script>


	<!-- Page-Level Scripts -->
	<script>
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				giftName : $.trim($('#giftName').val()),
				giftType : $('#giftType option:selected').val(),
				isDel : 0
			};
		}

		function query() {
			$('#listTable').bootstrapTable('selectPage', 1);
		}

		// 礼品类型
		function formateGiftType(value, row, index){
			var giftTypeJson = ${giftTypeJson };
			var result = '';
			$.each(giftTypeJson,function(i,n){
				if(value == i){
					result = n;
					return false;
				}
			});
			return result;
		}

		// 格式化时间
		function formateDate(value, row, index) {
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd HH:mm:ss");
			} else {
				return "";
			}
		}
		
		// 活动礼品校验
		$("#addForm").validate({
            rules: {
            	giftName: "required",
                giftValue: {
                    required: true,
                    nonnegaInteger: true,
                },
            	giftUnit: "required",
            	giftType: "required"
            }
        });
		
		// 新增活动礼品
		$("#addActGift").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#addForm").valid()){
				$.ajax({
		            type: "post",
		            url: "activity/addActGift",
		            dataType:"json",
		            data: $("#addForm").serialize(),
		            success: function (result) {
		            	if(result.code === 0){
				           	$('#addModal').modal('hide')
				           	$("input[type=reset]").trigger("click");
				           	$('#listTable').bootstrapTable('refresh');
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			}
			$(this).removeAttr("disable");
		})
 		
		// 修改活动礼品
		function toEditActGift() {
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			$.ajax({
	            type: "post",
	            url: "activity/findActGift",
	            dataType:"json",
	            data: {
	            	"actGiftFid" : rows[0].fid
	            },
	            success: function (result) {
	            	if(result.code === 0){
	            		$("#actGiftFid").val(result.data.obj.fid);
	            		$("#actGiftName").val(result.data.obj.giftName);
	            		$("#actGiftValue").val(result.data.obj.giftValue);
	            		$("#actGiftUnit").val(result.data.obj.giftUnit);
	            		$("#actGiftType").val(result.data.obj.giftType);
	            		$("#actGiftRemark").val(result.data.obj.remark);
					}
		           	$('#editModal').modal('show');
	            },
	            error: function(result) {
		           	$('#editModal').modal('show');
	            }
	      });
		}
		
		// 活动礼品校验
		$("#editForm").validate({
            rules: {
            	giftName: "required",
                giftValue: {
                    required: true,
                    nonnegaInteger: true,
                },
            	giftUnit: "required",
            	giftType: "required"
            }
        });
		
		// 新增活动礼品
		$("#editActGift").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#editForm").valid()){
				$.ajax({
		            type: "post",
		            url: "activity/editActGift",
		            dataType:"json",
		            data: $("#editForm").serialize(),
		            success: function (result) {
		            	if(result.code === 0){
				           	$('#editModal').modal('hide')
				           	$("input[type=reset]").trigger("click");
				           	$('#listTable').bootstrapTable('refresh');
						}else{
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			}
			$(this).removeAttr("disable");
		})
		
		// 逻辑删除活动礼品
		function delActGift() {
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("请选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			layer.confirm("确认删除吗?", {icon: 5,title:'提示'},function(index){
				$.ajax({
		            type: "post",
		            url: "activity/delActGift",
		            dataType:"json",
		            data: {
		            	"actGiftFid" : rows[0].fid
		            },
		            success: function (result) {
		            	if(result.code === 0){
		            		$('#listTable').bootstrapTable('refresh');
						} else {
							layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
						}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			  layer.close(index);
			});
		}
	</script>
</body>
</html>
