<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <base href="${basePath}">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="css/animate.css${VERSION}001" rel="stylesheet">
    <link href="css/style.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/treeview/bootstrap-treeview.css${VERSION}001" rel="stylesheet">
   
  
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="ibox float-e-margins">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <!-- 左侧树div -->
                    <div class="col-sm-3">
                        <div id="treeview" class="test" style="overflow-y:auto; overflow-x:auto;height:600px;margin-top:40px;"></div>
                    </div>
                    <!-- 右侧table div -->
                    <div class="col-sm-9">
                        <!-- <button id="stopMenuButton" type="button" class="btn btn-primary">停用菜单</button> -->
						<div class="col-sm-12">
	                       <button id="addMenuButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">新增菜单</button> 
	                       <!-- 菜单列表 -->
	                       <div class="example-wrap">
	                            <div class="example">
		                            <table id="listTable" class="table table-bordered" data-click-to-select="true"
				                    data-toggle="table"
				                    data-side-pagination="server"
				                    data-pagination="true"
				                    data-page-list="[10,20,50,100]"
				                    data-pagination="true"
				                    data-page-size="10"
				                    data-pagination-first-text="首页"
				                    data-pagination-pre-text="上一页"
				                    data-pagination-next-text="下一页"
				                    data-pagination-last-text="末页"
				                    data-content-type="application/x-www-form-urlencoded"				  
				                    data-query-params="paginationParam"
				                    data-method="post" 
				                    data-row-style="rowStyle" 
				                    data-height="630"
				                    data-url="resource/menuPageList">    
				                    <thead>
			                        <tr>
			                            <th data-field="id" data-align="center" data-checkbox="true"></th>
			                            <th data-field="resName" data-align="center" data-sortable="true">菜单名称</th>
			                            <th data-field="resUrl" data-align="center" data-formatter="urlFormatData">URL</th>
			                            <th data-field="orderCode" data-align="center">排序</th>
			                            <th data-field="resState" data-align="center" data-formatter="stateFormat">状态</th>
			                             <th data-field="menuAuth" data-align="center" data-formatter="stateMenuAuth">菜单特权</th>
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
          <h4 class="modal-title">添加菜单</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
			                   <form id="menuEditForm" action="system/permission/addMenuRes" class="form-horizontal m-t" >
				                   <input id="selectedMenuId" type="hidden" name="parentFid" value="${treeNode.id}">
				                           
				                   <div class="form-group">
				                       <label class="col-sm-3 control-label">上级目录：</label>
				                       <div class="col-sm-8">
				                           <input id="selectedMenuText" class="form-control" type="text" value="${treeNode.text}" readonly="readonly" >
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                   </div>
				                           
				                  <div class="form-group">
				                       <label class="col-sm-3 control-label">名称：</label>
				                       <div class="col-sm-8">
				                           <input id="resName" name="resName" class="form-control" type="text" value="${menu.resName }">
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                 </div>
				                 
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">图标：</label>
				                     <div class="col-sm-4">
				                         <input id="className" name="className" class="form-control" type="text"  value="${menu.className }" aria-required="true" aria-invalid="true" class="error">
				                     </div>
                                     <span class="help-block m-b-none">请填格式为"fa fa-xxx"，<a href="http://www.yeahzan.com/fa/facss.html" target="_blank">点击参考</a></span>
				                 </div>
				                 
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">URL：</label>
				                     <div class="col-sm-8">
				                         <input id="resUrl" name="resUrl" class="form-control" type="text" value="${menu.resUrl }">
				                         <span class="help-block m-b-none"></span>
				                     </div>
				                 </div>
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">排序：</label>
				                     <div class="col-sm-8">
				                         <input id="orderCode" name="orderCode" class="form-control" type="text" value="${menu.orderCode }">
				                     </div>
				                 </div>
				                <%--  <div class="form-group">
				                     <label class="col-sm-3 control-label">是否启用：</label>
				                     <div class="col-sm-8">
				                         <input id="resState" name="resState" class="form-control" type="text"  value="${menu.resState }" aria-required="true" aria-invalid="false" class="valid">
				                     </div>
				                 </div> --%>
				                         
				                <div class="form-group">
				                     <label class="col-sm-3 control-label">菜单类型</label>
				                     <div class="col-sm-8">
					                    <input id="resType" name="resType"  type="hidden" class="form-control" type="text"  value="${menu.resType }" aria-required="true" aria-invalid="false" class="valid">
				                        <select id="resTypeSelect" class="form-control m-b" >
				                            <option value="-1">请选择</option>
				                            <option value="0">非功能点菜单</option>
				                            <option value="1">功能点菜单</option>
				                            <option value="2">功能按钮</option>
				                        </select>
				                     </div>
				                 </div>
				                 
				                  <div class="form-group">
				                     <label class="col-sm-3 control-label">菜单权限类型</label>
				                     <div class="col-sm-8">
					                    <input id="menuAuth" name="menuAuth"  type="hidden" class="form-control" type="text"  value="${menu.menuAuth }" aria-required="true" aria-invalid="false" class="valid">
				                        <select id="menuAuthSelect" class="form-control m-b" >
				                            <option value="0">普通菜单</option>
				                            <option value="1">特权菜单</option>
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


<!-- 修改菜单  弹出框 -->
<div class="modal inmodal" id="myModalE" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content animated bounceInRight">
       <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
          </button>
          <h4 class="modal-title">修改菜单</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-14">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
			                   <form id="upDateMenuForm" action="resource/updateResource" class="form-horizontal m-t" >
				                   <input type="hidden" name="fid" id="eFid">
				                   <div class="form-group">
				                       <label class="col-sm-3 control-label">上级目录：</label>
				                       <div class="col-sm-8">
				                           <input id="eParentName" class="form-control" type="text"  readonly="readonly" >
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                   </div>
				                           
				                  <div class="form-group">
				                       <label class="col-sm-3 control-label">名称：</label>
				                       <div class="col-sm-8">
				                           <input id="eResName" name="resName" class="form-control" type="text" >
				                           <span class="help-block m-b-none"></span>
				                       </div>
				                 </div>
				                 
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">图标：</label>
				                     <div class="col-sm-4">
				                         <input id="eClassName" name="className" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error">
				                     </div>
                                     <span class="help-block m-b-none">图标格式为"fa fa-xxx"，<a href="http://www.yeahzan.com/fa/facss.html" target="_blank">点击参考</a></span>
				                 </div>
				                 
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">URL：</label>
				                     <div class="col-sm-8">
				                         <input id="eResUrl" name="resUrl" class="form-control" type="text" >
				                         <span class="help-block m-b-none"></span>
				                     </div>
				                 </div>
				                 <div class="form-group">
				                     <label class="col-sm-3 control-label">排序：</label>
				                     <div class="col-sm-8">
				                         <input id="eOrderCode" name="orderCode" class="form-control" type="text" >
				                     </div>
				                 </div>
				                <%--  <div class="form-group">
				                     <label class="col-sm-3 control-label">是否启用：</label>
				                     <div class="col-sm-8">
				                         <input id="resState" name="resState" class="form-control" type="text"  value="${menu.resState }" aria-required="true" aria-invalid="false" class="valid">
				                     </div>
				                 </div> --%>
				                         
				                <div class="form-group">
				                     <label class="col-sm-3 control-label">菜单类型</label>
				                     <div class="col-sm-8">
				                        <select id="eResType"  name="resType" class="form-control m-b" >
				                            <option value="-1">请选择</option>
				                            <option value="0">非功能点菜单</option>
				                            <option value="1">功能点菜单</option>
				                            <option value="2">功能按钮</option>
				                        </select>
				                     </div>
				                 </div>
				                 
				                  <div class="form-group">
				                     <label class="col-sm-3 control-label">菜单权限类型</label>
				                     <div class="col-sm-8">
				                        <select id="eMenuAuth" class="form-control m-b" name="menuAuth">
				                            <option value="0">普通菜单</option>
				                            <option value="1">特权菜单</option>
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
         <button type="button" id="updateMenu" class="btn btn-primary" >保存</button>
      </div>
     </div>
   </div>
</div>
 
<!-- 全局js -->
<script src="js/jquery.min.js${VERSION}001"></script>
<script src="js/bootstrap.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="js/content.js${VERSION}001"></script>


<!-- Bootstrap table -->
<script src="js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>

<!-- Bootstrap-Treeview plugin javascript -->
<script src="js/plugins/treeview/bootstrap-treeview.js${VERSION}001"></script>

<!-- jQuery Validation plugin javascript-->
<script src="js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>

<script src="js/plugins/validate/messages_zh.min.js${VERSION}001"></script>

<script src="js/plugins/layer/layer.min.js${VERSION}"></script>
<script src="js/plugins/layer/laydate/laydate.js${VERSION}"></script>

<!-- layer javascript -->
<script src="js/plugins/layer/layer.min.js${VERSION}001"></script>


<script type="text/javascript">
	var refreshTreeUrl = "resource/menuPageList"; //刷新右侧列表路径
	var saveMenuResourceUrl = "resource/insertMenuResource";//添加菜单路径
	var changeStatusResourceUrl = "resource/changeMenuStatus";//更改状态路径
	var parent_id =  "${treeNode.id}";// 作为分页来用，保证分页是所选中菜单的
	
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
	  
	   return {
	        limit: params.limit,
	        offset: params.offset,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        parent_fid:parent_id
	        
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
   	//0:普通权限 1=特权菜单
   	function stateMenuAuth(value, row, index){
   		if(value == 0){
   			return '普通权限 ';
   		}
   		if(value == 1){
   			return '特权菜单';
   		}
   		return "";
   	}
   	/* 列表 操作 值设置 */
   	function menuOperData(value, row, index){
   		if(row.resState == 0){
   			return "<button type='button'  class='btn btn-red btn-sm' onclick='changeStatus(\""+row.fid+'\",\"'+row.resState+"\");'><span> 启用</span></button>"
   			+"&nbsp;&nbsp;&nbsp<button type='button'  class='btn btn-red btn-sm' onclick='upMenuShow(\""+row.fid+"\");'><span> 修改</span></button>";
   		}else if(row.resState == 1){
   			return "<button type='button'  class='btn btn-primary btn-sm' onclick='changeStatus(\""+row.fid+'\",\"'+row.resState+"\");'><span> 停用</span></button>"
   			+"&nbsp;&nbsp;&nbsp<button type='button'  class='btn btn-red btn-sm' onclick='upMenuShow(\""+row.fid+"\");'><span> 修改</span></button>";
   		}
   		
   	}
   	 /* url 操作 值设置 */
   	function urlFormatData(value, row, index){
   		if(checkNotNull(value)){
   			return value;
   		}else{
   			return "*";
   		}
   	}
   	
   	 /* 左侧树 初始化设置 */
   	function initTreeView(Obj){
   	    //加载左侧树	
   	    $('#treeview').treeview({
   	        color: " inherit",
   	        data: Obj,
   	        levels:1,
   	        onNodeSelected: function (event, node) {
   	          
   	        //添加菜单时  上层目录  默认设置
   	        var fid = node.id; //默认菜单id
   	        var fidText = node.text;//默认菜单名称
   	        
   	       $("#selectedMenuId").val(fid);
   	       $("#selectedMenuText").val(node.text);
   	       
   	        $.ajax({
   		   	url:refreshTreeUrl,
   			type:"post",
   			dataType:"json",
   			data:{"fid":fid,"limit":10,"page":1},
   			success:function(data){
   				parent_id = fid;
   				//刷新table
   				$('#listTable').bootstrapTable('load', data);
   				$('#listTable').bootstrapTable('selectPage', 1)
   			},
   	       });
   	    }
   	  })
   	}
   	
   	/* 改变菜单状态  */
   	function changeStatus(fid,resState){
   		var selectedMenuId = $("#selectedMenuId").val();
   		
   		var mes = '';
   		mes = resState == 0 ? '确认要启用菜单？' : '确认要关闭菜单？';// 提示信息
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
		        	   $('#listTable').bootstrapTable('refresh', {query: {fid: selectedMenuId}});
		          	},
		           error: function(result) {
		              alert("error:"+result);
		            }
		     });
   		  
   		  layer.close(index);
   		});
   	}
   	
   	/* 校验form表单 */
   	function checkMenuFromData(){
   		var res = true;
   		var resName = $("#resName").val();
   		if(!checkNotNull(resName)){
   			res = false;
   			//自定义 类型弹出框
   			layer.msg("请填写菜单名称！",{icon: 6,time: 1000,offset:'30px',area:['200px', '70px']});  
   			return res;
   		}
   		var resType = $("#resType").val();
   		if(!checkNotNull(resType) || resType == -1){
   			res = false;
   		    //自定义 类型弹出框
   			layer.msg("请选择菜单类型！",{icon: 6,time: 1000,offset:'30px',area:['200px', '70px']});
   			return res;
   		}
   		return res;
   		
   	}
   	
 	/* 更新校验form表单 */
   	function checkUpMenuFromData(){
   		var res = true;
   		var resName = $("#eResName").val();
   		if(!checkNotNull(resName)){
   			res = false;
   			//自定义 类型弹出框
   			layer.msg("请填写菜单名称！",{icon: 6,time: 1000,offset:'30px',area:['200px', '70px']});  
   			return res;
   		}
   		var resType = $("#eResType").val();
   		if(!checkNotNull(resType) || resType == -1){
   			res = false;
   		    //自定义 类型弹出框
   			layer.msg("请选择菜单类型！",{icon: 6,time: 1000,offset:'30px',area:['200px', '70px']});
   			return res;
   		}
   		return res;
   		
   	}
   	
   	$(document).ready(function () {
   		
   	/* 初始化左侧树  */
   	initTreeView('${treeview}');
   	
	/* 选择 菜单类型 */
	$('#resTypeSelect').change(function(){
		var resTypeSelected = $(this).children('option:selected').val();
		$("#resType").val(resTypeSelected);
		
	});
	
	/* 选择 菜单权限类型 */
	$('#menuAuthSelect').change(function(){
		var menuAuthSelected = $(this).children('option:selected').val();
		$("#menuAuth").val(menuAuthSelected);
	});
  	
	/*保存菜单*/
	$("#saveMenuRes").click(function(){
		var selectedMenuId = $("#selectedMenuId").val();
		//校验通过  则提交
		if(checkMenuFromData()){
			$.ajax({
	            type: "POST",
	            url: saveMenuResourceUrl,
	            dataType:"json",
	            data: $("#menuEditForm").serialize(),
	            success: function (result) {
	            initTreeView(result.data.treeview);
	           	$('#listTable').bootstrapTable('refresh', {query: {fid: selectedMenuId}});
	           	$('#myModal').modal('hide');
	           	$("input[type=reset]").trigger("click");
	            },
	            error: function(result) {
	               alert("error:"+result);
	            }
	      });
		}
	})
	/*更新菜单*/
	$("#updateMenu").click(function(){
		var selectedMenuId = $("#selectedMenuId").val();
		//校验通过  则提交
		if(checkUpMenuFromData()){
			$.ajax({
	            type: "POST",
	            url: "resource/updateResource",
	            dataType:"json",
	            data: $("#upDateMenuForm").serialize(),
	            success: function (result) {
	           	$('#listTable').bootstrapTable('refresh', {query: {fid: selectedMenuId}});
	           	$('#myModalE').modal('hide');
	            },
	            error: function(result) {
	               alert("error:"+result);
	            }
	      });
		}
	})
	});
   	//修改菜单
   	function upMenuShow(fid){
		$.ajax({
            type: "POST",
            url: 'resource/findResourceByFid',
            dataType:"json",
            data: {"fid":fid},
            success: function (result) {
            	if(result.code==0){
            		var menu=result.data.resource;
            		$("#eFid").val(menu.fid);
            		$("#eResName").val(menu.resName);
            		$("#eClassName").val(menu.className);
            		$("#eResUrl").val(menu.resUrl);
            		$("#eOrderCode").val(menu.orderCode);
            		$("#eResType").val(menu.resType);
            		$("#eMenuAuth").val(menu.menuAuth);
            		$("#eParentName").val(result.data.panrentName);
            		$('#myModalE').modal('show');
            	}else{
            		layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            	}
            },
            error: function(result) {
            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
            }
      });
   	}
</script>
   

</body>
  
</html>