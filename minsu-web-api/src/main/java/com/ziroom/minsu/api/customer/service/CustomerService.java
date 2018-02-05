package com.ziroom.minsu.api.customer.service;


import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgExtEntity;
import com.ziroom.minsu.services.customer.api.inner.CustomerInfoService;
import com.ziroom.minsu.services.customer.api.inner.CustomerMsgManagerService;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/26.
 * @version 1.0
 * @since 1.0
 */
@Service("api.customerService")
public class CustomerService {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(CustomerService.class);



    @Resource(name = "customer.customerMsgManagerService")
    private CustomerMsgManagerService customerMsgManagerService;

    @Resource(name = "customer.customerInfoService")
    private CustomerInfoService customerInfoService;


    /**
     * 获取当前的用户信息
     * @param uid
     * @return
     */
    public  CustomerBaseMsgEntity getCustomerBaseMsgEntity(String uid){
        if (Check.NuNStr(uid)){
            return null;
        }
        CustomerBaseMsgEntity customerBase = null;
        String customerVoJson = customerInfoService.getCustomerInfoByUid(uid);
        DataTransferObject customerDto = JsonEntityTransform.json2DataTransferObject(customerVoJson);
        if(customerDto.getCode() == DataTransferObject.SUCCESS){
            //获取最新的用户信息
            customerBase = customerDto.parseData("customerBase", new TypeReference<CustomerBaseMsgEntity>() {});
        }
        return customerBase;
    }

    /**
     * 验证当前的房东的认证信息
     * @param customer
     * @return
     */
    public boolean checkAuthAll(CustomerBaseMsgEntity customer){
        //默认当前的认证信息
        boolean checkAll = false;
        if (Check.NuNObj(customer)){
            return checkAll;
        }
        if (Check.NuNStr(customer.getUid())){
            return checkAll;
        }
        //身份信息
        Integer isIdentityAuth = customer.getIsIdentityAuth();
        //联系方式
        Integer isContactAuth = customer.getIsContactAuth();
        //真实头像
        Integer isUploadIcon = customer.getIsUploadIcon();
        //头像 个人介绍  昵称
        Integer isFinishHead = YesOrNoEnum.NO.getCode();

        String nickName = customer.getNickName();
        Integer isFinishNickName = YesOrNoEnum.NO.getCode();
        if(!Check.NuNStr(nickName)){
            isFinishNickName = YesOrNoEnum.YES.getCode();
        }
        Integer isFinishIntroduce = YesOrNoEnum.NO.getCode();
        DataTransferObject customerExtDto = JsonEntityTransform.json2DataTransferObject(customerMsgManagerService.selectCustomerExtByUid(customer.getUid()));
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

        if(isIdentityAuth == YesOrNoEnum.YES.getCode()
                && isContactAuth == YesOrNoEnum.YES.getCode()
                && isFinishHead == YesOrNoEnum.YES.getCode()){
            checkAll = true;
        }
        return checkAll;
    }





}
