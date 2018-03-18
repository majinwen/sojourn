package com.ziroom.zrp.service.houses.dao;

import com.alibaba.fastjson.JSON;
import com.ziroom.zrp.houses.entity.CmsProjectLabelEntity;
import com.ziroom.zrp.houses.entity.CmsProjectLabelImgEntity;
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
public class CmsProjectLabelImgTest extends BaseTest{

    @Resource(name="houses.cmsProjectLabelImgDao")
    private CmsProjectLabelImgDao cmsProjectLabelImgDao;

    @Test
    public void testGetCmsProjectLabels() {
        List<CmsProjectLabelImgEntity> cmsProjectLabelImgs = cmsProjectLabelImgDao.getCmsProjectLabelImgs("d5222171ee3340e1bda60ef12a668538");
        System.err.println(JSON.toJSONString(cmsProjectLabelImgs));
    }

}
