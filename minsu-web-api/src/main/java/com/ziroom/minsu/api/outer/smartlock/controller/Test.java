/**
 * @FileName: Test.java
 * @Package com.ziroom.minsu.api.outer.smartlock.controller
 * 
 * @author liujun
 * @created 2016年6月24日
 * 
 * Copyright 2016 ziroom
 */
package com.ziroom.minsu.api.outer.smartlock.controller;

import java.util.ArrayList;
import java.util.List;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.house.dto.SmartLockDto;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liujun
 * @since 1.0
 * @version 1.0
 */
public class Test {
	public static void main(String[] args) {
		SmartLockDto dto = new SmartLockDto();
		dto.setHouseFid("8a9e9aae5419cc22015419cc250a0002");
		
		List<String> list = new ArrayList<String>();
		list.add("8a9e9aae5419d73b015419d73ddb0001");
		
		dto.setRoomFidList(list);
		
		System.err.println(JsonEntityTransform.Object2Json(dto));
		
	}
}
