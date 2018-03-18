package com.ziroom.zrp.service.houses.dao;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.RentRoomInfoDto;

public class RoomRntInfoTest extends BaseTest{
	
	@Resource(name="houses.roomRentInfoDao")
	private RoomRentInfoDao roomRentInfoDao;
	
}
