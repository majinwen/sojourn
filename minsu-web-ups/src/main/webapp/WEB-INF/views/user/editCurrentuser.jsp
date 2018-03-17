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

    <link rel="shortcut icon" href="${staticResourceUrl}/favicon.ico">
    <link href="css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="css/font-awesome.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="css/bootstrap.min.css${VERSION}001" rel="stylesheet">
    <link href="css/plugins/iCheck/custom.css${VERSION}001" rel="stylesheet">
    <link href="css/animate.css${VERSION}001" rel="stylesheet">
    <link href="css/style.css${VERSION}001" rel="stylesheet">
 </head>
  
  <body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>添加用户</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="form_basic.html#">选项1</a>
                                </li>
                                <li><a href="form_basic.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <form action="user/updateCurrentuser" class="form-horizontal" method="post" id="userSaveForm" onsubmit="document.charset='utf-8'" >
                        <input type="hidden" name="fid" value="${userInfo.fid }">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">账户名称</label>

                                <div class="col-sm-2">
                                    <input type="text" class="form-control"  name="userAccount" value="${userInfo.userAccount }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">密码</label>

                                <div class="col-sm-2">
                                    <input type="password" class="form-control" name="userPassword" value="${userInfo.userPassword }">
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">员工选择</label>

                                <div class="col-sm-2">
                                <div class="input-group">
                                     <input type="hidden" class="form-control" name="employeeFid"  id="empId" value="${userInfo.employeeFid}">
                                     <input type="text" class="form-control" name="empName"  id="empName" value="${userInfo.fullName }">
                                     <span class="input-group-btn">
                                     <button type="button" class="btn btn-white" data-toggle="modal" data-target="#myModal5">
                                    	员工选择
                                	</button>
                                	</span>
                                </div>
                                </div>  
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                               <label class="col-sm-2 control-label">账号行政区域选择</label>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="nationCode"  id="nationCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="provinceCode"  id="provinceCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
	                           <div class="col-sm-2">
                                   <select class="form-control m-b" name="cityCode"  id="cityCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
                               <div class="col-sm-2">
                                   <select class="form-control m-b" name="areaCode"  id="areaCode">
                                       <option>请选择</option>
                                   </select>
                               </div>
                               <button class="btn btn-primary" type="button" onclick="addCityCode();"><i class="fa fa-save"></i>&nbsp;添加</button>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-2 control-label">负责区域</label>
                               <div id="areaPiece">
                               <c:forEach items="${userInfo.cityList }" var="city">
                               <div class='col-sm-2'><p class='form-control-static'>${city.nationName}<c:if test="${city.provinceName!=null && city.provinceName!=''}">,${city.provinceName}</c:if><c:if test="${city.cityName!=null &&city.cityName!='' }">,${city.cityName}</c:if><c:if test="${city.areaName!=null && city.areaName!='' }">,${city.areaName}</c:if>
                               <input type='hidden' class='form-control codeHidden' name='areaCodeList' value="${city.nationCode}<c:if test="${city.provinceCode!=null && city.provinceCode!=''}">:${city.provinceCode}</c:if><c:if test="${city.cityCode!=null && city.cityCode!=''}">:${city.cityCode}</c:if><c:if test="${city.areaCode!=null && city.areaCode!=''}">:${city.areaCode}</c:if>" />
                               &nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-white' onclick='deleteCodeDiv(this)'' ><font style='color:red'>删除</font></button></p></div>
                               </c:forEach>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    	是否管理员
                                </label>
                                <div class="radio i-checks">
                                        <label>
                                            <input type="radio" value="0" name="isAdmin" ${userInfo.isAdmin==0 ?'checked':''}> <i></i>否</label>
                                        <label>
                                            <input type="radio" value="1" name="isAdmin" ${userInfo.isAdmin==1 ?'checked':''}> <i></i>是</label>   
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                	<button type="button" class="btn btn-white" data-toggle="modal" data-target="#roleModel">角色选择</button>
								</label>

                                <div class="col-sm-10" id="roleDiv">
                                    <div name="selectedDiv" class="col-sm-2">
		                                 <c:forEach items="${userInfo.roles}" var="role" varStatus="rowIndex">
			                               <div class="input-group"><input type="hidden" class="form-control" name="roleFidList" value="${role.fid }" />
			                               <input type="text" class="form-control" name="empName" value="${role.roleName}" />
			                               <span class="input-group-btn"><button type="button" class="btn btn-white" onclick="deleteRoleDiv(this)" ><font style="color:red">删除</font></button></span>
			                               </div>
		                             </c:forEach>
	                               </div>
                               </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <div class="col-sm-4 col-sm-offset-2">
                                    <button class="btn btn-primary" type="submit">保存内容</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹出框员工 -->
    <div class="modal inmodal fade" id="myModal5" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">员工列表</h4>
                 </div>
                 <div class="ibox-content">
                     <div class="row" >
							<div class="form-group">
								<div class="row" style="margin-left: 10px;">
									<label class="col-sm-2 control-label mtop">员工编号:</label>
									<div class="col-sm-3">
										<input id="empCodeS" name="empCodeS" type="text"
											class="form-control">
									</div>
								    <label class="col-sm-2 control-label mtop">员工姓名:</label>
									<div class="col-sm-3">
										<input id="empNameS" name="empNameS" type="text"
											class="form-control">
									</div>
									<div class="col-sm-2">
										<button class="btn btn-primary" type="button"
											onclick="queryEmp();">
											<i class="fa fa-search"></i>&nbsp;查询
										</button>
									</div>
								</div>
							</div>
					 </div>
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="listTable" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[1,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-single-select="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="paginationParam"
					                    data-content-type="application/x-www-form-urlencoded"
					                    data-method="post" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="500"
					                    data-url="user/employeeList">
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-radio="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
					                            <th data-field="empCode">人员编号</th>
					                            <th data-field="empName">人员姓名</th>
					                            <th data-field="empSex">性别</th>
					                            <th data-field="empMobile">移动电话</th>
					                            <th data-field="empMail">电子邮箱</th>
					                            <th data-field="postName">部门</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="getSelectEmployee()" data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
     </div>
     
    <!-- 弹出框角色 -->
    <div class="modal inmodal fade" id="roleModel" tabindex="-1" role="dialog"  aria-hidden="true">
         <div class="modal-dialog">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                     <h4 class="modal-title">角色列表</h4>
                 </div>
                 <div class="ibox-content">
                 	  <div class="row" >
							<div class="form-group">
								<div class="row" style="margin-left: 10px;">
									<label class="col-sm-2 control-label mtop">角色类型:</label>
									<div class="col-sm-3">
										<select class="form-control m-b" name="roleType" id="roleType">
											<option value="">请选择</option>
											<option value="0">普通角色</option>
											<option value="1">数据区域角色</option>
											<option value="2">区域角色</option>
											<option value="3">数据角色</option>
										</select>
									</div>
								    <label class="col-sm-2 control-label mtop">系统:</label>
									<div class="col-sm-3">
										<select class="form-control m-b" name="systemFid"  id="systemFid">
											<option value="">请选择</option>
											<c:forEach items="${sysList }" var="sys">
											<option value="${sys.fid }">${sys.sysName }</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-2">
										<button class="btn btn-primary" type="button"
											onclick="query();">
											<i class="fa fa-search"></i>&nbsp;查询
										</button>
									</div>
								</div>
							</div>
					 </div>
	                 <div class="row row-lg">
	                    <div class="col-sm-12">
	                        <div class="example-wrap">
	                            <div class="example">
				                      <!-- 弹出框列表 -->
				                      <table id="roleTable" class="table table-hover table-bordered" data-click-to-select="true"
					                    data-toggle="table"
					                    data-side-pagination="server"
					                    data-pagination="true"
					                    data-page-list="[1,20,50]"
					                    data-pagination="true"
					                    data-page-size="10"
					                    data-show-refresh="true"
					                    data-pagination-first-text="首页"
					                    data-pagination-pre-text="上一页"
					                    data-pagination-next-text="下一页"
					                    data-pagination-last-text="末页"
					                    data-query-params="rolePaginationParam"
					                    data-content-type="application/x-www-form-urlencoded"
					                    data-method="post" 
					                    data-classes="table table-hover table-condensed"
					                    data-height="400"
					                    data-url="user/showRoles">
					                    <thead>
					                        <tr>
					                        	<th data-field="id" data-checkbox="true"></th>
					                            <th data-field="fid" data-visible="false"></th>
						                        <th data-field="roleName" data-align="center">名称</th>
					                        </tr>
					                    </thead>
					                    </table>
					              </div>
					          </div>
					       </div>
					  </div>
			     </div>      
                 <div class="modal-footer">
                     <button type="button" class="btn btn-primary" onclick="getSelectRole()"  data-dismiss="modal">保存</button>
                 </div>
             </div>
         </div>
     </div>
    

    <!-- 全局js -->
    <script src="js/jquery.min.js${VERSION}"></script>
    <script src="js/bootstrap.min.js${VERSION}"></script>

    <!-- 自定义js -->
    <script src="js/content.js${VERSION}"></script>

    <!-- iCheck -->
    <script src="js/plugins/iCheck/icheck.min.js${VERSION}"></script>
    
    <!-- Bootstrap table -->

    <script src="js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}"></script>
    <script src="js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}"></script>
    <script src="js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}"></script>
    <script src="js/minsu/common/geography.cascade.js${VERSION}"></script>
        <script src="js/plugins/validate/jquery.validate.min.js${VERSION}001"></script>
    <script src="js/plugins/validate/messages_zh.min.js${VERSION}001"></script>
    <script src="js/minsu/common/custom.js?${VERSION}"></script>
    <script src="js/plugins/layer/layer.min.js${VERSION}"></script>

    <script type="text/javascript">
    	 $(document).ready(function () {
			// 多级联动
			var areaJson = ${cityTreeList};
			var defaults = {
				geoJson	: areaJson,
				nationCode: '${userInfo.nationCode}',
				provinceCode: '${userInfo.provinceCode}',
				cityCode: '${userInfo.cityCode}'
			};
			geoCascade(defaults);
			
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    	 
    	 
        /*员工列表参数*/
    	function paginationParam(params) {
		    return {
		        limit: params.limit,
		        offset: params.offset,
		        page: $('#listTable').bootstrapTable('getOptions').pageNumber,
		        empCode : $.trim($('#empCodeS').val()),
		        empName : $.trim($('#empNameS').val())
	    	};
		}
		/*搜索*/
		function queryEmp() {
			$('#listTable').bootstrapTable('selectPage', 1);
		}
		/*选择员工*/
		function getSelectEmployee(){
			var selectVar=$('#listTable').bootstrapTable('getSelections');
			$("#empName").val(selectVar[0].empName);
			$("#empId").val(selectVar[0].fid);
		}
		/*角色列表参数*/
	   function rolePaginationParam(params) {
		    return {
		        limit: params.limit,
		        page: $('#roleTable').bootstrapTable('getOptions').pageNumber,
				systemFid : $.trim($('#systemFid').val()),
				roleType : $.trim($('#roleType').val())
	    	};
		}
		/*搜索*/
		function query() {
			$('#roleTable').bootstrapTable('selectPage', 1);
		}
		/*选择角色*/
		function getSelectRole(){
			var html = '<div name="selectedDiv" class="col-sm-2">';
			var selectVar=$('#roleTable').bootstrapTable('getSelections');
			var selectedIdArray = $("#roleDiv").find("input[name='roleFidList']");
			var selectedIdStr = $(selectedIdArray).map(function(){return $(this).val();}).get().join(", "); 
			
			$(selectVar).each(function(index,obj){
				if(!selectedIdStr || (!!selectedIdStr && selectedIdStr.indexOf(obj.fid) == -1)){
					html += '<div class="input-group"><input type="hidden" class="form-control" name="roleFidList" value="'+obj.fid+'" />';
					html += '<input type="text" class="form-control" name="empName" value="'+obj.roleName+'" />';
					html += '<span class="input-group-btn"><button type="button" class="btn btn-white" onclick="deleteRoleDiv(this)" ><font style="color:red">删除</font></button></span>'
					html += '</div>'
				}
			});
			
			html += '</div>';
			$("#roleDiv").append(html);
		}
		/*删除选择角色*/
		function deleteRoleDiv(obj){
			//删除选中人的div
			$(obj).parent().parent().remove();
			//删除 <div name="selectedDiv" class="col-sm-2">子节点为空的div
			var divArry = $("#roleDiv").find("div[name='selectedDiv']");
			$(divArry).each(function(index,obj){
				if($(obj).children().length<=0){
					$(obj).remove();
				}
			})
		}
		
		/*添加负责区域*/
		function addCityCode(){
			var cityName="";
			var cityCode="";
			if($("#nationCode").val()!=''){
				cityCode=$("#nationCode").val();
				cityName=$("#nationCode").find("option:selected").text();
			}else{
				return;
			}
			if($("#provinceCode").val()!=''){
				cityCode=cityCode+":"+$("#provinceCode").val();
				cityName=cityName+","+$("#provinceCode").find("option:selected").text();
			}
			if($("#cityCode").val()!=''){
				cityCode=cityCode+":"+$("#cityCode").val();
				cityName=cityName+","+$("#cityCode").find("option:selected").text();
			}
			if($("#areaCode").val()!=''){
				cityCode=cityCode+":"+$("#areaCode").val();
				cityName=cityName+","+$("#areaCode").find("option:selected").text();
			}
			var isPass=true;
			$(".codeHidden").each(function(){
				console.log($(this).val());
				console.log(cityCode);
				if($(this).val()==cityCode){
					isPass=false;
					return false;
				}
			});
			if(!isPass){
				return;
			}
			var areahtml=$("#areaPiece").html()
			var areaDiv="<div class='col-sm-2'><p class='form-control-static'>"+cityName+"<input type='hidden' class='form-control codeHidden' name='areaCodeList' value="+cityCode+" />"
			+"&nbsp;&nbsp;&nbsp;<button type='button' class='btn btn-white' onclick='deleteCodeDiv(this)'' ><font style='color:red'>删除</font></button></p>"+
			"</div>"
			$("#areaPiece").html(areahtml+areaDiv);
		}
		/*删除负责区域*/
		function deleteCodeDiv(obj){
			$(obj).parent().parent().remove();
		}
		/*异步保存*/
		$("#userSaveForm").validate({
		    rules: {
		    	userAccount: {
		          required: true
		        }
		    },
			submitHandler:function(){
				$.ajax({
		            type: "POST",
		            url: "user/updateCurrentuser",
		            dataType:"json",
		            data: $("#userSaveForm").serialize(),
		            success: function (result) {
		            	if(result.code==0){
							$.callBackParent("user/currentuserList",true,saveUserCallback);
		            	}else{
		            		layer.alert(result.msg, {icon: 5,time: 2000, title:'提示'});
		            	}
		            },
		            error: function(result) {
		            	layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
		            }
		      });
		}});
		//保存商机刷新父页列表
		function saveUserCallback(panrent){
			panrent.refreshData("listTable");
		}
    </script>
</body>
</html>
