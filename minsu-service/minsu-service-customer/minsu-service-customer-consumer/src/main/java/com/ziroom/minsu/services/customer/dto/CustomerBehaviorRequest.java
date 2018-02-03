package com.ziroom.minsu.services.customer.dto;

import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.valenum.customer.CustomerBehaviorCreateTypeEnum;

/**
 * <p>用户行为查询</p>
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年10月12日
 * @version 1.0
 * @since 1.0
 */
public class CustomerBehaviorRequest extends PageRequest{


    private static final long serialVersionUID = -6878461576161385664L;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 关联的具体行为的当事表fid
     */
    private String proveFid;

    /**
     * 行为类型
     */
    private Integer type;

    /**
     * 创建类型(1-系统生成 2-人工录入)
     * @see CustomerBehaviorCreateTypeEnum
     */
    private Integer createType;

    /**
     * 开始结束时间
     */
    private String startTime;

    private String endTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProveFid() {
        return proveFid;
    }

    public void setProveFid(String proveFid) {
        this.proveFid = proveFid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateType() {
        return createType;
    }

    public void setCreateType(Integer createType) {
        this.createType = createType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
