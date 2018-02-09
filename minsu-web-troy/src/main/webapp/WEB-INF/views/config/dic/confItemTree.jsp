<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <link href="${staticResourceUrl}/css/style.css${VERSION}001" rel="stylesheet">
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
                    <div class="col-sm-3">
                        <div id="treeview" class="test"></div>
                    </div>
                    <!-- 右侧table div -->
                    <div class="col-sm-9">
                        <div class="col-sm-12">
	                       <button id="addMenuButton" type="button" class="btn btn-primary"  data-target="#myModal">新增资源</button> 
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
				                    data-height="610"
				                    data-url="config/dic/confitemDatalist">    
				                    <thead>
			                        <tr>
			                            <th data-field="id" data-align="center" data-checkbox="true"></th>
			                            <th data-field="showName" data-align="center" data-sortable="true">名称</th>
			                            <th data-field="dicCode" data-align="center" >编码</th>
			                            <th data-field="dicIndex" data-align="center">排序</th>
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
          <h4 class="modal-title">添加配置项</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-12">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
		                       <input id="nodeType" type="hidden" value="">   
			                   <form id="menuEditForm" action="config/dic/addConfCityRes" class="form-horizontal m-t" >
				                   <input id="selectedMenuId" type="hidden" name="pfid" value="${treeNode.id}">
				                   <input id="selectedLevel" type="hidden" name="dicLevel" value="${treeNode.level}">
				                           
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
				                       <label class="col-sm-3 control-label">编码：</label>
				                       <div class="col-sm-8">
				                           <input id="dicCode" name="dicCode" class="form-control" type="text" value="">
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                  </div> 
				                  <div class="form-group">
				                       <label class="col-sm-3 control-label">排序：</label>
				                       <div class="col-sm-8">
				                           <input id="dicIndex" name="dicIndex" class="form-control" type="text" value="">
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                  </div>
				                  <div class="form-group">
				                     <label class="col-sm-3 control-label">配置项类型</label>
				                     <div class="col-sm-8">
					                    <input id="dicType" name="dicType"  type="hidden" class="form-control" type="text"  value="" aria-required="true" aria-invalid="false" class="valid">
				                        <select id="resTypeSelect" class="form-control m-b" >
				                            <option value="-1">请选择</option>
				                            <option value="0">非叶子节点</option>
				                            <option value="1">唯一值</option>
				                            <option value="2">多个值</option>
				                        </select>
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
<script src="${pageContext.request.contextPath}/resources/js/content.js${VERSION}001"></script>


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


<script type="text/javascript">
	var refreshTreeUrl = "config/dic/confitemDatalist"; //刷新右侧列表路径
	var saveResourceUrl = "config/dic/addConfitemRes";//添加菜单路径
	

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
   	
   	
   	 /* 左侧树 初始化设置 */
   	function initTreeView(Obj){
   		//加载左侧树	
   	    $('#treeview').treeview({
   	        color: " inherit",
   	        data: Obj,
   	        onNodeSelected: function (event, node) {
   	          
	        //添加菜单时  上层目录  默认设置
	        var fid = node.id; //默认菜单id
	        var fidText = node.text;//默认菜单名称
	       $("#selectedMenuId").val(fid);
   	       $("#selectedMenuText").val(node.text);
   	       $("#nodeType").val(node.nodeType);//节点项的类型
   	       $("#selectedLevel").val(node.level);
   	       //禁用code文本框
   	       if(node.level>=1){
   	    	   $("#dicCode").attr("disabled",true);
   	       }else{
   	    	   $("#dicCode").attr("disabled",false);
   	       }
   	       $.ajax({
   		   	url:refreshTreeUrl,
   			type:"post",
   			dataType:"json",
   			data:{"pfid":fid},
   			success:function(data){
   				//刷新table
   				$('#listTable').bootstrapTable('load', data)
   			},
   	       });
   	    }
   	  })
   	}
   	
   $(document).ready(function () {
   
   	/* 初始化左侧树  */
   	initTreeView('${treeview}');
   	
   	/* 选择 菜单类型 */
	$('#resTypeSelect').change(function(){
		var resTypeSelected = $(this).children('option:selected').val();
		$("#dicType").val(resTypeSelected);
		
	});
   	
   	/* 校验配置项结点是否为叶子节点   是：不可以添加子项  否：可以添加子项  其它type类型为非法类型*/
	$('#addMenuButton').click(function(){
		 var nodeType = $("#nodeType").val();
	     if(nodeType == 1 || nodeType == 2){
	    	 var mess = "上层节点为叶子节点,下面不能再有节点！";
	    	 msgLayer(mess);
	     }else if(nodeType == 0){
	    	 $('#myModal').modal('toggle');
	     }else{
	    	 var mess = "节点类型非法type类型！";
	    	 msgLayer(mess);
	     }
	});
   	
   	
   	
   	/*保存资源*/
	$("#saveMenuRes").click(function(){
		if($("#saveMenuRes").hasClass("btn-default")){
			return false;
		};
		$("#saveMenuRes").removeClass("btn-primary").addClass("btn-default");
		var selectedMenuId = $("#selectedMenuId").val();
		//校验通过  则提交
		//if(checkMenuFromData()){
			$.ajax({
	            type: "POST",
	            url: saveResourceUrl,
	            dataType:"json",
	            data: $("#menuEditForm").serialize(),
	            success: function (result) {
		            initTreeView(result.treeview);
		           	$('#listTable').bootstrapTable('refresh', {query: {pfid: selectedMenuId}});
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
