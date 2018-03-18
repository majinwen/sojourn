package com.zra.common.enums;

public enum ContractTypeEnum {

    LONG(1, "长租"),SHORT(2,"短租");
    
    private final int type;
    
    private final String desc;
    
    ContractTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    
    public int getType(){
        return this.type;
    }
    
    public String getDesc(){
        return this.desc;
    }
}
