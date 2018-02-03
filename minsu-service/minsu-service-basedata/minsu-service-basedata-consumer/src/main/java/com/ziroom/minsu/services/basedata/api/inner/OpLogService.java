package com.ziroom.minsu.services.basedata.api.inner;

import com.ziroom.minsu.entity.sys.OpLogEntity;

/**
 * <p>用户的操作记录</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 *
 * @author lusp on 2017/5/8.
 * @version 1.0
 * @since 1.0
 */
public interface OpLogService {

    /**
     * 保存操作记录
     * @param log
     */
    String saveOpLogInfo(OpLogEntity log);
    
   
    /**
	 * 按条件查询 日志列表记录
	 *
	 * @author lusp
	 * @created 2017/5/8
	 *
	 * @return
	 */
    String findOpLogList(String paramJson);
    
    
    
}
