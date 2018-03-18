package com.zra.push.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.push.dao.PushLogMapper;
import com.zra.push.entity.PushLog;

/**
 * 保存日志servcie.
 * @author cuiyh9
 */
@Component
public class PushService {

    /**
     * 保存日志dao层.
     * */
    @Autowired
    private PushLogMapper pushLogMapper; 
    
    /**
     * 保存日志.
     * @param pushLog PushLog
     * @return boolean true:成功;false:失败
     */
    public boolean savePushLog(PushLog pushLog) {
        int count = pushLogMapper.insert(pushLog);
        return count > 0;
    }
}
