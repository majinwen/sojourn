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
						<div class="form-group">
							<div class="row">
								<label class="col-sm-1 control-label mtop">房源编号:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="houseSn" name="houseSn"/>
								</div>
								<label class="col-sm-1 control-label mtop">房间编号:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="roomSn" name="roomSn"/>
								</div>
								<label class="col-sm-1 control-label mtop">房源名称:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control m-b" id="houseName" name="houseName">
								</div>
								<label class="col-sm-1 control-label mtop">房源状态:</label>
								<div class="col-sm-2">
									<select class="form-control m-b" id="houseStatus" name="houseStatus">
										<option value="">请选择</option>
										<c:forEach items="${houseStatusMap }" var="obj">
											<option value="${obj.key }">${obj.value }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row">
                                <label class="col-sm-1 control-label mtop">运营专员姓名:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="zoName" name="zoName"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">运营专员系统号:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="zoCode" name="zoCode"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">运营专员手机号:</label>
	                            <div class="col-sm-2">
                                 		<input type="text" class="form-control m-b" id="zoMobile" name="zoMobile">
                                </div>
                                <label class="col-sm-1 control-label mtop">出租类型:</label>
	                            <div class="col-sm-2">
                                 	<select class="form-control m-b" id="rentWay" name="rentWay">
	                                    <option value="">请选择</option>
	                                    <c:forEach items="${rentWayMap }" var="obj">
	                                    	<c:if test="${obj.key ==0 }"><option value="${obj.key }" selected="selected" >${obj.value }</option></c:if>
	                                    	<c:if test="${obj.key !=0 }"><option value="${obj.key }" >${obj.value }</option></c:if>
	                                    </c:forEach>
                                    </select>
                                </div>
	                        </div>
							<div class="row">
                                <label class="col-sm-1 control-label mtop">房东姓名:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="landlordName" name="landlordName"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">房东昵称:</label>	
                                <div class="col-sm-2">
                                    <input type="text" class="form-control m-b" id="landlordNickname" name="landlordNickname"/>
                                </div>
                                <label class="col-sm-1 control-label mtop">房东手机:</label>
	                            <div class="col-sm-2">
                                 		<input type="text" class="form-control m-b" id="landlordMobile" name="landlordMobile">
                                </div>
								<label class="col-sm-1 control-label mtop">房源渠道:</label>
								<div class="col-sm-2">
									<select class="form-control m-b" id="houseChannel" name="houseChannel">
										<option value="">请选择</option>
										<option value="1">直营</option>
										<option value="2">房东</option>
										<option value="3">地推</option>
									</select>
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
	                            <label class="col-sm-1 control-label mtop">区域:</label>
		                        <div class="col-sm-2">
	                                <select class="form-control m-b" id="areaCode">
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
		</div>
		<!-- Panel Other -->
		<div class="ibox float-e-margins">
			<div class="ibox-content">
				<div class="row row-lg">
					<div class="col-sm-12">
						<!-- Example Pagination -->
						<customtag:authtag authUrl="house/houseGuard/editHouseGuard">
						    <button class="btn btn-info" type="button" onclick="editHouseGuard();"><i class="fa fa-edit"></i>&nbsp;编辑</button>
						</customtag:authtag>
						<div class="example-wrap">
							<div class="example">
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
				                    data-single-select="false"
				                    data-height="496"
				                    data-classes="table table-hover table-condensed"
				                    data-url="house/houseGuard/showHouseGuard">                    
				                    <thead>
				                        <tr>
				                            <th data-field="fid" data-visible="false"></th>
				                            <th data-field="id" data-width="2%" data-checkbox="true"></th>
				                            <th data-field="houseSn" data-width="100px" data-align="center" data-formatter="formateHouseSn">房源编号</th>
				                            <th data-field="roomSn" data-width="100px" data-align="center" data-formatter="formateRoomSn">房间编号</th>
				                            <th data-field="houseName" data-width="200px" data-align="center">房源名称</th>
				                            <th data-field="houseAddr" data-width="200px" data-align="center">房源地址</th>
				                            <th data-field="landlordName" data-width="100px" data-align="center">房东姓名</th>
				                            <th data-field="landlordNickname" data-width="100px" data-align="center">房东昵称</th>
				                            <th data-field="landlordMobile" data-width="100px" data-align="center">房东手机</th>
				                            <!-- <th data-field="empPushName" data-width="100px" data-align="center">地推管家</th>
				                            <th data-field="empPushCode" data-width="100px" data-align="center">系统号</th>
				                            <th data-field="empPushMobile" data-width="100px" data-align="center">管家手机</th> -->
				                            <th data-field="empGuardName" data-width="100px" data-align="center">运营专员</th>
				                            <th data-field="empGuardCode" data-width="100px" data-align="center">专员系统号</th>
				                            <th data-field="empGuardMobile" data-width="100px" data-align="center">专员手机号</th>
											<th data-field="houseChannel" data-width="150px" data-align="center" data-formatter="formateHouseChannel">房源渠道</th>
				                            <th data-field="houseStatus" data-width="150px" data-align="center" data-formatter="formateHouseStatus">房源状态</th>
				                        </tr>
				                    </thead>
				                    </table>             
	                            </div>
							</div>
						</div>
						<!-- End Example Pagination -->
					</div>
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
	
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/house/houseCommon.js${VERSION}"></script>
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
		});
		
	    function paginationParam(params) {
		    return {
		        limit: params.limit,
		        page:$('#listTable').bootstrapTable('getOptions').pageNumber,
		        zoName:$.trim($('#zoName').val()),
		        zoCode:$.trim($('#zoCode').val()),
		        zoMobile:$.trim($('#zoMobile').val()),
		        landlordName:$.trim($('#landlordName').val()),
		        landlordNickname:$.trim($('#landlordNickname').val()),
		        landlordMobile:$.trim($('#landlordMobile').val()),
		        nationCode:$('#nationCode option:selected').val(),
		        provinceCode:$('#provinceCode option:selected').val(),
		        cityCode:$('#cityCode option:selected').val(),
		       	areaCode:$('#areaCode option:selected').val(),
		        houseSn:$.trim($('#houseSn').val()),
		        houseName:$.trim($('#houseName').val()),
		        houseStatus:$('#houseStatus option:selected').val(),
		        rentWay:$('#rentWay option:selected').val(),
				houseChannel:$('#houseChannel option:selected').val(),
		        roomSn:$('#roomSn').val()
	    	};
		}

	    $('#rentWay').change(function(){
	    	if($(this).children('option:selected').val()==1){
	    		$('#houseStatus').parent().prev().html("房间状态");
	    		
	    	}else{
	    		$('#houseStatus').parent().prev().html("房源状态");
	    	}
	    	
	    }) 
	    
	    function query(){
	    	$('#listTable').bootstrapTable('selectPage', 1);
	    }
		
		// 房源编号
		function formateHouseSn(value, row, index){
			 return "<a href='javascript:;' class='oneline line-width' title=\""+value+"\" <customtag:authtag authUrl='house/houseGuard/houseGuardDetail'>onclick='showHouseDetail(\""+row.houseFid+"\");'</customtag:authtag>>"+value+"</a>";
		}
		
		// 房间编号
		function formateRoomSn(value, row, index){
			var htm='-';
			if(value!=null && value!=''){
				htm='';
				var pairs = value.split(",");
				for(var i = 0;i <pairs.length; i++) {
					var pair = pairs[i];
					var params = pair.split("|");
					var roomSn = params[0];
					var roomFid = params[1];
					var roomStatus = params[2];
					roomStatus = formateHouseStatus(roomStatus, null, null);
					if(htm==''){ 
						htm = "<a href='javascript:;' class='oneline line-width' title=\""+roomSn+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showRoomDetail(\""+roomFid+"\");'</customtag:authtag>>"+roomSn+"("+roomStatus+")"+"</a>";	
					}else{
						htm = htm+","+"<a href='javascript:;' class='oneline line-width' title=\""+roomSn+"\" <customtag:authtag authUrl='house/houseMgt/houseDetail'>onclick='showRoomDetail(\""+roomFid+"\");'</customtag:authtag>>"+roomSn+"("+roomStatus+")"+"</a>";	
					}
				} 
			}
			return htm;
		}
		
		//展示房间详情
		function showRoomDetail(houseRoomFid){
			$.openNewTab(new Date().getTime(),'house/houseMgt/houseDetail?rentWay=1&houseFid='+houseRoomFid, "房间详情");
		}

		// 1:直营, 2:房东, 3:地推
		function formateHouseChannel(value, row, index){
			var houseChannel = "";
			if(value == 1){
				houseChannel = "直营";
			}else if(value == 2){
				houseChannel = "房东";
			}else if(value == 3){
				houseChannel = "地推";
			}
			return houseChannel;
		}

		// 房源状态
		function formateHouseStatus(value, row, index){
			var houseStatusJson = ${houseStatusJson };
			var result = '';
			$.each(houseStatusJson,function(i,n){
				if(value == i){
					result = n;
					return false;
				}
			});
			return result;
		}
		
		// 展示房源专员关系详情
		function showHouseDetail(houseBaseFid){
			$.openNewTab(new Date().getTime(),'house/houseGuard/houseGuardDetail?houseBaseFid='+houseBaseFid, "房源专员关系详情");
		}
		
		function editHouseGuard(){
			var rows = $('#listTable').bootstrapTable('getSelections');
			if (rows.length == 0) {
				layer.alert("至少选择一条记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			var flag = checkIsSameArea(rows);
			if(!flag){
				layer.alert("请选择相同区域记录进行操作", {
					icon : 6,
					time : 2000,
					title : '提示'
				});
				return;
			}
			
			var url = "house/houseGuard/editHouseGuard";
			$.each(rows,function(i,row){
				if(i == 0){
					url += "?listHouseFid[0]="+row.houseFid;
				}else{
					url += "&listHouseFid["+i+"]="+row.houseFid;
				}
			});
			$.openNewTab(new Date().getTime(),url, "修改房源专员");
		}
		
		// 判断区域是否相同
		function checkIsSameArea(rows){
			var flag = true;
			var areaCode = rows[0].areaCode;
			$.each(rows,function(i,row){
				flag = areaCode === row.areaCode;
				return flag;
			});
			return flag;
		}
	</script>
</body>
</html>