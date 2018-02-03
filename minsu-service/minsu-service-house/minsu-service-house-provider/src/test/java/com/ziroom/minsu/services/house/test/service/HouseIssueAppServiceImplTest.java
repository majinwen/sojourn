package com.ziroom.minsu.services.house.test.service;

import com.ziroom.minsu.services.house.issue.dto.HouseTypeLocationDto;
import com.ziroom.minsu.services.house.issue.vo.HouseBaseVo;
import com.ziroom.minsu.services.house.service.HouseIssueAppServiceImpl;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import com.ziroom.minsu.valenum.house.RentWayEnum;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author yanb
 * @version 1.0
 * @Date Created in 2017年11月17日 14:15
 * @since 1.0
 */
public class HouseIssueAppServiceImplTest extends BaseTest{
    @Resource(name="house.houseIssueAppServiceImpl")
    private HouseIssueAppServiceImpl houseIssueAppService;

    @Test
    public void testSaveHousePhyAndExt() throws Exception {
        HouseTypeLocationDto dto=new HouseTypeLocationDto();
        dto.setHouseBaseFid("8a9e9aa660586a900160586a92ce0001");
        dto.setRentWay(RentWayEnum.ROOM.getCode());
        dto.setRoomType(1);
        dto.setHouseType(1);
        dto.setRegionCode("100000,110000,110100,110101");
        dto.setRegionName("中国,北京,北京市,朝阳区");
        dto.setHouseStreet("合租yanb2");
        dto.setLandlordUid("合租yanb2");
        dto.setHouseSource(3);
        //dto.setHouseGuardRel();
        HouseBaseVo houseBaseVo=houseIssueAppService.saveHousePhyAndExt(dto);
        System.err.print(houseBaseVo.toString());
    }
}
