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
<link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
<link href="${staticResourceUrl}/css/plugins/blueimp/css/blueimp-gallery.min.css${VERSION}" rel="stylesheet">
<style>
.modal-lm{min-width:880px;} 
.lightBoxGallery img {
	margin: 5px;
	width: 160px;
}
.row{margin:0;}
#operList td{font-size:12px;}
</style>
</head>
<body class="gray-bg">
	<!-- 图片弹出层 -->
	<div id="blueimp-gallery" class="blueimp-gallery">
		<div class="slides"></div>
		<h3 class="title"></h3>
		<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a> <a
			class="play-pause"></a>
		<ol class="indicator"></ol>
	</div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="col-sm-12">
			<div class="ibox float-e-margins">
				<div class="ibox-content">
					<form class="form-group">
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">管家姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="dtGuardName" name="dtGuardName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">管家系统号:</label>
							</div>
							<div class="col-sm-2">
								<input id="dtGuardCode" name="dtGuardCode" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">管家手机号:</label>
							</div>
							<div class="col-sm-2">
								<input id="dtGuardMobile" name="dtGuardMobile" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row m-b">
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房东姓名:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordName" name="landlordName" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">房东手机号:</label>
							</div>
							<div class="col-sm-2">
								<input id="landlordMobile" name="landlordMobile" type="text"
									class="form-control">
							</div>
							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">地推进度:</label>
							</div>
							<div class="col-sm-2">
								<select class="form-control" name="businessPlan" id="businessPlan">
									  <option value="">请选择</option>
	                           	   	  <option value="0">已获取房源信息</option>
	                           	   	  <option value="1">已获取联系方式</option>
	                           	   	  <option value="2">已沟通意向</option>
	                           	   	  <option value="3">已发布</option>
	                           	   	  <option value="4">管家驳回</option>
	                           	   	  <option value="5">品质驳回</option>
	                           	   	  <option value="6">已上架</option>
	                           	   	  <option value="7">已线下核验</option>
	                           	   	  <option value="8">已拍照</option>
								</select>
							</div>
						</div>
						<div class="row m-b">
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">房源商机编号:</label>
                            </div>
                            <div class="col-sm-2">
								<input id="busniessSn" name="busniessSn" type="text"
									class="form-control">
							</div>

							<div class="col-sm-1 text-right">
								<label class="control-label"
									style="height: 35px; line-height: 35px;">详细地址:</label>
							</div>
							<div class="col-sm-3">
								<input id="houseAddr" name="houseAddr" type="text"
									class="form-control">
							</div>
						</div>
						<div class="row">
                             <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">国家:</label>
                            </div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b m-b" id="nationCode">
                                </select>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">省份:</label>
                            </div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b " id="provinceCode">
                                </select>
                            </div>
                            <div class="col-sm-1 text-right">
                                <label class="control-label"
                                       style="height: 35px; line-height: 35px;">城市:</label>
                            </div>
	                        <div class="col-sm-2">
                                <select class="form-control m-b" id="cityCode">
                                </select>
                            </div>
                           	<div class="col-sm-2">
			                    <button class="btn btn-primary" type="button" onclick="query();"><i class="fa fa-search"></i>&nbsp;查询</button>
			                </div>
	                    </div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="ibox float-e-margins">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<customtag:authtag authUrl="house/houseBusiness/houseBusinessAdd"><button class="btn btn-primary" type="button" onclick="toHouseBusinessAdd();"><i class="fa fa-save"></i>&nbsp;添加</button></customtag:authtag>
					<div class="example-wrap">
						<div class="example">
							<table class="table table-bordered" id="listTable"
								data-click-to-select="true" data-toggle="table"
								data-side-pagination="server" data-pagination="true"
								data-page-list="[10,20,50]" data-pagination="true"
								data-page-size="10" data-pagination-first-text="首页"
								data-pagination-pre-text="上一页" data-pagination-next-text="下一页"
								data-pagination-last-text="末页"
								data-content-type="application/x-www-form-urlencoded"
								data-query-params="paginationParam" data-method="post"
								data-height="520"
								data-single-select="true" data-url="house/houseBusiness/showHouseBusinessList">
								<thead>
									<tr>
										<th data-field="fid" data-visible="false"></th>
										<th data-field="busniessSn" data-width="15%" data-align="center"  data-formatter="formateBusniessSn">房源商机编号</th>
										<th data-field="houseAddr" data-width="15%" data-align="center">房源地址</th>
										<th data-field="landlordName" data-width="10%" data-align="center">房东姓名</th>
										<th data-field="landlordMobile" data-width="10%" data-align="center">房东手机号</th>
										<th data-field="createDate" data-width="15%" data-align="center"  data-formatter="formateDate">录入时间</th>
										<th data-field="dtGuardName" data-width="5%" data-align="center">地推管家姓名</th>
                                        <th data-field="dtGuardCode" data-width="5%" data-align="center">地推管家系统号</th>
										<th data-field="dtGuardMobile" data-width="10%" data-align="center" >地推管家手机号</th>
										<th data-field="operate" data-width="15%" data-align="center" data-formatter="formateOperate">操作</th>
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
	<!-- 全局js -->
	<script src="${staticResourceUrl}/js/jquery.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/bootstrap.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/commonUtils.js" type="text/javascript"></script>
	<!-- Bootstrap table -->
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js?${VERSION}"></script>
    <script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/plugins/layer/layer.min.js?${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}"></script>
	<!-- 照片显示插件 -->
	<script src="${staticResourceUrl}/js/plugins/blueimp/jquery.blueimp-gallery.min.js${VERSION}"></script>
	<!-- Page-Level Scripts -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	<!-- 时间日期操作 -->
	<script src="${staticResourceUrl}/js/minsu/common/date.proto.js${VERSION}"></script>
	
	
	<script>
		$(function(){
  			// 多级联动
  			var areaJson = ${cityTreeList};
  			var defaults = {
				geoJson	: areaJson
  			};
  			geoCascade(defaults);
		})
		 // 新增用户
		function toHouseBusinessAdd(){
			$.openNewTab("toHouseBusinessAdd","house/houseBusiness/houseBusinessAdd", "新增商机");
		}
		 
		//分页查询参数
		function paginationParam(params) {
			return {
				limit : params.limit,
				page : $('#listTable').bootstrapTable('getOptions').pageNumber,
				dtGuardName:$.trim($('#dtGuardName').val()),
				dtGuardCode:$.trim($('#dtGuardCode').val()),
				dtGuardMobile:$.trim($('#dtGuardMobile').val()),
				landlordName:$.trim($('#landlordName').val()),	
				landlordMobile:$.trim($('#landlordMobile').val()),
				businessPlan:$.trim($('#businessPlan').val()),			
				busniessSn:$.trim($('#busniessSn').val()),			
				houseAddr:$.trim($('#houseAddr').val()),			
				nationCode:$.trim($('#nationCode').val()),			
				provinceCode:$.trim($('#provinceCode').val()),
				cityCode:$.trim($('#cityCode').val())
			}
		}
		
		//条件查询
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		// 房源商机编号
		function formateBusniessSn(value, row, index){
			 return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseBusiness/showHouseBusinessDetail'>onclick='showHouseBusinessDetail(\""+row.fid+"\");'</customtag:authtag>>"+value+"</a>";
		}
		//展示房源商机详情
		function showHouseBusinessDetail(businessFid){
			$.openNewTab(new Date().getTime(),'house/houseBusiness/showHouseBusinessDetail?businessFid='+businessFid, "商机详情");
		}
		
		//操作
		function formateOperate(value, row, index){
			return "<a href='javascript:;' <customtag:authtag authUrl='house/houseBusiness/houseBusinessUpdate'>onclick='houseBusinessUpdate(\""+row.fid+"\");'</customtag:authtag>><span>修改</span></a>  "
			+ "<a href='javascript:;' <customtag:authtag authUrl='house/houseBusiness/houseBusinessUpdate'>onclick='houseBusinessSpecialUpdate(\""+row.fid+"\");'</customtag:authtag>><span>特殊修改</span></a> "
			+" <a href='javascript:;' <customtag:authtag authUrl='house/houseBusiness/delHouseBusiness'>onclick='delHouseBusiness(\""+row.fid+"\");'</customtag:authtag>><span>删除</span></a>";
		}
		
		//修改房源商机页
		function houseBusinessUpdate(businessFid){
			$.openNewTab(new Date().getTime(),'house/houseBusiness/houseBusinessUpdate?flag=0&businessFid='+businessFid, "修改商机");
		}
		
		//特殊修改
		function houseBusinessSpecialUpdate(businessFid){
			$.openNewTab(new Date().getTime(),'house/houseBusiness/houseBusinessSpecialUpdate?flag=1&businessFid='+businessFid, "修改商机");
		}
		
		//删除商机
		function delHouseBusiness(businessFid){
			layer.confirm("确定要删除吗?", {icon: 5, title:'提示'}, function(index) {
				$.ajax({
		            type: "POST",
		            url: "house/houseBusiness/delHouseBusiness",
		            dataType:"json",
		            data: {"businessFid":businessFid},
		            success: function (result) {
		            	if(result.code==0){
		            		$('#listTable').bootstrapTable('selectPage', $('#listTable').bootstrapTable('getOptions').pageNumber);
		            	}else{
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		        });
				layer.close(index);
			})
		}
		//格式化录入时间
		function formateDate(value, row, index){
			if (value != null) {
				var vDate = new Date(value);
				return vDate.format("yyyy-MM-dd HH:mm:ss");
			} else {
				return "";
			}
		}
	</script>
</body>
</html>
