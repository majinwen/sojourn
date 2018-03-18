package com.zra.repair.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.apollo.logproxy.slf4j.LoggerFactoryProxy;
import com.google.common.collect.Maps;
import com.zra.repair.dao.ZryRepairOrderLogMapper;
import com.zra.repair.entity.ZryRepairOrderLog;
import com.zra.repair.service.ZryRepairOrderLogService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>自如寓报修日志处理</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月28日 15:34
 * @since 1.0
 * @version 1.0
 */
@Service
public class ZryRepairOrderLogServiceImpl implements ZryRepairOrderLogService {

    private final static Logger log = LoggerFactoryProxy.getLogger(ZryRepairOrderLogServiceImpl.class);

    @Autowired
    private ZryRepairOrderLogMapper zryRepairOrderLogMapper;

    @Override
    public List<ZryRepairOrderLog> selectByRepairFid(String rFid) throws Exception {

        Map seachMap = Maps.newHashMap();

        seachMap.put("repairOrder", rFid);

        seachMap.put("orderBy", "id");
        seachMap.put("sortBy", "DESC");

        return zryRepairOrderLogMapper.selectByCondition(seachMap);
    }

    @Override
    public boolean save(ZryRepairOrderLog zryRepairOrderLog) {

        boolean result = false;

        try {
            result = zryRepairOrderLogMapper.insertSelective(zryRepairOrderLog) >= 0;
        } catch (Exception e) {
            log.error("Ziroom Apartment insert repairOrderLog error : \n {}", JSONObject.toJSONString(zryRepairOrderLog),e);
        }

        return result;
    }
}
