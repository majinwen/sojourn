package com.ziroom.minsu.services.solr.common;

import java.util.List;


/**
 * <p>分词的处理</p>
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
public interface IKAnalyzerService {

    /**
     * 分词
     * @author afi
     * @param txt
     * @param useSmart
     * @return
     */
    List<String> ikAnalysis(String txt,Boolean useSmart);
}
