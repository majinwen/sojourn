package com.ziroom.minsu.entity.base;

import com.asura.framework.base.entity.BaseEntity;

/**
 * <p>民宿的展示对象</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/9/8.
 * @version 1.0
 * @since 1.0
 */
public class MinsuShowEntity extends BaseEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -5456234234423L;

    //显示
    private String showName = "";

    //名称
    private String name = "";

    //值
    private String code = "";

    //值
    private Integer status = 0;

    public MinsuShowEntity(String showName) {
        this.showName = showName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
