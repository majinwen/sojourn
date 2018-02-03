package com.ziroom.minsu.services.solr.query.parser.support;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.CmsArticleEntity;
import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import com.ziroom.minsu.services.search.vo.CmsArticleImgVo;
import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
 * @Date Created in 2017年07月25日 15:08
 * @since 1.0
 */
public class CmsArticleQueryResultParser extends AbstractQueryResultParser<CmsArticleEntity> {


    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleQueryResultParser.class);

    private static final String datePatten = "yyMMdd";
    private static final String split = ",";
    private static final String cmsdatePatten = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected CmsArticleEntity doParser(Map<String, Object> par, SolrDocument doc) {
        if (Check.NuNObj(doc)) {
            return null;
        }

        CmsArticleEntity articleEntity = new CmsArticleEntity();

        articleEntity.setBannerImg(getBannerImg( doc));
        articleEntity.setBusinessArea(getBusinessArea( doc));
        articleEntity.setCityCode(ValueUtil.getStrValue(doc.getFieldValue("cityCode")));
        articleEntity.setCityName(ValueUtil.getStrValue(doc.getFieldValue("cityName")));
        articleEntity.setId(ValueUtil.getStrValue(doc.getFieldValue("id")));
        articleEntity.setSubTitle(ValueUtil.getStrValue(doc.getFieldValue("subTitle")));
        articleEntity.setTargetUrl(getTargetUrl(ValueUtil.getStrValue(doc.getFieldValue("targetUrl")),articleEntity.getId()));
        articleEntity.setTitle(ValueUtil.getStrValue(doc.getFieldValue("title")));
        articleEntity.setCategory(ValueUtil.getStrValue(doc.getFieldValue("category")));
        articleEntity.setSubCategory(ValueUtil.getStrValue(doc.getFieldValue("subCategorys")));
        return articleEntity;
    }


    /**
     * 返回完整的url
     * @author zl
     * @created 2017/8/11 15:34
     * @param
     * @return
     */
    private String getTargetUrl(String url,String id){
        return url+"?id="+id;
    }

    /**
     * 转化标签
     * @author zl
     * @created 2017/8/4 18:14
     * @param
     * @return
     */
    private List<String> getTags(SolrDocument doc){
        List<String> list = new ArrayList<>();

        Collection<Object> strs = doc.getFieldValues("tags");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(eleStr);
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"转化标签失败，strs={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }
        return list;
    }

    /**
     * 转化商圈
     * @author zl
     * @created 2017/8/4 18:15
     * @param
     * @return
     */
    private String getBusinessArea(SolrDocument doc){
        List<String> list = new ArrayList<>();

        Collection<Object> strs = doc.getFieldValues("businessAreas");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(eleStr);
                }
            }
            if (!Check.NuNCollection(list)) {
                return list.get(0);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"转化商圈失败，strs={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }
        return null;
    }

    /**
     * 转化头图
     * @author zl
     * @created 2017/8/4 18:09
     * @param
     * @return
     */
    private String getBannerImg(SolrDocument doc){

        List<CmsArticleImgVo> list = new ArrayList<>();
        Collection<Object> strs = doc.getFieldValues("bannerImg");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(JsonEntityTransform.json2Entity(eleStr,CmsArticleImgVo.class));
                }
            }

            if(!Check.NuNCollection(list) && !Check.NuNObj(list.get(0))){
                return list.get(0).getImg();
            }

        }catch (Exception e){
            LogUtil.error(LOGGER,"转化头图失败，imgStr={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }

        return null;
    }






}
