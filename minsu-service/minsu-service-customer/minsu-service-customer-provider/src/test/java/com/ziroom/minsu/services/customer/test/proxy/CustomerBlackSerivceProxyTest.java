package com.ziroom.minsu.services.customer.test.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.sun.tools.javac.util.List;
import com.ziroom.minsu.entity.customer.CustomerBlackEntity;
import com.ziroom.minsu.services.customer.dto.CustomerBlackDto;
import com.ziroom.minsu.services.customer.proxy.CustomerBlackServiceProxy;
import com.ziroom.minsu.services.customer.proxy.CustomerCollectionServiceProxy;
import com.ziroom.minsu.services.customer.test.BaseTest;

import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by homelink on 2016/10/27.
 */
public class CustomerBlackSerivceProxyTest extends BaseTest {

    @Resource(name = "customer.customerBlackServiceProxy")
    private CustomerBlackServiceProxy customerBlackServiceProxy;


    @Test
    public void queryCustomerBlackList(){

        CustomerBlackDto par = new CustomerBlackDto();
        String result = customerBlackServiceProxy.queryCustomerBlackList(JsonEntityTransform.Object2Json(par));
        System.out.println(result);

    }

    @Test
    public void testfindCustomerBlackByUid(){
        String result = customerBlackServiceProxy.findCustomerBlackByUid("sdfsdfsdfsdfsf");

        System.out.println(result);

    }


    @Test
    public void testSaveCustomerBlack(){
        CustomerBlackEntity entity = new CustomerBlackEntity();
        entity.setFid(UUIDGenerator.hexUUID());
        entity.setUid(UUIDGenerator.hexUUID());
        entity.setRemark("测试啊啊");
        customerBlackServiceProxy.saveCustomerBlack(JsonEntityTransform.Object2Json(entity));
    }
    
    @Test
    public void testgetCustomerBlackBatch(){
    	 long start = System.currentTimeMillis();
    	 Set<String> uidSet = new HashSet<String>();
		 uidSet.add("23fba67c-1f0d-4009-8ab5-d94130243ad0");
		 uidSet.add("7f56afc5-3318-4dde-9a8b-29d84b370b0a");
		 uidSet.add("82215fee-800b-4987-ae99-7585c2558b99");
		 uidSet.add("30c83fd1-97ce-46b1-a632-e8076ab0fcae");
		 uidSet.add("912189cf-59df-48bc-a282-9649f6df489f");
		 uidSet.add("bba260fe-369c-4560-b03d-e22bb4e1e163");
		 uidSet.add("d235c7de-172d-42b4-b3b1-c760e45c8fae");
		 uidSet.add("6005c98b-9e2c-4442-9667-d68b79c7f6e6");
		 uidSet.add("d3e2eb42-b06b-4945-a9d2-0ddf404c580b");
		 uidSet.add("c73657fc-3f37-4a50-9df9-cfb90fbd11e3");
		 uidSet.add("061f210c-9fe0-40ec-a171-53af457e5bfb");
		 uidSet.add("a117ddd4-fc9f-4e40-b5eb-102664927ee2");
		 uidSet.add("d2b43b5e-e9c3-456b-9930-e2f5ce508216");
		 uidSet.add("22cc78ca-c14c-4174-8221-02f2de8235f7");
		 uidSet.add("8b5f4499-2e9f-41f3-b373-fe6ce0d0453d");
		 uidSet.add("9e874aeb-88b0-400b-9817-83e0f3905c7b");
		 uidSet.add("6964ed69-5726-4561-9698-7e412f54279e");
		 uidSet.add("ddc8cf02-2263-deed-7c13-1841d0726a73");
		 uidSet.add("91e0bea5-ec42-505e-7977-9fd31a5a232b");
		 uidSet.add("ed66bd11-5b99-48e1-8ec9-b9b3ea5b204d");
		 uidSet.add("5b73e83f-1944-4616-9a0e-a426b808b77d");
		 uidSet.add("5c47e29c-a503-6104-4a98-ad952627da72");
		 uidSet.add("1a9a0dfa-5fcb-4b04-a045-f51ee00d02a5");
		 uidSet.add("5303d9db-60a8-4692-b94d-450b67b165e1");
		 uidSet.add("17f29c6a-faa9-417b-8894-fecc52d81030");
		 uidSet.add("6e8b6fcb-bb98-4a71-9f08-f225b73f776b");
		 uidSet.add("8e8924ce-08d0-4f5c-9acd-acdef9252839");
		 uidSet.add("6e1eb192-ceea-4392-b7ed-b8314501e998");
		 uidSet.add("986e497a-f85a-470f-b498-bb9f39107e93");
		 uidSet.add("551f12ea-895e-70e1-9a16-0c5e86eca7e7");
		 uidSet.add("66b8f109-8649-48f9-bd60-97ccafb58cc7");
		 uidSet.add("0c6e4a5d-5ce8-4bba-b44b-cdd523fc3385");
		 uidSet.add("738c345d-f178-4e25-ae38-7c435be408ce");
		 uidSet.add("66f1bdd8-7f99-4c9c-868b-3e68f06eda5a");
		 uidSet.add("b654cd60-bc83-4d1f-a23b-12e6f9bdc4a6");
		 uidSet.add("111d7d21-beac-4e96-805a-90c069b1836a");
		 uidSet.add("accbf7e2-eac5-4b01-9d85-5ea54a934a5f");
		 uidSet.add("c6b55399-c53b-4561-b808-db211d2a7166");
	     System.out.println(customerBlackServiceProxy.getCustomerBlackBatch(JsonEntityTransform.Object2Json(uidSet)));
         long end = System.currentTimeMillis();
         System.out.println(end-start);
    }
    
}
