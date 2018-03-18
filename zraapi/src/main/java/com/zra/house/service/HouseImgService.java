package com.zra.house.service;

import com.zra.house.dao.HouseImgMapper;
import com.zra.house.entity.HouseImgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
 * @date 2016/8/1 17:26
 * @since 1.0
 */
@Service
public class HouseImgService {

    @Autowired
    private HouseImgMapper houseImgMapper;

    /**
     * 根据房型id查询房型图片信息
     * @param houseTypeId
     * @return
     */
    public List<HouseImgEntity> findImgByHouseTypeId(String houseTypeId){
        return houseImgMapper.findImgByHouseTypeId(houseTypeId);
    }
}
