package com.ziroom.zrp.service.trading.entity;

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
 * @Date 2017年10月12日 15时04分
 * @Version 1.0
 * @Since 1.0
 */
public class SurrenderCostSumVo {
    private List<SurrenderCostSumHeadVo> surrenderCostSumHeadVoList;
    private String totalHave;
    private String totalMust;
    private String totalRefund;
    private SurrenderCostSumBodyVo surrenderCostSumBodyVo;

    public String getTotalHave() {
        return totalHave;
    }

    public void setTotalHave(String totalHave) {
        this.totalHave = totalHave;
    }

    public String getTotalMust() {
        return totalMust;
    }

    public void setTotalMust(String totalMust) {
        this.totalMust = totalMust;
    }

    public String getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(String totalRefund) {
        this.totalRefund = totalRefund;
    }

    public List<SurrenderCostSumHeadVo> getSurrenderCostSumHeadVoList() {
        return surrenderCostSumHeadVoList;
    }

    public void setSurrenderCostSumHeadVoList(List<SurrenderCostSumHeadVo> surrenderCostSumHeadVoList) {
        this.surrenderCostSumHeadVoList = surrenderCostSumHeadVoList;
    }

    public SurrenderCostSumBodyVo getSurrenderCostSumBodyVo() {
        return surrenderCostSumBodyVo;
    }

    public void setSurrenderCostSumBodyVo(SurrenderCostSumBodyVo surrenderCostSumBodyVo) {
        this.surrenderCostSumBodyVo = surrenderCostSumBodyVo;
    }
}
