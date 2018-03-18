package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.zrp.houses.entity.CityEntity;
import com.ziroom.zrp.service.houses.api.CityService;
import com.ziroom.zrp.service.houses.service.CityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
@Component("houses.cityServiceProxy")
public class CityServiceProxy implements CityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceProxy.class);

    @Resource(name="houses.cityServiceImpl")
    private CityServiceImpl cityServiceImpl;

    @Override
    public String findById(String id) {
        DataTransferObject dto = new DataTransferObject();
        try {
            if (Check.NuNStrStrict(id)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("城市id为空");
                return dto.toJsonString();
            }
            CityEntity  cityEntity = cityServiceImpl.findById(id);
            dto.putValue("data", cityEntity);
            return dto.toJsonString();

        } catch (Exception e) {
            LogUtil.error(LOGGER,"", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("findById 系统异常");
            return dto.toJsonString();
        }

    }

    @Override
    public String findCityList() {
        DataTransferObject dto = new DataTransferObject();
        try {
            List<CityEntity> cityEntities = cityServiceImpl.findCityList();
            dto.putValue("cityEntities",cityEntities);
        }catch (Exception e){
            LogUtil.error(LOGGER, "【findCityList】 error:{}", e);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("系统错误");
        }
        return dto.toJsonString();
    }
}
