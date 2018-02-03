package com.ziroom.minsu.services.basedata.service;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.entity.conf.CityRegionEntity;
import com.ziroom.minsu.entity.conf.CityRegionRelEntity;
import com.ziroom.minsu.services.basedata.dao.CityRegionDao;
import com.ziroom.minsu.services.basedata.dao.CityRegionRelDao;
import com.ziroom.minsu.valenum.msg.IsDelEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 大区关联信息
 * </p>
 * <p/>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd on 2017/1/9.
 * @version 1.0
 * @since 1.0
 */
@Service("basedata.cityRegionServiceImpl")
public class CityRegionServiceImpl {

    @Resource(name = "basedata.cityRegionDao")
    private CityRegionDao cityRegionDao;

    @Resource(name = "basedata.cityRegionRelDao")
    private CityRegionRelDao cityRegionRelDao;

    /**
     * 保存城市大区
     * @author jixd
     * @created 2017年01月09日 10:41:27
     * @param
     * @return
     */
    public int insertCityRegion(CityRegionEntity cityRegionEntity){
        return cityRegionDao.insertCityRegion(cityRegionEntity);
    }

    /**
     * 保存大区关系
     * @author jixd
     * @created 2017年01月09日 10:41:42
     * @param
     * @return
     */
    public int insertCityRegionRel(CityRegionRelEntity cityRegionRelEntity){
        cityRegionRelEntity.setIsDel(IsDelEnum.NOT_DEL.getCode());
        List<CityRegionRelEntity> cityRegionRelList = cityRegionRelDao.findCityRegionRelList(cityRegionRelEntity);
        if (!Check.NuNCollection(cityRegionRelList)){
            return 0;
        }
        return cityRegionRelDao.insertCityRegionRel(cityRegionRelEntity);
    }

    /**
     * 更新城市大区
     * @author jixd
     * @created 2017年01月09日 10:42:47
     * @param
     * @return
     */
    public int updateCityRegion(CityRegionEntity cityRegionEntity){
        return cityRegionDao.updateByFid(cityRegionEntity);
    }

    /**
     * 更新城市大区关联
     * @author jixd
     * @created 2017年01月09日 10:44:16
     * @param
     * @return
     */
    public int updateCityRegionRel(CityRegionRelEntity cityRegionRelEntity){
        return cityRegionRelDao.updateByFid(cityRegionRelEntity);
    }

    /**
     * 删除大区
     * @author jixd
     * @created 2017年01月10日 11:53:09
     * @param
     * @return
     */
    public int delCityRegion(String fid){
        CityRegionEntity cityRegionEntity = new CityRegionEntity();
        cityRegionEntity.setFid(fid);
        cityRegionEntity.setIsDel(IsDelEnum.DEL.getCode());
        int i = cityRegionDao.updateByFid(cityRegionEntity);
        if (i > 0){
            CityRegionRelEntity cityRegionRelEntity = new CityRegionRelEntity();
            cityRegionRelEntity.setRegionFid(fid);
            cityRegionRelEntity.setIsDel(IsDelEnum.DEL.getCode());
            i += cityRegionRelDao.updateByFid(cityRegionRelEntity);
        }

        return i;
    }

    /**
     * 根据大区名字查询记录
     * @author jixd
     * @created 2017年01月10日 14:23:59
     * @param
     * @return
     */
    public CityRegionEntity findCityRegionByName(String name){
        return cityRegionDao.findByName(name);
    }

    /**
     * 查找所有的大区列表
     * @author jixd
     * @created 2017年01月10日 14:33:26
     * @param
     * @return
     */
    public List<CityRegionEntity> findAllRegion(){
        return cityRegionDao.findAllRegion();
    }

}
