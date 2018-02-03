package com.ziroom.minsu.services.order.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.order.dto.EvalSynRequest;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/12.
 * @version 1.0
 * @since 1.0
 */
@Repository("order.orderEvalDao")
public class OrderEvalDao {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(OrderDao.class);
    private String SQLID = "order.orderEvalDao.";

    @Autowired
    @Qualifier("order.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;



    /**
     * 同步当前的初评状态
     * @author afi
     * @param evalSynRequest
     * @return
     */
    public int updatePjStatuByOrderSn(EvalSynRequest evalSynRequest){
        if(Check.NuNObj(evalSynRequest)||Check.NuNCollection(evalSynRequest.getListOrderSn())||Check.NuNObj(evalSynRequest.getEvlStatus())) return -1;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("evlPjStatus", evalSynRequest.getEvlStatus());
        map.put("listOrderSn", evalSynRequest.getListOrderSn());
        return this.mybatisDaoContext.update(SQLID+"updatePjStatuByOrderSn", map);
    }


    /**
     * 获取当前用户待评价的订单数量
     * @author afi
     * @param userUid
     * @return
     */
    public Long countUserWaitEvaNumAll(String userUid,int limitDay){
        if (Check.NuNStr(userUid)){
            return 0L;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("userUid",userUid);
        par.put("limitDay",limitDay);
        return mybatisDaoContext.count(SQLID + "countUserWaitEvaNumAll", par);
    }


    /**
     * 获取当前房东待评价的订单数量
     * @author afi
     * @param landUid
     * @return
     */
    public Long countLandWaitEvaNumAll(String landUid,int limitDay){
        if (Check.NuNStr(landUid)){
            return 0L;
        }
        Map<String,Object> par = new HashMap<>();
        par.put("landUid",landUid);
        par.put("limitDay",limitDay);
        return mybatisDaoContext.count(SQLID + "countLandWaitEvaNumAll", par);
    }

}

