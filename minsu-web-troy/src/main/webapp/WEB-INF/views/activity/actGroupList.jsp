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
									<label class="col-sm-1 control-label mtop">活动组编号:</label>
									<div class="col-sm-2">
										<input id="groupSn" name="groupSn" type="text" class="form-control">
									</div>
									<label class="col-sm-1 control-label mtop">活动组名称:</label>
									<div class="col-sm-2">
										<input id="groupName" name="groupName" type="text" class="form-control">
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
						<customtag:authtag authUrl="activity/addActGroup">
						    <button class="btn btn-primary" type="button" data-toggle="modal" data-target="#addModal"><i class="fa fa-plus"></i>&nbsp;添加</button>
						</customtag:authtag>
						<customtag:authtag authUrl="activity/editActGroup">
						    <button class="btn btn-info" type="button" onclick="toEditActGroup();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
						</customtag:authtag>
						<customtag:authtag authUrl="activity/delActGroup">
						    <button class="btn btn-warning" type="button" onclick="delActGroup();"><i class="fa fa-times"></i>&nbsp;删除</button>
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
			                    data-url="activity/showActGroup">                    
			                    <thead>
			                        <tr>
			                        	<th data-field="id" data-width="2%" data-radio="true"></th>
			                            <th data-field="groupSn" data-width="25%" data-align="center">活动组编号</th>
			                            <th data-field="groupName" data-width="25%" data-align="center">活动组名称</th>
			                            <th data-field="remark" data-width="25%" data-align="center">备注</th>
			                            <th data-field="createTime" data-width="25%" data-align="center" data-formatter="formateDate">创建时间</th>
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
	
	<!-- 添加 活动组  弹出框 -->
	<div class="modal inmodal" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">添加活动组</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="addForm" class="form-horizontal m-t" >
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">活动组编号：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" id="actGroupSn" name="groupSn">
						                       <label id="message" class="control-label" style="color:#F00"></label>
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">活动组名称：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" name="groupName" id="addGroupName">
					                       </div>
					                   </div>
					                   <div class="form-group">
										   <label class="col-sm-3 control-label">组内活动互斥：</label>
										   <div class="col-sm-8">
											   <select class="form-control" name="isRepeat" id="actRepeat">
												   <option value="0">是</option>
												   <option value="1">否</option>
											   </select>
										   </div>
									   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">是否限制次数：</label>
					                       <div class="col-sm-8">
					                       	   <select class="form-control "  id="isLimit"  onChange="changeLimit();">
					                       	   		<option value="0">不限制</option>
					                       	   		<option value="1">限制次数</option>
					                       	   </select>
					                       </div>
					                   </div>
					                   <div class="form-group" id="groupLimitNumDiv">
					                       <label class="col-sm-3 control-label">限制次数：</label>
					                       <div class="col-sm-8" >
					                           <input class="form-control" type="text" name="groupLimitNum" id="groupLimitNum">
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">备注</label>
						                   <div class="col-sm-8">
						                       <textarea rows="3" name="remark" class="form-control m-b" id="remark"></textarea>
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
	         <button type="button" id="addActGroup" class="btn btn-primary">保存</button>
	      </div>
	     </div>
	   </div>
	</div>
	
	<!-- 添加 活动组  弹出框 -->
	<div class="modal inmodal" id="editModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content animated bounceInRight">
	       <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	          <h4 class="modal-title">修改活动组</h4>
	       </div>
	       <div class="modal-body">
		        <div class="wrapper wrapper-content animated fadeInRight">
			        <div class="row">
			          <div class="col-sm-14">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="editForm" class="form-horizontal m-t" >
			                           <input type="hidden" id="actGroupFid" name="fid">
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">活动组名称：</label>
					                       <div class="col-sm-8">
					                           <input class="form-control" type="text" id="actGroupName" name="groupName">
					                       </div>
					                   </div>
					                   <div class="form-group">
										   <label class="col-sm-3 control-label">组内活动互斥：</label>
										   <div class="col-sm-8">
											   <select class="form-control" id="actGroupRepeat" name="isRepeat">
												   <option value="0">是</option>
												   <option value="1">否</option>
											   </select>
										   </div>
									   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">是否限制次数：</label>
					                       <div class="col-sm-8">
					                       	   <select class="form-control "  id="actGroupIsLimit"  onChange="changeActLimit();">
					                       	   		<option value="0">不限制</option>
					                       	   		<option value="1">限制次数</option>
					                       	   </select>
					                       </div>
					                   </div>
					                   <div class="form-group" id="actGroupLimitNumDiv">
					                       <label class="col-sm-3 control-label">限制次数：</label>
					                       <div class="col-sm-8" >
					                           <input class="form-control" type="text" name="groupLimitNum" id="actGroupLimitNum">
					                       </div>
					                   </div>
					                   <div class="form-group">
					                       <label class="col-sm-3 control-label">备注</label>
						                   <div class="col-sm-8">
						                       <textarea rows="3" id="actGroupRemark" name="remark" class="form-control m-b"></textarea>
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
	         <button type="button" id="editActGroup" class="btn btn-primary">保存</button>
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
				groupSn : $.trim($('#groupSn').val()),
				groupName : $.trim($('#groupName').val()),
				isDel : 0
			};
		}

		function query() {
			$('#listTable').bootstrapTable('selectPage', 1);
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
		
		// 校验活动组编号是否唯一
		$("#actGroupSn").change(function(){
			var _this = this;
			$.ajax({
	            type: "post",
	            url: "activity/validateGroupSn",
	            dataType:"json",
	            data: {
	            	"groupSn" : $(_this).val()
	            },
	            success: function (result) {
	            	if(result.code === 0){
	            		if(result.data.isUnique == true){
	            			$(_this).parent().find("label").text("");
	            		} else {
	            			$(_this).parent().find("label").text(result.msg);
	            			$(_this).val("");
	            			$(_this).focus();
	            		}
					}
	            }
	      });
		});	
		
		// 活动组校验
		$("#addForm").validate({
            rules: {
            	groupSn: "required",
            	groupName: "required",
            	groupLimitNum:{required:true,digits:true,min:1}
            }
        });
		
		// 新增活动组
		$("#addActGroup").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#addForm").valid()){
				var limitNum;
				var isLimit=$("#isLimit").val();
				if(isLimit == 0){
					limitNum=0;
				} else {
					limitNum=$("#groupLimitNum").val();
				}
				$.ajax({
		            type: "post",
		            url: "activity/addActGroup",
		            dataType:"json",
		            data: {"groupSn":$("#actGroupSn").val(),"groupName":$("#addGroupName").val(),"groupLimitNum":limitNum,"remark":$("#remark").val(),"isRepeat":$("#actRepeat").val()},
		            success: function (result) {
		            	if(result.code === 0){
				           	$('#addModal').modal('hide')
				           	$("input[type=reset]").trigger("click");
				           	$('#listTable').bootstrapTable('refresh');
				           	$("#groupLimitNumDiv").hide();
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
 		
		// 修改活动组
		function toEditActGroup() {
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
	            url: "activity/findActGroup",
	            dataType:"json",
	            data: {
	            	"groupSn" : rows[0].groupSn
	            },
	            success: function (result) {
	            	if(result.code === 0){
	            		$("#actGroupFid").val(result.data.obj.fid);
	            		$("#actGroupSn").val(result.data.obj.groupSn);
	            		$("#actGroupRepeat").val(result.data.obj.isRepeat);
	            		$("#actGroupName").val(result.data.obj.groupName);
	            		$("#actGroupRemark").val(result.data.obj.remark);
	            		if(result.data.obj.groupLimitNum>0){
	            			$("#actGroupIsLimit").val(1);
	            			$("#actGroupLimitNum").val(result.data.obj.groupLimitNum);
	            			$("#actGroupLimitNumDiv").show();
	            		} else {
	            			$("#actGroupIsLimit").val(0);
	            			$("#actGroupLimitNum").val("");
	            			$("#actGroupLimitNumDiv").hide();
	            		}
					}
		           	$('#editModal').modal('show');
	            },
	            error: function(result) {
		           	$('#editModal').modal('show');
	            }
	      });
		}
		
		// 活动组校验
		$("#editForm").validate({
			rules: {
            	groupSn: "required",
            	groupName: "required",
            	groupLimitNum:{required:true,digits:true,min:1}
            }
        });
		
		// 新增活动组
		$("#editActGroup").click(function(){
			$(this).attr("disable", "disable");
			//校验通过  则提交
			if($("#editForm").valid()){
				var limitNum;
				var isLimit=$("#actGroupIsLimit").val();
				if(isLimit == 0){
					limitNum=0;
				} else {
					limitNum=$("#actGroupLimitNum").val();
				}
				$.ajax({
		            type: "post",
		            url: "activity/editActGroup",
		            dataType:"json",
		            data: {"fid":$("#actGroupFid").val(),"groupName":$("#actGroupName").val(),"groupLimitNum":limitNum,"remark":$("#actGroupRemark").val(),"isRepeat":$("#actGroupRepeat").val()},
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
		
		// 逻辑删除活动组
		function delActGroup() {
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
		            url: "activity/delActGroup",
		            dataType:"json",
		            data: {
		            	"actGroupFid" : rows[0].fid
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
		
		//是否限制次数选择
		function changeLimit(){
			var isLimit=$("#isLimit").val();
			if(isLimit==0){
				$("#groupLimitNumDiv").hide();
			} else if (isLimit==1) {
				$("#groupLimitNumDiv").show();
			}
		}
		
		//是否限制次数选择
		function changeActLimit(){
			var isLimit=$("#actGroupIsLimit").val();
			if(isLimit==0){
				$("#actGroupLimitNumDiv").hide();
			} else if (isLimit==1) {
				$("#actGroupLimitNumDiv").show();
			}
		}
		
		$(function(){ 
			$("#groupLimitNumDiv").hide();
			$("#actGroupLimitNumDiv").hide();
		})
		
		function isNullOrBlank(obj){
            return obj == undefined || obj == null || $.trim(obj).length == 0;
        }
	</script>
</body>
</html>
