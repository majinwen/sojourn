package com.ziroom.minsu.report.order.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.asura.framework.dao.mybatis.paginator.domain.PageBounds;
import com.ziroom.minsu.report.order.dto.OrderEvaluate2Request;
import com.ziroom.minsu.report.order.dto.OrderEvaluateDetailRequest;
import com.ziroom.minsu.report.order.dto.OrderEvaluateRequest;
import com.ziroom.minsu.report.order.entity.OrderEvaluateDetailEntity;
import com.ziroom.minsu.report.order.entity.OrderEvaluateEntity;
import com.ziroom.minsu.report.order.vo.OrderEvaluateVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Repository("report.orderEvaluateDao")
public class OrderEvaluateDao {

    private String SQLID="report.orderEvaluateDao.";

    @Autowired
    @Qualifier("minsuReport.all.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;

    /**
     * 通过城市查询评价
     * @author afi
     * @param orderEvaluateRequest
     * @return
     */
    public List<OrderEvaluateEntity> getOrderEvaluateListByCityCode(OrderEvaluateRequest orderEvaluateRequest){
        return mybatisDaoContext.findAll(SQLID + "getOrderEvaluateListByCityCode", OrderEvaluateEntity.class,orderEvaluateRequest);
    }


    /**
     * 通过城市查询评价
     * @author afi
     * @param cityCode
     * @param starTime
     * @param endTime
     * @return
     */
    public OrderEvaluateEntity getOrderEvaluateByCityCode(String cityCode,String starTime,String endTime){
        Map<String,Object> par = new HashedMap();
        par.put("cityCode",cityCode);
        par.put("starTime",starTime);
        par.put("endTime",endTime);
        return mybatisDaoContext.findOne(SQLID + "getOrderEvaluateByCityCode", OrderEvaluateEntity.class,par);
    }



    /**
     * #房东总评分
     * @author afi
     * @param orderRequest
     * @return
     */
    public OrderEvaluateDetailEntity avgLanScoreInfoByCityCode(OrderEvaluateDetailRequest orderRequest){

    	if(Check.NuNObj(orderRequest)){
    		return null;
    	}
    	Map<String, Object> paramMap = new HashMap<String, Object>(3);
    	paramMap.put("cityCode", orderRequest.getCityCode());
    	paramMap.put("beginTime", orderRequest.getStarTime());
    	paramMap.put("endTime", orderRequest.getEndTime());

    	return mybatisDaoContext.findOne(SQLID + "avgLanScoreInfoByCityCode",OrderEvaluateDetailEntity.class ,paramMap);
    }


    /**
     * #房客平均评分
     * @author afi
     * @param orderRequest
     * @return
     */
    public Double avgUserScoreInfo(OrderEvaluateDetailRequest orderRequest){
        Double result = 0.0;
        if(Check.NuNObj(orderRequest)){
            return result;
        }
        Map<String, Object> paramMap = new HashMap<String, Object>(3);
        paramMap.put("cityCode", orderRequest.getCityCode());
        paramMap.put("beginTime", orderRequest.getStarTime());
        paramMap.put("endTime", orderRequest.getEndTime());
        result = mybatisDaoContext.findOne(SQLID + "avgUserScoreInfo",Double.class, paramMap);
        return result;
    }


    /**
     * 分页查询订单评价报表
     *
     * @author lishaochuan
     * @create 2017/3/10 18:32
     * @param 
     * @return 
     */
    public PagingResult<OrderEvaluateVo> getOrderEvaluate(OrderEvaluate2Request request){
        PageBounds pageBounds = new PageBounds();
        pageBounds.setLimit(request.getLimit());
        pageBounds.setPage(request.getPage());
        return mybatisDaoContext.findForPage(SQLID + "getOrderEvaluate", OrderEvaluateVo.class, request.toMap(), pageBounds);
    }


}
