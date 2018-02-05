package test;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.services.order.dto.LandOrderListRequest;
import com.ziroom.minsu.services.order.dto.OrderEvalRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/14.
 * @version 1.0
 * @since 1.0
 */
public class LandlordEvaControllerTest {




    private static final Logger LOGGER = LoggerFactory.getLogger(LandOrderControllerTest.class);

    private static IEncrypt iEncrypt= EncryptFactory.createEncryption("DES");

    /**
     *
     * 查询订单详情
     *
     * @author yd
     * @created 2016年6月25日 下午3:56:22
     *
     */
    public static void queryEvaluateNew(){
        OrderEvalRequest request = new OrderEvalRequest();
        request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
        request.setOrderEvalType(1);
        request.setLimit(10);
        request.setPage(1);

        System.err.println(JsonEntityTransform.Object2Json(request));
        String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(request));
        System.err.println("2y5QfvAy="+paramJson+"&hPtJ39Xs="+ MD5Util.MD5Encode(JsonEntityTransform.Object2Json(request).toString(),"UTF-8"));
    }
    public static void main(String[] args) {

        queryEvaluateNew();

    }

}
