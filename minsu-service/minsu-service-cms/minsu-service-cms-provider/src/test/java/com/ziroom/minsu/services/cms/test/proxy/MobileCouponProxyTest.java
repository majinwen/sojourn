package com.ziroom.minsu.services.cms.test.proxy;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.RandomUtil;
import com.ziroom.minsu.services.cms.dto.InviteCouponRequest;
import com.ziroom.minsu.services.cms.dto.MobileCouponRequest;
import com.ziroom.minsu.services.cms.proxy.MobileCouponProxy;
import com.ziroom.minsu.services.cms.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on on 2016/12/9.
 * @version 1.0
 * @since 1.0
 */
public class MobileCouponProxyTest extends BaseTest {

	/**
	 * 线程池框架
	 */
	private final static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50, 100, 3000L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());


	@Resource(name = "cms.mobileCouponProxy")
	private MobileCouponProxy mobileCouponProxy;


	@Test
	public void pullActCouponByMobile() {
		MobileCouponRequest request = new MobileCouponRequest();
		request.setMobile("afi");
		request.setActSn("QXMT");
		String rst = mobileCouponProxy.pullActCouponByMobile(JsonEntityTransform.Object2Json(request));
		System.err.println(rst);
	}


	@Test
	public void pullGroupCouponByMobile() {
		MobileCouponRequest request = new MobileCouponRequest();
		request.setMobile("18811366521");
		request.setGroupSn("HAIYANJIHUA2017");
		String rst = mobileCouponProxy.pullGroupCouponByMobile(JsonEntityTransform.Object2Json(request));
		System.err.println(rst);
	}

	@Test
	public void pullGroupCouponByUid(){
		try {
			int i = 0;
			for (i = 0; i <100; i++) {
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						MobileCouponRequest request = new MobileCouponRequest();
						request.setUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350"+RandomUtil.genRandomNum(5));
						request.setGroupSn("HAIYANJIHUA2017");
						String rst = mobileCouponProxy.pullGroupCouponByUid(JsonEntityTransform.Object2Json(request));
						System.err.println(rst);
					}
				});
				t1.start();
			}
			Thread.sleep(3000);
			for (i = 0; i <50; i++) {
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						MobileCouponRequest request = new MobileCouponRequest();
						request.setUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350"+RandomUtil.genRandomNum(5));
						request.setGroupSn("HAIYANJIHUA2017");
						String rst = mobileCouponProxy.pullGroupCouponByUid(JsonEntityTransform.Object2Json(request));
						System.err.println(rst);
					}
				});
				t1.start();
			}
			
			Thread.sleep(1000);
			for (i = 0; i <20; i++) {
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						MobileCouponRequest request = new MobileCouponRequest();
						request.setUid("7a8c4184-8e2e-37b4-08e8-f4c20225e350"+RandomUtil.genRandomNum(5));
						request.setGroupSn("HAIYANJIHUA2017");
						String rst = mobileCouponProxy.pullGroupCouponByUid(JsonEntityTransform.Object2Json(request));
						System.err.println(rst);
					}
				});
				t1.start();
			}
			Thread.sleep(999999);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@Test
	public void testpullActCouponByUid(){
		MobileCouponRequest request = new MobileCouponRequest();
		request.setActSn("FYJ20");
		request.setUid("5f4f193b-07fd-a708-85f8-22907004fd6d");
		String s = mobileCouponProxy.pullActCouponByUid(JsonEntityTransform.Object2Json(request));
		System.out.println(s);
	}

	@Test
	public void acceptPullCouponByUidTest() {
		InviteCouponRequest inviteCoupon = new InviteCouponRequest();
		inviteCoupon.setUid("yanb1.2");
		inviteCoupon.setGroupSn("YAOQINGREN");
		inviteCoupon.setInviteCode("YANBTTBB");
		inviteCoupon.setInviteSource(1);
//		inviteCoupon.setInviteUid("yanb");
		String result = mobileCouponProxy.acceptPullCouponByUid(JsonEntityTransform.Object2Json(inviteCoupon));
		System.err.println(result);

	}
}
