package com.ziroom.minsu.services.solr.index;

import com.ziroom.minsu.services.solr.query.parser.QueryResultParser;
import com.ziroom.minsu.services.solr.query.parser.support.*;
import com.ziroom.minsu.services.solr.query.transfer.QueryParameterTransfer;
import com.ziroom.minsu.services.solr.query.transfer.support.*;

/**
 * <p>索引的类型</p>
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
public enum SolrCore {




    /**
     * 测试
     */
    m_suggest("m_suggest","solr.url","用户联想"){
        @Override
        public QueryParameterTransfer getTransfer() {
            return new SuggestInfoQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new CommonQueryResultParser();
        }
    },
    m_spell("m_spell","solr.url","楼盘信息"){
        @Override
        public QueryParameterTransfer getTransfer() {
            return new CommunityInfoQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new CommonQueryResultParser();
        }
    },
    m_house_info("m_house_info","solr.url","民宿房源索引"){

        @Override
        public QueryParameterTransfer getTransfer() {
            return new HouseInfoQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new HouseInfoQueryResultParser();
        }
    },
    complate("m_house_info","solr.url","民宿房源索引"){
        @Override
        public QueryParameterTransfer getTransfer() {
            return new HouseInfoQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new HouseInfoQueryResultParser();
        }
    },
    zry_house_info("m_house_info","solr.url","自如驿索引"){

        @Override
        public QueryParameterTransfer getTransfer() {
            return new ZryHouseInfoQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new ZryHouseInfoQueryResultParser();
        }
    },
    cms_article_info("cms_article_info","solr.cms.url","cms文章索引"){

        @Override
        public QueryParameterTransfer getTransfer() {
            return new CmsQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new CmsArticleQueryResultParser();
        }
    },
    cms_article_info_detail("cms_article_info","solr.cms.url","cms文章索引"){

        @Override
        public QueryParameterTransfer getTransfer() {
            return new CmsQueryParameterTransfer();
        }

        @Override
        public QueryResultParser getParser() {
            return new CmsArticleDetailResultParser();
        }
    };


    private SolrCore(String solrSeverCode,String solrSeverUrl,String name){
        this.solrSeverCode = solrSeverCode;
        this.solrSeverUrl = solrSeverUrl;
        this.showName = name;
    }

    private String solrSeverUrl;

    /**
     * 读取的solr服务信息
     */
    private String solrSeverCode;

    /**
     * 描述
     */
    private String showName;

    public String getShowName() {
        return showName;
    }


    public String getSolrSeverCode() {
        return solrSeverCode;
    }

    public String getSolrSeverUrl() {
        return solrSeverUrl;
    }

    abstract public QueryParameterTransfer getTransfer();

    abstract public QueryResultParser getParser();

}
