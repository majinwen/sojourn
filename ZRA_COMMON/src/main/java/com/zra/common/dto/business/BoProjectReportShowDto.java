package com.zra.common.dto.business;

/**
 * 项目报表展示Dto
 * @author tianxf9
 *
 */
public class BoProjectReportShowDto {
	
	//项目Id
	private String projectId;
	//项目名称
	private String projectName;
	//约看量
	private int yueKanTotal;
	//400来电约看量
	private int yueKanCC;
	//在线约看量
	private int yueKanOnLine;
	//云霄约看量
	private int yueKanCloud;
	//其他约看量
	private int yueKanOther;
	
	//约看及时跟进率
	private double yueKanToGJRate;
	
	//总客源量
	private int kylTotal;
	//400来电客源量
	private int kylCC;
	//在线客源量
	private int kylOnLine;
	//云霄客源量
	private int kylCloud;
	//其他客源量
	private int kylOther;
	
	//带看量
	private int daiKanTotal;
	//400来电带看量
	private int daiKanCC;
	//在线带看量
	private int daiKanOnLine;
	//云霄带看量
	private int daiKanCloud;
	//其他带看量
	private int daiKanOther;
	
	//约看-带看转换率
	private double yueKanToDaiKanRate;
	
	//客源量--带看 转化率 wangws21 2017-1-18
	private String kylToDaiKanRate;
	
	//回访量
	private int visitCount;
	//回访率
	private double visitRate;
	//成交量
	private int dealCount;
	
	//带看-成交转换率
	private double daiKanToDealRate;
	
	//新增约看量
	private int newYueKanTotal;
	//遗留约看量
	private int oldYueKanTotal;
	
	//add by tianxf9 添加接听率统计
	//400来电量
	private int callCount;
	//接听率
	private double answerRate;

	public int getDaiKanTotal() {
		return daiKanTotal;
	}

	public void setDaiKanTotal(int daiKanTotal) {
		this.daiKanTotal = daiKanTotal;
	}

	public int getDaiKanCC() {
		return daiKanCC;
	}

	public void setDaiKanCC(int daiKanCC) {
		this.daiKanCC = daiKanCC;
	}

	public int getDaiKanOnLine() {
		return daiKanOnLine;
	}

	public void setDaiKanOnLine(int daiKanOnLine) {
		this.daiKanOnLine = daiKanOnLine;
	}

	public int getDaiKanCloud() {
		return daiKanCloud;
	}

	public void setDaiKanCloud(int daiKanCloud) {
		this.daiKanCloud = daiKanCloud;
	}

	public int getDaiKanOther() {
		return daiKanOther;
	}

	public void setDaiKanOther(int daiKanOther) {
		this.daiKanOther = daiKanOther;
	}
	public int getYueKanTotal() {
		return yueKanTotal;
	}

	public void setYueKanTotal(int yueKanTotal) {
		this.yueKanTotal = yueKanTotal;
	}

	public int getYueKanCC() {
		return yueKanCC;
	}

	public void setYueKanCC(int yueKanCC) {
		this.yueKanCC = yueKanCC;
	}

	public int getYueKanOnLine() {
		return yueKanOnLine;
	}

	public void setYueKanOnLine(int yueKanOnLine) {
		this.yueKanOnLine = yueKanOnLine;
	}

	public int getYueKanCloud() {
		return yueKanCloud;
	}

	public void setYueKanCloud(int yueKanCloud) {
		this.yueKanCloud = yueKanCloud;
	}

	public int getYueKanOther() {
		return yueKanOther;
	}

	public void setYueKanOther(int yueKanOther) {
		this.yueKanOther = yueKanOther;
	}

	public double getYueKanToGJRate() {
		return yueKanToGJRate;
	}

	public void setYueKanToGJRate(double yueKanToGJRate) {
		this.yueKanToGJRate = yueKanToGJRate;
	}

	public double getYueKanToDaiKanRate() {
		return yueKanToDaiKanRate;
	}

	public void setYueKanToDaiKanRate(double yueKanToDaiKanRate) {
		this.yueKanToDaiKanRate = yueKanToDaiKanRate;
	}

	public int getNewYueKanTotal() {
		return newYueKanTotal;
	}

	public void setNewYueKanTotal(int newYueKanTotal) {
		this.newYueKanTotal = newYueKanTotal;
	}

	public int getOldYueKanTotal() {
		return oldYueKanTotal;
	}

	public void setOldYueKanTotal(int oldYueKanTotal) {
		this.oldYueKanTotal = oldYueKanTotal;
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

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

    public int getKylTotal() {
        return kylTotal;
    }

    public void setKylTotal(int kylTotal) {
        this.kylTotal = kylTotal;
    }

    public int getKylCC() {
        return kylCC;
    }

    public void setKylCC(int kylCC) {
        this.kylCC = kylCC;
    }

    public int getKylOnLine() {
        return kylOnLine;
    }

    public void setKylOnLine(int kylOnLine) {
        this.kylOnLine = kylOnLine;
    }

    public int getKylCloud() {
        return kylCloud;
    }

    public void setKylCloud(int kylCloud) {
        this.kylCloud = kylCloud;
    }

    public int getKylOther() {
        return kylOther;
    }

    public void setKylOther(int kylOther) {
        this.kylOther = kylOther;
    }

    public String getKylToDaiKanRate() {
        return kylToDaiKanRate;
    }

    public void setKylToDaiKanRate(String kylToDaiKanRate) {
        this.kylToDaiKanRate = kylToDaiKanRate;
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
    
}
