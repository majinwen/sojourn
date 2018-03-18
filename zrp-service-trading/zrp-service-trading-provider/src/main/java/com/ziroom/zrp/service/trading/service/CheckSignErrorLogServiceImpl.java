package com.ziroom.zrp.service.trading.service;

import com.ziroom.zrp.service.trading.dao.CheckSignErrorLogDao;
import com.ziroom.zrp.trading.entity.CheckSignErrorLogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @version 1.0
 * @Date Created in 2017年09月25日 20:03
 * @since 1.0
 */
@Service("trading.checkSignErrorLogServiceImpl")
public class CheckSignErrorLogServiceImpl {

    @Resource(name = "trading.checkSignErrorLogDao")
    private CheckSignErrorLogDao checkSignErrorLogDao;

    public int saveCheckSignErrorLog(CheckSignErrorLogEntity checkSignErrorLogEntity){
        return checkSignErrorLogDao.saveCheckSignErrorLog(checkSignErrorLogEntity);
    }

}
