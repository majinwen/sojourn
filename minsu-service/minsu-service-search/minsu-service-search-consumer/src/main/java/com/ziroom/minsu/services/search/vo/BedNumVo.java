package com.ziroom.minsu.services.search.vo;


import com.asura.framework.base.entity.BaseEntity;

public class BedNumVo extends BaseEntity{


    private static final long serialVersionUID = 1312893718979873L;

    /**
     * 床类型
     */
    private Integer bedType;

    /**
     * 床数量
     */
    private Integer bedNum;

    /**
     * 床类型名称
     */
    private String bedTypeName;



    public Integer getBedType() {
        return bedType;
    }

    public void setBedType(Integer bedType) {
        this.bedType = bedType;
    }


    public Integer getBedNum() {
        return bedNum;
    }

    public void setBedNum(Integer bedNum) {
        this.bedNum = bedNum;
    }

    public String getBedTypeName() {
        return bedTypeName;
    }

    public void setBedTypeName(String bedTypeName) {
        this.bedTypeName = bedTypeName;
    }

}
