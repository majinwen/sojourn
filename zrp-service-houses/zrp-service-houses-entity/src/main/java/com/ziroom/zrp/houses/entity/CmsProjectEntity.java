package com.ziroom.zrp.houses.entity;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>TODO</p>
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
public class CmsProjectEntity extends BaseEntity{
    private String fid;
    private String projectId; // 项目标识
    private String showImg; // 风采展示图片链接
    private String panoramicUrl; // 全景看房链接
    private String peripheralUrl; // 周边链接
    private String shareUrl; // 分享链接
    private String headImg; // 项目头图
    private String zspaceDesc; // zspace介绍
    private String zoDesc; // zo专属介绍
    private String zoImgUrl; // zo图片地址
    private String zoServiceDesc; // zo专属服务介绍
    private Integer isDel;
    private Integer isValid;
    private Date createTime;
    private Date updateTime;
    private String createId;
    private String updateId;
}
