package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.entity.CheckSignCusInfoVo;
import com.ziroom.zrp.trading.entity.CheckSignErrorLogEntity;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 电子验签错误日志dao(无纸化错误日志)
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp on 2017年09月25日
 * @version 1.0
 * @since 1.0
 */
@Repository("trading.checkSignErrorLogDao")
public class CheckSignErrorLogDao {

	private String SQLID = "trading.checkSignErrorLogDao.";

	@Autowired
	@Qualifier("trading.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;

	/**
	 * @description: 保存错误日志
	 * @author: lusp
	 * @date: 2017/9/25 15:44
	 * @params: checkSignErrorLogEntity
	 * @return: int
	 */
	public int saveCheckSignErrorLog(CheckSignErrorLogEntity checkSignErrorLogEntity){
		if(Check.NuNStr(checkSignErrorLogEntity.getFid())){
			checkSignErrorLogEntity.setFid(UUIDGenerator.hexUUID());
		}
		return mybatisDaoContext.save(SQLID + "insertSelective",checkSignErrorLogEntity);
	}
	
	

}
