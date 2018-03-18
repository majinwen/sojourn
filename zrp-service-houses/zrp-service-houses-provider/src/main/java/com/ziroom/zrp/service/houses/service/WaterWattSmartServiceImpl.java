package com.ziroom.zrp.service.houses.service;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.zrp.service.houses.dao.RoomInfoDao;
import com.ziroom.zrp.service.houses.dto.WaterWattPagingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>智能水电接口实现</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2018年02月04日 19:29
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
@Service("houses.waterWattSmartServiceImpl")
public class WaterWattSmartServiceImpl {

    @Resource(name = "houses.roomInfoDao")
    private RoomInfoDao roomInfoDao;

    /**
     * 智能电表分页接口
     * @param waterWattPagingDto
     * @return
     */
    public PagingResult pagingWatt(WaterWattPagingDto waterWattPagingDto) {
        return roomInfoDao.findRoomWattPaging(waterWattPagingDto);
    }

    /**
     * 智能水表分页接口
     * @param waterWattPagingDto
     * @return
     */
    public PagingResult pagingWater(WaterWattPagingDto waterWattPagingDto) {
        return roomInfoDao.findRoomWaterPaging(waterWattPagingDto);
    }

}
