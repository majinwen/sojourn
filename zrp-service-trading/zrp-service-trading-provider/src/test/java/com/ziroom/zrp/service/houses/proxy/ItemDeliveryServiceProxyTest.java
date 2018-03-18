package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.houses.dto.smartplatform.waterwatt.WaterElectricityInfoDto;
import com.ziroom.zrp.service.trading.dto.MemterRechargeDto;
import com.ziroom.zrp.service.trading.proxy.ItemDeliveryServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年12月04日 14:44
 * @since 1.0
 */
public class ItemDeliveryServiceProxyTest extends BaseTest{

    @Resource(name="trading.itemDeliveryServiceProxy")
    private ItemDeliveryServiceProxy itemDeliveryServiceProxy;

    @Test
    public void testSyncReceiptBillToFinByContractId() {
        String str = "{\"contractId\":\"8a908e10602f9a1f01602f9a387f0024\",\"roomId\":\"1001274\",\"createId\":\"9000085743620233512\",\"cityId\":\"110000\",\"from\":1,\"meterDetail\":{\"fid\":\"8a908e106033ffe90160341a7b3b0156\",\"fcontractid\":null,\"freturnwaterprice\":10.0,\"freturnelectricprice\":20.0,\"fwatermetertype\":\"1\",\"felectricmetertype\":\"1\",\"fwatermeternumber\":20.0,\"felectricmeternumber\":10.0,\"fpaywaterprice\":10.0,\"fpayelectricprice\":20.0,\"fcomment\":\"\",\"fwatermeterpic\":\"/apartment/upload/20171208/1512701035206-788999.jpg\",\"felectricmeterpic\":\"/apartment/upload/20171208/1512701037902-44.jpg\",\"fisdel\":null,\"fvalid\":null,\"fcityid\":null,\"fupdatetime\":null,\"fupdaterid\":null,\"fcreatetime\":null,\"fcreaterid\":null,\"cityid\":null,\"roomId\":null},\"rentItems\":[{\"fid\":\"8a908e106034caf6016034ee2fc50088\",\"contractid\":\"8a908e10602f9a1f01602f9a387f0024\",\"itemid\":\"ff80808148f280400148f2dce002001c\",\"foriginalnum\":1,\"factualnum\":1,\"ftype\":null,\"fneworold\":\"家居\",\"funitmeter\":0.0,\"fstate\":\"0\",\"fpayfee\":null,\"createtime\":1512702638000,\"updatetime\":1512702638000,\"createrid\":\"9000086138220237091\",\"updaterid\":\"9000086138220237091\",\"city\":null,\"fisdel\":0,\"fvalid\":1,\"surrenderid\":null,\"itemname\":\"晾衣架\",\"price\":130.0,\"cityid\":\"110000\",\"roomId\":\"1001274\",\"isDefined\":0,\"isbeditem\":0,\"itemType\":\"1\",\"isNew\":null},{\"fid\":\"8a908e106034caf6016034ee2fc70089\",\"contractid\":\"8a908e10602f9a1f01602f9a387f0024\",\"itemid\":\"ff80808148f350a70148f3727cd40008\",\"foriginalnum\":1,\"factualnum\":1,\"ftype\":null,\"fneworold\":\"家居\",\"funitmeter\":0.0,\"fstate\":\"0\",\"fpayfee\":null,\"createtime\":1512702638000,\"updatetime\":1512702638000,\"createrid\":\"9000086138220237091\",\"updaterid\":\"9000086138220237091\",\"city\":null,\"fisdel\":0,\"fvalid\":1,\"surrenderid\":null,\"itemname\":\"卫生间镜子\",\"price\":80.0,\"cityid\":\"110000\",\"roomId\":\"1001274\",\"isDefined\":0,\"isbeditem\":0,\"itemType\":\"1\",\"isNew\":null},{\"fid\":\"8a908e106034caf6016034ee2fc8008a\",\"contractid\":\"8a908e10602f9a1f01602f9a387f0024\",\"itemid\":\"ff80808148f350a70148f375c603000a\",\"foriginalnum\":1,\"factualnum\":1,\"ftype\":null,\"fneworold\":\"家居\",\"funitmeter\":0.0,\"fstate\":\"0\",\"fpayfee\":null,\"createtime\":1512702638000,\"updatetime\":1512702638000,\"createrid\":\"9000086138220237091\",\"updaterid\":\"9000086138220237091\",\"city\":null,\"fisdel\":0,\"fvalid\":1,\"surrenderid\":null,\"itemname\":\"花洒\",\"price\":300.0,\"cityid\":\"110000\",\"roomId\":\"1001274\",\"isDefined\":0,\"isbeditem\":0,\"itemType\":\"1\",\"isNew\":null},{\"fid\":\"8a908e106034caf6016034ee2fca008b\",\"contractid\":\"8a908e10602f9a1f01602f9a387f0024\",\"itemid\":\"ff80808148f280400148f28cf9000005\",\"foriginalnum\":1,\"factualnum\":1,\"ftype\":null,\"fneworold\":\"家电\",\"funitmeter\":0.0,\"fstate\":\"0\",\"fpayfee\":null,\"createtime\":1512702638000,\"updatetime\":1512702638000,\"createrid\":\"9000086138220237091\",\"updaterid\":\"9000086138220237091\",\"city\":null,\"fisdel\":0,\"fvalid\":1,\"surrenderid\":null,\"itemname\":\"滚筒洗衣机\",\"price\":1650.0,\"cityid\":\"110000\",\"roomId\":\"1001274\",\"isDefined\":0,\"isbeditem\":0,\"itemType\":\"2\",\"isNew\":null},{\"fid\":\"8a908e106034caf6016034ee2fcb008c\",\"contractid\":\"8a908e10602f9a1f01602f9a387f0024\",\"itemid\":\"ff80808148f350a70148f369408c0002\",\"foriginalnum\":1,\"factualnum\":1,\"ftype\":null,\"fneworold\":\"家具\",\"funitmeter\":0.0,\"fstate\":\"0\",\"fpayfee\":null,\"createtime\":1512702638000,\"updatetime\":1512702638000,\"createrid\":\"9000086138220237091\",\"updaterid\":\"9000086138220237091\",\"city\":null,\"fisdel\":0,\"fvalid\":1,\"surrenderid\":null,\"itemname\":\"橱柜\",\"price\":1500.0,\"cityid\":\"110000\",\"roomId\":\"1001274\",\"isDefined\":0,\"isbeditem\":0,\"itemType\":\"0\",\"isNew\":null}],\"lifeFeeItems\":[{\"id\":null,\"lifeitemBid\":null,\"contractId\":null,\"expenseitemId\":\"12\",\"roomId\":null,\"paymentAmount\":70.0,\"isDeleted\":null,\"deleterId\":null,\"deletedTime\":null,\"createrId\":null,\"createdTime\":null,\"updaterId\":null,\"fvalid\":null,\"updatedTime\":null}]}";
        itemDeliveryServiceProxy.saveDeliveryInfo(str);

    }


    @Test
    public  void generatingElectricityBillTest(){

        MemterRechargeDto memterRechargeDto = new MemterRechargeDto();
        memterRechargeDto.setContractId("8a90852953e73f9e015441c4a8240a25");
        memterRechargeDto.setRechargeMoney(10000);
        DataTransferObject dto = JsonEntityTransform.json2DataTransferObject(this.itemDeliveryServiceProxy.generatingElectricityBill(JsonEntityTransform.Object2Json(memterRechargeDto))) ;
        System.out.println(dto.toJsonString());
    }

    @Test
    public void readSmartDeviceByRoomInfoTest(){
        WaterElectricityInfoDto p = new WaterElectricityInfoDto();
        p.setContractId("100001");
        p.setProjectId("8a9099cb576ba5c101576ea29c8a0027");
        p.setRoomId("8a90a3ab57e7054a0157e7f830cd0ec0");
        p.setWaterwattReadType(1);
        String resultJson = itemDeliveryServiceProxy.readSmartDeviceByRoomInfo(p.toJsonStr());
        System.out.println(resultJson);
    }

}
