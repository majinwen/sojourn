package com.ziroom.minsu.services.customer.test.api.inner;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.customer.TelExtensionEntity;
import com.ziroom.minsu.services.customer.api.inner.TelExtensionService;
import com.ziroom.minsu.services.customer.dto.TelExtensionDto;
import com.ziroom.minsu.valenum.customer.ExtStatusEnum;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/6.
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:META-INF/spring/applicationContext-customer.xml"}
)
public class TelExtensionServiceTest {

    @Resource(name="customer.telExtensionServiceProxy")
    private TelExtensionService telExtensionService;




    @Test
    public void TestsaveExtension(){
        TelExtensionEntity entity  = new TelExtensionEntity();
        entity.setUid("ccc");
        entity.setExtStatus(ExtStatusEnum.NO.getCode());
        entity.setCreateUid("iii");
        String json = telExtensionService.saveExtensionIdempotent(JsonEntityTransform.Object2Json(entity));
        System.out.println(json);
    }


    @Test
    public void TestgetExtensionVOByPage(){

        TelExtensionDto telExtensionDto = new TelExtensionDto();


        String json = telExtensionService.getExtensionVOByPage(JsonEntityTransform.Object2Json(telExtensionDto));
        System.out.println(json);
    }


    @Test
    public void TestbindZiroomPhone(){
        String json = telExtensionService.bindZiroomPhone("03a9ec5a-d921-432b-b7ad-d9c429d0925d","afi");
        System.out.println(json);
    }


    @Test
    public void TestbreakBind(){
        String json = telExtensionService.breakBind("8a9e9a9f544b35ff01544b35ff950000", "afi");
        System.out.println(json);
    }

    @Test
    public void TestgetZiroomPhone(){
        String json = telExtensionService.getZiroomPhone("937d573a-4f25-638b-db9b-f97339e3e5ming-2");
        System.out.println(json);
    }



}
