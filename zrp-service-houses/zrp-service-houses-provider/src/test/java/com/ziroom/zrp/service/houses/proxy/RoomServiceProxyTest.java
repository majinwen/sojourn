package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

import com.ziroom.zrp.service.houses.dto.RoomStmartDto;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月07日 11:39
 * @since 1.0
 */
public class RoomServiceProxyTest extends BaseTest{

    @Resource(name = "houses.roomServiceProxy")
    private RoomServiceProxy roomServiceProxy;

    @Test
    public void testGetRoomByFid(){
        String roomEntity = roomServiceProxy.getRoomByFid("1001236");
        System.err.println(roomEntity);
    }


    @Test
    public void testGetRoomListByRoomIds(){
        String roomIds = "1001255,1001257";
        String result = roomServiceProxy.getRoomListByRoomIds(roomIds);
        System.out.println("result:" + result);
    }
    
    @Test
    public void testgetRentRoomByRoomId(){
    	String roomId = "1002848";
    	String  rentRoom = roomServiceProxy.getRentRoomInfoByRoomId(roomId);
    	DataTransferObject rentRoomObject = JsonEntityTransform.json2DataTransferObject(rentRoom);
    	if(rentRoomObject.getCode() == DataTransferObject.SUCCESS){
    		RentRoomInfoDto rentRoomDto = rentRoomObject.parseData("rentRoom", new TypeReference<RentRoomInfoDto>() {
    		});
    		System.err.println(JSONObject.toJSON(rentRoomDto));
    	}
    }

    @Test
    public void updateRoomStateFromSigningToRental() {
        String roomIds = "1001239,1001240";
        String result = roomServiceProxy.updateRoomStateFromSigningToRental(roomIds);
        System.out.println("updateRoomStateFromSigningToRental:" + result);
    }

    @Test
    public void testfindRoomListForPage(){
        String paramJson = "{\"page\":1,\"limit\":10,\"cityid\":\"\",\"projectid\":null,\"housetypeid\":null,\"buildingid\":null,\"ffloornumber\":null,\"froomnumber\":\"\"}";
        String resultJson = roomServiceProxy.findRoomListForPage(paramJson);
    }

    @Test
    public  void  findRoomContractSmartByPage(){

        RoomStmartDto  roomStmartDto = new RoomStmartDto();
        roomStmartDto.setUserName("李磊");
        String resultJson = this.roomServiceProxy.findRoomContractSmartByPage(JsonEntityTransform.Object2Json(roomStmartDto));
        System.out.println(resultJson);
    }
    
    
    @Test
    public void updateRoomsState() {
    	List<String> roomIds = new ArrayList<>();
    	roomIds.add("8a9099cb57a270320157a85bd7010046");
    	roomIds.add("8a9099cb57d2b3650157dc69cb360107");
    	String roomsIdStr = JsonEntityTransform.Object2Json(roomIds);
    	this.roomServiceProxy.updateRoomStateRent(roomsIdStr);
    }

}
