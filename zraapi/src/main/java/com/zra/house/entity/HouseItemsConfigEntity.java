package com.zra.house.entity;

import java.util.Date;

/**
 * <p>户型配置</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/4 13:44
 * @since 1.0
 */
public class HouseItemsConfigEntity {

    /**
     * 主键
     */
    private String id;

    /**
     * 户型ID
     */
    private String houseTypeId;

    /**
     * 物品ID
     */
    private String itemId;

    /**
     * 物品分类
     */
    private String itemsType;

    /**
     * 物品名称
     */
    private String itemsName;

    /**
     * 物品编码
     */
    private String itemsCode;

    /**
     * 物品数量
     */
    private Integer itemsNum;

    /**
     * 必配 1:是 0:否
     */
    private String inputOptions;

    /**
     * 是否有库存 1:是 0:否
     */
    private String inventoryManage;

    /**
     * 数量可修改 1:是 0:否
     */
    private String numModify;

    /**
     * 项目ID
     */
    private String projectId;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 是否有效
     */
    private Integer valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private String createrId;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人id
     */
    private String updaterId;

    /**
     * 是否删除
     */
    private Integer isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(String houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemsType() {
        return itemsType;
    }

    public void setItemsType(String itemsType) {
        this.itemsType = itemsType;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemsCode() {
        return itemsCode;
    }

    public void setItemsCode(String itemsCode) {
        this.itemsCode = itemsCode;
    }

    public Integer getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }

    public String getInputOptions() {
        return inputOptions;
    }

    public void setInputOptions(String inputOptions) {
        this.inputOptions = inputOptions;
    }

    public String getInventoryManage() {
        return inventoryManage;
    }

    public void setInventoryManage(String inventoryManage) {
        this.inventoryManage = inventoryManage;
    }

    public String getNumModify() {
        return numModify;
    }

    public void setNumModify(String numModify) {
        this.numModify = numModify;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterId() {
        return updaterId;
    }

    public void setUpdaterId(String updaterId) {
        this.updaterId = updaterId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
