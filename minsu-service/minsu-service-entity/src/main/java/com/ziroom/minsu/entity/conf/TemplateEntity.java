package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

import java.util.Date;

/**
 * <p>模板表</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/19.
 * @version 1.0
 * @since 1.0
 */
public class TemplateEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;


    private Integer id;

    /** 父fid */
    private String pfid;

    /** 业务编号 */
    private String fid;

    /** 名称 */
    private String templateName;

    /** 模板状态 */
    private Integer templateStatus;
    /**
     * 业务线类型：1:民宿 2：公寓 3：驿站
     */
    private Integer templateLine;

    /** 创建时间 */
    private Date createDate;

    /** 修改时间 */
    private Date lastModifyDate;

    /** 创建人 */
    private String createFid;

    /** 是否删除 1：已经删除 0：未删除 */
    private Integer isDel;


    public String getPfid() {
        return pfid;
    }

    public void setPfid(String pfid) {
        this.pfid = pfid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(Integer templateStatus) {
        this.templateStatus = templateStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreateFid() {
        return createFid;
    }

    public void setCreateFid(String createFid) {
        this.createFid = createFid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getTemplateLine() {
        return templateLine;
    }

    public void setTemplateLine(Integer templateLine) {
        this.templateLine = templateLine;
    }
}
