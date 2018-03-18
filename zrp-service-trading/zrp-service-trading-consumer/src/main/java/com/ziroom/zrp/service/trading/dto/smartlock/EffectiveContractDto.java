package com.ziroom.zrp.service.trading.dto.smartlock;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @version 1.0
 * @Date Created in 2017年12月08日 17:49
 * @since 1.0
 */
@Data
@ApiModel(value = "返回合同列表对象")  
public class EffectiveContractDto {
	@ApiModelProperty(value = "城市编码", required = true)
	private String cityCode; // 城市编码
	@ApiModelProperty(value = "城市名称", required = true)
	private String cityName; // 城市名称
	@ApiModelProperty(value = "项目标识", required = true)
	private String projectId; //项目标识
	@ApiModelProperty(value = "项目名称", required = true)
	private String projectName; // 项目名称
	@ApiModelProperty(value = "房间标识", required = true)
	private String roomId; // 房间标识
	@ApiModelProperty(value = "房间号", required = true)
	private String roomCode; // 房间号
	@ApiModelProperty(value = "合同号", required = true)
	private String contractCode; // 合同号
	@ApiModelProperty(value = "客户姓名", required = true)
	private String customerName;// 客户姓名
	@ApiModelProperty(value = "客户手机号", required = true)
	private String customerMobile;// 客户手机号
	@ApiModelProperty(value = "业务线", required = true)
	private String busiLine;// 业务线
	@ApiModelProperty(value = "是否智能锁 0：否，1：是", required = true)
	private Integer isSmartLock;//是否智能锁 0：否，1：是
	@ApiModelProperty(value = "是否智能水表 0：否，1：是", required = true)
	private Integer isSmartWatermeter;//是否智能水表 0：否，1：是
	@ApiModelProperty(value = "是否智能电表 0：否，1：是", required = true)
	private Integer isSmartWattmeter;//是否智能电表 0：否，1：是
	@ApiModelProperty(value = "客户类型：1 普通个人客户 2 企悦会员工 3 企业客户", required = true)
	private Integer customerType;//客户类型：1 普通个人客户 2 企悦会员工 3 企业客户 
	@ApiModelProperty(value="合同id",required=true)
	private String contractId;
}
