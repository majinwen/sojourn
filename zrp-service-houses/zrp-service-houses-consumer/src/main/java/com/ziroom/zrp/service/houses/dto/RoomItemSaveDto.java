package com.ziroom.zrp.service.houses.dto;

/**
 * <p>房间配置物品保存</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月02日 18:17
 * @since 1.0
 */
public class RoomItemSaveDto {

    private String roomId;

    private String itemId;

    private String createId;

    public RoomItemSaveDto() {
    }

    public RoomItemSaveDto(String roomId, String itemId, String createId) {

        this.roomId = roomId;
        this.itemId = itemId;
        this.createId = createId;
    }


    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }
}
