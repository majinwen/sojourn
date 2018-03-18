package com.zra.business.entity.dto;

import java.util.Date;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/5 14:14
 * @since 1.0
 */
public class BusinessDetailDto {

    /**
     * 业务id
     */
    private String bid;

    /**
     * 商机状态
     */
    private Byte step;
    /**
     * 商机状态名称
     */
    private Byte stepName;
    /**
     * 提交时间
     */
    private String createTime;
    /**
     *  期望约看时间
     */
    private String expectTime;
    /**
     * 约看时间
     */
    private String resultTime;
    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 房型id
     */
    private String houseTypeId;

    /**
     * 地址
     */
    private String address;
    /**
     * zo管家bid
     */
    private String zoId;

    /**
     * zo管家姓名
     */
    private String zoName;

    /**
     * 商机客户id
     */
    private String customerId;

    /**
     * 评价的tokenId
     */
    private String evaluateTokenId;

    /**
     * 是否评价
     */
    private Integer isEvaluate = 0;

}
