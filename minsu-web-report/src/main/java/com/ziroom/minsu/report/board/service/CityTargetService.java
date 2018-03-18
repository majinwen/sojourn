package com.ziroom.minsu.report.board.service;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.report.board.dao.CityTargetDao;
import com.ziroom.minsu.report.board.dao.CityTargetLogDao;
import com.ziroom.minsu.report.board.dto.CityTargetRequest;
import com.ziroom.minsu.report.board.entity.CityTargetEntity;
import com.ziroom.minsu.report.board.entity.CityTargetLogEntity;
import com.ziroom.minsu.report.board.vo.CityTargetItem;
import com.ziroom.minsu.report.board.vo.RegionItem;
import com.ziroom.minsu.report.common.util.UserUtil;
import com.ziroom.minsu.services.basedata.entity.CurrentuserVo;
import com.ziroom.minsu.services.basedata.entity.UpsUserVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>城市目标</P>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/11.
 * @version 1.0
 * @since 1.0
 */
@Service("report.cityTargetService")
public class CityTargetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityTargetService.class);

    @Resource(name="report.cityTargetDao")
    private CityTargetDao cityTargetDao;

    @Resource(name="report.cityTargetLogDao")
    private CityTargetLogDao cityTargetLogDao;

    /**
     * 城市目标列表
     * @author jixd
     * @created 2017年01月11日 14:42:15
     * @param
     * @return
     */
    public List<CityTargetItem> findTargetCityList(CityTargetRequest cityTargetRequest){
        return cityTargetDao.findTargetCityList(cityTargetRequest);
    }


    /**
     * 查询实体列表
     * @author jixd
     * @created 2017年01月12日 18:24:11
     * @param
     * @return
     */
    public List<CityTargetEntity> findTargetCityEntityList(CityTargetRequest cityTargetRequest){
        return cityTargetDao.findTargetCityEntityList(cityTargetRequest);
    }

    /**
     * 分页查询大区fid列表
     * @author jixd
     * @created 2017年01月11日 20:27:38
     * @param
     * @return
     */
    public PagingResult<RegionItem> groupByRegionFidList(CityTargetRequest cityTargetRequest){
        return cityTargetDao.groupByRegionFidList(cityTargetRequest);
    }


    /**
     * 保存城市目标
     * @author jixd
     * @created 2017年01月12日 17:24:14
     * @param
     * @return
     */
    public int saveCityTarget(CityTargetEntity cityTargetEntity){
        CityTargetRequest cityTargetRequest = new CityTargetRequest();
        cityTargetRequest.setCityCode(cityTargetEntity.getCityCode());
        cityTargetRequest.setTargetMonth(cityTargetEntity.getTargetMonth());
        List<CityTargetItem> targetCityList = cityTargetDao.findTargetCityList(cityTargetRequest);
        if (!Check.NuNCollection(targetCityList)){
            return 0;
        }
        if (Check.NuNStr(cityTargetEntity.getFid())){
            cityTargetEntity.setFid(UUIDGenerator.hexUUID());
        }
        int i = cityTargetDao.insertCityTarget(cityTargetEntity);
        if (i>0){
            CityTargetLogEntity cityTargetLogEntity = new CityTargetLogEntity();
            BeanUtils.copyProperties(cityTargetEntity,cityTargetLogEntity);
            cityTargetLogEntity.setTargetFid(cityTargetEntity.getFid());
            i += cityTargetLogDao.insertCityTargetLog(cityTargetLogEntity);
        }
        return i;
    }

    /**
     * 更新城市目标
     * @author jixd
     * @created 2017年01月12日 17:45:29
     * @param
     * @return
     */
    public int updateCityTarget(CityTargetEntity cityTargetEntity){
        int i = cityTargetDao.updateByFid(cityTargetEntity);
        if (i>0){
            CityTargetLogEntity cityTargetLogEntity = new CityTargetLogEntity();
            BeanUtils.copyProperties(cityTargetEntity,cityTargetLogEntity);
            cityTargetLogEntity.setTargetFid(cityTargetEntity.getFid());
            UpsUserVo upsUserVo=UserUtil.getUpsUserMsg();
            cityTargetLogEntity.setCreateEmpCode(upsUserVo.getEmployeeEntity().getEmpCode());
            cityTargetLogEntity.setCreateEmpName(upsUserVo.getEmployeeEntity().getEmpName());
            i += cityTargetLogDao.insertCityTargetLog(cityTargetLogEntity);
        }
        return i;
    }

    /**
     * 查询历史记录
     * @author jixd
     * @created 2017年01月12日 18:22:31
     * @param
     * @return
     */
    public List<CityTargetLogEntity> findCityTargetLog(String targetFid){
        return cityTargetLogDao.findCityTargetLog(targetFid);
    }



}
