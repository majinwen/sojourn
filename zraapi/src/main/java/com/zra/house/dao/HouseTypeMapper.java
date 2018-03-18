package com.zra.house.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zra.house.entity.HouseTypeEntity;

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
 * @date 2016/8/8 9:51
 * @since 1.0
 */
@Repository
public interface HouseTypeMapper {

    /**
     * id获取房型信息
     * @param houseTypeId
     * @return
     */
    HouseTypeEntity findHouseTypeById(@Param("houseTypeId") String houseTypeId);
    
    /**
     * 查询说有户型
     * @author tianxf9
     * @return
     */
    List<HouseTypeEntity> findAllHouseType();

    String getPhoneByHtId(@Param("houseTypeId") String houseTypeId);
}
