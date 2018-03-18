package com.zra.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 公司编码
 * Created by cuigh6 on 2016/12/27.
 */
public enum CompanyCodeEnum {
    JIANGFU("01", "8015"),
    YAYUNCUN("02", "8015"),
    XIZHIMEN("03", "8015"),
    HUANLEGU("04", "8015"),
    YANGGUANG("05", "8015"),
    SHANGDILINGYUN("06", "8015"),
    QIBAO("07", "5010"),
    JIAODA("08", "8015");
    private String projectCode;
    private String companyCode;

    protected static final Map<String,String> enum2Map = new HashMap();
    static {
        for (CompanyCodeEnum companyCodeEnum : CompanyCodeEnum.values()) {
            enum2Map.put(companyCodeEnum.getProjectCode(), companyCodeEnum.getCompanyCode());
        }
    }
    
    CompanyCodeEnum(String projectCode, String companyCode) {
        this.projectCode = projectCode;
        this.companyCode = companyCode;
    }
    
    public String getProjectCode() {
        return projectCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

	public static Map<String, String> getEnum2Map() {
		return enum2Map;
	}

}
