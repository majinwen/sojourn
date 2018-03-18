package com.ziroom.zrp.service.houses.dao;

import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

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
 * @Date Created in 2017年10月16日 15:00
 * @since 1.0
 */
public class CityDaoTest extends BaseTest{

    @Resource(name="houses.cityDao")
    private CityDao cityDao;

    @Test
    public void testSelectByPrimaryKey() {
        CityEntity cityEntity = cityDao.selectByPrimaryKey("110000");
        System.out.println("cityEntity.getCenterLat():" + cityEntity.getCenterLat());
    }

}
