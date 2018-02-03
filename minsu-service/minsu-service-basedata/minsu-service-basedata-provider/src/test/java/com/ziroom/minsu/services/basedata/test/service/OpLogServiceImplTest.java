package com.ziroom.minsu.services.basedata.test.service;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.sys.OpLogEntity;
import com.ziroom.minsu.services.basedata.api.inner.OpLogService;
import com.ziroom.minsu.services.basedata.dto.OpLogRequest;
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
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class OpLogServiceImplTest extends BaseTest {


    @Resource(name="basedata.opLogProxy")
    private OpLogService opLogService;

    @Test
    public void TestTest(){

        System.out.println("test");
    }


    @Test
    public void TestInsertCurrentuser(){

        OpLogEntity log = new OpLogEntity();
        log.setOpEmployeeId("1231");
        log.setOpUrl("testUrl");
        opLogService.saveOpLogInfo(log);
    }



    @Test
    public void findSysOpLogListByProxy(){
        OpLogRequest opLogRequest = new OpLogRequest();
        opLogRequest.setLimit(10);
        opLogRequest.setPage(1);
        //opLogRequest.setOpUrl("testUrl2");
        //opLogRequest.setEmpName("李英杰");
        //opLogRequest.setEmpCode("20152597");
        String resultJson = opLogService.findOpLogList(JsonEntityTransform.Object2Json(opLogRequest));
        System.out.println(resultJson);

    }



}
