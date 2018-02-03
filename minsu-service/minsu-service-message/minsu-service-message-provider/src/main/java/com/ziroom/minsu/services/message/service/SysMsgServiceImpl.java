package com.ziroom.minsu.services.message.service;



import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.entity.message.SysMsgEntity;
import com.ziroom.minsu.services.message.dao.SysMsgDao;
import com.ziroom.minsu.services.message.dto.SysMsgRequest;

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
@Service("message.sysMsgServiceImpl")
public class SysMsgServiceImpl {
	
	@Resource(name="message.sysMsgDao")
	private SysMsgDao sysMsgDao;
	/**
	 * 新增系统消息
	 * @author jixd on 2016年4月18日
	 */
	public int saveSysMsg(SysMsgEntity sysMsgEntity){
		return sysMsgDao.saveSysMsg(sysMsgEntity);
	}
	
	/**
	 * 查询系统消息
	 * @author jixd on 2016年4月18日
	 */
	public PagingResult<SysMsgEntity> querySysMsg(SysMsgRequest sysMsgRequest) {
		return sysMsgDao.queryByTargetUid(sysMsgRequest);
	}
	
	/**
	 * 删除系统消息
	 * @author jixd on 2016年4月18日
	 */
	public int deleteSysMsg(SysMsgEntity sysMsgEntity){
		return sysMsgDao.deleteSysMsg(sysMsgEntity);
	}
	/**
	 * 
	 * 更新系统消息为已读状态
	 *
	 * @author jixd
	 * @created 2016年4月21日 下午4:21:09
	 *
	 * @param sysMsgEntity
	 * @return
	 */
	public int updateSysMsgRead(SysMsgEntity sysMsgEntity){
		return sysMsgDao.updateSysMsgRead(sysMsgEntity);
	}

	/**
	 * 批量增加系统消息
	 * @author jixd on 2016年4月18日
	 */
	public int saveSysMsgList(List<SysMsgEntity> list){
		return sysMsgDao.saveSysMsgBatch(list);
	}
}