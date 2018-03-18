package com.ziroom.zrp.service.trading.utils;

import java.math.BigDecimal;
import java.util.Properties;

import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.RoomInfoEntity;
import com.ziroom.zrp.service.trading.pojo.LeaseCyclePojo;
import com.ziroom.zrp.service.trading.utils.factory.LeaseCycleFactory;
import com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.PropertiesUtils;
import com.ziroom.zrp.houses.entity.ProjectEntity;
import com.ziroom.zrp.service.trading.proxy.calculation.DayLeaseCycleImpl;
import com.ziroom.zrp.trading.entity.RentContractEntity;
import com.zra.common.constant.BillMsgConstant;
import com.zra.common.vo.contract.ProjectInfoVo;

public class RentHeadInfoUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RentHeadInfoUtil.class);
	
	public static ProjectInfoVo getRentHeadInfo(RentContractEntity rentContractEntity,ProjectEntity projectEntity,RoomInfoEntity roomInfoEntity){
		ProjectInfoVo projectInfo = new ProjectInfoVo();
		if(Check.NuNObj(rentContractEntity) || Check.NuNObj(projectEntity)){
			return projectInfo;
		}
		projectInfo.setProName(projectEntity.getFname());
		projectInfo.setProAddress(projectEntity.getFaddress());
		if(!Check.NuNStr(projectEntity.getFHeadFigureUrl())){
			Properties properties = PropertiesUtils.getProperties("trading.properties");
			projectInfo.setProHeadFigureUrl(properties.getProperty("pic_url").trim()+projectEntity.getFHeadFigureUrl());
		}
		//计算租金的静态类
		LeaseCyclePojo leaseCyclePojo = new LeaseCyclePojo(rentContractEntity.getConType(),rentContractEntity.getProjectId(),roomInfoEntity.getRentType());
		String roomSalesPrice = null;
		if(Check.NuNObj(rentContractEntity.getFactualprice())){//实际出房价为空，取计划出房价
			try {
				roomSalesPrice = LeaseCycleFactory.createLeaseCycle(leaseCyclePojo).calculateActualRoomPrice(rentContractEntity.getConRentYear(), rentContractEntity.getRoomSalesPrice());
			} catch (Exception e) {
				LogUtil.error(LOGGER, "计算房间价格出错,e:{}", e);
			}
		}else{
			BigDecimal actualPrice = new BigDecimal(rentContractEntity.getFactualprice()).setScale(0, BigDecimal.ROUND_HALF_UP);
			if(LeaseCycleEnum.DAY.getCode().equals(rentContractEntity.getConType())){
				roomSalesPrice = String.format("%s%s/日",
    					BillMsgConstant.RMB, actualPrice);
			}else{
				roomSalesPrice = String.format("%s%s/月",
    					BillMsgConstant.RMB, actualPrice);
			}
		}
		projectInfo.setRoomSalesPrice(roomSalesPrice);
		return projectInfo;
	}
}
