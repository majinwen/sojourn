package com.ziroom.minsu.services.evaluate.entity;

import java.io.Serializable;

/**
 * <p>两次评价列表展示，展示房东房客互评，并且房东头像</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @since 1.0
 * @version 1.0
 */
public class EvaluateHouseItemVo implements Serializable{

    private static final long serialVersionUID = -8103676203147309661L;
    /**
     * 房客名称
     */
    public String tenName;
    /**
     * 房客头像
     */
    public String tenPic;
    /**
     *
     */
    public String tenUid;
    /**
     * 初见评价内容
     */
    public String tenCjContent;
    /**
     * 评价内容
     */
    public String tenContent;
    /**
     * 评价时间
     */
    public String evaTime;

    /**
     * 房东名称
     */
    public String lanName;
    /**
     * 房东头像
     */
    public String lanPic;
    /**
     * 房东UID
     */
    public String lanUid;
    /**
     * 房东初见评价
     */
    public String lanCjContent;
    /**
     * 房东评价
     */
    public String lanContent;

    public String getTenName() {
        return tenName;
    }

    public void setTenName(String tenName) {
        this.tenName = tenName;
    }

    public String getTenPic() {
        return tenPic;
    }

    public void setTenPic(String tenPic) {
        this.tenPic = tenPic;
    }

    public String getTenUid() {
        return tenUid;
    }

    public void setTenUid(String tenUid) {
        this.tenUid = tenUid;
    }

    public String getTenCjContent() {
        return tenCjContent;
    }

    public void setTenCjContent(String tenCjContent) {
        this.tenCjContent = tenCjContent;
    }

    public String getTenContent() {
        return tenContent;
    }

    public void setTenContent(String tenContent) {
        this.tenContent = tenContent;
    }

    public String getEvaTime() {
        return evaTime;
    }

    public void setEvaTime(String evaTime) {
        this.evaTime = evaTime;
    }

    public String getLanName() {
        return lanName;
    }

    public void setLanName(String lanName) {
        this.lanName = lanName;
    }

    public String getLanPic() {
        return lanPic;
    }

    public void setLanPic(String lanPic) {
        this.lanPic = lanPic;
    }

    public String getLanUid() {
        return lanUid;
    }

    public void setLanUid(String lanUid) {
        this.lanUid = lanUid;
    }

    public String getLanCjContent() {
        return lanCjContent;
    }

    public void setLanCjContent(String lanCjContent) {
        this.lanCjContent = lanCjContent;
    }

    public String getLanContent() {
        return lanContent;
    }

    public void setLanContent(String lanContent) {
        this.lanContent = lanContent;
    }
}
