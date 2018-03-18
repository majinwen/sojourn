package com.ziroom.minsu.report.house.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.customer.dto.LandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>房东的房源统计信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2017/1/19.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.landHouseDao")
public class LandHouseDao {

    private String SQLID="report.landHouseDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 通过城市查询房源统计信息
     * @author afi
     * @param landRequest
     * @return
     */
    public List<CityNumEntity> getHouseNumListByCityCode(LandRequest landRequest){
        return mybatisDaoContext.findAll(SQLID + "getHouseNumListByCityCode", CityNumEntity.class,landRequest);
    }


    /**
     * 通过城市查询房东统计信息
     * @author afi
     * @param landRequest
     * @return
     */
    public List<CityNumEntity> getLandNumListByCityCode(LandRequest landRequest){
        return mybatisDaoContext.findAll(SQLID + "getLandNumListByCityCode", CityNumEntity.class,landRequest);
    }

}
