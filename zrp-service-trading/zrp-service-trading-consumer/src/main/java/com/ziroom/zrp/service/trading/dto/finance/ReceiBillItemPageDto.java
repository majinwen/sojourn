package com.ziroom.zrp.service.trading.dto.finance;

import java.util.List;

/**
 * <p>签约费用总览页面</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月26日 18:39
 * @since 1.0
 */
public class ReceiBillItemPageDto {

    //费用总计信息
    private ReceiBillItemTotalDto receiBillItemTotalDto;

    //每个房间费用信息
    private List<ReceiBillItemRoomDto> receiBillItemRoomDtoList;

    public ReceiBillItemTotalDto getReceiBillItemTotalDto() {
        return receiBillItemTotalDto;
    }

    public void setReceiBillItemTotalDto(ReceiBillItemTotalDto receiBillItemTotalDto) {
        this.receiBillItemTotalDto = receiBillItemTotalDto;
    }

    public List<ReceiBillItemRoomDto> getReceiBillItemRoomDtoList() {
        return receiBillItemRoomDtoList;
    }

    public void setReceiBillItemRoomDtoList(List<ReceiBillItemRoomDto> receiBillItemRoomDtoList) {
        this.receiBillItemRoomDtoList = receiBillItemRoomDtoList;
    }

}
