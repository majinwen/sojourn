package com.ziroom.minsu.services.customer.dao;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.MD5Util;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>客户头像Dao</p>
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
@Repository("customer.customerPicMsgDao")
public class CustomerPicMsgDao {
	
	private String SQLID="customer.customerPicMsgDao.";

    @Autowired
    @Qualifier("customer.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 
     * 插入客户图片信息
     *
     * @author bushujie
     * @created 2016年4月23日 下午1:49:39
     *
     * @param customerPicMsgEntity
     */
    public int insertCustomerPicMsg(CustomerPicMsgEntity customerPicMsgEntity){
    	
    	if(Check.NuNObj(customerPicMsgEntity)
				||Check.NuNStrStrict(customerPicMsgEntity.getUid())
				||Check.NuNObj(customerPicMsgEntity.getPicType())){
			throw new BusinessException("图片参数错误");
		}
    	if(Check.NuNStr(customerPicMsgEntity.getFid())){
    		customerPicMsgEntity.setFid(UUIDGenerator.hexUUID());
    	}
    	//线上数据库pic_name字段你长度是100，有客户插入图片名称过长，导致报错，现在处理截取100, 2018-1-12修改
    	if(!Check.NuNStr(customerPicMsgEntity.getPicName()) && customerPicMsgEntity.getPicName().length()>100){
    		String newPicName = customerPicMsgEntity.getPicName().substring(0, 100);
    		customerPicMsgEntity.setPicName(newPicName);
    	}
    	return mybatisDaoContext.save(SQLID+"insertCustomerPicMsg", customerPicMsgEntity);
    }
    
    /**
     * 
     * 查询客户图片根据类型
     *
     * @author bushujie
     * @created 2016年4月23日 下午6:23:27
     *
     * @param paramMap
     * @return
     */
    public CustomerPicMsgEntity getCustomerPicByType(Map<String, Object> paramMap){
    	return mybatisDaoContext.findOne(SQLID+ "getCustomerPicByType", CustomerPicMsgEntity.class, paramMap);
    }
    /**
     * 
     * 查询客户图片根据类型 list
     *
     * @author jixd
     * @created 2016年6月27日 下午12:56:30
     *
     * @param paramMap
     * @return
     */
    public List<CustomerPicMsgEntity> getCustomerPicListByType(Map<String, Object> paramMap){
    	return mybatisDaoContext.findAll(SQLID+"getCustomerPicByType", CustomerPicMsgEntity.class, paramMap);
    }


    /**
     * 删除当前类型的图片信息
     * @author afi
     * @param uid
     * @param picType
     * @return
     */
    public int delCustomerPicMsgByType(String uid,Integer picType){
        Map<String,Object>  par = new HashMap();
        par.put("uid",uid);
        par.put("picType",picType);
        return mybatisDaoContext.update(SQLID+"delCustomerPicMsgByType", par);
    }



    /**
     * 
     * 更新客户图片信息
     *
     * @author bushujie
     * @created 2016年4月23日 下午6:49:30
     *
     * @param customerPicMsgEntity
     * @return
     */
    public int updateCustomerPicMsg(CustomerPicMsgEntity customerPicMsgEntity){
    	//线上数据库pic_name字段你长度是100，有客户插入图片名称过长，导致报错，现在处理截取100, 2018-1-12修改
    	if(!Check.NuNStr(customerPicMsgEntity.getPicName()) && customerPicMsgEntity.getPicName().length()>100){
    		String newPicName = customerPicMsgEntity.getPicName().substring(0, 100);
    		customerPicMsgEntity.setPicName(newPicName);
    	}
    	return mybatisDaoContext.update(SQLID+"updateCustomerPicMsg", customerPicMsgEntity);
    }
    
    /**
     * 
     * 查询客户所有相关图片
     *
     * @author jixd
     * @created 2016年4月24日 上午1:01:30
     *
     * @return
     */
    public List<CustomerPicMsgEntity> queryCustomerAllPicByUid(String uid){
    	if(Check.NuNStr(uid)){
    		throw new BusinessException("uid is null");
    	}
    	return mybatisDaoContext.findAll(SQLID + "selectCustomerPicByUid", CustomerPicMsgEntity.class, uid);
    }

    /**
	 * 
	 * 获取最新修改且尚未审核的照片
	 *
	 * @author loushuai
	 * @created 2017年8月8日 下午5:08:40
	 *
	 * @param headPicMap
	 * @return
	 */
	public CustomerPicMsgEntity getLatestUnAuditHeadPic(Map<String, Object> paramMap) {
		return mybatisDaoContext.findOne(SQLID+"getLatestUnAuditHeadPic", CustomerPicMsgEntity.class, paramMap);
	}

	/**
	 * 
	 * 根据一些条件修改图片
	 *
	 * @author loushuai
	 * @created 2017年8月9日 下午3:24:56
	 *
	 * @param customerPicMsg
	 */
	public int updatePicMsgByCondition(CustomerPicMsgEntity customerPicMsg) {
		//线上数据库pic_name字段你长度是100，有客户插入图片名称过长，导致报错，现在处理截取100, 2018-1-12修改
    	if(!Check.NuNStr(customerPicMsg.getPicName()) && customerPicMsg.getPicName().length()>100){
    		String newPicName = customerPicMsg.getPicName().substring(0, 100);
    		customerPicMsg.setPicName(newPicName);
    	}
		return mybatisDaoContext.update(SQLID+"updatePicMsgByCondition", customerPicMsg);
	}

	/**
	 * 将某个用户的所有
	 * 
	 *
	 * @author loushuai
	 * @created 2017年8月9日 下午3:33:04
	 *
	 * @return
	 */
	public int updateSetOtherHeadPicIsdel(CustomerPicMsgEntity customerPicMsg) {
		return mybatisDaoContext.update(SQLID+"updateSetOtherHeadPicIsdel",customerPicMsg);
	}
}