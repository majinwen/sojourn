package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerResourceEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>用户角色关系表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/7/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("customer.customerResourceDao")
public class CustomerResourceDao {


    private String SQLID="customer.customerResourceDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 保存用户角色信息
     * @author afi
     * @param customerResourceEntity
     * @return
     */
    public int insertCustomerResource(CustomerResourceEntity customerResourceEntity){
        if(Check.NuNObj(customerResourceEntity)){
            throw new BusinessException("customerResourceEntity is null");
        }
        if (Check.NuNStr(customerResourceEntity.getFid())){
            customerResourceEntity.setFid(UUIDGenerator.hexUUID());
        }
        return  mybatisDaoContext.save(SQLID + "insertCustomerResource", customerResourceEntity);
    }


    /**
     * 冻结当前用户
     * @author afi
     * @param fid
     * @return
     */
    public int frozenCustomerResource(String fid){
        if(Check.NuNObj(fid)){
            throw new BusinessException("fid is null");
        }
        Map par = new HashMap();
        par.put("fid",fid);
        par.put("isFrozen", YesOrNoEnum.YES.getCode());
        return  mybatisDaoContext.update(SQLID + "updateCustomerResourceFrozen", par);
    }
    /**
     * 冻结并禁止
     * @author jixd
     * @created 2017年05月26日 11:20:34
     * @param
     * @return
     */
    public int frozenAndBanCustomerResource(String fid){
        if(Check.NuNObj(fid)){
            throw new BusinessException("fid is null");
        }
        Map par = new HashMap();
        par.put("fid",fid);
        par.put("isFrozen", YesOrNoEnum.YES.getCode());
        par.put("isBan",YesOrNoEnum.YES.getCode());
        return  mybatisDaoContext.update(SQLID + "updateCustomerResourceFrozen", par);
    }

    /**
     * 冻结当前用户
     * @author afi
     * @param fid
     * @return
     */
    public int unfrozenCustomerResource(String fid){
        if(Check.NuNObj(fid)){
            throw new BusinessException("fid is null");
        }
        Map par = new HashMap();
        par.put("fid",fid);
        par.put("isFrozen", YesOrNoEnum.NO.getCode());
        return  mybatisDaoContext.update(SQLID + "updateCustomerResourceFrozen", par);
    }



    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    public List<CustomerRoleEntity> getCustomerAllRoles(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return mybatisDaoContext.findAll(SQLID + "getCustomerAllRoles", CustomerRoleEntity.class, uid);
    }



	/**
	 * 根据uid获取所有角色
	 *
	 * @author loushuai
	 * @created 2017年5月13日 下午12:03:03
	 *
	 * @param uid
	 * @return
	 */
	public List<CustomerResourceEntity> getAllRolesByUid(String uid) {
		return  mybatisDaoContext.findAll(SQLID + "getAllRolesByUid", CustomerResourceEntity.class, uid);
	}


	/**
	 * 将t_customer_resource表中的 is_frozen  is_ban都改为1
	 * 取消天使房东资格，一年内不能申请
	 *
	 * @author loushuai
	 * @created 2017年5月24日 下午5:00:59
	 *
	 * @param fid
	 * @return
	 */
	public int frozenRoleInOneYear(String fid) {
		  if(Check.NuNObj(fid)){
	            throw new BusinessException("fid is null");
	        }
	        Map par = new HashMap();
	        par.put("fid",fid);
	        par.put("isFrozen", YesOrNoEnum.YES.getCode());
	        par.put("isBan", YesOrNoEnum.YES.getCode());
		return mybatisDaoContext.update(SQLID + "frozenRoleInOneYear", par);
	}

}
