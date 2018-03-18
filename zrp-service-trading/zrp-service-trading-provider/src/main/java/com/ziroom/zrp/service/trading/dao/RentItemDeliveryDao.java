package com.ziroom.zrp.service.trading.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.service.trading.dto.ContractRoomDto;
import com.ziroom.zrp.trading.entity.RentItemDeliveryEntity;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>物业交割物品</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月19日 14:41
 * @since 1.0
 */
@Repository("trading.rentItemDeliveryDao")
public class RentItemDeliveryDao {

    private String SQLID = "trading.rentItemDeliveryDao.";

    @Autowired
    @Qualifier("trading.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 获取物业交割物品列表
     * @author jixd
     * @created 2017年09月19日 15:57:47
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listValidItemByContractIdAndRoomId(ContractRoomDto contractRoomDto){
        return mybatisDaoContext.findAll(SQLID + "listValidItemByContractIdAndRoomId",RentItemDeliveryEntity.class,contractRoomDto);
    }

    /**
     * 获取物业交割物品列表
     * @author cuiyh9
     * @created 2017年09月19日 15:57:47
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listValidItemByContractIds(List<String> contractIdList){
        Map<String, Object> map = new HashMap<>();
        map.put("contractIds", contractIdList);
        return mybatisDaoContext.findAll(SQLID + "listValidItemByContractIds", RentItemDeliveryEntity.class, map);
    }

    /**
     * 新增项
     * @author jixd
     * @created 2017年09月19日 15:59:05
     * @param
     * @return
     */
    public int insert(RentItemDeliveryEntity rentItemDeliveryEntity){
        if (Check.NuNStr(rentItemDeliveryEntity.getFid())){
            rentItemDeliveryEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID + "insert",rentItemDeliveryEntity);
    }

    /**
     * 批量插入
     * @author jixd
     * @created 2017年11月03日 16:59:13
     * @param
     * @return
     */
    public int batchInsert(List<RentItemDeliveryEntity> list){
        int count = 0;
        if (Check.NuNCollection(list)){
            return count;
        }
        for (RentItemDeliveryEntity rentItemDeliveryEntity : list){
            rentItemDeliveryEntity.setCreatetime(new Date());
            rentItemDeliveryEntity.setUpdatetime(new Date());
            count += insert(rentItemDeliveryEntity);
        }
        return count;
    }

    /**
     * 删除物品
     * @author jixd
     * @created 2017年11月03日 16:55:00
     * @param
     * @return
     */
    public int deleteItemByContractIdAndRoomId(ContractRoomDto contractRoomDto){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("contractId",contractRoomDto.getContractId());
        paramMap.put("roomId",contractRoomDto.getRoomId());
        return mybatisDaoContext.update(SQLID + "deleteItemByContractIdAndRoomId",paramMap);
    }
    /**
     * 根据多个物品ID查询相应物品
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public List<RentItemDeliveryEntity> listItemDeliverysByItemIds(List<String> itemIds){
    	Map<String, Object> map = new HashMap<>();
        map.put("itemIds", itemIds);
        return mybatisDaoContext.findAll(SQLID+"listItemDeliverysByItemIds",RentItemDeliveryEntity.class,map);
    }
    
    /**
     * 更新物品项
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int update(RentItemDeliveryEntity rentItemDeliveryEntity){
        return mybatisDaoContext.update(SQLID + "update",rentItemDeliveryEntity);
    }
    /**
     * <p>根据多个参数查询合同物品项</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public RentItemDeliveryEntity selectRentItemDeliveryEntityByparam(String contractId,
    		String itemId,String surrenderId,String isNewFlag){
    	Map<String,String> map = new HashMap<String, String>();
    	map.put("contractId", contractId);
    	map.put("itemId", itemId);
    	map.put("surrenderId", surrenderId);
    	map.put("isNewFlag", isNewFlag);
    	return mybatisDaoContext.findOne(SQLID+"selectRentItemDeliveryEntityByparam", RentItemDeliveryEntity.class);
    }
}
