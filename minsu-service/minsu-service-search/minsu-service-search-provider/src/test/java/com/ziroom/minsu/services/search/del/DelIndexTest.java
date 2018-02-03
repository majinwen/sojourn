package com.ziroom.minsu.services.search.del;

import base.BaseTest;
import com.ziroom.minsu.services.solr.index.SolrCore;
import com.ziroom.minsu.services.solr.server.SolrServerWapper;
import org.junit.Test;

/**
 * <p>删除索引的测试</p>
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
public class DelIndexTest extends BaseTest{



    @Test
    public void TestTest(){

        System.out.println("test");
    }


    @Test
    public final void  TestdeleteBySolrId() throws Exception{
        SolrServerWapper server = SolrServerWapper.getSolrServer(SolrCore.m_spell);

        server.deleteByQuery("*:*");
    }

    @Test
    public final void  TestdeleteByQuery() throws Exception{
        SolrServerWapper server = SolrServerWapper.getSolrServer(SolrCore.m_house_info);

        server.deleteByQuery("*:*");
    }

    @Test
    public final void  TestdeleteByQuery2() throws Exception{
        SolrServerWapper server = SolrServerWapper.getSolrServer(SolrCore.m_suggest);

        server.deleteByQuery("*:*");
    }
}
