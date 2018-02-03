package com.ziroom.minsu.services.order.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.services.order.dto.AccountFillLogRequest;

/**
 * <p>
 * 充值日志表
 * </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/4/1.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.accountFillLogDao")
public class AccountFillLogDao {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(AccountFillLogDao.class);
	private String SQLID = "order.accountFillLogDao.";

	@Autowired
	@Qualifier("order.MybatisDaoContext")
	private MybatisDaoContext mybatisDaoContext;
	
	
	/**
	 *
	 * 插入资源记录
	 *
	 * @author liyingjie
	 * @created 2016年4月1日 
	 *
	 * @param accountEntity
	 */
	public void insertAccountFillLogRes(AccountFillLogEntity accountEntity) {
		if(Check.NuNObj(accountEntity) || Check.NuNStr(accountEntity.getFillSn()) || Check.NuNStr(accountEntity.getTradeNo())||Check.NuNStr(accountEntity.getOrderSn()) || Check.NuNStr(accountEntity.getTargetUid())){
        	LogUtil.info(logger, "insertAccountFillLogRes param:{}" , accountEntity);
            throw new BusinessException("insertAccountFillLogRes param error." );
        }
		mybatisDaoContext.save(SQLID + "insertAccountFillLog", accountEntity);
	}
	
	/**
	 *
	 * 查询充值失败的list
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param taskRequest
	 */
	public List<AccountFillLogEntity> taskGetFillFailList(AccountFillLogRequest taskRequest){
        if(Check.NuNObj(taskRequest)){
        	LogUtil.error(logger,"taskGetFillFailList param:{}",taskRequest);
            throw new BusinessException("taskGetFillFailList param error.");
        }
        Map<String,Object> par = new HashMap<>();
        par.put("limit",taskRequest.getLimit());
        return mybatisDaoContext.findAllByMaster(SQLID + "taskSelectByCondiction", AccountFillLogEntity.class, par);
    }
	
	
	/**
	 *
	 * 查询充值失败的count
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param 
	 */
	public Long taskCountFillFailNum(){
       return mybatisDaoContext.count(SQLID + "countFillFailNum");
   }
	
	/**
	 *
	 * 更新 为充值成功
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param taskRequest
	 */
	public int taskUpdateFillFailRes(AccountFillLogRequest taskRequest){
      if(Check.NuNObj(taskRequest) || Check.NuNStr(taskRequest.getFillSn())){
      	LogUtil.error(logger,"taskUpdateFillFailRes param:{}",taskRequest);
          throw new BusinessException("taskUpdateFillFailRes param error.");
      }
      Map<String,Object> par = new HashMap<>();
      par.put("fillSn", taskRequest.getFillSn());
      par.put("orderSn", taskRequest.getOrderSn());
      par.put("tradeNo", taskRequest.getTradeNo());
     
      return mybatisDaoContext.update(SQLID + "taskUpdateSuccess", par);
  }
	
	
	
	
	
}
