package com.ziroom.minsu.services.customer.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MessageSourceUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.customer.CustomerResourceEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.customer.api.inner.CustomerRoleService;
import com.ziroom.minsu.services.customer.constant.CustomerMessageConst;
import com.ziroom.minsu.services.customer.service.CustomerRoleServiceImpl;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import com.ziroom.minsu.valenum.customer.CustomerRoleEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/23.
 * @version 1.0
 * @since 1.0
 */
@Component("customer.customerRoleServiceProxy")
public class CustomerRoleServiceProxy implements CustomerRoleService{


    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerMsgManagerServiceProxy.class);

    private static final int limitDate = 12;

    @Resource(name = "customer.messageSource")
    private MessageSource messageSource;

    @Resource(name = "customer.customerRoleServiceImpl")
    private CustomerRoleServiceImpl customerRoleService;





    /**
     * 获取当前的所有的角色信
     * 分页查询
     * @author afi
     * @return
     */
    @Override
    public String getBaseRolesByPage(String paramJson){

        DataTransferObject dto = new DataTransferObject();
        try{
            PageRequest pageRequest = JsonEntityTransform.json2Object(paramJson, PageRequest.class);
            // 条件查询后台用户
            PagingResult<CustomerRoleBaseEntity> pr = customerRoleService.getBaseRolesByPage(pageRequest);
            dto.putValue("list", pr.getRows());
            dto.putValue("total", pr.getTotal());
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }



    /**
     * 获取当前的所有的角色信息
     * @author afi
     * @return
     */
    @Override
    public String getBaseRoles(){

        DataTransferObject dto = new DataTransferObject();
        try{
            List<CustomerRoleBaseEntity> roles = customerRoleService.getBaseRoles();
            dto.putValue("roles", roles);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 获取当前的所有的角色信息
     * @author afi
     * @return
     */
    @Override
    public String getBaseRolesMap(){
        DataTransferObject dto = new DataTransferObject();
        try{
            Map<String,CustomerRoleBaseEntity> map = new HashMap<>();
            List<CustomerRoleBaseEntity> roles = customerRoleService.getBaseRoles();
            if (!Check.NuNCollection(roles)){
                for (CustomerRoleBaseEntity role : roles) {
                    map.put(role.getRoleCode(),role);
                }
            }
            dto.putValue("rolesMap", map);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }



    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getCustomerRolesMap(String uid){

        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            Map<String,CustomerRoleEntity> roleMap = new HashMap();
            List<CustomerRoleEntity> roles = customerRoleService.getCustomerRoles(uid);
            if (!Check.NuNCollection(roles)){
                for (CustomerRoleEntity role : roles) {
                    roleMap.put(role.getRoleCode(),role);
                }
            }
            dto.putValue("roleMap", roleMap);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    @Override
    public String getCustomerRolesMapWithoutFrozen(String uid){

        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            Map<String,CustomerRoleEntity> roleMap = new HashMap();
            List<CustomerRoleEntity> roles = customerRoleService.getCustomerRolesWithoutFrozen(uid);
            if (!Check.NuNCollection(roles)){
                for (CustomerRoleEntity role : roles) {
                    roleMap.put(role.getRoleCode(),role);
                }
            }
            dto.putValue("roleMap", roleMap);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String getCustomerRoles(String uid) {

        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            List<CustomerRoleEntity> roles = customerRoleService.getCustomerRoles(uid);
            dto.putValue("roles", roles);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }




    /**
     * 解冻当前用户
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String unfrozenCustomerRoleByType(String uid,String roleType){
        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid) || Check.NuNStr(roleType)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            CustomerRoleEntity role = customerRoleService.getCustomerRoleByType(uid, roleType);
            //当前角色不存在
            if (Check.NuNObj(role)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("当前角色不存在或者已经失效");
                return dto.toJsonString();
            }
            if (role.getIsFrozen() == YesOrNoEnum.NO.getCode()){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("当前角色未冻结");
                return dto.toJsonString();
            }
            //验证在规定时间内是否有申请资格
            String checkIsBanJson = this.checkIsBan(uid, roleType);
            DataTransferObject checkIsBanDto = JsonEntityTransform.json2DataTransferObject(checkIsBanJson);
            LogUtil.info(LOGGER, "验证申请资格返回:{}", checkIsBanDto.toJsonString());
            if (checkIsBanDto.getCode() != DataTransferObject.SUCCESS) {
                LogUtil.error(LOGGER, "验证申请资格失败:{}", checkIsBanDto.toJsonString());
                return dto.toJsonString();
            }
            boolean isBan = checkIsBanDto.parseData("isBan", new TypeReference<Boolean>() {});
            if (!isBan) {
                LogUtil.info(LOGGER, "当前角色没有天使房东申请资格,不能解冻isBan:{}", isBan);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("当前角色没有申请资格，不能解冻");
                return dto.toJsonString();
            }

            customerRoleService.unfrozenCustomerRole(role.getFid());
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }

    /**
     * 冻结当前用户
     * @author afi
     * @param uid
     * @return
     */
    @Override
    public String frozenCustomerRoleByType(String uid,String roleType){

        DataTransferObject dto = new DataTransferObject();
        try{
            //判断是否存在uid
            if(Check.NuNStr(uid) || Check.NuNStr(roleType)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            CustomerRoleEntity role = customerRoleService.getCustomerRoleByType(uid, roleType);
            //当前角色不存在
            if (Check.NuNObj(role)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("当前角色不存在或者已经失效");
                return dto.toJsonString();
            }
            if (role.getIsFrozen() == YesOrNoEnum.YES.getCode()){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("当前角色已冻结");
                return dto.toJsonString();
            }


            customerRoleService.frozenCustomerRole(role.getFid());

        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }


    /**
     * 校验用户角色信息
     * #author afi
     * @param uid
     * @param roleCode
     * @return
     */
    public String checkCustomerRole(String uid,String roleCode){
        DataTransferObject dto = new DataTransferObject();
        try{

            //判断是否存在uid
            if(Check.NuNObj(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }
            if (Check.NuNStr(roleCode)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PARAM_NULL));
                return dto.toJsonString();
            }

            CustomerRoleBaseEntity roleBaseEntity = customerRoleService.getCustomerRoleBaseByType(roleCode);
            if (Check.NuNObj(roleBaseEntity)){
                //当前角色的有效期
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("异常的资质code");
                return dto.toJsonString();
            }

            //校验用户角色关系表中， 当前用户是否存在（包括终身和非终身）
            CustomerRoleEntity has = customerRoleService.getCustomerRoleByType(uid,roleCode);
            if (Check.NuNObj(has)){
                dto.putValue("has",0);
            }else {
                dto.putValue("has",1);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();
    }
    /**
     * 保存用户角色信息
     * @author afi
     * @param uid
     * @param roleCode
     * @return
     */
    @Override
    public String saveCustomerRole(String uid,String roleCode){
        DataTransferObject dto = new DataTransferObject();
        try{

            //判断是否存在uid
            if(Check.NuNObj(uid)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.UID_NULL_ERROR));
                return dto.toJsonString();
            }

            if (Check.NuNStr(roleCode)){
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, CustomerMessageConst.PARAM_NULL));
                return dto.toJsonString();
            }

            CustomerRoleBaseEntity roleBaseEntity = customerRoleService.getCustomerRoleBaseByType(roleCode);
            if (Check.NuNObj(roleBaseEntity)){
                //当前角色的有效期
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("异常的资质code");
                return dto.toJsonString();
            }

            //校验当前用户是否已经具有当前角色的角色是否已经存在
            CustomerRoleEntity has = customerRoleService.getCustomerRoleByType(uid,roleCode);
            if (!Check.NuNObj(has)){
                //当前角色的有效期
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, CustomerMessageConst.ERROR_CODE));
                dto.setMsg("当前角色已经存在");
                return dto.toJsonString();
            }

            //插入到数据库
            CustomerResourceEntity resourceEntity = new CustomerResourceEntity();
            resourceEntity.setUid(uid);
            resourceEntity.setRoleCode(roleCode);
            customerRoleService.insertCustomerResource(resourceEntity);
        }catch (Exception e){
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        return dto.toJsonString();

    }

    /**
     * 如果在一段时间内的，房东取消订单的次数withinSixMonth     取消客户uid的角色roleCode
     * @author loushuai
     * @param uid
     * @param roleCode
     * @return
     */
	public String cancelAngelLandlord(String uid, String roleCode) {
		DataTransferObject dto = new DataTransferObject();
		try {
			if(Check.NuNStr(uid) || Check.NuNStr(roleCode)){//校验参数是否为空
				dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
				dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
				return dto.toJsonString();
			}
			//校验roleCode对应的角色当前是否有效
			CustomerRoleBaseEntity roleBaseEntity = customerRoleService.getCustomerRoleBaseByType(roleCode);
	        if (Check.NuNObj(roleBaseEntity)){
	                //当前角色的有效期
	             dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
	             dto.setMsg("角色不存在");
	             return dto.toJsonString();
	       }
	        List<CustomerResourceEntity> allRoleList = customerRoleService.getAllRolesByUid(uid);
	        if(Check.NuNCollection(allRoleList)){//当前用户在t_customer_resource表中没有任何角色记录
                CustomerResourceEntity customerResourceEntity = new CustomerResourceEntity();
                customerResourceEntity.setUid(uid);
                customerResourceEntity.setIsBan(YesOrNoEnum.YES.getCode());
                customerResourceEntity.setIsFrozen(YesOrNoEnum.YES.getCode());
                customerResourceEntity.setRoleCode(roleCode);
                customerRoleService.insertCustomerResource(customerResourceEntity);
	        }else{
                for (CustomerResourceEntity customerResourceEntity : allRoleList) {
                    if(CustomerRoleEnum.SEED.getStr().equals(customerResourceEntity.getRoleCode())){
                        customerRoleService.frozenAndBanCustomerResource(customerResourceEntity.getFid());
                    }
                }
            }
		} catch (Exception e) {
			LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
		}
		return dto.toJsonString();
	}

	/**
	  * 检验是否有申请天使房东的资格
	  * @author wangwentao
	  * @created 2017/5/19 17:39
	  *
	  * @param 
	  * @return 
	  */
    @Override
    public String checkIsBan(String uid, String roleCode) {
        DataTransferObject dto = new DataTransferObject();
        LogUtil.info(LOGGER, "参数uid:{}",uid);
        try {
            if (Check.NuNStr(uid)) {
                dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
                return dto.toJsonString();
            }
            List<CustomerResourceEntity> allRoleList = customerRoleService.getAllRolesByUid(uid);
            dto.putValue("isBan", false);
            if(!Check.NuNCollection(allRoleList)){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -limitDate);
                Date limitDateAgo = calendar.getTime();
                for (CustomerResourceEntity customerResourceEntity : allRoleList) {
                    if (roleCode.equals(customerResourceEntity.getRoleCode())) {
                        boolean isDel = customerResourceEntity.getIsDel() == 0;
                        boolean isBan = customerResourceEntity.getIsBan() == 0;//规定时间内没有申请资格
                        boolean lessOneYear = customerResourceEntity.getLastModifyDate().after(limitDateAgo);
                        if (isDel && isBan && lessOneYear) {
                            dto.putValue("isBan", true);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }
        LogUtil.info(LOGGER, "查询结果dto:{}",dto.toJsonString());
        return dto.toJsonString();
    }
}
