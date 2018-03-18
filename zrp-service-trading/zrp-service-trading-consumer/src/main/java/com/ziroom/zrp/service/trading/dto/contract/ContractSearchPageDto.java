package com.ziroom.zrp.service.trading.dto.contract;

import com.asura.framework.base.entity.BaseEntity;
import com.zra.common.dto.base.BasePageParamDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>合同服务搜索参数</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月27日 15:36
 * @since 1.0
 */
@Data
public class ContractSearchPageDto extends BaseEntity{
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 管家所属项目
     */
    private List<String> projectIds;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 出房合同号
     */
    private String conRentCode;
    /**
     * 房间床位号
     */
    private String houseRoomNo;
    /**
     * 客户手机号
     */
    private String customerMobile;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 1-年租 2-月租 3-日租 @see com.ziroom.zrp.service.trading.valenum.LeaseCycleEnum
     */
    private String conType;
    /**
     * 合同状态 @See com.ziroom.zrp.service.trading.valenum.ContractStatusEnum
     */
    private String conStatusCode;

    /**
     * 审核状态：0:待审核；1：审核驳回；2：审核通过 @see com.ziroom.zrp.service.trading.valenum.ContractAuditStatusEnum
     */
    private String conAuditState;
    /**
     * 合同生效日期：起租日期
     */
    private String signStartDate;

    /**
     * 合同截止日期：到期日期
     */
    private String signEndDate;
    /**
     * 客户类型
     */
    private Integer customerType;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 物业交割状态 0 未交割 1已交割
     */
    private Integer deliveryState;
    /**
     * 主合同号
     */
    private String surParentRentCode;
    /**
     * 合同类型
     */
    private String source;

    private Integer page;
    /**
     * 每页显示条数
     */
    private Integer rows;
    /**
     * 0=合同管理列表  1=导出
     */
    private Integer oper;

}
