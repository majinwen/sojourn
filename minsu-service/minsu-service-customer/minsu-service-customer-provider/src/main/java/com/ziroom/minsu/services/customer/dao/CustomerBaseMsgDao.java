/**
 * @FileName: CustomerBaseMsgDao.java
 * @Package com.ziroom.minsu.services.customer.dao
 * 
 * @author bushujie
 * @created 2016年4月22日 下午4:08:48
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBaseMsgDto;
import com.ziroom.minsu.services.customer.dto.CustomerExtDto;
import com.ziroom.minsu.services.customer.dto.StaticsCusBaseReqDto;
import com.ziroom.minsu.services.customer.entity.CustomerAuthVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailImageVo;
import com.ziroom.minsu.services.customer.entity.CustomerDetailVo;
import com.ziroom.minsu.services.customer.entity.CustomerExt;

/**
 * <p>客户基本信息Dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
@Repository("customer.customerBaseMsgDao")
public class CustomerBaseMsgDao {

	private String SQLID="customer.customerBaseMsgDao.";

	@Autowired
	@Qualifier("customer.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;


	/**
	 * 
	 * 更新客户联系方式信息
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午4:15:46
	 *
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public int updateCustomerBaseMsg(CustomerBaseMsgEntity customerBaseMsgEntity){
		return mybatisDaoContext.update(SQLID+"updateCustomerBaseMsg", customerBaseMsgEntity);
	}

	
	/**
	 * 
	 * api同步用户 部分更新用户（其他接口 不要用）
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午4:15:46
	 *
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public int updateCustomerBaseMsgPortion(CustomerBaseMsgEntity customerBaseMsgEntity){
		return mybatisDaoContext.update(SQLID+"updateCustomerBaseMsg", customerBaseMsgEntity);
	}
	/**
	 * 
	 * 插入客户基本信息
	 *
	 * @author bushujie
	 * @created 2016年4月22日 下午4:22:02
	 *
	 * @param customerBaseMsgEntity
	 */
	public void insertCustomerBaseMsg(CustomerBaseMsgEntity customerBaseMsgEntity){
		mybatisDaoContext.save(SQLID+"insertCustomerBaseMsg", customerBaseMsgEntity);
	}
	/**
	 * 
	 * 查询客户基本信息
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午9:56:50
	 *
	 * @return list
	 */
	public PagingResult<CustomerBaseMsgEntity> queryCustomerBaseMsg(CustomerBaseMsgDto customerBaseMsgDto){
		if(Check.NuNObj(customerBaseMsgDto)){
			throw new BusinessException("customerBaseMsgDto is null");
		}
		PageBounds pageBounds=new PageBounds();
		pageBounds.setLimit(customerBaseMsgDto.getLimit());
		pageBounds.setPage(customerBaseMsgDto.getPage());
		return mybatisDaoContext.findForPage(SQLID + "selectByCondition", CustomerBaseMsgEntity.class, customerBaseMsgDto, pageBounds);
	}


    /**
     * 获取用户的列表
     * @author afi
     * @param customerExtDto
     * @return
     */
    public PagingResult<CustomerExt> getCustomerExtList(CustomerExtDto customerExtDto){
        if(Check.NuNObj(customerExtDto)){
            throw new BusinessException("customerExtDto is null");
        }
        PageBounds pageBounds=new PageBounds();
        pageBounds.setLimit(customerExtDto.getLimit());
        pageBounds.setPage(customerExtDto.getPage());
        if (Check.NuNStr(customerExtDto.getRoleCode())){
        	return mybatisDaoContext.findForPage(SQLID + "getCustomerExtAllList", CustomerExt.class, customerExtDto, pageBounds);
        }else {
            return mybatisDaoContext.findForPage(SQLID + "getCustomerExtList", CustomerExt.class, customerExtDto, pageBounds);
        }


    }

	/**
	 * 
	 * 根据uid查询客户信息
	 *
	 * @author jixd
	 * @created 2016年4月23日 下午10:58:11
	 *
	 * @param uid
	 * @return
	 */
	public CustomerBaseMsgEntity queryCustomerBaseMsgByUid(String uid){
		if(Check.NuNStr(uid)){
			throw new BusinessException("uid is null");
		}
		return mybatisDaoContext.findOne(SQLID+"selectByUid", CustomerBaseMsgEntity.class, uid);
	}
	
	/**
	 * 根据uidList批量查询客户信息
	 * 注意uidList不要太长
	 * @author lishaochuan
	 * @create 2016年8月10日下午2:06:10
	 * @param uidList
	 * @return
	 */
	public List<CustomerBaseMsgEntity> queryCustomerListByUidList(List<String> uidList){
		if(Check.NuNCollection(uidList)){
			throw new BusinessException("uidList is null");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uidList", uidList);
		return mybatisDaoContext.findAll(SQLID+"selectByUidList", CustomerBaseMsgEntity.class, map);
	}

	/**
	 * 
	 * 查询客户详细信息
	 *
	 * @author jixd
	 * @created 2016年4月25日 上午9:49:57
	 *
	 * @param uid
	 * @return
	 */
	public CustomerDetailVo getCustomerDetail(String uid){
		if(Check.NuNStr(uid)){
			throw new BusinessException("uid is null");
		}
		return mybatisDaoContext.findOne(SQLID+"getCustomerDeatilVoByUid", CustomerDetailVo.class,uid);
	}
	/**
	 * 
	 * 客户信息+图片
	 *
	 * @author jixd
	 * @created 2016年5月25日 下午9:51:43
	 *
	 * @param uid
	 * @return
	 */
	public CustomerDetailImageVo getCustomerDetailImage(String uid){
		if(Check.NuNStr(uid)){
			throw new BusinessException("获取用户信息UID不能为空");
		}
		return mybatisDaoContext.findOne(SQLID+"getCustomerDetailImageByUid", CustomerDetailImageVo.class,uid);
	}
	

	/**
	 * 
	 * 条件查询用户基本信息（条件不让为null 为null后就直接查全库了）
	 * 
	 * @author yd
	 * @created 2016年5月10日 下午5:36:54
	 *
	 * @param customerDto
	 * @return
	 */
	public List<CustomerBaseMsgEntity> selectByCondition(CustomerBaseMsgDto customerBaseDto){

		if(Check.NuNObj(customerBaseDto)){
			return null;
		}
		return mybatisDaoContext.findAll(SQLID + "selectByCondition",CustomerBaseMsgEntity.class, customerBaseDto);
	}
	
	/**
	 * 
	 * 手机号查询客户信息
	 *
	 * @author bushujie
	 * @created 2016年7月11日 下午4:47:37
	 *
	 * @param mobile
	 * @return
	 */
	public CustomerBaseMsgEntity getCustomerByMobile(String mobile){
		return mybatisDaoContext.findOne(SQLID+"getCustomerByMobile", CustomerBaseMsgEntity.class, mobile);
	}
	
	public List<CustomerBaseMsgEntity> getCustomerListByMobile(String mobile){
		return mybatisDaoContext.findAll(SQLID+"getCustomerListByMobile", CustomerBaseMsgEntity.class, mobile);
	}

	/**
	 *
	 * 查询客户认证信息
	 *
	 * @author bushujie
	 * @created 2016年7月26日 下午8:01:42
	 *
	 * @param uid
	 * @return
	 */
	public CustomerAuthVo getCustomerAuthData(String uid){
		return mybatisDaoContext.findOne(SQLID+"getCustomerAuthData", CustomerAuthVo.class, uid);
	}


	/**
     * 获取业主的列表
     * @author liyingjie
     * @param customerExtDto
     * @return
     */
    public PagingResult<CustomerBaseMsgEntity> staticsGetLandlordList(StaticsCusBaseReqDto staticsCusBaseReqDto){
        if(Check.NuNObj(staticsCusBaseReqDto)){
            throw new BusinessException("staticsCusBaseReqDto is null");
        }
        PageBounds pageBounds=new PageBounds();
        pageBounds.setLimit(staticsCusBaseReqDto.getLimit());
        pageBounds.setPage(staticsCusBaseReqDto.getPage());

        return mybatisDaoContext.findForPage(SQLID + "staticsGetLandlordList", CustomerBaseMsgEntity.class, null, pageBounds);
    }

    /**
     * 统计房东的数量
     * @author liyingjie
     * @param
     * @return
     */
    public Long countLanlordNum(){
        return mybatisDaoContext.count(SQLID + "countLanlordNum");
    }


	/**
	 * 根据手机号和名称获取用户uid
	 *
	 * @author loushuai
	 * @created 2017年5月25日 下午2:27:56
	 *
	 * @param customerBaseMsgEntity
	 * @return
	 */
	public List<CustomerBaseMsgEntity> getByCustomNameAndTel(Map<String, Object> paramMap) {
		return mybatisDaoContext.findAll(SQLID + "getByCustomNameAndTel", CustomerBaseMsgEntity.class, paramMap);
	}


}
