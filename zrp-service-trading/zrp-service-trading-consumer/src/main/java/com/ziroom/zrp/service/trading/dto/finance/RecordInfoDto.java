package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月10日 14时26分
 * @Version 1.0
 * @Since 1.0
 */
public class RecordInfoDto {
    /**
     * urgeTime : 2017-08-16 12:27:15
     * followName : 李强
     * urgeDesc : 客户无法还款，自动扣款失败，手动还款失败，已经反馈技术
     * curPlanNum : 3
     * limitPayDate : 2017-08-16
     * urgeAction : 跟踪技术
     * debtReason : 自如系统原因
     * urlList : [{"name":"2e1726c0823b11e74ba6465daaeb758160000511","url":"http://image.ziroom.com/g2/M00/55/02/ChAFfVmTyh-ATboFAADJsGM7qz0066.jpg"},{"name":"2e1726c1823b11e74ba6465daaeb758160000511","url":"http://image.ziroom.com/g2/M00/55/02/ChAFD1mTye2AIFs-AACxxVcHVfY090.jpg"},{"name":"2e1726c2823b11e74ba6465daaeb758160000511","url":"http://image.ziroom.com/g2/M00/55/02/ChAFfVmTyh-AWtUYAACyAMkUwaE262.jpg"}]
     */

    private String urgeTime;
    private String followName;
    private String urgeDesc;
    private String curPlanNum;
    private String limitPayDate;
    private String urgeAction;
    private String debtReason;
    private List<UrlListBean> urlList;

    public String getUrgeTime() {
        return urgeTime;
    }

    public void setUrgeTime(String urgeTime) {
        this.urgeTime = urgeTime;
    }

    public String getFollowName() {
        return followName;
    }

    public void setFollowName(String followName) {
        this.followName = followName;
    }

    public String getUrgeDesc() {
        return urgeDesc;
    }

    public void setUrgeDesc(String urgeDesc) {
        this.urgeDesc = urgeDesc;
    }

    public String getCurPlanNum() {
        return curPlanNum;
    }

    public void setCurPlanNum(String curPlanNum) {
        this.curPlanNum = curPlanNum;
    }

    public String getLimitPayDate() {
        return limitPayDate;
    }

    public void setLimitPayDate(String limitPayDate) {
        this.limitPayDate = limitPayDate;
    }

    public String getUrgeAction() {
        return urgeAction;
    }

    public void setUrgeAction(String urgeAction) {
        this.urgeAction = urgeAction;
    }

    public String getDebtReason() {
        return debtReason;
    }

    public void setDebtReason(String debtReason) {
        this.debtReason = debtReason;
    }

    public List<UrlListBean> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<UrlListBean> urlList) {
        this.urlList = urlList;
    }

    public static class UrlListBean {
        /**
         * name : 2e1726c0823b11e74ba6465daaeb758160000511
         * url : http://image.ziroom.com/g2/M00/55/02/ChAFfVmTyh-ATboFAADJsGM7qz0066.jpg
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
