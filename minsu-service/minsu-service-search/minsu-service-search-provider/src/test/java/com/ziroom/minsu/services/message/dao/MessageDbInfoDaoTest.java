package com.ziroom.minsu.services.message.dao;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.vo.WeightMessageVo;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>消息的数据</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/6.
 * @version 1.0
 * @since 1.0
 */
public class MessageDbInfoDaoTest extends BaseTest {


    @Resource(name="search.messageDbInfoDao")
    private MessageDbInfoDao messageDbInfoDao;




    @Test
    public void getRoomMessageTime(){
        Double time =messageDbInfoDao.getRoomMessageTime("8a9084df54c8752f0154c87d0c5d0011");
        System.out.println(JsonEntityTransform.Object2Json(time));
    }

    @Test
    public void getHouseMessageTime(){
        Double time =messageDbInfoDao.getHouseMessageTime("8a9084df54c8752f0154c87d0c5d0011");
        System.out.println(JsonEntityTransform.Object2Json(time));
    }




    @Test
    public void getRoomAvgAcceptTime(){
        WeightMessageVo weightOrderNumVo =messageDbInfoDao.getHouseMessageRate("8a9084df54c8752f0154c87d0c5d0011");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }

    @Test
    public void getRoomMessageRate(){
        WeightMessageVo weightOrderNumVo =messageDbInfoDao.getRoomMessageRate("8a9084df54c8752f0154c87d0c5d0011");
        System.out.println(JsonEntityTransform.Object2Json(weightOrderNumVo));
    }



}
