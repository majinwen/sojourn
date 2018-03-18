package com.zra.house.dao;

import com.zra.house.entity.HouseImgEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
 * @date 2016/8/5 17:55
 * @since 1.0
 */
@Repository
public interface HouseImgMapper {

    /**
     * 根据房型id查询房型图片信息
     * @param houseTypeId
     * @return
     */
    List<HouseImgEntity> findImgByHouseTypeId(@Param("houseTypeId") String houseTypeId);

}
