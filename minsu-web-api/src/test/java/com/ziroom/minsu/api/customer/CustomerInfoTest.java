package com.ziroom.minsu.api.customer;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.MD5Util;
import com.ziroom.minsu.api.common.encrypt.DESEncrypt;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import com.ziroom.minsu.api.customer.dto.LandlordDto;
import com.ziroom.minsu.services.common.utils.CloseableHttpUtil;
import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/16.
 * @version 1.0
 * @since 1.0
 */
public class CustomerInfoTest {

	private static 	IEncrypt iEncrypt=EncryptFactory.createEncryption("DES");
	private static DESEncrypt dESEncrypt  = new DESEncrypt();

    public static void main(String[] args) {
        String url = "http://passport.q.ziroom.com/api/index.php?r=user/save-cert";


//        String url = "http://passport.q.ziroom.com/api/index.php?r=user/save-cert";
        Map<String,String> par = new HashMap<>();
        par.put("user_name","测试");
        par.put("ziroom_token","b1828330-b532-492c-a740-5e47bc34e5a2");
        par.put("cert_type","1");
        par.put("cert_num","100000000200000000");
        String rst = CloseableHttpUtil.sendFormPost(url, par);
        Map<String,Object> map = (Map<String, Object>) JsonEntityTransform.json2Map(rst);
        int code =(Integer) map.get("errorcode");
        if (code == 2000){
            System.out.println("ok");
        }else {
            System.out.println(map.get("errormsg"));
        }
    }


    public static void main2(String[] args) {
        String url = "http://customer.q.ziroom.com/api/add_pay_msg.php";


        //http://customer.q.ziroom.com/api/add_pay_msg.php?uid=f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6&card_name=%E4%B8%AD%E5%9B%BD%E5%B9%BF%E5%A4%A7%E9%93%B6%E8%A1%8C&card_code=555620013254425574&is_del=0&province=%E5%8C%97%E4%BA%AC

        Map<String,String> par = new HashMap<>();
        par.put("uid","f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6");
        par.put("card_name","银行名称");
        par.put("card_code","555620013254425574");
        par.put("province","测试北京");
        par.put("is_del","0");
        String rst = CloseableHttpUtil.sendFormPost(url, par);
        System.out.println(rst);
    }
    
    
    public static void landlordIntroduceNew() {
    	LandlordDto landlordDto = new LandlordDto();
    	landlordDto.setLandlordUid("aa3b72ed-3b93-4018-93f4-880ec4d7096b");
    	
    	System.err.println(JsonEntityTransform.Object2Json(landlordDto));
		String paramJson=iEncrypt.encrypt(JsonEntityTransform.Object2Json(landlordDto));
		System.err.println("2y5QfvAy="+paramJson);
		System.err.println("hPtJ39Xs="+MD5Util.MD5Encode(JsonEntityTransform.Object2Json(landlordDto).toString(),"UTF-8"));
    	
    	
	}
    
    public static void landlordIntroduceNewDES(){
		
		String content = "b29f8942196125a5c26968b712b9b71c72c4f6954f19f7ac18aa0ed6a51db9ace9528e66c5e1166a2ebacbf23a7550e0310324b30da6671efdf8b0b99c12261cd4b2ac807f557a3eacd6ec8c81a9fdd51dfa016a62d4d0149b4cd76cab54c1deecf2ab70420ae87ddc0502656a23ec38cbbca7b1a9649b2d39eea3ad419013192d0347732f5debd424c23699b41d77113eab55a6eb63fa9b021f4643f52f625f";
		content = dESEncrypt.decrypt(content);
		
		System.out.println(content);
	}
//    public static void main(String[] args) {
//    	landlordIntroduceNew();
//    	landlordIntroduceNewDES();
//	}




}
