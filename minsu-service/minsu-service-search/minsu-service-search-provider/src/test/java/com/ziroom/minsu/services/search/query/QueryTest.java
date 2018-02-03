package com.ziroom.minsu.services.search.query;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.services.search.entity.QueryRequest;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.solr.common.QueryService;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.server.SolrServerWapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>搜索的测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/26.
 * @version 1.0
 * @since 1.0
 */
public class QueryTest extends BaseTest {


    @Resource(name="search.queryService")
    private QueryService queryService;




    @Test
    public void TestgetIds(){


        QueryRequest request = new QueryRequest();
        request.setPageIndex(0);
        request.setPageSize(10);
        Map<String, Object> filterQueries = new HashMap<String, Object>();
        request.setReturnFields("id");
        QueryResult result = queryService.getIds(SolrCore.m_house_info,request);
        System.out.println(JsonEntityTransform.Object2Json(result));
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");

    }


    @Test
    public void Testquery(){


        QueryRequest request = new QueryRequest();
        request.setPageIndex(0);
        request.setPageSize(10);
        Map<String, Object> filterQueries = new HashMap<String, Object>();

        filterQueries.put("cityCode", "310001");
        request.setFilterQueries(filterQueries);
        Map<String, Object> sorts = new HashMap<String , Object>();
        sorts.put("price", "desc");
        request.setSorts(sorts);

        request.setReturnFields("");
        QueryResult result = queryService.query(SolrCore.m_house_info,request);
        System.out.println(JsonEntityTransform.Object2Json(result));
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");
        System.out.println("++++++++++++++++++++++++");

    }


    @Test
    public void TestSuggest(){

        SolrQuery query = new SolrQuery();
        String token = "团结";
        query.set("qt", "/suggest");
        query.set("q", token);
        query.set("spellcheck", "on");
        query.set("spellcheck.build", "true");
        query.set("spellcheck.onlyMorePopular", "true");
        query.set("spellcheck.count", "100");
        query.set("spellcheck.alternativeTermCount", "4");
        query.set("spellcheck.onlyMorePopular", "true");
        query.set("spellcheck.extendedResults", "true");
        query.set("spellcheck.maxResultsForSuggest", "5");
        query.set("spellcheck.collate", "true");
        query.set("spellcheck.collateExtendedResults", "true");
        query.set("spellcheck.maxCollationTries", "5");
        query.set("spellcheck.maxCollations", "3");

        QueryResponse response = null;

        SolrServerWapper solrServer = SolrServerWapper.getSolrServer(SolrCore.m_house_info);

        response = solrServer.query(query);
        System.out.println("查询耗时：" + response.getQTime());

        SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
        if (spellCheckResponse != null) {
            SpellCheckResponse.Suggestion suggestion = spellCheckResponse.getSuggestion(token);
            System.out.println("NumFound: " + suggestion.getNumFound());
            System.out.println("Token: " + suggestion.getToken());
            System.out.print("suggested: ");
            List<String> suggestedList = suggestion.getAlternatives();
            for (String suggestedWord : suggestedList) {
                System.out.print(suggestedWord + ", ");
            }
            System.out.println("\n\n");

            System.out.println("The First suggested word for solr is : " + spellCheckResponse.getFirstSuggestion(token));
            System.out.println("\n\n");

            List<SpellCheckResponse.Collation> collatedList = spellCheckResponse.getCollatedResults();
            if (collatedList != null) {
                for (SpellCheckResponse.Collation collation : collatedList) {
                    System.out.println("collated query String: " + collation.getCollationQueryString());
                    System.out.println("collation Num: " + collation.getNumberOfHits());
                    List<SpellCheckResponse.Correction> correctionList = collation.getMisspellingsAndCorrections();
//                    for (SpellCheckResponse.Collation correction : correctionList) {
//                        System.out.println("original: " + "");
//                        System.out.println("correction: " + "");
//                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("The Collated word: " + spellCheckResponse.getCollatedResult());
            System.out.println();
        }

        System.out.println("查询耗时：" + response.getQTime());

    }
    
    
    @Test
    public void queryMultiCore(){ 
    	
    	try {
			
    		HttpSolrClient sc=new HttpSolrClient("http://127.0.0.1:8280/m_house_info");  
    		String shards = "127.0.0.1:8280/m_house_info,127.0.0.1:8280/m_house_info2";  
    		ModifiableSolrParams solrParams = new ModifiableSolrParams();  
    		solrParams.set("q", "8a90a2d455b47fe00155b9dd47c20121");  
//        solrParams.set("q.op", "AND");//设置查询关系 
    		solrParams.set("shards", shards);//设置shard  
    		QueryResponse rsp = sc.query(solrParams);  
    		System.out.println("命中数量："+rsp.getResults().getNumFound()); 
    		System.out.println(JsonEntityTransform.Object2Json(rsp.getResults()));
    		
    		for(SolrDocument sd:rsp.getResults()){  
    			System.out.println(sd);  
    		}  
    		sc.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    }
    
    
}
