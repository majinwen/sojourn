package com.ziroom.minsu.services.message.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.SysMsgManagerEntity;
import com.ziroom.minsu.services.message.dao.SysMsgDao;
import com.ziroom.minsu.services.message.dao.SysMsgManagerDao;
import com.ziroom.minsu.services.message.dto.SysMsgManagerRequest;

/**
 * <p>系统消息业务</p>
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
@Service("message.sysMsgManagerServiceImpl")
public class SysMsgManagerServiceImpl {
	
	@Resource(name="message.sysMsgManagerDao")
	private SysMsgManagerDao sysMsgManagerDao;
	
	@Resource(name="message.sysMsgDao")
	private SysMsgDao sysMsgDao;
	/**
	 * 新增系统消息
	 * @author jixd on 2016年4月18日
	 */
	public int saveSysMsgManager(SysMsgManagerEntity sysMsgManagerEntity){
		return sysMsgManagerDao.saveSysMsgManagerEntity(sysMsgManagerEntity);
	}
	/**
	 * 发布消息
	 * @author jixd on 2016年4月18日
	 */
	public int updateReleaseStatus(SysMsgManagerEntity sysMsgManagerEntity){
		return sysMsgManagerDao.releaseSysMsgByFid(sysMsgManagerEntity);
	}
	/**
	 * 删除未发布消息
	 * @author jixd on 2016年4月18日
	 */
	public int deleteSysMsgManager(SysMsgManagerEntity sysMsgManagerEntity){
		return sysMsgManagerDao.deleteSysMsgManagerByFid(sysMsgManagerEntity);
	}
	/**
	 * 更新信息 修改
	 * @author jixd on 2016年4月18日
	 */
	public int updateSysMsgManager(SysMsgManagerEntity sysMsgManagerEntity){
		return sysMsgManagerDao.updateSysMsgManager(sysMsgManagerEntity);
	}
	
	/**
	 * 带分页查询
	 * @author jixd on 2016年4月18日
	 */
	public PagingResult<SysMsgManagerEntity> querySysMsgManagerHasPage(SysMsgManagerRequest sysMsgManagerRequest){
		return sysMsgManagerDao.querySysMsgManager(sysMsgManagerRequest);
	}
	/**
	 * 根据fid查询
	 * @author jixd on 2016年4月19日
	 */
	public SysMsgManagerEntity findSysMsgManagerByFid(String fid){
		return sysMsgManagerDao.findSysMsgManagerByFid(fid);
	}
	
}
