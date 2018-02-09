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
    <base href="${basePath }">
    <title>自如民宿后台管理系统</title>
    <meta name="keywords" content="自如民宿后台管理系统">
    <meta name="description" content="自如民宿后台管理系统">
    <link rel="${staticResourceUrl}/shortcut icon" href="favicon.ico"> 
    <link href="${staticResourceUrl}/css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css?${VERSION}001" rel="stylesheet">
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
                    	<input type="hidden"  id="cityPcode" value="">
                        <!-- <button id="stopMenuButton" type="button" class="btn btn-primary">停用菜单</button> -->
						<div class="col-sm-12">
	                       <button id="addMenuButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">新增行政区划</button> 
	                       <!-- 菜单列表 -->
	                       <div class="example-wrap">
	                            <div class="example">
		                            <table id="listTable" data-click-to-select="true"
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
				                    data-query-params="paginationParam"
				                    data-content-type="application/x-www-form-urlencoded"
				                    data-method="post"
				                    data-row-style="rowStyle"
				                    data-height="620"
				                    data-url="config/city/confCityDatalist">
				                    <thead>
			                        <tr>
			                            <th data-field="id" data-align="center" data-checkbox="true"></th>
			                            <th data-field="showName" data-align="center" data-sortable="true">名称</th>
			                            <th data-field="code" data-align="center" >CODE</th>
			                            <th data-field="cityStatus" data-align="center" data-formatter="stateFormat">状态</th>
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
          <h4 class="modal-title">添加行政区划</h4>
       </div>
       <div class="modal-body">
	        <div class="wrapper wrapper-content animated fadeInRight">
		        <div class="row">
		          <div class="col-sm-12">
		               <div class="ibox float-e-margins">
		                   <div class="ibox-content">
			                   <form id="menuEditForm" action="/config/city/addConfCityRes" class="form-horizontal m-t" >
				                   <input id="selectedMenuId" type="hidden" name="pcode" value="${confCity.code}">
				                   <input id="selectedLevel" type="hidden" name="level" value="${confCity.level}">

				                   <div class="form-group">
				                       <label class="col-sm-3 control-label">上级配置：</label>
				                       <div class="col-sm-8">
				                           <input id="selectedMenuText" class="form-control" type="text" value="${confCity.showName}">
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
				                       <label class="col-sm-3 control-label">CODE：</label>
				                       <div class="col-sm-8">
				                           <input id="code" name="code" class="form-control" type="text" value="">
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

<!-- 自定义js  暂时引用本地js -->
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
	var refreshTreeUrl = "config/city/confCityDatalist"; //刷新右侧列表路径
	var saveMenuResourceUrl = "config/city/addConfCityRes";//添加菜单路径
	var changeStatusResourceUrl = "config/city/changeStatus";//更改状态路径
    var checkCodeUniqueUrl = "config/city/checkCodeUnique";// 校验code唯一
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
	   var pcode =  $("#cityPcode").val();
	    //返回一个对象
	    return {
	        limit: params.limit,
	        offset: params.offset,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        pcode:pcode
	        
    	};
	} 
    
   	/* 状态 值设置 */
   	function stateFormat(value, row, index){
   	 	if(value == 0){
			return'全部关闭';
		}else if(value == 1){
			return '全部开通';
		}else if(value == 2){
			return '关闭房东，开通房客';
		}else if(value == 3){
			return '关闭房客，开通房东';
		}else{
			return '';
		}
   	}
   	
   	/* 列表 操作 值设置 */
   	function menuOperData(value, row, index){
   		var csV = "";
   		if(row.cityStatus == 1){
   			csV = "全部开通";
   			return " <div class='btn-group'><a class='btn btn-success dropdown-toggle' data-toggle='dropdown' href='javascript:;'>"+csV+"<span class='caret'></span></a><ul class='dropdown-menu'><li><a class='btn btn-danger' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"0\",\""+row.code+"\",\""+row.level+"\");' href='javascript:;'>全部关闭</a></li><li><a class='btn btn-warning' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"3\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房客,开通房东</a></li><li><a class='btn btn-info' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"2\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房东,开通房客</a></li> </ul></div>";
   		}else if(row.cityStatus == 0){
   			csV = "全部关闭";
   			return " <div class='btn-group'><a class='btn btn-danger dropdown-toggle' data-toggle='dropdown' href='javascript:;'>"+csV+"<span class='caret'></span></a><ul class='dropdown-menu'><li><a class='btn btn-success' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"1\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>全部开通</a></li><li><a class='btn btn-warning' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"3\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房客,开通房东</a></li><li><a class='btn btn-info' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"2\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房东,开通房客</a></li> </ul></div>";
   		}else if(row.cityStatus == 3){
   			csV = "关闭房客，开通房东";
   			return " <div class='btn-group'><a class='btn btn-warning dropdown-toggle' data-toggle='dropdown' href='javascript:;'>"+csV+"<span class='caret'></span></a><ul class='dropdown-menu'><li><a class='btn btn-danger' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"0\",\""+row.code+"\",\""+row.level+"\");' href='javascript:;'>全部关闭</a></li><li><a class='btn btn-success' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"1\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>全部开通</a></li><li><a class='btn btn-info' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"2\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房东,开通房客</a></li> </ul></div>";
   		}else if(row.cityStatus == 2){
   			csV = "关闭房东，开通房客";
   			return " <div class='btn-group'><a class='btn btn-info dropdown-toggle' data-toggle='dropdown' href='javascript:;'>"+csV+"<span class='caret'></span></a><ul class='dropdown-menu'><li><a class='btn btn-danger' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"0\",\""+row.code+"\",\""+row.level+"\");' href='javascript:;'>全部关闭</a></li><li><a class='btn btn-success' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"1\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>全部开通</a></li><li><a class='btn btn-warning' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\"3\",\""+row.code+"\",\""+row.level+"\");' tabindex='-1' href='javascript:;'>关闭房客,开通房东</a></li> </ul></div>";
   		}
   		
   		/* if(row.cityStatus == 1){
   			return "<button type='button' class='btn btn-primary btn-sm' onclick='changeStatus(\""+row.fid+"\",\""+row.cityStatus+"\",\""+row.code+"\",\""+row.level+"\");'>停用</button>";
   		}else{
   			return "<button type='button' class='btn btn-red btn-sm' onclick='changeStatus(\""+row.fid+"\",\""+row.showName+"\",\""+row.cityStatus+"\",\""+row.code+"\",\""+row.level+"\");'>启用</button>";
   		}
 	*/

   	}
   	 
    /* 左侧树 初始化设置 */
   	function initTreeView(domId,obj){
   		 var callTreeBinary = function(event, node){
   		 //添加菜单时  上层目录  默认设置
	     var fid = node.id; //默认菜单id
	     var fidText = node.text;//默认菜单名称
	     var code = node.code;
	     $("#selectedMenuId").val(code);
	     $("#selectedMenuText").val(node.text);
	     $("#selectedLevel").val(node.level);
	     $("#cityPcode").val(code);
 	     if(node.level == 2 || node.level == 3){
 	    	 $("#langAndLat").show();
 	     }else{
 	    	 $("#langAndLat").hide();
 	     }  
 	     var data = {"pcode":code,limit:$('#listTable').bootstrapTable('getOptions').pageSize,page:1};
 	     var callBinary = function(result){
 	    	//刷新table
			 $('#listTable').bootstrapTable('load', result)
 	     }
 	     ajaxPostSubmit(refreshTreeUrl,data,callBinary);
   		 }
   	     //加载左侧树 
   		 treeViewCommon(domId,obj,callTreeBinary);
   	}
   	
   	/* 改变菜单状态  */
   	function changeStatus(fid,showName,resState,code,level){
   		var selectedMenuId = $("#selectedMenuId").val();
   		//var mes = resState == 1 ? '该城市下若有未履行订单，仍需继续履行，\n确认要停用城市？' : '确认要启用城市？'; 提示信息
   		var mes = "";
   		if(resState == 0){
   			mes = "该城市下若有未履行订单，仍需继续履行，\n确认全部关闭？";
   		}else if(resState == 1){
   			mes = "确认全部启用？";
   		}else if(resState == 2){
   			mes = "该城市下若有未履行订单，仍需继续履行，\n确认关闭房东，开通房客？";
   		}else if(resState == 3){
   			mes = "该城市下若有未履行订单，仍需继续履行，\n确认关闭房客，开通房东？";
   		}
   		var iconNum = resState == 0 ? 5 : 6; //显示icon层设置 6：笑脸  5：沮丧
   		
   		//传递参数
   		var data = {'selectedId':fid,'resStatus':resState,'showName':showName,'selectedCode':code,'resLevel':level};
   		//ajax调用回调函数
   		var callBinary = function(result){
   			if(result.code == 0){
	   		    //刷新右侧列表
	     	    $('#listTable').bootstrapTable('refresh', {query: {pcode: selectedMenuId,limit:$('#listTable').bootstrapTable('getOptions').pageSize}});
   			}else{
   				layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
   			}
   		}
   		
   	    //layerconfirm调用回调函数
   		var layerCallBinary = function(index){
   			ajaxPostSubmit(changeStatusResourceUrl,data,callBinary);
   			layer.close(index);
   		}
   	    
   	    //确认是否改变
   		layerConfirm(mes,iconNum,layerCallBinary);
   	}
   	
    $(document).ready(function(){
    /* 初始化左侧树  */
   	initTreeView('treeview','${treeview}');
    /*保存菜单*/
	$("#saveMenuRes").click(function(){
		var selectedMenuId = $("#selectedMenuId").val();
		//保存新增  城市配置  回调函数
		var callBinary = function(result){
			if(result.code == 0){
				initTreeView('treeview',result.data.treeview);
	           	$('#listTable').bootstrapTable('refresh', {query: {pcode: selectedMenuId,limit:$('#listTable').bootstrapTable('getOptions').pageSize}});
	           	$('#myModal').modal('hide');
	           	$("input[type=reset]").trigger("click");
			}else{
				msgLayer(result.msg);
			}
		}
		//ajax 保存新增 城市配置  传递的值参数
		var data = toJson($("#menuEditForm").serializeArray());
		
		//校验新增城市配置  code是否唯一  成功时的回调函数， 成功后 调用 ajax保存
		var callCheckCodeBinary = function(result){
			if(result.code == 0){
				//保存 添加的城市配置项  到数据库
				ajaxPostSubmit(saveMenuResourceUrl,data,callBinary);
			}else{
				msgLayer(result.msg);
			}
		}
		var callCheckCodeData = {'code':$("#code").val()}; //新增城市配置，客户输入的code值
		//校验 新增的城市配置  code是否唯一  ajax请求
		ajaxPostSubmit(checkCodeUniqueUrl,callCheckCodeData,callCheckCodeBinary);
	})
   });
   	
    function toJson(array){
		var json = {};
		$.each(array, function(i, n){
			var key = n.name;
			var value = n.value;
			if($.trim(value).length != 0){
				json[key] = value;
			}
		});
		return json;
	}	
  
</script>
   

</body>
  
</html>
