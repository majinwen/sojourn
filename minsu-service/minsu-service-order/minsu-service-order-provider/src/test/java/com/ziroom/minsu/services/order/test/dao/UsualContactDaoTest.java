package com.ziroom.minsu.services.order.test.dao;

import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.JsonEntityTransform;
import com.ziroom.minsu.entity.order.UsualContactEntity;
import com.ziroom.minsu.services.common.page.PageResult;
import com.ziroom.minsu.services.order.dao.UsualContactDao;
import com.ziroom.minsu.services.order.dto.UsualConRequest;
import com.ziroom.minsu.services.order.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>常用入住人</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/31.
 * @version 1.0
 * @since 1.0
 */
public class UsualContactDaoTest extends BaseTest {

        @Resource(name = "order.usualContactsDao")
        private UsualContactDao usualContactsDao;





    @Test
    public void TestfindOrderContactsByOrderSn() {
        List<UsualContactEntity> list = usualContactsDao.findOrderContactsByOrderSn("sn");
        System.out.println(JsonEntityTransform.Object2Json(list));
    }


        @Test
        public void TestgetOrderConfigList() {
                List<UsualContactEntity> list = usualContactsDao.getUsualContacts();
                System.out.println(JsonEntityTransform.Object2Json(list));
        }


        @Test
        public void TestgetUsualContactsByUid() {
                List<UsualContactEntity> list = usualContactsDao.getUsualContactsByUid("uid");
                System.out.println(JsonEntityTransform.Object2Json(list));
        }

        @Test
        public void TestgetDefaultContactsByUid() {
                UsualContactEntity usualContactsEntity = usualContactsDao.getDefaultContactsByUid("sc");
                System.out.println(JsonEntityTransform.Object2Json(usualContactsEntity));
        }



        @Test
        public void TestinsertUsualContactUidIsNULL() {
                UsualContactEntity uaual = new UsualContactEntity();
                uaual.setCardType(1);
                uaual.setCardValue("12312312");
                uaual.setConName("name");
                uaual.setConTel("sss");
                uaual.setConSex(1);
                uaual.setIsDefault(1);
                usualContactsDao.insertUsualContact(uaual);
        }

        @Test
        public void TestinsertUsualContact() {
                UsualContactEntity uaual = new UsualContactEntity();
                uaual.setUserUid("uid");
                uaual.setCardType(1);
                uaual.setCardValue("12312312");
                uaual.setConName("name");
                uaual.setConTel("sss");
                uaual.setConSex(1);
                uaual.setIsDefault(1);
                usualContactsDao.insertUsualContact(uaual);
        }
        @Test
        public void textFindUsualContactsByFid(){
        	
        	List<String> listFid = new ArrayList<String>();
        	listFid.add("aaa");
        	listFid.add("122333");
        	
        	UsualConRequest usualConRequest = new UsualConRequest();
        	usualConRequest.setListFid(listFid);
        	usualConRequest.setUserUid("1122222");

            PagingResult<UsualContactEntity> usualContactsByFid = usualContactsDao.findUsualContactsByFid(usualConRequest);

            System.out.println(usualContactsByFid);
        }
        
        @Test
        public void textUpdateByFid(){
        	 UsualContactEntity uaual = new UsualContactEntity();
             uaual.setUserUid("uidfdfds");
             uaual.setCardType(1);
             uaual.setCardValue("12fdsfdsf312312");
             uaual.setConName("namedsfdsfdsf");
             uaual.setConTel("ssffdsfdsfs");
             uaual.setConSex(1);
             uaual.setIsDefault(1);
             uaual.setFid("122333");
           int r =   usualContactsDao.updateByFid(uaual);
           
           System.out.println(r);
        }

}
