package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.trading.entity.MeterDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>水电费录入数据</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月19日 14:39
 * @since 1.0
 */
@Repository("trading.meterDetailDao")
public class MeterDetailDao {

    private String SQLID = "trading.meterDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存水电费录入记录
     * @author jixd
     * @created 2017年09月19日 14:49:31
     * @param
     * @return
     */
    public int insert(MeterDetailEntity meterDetailEntity){
        if (Check.NuNStr(meterDetailEntity.getFid())){
            meterDetailEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert",meterDetailEntity);
    }

    /**
     * 更新水电信息
     * @author jixd
     * @created 2017年11月03日 15:49:13
     * @param
     * @return
     */
    public int update(MeterDetailEntity meterDetailEntity){
        meterDetailEntity.setFcreaterid(null);
        return mybatisDaoContext.update(SQLID + "updateByFid",meterDetailEntity);
    }
    /**
     * 更新水电信息
     * @author jixd
     * @created 2018年01月08日 13:58:38
     * @param
     * @return
     */
    public int updateByContractId(MeterDetailEntity meterDetailEntity){
        return mybatisDaoContext.update(SQLID + "updateByContractId",meterDetailEntity);
    }

    /**
     * 保存或者更新
     * @param meterDetailEntity
     * @return
     */
    public int saveOrUpdate(MeterDetailEntity meterDetailEntity){
        int count = 0;
        if (Check.NuNStr(meterDetailEntity.getFid())){
            count = insert(meterDetailEntity);
        }else{
            count = update(meterDetailEntity);
        }
        return count;
    }

    /**
     * 根据合同号和房间号查询查询水电费
     * @author jixd
     * @created 2017年09月19日 14:50:59
     * @param
     * @return
     */
    public MeterDetailEntity findByContractIdAndRoomId(ContractRoomDto contractRoomDto){
        return mybatisDaoContext.findOneSlave(SQLID + "findByContractIdAndRoomId",MeterDetailEntity.class,contractRoomDto);
    }

    /**
     * 根据合同号查询水电费
     * @author cuiyh9
     * @created 2017年09月19日 14:50:59
     * @param
     * @return
     */
    public List<MeterDetailEntity> findByContractIds(List<String> contractIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIdList);
        return mybatisDaoContext.findAll(SQLID + "findByContractIds", MeterDetailEntity.class, map);
    }


}
