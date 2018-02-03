package com.ziroom.minsu.services.evaluate.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.entity.evaluate.LandlordReplyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>评价回复</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
@Repository("evaluate.landlordReplyDao")
public class LandlordReplyDao {

	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(LandlordReplyDao.class);

	private String SQLID = "evaluate.landlordReplyDao.";

	@Autowired
	@Qualifier("evaluate.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	/**
	 * 增加回复
	 * @author jixd
	 * @created 2016年11月07日 16:35:29
	 * @param
	 * @return
	 */
	public int saveLandlordReply(LandlordReplyEntity landlordReplyEntity){
		if (Check.NuNStr(landlordReplyEntity.getFid())){
			landlordReplyEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID+"insertSelective",landlordReplyEntity);
	}

	/**
	 * 主表查询回复内容
	 * @author jixd
	 * @created 2016年11月08日 17:02:01
	 * @param
	 * @return
	 */
	public LandlordReplyEntity findEvaReplyByEvaFid(String evaFid){
		return mybatisDaoContext.findOne(SQLID+"findEvaReplyByEvaFid",LandlordReplyEntity.class,evaFid);
	}
	/**
	 * 根据订单号 查询房东的回复
	 * @author jixd
	 * @created 2016年11月08日 17:02:01
	 * @param
	 * @return
	 */
	public LandlordReplyEntity findEvaReplyByOrderSn(String orderSn){
		return mybatisDaoContext.findOne(SQLID+"findEvaReplyByOrderSn",LandlordReplyEntity.class,orderSn);
	}

}
