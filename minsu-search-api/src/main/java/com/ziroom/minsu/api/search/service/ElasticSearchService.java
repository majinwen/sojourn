package com.ziroom.minsu.api.search.service;

import java.net.InetSocketAddress;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.thread.pool.SynLocationThreadPool;
import com.ziroom.minsu.services.search.dto.HouseInfoRequest;

import javax.annotation.PostConstruct;

/**
 * <p>ElasticSearch</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhangyl
 * @version 1.0
 * @since 1.0
 */
@Service("query.elasticSearchService")
public class ElasticSearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchService.class);

    @Value("#{'${elasticsearch.client.address}'.trim()}")
    private String esAddress;

    @Value("#{'${elasticsearch.cluster.name}'.trim()}")
    private String esClusterName;

    @Value("#{'${elasticsearch.minsu.search.index}'.trim()}")
    private String minsuSearchIndex;

    @Value("#{'${elasticsearch.minsu.search.type}'.trim()}")
    private String minsuSearchType;

    private TransportClient esClient;

    @PostConstruct
    private void init() {
        try {
            // 设置集群名称与自动嗅探
            Settings settings = Settings.builder().put("cluster.name", esClusterName).put("client.transport.sniff", true).build();

            // 初始化client
            String[] addressArray = esAddress.split(":");

            esClient = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(addressArray[0], Integer.parseInt(addressArray[1]))));
        } catch (Exception e) {
            LogUtil.error(LOGGER, "ElasticSearch getEsClient error！esAddress:{},esClusterName:{},e:{}", esAddress, esClusterName, e);
        }
    }

    /**
     * 索引一个搜索数据
     *
     * @param request
     */
    public void generateIndex(HouseInfoRequest houseInfoRequest) {

        Thread task = new Thread() {
            @Override
            public void run() {

                try {
                    if (Check.NuNObj(esClient)) {
                        init();
                    }

                    if (Check.NuNObj(esClient)) {
                        LogUtil.error(LOGGER, "ElasticSearch generateIndex error！TransportClient is null");
                    } else {
                        IndexResponse response = esClient.prepareIndex(minsuSearchIndex, minsuSearchType)
                                .setSource(JsonEntityTransform.Object2Json(houseInfoRequest), XContentType.JSON)
                                .get();

                        LogUtil.info(LOGGER, "ElasticSearch generateIndex minsuSearchIndex:{},minsuSearchType:{},response:{}", minsuSearchIndex, minsuSearchType, response);
                    }

                } catch (Exception e) {
                    LogUtil.error(LOGGER, "e:{}", e);
                }
            }
        };

        SynLocationThreadPool.execute(task);
    }

}
