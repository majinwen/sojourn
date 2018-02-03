package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.CustomerRoleBaseEntity;
import com.ziroom.minsu.services.common.dto.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>用户的角色</p>
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
@Repository("customer.customerRoleBaseDao")
public class CustomerRoleBaseDao {

	private String SQLID="customer.customerRoleBaseDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


    /**
     * 保存用户角色信息
     * @author afi
     * @param customerRoleBaseEntity
     * @return
     */
    public int insertCustomerBaseRole(CustomerRoleBaseEntity customerRoleBaseEntity){
        if(Check.NuNObj(customerRoleBaseEntity)){
            throw new BusinessException("customerRoleBaseEntity is null");
        }
        if (Check.NuNStr(customerRoleBaseEntity.getFid())){
            customerRoleBaseEntity.setFid(UUIDGenerator.hexUUID());
        }
        return  mybatisDaoContext.save(SQLID + "insertCustomerBaseRole", customerRoleBaseEntity);
    }


    /**
     * 获取当前的基础角色信息
     * @author afi
     * @param roleType
     * @return
     */
    public CustomerRoleBaseEntity getCustomerRoleBaseByType(String roleType) {
        if (Check.NuNStr(roleType)) {
            return null;
        }
        return mybatisDaoContext.findOne(SQLID + "getCustomerRoleBaseByType", CustomerRoleBaseEntity.class,roleType);
    }

    /**
     * 获取当前用户的角色信息
     * @author afi
     * @return
     */
    public List<CustomerRoleBaseEntity> getBaseRoles(){
        return mybatisDaoContext.findAll(SQLID + "getBaseRoles", CustomerRoleBaseEntity.class);
    }


    public PagingResult<CustomerRoleBaseEntity> getBaseRolesByPage(PageRequest pageRequest) {
        PageBounds pageBounds=new PageBounds();
        pageBounds.setLimit(pageRequest.getLimit());
        pageBounds.setPage(pageRequest.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getBaseRolesByPage", CustomerRoleBaseEntity.class, null, pageBounds);
    }
}
