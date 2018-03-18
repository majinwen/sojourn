package com.zra.report.constant;

public enum ReportDataTypeEnum {
	
	REPORT_ALL("all", "所有"),
    REPORT_BO("bo", "商机数据"),
    REPORT_STOCK("stock", "库存数据"),
    REPORT_RENEW("renew", "续约数据"), 
    REPORT_PAYMENT("payment", "回款数据");
	
	private String code;
    private String name;

    ReportDataTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ReportDataTypeEnum getByCode(String code){
        for(ReportDataTypeEnum reportDataTypeEnum: ReportDataTypeEnum.values()){
            if(reportDataTypeEnum.getCode().equals(code)){
                return reportDataTypeEnum;
            }
        }
        return null;
    }
}
