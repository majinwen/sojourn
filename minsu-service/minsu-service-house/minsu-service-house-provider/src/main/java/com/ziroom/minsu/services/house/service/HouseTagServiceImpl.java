/**
 * @FileName: HouseTagServiceImpl.java
 * @Package com.ziroom.minsu.services.house.service
 * 
 * @author yd
 * @created 2017年3月17日 下午8:44:45
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.services.house.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ziroom.minsu.services.house.dao.HouseTagDao;

/**
 * <p>房源标签具体实现</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
@Service("house.houseTagServiceImpl")
public class HouseTagServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HouseTagServiceImpl.class);
	
	@Resource(name = "house.houseTagDao")
	private HouseTagDao  houseTagDao;
	
	/**
	 * 
	 * 条件查询房源标签 返回标签fids 集合
	 *
	 * @author yd
	 * @created 2017年3月17日 下午7:35:34
	 *
	 * @param params
	 * @return
	 */
	public List<String> findHouseTagByParams(Map<String, Object> params){
		return this.houseTagDao.findHouseTagByParams(params);
	}

}
