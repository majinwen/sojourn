package com.ziroom.minsu.services.common.utils.test;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.util.DateUtil;

import java.util.Date;

/**
 * <p>TODO</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public class Test extends BaseEntity {

    private static final long serialVersionUID = 30968498946703L;

    private String aa;


    private String bb;


    public String getAa() {
        return aa;
    }

    public void setAa(String aa) {
        this.aa = aa;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }


    public  static  void main(String[] args){


        Date limitTime = DateUtil.intervalDate(-Math.abs(720), DateUtil.IntervalUnit.MINUTE);

        System.out.println(limitTime);
        System.out.println(DateUtil.dateFormat(limitTime, "yyyy-MM-dd HH:mm:ss"));

    }
}
