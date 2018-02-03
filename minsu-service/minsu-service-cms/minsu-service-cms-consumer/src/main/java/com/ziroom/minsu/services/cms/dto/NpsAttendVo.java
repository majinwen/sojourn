package com.ziroom.minsu.services.cms.dto;

import com.ziroom.minsu.entity.cms.NpsAttendEntiy;

import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/11/14 15:16
 * @version 1.0
 * @since 1.0
 */
public class NpsAttendVo extends NpsAttendEntiy {

    private static final long serialVersionUID = -5544177057793705331L;


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
