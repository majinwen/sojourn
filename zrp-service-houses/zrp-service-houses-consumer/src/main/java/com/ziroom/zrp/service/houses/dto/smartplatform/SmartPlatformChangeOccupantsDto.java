package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>更换入住人信息</p>
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
 * @Date Created in 2017年12月12日
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformChangeOccupantsDto extends BaseEntity {

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 出房合同号
     */
    private String rentContractCode;

    /**
     * 用户uid
     * 用户唯一表示
     */
    private String uid;

    /**
     * 用户姓名
     * 密码使用者的名字
     */
    private String userName;

    /**
     * 用户电话
     * 密码使用者电话号码
     */
    private String userPhone;


}
