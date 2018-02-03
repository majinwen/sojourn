package com.ziroom.minsu.services.house.test.proxy;

import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.SOAResParseUtil;
import com.ziroom.minsu.entity.house.HouseConfMsgEntity;
import com.ziroom.minsu.services.house.dto.HouseAndConfDto;
import com.ziroom.minsu.services.house.dto.HouseBaseExtDto;
import com.ziroom.minsu.services.house.issue.vo.HouseHallVo;
import com.ziroom.minsu.services.house.proxy.HouseIssueAppServiceProxy;
import com.ziroom.minsu.services.house.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
 * @Date Created in 2017年11月22日 12:58
 * @since 1.0
 */
public class HouseIssueAppServiceProxyTest extends BaseTest {
    @Resource(name = "house.houseIssueAppServiceProxy")
    private HouseIssueAppServiceProxy houseIssueAppServiceProxy;

    @Test
    public void saveHouseMsgAndConfTest() throws SOAParseException {
        HouseAndConfDto houseAndConfDto = new HouseAndConfDto();

        HouseBaseExtDto houseBaseMsgEntity = null;
        List<HouseConfMsgEntity> listHouseConfMsg = new ArrayList<HouseConfMsgEntity>();

        houseAndConfDto.setHouseBaseExtDto(houseBaseMsgEntity);
        houseAndConfDto.setHouseConfMsgList(listHouseConfMsg);

        String param = "{\"houseBaseExtDto\":{\"id\":2407,\"fid\":\"8a9084df601561ed01602001103d2564\",\"phyFid\":\"8a9084df601561ed01602001103a2563\",\"houseName\":null,\"rentWay\":1,\"houseType\":2,\"houseStatus\":10,\"houseWeight\":0,\"landlordUid\":\"d6f61af3-1db3-dd5d-02d9-8db272de1431\",\"leasePrice\":null,\"houseArea\":0.0,\"roomNum\":2,\"hallNum\":2,\"toiletNum\":2,\"kitchenNum\":1,\"balconyNum\":2,\"cameramanName\":null,\"cameramanMobile\":null,\"operateSeq\":3,\"intactRate\":0.3,\"refreshDate\":1512394566000,\"tillDate\":2143209600000,\"createDate\":1512394566000,\"lastModifyDate\":1512394592000,\"isDel\":0,\"houseAddr\":\"北京西城区ujjjjjhhbbn\",\"isPic\":0,\"isLock\":0,\"houseSource\":3,\"houseSn\":\"110114154798Z\",\"houseCleaningFees\":0,\"houseChannel\":2,\"houseBaseExt\":{\"id\":2376,\"fid\":\"8a9084df601561ed01602001103f2565\",\"houseBaseFid\":\"8a9084df601561ed01602001103d2564\",\"buildingNum\":null,\"unitNum\":null,\"floorNum\":null,\"houseNum\":null,\"houseStreet\":\"ujjjjj\",\"facilityCode\":null,\"serviceCode\":null,\"orderType\":1,\"homestayType\":0,\"minDay\":1,\"discountRulesCode\":null,\"depositRulesCode\":null,\"checkOutRulesCode\":\"TradeRulesEnum005002\",\"checkInLimit\":null,\"checkInTime\":\"0\",\"checkOutTime\":\"0\",\"sheetsReplaceRules\":0,\"fullDiscount\":0.0,\"isTogetherLandlord\":0,\"defaultPicFid\":null,\"oldDefaultPicFid\":null,\"detailAddress\":null,\"isLandlordPic\":0,\"houseQualityGrade\":null,\"isPhotography\":0,\"surveyResult\":0,\"rentRoomNum\":0},\"oldStatus\":null},\"houseConfMsgList\":[{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9084df601561ed01602001103d2564\",\"roomFid\":null,\"bedFid\":null,\"dicCode\":\"ProductRulesEnum002001\",\"dicVal\":\"1\",\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null},{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9084df601561ed01602001103d2564\",\"roomFid\":null,\"bedFid\":null,\"dicCode\":\"ProductRulesEnum002001\",\"dicVal\":\"5\",\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null},{\"id\":null,\"fid\":null,\"houseBaseFid\":\"8a9084df601561ed01602001103d2564\",\"roomFid\":null,\"bedFid\":null,\"dicCode\":\"ProductRulesEnum0015\",\"dicVal\":\"3\",\"createDate\":null,\"lastModifyDate\":null,\"isDel\":null}]}";

        String resultJson = houseIssueAppServiceProxy.saveHouseMsgAndConf(param);
        HouseHallVo houseHallVo = SOAResParseUtil.getValueFromDataByKey(resultJson, "houseHallVo", HouseHallVo.class);

        System.err.print(houseHallVo.toString());
    }
}
