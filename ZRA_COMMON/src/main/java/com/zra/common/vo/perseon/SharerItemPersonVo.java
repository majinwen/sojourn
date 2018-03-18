package com.zra.common.vo.perseon;

import com.zra.common.vo.base.BaseFieldVo;
import com.zra.common.vo.base.BaseItemValueVo;

/**
 * <p>合住人信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年9月14日
 * @since 1.0
 */
public class SharerItemPersonVo {

    /**
     * 合住人id
     */
    private String fid;
    /**
     * 姓名
     */
    private String name;
    /**
     * 证件信息
     */
    private BaseItemValueVo certInfo;
    /**
     * 电话信息
     */
    private String phone;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseItemValueVo getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(BaseItemValueVo certInfo) {
        this.certInfo = certInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
