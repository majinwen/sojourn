package com.zra.common.dto.zotel;


import java.util.Date;

/**
 * 400电话
 */
public class FourooTelDto {

    private String id;//zo id

    private String fourooTel;//400电话

    private Date creatTime;//创建时间

    private Date lastModifyTime;//最后修改时间

    private Integer isDel;//有效状态（1已删 0有效）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFourooTel() {
        return fourooTel;
    }

    public void setFourooTel(String fourooTel) {
        this.fourooTel = fourooTel;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer idDel) {
        this.isDel = idDel;
    }
}
