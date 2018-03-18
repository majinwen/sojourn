package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSON;
import com.ziroom.zrp.houses.entity.CmsProjectEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import com.ziroom.zrp.service.houses.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>项目标签页测试类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2018年3月5日 15:00
 * @since 1.0
 */
public class CmsProjectTest extends BaseTest{

    @Resource(name="houses.cmsProjectDao")
    private CmsProjectDao cmsProjectDao;

    @Test
    public void testGetCmsProjectLabels() {
        CmsProjectEntity cmsProject = cmsProjectDao.getCmsProject("21");
        System.err.println(JSON.toJSONString(cmsProject));
    }

}
