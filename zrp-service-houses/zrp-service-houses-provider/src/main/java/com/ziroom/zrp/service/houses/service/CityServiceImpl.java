package com.ziroom.zrp.service.houses.service;

import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.service.houses.dao.CityDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月16日 15:07
 * @since 1.0
 */
@Service("houses.cityServiceImpl")
public class CityServiceImpl {

    @Resource(name="houses.cityDao")
    private CityDao cityDao;

    /**
     * 查询city实体
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public CityEntity findById(String id) {
        return cityDao.selectByPrimaryKey(id);
    }

    /**
      * @description: 查询城市集合
      * @author: lusp
      * @date: 2017/10/19 下午 16:08
      * @params:
      * @return: List<CityEntity>
      */
    public List<CityEntity> findCityList(){
        return cityDao.findCityList();
    }
}
