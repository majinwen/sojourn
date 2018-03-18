package com.ziroom.zrp.service.trading.pojo;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.trading.entity.SharerEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>入住人和合住人信息</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月07日
 * @version 1.0
 * @since 1.0
 */
@Data
public class PersonAndSharerPojo extends BaseEntity {

    /**
     * 入住人主键
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新操作人
     */
    private String updateName;

    /**
     * 合住人
     */
	private List<SharerEntity> sharerEntities;
}
