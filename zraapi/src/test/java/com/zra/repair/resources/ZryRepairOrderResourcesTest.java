package com.zra.repair.resources;

import com.alibaba.fastjson.JSON;
import com.zra.repair.entity.dto.ZryRepairOrderDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @version 1.0
 * @Date Created in 2017年09月29日 21:15
 * @since 1.0
 */
// TODO: 2017/10/9 单元测试启动还是有问题 
//@RunWith(SpringRunner.class)
//@ActiveProfiles("dev")
public class ZryRepairOrderResourcesTest {

    private MockMvc mockMvc;

    @Resource
    private ZryRepairOrderResources zryRepairOrderResources;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        assertNotNull("autowired ZryRepairOrderResources fail !!", zryRepairOrderResources);
    }

//    @Test
    public void addAndLog() throws Exception {
        ZryRepairOrderDto orderVo = new ZryRepairOrderDto();

        orderVo.setOrderSn("ordersn0000");
        orderVo.setAreaCode("123");
        orderVo.setAreaName("望京");
        orderVo.setCategoryCode("0001");
        orderVo.setBusinessType(1);
        orderVo.setContractCode("BJ007990");
        orderVo.setCreateDate(Date.valueOf(LocalDate.now()));
        orderVo.setGoodsCode("213");
        orderVo.setCreateFid("517");
        orderVo.setRoomNum("A001");

        orderVo.setCreateFid("517");
        orderVo.setRepairOrder("ZR000000001");
        orderVo.setFromStatus(1);

        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post("/zra/repair");
        req.content(JSON.toJSONString(orderVo));
        req.contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(req);

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
    public void cancel() throws Exception {
        // // TODO: 2017/10/9
    }
}