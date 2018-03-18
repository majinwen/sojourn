package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.HouseItemsConfigEntity;
import com.ziroom.zrp.houses.entity.ItemListEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomItemsConfigEntity;
import com.ziroom.zrp.service.houses.dao.HouseItemsConfigDao;
import com.ziroom.zrp.service.houses.dao.ItemListDao;
import com.ziroom.zrp.service.houses.dao.RoomInfoDao;
import com.ziroom.zrp.service.houses.dao.RoomItemsConfigDao;
import com.ziroom.zrp.service.houses.dto.RoomItemSaveDto;
import com.ziroom.zrp.service.houses.entity.ExtHouseItemsConfigVo;
import com.ziroom.zrp.service.houses.entity.ExtRoomItemsConfigVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>物品清单</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 13:41
 * @since 1.0
 */
@Service("houses.itemsServiceImpl")
public class ItemsServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsServiceImpl.class);

    @Resource(name="houses.itemListDao")
    private ItemListDao itemListDao;

    @Resource(name = "houses.houseItemsConfigDao")
    private HouseItemsConfigDao houseItemsConfigDao;

    @Resource(name = "houses.roomItemsConfigDao")
    private RoomItemsConfigDao roomItemsConfigDao;

    @Resource(name = "houses.roomInfoDao")
    private RoomInfoDao roomInfoDao;

    /**
     * 物品清单列表
     * @author jixd
     * @created 2017年10月30日 13:46:06
     * @param
     * @return
     */
    public List<ItemListEntity> listItems(String itemType,String itemName){
       return itemListDao.listItems(itemType,itemName);
    }

    /**
     * 列出户型下面的物品列表
     * @author jixd
     * @created 2017年10月30日 14:04:36
     * @param
     * @return
     */
    public List<HouseItemsConfigEntity> listHouseItemsByHouseType(String houseTypeId){
        return houseItemsConfigDao.listHouseItemsByHouseType(houseTypeId);
    }

    /**
     * 列出房间下面的物品列表
     * @author jixd
     * @created 2017年10月30日 14:06:58
     * @param
     * @return
     */
    public List<ExtRoomItemsConfigVo> listRoomItemsByRoomId(String roomId){
        return roomItemsConfigDao.listRoomItemsByRoomId(roomId);
    }

    /**
     * 保存房间物品信息
     * @author jixd
     * @created 2017年11月02日 18:38:15
     * @param
     * @return
     */
    public int saveRoomItem(RoomItemSaveDto roomItemSaveDto){
        ItemListEntity itemListEntity = itemListDao.findByFid(roomItemSaveDto.getItemId());
        RoomInfoEntity roomInfo = roomInfoDao.getRoomInfoByFid(roomItemSaveDto.getRoomId());
        RoomItemsConfigEntity hasItem = roomItemsConfigDao.getItemByRoomIdAndItemId(roomItemSaveDto.getRoomId(), roomItemSaveDto.getItemId());
        if (!Check.NuNObj(hasItem)){
            LogUtil.info(LOGGER,"物品存在直接返回,roomId={},itemId={}",roomItemSaveDto.getRoomId(),roomItemSaveDto.getItemId());
            //已经存在直接返回
            return 0;
        }
        RoomItemsConfigEntity roomItemsConfigEntity = new RoomItemsConfigEntity();
        roomItemsConfigEntity.setRoomid(roomItemSaveDto.getRoomId());
        roomItemsConfigEntity.setProjectid(roomInfo.getProjectid());
        roomItemsConfigEntity.setFitemcode(itemListEntity.getFcode());
        roomItemsConfigEntity.setItemid(itemListEntity.getFid());
        roomItemsConfigEntity.setFnumber(1);
        roomItemsConfigEntity.setFnum(1);
        roomItemsConfigEntity.setCreaterid(roomItemSaveDto.getCreateId());
        roomItemsConfigEntity.setUpdaterid(roomItemSaveDto.getCreateId());
        roomItemsConfigEntity.setCityid(roomInfo.getCityid());
        roomItemsConfigEntity.setFprice(itemListEntity.getFprice());
        roomItemsConfigEntity.setFtype(roomInfo.getFtype());
        return roomItemsConfigDao.saveRoomItem(roomItemsConfigEntity);
    }
    
    /**
     * 根据fid查询
     * @author xiangbin
     * @created 2017年11月18日
     * @param
     * @return
     */
    public ItemListEntity findItemListByFid(String fid){
        return itemListDao.findByFid(fid);
    }
    /**
     * <p>保存房间物品信息</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int saveRoomItemsConfigEntity(RoomItemsConfigEntity roomItemsConfigEntity){
    	if(Check.NuNStr(roomItemsConfigEntity.getFid())){
    		roomItemsConfigEntity.setFid(UUID.randomUUID().toString());
    	}
    	return roomItemsConfigDao.saveRoomItem(roomItemsConfigEntity);
    }
    /**
     * <p>更新房间物品信息</p>
     * @author xiangb
     * @created 2017年11月20日
     * @param
     * @return
     */
    public int updateRoomItemsConfigEntity(RoomItemsConfigEntity roomItemsConfigEntity){
    	return roomItemsConfigDao.updateRoomItem(roomItemsConfigEntity);
    }
    
    /**
     * 多条件查询房间物品信息
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
    public RoomItemsConfigEntity selectRoomItemsConfigByParams(String roomId, String projectId, String itemId){
        return roomItemsConfigDao.selectRoomItemsConfigByParams(roomId, projectId, itemId);
    }

    /**
     * 查询房型物品扩展信息列表
     * @author jixd
     * @created 2017年11月28日 20:40:19
     * @param
     * @return
     */
    public List<ExtHouseItemsConfigVo> listHouseItemsExtByHouseTypeId(String houseTypeId){
        return houseItemsConfigDao.listHouseItemsExtByHouseTypeId(houseTypeId);
    }
}
