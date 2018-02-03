package com.ziroom.minsu.services.cms.test.base;

import com.asura.framework.base.util.UUIDGenerator;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/1.
 * @version 1.0
 * @since 1.0
 */
public class UUIDGeneratorTest {


    public static void main(String[] args) {
        for (int i = 0; i < 168; i++) {
            System.out.println(UUIDGenerator.hexUUID());
        }
    }
}
