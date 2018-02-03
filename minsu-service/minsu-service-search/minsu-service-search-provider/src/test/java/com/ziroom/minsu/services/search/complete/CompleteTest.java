package com.ziroom.minsu.services.search.complete;

import base.BaseTest;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.common.QueryService;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Set;

/**
 * <p>补全的测试</p>
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
public class CompleteTest extends BaseTest {

    @Resource(name="search.queryService")
    private QueryService queryService;

    @Test
    public void testAutoCompleteWithFacet(){
        Long start=System.currentTimeMillis();
        Set<String> autoCompleteList = queryService.autoCompleteWithFacet(SolrCore.m_house_info,new String[]{"houseName","houseNameAuto","houseAddr","houseAddrAuto","text"}, "测", 50);
        for (String autoWord : autoCompleteList) {
            System.out.println(autoWord);
        }
        Long end=System.currentTimeMillis();
        System.out.println("使用Facet做自动补全使用的时间为:"+(end-start));
    }

    @Test
    public void testAutoCompleteWithTerms(){

        Long start=System.currentTimeMillis();
        Set<String> autoCompleteList = queryService.autoCompleteWithTerms(SolrCore.m_house_info,"text", "测", 50);
        for (String autoWord : autoCompleteList) {
            System.out.println(autoWord);
        }
        Long end=System.currentTimeMillis();
        System.out.println("使用Facet做自动补全使用的时间为:"+(end-start));
    }

}
