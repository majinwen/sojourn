<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="customtag" uri="http://minsu.ziroom.com" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <base href="<%=basePath%>">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> 
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}0010" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">
   
  
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="ibox float-e-margins">
      </div>
      <div class="ibox float-e-margins">
        <div class="col-sm-16">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <!-- 左侧树div -->
                    <div class="col-sm-2">
                        <div id="treeview" class="test"></div>
                    </div>
                    <!-- 右侧table div -->
                    <div class="col-sm-10">
                        <div class="col-sm-12">
	                       <button id="addMenuButton" type="button" class="btn btn-primary"  data-target="#myModal">新增资源</button> 
	                       <customtag:authtag authUrl="config/dicItem/itemIndex">
	                       	   <button id="itemIndex" type="button" class="btn btn-primary" style="display: none;">分类排序</button>
	                       </customtag:authtag> 
	                       <!-- 菜单列表 -->
	                       <div class="example-wrap">
	                            <div class="example">
		                            <table id="listTable" data-click-to-select="true"
				                    data-toggle="table"
				                    data-side-pagination="server"
				                    data-page-list="[10,20,50,100]"
				                    data-pagination="false"
				                    data-page-size="10"
				                    data-pagination-first-text="首页"
				                    data-pagination-pre-text="上一页"
				                    data-pagination-next-text="下一页"
				                    data-pagination-last-text="末页"
									data-content-type="application/x-www-form-urlencoded"
				                    data-query-params="paginationParam"
				                    data-method="post" 
				                    data-row-style="rowStyle" 
				                    data-height="680"
				                    data-url="config/dicItem/confitemDatalist">    
				                    <thead>
			                        <tr>
			                            <th data-field="id" data-align="center" data-checkbox="true"></th>
			                            <th data-field="showName" data-align="center" data-sortable="true">名称</th>
			                            <th data-field="dicCode" data-align="center" >编码</th>
			                            <th data-field="itemValue" data-align="center" >值</th>
			                            <th data-field="itemStatus" data-align="center" data-formatter="stateFormat">状态</th>
			                            <th data-field="test" data-align="center"  data-formatter="menuOperData">操作</th>
			                         </tr>
				                    </thead>   
				                    </table>          
	                            </div>
	                        </div>
	                        <!-- 菜单列表结束 -->
			            </div>
			          </div>
                   <div class="clearfix"></div>
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
          <h4 class="modal-title">添加配置项值</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
		                       <input id="nodeType" type="hidden" value="">
				               <form id="menuEditForm" action="/confcity/addConfCityRes" class="form-horizontal m-t" >
				                   <input id="templateId" type="hidden" name="templateFid" value="${templateId}">
				                   <input id="dicCode" type="hidden" name="dicCode" value="">
				                   
				                           
				                   <div class="form-group">
				                       <label class="col-sm-3 control-label">上级配置：</label>
				                       <div class="col-sm-8">
				                           <input id="selectedMenuText" class="form-control" type="text" value="${treeNode.text}">
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                   </div>
				                           
				                  <div class="form-group">
				                       <label class="col-sm-3 control-label">名称：</label>
				                       <div class="col-sm-8">
				                           <input id="showName" name="showName" class="form-control" type="text" value="">
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                  </div>
				                  <div class="form-group">
				                       <label class="col-sm-3 control-label">编码(value值)：</label>
				                       <div class="col-sm-8">
				                           <input id="itemValue" name="itemValue" class="form-control" type="text" value="">
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
         <button type="button" id="saveMenuRes" class="btn btn-primary" >保存</button>
      </div>
     </div>
   </div>
</div>   
 
<!-- 全局js -->
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>


<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>



<!-- Bootstrap-Treeview plugin javascript -->
<script src="${staticResourceUrl}/js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<!-- jQuery Validation plugin javascript-->
<script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>

<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>


<script type="text/javascript">
	var refreshTreeUrl = "config/dicItem/confitemDatalist"; //刷新右侧列表路径
	var saveResourceUrl = "config/dicItem/addConfitemRes";//添加菜单路径
	var checkItemTypeUrl = "config/dicItem/checkitemType";//添加值的时候进行校验
	var changeStatusResourceUrl = "config/dicItem/changeStatus";//更改状态路径
	var resizeItemIndexUrl = "config/dicItem/itemIndex";//房源分类排序
	
	/* 校验值不为空 */
	function checkNotNull(obj){
   		if(obj == null || obj == "" ||obj == 'undefined'){
   			return false;
   		}else{
   			return true;
   		}
   		
   	}
	
    /* 页面设置 */
   	function paginationParam(params) {
	   var pfid =  "${treeNode.id}";
	   return {
	        limit: params.limit,
	        offset: params.offset,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        pfid:pfid
	        
    	};
	}
   	
   	/* 状态 值设置 */
   	function stateFormat(value, row, index){
   		if(value == 0){
   			return'已停用';
   		}else if(value == 1){
   			return '已启用';
   		}else{
   			return '';
   		}
   	}
   	
   	/* 列表 操作 值设置 */
   	function menuOperData(value, row, index){
   		if(row.itemStatus == 0){
   			return "<button type='button'  class='btn btn-red' onclick='changeStatus(\""+row.fid+'\",\"'+row.itemStatus+"\");'><span> 启用</span></button>";
   		}else if(row.itemStatus == 1){
   			return "<button type='button'  class='btn btn-primary' onclick='changeStatus(\""+row.fid+'\",\"'+row.itemStatus+"\");'><span> 停用</span></button>";
   		}
   		
   	}
   	
   	 /* 左侧树 初始化设置 */
   	function initTreeView(Obj){
   		//加载左侧树	
   	    $('#treeview').treeview({
   	        color: " inherit",
   	        data: Obj,
   	        onNodeSelected: function (event, node) {
   	          
	        //添加资源时  上层目录  默认设置
	        var fidText = node.text;//节点项的名称
	        var code = node.code; //节点项的code值
	        var templateId = $('#templateId').val();//模板id
	       
	        
	       $("#selectedMenuText").val(node.text); //节点项的名称
   	       $("#dicCode").val(code);  //节点项的code值
   	       $("#nodeType").val(node.nodeType);//节点项的类型
   	       
   	       // '房源分类'显示分类排序按钮
   	       resizeItemIndex(code);
   	      
   	        $.ajax({
   		   	url:refreshTreeUrl,
   			type:"post",
   			dataType:"json",
   			data:{"code":code,'templateId':templateId},
   			success:function(data){
   				//刷新table
   				$('#listTable').bootstrapTable('load', data)
   			},
   	       });
   	    }
   	  })
   	}
   	 
   	 function resizeItemIndex(code){
   		 if(code == 'ProductRulesEnum001'){
   			 $("#itemIndex").show();
   		 }else{
   			 $("#itemIndex").hide();
   		 }
   	 }
   	 
   	/* 改变菜单状态  */
   	function changeStatus(fid,resState){
   		var dicCode = $("#dicCode").val(); //节点项的code
   		var templateId = $("#templateId").val(); // 模板id
   		var nodeType = $("#nodeType").val();
   		var checkResult = true;
   		var bar = function(){
   			layer.msg(data.msg, {icon: 6,time: 2000, title:'提示'});
   		}
   		//校验当前值 的类型  是否可以启用
   		if(resState == 0){
   			$.ajax({
   	 		   	url:checkItemTypeUrl,
   	 			type:"POST",
   	 			dataType:"json",
   	 			data:{"dicCode":dicCode,'templateId':templateId,'nodeType':nodeType},
   	 			success:function(data){
   	 				if(!data.result){
   	 					checkResult = false;
   	 					layer.msg(data.msg, {icon: 6,time: 2000, title:'提示'});
   	 				}
   	 			},
   	 	    });
   		}
   		
   		if(checkResult){
   			var mes = '';
   	   		mes = resState == 0 ? '确认要启用？' : '确认要关闭？';// 提示信息
   	   		var iconNum = resState == 0 ? 6 : 5; //显示icon层设置 6：笑脸  5：沮丧
   	   	    //确认是否改变
   	   		layer.confirm(mes, {icon: iconNum, title:'提示'},function(index){
   	   			$.ajax({
   			           type: "POST",
   			           url: changeStatusResourceUrl,
   			           dataType:"json",
   			           traditional: true,
   			           data: {'selectedId':fid,'resStatus':resState},
   			           success: function (result) {
   			        	   //刷新右侧列表
   			        	   $('#listTable').bootstrapTable('refresh', {query: {"code":dicCode,'templateId':templateId}});
   			          	},
   			           error: function(result) {
   			              alert("error:"+result);
   			            }
   			     });
   	   		  
   	   		  layer.close(index);
   	   		});
   		}
   		
   	}
   	
   
   	
   $(document).ready(function () {
   
   	/* 初始化左侧树  */
   	initTreeView('${treeview}');
   	
   	
   	$('#addMenuButton').click(function(){
		 var nodeType = $("#nodeType").val();
		 var dicCode =  $("#dicCode").val();
		 var templateId = $('#templateId').val();
		 $.ajax({
 		   	url:checkItemTypeUrl,
 			type:"POST",
 			dataType:"json",
 			data:{"dicCode":dicCode,'templateId':templateId,'nodeType':nodeType},
 			success:function(data){
 				if(data.result){
 					$('#myModal').modal('toggle');
 				}else{
 					layer.msg(data.msg, {icon: 6,time: 2000, title:'提示'});
 				}
 			},
 	     });
			
	});
   	
   	//房源分类排序
   	$("#itemIndex").click(function(){
        var templateId = $('#templateId').val();//模板id
   		var dicCode = $("#dicCode").val(); //节点项的code
   		$.openNewTab(new Date().getTime(),resizeItemIndexUrl+"?code="+dicCode+"&templateId="+templateId, "房源分类排序");
   	});
   	
   	/*保存资源*/
	$("#saveMenuRes").click(function(){
		
		if($("#saveMenuRes").hasClass("btn-default")){
			return false;
		};
		$("#saveMenuRes").removeClass("btn-primary").addClass("btn-default");
		var templateId = $("#templateId").val();
		var code = $("#dicCode").val();
		//校验通过  则提交
		//if(checkMenuFromData()){
			$.ajax({
	            type: "POST",
	            url: saveResourceUrl,
	            dataType:"json",
	            data: $("#menuEditForm").serialize(),
	            success: function (result) {
		           // initTreeView(result.treeview);
		           	$('#listTable').bootstrapTable('refresh', {query: {"code":code,'templateId':templateId}});
		           	$('#myModal').modal('hide');
		           	$("input[type=reset]").trigger("click");
		            $("#saveMenuRes").removeClass("btn-default").addClass("btn-primary");
	            },
	            error: function(result) {
	               alert("error:"+result);
	               $("#saveMenuRes").removeClass("btn-default").addClass("btn-primary");
	            }
	      });
		//}
	})

});
   	
</script>
   

</body>
  
</html>
