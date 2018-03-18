package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.trading.entity.RentContractActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 合同活动dao
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017年10月20日
 * @version 1.0
 * @since 1.0
 */
@Repository("trading.rentContractActivityDao")
public class RentContractActivityDao {

	private String SQLID = "trading.rentContractActivityDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	  * @description: 插入活动信息
	  * @author: lusp
	  * @date: 2017/10/20 下午 17:59
	  * @params: rentContractActivityEntity
	  * @return: int
	  */
	public int insertSelective(RentContractActivityEntity rentContractActivityEntity){
		return mybatisDaoContext.save(SQLID + "insertSelective",rentContractActivityEntity);
	}

	/**
	 * 查询合同的活动列表
	 *
	 * @param contractId 合同标识
	 * @return list
	 * @author cuigh6
	 * @Date 2017年10月
	 */
	public List<RentContractActivityEntity> getContractActivityList(String contractId) {
		return this.mybatisDaoContext.findAll(SQLID + "getContractActivityList", RentContractActivityEntity.class, contractId);
	}

	/**
	 * 判断是否参加其灵海燕计划
	 *
	 * @Author: wangxm113
	 * @Date: 2017年11月04日 17时14分13秒
	 */
    public Integer havePlanOfHaiYanOfQiLing(String contractId, String haiyan_plan_qiling) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractId", contractId);
        map.put("QLHY", haiyan_plan_qiling);
        return this.mybatisDaoContext.findOne(SQLID + "havePlanOfHaiYanOfQiLing", Integer.class, map);
    }

    /**
     * 删除活动
     * @author jixd
     * @created 2017年11月15日 19:57:24
     * @param
     * @return
     */
    public int deleteActivityByContractId(String contractId){
    	Map<String,Object> paramMap = new HashMap<>();
    	paramMap.put("contractId",contractId);
    	return mybatisDaoContext.update(SQLID + "deleteActivityByContractId",paramMap);

	}
}
