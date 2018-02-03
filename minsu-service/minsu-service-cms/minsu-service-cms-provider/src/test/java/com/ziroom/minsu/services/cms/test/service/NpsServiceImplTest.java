package com.ziroom.minsu.services.cms.test.service;

import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.services.cms.service.NpsServiceImpl;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author homelink on 2016/11/11 15:07
 * @version 1.0
 * @since 1.0
 */
public class NpsServiceImplTest extends BaseTest{

    @Resource(name = "cms.npsServiceImpl")
    private NpsServiceImpl npsServiceImpl;

    @Test
    public void getByCode() throws Exception {
//        NpsEntiy byCode = npsServiceImpl.getNps();
//        System.err.println(JsonEntityTransform.Object2Json(byCode));

        System.err.println(UUIDGenerator.hexUUID());
    }


}