package com.ziroom.minsu.api.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.api.common.abs.AbstractController;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.customer.constant.CustomerConstant;
import com.ziroom.minsu.api.customer.entity.AccountInfoVo;
import com.ziroom.minsu.api.customer.entity.BankInfoVo;
import com.ziroom.minsu.api.customer.entity.BankParVo;
import com.ziroom.minsu.api.customer.entity.TypeVo;
import com.ziroom.minsu.api.customer.service.CardService;
import com.ziroom.minsu.api.customer.service.CustomerService;
import com.ziroom.minsu.entity.base.MinsuEntity;
import com.ziroom.minsu.entity.base.MinsuShowEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.common.utils.DataFormat;
import com.ziroom.minsu.services.common.utils.JsonTransform;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.services.order.api.inner.OrderPayService;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.RentPaymentEnum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum007Enum;

/**
 * <p>个人中心接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/7.
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("/personal")
@Controller
public class LandCenterController extends AbstractController{

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(LandCenterController.class);

    @Resource(name = "api.customerService")
    private CustomerService customerService;

    @Resource(name = "api.cardService")
    private CardService cardService;

    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;

    @Resource(name = "order.orderPayService")
    private OrderPayService orderPayService;
    
    @Value("#{'${ACCOUNT.SYSTEM_SOURCE}'.trim()}")
    private String SYSTEM_SOURCE;

    /**
     * 修改结算方式
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/saveType")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveType(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        String parJson = getParJson(request);
        if (Check.NuNStr(parJson)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
        }
        try{
            MinsuEntity par = JsonTransform.json2Entity(parJson, MinsuEntity.class);
            if (Check.NuNObj(par)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
            }
            if (Check.NuNStr(par.getCode()) || Check.NuNStr(par.getValue())){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数为空"), HttpStatus.OK);
            }

            CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
            customerBase.setUid(uid);

            if (CustomerConstant.Account.check_type_code.equals(par.getCode())){
                TradeRulesEnum007Enum rule = TradeRulesEnum007Enum.getRuleByCode(ValueUtil.getStrValue(par.getValue()));
                if (Check.NuNObj(rule)){
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("异常类型"), HttpStatus.OK);
                }
                //结算方式
                customerBase.setClearingCode(ValueUtil.getStrValue(par.getValue()));

            }else if(CustomerConstant.Account.receive_type_code.equals(par.getCode())){
                RentPaymentEnum rentPaymentEnum = RentPaymentEnum.getByCode(ValueUtil.getintValue(par.getValue()));
                if (Check.NuNObj(rentPaymentEnum)){
                    return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("异常类型"), HttpStatus.OK);
                }
                //收款方式
                customerBase.setRentPayment(ValueUtil.getintValue(par.getValue()));
            }else {
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("异常参数"), HttpStatus.OK);
            }

            String saveJson = customerInfoService.updateCustomerInfo(JsonEntityTransform.Object2Json(customerBase));
            DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(saveJson);
            if(dto.getCode() == DataTransferObject.SUCCESS){
                //更新成功
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(""), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(dto.getMsg()), HttpStatus.OK);
            }

        }catch (Exception e){
            LogUtil.error(LOGGER, "saveBank is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }


    /**
     * 修改结算方式
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/initEditReceive")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> initEditReceive(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        try{
            CustomerBaseMsgEntity customerBase = customerService.getCustomerBaseMsgEntity(uid);
            if (Check.NuNObj(customerBase)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }
            TypeVo typeVo = new TypeVo();
            typeVo.setTitle(CustomerConstant.Account.receive_type_title);
            List<MinsuShowEntity> list = new ArrayList<>();
            MinsuShowEntity checkType1 = new MinsuShowEntity(CustomerConstant.Account.receive_type_my_dis);
            checkType1.setName(RentPaymentEnum.ACCOUNT.getName());
            checkType1.setCode(ValueUtil.getStrValue(RentPaymentEnum.ACCOUNT.getCode()));
            if (RentPaymentEnum.ACCOUNT.getCode() == customerBase.getRentPayment()){
                checkType1.setStatus(YesOrNoEnum.YES.getCode());
            }
            list.add(checkType1);

            MinsuShowEntity checkType2 = new MinsuShowEntity(CustomerConstant.Account.receive_type_card_dis);
            checkType2.setName(RentPaymentEnum.BANK.getName());
            checkType2.setCode(ValueUtil.getStrValue(RentPaymentEnum.BANK.getCode()));
            if (RentPaymentEnum.BANK.getCode() == customerBase.getRentPayment()){
                checkType2.setStatus(YesOrNoEnum.YES.getCode());
            }
            list.add(checkType2);
            typeVo.setList(list);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(typeVo), HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(LOGGER, "initEditReceive is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }



    /**
     * 修改结算方式
     * @author afi
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/initEditCheck")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> initEditCheck(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        try{
            CustomerBaseMsgEntity customerBase  = customerService.getCustomerBaseMsgEntity(uid);
            if (Check.NuNObj(customerBase)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }

            TypeVo typeVo = new TypeVo();
            typeVo.setTitle(CustomerConstant.Account.check_type_title);
            List<MinsuShowEntity> list = new ArrayList<>();
            MinsuShowEntity checkType1 = new MinsuShowEntity(CustomerConstant.Account.check_type_order_dis);
            checkType1.setName(TradeRulesEnum007Enum.TradeRulesEnum007001.getName());
            checkType1.setCode(TradeRulesEnum007Enum.TradeRulesEnum007001.getValue());
            if (TradeRulesEnum007Enum.TradeRulesEnum007001.getValue().equals(customerBase.getClearingCode())){
                checkType1.setStatus(YesOrNoEnum.YES.getCode());
            }
            list.add(checkType1);
            MinsuShowEntity checkType2 = new MinsuShowEntity(CustomerConstant.Account.receive_type_my_dis);
            checkType2.setName(TradeRulesEnum007Enum.TradeRulesEnum007002.getName());
            checkType2.setCode(TradeRulesEnum007Enum.TradeRulesEnum007002.getValue());
            if (TradeRulesEnum007Enum.TradeRulesEnum007002.getValue().equals(customerBase.getClearingCode())){
                checkType2.setStatus(YesOrNoEnum.YES.getCode());
            }
            list.add(checkType2);
            typeVo.setList(list);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(typeVo), HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(LOGGER, "initEditCheck is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }




    /**
     * 校验当前的银行卡
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/checkInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> checkInfo(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        try{
            Map<String,Object>  checkFlag = new HashMap<>();
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            if (customerDto.getCode() != DataTransferObject.SUCCESS){
                //获取当前的用户信息失败
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail(customerDto.getMsg()), HttpStatus.OK);
            }
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
            if (Check.NuNObj(entity)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }
            boolean isAuth = customerService.checkAuthAll(entity);
            checkFlag.put("isAuth",isAuth);

            boolean isCard = false;
            Map<?, ?> cardMap = cardService.getUserCardInfo(uid);
            if (!Check.NuNMap(cardMap)){
                isCard = true;
            }
            checkFlag.put("isCard",isCard);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(checkFlag),HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(LOGGER, "checkBank is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }



    /**
     * 我的账户 - 绑定银行卡
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/saveBank")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> saveBank(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        
        try{
            String parJson = getParJson(request);
            BankParVo parVo = JsonEntityTransform.json2Object(parJson,BankParVo.class);
            if(Check.NuNObj(parVo)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
            }
            if (Check.NuNObj(parVo.getBankCode())
                    || Check.NuNStr(parVo.getBankCardNo())){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
            }
            CustomerVo customerVo = null;
            String customerVoJson = customerMsgManagerService.getCutomerVoFromDb(uid);
            DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
            if(newDto.getCode() == DataTransferObject.SUCCESS){
                //获取最新的用户信息
                customerVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
            }
            if (Check.NuNObj(customerVo)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }

            parVo.setUid(uid);
            parVo.setSystemSource(SYSTEM_SOURCE);
            //直接保存信息
            Map<?, ?> rst = cardService.bindBankCard(parVo.toMap());
            if (cardService.invokeStatus(rst)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(rst.get("message")), HttpStatus.OK);
            }else {
            	String message = "操作失败";
            	if ("30404".equals(rst.get("errorCode"))) {
            		message = Check.NuNObj(rst.get("message")) ? message : (String) rst.get("message");
				}
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(message), HttpStatus.OK);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "saveBank is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }
    
    /**
     * 我的账户 - 解绑银行卡
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/delBank")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> delBank(HttpServletRequest request) {
    	String uid = getUserId(request);
    	if (Check.NuNStr(uid)){
    		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
    	}
    	
    	try{
    		String parJson = getParJson(request);
    		BankParVo parVo = JsonEntityTransform.json2Object(parJson,BankParVo.class);
    		if(Check.NuNObj(parVo)){
    			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
    		}
    		if (Check.NuNStr(parVo.getBankCardNo())){
    			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("参数异常"), HttpStatus.OK);
    		}
    		CustomerVo customerVo = null;
    		String customerVoJson = customerMsgManagerService.getCutomerVoFromDb(uid);
    		DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
    		if(newDto.getCode() == DataTransferObject.SUCCESS){
    			//获取最新的用户信息
    			customerVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
    		}
    		if (Check.NuNObj(customerVo)){
    			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
    		}
    		
    		parVo.setUid(uid);
    		parVo.setSystemSource(SYSTEM_SOURCE);
    		//直接保存信息
    		Map<?, ?> rst = cardService.unbindBankCard(parVo.toMap());
    		if (cardService.invokeStatus(rst)){
    			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(rst.get("message")), HttpStatus.OK);
    		}else {
    			return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK("操作失败"), HttpStatus.OK);
    		}
    	}catch (Exception e){
    		LogUtil.error(LOGGER, "delBank is error, e={}",e);
    		return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
    	}
    }

    /**
     * 我的账户信息
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/initEditBank")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> initEditBank(HttpServletRequest request) {
        String uid = getUserId(request);
        if (Check.NuNStr(uid)){
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
        }
        try{
            CustomerVo customerVo = null;
            String customerVoJson = customerMsgManagerService.getCutomerVoFromDb(uid);
            DataTransferObject newDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
            if(newDto.getCode() == DataTransferObject.SUCCESS){
                //获取最新的用户信息
                customerVo = newDto.parseData("customerVo", new TypeReference<CustomerVo>() {});
            }
            if (Check.NuNObj(customerVo)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }
            // 获取银行卡信息
            Map<?, ?> bankMap = cardService.getUserCardInfo(uid);
            BankInfoVo bankInfoVo = cardService.getBankInfo(bankMap,customerVo.getRealName());

            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(bankInfoVo), HttpStatus.OK);
        }catch (Exception e){
            LogUtil.error(LOGGER, "initEditBank is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }

    /**
     * 我的账户信息
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_AUTH}/accountInfo")
    @ResponseBody
    public ResponseEntity<ResponseSecurityDto> accountInfo(HttpServletRequest request){
        AccountInfoVo accountInfoVo = new AccountInfoVo();
        try{
            String uid = getUserId(request);
            if (Check.NuNStr(uid)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("请登录"), HttpStatus.OK);
            }
            CustomerBaseMsgEntity customerBase = null;
            String customerVoJson = customerInfoService.getCustomerInfoByUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
            if(customerDto.getCode() == DataTransferObject.SUCCESS){
                //获取最新的用户信息
                customerBase = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
            }
            if (Check.NuNObj(customerBase)){
                return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("当前用户不存在"), HttpStatus.OK);
            }
            int userType = UserTypeEnum.TENANT.getUserType();
            if(customerBase.getIsLandlord() == YesOrNoEnum.YES.getCode()){
                userType = UserTypeEnum.LANDLORD.getUserType();
            }

            //获取账户信息
            String accountJson = orderPayService.getAccountBalance(customerBase.getUid(), userType);
            DataTransferObject accountDto = JsonEntityTransform.json2DataTransferObject(accountJson);
            String balanceStr = "0.00";
            if(accountDto.getCode() == DataTransferObject.SUCCESS){
                Object balance = accountDto.getData().get("balance");
                if(!Check.NuNObj(balance)){
                    balanceStr =  DataFormat.formatHundredPrice(ValueUtil.getintValue(balance));
                }
            }
            accountInfoVo.setBalance(balanceStr);
            // 获取银行卡信息
			Map<?, ?> bankMap = cardService.getUserCardInfo(customerBase.getUid());
            //填充银行卡信息
            cardService.fillCardInfo(bankMap,accountInfoVo,customerBase.getRealName());
            //填充当前的结算方式
            this.fillCheckType(customerBase,accountInfoVo);
            //填充当前的收款方式
            this.fillReceiveType(customerBase,accountInfoVo);
            //填充提示消息
            this.fillMsg(accountInfoVo);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptOK(accountInfoVo), HttpStatus.OK);

        }catch(Exception e){
            LogUtil.error(LOGGER, "accountInfo is error, e={}",e);
            return new ResponseEntity<>(ResponseSecurityDto.responseEncryptFail("服务错误"),HttpStatus.OK);
        }
    }


    /**
     * 填充当前的结算方式
     * @author afi
     * @param customerBase
     * @param accountInfoVo
     */
    private void fillCheckType(CustomerBaseMsgEntity customerBase,AccountInfoVo accountInfoVo){
        if (Check.NuNObjs(customerBase,accountInfoVo)){
            return;
        }
        //填充结算方式
        MinsuShowEntity checkType = new MinsuShowEntity(CustomerConstant.Account.check_type_show);
        TradeRulesEnum007Enum rule = TradeRulesEnum007Enum.getRuleByCode(customerBase.getClearingCode());
        if (Check.NuNObj(rule)){
            checkType.setName(rule.getName());
            checkType.setCode(rule.getValue());
        }
        accountInfoVo.setCheckType(checkType);
    }

    /**
     * 填充当前的收款方式
     * @author afi
     * @param customerBase
     * @param accountInfoVo
     */
    private void fillReceiveType(CustomerBaseMsgEntity customerBase,AccountInfoVo accountInfoVo){
        if (Check.NuNObjs(customerBase,accountInfoVo)){
            return;
        }
        //填充结算方式
        MinsuShowEntity receiveType = new MinsuShowEntity(CustomerConstant.Account.receive_type_show);
        RentPaymentEnum paymentEnum = RentPaymentEnum.getByCode(customerBase.getRentPayment());
        if (Check.NuNObj(receiveType)){
            receiveType.setName(paymentEnum.getName());
            receiveType.setCode(ValueUtil.getStrValue(paymentEnum.getCode()));
        }
        accountInfoVo.setReceiveType(receiveType);
    }


    /**
     * 填充提示消息
     * @author afi
     * @param accountInfoVo
     */
    private void fillMsg(AccountInfoVo accountInfoVo){
        if (Check.NuNObj(accountInfoVo)){
            return;
        }
        accountInfoVo.setMsg(CustomerConstant.Account.bank_msg);
    }

}
