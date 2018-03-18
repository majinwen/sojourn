package com.ziroom.zrp.service.houses.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.zrp.houses.entity.RoomItemsConfigEntity;
import com.ziroom.zrp.service.houses.entity.ExtRoomItemsConfigVo;

/**
 * <p>房间物品表dao</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 11:06
 * @since 1.0
 */
@Repository("houses.roomItemsConfigDao")
public class RoomItemsConfigDao {

    private String SQLID = "houses.roomItemsConfigDao.";

    @Autowired
    @Qualifier("houses.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 查询物品列表
     * @author jixd
     * @created 2017年10月30日 12:04:51
     * @param
     * @return
     */
    public List<ExtRoomItemsConfigVo> listRoomItemsByRoomId(String roomId){
        return mybatisDaoContext.findAll(SQLID + "listRoomItemsByRoomId",roomId);
    }


    /**
     * 保存房间物品信息
     * @author jixd
     * @created 2017年11月02日 18:35:16
     * @param
     * @return
     */
    public int saveRoomItem(RoomItemsConfigEntity roomItemsConfigEntity){
        if (Check.NuNObj(roomItemsConfigEntity.getFid())){
            roomItemsConfigEntity.setFid(UUIDGenerator.hexUUID());
        }
        return mybatisDaoContext.save(SQLID +"insert",roomItemsConfigEntity);
    }
    /**
     * 更新房间物品信息
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int updateRoomItem(RoomItemsConfigEntity roomItemsConfigEntity){
        return mybatisDaoContext.update(SQLID +"update",roomItemsConfigEntity);
    }
    /**
     * 获取房间中得物品
     * @author jixd
     * @created 2017年11月23日 10:50:37
     * @param
     * @return
     */
    public RoomItemsConfigEntity getItemByRoomIdAndItemId(String roomId,String itemId){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("roomId",roomId);
        paramMap.put("itemId",itemId);
        return mybatisDaoContext.findOne(SQLID +"getItemByRoomIdAndItemId",RoomItemsConfigEntity.class,paramMap);
    }
    
    /**
     * 更新房间物品信息
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public ExtRoomItemsConfigVo selectRoomItemsConfigByParams(String roomId, String projectId, String itemId){
    	Map<String,Object> param = new HashMap<>();
    	param.put("roomId", roomId);
    	param.put("projectId", projectId);
    	param.put("itemId", itemId);
        return mybatisDaoContext.findOne(SQLID +"selectRoomItemsConfigByParams",ExtRoomItemsConfigVo.class,param);
    }
}
