package com.ziroom.zrp.service.trading.dep;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.api.RoomService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>房间依赖层</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月27日 18:38
 * @since 1.0
 */
@Component("trading.roomDep")
public class RoomDep {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDep.class);

    @Resource(name = "houses.roomService")
    private RoomService roomService;

    /**
     * 查询房间信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public List<RoomInfoEntity> getRoomListByRoomIds(String roomIds) {
        String roomListResult = roomService.getRoomListByRoomIds(roomIds);
        DataTransferObject roomListDto =  JsonEntityTransform.json2DataTransferObject(roomListResult);
        List<RoomInfoEntity> roomInfoEntityList = null;
        if (DataTransferObject.SUCCESS == roomListDto.getCode()) {
            try {
                roomInfoEntityList = SOAResParseUtil.getListValueFromDataByKey(roomListResult, "roomInfoList", RoomInfoEntity.class);
            } catch (SOAParseException e) {
                LogUtil.error(LOGGER, "间查询结果为空:" + roomIds, e);
            }
        }
        return roomInfoEntityList;
    }

    /**
     * 查询房间信息
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public Map<String, RoomInfoEntity> getRoomMapByRoomIds(String roomIds) {
        List<RoomInfoEntity>  roomInfoEntityList = this.getRoomListByRoomIds(roomIds);
        if (roomInfoEntityList == null || roomInfoEntityList.size() == 0) {
            return null;
        }
        Map<String, RoomInfoEntity> roomInfoEntityMap = roomInfoEntityList.stream().collect(Collectors.toMap(RoomInfoEntity::getFid, roomInfoEntity -> roomInfoEntity));
        return roomInfoEntityMap;
    }

    /**
     * 将房间状态由签约进行中改为已出租
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public boolean updateRoomStateFromSigningToRental(String roomIds) {
        String result = roomService.updateRoomStateFromSigningToRental(roomIds);
        DataTransferObject dto =  JsonEntityTransform.json2DataTransferObject(result);
        return  dto.getCode() == DataTransferObject.SUCCESS;

    }

    /**
     * 查询房间信息 通过父房间标识
     * @param roomId 房间标识
     * @return
     * @author cuigh6
     * @Date 2018年2月7日
     */
    public List<RoomInfoEntity> getRoomInfoByParentId(String roomId) {

        try {
            String roomListJson = this.roomService.getRoomInfoByParentId(roomId);
            DataTransferObject dataTransferObject = JsonEntityTransform.json2DataTransferObject(roomListJson);
            if (dataTransferObject.getCode() == DataTransferObject.SUCCESS) {
                List<RoomInfoEntity> roomList = dataTransferObject.parseData("roomList", new TypeReference<List<RoomInfoEntity>>() {
                });
                return roomList;
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getRoomInfoByParentId】查询子房间列表报错,roomId={}", roomId, e);
        }
        return new ArrayList<>();
    }

}
