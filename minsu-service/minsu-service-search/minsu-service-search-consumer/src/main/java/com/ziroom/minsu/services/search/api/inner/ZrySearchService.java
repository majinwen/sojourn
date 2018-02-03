package com.ziroom.minsu.services.search.api.inner;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月25日 15:02
 * @since 1.0
 */
public interface ZrySearchService {


    /**
     *  当projectBid不为空时表示mq单个刷新，否则就是全量刷新
     * @author zl
     * @created 2017/7/28 10:02
     * @param
     * @return 
     */
    String freshIndex(String projectBid);


    /**
     * 根据条件搜索,没有搜到时推荐
     * @author zl
     * @created 2017/7/28 10:05
     * @param
     * @return 
     */
    String getHouseListByConditionAndRecommend(String paramsJson);




}
