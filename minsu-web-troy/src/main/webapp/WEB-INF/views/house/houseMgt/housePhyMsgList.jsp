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
    <link href="${staticResourceUrl}/css/font-awesome.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
	<link href="${staticResourceUrl}/css/custom-z.css${VERSION}" rel="stylesheet">
	<style type="text/css">
		.modal-lm{
			width: 1100px;
		}
	</style>
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
           <div class="col-sm-12">
		        <div class="row row-lg">
		        	<div class="col-sm-12">
		                <div class="ibox float-e-margins">
		                    <div class="ibox-content">
		                       	<div class="row">
	                                <label class="col-sm-1 control-label mtop">小区名称:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" id="communityName" name="communityName"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">管家姓名:</label>	
	                                <div class="col-sm-2">
	                                    <input type="text" class="form-control" id="zoName" name="zoName"/>
	                                </div>
	                                <label class="col-sm-1 control-label mtop">管家手机:</label>
		                            <div class="col-sm-2">
                                  		<input id="zoMobile" name="zoMobile" type="text" class="form-control m-b">
	                                </div>
		                        </div>
			                    <div class="row">
		                               <label class="col-sm-1 control-label mtop">国家:</label>
			                           <div class="col-sm-2">
		                                   <select class="form-control m-b m-b" id="nationCode">
		                                   </select>
		                               </div>
		                               <label class="col-sm-1 control-label mtop">省份:</label>
			                           <div class="col-sm-2">
		                                   <select class="form-control m-b " id="provinceCode">
		                                   </select>
		                               </div>
		                               <label class="col-sm-1 control-label mtop">城市:</label>
			                           <div class="col-sm-2">
		                                   <select class="form-control m-b" id="cityCode">
		                                   </select>
		                               </div>
		                               <div class="col-sm-1">
					                       <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
					                   </div>
			                       </div>
		                    </div>
		                </div>
		            </div>
		        </div>
       		    <div class="ibox float-e-margins">
		            <div class="ibox-content">
		                <div class="row row-lg">
		                    <div class="col-sm-12">
		                        <div class="example-wrap">
		                        	<customtag:authtag authUrl="house/houseMgt/updateHousePhyMsg"><button class="btn btn-primary " type="button" data-target="#myModal"  onclick="showHousePhy();"><i class="fa fa-plus"></i>&nbsp;绑定管家</button></customtag:authtag>
		                            <div class="example">
		                                <table id="listTable" class="table table-bordered"  data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-striped="true"
					                    data-page-list="[10,20,50]"
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
					                    data-url="house/houseMgt/showHousePhyMsgList">                    
					                    <thead>
					                        <tr>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="id" data-width="20%" data-radio="true"></th>
					                            <th data-field="communityName" data-width="20%" data-align="center">小区名称</th>
					                            <th data-field="zoName" data-width="30%" data-align="center">管家姓名</th>
					                            <th data-field="zoMobile" data-width="30%" data-align="center">管家手机</th>
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
	   <!-- 添加 菜单  弹出框 -->
	   <div class="modal inmodal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
		   <div class="modal-dialog modal-lm">
		    <div class="modal-content ">
		       <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
		       </div>
		       <div class="modal-body">
		       		<div class="wrapper wrapper-content animated fadeInRight newCommunity" id="searchId" style="display: none;">
		                  <div class="ibox-content">
			                   <div  class="form-group" >
			                   		<label class="col-sm-4">小区名称:</label>
		                   	   		<div class="col-sm-4">
		                   	   			<input id="ScommunityName" name="ScommunityName" class="form-control" type="text" value="">
		                   	   		</div>
		                   	   		<div class="col-sm-4">
		                   	   			 <button type="button" onclick="queryByName();" class="btn btn-white">搜索</button>
		                   	   		</div>
		                   	   	</div>
		                   </div>
			                   
		                   <div class="ibox float-e-margins">
								<div class="example-wrap">
									<div class="example">
										<table class="table" id="searchCommunityName"
											data-toggle="table"
											data-striped="true"
											data-side-pagination="server" data-pagination="true"
											data-page-list="[4]" data-pagination="true"
											data-page-size="4" data-pagination-first-text="首页"
											data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
											data-pagination-last-text="末页"
											data-content-type="application/x-www-form-urlencoded"
											data-query-params="modalPagination" data-method="post"
											data-height="260"
											data-unique-id="fid"
											data-url="house/houseMgt/showHousePhyMsgList">
											<thead>
												 <tr>
						                            <th data-field="fid" data-visible="false"></th>
						                            <th data-field="communityName" data-width="40%" data-align="center">小区名称</th>
						                            <th data-align="center" data-width="60%"  data-formatter="chooseOper">操作</th>
				                        		</tr>
											</thead>
										</table>
									</div>
								</div>
							</div>
					</div>
			        <div class="row row-lg">
			          <div class="col-sm-12">
			               <div class="ibox float-e-margins">
			                   <div class="ibox-content">
				                   <form id="housePhyForm" action="house/houseMgt/updateHousePhyMsg" class="form-horizontal m-t" >
					                  <input id="oldPhyFid" type="hidden" name="oldPhyFid" value="">
					                  <input id="newPhyFid" type="hidden" name="newPhyFid" value="">
					                  <div class="form-group">
					                  		<label class="col-sm-2 control-label">功能选择：</label>
					                  		<div class="col-sm-3">
					                  			<select id="selectfun" class="form-control">
					                  				<option value="1">绑定管家</option>
					                  				<option value="2">替换小区</option>
					                  			</select>
					                  		</div>
					                       <label class="col-sm-2 control-label">小区名称：</label>
					                       <div class="col-sm-3">
					                           <input id="cyName" name="communityName" class="form-control" type="text" value="" readonly="readonly">
					                       </div>
					                  </div>
					                  <div class="form-group">
					                  	   <label class="col-sm-2 control-label newCommunity" style="display: none;">选择新小区：</label>
					                       <div class="col-sm-3 newCommunity" style="display: none;">
					                           <input id="newcyName" name="newcyName" class="form-control" type="text" readonly="readonly" >
					                       </div>
					                       
					                       <label class="col-sm-2 control-label guan">管家姓名：</label>
					                       <div class="col-sm-3 guan">
					                           <input id="zrName" name="zoName" class="form-control" type="text" value="">
					                       </div>
					                       <label class="col-sm-2 control-label guan">管家电话：</label>
					                       <div class="col-sm-3 guan">
					                           <input id="zrMobile" name="zoMobile" class="form-control" type="text" value="">
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
		     <div class="modal-footer">
		         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
		         <button type="button" onclick="updateHousePhy();" class="btn btn-primary" >保存</button>
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
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
	    <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
	
	    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	    
	    <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	
	   <!-- Page-Level Scripts -->
	   <script>
		   $(function(){
				// 多级联动
				var areaJson = ${cityTreeList};
				var defaults = {
					geoJson	: areaJson
				};
				geoCascade(defaults);
				
				//选择显示隐藏
				$("#selectfun").change(function(){
					var value = $(this).val();
					if(value == 1){
						$(".guan").show();
						$(".newCommunity").hide();
						$(".newPhyFid").val("");
					}else{
						$(".guan").hide();
						$(".newCommunity").show();
					}
				});
			});
		   	//列表分页查询
		    function paginationParam(params) {
			    return {
			        limit: params.limit,
			        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
			        nationCode:$.trim($('#nationCode').val()),
			        provinceCode:$.trim($('#provinceCode').val()),
			        cityCode:$.trim($('#cityCode').val()),
			        areaCode:$.trim($('#areaCode').val()),
			        communityName:$.trim($('#communityName').val()),
			        zoName:$.trim($('#zoName').val()),
			        zoMobile:$.trim($('#zoMobile').val())
		    	};
			}
		   	//modal里面的查询分页
		    function modalPagination(params) {
			    return {
			        limit: params.limit,
			        page: $('#searchCommunityName').bootstrapTable('getOptions').pageNumber,
			        communityName:$.trim($("#ScommunityName").val())
		    	};
			}
		   	
		    //查询楼盘信息
		    function paramPage(params){
		    	 return {
			        limit: params.limit,
			        page: $('#searchCommunityName').bootstrapTable('getOptions').pageNumber,
			        communityName:$.trim($('#ScommunityName').val())
			    };
		    }
		    //查询
		    function query(){
		    	$('#listTable').bootstrapTable('selectPage', 1);
		    }
		    function queryByName(){
		    	$('#searchCommunityName').bootstrapTable('selectPage', 1);
		    }
	     	/* 列表 操作 值设置 */
	       	function chooseOper(value, row, index){
	       		return "<button type='button' onclick='getCommunityFid(\""+row.fid+"\")' class='btn btn-primary btn-xs'>选中</button>";
	       	}
	     	//获取fid
		    function getCommunityFid(newPhyFid){
		    	var row = $('#searchCommunityName').bootstrapTable('getRowByUniqueId', newPhyFid);
		    	$("#newPhyFid").val(newPhyFid);
		    	$("#newcyName").val(row.communityName);
		    }
		    //显示弹出框
		    function showHousePhy(){
		    	$("#oldPhyFid").val("");
		    	$("#newPhyFid").val("");
		    	var selectVar=$('#listTable').bootstrapTable('getSelections');
		    	if(selectVar.length==0){
		    		layer.alert("请选择一条记录", {icon: 5,time: 2000, title:'提示'});
		    	}else{
		    		$("#cyName").val(selectVar[0].communityName);
			    	$("#zrName").val(selectVar[0].zoName);
			    	$("#zrMobile").val(selectVar[0].zoMobile);
			    	$("#oldPhyFid").val(selectVar[0].fid);
			    	$('#myModal').modal('toggle');
		    	}
		    }
		    
		    //更新楼盘信息
		    function updateHousePhy(){
		    	$.ajax({
		            type: "POST",
		            url: "house/houseMgt/updateHousePhyMsg",
		            dataType:"json",
		            data: $("#housePhyForm").serialize(),
		            success: function (result) {
		            	if(result.code == 0){
				           	$('#listTable').bootstrapTable('refresh');
				           	$('#myModal').modal('hide');
				           	$("input[type=reset]").trigger("click");
		            	}else{
		            		layer.alert("操作失败", {icon: 5,time: 2000, title:'提示'});
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