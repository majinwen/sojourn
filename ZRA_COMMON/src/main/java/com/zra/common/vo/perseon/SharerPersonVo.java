package com.zra.common.vo.perseon;

import com.zra.common.vo.base.BaseItemSelectVo;

import java.util.List;

/**
 * <p>合租人详情</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年09月18日 10:43
 * @since 1.0
 */
public class SharerPersonVo {
    /**
     * 合租人fid
     */
    private String fid;

    /**
     * 真实姓名
     */
    private String name;
    /**
     * 证件类型
     */
    private List<BaseItemSelectVo> certType;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 电话号码
     */
    private String phone;

}
