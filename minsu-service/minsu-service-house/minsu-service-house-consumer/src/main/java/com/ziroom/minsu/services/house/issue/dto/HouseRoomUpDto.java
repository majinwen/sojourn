package com.ziroom.minsu.services.house.issue.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>更新房间信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年07月03日 18:23
 * @since 1.0
 */
public class HouseRoomUpDto {
    /**
     * 房源fid
     */
    private String houseBaseFid;
    /**
     *
     */
    private String roomFid;
    /**
     * 出租方式
     */
    private Integer rentWay;
    /**
     * 可出租房间数
     */
    private Integer rentRoomNum;

    /**
     * 待删除房间列表
     */
    private List<String> delRoomFidList = new ArrayList<>();
    
    /**
     * 操作步骤
     */
    private Integer step;
    

    public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public Integer getRentRoomNum() {
        return rentRoomNum;
    }

    public void setRentRoomNum(Integer rentRoomNum) {
        this.rentRoomNum = rentRoomNum;
    }

    public List<String> getDelRoomFidList() {
        return delRoomFidList;
    }

    public void setDelRoomFidList(List<String> delRoomFidList) {
        this.delRoomFidList = delRoomFidList;
    }

    public String getHouseBaseFid() {
        return houseBaseFid;
    }

    public void setHouseBaseFid(String houseBaseFid) {
        this.houseBaseFid = houseBaseFid;
    }

    public String getRoomFid() {
        return roomFid;
    }

    public void setRoomFid(String roomFid) {
        this.roomFid = roomFid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }
}
