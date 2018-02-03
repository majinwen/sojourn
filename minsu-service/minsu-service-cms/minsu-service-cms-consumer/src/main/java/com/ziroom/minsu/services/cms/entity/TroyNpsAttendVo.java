package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.NpsAttendEntiy;

import java.util.Date;

/**
 * <p>查新的展示逻辑</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/11/12.
 * @version 1.0
 * @since 1.0
 */
public class TroyNpsAttendVo extends NpsAttendEntiy{


    private static final long serialVersionUID = 309682342001446703L;


    /**
     * 调查编号
     */
    private String npsCode;

    /**
     * 调查名称
     */
    private String npsName;

    /**
     * 调查内容
     */
    private String npsContent;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


    @Override
    public String getNpsCode() {
        return npsCode;
    }

    @Override
    public void setNpsCode(String npsCode) {
        this.npsCode = npsCode;
    }

    public String getNpsName() {
        return npsName;
    }

    public void setNpsName(String npsName) {
        this.npsName = npsName;
    }

    public String getNpsContent() {
        return npsContent;
    }

    public void setNpsContent(String npsContent) {
        this.npsContent = npsContent;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
