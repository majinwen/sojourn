package com.ziroom.minsu.troy.bill.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.AuthMenuEntity;
import com.ziroom.minsu.entity.customer.CustomerBankCardMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.finance.api.inner.BillManageService;
import com.ziroom.minsu.services.finance.dto.FinanceIncomeRequest;
import com.ziroom.minsu.services.finance.dto.FinancePayVosRequest;
import com.ziroom.minsu.services.finance.dto.PaymentVouchersRequest;
import com.ziroom.minsu.services.finance.entity.FinanceIncomeVo;
import com.ziroom.minsu.services.finance.entity.FinancePayVouchersVo;
import com.ziroom.minsu.services.finance.entity.FinancePaymentVo;
import com.ziroom.minsu.services.house.api.inner.TroyHouseMgtService;
import com.ziroom.minsu.services.order.entity.FinancePayDetailInfoVo;
import com.ziroom.minsu.troy.auth.menu.EvaluateAuthUtils;
import com.ziroom.minsu.troy.common.util.UserUtil;
import com.ziroom.minsu.troy.order.service.CallFinanceService;
import com.ziroom.minsu.valenum.base.RoleTypeEnum;
import com.ziroom.minsu.valenum.order.FinancePayFlagEnum;
import com.ziroom.minsu.valenum.order.PaySourceTypeEnum;
import com.ziroom.minsu.valenum.order.PaymentStatusEnum;
import com.ziroom.minsu.valenum.order.ReceiveTypeEnum;

/**
 * <p>账单管理
 *   说明：收款单管理  付款单管理 收入管理
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("/bill")
public class BillController {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(BillController.class);
	
	@Resource(name = "order.billManageService")
	private BillManageService billManageService;
	
	@Resource(name = "customer.customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "api.callFinanceService")
	private CallFinanceService callFinanceService;
	
	@Resource(name="house.troyHouseMgtService")
	private TroyHouseMgtService troyHouseMgtService;
	
	/**
	 * 
	 * 到收款单管理页面
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:35:19
	 *
	 * @param request
	 */
	@RequestMapping("/paymentList")
	public void toPaymentList(HttpServletRequest request){
	}
	
	/**
	 * 
	 * 条件分页查询收款单
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:42:29
	 *
	 * @param request
	 * @param paymentRequest
	 * @return
	 */
	@RequestMapping("/queryPaymentVo")
	public @ResponseBody PageResult queryPaymentVoByPage(HttpServletRequest request,@ModelAttribute("paymentRequest")PaymentVouchersRequest  paymentRequest){
		//特殊权限相关修改
		Object authMenu = request.getAttribute("authMenu");
		paymentRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
		if(!addAuthData(authMenu,paymentRequest)){
			return null;
		}
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageService.queryPaymentVoByPage(JsonEntityTransform.Object2Json(paymentRequest)));

		List<FinancePaymentVo> lsitFinancePaymentVos = dto.parseData("listFinancePaymentVo", new TypeReference<List<FinancePaymentVo>>() {
		});
		
		if(lsitFinancePaymentVos == null){
			lsitFinancePaymentVos = new ArrayList<FinancePaymentVo>();
			dto.putValue("total", 0);
		} 
		LogUtil.info(logger, "查询结果为lsitFinancePaymentVos={}", lsitFinancePaymentVos);
		PageResult pageResult = new PageResult();
		pageResult.setRows(lsitFinancePaymentVos);
		pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
		return pageResult;

	}
	
	
	/**
	 * 
	 * 到公司收入管理页面
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:35:19
	 *
	 * @param request
	 */
	@RequestMapping("/incomeList")
	public void toIncomeList(HttpServletRequest request){
	}
	
	/**
	 * 
	 * 公司收入分页查询
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:54:15
	 *
	 * @param request
	 * @param incomeRequest
	 * @return
	 */
	@RequestMapping("/queryFinanceIncome")
	public @ResponseBody PageResult queryFinanceIncomeByPage(HttpServletRequest request,@ModelAttribute("incomeRequest")FinanceIncomeRequest incomeRequest){
		
		//特殊权限相关修改
		Object authMenu = request.getAttribute("authMenu");
		incomeRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
		if(!financeIncomeAddAuthData(authMenu,incomeRequest)){
			return null;
		}
		
		DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageService.queryFinanceIncomeByPage(JsonEntityTransform.Object2Json(incomeRequest)));

		List<FinanceIncomeVo> lsitFinanceIncomeVo= dto.parseData("listFinanceIncomeVo", new TypeReference<List<FinanceIncomeVo>>() {
		});
		LogUtil.info(logger, "查询结果为lsitFinanceIncomeVo={}", lsitFinanceIncomeVo);
		PageResult pageResult = new PageResult();
		pageResult.setRows(lsitFinanceIncomeVo);
		pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
		return pageResult;
	}

	/**
	 * 收款单详情
	 * @author jixd
	 * @created 2016年5月12日 下午2:50:15
	 *
	 * @return
	 */
	@RequestMapping("/paymentDetail")
	public @ResponseBody DataTransferObject  showPaymentDetail(String fid){
		String detail = billManageService.getPaymentVoById(fid);
		DataTransferObject detailDto = JsonEntityTransform.json2DataTransferObject(detail);
		return detailDto;
	}


    /**
     * 收入详情
     * @author afi
     * @param fid
     * @return
     */
    @RequestMapping("/incomeDetail")
    public @ResponseBody DataTransferObject  showIncomeDetail(String fid){
        String detail = billManageService.getFinanceIncomeDetail(fid);
        DataTransferObject detailDto = JsonEntityTransform.json2DataTransferObject(detail);
        return detailDto;
    }



	/**
	 * 
	 * 到付款单管理页面
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:35:19
	 *
	 * @param request
	 */
	@RequestMapping("/payVouchersList")
	public void toPayVouchersList(HttpServletRequest request){
	}
	
	/**
	 * 
	 * 付款单分页查询
	 *
	 * @author yd
	 * @created 2016年4月29日 下午2:56:43
	 *
	 * @param request
	 * @param payVosRequest
	 * @return
	 */
	@RequestMapping("/queryFinancePayVos")
	public @ResponseBody PageResult queryFinancePayVosByPage(HttpServletRequest request,@ModelAttribute("payVosRequest")FinancePayVosRequest payVosRequest ){
		try {
			// 根据付款单人姓名查询，需要获取到姓名对应的uid
			String receiveName = payVosRequest.getReceiveName();
			String receiveTel = payVosRequest.getReceiveTel();
			if(!Check.NuNStr(receiveName) || !Check.NuNStr(receiveTel)){
				CustomerBaseMsgDto customerBaseMsgDto = new CustomerBaseMsgDto();
				customerBaseMsgDto.setRealName(receiveName);
				customerBaseMsgDto.setCustomerMobile(receiveTel);
				
				DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(customerInfoService.selectByCondition(JsonEntityTransform.Object2Json(customerBaseMsgDto)));
				List<CustomerBaseMsgEntity> listCustomerBaseMsg = dto.parseData("listCustomerBaseMsg", new TypeReference<List<CustomerBaseMsgEntity>>() {});
				
				List<String> receiveUidList = new ArrayList<>();
				for (CustomerBaseMsgEntity customerBaseMsgEntity : listCustomerBaseMsg) {
					receiveUidList.add(customerBaseMsgEntity.getUid());
				}
				payVosRequest.setReceiveUidList(receiveUidList);
			}
			//权限相关设置
			Object authMenu = request.getAttribute("authMenu");
			payVosRequest.setRoleType(RoleTypeEnum.ADMIN.getCode());
			if(!financePayAddAuthData(authMenu,payVosRequest)){
				return null;
			}
			// 分页查询付款单
			DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.billManageService.queryFinancePayVosByPage(JsonEntityTransform.Object2Json(payVosRequest)));
			List<FinancePayVouchersVo> listFinancePayVouchersVos= dto.parseData("listFinancePayVouchersVo", new TypeReference<List<FinancePayVouchersVo>>() {});
			Set<String> uidSet = new HashSet<String>();
			for (FinancePayVouchersVo financePayVouchersVo : listFinancePayVouchersVos) {
				uidSet.add(financePayVouchersVo.getReceiveUid());
			}
			
			// 查询结果翻译用户名称
			List<String> uidList = new ArrayList<String>(uidSet);
			DataTransferObject uidDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(uidList)));
			List<CustomerBaseMsgEntity> customerList = uidDto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
			for (FinancePayVouchersVo financePayVouchersVo : listFinancePayVouchersVos) {
				for (CustomerBaseMsgEntity customerBaseMsgEntity : customerList) {
					if(!Check.NuNObj(financePayVouchersVo.getReceiveUid()) && financePayVouchersVo.getReceiveUid().equals(customerBaseMsgEntity.getUid())){
						financePayVouchersVo.setReceiveName(customerBaseMsgEntity.getRealName());
						financePayVouchersVo.setReceiveTel(customerBaseMsgEntity.getCustomerMobile());
						break;
					}
				}
			}
			
			
			LogUtil.info(logger, "查询结果为listFinancePayVouchersVos={}", listFinancePayVouchersVos);
			PageResult pageResult = new PageResult();
			pageResult.setRows(listFinancePayVouchersVos);
			pageResult.setTotal(Long.valueOf(dto.getData().get("total").toString()));
			return pageResult;
		} catch (Exception e) {
			LogUtil.error(logger,"e={}", e);
			return null;
		}
	}
	
	

	/**
	 * 
	 * 付款单详情
	 *
	 * @author jixd
	 * @created 2016年5月12日 下午2:17:58
	 * @param pvSn
	 * @return
	 */
	@RequestMapping("/payVouchersDetail")
	public String showPayVouchersDetail(HttpServletRequest request, String pvSn){
		try{
			String detail = billManageService.getPayVouchersDetail(pvSn);
			DataTransferObject detailDto = JsonEntityTransform.json2DataTransferObject(detail);
			FinancePayDetailInfoVo detailVo = detailDto.parseData("pvDetailVo", new TypeReference<FinancePayDetailInfoVo>() {});
			
			//获取收付款人名称
			List<String> uidList = new ArrayList<String>();
			uidList.add(detailVo.getReceiveUid());
			uidList.add(detailVo.getPayUid());
			DataTransferObject uidDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(uidList)));
			List<CustomerBaseMsgEntity> customerList = uidDto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
			for (CustomerBaseMsgEntity customerBaseMsgEntity : customerList) {
				if(!Check.NuNObj(detailVo.getReceiveUid()) && detailVo.getReceiveUid().equals(customerBaseMsgEntity.getUid())){
					detailVo.setReceiveName(customerBaseMsgEntity.getRealName());
				}
				if(!Check.NuNObj(detailVo.getPayUid()) && detailVo.getPayUid().equals(customerBaseMsgEntity.getUid())){
					detailVo.setPayName(customerBaseMsgEntity.getRealName());
				}
			}
			detailVo.setPaySourceName(PaySourceTypeEnum.getPaySourceTypeName(detailVo.getPaySourceType()));
			detailVo.setReceiveTypeName(ReceiveTypeEnum.getReceiveTypeEnumName(detailVo.getReceiveType()));
			
			
			//旧的银行卡信息
			String bankcardFid = detailVo.getBankcardFid();
			CustomerBankCardMsgEntity bankcard = null;
			if (!Check.NuNStr(bankcardFid)) {
				String bankJson = customerInfoService.getCustomerBankCardDbByFid(bankcardFid, detailVo.getReceiveUid());
				DataTransferObject bankDto = JsonEntityTransform.json2DataTransferObject(bankJson);
				if(bankDto.getCode() == DataTransferObject.SUCCESS){
					bankcard = bankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
					//bankcard.setBankcardNo(DataFormat.formatBankCardStar(bankcard.getBankcardNo()));
		        }
			}
			
			request.setAttribute("bankcard", bankcard);
			request.setAttribute("detail", detailVo);
			return "bill/payVouchersDetail";
		} catch (Exception e) {
			LogUtil.error(logger,"e={}", e);
			return null;
		}
	}


    /**
     * 校验是否可原路返回
     *
     * @param request
     * @param pvSn
     * @return
     * @author lishaochuan
     */
    @RequestMapping("/checkCanYlfh")
    @ResponseBody
    public DataTransferObject checkCanYlfh(HttpServletRequest request, String pvSn) {
        DataTransferObject dto = new DataTransferObject();
        try {

            return JsonEntityTransform.json2DataTransferObject(billManageService.checkCanYlfh(pvSn));

        } catch (Exception e) {
            LogUtil.error(logger, "e={}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto;
        }
    }
	
	/**
	 * 重新生成付款单前的预览详情页
	 * @author lishaochuan
	 * @create 2016年8月16日下午4:16:46
	 * @param request
	 * @param pvSn
	 * @return
	 */
	@RequestMapping("/showPreReCreatePayVourchers")
	@ResponseBody
	public DataTransferObject showPreReCreatePayVourchers(HttpServletRequest request, String pvSn){
		DataTransferObject dto = new DataTransferObject();
		try{
			// 判断付款单状态
			String detail = billManageService.getPayVouchersDetail(pvSn);
			DataTransferObject detailDto = JsonEntityTransform.json2DataTransferObject(detail);
			FinancePayDetailInfoVo detailVo = detailDto.parseData("pvDetailVo", new TypeReference<FinancePayDetailInfoVo>() {});
			
			this.checkPayFlag(dto, detailVo);
			if(dto.getCode() != DataTransferObject.SUCCESS){
				return dto;
			}
			
			//获取收付款人名称
			List<String> uidList = new ArrayList<String>();
			uidList.add(detailVo.getReceiveUid());
			uidList.add(detailVo.getPayUid());
			DataTransferObject uidDto = JsonEntityTransform.json2DataTransferObject(customerInfoService.getCustomerListByUidList(JsonEntityTransform.Object2Json(uidList)));
			List<CustomerBaseMsgEntity> customerList = uidDto.parseData("customerList", new TypeReference<List<CustomerBaseMsgEntity>>() {});
			for (CustomerBaseMsgEntity customerBaseMsgEntity : customerList) {
				if(detailVo.getReceiveUid().equals(customerBaseMsgEntity.getUid())){
					detailVo.setReceiveName(customerBaseMsgEntity.getRealName());
				}
				if(detailVo.getPayUid().equals(customerBaseMsgEntity.getUid())){
					detailVo.setPayName(customerBaseMsgEntity.getRealName());
				}
			}
			detailVo.setPaySourceName(PaySourceTypeEnum.getPaySourceTypeName(detailVo.getPaySourceType()));
			detailVo.setReceiveTypeName(ReceiveTypeEnum.getReceiveTypeEnumName(detailVo.getReceiveType()));
			
			//新的付款单号
			String parentPvSn = "";
			if("0".equals(detailVo.getParentPvSn()) || Check.NuNStr(detailVo.getParentPvSn())){
				parentPvSn = pvSn;
			}else{
				parentPvSn = detailVo.getParentPvSn();
			}
			DataTransferObject countDto = JsonEntityTransform.json2DataTransferObject(billManageService.countReCreatePvs(parentPvSn));
			long count = ValueUtil.getlongValue(countDto.getData().get("count"));
			String newPvSn = parentPvSn + "_" + (++count);
			dto.getData().put("newPvSn", newPvSn);
			
			//新的银行卡信息
			String bankJson = customerInfoService.getCustomerBankcard(detailVo.getReceiveUid());
	        DataTransferObject newBbankDto = JsonEntityTransform.json2DataTransferObject(bankJson);
	        CustomerBankCardMsgEntity newBankcard = null;
	        if(newBbankDto.getCode() == DataTransferObject.SUCCESS){
	        	newBankcard = newBbankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
	        }
	        
	        
	        //旧的银行卡信息
			String bankcardFid = detailVo.getBankcardFid();
			CustomerBankCardMsgEntity oldBankcard = null;
			if (!Check.NuNStr(bankcardFid)) {
				String oldBankJson = customerInfoService.getCustomerBankCardDbByFid(bankcardFid, detailVo.getReceiveUid());
				DataTransferObject oldBankDto = JsonEntityTransform.json2DataTransferObject(oldBankJson);
				if(oldBankDto.getCode() == DataTransferObject.SUCCESS){
		        	oldBankcard = oldBankDto.parseData("bankcard", new TypeReference<CustomerBankCardMsgEntity>() {});
		        }
			}
			
			//是否修改银行卡信息
			boolean isDiffBank = false;
			if (Check.NuNObj(newBankcard)) {
				isDiffBank = false;
			} else if (Check.NuNObj(oldBankcard)) {
				isDiffBank = true;
			} else if (Check.NuNStr(newBankcard.getBankcardHolder()) || Check.NuNStr(newBankcard.getBankName()) || Check.NuNStr(newBankcard.getBankcardNo())) {
				isDiffBank = false;
			} else if (!newBankcard.getBankcardHolder().equals(oldBankcard.getBankcardHolder())) {
				isDiffBank = true;
			} else if (!newBankcard.getBankName().equals(oldBankcard.getBankName())) {
				isDiffBank = true;
			} else if (!newBankcard.getBankcardNo().equals(oldBankcard.getBankcardNo())) {
				isDiffBank = true;
			}
			
			
			/*if(!Check.NuNObj(newBankcard)){
	    		newBankcard.setBankcardNo(DataFormat.formatBankCardStar(newBankcard.getBankcardNo()));
	    	}*/
	    	dto.getData().put("bankcard", newBankcard);
	    	dto.getData().put("isDiffBank", isDiffBank);
	    	dto.getData().put("detailVo", detailVo);
			return dto;
		} catch (Exception e) {
			LogUtil.error(logger,"e={}", e);
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
            return dto;
		}
	}
	
	
	/**
	 * 重新生成付款单
	 * @author lishaochuan
	 * @create 2016年8月16日下午8:59:07
	 * @param request
	 * @param pvSn
	 * @return
	 */
	@RequestMapping("/reCreatePayVourchers")
	@ResponseBody
	public DataTransferObject reCreatePayVourchers(HttpServletRequest request, String pvSn){
		DataTransferObject dto = new DataTransferObject();
		String detail = billManageService.getPayVouchersDetail(pvSn);
		DataTransferObject detailDto = JsonEntityTransform.json2DataTransferObject(detail);
		FinancePayDetailInfoVo detailVo = detailDto.parseData("pvDetailVo", new TypeReference<FinancePayDetailInfoVo>() {});
		
		this.checkPayFlag(dto, detailVo);
		if(dto.getCode() != DataTransferObject.SUCCESS){
			return dto;
		}
		
		dto = JsonEntityTransform.json2DataTransferObject(billManageService.reCreatePvs(pvSn, UserUtil.getCurrentUser().getUserAccount()));
		return dto;
	}
	
	
	/**
	 * 调用财务查询付款状态
	 * @author lishaochuan
	 * @create 2016年8月30日下午5:16:58
	 * @param dto
	 * @param detailVo
	 */
	private void checkPayFlag(DataTransferObject dto, FinancePayDetailInfoVo detailVo){
		if(PaymentStatusEnum.FAILED_PAY_UNDO.getCode() != detailVo.getPaymentStatus()){
			dto.setErrCode(1);
            dto.setMsg("只有"+PaymentStatusEnum.FAILED_PAY_UNDO.getName()+"状态的才能重新生成！");
			return;
		}
		
		// 先调财务接口判断当前付款单是否是真的失败，只有真正失败时才走以下逻辑
		Map<String, Object> callPayVoucherFlag = callFinanceService.callPayVoucherFlag(detailVo.getOrderSn(), detailVo.getPvSn());
		if(!Check.NuNObj(callPayVoucherFlag.get("result")) && (int)callPayVoucherFlag.get("result") != 1){
			dto.setErrCode(1);
            dto.setMsg("调用获取付款状态接口失败");
			return;
		}
		if(Check.NuNObj(callPayVoucherFlag.get("payFlag"))){
			dto.setErrCode(1);
            dto.setMsg("未获取到付款状态");
			return;
		}
		String payFlag = String.valueOf(callPayVoucherFlag.get("payFlag"));
		if(!FinancePayFlagEnum.EXCEPTION.getCode().equals(payFlag) && !FinancePayFlagEnum.NONE.getCode().equals(payFlag)){
			dto.setErrCode(1);
            dto.setMsg("财务付款状态不允许重新生成，财务付款状态:"+FinancePayFlagEnum.getNameByCode(payFlag));
			return;
		}
	}
	
	/**
	 * 
	 * 付款单权限配置
	 *
	 * @author bushujie
	 * @created 2016年11月1日 下午1:57:30
	 *
	 * @param authMenu
	 * @param paramRequest
	 * @return
	 */
	private boolean financePayAddAuthData(Object authMenu,FinancePayVosRequest paramRequest){
		
		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(logger, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(logger, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(logger, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 收款单权限配置
	 *
	 * @author bushujie
	 * @created 2016年11月1日 下午2:34:06
	 *
	 * @param authMenu
	 * @param paramRequest
	 * @return
	 */
	private boolean addAuthData(Object authMenu,PaymentVouchersRequest paramRequest){
		
		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(logger, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(logger, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(logger, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 收入查询权限配置
	 *
	 * @author bushujie
	 * @created 2016年11月1日 下午2:34:06
	 *
	 * @param authMenu
	 * @param paramRequest
	 * @return
	 */
	private boolean financeIncomeAddAuthData(Object authMenu,FinanceIncomeRequest paramRequest){
		
		boolean addFlag = false;
		//权限过滤
		if(!Check.NuNObj(authMenu)){
			AuthMenuEntity authMenuEntity = (AuthMenuEntity)authMenu;
			if(!Check.NuNObj(authMenuEntity.getRoleType())&&authMenuEntity.getRoleType().intValue()>0){
				paramRequest.setRoleType(authMenuEntity.getRoleType());
				DataTransferObject authDto =  EvaluateAuthUtils.getAuthHouseFids(authMenuEntity, troyHouseMgtService);
				if(authDto.getCode() == DataTransferObject.ERROR){
					LogUtil.error(logger, "当前菜单类型：{},权限异常error={}", "查看评价",authDto.getMsg());
					return addFlag;
				}
				try {
					List<String> fids = SOAResParseUtil.getListValueFromDataByKey(authDto.toJsonString(), "houseFids", String.class);
					if(Check.NuNCollection(fids)){
						LogUtil.error(logger, "当前菜单类型：{},无权限，fids={}", "查看评价",fids);
						return addFlag;
					}
					paramRequest.setHouseFids(fids);
				} catch (SOAParseException e) {
					LogUtil.error(logger, "评价权限查询房源集合异常e={}", e);
					return addFlag;
				}
			}
		}
		return true;
	}
}
