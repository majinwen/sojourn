package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.codehaus.jackson.type.TypeReference;
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
 * @Date Created in 2017年10月16日 15:14
 * @since 1.0
 */
public class CityServiceProxyTest extends BaseTest{

    @Resource(name="houses.cityServiceProxy")
    private CityServiceProxy cityServiceProxy;

    @Test
    public void testFindById() {
        String[] cityIds = new String[]{"110000", ""};
        for (String cityId : cityIds) {
            String result = cityServiceProxy.findById(cityId);
            DataTransferObject  dto  = JsonEntityTransform.json2Object(result, DataTransferObject.class);
            System.out.println(dto.getCode() + "," + dto.getMsg() + ":" + dto.toJsonString());
            if (DataTransferObject.SUCCESS ==  dto.getCode()) {
                CityEntity cityEntity = dto.parseData("data", new TypeReference<CityEntity>() {
                });
                System.out.println("cityEntity:" + cityEntity.getCityname());
            }
        }



    }

}
