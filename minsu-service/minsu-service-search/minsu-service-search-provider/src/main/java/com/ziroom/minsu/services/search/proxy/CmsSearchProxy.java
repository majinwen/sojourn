package com.ziroom.minsu.services.search.proxy;

import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.*;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.constant.MessageConst;
import com.ziroom.minsu.services.common.constant.SearchConstant;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.RedisKeyConst;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.api.inner.CmsSearchService;
import com.ziroom.minsu.services.search.api.inner.ZrySearchService;
import com.ziroom.minsu.services.search.dto.CmsArticleDetailRequest;
import com.ziroom.minsu.services.search.dto.CmsArticleRequest;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;
import com.ziroom.minsu.services.search.entity.CmsArticleDetailEntity;
import com.ziroom.minsu.services.search.entity.CmsArticleEntity;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.service.*;
import com.ziroom.minsu.services.search.vo.CmsArticleInfoVo;
import com.ziroom.minsu.services.search.vo.StaticResourceVo;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>自如驿对外暴露的接口服务都走这里</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月25日 15:04
 * @since 1.0
 */
@Component("search.cmsSearchProxy")
public class CmsSearchProxy implements CmsSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsSearchProxy.class);

    @Autowired
    private RedisOperations redisOperations;

    @Resource(name = "search.messageSource")
    private MessageSource messageSource;

    @Value("#{'${default_pic_size}'.trim()}")
    private String defaultPicSize;

    @Resource(name = "search.freshCmsIndexServiceImpl")
    private FreshCmsIndexServiceImpl freshCmsIndexServiceImpl;

    @Resource(name = "search.searchCmsServiceImpl")
    private SearchCmsServiceImpl searchCmsServiceImpl;

    @Resource(name = "search.searchConditionServiceImpl")
    SearchConditionServiceImpl searchConditionServiceImpl;

    public static final String THREAD_NAME_CMS_ARTICLE_LIST = "THREAD_NAME_CMS_ARTICLE_LIST";


    @Override
    public String freshIndex() {
        DataTransferObject dto = new DataTransferObject();

        boolean started=false;
        Thread[] threads = new Thread[Thread.activeCount()];
        Thread.enumerate(threads);
        if (!Check.NuNObject(threads)) {
            for (Thread th:threads) {
                if (THREAD_NAME_CMS_ARTICLE_LIST.equalsIgnoreCase(th.getName()) && th.isAlive()) {
                    started=true;
                }
            }
        }

        if (started) {
            LogUtil.info(LOGGER, "同步cms文章正在进行中！");
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setMsg("同步cms文章正在进行中，请耐心等待！");
            return dto.toJsonString();
        }else {

            try {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            freshCmsIndexServiceImpl.freshIndexAll();
                        } catch (Exception e) {
                            LogUtil.error(LOGGER, "刷新cms文章失败 ,e:{}", e);
                            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
                            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
                        }

                    }
                });
                thread.setName(THREAD_NAME_CMS_ARTICLE_LIST);
                thread.start();
            } catch (Exception e) {
                LogUtil.error(LOGGER, "同步cms文章 exception,e={}",e);
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("同步cms文章失败，请稍后再试！");
                return dto.toJsonString();
            }

        }

        return dto.toJsonString();
    }





    @Override
    public String getListByConditionAndRecommend(String paramsJson) {
        DataTransferObject dto = new DataTransferObject();
        try {
            CmsArticleRequest paramsRequest = JsonEntityTransform.json2Object(paramsJson, CmsArticleRequest.class);
            dto = vilidateHouseListBaseParam(paramsRequest);
            if (Check.NuNObj(dto) || dto.getCode()==DataTransferObject.ERROR){
                return dto.toJsonString();
            }
            // 条件查询后台
            QueryResult getHouseListInfo = searchCmsServiceImpl.searchArticleList(paramsRequest);
            if (getHouseListInfo.getTotal() <10) {
                CmsArticleRequest suggestRequest = new CmsArticleRequest();
                suggestRequest.setLimit(SolrConstant.suggest_page_limit);
                suggestRequest.setPage(1);
                if(getHouseListInfo.getTotal()>0){
                    List<CmsArticleEntity> list = (List<CmsArticleEntity>) getHouseListInfo.getValue();
                    if(!Check.NuNCollection(list)){
                        Set<String> notIds = new HashSet<>();
                        for (CmsArticleEntity cms:list) {
                            notIds.add(cms.getId());
                        }
                        suggestRequest.setNotIds(notIds);
                    }
                }

                QueryResult suggest = searchCmsServiceImpl.searchArticleList(suggestRequest);
                dto.putValue("suggest", suggest.getValue());
                dto.putValue("suggestMsg", getMsg(SearchConstant.StaticResourceCode.NO_MORE_TIPS_CMS));
            } else {
                dto.putValue("suggest", new ArrayList<>());
                dto.putValue("suggestMsg", "");
            }
            dto.putValue("list", getHouseListInfo.getValue());
            dto.putValue("total", getHouseListInfo.getTotal());
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getHouseListByConditionAndRecommend ,param={},e:{}", paramsJson, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }

        return dto.toJsonString();
    }


    /**
     * 查询静态资源
     * @author zl
     * @created 2017/8/9 17:54
     * @param
     * @return 
     */
    private String getMsg(String resCode ){

        try {
            StaticResourceVo resource=  searchConditionServiceImpl.getStaticResourceByResCode(resCode);
            if (!Check.NuNObj(resource)) return resource.getResContent();

        } catch (Exception e) {
            LogUtil.error(LOGGER, "查询静态资源异常 ,param={},e:{}", resCode, e);
        }

        return  "";
    }


    /**
     * 搜索参数校验
     * @author zl
     * @created 2017/7/31 16:30
     * @param
     * @return
     */
    public DataTransferObject vilidateHouseListBaseParam( CmsArticleRequest paramsRequest){

        DataTransferObject dto = new DataTransferObject();

        if(Check.NuNObj(paramsRequest)){
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.PARAM_NULL));
            return dto;
        }




        return dto;
    }


    @Override
    public String getArticleDetail(String paramsJson) {
        DataTransferObject dto = new DataTransferObject();
        try {
            CmsArticleDetailRequest paramsRequest = JsonEntityTransform.json2Object(paramsJson, CmsArticleDetailRequest.class);
            if (Check.NuNObj(paramsRequest)) {
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("参数错误");
                return dto.toJsonString();
            }
            // 条件查询后台
            CmsArticleDetailEntity detail = searchCmsServiceImpl.getArticleDetail(paramsRequest);
            if(Check.NuNObj(detail)){
                dto.setErrCode(DataTransferObject.ERROR);
                dto.setMsg("不存在");
                return dto.toJsonString();
            }

            dto.putValue("articleDetail",detail);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "getArticleDetail ,param={},e:{}", paramsJson, e);
            dto.setErrCode(MessageSourceUtil.getIntMessage(messageSource, MessageConst.ERROR_CODE));
            dto.setMsg(MessageSourceUtil.getChinese(messageSource, MessageConst.UNKNOWN_ERROR));
        }

        return dto.toJsonString();

    }




}
