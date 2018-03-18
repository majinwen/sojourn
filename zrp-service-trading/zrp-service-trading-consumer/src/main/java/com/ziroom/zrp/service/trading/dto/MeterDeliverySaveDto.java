package com.ziroom.zrp.service.trading.dto;

import com.ziroom.zrp.trading.entity.MeterDetailEntity;
import com.ziroom.zrp.trading.entity.RentItemDeliveryEntity;
import com.ziroom.zrp.trading.entity.RentLifeItemDetailEntity;

import java.util.List;

/**
 * <p>物业交割信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年11月03日 11:40
 * @since 1.0
 */
public class MeterDeliverySaveDto {
    /**
     * 合同号
     */
    private String contractId;
    /**
     * 房间信息
     */
    private String roomId;
    /**
     * 创建人Id
     */
    private String createId;
    /**
     * 城市id
     */
    private String cityId;
    /**
     * 来源 app录入=1 后台录入=2 企业录入=3
     */
    private Integer from;

    /**
     * 水电信息
     */
    private MeterDetailEntity meterDetail;
    /**
     * 交割信息
     */
    private List<RentItemDeliveryEntity> rentItems;
    /**
     * 生活费用项
     */
    private List<RentLifeItemDetailEntity> lifeFeeItems;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public MeterDetailEntity getMeterDetail() {
        return meterDetail;
    }

    public void setMeterDetail(MeterDetailEntity meterDetail) {
        this.meterDetail = meterDetail;
    }

    public List<RentItemDeliveryEntity> getRentItems() {
        return rentItems;
    }

    public void setRentItems(List<RentItemDeliveryEntity> rentItems) {
        this.rentItems = rentItems;
    }

    public List<RentLifeItemDetailEntity> getLifeFeeItems() {
        return lifeFeeItems;
    }

    public void setLifeFeeItems(List<RentLifeItemDetailEntity> lifeFeeItems) {
        this.lifeFeeItems = lifeFeeItems;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }
}
