package com.ziroom.zrp.service.houses.proxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.PersonAndSharerDto;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonDto;
import com.ziroom.zrp.service.trading.dto.checkin.RentCheckinPersonSearchDto;
import com.ziroom.zrp.service.trading.proxy.RentCheckinServiceProxy;
import com.ziroom.zrp.trading.entity.RentCheckinPersonEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>入住人业务实现单元测试</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author phil
 * @Date Created in 2017年12月04日 20:11
 * @Version 1.0
 * @Since 1.0
 */
@Slf4j
public class RentCheckServiceProxyTest extends BaseTest{

    private final static String contractId = "T123456";

    @Resource(name="trading.rentCheckinServiceProxy")
    private RentCheckinServiceProxy rentCheckinServiceProxy;

    /**
     * 添加
     *  - 入住人
     *  - 合住人
     *  param :
     *      @see PersonAndSharerDto
     *  return :
     *      @see DataTransferObject
     *          code=1 ==》 ERROR
     *          code=0 ==》 SUCCESS
     */
    @Test
    public void saveTest(){

        PersonAndSharerDto personAndSharerDto = new PersonAndSharerDto();

        // 入住人信息
        personAndSharerDto.setRentCheckinPersonEntity(
                RentCheckinPersonEntity.builder()
                        .name("入住人")
                        .emcyCntPhone("1232131231231")
                        .sex(1)
                        .age(28)
                        .certType(1) // todo 不知道是什么
                        .certNum("98765432456") // 证件号
                        .birthdayMonday("0808")
                        .contractId(contractId) // 合同号
                        .birthdayYear(1991)
                        .customerFrom(1)
                        .workAddress("朝阳区望京")
                        .phoneNum("112321312321")
                        .build()
        );
        SharerEntity entity = new SharerEntity();
        entity.setFname("合住人1");
        entity.setFmobile("1355515555");
        entity.setFcontractid(contractId);

        personAndSharerDto.setSharerEntities(
                Arrays.asList(entity)
        );

        final String result = rentCheckinServiceProxy.saveCheckinAndSharer(
                JSONObject.toJSONString(
                        personAndSharerDto
                )
        );

        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(result);

        Assert.notNull(JSONObject.parseObject(result), "save fail");
        Assert.isTrue(dto.getCode() == dto.SUCCESS, "save back error, " + dto.getMsg());

    }

    @Test
    public void testSelectHistoryPersonAndSharer(){
        RentCheckinPersonSearchDto param = new RentCheckinPersonSearchDto();
        param.setPage(1);
        param.setRows(10);
        param.setContractId("8a9099cb5a183e26015a1bc7b2f10095");
        System.out.println(rentCheckinServiceProxy.selectHistoryPersonAndSharer(JsonEntityTransform.Object2Json(param)));
    }

    /**
     * 查询入住人信息根据合同Id
     * @throws Exception
     */
    @Test
    public void findByContractIdTest() throws Exception {

        RentCheckinPersonDto personDto = SOAResParseUtil.getValueFromDataByKey(
                rentCheckinServiceProxy.findByContractId(contractId),
                "personEntity",
                RentCheckinPersonDto.class);

        Assert.notNull(personDto, "findByContractId back null by contractId :" + contractId);

        log.info(JSON.toJSONString(personDto));
    }

    @After
    public void deleteCheckinPersonTest() {
        // todo 删除测试数据 ==》 合同Id ：contractId 入住人 和 合住人
    }
    
    @Test
    public void getValidContractListTest(){
    	String resultJson=rentCheckinServiceProxy.getValidContractList("2ffbae0f-661e-46bd-c305-5ac995efd609");
    	System.err.println(resultJson);
    }
}
