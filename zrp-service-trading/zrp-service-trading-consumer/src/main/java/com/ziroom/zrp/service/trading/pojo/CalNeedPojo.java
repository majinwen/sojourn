package com.ziroom.zrp.service.trading.pojo;

import com.ziroom.zrp.trading.entity.RentContractEntity;

import java.math.BigDecimal;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangxm113
 * @version 1.0
 * @Date Created in 2017年07月27日 10:51
 * @since 1.0
 */
public class CalNeedPojo {
    //已缴房租
    private BigDecimal havePayFZ = BigDecimal.ZERO;
    //已缴服务费
    private BigDecimal havePayFWF = BigDecimal.ZERO;
    //违约责任方(0:公司；1:租客)
    private int resNo;
    //是否参加过其灵海燕计划，其灵海燕计划特殊处理 减免最后一个月房租
    private boolean havePlanOfHaiYanOfQiLing;
    //合同信息
    private RentContractEntity contract;
    //查询信息pojo
    private CalSurrenderPojo searchPojo;

    public BigDecimal getHavePayFZ() {
        return havePayFZ;
    }

    public void setHavePayFZ(BigDecimal havePayFZ) {
        this.havePayFZ = havePayFZ;
    }

    public BigDecimal getHavePayFWF() {
        return havePayFWF;
    }

    public void setHavePayFWF(BigDecimal havePayFWF) {
        this.havePayFWF = havePayFWF;
    }

    public int getResNo() {
        return resNo;
    }

    public void setResNo(int resNo) {
        this.resNo = resNo;
    }

    public boolean isHavePlanOfHaiYanOfQiLing() {
        return havePlanOfHaiYanOfQiLing;
    }

    public void setHavePlanOfHaiYanOfQiLing(boolean havePlanOfHaiYanOfQiLing) {
        this.havePlanOfHaiYanOfQiLing = havePlanOfHaiYanOfQiLing;
    }

    public RentContractEntity getContract() {
        return contract;
    }

    public void setContract(RentContractEntity contract) {
        this.contract = contract;
    }

    public CalSurrenderPojo getSearchPojo() {
        return searchPojo;
    }

    public void setSearchPojo(CalSurrenderPojo searchPojo) {
        this.searchPojo = searchPojo;
    }
}
