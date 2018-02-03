package com.ziroom.minsu.services.common.utils.test;

import com.ziroom.minsu.services.common.utils.JsonTransform;

/**
 * <p>TODO</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public class JsonTransformTest {


    public static void main(String[] args) {

//        Test test = new Test();
//        test.setAa("aaaa");
//        test.setBb("nnnnn");
//        System.out.println(JsonEntityTransform.Object2Json(test));

        String aa = "{\"aa\":\"aaaa\",\"bb\":\"nnnnn\",\"cc\":\"ssssss\"}";


        Test test = JsonTransform.json2Entity(aa,Test.class);
        System.out.println(aa);
    }

}
