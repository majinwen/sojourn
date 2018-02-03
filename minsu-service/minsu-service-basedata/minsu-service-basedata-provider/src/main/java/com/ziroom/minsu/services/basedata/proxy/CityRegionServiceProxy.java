package com.ziroom.minsu.services.basedata.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.CityRegionRelEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityRegionService;
import com.ziroom.minsu.services.basedata.service.CityRegionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>大区服务代理类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017年01月09日 10:50:11
 * @since 1.0
 * @version 1.0
 */
@Component("basedata.cityRegionServiceProxy")
public class CityRegionServiceProxy implements CityRegionService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CityRegionServiceProxy.class);

    @Resource(name = "basedata.cityRegionServiceImpl")
    private CityRegionServiceImpl cityRegionServiceImpl;


    @Override
    public String insertCityRegion(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(paramJson)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        CityRegionEntity cityRegionEntity = new CityRegionEntity();
        cityRegionEntity.setRegionName(paramJson);
        CityRegionEntity cityRegion = cityRegionServiceImpl.findCityRegionByName(paramJson);
        if (!Check.NuNObj(cityRegion)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("大区已经存在");
            return dto.toJsonString();
        }
        cityRegionEntity.setFid(UUIDGenerator.hexUUID());
        int i = cityRegionServiceImpl.insertCityRegion(cityRegionEntity);
        dto.putValue("count",i);
        dto.putValue("region",cityRegionEntity);
        return dto.toJsonString();
    }

    @Override
    public String insertCityRegionRel(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        CityRegionRelEntity cityRegionRelEntity = JsonEntityTransform.json2Entity(paramJson, CityRegionRelEntity.class);
        if (Check.NuNObj(cityRegionRelEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(cityRegionRelEntity.getRegionFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(cityRegionRelEntity.getProvinceCode())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("省份编码为空");
            return dto.toJsonString();
        }
        int i = cityRegionServiceImpl.insertCityRegionRel(cityRegionRelEntity);
        dto.putValue("count",i);
        return dto.toJsonString();
    }

    @Override
    public String updateCityRegion(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        CityRegionEntity cityRegionEntity = JsonEntityTransform.json2Entity(paramJson, CityRegionEntity.class);
        if (Check.NuNObj(cityRegionEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        if (Check.NuNStr(cityRegionEntity.getFid())){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int i = cityRegionServiceImpl.updateCityRegion(cityRegionEntity);
        dto.putValue("count",i);
        return dto.toJsonString();
    }

    @Override
    public String updateCityRegionRel(String paramJson) {
        DataTransferObject dto = new DataTransferObject();
        CityRegionRelEntity cityRegionRelEntity = JsonEntityTransform.json2Entity(paramJson, CityRegionRelEntity.class);
        if (Check.NuNObj(cityRegionRelEntity)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int i = cityRegionServiceImpl.updateCityRegionRel(cityRegionRelEntity);
        dto.putValue("count",i);
        return dto.toJsonString();
    }

    @Override
    public String delCityRegion(String fid) {
        DataTransferObject dto = new DataTransferObject();
        if (Check.NuNObj(fid)){
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("参数为空");
            return dto.toJsonString();
        }
        int i = cityRegionServiceImpl.delCityRegion(fid);
        dto.putValue("count",i);
        return dto.toJsonString();

    }

    @Override
    public String fillAllRegion() {
        DataTransferObject dto = new DataTransferObject();
        List<CityRegionEntity> list = cityRegionServiceImpl.findAllRegion();
        dto.putValue("list",list);
        return dto.toJsonString();
    }

}
