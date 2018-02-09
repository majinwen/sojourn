package com.ziroom.minsu.troy.cashback.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.BigDecimalUtil;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.cms.ActivityEntity;
import com.ziroom.minsu.entity.cms.ActivityVo;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.entity.order.FinanceCashbackEntity;
import com.ziroom.minsu.services.cms.api.inner.ActivityService;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.customer.api.inner.CustomerBlackService;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.order.api.inner.FinanceCashbackService;
import com.ziroom.minsu.services.order.dto.AuditCashbackQueryRequest;
import com.ziroom.minsu.services.order.dto.AuditCashbackRequest;
import com.ziroom.minsu.services.order.dto.OrderCashbackRequest;
import com.ziroom.minsu.services.order.entity.AuditCashbackVo;
import com.ziroom.minsu.services.order.entity.OrderCashbackVo;
import com.ziroom.minsu.troy.auth.menu.EvaluateAuthUtils;
import com.ziroom.minsu.troy.common.util.UploadExcelUtil;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

/**
 * <p>订单返现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * @author lishaochuan on 2016年9月8日
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("cashback")
public class CashbackController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(CashbackController.class);
	
	
	@Resource(name = "order.financeCashbackService")
	private FinanceCashbackService financeCashbackService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "cms.activityService")
	private ActivityService activityService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;
	
	@Resource(name="customer.customerBlackService")
	private CustomerBlackService customerBlackService;	
	
	/**
	 * 跳转返现申请列表页
	 * @author lishaochuan
	 * @create 2016年9月8日下午3:27:34
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toApplyPage")
	public String toApplyPage(HttpServletRequest request, HttpServletResponse response){
		try {
			String cashbackJson = activityService.getCashbackList(YesOrNoEnum.YES.getCode());
			List<ActivityVo> activityEntities = SOAResParseUtil.getListValueFromDataByKey(cashbackJson, "list", ActivityVo.class);
			
			Map<String, String> acts = new HashMap<>();
			for (ActivityVo activityVo : activityEntities) {
				acts.put(activityVo.getActSn(), activityVo.getActName());
			}
			
			request.setAttribute("acts", acts);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询进行中的返现活动失败", e);
		}
		
		return "/cashback/cashbackApply";
	}
	
	
	/**
	 * 查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午4:07:29
	 * @param request
	 * @return
	 */
	@RequestMapping("querApplyList")
	@ResponseBody
	public PageResult querApplyList(OrderCashbackRequest cashbackRequest,HttpServletRequest request) {
		PageResult pageResult = new PageResult();
		try{
			Object authMenu = request.getAttribute("authMenu");
			cashbackRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
			if(!addAuthData(authMenu,cashbackRequest)){
				return null;
			}
			String orderJson = financeCashbackService.getOrderCashbackList(JsonEntityTransform.Object2Json(cashbackRequest));
			DataTransferObject orderResultDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			List<OrderCashbackVo> orderlist = SOAResParseUtil.getListValueFromDataByKey(orderJson, "list", OrderCashbackVo.class);
			pageResult.setRows(orderlist);
			pageResult.setTotal(Long.valueOf(orderResultDto.getData().get("total").toString()));
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
		}
		return pageResult;
	}
	
	
	/**
	 * 返现申请
	 * @author lishaochuan
	 * @create 2016年9月9日上午9:14:25
	 * @param orderSn
	 * @return
	 */
	@RequestMapping("apply")
	@ResponseBody
	public DataTransferObject apply(FinanceCashbackEntity cashback) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(cashback)){
				LogUtil.error(LOGGER, "参数为空:cashback:{}", cashback);
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			if(Check.NuNObjs(cashback.getOrderSn(), cashback.getReceiveType(), cashback.getTotalFee())){
				LogUtil.error(LOGGER, "参数为空:cashback:{}", JsonEntityTransform.Object2Json(cashback));
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数错误");
	            return dto;
			}
			cashback.setCreateId(UserUtil.getCurrentUser().getUserAccount());
			
			String cashbackJson = financeCashbackService.saveCashback(JsonEntityTransform.Object2Json(cashback));
			dto = JsonEntityTransform.json2DataTransferObject(cashbackJson);
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}

	/**
	 * 
	 * 批量申请返现单
	 *
	 * @author loushuai
	 * @created 2018年1月23日 下午5:36:16
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST ,value="batchApplyCashBack")
	@ResponseBody
	public DataTransferObject  batchApplyCashBack(MultipartFile file) throws IOException{
		long start = System.currentTimeMillis();
		DataTransferObject dto = new DataTransferObject();
		StringBuilder customerBlackBuilder = null; //收款人在民宿黑名单==》订单号builder
		StringBuilder orOrderBuilder = null; //根据订单号查不出订单==》订单号builder
		StringBuilder upPayOrderBuilder = null;//订单号未支付==》订单号builder
		StringBuilder receiveTypeErrorBuilder = null; //收款人类型填写错误==》订单号builder
		StringBuilder actSnErrorBuilder = null; //根据活动号查询不出活动==》订单号builder
		StringBuilder paramNullBuilder = null; //参数错误==》订单号builder
		StringBuilder numGreateOneBuilder = null; //在该活动下，该收款人（房东）在已存在多笔返现单（无论返现单状态，无论房东房客）==》订单号builder
		StringBuilder numGreateOneTenBuilder = null; //在该活动下，该收款人（房客）在已存在多笔返现单（无论返现单状态，无论房东房客）==》订单号builder
	    try{
	    	 //这里得到的是一个集合，里面的每一个元素是String[]数组
	         List<String[]> list = UploadExcelUtil.readExcel(file); 
	         if(Check.NuNCollection(list)){
	        	 dto.setErrCode(DataTransferObject.ERROR);
	        	 dto.setMsg("解析出的excel数据为空");
	        	 return dto;
	         }
	         customerBlackBuilder = new StringBuilder();
			 customerBlackBuilder.append("黑名单订单号（该单导入失败）：");
	         orOrderBuilder = new StringBuilder();
	         orOrderBuilder.append("订单号不存在（该单导入失败）：");
	         upPayOrderBuilder = new StringBuilder();
	         upPayOrderBuilder.append("订单未支付（该单导入失败）：");
	         receiveTypeErrorBuilder = new StringBuilder();
			 receiveTypeErrorBuilder.append("收款人填写错误（该单导入失败）：");
			 actSnErrorBuilder = new StringBuilder();
			 actSnErrorBuilder.append("活动号不存在（该单导入失败）：");
			 paramNullBuilder = new StringBuilder();
			 paramNullBuilder.append("有必填项填写错误（该单导入失败）：");
			 numGreateOneBuilder = new StringBuilder();
			 numGreateOneBuilder.append("收款人（房东）存在多笔返现（该单导入成功）：");
			 numGreateOneTenBuilder = new StringBuilder();
			 numGreateOneTenBuilder.append("收款人（房客）存在多笔返现（该单导入成功）：");
	         //将List对象转化为需要申请返现单的参数对象 
	         String createiId = UserUtil.getCurrentUser().getUserAccount();
	         List<FinanceCashbackEntity> cashBacklist = new ArrayList<FinanceCashbackEntity>();
			 int isRequest=0;
			 int limit = 30;
			 String orderSn = null;
			 String receiveTypeStr = null;
			 String actSn = null;
			 String totalFee = null;
			 String remark = null;
			 Integer receiveType  = 0;
			 if(list.size()<=limit){
				for (String[] temp : list) {
					 try {
						orderSn = temp[0].trim();
						 receiveTypeStr = temp[1].trim();
						 actSn = temp[2].trim();
						 totalFee = temp[3].trim();
						 if(temp.length==5){
							 remark = temp[4].trim();
							 if(!Check.NuNStr(remark) && remark.length()>500){
								 remark = remark.substring(0, 500);
							 }
						 }
						 if("房客".equals(receiveTypeStr)){
							 receiveType=2;
						 }else if("房东".equals(receiveTypeStr)){
							 receiveType=1;
						 }else{
							 receiveTypeErrorBuilder.append(orderSn);
							 receiveTypeErrorBuilder.append(";");
							 continue;
						 }
						boolean checkNullAndAcivity = checkNullAndAcivity(orderSn,receiveTypeStr, actSn, totalFee,receiveType, paramNullBuilder, actSnErrorBuilder);
						if(!checkNullAndAcivity){
							continue;
						}
						transferCashBack(orderSn, receiveType, actSn, totalFee, remark, createiId, cashBacklist);
					} catch (Exception e) {
						 LogUtil.error(LOGGER, "batchApplyCashBack方法，发生错误错误e={},循环到的对象为={}", e, JsonEntityTransform.Object2Json(temp));
						 paramNullBuilder.append(orderSn);
						 paramNullBuilder.append(";");
						 continue;
					}
				}
				if(!Check.NuNCollection(cashBacklist)){
					String cashbackJson = financeCashbackService.saveCashbackBatch(JsonEntityTransform.Object2Json(cashBacklist));
					dto = JsonEntityTransform.json2DataTransferObject(cashbackJson);
		 		    if(dto.getCode()==DataTransferObject.ERROR){
		 		    	return dto;
		 		    }
		 		     transferBuilder(dto, orOrderBuilder, upPayOrderBuilder, customerBlackBuilder, numGreateOneBuilder, numGreateOneTenBuilder);
				}
			 }else{
				 for (String[] temp : list) {
					try {
						     isRequest++;
							 orderSn = temp[0].trim();
							 receiveTypeStr = temp[1].trim();
							 actSn = temp[2].trim();
							 totalFee = temp[3].trim();
							 if(temp.length==5){
								 remark = temp[4].trim();
								 if(!Check.NuNStr(remark) && remark.length()>500){
									 remark = remark.substring(0, 500);
								 }
							 }
							 if("房客".equals(receiveTypeStr)){
								 receiveType=2;
							 }else if("房东".equals(receiveTypeStr)){
								 receiveType=1;
							 }else{
								 receiveTypeErrorBuilder.append(orderSn);
								 receiveTypeErrorBuilder.append(";");
								 continue;
							 }
							boolean checkNullAndAcivity = checkNullAndAcivity(orderSn, receiveTypeStr,actSn, totalFee,receiveType, paramNullBuilder, actSnErrorBuilder);
							if(!checkNullAndAcivity){
								continue;
							}
							transferCashBack(orderSn, receiveType, actSn, totalFee, remark, createiId, cashBacklist);
							if(isRequest%limit == 0 ){
								if(!Check.NuNCollection(cashBacklist)){
									String cashbackJson = financeCashbackService.saveCashbackBatch(JsonEntityTransform.Object2Json(cashBacklist));
									cashBacklist = new ArrayList<FinanceCashbackEntity>();
									dto = JsonEntityTransform.json2DataTransferObject(cashbackJson);
						 		    if(dto.getCode()==DataTransferObject.ERROR){
						 		    	continue;
						 		    }
						 		    
						 		   transferBuilder(dto, orOrderBuilder, upPayOrderBuilder, customerBlackBuilder, numGreateOneBuilder, numGreateOneTenBuilder);
								}
								continue;
							}else if(isRequest==list.size()){
								if(!Check.NuNCollection(cashBacklist)){
									String cashbackJson = financeCashbackService.saveCashbackBatch(JsonEntityTransform.Object2Json(cashBacklist));
									cashBacklist = new ArrayList<FinanceCashbackEntity>();
									dto = JsonEntityTransform.json2DataTransferObject(cashbackJson);
						 		    if(dto.getCode()==DataTransferObject.ERROR){
						 		    	continue;
						 		    }
						 		    
						 		   transferBuilder(dto, orOrderBuilder, upPayOrderBuilder, customerBlackBuilder, numGreateOneBuilder, numGreateOneTenBuilder);
								}
							}
						 } catch (Exception e) {
							 LogUtil.error(LOGGER, "batchApplyCashBack方法，发生错误错误e={},循环到的对象为={}",e, JsonEntityTransform.Object2Json(temp));
							 paramNullBuilder.append(orderSn);
							 paramNullBuilder.append(";");
							 continue;
						}
			    }
		     }
	    } catch(Exception e){
	        dto.setErrCode(DataTransferObject.ERROR);
	        dto.setMsg("batchApplyCashBack方法出现异常");
	        LogUtil.error(LOGGER, "batchApplyCashBack方法，发生错误错误e={}",e);
	    }
	    if(customerBlackBuilder != null){
			dto.putValue("customerBlackList", customerBlackBuilder.toString());
	    	if("黑名单订单号（该单导入失败）：".equals(customerBlackBuilder.toString())){
	    		dto.putValue("customerBlackList", "");
	    	}
		}
	    if(orOrderBuilder != null){
	    	dto.putValue("noOrderList", orOrderBuilder.toString());
	    	if("订单号不存在（该单导入失败）：".equals(orOrderBuilder.toString())){
	    		dto.putValue("noOrderList", "");
	    	}
		}
	    if(upPayOrderBuilder != null){
	    	dto.putValue("upPayOrderList", upPayOrderBuilder.toString());
	    	if("订单未支付（该单导入失败）：".equals(upPayOrderBuilder.toString())){
	    		dto.putValue("upPayOrderList", "");
	    	}
		}
		if(receiveTypeErrorBuilder != null){
			dto.putValue("receiveTypeErrorList", receiveTypeErrorBuilder.toString());
	    	if("收款人填写错误（该单导入失败）：".equals(receiveTypeErrorBuilder.toString())){
	    		dto.putValue("receiveTypeErrorList", "");
	    	}
		}
		if(actSnErrorBuilder != null){
			dto.putValue("actSnErrorList", actSnErrorBuilder.toString());
	    	if("活动号不存在（该单导入失败）：".equals(actSnErrorBuilder.toString())){
	    		dto.putValue("actSnErrorList", "");
	    	}
		}
		if(paramNullBuilder != null){
			dto.putValue("paramNullList", paramNullBuilder.toString());
	    	if("有必填项填写错误（该单导入失败）：".equals(paramNullBuilder.toString())){
	    		dto.putValue("paramNullList", "");
	    	}
		}
		if(numGreateOneBuilder != null){
			dto.putValue("numGreateOneList", numGreateOneBuilder.toString());
	    	if("收款人（房东）存在多笔返现（该单导入成功）：".equals(numGreateOneBuilder.toString())){
	    		dto.putValue("numGreateOneList", "");
	    	}
		}
		if(numGreateOneTenBuilder != null){
			dto.putValue("numGreateOneTenList", numGreateOneTenBuilder.toString());
	    	if("收款人（房客）存在多笔返现（该单导入成功）：".equals(numGreateOneTenBuilder.toString())){
	    		dto.putValue("numGreateOneTenList", "");
	    	}
		}
		long end = System.currentTimeMillis();
		LogUtil.info(LOGGER, "batchApplyCashBack方法，耗时={},result={}", end-start,dto.toJsonString());
	    return dto;
	}
	
	public void transferBuilder(DataTransferObject dto,
			StringBuilder orOrderBuilder,StringBuilder upPayOrderBuilder,
			StringBuilder customerBlackBuilder,StringBuilder numGreateOneBuilder,StringBuilder numGreateOneTenBuilder){
		
		    String noOrderList = dto.parseData("noOrderList", new TypeReference<String>() {});
		    orOrderBuilder.append(noOrderList);
		    
		    String upPayOrderList = dto.parseData("upPayOrderList", new TypeReference<String>() {});
		    upPayOrderBuilder.append(upPayOrderList);
		    
		    String customerBlackList = dto.parseData("customerBlackList", new TypeReference<String>() {});
		    customerBlackBuilder.append(customerBlackList);
		    
		    String numGreateOneList = dto.parseData("numGreateOneList", new TypeReference<String>() {});
		    numGreateOneBuilder.append(numGreateOneList);
		    
		    String numGreateOneTenList = dto.parseData("numGreateOneTenList", new TypeReference<String>() {});
		    numGreateOneTenBuilder.append(numGreateOneTenList);
	}
	
	public void transferCashBack(String orderSn, Integer receiveType, String actSn, String totalFee,String remark,String createiId,List<FinanceCashbackEntity> cashBacklist){
		 FinanceCashbackEntity cashback = new FinanceCashbackEntity(); 
		 cashback.setOrderSn(orderSn);
   	 cashback.setActSn(actSn);
   	 Double value = Double.valueOf(totalFee);
   	 double taotal = BigDecimalUtil.mul(value, 100);
   	 LogUtil.info(LOGGER, "batchApplyCashBack方法，totalFee={}", taotal);
   	 cashback.setTotalFee(new Double(taotal).intValue());
   	 cashback.setReceiveType(receiveType);
   	 cashback.setApplyRemark(remark);
   	 cashback.setCreateId(createiId);
   	 cashBacklist.add(cashback);
	}
	
	/**
	 * 
	 * 校验参数是否为空，校验actSn对应的活动是否为空
	 *
	 * @author loushuai
	 * @created 2018年1月26日 下午9:18:50
	 *
	 * @param temp
	 * @param orderSn
	 * @param receiveTypeStr
	 * @param actSn
	 * @param totalFee
	 * @param remark
	 * @param paramNullBuilder
	 * @param actSnErrorBuilder
	 * @return
	 */
	public boolean checkNullAndAcivity(String orderSn,String receiveTypeStr, String actSn, String totalFee,
			Integer receiveType,StringBuilder paramNullBuilder, StringBuilder actSnErrorBuilder){
		 try {
			 if(Check.NuNObjs(orderSn, receiveTypeStr, actSn, totalFee)){ 
				 paramNullBuilder.append(orderSn);
				 paramNullBuilder.append(";");
				 return false;
			 }
			 //判断活动号是否存在
			 String activityResult = activityService.getActivityBySn(actSn);
			 DataTransferObject activityDto = JsonEntityTransform.json2DataTransferObject(activityResult);
			 if(activityDto.getCode()==DataTransferObject.ERROR){
				 actSnErrorBuilder.append(orderSn);
				 actSnErrorBuilder.append(";");
				 return false;
			 }
			 ActivityEntity activit = activityDto.parseData("activity", new TypeReference<ActivityEntity>() {});
			 if(Check.NuNObj(activit)){
				 actSnErrorBuilder.append(orderSn);
				 actSnErrorBuilder.append(";");
				 return false;
			 }
		} catch (BusinessException e) {
			LogUtil.error(LOGGER, "checkNullAndAcivity方法异常：e：{}", e);
			return false;
		}
        return true;
	}
	
	
	/**
	 * 
	 * 1，获取返现单申请的receiveUid是否为民宿黑名单    2，获取该uid在申请表中t_finance_cashback表中，在actSn活动下（不区分房东房客，不区分状态）已经有多少返现单
	 *
	 * @author loushuai
	 * @created 2018年1月23日 下午5:36:16
	 *
	 * @param uid
	 * @param actSn
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getCustomerBlackAndCashBackNum")
	@ResponseBody
	public DataTransferObject  getCustomerBlackAndCashBackNum(String uid, String  actSn,HttpServletRequest request) throws IOException{
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(uid) || Check.NuNObj(actSn)){
				LogUtil.error(LOGGER, "参数为空:cashback={},actSn={}", uid, actSn);
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
            //查询uid是否在民宿黑名单
			String customerBlackrResult = customerBlackService.findCustomerBlackByUid(uid);
			DataTransferObject customerBlackrDto = JsonEntityTransform.json2DataTransferObject(customerBlackrResult);
			if(customerBlackrDto.getCode()==DataTransferObject.SUCCESS){
				CustomerBlackEntity customerBlack = customerBlackrDto.parseData("obj", new TypeReference<CustomerBlackEntity>() {});
				dto.putValue("isCustomerBlack", "否");
				if(!Check.NuNObj(customerBlack)){
					dto.putValue("isCustomerBlack", "是");
			    }
			}
			//"此次活动该用户已有X笔返现申请"
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("uid", uid);
			paramMap.put("actSn", actSn);
			String hasCashBackNumJson = financeCashbackService.getHasCashBackNum(JsonEntityTransform.Object2Json(paramMap));
			DataTransferObject hasCashBackNumDto = JsonEntityTransform.json2DataTransferObject(hasCashBackNumJson);
			if(hasCashBackNumDto.getCode()==DataTransferObject.SUCCESS){
				long hasCashBackNum = hasCashBackNumDto.parseData("hasCashBackNu", new TypeReference<Long>() {});
				dto.putValue("hasCashBackNum", hasCashBackNum);
			}
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}
	
	/**
	 * 跳转返现审核列表页
	 * @author lishaochuan
	 * @create 2016年9月8日下午3:27:34
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toAuditPage")
	public String toAuditPage(HttpServletRequest request, HttpServletResponse response){
		try {
			String cashbackJson = activityService.getCashbackList(YesOrNoEnum.YES.getCode());
			List<ActivityVo> activityEntities = SOAResParseUtil.getListValueFromDataByKey(cashbackJson, "list", ActivityVo.class);
			
			Map<String, String> acts = new HashMap<>();
			for (ActivityVo activityVo : activityEntities) {
				acts.put(activityVo.getActSn(), activityVo.getActName());
			}
			
			request.setAttribute("acts", acts);
		} catch (Exception e) {
			LogUtil.error(LOGGER, "查询进行中的返现活动失败", e);
		}
		return "/cashback/cashbackAudit";
	}
	
	
	/**
	 * 查询返现申请列表
	 * @author lishaochuan
	 * @create 2016年9月8日下午4:07:29
	 * @param request
	 * @return
	 */
	@RequestMapping("querAuditList")
	@ResponseBody
	public PageResult querAuditList(AuditCashbackQueryRequest aRequest,HttpServletRequest request) {
		PageResult pageResult = new PageResult();
		try{
			//处理用户uid查询参数
			this.dealAuditCashbackQueryRequest(aRequest);
			if((!Check.NuNStr(aRequest.getReceiveName()) || !Check.NuNStr(aRequest.getReceiveTel())) && Check.NuNCollection(aRequest.getReceiveUidList())){
				pageResult.setRows(new ArrayList<>());
				pageResult.setTotal(0L);
				return pageResult;
			}
			//特殊权限相关修改
			Object authMenu = request.getAttribute("authMenu");
			aRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
			if(!auditCashbackAddAuthData(authMenu,aRequest)){
				return null;
			}
			String orderJson = financeCashbackService.getAuditCashbackList(JsonEntityTransform.Object2Json(aRequest));
			DataTransferObject orderResultDto = JsonEntityTransform.json2DataTransferObject(orderJson);
			List<AuditCashbackVo> list = SOAResParseUtil.getListValueFromDataByKey(orderJson, "list", AuditCashbackVo.class);
			
			
			//翻译用户名称
			Set<String> uidSet = new HashSet<>();
			for (AuditCashbackVo auditCashbackVo : list) {
				uidSet.add(auditCashbackVo.getReceiveUid());
			}
			List<String> uidList = new ArrayList<>(uidSet);
			DataTransferObject uidDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(uidList)));
			List<CustomerBaseMsgEntity> customerList = uidDto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
			
			
			//翻译活动名称
			String cashbackJson = activityService.getCashbackList(YesOrNoEnum.YES.getCode());
			List<ActivityVo> activityEntities = SOAResParseUtil.getListValueFromDataByKey(cashbackJson, "list", ActivityVo.class);
			Map<String, String> acts = new HashMap<>();
			for (ActivityVo activityVo : activityEntities) {
				acts.put(activityVo.getActSn(), activityVo.getActName());
			}
			//2018-01-20 两个需求，1， 填充每个订单的房东及房客是否为在民宿黑名单      2，"此次活动该用户已有X笔返现申请"========该receiveUid（无论作为房东还是房客，无论什么状态）已经有多少返现单
			//考虑到申请返现单列表可也选择每页展示10，20，50，  1，需要提供批量查询民宿黑名单的接口   2，需要提供批量"此次活动该用户已有X笔返现申请"
			//黑名单批量查询接口
			Map<String, Object> resultMap = null;
			String listCustomerBlackrResult = customerBlackService.getCustomerBlackBatch(JsonEntityTransform.Object2Json(uidSet));
			DataTransferObject listCustomerBlackrDto = JsonEntityTransform.json2DataTransferObject(listCustomerBlackrResult);
			if(listCustomerBlackrDto.getCode()==DataTransferObject.SUCCESS){
				resultMap = listCustomerBlackrDto.parseData("resultMap", new TypeReference<Map>() {});
			}
			
			for (AuditCashbackVo auditCashbackVo : list) {
				for (CustomerBaseMsgEntity customerBaseMsgEntity : customerList) {
					if(!Check.NuNObj(auditCashbackVo.getReceiveUid()) && auditCashbackVo.getReceiveUid().equals(customerBaseMsgEntity.getUid())){
						auditCashbackVo.setReceiveName(customerBaseMsgEntity.getRealName());
						auditCashbackVo.setReceiveTel(customerBaseMsgEntity.getCustomerMobile());
						break;
					}
				}
				auditCashbackVo.setActName(acts.get(auditCashbackVo.getActSn()));
				//填充该用户是否为黑名单
				if(!Check.NuNMap(resultMap)){
					Integer isCustomerBlack = (Integer) resultMap.get(auditCashbackVo.getReceiveUid());
					if(!Check.NuNObj(isCustomerBlack)){
						auditCashbackVo.setIsCustomerBlack(YesOrNoEnum.NO.getName());
						if(isCustomerBlack.intValue() == YesOrNoEnum.YES.getCode()){
							auditCashbackVo.setIsCustomerBlack(YesOrNoEnum.YES.getName());
						}
					}
			    }
			}
			pageResult.setRows(list);
			pageResult.setTotal(Long.valueOf(orderResultDto.getData().get("total").toString()));
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
		}
		return pageResult;
	}
	
	
	/**
	 * 查询返现总金额
	 * @author lishaochuan
	 * @create 2016年9月13日下午5:31:11
	 * @param request
	 * @return
	 */
	@RequestMapping("getAuditCashbackSumFee")
	@ResponseBody
	public DataTransferObject getAuditCashbackSumFee(AuditCashbackQueryRequest aRequest,HttpServletRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try{
			//处理用户uid查询参数
			this.dealAuditCashbackQueryRequest(aRequest);
			//特殊权限相关修改
			Object authMenu = request.getAttribute("authMenu");
			aRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
			if(!auditCashbackAddAuthData(authMenu,aRequest)){
				return null;
			}
			String sumFeeJson = financeCashbackService.getAuditCashbackSumFee(JsonEntityTransform.Object2Json(aRequest));
			dto = JsonEntityTransform.json2DataTransferObject(sumFeeJson);
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}
	
	/**
	 * 处理用户uid查询参数
	 * @author lishaochuan
	 * @create 2016年9月13日下午5:29:02
	 * @param request
	 */
	private void dealAuditCashbackQueryRequest(AuditCashbackQueryRequest request){
		// 根据姓名查询，需要获取到姓名对应的uid
		String receiveName = request.getReceiveName();
		String receiveTel = request.getReceiveTel();
		List<String> receiveUidList = new ArrayList<>();
		if(!Check.NuNStr(receiveName) || !Check.NuNStr(receiveTel)){
			CustomerBaseMsgDto customerBaseMsgDto = new CustomerBaseMsgDto();
			customerBaseMsgDto.setRealName(receiveName);
			customerBaseMsgDto.setCustomerMobile(receiveTel);
			
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseMsgDto)));
			List<CustomerBaseMsgEntity> listCustomerBaseMsg = dto.parseData("listCustomerBaseMsg", new TypeReference<List<CustomerBaseMsgEntity>>() {});
			
			for (CustomerBaseMsgEntity customerBaseMsgEntity : listCustomerBaseMsg) {
				receiveUidList.add(customerBaseMsgEntity.getUid());
			}
		}
		if(!Check.NuNStr(request.getReceiveUid())){
			receiveUidList.add(request.getReceiveUid());
		}
		request.setReceiveUidList(receiveUidList);
	}
	
	
	/**
	 * 获取返现操作log
	 * @author lishaochuan
	 * @create 2016年9月11日下午3:04:41
	 * @param cashbackSn
	 * @return
	 */
	@RequestMapping("getLog")
	@ResponseBody
	public DataTransferObject getLog(String cashbackSn) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(cashbackSn)){
				LogUtil.error(LOGGER, "参数为空:cashbackSn:{}", cashbackSn);
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			String logJson = financeCashbackService.getLogByCashbackSn(cashbackSn);
			dto = JsonEntityTransform.json2DataTransferObject(logJson);
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}
	
	
	
	/**
	 * 审核返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:25:14
	 * @param cashbackSns
	 * @return
	 */
	@RequestMapping("audit")
	@ResponseBody
	public DataTransferObject audit(AuditCashbackRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(request)){
				LogUtil.error(LOGGER, "参数为空:request:{}", request);
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			if(Check.NuNCollection(request.getCashbackSns())){
				LogUtil.error(LOGGER, "参数为空:cashbackSns:{}", request.getCashbackSns());
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			request.setUserId(UserUtil.getCurrentUser().getUserAccount());
			//2018-1-18修改，批量审核返现单的数量在20及以上的时候，容易超时，改为dubbo的异步调用，如下order-provider.xml配置。异步并且不需要返回结果
			         //*<dubbo:method name="auditCashback" timeout="5000" retries="0" async="true" return="false"/>
			//注意，注意，注意，*****如果其他有方法经常超时，需要改为dubbo的异步调用，一定记得将调用层（一般是controller层）的返回结果的解析给去掉，不然会报空指针异常。因为返回为null
			financeCashbackService.auditCashback(JsonEntityTransform.Object2Json(request));
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}
	
	
	
	/**
	 * 驳回返现
	 * @author lishaochuan
	 * @create 2016年9月9日下午8:25:14
	 * @param cashbackSns
	 * @return
	 */
	@RequestMapping("reject")
	@ResponseBody
	public DataTransferObject reject(AuditCashbackRequest request) {
		DataTransferObject dto = new DataTransferObject();
		try{
			if(Check.NuNObj(request)){
				LogUtil.error(LOGGER, "参数为空:request:{}", request);
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			if(Check.NuNCollection(request.getCashbackSns())){
				LogUtil.error(LOGGER, "参数为空:cashbackSns:{}", request.getCashbackSns());
				dto.setErrCode(DataTransferObject.ERROR);
	            dto.setMsg("参数为空");
	            return dto;
			}
			request.setUserId(UserUtil.getCurrentUser().getUserAccount());
			String cashbackJson = financeCashbackService.rejectCashback(JsonEntityTransform.Object2Json(request));
			dto = JsonEntityTransform.json2DataTransferObject(cashbackJson);
			
		}catch(Exception e){
			LogUtil.error(LOGGER, "e:{}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
		}
		return dto;
	}
	
	/**
	 * 
	 * 返现申请权限配置
	 *
	 * @author yd
	 * @created 2016年10月31日 下午3:51:31
	 *
	 * @param authMenu
	 * @param evaluateRequest
	 */
	private boolean addAuthData(Object authMenu,OrderCashbackRequest paramRequest){
		
		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(LOGGER, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(LOGGER, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 返现审核权限配置
	 *
	 * @author bushujie
	 * @created 2016年11月1日 上午10:20:21
	 *
	 * @param authMenu
	 * @param paramRequest
	 * @return
	 */
	private boolean auditCashbackAddAuthData(Object authMenu,AuditCashbackQueryRequest paramRequest){
		
		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(LOGGER, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(LOGGER, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(LOGGER, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}
	
}
