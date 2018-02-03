package com.ziroom.minsu.services.cms.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.cms.ActCouponEntity;
import com.ziroom.minsu.entity.cms.ActCouponUserEntity;
import com.ziroom.minsu.services.cms.dao.ActCouponDao;
import com.ziroom.minsu.services.cms.dto.ActCouponRequest;
import com.ziroom.minsu.services.cms.dto.CheckCouponRequest;
import com.ziroom.minsu.services.cms.entity.ActCouponInfoUserVo;
import com.ziroom.minsu.services.cms.entity.CouponUserUidVo;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/8.
 * @version 1.0
 * @since 1.0
 */
public class ActCouponDaoTest  extends BaseTest {

    @Resource(name = "cms.actCouponDao")
    private ActCouponDao actCouponDao;

    @Test
    public void getOneActCouByGroupSn() throws ParseException {
        ActCouponEntity actCouponEntity = actCouponDao.getOneActCouByGroupSn("2343");
        System.out.println(JsonEntityTransform.Object2Json(actCouponEntity));
        try {
            Thread.sleep(100000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getCouponListCheckByUidDefaultFree() throws ParseException {
        CheckCouponRequest request = new CheckCouponRequest();
        request.setUid("a06f82a2-423a-e4e3-4ea8-e98317540190");
        request.setCityCode("110100");
        request.setPrice(100000);
        request.setCityCode("123123");
        List<ActCouponUserEntity> rst = actCouponDao.getCouponListCheckByUidDefault(request);
        System.out.println(JsonEntityTransform.Object2Json(rst));

    }



    @Test
    public void getCouponListCheckByUid() throws ParseException {
        CheckCouponRequest request = new CheckCouponRequest();
        request.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
        request.setCityCode("110100");
        request.setPrice(100000);
        List<ActCouponUserEntity> rst = actCouponDao.getCouponListCheckByUid(request);
        System.out.println(JsonEntityTransform.Object2Json(rst));

    }


    @Test
    public void TestsaveCoupon() throws ParseException {
    	/*int num = 128;
    	for (int i = 0; i < 10; i++) {*/
    	int num = 149;
		ActCouponEntity actCouponEntity = new ActCouponEntity();
		actCouponEntity.setCouponSn(num + "");
		actCouponEntity.setActSn("ziroom");
		actCouponEntity.setActCut(10);
		actCouponEntity.setActLimit(199);
		actCouponEntity.setActType(1);
		//actCouponEntity.setActUser(0);
		actCouponEntity.setActUser("0");
		//actCouponEntity.setCityCode("110100");
		actCouponEntity.setCouponName("优惠券" + num);
		actCouponEntity.setCouponStartTime(DateUtil.parseDate("2016-06-15", "yyyy-MM-dd"));
		actCouponEntity.setCouponEndTime(DateUtil.parseDate("2016-06-17", "yyyy-MM-dd"));
		actCouponEntity.setCheckInTime(DateUtil.parseDate("2016-06-15", "yyyy-MM-dd"));
		actCouponEntity.setCheckOutTime(DateUtil.parseDate("2016-06-17", "yyyy-MM-dd"));
		actCouponEntity.setCouponStatus(1);
		int aa = actCouponDao.saveCoupon(actCouponEntity);
		System.out.println(JsonEntityTransform.Object2Json(aa));
		//}
    }


    @Test
    public void TestgetCouponBySn() {

        ActCouponEntity actCouponEntity = actCouponDao.getCouponBySn("Test1.4");

        System.out.println(JsonEntityTransform.Object2Json(actCouponEntity));
    }

    @Test
    public void TestgetCouponListByActSn() {
    	ActCouponRequest request = new ActCouponRequest();
    	request.setActSn("ziroom");
        PagingResult<ActCouponEntity> result = actCouponDao.getCouponListByActSn(request);

        System.out.println(JsonEntityTransform.Object2Json(result));
    }




    @Test
    public void getCouponFullList(){

        ActCouponRequest request = new ActCouponRequest();

//        request.setActSn("ziroom");
//        request.setUid("e0a0f779-9117-6283-84e1-43e0be20ecf4");
//        request.setCouponSn("ziroomBRAHXE");
//        request.setCouponStatus(2);
//        request.setCouponName("af");
        request.setOrderSn("123");
        PagingResult<ActCouponUserEntity> v = actCouponDao.getCouponFullList(request);

        System.err.println(JsonEntityTransform.Object2Json(v));
    }

    
    @Test
    public void TestGetActCouponOrderVoByCouponSn(){
    	ActCouponUserEntity v = actCouponDao.getActCouponUserVoByCouponSn("123");
    	
    	System.err.println(JsonEntityTransform.Object2Json(v));
    }

    
    @Test
    public void TestgetActCouponInfoVoByCouponSn(){
    	ActCouponInfoUserVo v = actCouponDao.getActCouponInfoVoByCouponSn("123");
    	
    	System.err.println(JsonEntityTransform.Object2Json(v));
    }
    
    
    @Test
    public void getExpireCountTest(){
    	long count = actCouponDao.getExpireCount();
    	System.err.println(count);
    }
    
    
    @Test
    public void updateExpireListTest(){
    	int num = actCouponDao.updateExpireList(1000);
    	System.err.println(num);
    }

    @Test
    public void testcountAvaliableCouponByActSn(){
        Long aLong = actCouponDao.countAvaliableCouponByActSn("yuango");
        System.err.println(aLong);
    }
    
    @Test
    public void testgetCouponByActSn(){
    
    	
    	for (int i = 0; i < 2; i++) {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					ActCouponEntity actCouponEntity= actCouponDao.getCouponByActSn("HAIYA2");
			    	System.out.println(actCouponEntity.toJsonStr());
				}
			});
			t1.start();
		}
		
		try {
			Thread.sleep(999999);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testgetCouponListAllByGroupSn(){
        ActCouponRequest actCouponRequest = new ActCouponRequest();
        actCouponRequest.setPage(1);
        actCouponRequest.setGroupSn("XQYLQ1701");
        PagingResult<CouponUserUidVo> oneMonthExpireCouponUidByGroupSN = actCouponDao.getOneMonthExpireCouponUidByGroupSN(actCouponRequest);
        System.err.println(JsonEntityTransform.Object2Json(oneMonthExpireCouponUidByGroupSN));
    }

    @Test
    public void testUpdateCoupon() {
        ActCouponEntity actCouponEntity = new ActCouponEntity();
        actCouponEntity.setCouponSn("test1");
        actCouponEntity.setCouponStatus(2);
        int result=actCouponDao.updateCoupon(actCouponEntity);
        System.err.println(result);
    }

    @Test
    public void getActSnStatusByCouponSn() {

        ActCouponEntity actCouponEntity = actCouponDao.getActSnStatusByCouponSn("Test1.4");

        System.err.println(JsonEntityTransform.Object2Json(actCouponEntity));
    }

}
