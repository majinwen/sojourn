package com.ziroom.minsu.services.customer.entity;

import com.ziroom.minsu.entity.customer.CustomerBaseMsgEntity;
import com.ziroom.minsu.entity.customer.CustomerPicMsgEntity;

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
 * @author wangwt
 * @version 1.0
 * @Date Created in 2017年06月20日 19:46
 * @since 1.0
 */
public class IdentitySaveVo extends CustomerBaseMsgEntity{

    private static final long serialVersionUID = 7184020553882551686L;

    /*
     * 图片list
     */
    private List<CustomerPicMsgEntity> picList;

    public List<CustomerPicMsgEntity> getPicList() {
        return picList;
    }

    public void setPicList(List<CustomerPicMsgEntity> picList) {
        this.picList = picList;
    }


}
