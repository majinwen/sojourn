package com.ziroom.minsu.services.search.service;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.basedata.dao.HotRegionDbInfoDao;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.vo.HotRegionSimpleVo;
import com.ziroom.minsu.services.solr.utils.SpellUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>初始化拼音信息</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.spellServiceImpl")
public class SpellServiceImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(SearchIndexServiceImpl.class);

    @Resource(name = "search.hotRegionDbInfoDao")
    private HotRegionDbInfoDao hotRegionDbInfoDao;

    @Resource(name="search.searchServiceImpl")
    private SearchServiceImpl searchService;

    /**
     * 初始化拼音信息
     * @author afi
     */
    public void initSpellInfo(){
        //只从搜索里面初始化即可
        initIndexPinyin();
    }

    /**
     * @author afi
     * 初始化热门区域拼音词库
     */
    public void initRegionPinyin(){
        //初始化热门区域词库
        List<HotRegionSimpleVo>  hotRegionSimpleVoList = hotRegionDbInfoDao.getHotRegionSimpleList();
        if(!Check.NuNCollection(hotRegionSimpleVoList)){
            for(HotRegionSimpleVo hot: hotRegionSimpleVoList){
                String name = hot.getRegionName();
                SpellUtils.addSpell(name);
            }
        }
    }


    /**
     * @author afi
     * 初始化城市区域拼音词库
     */
    public void initAreaPinyin(){
        //初始化热门区域词库
        List<HotRegionSimpleVo>  hotRegionSimpleVoList = hotRegionDbInfoDao.getAreaSimpleList();
        if(!Check.NuNCollection(hotRegionSimpleVoList)){
            for(HotRegionSimpleVo hot: hotRegionSimpleVoList){
                String name = hot.getRegionName();
                SpellUtils.addSpell(name);
            }
        }
    }




    /**
     * @author afi
     * 初始化索引的拼音信息
     */
    public void initIndexPinyin(){
        int limit  = 100;
        PageRequest pageRequest = new PageRequest();
        QueryResult queryResult =searchService.getSpellListInfo(pageRequest);
        Long  count = queryResult.getTotal();
        if(Check.NuNObj(count) || count == 0){
            return;
        }
        int pageAll = ValueUtil.getPage(count.intValue(),limit);
        for(int page=1 ; page <= pageAll ; page++){
            try{
                pageRequest.setPage(page);
                queryResult =searchService.getSpellListInfo(pageRequest);
                LOGGER.info("test zhangshaobin" + queryResult.toString());
                List<SolrDocument> docs = (List) queryResult.getValue();
                for (SolrDocument doc : docs) {
                    String name = ValueUtil.getStrValue(doc.getFieldValue("communityName"));
                    SpellUtils.addSpell(name);
                    SpellUtils.addHouseOnlySpell(name);
                }
            }catch (Exception e){
                LogUtil.error(LOGGER, "e:{}", e);
            }
        }
    }
}
