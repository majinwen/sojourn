package com.ziroom.minsu.services.search.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RandomUtil;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.vo.*;
import com.ziroom.minsu.services.solr.common.IndexService;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.valenum.house.OrderTypeEnum;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import com.ziroom.minsu.valenum.search.SearchDataSourceTypeEnum;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>刷新cms文章</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月28日 10:21
 * @since 1.0
 */
@Service(value = "search.freshCmsIndexServiceImpl")
public class FreshCmsIndexServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(FreshCmsIndexServiceImpl.class);

    @Resource(name="search.indexService")
    private IndexService indexService;

    @Resource(name = "search.queryService")
    private QueryService queryService;

    @Value("#{'${pic_base_addr_minsu}'.trim()}")
    private String picBaseAddrMona;

    @Value("#{'${default_head_size}'.trim()}")
    private String defaultHeadSize;

    @Value("#{'${default_head_url}'.trim()}")
    private String defaultHeadUrl;

    @Value("#{'${static_base_url}'.trim()}")
    private String staticBaseUrl;

    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;

    @Value("#{'${CMS_ARTICLE_LIST_URL}'.trim()}")
    private String CMS_ARTICLE_LIST_URL;

    @Value("#{'${cms_static_detail_base}'.trim()}")
    private String cms_static_detail_base;

    private static final String datePatten = "yyMMdd";
    private static final String split = ",";

    private static final String cmsdatePatten = "yyyy-MM-dd HH:mm:ss";



    /**
     * 批量创建索引
     * @author zl
     * @created 2017/7/28 14:11
     * @param
     * @return 
     */
    public void freshIndexAll(){


        Set<String> existIdes = new HashSet<>();
        try {
            int page=1;
            int limit=20;
            boolean error = false;
            List<CmsArticleInfoVo> articleVoList = new ArrayList<>();
            do{

                articleVoList = getCmsArticleList(page,limit);
                if(articleVoList==null){
                    error = true;
                    break;
                }
                if(!Check.NuNCollection(articleVoList)){
                    dealBatchIndex(articleVoList,existIdes);
                }
                page++;
            }while (!Check.NuNCollection(articleVoList));

            if(!error){
                delNotExist(existIdes);
            }
        }catch (Exception e){
            LogUtil.error(LOGGER, "全量刷新cms文章失败 ,e:{}",e);
        }

    }

    /**
     * 批量刷新索引
     * @author zl
     * @created 2017/7/31 15:48
     * @param
     * @return 
     */
    public void dealBatchIndex(List<CmsArticleInfoVo> articleVoList,Set<String> existIdes){

        try {
            if(Check.NuNCollection(articleVoList)){
                return;
            }
            List<Object> articleInfoList = new ArrayList<>();
            for (CmsArticleInfoVo articleInfoVo:articleVoList) {
                CmsArticleInfo  articleInfo = transToCmsArticleInfo( articleInfoVo);
                articleInfoList.add(articleInfo);
                if(existIdes!=null){
                    existIdes.add(articleInfo.getId());
                }
            }
            indexService.batchCreateIndex(SolrCore.cms_article_info, articleInfoList);
        }catch (Exception e){
            LogUtil.error(LOGGER, "批量刷新cms文章失败 ,params={},e:{}",JsonEntityTransform.Object2Json(articleVoList),e);
        }

    }



    /**
     * 同步cms文章
     * @author zl
     * @created 2017/7/31 11:20
     * @param
     * @return
     */
    public List<CmsArticleInfoVo> getCmsArticleList(int page,int limit){

        List<CmsArticleInfoVo> list = new ArrayList<>();
        try {

            String url = CMS_ARTICLE_LIST_URL+"&pageSize="+limit+"&page="+page;

            long t1 = System.currentTimeMillis();
            String listJson = CloseableHttpUtil.sendGet(url,null);

            if (Check.NuNStr(listJson)) {
                LogUtil.error(LOGGER, "【getCmsArticleList】同步cms文章接口没有响应:URL={},page={},limt={}", CMS_ARTICLE_LIST_URL, page,limit);
                return null;
            }

            LogUtil.info(LOGGER, "【getCmsArticleList】同步cms文章接口耗时{}ms返回数据，result={}", (System.currentTimeMillis() - t1), listJson);

            JSONObject resObject = JSONObject.parseObject(listJson);

            if (Check.NuNObj(resObject) || !resObject.containsKey("error_code") || !"0".equals(resObject.getString("error_code"))) {
                LogUtil.error(LOGGER, "【getCmsArticleList】同步cms文章接口请求异常:URL={},page={},limt={}", CMS_ARTICLE_LIST_URL, page,limit);
                return null;
            }

            if (resObject.containsKey("data")) {
                JSONObject dataObject =resObject.getJSONObject("data");
                if(dataObject.containsKey("data")){
                    JSONArray jsonArray = dataObject.getJSONArray("data");
                    try{
                        list = JSONObject.parseArray(jsonArray.toJSONString(), CmsArticleInfoVo.class);
                    }catch (Exception e){
                        LogUtil.error(LOGGER, "【getCmsArticleList】cms文章【运营数据配置错误】:URL={},page={},limt={},e={}", CMS_ARTICLE_LIST_URL, page,limit,e);
                        return null;
                    }
                }
            }

        } catch (Exception e) {
            LogUtil.error(LOGGER, "【getCmsArticleList】同步cms文章接口请求异常:URL={},page={},limt={},e={}", CMS_ARTICLE_LIST_URL, page,limit,e);
            return null;
        }
        return list;
    }


    /**
     * 转化成要建索引的数据对象
     * @author zl
     * @created 2017/7/28 11:00
     * @param
     * @return 
     */
    public CmsArticleInfo transToCmsArticleInfo(CmsArticleInfoVo articleInfoVo){

        if(Check.NuNObj(articleInfoVo)){
            return null;
        }

        try {
            CmsArticleInfo articleInfo = new CmsArticleInfo();
            articleInfo.setId(articleInfoVo.getId());
            articleInfo.setTitle(articleInfoVo.getTitle());
            articleInfo.setArticleSn(articleInfoVo.getArticleSN());
//            articleInfo.setAreaCode();
//            articleInfo.setAreaName();
//            articleInfo.setArticleType();
            articleInfo.setCityCode(articleInfoVo.getCityCode());
            articleInfo.setCityName(articleInfoVo.getCityName());
//            articleInfo.setContentTags(getContentTags());
            articleInfo.setSubTitle(articleInfoVo.getSubTitle());
            articleInfo.setSummary(articleInfoVo.getSummary());
            articleInfo.setWeights(getWeights(articleInfoVo));
            articleInfo.setAddress(articleInfoVo.getAddress());
            articleInfo.setBannerImg(getBannerImg( articleInfoVo));
            articleInfo.setBody(articleInfoVo.getBody());
            articleInfo.setBodyImg(getBodyImg( articleInfoVo));
            articleInfo.setBusinessAreas(getBusinessAreas(articleInfoVo.getBusinessAreas()));
            articleInfo.setCategory(articleInfoVo.getCategory());
            articleInfo.setCreatedTime(getCreatedTime(articleInfoVo.getCreated_at()));
            articleInfo.setLocation(getLocation(articleInfoVo.getLocation()));
            articleInfo.setOpeningHours(articleInfoVo.getOpeningHours());
            articleInfo.setPrice(getPrice(articleInfoVo.getPrice()));
            articleInfo.setShareImg(articleInfoVo.getShareImg());
            articleInfo.setShareSubTitle(articleInfoVo.getShareSubTitle());
            articleInfo.setShareTitle(articleInfoVo.getShareTitle());
            articleInfo.setSubCategorys(articleInfoVo.getSubCategorys());
            articleInfo.setTags(getTags(articleInfoVo.getTags()));
            articleInfo.setTargetUrl(getTargetUrl(articleInfoVo.getTargetUrl()));
            articleInfo.setTel(articleInfoVo.getTel());
            articleInfo.setUpdateTime(getUpdateTime(articleInfoVo.getUpdated_at()));
            articleInfo.setRandNum(getRandNum());
            articleInfo.setBackgroundColor(articleInfoVo.getBackgroundColor());

            return articleInfo;
        }catch (Exception e){
            LogUtil.error(LOGGER, "转化cms文章异常 ,params={},e:{}", JsonEntityTransform.Object2Json(articleInfoVo) ,e);
        }
        return null;
    }

    /**
     * 拼接url
     * @author zl
     * @created 2017/8/23 15:26
     * @param
     * @return 
     */
    private String getTargetUrl(String uri){
        if(!Check.NuNStr(uri) && uri.startsWith("http")){//兼容
            return uri;
        }
        return cms_static_detail_base+uri;
    }



    /**
     * 产生随机数
     * @author zl
     * @created 2017/8/4 11:13
     * @param
     * @returnn
     */
    private Integer getRandNum(){
        Integer num = RandomUtils.nextInt(1,10000000);
        return num;
    }

    /**
     * 更新时间转化
     * @author zl
     * @created 2017/8/4 9:59
     * @param
     * @return 
     */
    private Long getUpdateTime(String updateTime){
        try {
            return DateUtil.formatTimestampToLong(updateTime);
        }catch (Exception e){
            LogUtil.error(LOGGER, "更新时间转化错误 ,updateTime={},e:{}", updateTime ,e);
        }
        return System.currentTimeMillis();
    }


    /**
     * 转化标签
     * @author zl
     * @created 2017/8/4 9:21
     * @param
     * @return
     */
    private Set<String> getTags(String tagsStr){
        Set<String> tags = new HashSet<>();
        if (!Check.NuNStr(tagsStr)){
            tagsStr = tagsStr.replaceAll("，",",");
            String[] strs = tagsStr.split(",");
            for (String str:strs ) {
                tags.add(str);
            }
        }
        return tags;
    }

    /**
     * 转化位置
     * @author zl
     * @created 2017/8/9 14:24
     * @param
     * @return 
     */
    private String getLocation(String location){
        if (!Check.NuNStr(location)){
            location = location.replaceAll("，",",");
        }
        return location;
    }

    /**
     * 转化价格
     * @author zl
     * @created 2017/8/4 9:53
     * @param
     * @return
     */
    private String getPrice(String priceStr){
        try {
//            return new Double(ValueUtil.getdoubleValue(priceStr)).intValue();
        }catch (Exception e){
            LogUtil.error(LOGGER, "价格转化错误 ,priceStr={},e:{}", priceStr ,e);
        }
        return priceStr;
    }


    /**
     * 转化创建时间
     * @author zl
     * @created 2017/8/4 9:50
     * @param
     * @return
     */
    private Long getCreatedTime(String createdTime){
        try {
            return DateUtil.formatTimestampToLong(createdTime);
        }catch (Exception e){
            LogUtil.error(LOGGER, "创建时间转化错误 ,createdTime={},e:{}", createdTime ,e);
        }
        return System.currentTimeMillis();
    }

    /**
     * 商圈
     * @author zl
     * @created 2017/8/4 9:44
     * @param
     * @return 
     */
    private Set<String> getBusinessAreas(String businessAreas){
        Set<String> tags = new HashSet<>();
        if (!Check.NuNStr(businessAreas)){
            businessAreas = businessAreas.replaceAll("，",",");
            String[] strs = businessAreas.split(",");
            for (String str:strs ) {
                tags.add(str);
            }
        }
        return tags;
    }

    /**
     * 转化内容图片
     * @author zl
     * @created 2017/8/4 9:40
     * @param
     * @return
     */
    private Set<String> getBodyImg(CmsArticleInfoVo articleInfoVo){
        Set<String> imgs = new HashSet<>();
        if (!Check.NuNCollection(articleInfoVo.getBodyImg())){
            for (CmsArticleImgVo imgVo:articleInfoVo.getBodyImg() ) {
                if(Check.NuNStr(imgVo.getImg())) continue;
                imgs.add(JsonEntityTransform.Object2Json(imgVo));
            }
        }
        return  imgs;
    }

    /**
     * 转化头图
     * @author zl
     * @created 2017/8/4 9:40
     * @param
     * @return 
     */
    private Set<String> getBannerImg(CmsArticleInfoVo articleInfoVo){
        Set<String> imgs = new HashSet<>();
        if (!Check.NuNCollection(articleInfoVo.getBannerImg())){
            for (CmsArticleImgVo imgVo:articleInfoVo.getBannerImg() ) {
                if(Check.NuNStr(imgVo.getImg())) continue;
                imgs.add(JsonEntityTransform.Object2Json(imgVo));
            }
        }
        return  imgs;
    }

    
    /**
     * 获取权重
     * @author zl
     * @created 2017/8/4 9:25
     * @param
     * @return
     */
    private Long getWeights(CmsArticleInfoVo articleInfoVo){
        Long weights =0L;


        return  weights;
    }
    

    /**
     * 转化内容标签
     * @author zl
     * @created 2017/8/4 9:21
     * @param
     * @return 
     */
    private Set<String> getContentTags(String contentTags){
        Set<String> tags = new HashSet<>();
        if (!Check.NuNStr(contentTags)){
            tags.add(contentTags);
        }
        return tags;
    }



    /**
     * 删除不存在的索引
     * @author zl
     * @created 2017/7/31 14:27
     * @param
     * @return 
     */
    private void delNotExist(Set<String> existIdes){

        Set<String> indexIds = getAllIds();
        if(Check.NuNCollection(indexIds)){
            return;
        }
        if(!Check.NuNCollection(existIdes)){
            indexIds.removeAll(existIdes);
        }
        if(Check.NuNCollection(indexIds)){
            return;
        }
        List<String> delIds = new ArrayList<>();
        delIds.addAll(indexIds);
        LogUtil.info(LOGGER, "删除cms索引{}条", delIds.size());
        indexService.deleteByIds(SolrCore.cms_article_info, delIds);
    }

    /**
     * 查询现有的id集合
     * @author zl
     * @created 2017/7/31 14:30
     * @param
     * @return 
     */
    private Set<String> getAllIds(){
        Set<String> all = new HashSet<>();
        QueryRequest request = new QueryRequest();
        request.setPageSize(SolrConstant.create_index_page_limit);
        List<String> list = new ArrayList<>();
        do{
            QueryResult result =queryService.getIds(SolrCore.cms_article_info, request);
            list= (List<String>)result.getValue();
            if(!Check.NuNCollection(list)){
                all.addAll(list);
            }
            request.setPageIndex(request.getPageIndex() + 1);
        }while (!Check.NuNCollection(list));

        return all;
    }


}
