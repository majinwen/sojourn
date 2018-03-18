package com.ziroom.zrp.service.houses.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.zrp.service.houses.base.BaseTest;
import com.ziroom.zrp.service.trading.dto.surrender.CostItemAccountDto;
import com.ziroom.zrp.service.trading.dto.surrender.SurrenderCostNextDto;
import com.ziroom.zrp.service.trading.proxy.SurrenderServiceProxy;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年10月18日 10时15分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderServiceProxyTest extends BaseTest {
    @Resource(name = "trading.surrenderServiceProxy")
    private SurrenderServiceProxy surrenderServiceProxy;

    @Test
    public void testGetCostItemAccount() {
//        {"surParentId":null,"contractId":"8a908e106072f16b016073ea4fe00712","resNo":0,"anew":0}
//        String str = "{\"surParentId\":\"123\",\"contractId\":null,\"resNo\":0,\"anew\":0}";
//        String str = "{\"surParentId\":null,\"contractId\":\"8a9091bd5f9090c1015f9090c1910000\",\"resNo\":0,\"anew\":0}";
        String str = "{\"surParentId\":null,\"contractId\":\"8a90a3ab5cf40ef3015cfd04340b04d5\",\"resNo\":0,\"anew\":0}";
        String costItemAccount = surrenderServiceProxy.getCostItemAccount(str);
        System.out.println(costItemAccount);
    }

    @Test
    public void testsaveSurrenderCost() {
        SurrenderCostNextDto dto = new SurrenderCostNextDto();
//        dto.setContractId("8a9091bd5f9090c1015f9090c1910000");\
        dto.setSurParentId("123");
        dto.setFcostremark("测试");
        dto.setFsurtype("1");
        dto.setFresponsibility("1");
        dto.setFpayer("0");
        dto.setFsettlementamount(100D);
        dto.setFaccountname("王晓明");
        dto.setFbankaccount("622500000000");
        dto.setBankCode("C001");
        dto.setFbankname("中国银行");
        dto.setIsEdit(1);
        dto.setZoId("20236468");
        dto.setZoName("王小明");
        dto.setNeedReCal(1);
        String cost = surrenderServiceProxy.saveSurrenderCost(JsonEntityTransform.Object2Json(dto));
        System.out.println(cost);
    }

    @Test
    public void testcancelSurrender() {
        String paramStr = "{\"surParentId\":\"123\",\"surrenderId\":null,\"zoId\":\"20236468\",\"contractId\":\"8a9091bd60028a800160066190390003\"}";
        String s = surrenderServiceProxy.cancelSurrender(paramStr);
        System.out.println(s);
    }

    @Test
    public void testdoSurrender() {
//        String paramStr = "{\"surParentId\":\"123\",\"surrenderId\":null,\"zoId\":\"20236468\",\"contractId\":\"8a9091bd60028a800160066190390003\"}";
        String paramStr = "{\"surrenderId\":\"2b406606-8a20-4d8a-9987-e8fa41a291b8\",\"contractId\":\"8a9099cb5cc47b76015cc5447e4d012b\",\"surParentId\":null,\"zoId\":\"1\",\"zoName\":\"系统管理员\"}";
        System.out.println(surrenderServiceProxy.doSurrender(paramStr));
    }

    @Test
    public void testgetSurRoomList(){
        String paramStr = "{\"surParentId\":\"123\",\"surrenderId\":null,\"rows\":10,\"pageNum\":0,\"contractId\":\"8a9091bd60028a800160066190390003\"}";
        System.out.println(surrenderServiceProxy.getSurRoomList(paramStr));
    }

    @Test
    public void testeditCommitAudit() {
//        String paramStr = "{\"surParentId\":\"123\",\"surrenderId\":null,\"rows\":10,\"pageNum\":0,\"contractId\":\"8a9091bd60028a800160066190390003\"}";
        String paramStr = "{\"surParentId\":null,\"surrenderId\":null,\"rows\":10,\"pageNum\":0,\"contractId\":\"8a9091bd60028a800160066190390003\"}";
        System.out.println(surrenderServiceProxy.editCommitAudit(paramStr));
    }

    @Test
    public void testqueryConStatus() {
        String s = surrenderServiceProxy.queryConStatus("8a908e10606ea6be01606ead9a520010");
        System.out.println(s);
    }
    @Test
    public void testfindMaxDate(){
    	String param = "BJZYS041748204";
    	System.out.println(surrenderServiceProxy.findMaxDate(param));
    }
    @Test
    public void testconfirmSurrAudit(){
    	String param = "{\"employeeName\":\"项斌\",\"surrenderId\":\"9028076f-690b-4b72-b743-9b2381c1f7be\",\"noAuditReason\":\"测试不通过\",\"employeeId\":\"bbb9bf36d53245dca879ee05a9bd5516\",\"auditType\":\"1\",\"auditResult\":\"no\"}";
    	this.surrenderServiceProxy.confirmSurrAudit(param);
    	System.out.println("1");
    	
    }
    @Test
    public void testsaveSurrenderType(){
    	String param = "{\"surrenderId\":\"dd786e18-3e33-43ce-b0dd-e8894078cc65\",\"contractId\":\"8a908e10606ea6be01606ead9a52000e\",\"surrendercostId\":null,\"freleasedate\":null,\"createrid\":null,\"updaterid\":null,\"fcreatetime\":null,\"fupdatetime\":null,\"fvalid\":null,\"fisdel\":null,\"fsurtype\":\"0\",\"fsurreason\":null,\"fsurreasonother\":null,\"frentalorderspic\":null,\"fapplicationdate\":null,\"factualdate\":null,\"fexpecteddate\":null,\"fsurrendercode\":null,\"surParentCode\":null,\"surParentId\":\"81c852c8-17d4-4679-bb6f-f4777a519564\",\"roomId\":null,\"fnewrentcode\":null,\"fsubletname\":null,\"fsubletpersonid\":null,\"ftenantname\":null,\"fiscancel\":null,\"fsubmitstatus\":null,\"frentauditstatus\":null,\"fhandlezo\":null,\"fauditor\":null,\"frentauditdate\":null,\"fdeliverydate\":null,\"frentenddate\":null,\"fapplystatus\":null,\"fhandlezoname\":null,\"fauditorname\":null,\"cityid\":null,\"fsource\":null,\"rentType\":null,\"zwFirstAuditDate\":null,\"zwApproveDate\":null,\"cwFirstAuditDate\":null,\"cwApproveDate\":null,\"conRentCode\":null,\"conStatuCode\":null}";
    	System.out.println(surrenderServiceProxy.saveSurrenderType(param));
    	
    }
    @Test
    public void testfindReNewContractByContractId(){
    	System.out.println(surrenderServiceProxy.findReNewContractByContractId("8a908e105fe828a2015fe83bf8b20008"));
    }
}

