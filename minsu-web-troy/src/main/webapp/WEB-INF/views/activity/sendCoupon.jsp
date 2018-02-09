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
									<label class="col-sm-1 control-label mtop">短信内容:</label>
									<div class="col-sm-4">
                                        <textarea name="smsContent" class="form-control" rows="3"></textarea>
									</div>
								</div>
                                <div class="hr-line-dashed"></div>

                                <div class="row">
                                    <label class="col-sm-1 control-label mtop">手机号码:</label>
                                    <div class="col-sm-4">
                                        <textarea name="phoneNums" class="form-control" rows="3"></textarea>
                                    </div>
                                </div>

                                <div class="hr-line-dashed"></div>

                                <div class="row">
                                    <label class="col-sm-1 control-label mtop">活动码:</label>
                                    <div class="col-sm-4">
                                        <input name="actSn" class="form-control" type="text"/>
                                    </div>
                                </div>
								<div class="hr-line-dashed"></div>
							</div>
							
							<div class="hr-line-dashed" ></div>
							<div class="form-group">
								<div class="col-sm-4 col-sm-offset-2">
									<button id="saveBtn" class="btn btn-primary" type="button">保存内容</button>
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
    <script src="${staticResourceUrl}/js/plugins/layer/laydate/laydate.js${VERSION}001"></script>
    <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
    <script src="${basePath}js/minsu/common/commonUtils.js${VERSION}010" type="text/javascript"></script>
    <script src="${staticResourceUrl}/js/plugins/layer/layer.min.js${VERSION}"></script>
    <script src="${staticResourceUrl}/js/plugins/sweetalert/sweetalert.min.js${VERSION}"></script>
    
    <script type="text/javascript">
        $(function () {
            //绑定手机号优惠券
           $("#saveBtn").on("click",function () {
              $("#saveBtn").attr("disabled");
               $.post("/activity/saveCouponAndPhoneNums",$("#activityForm").serialize(),function (data) {
                   console.log(data);
                   $("#saveBtn").removeAttr("disabled");
                   if (data.code == 0){
                       var list = data.data.list;
                       var nosucc = "";
                       if (list){
                           $.each(list,function (i, n) {
                               nosucc += (n+" ");
                           });
                       }
                       if ($.trim(nosucc)){
                           layer.alert("保存成功，失败的手机号"+nosucc, {
                               icon: 1,
                               skin: 'layer-ext-moon'
                           })
                       }else{
                           layer.alert("保存成功", {
                               icon: 1,
                               skin: 'layer-ext-moon'
                           })
                       }
                   }else{
                       layer.alert(data.msg, {
                           icon: 6,
                           skin: 'layer-ext-moon'
                       });
                   }
               },'json');

           }) ;
        });
    </script>
</body>
</html>
