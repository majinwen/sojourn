package com.ziroom.minsu.services.solr.common.impl;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.solr.constant.SolrConstant;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.server.SolrServerWapper;
import com.ziroom.minsu.services.solr.common.IndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 创建索引的实现 </p>
 * <p/>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
@Service(value = "search.indexService")
public class IndexServiceImpl implements IndexService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexServiceImpl.class);

	@Override
	public void creatIndex(SolrCore solrcore, Object entity) {
		SolrServerWapper server = SolrServerWapper.getSolrServer(solrcore);
		server.creatIndex(entity);
	}

	@Override
	public void batchCreateIndex(SolrCore solrcore, List<Object> entityList) {
		if (entityList == null || entityList.size() == 0) {
			return;
		}
		int length = entityList.size();
		SolrServerWapper server = SolrServerWapper.getSolrServer(solrcore);
		int page = ValueUtil.getPage(length, SolrConstant.solr_page_limit);
		LogUtil.info(LOGGER, "solr创建索引 page:{}",page);
		int current = 0;
		for(int i=0;i<page;i++){
			List<Object> updateList = new ArrayList<>();
			//获取当前的循环的最小值
			int compareMin = SolrConstant.solr_page_limit>length?length:SolrConstant.solr_page_limit;
			for(int j=0;j<compareMin;j++){
				updateList.add(entityList.get(current));
				current++;
				length--;
			}
			server.creatIndexs(updateList);
			LogUtil.info(LOGGER, "solr创建索引结束 第:{}页",i);
		}
	}

	@Override
	public void create(SolrCore solrcore, Object entity) {
		SolrServerWapper server = SolrServerWapper.getSolrServer(solrcore);
		server.creatIndex(entity);

	}

    /**
     * 删除索引
     * @param query
     */
    @Override
    public void deleteByQuery(SolrCore solrcore,String query){
        SolrServerWapper server = SolrServerWapper.getSolrServer(solrcore);
        server.deleteByQuery(query);
    }


    /**
     * 通过idList删除索引信息
     * @author afi
     * @param solrcore
     * @param ids
     */
    @Override
    public void deleteByIds(SolrCore solrcore,List<String> ids){
        if (ids == null || ids.size() == 0) {
            return;
        }
        int length = ids.size();
        SolrServerWapper server = SolrServerWapper.getSolrServer(solrcore);
        int page = ValueUtil.getPage(length, SolrConstant.solr_page_limit);
        int current = 0;
        for(int i=0;i<page;i++){
            List<String> delList = new ArrayList<>();
            //获取当前的循环的最小值
            int compareMin = SolrConstant.solr_page_limit>length?length:SolrConstant.solr_page_limit;
            for(int j=0;j<compareMin;j++){
                delList.add(ids.get(current));
                current++;
                length--;
            }
            String idStr = ValueUtil.transList2StrInSolr(delList);
            server.deleteByQuery("id:"+idStr);
        }
    }


}
