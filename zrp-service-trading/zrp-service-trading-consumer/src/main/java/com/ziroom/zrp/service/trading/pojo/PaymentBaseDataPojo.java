package com.ziroom.zrp.service.trading.pojo;

/**
 * <p>计算缴款的基础数据类</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuigh6
 * @Date Created in 2017年09月13日 11:27
 * @version 1.0
 * @since 1.0
 */
public class PaymentBaseDataPojo {
	/**
	 * 合同对象
	 */
	private ContractPojo contractPojo;

	/**
	 * 房间对象
	 */
	private RoomPojo roomPojo;


	public ContractPojo getContractPojo() {
		return contractPojo;
	}

	public void setContractPojo(ContractPojo contractPojo) {
		this.contractPojo = contractPojo;
	}

	public RoomPojo getRoomPojo() {
		return roomPojo;
	}

	public void setRoomPojo(RoomPojo roomPojo) {
		this.roomPojo = roomPojo;
	}
}
