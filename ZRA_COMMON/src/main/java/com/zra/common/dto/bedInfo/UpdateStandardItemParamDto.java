package com.zra.common.dto.bedInfo;

/**
 * 更新床位配置和物品关系的数量，
 * @author tianxf9
 *
 */
public class UpdateStandardItemParamDto {
	
	private int id;
	
	private int itemNum;
	
	private Byte inputOptions;
	
	private Byte numModify;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemNum() {
		return itemNum;
	}

	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}

	public Byte getInputOptions() {
		return inputOptions;
	}

	public void setInputOptions(Byte inputOptions) {
		this.inputOptions = inputOptions;
	}

	public Byte getNumModify() {
		return numModify;
	}

	public void setNumModify(Byte numModify) {
		this.numModify = numModify;
	}
	
}
