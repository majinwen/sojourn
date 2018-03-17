package com.ziroom.minsu.mapp.customer.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ziroom.minsu.services.basedata.api.inner.ZkSysService;
import com.ziroom.minsu.services.common.conf.EnumMinsuConfig;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.mapp.common.util.CustomerVoUtils;
import com.ziroom.minsu.services.common.utils.PicUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.services.customer.dto.CustomerPicDto;
import com.ziroom.minsu.services.customer.entity.CustomerVo;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerIdTypeEnum;
import com.ziroom.minsu.valenum.customer.CustomerPicTypeEnum;

/**
 * <p>权限认证</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/29.
 * @version 1.0
 * @since 1.0
 */
@RequestMapping("auth")
@Controller
public class AuthController {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;


    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;
    
    @Value("#{'${pic_base_addr_mona}'.trim()}")
	private String picBaseAddrMona;

	@Value("#{'${detail_big_pic}'.trim()}")
	private String detail_big_pic;

    @Value("#{'${LOGIN_UNAUTH}'.trim()}")
    private String LOGIN_UNAUTH;

    @Resource(name="basedata.zkSysService")
    private ZkSysService zkSysService;
    /**
     * 校验当前的认证信息
     * @author afi
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/init")
    public String initCustomerCenter(HttpServletRequest request,Model model,String checkFlag) {
        try {
            //提示升级
            String zkSysValue = zkSysService.getZkSysValue(EnumMinsuConfig.minsu_isOpenNewVersion.getType(), EnumMinsuConfig.minsu_isOpenNewVersion.getCode());

            if ("1".equals(zkSysValue)){
                return "common/upgrade";
            }

            CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
            String uid = customerVo.getUid();
            model.addAttribute("customerVo", customerVo);
            String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
            DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
            CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {});
            //校验当前的
            if(!Check.NuNObj(entity)){
                model.addAttribute("customer", entity);
            }
            //身份信息
            Integer isIdentityAuth = entity.getIsIdentityAuth();
            //联系方式
            Integer isContactAuth = entity.getIsContactAuth();
            //真实头像
            Integer isUploadIcon = entity.getIsUploadIcon();
            
            //头像 个人介绍  昵称 
            Integer isFinishHead = YesOrNoEnum.NO.getCode();
            
            String nickName = entity.getNickName();
            Integer isFinishNickName = YesOrNoEnum.NO.getCode();
            if(!Check.NuNStr(nickName)){
            	isFinishNickName = YesOrNoEnum.YES.getCode();
            }
            Integer isFinishIntroduce = YesOrNoEnum.NO.getCode();
            DataTransferObject customerExtDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(uid));
            if(customerExtDto.getCode() == DataTransferObject.SUCCESS){
            	CustomerBaseMsgExtEntity customerBaseMsgExt = customerExtDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
            	if(!Check.NuNObj(customerBaseMsgExt) && !Check.NuNStr(customerBaseMsgExt.getCustomerIntroduce())){
            		isFinishIntroduce = YesOrNoEnum.YES.getCode();
            	}
            }
			
            if(isUploadIcon == YesOrNoEnum.YES.getCode() 
                    && isFinishNickName == YesOrNoEnum.YES.getCode() 
                    && isFinishIntroduce == YesOrNoEnum.YES.getCode()){
            	isFinishHead = YesOrNoEnum.YES.getCode();
            }
			
            int fullFlag = YesOrNoEnum.NO.getCode();
            if(isIdentityAuth == YesOrNoEnum.YES.getCode()
                    && isContactAuth == YesOrNoEnum.YES.getCode()
                    && isFinishHead == YesOrNoEnum.YES.getCode()){
                fullFlag = YesOrNoEnum.YES.getCode();
            }

            if(fullFlag == YesOrNoEnum.YES.getCode()
                    && ValueUtil.getintValue(checkFlag) == YesOrNoEnum.NO.getCode()){
                //如果当前已经填写完成,并且不需要校验，直接跳过走发布房源的流程
                return "redirect:/houseDeploy/"+LOGIN_UNAUTH+"/toHouseType";
            }else {
            	model.addAttribute("isFinishHead", isFinishHead);
                model.addAttribute("isIdentityAuth", isIdentityAuth);
                model.addAttribute("isContactAuth", isContactAuth);
                model.addAttribute("isUploadIcon", isUploadIcon);
                model.addAttribute("fullFlag", fullFlag);
                return "auth/init";
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
        }
        return "auth/init";
    }




    /**
     * 初始化修改信息
     * @author afi
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/cardInfo")
    public String initSaveBase(HttpServletRequest request,Model model) {

        CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
        String uid = customerVo.getUid();
        model.addAttribute("customerVo", customerVo);
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
        CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
        });

        customerVo.setRealName(entity.getRealName());
        customerVo.setIdNo(entity.getIdNo());
        customerVo.setIdType(entity.getIdType());

        List<CustomerIdTypeEnum> idTypeList = CustomerIdTypeEnum.getCustomerIdTypeEnums();
        model.addAttribute("customer",customerVo);
        model.addAttribute("idTypeList",idTypeList);
        return "auth/cardInfo";
    }


    /**
     * 初始化修改电话
     * @author afi
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/initSaveTel")
    public String initSaveTel(HttpServletRequest request,Model model) {

        CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
        String uid = customerVo.getUid();
        model.addAttribute("customerVo", customerVo);
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
        CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
        });
        String customerMobile = entity.getCustomerMobile();
        if(Check.NuNStr(customerMobile)){
            return "auth/telInfoAdd";
        }else {
            model.addAttribute("customerMobile",customerMobile);
            return "auth/telInfoEdit";
        }
    }


    /**
     * 初始化修改电话
     * @author afi
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/initAddTel")
    public String initAddTel(HttpServletRequest request,Model model) {

        CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
        String uid = customerVo.getUid();
        model.addAttribute("customerVo", customerVo);
        String customerJson = customerMsgManagerService.getCustomerBaseMsgEntitybyUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerJson);
        CustomerBaseMsgEntity entity = customerDto.parseData("customer", new TypeReference<CustomerBaseMsgEntity>() {
        });
        String customerMobile = entity.getCustomerMobile();
        model.addAttribute("customerMobile",customerMobile);
        return "auth/telInfoAdd";
    }

    /**
     * 保存
     * @param request
     * @param customerBase
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/saveBaseInf")
    @ResponseBody
    public DataTransferObject saveBaseInf(HttpServletRequest request,CustomerBaseMsgEntity customerBase) {
        DataTransferObject dto = null;
        if(Check.NuNObj(customerBase)){
            dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
            String uid = customerVo.getUid();
            customerBase.setUid(uid);
            if(!Check.NuNStr(customerBase.getRealName())
                    && !Check.NuNStr(customerBase.getIdNo())
                    && !Check.NuNObj(customerBase.getIdType())){
                customerBase.setIsIdentityAuth(YesOrNoEnum.YES.getCode());
            }else {
                customerBase.setIsIdentityAuth(YesOrNoEnum.NO.getCode());
            }
            String saveJson =customerMsgManagerService.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(customerBase));
            dto = JsonEntityTransform.json2DataTransferObject(saveJson);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.info(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
        }
        return dto;
    }


    /**
     * 保存 电话信息
     * @author afi
     * @param request
     * @param customerMobile
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/saveTel")
    @ResponseBody
    public DataTransferObject saveTel(HttpServletRequest request,String customerMobile) {
        DataTransferObject dto = null;
        if(Check.NuNStr(customerMobile)){
            dto = new DataTransferObject();
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数异常");
            return dto;
        }
        try {
            CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
            String uid = customerVo.getUid();
            CustomerBaseMsgEntity customerBase = new CustomerBaseMsgEntity();
            customerBase.setUid(uid);
            customerBase.setCustomerMobile(customerMobile);
            customerBase.setIsContactAuth(YesOrNoEnum.YES.getCode());
            String saveJson = customerMsgManagerService.updateCustomerBaseMsg(JsonEntityTransform.Object2Json(customerBase));
            dto = JsonEntityTransform.json2DataTransferObject(saveJson);
        } catch (Exception e) {
            dto = new DataTransferObject();
            LogUtil.info(LOGGER, "error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("服务错误");
        }
        return dto;
    }
    
    /**
     * 
     * 跳转到上传头像页面
     *
     * @author jixd
     * @created 2016年5月30日 上午12:19:21
     *
     * @param request
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/headPic")
    public String headPic(HttpServletRequest request){
    	 CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
         String uid = customerVo.getUid();
         CustomerPicDto picdto = new CustomerPicDto();
         picdto.setUid(uid);
         picdto.setPicType(CustomerPicTypeEnum.YHTX.getCode());
         
         String customerPicJson = customerMsgManagerService.getCustomerPicByType(JsonEntityTransform.Object2Json(picdto));
         DataTransferObject customerPicDto = JsonEntityTransform.json2DataTransferObject(customerPicJson);
         String headPicUrl = "";
         if(customerPicDto.getCode() == DataTransferObject.SUCCESS){
        	 CustomerPicMsgEntity picEntity = customerPicDto.parseData("customerPicMsgEntity", new TypeReference<CustomerPicMsgEntity>() {});
        	 if(!Check.NuNObj(picEntity) && !Check.NuNStr(picEntity.getPicServerUuid())){
        		 headPicUrl = PicUtil.getFullPic(picBaseAddrMona, picEntity.getPicBaseUrl(), picEntity.getPicSuffix(), detail_big_pic);
        	 }
         }
         
         
         String introduceJson = customerMsgManagerService.selectCustomerExtByUid(customerVo.getUid());
			DataTransferObject introduceDto = JsonEntityTransform.json2DataTransferObject(introduceJson);
			if(introduceDto.getCode() == DataTransferObject.SUCCESS){
				CustomerBaseMsgExtEntity extEntity = introduceDto.parseData("customerBaseMsgExt", new TypeReference<CustomerBaseMsgExtEntity>() {});
				if(!Check.NuNObj(extEntity)){
					request.setAttribute("hasIntroduce", 1);
				}
			}
			
			Integer hasNickName = 0;
			if(!Check.NuNStr(customerVo.getNickName())){
				hasNickName = 1;
			}
		 
         request.setAttribute("hasNickName", hasNickName);
         request.setAttribute("headPicUrl", headPicUrl);
    	return "auth/headPic";
    }
    
    /**
     * 
     * 跳转到
     *
     * @author jixd
     * @created 2016年6月17日 上午10:02:31
     *
     * @return
     */
    @RequestMapping("/${LOGIN_UNAUTH}/toNickNamePage")
    public String toNickNamePage(HttpServletRequest request){
    	 CustomerVo customerVo  = CustomerVoUtils.getCusotmerVoFromSesstion(request);
    	 request.setAttribute("nickName", customerVo.getNickName());
    	return "auth/addNickName";
    }
    
}
