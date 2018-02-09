package com.ziroom.minsu.troy.cms.service;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.api.inner.ConfCityService;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.valenum.common.YesOrNoEnum;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
@Service("api.cityService")
public class CityService {

    /**
     * 日志对象
     */
    private static Logger LOGGER = LoggerFactory.getLogger(CityService.class);

    @Resource(name="basedata.confCityService")
    private ConfCityService confCityService;

    /**
     * 获取开放城市列表
     * @author afi
     * @created 2016年9月15日
     * @param
     */
    public List<Map> getOpenCityList(){
        List<Map> openList = null;
        try{
            String resultJson =  confCityService.getOpenCity();
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            openList = resultDto.parseData("list", new TypeReference<List<Map>>() {});
        }catch(Exception ex){
            LogUtil.error(LOGGER, "error:{}", ex);
            openList = new ArrayList<Map>();
        }
        return openList;
    }


    /**
     * 获取城市键值对
     * @author afi
     * @created 2016年9月15日
     * @param
     */
    public Map<String, String> getOpenCityMap(){
        Map<String, String> cityMap = null;
        try{
            String cityJson = confCityService.getOpenCityMap();
            DataTransferObject cityDto = JsonEntityTransform.json2DataTransferObject(cityJson);
           cityMap = cityDto.parseData("map", new TypeReference<Map<String, String>>() {});

        }catch(Exception ex){
            LogUtil.error(LOGGER, "error:{}", ex);
            cityMap = new HashMap<>();
        }
        return cityMap;
    }



    /**
     * 城市code处理
     * @author liyingjie
     * @created 2016年7月14日
     *
     */
    public String getCityCode(String allCityCode,String [] cityCodeArray){
        String cityCode = "";
        if(Check.NuNStr(allCityCode) && Check.NuNObj(cityCodeArray)){
            cityCode = "0" ;
            return cityCode;
        }
        if(!Check.NuNStr(allCityCode)){
            if (allCityCode.equals(ValueUtil.getStrValue(YesOrNoEnum.YES.getCode()))){
                cityCode = "0" ;
                return cityCode;
            }
        }
        if(!Check.NuNObj(cityCodeArray)){
            for(int i=0;i<cityCodeArray.length;i++){
                if(i==0){
                    cityCode += cityCodeArray[i];
                }else {
                    cityCode += ","+cityCodeArray[i];
                }
            }
        }
        return cityCode;
    }
}
