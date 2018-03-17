package com.ziroom.minsu.mapp.card;

import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/26.
 * @version 1.0
 * @since 1.0
 */
public class UserTest {


    public static void main(String[] args) {
        String url = "http://passport.q.ziroom.com/api/index.php?r=user/index&uid=f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6";
        String json = CloseableHttpsUtil.sendFormPost(url, null);
        Map rst = JsonEntityTransform.json2Map(json);
        String code = ValueUtil.getStrValue(rst.get("code"));

        if (code.equals("20000")){
            Map date = (Map) rst.get("data");
            Map cards =  (Map) date.get("cards");
            System.out.println(JsonEntityTransform.Object2Json(cards));
        }


    }



}
