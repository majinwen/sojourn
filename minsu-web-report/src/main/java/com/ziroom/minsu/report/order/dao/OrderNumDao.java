package com.ziroom.minsu.report.order.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.report.basedata.entity.CityNumEntity;
import com.ziroom.minsu.report.order.dto.OrderNumRequest;
import com.ziroom.minsu.report.order.entity.OrderNumEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/5.
 * @version 1.0
 * @since 1.0
 */
@Repository("report.orderNumDao")
public class OrderNumDao {

    private String SQLID="report.orderNumDao.";

    @Autowired
	@Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;





	/**
	 * 获取当前的订单的统计信息
	 * @author afi
	 * @param orderNumRequest
	 * @return
	 */
	public List<OrderNumEntity> getOrderNumByCity(OrderNumRequest orderNumRequest){

		return mybatisDaoContext.findAll(SQLID + "getOrderNumByCity", OrderNumEntity.class,orderNumRequest);
	}

	/**
	 * 获取当前的订单的统计信息
	 * @author afi
	 * @param orderNumRequest
	 * @return
	 */
	public List<CityNumEntity> getUserNumByCity(OrderNumRequest orderNumRequest){

		return mybatisDaoContext.findAll(SQLID + "getUserNumByCity", CityNumEntity.class,orderNumRequest);
	}



	/**
	 * 获取当前的订单的统计信息
	 * @author afi
	 * @param orderNumRequest
	 * @return
	 */
	public List<CityNumEntity> getUserSuccessNumByCity(OrderNumRequest orderNumRequest){

		return mybatisDaoContext.findAll(SQLID + "getUserSuccessNumByCity", CityNumEntity.class,orderNumRequest);
	}



	/**
	 * 获取当前城市的累计咨询信息
	 * @author afi
	 * @param orderNumRequest
	 * @return
	 */
	public List<CityNumEntity> getConsultNumByCity(OrderNumRequest orderNumRequest){
		return mybatisDaoContext.findAll(SQLID + "getConsultNumByCity", CityNumEntity.class,orderNumRequest);
	}

}
