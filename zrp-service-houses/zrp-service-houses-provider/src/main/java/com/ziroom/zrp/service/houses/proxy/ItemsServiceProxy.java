package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.HouseItemsConfigEntity;
import com.ziroom.zrp.houses.entity.ItemListEntity;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.houses.entity.RoomItemsConfigEntity;
import com.ziroom.zrp.service.houses.api.ItemsService;
import com.ziroom.zrp.service.houses.dto.RoomItemSaveDto;
import com.ziroom.zrp.service.houses.entity.ExtHouseItemsConfigVo;
import com.ziroom.zrp.service.houses.entity.ExtRoomItemsConfigVo;
import com.ziroom.zrp.service.houses.entity.ItemConfigVo;
import com.ziroom.zrp.service.houses.service.ItemsServiceImpl;
import com.ziroom.zrp.service.houses.service.RoomInfoServiceImpl;
import com.ziroom.zrp.service.houses.valenum.ItemTypeEnum;
import com.ziroom.zrp.service.houses.valenum.RoomTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>物品相关接口</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年10月30日 14:18
 * @since 1.0
 */

@Component("houses.itemsServiceProxy")
public class ItemsServiceProxy implements ItemsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemsServiceProxy.class);

    @Resource(name="houses.itemsServiceImpl")
    private ItemsServiceImpl itemsServiceImpl;

    @Resource(name = "houses.roomInfoServiceImpl")
    private RoomInfoServiceImpl roomInfoServiceImpl;


    /**
     * 物品清单
     * @author jixd
     * @created 2017年10月30日 14:18:30
     * @param
     * @return
     */
    @Override
    public String listItems(String itemType,String itemName) {
        LogUtil.info(LOGGER,"【listItems】参数",1);
        DataTransferObject dto = new DataTransferObject();
        dto.putValue("list",itemsServiceImpl.listItems(itemType,itemName));
        String str = dto.toJsonString();
        return str;
    }

    /**
     * 列出 房间和户型下面的 物品列表
     * @author jixd
     * @created 2017年10月30日 14:18:40
     * @param
     * @return
     */
    @Override
    public String listItemsByRoomIdAndHouseType(String roomId) {
        LogUtil.info(LOGGER,"【listItemsByRoomIdAndHouseType】参数roomId={}",roomId);
        DataTransferObject dto = new DataTransferObject();
        Optional<RoomInfoEntity> roomInfoOption = Optional.ofNullable(roomInfoServiceImpl.getRoomInfoByFid(roomId));
        if(!roomInfoOption.isPresent()){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间不存在");
            return dto.toJsonString();
        }
        RoomInfoEntity roomInfoEntity = roomInfoOption.get();
        //房间类型  0房间出租 1床位出租
        final Integer roomType = roomInfoEntity.getFtype();
        List<ItemConfigVo> itemList = new ArrayList<>();
        List<ExtRoomItemsConfigVo> roomItems = itemsServiceImpl.listRoomItemsByRoomId(roomId);
        if (!Check.NuNCollection(roomItems)){
            roomItems = roomItems.stream().filter(r -> r.getFtype() == roomType).collect(Collectors.toList());
            if (!Check.NuNCollection(roomItems)){
                for (ExtRoomItemsConfigVo roomItemsConfigVo : roomItems){
                    ItemConfigVo configVo = new ItemConfigVo();
                    itemList.add(configVo);
                    configVo.setItemId(roomItemsConfigVo.getItemid());
                    configVo.setItemName(roomItemsConfigVo.getItemName());
                    int type = Integer.parseInt(roomItemsConfigVo.getItemType());
                    configVo.setItemType(type);
                    configVo.setItemTypeName(ItemTypeEnum.getByCode(type).getName());
                    configVo.setNum(roomItemsConfigVo.getFnumber());
                    configVo.setPrice(roomItemsConfigVo.getItemPrice());
                }
            }
        }

		List<ExtHouseItemsConfigVo> houseItems = itemsServiceImpl.listHouseItemsExtByHouseTypeId(roomInfoEntity.getHousetypeid());
		houseItems = houseItems.stream().filter(h -> h.getFtype() == roomType).collect(Collectors.toList());
		if (!Check.NuNCollection(houseItems)){
			for (ExtHouseItemsConfigVo itemsConfigVo : houseItems){
				ItemConfigVo configVo = new ItemConfigVo();
				itemList.add(configVo);
				configVo.setItemId(itemsConfigVo.getFitemid());
				configVo.setItemName(itemsConfigVo.getFitemsname());
				int type = Integer.parseInt(itemsConfigVo.getFitemstype());
				configVo.setItemType(type);
				configVo.setItemTypeName(ItemTypeEnum.getByCode(type).getName());
				configVo.setNum(itemsConfigVo.getFitemsnum());
				configVo.setPrice(itemsConfigVo.getItemPrice());
			}
		}

		if (!Check.NuNCollection(itemList)){
			Map<String, List<ItemConfigVo>> groupMap = itemList.stream().collect(Collectors.groupingBy(ItemConfigVo::getItemId));
			itemList.clear();
			for (Map.Entry<String, List<ItemConfigVo>> entry : groupMap.entrySet()){
				List<ItemConfigVo> list = entry.getValue();
				itemList.add(list.get(0));
			}
		}

        dto.putValue("list",itemList);
        LogUtil.info(LOGGER,"物品列表list={}",itemList.size());
        return dto.toJsonString();
    }

    @Override
    public String saveRoomItemConfig(String paramJson) {
        LogUtil.info(LOGGER,"【listItemsByRoomIdAndHouseType】参数{}",paramJson);
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNStr(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        RoomItemSaveDto roomItemSaveDto = JsonEntityTransform.json2Object(paramJson, RoomItemSaveDto.class);
        if (Check.NuNStr(roomItemSaveDto.getItemId()) || Check.NuNStr(roomItemSaveDto.getRoomId())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }

        int i = itemsServiceImpl.saveRoomItem(roomItemSaveDto);
        dto.putValue("count",i);
        LogUtil.info(LOGGER,"【saveRoomItemConfig】返回结果dto={}",dto.toJsonString());
        return dto.toJsonString();
    }
    /**
	 * 根据roomId区分是房间还是床位，然后根据规则（如果是房间，获取房间物品配置，如果没有则获取该户型下的物品配置；
	 * 如果是床位，则获取床位物品配置，如果没有则获取对应户型下的物品配置，再与其父房间的物品取并集）获取对应的物品配置
	 *
	 * @Author: xiangbin
	 * @CreateDate: 2017年11月18日
	 */
    @Override
    public String getConfigsByRoomId(String roomId){
    	LogUtil.info(LOGGER, "【getConfigsByRoomId】入参：{}", roomId);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		List<ExtRoomItemsConfigVo> result = new ArrayList<>();
    		List<ExtRoomItemsConfigVo> beforeResult = new ArrayList<>();
    		RoomInfoEntity room = roomInfoServiceImpl.getRoomInfoByFid(roomId);
    		//类型：0:-房间；1-床位
    		if (room == null) {//没有查询到房间

    		} else if (room.getFtype() == RoomTypeEnum.BED.getCode()) {
    			beforeResult = getRoomOrHouseTypeConfigs(room.getFid(), room.getHousetypeid(), RoomTypeEnum.BED.getCode());
    		} else if (room.getFtype() == RoomTypeEnum.ROOM.getCode()) {
    			List<ExtRoomItemsConfigVo> bedConfigs = getRoomOrHouseTypeConfigs(room.getFid(), room.getHousetypeid(), RoomTypeEnum.ROOM.getCode());
    			List<ExtRoomItemsConfigVo> houseConfigs = getRoomOrHouseTypeConfigs(room.getParentId(), room.getHousetypeid(), RoomTypeEnum.BED.getCode());
    			bedConfigs.addAll(houseConfigs);
    			beforeResult.addAll(bedConfigs);
    		}

//    		Map typeMap = InitSelect.getSelectMap(ItemTypeEnum.class);
    		for (ExtRoomItemsConfigVo config : beforeResult) {
    			if(!Check.NuNStr(config.getItemid())){
    				config.setItemType(String.valueOf(ItemTypeEnum.getByCode(Integer.valueOf(config.getItemid())).getCode()));
    			}
    			result.add(config);
    		}
    		dto.putValue("result", result);
            return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.info(LOGGER, "【getConfigsByRoomId】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
    	}
    }
    
    /**
	 * 获取房间（床位）物品配置，如果没有则获取该户型下的物品配置
	 *
	 * @Author: xiangbin
	 * @CreateDate: 2017年11月18日
	 */
	private List<ExtRoomItemsConfigVo> getRoomOrHouseTypeConfigs(String roomId, String htId, int bedOrRoom) {

		//获取该房间下的物品
		List<ExtRoomItemsConfigVo> result = itemsServiceImpl.listRoomItemsByRoomId(roomId);

		RoomInfoEntity room = roomInfoServiceImpl.getRoomInfoByFid(roomId);
		//如果没有取到
		if (result == null || result.isEmpty()) {
			result = new ArrayList<>();
			//获取该房间户型下属于房间的物品
			List<HouseItemsConfigEntity> htConfigsList = itemsServiceImpl.listHouseItemsByHouseType(htId);
			//housetypeConfigService.findHouseTypeConfig(new Page(1, 100), htId);
			for (HouseItemsConfigEntity item : htConfigsList) {
				if ((int) item.getFtype() == bedOrRoom) {//属于房间或者床位的
					ExtRoomItemsConfigVo roomConf = fromHtConfToRoomConf(room, item);
					roomConf.setItemFrom(0);
					result.add(roomConf);
				}
			}
		} else {
			// 设置物品来源为房间（床位）
			for (ExtRoomItemsConfigVo roomConfig : result) {
				ItemListEntity item = itemsServiceImpl.findItemListByFid(roomConfig.getItemid());
						//iItemService.findItemById(roomConfig.getItemId().getId());
				roomConfig.setItemType(item.getFtype());	// 设置物品类型
				roomConfig.setItemFrom(1);
			}
		}
		return result;
	}
	

	public ExtRoomItemsConfigVo fromHtConfToRoomConf(RoomInfoEntity room, HouseItemsConfigEntity item){
		ExtRoomItemsConfigVo roomConf = new ExtRoomItemsConfigVo();
		roomConf.setRoomid(room.getFid());
		roomConf.setItemid(item.getFitemid());
		roomConf.setFtype(item.getFtype());
		roomConf.setItemName(item.getFitemsname());
		roomConf.setFitemcode(item.getFitemscode());
		roomConf.setFnumber(item.getFitemsnum());
		roomConf.setFnum(item.getFitemsnum());
		roomConf.setProjectid(item.getProjectid());
		roomConf.setCityid(item.getCityid());
		roomConf.setFcreatetime(new Date());
//		roomConf.setCreaterId(user.getId());
		roomConf.setFupdatetime(new Date());
//		roomConf.setUpdaterId(user.getId());
		roomConf.setFtype(item.getFtype());
		roomConf.setFvalid(item.getFvalid());
		return roomConf;
	}
	/**
	 * <p>查询物品项</p>
	 * @author xiangb
	 * @created 2017年11月20日
	 * @param
	 * @return
	 */
	@Override
	public String getItemListByFid(String itemId){
		LogUtil.info(LOGGER, "【getItemListByFid】入参：{}", itemId);
    	DataTransferObject dto = new DataTransferObject();
    	if(Check.NuNStr(itemId)){
    		dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("入参为空");
            return dto.toJsonString();
    	}
    	try{
    		ItemListEntity itemList = itemsServiceImpl.findItemListByFid(itemId);
    		if(Check.NuNObj(itemList)){
    			dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("未查询到对应的信息");
                return dto.toJsonString();
    		}
    		dto.putValue("itemList", itemList);
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.error(LOGGER, "【getItemListByFid】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
    	}
	}
	/**
	 * <p>保存或更新物品项</p>
	 * @author xiangb
	 * @created 2017年11月20日
	 * @param
	 * @return
	 */
	@Override
	public String saveOrUPdateItemList(String param){
		LogUtil.info(LOGGER, "【saveOrUPdateItemList】入参：{}", param);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		RoomItemsConfigEntity roomItemsConfigEntity = JsonEntityTransform.json2Entity(param, RoomItemsConfigEntity.class);
        	if(Check.NuNObj(roomItemsConfigEntity)){
        		dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数异常");
                return dto.toJsonString();
        	}
    		if(Check.NuNStr(roomItemsConfigEntity.getFid())){
    			itemsServiceImpl.saveRoomItemsConfigEntity(roomItemsConfigEntity);
    		}else{
    			itemsServiceImpl.updateRoomItemsConfigEntity(roomItemsConfigEntity);
    		}
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.error(LOGGER, "【saveOrUPdateItemList】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
    	}
	}
	/**
     * 多条件查询房间物品信息
     * @author xiangbin
     * @created 2017年11月20日
     * @param
     * @return
     */
	@Override
	public String selectRoomItemsConfigByParams(String param){
		LogUtil.info(LOGGER, "【selectRoomItemsConfigByParams】入参：{}", param);
    	DataTransferObject dto = new DataTransferObject();
    	try{
    		Map<String,String> map = (Map<String,String>)JsonEntityTransform.json2Map(param);
        	String roomId = map.get("roomId");
        	String projectId = map.get("projectId");
        	String itemId = map.get("itemId");
        	RoomItemsConfigEntity roomItemsConfigEntity = itemsServiceImpl.selectRoomItemsConfigByParams(roomId, projectId, itemId);
        	dto.putValue("roomItemsConfigEntity", roomItemsConfigEntity);
    		return dto.toJsonString();
    	}catch(Exception e){
    		LogUtil.error(LOGGER, "【selectRoomItemsConfigByParams】出错：{}", e);
    		dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统异常");
            return dto.toJsonString();
    	}
	}
}
