package com.ziroom.minsu.services.cms.dto;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/11/11 15:41
 * @version 1.0
 * @since 1.0
 */
public class NpsAttendRequest extends NpsGetRequest{

    private static final long serialVersionUID = 9283492130892743L;

    /**
     * 分数
     */
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
