package com.ziroom.minsu.services.solr.constant;

/**
 * <p>搜索的一些常亮</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/29.
 * @version 1.0
 * @since 1.0
 */
public class SolrConstant {


    /**
     * 批量更新索引的长度
     * TODO 这个暂时写死
     */
    public static int solr_page_limit = 1000;

    /**
     * 推荐的长度
     */
    public static int suggest_page_limit = 10;

    /**
     * 创建索引的查询sql的分页信息的长度
     */
    public static int create_index_page_limit = 100;


    public static String FACET_SORT = "count";


    public static String PT = "pt";

    public static String S_FIELD = "sfield";

    public static String SORT = "sort";

    public static String SPATIAL = "spatial";

    public static String GEODIST = "geodist()";


    public static final String picSize = "{picSize}";

    public static final String iconPath = "{iconPath}";


    //不限制的最大入住人数
    public static final int default_person_count = 99;


    //不限制的最大入住人数 展示
    public static final int default_person_count_show = 0;


}
