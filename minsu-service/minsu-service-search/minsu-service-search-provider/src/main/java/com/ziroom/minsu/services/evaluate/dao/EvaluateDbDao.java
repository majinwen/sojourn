package com.ziroom.minsu.services.evaluate.dao;

import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.search.vo.EvaluateDbInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>评价相关的</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/11.
 * @version 1.0
 * @since 1.0
 */
@Repository("search.evaluateDbDao")
public class EvaluateDbDao {

    private String SQLID="search.evaluateDbDao.";

    @Autowired
    @Qualifier("searchEvaluate.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取房源的配置属性
     * @param houseFid
     * @return
     */
    public EvaluateDbInfoVo getEvaluateByHouse(String houseFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getEvaluateByHouse", EvaluateDbInfoVo.class, houseFid);
    }


    /**
     * 获取房源的配置属性
     * @param roomFid
     * @return
     */
    public EvaluateDbInfoVo getEvaluateByRoom(String roomFid){
        return mybatisDaoContext.findOneSlave(SQLID + "getEvaluateByRoom", EvaluateDbInfoVo.class, roomFid);
    }
}
