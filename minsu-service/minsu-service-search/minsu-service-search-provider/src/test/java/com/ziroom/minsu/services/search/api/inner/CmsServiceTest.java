package com.ziroom.minsu.services.search.api.inner;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.dto.CmsArticleDetailRequest;
import com.ziroom.minsu.services.search.dto.CmsArticleRequest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年08月10日 10:56
 * @since 1.0
 */
public class CmsServiceTest extends BaseTest {

    @Resource(name = "search.cmsSearchService")
    private CmsSearchService cmsSearchService;

    private static final String picSize = "_Z_375_211";


    @Test
    public void getListByConditionAndRecommend(){
        CmsArticleRequest paramsRequest = new CmsArticleRequest();
        paramsRequest.setCityCode("110100");

        String detailJson = cmsSearchService.getListByConditionAndRecommend(JsonEntityTransform.Object2Json(paramsRequest));

        System.out.println(detailJson);

    }

    @Test
    public void getArticleDetail(){
        CmsArticleDetailRequest paramsRequest = new CmsArticleDetailRequest();
        paramsRequest.setId("489");

        String detailJson = cmsSearchService.getArticleDetail(JsonEntityTransform.Object2Json(paramsRequest));

        System.out.println(detailJson);

    }

}
