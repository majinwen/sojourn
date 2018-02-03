package com.ziroom.minsu.valenum.order;

import com.asura.framework.base.util.Check;
import com.ziroom.minsu.valenum.common.UserTypeEnum;

public enum IncomeTypeEnum {
	USER_RENT_COMMISSION(1, "客户房租佣金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.TENANT;
        }
    },
	USER_PUNISH_COMMISSION(2, "客户违约金佣金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
	LANDLORD_RENT_COMMISSION(3, "房东房租佣金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
	LANDLORD_PUNISH_COMMISSION(4, "房东违约金佣金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
	LANDLORD_PUNISH(5, "房东违约金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
    BLACK(7, "付款单黑名单"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
    USER_CLEAN_COMMISSION(6, "清洁费佣金"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    },
    PENALTY(8, "罚款单"){
        @Override
        public UserTypeEnum getUserType() {
            return UserTypeEnum.LANDLORD;
        }
    };

	IncomeTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/** 编码 */
	private int code;

	/** 名称 */
	private String name;

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

    /**
     * 获取当前的付款人类型
     * @see UserTypeEnum
     * @return
     */
    public UserTypeEnum getUserType(){
        return null;
    }


    /**
     * 获取当前的枚举值
     * @param code
     * @return
     */
    public static IncomeTypeEnum getByCode(Integer code){
        if (Check.NuNObj(code)){
            return null;
        }
        for(final IncomeTypeEnum incomeTypeEnum : IncomeTypeEnum.values()){
            if(incomeTypeEnum.getCode() == code){
                return incomeTypeEnum;
            }
        }
        return null;
    }
}
