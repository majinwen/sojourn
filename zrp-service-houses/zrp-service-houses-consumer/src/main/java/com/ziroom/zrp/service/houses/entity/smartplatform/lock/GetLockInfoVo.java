package com.ziroom.zrp.service.houses.entity.smartplatform.lock;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>获取智能锁设备状态</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @version 1.0
 * @Date Created in 2018年01月15日
 * @since 1.0
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GetLockInfoVo extends BaseEntity {

    /**
     * 门锁在线状态：1：在线2：离线
     */
    private Integer onoffLine;

    /**
     * 门锁的电量信息，-1表示未知，0-100
     */
    private Integer power;

}
