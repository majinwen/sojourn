package com.ziroom.minsu.services.basedata.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.dao.OpLogDao;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
import com.ziroom.minsu.services.basedata.entity.OpLogVo;

/**
 * <p>操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
@Service("basedata.opLogService")
public class OpLogServiceImpl {




    @Resource(name = "basedata.opLogDao")
    private OpLogDao opLogDao;


    /**
     * 保存操作记录
     * @param log
     */
    public void saveOpLogInfo(OpLogEntity log) {
        //飞空校验
        if(Check.NuNObj(log)){
            return;
        }
        //直接插入数据库
        opLogDao.insertSysOpLogEntity(log);
    }
    
    
    /**
	 * 
	 * 查询日志列表
	 *
	 * @author liyingjie
	 * @created 2016年3月19日 上午11:20:09
	 *
	 * @param opLogRequest
	 * @return
	 */
	public PagingResult<OpLogVo> findMenuResList(OpLogRequest opLogRequest) {
		return opLogDao.findSysOpLogList(opLogRequest);
	}
    
}
