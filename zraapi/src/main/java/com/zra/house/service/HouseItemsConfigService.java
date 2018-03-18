package com.zra.house.service;

import com.zra.house.dao.HouseItemsConfigMapper;
import com.zra.house.entity.dto.HouseConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/4 14:41
 * @since 1.0
 */
@Service
public class HouseItemsConfigService {

    @Autowired
    private HouseItemsConfigMapper houseItemsConfigMapper;

    /**
     * 查询网站显示户型配置物品
     * @param houseTypeId
     * @return
     */
    public List<HouseConfigDto> findItemsConfigByHouseTypeId(String houseTypeId){
        return houseItemsConfigMapper.findItemsConfigByHouseTypeId(houseTypeId);
    }
}
