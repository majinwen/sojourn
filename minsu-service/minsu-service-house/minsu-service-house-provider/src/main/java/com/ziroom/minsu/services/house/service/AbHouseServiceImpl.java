package com.ziroom.minsu.services.house.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.house.AbHouseRelateEntity;
import com.ziroom.minsu.entity.house.AbHouseStatusEntity;
import com.ziroom.minsu.services.house.airbnb.dto.AbHouseDto;
import com.ziroom.minsu.services.house.airbnb.vo.AbHouseRelateVo;
import com.ziroom.minsu.services.house.dao.AbHouseRelateDao;
import com.ziroom.minsu.services.house.dao.AbHouseStatusDao;
import com.ziroom.minsu.services.house.dao.HouseBaseMsgDao;
import com.ziroom.minsu.services.house.dao.HouseRoomMsgDao;

/**
 * airbnb房源日历处理
 * @author jixd
 * @created 2017年04月15日 15:54:09
 * @param
 * @return
 */
@Service("house.abHouseServiceImpl")
public class AbHouseServiceImpl {

    @Resource(name = "house.abHouseStatusDao")
    private AbHouseStatusDao abHouseStatusDao;

    @Resource(name = "house.abHouseRelateDao")
    private AbHouseRelateDao abHouseRelateDao;

    @Resource(name="house.houseBaseMsgDao")
    private HouseBaseMsgDao houseBaseMsgDao;

    @Resource(name = "house.houseRoomMsgDao")
    private HouseRoomMsgDao houseRoomMsgDao;

    /**
     * 保存民宿房源与airbnbk房源日历关系
     * 1.保存之前先删除今天以后的数据
     * 2.插入今天以后的日历数据
     * @author jixd
     * @created 2017年04月15日 15:55:30
     * @param
     * @return
     */
    public int saveHouseCalendar(AbHouseDto abHouseDto){
        int delCount =abHouseStatusDao.deleteByLockTime(abHouseDto);
        int count = 0;
        List<AbHouseStatusEntity> abStatusList = abHouseDto.getAbStatusList();
        if (!Check.NuNCollection(abStatusList)){
            for (AbHouseStatusEntity abHouseStatusEntity : abStatusList){
                count += abHouseStatusDao.save(abHouseStatusEntity);
            }
        }
        return count;
    }

    /**
     * 插入airbnb与民宿房源对应关系
     * @author jixd
     * @created 2017年04月15日 15:56:44
     * @param
     * @return
     */
    public int saveHouseRelate(AbHouseRelateEntity abHouseRelateEntity){
        return abHouseRelateDao.save(abHouseRelateEntity);
    }

    /**
     * 分页查询房源关联信息
     * @author jixd
     * @created 2017年04月17日 10:03:47
     * @param
     * @return
     */
    public PagingResult<AbHouseRelateVo> listRelateVoByPage(AbHouseDto abHouseDto){
        return abHouseRelateDao.listRelateByPage(abHouseDto);
    }

    /**
     * 查询房源根据房源fid
     * @author jixd
     * @created 2017年04月17日 10:04:47
     * @param
     * @return
     */
    public AbHouseRelateVo findRelateByHouseFid(AbHouseDto abHouseDto){
        return abHouseRelateDao.findRelateByHouseFid(abHouseDto);
    }
    /**
     *
     * @author jixd
     * @created 2017年05月18日 17:52:50
     * @param
     * @return
     */
    public AbHouseRelateEntity findAbHouseByHouse(AbHouseRelateEntity abHouseRelateEntity){
        return abHouseRelateDao.findAbHouseByHouseInfo(abHouseRelateEntity);
    }

    /**
     * 
     * 根据fid修改关系记录
     *
     * @author zyl
     * @created 2017年6月29日 上午10:10:35
     *
     * @param abHouseRelateVo
     * @return
     */
    public int updateHouseRelateByFid(AbHouseRelateVo abHouseRelateVo){
        return abHouseRelateDao.updateHouseRelateByFid(abHouseRelateVo);
    }
}
