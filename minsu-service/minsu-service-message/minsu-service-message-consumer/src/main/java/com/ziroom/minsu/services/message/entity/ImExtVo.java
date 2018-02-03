package com.ziroom.minsu.services.message.entity;

import com.asura.framework.base.entity.BaseEntity;
import com.ziroom.minsu.services.message.dto.ShareHouseMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * im返回vo
 * @author jixd
 * @created 2017年04月10日 18:04:31
 * @param
 * @return
 */
public class ImExtVo extends BaseEntity{
    /**
	 * 序列id
	 */
	private static final long serialVersionUID = 2415216889443720025L;

	/**
     * 房源或房间fid
     */
    private String fid;

    /**
     * 出租方式 0=整租  1 = 分租
     */
    private Integer rentWay;
    
    /**
	 * 房间类型 ：共享客厅 或者 独立房间
	 */
	private String rentWayName;
    /**
     * 起始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 环信 同步民宿消息标识 （民宿：）
     */
    private String ziroomFlag;

    /**
     * 入住人数
     */
    private String personNum;

    /**
     * 房源名称
     */
    private String houseName;

    /**
     * 房源url
     */
    private String housePicUrl;

    /**
     * 房源卡 代表是：房客点击联系房东 填写完信息 进来的
     */
    private String houseCard;

    /**
     * 环信 消息id
     */
    private String huanxinMsgId;

    /**
     * 分享房源信息
     */
    private List<ShareHouseMsg> shareHouseMsg = new ArrayList<>();
    /**
     * 消息类型 0=普通消息 1=首次咨询 2=分享卡片 3=自动回复
     */
    private String msgType;

    /**
     * 角色类型 1=房东 2=房客 3=房东和房客
     */
    private Integer roleType;
    /**
     * 同步环信聊天记录环境标识 （minsu_d  minsu_t  minsu_q minsu_online）
     */
    private String domainFlag;
    
    /**
     * 昵称
     */
    private String nicName;
    
    /**
     * 头像
     */
    private String userPic;
    
    /**
     * 2017.11.27版本，增加木木的表情
     */
    private String em_expr_big_name;

    /**
	 * @return the nicName
	 */
	public String getNicName() {
		return nicName;
	}

	/**
	 * @param nicName the nicName to set
	 */
	public void setNicName(String nicName) {
		this.nicName = nicName;
	}

	/**
	 * @return the userPic
	 */
	public String getUserPic() {
		return userPic;
	}

	/**
	 * @param userPic the userPic to set
	 */
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}

	public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getRentWay() {
        return rentWay;
    }

    public void setRentWay(Integer rentWay) {
        this.rentWay = rentWay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getZiroomFlag() {
        return ziroomFlag;
    }

    public void setZiroomFlag(String ziroomFlag) {
        this.ziroomFlag = ziroomFlag;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHousePicUrl() {
        return housePicUrl;
    }

    public void setHousePicUrl(String housePicUrl) {
        this.housePicUrl = housePicUrl;
    }

    public String getHouseCard() {
        return houseCard;
    }

    public void setHouseCard(String houseCard) {
        this.houseCard = houseCard;
    }

    public String getHuanxinMsgId() {
        return huanxinMsgId;
    }

    public void setHuanxinMsgId(String huanxinMsgId) {
        this.huanxinMsgId = huanxinMsgId;
    }

    public String getDomainFlag() {
        return domainFlag;
    }

    public void setDomainFlag(String domainFlag) {
        this.domainFlag = domainFlag;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public List<ShareHouseMsg> getShareHouseMsg() {
        return shareHouseMsg;
    }

    public void setShareHouseMsg(List<ShareHouseMsg> shareHouseMsg) {
        this.shareHouseMsg = shareHouseMsg;
    }

	public String getEm_expr_big_name() {
		return em_expr_big_name;
	}

	public void setEm_expr_big_name(String em_expr_big_name) {
		this.em_expr_big_name = em_expr_big_name;
	}

	public String getRentWayName() {
		return rentWayName;
	}

	public void setRentWayName(String rentWayName) {
		this.rentWayName = rentWayName;
	}

}
