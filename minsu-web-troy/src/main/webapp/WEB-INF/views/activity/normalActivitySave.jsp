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
	<link href="${staticResourceUrl}/css/plugins/jsTree/style.min.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
    
    <style type="text/css">
      .gift-form-control {
    background-color: #FFFFFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    padding: 6px 7px;
    -webkit-transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
    width:80px;
    font-size: 14px;
    text-align:center;
    }
    .checkbox input[type=checkbox]{
	    margin-top: 2px;
	}
	.checkbox>.checkBoxTit{
		font-weight:bold;
	}
	.gift_lable {
     display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    font-weight: 700;
    height:34px;
    padding-top:16px;
    }
    #allCheckBox{
		text-align: center;
    }
    #allCheckBox>label{
    	font-weight:bold;
    }
    .form-horizontal .checkbox{
    	height:50px;
    }

	  .row-top {
		  margin-top: 15px;
	  }
    </style>
</head>
 <body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight">
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-content">
			           <form id="activityForm" action="" class="form-horizontal" onsubmit="document.charset='utf-8'" method="post" >
			           		<input type="hidden" id="actKind" name="actKind" value="2">
			           		<input type="hidden" id="giftInfo" name="giftInfo" value="">
							<div class="form-group">
								<div class="row">
									<label class="col-sm-1 control-label mtop">业务线:</label>
									<div class="col-sm-2">
										<select id="serviceLine" name="serviceLine" class="form-control">
											<option value="1">民宿</option>
											<option value="2">公寓</option>
										</select>
									</div>
								</div>

								<%--基本信息--%>
								<div class="hr-line-dashed"></div>
								<div class="row">
									<div class="col-sm-1 text-right">
										<h2><strong>基本信息</strong></h2>
									</div>
								</div>
								<div class="row row-top">
									<label class="col-sm-1 control-label"><span
											class="text-danger">*</span>活动名称:</label>
									<div class="col-sm-2">
										<input id="actName" name="actName" value="" type="text" class="form-control"
											   maxlength="20">
									</div>
								</div>

								<div class="row row-top zra-show">
									<label class="col-sm-1 control-label">减免项:</label>
									<div class="col-sm-2">
										<select id="actCostType" name="actCostType" class="form-control">
											<option value="1">服务费</option>
										</select>
									</div>


									<label class="col-sm-1 control-label"><span
											class="text-danger">*</span>活动类型:</label>
									<div class="col-sm-1">
										<select id="actTypeSelect" class="form-control" name="actType" required>
											<option value="">请选择</option>
											<option value="2">折扣</option>
											<option value="21">满减(天)</option>
										</select>
									</div>

									<%--折扣--%>
									<div class="discount2" style="display: none">
										<div class="col-sm-1">
                                            <div class="col-sm-10"><input type="text" id="discount_actcut"
                                                                          name="actCut"
                                                                          class="form-control"
                                                                          onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                          onkeypress="return event.keyCode>=48&&event.keyCode<=57"
                                                                          ng-pattern="/[^a-zA-Z]/"/></div>
											<div class="col-sm-2 control-label">%</div>
										</div>
										<div class="col-sm-2">
											<div class="col-sm-4 control-label">最高优惠</div>
											<div class="col-sm-8"><input type="text" id="discount_actmax" name="actMax"
                                                                         onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                         onkeypress="return event.keyCode>=48&&event.keyCode<=57"
                                                                         ng-pattern="/[^a-zA-Z]/"
                                                                         class="form-control"/></div>
										</div>
									</div>

									<%--满减--%>
									<div class="cut21" style="display: none">
										<div class="col-sm-1">
											<div class="col-sm-2 control-label">满</div>
											<div class="col-sm-10"><input type="text" id="cut_actlimit" name="actLimit"
                                                                          class="form-control"
                                                                          onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                          onkeypress="return event.keyCode>=48&&event.keyCode<=57"
                                                                          ng-pattern="/[^a-zA-Z]/"/></div>
										</div>
										<div class="col-sm-1">
											<div class="col-sm-2 control-label">减</div>
											<div class="col-sm-10"><input type="text" id="cut_actcut" name="actCut"
                                                                          class="form-control"
                                                                          onkeyup="this.value=this.value.replace(/\D/g,'')"
                                                                          onkeypress="return event.keyCode>=48&&event.keyCode<=57"
                                                                          ng-pattern="/[^a-zA-Z]/"/></div>
										</div>
									</div>
								</div>
                                <%--民宿活动--%>
								<div class="row row-top minsu-show">
									<label class="col-sm-1 control-label">活动类型:</label>
									<div class="col-sm-2">
										<select id="actType" name="actType" class="form-control"
												class="{required:true}">
											<option value="20">免佣金活动</option>
											<option value="23">返现活动</option>
											<option value="30">礼品活动</option>
											<option value="25">首单立减</option>
										</select>
									</div>
									<label id="roleCodeLabel" class="col-sm-1 control-label mtop">活动对象:</label>
									<div class="col-sm-2">
										<select id="roleCode" name="roleCode" class="form-control">
											<option value="0" id="roleCodeAll">全部</option>
											<c:forEach items="${roles}" var="obj">
												<option value="${obj.roleCode }">${obj.roleName }</option>
											</c:forEach>
										</select>
									</div>

									<div id="freeDiv">
										<label id="actCutChange" class="col-sm-1 control-label mtop">免佣天数:</label>
										<div class="col-sm-2">
											<input id="actCut" name="actCut" value="${activity.actCut}" type="text"
												   class="form-control" maxlength="20"
												   onkeyup="this.value=this.value.replace(/\D/g,'')"
												   onafterpaste="this.value=this.value.replace(/\D/g,'')">
										</div>
									</div>

								</div>
								<%--活动礼品--%>
								<div class="row row-top" id="acGift" style="display: none;">
									<label class="col-sm-1 control-label">请选择活动礼品:</label>
									<div id="giftCheckBox" class="col-sm-11">
										<c:forEach items="${listActivityGift }" var="obj" varStatus="status">
											<div class="col-sm-2 ">
												<div class="checkbox i-checks">
													<label class="gift_lable">
														<input type="checkbox" flag="0" name="giftArray"
															   value="${obj.fid }"> <i></i>${obj.giftName }
													</label>
													<input value="1" style="display: none;" name="giftNum"
														   giftFid="${obj.fid }"
														   onBlur="this.value=parseInt(this.value);if(isNaN(this.value) || this.value<=0){this.value=1;this.focus();};"
														   maxlength="3" class="gift-form-control" type="text">
												</div>

											</div>
										</c:forEach>
										<span class="help-block m-b-none"></span>
									</div>
								</div>


								<div class="row row-top">
									<label class="col-sm-1 control-label"><span
											class="text-danger">*</span>活动时间:</label>
									<div class="col-sm-2">
										<input id="actStartTime" name="sActStartTime" value="" class="laydate-icon form-control layer-date">
									</div>
									<label class="col-sm-1 control-label">到:</label>
									<div class="col-sm-2">
										<input id="actEndTime" name="sActEndTime" value="" class="laydate-icon form-control layer-date">
									</div>
								</div>


								<div class="row row-top minsu-show">
									<div class="col-sm-1 control-label mtop">
										<label class="control-label mtop">活动组:</label>
									</div>
									<div class="col-sm-2">
										<select id="groupSn" name="groupSn" class="form-control">
											<option value="">不限</option>
											<c:forEach items="${groupList}" var="group">
												<option value="${group.groupSn}">${group.groupName}</option>
											</c:forEach>
										</select>
									</div>
									<div class="col-sm-1 control-label">

									</div>
								</div>


								<div class="row row-top zra-show">
									<label class="col-sm-1 control-label mtop">是否允许叠加:</label>
									<div class="col-sm-2">
										<select id="isStack" name="isStack" class="form-control">
											<option value="0">否</option>
											<option value="1">是</option>
										</select>
									</div>
								</div>


								<div class="hr-line-dashed"></div>
								<div class="row">
									<div class="col-sm-1 text-right">
										<h2><strong>活动限制</strong></h2>
									</div>
								</div>
								<div class="row row-top zra-show">
									<label class="col-sm-1 control-label">客户类型:</label>
									<div class="col-sm-2">
										<select id="customerType" name="customerType" class="form-control">
											<option value="0" selected>不限制</option>
											<option value="1">个人</option>
											<option value="2">企业</option>
										</select>
									</div>

									<label class="col-sm-1 control-label">签约类型:</label>
									<div class="col-sm-2">
										<select id="signType" name="signType" class="form-control">
											<option value="0" selected>不限制</option>
											<option value="1">新签</option>
											<option value="2">续约</option>
										</select>
									</div>
								</div>

								<div class="row row-top zra-show">
									<label class="col-sm-1 control-label">用户分组:</label>
									<div class="col-sm-2">
										<select id="userGroup" name="groupUserFid" class="form-control">
											<option value="0" selected>不限制</option>
											<c:forEach items="${userGroups}" var="obj">
												<option value="${obj.fid}">${obj.groupName}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="row row-top zra-show">
									<label class="col-sm-1 control-label">房源分组:</label>
									<div class="col-sm-2">
										<select id="houseGroup" name="groupHouseFid" class="form-control">
											<option value="0" selected>不限制</option>
											<c:forEach items="${houseGroups}" var="obj">
												<option value="${obj.fid}">${obj.groupName}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="row row-top minsu-show">
									<label class="col-sm-1 control-label">限制城市:</label>
									<div class="col-sm-1">
										<div id="allCheckBox" class="checkbox i-checks">
											<input id="allCityCheckBox" name="allCityCode" type="checkbox" value="">全部
										</div>
									</div>
									<div id="cityCheckBox" class="col-sm-10">
										<c:forEach items="${cityList }" var="obj" varStatus="status">
											<div class="col-sm-1 ">
												<div class="checkbox i-checks">
													<label>
														<input type="checkbox" name="cityCodeArray"
															   value="${obj.code }"> <i></i>${obj.name }
													</label>
												</div>
											</div>
										</c:forEach>
										<span class="help-block m-b-none"></span>
									</div>
								</div>
							</div>

						   <div class="hr-line-dashed"></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button id="saveBtn" class="btn btn-primary" type="button">保存内容</button>
									<%--<button id="goBackBtn" class="btn btn-primary">返回列表</button>--%>
								</div>
							</div>
						</form>
			        </div>
                </div>
            </div>
        </div>
    </div>
	

    <!-- 全局js -->
    <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>

    <!-- jsTree plugin javascript -->
    <script src="${staticResourceUrl}/js/plugins/jsTree/jstree.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/validate/jquery.validate.min.js${VERSION}"></script>
    <%--<script src="${staticResourceUrl}/js/plugins/validate/messages_zh.min.js${VERSION}"></script>--%>
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
    <script src="${basePath}js/minsu/common/commonUtils.js${VERSION}010" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
	<script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
    
    <script>
        $(function (){
            //展示不同业务线数据
            lineShow($("#serviceLine").val());

        	//初始化日期
        	CommonUtils.datePickerFormat('actStartTime');
        	CommonUtils.datePickerFormat('actEndTime');

//			/**复选框点击事件*/
			$("#allCityCheckBox").click(function(){
				setCheckBoxSelected('allCityCheckBox','cityCheckBox','cityCodeArray');
			});

			$("#actType").change(function(){
				initFree($(this).val());
			});
			
			//礼物选中
			$("input[name='giftArray']").change(function(){
				var _obj = $(this).parent().parent();
				var flag= parseInt($(this).attr("flag"));
				
				if(flag == 0){
					_obj.find("input[name='giftNum']").eq(0).show();
				}else{
					_obj.find("input[name='giftNum']").eq(0).hide();
					_obj.find("input[name='giftNum']").eq(0).val(1);
					
				}
				$(this).attr("flag",flag == 0?1:0);
				
			});

            $("#serviceLine").change(function () {
                var val = $(this).val();
                lineShow(val);
            });

            $("#actTypeSelect").change(function () {
                var val = $(this).val();
                actTypeShow(val);
            });

            $("#discount_actcut").on('blur', function () {
                var val = $(this).val().replace(/[^\d]/g, '');
                if (parseInt(val) == 0) {
                    $(this).val(1);
                }
                if (parseInt(val) > 100) {
                    $(this).val(100);
                }
            });

        });

		var initFree = function (type) {
			if(typeof(type) == ""||typeof(type) == undefined){
				return ;
			}
			$("#acGift").hide();
			$("#roleCodeAll").attr("selected",true);
			$("#roleCode").attr("disabled",false);
			if(type == 20){
				$("#freeDiv").show();
				$("#actCutChange").html("免佣天数:");
				$("#roleCodeLabel").show();
				$("#roleCode").show();
			}else if(type == 30){
				//礼品活动 
				$("#roleCodeAll").attr("selected",true);
				$("#roleCode").attr("disabled",true);
				$("#actCut").val(0);
				$("#freeDiv").hide();
				$("#acGift").show();
				$("#roleCodeLabel").show();
				$("#roleCode").show();
			}else if(type == 25){
				$("#freeDiv").show();
				$("#actCutChange").html("立减金额:");
				$("#roleCodeLabel").hide();
				$("#roleCode").hide();
			}else {
				$("#actCut").val(0);
				$("#freeDiv").hide();
				$("#roleCodeLabel").show();
				$("#roleCode").show();
				
			}
		}

		/** 设置checkBox使用情况*/
		var setCheckBoxSelectedDisabled = function(selectedCheckBox,setCheckBox,checkBoxName){
			if($('#'+selectedCheckBox).is(':checked')) {
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").attr("disabled","disabled");
			}else{
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("disabled");
			}
		};


		/** 设置checkBox使用情况*/
		var setCheckBoxSelected = function(selectedCheckBox,setCheckBox,checkBoxName){
			if($('#'+selectedCheckBox).is(':checked')) {
				$("#allCityCheckBox").val(1);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").prop("checked",true);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").attr("disabled","disabled");
			}else{
				$("#allCityCheckBox").val(0);
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("checked");
				$("#"+setCheckBox).find("input[name='"+checkBoxName+"']").removeAttr("disabled");
			}
		};

        $("#saveBtn").click(function(){
        	if($("#actName").val() == ''){
        		layer.alert("活动名称不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if($("#actStartTime").val() == ''){
        		layer.alert("开始时间不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}
        	if($("#actEndTime").val() == ''){
        		layer.alert("结束时间不能为空", {icon: 6,time: 2000, title:'提示'});
        		return;
        	}

            var line = $("#serviceLine").val();
            if (line == 1) {
                //民宿校验
                if ($("#actCut").val() == '') {
                    if ($("#actType").val() == 25) {
                        layer.alert("请立减 金额", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                    layer.alert("请填写免佣天数或优惠金额", {icon: 6, time: 2000, title: '提示'});
                    return;
                }
                if ($("#actCut").val() > 0) {
                } else {
                    if ($("#actType").val() == 20) {
                        layer.alert("请填写正确的免佣天数", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                }
                if ($("#actType").val() == 25) {
                    //var moneyChckRegex = /^[0-9]*[1-9][0-9]*$/ ;
                    //var moneyChckRegex =  /^-?\d+$/;

                    if (isNaN($("#actCut").val())) {
                        layer.alert("请填写数字", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                    if (parseInt($("#actCut").val()) < 0 || parseInt($("#actCut").val()) > 600) {
                        layer.alert("请填写0-600之间的数字", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                }
                //礼物封装
                var giftInfo = "";
                $("#giftCheckBox").find("input[name='giftArray']").each(function () {

                    var _obj = $(this).parent().parent();
                    var flag = parseInt($(this).attr("flag"));

                    if (flag == 1) {
                        var _giftObj = _obj.find("input[name='giftNum']").eq(0);
                        var giftFid = _giftObj.attr("giftFid");
                        var giftVal = _giftObj.val();
                        if (giftFid != null && giftFid != undefined && giftFid != "") {
                            giftInfo += giftFid + "_" + giftVal + ",";
                        }
                    }
                });
                $("#giftInfo").val(giftInfo);

            } else if (line == 2) {
                //自如寓校验
                if (!$("#actTypeSelect").val()) {
                    layer.alert("请选择活动类型", {icon: 6, time: 2000, title: '提示'});
                    return;
                }
                var actType = $("#actTypeSelect").val();
                if (actType == 2) {
                    if (!$("#discount_actcut").val()) {
                        layer.alert("请填写折扣百分比", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                    if (!$("#discount_actmax").val()) {
                        layer.alert("请填写最高优惠", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                } else if (actType == 21) {
                    if (!$("#cut_actlimit").val()) {
                        layer.alert("请填写满减金额", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                    if (!$("#cut_actcut").val()) {
                        layer.alert("请填写满减金额", {icon: 6, time: 2000, title: '提示'});
                        return;
                    }
                }
            }

            $("div:hidden").remove();
			$.ajax({
				url:"normalActivity/saveActivity",
				dataType:"json",
				data: $("#activityForm").serialize(),
				type:"post",
				async: false,
				success:function(data) {
					if(data.code === 0){
						layer.alert("操作成功", {icon: 6,time: 2000, title:'提示'});
						setTimeout(function(){
							$.callBackParent("activity/activityList",true,callBack);
						},1500);
					}else{
						layer.alert(data.msg, {icon: 5,time: 2000, title:'提示'});
					}
				},
				error:function(result){
					layer.alert("未知错误", {icon: 5,time: 2000, title:'提示'});
				}
			});
		})

        function lineShow(type) {
            if (type == 1) {
                $(".minsu-show").show();
                $(".zra-show").hide();

                $("#actTypeSelect").attr("disabled", "disabled");
                $("#discount_actcut").attr("disabled", "disabled");
                $("#cut_actcut").attr("disabled", "disabled");

                $("#actType").removeAttr("disabled");
                $("#actCut").removeAttr("disabled");
            } else if (type == 2) {
                $(".minsu-show").hide();
                $(".zra-show").show();

                $("#actTypeSelect").removeAttr("disabled");
                $("#discount_actcut").removeAttr("disabled");
                $("#cut_actcut").removeAttr("disabled");

                $("#actType").attr("disabled", "disabled");
                $("#actCut").attr("disabled", "disabled");
            }
        }

        function actTypeShow(val) {
            if (val == 2) {
                $(".discount2").show();
                $(".cut21").hide();

                $("#discount_actcut").removeAttr("disabled");
                $("#cut_actcut").attr("disabled", "disabled");

            } else if (val == 21) {
                $(".discount2").hide();
                $(".cut21").show();

                $("#cut_actcut").removeAttr("disabled");
                $("#discount_actcut").attr("disabled", "disabled");

            } else {
                $(".discount2").hide();
                $(".cut21").hide();
            }
        }
		
		
		function callBack(parent){
			parent.refreshData("listTable");
		}


	</script>
</body>
</html>
