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

    <link href="${staticResourceUrl}/favicon.ico${VERSION}" rel="shortcut icon">
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	
	<style type="text/css">
		.line-width{
			width: 200px;;
		}
		/* td{
			white-space: nowrap;text-overflow: ellipsis;overflow: hidden;
		} */
	</style>
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
               	  <div class="tabs-container"> 
	                <div class="tab-content">
                        <div id="tab-1" class="tab-pane active">
					        <div class="row row-lg">
					        	<div class="col-sm-12">
					                <div class="ibox float-e-margins">
					                    <div class="ibox-content">
					                        <div class="row">
												<label class="col-sm-1 control-label mtop">标签名称:</label>
												<div class="col-sm-2">
													<input type="text" class="form-control" id="tagName" name="tagName"/>
												</div>
					                        	<label class="col-sm-1 control-label mtop">标签类型:</label>
					                            <div class="col-sm-2">
				                                    <select class="form-control m-b" id="tagType">
					                                    <option value="">请选择</option>
					                                    <c:forEach items="${tagTypeMap }" var="obj">
						                                    <option value="${obj.key }">${obj.value }</option>
					                                    </c:forEach>
					                                </select>
				                                </div>
					                        	<label class="col-sm-1 control-label mtop">标签状态:</label>
					                            <div class="col-sm-2">
				                                    <select class="form-control m-b" id="isValid">
					                                    <option value="">请选择</option>
					                                    <option value="0">否</option>
				                                        <option value="1">是</option>
				                                    </select>
				                                </div>  
					                            <div class="col-sm-2">
				                                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
				                                </div>
					                        	 
						                    </div> 
					                        <br/> 
											<div class="row"> 
								                <div class="col-sm-1">
								                	<button class="btn btn-primary" type="button"  data-target="#myModal" onclick="modifyTagName('','','')" ><i class="glyphicon glyphicon-plus"></i>&nbsp;添加标签内容</button>
								                </div>
								                <label class="col-sm-1 control-label mtop">&nbsp;</label>
								                <div class="col-sm-1">
								                	<button class="btn btn-primary" type="button"  onclick="featureTagList();" ><i class="glyphicon glyphicon-cog"></i>&nbsp;设置业务标签</button>
								                </div>

											</div>
					                    </div>
					                </div>
					            </div>
					        </div>	
					        <div class="ibox float-e-margins">
					            <div class="ibox-content">
					                <div class="row row-lg">
					                    <div class="col-sm-14">
					                        <div class="example-wrap">
					                            <div class="example">
					                                <table id="listTable" class="table table-bordered"  data-click-to-select="true"
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
								                    data-unique-id="fid"
								                    data-classes="table table-hover table-condensed"
								                    data-url="config/confTag/showConfTagList">                    
								                    <thead>
								                        <tr>
								                            <th data-field="fid" data-width="200px" data-align="center">标签fid</th>
								                            <th data-field="tagTypeName" data-width="200px" data-align="center">标签类型</th>
								                            <th data-field="tagName" data-width="200px" data-align="center">标签名称</th>
								                            <th data-field="isValid" data-width="50px" data-align="center" data-formatter="formateIsValid">标签状态</th>
								                            <th data-field="lastModifyDate" data-width="200px" data-align="center" data-formatter="formateDate">更新时间</th>
								                            <th data-field="createFid" data-width="100px" data-align="center">创建人uid</th>
								                            <th data-field="operate" data-width="150px" data-align="center" data-formatter="formateOperate">操作</th>
								                            
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
        				
					</div>
	              </div>
	           </div>
	       </div>
	   </div>
	   
	   <!-- 添加  弹出框 -->
	   <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content animated bounceInRight">
		       <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
		          </button>
		          <h4 class="modal-title">标签</h4>
		       </div>
		       <div class="modal-body">
			        <div class="wrapper wrapper-content animated fadeInRight">
				        <div class="row">
				          <div class="col-sm-14">
				               <div class="ibox float-e-margins">
				                   <div class="ibox-content">
						               <form id="editForm" class="form-horizontal m-t" >
						                   <input id="saveFid" type="hidden" name="fid" >
						                           
						                   <div class="form-group">
						                       <label class="col-sm-3 control-label">标签类型：</label>
						                       <div class="col-sm-8"> 
						                       
						                       		 <span id="tagTypeSpan" class="help-block m-b-none"></span>
						                       </div>
						                   </div>
						                           
						                  <div class="form-group">
						                       <label class="col-sm-3 control-label">标签名称：</label>
						                       <div class="col-sm-8">
						                           <input id="saveTagName" name="tagName" class="form-control" type="text" value="">
						                           <span class="help-block m-b-none"></span>
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
		         <button type="button" class="btn btn-primary" onclick="saveTag()" >保存</button>
		      </div>
		     </div>
		   </div>
		</div> 
	
	   <!-- 全局js -->
	    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	    <!-- Bootstrap table -->
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	  <%--   <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script> --%>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
	    <script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}001"></script>
	
	
	   <!-- Page-Level Scripts -->
	   <script>
		   
		    function isNullOrBlank(obj){
		    	return obj == undefined || obj == null || $.trim(obj).length == 0;
		    }
		    
		    
		    function paginationParam(params) {
		    	 
			    return {
			        limit: params.limit,
			        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
			        tagName:$.trim($('#tagName').val()),
			        tagType:$('#tagType option:selected').val(),
			        isValid:$('#isValid option:selected').val()
		    	};
			} 
		    
		    function query(){ 
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    } 
			
			// 是否有效
			function formateIsValid(value, row, index){
				if(value == '0'){
					return "否";
				}else{
					return "是";
				}
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
			
			//操作
			function formateOperate(value, row, index){
				var btn = "";
				if(row.isValid==1 || row.isValid=='1'){
					btn = '<button type="button" class="btn btn-info btn-xs" onclick="changeStatus(\''+row.fid+'\',0)" > 已启用</button>';
				}else{
					btn = '<button type="button" class="btn btn-info btn-xs" onclick="changeStatus(\''+row.fid+'\',1)" > 已停用 </button>';
				}
				
				btn += '&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-danger btn-xs" onclick="modifyTagName(\''+row.fid+'\',\''+row.tagName+'\',\''+row.tagType+'\')" > 修改名称 </button>';
				
				return btn;
			}
			
			//修改状态
			function changeStatus(fid,isValid){
				
					if(isNullOrBlank(fid) || isNullOrBlank(isValid)){
						return;
					}
				
					$.ajax({
			            type: "POST",
			            url: "config/confTag/changeStatus",
			            dataType:"json",
			            data: {fid:fid,isValid:isValid},
			            success: function (result) {
			            	
			            	var status = result.code;
			            	if(status==0 || status=='0'){			            		
			            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
			            		$('#listTable').bootstrapTable('refresh', {silent: true});
			            	}else{
			            		layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
			            	}
			            	
			            },
			            error: function(result) {
			            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
			            }
			      });
				
			}
			
			
			//修改标签名称弹层
			function modifyTagName(fid,tagName,tagType){
				 
				$("#saveTagType").remove();
				var tagTypeSlect = $("#tagType").clone();
				$(tagTypeSlect).attr("id","saveTagType");
				$("#tagTypeSpan").before(tagTypeSlect);
				
				if(!isNullOrBlank(tagType)){
					$("#saveTagType").val(tagType); 
					$("#saveTagType").attr('disabled',"true");
				}
				
				$("#saveFid").val(fid); 
				$("#saveTagName").val(tagName); 
				
				$('#myModal').modal('toggle');
			}
			
			//保存标签
			function saveTag(){
				
				var fid = $("#saveFid").val();				
				var tagName = $("#saveTagName").val();
				var tagType = $('#saveTagType option:selected').val();
				
				var url="";
				var data ={};
				
				if(isNullOrBlank(tagName)){
					layer.alert("标签名称不能为空", {icon: 5,time: 2000, title:'提示'});
					return;
				}
				
				if(isNullOrBlank(fid)){//新增
					if(isNullOrBlank(tagType)){
						layer.alert("未选择标签类型", {icon: 5,time: 2000, title:'提示'});
						return;
					}
					url="config/confTag/addConfTag";
					data.tagName = tagName; 
					data.tagType = tagType;	
				}else{
					url="config/confTag/modifyTagName";
					data.fid = fid; 
					data.tagName = tagName; 
				}
				
				$.ajax({
		            type: "POST",
		            url: url,
		            dataType:"json",
		            data: data,
		            success: function (result) {
		            	
		            	var status = result.code;
		            	if(status==0 || status=='0'){			            		
		            		$('#myModal').modal('hide');
		            		layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
		            		$('#listTable').bootstrapTable('refresh', {silent: true});
		            	}else{
		            		layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
		            	}
		            	
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
			
		}
			
		//设置特色标签
		function featureTagList(){
			$.openNewTab("featureTagList","config/featureTag/tagList", "设置业务标签");
		}	
			
			
		</script>
	</body>
</html>
