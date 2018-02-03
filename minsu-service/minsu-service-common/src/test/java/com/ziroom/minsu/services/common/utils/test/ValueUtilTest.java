package com.ziroom.minsu.services.common.utils.test;

import com.asura.framework.base.util.JsonEntityTransform;
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
 * @author afi on 2016/5/23.
 * @version 1.0
 * @since 1.0
 */
public class ValueUtilTest {


    public static void main(String[] args) {

        Map<String,String> par = new HashMap();
        par.put("{1}","==123123==");
        par.put("{2}",null);
        par.put("{3}", null);

//        System.out.println(JsonEntityTransform.Object2Json(par));
//
//        System.out.println(JsonEntityTransform.Object2Json(ValueUtil.cleanMap(par)));

        String aa = "adasdasdasd{1},========={2},--------------{3}";

        for (Map.Entry<String, String> entry:par.entrySet()) {
            aa = aa.replace( entry.getKey(), entry.getValue());
        }
        System.out.println(aa);

    }
}
