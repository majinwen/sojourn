package com.ziroom.minsu.api.order.entity.base;

import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年08月01日 11:47
 * @since 1.0
 */
public class MoneySubListItem extends MoneyDescTipsMsgItem {

    private String subTitle;

    private List<Map<String, Object>> subList;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<Map<String, Object>> getSubList() {
        return subList;
    }

    public void setSubList(List<Map<String, Object>> subList) {
        this.subList = subList;
    }
}
