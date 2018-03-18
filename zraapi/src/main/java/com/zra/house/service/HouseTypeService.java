package com.zra.house.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zra.house.dao.HouseTypeMapper;
import com.zra.house.entity.HouseTypeEntity;
import com.zra.house.entity.dto.HouseTypeDetailDto;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/8 10:12
 * @since 1.0
 */
@Service
public class HouseTypeService {

    @Autowired
    private HouseTypeMapper houseTypeMapper;

    /**
     * id获取房型信息
     * @param houseTypeId
     * @return
     */
    public HouseTypeEntity findHouseTypeById(String houseTypeId){
        return houseTypeMapper.findHouseTypeById(houseTypeId);
    }

    /**
     * 数据处理
     * @param houseTypeEntityList
     * @return
     */
    public List<HouseTypeDetailDto> convert(List<HouseTypeEntity> houseTypeEntityList){
        List<HouseTypeDetailDto> roomDetailDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(houseTypeEntityList)){
            for (HouseTypeEntity houseTypeEntity: houseTypeEntityList){
                roomDetailDtoList.add(convert(houseTypeEntity));
            }
        }
        return roomDetailDtoList;
    }

    /**
     * 数据处理
     * @param houseTypeEntity
     * @return
     */
    public HouseTypeDetailDto convert(HouseTypeEntity houseTypeEntity){
        HouseTypeDetailDto houseTypeDetailDto = new HouseTypeDetailDto();
        if(houseTypeEntity != null){
            BeanUtils.copyProperties(houseTypeEntity, houseTypeDetailDto);
        }
        return houseTypeDetailDto;
    }
    
    /**
     * 获取所有户型
     * @author tianxf9
     * @return
     */
    public List<HouseTypeEntity> findAllHouseType() {
    	return houseTypeMapper.findAllHouseType();
    }

    public String getPhoneByHtId(String houseTypeId) {
        return houseTypeMapper.getPhoneByHtId(houseTypeId);
    }

}
