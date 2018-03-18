package com.ziroom.zrp.trading.entity;
import com.ziroom.zrp.trading.entity.RentContractEntity;

/**
 * <p>合同实体加物业状态</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author xiangb
 * @version 1.0
 * @Date Created in 2017年10月19日
 * @since 1.0
 */
public class RentContractAndDetailEntity extends RentContractEntity {

	private static final long serialVersionUID = 4240978372017828815L;
	/**
     * 物业交割状态；0：未交割；1:已交割
     */
    private Integer deliveryState;
    private Integer showEnum;//合同APP显示状态
    private Integer operationEnum;//合同APP显示操作
    private Integer showContactZO;//是否显示联系管家
	public Integer getDeliveryState() {
		return deliveryState;
	}
	public void setDeliveryState(Integer deliveryState) {
		this.deliveryState = deliveryState;
	}
	public Integer getShowEnum() {
		return showEnum;
	}
	public void setShowEnum(Integer showEnum) {
		this.showEnum = showEnum;
	}
	public Integer getOperationEnum() {
		return operationEnum;
	}
	public void setOperationEnum(Integer operationEnum) {
		this.operationEnum = operationEnum;
	}
	public Integer getShowContactZO() {
		return showContactZO;
	}
	public void setShowContactZO(Integer showContactZO) {
		this.showContactZO = showContactZO;
	}
	
}