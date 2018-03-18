package com.zra.vacancyreport.entity;

import java.util.Date;

/**
 * <pre>
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑, 永无BUG!
 * 　　　　┃　　　┃Code is far away from bug with the animal protecting
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * </pre>
 */
public class VacancyReportEntity {
	
	private Integer Id;

    // 业务主键
    private String reportEmptyId;
    
    //项目id
    private String projectId;

    // 房间ID
    private String roomId;
    
    //房间号
    private String roomNum;
    
    //户型ID
    private String houseTypeId;

    // 出租方式：1按房间2按床位
    private Integer rentType;

    // 房间状态:0:待租中；1：已出租；2：配置中；3：已下定；4：锁定；5：已下架；6：预定进行中；7：签约进行中；8：可预订
    private String roomState;

    // 空置天数
    private Integer emptyNum;

    // 7天内带看次数
    private Integer seeNum;

    // 房间是否空置：0 ：空置；1：非空置
    private Integer isEmpty;

    // 记录时间
    private Date recordDate;

    // 创建人ID
    private String createrId;

    // 创建时间
    private Date createTime;

    // 操作人
    private String updaterId;

    // 更新时间
    private Date updateTime;

    // 删除者
    private String deleteId;

    // 删除时间
    private Date deleteTime;

    // 是否删除(0:否,1:是)
    private Integer isDel;

   
    public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getReportEmptyId() {
		return reportEmptyId;
	}

	public void setReportEmptyId(String reportEmptyId) {
		this.reportEmptyId = reportEmptyId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(String houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public Integer getRentType() {
        return rentType;
    }

    public void setRentType(Integer rentType) {
        this.rentType = rentType;
    }

    public String getRoomState() {
        return roomState;
    }

    public void setRoomState(String roomState) {
        this.roomState = roomState;
    }

    public Integer getEmptyNum() {
    	if(emptyNum==null) {
    		return 0;
    	}
        return emptyNum;
    }

    public void setEmptyNum(Integer emptyNum) {
        this.emptyNum = emptyNum;
    }

    public Integer getSeeNum() {
    	if(seeNum==null) {
    		return 0;
    	}
        return seeNum;
    }

    public void setSeeNum(Integer seeNum) {
        this.seeNum = seeNum;
    }

    public Integer getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Integer isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "VacancyReportEntity[" +
                "id=" + reportEmptyId +
                ", roomId='" + roomId + '\'' +
                ", rentType='" + rentType + '\'' +
                ", roomState='" + roomState + '\'' +
                ", emptyNum=" + emptyNum +
                ", seeNum=" + seeNum +
                ", isEmpty=" + isEmpty +
                ", recordDate=" + recordDate +
                ", createrId='" + createrId + '\'' +
                ", createTime=" + createTime +
                ", updaterId='" + updaterId + '\'' +
                ", updateTime=" + updateTime +
                ", deleteId='" + deleteId + '\'' +
                ", deleteTime=" + deleteTime +
                ", isDel=" + isDel +
                ']';
    }

    /**
     * 默认构造器
     * Created by dongl50 on 2016/12/2
     * 9:53
     */
    public VacancyReportEntity() {
        super();
    }

    // TODO
    /**
     * 含参构造器
     *
     * Created by dongl50 on 2016/12/2
     * 9:56
     */

}
