package com.zra.repair.entity.dto;

import com.zra.house.entity.dto.PageDto;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author fengbo.yue
 * @Date Created in 2017年09月30日 09:53
 * @since 1.0
 * @version 1.0
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZryRepairOrderPageDto extends PageDto implements Serializable {

    // 项目fid
    private String itemFid;
    // 当前页
    private Integer pageNum;
    // 每页数量
    private Integer pageSize;
    // 维修单号
    private String orderSn;
    // 维修房间号
    private String roomNum;
    // 报修人
    private String repairsMan;
    // 区域类型
    private Integer areaType;
    // 大类编号
    private String categoryCode;
    // 业务类型
    private String businessType;
    // 维修单状态
    private Integer orderStatus;
    // 上门联系人
    private String visitLinkman;
    // 上门联系人电话
    private String visitMobile;
    // 物品编号
    private String goodsCode;
    // 区域编号
    private String areaCode;
    // 创建时间
    private Date createDate;
    private Timestamp start;
    private Timestamp end;
    
    //同步条件
    private Integer isSync;
    /**
     * 城市编码
     */
    private String cityCode;

}
