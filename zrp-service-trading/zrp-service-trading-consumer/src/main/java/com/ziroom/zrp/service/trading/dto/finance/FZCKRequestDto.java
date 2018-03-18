package com.ziroom.zrp.service.trading.dto.finance;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @Author wangxm113
 * @Date 2017年11月10日 14时39分
 * @Version 1.0
 * @Since 1.0
 */
public class FZCKRequestDto {

    /**
     * houseId : 60009958
     * isOpen : 0
     * pageSize : 20
     * pageNum : 0
     */

    private String houseId;
    private int isOpen;
    private int pageSize;
    private int pageNum;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
