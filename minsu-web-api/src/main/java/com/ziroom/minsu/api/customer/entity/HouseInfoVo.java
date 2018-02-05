package com.ziroom.minsu.api.customer.entity;

import com.ziroom.minsu.services.search.entity.HouseInfoEntity;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>收藏房源信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl
 * @Date Created in 2017/9/19 18:28
 * @version 1.0
 * @since 1.0
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class HouseInfoVo extends HouseInfoEntity {


    private static final long serialVersionUID = -1574327420708519587L;

    /**
     * 房源状态(40:上架, 41:下架)
     */
    private Integer houseStatus;

    public Integer getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(Integer houseStatus) {
        this.houseStatus = houseStatus;
    }
}
