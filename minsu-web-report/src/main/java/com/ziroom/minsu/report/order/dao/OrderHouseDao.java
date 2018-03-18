package com.ziroom.minsu.report.order.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.house.entity.HouseNumEntity;
import com.ziroom.minsu.report.order.dto.OrderHouseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>订单评价</p>
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
@Repository("report.orderHouseDao")
public class OrderHouseDao {

    private String SQLID="report.orderHouseDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 通过城市查询数量
     * @author afi
     * @param orderHouseRequest
     * @return
     */
    public List<HouseNumEntity> getHouseNumByCityCode(OrderHouseRequest orderHouseRequest){
        return mybatisDaoContext.findAll(SQLID + "getHouseNumByCityCode", HouseNumEntity.class,orderHouseRequest);
    }




    /**
     * 通过城市查询数量
     * @author afi
     * @param orderHouseRequest
     * @return
     */
    public List<CityNumEntity> getOrderNumListByCityCode(OrderHouseRequest orderHouseRequest){
        return mybatisDaoContext.findAll(SQLID + "getOrderNumListByCityCode", CityNumEntity.class,orderHouseRequest);
    }


    /**
     * 通过城市查询数量
     * @author afi
     * @param orderHouseRequest
     * @return
     */
    public List<CityNumEntity> getDayNumListByCityCode(OrderHouseRequest orderHouseRequest){
        return mybatisDaoContext.findAll(SQLID + "getDayNumListByCityCode", CityNumEntity.class,orderHouseRequest);
    }



}
