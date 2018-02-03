package com.ziroom.minsu.services.message.test.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.SOAResParseUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.message.MsgAdvisoryFollowupEntity;
import com.ziroom.minsu.services.message.proxy.MsgAdvisoryFollowupServiceProxy;
import com.ziroom.minsu.services.message.test.base.BaseTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wangwentao 2017/5/27
 * @version 1.0
 * @since 1.0
 */
public class MsgAdvisoryFollowupServiceProxyTest extends BaseTest{

    @Resource(name = "message.msgAdvisoryFollowupServiceProxy")
    private MsgAdvisoryFollowupServiceProxy msgAdvisoryFollowupServiceProxy;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void save() throws Exception {
        MsgAdvisoryFollowupEntity msgAdvisoryFollowupEntity = new MsgAdvisoryFollowupEntity();
        msgAdvisoryFollowupEntity.setFid(UUIDGenerator.hexUUID());
//        msgAdvisoryFollowupEntity.setMsgFirstAdvisoryFid("8a9e988e5b4c1dc6015b4c1dc6370000");
        msgAdvisoryFollowupEntity.setBeforeStatus(10);
        msgAdvisoryFollowupEntity.setAfterStatus(20);
        msgAdvisoryFollowupEntity.setEmpFid("101000");
        String resultJson = msgAdvisoryFollowupServiceProxy.save(JsonEntityTransform.Object2Json(msgAdvisoryFollowupEntity));
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        Assert.assertTrue(dto.getCode() == DataTransferObject.SUCCESS);
        Assert.assertEquals(1, dto.getData().get("result"));

    }

    @Test
    public void getByMsgAdvisoryfid() throws Exception {
        String fid = "8a9e98b45c47c681015c47c681f70000";
        String resultJson = msgAdvisoryFollowupServiceProxy.getByFid(fid);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        Assert.assertTrue(dto.getCode() == DataTransferObject.SUCCESS);
        Assert.assertNotNull(dto.getData().get("result"));
    }

    @Test
    public void getAllByFisrtAdvisoryFid() throws Exception {
        String msgFirstAdFid = "8a9e988a5c3d4feb015c3d4febc80000";
        String resultJson = msgAdvisoryFollowupServiceProxy.getAllByFisrtAdvisoryFid(msgFirstAdFid);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(resultJson);
        Assert.assertTrue(dto.getCode() == DataTransferObject.SUCCESS);
        Assert.assertNotNull(dto.getData().get("list"));
        List<MsgAdvisoryFollowupEntity> list = SOAResParseUtil.getListValueFromDataByKey(resultJson, "list", MsgAdvisoryFollowupEntity.class);
        Assert.assertEquals(2, list.size());
    }

}