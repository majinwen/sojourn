package com.zra.house.dao;

import com.zra.house.entity.dto.HouseConfigDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>房屋配置dao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/4 13:59
 * @since 1.0
 */
@Repository
public interface HouseItemsConfigMapper {

    /**
     *
     * 查询网站显示户型配置物品
     * @param houseTypeId
     * @return
     */
    List<HouseConfigDto> findItemsConfigByHouseTypeId(@Param("houseTypeId") String houseTypeId);
}
