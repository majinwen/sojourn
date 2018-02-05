package com.ziroom.minsu.api.customer.entity;

import com.asura.framework.base.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/7.
 * @version 1.0
 * @since 1.0
 */
public class BankInfoVo extends BankParVo {

    /** 序列化ID */
    private static final long serialVersionUID = 77432580957371L;


    private String title = "收款信息";


    private List<Map<String,String>> bankList;


    private List<Map<String,String>> cityList;



    public List<Map<String, String>> getBankList() {
        return bankList;
    }

    public void setBankList(List<Map<String, String>> bankList) {
        this.bankList = bankList;
    }

    public List<Map<String, String>> getCityList() {
        return cityList;
    }

    public void setCityList(List<Map<String, String>> cityList) {
        this.cityList = cityList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
