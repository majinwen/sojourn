package com.ziroom.minsu.services.basedata.entity;

import com.ziroom.minsu.entity.conf.HotRegionEntity;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/16
 */
public class RegionExtVo extends HotRegionEntity {

    /** 景点商圈表中的描述*/
    private String hotRegionBrief;

    /** 景点商圈内容*/
    private String hotRegionContent;

    public String getHotRegionBrief() {
        return hotRegionBrief;
    }

    public void setHotRegionBrief(String hotRegionBrief) {
        this.hotRegionBrief = hotRegionBrief;
    }

    public String getHotRegionContent() {
        return hotRegionContent;
    }

    public void setHotRegionContent(String hotRegionContent) {
        this.hotRegionContent = hotRegionContent;
    }
}
