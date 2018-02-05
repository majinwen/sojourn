package com.ziroom.minsu.api.test.house.controller;

import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.services.common.utils.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>房源管理controller test</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author wangwt
 * @version 1.0
 * @Date Created in 2017年06月23日 11:28
 * @since 1.0
 */
public class LandlordHouseMgtControllerTest {
    private IEncrypt iEncrypt;
    private DESEncrypt dESEncrypt;

    @Before
    public void setUp() throws Exception {
        iEncrypt= EncryptFactory.createEncryption("DES");
        dESEncrypt  = new DESEncrypt();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void upDownHouse() throws Exception {
        String param = "{\"houseFid\":\"8a90a2d4549341c6015494bb1e8501c3\", \"landlordUid\":\"9afeae98-56ff-0c35-77cf-8624b2e1efae\", \"rentWay\":0}";
        String paramJson=iEncrypt.encrypt(param);
        System.err.println("2y5QfvAy="+paramJson);
        System.err.println("hPtJ39Xs="+ MD5Util.MD5Encode(param,"UTF-8"));
    }


    @Test
    public void getHouseBaseTest() throws Exception {
        String param = "{\"uid\" : \"4b37c562-6ce9-7065-e9e6-aab2182cdbc4\",\"fid\" : \"8a9084df56b5aea20156b66f1c870184\",\"rentWay\" : \"0\"}";
        String paramJson=iEncrypt.encrypt(param);
        System.err.println("2y5QfvAy="+paramJson);
        System.err.println("hPtJ39Xs="+ MD5Util.MD5Encode(param,"UTF-8"));
    }




    @Test
    public void cancleHouse() throws Exception {
        String param = "{\"houseFid\":\"8a9e9a9454801ac50154801edf380015\", \"landlordUid\":\"9afeae98-56ff-0c35-77cf-8624b2e1efae\", \"rentWay\":0}";
        String paramJson=iEncrypt.encrypt(param);
        System.err.println("2y5QfvAy="+paramJson);
        System.err.println("hPtJ39Xs="+ MD5Util.MD5Encode(param,"UTF-8"));
    }

    @Test
    public void saveHouseOrRoomPriceForModify() throws Exception {
        String param = "{\"houseBaseFid\":\"8a90a2d45d2fd402015d306798fd00f1\",\"cleaningFees\":0,\"weekendPriceVal\":3000.0,\"thirtyDiscountRate\"" +
                ":7.8,\"rentWay\":0,\"weekendPriceSwitch\":1,\"fullDayRateSwitch\":0,\"uid\":\"85bd041a-39ba-4035-8eee-996fca6ee750\"," +
                "\"sevenDiscountRate\":9.0,\"weekendPriceType\":\"5,6\",\"leasePrice\":2009}";
        String paramJson=iEncrypt.encrypt(param);
        System.err.println("2y5QfvAy="+paramJson);
        System.err.println("hPtJ39Xs="+ MD5Util.MD5Encode(param,"UTF-8"));
    }

    public static void main(String[] args) {
        Double weekendPriceVal = new Double("100");
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(weekendPriceVal.toString());
//        boolean r = weekendPriceVal.toString().matches("[0-9]*");
        System.out.println(isNum.matches());


//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(str);


//        Double weekendPriceVal = new Double("100");
//        boolean r = weekendPriceVal.toString().matches("[0-9]+");
//        System.out.println(r);
        boolean r2 = StringUtils.isNumeric("100");
        System.out.println(r2);

    }

}