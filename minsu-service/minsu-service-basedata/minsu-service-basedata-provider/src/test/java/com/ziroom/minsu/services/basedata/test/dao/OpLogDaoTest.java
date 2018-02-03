package com.ziroom.minsu.services.basedata.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.dao.OpLogDao;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
import com.ziroom.minsu.services.basedata.entity.OpLogVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>操作记录测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/12.
 * @version 1.0
 * @since 1.0
 */
public class OpLogDaoTest extends BaseTest {



    @Resource(name="basedata.opLogDao")
    private OpLogDao opLogDao;


    @Test
    public void TestTest(){

        System.out.println("test");
    }



    @Test
    public void TestInsertCurrentuser(){
        OpLogEntity log = new OpLogEntity();
        log.setOpEmployeeId("1231");
        log.setOpUrl("testUrl");
        opLogDao.insertSysOpLogEntity(log);
    }


    @Test
    public void findSysOpLogList(){
        OpLogRequest opLogRequest = new OpLogRequest();
        opLogRequest.setLimit(10);
        opLogRequest.setPage(1);
        //opLogRequest.setOpUrl("testUrl2");
        //opLogRequest.setEmpName("李英杰");
        //opLogRequest.setEmpCode("20152597");
        PagingResult<OpLogVo> list = opLogDao.findSysOpLogList(opLogRequest);
        System.out.println(JsonEntityTransform.Object2Json(list.getRows()));

    }


}
