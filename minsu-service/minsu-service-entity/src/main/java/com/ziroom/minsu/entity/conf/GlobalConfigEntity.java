package com.ziroom.minsu.entity.conf;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>全局的设置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/8.
 * @version 1.0
 * @since 1.0
 */
public class GlobalConfigEntity extends BaseEntity {


    private static final long serialVersionUID = 456414564564703L;

    /**未支付的最大数量 */
    private Integer maxNoPayNumber;


    public Integer getMaxNoPayNumber() {
        return maxNoPayNumber;
    }

    public void setMaxNoPayNumber(Integer maxNoPayNumber) {
        this.maxNoPayNumber = maxNoPayNumber;
    }
}
