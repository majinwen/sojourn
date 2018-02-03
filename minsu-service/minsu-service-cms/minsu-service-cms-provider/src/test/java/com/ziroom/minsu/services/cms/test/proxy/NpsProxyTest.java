package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.NpsEntiy;
import com.ziroom.minsu.services.cms.dto.NpsAttendRequest;
import com.ziroom.minsu.services.cms.dto.NpsGetCondiRequest;
import com.ziroom.minsu.services.cms.dto.NpsGetRequest;
import com.ziroom.minsu.services.cms.proxy.NpsProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import com.ziroom.minsu.valenum.common.UserTypeEnum;
import org.junit.Test;

import javax.annotation.Resource;

/** <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/14 9:56
 * @version 1.0
 * @since 1.0
 */
public class NpsProxyTest extends BaseTest {


    @Resource(name = "cms.npsProxy")
    private NpsProxy npsProxy;



    @Test
    public void getNps() {
        NpsGetRequest npsGetRequest = new NpsGetRequest();
        npsGetRequest.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        npsGetRequest.setUserType(UserTypeEnum.TENANT.getUserCode());
//        npsGetRequest.setNpsCode("recommend");
        String nps = npsProxy.getNps(JsonEntityTransform.Object2Json(npsGetRequest));
        System.err.println(nps);
    }


    @Test
    public void getNpsAttendForPage() {
       /* NpsGetCondiRequest request = new NpsGetCondiRequest();
        String str = npsProxy.getNpsAttendForPage(JsonEntityTransform.Object2Json(request));*/
//        String str = npsProxy.getNpsAttendForPage("{\"page\":1,\"limit\":10,\"npsCode\":\"\",\"userType\":\"\",\"uid\":\"\",\"userName\":\"\",\"mobile\":null}");
        String str2 = npsProxy.getNpsForPage("{\"page\":1,\"limit\":10,\"npsCode\":\"\",\"userType\":\"\",\"uid\":\"\",\"userName\":\"\",\"mobile\":null}");
        System.err.println(str2);
    }

    @Test
    public void updateNps() {
        NpsEntiy nps = new NpsEntiy();
        nps.setNpsCode("recommend2");
        nps.setNpsStatus(1);
        String result = npsProxy.editNpsStatus(nps);
        System.out.println(result);
    }

    @Test
    public void saveNpsAttend(){
        NpsAttendRequest npsAttendRequest = new NpsAttendRequest();
        npsAttendRequest.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        npsAttendRequest.setUserType(1);
        npsAttendRequest.setScore(10);
        npsAttendRequest.setOrderSn("160511252A9834163856");
        //npsAttendRequest.setNpsCode("recommend");
        String aa = npsProxy.saveNpsAttend(JsonEntityTransform.Object2Json(npsAttendRequest));
        System.out.println(aa);
    }

    @Test
    public void calculateNPS(){

        NpsGetCondiRequest npsGetCondiRequest = new NpsGetCondiRequest();
        npsGetCondiRequest.setNpsName("评价后调研");
        npsGetCondiRequest.setNpsStartTime("2016-07-03 00:00:00");
        npsGetCondiRequest.setNpsEndTime("2017-07-07 23:59:59");
        String result = npsProxy.calculateNPS(npsGetCondiRequest);
        System.out.print(result);


    }

}

