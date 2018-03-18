package com.ziroom.zrp.service.trading.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.service.trading.entity.LifeItemVo;
import com.ziroom.zrp.trading.entity.RentLifeItemDetailEntity;

/**
 * <p>生活费用明细</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月19日 14:43
 * @since 1.0
 */
@Repository("trading.rentLifeItemDetailDao")
public class RentLifeItemDetailDao {

    private String SQLID = "trading.rentLifeItemDetailDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 保存生活费用
     * @author jixd
     * @created 2017年09月19日 17:02:35
     * @param
     * @return
     */
    public int insert(RentLifeItemDetailEntity rentLifeItemDetailEntity){
        if (Check.NuNStr(rentLifeItemDetailEntity.getId())){
            rentLifeItemDetailEntity.setId(UUIDGenerator.hexUUID());
        }
        if (Check.NuNStr(rentLifeItemDetailEntity.getLifeitemBid())){
            rentLifeItemDetailEntity.setLifeitemBid(UUIDGenerator.hexUUID());
        }

        return mybatisDaoContext.save(SQLID + "insert",rentLifeItemDetailEntity);
    }

    /**
     * 批量插入生活费用
     * @author jixd
     * @created 2017年11月03日 16:45:17
     * @param
     * @return
     */
    public int batchInsert(List<RentLifeItemDetailEntity> list){
        int count = 0;
        if (Check.NuNCollection(list)){
            return count;
        }
        for (RentLifeItemDetailEntity rentLifeItemDetailEntity : list){
            count += insert(rentLifeItemDetailEntity);
        }
        return count;
    }

    /**
     * 删除生活费用
     * @author jixd
     * @created 2017年11月03日 16:22:16
     * @param
     * @return
     */
    public int deleteLifeItem(ContractRoomDto contractRoomDto){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractRoomDto.getContractId());
        paramMap.put("roomId",contractRoomDto.getRoomId());
        return mybatisDaoContext.update(SQLID + "deleteLifeItem",paramMap);
    }

    /**
     * 查询生活费用
     * @author jixd
     * @created 2017年11月03日 16:20:02
     * @param
     * @return
     */
    public List<LifeItemVo> listLifeItemByContractIdAndRoomId(ContractRoomDto contractRoomDto){
        return mybatisDaoContext.findAll(SQLID + "listLifeItemByContractIdAndRoomId",LifeItemVo.class,contractRoomDto);
    }

    /**
     * 查询生活费用
     * @author cuiyh9
     * @created 2017年11月03日 16:20:02
     * @param
     * @return
     */
    public List<LifeItemVo> listLifeItemByContractIds(List<String> contractIdList){
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIdList);
        return mybatisDaoContext.findAll(SQLID + "listLifeItemByContractIds", LifeItemVo.class, map);
    }

    /**
     * 查询列表
     * @param contractRoomDto
     * @return
     */
    public List<RentLifeItemDetailEntity> listLifeItemEntityByContractIdAndRoomId(ContractRoomDto contractRoomDto){
       return mybatisDaoContext.findAll(SQLID + "listLifeItemEntityByContractIdAndRoomId",contractRoomDto);
    }


}
