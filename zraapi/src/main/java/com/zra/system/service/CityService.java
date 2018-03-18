package com.zra.system.service;

import com.zra.house.entity.dto.CityDto;
import com.zra.system.dao.CityMapper;
import com.zra.system.entity.CityEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>城市service</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/7/29 18:39
 * @since 1.0
 */
@Service
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    /**
     * 查询所有城市
     * @return
     */
    public List<CityEntity> findAllCity(){
        return cityMapper.findAllCity();
    }
    
    /**
     * 获取所有城市包括全国
     * @author tianxf9
     * @return
     */
    public List<CityEntity> findAllCity2() {
    	return cityMapper.findAllCity2();
    }

    /**
     * 数据处理
     * @param cityEntity
     * @return
     */
    public static CityDto convert( CityEntity cityEntity){
        CityDto cityDto = new CityDto();
        BeanUtils.copyProperties(cityEntity, cityDto);
        return cityDto;
    }

    /**
     * 数据处理
     * @param cityEntityList
     * @return
     */
    public static List<CityDto> convert(List<CityEntity> cityEntityList){
        List<CityDto> cityDtoList = new ArrayList<>();
        for (CityEntity cityEntity: cityEntityList){
            cityDtoList.add(convert(cityEntity));
        }
        return cityDtoList;
    }
}
