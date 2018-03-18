package com.ziroom.minsu.report.common.util;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.report.basedata.vo.NationRegionCityVo;

import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/3/10.
 * @version 1.0
 * @since 1.0
 */
public class NationRegionCityUtil {


    /**
     * 填充当前的城市信息
     * @param list
     * @param map
     * @param cityList
     */
    public static void fillNationRegionCity(List<NationRegionCityVo> list, Map<String,NationRegionCityVo> map,List<String> cityList){
        if (Check.NuNCollection(list) || Check.NuNObjs(map,cityList)){
            return;
        }
        for (NationRegionCityVo cityVo : list) {
            map.put(cityVo.getCityCode(),cityVo);
            cityList.add(cityVo.getCityCode());
        }
    }


}
