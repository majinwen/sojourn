package com.ziroom.minsu.services.customer.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.customer.CustomerResourceEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.entity.customer.CustomerRoleEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.customer.dao.CustomerResourceDao;
import com.ziroom.minsu.services.customer.dao.CustomerRoleBaseDao;
import com.ziroom.minsu.services.customer.dao.CustomerRoleInfoDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
@Service("customer.customerRoleServiceImpl")
public class CustomerRoleServiceImpl {

    @Resource(name="customer.customerRoleBaseDao")
    private CustomerRoleBaseDao customerRoleBaseDao;


    @Resource(name="customer.customerResourceDao")
    private CustomerResourceDao customerResourceDao;


    @Resource(name="customer.customerRoleInfoDao")
    private CustomerRoleInfoDao customerRoleInfoDao;







    /**
     * 获取当前用户的角色信息
     * @author afi
     * @return
     */
    public  PagingResult<CustomerRoleBaseEntity> getBaseRolesByPage(PageRequest pageRequest){

        return customerRoleBaseDao.getBaseRolesByPage(pageRequest);
    }


    /**
     * 获取当前用户的角色信息
     * @author afi
     * @return
     */
    public List<CustomerRoleBaseEntity> getBaseRoles(){

        return customerRoleBaseDao.getBaseRoles();
    }


    /**
     * 获取当前的基础角色信息
     * @author afi
     * @param roleType
     * @return
     */
    public CustomerRoleBaseEntity getCustomerRoleBaseByType(String roleType){
        if(Check.NuNStr(roleType)){
            return null;
        }
        return customerRoleBaseDao.getCustomerRoleBaseByType(roleType);
    }


    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    public List<CustomerRoleEntity> getCustomerRoles(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return customerRoleInfoDao.getCustomerRolesInfoByUid(uid);
    }

    /**
      * 获取当前用户的角色信息,不包含是否冻结
      * @author wangwentao
      * @created 2017/5/25 18:26
      *
      * @param
      * @return
      */
    public List<CustomerRoleEntity> getCustomerRolesWithoutFrozen(String uid){
        if(Check.NuNStr(uid)){
            return null;
        }
        return customerRoleInfoDao.getCustomerRolesInfoByUidS(uid);
    }

    /**
     * 获取当前用户的角色信息
     * @author afi
     * @param uid
     * @return
     */
    public CustomerRoleEntity getCustomerRoleByType(String uid,String roleType){
        if(Check.NuNStr(uid) || Check.NuNStr(roleType)){
            return null;
        }
        return customerRoleInfoDao.getCustomerRoleByType(uid, roleType);
    }


    /**
     * 保存用户角色信息
     * @author afi
     * @param customerResourceEntity
     * @return
     */
    public int insertCustomerResource(CustomerResourceEntity customerResourceEntity){
        return customerResourceDao.insertCustomerResource(customerResourceEntity);
    }



    /**
     * 解冻当前用户
     * @author afi
     * @param fid
     * @return
     */
    public int unfrozenCustomerRole(String fid){
        return customerResourceDao.unfrozenCustomerResource(fid);
    }

    /**
     * 冻结当前用户
     * @author afi
     * @param fid
     * @return
     */
    public int frozenCustomerRole(String fid){
        return customerResourceDao.frozenCustomerResource(fid);
    }

    /**
     * 禁止
     * @author jixd
     * @created 2017年05月26日 11:21:57
     * @param
     * @return
     */
    public int frozenAndBanCustomerResource(String fid){
        return customerResourceDao.frozenAndBanCustomerResource(fid);
    }


	/**
	 * 根据用户uid查询t_customer_resource获取所有的角色
	 *
	 * @author loushai
	 * @created 2017年5月13日 上午11:59:00
	 *
	 * @param uid
	 * @return
	 */
	public List<CustomerResourceEntity> getAllRolesByUid(String uid) {
		return customerResourceDao.getAllRolesByUid(uid);
	}


	/**
	 * 将t_customer_resource表中的 is_frozen  is_ban都改为1
	 * 取消天使房东资格，一年内不能申请
	 * @author loushuai
	 * @created 2017年5月24日 下午4:58:13
	 *
	 * @param fid
	 */
	public int frozenRoleInOneYear(String fid) {
		return customerResourceDao.frozenRoleInOneYear(fid);
	}


}
