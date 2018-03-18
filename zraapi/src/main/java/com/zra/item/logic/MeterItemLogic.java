package com.zra.item.logic;

/**
 * Created by PC on 2016/9/23.
 */

import com.zra.common.dto.bedInfo.ItemDto;
import com.zra.common.dto.meteritem.MeterItemParamDto;
import com.zra.item.entity.ItemdeliveryEntity;
import com.zra.item.service.MeterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 物品合同配置logic
 *
 * @author xiaona
 *
 */
@Component
public class MeterItemLogic {

	@Autowired
	private MeterItemService meterItemService;

	@Autowired
	private ItemLogic itemlogic;
	/**
	 * 根据物品id和合同以及房间床位更新合同以及房间物品配置表
	 * @return
	 */
	public boolean saveItemConfigById(MeterItemParamDto paramDto){
		boolean isSuccess=meterItemService.saveItemConfigByIds(paramDto);
		return  isSuccess;
	}
}
