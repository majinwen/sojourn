package com.ziroom.minsu.services.order.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.order.AccountFillLogEntity;
import com.ziroom.minsu.services.order.dao.AccountFillLogDao;
import com.ziroom.minsu.services.order.dto.AccountFillLogRequest;

/**
 * <p>用户的service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liyingjie on 2016/5/3.
 * @version 1.0
 * @since 1.0
 */
@Service("order.accountFillLogServiceImpl")
public class AccountFillLogServiceImpl {
	
    @Resource(name = "order.accountFillLogDao")
    private AccountFillLogDao accountFillLogDao;

    
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
		accountFillLogDao.insertAccountFillLogRes(accountEntity);
	}
	
	/**
	 *
	 * 查询 资源记录
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param taskRequest
	 */
	public List<AccountFillLogEntity> taskGetFillFailList(AccountFillLogRequest taskRequest){
       return accountFillLogDao.taskGetFillFailList(taskRequest);
   }
	
	
	/**
	 *
	 * 统计失败记录
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param taskRequest
	 */
	public Long taskCountFillFailNum(){
      return accountFillLogDao.taskCountFillFailNum();
  }
	
	/**
	 *
	 * 更新失败记录
	 *
	 * @author liyingjie
	 * @created 2016年5月3日 
	 *
	 * @param taskRequest
	 */
	public int taskUpdateFillFailRes(AccountFillLogRequest taskRequest){
     return accountFillLogDao.taskUpdateFillFailRes(taskRequest);
 }

   


   
    
    
}

