package com.zra.system.logic;

import com.zra.system.entity.CityEntity;
import com.zra.system.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>城市逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/7/29 18:37
 * @since 1.0
 */
@Component
public class CityLogic {

    @Autowired
    private CityService cityService;

    /**
     * 查询所有城市
     * @return
     */
    public List<CityEntity> findAllCity(){
        return cityService.findAllCity();
    }
    
    /**
     * 查询所有城市包括所有
     * @author tianxf9
     * @return
     */
    public List<CityEntity> findAllCity2() {
    	return cityService.findAllCity2();
    }
}
