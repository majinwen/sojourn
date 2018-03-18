package com.ziroom.zrp.houses.entity;

import java.util.Date;

import com.asura.framework.base.entity.BaseEntity;

/**
 * 
 * <p>房间表扩展</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class RoomInfoExtEntity extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8587954659219952959L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 逻辑fid
     */
    private String fid;

    /**
     * 房间fid
     */
    private String roomFid;

    /**
     * 是否绑定智能电表 0-未绑定，1-已绑定
     */
    private Integer isBindAmmeter;

    /**
     * 是否绑定智能水表 0-未绑定，1-已绑定
     */
    private Integer isBindWatermeter;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后更新时间
     */
    private Date lastModifyDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid == null ? null : roomFid.trim();
    }

    public Integer getIsBindAmmeter() {
        return isBindAmmeter;
    }

    public void setIsBindAmmeter(Integer isBindAmmeter) {
        this.isBindAmmeter = isBindAmmeter;
    }

    public Integer getIsBindWatermeter() {
        return isBindWatermeter;
    }

    public void setIsBindWatermeter(Integer isBindWatermeter) {
        this.isBindWatermeter = isBindWatermeter;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }
}