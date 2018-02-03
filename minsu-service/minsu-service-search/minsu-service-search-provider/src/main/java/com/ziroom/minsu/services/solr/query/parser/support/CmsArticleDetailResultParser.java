package com.ziroom.minsu.services.solr.query.parser.support;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.CmsArticleDetailEntity;
import com.ziroom.minsu.services.search.entity.CmsArticleEntity;
import com.ziroom.minsu.services.search.vo.CmsArticleImgVo;
import com.ziroom.minsu.services.solr.query.parser.AbstractQueryResultParser;
import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
 * @Date Created in 2017年08月02日 16:27
 * @since 1.0
 */
public class CmsArticleDetailResultParser extends AbstractQueryResultParser<CmsArticleDetailEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleDetailResultParser.class);

    private static final String datePatten = "yyMMdd";
    private static final String split = ",";
    private static final String cmsdatePatten = "yyyy-MM-dd HH:mm:ss";

    @Override
    protected CmsArticleDetailEntity doParser(Map<String, Object> par, SolrDocument doc) {
        if (Check.NuNObj(doc)) {
            return null;
        }

        CmsArticleDetailEntity articleEntity = new CmsArticleDetailEntity();

        articleEntity.setAddress(ValueUtil.getStrValue(doc.getFieldValue("address")));
        articleEntity.setBannerImg(getBannerImg( doc));
        articleEntity.setBusinessAreas(getBusinessAreas( doc));
        articleEntity.setCityCode(ValueUtil.getStrValue(doc.getFieldValue("cityCode")));
        articleEntity.setCityName(ValueUtil.getStrValue(doc.getFieldValue("cityName")));
        articleEntity.setId(ValueUtil.getStrValue(doc.getFieldValue("id")));
        articleEntity.setSubTitle(ValueUtil.getStrValue(doc.getFieldValue("subTitle")));
        articleEntity.setTags( getTags( doc));
        articleEntity.setTitle(ValueUtil.getStrValue(doc.getFieldValue("title")));
        articleEntity.setArticleSN(ValueUtil.getStrValue(doc.getFieldValue("articleSn")));
        articleEntity.setBody(ValueUtil.getStrValue(doc.getFieldValue("body")));
        articleEntity.setBodyImg(getBodyImg( doc));
        articleEntity.setCategory(ValueUtil.getStrValue(doc.getFieldValue("category")));
        articleEntity.setCreatedTime(getCreatedTime( doc));
        articleEntity.setLocation(ValueUtil.getStrValue(doc.getFieldValue("location")));
        articleEntity.setOpeningHours(ValueUtil.getStrValue(doc.getFieldValue("openingHours")));
        articleEntity.setPrice(ValueUtil.getStrValue(doc.getFieldValue("price")));
        articleEntity.setShareImg(ValueUtil.getStrValue(doc.getFieldValue("shareImg")));
        articleEntity.setShareSubTitle(ValueUtil.getStrValue(doc.getFieldValue("shareSubTitle")));
        articleEntity.setShareTitle(ValueUtil.getStrValue(doc.getFieldValue("shareTitle")));
        articleEntity.setTargetUrl(ValueUtil.getStrValue(doc.getFieldValue("targetUrl")) + "?id=" + articleEntity.getId());
        articleEntity.setSubCategorys(ValueUtil.getStrValue(doc.getFieldValue("subCategorys")));
        articleEntity.setSummary(ValueUtil.getStrValue(doc.getFieldValue("summary")));
        articleEntity.setTel(ValueUtil.getStrValue(doc.getFieldValue("tel")));
        articleEntity.setBackgroundColor(ValueUtil.getStrValue(doc.getFieldValue("backgroundColor")));

        return articleEntity;
    }

    /**
     * 转化内容图片
     * @author zl
     * @created 2017/8/4 18:48
     * @param
     * @return 
     */
    private List<CmsArticleImgVo> getBodyImg(SolrDocument doc){

        List<CmsArticleImgVo> list = new ArrayList<>();
        Collection<Object> strs = doc.getFieldValues("bodyImg");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(JsonEntityTransform.json2Entity(eleStr,CmsArticleImgVo.class));
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"转化内容图片失败，imgStr={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }

        if(list.size()>1){
            Collections.sort(list, new Comparator<CmsArticleImgVo>() {
                @Override
                public int compare(CmsArticleImgVo o1, CmsArticleImgVo o2) {
                    if(!Check.NuNObj(o1.getSort()) && !Check.NuNObj(o2.getSort())){
                        int s1 = ValueUtil.getintValue(o1.getSort());
                        int s2 = ValueUtil.getintValue(o2.getSort());
                        return s2-s1;
                    }
                    return 0;
                }
            });
        }
        return list;
    }


    /**
     * 转化创建时间
     * @author zl
     * @created 2017/8/4 18:46
     * @param
     * @return
     */
    private String getCreatedTime(SolrDocument doc){
            Long date = ValueUtil.getlongValue(doc.getFieldValue("createdTime"));
            if (!Check.NuNObj(date)){
                try{
                    return DateUtil.dateFormat(new Date(date),cmsdatePatten);
                }catch (Exception e){
                    LogUtil.error(LOGGER,"转化创建时间错误，date={}，e={}", date,e);
                }
            }

        return null;
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
            LogUtil.error(LOGGER,"转化标签失败，strs={}，e={}", JsonEntityTransform.Object2Json(strs),e);
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
    private List<String> getBusinessAreas(SolrDocument doc){
        List<String> list = new ArrayList<>();

        Collection<Object> strs = doc.getFieldValues("businessAreas");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(eleStr);
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"转化商圈失败，strs={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }
        return list;
    }

    /**
     * 转化头图
     * @author zl
     * @created 2017/8/4 18:09
     * @param
     * @return
     */
    private List<CmsArticleImgVo> getBannerImg(SolrDocument doc){

        List<CmsArticleImgVo> list = new ArrayList<>();
        Collection<Object> strs = doc.getFieldValues("bannerImg");
        try{
            if (!Check.NuNCollection(strs)) {
                for (Object ele : strs) {
                    String eleStr = ValueUtil.getStrValue(ele);
                    list.add(JsonEntityTransform.json2Entity(eleStr,CmsArticleImgVo.class));
                }
            }
        }catch (Exception e){
            LogUtil.error(LOGGER,"转化头图失败，imgStr={}，e={}",JsonEntityTransform.Object2Json(strs),e);
        }

        return list;
    }


}
