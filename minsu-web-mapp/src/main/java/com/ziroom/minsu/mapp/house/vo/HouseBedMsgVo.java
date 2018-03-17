package com.ziroom.minsu.mapp.house.vo;

import com.ziroom.minsu.entity.house.HouseBedMsgEntity;

/**
 * <p>床的视图</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/5/28.
 * @version 1.0
 * @since 1.0
 */
public class HouseBedMsgVo extends HouseBedMsgEntity {

    private static final long serialVersionUID = -4490032497990014968L;


    /**
     * 床类型描述
     */
    private String bedTypeDis;

    /**
     * 床大小描述
     */
    private String bedSizeDis;


    public String getBedTypeDis() {
        return bedTypeDis;
    }

    public void setBedTypeDis(String bedTypeDis) {
        this.bedTypeDis = bedTypeDis;
    }

    public String getBedSizeDis() {
        return bedSizeDis;
    }

    public void setBedSizeDis(String bedSizeDis) {
        this.bedSizeDis = bedSizeDis;
    }
}
