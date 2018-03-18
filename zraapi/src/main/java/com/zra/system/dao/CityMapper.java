package com.zra.system.dao;

import com.zra.system.entity.CityEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>城市dao</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/7/29 18:54
 * @since 1.0
 */
@Repository
public interface CityMapper {

    /**
     * 查询所有城市
     * @return
     */
    List<CityEntity> findAllCity();
    
    /**
     * 获取所有城市包括不限
     * @author tianxf9
     * @return
     */
    List<CityEntity> findAllCity2();
}
