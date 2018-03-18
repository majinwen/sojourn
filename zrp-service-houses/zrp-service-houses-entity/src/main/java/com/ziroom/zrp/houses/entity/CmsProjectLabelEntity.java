package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>营销配置项目标签信息类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2018年3月5日
 * @version 1.0
 * @since 1.0
 */
@Data
public class CmsProjectLabelEntity extends BaseEntity{
    private String fid;
    private String projectId; // 项目标识
    private String moduleName; // 标签名称
    private String moduleOrder; // 标签顺序
    private Integer isDel;
    private Integer isValid;
    private Date createTime;
    private Date updateTime;
    private String createId;
    private String updateId;
}
