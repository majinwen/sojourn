package com.ziroom.minsu.services.solr.server;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.solr.exception.SearchException;
import com.ziroom.minsu.services.solr.index.SolrCore;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>solrj 搜索服务的封装</p>
 * <p/>
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
public class SolrServerWapper {

    /** log */
    private static final Logger LOGGER = LoggerFactory.getLogger(SolrServerWapper.class);

    private static final Map<SolrCore, SolrServerWapper> servers = new ConcurrentHashMap<>();

    private HttpSolrClient solrServer;

    private ResourceBundle rb = ResourceBundle.getBundle("solr");


    static {
        for (SolrCore type : SolrCore.values()) {
            servers.put(type, new SolrServerWapper(type));
        }
    }

    /**
     *
     * @author zl
     * @created 2017/8/11 9:55
     * @param
     * @return
     */
    private SolrServerWapper(SolrCore type) {
        String pre = rb.getString(type.getSolrSeverUrl());
        if (Check.NuNStr(pre)){
            throw new SearchException("there is no solr.url in  solr.properties");
        }
        if(!pre.endsWith("/")){
            pre = pre + "/";
        }
        this.solrServer =  new HttpSolrClient(pre+ type.getSolrSeverCode());
//        this.solrServer.setConnectionTimeout(1);
//        this.solrServer.setSoTimeout(5);
    }

    /**
     * 获取当前的搜索服务
     * @param indexType
     * @return
     */
    public static SolrServerWapper getSolrServer(SolrCore indexType) {

        return servers.get(indexType);
    }



    /**
     * 搜索的查询
     * @param params
     * @return
     */
    public QueryResponse query(SolrParams params) {
    	QueryResponse queryResponse = null;
    	
    	for(int i=1;i<=5;i++){
    		try {
                queryResponse  = solrServer.query(params, SolrRequest.METHOD.POST); 
            } catch (Exception e) {
            	if (i==5) {
            		throw new SearchException("query failture.", e);
				}
            }
    		if (queryResponse!=null && queryResponse.getStatus()==0) {
				break;
			}
    	}
    	
    	return queryResponse;
    }



    public void creatIndex(Object obj) {
        try {
            solrServer.addBean(obj);
            solrServer.commit();
        } catch (SolrServerException e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("creat index failture.", e);
        }catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("creat index failture.", e);
        }
    }


    public void creatIndexs(List<Object> eleList) {
        try {
            solrServer.addBeans(eleList);
            solrServer.commit();
        } catch (SolrServerException e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("creat indexs failture.", e);
        }catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("creat indexs failture.", e);
        }
    }


    /**
     * 删除索引信息
     * @param solrId
     */
    public void deleteBySolrId(String solrId) {
        try {
            solrServer.deleteById(solrId);
            solrServer.commit();
        } catch (SolrServerException e) {
            throw new SearchException("deleteBySolrId failture.", e);
        }catch (Exception e) {
            throw new SearchException("deleteBySolrId failture.", e);
        }
    }


    /**
     * 删除索引信息
     * @param queryStr
     */
    public void deleteByQuery(String queryStr) {
        try {
            solrServer.deleteByQuery(queryStr);
            solrServer.commit();
        } catch (SolrServerException e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("deleteByQuery failture.", e);
        }catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
            throw new SearchException("deleteByQuery failture.", e);
        }
    }


}
