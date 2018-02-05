package test;

import java.util.ArrayList;
import java.util.List;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.order.dto.UsualConApiRequest;
import com.ziroom.minsu.api.order.dto.UsualContactApiRequest;
import com.ziroom.minsu.api.order.dto.UsualContactRequest;
import com.ziroom.minsu.api.order.entity.UsualContactVo;

/**
 * <p>房客端  下单 支付 测试</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author yd
 * @since 1.0
 * @version 1.0
 */
public class UsualContactControllerTest {

	
	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	/**
	 * 
	 * 添加联系人测试
	 *
	 * @author yd
	 * @created 2016年4月30日 下午10:54:55
	 *
	 */
	public static void saveContactsTest() {
		
		UsualContactRequest usualContactRequest =new UsualContactRequest();

		usualContactRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		usualContactRequest.setUid("8a9e9a9e543d23f901543d23f9e90000");
		UsualContactVo usualContactVo  = new UsualContactVo();
		usualContactVo.setCardType(1);
		usualContactVo.setCardValue("420684199007221552");
		usualContactVo.setConName("测试1");
		List<UsualContactVo> listContactVos = new ArrayList<UsualContactVo>();
		listContactVos.add(usualContactVo);
		usualContactRequest.setListContactVos(listContactVos);
		System.err.println(JsonEntityTransform.Object2Json(usualContactRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(usualContactRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(usualContactRequest).toString(),"UTF-8"));
	}
	
	/**
	 * 
	 * 查找
	 *
	 * @author yd
	 * @created 2016年5月2日 下午2:42:28
	 *
	 */
	public static void findUsualContactsTest(){
	
		
		UsualConApiRequest usualConApiRequest  = new UsualConApiRequest();
		
		usualConApiRequest.setUid("8a9e9a9e543d23f901543d23f9e90000");
		usualConApiRequest.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		
		//usualConApiRequest.setUserUid(usualConApiRequest.getUid());
		System.err.println(JsonEntityTransform.Object2Json(usualConApiRequest));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(usualConApiRequest));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(usualConApiRequest).toString(),"UTF-8"));
	}
	
	/**
	 * 
	 * 修改常用联系人
	 *
	 * @author yd
	 * @created 2016年5月2日 下午2:51:44
	 *
	 */
	public static void updateContactTest(){
		
		UsualContactApiRequest usualContactApi= new UsualContactApiRequest();
		usualContactApi.setUid("8a9e9a9e543d23f901543d23f9e90000");
		usualContactApi.setLoginToken("dfkdkfkdjf223j2kj32kj32j32j");
		usualContactApi.setFid("122333");
		usualContactApi.setConName("杨东");
		usualContactApi.setConTel("132648723258");
		
		System.err.println(JsonEntityTransform.Object2Json(usualContactApi));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(usualContactApi));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(usualContactApi).toString(),"UTF-8"));
	}
	//客户端 订单测试
	public static void main(String[] args) {
		//saveContactsTest();
		
		//findUsualContactsTest();
		
		updateContactTest();
		
	}

}
