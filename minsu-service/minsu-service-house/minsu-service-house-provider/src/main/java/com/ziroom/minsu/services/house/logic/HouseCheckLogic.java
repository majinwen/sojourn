/**
 * @FileName: HouseCheckLogic.java
 * @Package com.ziroom.minsu.services.house.logic
 * 
 * @author bushujie
 * @created 2016年4月5日 下午6:03:42
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.logic;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.springframework.stereotype.Component;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.house.HouseBaseMsgEntity;
import com.ziroom.minsu.entity.house.HouseRoomMsgEntity;
import com.ziroom.minsu.services.house.service.HouseManageServiceImpl;

/**
 * <p>公用判断</p>
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
@Component("house.houseCheckLogic")
public class HouseCheckLogic {
	
    @Resource(name = "house.validator")
    private Validator validator;
    
    /**
     * 
     * 判断房源是否存在
     *
     * @author bushujie
     * @created 2016年4月5日 下午6:11:06
     *
     * @param houseManageServiceImpl
     * @param dto
     * @return
     */
    public boolean checkHouseBaseNull(HouseManageServiceImpl houseManageServiceImpl,DataTransferObject dto,String houseBaseFid){
    	HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseBaseFid);
		//判断房源是否存在
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源不存在");
            return false;
		}
		dto.putValue("landlordUid", houseBaseMsgEntity.getLandlordUid());
		dto.putValue("houseName", houseBaseMsgEntity.getHouseName());
    	return true;
    }
    /**
     * 
     * 判断房间是否存在
     *
     * @author bushujie
     * @created 2016年4月5日 下午6:21:22
     *
     * @param houseManageServiceImpl
     * @param dto
     * @param houseRoomFid
     * @return
     */
    public boolean checkHouseRoomNull(HouseManageServiceImpl houseManageServiceImpl,DataTransferObject dto,String houseRoomFid){
    	HouseRoomMsgEntity houseRoomMsgEntity=houseManageServiceImpl.getHouseRoomByFid(houseRoomFid);
		//判断房间是否存在
		if(Check.NuNObj(houseRoomMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房间不存在");
            return false;
		}
		HouseBaseMsgEntity houseBaseMsgEntity=houseManageServiceImpl.getHouseBaseMsgEntityByFid(houseRoomMsgEntity.getHouseBaseFid());
		if(Check.NuNObj(houseBaseMsgEntity)){
			dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("房源不存在");
            return false;
		}
		dto.putValue("houseFid", houseRoomMsgEntity.getHouseBaseFid());
		dto.putValue("landlordUid", houseBaseMsgEntity.getLandlordUid());
		dto.putValue("houseName", houseBaseMsgEntity.getHouseName());
		dto.putValue("roomName", houseRoomMsgEntity.getRoomName());
    	return true;
    }
}
