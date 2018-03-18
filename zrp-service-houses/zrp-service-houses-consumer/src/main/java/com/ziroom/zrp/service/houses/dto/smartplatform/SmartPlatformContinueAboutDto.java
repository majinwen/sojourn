package com.ziroom.zrp.service.houses.dto.smartplatform;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.zrp.service.houses.valenum.HouseTypeEnum;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * <p>续约</p>
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
public class SmartPlatformContinueAboutDto extends BaseEntity {

    /**
     * 出房合同号
     */
    private String rentContractCode;

    /**
     * 收房合同/项目id
     */
    private String hireContractCode;

    /**
     * 老出房合同
     */
    private String logRentContractCode;

    /**
     * 新出房合同/生效日期
     */
    private String rentContractStartDate;

    /**
     * 新出房合同/失效日期
     */
    private String rentContractEndDate;

    /**
     * 房屋类型
     */
    private Integer houseType = HouseTypeEnum.ZYU.getCode();

}
