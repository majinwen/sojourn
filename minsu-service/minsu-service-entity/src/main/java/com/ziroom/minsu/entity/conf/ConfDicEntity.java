package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.Check;

import java.util.Date;

/**
 * <p>配置字典表</p>
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
public class ConfDicEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -7144472568025171L;

    private Integer id;

    /** 编码 */
    private String fid;

    /** 展示名称*/
    private String showName;

    /** code */
    private String dicCode;

    /**
     * 字典排序
     */
    private Integer dicIndex;

    /** 父id */
    private String pfid;

    /** 结构层级 */
    private Integer dicLevel;
    /**
     * 业务线类型：1：民宿 2：公寓 3：驿站
     */
    private Integer dicLine;

    /** 创建时间  */
    private Date createDate;

    /** 最后一次修改时间 */
    private Date lastModifyDate;

    /** 创建人fid */
    private String createFid;

    /** 0：非叶子节点1：唯一值2：枚举3：列表 */
    private Integer dicType;

    /** 是否删除 0：未删除 1：已经删除 */
    private Integer isDel;

    /** 是否可编辑 0：不编辑 1：可编辑 */
    private Integer isEdit = 1;


    public Integer getIsEdit() {
        return isEdit;
    }



    public void setIsEdit(Integer isEdit) {
        this.isEdit = isEdit;
    }

    public Integer getDicIndex() {
        return dicIndex;
    }

    public void setDicIndex(Integer dicIndex) {
        this.dicIndex = dicIndex;
    }

    public Integer getDicLevel() {
        return dicLevel;
    }

    public void setDicLevel(Integer dicLevel) {
        this.dicLevel = dicLevel;
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

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getPfid() {
        return pfid;
    }

    public void setPfid(String pfid) {
        this.pfid = pfid;
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

	public Integer getDicType() {
		return dicType;
	}

	public void setDicType(Integer dicType) {
		this.dicType = dicType;
	}

    public Integer getDicLine() {
        return dicLine;
    }

    public void setDicLine(Integer dicLine) {
        this.dicLine = dicLine;
    }
}
