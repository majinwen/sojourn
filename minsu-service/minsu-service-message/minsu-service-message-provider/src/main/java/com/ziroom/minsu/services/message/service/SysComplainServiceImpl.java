
package com.ziroom.minsu.services.message.service;

import javax.annotation.Resource;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.message.dto.LandlordComplainRequest;
import com.ziroom.minsu.services.message.entity.SysComplainVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.entity.message.SysComplainEntity;
import com.ziroom.minsu.services.message.dao.SysComplainDao;

/**
 * <p>投诉建议业务处理层</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("message.sysComplainServiceImpl")
public class SysComplainServiceImpl {

	
	/**
	 *  日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(SysComplainServiceImpl.class);
	
	@Resource(name = "message.sysComplainDao")
	private SysComplainDao sysComplainDao;
	

	/**
	 * 
	 * 保存投诉建议实体
	 *
	 * @author yd
	 * @created 2016年5月4日 上午11:53:29
	 *
	 * @param sysComplainEntity
	 * @return
	 */
	public int save(SysComplainEntity sysComplainEntity){
		return sysComplainDao.save(sysComplainEntity);
	}

	/**
	 *	根据条件查询
	 * @author wangwentao 2017/4/24 19:04
	 */
	public PagingResult<SysComplainVo> queryByCondition(LandlordComplainRequest request){
		return sysComplainDao.queryByCondition(request);
	}

	/**
	 *
	 * @author wangwentao 2017/4/25 14:18
	 */
	public SysComplainEntity selectByPrimaryKey(Integer id) {
		return sysComplainDao.selectByPrimaryKey(id);
	}
}
