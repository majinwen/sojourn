<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<link href="${staticResourceUrl}/css/plugins/bootstrap-table/bootstrap-table.min.css${VERSION}001" rel="stylesheet">
    <link href="${staticResourceUrl}/css/animate.css${VERSION}" rel="stylesheet">
    <link href="${staticResourceUrl}/css/style.css${VERSION}" rel="stylesheet">
</head>
  
  <body class="gray-bg">
	   <div class="wrapper wrapper-content animated fadeInRight">
	       <div class="row">
	           <div class="col-sm-12">
	               <div class="ibox float-e-margins">
				  	   <div class="ibox-content">
				           	<div class="form-group">
		                       <div class="row">
		                             <div class="col-sm-1">
			                   	   <label class="control-label mtop">国家/地区:</label>
	                               </div>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b m-b" name="businessMsg.nationCode" id="nationCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">省:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b " name="businessMsg.provinceCode" id="provinceCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">市:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="businessMsg.cityCode"  id="cityCode" disabled="disabled">
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">区:</label>
		                           <div class="col-sm-2">
	                                   <select class="form-control m-b" name="businessMsg.areaCode"  id="areaCode" disabled="disabled">
	                                   </select>
	                               </div>
		                       </div>
		                       
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房源商机录入</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">小区名称:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="businessMsg.communityName" value="${businessInfo.businessMsg.communityName }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">详细地址:</label>
	                               </div>
		                           <div class="col-sm-3">
	                                   <input name="businessMsg.houseAddr" value="${ businessInfo.businessMsg.houseAddr}"  type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">房源类型:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <select class="form-control m-b" name="businessMsg.rentWay" disabled="disabled">
		                           	   	  <option value="0" ${businessInfo.businessMsg.rentWay==0 ?'selected':''}>整租</option>
		                           	   	  <option value="1" ${businessInfo.businessMsg.rentWay==1 ?'selected':''}>合租</option>
		                           	   	  <option value="2" ${businessInfo.businessMsg.rentWay==2 ?'selected':''}>可整可分</option>
	                                   </select>
	                               </div>
				                   <label class="col-sm-1 control-label mtop">商圈:</label>
		                           <div class="col-sm-1">
	                                   <input name="businessMsg.businessArea" value="${ businessInfo.businessMsg.businessArea}" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房源其他信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">信息来源:</label>
		                           <div class="col-sm-1">
		                           	   <select class="form-control m-b" name="businessSource.busniessSource" disabled="disabled">
		                           	   	  <option value="0" ${businessInfo.businessSource.busniessSource==0 ?'selected':''}>地推扩展</option>
		                           	   	  <option value="1" ${businessInfo.businessSource.busniessSource==1 ?'selected':''}>房东自住发布</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">是否在作业范围:</label>
		                           <div class="col-sm-1">
		                           	   <select class="form-control m-b" name="businessSource.isJobArea" disabled="disabled">
		                           	   	  <option value="0" ${businessInfo.businessSource.isJobArea==0 ?'selected':''}>是</option>
		                           	   	  <option value="1" ${businessInfo.businessSource.isJobArea==1 ?'selected':''}>否</option>
	                                   </select>
	                               </div>
				                   <label class="col-sm-1 control-label ">平台链接:</label>
		                           <div class="col-sm-3 ">
		                           	   <input  name="businessSource.sourceUrl" value="${businessInfo.businessSource.sourceUrl }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
		                           <div class="col-sm-1">
				                   	   <label class="control-label mtop">评价量:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="businessSource.evaluateNum" value="${ businessInfo.businessSource.evaluateNum}" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                               <div class="col-sm-1">
				                   	   <label class="control-label mtop">评分:</label>
	                               </div>
		                           <div class="col-sm-1">
	                                   <input name="businessSource.houseGrade" value="${businessInfo.businessSource.houseGrade }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">房东信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">房东姓名:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordName" value="${businessInfo.businessExt.landlordName }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                                <label class="col-sm-1 control-label mtop">房东手机:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordMobile" value="${businessInfo.businessExt.landlordMobile }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房东昵称:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordNickName" value="${ businessInfo.businessExt.landlordNickName}" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                                <label class="col-sm-1 control-label mtop">QQ号码:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordQq" value="${businessInfo.businessExt.landlordQq }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">微信昵称:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.landlordWechat" value="${businessInfo.businessExt.landlordWechat }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">电子邮箱:</label>
		                           <div class="col-sm-2">
		                           	   <input name="businessExt.landlordEmail" value="${businessInfo.businessExt.landlordEmail }" type="text" class="form-control m-b" readonly="readonly" >
	                               </div>
	                               <label class="col-sm-1 control-label mtop">房东类型:</label>
		                           <div class="col-sm-1">
		                           	     <select class="form-control m-b" name="businessExt.landlordType"  disabled="disabled">
			                           		<option value="0" ${businessInfo.businessExt.landlordType==0 ?'selected':''}>专业型</option>
			                           		<option value="1" ${businessInfo.businessExt.landlordType==1 ?'selected':''}>半专业型</option>
			                           		<option value="2" ${businessInfo.businessExt.landlordType==2 ?'selected':''}>体验型</option>
			                             </select>
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">业务信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">经理:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.managerName" value="${businessInfo.businessExt.managerName }" type="text" class="form-control m-b" id="managerName" readonly="readonly">
		                           	   <input name="businessExt.managerCode" value="${businessInfo.businessExt.managerCode }" type="hidden" class="form-control m-b" id="managerCode">
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">维护管家:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.whGuardName" value="${businessInfo.businessExt.whGuardName }" type="text" class="form-control m-b" id="whGuardName" readonly="readonly">
		                           	   <input name="businessExt.whGuardCode" value="${businessInfo.businessExt.whGuardCode }" type="hidden" class="form-control m-b" id="whGuardCode">
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">地推信息</label>
	                           <div class="hr-line-dashed"></div>
	                           <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">地推管家:</label>
		                           <div class="col-sm-1">
		                           	   <input name="businessExt.dtGuardName" value="${businessInfo.businessExt.dtGuardName }" type="text" class="form-control m-b" id="dtGuardName" readonly="readonly">
		                           	   <input name="businessExt.dtGuardCode" value="${businessInfo.businessExt.dtGuardCode }" type="hidden" class="form-control m-b" id="dtGuardCode">
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">是否与房东见面沟通过:</label>
		                           <div class="col-sm-1">
		                           	    <select class="form-control m-b" name="businessExt.isMeet"  disabled="disabled">
		                           	   	  <option value="0" ${businessInfo.businessExt.isMeet==0 ?'selected':''}>是</option>
		                           	   	  <option value="1" ${businessInfo.businessExt.isMeet==1 ?'selected':''}>否</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">目前进度:</label>
		                           <div class="col-sm-2">
		                           	    <select class="form-control m-b" name="businessExt.businessPlan" disabled="disabled">
		                           	   	  <option value="0" ${businessInfo.businessExt.businessPlan==0 ?'selected':''}>已获取房源信息</option>
		                           	   	  <option value="1" ${businessInfo.businessExt.businessPlan==1 ?'selected':''}>已获取联系方式</option>
		                           	   	  <option value="2" ${businessInfo.businessExt.businessPlan==2 ?'selected':''}>已沟通意向</option>
		                           	   	  <option value="3" ${businessInfo.businessExt.businessPlan==3 ?'selected':''}>已发布</option>
		                           	   	  <option value="4" ${businessInfobusinessExt.businessPlan==4?'selected':''}>管家驳回</option>
		                           	   	  <option value="5" ${businessInfo.businessExt.businessPlan==5 ?'selected':''}>品质驳回</option>
		                           	   	  <option value="6" ${businessInfo.businessExt.businessPlan==6 ?'selected':''}>已上架</option>
		                           	   	  <option value="7" ${businessInfo.businessExt.businessPlan==7 ?'selected':''}>已线下核验</option>
		                           	   	  <option value="8" ${businessInfo.businessExt.businessPlan==8 ?'selected':''}>已拍照</option>
	                                   </select>
	                               </div>
	                               <label class="col-sm-1 control-label mtop">驳回原因:</label>
		                           <div class="col-sm-2">
		                           	    <select class="form-control m-b" name="businessExt.rejectReason" disabled="disabled">
		                           	      <option value="">请选择</option>
		                           	   	  <option value="0" ${businessInfo.businessExt.rejectReason==0 ?'selected':''}>房东挂错</option>
		                           	   	  <option value="1" ${businessInfo.businessExt.rejectReason==1 ?'selected':''}>品质不符无法升级</option>
		                           	   	  <option value="2" ${businessInfo.businessExt.rejectReason==2 ?'selected':''}>品质不符需要升级</option>
		                           	   	  <option value="3" ${businessInfo.businessExt.rejectReason==3 ?'selected':''}>其他</option>
	                                   </select>
	                               </div>
		                       </div>
							   <div class="row">
							   	   <label class="col-sm-1 control-label mtop">跟进内容:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="businessExt.followContent" class="form-control m-b" readonly="readonly" >${businessInfo.businessExt.followContent }</textarea>
	                               </div>
							   </div>
							   <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">发布时间:</label>
		                           <div class="col-sm-2">
		                           	   <input name="releaseDate" value="${businessInfo.releaseDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">上架时间:</label>
		                           <div class="col-sm-2">
		                           	   <input name="putawayDate" value="${businessInfo.putawayDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
	                               <label class="col-sm-1 control-label mtop">预约线下核验日期:</label>
		                           <div class="col-sm-2">
		                           	   <input name="makeCheckDate" value="${businessInfo.makeCheckDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">实际线下核验日期:</label>
		                           <div class="col-sm-2">
		                           	   <input name="realCheckDate" value="${businessInfo.realCheckDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
		                       </div>
		                       <div class="row">
		                       	   <label class="col-sm-1 control-label mtop">预约拍照日期:</label>
		                           <div class="col-sm-2">
		                           	   <input name="makePhotoDate" value="${businessInfo.makePhotoDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
		                       	   <label class="col-sm-1 control-label mtop">实际拍照日期:</label>
		                           <div class="col-sm-2">
		                           	   <input name="realPhotoDate" value="${businessInfo.realPhotoDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
	                               <label class="col-sm-1 control-label mtop">登记日期:</label>
		                           <div class="col-sm-2">
		                           	   <input name="registerDate" value="${businessInfo.registerDate}" type="text" class="form-control m-b"  readonly="readonly">
	                               </div>
		                       </div>
		                       <label class="help-block m-b-none mtop" style="font-size:20px;">备注信息</label>
	                           <div class="hr-line-dashed"></div>
		                       <div class="row">
							   	   <label class="col-sm-1 control-label mtop">备注信息:</label>
		                           <div class="col-sm-5">
		                           	   <textarea name="businessMsg.businessRemark" class="form-control m-b" readonly="readonly">${businessInfo.businessMsg.businessRemark }</textarea>
	                               </div>
							   </div>
			               </div>
			           </div>
	               </div>
	           </div>
	       </div>
	   </div>
	   <!-- 全局js -->
	   <script src="${staticResourceUrl}/js/jquery.min.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/bootstrap.min.js${VERSION}"></script>
	
	   <!-- 自定义js -->
	   <script src="${staticResourceUrl}/js/content.js${VERSION}"></script>
	   <script src="${staticResourceUrl}/js/minsu/common/custom.js${VERSION}"></script>
	   
	   <!-- Bootstrap table -->
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js${VERSION}001"></script>
       <script src="${staticResourceUrl}/js/minsu/common/geography.cascade.js${VERSION}001"></script>
       
       <script type="text/javascript">
		   $(function(){
	     	   // 多级联动
			   var geoJson = ${cityTreeList};
			   var options = {
				   geoJson	: geoJson,
				   nationCode: '${businessInfo.businessMsg.nationCode}',
				   provinceCode: '${businessInfo.businessMsg.provinceCode}',
				   cityCode: '${businessInfo.businessMsg.cityCode}',
				   areaCode: '${businessInfo.businessMsg.areaCode}'
			   };
			   geoCascade(options);
		   });  
	   </script>
	</body>
</html>
