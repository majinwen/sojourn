package com.ziroom.minsu.services.message.dao;

import com.asura.framework.base.util.Check;
import com.asura.framework.dao.mybatis.base.MybatisDaoContext;
import com.ziroom.minsu.services.search.vo.WeightMessageVo;
import com.ziroom.minsu.services.search.vo.WeightOrderNumVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * 
 * <p>房源物理信息dao</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi
 * @since 1.0
 * @version 1.0
 */
@Repository("search.messageDbInfoDao")
public class MessageDbInfoDao {


    private String SQLID="search.messageDbInfoDao.";

    @Autowired
    @Qualifier("searchMessage.MybatisDaoContext")
    private MybatisDaoContext mybatisDaoContext;


    /**
     * 获取当前房源的消息情况
     * @author afi
     * @param houseFId
     * @return
     */
    public WeightMessageVo getHouseMessageRate(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseMessageRate",WeightMessageVo.class, houseFId);
    }



    /**
     * 获取当前房源的消息情况
     * @author afi
     * @param houseFId
     * @return
     */
    public WeightMessageVo getRoomMessageRate(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomMessageRate",WeightMessageVo.class, houseFId);
    }



    /**
     * 获取当前房源的消息情况
     * @author afi
     * @param houseFId
     * @return
     */
    public Double getHouseMessageTime(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getHouseMessageTime",Double.class, houseFId);
    }


    /**
     * 获取当前房源的消息情况
     * @author afi
     * @param houseFId
     * @return
     */
    public Double getRoomMessageTime(String houseFId){
        if (Check.NuNStr(houseFId)){
            return null;
        }
        return mybatisDaoContext.findOneSlave(SQLID + "getRoomMessageTime",Double.class, houseFId);
    }

}