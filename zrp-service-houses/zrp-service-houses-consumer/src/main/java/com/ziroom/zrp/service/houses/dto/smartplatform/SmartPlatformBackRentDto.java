package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>退租</p>
 * wiki-http://wiki.ziroom.com/pages/viewpage.action?pageId=337674251
 * <p>
 * <PRE>
 * <BR>    修改记录 
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zhangyl2
 * @Date Created in 2017年12月12日
 * @version 1.0
 * @since 1.0
 */
@Data
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class SmartPlatformBackRentDto extends BaseEntity {

    /**
     * 出房合同
     */
    private String rentContractCode;

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 房屋类型
     * 1.友家 4.自如寓
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

}
