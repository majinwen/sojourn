package com.ziroom.minsu.services.message.service;

import com.ziroom.minsu.entity.message.MsgBaseLogEntity;
import com.ziroom.minsu.services.message.dao.MsgBaseDao;
import com.ziroom.minsu.services.message.dao.MsgBaseErrorDao;
import com.ziroom.minsu.services.message.dao.MsgBaseLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * <p>同步appIM保存日志</p>
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
@Service("message.msgBaseLogServiceImpl")
public class MsgBaseLogServiceImpl {

    /**
     *  日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(MsgBaseLogServiceImpl.class);

    @Resource(name = "message.msgBaseLogDao")
    private MsgBaseLogDao msgBaseLogDao;


    public int saveLog(MsgBaseLogEntity msgBaseLogEntity){
        return msgBaseLogDao.save(msgBaseLogEntity);
    }



}
