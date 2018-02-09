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
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
	  <input id="code" name="code" type="hidden" value="${code }">
	  <input id="templateId" name="templateId" type="hidden" value="${templateId }">
      <div class="ibox float-e-margins">
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                	<customtag:authtag authUrl="config/dicItem/updateItemList">
                    	<input id="editBtn" type="button" value="保存" class="btn btn-primary">
                    </customtag:authtag> 
                    <div class="example-wrap">
                        <div class="example">
	                        <table id="listTable" 
	                        	data-click-to-select="true"
			                    data-toggle="table"
			                    data-side-pagination="server"
			                    data-page-list="[10,20,50]"
			                    data-pagination="false"
			                    data-page-size="50"
			                    data-pagination-first-text="首页"
			                    data-pagination-pre-text="上一页"
			                    data-pagination-next-text="下一页"
			                    data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
			                    data-query-params="paginationParam"
			                    data-method="post" 
			                    data-single-select="true"
			                    data-row-style="rowStyle" 
			                    data-height="680"
			                    data-url="config/dicItem/confitemDatalist">    
			                    <thead>
		                        <tr>
		                            <th data-field="fid" data-align="center" data-visible="false"></th>
		                            <th data-field="showName" data-align="center">名称</th>
		                            <th data-field="dicCode" data-align="center" >编码</th>
		                            <th data-field="itemValue" data-align="center" >值</th>
		                            <th data-field="itemIndex" data-align="center" >排序位置</th>
		                            <th data-field="itemStatus" data-align="center" data-formatter="stateFormat">状态</th>
		                            <th data-field="operate" data-align="center" data-formatter="formateOperate">操作</th>
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
<script src="${staticResourceUrl}/js/jquery.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}001"></script>

<!-- 自定义js -->
<script src="${staticResourceUrl}/js/content.js${VERSION}001"></script>


<!-- Bootstrap table -->
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>

<!-- layer javascript -->
<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}001"></script>

<script type="text/javascript">
	$(function(){
		$('#listTable').on('load-success.bs.table', function (data) {
			resizeButton();
		});
	});
	
	function resizeButton(){
		$.each($("button.moveup"), function(i, n){
			if(i == 0){
				$(n).hide();
			} else {
				$(n).show();
			}
		});
		
		var size = $(".movedown").size();
		var last = size < 1 ? size : size - 1;
		$.each($("button.movedown"), function(i, n){
			if(i == last){
				$(n).hide();
			} else {
				$(n).show();
			}
		});
	}
	
	/* 校验值不为空 */
	function checkNull(obj){
		return typeof(obj) == 'undefined' || obj == null || obj == "";
   	}
	
    /* 页面设置 */
   	function paginationParam(params) {
	   return {
	        limit: params.limit,
	        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
	        templateId:$("#templateId").val(),
	        code:$("#code").val()
    	};
	}
   	
   	/* 状态 值设置 */
   	function stateFormat(value, row, index){
   		if(value == 0){
   			return '已停用';
   		}else if(value == 1){
   			return '已启用';
   		}else{
   			return '';
   		}
   	}

	// 操作列
	function formateOperate(value, row, index) {
		return "<input class='item' name='fid' type='hidden' value='"+row.fid+"'><input class='item itemIndex' index='"+(index+1)+"' name='itemIndex' type='hidden' value='"+(index+1)+"'>" 
				+ "<button type='button' class='btn btn-primary btn-sm moveup' onclick='moveup(this);'><span>上移</span></button>&nbsp;"
				+ "<button type='button' class='btn btn-primary btn-sm movedown' onclick='movedown(this);'><span>下移</span></button>";
	}
	
	function moveup(obj){
		var _obj = $(obj).closest('tr');
		prev($(_obj[0]));
		resizeButton();
		resizeItemIndex();
	}
	
	function movedown(obj){
		var _obj = $(obj).closest('tr');
		next($(_obj[0]));
		resizeButton();
		resizeItemIndex();
	}
	
	function resizeItemIndex(){
		$.each($("input.itemIndex"), function(i, n){
			$(this).val(i+1);
		});
	}
	
	function prev(o) {
        var prevs = o.prevAll('tr');
        if (prevs.length > 0) {
            var tmp = o.clone(true);
            var oo = prevs[0];
            o.remove();
            $(oo).before(tmp);
        }
    }

    function next(o) {
        var nexts = o.nextAll('tr');
        if (nexts.length > 0) {
            var tmp = o.clone(true);
            var oo = nexts[0];
            o.remove();
            $(oo).after(tmp);
        }
    }
    
    $("#editBtn").click(function(){
    	$(this).attr("disabled", "disabled");
    	var rows = $('#listTable').bootstrapTable('getData', true);
    	if(rows.length == 0 || (rows.length == 1 && !checkNull(rows[0].itemIndex))){
	    	$(this).removeAttr("disabled");
	    	return;
    	}
    	
    	var flag = true;
    	$.each($("input.itemIndex"), function(i, n){
    		var pre = parseInt($(n).attr('index'));
    		var next = parseInt($(n).val());
    		if(pre != next){
    			flag = false;
    			return false;
    		}
    	});
    	if(flag){
    		layer.alert("请先调整排序再进行操作", {
				icon : 6,
				time : 2000,
				title : '提示'
			});
	    	$(this).removeAttr("disabled");
	    	return;
    	}
    	
    	$.ajax({
			url : "config/dicItem/updateItemList",
			data : {"jsonStr":toJsonStr($("input.item").serializeArray())},
			type : "post",
			dataType : "json",
			success : function(result) {
				if (result.code === 0) {
					$('#listTable').bootstrapTable('refresh');
				} else {
					layer.alert("操作失败", {
						icon : 5,
						time : 2000,
						title : '提示'
					});
				}
			},
			error : function(result) {
				layer.alert("未知错误", {
					icon : 5,
					time : 2000,
					title : '提示'
				});
			}
		});
    	$(this).removeAttr("disabled");
    });
    
    function toJsonStr(array){
    	var dicCode = $("#code").val();
		var rows = [];
		var row;
		$.each(array, function(i, n){
			if(i%2==0){
				row = {};
				row.dicCode = dicCode;
				row = assemble(row,n);
			}else{
				row = assemble(row,n);
				rows.push(row);
			}
		});
		return JSON.stringify(rows);
	}
    
    function assemble(row,n){
		var key = n.name;
		var value = n.value;
		if($.trim(value).length != 0){
			row[key] = value;
		}
    	return row;
    }
</script>
</body>
</html>
