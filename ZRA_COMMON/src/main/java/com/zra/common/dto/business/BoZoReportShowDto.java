package com.zra.common.dto.business;

/**
 * ZO项目报表展示Dto
 * @author wangws21 2016-8-17
 *
 */
public class BoZoReportShowDto {

	private String projectId;
	//项目名称
	private String projectName;
	//管家姓名
	private String zoName;
	//管家id
	private String zoId;
	//约看量
	private int yuKanTotal;
	//约看及时跟进率
	private double yuKanToGJRate;
	
	//wangws21 2017-1-18 添加客源量
	private int kylTotal;
	
	//带看量
	private int daiKanTotal;
	//约看-带看转换率
	private double yuKanToDaiKanRate;
	
	//客源量-带看 转化率
	private String kylToDaikanRate;
	
	//回访量
	private int visitCount;
	//回访率
	private double visitRate;
	//成交量
	private int dealCount;
	
	//带看-成交转换率
	private double daiKanToDealRate;
	//首次完结占比
	private double wanJieRate;
	
	// add by tianxf9——添加接听率统计
	private String zoPhone;
	//来电数量
	private int callCount;
	//接听率
	private double answerRate;

	public BoZoReportShowDto() {
	}
	
	public BoZoReportShowDto(String projectId,String projectName, String zoId) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.zoId = zoId;
	}



	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getZoName() {
		return zoName;
	}

	public void setZoName(String zoName) {
		this.zoName = zoName;
	}

	public String getZoId() {
		return zoId;
	}

	public void setZoId(String zoId) {
		this.zoId = zoId;
	}

	public int getYuKanTotal() {
		return yuKanTotal;
	}

	public void setYuKanTotal(int yuKanTotal) {
		this.yuKanTotal = yuKanTotal;
	}

	public double getYuKanToGJRate() {
		return yuKanToGJRate;
	}

	public void setYuKanToGJRate(double yuKanToGJRate) {
		this.yuKanToGJRate = yuKanToGJRate;
	}

	public int getDaiKanTotal() {
		return daiKanTotal;
	}

	public void setDaiKanTotal(int daiKanTotal) {
		this.daiKanTotal = daiKanTotal;
	}

	public double getYuKanToDaiKanRate() {
		return yuKanToDaiKanRate;
	}

	public void setYuKanToDaiKanRate(double yuKanToDaiKanRate) {
		this.yuKanToDaiKanRate = yuKanToDaiKanRate;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public double getVisitRate() {
		return visitRate;
	}

	public void setVisitRate(double visitRate) {
		this.visitRate = visitRate;
	}

	public int getDealCount() {
		return dealCount;
	}

	public void setDealCount(int dealCount) {
		this.dealCount = dealCount;
	}

	public double getDaiKanToDealRate() {
		return daiKanToDealRate;
	}

	public void setDaiKanToDealRate(double daiKanToDealRate) {
		this.daiKanToDealRate = daiKanToDealRate;
	}

	public double getWanJieRate() {
		return wanJieRate;
	}

	public void setWanJieRate(double wanJieRate) {
		this.wanJieRate = wanJieRate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

    public int getKylTotal() {
        return kylTotal;
    }

    public void setKylTotal(int kylTotal) {
        this.kylTotal = kylTotal;
    }

    public String getKylToDaikanRate() {
        return kylToDaikanRate;
    }

    public void setKylToDaikanRate(String kylToDaikanRate) {
        this.kylToDaikanRate = kylToDaikanRate;
    }

	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public double getAnswerRate() {
		return answerRate;
	}

	public void setAnswerRate(double answerRate) {
		this.answerRate = answerRate;
	}

	public String getZoPhone() {
		return zoPhone;
	}

	public void setZoPhone(String zoPhone) {
		this.zoPhone = zoPhone;
	}
    
    
}
