package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>用户的角色信息</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.customerRoleInfoDao")
public class CustomerRoleInfoDao {

	private String SQLID="customer.customerRoleInfoDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
	public List<CustomerRoleEntity> getCustomerRolesInfoByUid(String uid){
		if(Check.NuNStr(uid)){
			return null;
		}
		return mybatisDaoContext.findAll(SQLID + "getCustomerRolesInfoByUid", CustomerRoleEntity.class, uid);
	}

    /**
      * 获取当前用户的角色信息,不包含是否冻结
      * @author wangwentao
      * @created 2017/5/25 18:25
      *
      * @param
      * @return
      */
    public List<CustomerRoleEntity> getCustomerRolesInfoByUidS(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getCustomerRolesInfoByUidS", CustomerRoleEntity.class, uid);
    }




    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    public CustomerRoleEntity getCustomerRoleByType(String uid,String roleType){
        if(Check.NuNStr(uid)){
            return null;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("uid",uid);
        par.put("roleType",roleType);
        return mybatisDaoContext.findOne(SQLID + "getCustomerRoleByType", CustomerRoleEntity.class, par);
    }

}
